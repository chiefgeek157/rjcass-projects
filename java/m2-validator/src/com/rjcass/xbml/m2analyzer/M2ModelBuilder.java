package com.rjcass.xbml.m2analyzer;

import java.io.File;

public interface M2ModelBuilder
{
	public abstract void addListener(M2ModelBuilderListener listener);

	public abstract M2Model validate(File file);

	public abstract M2Model validate(String path);

}