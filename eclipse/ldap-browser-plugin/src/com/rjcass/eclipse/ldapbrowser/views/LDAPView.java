package com.rjcass.eclipse.ldapbrowser.views;

import org.eclipse.core.commands.common.CommandException;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

import com.rjcass.eclipse.ldapbrowser.LDAPBrowserException;
import com.rjcass.eclipse.ldapbrowser.handlers.EditNodeHandler;
import com.rjcass.eclipse.ldapbrowser.model.LDAPModel;
import com.rjcass.eclipse.ldapbrowser.providers.LDAPModelTreeContentProvider;
import com.rjcass.eclipse.ldapbrowser.providers.LDAPNodeLabelProvider;

public class LDAPView extends ViewPart
{
	public static final String	ID	= "com.rjcass.eclipse.ldapbrowser.views.LDAPView";

	private TreeViewer			mTreeViewer;
	private LDAPModel			mModel;

	public LDAPView()
	{}

	@Override
	public void createPartControl(Composite parent)
	{
		mModel = new LDAPModel();

		mTreeViewer = new TreeViewer(parent);
		mTreeViewer.setContentProvider(new LDAPModelTreeContentProvider());
		mTreeViewer.setLabelProvider(new LDAPNodeLabelProvider());
		mTreeViewer.setInput(mModel);

		mTreeViewer.addDoubleClickListener(new IDoubleClickListener()
		{
			@Override
			public void doubleClick(DoubleClickEvent event)
			{
				IHandlerService handlerService = (IHandlerService)getSite().getService(IHandlerService.class);
				try
				{
					handlerService.executeCommand(EditNodeHandler.ID, null);
				}
				catch (CommandException e)
				{
					throw new LDAPBrowserException(e);
				}
			}
		});

		getSite().setSelectionProvider(mTreeViewer);
	}

	@Override
	public void setFocus()
	{
		mTreeViewer.getControl().setFocus();
	}

	public LDAPModel getModel()
	{
		return mModel;
	}

	public void collapseAll()
	{
		mTreeViewer.collapseAll();
	}
}
