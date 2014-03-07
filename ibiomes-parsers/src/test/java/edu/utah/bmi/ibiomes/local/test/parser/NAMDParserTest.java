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
import edu.utah.bmi.ibiomes.parse.chem.amber.AmberParameterTopologyFile;
import edu.utah.bmi.ibiomes.parse.chem.common.DCDTrajectoryFile;
import edu.utah.bmi.ibiomes.parse.chem.common.ProteinStructureFile;
import edu.utah.bmi.ibiomes.parse.chem.namd.NAMDConfigurationFile;
import edu.utah.bmi.ibiomes.parse.chem.namd.NAMDStandardOutputFile;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.conf.IBIOMESExecutionTimeSummary;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.local.test.TestCommon;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

/**
 * Test suite for NAMD file parser.
 * @author Julien Thibault, University of Utah
 *
 */
public class NAMDParserTest{

	private final Logger logger = Logger.getLogger(NAMDParserTest.class);
	
	String[] psfFiles = {"namd-tutorial-files/common/ubq_ws.psf"};
	
	String[] confFiles = {
			"namd-tutorial-files/common/sample.conf",
			"namd-amber/dyn1.conf"
	};

	String[] outFiles = {
			"namd-amber/dyn1.out"
	};
	
	String[] dcdFiles = {"namd-tutorial-files/namd-coarse-graining/5_simulation/example-output/output/sim.dcd"};

	String[] parmtopFiles = {"namd-amber/sys.parm"};
	
	String[] collections = {
			"namd-amber"
	};

	public NAMDParserTest() throws Exception{
		IBIOMESConfiguration config = IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
		config.setHasCollectTimingsOn(true);
	}
	
	@Test
	public void testPSF()
	{
		try {
			MetadataAVUList md = null;
			LocalFileFactory ffactory = LocalFileFactory.instance();
			
			// NAMD input files
			for (int f=0; f<psfFiles.length; f++)
			{
				String inputFile = TestCommon.TEST_DATA_DIR + "/namd/" + psfFiles[f];
				System.out.println("Parsing Protein Structure File (PSF) " + inputFile);
				ProteinStructureFile inp  = new ProteinStructureFile(inputFile);
				md = inp.getMetadata();
				logger.info(md.toString());
				
				LocalFile inpGen  = ffactory.getFile(inputFile, Software.NAMD);
				logger.info("----------------------------------------");
				md = inpGen.getMetadata();
				logger.info(md.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	/**
	 * NAMD config files
	 */
	public void testNAMDConfFiles()
	{
		try{
			MetadataAVUList md = null;
			LocalFileFactory ffactory = LocalFileFactory.instance();
	
			for (int f=0; f<confFiles.length; f++)
			{
				String outputFile = TestCommon.TEST_DATA_DIR + "/namd/" + confFiles[f];
				System.out.println("Parsing NAMD configuration file " + outputFile);
				NAMDConfigurationFile out  = new NAMDConfigurationFile(outputFile);
				md = out.getMetadata();
				logger.info(md.toString());
				
				LocalFile inpGen  = ffactory.getFile(outputFile, Software.NAMD);
				logger.info("----------------------------------------");
				md = inpGen.getMetadata();
				logger.info(md.toString());
			}
			//timings
			IBIOMESExecutionTimeSummary timingsSummary = IBIOMESExecutionTimeSummary.getInstance();
			timingsSummary.printToFile(TestCommon.TEST_DATA_DIR + "/logs/times-NAMD-conf.log");
			timingsSummary.clear();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	/**
	 * NAMD/AMBER parmtop files
	 */
	public void testNAMDParmtopFiles()
	{
		try{
			MetadataAVUList md = null;
			LocalFileFactory ffactory = LocalFileFactory.instance();
	
			for (int f=0; f<parmtopFiles.length; f++)
			{
				String outputFile = TestCommon.TEST_DATA_DIR + "/namd/" + parmtopFiles[f];
				System.out.println("Parsing NAMD/AMBER parmtop file " + outputFile);
				AmberParameterTopologyFile out  = new AmberParameterTopologyFile(outputFile);
				md = out.getMetadata();
				logger.info(md.toString());
				
				LocalFile inpGen  = ffactory.getFile(outputFile, Software.NAMD);
				logger.info("----------------------------------------");
				md = inpGen.getMetadata();
				logger.info(md.toString());
			}
			
			//timings
			IBIOMESExecutionTimeSummary timingsSummary = IBIOMESExecutionTimeSummary.getInstance();
			timingsSummary.printToFile(TestCommon.TEST_DATA_DIR + "/logs/times-NAMD-topo.log");
			timingsSummary.clear();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * NAMD log files
	 */
	@Test
	public void tesLogFiles()
	{
		try{
			MetadataAVUList md = null;
			LocalFileFactory ffactory = LocalFileFactory.instance();
			
			for (int f=0; f<outFiles.length; f++)
			{
				String outputFile = TestCommon.TEST_DATA_DIR + "/namd/" + outFiles[f];
				System.out.println("Parsing NAMD log file " + outputFile);
				NAMDStandardOutputFile out  = new NAMDStandardOutputFile(outputFile);
				md = out.getMetadata();
				logger.info(md.toString());
				
				LocalFile inpGen  = ffactory.getFile(outputFile, Software.NAMD);
				logger.info("----------------------------------------");
				md = inpGen.getMetadata();
				logger.info(md.toString());
			}
			//timings
			IBIOMESExecutionTimeSummary timingsSummary = IBIOMESExecutionTimeSummary.getInstance();
			timingsSummary.printToFile(TestCommon.TEST_DATA_DIR + "/logs/times-NAMD-output.log");
			timingsSummary.clear();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDCDFiles()
	{
		try{
			MetadataAVUList md = null;
			LocalFileFactory ffactory = LocalFileFactory.instance();
					
			// NAMD output files
			for (int f=0; f<dcdFiles.length; f++)
			{
				String outputFile = TestCommon.TEST_DATA_DIR + "/namd/" + dcdFiles[f];
				System.out.println("Parsing NAMD trajectory file " + outputFile);
				DCDTrajectoryFile out  = new DCDTrajectoryFile(outputFile);
				md = out.getMetadata();
				logger.info(md.toString());
				
				LocalFile inpGen  = ffactory.getFile(outputFile, Software.NAMD);
				logger.info("----------------------------------------");
				md = inpGen.getMetadata();
				logger.info(md.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testNAMDCollections(){
		
		try{
			MetadataAVUList md = null;
			
			// NAMD collections
			for (int c=0; c<collections.length; c++)
			{
				String inputDir = TestCommon.TEST_DATA_DIR + "/namd/" + collections[c];
				System.out.println("Parsing NAMD experiment " + inputDir);
				ExperimentFactory factory = new ExperimentFactory(inputDir);
				LocalDirectory coll = factory.parseDirectoryForMetadata(Software.NAMD, null, null);
				
				md = coll.getMetadata();
				//System.out.println(md.toString());
			}
			assert(true);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
