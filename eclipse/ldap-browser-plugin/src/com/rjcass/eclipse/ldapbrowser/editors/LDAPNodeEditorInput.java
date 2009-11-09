package com.rjcass.eclipse.ldapbrowser.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.rjcass.eclipse.ldapbrowser.model.LDAPNode;

public class LDAPNodeEditorInput implements IEditorInput
{
	private LDAPNode	mNode;

	public LDAPNodeEditorInput(LDAPNode node)
	{
		mNode = node;
	}

	@Override
	public boolean exists()
	{
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor()
	{
		return ImageDescriptor.getMissingImageDescriptor();
	}

	@Override
	public String getName()
	{
		return mNode.getName();
	}

	@Override
	public IPersistableElement getPersistable()
	{
		return null;
	}

	@Override
	public String getToolTipText()
	{
		return mNode.getDN();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class adapter)
	{
		return null;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (super.equals(obj))
		{
			return true;
		}
		if (obj instanceof LDAPNodeEditorInput)
		{
			return mNode.equals(((LDAPNodeEditorInput)obj).mNode);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return mNode.hashCode();
	}

	public LDAPNode getNode()
	{
		return mNode;
	}
}