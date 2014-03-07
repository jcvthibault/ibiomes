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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.local.test.TestCommon;
import edu.utah.bmi.ibiomes.parse.DirectoryParser;
import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.parse.LocalFile;

public class DirectoryParserWithDescriptorTest {
	
	private final Logger logger = Logger.getLogger(DirectoryParserWithDescriptorTest.class);
		
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
			TestCommon.TEST_DATA_DIR + "/amber/qm_mm/NMA",
			TestCommon.TEST_DATA_DIR + "/amber/kang_Na_orig"};
	
	String[] gromacsCollections = {
			TestCommon.TEST_DATA_DIR + "/gromacs/FAD", 
			TestCommon.TEST_DATA_DIR + "/gromacs/nmr1",
			TestCommon.TEST_DATA_DIR + "/gromacs/nmr2",
			TestCommon.TEST_DATA_DIR + "/gromacs/methanol",
			TestCommon.TEST_DATA_DIR + "/gromacs/water"};

	public DirectoryParserWithDescriptorTest() throws Exception{
		IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
	}
	
	@Test
	public void testConvertGaussian() throws Exception
	{
		try{
			for (int f=0; f<gaussianCollections.length; f++)
			{
				DirectoryStructureDescriptor desc = 
						new DirectoryStructureDescriptor(TestCommon.TEST_DATA_DIR + "/project_descriptor/desc_gaussian.xml");
				System.out.println("Parsing Gaussian experiment " + gaussianCollections[f] + "...");
				DirectoryParser parser  = new DirectoryParser(gaussianCollections[f], desc);
				LocalDirectory exp = parser.parseDirectories(Software.GAUSSIAN);
				dumpDirectory(exp);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void testConvertNWChem() throws Exception
	{
		try{
			for (int f=0; f<nwchemCollections.length; f++)
			{
				DirectoryStructureDescriptor desc = 
						new DirectoryStructureDescriptor(TestCommon.TEST_DATA_DIR + "/project_descriptor/desc_nwchem.xml");
				System.out.println("Parsing NWChem experiment " + nwchemCollections[f] + "...");
				DirectoryParser parser  = new DirectoryParser(nwchemCollections[f], desc);
				LocalDirectory exp = parser.parseDirectories(Software.NWCHEM);
				dumpDirectory(exp);
			}

		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	//TODO create descriptor for GAMESS experiments
	/*@Test
	public void testConvertGamess() throws Exception
	{
		try{
			for (int f=0; f<gamessCollections.length; f++)
			{
				DirectoryStructureDescriptor desc = 
						new DirectoryStructureDescriptor(TestCommon.TEST_DATA_DIR + "/project_descriptor/desc_gamess.xml");
				System.out.println("Parsing GAMESS experiment " + gamessCollections[f] + "...");
				DirectoryParser parser  = new DirectoryParser(gamessCollections[f], desc);
				LocalDirectory exp = parser.parseDirectories(Software.GAMESS);
				dumpDirectory(exp);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}*/
	
	@Test
	public void testConvertAmber() throws Exception
	{
		try{
			for (int f=0; f<amberCollections.length; f++)
			{
				DirectoryStructureDescriptor desc = 
						new DirectoryStructureDescriptor(TestCommon.TEST_DATA_DIR + "/project_descriptor/desc_amber.xml");
				System.out.println("Parsing Amber experiment " + amberCollections[f] + "...");
				DirectoryParser parser  = new DirectoryParser(amberCollections[f], desc);
				LocalDirectory exp = parser.parseDirectories(Software.AMBER);
				dumpDirectory(exp);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConvertGromacs() throws Exception
	{
		try{
			for (int f=0; f<gromacsCollections.length; f++)
			{
				DirectoryStructureDescriptor desc = 
						new DirectoryStructureDescriptor(TestCommon.TEST_DATA_DIR + "/project_descriptor/desc_gromacs.xml");
				System.out.println("Parsing Gromacs experiment " + gromacsCollections[f] + "...");
				DirectoryParser parser  = new DirectoryParser(gromacsCollections[f], desc);
				LocalDirectory exp = parser.parseDirectories(Software.GROMACS);
				dumpDirectory(exp);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void dumpDirectory(LocalDirectory dir){
		logger.debug("Directory: " + dir.getAbsolutePath());
		HashMap<String, ArrayList<LocalFile>> files = dir.getFilesByFormat();
		if (files != null){
			for (String key : files.keySet()){
				ArrayList<LocalFile> filesInFormat = files.get(key);
				for (LocalFile file : filesInFormat){
					logger.debug("   |--- " + file.getName() + " (" + key + ")");
				}
			}
		}
		List<LocalDirectory> subdirs = dir.getSubdirectories();
		if (subdirs!=null){
			for (LocalDirectory subdir : subdirs){
				dumpDirectory(subdir);
			}
		}
	}
}
