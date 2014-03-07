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

package edu.utah.bmi.ibiomes.lite.test;

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.lite.IBIOMESLiteManager;
import edu.utah.bmi.ibiomes.lite.test.TestCommon;

/**
 * Tests for experiment export to PDF document
 * @author Julien Thibault
 *
 */
public class HTMLSummaryConverterTest {

	private final Logger logger = Logger.getLogger(HTMLSummaryConverterTest.class);
	
	private final String testLiteWebPath = TestCommon.TEST_DATA_DIR + "/lite/lite-web/";
	
	private final String xmlDescriptorAmber    = TestCommon.TEST_DATA_DIR + "/project_descriptor/desc_amber.xml";
	private final String xmlDescriptorGaussian = TestCommon.TEST_DATA_DIR + "/project_descriptor/desc_gaussian.xml";
	private final String xmlDescriptorAmberQMMM = TestCommon.TEST_DATA_DIR + "/project_descriptor/desc_amber_andy.xml";

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
			TestCommon.TEST_DATA_DIR + "/amber/remd/multi-d",
			TestCommon.TEST_DATA_DIR + "/amber/remd/h-remd",
			TestCommon.TEST_DATA_DIR + "/amber/kang_Na_orig",
			TestCommon.TEST_DATA_DIR + "/amber/sebomd/am1-nucleoside",
			TestCommon.TEST_DATA_DIR + "/amber/sebomd/mndo-methionine",
			TestCommon.TEST_DATA_DIR + "/amber/sebomd/pm3-alaninedipeptide"};

	String[] amberCollectionsQmmm = {
			TestCommon.TEST_DATA_DIR + "/amber/qm_mm/TIP3-cap/us.a150.d150",
			TestCommon.TEST_DATA_DIR + "/amber/qm_mm/TIP3-cap/us.B3LYP.a150.d150",
			TestCommon.TEST_DATA_DIR + "/amber/qm_mm/TIP3-cap/us.QMMM.a150.d150",
			TestCommon.TEST_DATA_DIR + "/amber/qm_mm/aladip-qmmm-md",};
	
	String[] gromacsCollections = {
			TestCommon.TEST_DATA_DIR + "/gromacs/FAD", 
			TestCommon.TEST_DATA_DIR + "/gromacs/nmr1",
			TestCommon.TEST_DATA_DIR + "/gromacs/nmr2",
			TestCommon.TEST_DATA_DIR + "/gromacs/speptide",
			TestCommon.TEST_DATA_DIR + "/gromacs/methanol",
			TestCommon.TEST_DATA_DIR + "/gromacs/water",
			TestCommon.TEST_DATA_DIR + "/gromacs/qmmm_protein", 
			TestCommon.TEST_DATA_DIR + "/gromacs/qmmm_pyp"};

	String[] namdCollections = {
			TestCommon.TEST_DATA_DIR + "/namd/namd-amber"};
	
	String[] charmmCollections = {
			TestCommon.TEST_DATA_DIR + "/charmm/embnet-tutorial"};
	
	public HTMLSummaryConverterTest() throws Exception{
		IBIOMESConfiguration config = IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
	}
	
	@Test
	public void testConvertGaussian() throws Exception
	{
		try{
			for (int f=0; f<gaussianCollections.length; f++)
			{
				System.out.println("Publishing Gaussian experiment " + gaussianCollections[f] + " to HTML...");
				IBIOMESConfiguration config = IBIOMESConfiguration.getInstance();
				config.setOutputToConsole(false);
				IBIOMESLiteManager mng = IBIOMESLiteManager.getInstance(testLiteWebPath, true);
				mng.publishExperiment(gaussianCollections[f], Software.GAUSSIAN, xmlDescriptorGaussian, true);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void testConvertNAMD() throws Exception
	{
		try{
			for (int f=0; f<namdCollections.length; f++)
			{
				System.out.println("Publishing NAMD experiment " + namdCollections[f] + " to HTML...");
				IBIOMESConfiguration config = IBIOMESConfiguration.getInstance();
				config.setOutputToConsole(false);
				IBIOMESLiteManager mng = IBIOMESLiteManager.getInstance(testLiteWebPath, true);
				mng.publishExperiment(namdCollections[f], Software.NAMD, null, true);
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
				System.out.println("Publishing NWChem experiment " + nwchemCollections[f] + " to HTML...");
				IBIOMESConfiguration config = IBIOMESConfiguration.getInstance();
				config.setOutputToConsole(false);
				IBIOMESLiteManager mng = IBIOMESLiteManager.getInstance(testLiteWebPath, true);
				mng.publishExperiment(nwchemCollections[f], Software.NWCHEM, null, true);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void testConvertCHARMM() throws Exception
	{
		try{
			for (int f=0; f<charmmCollections.length; f++)
			{
				System.out.println("Publishing CHARMM experiment " + charmmCollections[f] + " to HTML...");
				IBIOMESConfiguration config = IBIOMESConfiguration.getInstance();
				config.setOutputToConsole(false);
				IBIOMESLiteManager mng = IBIOMESLiteManager.getInstance(testLiteWebPath, true);
				mng.publishExperiment(charmmCollections[f], Software.CHARMM, null, true);
			}

		} catch (Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void testConvertGamess() throws Exception
	{
		try{
			for (int f=0; f<gamessCollections.length; f++)
			{
				System.out.println("Publishing GAMESS experiment " + gamessCollections[f] + " to HTML...");
				IBIOMESConfiguration config = IBIOMESConfiguration.getInstance();
				config.setOutputToConsole(false);
				IBIOMESLiteManager mng = IBIOMESLiteManager.getInstance(testLiteWebPath, true);
				mng.publishExperiment(gamessCollections[f], Software.GAMESS, null, true);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConvertAmber() throws Exception
	{
		try{
			for (int f=0; f<amberCollections.length; f++)
			{
				System.out.println("Publishing Amber experiment " + amberCollections[f] + " to HTML...");
				IBIOMESConfiguration config = IBIOMESConfiguration.getInstance();
				config.setOutputToConsole(false);
				IBIOMESLiteManager mng = IBIOMESLiteManager.getInstance(testLiteWebPath, true);
				mng.publishExperiment(amberCollections[f], Software.AMBER, xmlDescriptorAmber, true);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void testConvertAmberQmmm() throws Exception
	{
		try{
			for (int f=0; f<amberCollectionsQmmm.length; f++)
			{
				System.out.println("Publishing Amber QM/MM experiment " + amberCollectionsQmmm[f] + " to HTML...");
				IBIOMESConfiguration config = IBIOMESConfiguration.getInstance();
				config.setOutputToConsole(false);
				IBIOMESLiteManager mng = IBIOMESLiteManager.getInstance(testLiteWebPath, true);
				mng.publishExperiment(amberCollectionsQmmm[f], Software.AMBER, xmlDescriptorAmberQMMM, true);
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
				System.out.println("Publishing Gromacs experiment " + gromacsCollections[f] + " to HTML...");
				IBIOMESConfiguration config = IBIOMESConfiguration.getInstance();
				config.setOutputToConsole(false);
				IBIOMESLiteManager mng = IBIOMESLiteManager.getInstance(testLiteWebPath, true);
				mng.publishExperiment(gromacsCollections[f], Software.GROMACS, null, true);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
