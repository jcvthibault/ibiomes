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

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.local.test.TestCommon;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.LocalFileFactory;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFactory;
import edu.utah.bmi.ibiomes.parse.chem.gamess.GAMESSInputFile;
import edu.utah.bmi.ibiomes.parse.chem.gamess.GAMESSOutputFile;

/**
 * Test suite for GAMESS file parsers
 * @author Julien Thibault, University of Utah
 *
 */
public class GamessParserTest {

	private final Logger logger = Logger.getLogger(GamessParserTest.class);
	
	String[] inputFiles = {"acetonitrile/acetonitrile.inp", "S3/S3anion.inp", "methanol/methanol.inp", "sodium_tddft/NaTDDFT.inp"};
	String[] outputFiles = {"acetonitrile/acetonitrile.out", "S3/S3anion.out", "methanol/methanol.out"};
	String[] collections = {"acetonitrile", "S3", "methanol", "sodium_tddft"};

	public GamessParserTest() throws Exception{
		IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
	}
	
	@Test
	public void testGamessInputs() throws Exception
	{
		try{
			MetadataAVUList md = null;
			LocalFileFactory ffactory = LocalFileFactory.instance();
			
			// GAMESS input files
			for (int f=0; f<inputFiles.length; f++)
			{
				String inputFile = TestCommon.TEST_DATA_DIR + "/gamess/" + inputFiles[f];
				System.out.println("Parsing GAMESS input file " + inputFile);
				GAMESSInputFile inp  = new GAMESSInputFile(inputFile);
				md = inp.getMetadata();
				logger.info(md.toString());
				
				LocalFile inpGen  = ffactory.getFile(inputFile, Software.GAMESS);
				logger.debug("----------------------------------------");
				md = inpGen.getMetadata();
				logger.debug(md.toString());
			}
		} catch (Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void testGamessOutputs() throws Exception
	{
		try{
			MetadataAVUList md = null;
			LocalFileFactory ffactory = LocalFileFactory.instance();
					
			// GAMESS output files
			for (int f=0; f<outputFiles.length; f++)
			{
				String outputFile = TestCommon.TEST_DATA_DIR + "/gamess/" + outputFiles[f];
				System.out.println("Parsing GAMESS output file " + outputFile);
				GAMESSOutputFile out  = new GAMESSOutputFile(outputFile);
				md = out.getMetadata();
				logger.debug(md.toString());
				
				LocalFile inpGen  = ffactory.getFile(outputFile, Software.GAMESS);
				logger.debug("----------------------------------------");
				md = inpGen.getMetadata();
				logger.debug(md.toString());
			}
		} catch (Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void testGamessCollections() throws Exception{
		
		try{
			MetadataAVUList md = null;
			
			// GAMESS collections
			for (int c=0; c<collections.length; c++)
			{
				String inputDir = TestCommon.TEST_DATA_DIR + "/gamess/" + collections[c];
				System.out.println("Parsing GAMESS experiment " + inputDir);
				ExperimentFactory factory = new ExperimentFactory(inputDir);
				LocalDirectory coll = factory.parseDirectoryForMetadata(Software.GAMESS, null, null);
				md = coll.getMetadata();
				logger.debug(md.toString());
			}
		} catch (Exception e){
			e.printStackTrace();
			throw e;
		}
	}
}
