package com.rjcass.xbml.m2analyzer.ui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ProgressMonitor;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rjcass.xbml.m2analyzer.M2Model;
import com.rjcass.xbml.m2analyzer.M2ModelBuilder;
import com.rjcass.xbml.m2analyzer.M2ModelBuilderFactory;
import com.rjcass.xbml.m2analyzer.model.XBMLEntity;

public class AnalyzerUI implements UIServices
{
	private static Log sLog = LogFactory.getLog(AnalyzerUI.class);

	private M2ModelBuilderFactory mBuilderFactory;
	private JFrame mMainFrame;
	private DefaultTreeModel mTreeModel;

	public AnalyzerUI()
	{}

	public void setBuilderFactory(M2ModelBuilderFactory factory)
	{
		mBuilderFactory = factory;
	}

	public void createAndShowGUI()
	{
		mMainFrame = new JFrame("xBML M2 M2ModelBuilderImpl");
		mMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);

		ExitAction exitAction = new ExitAction(this);
		OpenAction openAction = new OpenAction(this);

		fileMenu.add(new JMenuItem(openAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));

		mTreeModel = new DefaultTreeModel(null);
		JTree tree = new JTree(mTreeModel);
		JScrollPane scrollPane = new JScrollPane(tree);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, null);
		// Set the menu bar and add the label to the content pane.
		mMainFrame.setJMenuBar(menuBar);
		mMainFrame.getContentPane().add(splitPane, BorderLayout.CENTER);

		// Display the window.
		mMainFrame.pack();
		mMainFrame.setVisible(true);
	}

	public void exit()
	{
		System.exit(0);
	}

	public JFrame getMainFrame()
	{
		return mMainFrame;
	}

	public void open(File file)
	{
		sLog.debug("Opening " + file);

		final ProgressMonitor progressMonitor = new ProgressMonitor(mMainFrame, "Examining repository...", null, 0, 100);

		M2ModelBuilder builder = mBuilderFactory.getBuilder();
		BuildModelTask task = new BuildModelTask(this, builder, file);
		task.addPropertyChangeListener(new PropertyChangeListener()
		{

			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if ("progress" == evt.getPropertyName())
				{
					int progress = (Integer)evt.getNewValue();
					progressMonitor.setProgress(progress);
				}

			}
		});
		task.execute();
	}

	public void loadModel(M2Model model)
	{
		sLog.debug("In loadModel()");
		XBMLEntity root = model.getRoot();
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(root);
		addChildNodes(root, rootNode);
		mTreeModel.setRoot(rootNode);
	}

	private void addChildNodes(XBMLEntity parent, DefaultMutableTreeNode parentNode)
	{
		Set<XBMLEntity> children = parent.getChildren();
		for (XBMLEntity child : children)
		{
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
			parentNode.add(childNode);
			addChildNodes(child, childNode);
		}
	}
}
