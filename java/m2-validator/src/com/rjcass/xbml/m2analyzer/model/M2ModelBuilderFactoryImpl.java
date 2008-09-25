package com.rjcass.xbml.m2analyzer.model;

import com.rjcass.xbml.m2analyzer.M2ModelBuilder;
import com.rjcass.xbml.m2analyzer.M2ModelBuilderFactory;

public class M2ModelBuilderFactoryImpl implements M2ModelBuilderFactory
{
	private SnippetFactory mSnippetFactory;
	private XBMLEntityFactory mEntityFactory;

	public M2ModelBuilderFactoryImpl()
	{}

	@Override
	public M2ModelBuilder getBuilder()
	{
		M2ModelBuilderImpl builder = new M2ModelBuilderImpl();
		builder.setEntityFactory(mEntityFactory);
		builder.setSnippetFactory(mSnippetFactory);
		return builder;
	}

	public void setSnippetFactory(SnippetFactory snippetFactory)
	{
		mSnippetFactory = snippetFactory;
	}

	public void setEntityFactory(XBMLEntityFactory entityFactory)
	{
		mEntityFactory = entityFactory;
	}
}
