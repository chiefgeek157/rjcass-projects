package com.rjcass.eclipse.ldapbrowser.preferences;

import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;

import com.rjcass.eclipse.ldapbrowser.LDAPBrowserException;

public class SecurePasswordFieldEditor extends StringFieldEditor
{
	protected ISecurePreferences	mSecurePreferences;

	public SecurePasswordFieldEditor(String name, String labelText, Composite parent, ISecurePreferences prefs)
	{
		super(name, labelText, parent);
		mSecurePreferences = prefs;
	}

	public SecurePasswordFieldEditor(String name, String labelText, int width, Composite parent, ISecurePreferences prefs)
	{
		super(name, labelText, width, parent);
		mSecurePreferences = prefs;
	}

	public SecurePasswordFieldEditor(String name, String labelText, int width, int strategy, Composite parent,
			ISecurePreferences prefs)
	{
		super(name, labelText, width, strategy, parent);
		mSecurePreferences = prefs;
	}

	public ISecurePreferences getSecurePreferences()
	{
		return mSecurePreferences;
	}

	@Override
	protected void doLoad()
	{
		if (getTextControl() != null)
		{
			try
			{
				String value = getSecurePreferences().get(getPreferenceName(), "");
				getTextControl().setText(value);
				oldValue = value;
			}
			catch (StorageException e)
			{
				throw new LDAPBrowserException(e);
			}
		}
	}

	@Override
	protected void doLoadDefault()
	{}

	@Override
	protected void doStore()
	{
		try
		{
			getSecurePreferences().put(getPreferenceName(), getTextControl().getText(), true);
		}
		catch (StorageException e)
		{
			throw new LDAPBrowserException(e);
		}
	}

	@Override
	protected void doFillIntoGrid(Composite parent, int numColumns)
	{
		super.doFillIntoGrid(parent, numColumns);
		getTextControl().setEchoChar('*');
	}
}
