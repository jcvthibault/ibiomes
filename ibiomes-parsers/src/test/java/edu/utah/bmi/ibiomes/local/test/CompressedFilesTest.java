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

package edu.utah.bmi.ibiomes.local.test;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.LocalFileFactory;

/**
 * Test suite for compressed and archived files
 * @author Julien Thibault, University of Utah
 *
 */
public class CompressedFilesTest{

	private final Logger logger = Logger.getLogger(CompressedFilesTest.class);
	
	String[] files = {
			"compressed/1BIVm1.pdb.bz2",
			"compressed/lifetime.5.dat.bz2",
			"compressed/mini5.out.bz2",
			"compressed/1BIVm1.pdb.gz",
			"compressed/lifetime.5.dat.gz",
			"compressed/mini5.out.gz"
		};
	String[] filesTar = {
			"compressed/1BIVm1.tar.gz",
			"compressed/mini5.tgz"
		};
	
	public CompressedFilesTest() throws Exception{
		IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
	}
	
	@Test
	public void testCompressed() throws Exception
	{
		IBIOMESFileReader inputReader = null;
		for (int f=0; f<files.length; f++)
		{
			String inputFile = TestCommon.TEST_DATA_DIR + "/" + files[f];
			logger.debug("Reading compressed file " + inputFile + "...");

			try{
				inputReader = new IBIOMESFileReader(new File(inputFile));
			    String line = null;
		        while (( line = inputReader.readLine()) != null){
		        }
		        inputReader.close();
		
			} 
			catch (Exception e){
				//e.printStackTrace();
				if (inputReader!=null)
					try {
						inputReader.close();
					} catch (IOException e1) {
					}
			}
		}
	}
	
	@Test
	public void testParseCompressed() throws Exception
	{
		IBIOMESFileReader inputReader = null;
		
		for (int f=0; f<files.length; f++)
		{
			String inputFile = TestCommon.TEST_DATA_DIR + "/" + files[f];
			logger.debug("Parsing compressed file " + inputFile + "...");
			LocalFileFactory factory = LocalFileFactory.instance();

			try{
				LocalFile parsedFile = factory.getFile(inputFile, Software.AMBER);
				logger.debug("\tFormat detected --> " + parsedFile.getFormat());
			} 
			catch (Exception e){
				e.printStackTrace();
				if (inputReader!=null)
					try {
						inputReader.close();
					} catch (IOException e1) {
					}
				throw e;
			}
		}
	}
	
	@Test
	public void testParseArchived() throws Exception
	{
		IBIOMESFileReader inputReader = null;
		
		for (int f=0; f<filesTar.length; f++)
		{
			String inputFile = TestCommon.TEST_DATA_DIR + "/" + filesTar[f];
			logger.debug("Parsing compressed file " + inputFile + "...");
			LocalFileFactory factory = LocalFileFactory.instance();

			try{
				LocalFile parsedFile = factory.getFile(inputFile, null);
				logger.debug("\tFormat detected --> " + parsedFile.getFormat());
			} 
			catch (Exception e){
				e.printStackTrace();
				if (inputReader!=null)
					try {
						inputReader.close();
					} catch (IOException e1) {
					}
				throw e;
			}
		}
	}
}
