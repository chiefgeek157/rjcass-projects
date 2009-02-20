package com.rjcass.depends.jung;

import java.awt.Dimension;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.SingleFrameApplication;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout2;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.TestGraphs;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class DependsApp extends SingleFrameApplication
{
	private VisualizationViewer<String, Number> mViewer;
	private Layout<String, Number> mLayout;
	private Graph<String, Number> mGraph;

	public static void main(String[] args)
	{
		Application.launch(DependsApp.class, args);
	}

	@Override
	protected void startup()
	{
		org.springframework.context.ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		Object menuBuilder = context.getBean("menuBuilder");

		// mGraph = new DirectedSparseMultigraph<String, Number>();
		mGraph = TestGraphs.createDirectedAcyclicGraph(3, 3, .5);
		mLayout = new SpringLayout2<String, Number>(mGraph);
		mLayout.setSize(new Dimension(300, 300));
		mViewer = new VisualizationViewer<String, Number>(mLayout);
		mViewer.setPreferredSize(new Dimension(500, 500));

		mViewer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>());
		mViewer.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<Number>());
		DefaultModalGraphMouse<String, Number> gm = new DefaultModalGraphMouse<String, Number>();
		gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
		mViewer.setGraphMouse(gm);

		FrameView view = getMainView();
		view.setMenuBar(createMenuBar());
		view.setToolBar(createToolBar());

		show(mViewer);
	}

	private JMenuBar createMenuBar()
	{
		ApplicationContext context = getContext();
		ActionMap actionMap = context.getActionMap(this);

		JMenuItem exitItem = new JMenuItem();
		exitItem.setAction(actionMap.get("quit"));

		JMenuItem cutItem = new JMenuItem();
		cutItem.setAction(actionMap.get("cut"));

		JMenuItem copyItem = new JMenuItem();
		copyItem.setAction(actionMap.get("copy"));

		JMenuItem pasteItem = new JMenuItem();
		pasteItem.setAction(actionMap.get("paste"));

		JMenuItem deleteItem = new JMenuItem();
		deleteItem.setAction(actionMap.get("delete"));

		JMenu fileMenu = new JMenu();
		fileMenu.setName("fileMenu");
		fileMenu.add(exitItem);

		JMenu editMenu = new JMenu();
		editMenu.setName("editMenu");
		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.add(deleteItem);

		JMenuBar menubar = new JMenuBar();
		menubar.add(fileMenu);
		menubar.add(editMenu);

		return menubar;
	}

	private JToolBar createToolBar()
	{
		ApplicationContext context = getContext();
		ActionMap actionMap = context.getActionMap(this);

		JButton cutButton = new JButton();
		cutButton.setAction(actionMap.get("cut"));

		JButton copyButton = new JButton();
		copyButton.setAction(actionMap.get("copy"));

		JButton pasteButton = new JButton();
		pasteButton.setAction(actionMap.get("paste"));

		JButton deleteButton = new JButton();
		deleteButton.setAction(actionMap.get("delete"));

		JToolBar toolbar = new JToolBar();
		toolbar.add(cutButton);
		toolbar.add(copyButton);
		toolbar.add(pasteButton);
		toolbar.add(deleteButton);

		return toolbar;
	}
}