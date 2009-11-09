package com.rjcass.eclipse.ldapbrowser.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.rjcass.eclipse.ldapbrowser.providers.LDAPAttributeTableContentProvider;
import com.rjcass.eclipse.ldapbrowser.providers.LDAPAttributeTableLabelProvider;

public class LDAPNodeEditor extends EditorPart
{
	public static final String	ID	= "com.rjcass.eclipse.ldapbrowser.editors.LDAPNodeEditor";

	public LDAPNodeEditor()
	{}

	@Override
	public void doSave(IProgressMonitor monitor)
	{}

	@Override
	public void doSaveAs()
	{}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException
	{
		setSite(site);
		setInput(input);
	}

	@Override
	public boolean isDirty()
	{
		return false;
	}

	@Override
	public boolean isSaveAsAllowed()
	{
		return false;
	}

	@Override
	public void createPartControl(Composite parent)
	{
		TableViewer viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);

		TableViewerColumn column = new TableViewerColumn(viewer, SWT.None);
		column.getColumn().setResizable(true);
		column.getColumn().setText("Name");
		column.getColumn().setWidth(100);

		column = new TableViewerColumn(viewer, SWT.None);
		column.getColumn().setResizable(true);
		column.getColumn().setText("Value");
		column.getColumn().setWidth(100);

		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new LDAPAttributeTableContentProvider());
		viewer.setLabelProvider(new LDAPAttributeTableLabelProvider());

		LDAPNodeEditorInput input = (LDAPNodeEditorInput)getEditorInput();
		viewer.setInput(input);
	}

	@Override
	public void setFocus()
	{}
}
