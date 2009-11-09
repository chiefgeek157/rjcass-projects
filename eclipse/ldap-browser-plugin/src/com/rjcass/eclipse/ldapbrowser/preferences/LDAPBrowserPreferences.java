package com.rjcass.eclipse.ldapbrowser.preferences;

public interface LDAPBrowserPreferences
{
	String	LDAP_PROVIDER_LIST			= "providers";
	String	LDAP_PROVIDER_LIST_DELIM	= ",";

	String	PROTOCOL					= "protocol";
	String	HOSTNAME					= "hostname";
	String	PORT						= "port";
	String	AUTH_MODE					= "authMode";
	String	USERNAME					= "username";
	String	PASSWORD					= "password";

	String	PROTOCOL_LDAP				= "ldap";

	String	AUTH_MODE_SIMPLE			= "simple";

	String	DEFAULT_PROTOCOL			= PROTOCOL_LDAP;
	int		DEFAULT_PORT				= 389;
	String	DEFAULT_AUTH_MODE			= AUTH_MODE_SIMPLE;
}
