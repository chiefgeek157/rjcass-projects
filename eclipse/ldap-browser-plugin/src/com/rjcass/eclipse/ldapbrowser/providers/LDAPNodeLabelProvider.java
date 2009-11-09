package com.rjcass.eclipse.ldapbrowser.providers;

import org.eclipse.jface.viewers.LabelProvider;

import com.rjcass.eclipse.ldapbrowser.model.LDAPNode;

public class LDAPNodeLabelProvider extends LabelProvider
{
	public LDAPNodeLabelProvider()
	{}

	@Override
	public String getText(Object element)
	{
		LDAPNode node = (LDAPNode)element;
		return node.getName();
	}
}
