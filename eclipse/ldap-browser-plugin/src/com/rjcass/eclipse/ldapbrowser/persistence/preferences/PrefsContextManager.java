package com.rjcass.eclipse.ldapbrowser.persistence.preferences;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;

import com.rjcass.eclipse.ldapbrowser.LDAPBrowserException;
import com.rjcass.eclipse.ldapbrowser.LDAPBrowserPlugin;
import com.rjcass.eclipse.ldapbrowser.persistence.ContextInfo;
import com.rjcass.eclipse.ldapbrowser.persistence.ContextInfoValidator;
import com.rjcass.eclipse.ldapbrowser.persistence.ContextStorageManager;
import com.rjcass.eclipse.ldapbrowser.persistence.NameInUseException;
import com.rjcass.eclipse.ldapbrowser.persistence.NoSuchContextException;

public class PrefsContextManager implements ContextStorageManager
{
	public String	PREF_CONTEXT_LIST	= "ContextList";
	public String	PREF_CONTEXT_DELIM	= "|";
	public String	PREF_FIELD_DELIM	= ";";
	public String	PREF_FIELD_EQUALITY	= "=";

	public String	FIELD_NAME			= "name";
	public String	FIELD_PROTOCOL		= "protocol";
	public String	FIELD_HOSTNAME		= "hostname";
	public String	FIELD_PORT			= "port";
	public String	FIELD_AUTH_MODE		= "authMode";
	public String	FIELD_PRINCIPAL		= "principal";
	public String	FIELD_PASSWORD		= "password";

	@Override
	public ContextInfo storeContext(String name, String protocol, String hostname, int port, String authMode, String principal,
			String password)
	{
		ContextInfoValidator.Validation result = ContextInfoValidator.validate(name, protocol, hostname, port, authMode, principal,
				password);
		if (!result.valid)
			throw new IllegalArgumentException(result.messages);

		IPersistentPreferenceStore prefs = (IPersistentPreferenceStore)LDAPBrowserPlugin.getDefault().getPreferenceStore();
		ISecurePreferences securePrefs = LDAPBrowserPlugin.getDefault().getSecurePreferences();

		Map<String, Map<String, String>> contexts = getContexts(prefs, securePrefs);
		if (contexts.containsKey(name))
			throw new NameInUseException("Name already in use: " + name);

		Map<String, String> fields = new HashMap<String, String>();
		fields.put(FIELD_PROTOCOL, protocol);
		fields.put(FIELD_HOSTNAME, hostname);
		fields.put(FIELD_PORT, String.valueOf(port));
		fields.put(FIELD_AUTH_MODE, authMode);
		fields.put(FIELD_PRINCIPAL, principal);
		fields.put(FIELD_PASSWORD, password);

		contexts.put(name, fields);

		putContexts(contexts, prefs, securePrefs);

		PrefsContextInfo info = new PrefsContextInfo(name, protocol, hostname, port, authMode, principal, password);

		return info;
	}

	@Override
	public ContextInfo updateContext(String oldName, String name, String protocol, String hostname, int port, String authMode,
			String principal, String password)
	{
		ContextInfoValidator.Validation result = ContextInfoValidator.validate(name, protocol, hostname, port, authMode, principal,
				password);
		if (!result.valid)
			throw new IllegalArgumentException(result.messages);

		IPersistentPreferenceStore prefs = (IPersistentPreferenceStore)LDAPBrowserPlugin.getDefault().getPreferenceStore();
		ISecurePreferences securePrefs = LDAPBrowserPlugin.getDefault().getSecurePreferences();

		Map<String, Map<String, String>> contexts = getContexts(prefs, securePrefs);
		if (!contexts.containsKey(oldName))
			throw new NameInUseException("Context not found: " + oldName);
		if (contexts.containsKey(name))
			throw new NameInUseException("Name already in use: " + name);

		contexts.remove(oldName);

		Map<String, String> fields = new HashMap<String, String>();
		fields.put(FIELD_PROTOCOL, protocol);
		fields.put(FIELD_HOSTNAME, hostname);
		fields.put(FIELD_PORT, String.valueOf(port));
		fields.put(FIELD_AUTH_MODE, authMode);
		fields.put(FIELD_PRINCIPAL, principal);
		fields.put(FIELD_PASSWORD, password);

		contexts.put(name, fields);

		putContexts(contexts, prefs, securePrefs);

		PrefsContextInfo info = new PrefsContextInfo(name, protocol, hostname, port, authMode, principal, password);

		return info;
	}

	@Override
	public void deleteContext(ContextInfo info)
	{
		if (info == null)
			throw new NullPointerException("Info cannot be null");

		IPersistentPreferenceStore prefs = (IPersistentPreferenceStore)LDAPBrowserPlugin.getDefault().getPreferenceStore();
		ISecurePreferences securePrefs = LDAPBrowserPlugin.getDefault().getSecurePreferences();

		String name = info.getName();

		Map<String, Map<String, String>> contexts = getContexts(prefs, securePrefs);
		if (!contexts.containsKey(name))
			throw new NoSuchContextException("Name not found: " + name);
		contexts.remove(name);

		putContexts(contexts, prefs, securePrefs);
	}

	@Override
	public List<ContextInfo> getContexts()
	{
		List<ContextInfo> contextInfos = new ArrayList<ContextInfo>();

		IPreferenceStore prefs = LDAPBrowserPlugin.getDefault().getPreferenceStore();
		ISecurePreferences securePrefs = LDAPBrowserPlugin.getDefault().getSecurePreferences();

		Map<String, Map<String, String>> contexts = getContexts(prefs, securePrefs);

		for (Map.Entry<String, Map<String, String>> context : contexts.entrySet())
		{
			String name = context.getKey();
			Map<String, String> fields = context.getValue();
			String protocol = fields.get(FIELD_PROTOCOL);
			String hostname = fields.get(FIELD_HOSTNAME);
			int port = Integer.valueOf(fields.get(FIELD_PORT));
			String authMode = fields.get(FIELD_AUTH_MODE);
			String principal = fields.get(FIELD_PRINCIPAL);
			String password = fields.get(FIELD_PASSWORD);
			ContextInfo info = new PrefsContextInfo(name, protocol, hostname, port, authMode, principal, password);

			contextInfos.add(info);
		}

		return contextInfos;
	}

	private Map<String, Map<String, String>> getContexts(IPreferenceStore prefs, ISecurePreferences securePrefs)
	{
		Map<String, Map<String, String>> contexts = new HashMap<String, Map<String, String>>();

		String allContextsStr = prefs.getString(PREF_CONTEXT_LIST).trim();
		StringTokenizer allContextsTokenizer = new StringTokenizer(allContextsStr, PREF_CONTEXT_DELIM);

		String charSet = Charset.defaultCharset().name();
		while (allContextsTokenizer.hasMoreTokens())
		{
			String contextStr = allContextsTokenizer.nextToken().trim();
			StringTokenizer contextST = new StringTokenizer(contextStr, PREF_FIELD_DELIM);
			Map<String, String> fields = new HashMap<String, String>();
			while (contextST.hasMoreTokens())
			{
				String field = contextST.nextToken();
				String[] parts = field.split(PREF_FIELD_EQUALITY);
				String value = "";
				if (parts.length == 2)
				{
					try
					{
						value = URLDecoder.decode(parts[1], charSet);
					}
					catch (UnsupportedEncodingException e)
					{
						throw new LDAPBrowserException(e);
					}
				}
				fields.put(parts[0], value);
			}

			String name = fields.remove(FIELD_NAME);
			if (name == null)
				throw new LDAPBrowserException("No name defined: " + contextStr);

			try
			{
				String encName = URLEncoder.encode(name, charSet);
				String password = securePrefs.get(encName + "." + FIELD_PASSWORD, "");
				fields.put(FIELD_PASSWORD, password);
			}
			catch (StorageException e)
			{
				throw new LDAPBrowserException(e);
			}
			catch (UnsupportedEncodingException e)
			{
				throw new LDAPBrowserException(e);
			}

			contexts.put(name, fields);
		}

		return contexts;
	}

	private void putContexts(Map<String, Map<String, String>> contexts, IPersistentPreferenceStore prefs,
			ISecurePreferences securePrefs)
	{
		String charSet = Charset.defaultCharset().name();
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Map<String, String>> context : contexts.entrySet())
		{
			Map<String, String> fields = context.getValue();
			try
			{
				String encName = URLEncoder.encode(context.getKey(), charSet);
				String encProtocol = URLEncoder.encode(fields.get(FIELD_PROTOCOL), charSet);
				String encHostname = URLEncoder.encode(fields.get(FIELD_HOSTNAME), charSet);
				String encPort = URLEncoder.encode(fields.get(FIELD_PORT), charSet);
				String encAuthMode = URLEncoder.encode(fields.get(FIELD_AUTH_MODE), charSet);
				String encPrincipal = URLEncoder.encode(fields.get(FIELD_PRINCIPAL), charSet);

				sb.append(FIELD_NAME).append(PREF_FIELD_EQUALITY).append(encName).append(PREF_FIELD_DELIM);
				sb.append(FIELD_PROTOCOL).append(PREF_FIELD_EQUALITY).append(encProtocol).append(PREF_FIELD_DELIM);
				sb.append(FIELD_HOSTNAME).append(PREF_FIELD_EQUALITY).append(encHostname).append(PREF_FIELD_DELIM);
				sb.append(FIELD_PORT).append(PREF_FIELD_EQUALITY).append(encPort).append(PREF_FIELD_DELIM);
				sb.append(FIELD_AUTH_MODE).append(PREF_FIELD_EQUALITY).append(encAuthMode).append(PREF_FIELD_DELIM);
				sb.append(FIELD_PRINCIPAL).append(PREF_FIELD_EQUALITY).append(encPrincipal).append(PREF_FIELD_DELIM);
				sb.append(PREF_CONTEXT_DELIM);

				try
				{
					securePrefs.put(encName + "." + FIELD_PASSWORD, fields.get(FIELD_PASSWORD), true);
				}
				catch (StorageException e)
				{
					throw new LDAPBrowserException(e);
				}
			}
			catch (UnsupportedEncodingException e)
			{
				throw new LDAPBrowserException(e);
			}
		}

		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		String contextsStr = sb.toString();

		prefs.setValue(PREF_CONTEXT_LIST, contextsStr);
		try
		{
			prefs.save();
		}
		catch (IOException e)
		{
			throw new LDAPBrowserException(e);
		}
	}
}
