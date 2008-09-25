package com.rjcass.xbml.m2analyzer.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.rjcass.xbml.m2analyzer.M2AnalyzerException;

public class XBMLProject extends XBMLEntity
{
	private static final String TOP_LEVEL_PROJECT_PATTERN = "^xBML/Projects/[^/]+/[^/].xml$";

	private Set<XBMLProject> mProjects;
	private Set<XBMLView> mViews;

	public XBMLProject()
	{
		mProjects = new HashSet<XBMLProject>();
		mViews = new HashSet<XBMLView>();
		setProjectPath(true);
	}

	public boolean isTopLevelProject()
	{
		return getPath().matches(TOP_LEVEL_PROJECT_PATTERN);
	}

	public Set<XBMLProject> getProjects()
	{
		return Collections.unmodifiableSet(mProjects);
	}

	public Set<XBMLView> getViews()
	{
		return Collections.unmodifiableSet(mViews);
	}

	public void addProject(XBMLProject project)
	{
		mProjects.add(project);
	}

	public void addView(XBMLView view)
	{
		mViews.add(view);
	}

	@Override
	public Set<XBMLEntity> getChildren()
	{
		Set<XBMLEntity> children = new HashSet<XBMLEntity>();
		children.addAll(mProjects);
		children.addAll(mViews);
		return children;
	}

	@Override
	public void addChild(XBMLEntity entity)
	{
		if (entity instanceof XBMLProject)
		{
			mProjects.add((XBMLProject)entity);
		}
		else if (entity instanceof XBMLView)
		{
			mViews.add((XBMLView)entity);
		}
		else
		{
			throw new M2AnalyzerException("Wrong type of child: " + entity);
		}
	}
}
