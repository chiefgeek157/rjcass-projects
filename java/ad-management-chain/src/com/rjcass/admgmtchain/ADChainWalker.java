package com.rjcass.admgmtchain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class ADChainWalker
{
	private String	mLDAPUser;
	private String	mLDAPPassword;
	private String	mLDAPURL;
	private String	mUserSearchBase;
	private boolean	mUseSSL;

	public static void main(String[] args)
	{
		ADChainWalker chain = new ADChainWalker();

		chain.setLDAPUser("CN=XXXXXXXX,OU=Wellpoint,OU=Associates,OU=usersAndGroups,DC=us,DC=ad,DC=wellpoint,DC=com");
		chain.setLDAPPassword("XXXXXXXX");
		chain.setLDAPURL("ldap://us.ad.wellpoint.com:389");
		chain.setUserSearchBase("OU=usersAndGroups,DC=us,DC=ad,DC=wellpoint,DC=com");

		// String ldapURL = "ldap://us.ad.wellpoint.com:636";
		// boolean useSSL = true;

		BufferedReader r = null;
		try
		{
			r = new BufferedReader(new FileReader("C:/Documents and Settings/XXXXXXXX/Desktop/XMLSpyUsers.txt"));

			Set<String> names = new HashSet<String>();

			String name = r.readLine();
			while (name != null)
			{
				System.out.println("Adding user to process " + name);
				names.add(name.trim());
				name = r.readLine();
			}

			Collection<ADUser> users = chain.buildManagementChains(names);

			OutputStream fos = null;
			try
			{
				fos = new FileOutputStream("C:/Documents and Settings/XXXXXXXX/Desktop/XMLSpy User Chain.csv");
				chain.createUserReport(users, fos);
			}
			catch (FileNotFoundException e)
			{
				System.out.println("Could not open output file");
			}
			finally
			{
				if (fos != null)
					try
					{
						fos.close();
					}
					catch (IOException e)
					{
						// Ignore
					}
			}
		}
		catch (FileNotFoundException e1)
		{
			System.out.println("Could not open input file");
		}
		catch (IOException e)
		{
			System.out.println("Could not read line");
		}
		finally
		{
			if (r != null)
			{
				try
				{
					r.close();
				}
				catch (IOException e)
				{
					// Ignore
				}
			}
		}
	}

	public ADChainWalker()
	{}

	public void createUserReport(Collection<ADUser> users, OutputStream os)
	{
		PrintWriter pw = new PrintWriter(os);

		pw.print("User,User Title,User Cost Center,User Department");
		pw.print(",Manager 1,Manager 1 Title,Manager 1 Cost Center,Manager 1 Department");
		pw.print(",Manager 2,Manager 2 Title,Manager 2 Cost Center,Manager 2 Department");
		pw.print(",Manager 3,Manager 3 Title,Manager 3 Cost Center,Manager 3 Department");
		pw.print(",Manager 4,Manager 4 Title,Manager 4 Cost Center,Manager 4 Department");
		pw.print(",Manager 5,Manager 5 Title,Manager 5 Cost Center,Manager 5 Department");
		pw.print(",Manager 6,Manager 6 Title,Manager 6 Cost Center,Manager 6 Department");
		pw.print(",Manager 7,Manager 7 Title,Manager 7 Cost Center,Manager 7 Department");
		pw.print(",Manager 8,Manager 8 Title,Manager 8 Cost Center,Manager 8 Department");
		pw.println();

		for (ADUser user : users)
		{
			StringBuilder b = new StringBuilder();
			b.append("\"");
			b.append(user.getDisplayName());
			b.append("\",\"");
			b.append(user.getTitle());
			b.append("\",\"");
			b.append(user.getCostCenter());
			b.append("\",\"");
			b.append(user.getDepartment());
			b.append("\"");
			List<ADUser> chain = user.getManagementChain();
			for (int i = chain.size() - 1; i > 0; i--)
			{
				ADUser manager = chain.get(i);
				b.append(",\"");
				b.append(manager.getDisplayName());
				b.append("\",\"");
				b.append(manager.getTitle());
				b.append("\",\"");
				b.append(manager.getCostCenter());
				b.append("\",\"");
				b.append(manager.getDepartment());
				b.append("\"");
			}
			for (int i = 8 - chain.size(); i > 0; i--)
			{
				b.append(",,,,");
			}
			pw.println(b);
		}
		pw.flush();
	}

	public void setLDAPUser(String user)
	{
		mLDAPUser = user;
	}

	public String getLDAPUser()
	{
		return mLDAPUser;
	}

	public String getLDAPPassword()
	{
		return mLDAPPassword;
	}

	public void setLDAPPassword(String password)
	{
		mLDAPPassword = password;
	}

	public String getLDAPURL()
	{
		return mLDAPURL;
	}

	public void setLDAPURL(String ldapURL)
	{
		mLDAPURL = ldapURL;
	}

	public String getUserSearchBase()
	{
		return mUserSearchBase;
	}

	public void setUserSearchBase(String userSearchBase)
	{
		mUserSearchBase = userSearchBase;
	}

	public boolean usesSSL()
	{
		return mUseSSL;
	}

	public void useSSL(boolean useSSL)
	{
		mUseSSL = useSSL;
	}

	public Collection<ADUser> buildManagementChains(Collection<String> names)
	{
		Map<String, ADUser> userMap = new HashMap<String, ADUser>();
		List<ADUser> users = new ArrayList<ADUser>();

		DirContext ctx = null;

		try
		{
			ctx = getContext();

			SearchControls searchCtls = new SearchControls();
			searchCtls.setReturningAttributes(ADUser.ATTRIBUTES);
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			for (String name : names)
			{
				try
				{
					ADUser user = buildChainAtDisplayName(name, userMap, ctx, searchCtls);
					if (user != null)
						users.add(user);
					else
						System.out.println("WARNING: user " + name + " not found");
				}
				catch (NamingException e)
				{
					System.err.println("Problem with query: " + e);
				}
			}
		}
		catch (NamingException e)
		{
			System.err.println("Problem authenticating: " + e);
		}
		finally
		{
			if (ctx != null)
			{
				try
				{
					ctx.close();
				}
				catch (NamingException e)
				{
					// Do nothing
				}
			}
		}

		return users;
	}

	private void dumpAttributes(Attributes attrs) throws NamingException
	{
		NamingEnumeration<? extends Attribute> attrEnum = attrs.getAll();
		while (attrEnum.hasMore())
		{
			Attribute attr = attrEnum.next();
			String id = attr.getID();
			NamingEnumeration<?> values = attr.getAll();
			while (values.hasMore())
			{
				Object value = values.next();
				System.out.println("   " + id + ": '" + value + "'");
			}
		}
	}

	private DirContext getContext() throws NamingException
	{
		Hashtable<String, String> env = new Hashtable<String, String>();

		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

		env.put(Context.PROVIDER_URL, mLDAPURL);
		if (mUseSSL)
			env.put(Context.SECURITY_PROTOCOL, "ssl");

		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, mLDAPUser);
		env.put(Context.SECURITY_CREDENTIALS, mLDAPPassword);

		LdapContext ctx = new InitialLdapContext(env, null);

		// Attributes attrs = ctx.getAttributes("");
		// dumpAttributes(attrs);

		return ctx;
	}

	private ADUser buildChainAtDisplayName(String displayName, Map<String, ADUser> users, DirContext ctx, SearchControls searchCtls)
			throws NamingException
	{
		System.out.println("Searching for user " + displayName);
		String searchFilter = "(&(objectClass=user)(" + ADUser.ATTR_DISPLAY_NAME + "=" + displayName + "*))";

		return buildChain(searchFilter, users, ctx, searchCtls);
	}

	private ADUser buildChainAtDN(String dn, Map<String, ADUser> users, DirContext ctx, SearchControls searchCtls)
			throws NamingException
	{
		// System.out.println("Searching for user " + dn);
		String searchFilter = "(&(objectClass=user)(" + ADUser.ATTR_DISTINGUISHED_NAME + "=" + dn + "))";

		return buildChain(searchFilter, users, ctx, searchCtls);
	}

	private ADUser buildChain(String searchFilter, Map<String, ADUser> users, DirContext ctx, SearchControls searchCtls)
			throws NamingException
	{
		ADUser user = null;

		NamingEnumeration<SearchResult> searchResults = ctx.search(mUserSearchBase, searchFilter, searchCtls);

		Attributes userAttributes = null;
		BigInteger maxTimestamp = BigInteger.valueOf(0);
		while (searchResults.hasMoreElements())
		{
			BigInteger timestamp = null;
			SearchResult sr = searchResults.next();
			Attributes attrs = sr.getAttributes();
			if (attrs != null)
			{
				String timestampText = getAttribute(attrs, ADUser.ATTR_LAST_LOGON_TIMESTAMP);
				if (timestampText != null)
				{
					timestamp = new BigInteger(timestampText);
				}
				else
				{
					System.out.println("WARNING: no value for " + ADUser.ATTR_LAST_LOGON_TIMESTAMP);
				}

				if (userAttributes == null)
				{
					userAttributes = attrs;
					if (timestamp != null)
						maxTimestamp = timestamp;
				}
				else
				{
					System.out.println("WARNING: more than one result matched " + searchFilter);
					if (timestamp != null)
					{
						if (timestamp.compareTo(maxTimestamp) > 0)
						{
							System.out.println("INFO: using this user instead " + timestamp);
							userAttributes = attrs;
							maxTimestamp = timestamp;
						}
					}
				}
			}
			else
			{
				System.out.println("WARNING: NULL attributes for " + searchFilter);
			}
		}

		if (userAttributes != null)
		{
			user = createADUser(userAttributes);
			String dn = user.getDistinguishedName();
			if (!users.containsKey(dn))
			{
				// System.out.println("Putting user " + user);
				users.put(dn, user);
				String managerDN = getAttribute(userAttributes, ADUser.ATTR_MANAGER);
				if (managerDN != null)
				{
					ADUser manager = users.get(managerDN);
					if (manager == null)
						manager = buildChainAtDN(managerDN, users, ctx, searchCtls);
					if (manager != user)
					{
						// System.out.println("Setting user " + user +
						// " manager " + manager);
						user.setManager(manager);
					}
				}
				else
				{
					System.out.println("WARNING: user manager is blank for " + searchFilter);
				}
			}
			else
			{
				System.out.println("WARNING: NULL attributes for " + searchFilter);
			}
		}

		if (user == null)
			System.out.println("WARNING: Did not find " + searchFilter);
		return user;
	}

	private ADUser createADUser(Attributes attrs) throws NamingException
	{
		// System.out.println("Creating " + attrs);
		ADUser user = new ADUser();

		user.setCostCenter(getAttribute(attrs, ADUser.ATTR_COST_CENTER));
		user.setDepartment(getAttribute(attrs, ADUser.ATTR_DEPARTMENT));
		user.setDisplayName(getAttribute(attrs, ADUser.ATTR_DISPLAY_NAME));
		user.setDistinguishedName(getAttribute(attrs, ADUser.ATTR_DISTINGUISHED_NAME));
		user.setEmail(getAttribute(attrs, ADUser.ATTR_EMAIL));
		user.setLastLogonTimestamp(getAttribute(attrs, ADUser.ATTR_LAST_LOGON_TIMESTAMP));
		user.setPhone(getAttribute(attrs, ADUser.ATTR_PHONE));
		user.setTitle(getAttribute(attrs, ADUser.ATTR_TITLE));

		return user;
	}

	private String getAttribute(Attributes attrs, String id) throws NamingException
	{
		String result = null;
		Attribute attr = attrs.get(id);
		if (attr != null)
		{
			result = (String)attr.get(0);
		}

		return result;
	}
}
