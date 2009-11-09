package com.rjcass.eclipse.ldapbrowser.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.rjcass.eclipse.ldapbrowser.model.RootLDAPNode;
import com.rjcass.eclipse.ldapbrowser.views.LDAPView;

public class DeleteRootContextHandler extends AbstractHandler
{
	public static String	ID	= "com.rjcass.eclipse.ldapbrowser.commands.DeleteRootContext";

	public DeleteRootContextHandler()
	{}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		// Get the view
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
					view.getModel().removeRootContext(node);
				}
			}
		}
		return null;
	}
}
