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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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

import org.jfree.chart.JFreeChart;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.utah.bmi.Utils;
import edu.utah.bmi.ibiomes.catalog.MetadataLookup;
import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.experiment.XMLConverter;
import edu.utah.bmi.ibiomes.graphics.plot.ColumnDataFile;
import edu.utah.bmi.ibiomes.graphics.plot.PlotGenerator;
import edu.utah.bmi.ibiomes.io.Locker;
import edu.utah.bmi.ibiomes.lite.search.IBIOMESLiteSearchRecordSet;
import edu.utah.bmi.ibiomes.metadata.FileMetadata;
import edu.utah.bmi.ibiomes.metadata.IBIOMESFileGroup;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.CSVFile;
import edu.utah.bmi.ibiomes.parse.DirectoryParsingProgressBar;
import edu.utah.bmi.ibiomes.parse.IBIOMESListener;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFactory;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFolder;
import edu.utah.bmi.ibiomes.xml.XPathReader;

/**
 * Utility class to find iBIOMES descriptors in the local resource, 
 * and copy the necessary files (xml, pictures) to the public html folder for web display.
 *  
 * @author Julien Thibault, University of Utah
 *
 */
public class IBIOMESLiteManager {

	public final static String PATH_FOLDER_SEPARATOR  = (Utils.isWindows() ? "\\" : "/");
	public final static String PATH_FOLDER_SEPARATOR_REGEX  = (Utils.isWindows() ? "\\\\" : "/");
	public final static String IBIOMES_LITE_VERSION = "09-16-2017";
	
	private final static String IBIOMES_EXPERIMENT_SUMMARY_XSL_PATH   = "style"+PATH_FOLDER_SEPARATOR+"experiment_summary.xsl";
	private final static String IBIOMES_EXPERIMENT_WORKFLOW_XSL_PATH  = "style"+PATH_FOLDER_SEPARATOR+"experiment_workflow.xsl";
	private final static String IBIOMES_EXPERIMENT_FILE_TREE_XSL_PATH = "style"+PATH_FOLDER_SEPARATOR+"experiment_files.xsl";
	private final static String IBIOMES_EXPERIMENT_RUNS_XSL_PATH      = "style"+PATH_FOLDER_SEPARATOR+"experiment_runs.xsl";
	private final static String IBIOMES_EXPERIMENT_SET_XSL_PATH       = "style"+PATH_FOLDER_SEPARATOR+"experiment_list.xsl";
	
	private final static String IBIOMES_LITE_EXPERIMENT_DIR = "experiments";
	private final static String IBIOMES_LITE_XML_INDEX 		= "ibiomes.compiled.xml";
	private final static String IBIOMES_LITE_HTML_INDEX 	= "index.html";
	
	private final static String METADATA_LOOKUP_INDEX_PATH = "data"+PATH_FOLDER_SEPARATOR+"metadata-attr";

	public static final String IBIOMES_DESC_FILE_TREE_FILE_NAME = ".ibiomes.xml";
	public static final String IBIOMES_DESC_WORKFLOW_FILE_NAME 	= ".ibiomes-details.xml";
	public static final String IBIOMES_PARSE_CONFIG_FILE_NAME 	= ".ibiomes-parse.config";
	public static final String IBIOMES_LITE_DATA_DIR 			= "data";
	public static final String IBIOMES_TEMP_DIR 				= "temp";

	public static final String PARSER_CONFIG_PROPERTY_RULE_SET_FILE 	= "parsingRuleSetFilePath";
	public static final String PARSER_CONFIG_PROPERTY_SOFTWARE_CONTEXT 	= "softwareContext";
	private static final String PARSER_CONFIG_PROPERTY_DEPTH_INDEPENDENCE = "depthForIndependentGroups";
	
	private final static int PLOT_WIDTH = 600;
	private final static int PLOT_HEIGHT = 480;
	
	/**
	 * Environment variable that specifies the path to the web folder.
	 */
	public final static String IBIOMES_LITE_WEBDIR_ENV_VAR = "IBIOMES_LITE_WEBDIR";
	/**
	 * Environment variable that sets the path to the iBIOMES libraries.
	 */
	public final static String IBIOMES_HOME_ENV_VAR = "IBIOMES_HOME";

	
	private String publicHtmlFolder; 
	private String ibiomesHomeFolder; 
	private XmlExperimentListFile experimentListXml;
	
	private static IBIOMESLiteManager ibiomesLite = null;
	private boolean outputToConsole = true;
	
	/**
	 * Create instance of iBIOMES Lite manager by specifying the location of the target web folder.
	 * @param dirPath Path to the iBIOMES Lite web folder
	 * @throws Exception 
	 */
	private IBIOMESLiteManager(String webDirPath) throws Exception
	{
		//set default location for web directory if necessary
		if (webDirPath == null || webDirPath.length()==0) {
			webDirPath = System.getenv(IBIOMES_LITE_WEBDIR_ENV_VAR);
			if (webDirPath == null || webDirPath.length()==0)
				throw new Exception("Environment variable '"+IBIOMES_LITE_WEBDIR_ENV_VAR+"' was not set");
		}
		File dir = new File(webDirPath);
		if (webDirPath!= null && dir.exists()){
			publicHtmlFolder = dir.getCanonicalPath();
			experimentListXml = new XmlExperimentListFile(publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_LITE_XML_INDEX);
		}
		else throw new FileNotFoundException("The iBIOMES Lite web folder could not be located ('"+webDirPath+"'?)");
		
		//check that $IBIOMES_HOME is set
		ibiomesHomeFolder = System.getenv(IBIOMES_HOME_ENV_VAR);
		if (ibiomesHomeFolder == null || ibiomesHomeFolder.length()==0) {
			throw new FileNotFoundException("Environment variable '"+IBIOMES_HOME_ENV_VAR+"' was not set");
		}
		//load iBIOMES configuration
		IBIOMESConfiguration ibiomesConfig = IBIOMESConfiguration.getInstance();
		outputToConsole = ibiomesConfig.isOutputToConsole();
	}
	
	/**
	 * Get new iBIOMES Lite manager with default settings or current instance if it already exists.
	 * @throws Exception 
	 */
	public static IBIOMESLiteManager getInstance() throws Exception{
		if (ibiomesLite == null)
			ibiomesLite = new IBIOMESLiteManager(null);
		return ibiomesLite;
	}
	
	/**
	 * Get new iBIOMES Lite manager with default settings or current instance if forceLoad flag is set to false.
	 * @param forceReload Flag used to force the creation of a new instance
	 * @throws Exception 
	 */
	public static IBIOMESLiteManager getInstance(boolean forceReload) throws Exception{
		if ( (ibiomesLite == null) ||forceReload){
			ibiomesLite = new IBIOMESLiteManager(null);
		}
		return ibiomesLite;
	}
	
	/**
	 * Create instance of iBIOMES Lite manager by specifying the location of the web directory.
	 * @param webDirPath Path to the iBIOMES Lite web folder
	 * @throws Exception 
	 */
	public static IBIOMESLiteManager getInstance(String webDirPath, boolean forceReload) throws Exception{
		if ( (ibiomesLite == null) ||forceReload){
			ibiomesLite = new IBIOMESLiteManager(webDirPath);
		}
		return ibiomesLite;
	}
	
	/**
	 * Get path to iBIOMES Lite web folder
	 * @return path to iBIOMES Lite web folder
	 */
	public String getWebDirLocation() {
		return publicHtmlFolder;
	}
	
	/**
	 * Publish experiment. Parse corresponding directory if descriptor files does not exist yet.
	 * @param experimentDirPath Path to experiment directory
	 * @throws Exception 
	 */
	public void publishExperiment(String experimentDirPath) throws Exception{
		publishExperiment(experimentDirPath, null, null, false, 0, null);
	}

	/**
	 * Publish experiment. Parse corresponding directory if descriptor files does not exist yet.
	 * @param experimentDirPath Path to experiment directory
	 * @param software Software context
	 * @param xmlDescPath Path to XML file containing metadata generation rules
	 * @throws Exception
	 */
	public void publishExperiment(String experimentDirPath, String software, String xmlDescPath, boolean isForceDescUpdate) throws Exception {
		this.publishExperiment(experimentDirPath, software, xmlDescPath, isForceDescUpdate, 0, null);
	}
	
	/**
	 * Publish experiment. Parse corresponding directory if descriptor files does not exist yet.
	 * @param experimentDirPath Path to experiment directory
	 * @param software Software context
	 * @param xmlDescPath Path to XML file containing metadata generation rules
	 * @param depth 
	 * @throws Exception
	 */
	public void publishExperiment(String experimentDirPath, String software, String xmlDescPath, boolean isForceDescUpdate, int depth) throws Exception {
		this.publishExperiment(experimentDirPath, software, xmlDescPath, isForceDescUpdate, depth, null);
	}
	
	/**
	 * Publish experiment. Parse corresponding directory if descriptor files does not exist yet.
	 * @param experimentDirPath Path to experiment directory
	 * @param software Software context
	 * @param xmlDescPath Path to XML file containing metadata generation rules
	 * @param depth 
	 * @throws Exception 
	 */
	public void publishExperiment(String experimentDirPath, String software, String xmlDescPath, boolean isForceDescUpdate, int depth, String externalUrl) throws Exception
	{
		Locker experimentLocker = null;
		Locker webdirLocker = null;
		try{
			//lock directory being parsed
			experimentLocker = new Locker(experimentDirPath);
			webdirLocker = new Locker(publicHtmlFolder);
			//check lock ... 
			boolean isLocked = experimentLocker.lock();
			if (isLocked){
				File experimentDesc = new File(experimentDirPath + PATH_FOLDER_SEPARATOR + IBIOMES_DESC_FILE_TREE_FILE_NAME);
				if (!experimentDesc.exists() || isForceDescUpdate){
					//parse directory
					this.parse(experimentDirPath, software, xmlDescPath, depth, externalUrl);
				}
				else{
					if (outputToConsole)
						System.out.println("Using existing descriptors (use -f option to force parsing)...");					
				}
				//lock web directory for updates
				if (outputToConsole)
					System.out.println("Wating for other users to finish publishing...");
				isLocked = webdirLocker.waitAndLock();
				
				//update XML file with list of experiments
				XmlExperimentFile experimentXml = new XmlExperimentFile(experimentDesc.getAbsolutePath());
				int id = experimentListXml.addNewExperiment(experimentXml);
				experimentListXml.saveXml();
				
				//create new directory for this experiment
				String liteDirPath = publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_LITE_EXPERIMENT_DIR + PATH_FOLDER_SEPARATOR + id;
				if (Files.exists(Paths.get(liteDirPath)))
					Utils.removeDirectoryRecursive(Paths.get(liteDirPath));
				Files.createDirectory(Paths.get(liteDirPath));
		
				//copy experiment XML descriptors
				String newFileTreeXmlPath = liteDirPath + PATH_FOLDER_SEPARATOR + "index.xml";
				String newWorkflowXmlPath = liteDirPath + PATH_FOLDER_SEPARATOR + "index-details.xml";
				Files.copy(Paths.get(experimentDesc.getAbsolutePath()), Paths.get(newFileTreeXmlPath), StandardCopyOption.REPLACE_EXISTING);
				Files.copy(Paths.get(experimentDirPath + PATH_FOLDER_SEPARATOR + IBIOMES_DESC_WORKFLOW_FILE_NAME), Paths.get(newWorkflowXmlPath), StandardCopyOption.REPLACE_EXISTING);
				
				//pull data files (csv, pdb, and images)
				String dataDirPath = liteDirPath + PATH_FOLDER_SEPARATOR + IBIOMES_LITE_DATA_DIR;
				Files.createDirectory(Paths.get(dataDirPath));
				newFileTreeXmlPath = "file:///" + newFileTreeXmlPath.replaceAll("\\\\", "/");
				this.pullDataFilesForExperiment(newFileTreeXmlPath, newWorkflowXmlPath, liteDirPath + PATH_FOLDER_SEPARATOR + IBIOMES_LITE_DATA_DIR);
				
				//generate HTML pages
				if (outputToConsole)
					System.out.println("Generating HTML...");
				//experiment summary
				this.transformXmlToHtml(
						newWorkflowXmlPath,
						publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_LITE_EXPERIMENT_DIR +PATH_FOLDER_SEPARATOR + id + PATH_FOLDER_SEPARATOR+ "index.html",
						publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_EXPERIMENT_SUMMARY_XSL_PATH);
				//experiment workflow (tree view)
				this.transformXmlToHtml(
						newWorkflowXmlPath,
						publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_LITE_EXPERIMENT_DIR +PATH_FOLDER_SEPARATOR + id + PATH_FOLDER_SEPARATOR+ "details.html",
						publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_EXPERIMENT_WORKFLOW_XSL_PATH);
				//experiment runs (timings, resources)
				this.transformXmlToHtml(
						newWorkflowXmlPath,
						publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_LITE_EXPERIMENT_DIR +PATH_FOLDER_SEPARATOR + id + PATH_FOLDER_SEPARATOR+ "runs.html",
						publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_EXPERIMENT_RUNS_XSL_PATH);
				//experiment file browser
				this.transformXmlToHtml(
						newFileTreeXmlPath,
						publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_LITE_EXPERIMENT_DIR +PATH_FOLDER_SEPARATOR + id + PATH_FOLDER_SEPARATOR+ "files.html",
						publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_EXPERIMENT_FILE_TREE_XSL_PATH);
				//list of experiments
				experimentListXml.saveToHTML(
						publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_LITE_HTML_INDEX, 
						publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_EXPERIMENT_SET_XSL_PATH);
			}
			else {
				System.out.println("Could not lock "+experimentDirPath+" for parsing.");
				System.out.println("Another user might be publishing this experiment right now. If you believe this is not the case, delete the "+Locker.LOCK_FILE_NAME+" file in the experiment directory and re-try.");
			}

			//unlock directories
			experimentLocker.unlock();
			webdirLocker.unlock();
			
		}
		catch(Exception e){
			if (experimentLocker!=null) experimentLocker.unlock();
			if (webdirLocker!=null) webdirLocker.unlock();
			throw e;
		}
	}
	
	/**
	 * Parse experiment directory and generate XML file
	 * @param experimentDirPath Path to experiment directory
	 * @param software Software context
	 * @param xmlDescPath Path to XML file containing metadata generation rules
	 * @param depth 
	 * @throws Exception
	 */
	public ExperimentFolder parse(String experimentDirPath, String software, String xmlDescPath, int depth) throws Exception{
		return this.parse(experimentDirPath, software, xmlDescPath, depth, null);
	}
	
	/**
	 * Parse experiment directory and generate XML file
	 * @param experimentDirPath Path to experiment directory
	 * @param software Software context
	 * @param xmlDescPath Path to XML file containing metadata generation rules
	 * @param depth 
	 * @param externalUrl
	 * @throws Exception
	 */
	public ExperimentFolder parse(String experimentDirPath, String software, String xmlDescPath, int depth, String externalUrl) throws Exception{
		//read descriptor file if exists
		DirectoryStructureDescriptor desc = null; 
		if (xmlDescPath != null && xmlDescPath.length()!=0){
			if (outputToConsole)
				System.out.println("Loading parsing rules from XML descriptor...");
			desc = new DirectoryStructureDescriptor(xmlDescPath);
		}

		ExperimentFolder experimentFolder = null;
		File dir = new File(experimentDirPath);
		int nFiles = Utils.countNumberOfFilesInDirectory(dir.getAbsolutePath());
		//System.out.println("Parsing directory ("+nFiles+" files)...");
		experimentDirPath = dir.getCanonicalPath();
		ExperimentFactory expFactory = new ExperimentFactory(experimentDirPath, depth);
		
		//create listeners for progress bar
		List<IBIOMESListener> listeners = null;
		if (outputToConsole){
			listeners = new ArrayList<IBIOMESListener>();
			listeners.add((IBIOMESListener)new DirectoryParsingProgressBar(nFiles, "Parsing file"));
		}
		//parse	directory
		experimentFolder = expFactory.parseDirectoryForExperimentWorkflowAndMetadata( software, desc, listeners, externalUrl);
		//create XML descriptor
		if (outputToConsole)
			System.out.println("Saving experiment file tree descriptor ("+IBIOMESLiteManager.IBIOMES_DESC_FILE_TREE_FILE_NAME+")...");
		experimentFolder.getFileDirectory().storeToXML(experimentDirPath + PATH_FOLDER_SEPARATOR + IBIOMESLiteManager.IBIOMES_DESC_FILE_TREE_FILE_NAME);
		//create detailed XML description of experiment
		if (outputToConsole)
			System.out.println("Saving experiment workflow ("+IBIOMESLiteManager.IBIOMES_DESC_WORKFLOW_FILE_NAME+")...");
		this.generateDetailedXML(experimentFolder, experimentDirPath + PATH_FOLDER_SEPARATOR + IBIOMESLiteManager.IBIOMES_DESC_WORKFLOW_FILE_NAME);
		//create property file to store the parsing configuration
		if (outputToConsole)
			System.out.println("Saving parser configuration ("+IBIOMESLiteManager.IBIOMES_PARSE_CONFIG_FILE_NAME+")...");
		this.createParsingConfigFile(experimentDirPath + PATH_FOLDER_SEPARATOR + IBIOMESLiteManager.IBIOMES_PARSE_CONFIG_FILE_NAME, xmlDescPath, software, depth);
		
		return experimentFolder;
	}
	
	/**
	 * Pull data files (pdb and images) for a given experiment
	 * @param fileTreeXmlPath Path to XML file representing the project file tree
	 * @param workflowXmlPath Path to XML file representing the experiment workflow
	 * @param dataDirPath Path to directory used to store data files
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException 
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 */
	private void pullDataFilesForExperiment(String fileTreeXmlPath, String workflowXmlPath, String dataDirPath) throws SAXException, IOException, XPathExpressionException, ParserConfigurationException, TransformerException {
		
		if (outputToConsole)
			System.out.println("Copying analysis data files...");
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document fileTreeDoc = docBuilder.parse(fileTreeXmlPath);
		fileTreeDoc = Utils.normalizeXmlDoc(fileTreeDoc);
		
		Element fileTreeRootElt = (Element)fileTreeDoc.getDocumentElement().getChildNodes().item(0);
		String dirPath = fileTreeRootElt.getAttribute("absolutePath");
				
		XPathReader xreader = new XPathReader(fileTreeDoc);
		
		//load XML representation of experiment workflow
		Document docWorkflow = docBuilder.parse(workflowXmlPath);
		docWorkflow = Utils.normalizeXmlDoc(docWorkflow);
		Element workflowRootElt = (Element)docWorkflow.getDocumentElement();
		
		//find main structure for display in Jmol
		Element jmolElt = pullJmolFile(fileTreeDoc, fileTreeRootElt, xreader, dataDirPath, dirPath);
		if (jmolElt!=null)
			workflowRootElt.appendChild(docWorkflow.importNode(jmolElt, true));
		
		//find analysis data
		NodeList matchingFiles = (NodeList)xreader.read(
				"//file[AVUs/AVU[@id='"+FileMetadata.FILE_CLASS+"' and text()='"+FileMetadata.FILE_CLASS_ANALYSIS.toUpperCase()+"']]", 
				XPathConstants.NODESET);
		
		//add publication information
		//Element dirNode = (Element)fileTreeDoc.getDocumentElement().getFirstChild();
		//dirNode.setAttribute("publisher", workflowRootElt.getAttribute("publisher"));
		//dirNode.setAttribute("publicationDate", workflowRootElt.getAttribute("publicationDate"));
		
		//analysis data
		if (matchingFiles != null && matchingFiles.getLength()>0)
		{	
			Element dataElt = docWorkflow.createElement("analysis");
			workflowRootElt.appendChild(dataElt);
			
			Element imgElt = docWorkflow.createElement("images");
			Element pdbElt = docWorkflow.createElement("structures");
			Element csvElts = docWorkflow.createElement("spreadsheets");
			Element otherDataElts = docWorkflow.createElement("unknowns");
			
			dataElt.appendChild(imgElt);
			dataElt.appendChild(csvElts);
			dataElt.appendChild(pdbElt);
			dataElt.appendChild(otherDataElts);
			
			PlotGenerator plotTool = new PlotGenerator();
			
			for (int f=0; f<matchingFiles.getLength(); f++)
			{
				Element fileNode = (Element)matchingFiles.item(f);
				String dataFilePath = fileNode.getAttribute("absolutePath");
				//copy file
				String dataFileNewName = dataFilePath.substring(dirPath.length()+1).replaceAll(PATH_FOLDER_SEPARATOR_REGEX, "_");
				String dataFileDestPath = dataDirPath + PATH_FOLDER_SEPARATOR + dataFileNewName;
				Files.copy(Paths.get(dataFilePath), Paths.get(dataFileDestPath), StandardCopyOption.REPLACE_EXISTING);
				//set read permissions
				if (!Utils.isWindows()){
					HashSet<PosixFilePermission> permissions = new HashSet<PosixFilePermission>();
					permissions.add(PosixFilePermission.OWNER_READ);
					permissions.add(PosixFilePermission.OWNER_WRITE);
					permissions.add(PosixFilePermission.OWNER_EXECUTE);
					permissions.add(PosixFilePermission.GROUP_READ);
					permissions.add(PosixFilePermission.OTHERS_READ);
					Files.setPosixFilePermissions(Paths.get(dataFileDestPath), permissions);
				}
				//read file AVUs
				NodeList avuNodes = (NodeList)xreader.read("//file[@absolutePath='"+dataFilePath+"']/AVUs/AVU", XPathConstants.NODESET);
				MetadataAVUList avuList = new MetadataAVUList();
				if (avuNodes != null){
					for (int a=0; a<avuNodes.getLength(); a++)	{
						Element avuNode = (Element)avuNodes.item(a);
						avuList.add(
								new MetadataAVU(avuNode.getAttribute("id").toUpperCase(),
								avuNode.getFirstChild().getNodeValue())
							);
					}
				}
				
				//add reference in XML doc
				String description = avuList.getValue(FileMetadata.FILE_DESCRIPTION);
				String format = fileNode.getAttribute("format");
				if (IBIOMESFileGroup.isJmolFile(format)){
					Element jmolFileElt = docWorkflow.createElement("structure");
					jmolFileElt.setAttribute("path", dataFileNewName);
					if (description!=null && description.length()>0)
						jmolFileElt.setAttribute("description", description);
					pdbElt.appendChild(jmolFileElt);
				}
				else if (format.equals(LocalFile.FORMAT_CSV)){
					Element csvElt = docWorkflow.createElement("spreadsheet");
					csvElt.setAttribute("path", dataFileNewName);
					if (description!=null && description.length()>0)
						csvElt.setAttribute("description", description);
					csvElts.appendChild(csvElt);
					//try to generate plot and save image
					try{
						String imgPath = dataFileNewName + "_plot.png";
						String plotType = generatePlotForCSV(plotTool, dataFileDestPath, avuList, dataFileDestPath + "_plot", "png");
						csvElt.setAttribute("plotPath", imgPath);
						if (outputToConsole){
							if (plotType==null)
								plotType = "";
							else plotType += " ";
							System.out.println("\t"+plotType+"plot generated for "+ dataFileNewName);
						}
						
					} catch (Exception e){
						if (outputToConsole)
							System.out.println("Warning: Plot for '"+ dataFileDestPath + "' could not be generated.");
						try {
							if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
								e.printStackTrace();
						} catch (Exception e1) {
						}
					}
				}
				else if (IBIOMESFileGroup.isImageFile(format)){
					Element imgFileElt = docWorkflow.createElement("image");
					imgFileElt.setAttribute("path", dataFileNewName);
					if (description!=null && description.length()>0)
						imgFileElt.setAttribute("description", description);
					imgElt.appendChild(imgFileElt);
				}
				else{
					Element otherFileElt = docWorkflow.createElement("unknown");
					otherFileElt.setAttribute("path", dataFileNewName);
					if (description!=null && description.length()>0)
						otherFileElt.setAttribute("description", description);
					imgElt.appendChild(otherDataElts);
				}
			}
		}
		
		//update XML files
		File outputXmlAvusFile = new File(fileTreeXmlPath);
		if (outputXmlAvusFile.exists())
			outputXmlAvusFile.delete();
		
		File outputXmlWorkflowFile = new File(workflowXmlPath);
		if (outputXmlWorkflowFile.exists())
			outputXmlWorkflowFile.delete();
		
		net.sf.saxon.TransformerFactoryImpl tFactory = new net.sf.saxon.TransformerFactoryImpl();
		Transformer transformer = tFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		
		DOMSource source = new DOMSource(fileTreeDoc);
		StreamResult result = null;
		result = new StreamResult(fileTreeXmlPath);
		transformer.transform(source, result);
		
		source = new DOMSource(docWorkflow);
		result = null;
		result = new StreamResult(outputXmlWorkflowFile);
		transformer.transform(source, result);
	}
	
	/**
	 * Copy file that will displayed in Jmol
	 * @param doc XML document
	 * @param rootElt Root element
	 * @param xreader XPath reader for the document
	 * @param dataDirPath Path to directory that contains analysis data
	 * @param dirPath Path to experiment directory
	 * @return XML element for Jmol data
	 * @throws IOException 
	 */
	private Element pullJmolFile(Document doc, Node rootElt, XPathReader xreader, String dataDirPath, String dirPath) throws IOException
	{	
		Element jmolElt = doc.createElement("jmol");
		
		String mainStructureRelPath = (String)xreader.read("ibiomes/directory/AVUs/AVU[@id='MAIN_3D_STRUCTURE_FILE']", XPathConstants.STRING);
		if (mainStructureRelPath!=null && mainStructureRelPath.length()>0)
		{
			String dataFileNewName = mainStructureRelPath.replaceAll(PATH_FOLDER_SEPARATOR_REGEX, "_");
			String dataFileDestPath = dataDirPath + PATH_FOLDER_SEPARATOR + dataFileNewName;
			Files.copy(Paths.get(dirPath + PATH_FOLDER_SEPARATOR + mainStructureRelPath), Paths.get(dataFileDestPath), StandardCopyOption.REPLACE_EXISTING);
			//set read permissions
			if (!Utils.isWindows()){
				Set<PosixFilePermission> permissions = new HashSet<PosixFilePermission>();
				permissions.add(PosixFilePermission.OWNER_READ);
				permissions.add(PosixFilePermission.OWNER_WRITE);
				permissions.add(PosixFilePermission.OWNER_EXECUTE);
				permissions.add(PosixFilePermission.GROUP_READ);
				permissions.add(PosixFilePermission.OTHERS_READ);
				Files.setPosixFilePermissions(Paths.get(dataFileDestPath), permissions);
			}
			jmolElt.setAttribute("path", dataFileNewName);
			jmolElt.setAttribute("name", mainStructureRelPath);
	
			NodeList avuNodes = (NodeList)xreader.read("//file[@absolutePath='"+dirPath + PATH_FOLDER_SEPARATOR + mainStructureRelPath+"']/AVUs/AVU", XPathConstants.NODESET);
			MetadataAVUList avuList = parseMetadata(avuNodes);
			String description = avuList.getValue(FileMetadata.FILE_DESCRIPTION);
			if (description!=null && description.length()>0)
				jmolElt.setAttribute("description", description);
			rootElt.appendChild(jmolElt);
			
			return jmolElt;
		}
		else return null;
	}
	
	private MetadataAVUList parseMetadata(NodeList avuNodes){
		MetadataAVUList avuList = new MetadataAVUList();
		if (avuNodes != null){
			for (int a=0; a<avuNodes.getLength(); a++)	{
				Element avuNode = (Element)avuNodes.item(a);
				avuList.add(
						new MetadataAVU(avuNode.getAttribute("id").toUpperCase(),
						avuNode.getFirstChild().getNodeValue())
					);
			}
		}
		return avuList;
	}
	
	/**
	 * Generate plot image for given CSV file
	 * @param plotTool Plot generator
	 * @param csvPath Path to CSV file to plot
	 * @param avuList List of metadata for the file
	 * @param imagePath Path to the output image
	 * @param format Format of the image
	 * @return type of the plot
	 * @throws Exception 
	 */
	public static String generatePlotForCSV(PlotGenerator plotTool, String csvPath, MetadataAVUList avuList, String imagePath, String format) throws Exception
	{
		String selectedChartType = avuList.getValue(CSVFile.PREFERRED_PLOT_TYPE);
		String title = avuList.getValue(FileMetadata.FILE_DESCRIPTION);
		String units = avuList.getValue(CSVFile.DATA_UNITS);
		String axisLabels = avuList.getValue(CSVFile.DATA_LABELS);
		String logScaleXStr = avuList.getValue(CSVFile.SCALE_LOG_X).trim().toLowerCase();
		String logScaleYStr = avuList.getValue(CSVFile.SCALE_LOG_Y).trim().toLowerCase();
		boolean logScaleX = logScaleXStr.matches("(true)|(yes)");
		boolean logScaleY = logScaleYStr.matches("(true)|(yes)");
		String seriesLabelList = avuList.getValue(CSVFile.SERIES_LABELS);
		String[] seriesLabels = null;
		if (seriesLabelList!=null && seriesLabelList.length()>0){
			seriesLabels = seriesLabelList.split("\\,");
		}
		
		//parse axis labels
		String[] labelValues = null;
		String[] unitsValues = null;
		String xTitle=null, yTitle=null, zTitle=null;
		String xUnit=null, yUnit=null, zUnit=null;
		
		if (axisLabels != null){
			labelValues = axisLabels.split("\\,");
			if (labelValues.length>0) xTitle =  labelValues[0];
			if (labelValues.length>1) yTitle =  labelValues[1];
			if (labelValues.length>2) zTitle =  labelValues[2];
		}
		if (xTitle == null) xTitle = "x";
		if (yTitle == null) yTitle = "y";
		if (zTitle == null) zTitle = "z";
		
		//parse units
		if (units != null){
			unitsValues = units.split(",");
			if (unitsValues.length>0) xUnit =  unitsValues[0];
			if (unitsValues.length>1) yUnit =  unitsValues[1];
			if (unitsValues.length>2) zUnit =  unitsValues[2];
		}
		if (xUnit != null && xUnit.length()>0)
			xTitle = xTitle + " ("+ xUnit +")";
		if (yUnit != null && yUnit.length()>0)
			yTitle = yTitle + " ("+ yUnit +")";
		if (zUnit != null && zUnit.length()>0)
			zTitle = zTitle + " ("+ zUnit +")";
		
		ColumnDataFile csv = new ColumnDataFile(new File(csvPath));
		//create plot
		JFreeChart chart = plotTool.createPlot(csv, selectedChartType, title, xTitle, yTitle, zTitle, seriesLabels, logScaleX, logScaleY);
		//save as image
		plotTool.createImage(chart, PLOT_WIDTH, PLOT_HEIGHT, imagePath, format);
		
		return selectedChartType;
	}

	/**
	 * Remove existing XML and HTML files
	 * @throws Exception 
	 */
	public int cleanData() throws Exception {
		
		int nDelete = 0;
		if (this.outputToConsole)
			System.out.println("Removing experiments...");
		
		//remove main XML file
		Path indexXmlPath = Paths.get(publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_LITE_XML_INDEX);
		if (Files.exists(indexXmlPath, LinkOption.NOFOLLOW_LINKS))
			Files.delete(indexXmlPath);
		this.experimentListXml = new XmlExperimentListFile(publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_LITE_XML_INDEX);

		//remove main HTML file
		Path indexHtmlPath = Paths.get(publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_LITE_HTML_INDEX);
		if (Files.exists(indexHtmlPath, LinkOption.NOFOLLOW_LINKS))
			Files.delete(indexHtmlPath);
		
		//remove experiments data (XML, HTML, files)
		File htmlDir = new File(publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_LITE_EXPERIMENT_DIR);
		File[] htmlFiles = htmlDir.listFiles();
		for (int f=0;f<htmlFiles.length;f++){
			Utils.removeDirectoryRecursive(Paths.get(htmlFiles[f].getCanonicalPath()));
			nDelete++;
		}
		return nDelete;
	}
	
	/**
	 * Remove XML and HTML files for a given experiment
	 * @throws TransformerException 
	 * @throws IOException
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws XPathExpressionException 
	 */
	public int removeExperiment(String experimentPath) throws TransformerException, IOException, XPathExpressionException, ParserConfigurationException, SAXException {
		
		File experimentDir = new File(experimentPath);
		if (!experimentDir.exists())
			return -1;
		experimentPath = experimentDir.getCanonicalPath();

		int experimentId = this.experimentListXml.removeExperiment(experimentPath);
		if (experimentId!=-1){
			//update XML list and regenerate HTML
			this.experimentListXml.saveXml();
			this.experimentListXml.saveToHTML(
					publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_LITE_HTML_INDEX, 
					publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_EXPERIMENT_SET_XSL_PATH);
			//delete experiment folder in iBIOMES Lite
			Path expWebDirPath = Paths.get(publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_LITE_EXPERIMENT_DIR + PATH_FOLDER_SEPARATOR + experimentId);
			if (Files.exists(expWebDirPath, LinkOption.NOFOLLOW_LINKS))
				Utils.removeDirectoryRecursive(expWebDirPath);
		}
		return experimentId;
	}
	
	/**
	 * Regenerate descriptors for the experiments contained in the given file tree.
	 * @return List of descriptor paths
	 * @throws Exception 
	 * @throws IOException 
	 */
	public List<String> regeneratePublishedDescriptors() throws Exception
	{
		String descriptorPath = null;
		int depth=0;
		List<String> descriptorsPath = experimentListXml.getExperimentList();
		List<ExperimentFolder> experiments = experimentListXml.readExperiments(this.outputToConsole);
		for (ExperimentFolder experiment : experiments){
			try{
				//load parsing configuration previously used
				String parsingRuleSetFilePath = null;
				String softwareContext = null;
				Properties parsingConfig = loadParsingConfigFile(experiment.getFileDirectory().getAbsolutePath() + PATH_FOLDER_SEPARATOR + IBIOMES_PARSE_CONFIG_FILE_NAME);
				if (parsingConfig!=null){
					parsingRuleSetFilePath = parsingConfig.getProperty(PARSER_CONFIG_PROPERTY_RULE_SET_FILE);
					softwareContext = parsingConfig.getProperty(PARSER_CONFIG_PROPERTY_SOFTWARE_CONTEXT);
					try{
						depth = Integer.parseInt(parsingConfig.getProperty(PARSER_CONFIG_PROPERTY_SOFTWARE_CONTEXT));
					} catch(NumberFormatException e){
						//TODO
					}
				}
				//parse
				descriptorPath = experiment.getFileDirectory().getAbsolutePath() + PATH_FOLDER_SEPARATOR + IBIOMES_DESC_FILE_TREE_FILE_NAME;
				this.parse(experiment.getFileDirectory().getAbsolutePath(), softwareContext, parsingRuleSetFilePath, depth);
				experiment.getFileDirectory().storeToXML(descriptorPath);
			}
			catch(Exception e){
				//logger
				System.err.println("[ERROR] Error when updating '"+descriptorPath+"':" + e.getLocalizedMessage());
			}
		}
		return descriptorsPath;
	}
	
	/**
	 * Update web content based on descriptors that have already been published.
	 * @return List of updated experiments
	 * @throws Exception 
	 */
	public List<String> updateWebContent() throws Exception
	{
		//retrieve list of experiments already published 
		List<String> experimentsPath = experimentListXml.getExperimentList();
		//clean
		this.cleanData();
		//retrieve descriptors (or parse if necessary) and data
		for (String experimentPath : experimentsPath){
			this.publishExperiment(experimentPath);
		}
		return experimentsPath;
	}
	
	/**
	 * Generate PDF document with experiment metadata and available images
	 * @param outputPath
	 */
	public void generatePdf(String outputPath, String experimentDirPath, String software, String xmlDescPath, boolean isForceDescUpdate, int depth) throws Exception
	{
		File experimentDesc = new File(experimentDirPath + PATH_FOLDER_SEPARATOR + IBIOMES_DESC_FILE_TREE_FILE_NAME);
		/*if (!experimentDesc.exists() || isForceDescUpdate){
			//parse directory
			this.parse(experimentDirPath, software, xmlDescPath, depth);
		}*/
		//load experiment descriptor
		XmlExperimentFile experimentXml = new XmlExperimentFile(experimentDesc.getAbsolutePath());
		ExperimentFolder experiment = experimentXml.readExperiment();
		
		//store to PDF document
		MetadataLookup lookupdIndex = new MetadataLookup(ibiomesHomeFolder + PATH_FOLDER_SEPARATOR + METADATA_LOOKUP_INDEX_PATH);
		ExperimentTransformer transformer = new ExperimentTransformer(lookupdIndex);
		transformer.storeExperimentAsPDF(experiment, outputPath);
	}
	
	/**
	 * Store experiment details as XML file
	 * @param outputFilePath Path to XML file
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void generateDetailedXML(ExperimentFolder experimentFolder, String outputFilePath) throws IllegalArgumentException, IllegalAccessException, Exception{
		XMLConverter converter = new XMLConverter();
		Document doc = converter.convertExperiment(experimentFolder);
		DOMSource source = new DOMSource(doc);
		net.sf.saxon.TransformerFactoryImpl tFactory = new net.sf.saxon.TransformerFactoryImpl();
		Transformer transformer = tFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "no");
		//transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		File outputXMLFile = new File(outputFilePath);
		StreamResult result = new StreamResult(outputXMLFile);
		transformer.transform(source, result);
	}
	
	/**
	 * Store experiment details as HTML file
	 * @param outputFilePath Path to HTML file
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void transformXmlToHtml(String experimentDescPath, String outputFilePath, String xslUrl) throws IllegalArgumentException, IllegalAccessException, Exception{
				
		//load XML
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(experimentDescPath);

		DOMSource source = new DOMSource(doc);
		//transform XML to HTML
		net.sf.saxon.TransformerFactoryImpl tFactory = new net.sf.saxon.TransformerFactoryImpl();
		Transformer transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(xslUrl));
		//transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.INDENT, "no");
		File outputHTMLFile = new File(outputFilePath);
		StreamResult result = new StreamResult(outputHTMLFile);
		transformer.transform(source, result);
	}
	
	
	private void createParsingConfigFile(String configFilePath, 
			String parsingRuleSetFilePath, 
			String softwareContext,
			int depth){
		Properties prop = new Properties();
		 
    	try {
    		//set the properties value
    		if (parsingRuleSetFilePath!=null && parsingRuleSetFilePath.length()>0)
    			prop.setProperty(PARSER_CONFIG_PROPERTY_RULE_SET_FILE, parsingRuleSetFilePath);
    		if (softwareContext!=null && softwareContext.length()>0)
    			prop.setProperty(PARSER_CONFIG_PROPERTY_SOFTWARE_CONTEXT, softwareContext);
    		if (softwareContext!=null && softwareContext.length()>0)
    			prop.setProperty(PARSER_CONFIG_PROPERTY_DEPTH_INDEPENDENCE, String.valueOf(depth));
    		//save properties to project root folder
    		prop.store(new FileOutputStream(configFilePath), null);
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
	}
	
	private Properties loadParsingConfigFile(String configFilePath){
		Properties prop = new Properties();
    	try {
            //load a properties file
    		prop.load(new FileInputStream(configFilePath));
    		return prop;
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
    		return null;
        }
	}

	/**
	 * Search experiments by keywords
	 * @param keywords Keywords
	 * @return List of matching experiments
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws XPathExpressionException 
	 */
	public IBIOMESLiteSearchRecordSet search(String[] keywords) throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
		XmlExperimentListFile xmlList = new XmlExperimentListFile(publicHtmlFolder + PATH_FOLDER_SEPARATOR + IBIOMES_LITE_XML_INDEX);
		return xmlList.search(keywords);
	}
	
	/**
	 * get version of iBIOMES Lite
	 * @return version
	 */
	public static String getVersion(){
		return IBIOMES_LITE_VERSION + " [" + Utils.getOperatingSystem() + "]";
	}
}
