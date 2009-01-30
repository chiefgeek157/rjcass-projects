package com.rjcass.depends.jung;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.jdesktop.application.Application;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout2;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.TestGraphs;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class Main extends Application
{
	private JFrame mMainFrame;
	private VisualizationViewer<String, Number> mViewer;
	private Layout<String, Number> mLayout;
	private Graph<String, Number> mGraph;

	public static void main(String[] args)
	{
		Application.launch(Main.class, args);
	}

	@Override
	protected void startup()
	{
		mMainFrame = new JFrame("BasicFrameworkApp");
		mMainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mMainFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				exit();
			}
		});

		// mGraph = new DirectedSparseMultigraph<String, Number>();
		mGraph = TestGraphs.createDirectedAcyclicGraph(3, 3, .5);
		mLayout = new SpringLayout2<String, Number>(mGraph);
		mLayout.setSize(new Dimension(300, 300));
		mViewer = new VisualizationViewer<String, Number>(mLayout);
		mViewer.setPreferredSize(new Dimension(500, 500));

		// Show vertex and edge labels
		mViewer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>());
		mViewer.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<Number>());
		// Create a graph mouse and add it to the visualization component
		DefaultModalGraphMouse<String, Number> gm = new DefaultModalGraphMouse<String, Number>();
		gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
		mViewer.setGraphMouse(gm);

		mMainFrame.getContentPane().add(mViewer);
		mMainFrame.pack();
		mMainFrame.setVisible(true);
	}

	@Override
	protected void shutdown()
	{
		mMainFrame.setVisible(false);
	}
}