package com.rjcass.eclipse.ldapbrowser.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.rjcass.eclipse.ldapbrowser.dialogs.ContextDialog;
import com.rjcass.eclipse.ldapbrowser.model.LDAPModel;
import com.rjcass.eclipse.ldapbrowser.views.LDAPView;

public class AddRootContextHandler extends AbstractHandler
{
	public static String	ID	= "com.rjcass.eclipse.ldapbrowser.commands.AddRootContext";

	public AddRootContextHandler()
	{}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);

		ContextDialog dialog = new ContextDialog(window.getShell());
		int result = dialog.open();

		switch (result)
		{
			case Dialog.OK:
				// Get the view
				IWorkbenchPage page = window.getActivePage();

				LDAPView view = (LDAPView)page.findView(LDAPView.ID);
				if (view == null)
				{
					System.err.println("No view");
				}
				else
				{
					LDAPModel model = view.getModel();
					model.addRootContext(dialog.getName(), dialog.getProtocol(), dialog.getHostname(), dialog.getPort(), dialog
							.getAuthMode(), dialog.getPrincipal(), dialog.getPassword());
				}

			case Dialog.CANCEL:
				// Fall through
			default:
		}
		return null;
	}
}
