package com.rjcass.eclipse.ldapbrowser.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.rjcass.eclipse.ldapbrowser.persistence.ContextInfo;
import com.rjcass.eclipse.ldapbrowser.persistence.ContextInfoValidator;

public class ContextDialog extends TitleAreaDialog
{
	private List<String>	mProtocols;
	private List<String>	mAuthModes;
	private String			mName;
	private Text			mNameWidget;
	private String			mProtocol;
	private Combo			mProtocolWidget;
	private String			mHostname;
	private Text			mHostnameWidget;
	private int				mPort;
	private Text			mPortWidget;
	private String			mAuthMode;
	private Combo			mAuthModeWidget;
	private String			mPrincipal;
	private Text			mPrincipalWidget;
	private String			mPassword;
	private Text			mPasswordWidget;

	public ContextDialog(Shell parentShell)
	{
		super(parentShell);

		mProtocols = new ArrayList<String>();
		mAuthModes = new ArrayList<String>();

		loadDefaults();
	}

	public String getName()
	{
		return mName;
	}

	public void setName(String name)
	{
		mName = name;
	}

	public String getProtocol()
	{
		return mProtocol;
	}

	public void setProtocol(String protocol)
	{
		mProtocol = protocol;
	}

	public String getHostname()
	{
		return mHostname;
	}

	public void setHostname(String hostname)
	{
		mHostname = hostname;
	}

	public int getPort()
	{
		return mPort;
	}

	public void setPort(int port)
	{
		mPort = port;
	}

	public String getAuthMode()
	{
		return mAuthMode;
	}

	public void setAuthMode(String authMode)
	{
		mAuthMode = authMode;
	}

	public String getPrincipal()
	{
		return mPrincipal;
	}

	public void setPrincipal(String principal)
	{
		mPrincipal = principal;
	}

	public String getPassword()
	{
		return mPassword;
	}

	public void setPassword(String password)
	{
		mPassword = password;
	}

	@Override
	protected void configureShell(Shell newShell)
	{
		super.configureShell(newShell);
		newShell.setText("Edit LDAP Context");
	}

	@Override
	protected Control createContents(Composite parent)
	{
		Control contents = super.createContents(parent);

		setTitle("Edit LDAP Context");
		setMessage("Fill in the fields below to create a new LDAP Context", IMessageProvider.INFORMATION);

		return contents;
	}

	@Override
	protected Control createDialogArea(Composite parent)
	{
		Composite composite = new Composite(parent, SWT.BORDER);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		GridLayout layout = new GridLayout(2, false);
		composite.setLayout(layout);

		Label label1 = new Label(composite, SWT.NONE);
		label1.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		label1.setText("Name");
		mNameWidget = new Text(composite, SWT.BORDER);
		mNameWidget.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		mNameWidget.setText(mName);

		Label label2 = new Label(composite, SWT.NONE);
		label2.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		label2.setText("Protocol");
		mProtocolWidget = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		mProtocolWidget.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		mProtocolWidget.setItems(mProtocols.toArray(new String[0]));
		mProtocolWidget.select(mProtocols.indexOf(mProtocol));

		Label label3 = new Label(composite, SWT.NONE);
		label3.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		label3.setText("Hostname");
		mHostnameWidget = new Text(composite, SWT.BORDER);
		mHostnameWidget.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		mHostnameWidget.setText(mHostname);

		Label label4 = new Label(composite, SWT.NONE);
		label4.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		label4.setText("Port");
		mPortWidget = new Text(composite, SWT.BORDER);
		mPortWidget.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		mPortWidget.setText(String.valueOf(mPort));
		mPortWidget.addVerifyListener(new VerifyListener()
		{
			@Override
			public void verifyText(VerifyEvent e)
			{
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++)
				{
					if (!('0' <= chars[i] && chars[i] <= '9'))
					{
						e.doit = false;
						return;
					}
				}
			}
		});

		Label label5 = new Label(composite, SWT.RIGHT);
		label5.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		label5.setText("Authorization Mode");
		mAuthModeWidget = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		mAuthModeWidget.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		mAuthModeWidget.setItems(mAuthModes.toArray(new String[0]));
		mAuthModeWidget.select(mAuthModes.indexOf(mAuthMode));

		Label label6 = new Label(composite, SWT.RIGHT);
		label6.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		label6.setText("Principal");
		mPrincipalWidget = new Text(composite, SWT.BORDER);
		mPrincipalWidget.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		mPrincipalWidget.setText(mPrincipal);

		Label label7 = new Label(composite, SWT.RIGHT);
		label7.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		label7.setText("Password");
		mPasswordWidget = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		mPasswordWidget.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		mPasswordWidget.setText(mPassword);

		return composite;
	}

	@Override
	protected void okPressed()
	{
		copyValues();
		ContextInfoValidator.Validation result = ContextInfoValidator.validate(mName, mProtocol, mHostname, mPort, mAuthMode,
				mPrincipal, mPassword);
		if (result.valid)
		{
			setErrorMessage(null);
			super.okPressed();
		}
		else
		{
			setErrorMessage(result.messages);
		}
	}

	private void loadDefaults()
	{
		mProtocols.add(ContextInfo.PROTOCOL_LDAP);

		mAuthModes.add(ContextInfo.AUTH_MODE_SIMPLE);

		mName = "";
		mProtocol = ContextInfo.DEFAULT_PROTOCOL;
		mHostname = "";
		mPort = ContextInfo.DEFAULT_PORT;
		mAuthMode = ContextInfo.DEFAULT_AUTH_MODE;
		mPrincipal = "";
		mPassword = "";
	}

	private void copyValues()
	{
		mName = mNameWidget.getText();
		mProtocol = mProtocolWidget.getText();
		mHostname = mHostnameWidget.getText();
		mPort = Integer.valueOf(mPortWidget.getText());
		mAuthMode = mAuthModeWidget.getText();
		mPrincipal = mPrincipalWidget.getText();
		mPassword = mPasswordWidget.getText();
	}
}
