package com.rjcass.eclipse.ldapbrowser.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

import com.rjcass.eclipse.ldapbrowser.views.LDAPView;

public class LDAPBrowserPerspectiveFactory implements IPerspectiveFactory
{
	@Override
	public void createInitialLayout(IPageLayout layout)
	{
		String editorArea = layout.getEditorArea();

		IFolderLayout left = layout.createFolder("topLeft", IPageLayout.LEFT, 0.25f, editorArea);
		left.addView(LDAPView.ID);
		
		IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.75f, editorArea);
		bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
		bottom.addPlaceholder(IConsoleConstants.ID_CONSOLE_VIEW);
	}
}
