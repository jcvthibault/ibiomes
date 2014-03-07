/*
 * iBIOMES - Integrated Biomolecular Simulations
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

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.LocalFileFactory;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFactory;
import edu.utah.bmi.ibiomes.parse.chem.gaussian.GaussianInputFile;
import edu.utah.bmi.ibiomes.parse.chem.gaussian.GaussianOutputFile;
import edu.utah.bmi.ibiomes.parse.chem.gaussian.GaussianUtils;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.local.test.TestCommon;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

/**
 * Test suite for Gaussian file parsers
 * @author Julien Thibault, University of Utah
 *
 */
public class GaussianParserTest {

	private final Logger logger = Logger.getLogger(GaussianParserTest.class);
	
	String[] inputFileNames = {
				"multi_mol/multi.gjf",
				"acac/acac_sp.gjf", 
				"fosfina/fosfina.gjf", 
				"tamoxifen/FOHT_A_DFT.com", 
				"N6-SH2/N6-SH2.com",
				"N6-SH2a/N6-SH2a.com", 
				"modredundant/cat2a-foht.com", 
				"modredundant/oxyferryl.com",
				"flutc_dft/Flutc_DFT.com"
			};
	
	String[] outputFileNames = {
				"acac/acac.log", 
				"fosfina/fosfina.log", 
				"tamoxifen/FOHT_A_DFT.log", 
				"N6-SH2/N6-SH2.log", 
				"N6-SH2a/N6-SH2a.log"
			};
	
	String[] collectionNames = {
			"acac", 
			"fosfina", 
			"fosfina_rec", 
			"gli", 
			"N6-SH2", 
			"N6-SH2a", 
			"PDTO", 
			"prd-cat1-7a", 
			"modredundant",
			"clx2-multi", 
			"tamoxifen"};

	public GaussianParserTest() throws Exception{
		IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
	}
	
	@Test
	public void testGaussianInputs(){
		try {
			LocalFileFactory ffactory = LocalFileFactory.instance();
			// GAUSSIAN input files
			for (int f=0; f<inputFileNames.length; f++)
			{
				String inputFile = TestCommon.TEST_DATA_DIR + "/gaussian/" + inputFileNames[f];
				System.out.println("Parsing GAUSSIAN input file " + inputFile);
				GaussianInputFile inp  = new GaussianInputFile(inputFile);
				MetadataAVUList md1 = inp.getMetadata();
				logger.debug(md1.toString());
				
				LocalFile inpGen  = ffactory.getFile(inputFile, Software.GAUSSIAN);
				MetadataAVUList md2 = inpGen.getMetadata();
				logger.debug(md2.toString());
				
				Assert.assertTrue(TestCommon.compareMetadata(md1, md2));
				//TestCommon.compareMetadataToExpected(md1, TestCommon.TEST_DATA_DIR + "/expected_metadata/gaussian/" + inputFileNames[f] + ".metadata");
				Assert.assertTrue(TestCommon.compareMetadataToExpected(
						md1, 
						TestCommon.TEST_DATA_DIR + "/expected_metadata/gaussian/" + inputFileNames[f] + ".metadata")
					);
				
				logger.debug("----------------------------------------");
			}
		}
		catch (IllegalAccessException e){
			e.printStackTrace();
			assert(false);
		} catch (SecurityException e) {
			e.printStackTrace();
			assert(false);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			assert(false);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			assert(false);
		} catch (IOException e) {
			e.printStackTrace();
			assert(false);
		} catch (Exception e) {
			e.printStackTrace();
			assert(false);
		}
	}

	@Test
	public void testGaussianOutputs()
	{
		try{
			LocalFileFactory ffactory = LocalFileFactory.instance();
					
			// GAUSSIAN output files
			for (int f=0; f<outputFileNames.length; f++)
			{
				String outputFile = TestCommon.TEST_DATA_DIR + "/gaussian/" + outputFileNames[f];
				System.out.println("Parsing GAUSSIAN output file " + outputFile);
				GaussianOutputFile out  = new GaussianOutputFile(outputFile);
				MetadataAVUList md1 = out.getMetadata();
				logger.debug(md1.toString());
				
				//extract SCF energy values and store to CSV
				GaussianUtils.extractEnergyFromLogFile(out, TestCommon.TEST_DATA_DIR + "/output/" + out.getName()+ ".csv");
				
				LocalFile inpGen  = ffactory.getFile(outputFile, Software.GAUSSIAN);
				MetadataAVUList md2 = inpGen.getMetadata();
				logger.debug(md2.toString());
				
				Assert.assertTrue(TestCommon.compareMetadata(md1, md2));
				Assert.assertTrue(TestCommon.compareMetadataToExpected(
						md1, 
						TestCommon.TEST_DATA_DIR + "/expected_metadata/gaussian/" + outputFileNames[f] + ".metadata")
					);
				logger.debug("----------------------------------------");
			}
		}
		catch (IllegalAccessException e){
			e.printStackTrace();
			assert(false);
		} catch (SecurityException e) {
			e.printStackTrace();
			assert(false);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			assert(false);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			assert(false);
		} catch (IOException e) {
			e.printStackTrace();
			assert(false);
		} catch (Exception e) {
			e.printStackTrace();
			assert(false);
		}
	}

	@Test
	public void testGaussianCollections(){
		
		try{
			// GAUSSIAN collections
			for (int c=0; c<collectionNames.length; c++)
			{
				String inputDir = TestCommon.TEST_DATA_DIR + "/gaussian/" + collectionNames[c];
				System.out.println("Parsing GAUSSIAN experiment " + inputDir);
				ExperimentFactory factory = new ExperimentFactory(inputDir);
				LocalDirectory coll = factory.parseDirectoryForMetadata(Software.GAUSSIAN, null, null);
				MetadataAVUList md1 = coll.getMetadata();
				logger.debug(md1.toString());
				
				Assert.assertTrue(TestCommon.compareMetadataToExpected(
						md1, 
						TestCommon.TEST_DATA_DIR + "/expected_metadata/gaussian/" + collectionNames[c] + ".metadata")
					);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			assert(false);
		}
	}
}
