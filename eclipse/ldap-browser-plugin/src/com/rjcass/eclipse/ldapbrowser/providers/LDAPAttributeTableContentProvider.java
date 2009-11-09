package com.rjcass.eclipse.ldapbrowser.providers;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.rjcass.eclipse.ldapbrowser.editors.LDAPNodeEditorInput;
import com.rjcass.eclipse.ldapbrowser.model.AttributeValue;
import com.rjcass.eclipse.ldapbrowser.model.LDAPNode;

public class LDAPAttributeTableContentProvider implements IStructuredContentProvider
{
	@Override
	public void dispose()
	{}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
	{}

	@Override
	public Object[] getElements(Object inputElement)
	{
		LDAPNodeEditorInput input = (LDAPNodeEditorInput)inputElement;
		LDAPNode node = input.getNode();
		List<AttributeValue> attrs = node.getModel().getNodeAttributes(node);
		return attrs.toArray();
	}
}
