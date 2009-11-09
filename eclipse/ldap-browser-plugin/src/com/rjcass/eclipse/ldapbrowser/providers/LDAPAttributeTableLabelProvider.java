package com.rjcass.eclipse.ldapbrowser.providers;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.rjcass.eclipse.ldapbrowser.model.AttributeValue;

public class LDAPAttributeTableLabelProvider extends LabelProvider implements ITableLabelProvider
{
	public LDAPAttributeTableLabelProvider()
	{}

	@Override
	public String getColumnText(Object element, int columnIndex)
	{
		AttributeValue attr = (AttributeValue)element;
		if (columnIndex == 0)
			return attr.getName();
		if (columnIndex == 1)
			return attr.getValue();
		return null;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex)
	{
		return null;
	}
}
