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
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.conf.DirectoryStructureRuleSet;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

/**
 * File directory parser
 * @author Julien Thibault, University of Utah
 *
 */
public class DirectoryParser {
	
	private final Logger logger = Logger.getLogger(DirectoryParser.class);

	protected String name;
	protected String rootDirectoryPath;
	protected String externalURL;
	protected String description;
	protected DirectoryStructureDescriptor parserRuleSet;
	private LocalFileFactory factory;
	protected List<IBIOMESListener> listeners;
		
	/**
	 * 
	 * @param rootDirectoryPath Path to the root directory that needs to be parsed
	 * @throws Exception
	 */
	public DirectoryParser(String rootDirectoryPath) throws Exception {
		initCollection(rootDirectoryPath, null, null, null);
	}

	/**
	 * 
	 * @param rootDirectoryPath Path to the root directory that needs to be parsed
	 * @param descriptorFile Parsing rule file
	 * @throws Exception
	 */
	public DirectoryParser(String rootDirectoryPath, DirectoryStructureDescriptor descriptorFile) throws Exception{
		initCollection(rootDirectoryPath, descriptorFile, null, null);
	}

	/**
	 * 
	 * @param rootDirectoryPath Path to the root directory that needs to be parsed
	 * @param descriptorFile Parsing rule file
	 * @param listeners Listener to follow parsing progress
	 * @throws Exception
	 */
	public DirectoryParser(String rootDirectoryPath, DirectoryStructureDescriptor descriptorFile, List<IBIOMESListener> listeners) throws Exception{
		initCollection(rootDirectoryPath, descriptorFile, listeners, null);
	}

	/**
	 * 
	 * @param rootDirectoryPath Path to the root directory that needs to be parsed
	 * @param descriptorFile Parsing rule file
	 * @param listeners Listener to follow parsing progress
	 * @param externalURL External URL to directory
	 * @throws Exception
	 */
	public DirectoryParser(String rootDirectoryPath, DirectoryStructureDescriptor descriptorFile, List<IBIOMESListener> listeners, String externalURL) throws Exception{
		initCollection(rootDirectoryPath, descriptorFile, listeners, externalURL);
	}
	
	private void initCollection(
			String localPath, 
			DirectoryStructureDescriptor descriptorFile,
			List<IBIOMESListener> listeners,
			String externalURL) throws Exception
	{
		File file = new File(localPath);
		this.rootDirectoryPath = file.getCanonicalPath();
		this.externalURL = externalURL;
		this.name = rootDirectoryPath.substring(localPath.lastIndexOf('/')+1);
		this.listeners = listeners;
		this.parserRuleSet = descriptorFile;
		this.factory = LocalFileFactory.instance();
	}
	
	/**
	 * Get local path to the directory
	 * @return Local path to the directory
	 */
	
	public String getAbsolutePath(){
		return this.rootDirectoryPath;
	}
	
	/**
	 * Get directory name
	 * @return Directory name
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * Get externalURL
	 * @return External URL
	 */
	public String getExternalURL(){
		return this.externalURL;
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
	 * Parse directory using descriptor file
	 * @param softwareContext Software context
	 * @throws Exception 
	 */
	public LocalDirectory parseDirectories(String softwareContext) throws Exception
	{
		logger.info("Parsing directory '"+this.rootDirectoryPath+"'");
		File dir = new File(this.rootDirectoryPath);
		String canonicalPath = dir.getCanonicalPath();
		String relativePathFromTop = canonicalPath.substring(this.rootDirectoryPath.length(), canonicalPath.length());
		if (dir.exists() && dir.isDirectory())
		{
			return this.parseDirectoryRecursive(canonicalPath, relativePathFromTop, softwareContext);
		}
		else {
			throw new Exception("Directory '"+ canonicalPath +"' does not exist!");
		}
	}
	
	/**
	 * Recursive function to parse directory
	 * @param canonicalPath Path to directory to parse
	 * @param relativePathFromTop Relative path of directory from project root
	 * @param softwareContext Software context for parsing
	 * @return Parsed directory
	 * @throws Exception
	 */
	private LocalDirectory parseDirectoryRecursive(
			String canonicalPath, 
			String relativePathFromTop, 
			String softwareContext) throws Exception
	{
		File dir = new File(canonicalPath);
		logger.info("Parsing directory '"+relativePathFromTop+"'");
		LocalDirectoryImpl parsedDirectory = new LocalDirectoryImpl(canonicalPath, relativePathFromTop);
		parsedDirectory.setSoftwareContext(softwareContext);

		if (this.externalURL != null) {
			String dirName = parsedDirectory.getName();
			int idx = dirName.indexOf("/");
			if (idx == -1) {
				parsedDirectory.setExternalURL(this.externalURL);
			}
			else {
				parsedDirectory.setExternalURL(this.externalURL + "/" + parsedDirectory.getName().substring(idx+1));
			}
		}
		
		String[] files = dir.list();
		for (int f=0; f<files.length; f++)
		{
			String filePath = files[f];
			File file = new File(canonicalPath + "/" + filePath);
			
			//if file/directory is readable
			if (file.canRead()){
				//if the current file is a subdirectory
				if (file.isDirectory())
				{
					//parse subdirectories (recursive) 
					LocalDirectory subdir = parseDirectoryRecursive(
							canonicalPath + "/" + filePath, 
							relativePathFromTop,
							softwareContext);
					parsedDirectory.addSubdirectory(subdir);
				}
				else {
					//if its a regular file 
					if (!file.getName().startsWith(".") && 
						!file.getName().endsWith("~"))
					{
						//get path relative to root of project
						String canonicalFilePath = canonicalPath + "/" + filePath;
						String relativeFilePath = "";
						if (relativePathFromTop != null && relativePathFromTop.length()>0){
							relativeFilePath = relativePathFromTop + "/";
						}
						relativeFilePath += filePath;
						
						String fileFormat = null;
						String fileDescription = null;
						MetadataAVUList fileExtendedAttributes = null;
						List<String> fileClasses = null;
						
						//retrieve file properties defined in descriptor file
						if (this.parserRuleSet!=null){
							DirectoryStructureRuleSet ruleSet = this.parserRuleSet.getRuleSetForFile(relativeFilePath);
							fileExtendedAttributes = ruleSet.getExtendedAttributes();
							fileFormat = ruleSet.getFileFormat();
							fileDescription = ruleSet.getDescription();
							fileClasses = ruleSet.getFileClasses();
							String fileSoftwareContext = ruleSet.getSoftwareContext();
							if (fileSoftwareContext!=null)
								softwareContext = fileSoftwareContext;
						}
						
						//get file reference
						try{
							LocalFile localFile = null;
							if (fileFormat != null && fileFormat.length()>0)
								localFile = factory.getFileInstanceFromFormat(canonicalFilePath, fileFormat);
							else
								localFile = factory.getFile(canonicalFilePath, softwareContext);
							
							//add/update properties parsed from descriptor
							localFile.setAssignedClasses(fileClasses);
							localFile.setDescription(fileDescription);
							localFile.setExtendedAttributes(fileExtendedAttributes);
							
							//add relative path
							localFile.setRelativePathFromProjectRoot(canonicalFilePath.substring(this.rootDirectoryPath.length() + 1));

							if (externalURL != null) { 
								localFile.setExternalURL(externalURL + "/" + localFile.getRelativePathFromProjectRoot());
							}
	
							//add file to directory
							if (!parsedDirectory.getFilesByFormat().containsKey(localFile.getFormat())){
								parsedDirectory.getFilesByFormat().put(localFile.getFormat(), new ArrayList<LocalFile>());
							}
							parsedDirectory.getFilesByFormat().get(localFile.getFormat()).add(localFile);
						} catch (Exception e) {
							if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
								e.printStackTrace();
						}
					}
					updateListeners();
				}
			}
			else{
				logger.info("File "+filePath+" is not readable");
			}
		}
		return parsedDirectory;
	}
	
	/**
	 * Update listeners
	 */
	private void updateListeners(){
		if (this.listeners!=null && this.listeners.size()>0){
			for (IBIOMESListener listener : this.listeners){
				listener.update();
			}
		}
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
}
