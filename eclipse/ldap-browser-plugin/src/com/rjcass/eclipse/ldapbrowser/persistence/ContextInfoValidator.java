package com.rjcass.eclipse.ldapbrowser.persistence;

public class ContextInfoValidator
{
	public static Validation validate(String name, String protocol, String hostname, int port, String authMode, String principal,
			String password)
	{
		Validation validation = (new ContextInfoValidator()).new Validation();
		validation.valid = true;
		StringBuilder sb = new StringBuilder();
		if (name.length() < 1 || name.length() > ContextInfo.NAME_MAX_LENGTH)
		{
			sb.append("Name must be between 1 and " + ContextInfo.NAME_MAX_LENGTH + " characters");
			validation.valid = false;
		}
		if (hostname.length() < 1 || hostname.length() > ContextInfo.HOSTNAME_MAX_LENGTH)
		{
			if (sb.length() > 0)
				sb.append("; ");
			sb.append("Hostname must be between 1 and " + ContextInfo.HOSTNAME_MAX_LENGTH + " characters");
			validation.valid = false;
		}
		if (port < ContextInfo.PORT_MIN || port > ContextInfo.PORT_MAX)
		{
			if (sb.length() > 0)
				sb.append("; ");
			sb.append("Port must be between " + ContextInfo.PORT_MIN + " and " + ContextInfo.PORT_MAX);
			validation.valid = false;
		}
		if (principal.length() > ContextInfo.PRINCIPAL_MAX_LENGTH)
		{
			if (sb.length() > 0)
				sb.append("; ");
			sb.append("Principal must be <= " + ContextInfo.PRINCIPAL_MAX_LENGTH + " characters");
			validation.valid = false;
		}
		if (password.length() > ContextInfo.PASSWORD_MAX_LENGTH)
		{
			if (sb.length() > 0)
				sb.append("; ");
			sb.append("Password must be <= " + ContextInfo.PASSWORD_MAX_LENGTH + " characters");
			validation.valid = false;
		}

		if (sb.length() > 0)
		{
			validation.messages = sb.toString();
		}

		return validation;
	}

	public class Validation
	{
		public boolean	valid;
		public String	messages;
	}
}
