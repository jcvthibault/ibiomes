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

package edu.utah.bmi.ibiomes.lite;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import edu.utah.bmi.Utils;
import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.metadata.PlatformMetadata;
import edu.utah.bmi.ibiomes.parse.DirectoryParsingProgressBar;
import edu.utah.bmi.ibiomes.parse.IBIOMESListener;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFactory;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFolder;
import edu.utah.bmi.ibiomes.xml.XPathReader;

/**
 * XML file containing the representation of an experiment (metadata and system information).
 * @author Julien Thibault, University of Utah
 *
 */
public class XmlExperimentFile {

	private String path;
	private Document xmlDoc;
	private String experimentPath;

	public XmlExperimentFile(String path) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		File file = new File(path);
		this.path = file.getCanonicalPath();
		this.experimentPath = this.path.substring(0, this.path.lastIndexOf(IBIOMESLiteManager.PATH_FOLDER_SEPARATOR));
		this.xmlDoc = this.parseDocument();
	}
	
	/**
	 * Get XML document
	 * @return XML document
	 */
	public Document getXmlDocument(){
		return this.xmlDoc;
	}
	
	public String getExperimentPath(){
		return this.experimentPath;
	}
	
	/**
	 * Parse XML file
	 * @return XML document
	 * @throws XPathExpressionException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private Document parseDocument() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(this.path);
		doc = Utils.normalizeXmlDoc(doc);
		return doc;
	}
	
	/**
	 * Transform to HTML and save to file
	 * @param outputPath Path to output file.
	 * @param xslPath Path to XSL file.
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws XPathExpressionException 
	 * @throws TransformerException 
	 */
	public void saveToHTML(String outputPath, String xslPath) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, TransformerException
	{
		Document doc = this.parseDocument();
		//XSLT transformation
		net.sf.saxon.TransformerFactoryImpl tFactory = new net.sf.saxon.TransformerFactoryImpl();
		Transformer transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(xslPath));
		//format output
		transformer.setOutputProperty(OutputKeys.INDENT, "no");
		//transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		DOMSource source = new DOMSource(doc);
		StreamResult result = null;
		result = new StreamResult(new File(outputPath));
		//output to HTML
		transformer.transform(source, result);
	}
	
	/**
	 * Generate experiment object from XML
	 * @return Parsed experiment folder
	 * @throws Exception
	 */
	public ExperimentFolder readExperiment() throws Exception {

		String softwareContext = null;
		DirectoryStructureDescriptor metadataRuleFile = null;

		//parse XML to get software type
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(this.path);
		
		//check if the experiment directory was parsed using a rule file
		Element rootElt = doc.getDocumentElement();
		if (rootElt.hasAttribute("ruleFile"))
			metadataRuleFile = new DirectoryStructureDescriptor(rootElt.getAttribute("ruleFile"));
		
		//check which software was used
		XPathReader xreader = new XPathReader(doc);
		Element softwareNode = (Element) xreader.read("/ibiomes/directory/AVUs/AVU[@id='"+PlatformMetadata.SOFTWARE_NAME+"']", XPathConstants.NODE);
		if (softwareNode!=null){
			softwareContext = softwareNode.getFirstChild().getNodeValue();
		}
		String experimentPath = (String) xreader.read("/ibiomes/directory/@absolutePath", XPathConstants.STRING);
		
		//parse experiment directory to regenerate metadata
		ExperimentFolder experiment = null;
		ExperimentFactory experimentFactory = new ExperimentFactory(experimentPath);
		
		//create listener for progress bar
		List<IBIOMESListener> listeners = null;
		if (IBIOMESConfiguration.getInstance().isOutputToConsole()){
			File dir = new File(experimentPath);
			int nFiles = Utils.countNumberOfFilesInDirectory(dir.getAbsolutePath());
			listeners = new ArrayList<IBIOMESListener>();
			listeners.add((IBIOMESListener)new DirectoryParsingProgressBar(nFiles, "Parsing file"));
		}
		
		if (softwareContext!=null && softwareContext.length()>0)
			experiment = experimentFactory.parseDirectoryForExperimentWorkflowAndMetadata(softwareContext, metadataRuleFile, listeners);
		else 
			experiment = experimentFactory.parseDirectoryForExperimentWorkflowAndMetadata(null, metadataRuleFile, listeners);
		
		return experiment;
	}
}
