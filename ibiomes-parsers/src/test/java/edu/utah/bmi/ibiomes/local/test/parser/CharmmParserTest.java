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
import org.junit.Test;

import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.LocalFileFactory;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFactory;
import edu.utah.bmi.ibiomes.parse.chem.charmm.CHARMMCoordinateFile;
import edu.utah.bmi.ibiomes.parse.chem.charmm.CHARMMInputFile;
import edu.utah.bmi.ibiomes.parse.chem.common.DCDTrajectoryFile;
import edu.utah.bmi.ibiomes.parse.chem.common.ProteinStructureFile;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.local.test.TestCommon;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

public class CharmmParserTest {

	private final Logger logger = Logger.getLogger(CharmmParserTest.class);
	
	String[] psfFiles = {
			"namd/namd-tutorial-files/common/ubq_ws.psf"
		};
	String[] confFiles = {
			"charmm/embnet-tutorial/md_run.inp"
		};
	String[] dcdFiles = {
			"namd/namd-tutorial-files/namd-coarse-graining/5_simulation/example-output/output/sim.dcd"
		};
	String[] collections = {
			"charmm/embnet-tutorial"
		};

	public CharmmParserTest() throws Exception{
		IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
	}
	
	@Test
	public void testPSF()
	{
		try {
			MetadataAVUList md = null;
			LocalFileFactory ffactory = LocalFileFactory.instance();
			
			for (int f=0; f<psfFiles.length; f++)
			{
				String inputFile = TestCommon.TEST_DATA_DIR + "/" + psfFiles[f];
				System.out.println("Parsing Protein Structure File (PSF) " + inputFile);
				ProteinStructureFile inp  = new ProteinStructureFile(inputFile);
				md = inp.getMetadata();
				logger.info(md.toString());
				
				LocalFile inpGen  = ffactory.getFile(inputFile, Software.CHARMM);
				md = inpGen.getMetadata();
				logger.debug(md.toString());
				logger.debug("----------------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testCharmmInputFiles()
	{
		try{
			MetadataAVUList md = null;
			LocalFileFactory ffactory = LocalFileFactory.instance();
					
			for (int f=0; f<confFiles.length; f++)
			{
				String outputFile = TestCommon.TEST_DATA_DIR + "/" + confFiles[f];
				System.out.println("Parsing CHARMM input file " + outputFile);
				CHARMMInputFile out  = new CHARMMInputFile(outputFile);
				md = out.getMetadata();
				logger.info(md.toString());
				
				LocalFile inpGen  = ffactory.getFile(outputFile, Software.CHARMM);
				md = inpGen.getMetadata();
				logger.debug(md.toString());
				logger.debug("----------------------------------------");
			}
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
					
			for (int f=0; f<dcdFiles.length; f++)
			{
				String outputFile = TestCommon.TEST_DATA_DIR + "/" + dcdFiles[f];
				System.out.println("Parsing CHARMM DCD trajectory file " + outputFile);
				DCDTrajectoryFile out  = new DCDTrajectoryFile(outputFile);
				md = out.getMetadata();
				logger.info(md.toString());
				
				LocalFile inpGen  = ffactory.getFile(outputFile, Software.CHARMM);
				md = inpGen.getMetadata();
				logger.debug(md.toString());
				logger.debug("----------------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCharmmCollections(){
		
		try{
			MetadataAVUList md = null;
			for (int c=0; c<collections.length; c++)
			{
				String inputDir = TestCommon.TEST_DATA_DIR + "/" + collections[c];
				System.out.println("Parsing CHARMM experiment " + inputDir);
				ExperimentFactory factory = new ExperimentFactory(inputDir);
				LocalDirectory coll = factory.parseDirectoryForMetadata(Software.CHARMM, null, null);
				md = coll.getMetadata();
				logger.info(md.toString());
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
