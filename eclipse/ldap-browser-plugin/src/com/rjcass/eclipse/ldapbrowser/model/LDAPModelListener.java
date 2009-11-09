package com.rjcass.eclipse.ldapbrowser.model;

public interface LDAPModelListener
{
	void rootContextAdded(RootLDAPNode node);

	void rootContextRemoved(RootLDAPNode node);

	void rootContextChanged(RootLDAPNode node);
}
