package com.rjcass.eclipse.ldapbrowser.persistence;

public interface ContextInfo
{
	int		NAME_MAX_LENGTH			= 50;

	String	PROTOCOL_LDAP			= "ldap";

	int		HOSTNAME_MAX_LENGTH		= 255;

	int		PORT_MIN				= 1;
	int		PORT_MAX				= 65535;
	int		PORT_MAX_LENGTH			= 5;
	int		PORT_LDAP				= 389;
	int		PORT_LDAPS				= 636;

	String	AUTH_MODE_SIMPLE		= "simple";

	int		PRINCIPAL_MAX_LENGTH	= 1000;

	int		PASSWORD_MAX_LENGTH		= 256;

	String	DEFAULT_PROTOCOL		= PROTOCOL_LDAP;
	int		DEFAULT_PORT			= PORT_LDAP;
	String	DEFAULT_AUTH_MODE		= AUTH_MODE_SIMPLE;

	String getName();

	String getProtocol();

	String getHostname();

	int getPort();

	String getPrincipal();

	String getPassword();

	String getAuthMode();
}