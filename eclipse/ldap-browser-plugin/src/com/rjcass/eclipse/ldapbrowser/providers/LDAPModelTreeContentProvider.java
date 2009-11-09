package com.rjcass.eclipse.ldapbrowser.providers;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import com.rjcass.eclipse.ldapbrowser.model.LDAPModel;
import com.rjcass.eclipse.ldapbrowser.model.LDAPModelListener;
import com.rjcass.eclipse.ldapbrowser.model.LDAPNode;
import com.rjcass.eclipse.ldapbrowser.model.RootLDAPNode;

public class LDAPModelTreeContentProvider implements ITreeContentProvider, LDAPModelListener
{
	private TreeViewer	mViewer;

	public LDAPModelTreeContentProvider()
	{}

	@Override
	public Object[] getChildren(Object parentElement)
	{
		LDAPNode node = (LDAPNode)parentElement;
		List<LDAPNode> children = node.getChildren();
		return children.toArray();
	}

	@Override
	public Object getParent(Object element)
	{
		LDAPNode node = (LDAPNode)element;
		return node.getParent();
	}

	@Override
	public boolean hasChildren(Object element)
	{
		LDAPNode node = (LDAPNode)element;
		return node.hasChildren();
	}

	@Override
	public Object[] getElements(Object inputElement)
	{
		LDAPModel model = (LDAPModel)inputElement;
		List<RootLDAPNode> contexts = model.getRootContexts();
		return contexts.toArray();
	}

	@Override
	public void dispose()
	{}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
	{
		mViewer = (TreeViewer)viewer;
		LDAPModel oldModel = (LDAPModel)oldInput;
		LDAPModel newModel = (LDAPModel)newInput;
		if (oldModel != null)
			oldModel.removeLDAPModelListener(this);
		if (newModel != null)
			newModel.addLDAPModelListener(this);
	}

	@Override
	public void rootContextAdded(RootLDAPNode node)
	{
		mViewer.add(mViewer.getInput(), node);
	}

	@Override
	public void rootContextChanged(RootLDAPNode node)
	{
		mViewer.collapseToLevel(node, TreeViewer.ALL_LEVELS);
		mViewer.update(node, null);
	}

	@Override
	public void rootContextRemoved(RootLDAPNode node)
	{
		mViewer.remove(node);
	}
}
