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

import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.w3c.dom.Document;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.experiment.Experiment;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.experiment.XMLConverter;
import edu.utah.bmi.ibiomes.lite.test.TestCommon;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFactory;

/**
 * Tests for experiment export to PDF document
 * @author Julien Thibault
 *
 */
public class HTMLDetailsConverterTest {

	private final Logger logger = Logger.getLogger(HTMLDetailsConverterTest.class);
	private final String xslUrl = TestCommon.TEST_DATA_DIR + "/lite/lite-web/style/ibiomes_experiment_details.xsl";
	
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
			TestCommon.TEST_DATA_DIR + "/gromacs/speptide",
			TestCommon.TEST_DATA_DIR + "/gromacs/nmr1",
			TestCommon.TEST_DATA_DIR + "/gromacs/nmr2",
			TestCommon.TEST_DATA_DIR + "/gromacs/methanol",
			TestCommon.TEST_DATA_DIR + "/gromacs/water"};

	String[] namdCollections = {
			TestCommon.TEST_DATA_DIR + "/namd/namd-amber"};
	
	public HTMLDetailsConverterTest() throws Exception{
		File dir = new File(TestCommon.TEST_DATA_DIR + "/lite/lite-web/experiments/test");
		if (!dir.exists())
			dir.mkdir();
		IBIOMESConfiguration config = IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
	}
	
	public void testConvertGaussian() throws Exception
	{
		XMLConverter converter = new XMLConverter();
		try{
			for (int f=0; f<gaussianCollections.length; f++)
			{
				System.out.println("Converting Gaussian experiment " + gaussianCollections[f] + " to HTML...");
				ExperimentFactory factory = new ExperimentFactory(gaussianCollections[f]);
				Experiment exp = factory.parseDirectoryForExperimentWorkflow(Software.GAUSSIAN, null, null);
				Document doc = converter.convertExperiment(exp);
				DOMSource source = new DOMSource(doc);
				//transform XML to HTML
				TransformerFactory tFactory = TransformerFactory.newInstance();
				Transformer transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(xslUrl));
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				File outputHTMLFile = new File(TestCommon.TEST_DATA_DIR + "/lite/lite-web/experiments/test/gaussian_" + exp.getName()+ ".html");
				StreamResult result = new StreamResult(outputHTMLFile);
				transformer.transform(source, result);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public void testConvertNWChem() throws Exception
	{
		XMLConverter converter = new XMLConverter();
		
		try{
			for (int f=0; f<nwchemCollections.length; f++)
			{
				System.out.println("Converting NWChem experiment " + nwchemCollections[f] + " to HTML...");
				ExperimentFactory factory = new ExperimentFactory(nwchemCollections[f]);
				Experiment exp = factory.parseDirectoryForExperimentWorkflow(Software.NWCHEM, null, null);
				Document doc = converter.convertExperiment(exp);
				DOMSource source = new DOMSource(doc);
				//transform XML to HTML
				TransformerFactory tFactory = TransformerFactory.newInstance();
				Transformer transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(xslUrl));
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				File outputHTMLFile = new File(TestCommon.TEST_DATA_DIR + "/lite/lite-web/experiments/test/nwchem_" + exp.getName()+ ".html");
				StreamResult result = new StreamResult(outputHTMLFile);
				transformer.transform(source, result);
			}

		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void testConvertNAMD() throws Exception
	{
		XMLConverter converter = new XMLConverter();
		
		try{
			for (int f=0; f<namdCollections.length; f++)
			{
				System.out.println("Converting NAMD experiment " + namdCollections[f] + " to HTML...");
				ExperimentFactory factory = new ExperimentFactory(namdCollections[f]);
				Experiment exp = factory.parseDirectoryForExperimentWorkflow(Software.NAMD, null, null);
				Document doc = converter.convertExperiment(exp);
				DOMSource source = new DOMSource(doc);
				//transform XML to HTML
				TransformerFactory tFactory = TransformerFactory.newInstance();
				Transformer transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(xslUrl));
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				File outputHTMLFile = new File(TestCommon.TEST_DATA_DIR + "/lite/lite-web/experiments/test/nwchem_" + exp.getName()+ ".html");
				StreamResult result = new StreamResult(outputHTMLFile);
				transformer.transform(source, result);
			}

		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void testConvertGamess() throws Exception
	{
		XMLConverter converter = new XMLConverter();
		try{
			for (int f=0; f<gamessCollections.length; f++)
			{
				System.out.println("Converting GAMESS experiment " + gamessCollections[f] + " to HTML...");
				ExperimentFactory factory = new ExperimentFactory(gamessCollections[f]);
				Experiment exp = factory.parseDirectoryForExperimentWorkflow(Software.GAMESS, null, null);
				Document doc = converter.convertExperiment(exp);
				DOMSource source = new DOMSource(doc);
				//transform XML to HTML
				TransformerFactory tFactory = TransformerFactory.newInstance();
				Transformer transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(xslUrl));
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				File outputHTMLFile = new File(TestCommon.TEST_DATA_DIR + "/lite/lite-web/experiments/test/gamess_" + exp.getName()+ ".html");
				StreamResult result = new StreamResult(outputHTMLFile);
				transformer.transform(source, result);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void testConvertAmber() throws Exception
	{
		XMLConverter converter = new XMLConverter();
		try{
			for (int f=0; f<amberCollections.length; f++)
			{
				System.out.println("Converting Amber experiment " + amberCollections[f] + " to HTML...");
				ExperimentFactory factory = new ExperimentFactory(amberCollections[f]);
				Experiment exp = factory.parseDirectoryForExperimentWorkflow(Software.AMBER, null, null);
				Document doc = converter.convertExperiment(exp);
				DOMSource source = new DOMSource(doc);
				//transform XML to HTML
				TransformerFactory tFactory = TransformerFactory.newInstance();
				Transformer transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(xslUrl));
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				File outputHTMLFile = new File(TestCommon.TEST_DATA_DIR + "/lite/lite-web/experiments/test/amber_" + exp.getName()+ ".html");
				StreamResult result = new StreamResult(outputHTMLFile);
				transformer.transform(source, result);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void testConvertGromacs() throws Exception
	{
		XMLConverter converter = new XMLConverter();
		
		try{
			for (int f=0; f<gromacsCollections.length; f++)
			{
				System.out.println("Converting Gromacs experiment " + gromacsCollections[f] + " to HTML...");
				ExperimentFactory factory = new ExperimentFactory(gromacsCollections[f]);
				Experiment exp = factory.parseDirectoryForExperimentWorkflow(Software.GROMACS, null, null);
				Document doc = converter.convertExperiment(exp);
				DOMSource source = new DOMSource(doc);
				//transform XML to HTML
				TransformerFactory tFactory = TransformerFactory.newInstance();
				Transformer transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(xslUrl));
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				File outputHTMLFile = new File(TestCommon.TEST_DATA_DIR + "/lite/lite-web/experiments/test/gromacs_" + exp.getName()+ ".html");
				StreamResult result = new StreamResult(outputHTMLFile);
				transformer.transform(source, result);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
