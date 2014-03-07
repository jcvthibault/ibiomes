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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.utah.bmi.Utils;
import edu.utah.bmi.ibiomes.lite.search.IBIOMESLiteSearchRecord;
import edu.utah.bmi.ibiomes.lite.search.IBIOMESLiteSearchRecordSet;
import edu.utah.bmi.ibiomes.parse.DirectoryParsingProgressBar;
import edu.utah.bmi.ibiomes.parse.IBIOMESListener;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFactory;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFolder;
import edu.utah.bmi.ibiomes.xml.XPathReader;

/**
 * XML file containing the list of experiments currently published in iBIOMES Lite
 * @author Julien Thibault, University of Utah
 *
 */
public class XmlExperimentListFile {
	
	private String path;
	private Document xmlDoc = null;

	/**
	 * Load existing XML file or initialize new one if it doesn't exist
	 * @param path Path to file
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 * @throws XPathExpressionException 
	 */
	public XmlExperimentListFile(String path) throws IOException, ParserConfigurationException, XPathExpressionException, SAXException{
		this.path = path;
		//if doesnt exist initialize with empty <ibiomes/>
		if (!Files.exists(Paths.get(path))){
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			xmlDoc = docBuilder.newDocument();
			xmlDoc.appendChild(xmlDoc.createElement("ibiomes"));
		}
		else {
			xmlDoc = parseDocument();
			
		}
	}
	
	/**
	 * Return next available experiment ID based on current list of experiment IDs in the XML file.
	 * @return Next available ID.
	 */
	private int getNextAvailableID(){
		int id = -1;
		XPathReader xreader = new XPathReader(xmlDoc);
		//find ids
		NodeList topNodes = (NodeList)xreader.read("/ibiomes/directory", XPathConstants.NODESET);
		for (int n=0; n<topNodes.getLength(); n++)
		{
			int experimentId = Integer.parseInt(((Element)topNodes.item(n)).getAttribute("id"));
			if (experimentId>id)
				id = experimentId;
		}
		return (id+1);
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
	 * Add new experiment to XML list
	 * @param exp Experiment descriptor file
	 * @return New ID
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws XPathExpressionException 
	 */
	public int addNewExperiment(XmlExperimentFile exp) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{

		int newId = -1;
		Document xmlExperiment = exp.getXmlDocument();
		
		//if experiment already present remove older version
		XPathReader xreader = new XPathReader(xmlDoc);
		Node experimentNode = (Node)xreader.read("/ibiomes/directory[@absolutePath='"+exp.getExperimentPath()+"']", XPathConstants.NODE);
		if (experimentNode != null){
			newId = Integer.valueOf(((Element)experimentNode).getAttribute("id"));
			xmlDoc.getDocumentElement().removeChild(experimentNode);
		}
		else 
			newId = this.getNextAvailableID();
		
		Element rootElement = xmlDoc.getDocumentElement();
		
		Element docRoot = (Element)xmlExperiment.getDocumentElement().getChildNodes().item(0);
		//add ref to file ID
		docRoot.setAttribute("id", String.valueOf(String.valueOf(newId)));
		//add publisher username
		docRoot.setAttribute("publisher", System.getProperty("user.name"));
		
		//keep only AVUs node
		NodeList chilNodes = docRoot.getChildNodes();
		int nChild = chilNodes.getLength();
		for (int c=nChild-1; c>=0; c--){
			if (!((Element)chilNodes.item(c)).getTagName().equals("AVUs")){
				docRoot.removeChild(chilNodes.item(c));
			}
		}
        //append to main document
		rootElement.appendChild(xmlDoc.importNode(docRoot, true));
		
		return newId;
	}
	
	/**
	 * Transform to HTML and save to file
	 * @param outputPath Path to HTML output file.
	 * @param xslPath Path to XSL file.
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws XPathExpressionException 
	 * @throws TransformerException 
	 */
	public void saveToHTML(String outputPath, String xslPath) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, TransformerException
	{
		//XSLT transformation
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer(new javax.xml.transform.stream.StreamSource(xslPath));
		//format output
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		DOMSource source = new DOMSource(xmlDoc);
		StreamResult result = null;
		result = new StreamResult(new File(outputPath));
		//output to HTML
		transformer.transform(source, result);
		
	}

	/**
	 * Save changes to XML document in file
	 * @throws TransformerException
	 */
	public void saveXml() throws TransformerException
	{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		DOMSource source = new DOMSource(xmlDoc);
		StreamResult result = null;
		result = new StreamResult(new File(this.path));
		transformer.transform(source, result);
	}
	

	/**
	 * Get list of paths to experiments currently published in iBIOMES Lite
	 * @return List of paths
	 */
	public List<String> getExperimentList(){
		ArrayList<String> expList = new ArrayList<String>();
		NodeList experimentNodes = xmlDoc.getDocumentElement().getElementsByTagName("directory");
		for (int e=0; e<experimentNodes.getLength(); e++){
			Element expNode = (Element) experimentNodes.item(e);
			String experimentPath = expNode.getAttribute("absolutePath");
			expList.add(experimentPath);
		}
		return expList;
	}
	
	/**
	 * Generate experiment objects from XML
	 * @return List of experiments
	 * @throws Exception
	 */
	public List<ExperimentFolder> readExperiments(boolean outputToConsole) throws Exception{

		List<ExperimentFolder> experiments = new ArrayList<ExperimentFolder>();
		XPathReader xreader = new XPathReader(xmlDoc);
		NodeList experimentNodes = (NodeList) xreader.read("/ibiomes/directory", XPathConstants.NODESET);
		for (int e=0; e<experimentNodes.getLength(); e++)
		{
			String id = ((Element)experimentNodes.item(e)).getAttribute("id");
		
			String softwareContext = null;	
			Element softwareNode = (Element) xreader.read("/ibiomes/directory[@id='"+id+"']/AVUs/AVU[@id='SOFTWARE_NAME']", XPathConstants.NODE);
			if (softwareNode!=null){
				softwareContext = softwareNode.getTextContent();
			}
			String experimentPath = (String) xreader.read("/ibiomes/directory[@id='"+id+"']/@absolutePath", XPathConstants.STRING);
	
			/*System.out.println("Descriptor: " + xmlFilePath);
			System.out.println("Experiment path: " + experimentPath);
			System.out.println("Software: " + softwareContext);*/
			
			ExperimentFolder experiment = null;
			ExperimentFactory experimentFactory = new ExperimentFactory(experimentPath);
			
			List<IBIOMESListener> listeners = null;
			if (outputToConsole){
				File dir = new File(experimentPath);
				int nFiles = Utils.countNumberOfFilesInDirectory(dir.getAbsolutePath());
				listeners = new ArrayList<IBIOMESListener>();
				listeners.add((IBIOMESListener)new DirectoryParsingProgressBar(nFiles, "Parsing file"));
			}
			
			if (softwareContext!=null && softwareContext.length()>0)
				experiment = experimentFactory.parseDirectoryForExperimentWorkflowAndMetadata(softwareContext, null, listeners);
			else 
				experiment = experimentFactory.parseDirectoryForExperimentWorkflowAndMetadata(null, null, listeners);
			
			experiments.add(experiment);
		
		}
		return experiments;
	}
	
	/**
	 * Remove experiment from XML list
	 * @param experimentPath Path to experiment in file system
	 * @return ID of the experiment removed or -1 if no experiment matches.
	 */
	public int removeExperiment(String experimentPath) {
		int experimentId = -1;
		XPathReader xreader = new XPathReader(xmlDoc);
		Element experimentNode = (Element) xreader.read("/ibiomes/directory[@absolutePath='"+experimentPath+"']", XPathConstants.NODE);
		if (experimentNode!=null){
			experimentId = Integer.valueOf(experimentNode.getAttribute("id"));
			xmlDoc.getDocumentElement().removeChild(experimentNode);
		}
		return experimentId;
	}
	
	/**
	 * Clean content of XML
	 * @throws IOException
	 * @throws ParserConfigurationException 
	 */
	public void clean() throws IOException, ParserConfigurationException 
	{
		this.xmlDoc = null;
		Files.deleteIfExists(Paths.get(this.path));

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		xmlDoc = docBuilder.newDocument();
		xmlDoc.appendChild(xmlDoc.createElement("ibiomes"));
	}
	
	/**
	 * Search experiments by keyword
	 * @param keywords List of keywords
	 * @return List of matching experiments
	 */
	public IBIOMESLiteSearchRecordSet search(String[] keywords) {
		IBIOMESLiteSearchRecordSet recordSet = new IBIOMESLiteSearchRecordSet();
		
		XPathReader xreader = new XPathReader(xmlDoc);
		NodeList experimentNodes = (NodeList) xreader.read("/ibiomes/directory", XPathConstants.NODESET);
		for (int e=0; e<experimentNodes.getLength(); e++)
		{
			String id = ((Element)experimentNodes.item(e)).getAttribute("id");
			String experimentPath = (String) xreader.read("/ibiomes/directory[@id='"+id+"']/@absolutePath", XPathConstants.STRING);
			NodeList avuNodes = (NodeList) xreader.read("/ibiomes/directory[@id='"+id+"']/AVUs/AVU", XPathConstants.NODESET);
			ArrayList<String> avuList = new ArrayList<String>();
			for (int a=0; a<avuNodes.getLength(); a++){
				Element avuNode = (Element)avuNodes.item(a);
				String avuValue = avuNode.getTextContent();				
				if (avuValue!=null && avuValue.length()>0){
					avuList.add(avuValue.toLowerCase());
				}
			}
			boolean found = true;
			for (int k=0; k<keywords.length; k++){
				String keyword = keywords[k].trim().toLowerCase();
				if (!keyword.contains("%")){
					//direct match
					if (!avuList.contains(keyword)){
						found = false;
						break;
					}
				}
				else //use regular expression
				{
					boolean foundRegex = false;
					keyword = keyword.replaceAll("%", ".*");
					for (String avu : avuList){
						if (avu.matches(keyword)){
							foundRegex = true;
							break;
						}
					}
					if (!foundRegex){
						found = false;
						break;
					}
				}
			}
			if (found){
				IBIOMESLiteSearchRecord record = new IBIOMESLiteSearchRecord(id, experimentPath, id);
				recordSet.add(record);
			}
		}
		return recordSet;
	}

}
