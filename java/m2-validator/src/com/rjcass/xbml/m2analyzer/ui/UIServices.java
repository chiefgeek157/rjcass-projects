package com.rjcass.xbml.m2analyzer.ui;

import java.io.File;

import javax.swing.JFrame;

import com.rjcass.xbml.m2analyzer.M2Model;

public interface UIServices
{
	void exit();

	JFrame getMainFrame();

	void open(File file);
	
	void loadModel(M2Model model);
}
