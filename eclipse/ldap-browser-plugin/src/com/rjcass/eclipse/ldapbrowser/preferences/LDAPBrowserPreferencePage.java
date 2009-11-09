package com.rjcass.eclipse.ldapbrowser.preferences;

import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.rjcass.eclipse.ldapbrowser.LDAPBrowserPlugin;

public class LDAPBrowserPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage
{
	private ISecurePreferences	mSecurePrefs;

	public LDAPBrowserPreferencePage()
	{
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench)
	{
		mSecurePrefs = LDAPBrowserPlugin.getDefault().getSecurePreferences();
		setPreferenceStore(LDAPBrowserPlugin.getDefault().getPreferenceStore());
		setDescription("LDAP Browser Preferences");
	}

	public ISecurePreferences getSecurePreferences()
	{
		return mSecurePrefs;
	}

	@Override
	protected void createFieldEditors()
	{
		addField(new RadioGroupFieldEditor(LDAPBrowserPreferences.PROTOCOL, "Protocol:", 1, new String[][] { {
				LDAPBrowserPreferences.PROTOCOL_LDAP, LDAPBrowserPreferences.PROTOCOL_LDAP }, }, getFieldEditorParent()));
		addField(new StringFieldEditor(LDAPBrowserPreferences.HOSTNAME, "Host name:", getFieldEditorParent()));
		addField(new IntegerFieldEditor(LDAPBrowserPreferences.PORT, "Port number:", getFieldEditorParent()));
		addField(new StringFieldEditor(LDAPBrowserPreferences.USERNAME, "Username:", getFieldEditorParent()));
		addField(new SecurePasswordFieldEditor(LDAPBrowserPreferences.PASSWORD, "Password:", getFieldEditorParent(),
				getSecurePreferences()));
	}
}
