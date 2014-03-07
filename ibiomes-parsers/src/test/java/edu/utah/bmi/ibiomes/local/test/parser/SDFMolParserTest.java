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

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.local.test.TestCommon;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.LocalFileFactory;
import edu.utah.bmi.ibiomes.parse.chem.common.SDFFile;

/**
 * Test suite for SDF/Mol file parser
 * @author Julien Thibault, University of Utah
 *
 */
public class SDFMolParserTest {
	
	private final Logger logger = Logger.getLogger(SDFMolParserTest.class);

	public SDFMolParserTest() throws Exception{
		IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
	}
	
	@Test
	public void testSDF() throws Exception
	{
		MetadataAVUList md = null;
		LocalFileFactory ffactory;
	
		ffactory = LocalFileFactory.instance();
	
		String[] inputFileNames = {
				"/sdf/Molecule_20_PEI-PAMAM.sdf", 
				"/sdf/Molecule_23_G4PAMAM-NH-Cys(SH)-NH2.sdf", 
				"/sdf/Molecule_96_Generation_5_PAMAM.sdf"
			};
		
		// Mol2 files
		for (int f=0; f<inputFileNames.length; f++)
		{
			String inputFile = TestCommon.TEST_DATA_DIR + inputFileNames[f];
			System.out.println("Parsing SDF/MOL file " + inputFile);
			SDFFile inp  = new SDFFile(inputFile);
			md = inp.getMetadata();
			logger.debug(md.toString());
			
			LocalFile inpGen  = ffactory.getFile(inputFile, null);
			logger.debug("----------------------------------------");
			md = inpGen.getMetadata();
			logger.debug(md.toString());
		}
		assert(true);
	}
}
