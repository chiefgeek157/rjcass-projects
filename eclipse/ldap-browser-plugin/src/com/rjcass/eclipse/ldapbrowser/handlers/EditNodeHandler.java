package com.rjcass.eclipse.ldapbrowser.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.rjcass.eclipse.ldapbrowser.editors.LDAPNodeEditor;
import com.rjcass.eclipse.ldapbrowser.editors.LDAPNodeEditorInput;
import com.rjcass.eclipse.ldapbrowser.model.LDAPNode;
import com.rjcass.eclipse.ldapbrowser.views.LDAPView;

public class EditNodeHandler extends AbstractHandler
{
	public static String	ID	= "com.rjcass.eclipse.ldapbrowser.commands.EditNode";

	public EditNodeHandler()
	{}

	@Override
	public Object execute(ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException
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
					LDAPNode node = (LDAPNode)obj;
					LDAPNodeEditorInput input = new LDAPNodeEditorInput(node);
					try
					{
						page.openEditor(input, LDAPNodeEditor.ID);
					}
					catch (PartInitException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}
}
