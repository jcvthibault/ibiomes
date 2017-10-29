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

package edu.utah.bmi.ibiomes.parse.chem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import edu.utah.bmi.ibiomes.parse.DirectoryParser;
import edu.utah.bmi.ibiomes.parse.IBIOMESListener;
import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.parse.chem.amber.AmberCollection;
import edu.utah.bmi.ibiomes.parse.chem.charmm.CHARMMCollection;
import edu.utah.bmi.ibiomes.parse.chem.common.TopologyFileCollection;
import edu.utah.bmi.ibiomes.parse.chem.gamess.GAMESSCollection;
import edu.utah.bmi.ibiomes.parse.chem.gaussian.GaussianCollection;
import edu.utah.bmi.ibiomes.parse.chem.gromacs.GROMACSCollection;
import edu.utah.bmi.ibiomes.parse.chem.namd.NAMDCollection;
import edu.utah.bmi.ibiomes.parse.chem.nwchem.NWChemCollection;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;
import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.experiment.Experiment;
import edu.utah.bmi.ibiomes.experiment.ExperimentProcessGroup;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.metadata.BiosimMetadata;
import edu.utah.bmi.ibiomes.metadata.GeneralMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

/**
 * Factory for local experiments process groups. The file directory parser can process 
 * multiple groups of files independently. This is achieved by specifying the depth in 
 * the tree where each folder represents a separate group of tasks.
 * '0' means that the parser will be run once at the root directory. '1' means that the 
 * parser will be run for each direct child of the root folder, etc.
 * @author Julien Thibault, University of Utah
 *
 */
public class ExperimentFactory {
	
	private final Logger logger = Logger.getLogger(ExperimentFactory.class);
	private LocalDirectory directory = null;
	private String localPath = null;
	private List<ExperimentFolder> experimentFolders = null;
	private int depthIndependentGroups = 0;
	
	private IBIOMESConfiguration defaultConfig = null;
	
	/**
	 * New experiment factory
	 * @param localPath Path to local directory
	 * @throws Exception 
	 */
	public ExperimentFactory(String localPath) throws Exception{
		this.localPath = localPath;
		this.experimentFolders = null;
		this.defaultConfig = IBIOMESConfiguration.getInstance();
	}
	
	/**
	 * New experiment factory
	 * @param localPath Path to local directory
	 * @param depthForIndependentFolders Depth to run independent passes for parsing
	 * @throws Exception 
	 */
	public ExperimentFactory(String localPath, int depthForIndependentFolders) throws Exception {
		this.localPath = localPath;
		this.experimentFolders = null;
		this.depthIndependentGroups = depthForIndependentFolders;
		this.defaultConfig = IBIOMESConfiguration.getInstance();
	}
	
	/**
	 * Get depth in the tree where the parser should be run independently for each folder. 
	 * @return Depth in the tree where the parser should be run independently for each folder 
	 */
	public int getDepthIndependentGroups() {
		return depthIndependentGroups;
	}

	/**
	 * Set depth in the tree where the parser should be run independently for each folder 
	 * @param depthIndependentGroups Depth in the tree where the parser should be run independently for each folder 
	 */
	public void setDepthIndependentGroups(int depthIndependentGroups) {
		this.depthIndependentGroups = depthIndependentGroups;
	}
	
	/**
	 * Parse file directory 
	 * @param softwareContext Software context for parsing
	 * @param descriptorFile Parsing rule descriptor file
	 * @param listeners Parsing progress listeners
	 * @param externalURL External URL
	 * @return List of parsed folders
	 * @throws Exception 
	 */
	private List<ExperimentFolder> parseFileDirectory(
			String softwareContext, 
			DirectoryStructureDescriptor descriptorFile,
			List<IBIOMESListener> listeners,
			String externalURL) throws Exception
	{
		//load default software context if necessary
		if (softwareContext==null && this.defaultConfig!=null){
			softwareContext = this.defaultConfig.getDefaultSoftwareContext();
		}

		//load default XML descriptor file if necessary
		if (descriptorFile==null && this.defaultConfig!=null){
			descriptorFile = this.defaultConfig.getDefaultParserRuleFile();
		}
		
		logger.info("Parsing directory " + this.localPath);
		
		//parse files
		DirectoryParser parser = new DirectoryParser(localPath, descriptorFile, listeners, externalURL);		
		this.directory = parser.parseDirectories(softwareContext);
		this.experimentFolders = new ArrayList<ExperimentFolder>();

		//retrieve independent groups
		List<String> independentGroups = this.retrieveIndependentGroups();
		List<MolecularSystem> extraSystems = new ArrayList<MolecularSystem>();
		
		for (int g=0;g<independentGroups.size();g++)
		{
			LocalDirectory subdirectory = directory.findSubdirectoryByPath(independentGroups.get(g));
			ExperimentFolder experimentCollection = parseCollection(subdirectory, softwareContext, descriptorFile);

			//add identified process group to experiment if it contains at least one task
			if (experimentCollection.getTasks().size()>0 || independentGroups.size()==1)
				this.experimentFolders.add(experimentCollection);
			else if (experimentCollection.getMolecularSystems().size()>0)
				extraSystems.addAll(experimentCollection.getMolecularSystems());
		}
		// if depth is specified, 
		// need to parse files on top directories to see if a molecular system 
		// can be found and used for independent groups without one
		if (this.depthIndependentGroups>0){
			MolecularSystem system = TopologyFileCollection.findLargestMolecularSystem(
					softwareContext, 
					directory.getFilesByFormatRecursive(independentGroups));
			//compare system and extraSystems
			extraSystems.add(system);
			system = TopologyFileCollection.findLargestMolecularSystem(extraSystems);
			//add molecular system to groups without one
			for (ExperimentFolder folder : this.experimentFolders){
				List<ExperimentProcessGroup> groups = folder.getProcessGroups();
				if (groups!=null){
					for (ExperimentProcessGroup group : groups){
						if (group.getMolecularSystem()==null)
							group.setMolecularSystem(system);
					}
				}
			}
		}
		return this.experimentFolders;
	}
	
	/**
	 * Parse collection of files to generate experiment metadata
	 * @param softwareContext Software context
	 * @param descriptorFile Descriptor file containing parsing rules
	 * @param listeners Listeners to follow parsing progress
	 * @return Parsed directory
	 * @throws Exception
	 */
	public LocalDirectory parseDirectoryForMetadata(
			String softwareContext, 
			DirectoryStructureDescriptor descriptorFile,
			List<IBIOMESListener> listeners) throws Exception
	{	
		return parseDirectoryForMetadata(softwareContext, descriptorFile, listeners, "");
	}
	/**
	 * Parse collection of files to generate experiment metadata
	 * @param softwareContext Software context
	 * @param descriptorFile Descriptor file containing parsing rules
	 * @param listeners Listeners to follow parsing progress
	 * @return Parsed directory
	 * @throws Exception
	 */
	public LocalDirectory parseDirectoryForMetadata(
			String softwareContext, 
			DirectoryStructureDescriptor descriptorFile,
			List<IBIOMESListener> listeners,
			String externalURL) throws Exception
	{	
		if (this.experimentFolders==null)
			this.parseFileDirectory(softwareContext, descriptorFile, listeners, externalURL);

		logger.info("Generating metadata for " + this.localPath);
		
		//get metadata for each group
		for (ExperimentFolder experimentGroup : experimentFolders){
			experimentGroup.getFileDirectory().setMetadata(experimentGroup.getMetadata());
		}
		
		//escalate metadata to experiment level
		//TODO filtering / aggregate AVUs when needed
		MetadataAVUList metadata = directory.getMetadata();
		for (ExperimentFolder experimentGroup : experimentFolders){
			metadata.addAll(experimentGroup.getMetadata());
		}
		metadata.add(new MetadataAVU(GeneralMetadata.IBIOMES_FILE_TYPE, BiosimMetadata.DIRECTORY_EXPERIMENT));
		directory.setMetadata(metadata);
		
		return directory;
	}
	
	/**
	 * Parse collection of files and generate representation of virtual experiment workflow
	 * @param softwareContext Software context
	 * @param descriptorFile Descriptor file containing parsing rules
	 * @param listeners Listeners to follow parsing progress
	 * @return Experiment
	 * @throws Exception
	 */
	public Experiment parseDirectoryForExperimentWorkflow(
			String softwareContext, 
			DirectoryStructureDescriptor descriptorFile,
			List<IBIOMESListener> listeners,
			String externalURL) throws Exception
	{
		if (this.experimentFolders==null)
			this.parseFileDirectory(softwareContext, descriptorFile, listeners, externalURL);

		logger.info("Generating experiment workflow for " + this.localPath);
		
		ArrayList<ExperimentProcessGroup> processGroups = new ArrayList<ExperimentProcessGroup>();
		for (ExperimentFolder experimentGroup : experimentFolders){
			//add identified process groups to experiment
			if (experimentGroup.getProcessGroups()!=null)
				processGroups.addAll(experimentGroup.getProcessGroups());
		}
		
		Experiment experiment = new Experiment(directory.getName());
		experiment.setProcessGroups(processGroups);
		
		return experiment;
	}

	/**
	 * Parse collection of files to generate metadata and representation of virtual experiment workflow
	 * @param softwareContext Software context
	 * @param descriptorFile Descriptor file containing parsing rules
	 * @param listeners Listeners to follow parsing progress
	 * @return Experiment and associated directory
	 * @throws Exception 
	 */
	public ExperimentFolder parseDirectoryForExperimentWorkflowAndMetadata(
			String softwareContext, 
			DirectoryStructureDescriptor descriptorFile,
			List<IBIOMESListener> listeners) throws Exception {
		return parseDirectoryForExperimentWorkflowAndMetadata(softwareContext, descriptorFile, listeners, "");
	}
	/**
	 * Parse collection of files to generate metadata and representation of virtual experiment workflow
	 * @param softwareContext Software context
	 * @param descriptorFile Descriptor file containing parsing rules
	 * @param listeners Listeners to follow parsing progress
	 * @return Experiment and associated directory
	 * @throws Exception 
	 */
	public ExperimentFolder parseDirectoryForExperimentWorkflowAndMetadata(
			String softwareContext, 
			DirectoryStructureDescriptor descriptorFile,
			List<IBIOMESListener> listeners,
			String externalURL) throws Exception 
	{
		LocalDirectory directory = this.parseDirectoryForMetadata(softwareContext, descriptorFile, listeners, externalURL);
		Experiment experiment = this.parseDirectoryForExperimentWorkflow(softwareContext, descriptorFile, listeners, externalURL);
		ExperimentFolder folder = new ExperimentFolder(directory, softwareContext, descriptorFile);
		if (experiment!=null){
			folder.setProcessGroups(experiment.getProcessGroups());
			folder.setName(experiment.getName());
			folder.setDescription(experiment.getDescription());
			folder.setExternalURL(externalURL);
		}
		return folder;
	}
	
	/**
	 * Parse collection based on software context
	 * @param directory Local directory
	 * @param softwareContext Software context
	 * @param descriptorFile XML descriptor
	 * @return Experiment and associated directory
	 * @throws Exception
	 */
	private ExperimentFolder parseCollection(
			LocalDirectory directory, 
			String softwareContext, 
			DirectoryStructureDescriptor descriptorFile) throws Exception{
		
		ExperimentFolder experimentCollection = null;
		
		// look for software-specific collections first
		if (softwareContext != null)
		{
			softwareContext = softwareContext.toLowerCase().trim();
			
			if (softwareContext.equals(Software.AMBER.toLowerCase())){
				experimentCollection = new AmberCollection(directory, descriptorFile);
			}
			else if (softwareContext.equals(Software.GROMACS.toLowerCase())){
				experimentCollection = new GROMACSCollection(directory, descriptorFile);
			}
			else if (softwareContext.equals(Software.CHARMM.toLowerCase())){
				experimentCollection = new CHARMMCollection(directory, descriptorFile);
			}
			else if (softwareContext.equals(Software.NAMD.toLowerCase())){
				experimentCollection = new NAMDCollection(directory, descriptorFile);
			}
			else if (softwareContext.equals(Software.NWCHEM.toLowerCase())){
				experimentCollection = new NWChemCollection(directory, descriptorFile);
			}
			else if (softwareContext.equals(Software.GAUSSIAN.toLowerCase())){
				experimentCollection = new GaussianCollection(directory, descriptorFile);
			}
			else if (softwareContext.equals(Software.GAMESS.toLowerCase())){
				experimentCollection = new GAMESSCollection(directory, descriptorFile);
			}
		}
		if (experimentCollection == null)
			//return generic collection
			experimentCollection = new DefaultExperimentImpl(directory, descriptorFile);
		
		return experimentCollection;
	}
	
	/**
	 * Retrieve list of folders to parse independently based on given depth
	 * @return List of folders to parse independently based on given depth
	 */
	public List<String> retrieveIndependentGroups(){
		ArrayList<String> folders = new ArrayList<String>();
		if (depthIndependentGroups==0)
			folders.add(localPath);
		else {
			return retrieveIndependentGroupsRecursive(localPath, folders, 0);
		}
		return folders;
	}
	
	/**
	 * Retrieve list of folders to parse independently
	 * @param dirPath Path to directory to parse recursively
	 * @param groups List of independent groups
	 * @param depth Current depth of recursion
	 * @return List of folders to parse independently
	 */
	private List<String> retrieveIndependentGroupsRecursive(String dirPath, List<String> groups, int depth){
		File dir = new File(dirPath);
		String[] fileList = dir.list();
		int nFiles = 0;
		int nDirs = 0;
		depth++;
		for (int f=0; f<fileList.length; f++)
		{
			String fullPath = dirPath + "/" + fileList[f];
			File file = new File(fullPath);
			if (file.isFile()){
				//TODO orphan files that will not be parsed ??
				nFiles++;
			}
			else {
				if (depth==this.depthIndependentGroups){
					groups.add(fullPath);
				}
				else {
					return retrieveIndependentGroupsRecursive(fullPath, groups, depth);
				}
				nDirs++;
			}
		}
		//if theres no subfolder add this folder to groups, 
		// even if the target depth hasnt been reached yet
		if (nDirs==0){
			groups.add(dirPath);
		}
		return groups;
	}
}
