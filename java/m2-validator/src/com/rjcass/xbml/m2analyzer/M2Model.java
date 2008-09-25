package com.rjcass.xbml.m2analyzer;

import java.util.Set;

import com.rjcass.xbml.m2analyzer.model.XBMLEntity;

public interface M2Model
{
	XBMLEntity getRoot();

	Set<XBMLEntity> getAllEntities();

	Set<XBMLEntity> getVisited();

	Set<XBMLEntity> getHasMissingChildren();

	Set<XBMLEntity> getMissingChildren();
}
