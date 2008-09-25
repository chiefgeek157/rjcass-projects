package com.rjcass.xbml.m2analyzer.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class XBMLProfiledEntity extends XBMLEntity
{
	private Set<XBMLProfile> mProfiles;

	public XBMLProfiledEntity()
	{
		mProfiles = new HashSet<XBMLProfile>();
	}

	public Set<XBMLProfile> getProfiles()
	{
		return Collections.unmodifiableSet(mProfiles);
	}

	public void addProfile(XBMLProfile profile)
	{
		mProfiles.add(profile);
	}
}
