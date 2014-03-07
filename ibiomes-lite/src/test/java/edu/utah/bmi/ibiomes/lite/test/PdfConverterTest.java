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

/**
 * Tests for experiment export to PDF document
 * @author Julien Thibault
 *
 */
public class PdfConverterTest {

	private final Logger logger = Logger.getLogger(PdfConverterTest.class);
	
	public final static String TEST_DATA_DIR = System.getenv("IBIOMES_HOME") + "/test";

	private final String xmlDescriptorAmber    = TestCommon.TEST_DATA_DIR + "/project_descriptor/desc_amber.xml";
	private final String xmlDescriptorGaussian = TestCommon.TEST_DATA_DIR + "/project_descriptor/desc_gaussian.xml";
	
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
	
	
	public PdfConverterTest() throws Exception{
		IBIOMESConfiguration config = IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
	}
	
	public void testConvertGaussian() throws Exception
	{
		try{
			for (int f=0; f<gaussianCollections.length; f++)
			{
				System.out.println("Creating PDF summary for Gaussian experiment " + gaussianCollections[f] + "...");
				IBIOMESConfiguration config = IBIOMESConfiguration.getInstance();
				config.setOutputToConsole(false);
				IBIOMESLiteManager mng = IBIOMESLiteManager.getInstance();
				mng.generatePdf(
						TestCommon.TEST_DATA_DIR + "/output/" + gaussianCollections[f].substring(gaussianCollections[f].lastIndexOf("/")) + ".pdf", 
						gaussianCollections[f], 
						Software.GAUSSIAN, 
						xmlDescriptorGaussian, 
						true, 
						0);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	public void testConvertNWChem() throws Exception
	{
		try{
			for (int f=0; f<nwchemCollections.length; f++)
			{
				System.out.println("Creating PDF summary for NWChem experiment " + nwchemCollections[f] + "...");
				IBIOMESConfiguration config = IBIOMESConfiguration.getInstance();
				config.setOutputToConsole(false);
				IBIOMESLiteManager mng = IBIOMESLiteManager.getInstance();
				mng.generatePdf(
						TestCommon.TEST_DATA_DIR + "/output/" + nwchemCollections[f].substring(nwchemCollections[f].lastIndexOf("/")) + ".pdf", 
						nwchemCollections[f], 
						Software.NWCHEM, 
						null, 
						true, 
						0);
			}

		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void testConvertGamess() throws Exception
	{
		try{
			for (int f=0; f<gamessCollections.length; f++)
			{
				System.out.println("Creating PDF summary for GAMESS experiment " + gamessCollections[f] + "...");
				IBIOMESConfiguration config = IBIOMESConfiguration.getInstance();
				config.setOutputToConsole(false);
				IBIOMESLiteManager mng = IBIOMESLiteManager.getInstance();
				mng.generatePdf(
						TestCommon.TEST_DATA_DIR + "/output/" + gamessCollections[f].substring(gamessCollections[f].lastIndexOf("/")) + ".pdf", 
						gamessCollections[f], 
						Software.GAMESS, 
						null, 
						true, 
						0);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void testConvertAmber() throws Exception
	{
		try{
			for (int f=0; f<amberCollections.length; f++)
			{
				System.out.println("Creating PDF summary for AMBER experiment " + amberCollections[f] + "...");
				IBIOMESConfiguration config = IBIOMESConfiguration.getInstance();
				config.setOutputToConsole(false);
				IBIOMESLiteManager mng = IBIOMESLiteManager.getInstance();
				mng.generatePdf(
						TestCommon.TEST_DATA_DIR + "/output/" + amberCollections[f].substring(amberCollections[f].lastIndexOf("/")) + ".pdf", 
						amberCollections[f], 
						Software.AMBER, 
						xmlDescriptorAmber, 
						true, 
						0);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void testConvertGromacs() throws Exception
	{
		try{
			for (int f=0; f<gromacsCollections.length; f++)
			{
				System.out.println("Creating PDF summary for GROMACS experiment " + gromacsCollections[f] + "...");
				IBIOMESConfiguration config = IBIOMESConfiguration.getInstance();
				config.setOutputToConsole(false);
				IBIOMESLiteManager mng = IBIOMESLiteManager.getInstance();
				mng.generatePdf(
						TestCommon.TEST_DATA_DIR + "/output/" + gromacsCollections[f].substring(gromacsCollections[f].lastIndexOf("/")) + ".pdf", 
						gromacsCollections[f], 
						Software.GROMACS, 
						null, 
						true, 
						0);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
