package com.rjcass.xbml.m2analyzer.ui;

import java.io.File;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rjcass.xbml.m2analyzer.M2Model;
import com.rjcass.xbml.m2analyzer.M2ModelBuilder;
import com.rjcass.xbml.m2analyzer.M2ModelBuilderListener;

public class BuildModelTask extends SwingWorker<M2Model, Void> implements M2ModelBuilderListener
{
	private static Log sLog = LogFactory.getLog(BuildModelTask.class);

	private UIServices mServices;
	private M2ModelBuilder mBuilder;
	private File mFile;

	public BuildModelTask(UIServices services, M2ModelBuilder builder, File file)
	{
		mServices = services;
		mBuilder = builder;
		mFile = file;
		
		mBuilder.addListener(this);
	}

	@Override
	public void validatorProgress(int percentComplete)
	{
		setProgress(percentComplete);
	}

	@Override
	protected M2Model doInBackground() throws Exception
	{
		sLog.debug("In doInBackground()");
		M2Model model = mBuilder.validate(mFile);
		return model;
	}

	@Override
	protected void done()
	{
		sLog.debug("In done()");
		setProgress(100);
		try
		{
			mServices.loadModel(get());
		}
		catch (ExecutionException e)
		{
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
