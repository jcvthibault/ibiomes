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
import edu.utah.bmi.ibiomes.parse.chem.common.Mol2File;

/**
 * Test suite for Mol2 file parser
 * @author Julien Thibault, University of Utah
 *
 */
public class Mol2ParserTest {
	
	private final Logger logger = Logger.getLogger(Mol2ParserTest.class);

	public Mol2ParserTest() throws Exception{
		IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
	}
	
	@Test
	public void testMol2()
	{
		try{
			MetadataAVUList md = null;
			LocalFileFactory ffactory;
		
			ffactory = LocalFileFactory.instance();
		
			String[] inputFileNames = {
					"/mol2/17.mol2", 
					"/mol2/fol.mol2", 
					"/mol2/nadp.mol2", 
					"/mol2/1zik.mol2"
				};
			
			// Mol2 files
			for (int f=0; f<inputFileNames.length; f++)
			{
				String inputFile = TestCommon.TEST_DATA_DIR + inputFileNames[f];
				System.out.println("Parsing Mol2 file " + inputFile);
				Mol2File inp  = new Mol2File(inputFile);
				md = inp.getMetadata();
				logger.info(md.toString());
				
				LocalFile inpGen  = ffactory.getFile(inputFile, null);
				md = inpGen.getMetadata();
				logger.debug(md.toString());
				logger.info("----------------------------------------");
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
