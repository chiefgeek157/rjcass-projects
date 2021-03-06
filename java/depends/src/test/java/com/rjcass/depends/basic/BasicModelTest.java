package com.rjcass.depends.basic;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.rjcass.depends.Layer;
import com.rjcass.depends.basic.BasicLayer;
import com.rjcass.depends.basic.BasicModel;
import com.rjcass.depends.spi.SPILayer;

public class BasicModelTest
{
	@BeforeClass
	public static void setUpClass() throws Exception
	{}

	@AfterClass
	public static void tearDownClass() throws Exception
	{}

	public BasicModelTest()
	{}

	@Before
	public void setUp()
	{}

	@After
	public void tearDown()
	{}

	@Ignore
	@Test
	public void testLayers()
	{
		BasicModel model = new BasicModel();
		SPILayer layerA = new BasicLayer();
		SPILayer layerB = new BasicLayer();
		SPILayer layerC = new BasicLayer();
		model.addLayer(layerA);
		model.addLayer(layerB);
		model.addLayer(layerC);
		Layer l1 = model.getTopLayer();
		assertEquals(l1, layerA);
		Layer l2 = l1.getChildLayer();
		Layer l3 = l2.getChildLayer();
		assertEquals(l2, layerB);
		assertEquals(l3, layerC);
		assertEquals(l2.getParentLayer(), l1);
		assertEquals(l3.getParentLayer(), l2);
		assertEquals(model, layerA.getModel());
		assertEquals(model, layerB.getModel());
		assertEquals(model, layerC.getModel());
	}
}