package com.rjcass.eclipse.ldapbrowser.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.rjcass.eclipse.ldapbrowser.views.LDAPView;

public class CollapseAllHandler extends AbstractHandler
{
	public static String	ID	= "com.rjcass.eclipse.ldapbrowser.commands.CollapseAll";

	public CollapseAllHandler()
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
			view.collapseAll();
		}
		return null;
	}
}
