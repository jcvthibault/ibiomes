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

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.local.test.TestCommon;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.LocalFileFactory;
import edu.utah.bmi.ibiomes.parse.chem.common.PDBFile;

/**
 * Test suite for PDB file parser
 * @author Julien Thibault, University of Utah
 *
 */
public class PDBFileTest{
	
	private final Logger logger = Logger.getLogger(PDBFileTest.class);

	public PDBFileTest() throws Exception{
		IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
	}
	
	@Test
	public void testPDB()
	{
		try{
			MetadataAVUList md = null;
			LocalFileFactory ffactory;
		
			ffactory = LocalFileFactory.instance();
		
			String[] inputFileNames = {
					"/amber/1BIVm1/1BIVm1.pdb", 
					"/amber/tutorial1/nuc.pdb", 
					"/amber/tutorial1/a-dna/a-dna.nab.pdb", 
					"/amber/tutorial1/a-dna/a-dna_20-1820ps_average.pdb",
					"/amber/tutorial3/tc5b_linear.pdb",
					"/amber/tutorial3/equil_average_28-35.pdb"
			};
			
			// PDB files
			for (int f=0; f<inputFileNames.length; f++)
			{
				String inputFile = TestCommon.TEST_DATA_DIR + inputFileNames[f];
				System.out.println("Parsing PDB file " + inputFile);
				PDBFile inp  = new PDBFile(inputFile);
				md = inp.getMetadata();
				logger.info(md.toString());
				
				LocalFile inpGen  = ffactory.getFile(inputFile, null);
				logger.debug("----------------------------------------");
				md = inpGen.getMetadata();
				logger.debug(md.toString());
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

}
