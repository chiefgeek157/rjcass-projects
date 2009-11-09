package com.rjcass.eclipse.ldapbrowser.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.rjcass.eclipse.ldapbrowser.dialogs.ContextDialog;
import com.rjcass.eclipse.ldapbrowser.model.LDAPModel;
import com.rjcass.eclipse.ldapbrowser.model.RootLDAPNode;
import com.rjcass.eclipse.ldapbrowser.views.LDAPView;

public class RootContextPropertiesHandler extends AbstractHandler implements IHandler
{
	public static String	ID	= "com.rjcass.eclipse.ldapbrowser.commands.RootContextProperties";

	public RootContextPropertiesHandler()
	{}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();

		LDAPView view = (LDAPView)page.findView(LDAPView.ID);
		if (view == null)
		{
			System.err.println("No view");
		}
		else
		{
			ISelection selection = view.getSite().getSelectionProvider().getSelection();
			if (selection != null && selection instanceof IStructuredSelection)
			{
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				if (obj != null)
				{
					RootLDAPNode node = (RootLDAPNode)obj;

					ContextDialog dialog = new ContextDialog(window.getShell());
					dialog.setName(node.getName());
					dialog.setProtocol(node.getProtocol());
					dialog.setHostname(node.getHostname());
					dialog.setPort(node.getPort());
					dialog.setAuthMode(node.getAuthMode());
					dialog.setPrincipal(node.getPrincipal());
					dialog.setPassword(node.getPassword());
					int result = dialog.open();

					switch (result)
					{
						case Dialog.OK:
							// Get the view
							LDAPModel model = view.getModel();
							model.updateRootContext(node, dialog.getName(), dialog.getProtocol(), dialog.getHostname(), dialog
									.getPort(), dialog.getAuthMode(), dialog.getPrincipal(), dialog.getPassword());

						case Dialog.CANCEL:
							// Fall through
						default:
					}
				}
			}
		}
		return null;
	}

}
