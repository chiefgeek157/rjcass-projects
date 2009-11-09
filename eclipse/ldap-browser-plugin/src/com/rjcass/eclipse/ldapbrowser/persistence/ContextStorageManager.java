package com.rjcass.eclipse.ldapbrowser.persistence;

import java.util.List;

public interface ContextStorageManager
{
	String	EXTENSION_POINT_ID				= "com.rjcass.eclipse.ldapbrowser.extensionpoints.ContextStorageManager";
	String	EXTENSION_POINT_CLASS_NAME_ATTR	= "class";

	/**
	 * Get the list of root contexts currently stored.
	 * 
	 * @return The list of root contexts. Will return an empty list if there are
	 *         none.
	 */
	List<ContextInfo> getContexts();

	/**
	 * Store the given root context. Will create a new entry.
	 * 
	 * @return the created ContextInfo
	 * @throws NameInUseException
	 *             if name is already in use.
	 */
	ContextInfo storeContext(String name, String protocol, String hostname, int port, String authMode, String principal,
			String password);

	/**
	 * Store the given root context using the new info.
	 * 
	 * @return a new ContextInfo reflecting the new values
	 * @throws NoSuchContextException
	 *             if there is no context with oldName
	 * @throws NameInUseException
	 *             if the new name is already in use
	 */
	ContextInfo updateContext(String oldName, String name, String protocol, String hostname, int port, String authMode,
			String principal, String password);

	/**
	 * Delete the given root context.
	 * 
	 * @param info
	 *            the root context info to delete.
	 * @throws NoSuchContextException
	 *             if a root context with the given name cannot be found.
	 */
	void deleteContext(ContextInfo info);
}
