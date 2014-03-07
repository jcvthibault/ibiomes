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
import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFactory;

/**
 * Test suite for default experiment file parser
 * @author Julien Thibault, University of Utah
 *
 */
public class DefaultParserTest{

	private final Logger logger = Logger.getLogger(DefaultParserTest.class);
	
	String[] gaussianCollections = {
			TestCommon.TEST_DATA_DIR + "/gaussian/acac", 
			TestCommon.TEST_DATA_DIR + "/gaussian/fosfina", 
			TestCommon.TEST_DATA_DIR + "/gaussian/gli", 
			TestCommon.TEST_DATA_DIR + "/gaussian/N6-SH2",
			TestCommon.TEST_DATA_DIR + "/gaussian/modredundant",
			TestCommon.TEST_DATA_DIR + "/gaussian/clx2-multi",  
			TestCommon.TEST_DATA_DIR + "/gaussian/PDTO"};
	
	String[] nwchemCollections = {
			TestCommon.TEST_DATA_DIR + "/nwchem/n2_ccsd",
			TestCommon.TEST_DATA_DIR + "/nwchem/oniom1",
			TestCommon.TEST_DATA_DIR + "/nwchem/prop_ch3f",
			TestCommon.TEST_DATA_DIR + "/nwchem/qmmm_esp0",
			TestCommon.TEST_DATA_DIR + "/nwchem/dft_bsse",
			TestCommon.TEST_DATA_DIR + "/nwchem/ch5n_nbo",
			TestCommon.TEST_DATA_DIR + "/nwchem/water",
			TestCommon.TEST_DATA_DIR + "/nwchem/ethanol"};
	
	String[] gamessCollections = {
			TestCommon.TEST_DATA_DIR + "/gamess/acetonitrile", 
			TestCommon.TEST_DATA_DIR + "/gamess/S3",
			TestCommon.TEST_DATA_DIR + "/gamess/methanol",
			TestCommon.TEST_DATA_DIR + "/gamess/sodium_tddft"};
	
	String[] amberCollections = {
			TestCommon.TEST_DATA_DIR + "/amber/1BIVm1", 
			TestCommon.TEST_DATA_DIR + "/amber/rnamod_drd",
			TestCommon.TEST_DATA_DIR + "/amber/tutorial3",
			TestCommon.TEST_DATA_DIR + "/amber/tutorial1/a-dna",
			TestCommon.TEST_DATA_DIR + "/amber/2H49",
			TestCommon.TEST_DATA_DIR + "/amber/kang_Na_orig"};
	
	String[] gromacsCollections = {
			TestCommon.TEST_DATA_DIR + "/gromacs/FAD", 
			TestCommon.TEST_DATA_DIR + "/gromacs/nmr1",
			TestCommon.TEST_DATA_DIR + "/gromacs/nmr2",
			TestCommon.TEST_DATA_DIR + "/gromacs/methanol",
			TestCommon.TEST_DATA_DIR + "/gromacs/water"};

	public DefaultParserTest() throws Exception{
		IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
	}
	
	@Test
	public void testAmber() throws Exception{
		try{
			for (int c=0; c<amberCollections.length; c++)
			{
				String inputDir = amberCollections[c];
				System.out.println("Parsing unknown collection " + inputDir);
				ExperimentFactory factory = new ExperimentFactory(inputDir);
				LocalDirectory coll = factory.parseDirectoryForMetadata(null, null, null);
				MetadataAVUList md1 = coll.getMetadata();
				logger.debug(md1.toString());
			}
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void testGaussian() throws Exception{
		try{
			for (int c=0; c<gaussianCollections.length; c++)
			{
				String inputDir = gaussianCollections[c];
				System.out.println("Parsing unknown collection " + inputDir);
				ExperimentFactory factory = new ExperimentFactory(inputDir);
				LocalDirectory coll = factory.parseDirectoryForMetadata(null, null, null);
				MetadataAVUList md1 = coll.getMetadata();
				logger.debug(md1.toString());
			}
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void testGromacs() throws Exception{
		try{
			for (int c=0; c<gromacsCollections.length; c++)
			{
				String inputDir = gromacsCollections[c];
				System.out.println("Parsing unknown collection " + inputDir);
				ExperimentFactory factory = new ExperimentFactory(inputDir);
				LocalDirectory coll = factory.parseDirectoryForMetadata(null, null, null);
				MetadataAVUList md1 = coll.getMetadata();
				logger.debug(md1.toString());
			}
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void testNWChem() throws Exception{
		try{
			for (int c=0; c<nwchemCollections.length; c++)
			{
				String inputDir = nwchemCollections[c];
				System.out.println("Parsing unknown collection " + inputDir);
				ExperimentFactory factory = new ExperimentFactory(inputDir);
				LocalDirectory coll = factory.parseDirectoryForMetadata(null, null, null);
				MetadataAVUList md1 = coll.getMetadata();
				logger.debug(md1.toString());
			}
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void testGAMESS() throws Exception{
		try{
			for (int c=0; c<gamessCollections.length; c++)
			{
				String inputDir = gamessCollections[c];
				System.out.println("Parsing unknown collection " + inputDir);
				ExperimentFactory factory = new ExperimentFactory(inputDir);
				LocalDirectory coll = factory.parseDirectoryForMetadata(null, null, null);
				MetadataAVUList md1 = coll.getMetadata();
				logger.debug(md1.toString());
			}
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
}
