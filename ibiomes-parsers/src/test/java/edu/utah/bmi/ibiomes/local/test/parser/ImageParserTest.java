/* iBIOMES - Integrated Biomolecular Simulations
 * Copyright (C) 2014  Julien Thibault, University of Utah
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.utah.bmi.ibiomes.local.test.parser;

import java.io.File;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.utah.bmi.Utils;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.local.test.TestCommon;

/**
 * Test image parsers
 * @author Julien Thibault, University of Utah
 *
 */
public class ImageParserTest{

	private final Logger logger = Logger.getLogger(ImageParserTest.class);
	
	String[] filesJPEG = {"images/image.jpeg"};
	String[] filesPNG = { "images/image.png"};
	String[] filesGIF = {"images/image.gif"};
	String[] filesTIF = {"images/image.tif"};
	String[] filesBMP = {"images/image.bmp"};

	public ImageParserTest() throws Exception{
		IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
	}
	
	@Test
	public void testJpeg() throws Exception
	{
		for (int f=0; f<filesJPEG.length; f++){
			File file = new File(TestCommon.TEST_DATA_DIR + "/" + filesJPEG[f]);
			byte[] bytes = Utils.readFirstBytes(4, file);
			String hex = Utils.getHex(bytes);
			Assert.assertEquals(hex, "FFD8FFE0");
		}
	}
	
	@Test
	public void testPng() throws Exception
	{
		for (int f=0; f<filesPNG.length; f++){
			File file = new File(TestCommon.TEST_DATA_DIR + "/" + filesPNG[f]);
			byte[] bytes = Utils.readFirstBytes(4, file);
			String hex = Utils.getHex(bytes);
			Assert.assertEquals(hex, "89504E47");
		}
	}
	
	@Test
	public void testGif() throws Exception
	{
		for (int f=0; f<filesGIF.length; f++){
			File file = new File(TestCommon.TEST_DATA_DIR + "/" + filesGIF[f]);
			byte[] bytes = Utils.readFirstBytes(4, file);
			String hex = Utils.getHex(bytes);
			Assert.assertEquals(hex, "47494638");
		}
	}

	@Test
	public void testTif() throws Exception
	{
		for (int f=0; f<filesTIF.length; f++){
			File file = new File(TestCommon.TEST_DATA_DIR + "/" + filesTIF[f]);
			byte[] bytes = Utils.readFirstBytes(4, file);
			String hex = Utils.getHex(bytes);
			Assert.assertEquals(hex, "49492A00");
		}
	}
}
