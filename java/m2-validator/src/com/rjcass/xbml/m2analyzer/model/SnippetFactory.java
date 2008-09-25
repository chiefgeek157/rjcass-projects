package com.rjcass.xbml.m2analyzer.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.FactoryConfigurationError;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.rjcass.xbml.m2analyzer.M2AnalyzerException;

public class SnippetFactory
{
	// private static Log sLog = LogFactory.getLog(SnippetFactory.class);

	public SnippetFactory()
	{}

	public Snippet createSnippet(File file)
	{
		Snippet snippet = null;
		InputStream is = null;
		try
		{
			is = new FileInputStream(file);
			String path = file.getPath().replace("\\", "/");
			int start = path.indexOf("xBML/");
			path = path.substring(start, path.length());
			snippet = createSnippet(path, is);
		}
		catch (FileNotFoundException e)
		{
			throw new M2AnalyzerException(e);
		}
		finally
		{
			if (is != null)
				try
				{
					is.close();
				}
				catch (IOException e)
				{
					throw new M2AnalyzerException(e);
				}
		}
		return snippet;
	}

	public Snippet createSnippet(String path, InputStream is)
	{
		Snippet snippet = new Snippet(path);

		try
		{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(is);
			Element elem = doc.getDocumentElement();
			snippet.setElement(elem);
		}
		catch (ParserConfigurationException e)
		{
			throw new M2AnalyzerException(e);
		}
		catch (SAXException e)
		{
			throw new M2AnalyzerException(e);
		}
		catch (IOException e)
		{
			throw new M2AnalyzerException(e);
		}
		catch (FactoryConfigurationError e)
		{
			throw new M2AnalyzerException(e);
		}

		return snippet;
	}
}
