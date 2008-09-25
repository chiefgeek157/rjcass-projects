package com.rjcass.xbml.m2analyzer.model;

import java.io.File;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rjcass.xbml.m2analyzer.M2AnalyzerException;
import com.rjcass.xbml.m2analyzer.M2Model;
import com.rjcass.xbml.m2analyzer.M2ModelBuilder;
import com.rjcass.xbml.m2analyzer.M2ModelBuilderListener;

public class M2ModelBuilderImpl implements M2ModelBuilder
{
	private static Log sLog = LogFactory.getLog(M2ModelBuilderImpl.class);

	private SnippetFactory mSnippetFactory;
	private XBMLEntityFactory mEntityFactory;
	private Set<M2ModelBuilderListener> mListeners;

	public M2ModelBuilderImpl()
	{
		mListeners = new HashSet<M2ModelBuilderListener>();
	}

	public void addListener(M2ModelBuilderListener listener)
	{
		mListeners.add(listener);
	}

	public M2Model validate(File file)
	{
		M2Model model;
		if (file.exists())
		{
			RepositoryScanner scanner = null;
			if (file.isDirectory())
			{
				scanner = new FolderScanner(file, mSnippetFactory);
			}
			else if (file.getPath().endsWith(".zip"))
			{
				scanner = new ZipScanner(file, mSnippetFactory);
			}
			else
			{
				throw new M2AnalyzerException("'" + file.getPath() + "' must be a Zip file or folder");
			}
			model = validate(scanner);
		}
		else
		{
			throw new M2AnalyzerException("Path '" + file.getPath() + "' does not exist.");
		}
		return model;
	}

	public M2Model validate(String path)
	{
		File file = new File(path);
		return validate(file);
	}

	public SnippetFactory getSnippetFactory()
	{
		return mSnippetFactory;
	}

	public void setSnippetFactory(SnippetFactory snippetFactory)
	{
		mSnippetFactory = snippetFactory;
	}

	public void setEntityFactory(XBMLEntityFactory entityFactory)
	{
		mEntityFactory = entityFactory;
	}

	private M2Model validate(RepositoryScanner scanner)
	{
		sLog.info("Building Entity map");

		Map<String, XBMLEntity> entitiesById = new HashMap<String, XBMLEntity>();
		XBMLProject root = new XBMLProject();
		root.setPath("xBML");
		root.setName("xBML");
		Repository repos = new Repository(root);

		for (Snippet snippet : scanner)
		{
			fireProgressEvent(scanner.getPercentComplete());
			XBMLEntity entity = mEntityFactory.createEntity(entitiesById, snippet);
			if (entity.isProjectPath())
				repos.addEntity(entity);
		}

		sLog.debug("Building links between tiers");
		repos.buildLinks();

		Queue<XBMLEntity> queue = new LinkedList<XBMLEntity>();
		Set<XBMLEntity> visited = new HashSet<XBMLEntity>();
		Set<XBMLEntity> hasMissingChild = new HashSet<XBMLEntity>();
		Set<XBMLEntity> missingChildren = new HashSet<XBMLEntity>();

		queue.add(root);

		sLog.debug("Visiting entities");
		while (queue.size() > 0)
		{
			// sLog.debug("Queue size: " + queue.size());
			XBMLEntity entity = queue.poll();
			visited.add(entity);

			Set<XBMLEntity> children = entity.getChildren();
			for (XBMLEntity child : children)
			{
				if (!child.isValidated())
				{
					// sLog.debug("Child " + childID + " of " + snippet +
					// " not found");
					hasMissingChild.add(entity);
					missingChildren.add(child);
				}
				else
				{
					if (!visited.contains(child))
						queue.add(child);
				}
			}
		}

		Set<XBMLEntity> unreferenced = new HashSet<XBMLEntity>();
		unreferenced.addAll(entitiesById.values());
		sLog.info("Total         Entities: " + unreferenced.size());
		sLog.info("Visited       Entities: " + visited.size());
		sLog.info("Has Missing Child     : " + hasMissingChild.size());
		sLog.info("Missing Children      : " + missingChildren.size());

		unreferenced.removeAll(visited);
		sLog.info("Unreferenced  Entities: " + unreferenced.size());

		Set<XBMLEntity> invalid = new HashSet<XBMLEntity>();
		invalid.addAll(hasMissingChild);
		invalid.retainAll(visited);
		sLog.info("Invalid       Entities: " + invalid.size());

		Map<Class<? extends XBMLEntity>, Integer> unreferencedTypes = new HashMap<Class<? extends XBMLEntity>, Integer>();
		for (XBMLEntity entity : unreferenced)
		{
			Class<? extends XBMLEntity> cls = entity.getClass();
			Integer countInt = unreferencedTypes.get(cls);
			int count = 0;
			if (countInt != null)
				count = countInt.intValue();
			unreferencedTypes.put(cls, count + 1);
		}

		sLog.info("Unreferenced type counts:");
		for (Map.Entry<Class<? extends XBMLEntity>, Integer> entry : unreferencedTypes.entrySet())
		{
			sLog.info(entry.getKey() + ": " + entry.getValue());
		}

		return new M2ModelImpl(root, entitiesById.values(), visited, hasMissingChild, missingChildren);
	}

	private void fireProgressEvent(int percentComplete)
	{
		Set<M2ModelBuilderListener> listeners = new HashSet<M2ModelBuilderListener>();
		listeners.addAll(mListeners);
		for (M2ModelBuilderListener listener : listeners)
		{
			listener.validatorProgress(percentComplete);
		}
	}

	private class Repository
	{
		private Node mRoot;

		public Repository(XBMLEntity root)
		{
			mRoot = new Node(root);
			mRoot.setName(root.getPath());
		}

		public void addEntity(XBMLEntity entity)
		{
			String[] pathTokens = entity.getPath().split("/");
			Queue<String> tokens = new LinkedList<String>();
			for (int i = 0; i < pathTokens.length; i++)
				tokens.add(pathTokens[i]);
			String token = tokens.poll();
			if (mRoot.getName().equals(token))
				mRoot.addChild(tokens, entity);
			else
				throw new M2AnalyzerException("Path does match root node.  Name=" + mRoot.mName + ", token=" + token);
		}

		public void buildLinks()
		{
			Deque<XBMLEntity> stack = new LinkedList<XBMLEntity>();
			// sLog.debug("Pushing " + mRoot.getEntity());
			stack.push(mRoot.getEntity());
			Node node = mRoot;
			visitNode(stack, node);
		}

		public void dump()
		{
			mRoot.dump("", "+--");
		}

		private void visitNode(Deque<XBMLEntity> stack, Node node)
		{
			for (Node child : node.getChildren())
			{
				XBMLEntity childEntity = child.getEntity();
				if (childEntity != null)
				{
					// sLog.debug("Adding child to " + stack.peek() + " child "
					// + childEntity);
					stack.peek().addChild(childEntity);
					stack.push(childEntity);
				}
				visitNode(stack, child);
				if (childEntity != null)
				{
					stack.pop();
					// XBMLEntity popped = stack.pop();
					// sLog.debug("Popped " + popped);
				}
			}
		}

		private class Node
		{
			private String mName;
			private XBMLEntity mEntity;
			private Node mParent;
			private Set<Node> mChildren;

			public Node()
			{
				mChildren = new HashSet<Node>();
			}

			public Node(XBMLEntity entity)
			{
				this();
				setEntity(entity);
				setName(entity.getPath());
			}

			public Set<Node> getChildren()
			{
				return Collections.unmodifiableSet(mChildren);
			}

			public void addChild(Queue<String> tokens, XBMLEntity entity)
			{
				String token = tokens.poll();
				if (tokens.size() == 0)
				{
					// sLog.debug("Setting entity for node " + this + " entity "
					// + entity);
					setEntity(entity);
				}
				else
				{
					Node matchingChild = null;
					for (Node child : mChildren)
					{
						if (token.equals(child.getName()))
						{
							matchingChild = child;
							break;
						}
					}
					if (matchingChild == null)
					{
						matchingChild = new Node();
						matchingChild.setName(token);
						matchingChild.setParent(this);
						// sLog.debug("Adding child to parent " + this +
						// " child " + matchingChild);
						mChildren.add(matchingChild);
					}
					matchingChild.addChild(tokens, entity);
				}
			}

			public String getName()
			{
				return mName;
			}

			public void setName(String name)
			{
				mName = name;
			}

			public XBMLEntity getEntity()
			{
				return mEntity;
			}

			public void setEntity(XBMLEntity entity)
			{
				mEntity = entity;
			}

			public Node getParent()
			{
				return mParent;
			}

			public void setParent(Node parent)
			{
				mParent = parent;
			}

			public String toString()
			{
				return (new StringBuilder("Node[name=").append(mName).append("]").toString());
			}

			public void dump(String parentPrefix, String prefix)
			{
				sLog.debug(parentPrefix + mName);
				for (Node child : mChildren)
				{
					child.dump(parentPrefix + prefix, prefix);
				}
			}
		}
	}
}
