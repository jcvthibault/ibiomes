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

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.conf.IBIOMESExecutionTimeSummary;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.local.test.TestCommon;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.LocalFileFactory;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFactory;
import edu.utah.bmi.ibiomes.parse.chem.amber.AmberLeapLogFile;
import edu.utah.bmi.ibiomes.parse.chem.amber.AmberMdInputFile;
import edu.utah.bmi.ibiomes.parse.chem.amber.AmberMdOutputFile;
import edu.utah.bmi.ibiomes.parse.chem.amber.AmberParameterTopologyFile;

/**
 * Test suite for AMBER files parsers
 * @author Julien Thibault, University of Utah
 *
 */
public class AmberParserTest{

	private final Logger logger = Logger.getLogger(AmberParserTest.class);
	
	String[] topoFileNames = {
			"CGGT/CGGT-ions.topo",
			"GAAC.topo",
			"complex_vacuo.topo",
			"1BIVm1/vac.topo",
			"1BIVm1/solvtd.topo",
			"1BIVm1/test.topo",
			"rnamod_drd/c414_vac.topo"
		};
	String[] inputFileNames = {
			"rnamod_drd/min.in" ,
			"tutorial1/a-dna/a-dna_min2.in",
			"tutorial1/a-dna/a-dna_md1.in",
			"sebomd/am1-nucleoside/md.in",
			"qm_mm/NMA/md_qmmm.in"
		};
	String[] outputFileNames = {
			"1BIVm1/mini.out",
			"1BIVm1/eq2.out",
			"tutorial3/heat1.out",
			"tutorial3/heat2.out",
			"rnamod_drd/md_gb.out",
			"remd/multi-d/rep.001.out",
			"remd/h-remd/rep.001.out",
			"sebomd/am1-nucleoside/md.out",
			"qm_mm/NMA/md_qmmm.out"
		};
	String[] collectionNames = {
			"1BIVm1", 
			"qm_mm/NMA",
			"rnamod_drd", 
			"tutorial3", 
			"tutorial1/a-dna",
			"2H49", 
			"remd/h-remd", 
			"remd/multi-d", 
			"sebomd/am1-nucleoside",
			"sebomd/mndo-methionine",
			"sebomd/pm3-alaninedipeptide",
			"kang_Na_orig"
		};
	
	String[] leapLogNames = {
			"rnamod_drd/leap.log",
			"kang_Na_orig/leap.log",
			"tutorial3/leap.log"
		};
	
	String[] qmmmAndy = {
			"qm_mm/aladip-qmmm-md"
			//"qm_mm/TIP3-cap/us.QMMM.a150.d150"
		};
	
	public AmberParserTest() throws Exception{
		IBIOMESConfiguration config = IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
		config.setHasCollectTimingsOn(true);
	}
	
	@Test
	public void testTopologies() throws Exception
	{
		LocalFileFactory ffactory = LocalFileFactory.instance();
		
		// AMBER topology files
		for (int f=0; f<topoFileNames.length; f++)
		{
			String inputFile = TestCommon.TEST_DATA_DIR + "/amber/" + topoFileNames[f];
			System.out.println("Parsing AMBER topology file " + inputFile + "...");
			AmberParameterTopologyFile inp  = new AmberParameterTopologyFile(inputFile);
			
			/*List<MolecularSystem> systems = inp.getMolecularSystems();
			for (MolecularSystem system : systems){
				List<MoleculeOccurrence> ions = system.getIonOccurrences();
				for (MoleculeOccurrence ion : ions){
					System.out.println("\nION " + ion.getMetadata());
				}
				List<MoleculeOccurrence> molecules = system.getSoluteMoleculeOccurrences();
				for (MoleculeOccurrence molecule : molecules){
					System.out.println("\nMOLECULE " + molecule.getMetadata());
				}
			}*/
			
			MetadataAVUList md1 = inp.getMetadata();
			logger.info(md1.toString());
			
			LocalFile inpGen  = ffactory.getFile(inputFile, Software.AMBER);
			MetadataAVUList md2 = inpGen.getMetadata();
			Assert.assertTrue(TestCommon.compareMetadata(md1, md2));
			
			//TestCommon.compareMetadataToExpected(md1, TestCommon.TEST_DATA_DIR + "/expected_metadata/amber/" + topoFileNames[f] + ".metadata");	
			Assert.assertTrue(TestCommon.compareMetadataToExpected(
					md1, 
					TestCommon.TEST_DATA_DIR + "/expected_metadata/amber/" + topoFileNames[f] + ".metadata")
				);
			logger.info("----------------------------------------");
		}
		//timings
		IBIOMESExecutionTimeSummary timingsSummary = IBIOMESExecutionTimeSummary.getInstance();
		timingsSummary.printToFile(TestCommon.TEST_DATA_DIR + "/logs/times-AMBER-topo.log");
		timingsSummary.clear();
	}
	
	@Test
	public void testInputs() throws Exception
	{
		LocalFileFactory ffactory = LocalFileFactory.instance();
		
		// AMBER input files
		for (int f=0; f<inputFileNames.length; f++)
		{
			String inputFile = TestCommon.TEST_DATA_DIR + "/amber/" + inputFileNames[f];
			System.out.println("Parsing AMBER input file " + inputFile + "...");
			AmberMdInputFile inp  = new AmberMdInputFile(inputFile);
			MetadataAVUList md1 = inp.getMetadata();
			logger.info(md1.toString());
			
			LocalFile inpGen  = ffactory.getFile(inputFile, Software.AMBER);
			MetadataAVUList md2 = inpGen.getMetadata();
			Assert.assertTrue(TestCommon.compareMetadata(md1, md2));
			
			//TestCommon.compareMetadataToExpected( md1, TestCommon.TEST_DATA_DIR + "/expected_metadata/amber/" + inputFileNames[f] + ".metadata");
			Assert.assertTrue(TestCommon.compareMetadataToExpected(
					md1, 
					TestCommon.TEST_DATA_DIR + "/expected_metadata/amber/" + inputFileNames[f] + ".metadata")
				);
			logger.info("----------------------------------------");
		}
	}
	
	@Test
	public void testOutputs() throws Exception
	{
		try{
			LocalFileFactory ffactory = LocalFileFactory.instance();
			
			// AMBER output files
			for (int f=0; f<outputFileNames.length; f++)
			{
				String outputFile = TestCommon.TEST_DATA_DIR + "/amber/" + outputFileNames[f];
				System.out.println("Parsing AMBER output file " + outputFile + "...");
				AmberMdOutputFile out  = new AmberMdOutputFile(outputFile);
				MetadataAVUList md1 = out.getMetadata();
				logger.info(md1.toString());
				
				LocalFile inpGen  = ffactory.getFile(outputFile, Software.AMBER);
				MetadataAVUList md2 = inpGen.getMetadata();
				Assert.assertTrue(TestCommon.compareMetadata(md1, md2));
				
				//TestCommon.compareMetadataToExpected(md1, TestCommon.TEST_DATA_DIR + "/expected_metadata/amber/" + outputFileNames[f] + ".metadata");
				Assert.assertTrue(TestCommon.compareMetadataToExpected(
						md1, 
						TestCommon.TEST_DATA_DIR + "/expected_metadata/amber/" + outputFileNames[f] + ".metadata")
					);
				logger.info("----------------------------------------");
			}

			IBIOMESExecutionTimeSummary timingsSummary = IBIOMESExecutionTimeSummary.getInstance();
			timingsSummary.printToFile(TestCommon.TEST_DATA_DIR + "/logs/times-AMBER-output.log");
			timingsSummary.clear();
		}
		catch(Exception e ){
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void testCollections() throws Exception {
		
		// AMBER collections
		for (int c=0; c<collectionNames.length; c++)
		{
			String inputDir = TestCommon.TEST_DATA_DIR + "/amber/" + collectionNames[c];
			System.out.println("Parsing AMBER experiment " + inputDir + "...");
			ExperimentFactory factory = new ExperimentFactory(inputDir);
			LocalDirectory coll = factory.parseDirectoryForMetadata(Software.AMBER, null, null);
			MetadataAVUList md1 = coll.getMetadata();
			logger.info(md1.toString());
			
			//TestCommon.compareMetadataToExpected( md1, TestCommon.TEST_DATA_DIR + "/expected_metadata/amber/" + collectionNames[c] + ".metadata");
			Assert.assertTrue(TestCommon.compareMetadataToExpected(
					md1, 
					TestCommon.TEST_DATA_DIR + "/expected_metadata/amber/" + collectionNames[c] + ".metadata")
				);
			logger.info("----------------------------------------");
		}
	}
	
	@Test
	public void testCollectionsAndy() throws Exception {
		
		DirectoryStructureDescriptor xml = new DirectoryStructureDescriptor(TestCommon.TEST_DATA_DIR + "/project_descriptor/desc_amber_andy.xml");
		// AMBER collections
		for (int c=0; c<qmmmAndy.length; c++)
		{
			String inputDir = TestCommon.TEST_DATA_DIR + "/amber/" + qmmmAndy[c];
			System.out.println("Parsing AMBER experiment " + inputDir + "...");
			ExperimentFactory factory = new ExperimentFactory(inputDir);
			LocalDirectory coll = factory.parseDirectoryForMetadata(Software.AMBER, xml , null);
			MetadataAVUList md1 = coll.getMetadata();
			logger.info(md1.toString());
			
			//TestCommon.compareMetadataToExpected( md1, TestCommon.TEST_DATA_DIR + "/expected_metadata/amber/" + collectionNames[c] + ".metadata");
			Assert.assertTrue(TestCommon.compareMetadataToExpected(
					md1, 
					TestCommon.TEST_DATA_DIR + "/expected_metadata/amber/" + qmmmAndy[c] + ".metadata")
				);
			logger.info("----------------------------------------");
		}
	}
	
	@Test
	public void testLeapLogs() throws Exception
	{
		// AMBER input files
		for (int f=0; f<leapLogNames.length; f++)
		{
			String leapLog = TestCommon.TEST_DATA_DIR + "/amber/" + leapLogNames[f];
			System.out.println("Parsing AMBER Leap log file " + leapLog + "...");
			AmberLeapLogFile inp  = new AmberLeapLogFile(leapLog);
			MetadataAVUList md1 = inp.getMetadata();
			logger.info(md1.toString());
			
			/*TestCommon.compareMetadataToExpected( md1, TestCommon.TEST_DATA_DIR + "/expected_metadata/amber/" + leapLogNames[f] + ".metadata");
			Assert.assertTrue(TestCommon.compareMetadataToExpected(
					md1, 
					TestCommon.TEST_DATA_DIR + "/expected_metadata/amber/" + leapLogNames[f] + ".metadata")
				);*/
			logger.info("----------------------------------------");
		}
	}
}
