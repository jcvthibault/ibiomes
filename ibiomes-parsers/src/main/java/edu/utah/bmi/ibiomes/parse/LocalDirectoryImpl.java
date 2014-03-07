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

package edu.utah.bmi.ibiomes.parse;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.metadata.FileMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

/**
 * Local directory with parsed files (detected formats) and subdirectories.
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="directory")
public class LocalDirectoryImpl implements LocalDirectory {
	
	private static final String IBIOMES_XSL_PATH_DEFAULT = "data"+IBIOMESConfiguration.PATH_FOLDER_SEPARATOR+"ibiomes_local.xsl";
	private final Logger logger = Logger.getLogger(LocalDirectoryImpl.class);

	protected String name;
	protected int level;
	protected String absolutePath;
	protected String relativePathFromTop;
	protected String description;
	protected DirectoryStructureDescriptor parserRuleSet;
	protected MetadataAVUList metadata;
	protected HashMap<String, ArrayList<LocalFile>>  files;
	protected ArrayList<LocalDirectory>  subdirectories;
	protected String softwareContext;
	protected String publisher;
	protected String publicationDate;
	
	protected LocalDirectoryImpl(){	
	}
	
	/**
	 * Constructor
	 * @param absolutePath
	 * @param relativePathFromTop
	 * @throws Exception
	 */
	public LocalDirectoryImpl(
			String absolutePath, 
			String relativePathFromTop) throws Exception {
		initCollection(absolutePath, relativePathFromTop, null, null, null);
	}

	/**
	 * Constructor
	 * @param absolutePath
	 * @param relativePathFromTop
	 * @param softwareContext
	 * @throws Exception
	 */
	protected LocalDirectoryImpl(
			String absolutePath, 
			String relativePathFromTop, 
			String softwareContext) throws Exception{
		initCollection(absolutePath, relativePathFromTop, softwareContext, null, null);
	}
	
	/**
	 * Constructor
	 * @param absolutePath
	 * @param relativePathFromTop
	 * @param softwareContext
	 * @param metadata
	 * @throws Exception
	 */
	protected LocalDirectoryImpl(
			String absolutePath, 
			String relativePathFromTop, 
			String softwareContext, 
			MetadataAVUList metadata) throws Exception{
		initCollection(absolutePath, relativePathFromTop, softwareContext, null, null);
	}
	
	/**
	 * Constructor
	 * @param absolutePath
	 * @param relativePathFromTop
	 * @param softwareContext
	 * @param metadata
	 * @param parserRuleSet Parser rule descriptor
	 * @throws Exception
	 */
	protected LocalDirectoryImpl(
			String absolutePath, 
			String relativePathFromTop, 
			String softwareContext, 
			MetadataAVUList metadata,
			DirectoryStructureDescriptor parserRuleSet) throws Exception{
		initCollection(absolutePath, relativePathFromTop, softwareContext, null, parserRuleSet);
	}
	
	private void initCollection(
			String localPath,
			String relativePathFromTop,
			String softwareContext, 
			MetadataAVUList metadata,
			DirectoryStructureDescriptor parserRuleSet) throws Exception
	{
		if (metadata != null)
			this.metadata = metadata;
		else this.metadata = new MetadataAVUList();

		File file = new File(localPath);
		this.absolutePath = file.getCanonicalPath();
		if (relativePathFromTop==null)
			this.relativePathFromTop = "";
		this.name = localPath.substring(localPath.lastIndexOf(IBIOMESConfiguration.PATH_FOLDER_SEPARATOR)+1);

		this.softwareContext = softwareContext;
		this.parserRuleSet = parserRuleSet;
		this.files = new HashMap<String, ArrayList<LocalFile>>();
		this.subdirectories = new ArrayList<LocalDirectory>();
		
		this.publisher = System.getProperty("user.name");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		this.publicationDate = dateFormat.format(new Date());
	}

	/**
	 * Get metadata
	 * @return Metadata list
	 */
	@XmlTransient
	public MetadataAVUList getMetadata(){	
		return this.metadata;
	}
	
	/**
	 * Set metadata
	 * @param metadata Metadata
	 */
	public void setMetadata(MetadataAVUList metadata){	
		this.metadata = metadata;
	}
	
	/**
	 * Add metadata
	 * @param metadata Metadata
	 */
	public void addMetadata(MetadataAVUList metadata){
		if (metadata != null){
			if (this.metadata==null)
				this.metadata = new MetadataAVUList();
			this.metadata.addAll(metadata);
		}
	}
	
	/**
	 * Retrieve list of metadata for all the files
	 * @return List of metadata for all the files
	 * @throws Exception 
	 */
	@XmlTransient
	public MetadataAVUList getFileMetadata() throws Exception {
		MetadataAVUList metadata = new MetadataAVUList();
		Collection<ArrayList<LocalFile>> fileGroups = this.files.values();
		for (ArrayList<LocalFile> fileGroup : fileGroups){
			for (LocalFile file : fileGroup){
				metadata.addAll(file.getMetadata());
			}
		}
		return metadata;
	}

	/**
	 * Get files (grouped by format)
	 * @return List of files
	 */
	@XmlTransient
	public Collection<ArrayList<LocalFile>> getFiles() {
		return this.files.values();
	}

	/**
	 * Get files (grouped by format)
	 * @return List of files
	 */
	@XmlTransient
	public HashMap<String, ArrayList<LocalFile>> getFilesByFormat() {
		return this.files;
	}
	
	/**
	 * Retrieve all the files in this collection and the sub-collections, using a directory exclusion list
	 * @param directoryExclusionList List of directories to exclude from the listing
	 * @return List of files
	 */
	public HashMap<String,ArrayList<LocalFile>> getFilesByFormatRecursive(List<String> directoryExclusionList){
		HashMap<String,ArrayList<LocalFile>> files = new HashMap<String,ArrayList<LocalFile>>();
		return getAllFilesRecursively(this, files, directoryExclusionList);
	}
	
	/**
	 * Retrieve all the files in this collection and the sub-collections
	 * @return List of files
	 */
	public HashMap<String,ArrayList<LocalFile>> getFilesByFormatRecursive(){
		return getFilesByFormatRecursive(null);
	}
	
	/**
	 * Get list of subdirectories
	 * @return List of subdirectories
	 */
	@XmlTransient
	public ArrayList<LocalDirectory> getSubdirectories() {
		return this.subdirectories;
	}
	
	/**
	 * Find subdirectory by path
	 */
	public LocalDirectory findSubdirectoryByPath(String path) {
		path = path.trim();
		if (path.endsWith("/"))
			path = path.substring(0, path.length()-1);
		if (this.getAbsolutePath().equals(path))
			return this;
		else
			return this.findSubdirectoryByPathRecursive(path);
	}
	
	/**
	 * Find subdirectory by path
	 */
	public LocalDirectory findSubdirectoryByPathRecursive(String path) {
		for (LocalDirectory subdir : this.subdirectories){
			if (subdir.getAbsolutePath().equals(path))
				return subdir;
			else if (path.startsWith(subdir.getAbsolutePath()))
				return subdir.findSubdirectoryByPath(path);
		}
		return null;
	}
	
	/**
	 * Get local path to the directory
	 * @return Local path to the directory
	 */
	
	public String getAbsolutePath(){
		return this.absolutePath;
	}

	/**
	 * Get local path to the parent directory
	 * @return local path to the parent directory
	 */
	public String getParent(){
		return this.absolutePath.substring(0, this.absolutePath.lastIndexOf(IBIOMESConfiguration.PATH_FOLDER_SEPARATOR));
	}
	
	/**
	 * Get directory name
	 * @return Directory name
	 */
	@XmlAttribute
	public String getName(){
		return this.name;
	}

	/**
	 * Get description
	 * @return Directory description
	 */
	public String getDescription(){
		return this.description;
	}
	/**
	 * Add textual description to the directory
	 * @param desc Directory description
	 */
	public void setDescription(String desc){
		if (desc != null) 
			this.description = desc.trim();
	}

	/**
	 * Get relative path from the top directory
	 * @return Relative path from the top directory
	 */
	@XmlTransient
	public String getRelativePathFromTop() {
		if (this.relativePathFromTop ==null)
			return "";
		else return this.relativePathFromTop;
	}

	/**
	 * Set relative path to the top directory
	 * @param relativePath Path to the top directory
	 */
	public void setRelativePathFromTop(String relativePath) {
		this.relativePathFromTop = relativePath;
	}
	
	@XmlAttribute
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@XmlAttribute
	public String getPublicationDate() {
		return publicationDate;
	}
	
	/**
	 * Get software context
	 * @return Software context
	 */
	public String getSoftwareContext() {
		return this.softwareContext;
	}	

	/**
	 * Set software context
	 * @param softwareContext Software context
	 */
	public void setSoftwareContext(String softwareContext) {
		this.softwareContext = softwareContext;
	}	
	
	/**
	 * List recursively all the files and the sub-collections in this collection
	 * @return List of files
	 */
	public String[] listAllFiles(File dir)
	{
		if (dir.isDirectory())
		{
			String currentDir = dir.getAbsolutePath();
			ArrayList<String> files = listAllFiles(currentDir, currentDir);
			String[] res = new String[files.size()];
			return files.toArray(res);
		}
		else return null;
	}
	
	/**
	 * List recursively all the files in this collection and the sub-collections
	 * @param rootDir
	 * @param currentDir
	 * @return List of files
	 */
	private ArrayList<String> listAllFiles(String rootDir, String currentDir){
		
		ArrayList<String> files = new ArrayList<String>();
		File coll = new File(currentDir);
		String[] fileList = coll.list();
		for (int f=0; f<fileList.length; f++)
		{
			String path = currentDir + "/" + fileList[f];
			String relativePath = path.substring(rootDir.length() + 1);
			File file = new File(path);
			if (file.isFile()){
				files.add(relativePath);
			}
			else {
				files.add(relativePath);
				files.addAll(listAllFiles(rootDir, path));
			}
		}
		return files;
	}
	
	/**
	 * Retrieve all the files in the given collection and the sub-collections
	 * @return List of files
	 */
	public HashMap<String,ArrayList<LocalFile>> getAllFilesRecursively(
			LocalDirectory dir, 
			HashMap<String,ArrayList<LocalFile>> allFiles,
			List<String> directoryExclusionList){
		
		HashMap<String,ArrayList<LocalFile>> filesInDir = dir.getFilesByFormat();
		
		//for each file format group
		for (String key : filesInDir.keySet()){
			for (LocalFile file : filesInDir.get(key)){
				if (!allFiles.containsKey(key)){
					allFiles.put(key, new ArrayList<LocalFile>());
				}
				allFiles.get(key).add(file);
			}
		}
		//for each subdirectory
		for (LocalDirectory subdir : dir.getSubdirectories())
		{
			//if the subdir is not part of the exclusion list
			if (directoryExclusionList==null || !directoryExclusionList.contains(subdir.getAbsolutePath()))
				allFiles = getAllFilesRecursively(subdir, allFiles);
		}
		return allFiles;
	}
	
	/**
	 * Retrieve all the files in the given collection and the sub-collections
	 * @return List of files
	 */
	public HashMap<String,ArrayList<LocalFile>> getAllFilesRecursively(
			LocalDirectory dir, 
			HashMap<String,ArrayList<LocalFile>> allFiles){
		return getAllFilesRecursively(dir, allFiles, null);
	}

	/**
	 * Add a subdirectory to this current directory
	 * @param subdir Subdirectory
	 */
	public void addSubdirectory(LocalDirectory subdir)
	{
		if (subdirectories == null){
			subdirectories = new ArrayList<LocalDirectory>();
		}		
		subdirectories.add(subdir);
	}
	
	
	/**
	 * Dump list of files/subdirectories and associated metadata
	 * @throws Exception
	 */
	public void dumpToText() throws Exception
	{
		dumpToText(this, 0);
	}
	
	private void dumpToText(LocalDirectory collection, int level) throws Exception
	{
		String tab = "";
		for (int i=0; i<level; i++){
			tab += "\t";
		}

		System.out.println(tab + "-----------------------------------------------------------");
		System.out.println(tab + "DIRECTORY: " + collection.getAbsolutePath());
		MetadataAVUList dirMD = collection.getMetadata();
		for (MetadataAVU avu : dirMD){
			String avuStr = avu.toString().replaceAll("\\n", " ");
			if (avuStr.length()>100){
				avuStr = avuStr.substring(0, 100) + "...\"";
			}
			System.out.println(tab + "\t" + avuStr);
		}
		System.out.println("");

		Collection<ArrayList<LocalFile>> files = collection.getFiles();
		for (ArrayList<LocalFile> fileGroup : files){
			for (LocalFile file : fileGroup){
				System.out.println(tab + "\tFILE: " + file.getAbsolutePath());
				MetadataAVUList fileMD = file.getMetadata();
				for (MetadataAVU avu : fileMD){
					String avuStr = avu.toString().replaceAll("\\n", " ");
					if (avuStr.length()>100){
						avuStr = avuStr.substring(0, 100) + "...\"";
					}
					System.out.println(tab + "\t\t" + avuStr);
				}
			}
		}
		
		for (LocalDirectory dir : collection.getSubdirectories()){
			dumpToText(dir, level+1);
		}
	}
	
	/**
	 * Dump list of files/subdirectories and associated metadata into XML output
	 * @throws Exception
	 */
	public void storeToXML() throws Exception {
		storeToXML(null);
	}
	
	/**
	 * Dump list of files/subdirectories and associated metadata into XML file
	 * @param outputPath Path to XML output file
	 * @throws Exception
	 */
	public void storeToXML(String outputPath) throws Exception
	{
		//create new XML doc
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document xmlDocument = docBuilder.newDocument();
		
		//add root element
		Element rootElement = xmlDocument.createElement("ibiomes");
		xmlDocument.appendChild(rootElement);
		
		//get XML representation
		this.getXmlRepresentation(this, xmlDocument, rootElement);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		DOMSource source = new DOMSource(xmlDocument);
		StreamResult result = null;
		if (outputPath!=null) 
			result = new StreamResult(new File(outputPath));
		else 
			result = new StreamResult(System.out);
 
		transformer.transform(source, result);
	}
	
	private void getXmlRepresentation(LocalDirectory collection, Document xmlDocument, Element rootElement) throws Exception
	{
		//add directory element
		Element dirElement = xmlDocument.createElement("directory");
		rootElement.appendChild(dirElement);
		dirElement.setAttribute("absolutePath", collection.getAbsolutePath());
		dirElement.setAttribute("name", collection.getName());
		dirElement.setAttribute("publisher", collection.getPublisher());
		dirElement.setAttribute("publicationDate", collection.getPublicationDate());
		dirElement.setAttribute("parent", collection.getParent());
		
		JAXBContext contextObj = JAXBContext.newInstance(MetadataAVUList.class);
	    Marshaller marshallerObj = contextObj.createMarshaller();
	    marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    marshallerObj.marshal(collection.getMetadata(), dirElement);
	    
	    //files
	    Element filesNode = xmlDocument.createElement("files");
	    List<String> formats = new ArrayList<String>(collection.getFilesByFormat().keySet());
	    Collections.sort(formats);
	    //file groups (by format)
		for (String fileFormat : formats){
			ArrayList<LocalFile> fileGroup = collection.getFilesByFormat().get(fileFormat);
			Element fileGroupNode = xmlDocument.createElement("fileGroup");
			fileGroupNode.setAttribute("format", fileFormat);
			for (LocalFile file : fileGroup)
			{
				//individual file
				Element fileNode = xmlDocument.createElement("file");
				fileNode.setAttribute("absolutePath", file.getCanonicalPath());
				fileNode.setAttribute("parent", file.getParent());
				fileNode.setAttribute("relativePath", file.getRelativePathFromProjectRoot());
				fileNode.setAttribute("name", file.getName());
				fileNode.setAttribute("size", String.valueOf(file.length()));
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm");
				fileNode.setAttribute("modificationDate", (dateFormat.format(new Date(file.lastModified()))));
				
				if (fileFormat != null && fileFormat.trim().length()>0)
					fileNode.setAttribute("format", file.getMetadata().getValue(FileMetadata.FILE_FORMAT));
				else fileNode.setAttribute("format", "Unknown");
				
				//file metadata
				marshallerObj.marshal(file.getMetadata(), fileNode);
				
				fileGroupNode.appendChild(fileNode);
			}				
			filesNode.appendChild(fileGroupNode);
		}
		dirElement.appendChild(filesNode);
		
		//subdirectories
		Element collectionsNode = xmlDocument.createElement("subdirectories");
		for (LocalDirectory dir : collection.getSubdirectories()){
			this.getXmlRepresentation(dir, xmlDocument, collectionsNode);
		}
		dirElement.appendChild(collectionsNode);
	}
	
	/**
	 * Dump list of files/subdirectories and associated metadata into HTML output
	 * @throws Exception
	 */
	public void storeToHTML() throws Exception
	{
		storeToHTML(null);
	}
	
	/**
	 * Dump list of files/subdirectories and associated metadata into HTML file
	 * @param outputPath Path to HTML output file
	 * @throws Exception
	 */
	public void storeToHTML(String outputPath) throws Exception
	{
		String xslUrl = System.getenv().get("IBIOMES_HOME") + "/" + IBIOMES_XSL_PATH_DEFAULT;
		
		//create new XML doc
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document xmlDocument = docBuilder.newDocument();
		
		//add root element
		Element rootElement = xmlDocument.createElement("ibiomes");
		xmlDocument.appendChild(rootElement);
		
		//get XML representation
		this.getXmlRepresentation(this, xmlDocument, rootElement);
		//XSLT transformation
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer(new javax.xml.transform.stream.StreamSource(xslUrl));
		//format output
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		DOMSource source = new DOMSource(xmlDocument);
		StreamResult result = null;
		if (outputPath!=null) 
			result = new StreamResult(new File(outputPath));
		else 
			result = new StreamResult(System.out);
		//output to HTML
		transformer.transform(source, result);
	}
	
}
