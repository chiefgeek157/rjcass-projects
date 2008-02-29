package com.rjcass.graph;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Model extends ModelEntity
{
    private ModelEntityFactory mFactory;
    private Set<Graph> mGraphs;
    private Set<ModelListener> mListeners;

    public void setFactory(ModelEntityFactory factory)
    {
        mFactory = factory;
    }
    
    public boolean isValid()
    {
        return true;
    }

    public int getGraphCount()
    {
        return mGraphs.size();
    }

    public Set<Graph> getGraphs()
    {
        validate();
        return Collections.unmodifiableSet(mGraphs);
    }

    public Node addNode()
    {
        validate();
        Graph graph = mFactory.createGraph();
        graph.setModel(this);
        Node node = mFactory.createNode();
        graph.addNode(node);
        mGraphs.add(graph);
        fireGraphAdded(graph);
        return node;
    }

    public void addListener(ModelListener listener)
    {
        mListeners.add(listener);
    }

    public void removeListener(ModelListener listener)
    {
        mListeners.remove(listener);
    }

    protected Model()
    {
        mGraphs = new HashSet<Graph>();
        mListeners = new HashSet<ModelListener>();
    }

    Arc createArc(Node node1, Node node2)
    {
        Arc arc = mFactory.createArc();
        arc.setStartNode(node1);
        arc.setEndNode(node2);

        // Combine graphs, if needed
        Graph graph1 = node1.getGraph();
        Graph graph2 = node2.getGraph();
        if(graph1 != graph2)
        {
            Graph source, target;
            if(graph2.getNodeCount() > graph1.getNodeCount())
            {
                target = graph2;
                source = graph1;
            }
            else
            {
                target = graph1;
                source = graph2;
            }
            for(Node node : source.getNodes())
            {
                source.removeNode(node);
                target.addNode(node);
            }
        }
        return arc;
    }

    boolean deleteArc(Node node1, Node node2)
    {
        Set<Node> moveSet = new HashSet<Node>();
        Queue<Node> queue = new LinkedList<Node>();

        queue.add(node2);
        boolean split = true;
        while(queue.size() > 0 && split)
        {
            Node currentNode = queue.poll();
            moveSet.add(currentNode);
            for(Node neighbor : currentNode.getAdjacentNodes())
            {
                if(neighbor == node1)
                {
                    split = false;
                    break;
                }
                if(!moveSet.contains(neighbor) && !queue.contains(neighbor))
                {
                    queue.add(neighbor);
                }
            }
        }

        if(split)
        {
            Graph source = node1.getGraph();
            Graph target = mFactory.createGraph();
            target.setModel(this);
            mGraphs.add(target);
            for(Node node : moveSet)
            {
                source.removeNode(node);
                target.addNode(node);
            }
        }

        return split;
    }

    void removeGraph(Graph graph)
    {
        graph.setModel(null);
        mGraphs.remove(graph);
        fireGraphRemoved(graph);
    }

    private void fireGraphAdded(Graph graph)
    {
        Set<ModelListener> listeners = new HashSet<ModelListener>(mListeners);
        for(ModelListener listener : listeners)
            listener.graphAdded(this, graph);
    }

    private void fireGraphRemoved(Graph graph)
    {
        Set<ModelListener> listeners = new HashSet<ModelListener>(mListeners);
        for(ModelListener listener : listeners)
            listener.graphRemoved(this, graph);
    }
}
