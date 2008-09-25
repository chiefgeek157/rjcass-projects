package com.rjcass.xbml.m2analyzer.model;

import java.util.Collections;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rjcass.xbml.m2analyzer.M2AnalyzerException;

public abstract class XBMLEntity
{
	private static Pattern sPathCropPattern = Pattern.compile("/[^/]+\\.xml$");

	private String mId;
	private String mName;
	private String mPath;
	private boolean mIsProjectPath;

	public XBMLEntity()
	{}

	public String getName()
	{
		return mName;
	}

	public void setName(String name)
	{
		mName = name;
	}

	public String getId()
	{
		return mId;
	}

	public void setId(String id)
	{
		mId = id;
	}

	public String getPath()
	{
		return mPath;
	}

	public void setPath(String path)
	{
		mPath = path;
	}

	public String getCroppedPath()
	{
		Matcher matcher = sPathCropPattern.matcher(mPath);
		if (!matcher.find())
			throw new M2AnalyzerException("Did not match pattern " + sPathCropPattern.pattern() + " path " + mPath);
		return mPath.substring(0, matcher.start());
	}

	public boolean isValidated()
	{
		return (mPath != null);
	}

	public Set<XBMLEntity> getChildren()
	{
		return Collections.emptySet();
	}

	public boolean isProjectPath()
	{
		return mIsProjectPath;
	}

	public void addChild(XBMLEntity entity)
	{}

	public String toString()
	{
		return (new StringBuilder(getClass().getSimpleName()+"[path=").append(mPath).append("]").toString());
	}

	protected void setProjectPath(boolean isProjectPath)
	{
		mIsProjectPath = isProjectPath;
	}
}
