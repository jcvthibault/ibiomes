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

import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.LocalFileFactory;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFactory;
import edu.utah.bmi.ibiomes.parse.chem.nwchem.NWChemInputFile;
import edu.utah.bmi.ibiomes.parse.chem.nwchem.NWChemOutputFile;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.local.test.TestCommon;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

/**
 * Test suite for NWChem file parsers
 * @author Julien Thibault, University of Utah
 *
 */
public class NWChemParserTest {
	
	private final Logger logger = Logger.getLogger(NWChemParserTest.class);

	String[] inputFileNames = {
			"water/water.nw",
			"n2_ccsd/n2_ccsd.nw", 
			"oniom1/oniom1.nw", 
			"prop_ch3f/prop_ch3f.nw", 
			"qmmm_esp0/qmmm_esp0.nw", 
			"dft_bsse/dft_bsse.nw", 
			"ch5n_nbo/ch5n_nbo.nw", 
			"ethanol/ethanol_md.nw",
			"mp2_si2h6/mp2_si2h6.nw"
		};
	
	String[] outputFileNames = {
			"n2_ccsd/n2_ccsd.out", 
			"oniom1/oniom1.out", 
			"prop_ch3f/prop_ch3f.out", 
			"qmmm_esp0/qmmm_esp0.out", 
			"dft_bsse/dft_bsse.out", 
			"ch5n_nbo/ch5n_nbo.out", 
			"ethanol/ethanol_md.out",
			"mp2_si2h6/mp2_si2h6.out"};
	
	String[] collectionNames = {
			"n2_ccsd", 
			"oniom1", 
			"prop_ch3f", 
			"qmmm_esp0", 
			"dft_bsse", 
			"ch5n_nbo", 
			"ethanol"};

	public NWChemParserTest() throws Exception{
		IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
	}
	
	@Test
	public void testNwchemInputs() throws Exception
	{
		try {
			MetadataAVUList md = null;
			LocalFileFactory ffactory = LocalFileFactory.instance();
			
			// NWChem input files
			for (int f=0; f<inputFileNames.length; f++)
			{
				String inputFile = TestCommon.TEST_DATA_DIR + "/nwchem/" + inputFileNames[f];
				System.out.println("Parsing NWChem input file " + inputFile + "...");
				NWChemInputFile inp  = new NWChemInputFile(inputFile);
				md = inp.getMetadata();
				logger.info(md.toString());
				
				LocalFile inpGen  = ffactory.getFile(inputFile, Software.NWCHEM);
				md = inpGen.getMetadata();
				logger.debug(md.toString());
				logger.debug("----------------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Test
	public void testNwchemOutputs() throws Exception
	{
		try{
			MetadataAVUList md = null;
			LocalFileFactory ffactory = LocalFileFactory.instance();
					
			// NWChem output files
			for (int f=0; f<outputFileNames.length; f++)
			{
				String outputFile = TestCommon.TEST_DATA_DIR + "/nwchem/" + outputFileNames[f];
				System.out.println("Parsing NWChem output file " + outputFile + "...");
				NWChemOutputFile out  = new NWChemOutputFile(outputFile);
				md = out.getMetadata();
				logger.info(md.toString());
				
				LocalFile inpGen  = ffactory.getFile(outputFile, Software.NWCHEM);
				logger.debug("----------------------------------------");
				md = inpGen.getMetadata();
				logger.debug(md.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void testNwchemCollections() throws Exception{
		
		try{
			MetadataAVUList md = null;
			
			// NWChem collections
			for (int c=0; c<collectionNames.length; c++)
			{
				String inputDir = TestCommon.TEST_DATA_DIR + "/nwchem/" + collectionNames[c];
				System.out.println("Parsing NWChem experiment " + inputDir + "...");
				ExperimentFactory factory = new ExperimentFactory(inputDir);
				LocalDirectory coll = factory.parseDirectoryForMetadata(Software.NWCHEM, null, null);
				md = coll.getMetadata();
				logger.info(md.toString());
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
