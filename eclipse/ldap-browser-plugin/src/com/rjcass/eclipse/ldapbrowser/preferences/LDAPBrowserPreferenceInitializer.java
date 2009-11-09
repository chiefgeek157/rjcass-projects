package com.rjcass.eclipse.ldapbrowser.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

public class LDAPBrowserPreferenceInitializer extends AbstractPreferenceInitializer
{
	public LDAPBrowserPreferenceInitializer()
	{}

	@Override
	public void initializeDefaultPreferences()
	{
		//IEclipsePreferences node = new DefaultScope().getNode(LDAPBrowserPlugin.PLUGIN_ID);
		//node.put(LDAPBrowserPreferences.PROTOCOL, LDAPBrowserPreferences.DEFAULT_PROTOCOL);
		//node.put(LDAPBrowserPreferences.PORT, LDAPBrowserPreferences.DEFAULT_PORT);
	}
}
