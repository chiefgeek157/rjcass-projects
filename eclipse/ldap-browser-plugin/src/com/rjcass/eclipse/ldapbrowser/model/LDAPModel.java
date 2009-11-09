package com.rjcass.eclipse.ldapbrowser.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.PartialResultException;
import javax.naming.SizeLimitExceededException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.rjcass.eclipse.ldapbrowser.LDAPBrowserException;
import com.rjcass.eclipse.ldapbrowser.persistence.ContextInfo;
import com.rjcass.eclipse.ldapbrowser.persistence.ContextStorageManager;

public class LDAPModel
{
	private static final String		LDAP_INITIAL_CONTEXT_CLASSNAME	= "com.sun.jndi.ldap.LdapCtxFactory";
	private static final int		PAGE_SIZE						= 100;

	private ContextStorageManager	mManager;
	private Set<LDAPModelListener>	mListeners;

	public LDAPModel()
	{
		mListeners = new HashSet<LDAPModelListener>();

		IConfigurationElement[] elems = Platform.getExtensionRegistry().getConfigurationElementsFor(
				ContextStorageManager.EXTENSION_POINT_ID);
		for (IConfigurationElement elem : elems)
		{
			try
			{
				Object obj = elem.createExecutableExtension(ContextStorageManager.EXTENSION_POINT_CLASS_NAME_ATTR);
				if (obj instanceof ContextStorageManager)
				{
					mManager = (ContextStorageManager)obj;
				}
			}
			catch (CoreException e)
			{
				throw new LDAPBrowserException(e);
			}
		}
	}

	public List<RootLDAPNode> getRootContexts()
	{
		List<ContextInfo> contexts = mManager.getContexts();
		List<RootLDAPNode> nodes = new ArrayList<RootLDAPNode>();
		for (ContextInfo info : contexts)
		{
			RootLDAPNode node = new RootLDAPNode(this, info);
			nodes.add(node);
		}
		return nodes;
	}

	public void addChildren(LDAPNode node)
	{
		LdapContext context = null;
		try
		{
			context = getLdapContext((RootLDAPNode)node.getRoot());
			context.setRequestControls(new Control[] { new PagedResultsControl(PAGE_SIZE, Control.CRITICAL) });

			byte[] cookie = null;
			int numResults = 0;
			do
			{
				NamingEnumeration<NameClassPair> names = context.list(node.getDN());

				try
				{
					while (names != null && names.hasMore())
					{
						NameClassPair pair = names.next();
						String name = pair.getName();
						LDAPNode childNode = new LDAPNode(this, name);
						node.addChild(childNode);
						numResults++;
					}
				}
				catch (PartialResultException e)
				{
					System.err.println("Ignoring partial result exception: " + e);
				}
				catch (SizeLimitExceededException e)
				{
					System.err.println("Ignoring size limit exceeded exception: " + e);
				}

				Control[] controls = context.getResponseControls();
				if (controls != null)
				{
					for (int i = 0; i < controls.length; i++)
					{
						if (controls[i] instanceof PagedResultsResponseControl)
						{
							PagedResultsResponseControl prrc = (PagedResultsResponseControl)controls[i];
							cookie = prrc.getCookie();
						}
					}
				}

				context.setRequestControls(new Control[] { new PagedResultsControl(PAGE_SIZE, cookie, Control.CRITICAL) });

			} while (cookie != null);

			System.err.println("Total children: " + numResults);
		}
		catch (AuthenticationException e)
		{
			throw new LDAPAuthenticationException(e);
		}
		catch (NamingException e)
		{
			throw new LDAPBrowserException(e);
		}
		catch (IOException e)
		{
			throw new LDAPBrowserException(e);
		}
		finally
		{
			if (context != null)
			{
				try
				{
					context.close();
				}
				catch (NamingException e)
				{
					// Ignore
				}
			}
		}
	}

	public List<AttributeValue> getNodeAttributes(LDAPNode node)
	{
		List<AttributeValue> attrList = new ArrayList<AttributeValue>();

		LdapContext context = null;
		try
		{
			context = getLdapContext((RootLDAPNode)node.getRoot());

			Attributes attrs = context.getAttributes(node.getDN());
			NamingEnumeration<? extends Attribute> e = attrs.getAll();
			while (e.hasMore())
			{
				Attribute attr = e.next();
				String id = attr.getID();

				NamingEnumeration<?> ne = attr.getAll();
				while (ne.hasMore())
				{
					Object valueObj = ne.next();
					String value = null;
					if (valueObj != null)
						value = valueObj.toString();
					AttributeValue attrValue = new AttributeValue(id, value);
					attrList.add(attrValue);
				}
			}
		}
		catch (AuthenticationException e)
		{
			throw new LDAPAuthenticationException(e);
		}
		catch (NamingException e)
		{
			throw new LDAPBrowserException(e);
		}
		catch (IOException e)
		{
			throw new LDAPBrowserException(e);
		}
		finally
		{
			if (context != null)
			{
				try
				{
					context.close();
				}
				catch (NamingException e)
				{
					// Ignore
				}
			}
		}

		return attrList;
	}

	public RootLDAPNode addRootContext(String name, String protocol, String hostname, int port, String authMode, String principal,
			String password)
	{
		ContextInfo info = mManager.storeContext(name, protocol, hostname, port, authMode, principal, password);

		RootLDAPNode node = new RootLDAPNode(this, info);
		fireRootContextAddedEvent(node);
		return node;
	}

	public void updateRootContext(RootLDAPNode node, String name, String protocol, String hostname, int port, String authMode,
			String principal, String password)
	{
		ContextInfo info = mManager.updateContext(node.getName(), name, protocol, hostname, port, authMode, principal, password);

		node.setInfo(info);
		fireRootContextChangedEvent(node);
	}

	public void removeRootContext(RootLDAPNode node)
	{
		mManager.deleteContext(node.getInfo());
		fireRootContextRemovedEvent(node);
	}

	public void addLDAPModelListener(LDAPModelListener listener)
	{
		mListeners.add(listener);
	}

	public void removeLDAPModelListener(LDAPModelListener listener)
	{
		mListeners.remove(listener);
	}

	private LdapContext getLdapContext(RootLDAPNode root) throws NamingException, IOException
	{
		StringBuilder ldapURL = new StringBuilder("ldap://");
		ldapURL.append(root.getHostname()).append(":").append(root.getPort());

		Hashtable<String, String> env = new Hashtable<String, String>();

		env.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_INITIAL_CONTEXT_CLASSNAME);
		env.put(Context.PROVIDER_URL, ldapURL.toString());
		env.put(Context.SECURITY_AUTHENTICATION, root.getAuthMode());
		env.put(Context.SECURITY_PRINCIPAL, root.getPrincipal());
		env.put(Context.SECURITY_CREDENTIALS, root.getPassword());
		// env.put(Context.REFERRAL, "follow");

		return new InitialLdapContext(env, null);
	}

	private void fireRootContextAddedEvent(RootLDAPNode node)
	{
		Set<LDAPModelListener> listeners = new HashSet<LDAPModelListener>(mListeners);
		for (LDAPModelListener listener : listeners)
		{
			listener.rootContextAdded(node);
		}
	}

	private void fireRootContextChangedEvent(RootLDAPNode node)
	{
		Set<LDAPModelListener> listeners = new HashSet<LDAPModelListener>(mListeners);
		for (LDAPModelListener listener : listeners)
		{
			listener.rootContextChanged(node);
		}
	}

	private void fireRootContextRemovedEvent(RootLDAPNode node)
	{
		Set<LDAPModelListener> listeners = new HashSet<LDAPModelListener>(mListeners);
		for (LDAPModelListener listener : listeners)
		{
			listener.rootContextRemoved(node);
		}
	}
}
