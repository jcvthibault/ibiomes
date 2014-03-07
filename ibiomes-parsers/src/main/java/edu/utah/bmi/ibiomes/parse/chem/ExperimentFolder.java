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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.conf.TaskGroupingPolicy;
import edu.utah.bmi.ibiomes.experiment.Experiment;
import edu.utah.bmi.ibiomes.experiment.ExperimentProcess;
import edu.utah.bmi.ibiomes.experiment.ExperimentProcessGroup;
import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.REMDTask;
import edu.utah.bmi.ibiomes.experiment.summary.SummaryExperimentTasks;
import edu.utah.bmi.ibiomes.experiment.summary.SummaryMolecularSystems;
import edu.utah.bmi.ibiomes.metadata.ExperimentMetadata;
import edu.utah.bmi.ibiomes.metadata.FileMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.metadata.PlatformMetadata;
import edu.utah.bmi.ibiomes.metadata.TopologyMetadata;
import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.common.TopologyFileCollection;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;

/**
 * Virtual experiment with parsing configuration for the associated files
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="experiment")
public class ExperimentFolder extends Experiment
{
	protected String softwareContext = null;
	protected LocalFile mainStructureFile = null;
	protected TopologyFile mainTopologyFile = null;
	protected DirectoryStructureDescriptor parserRuleSet;
	protected HashMap<String, ArrayList<LocalFile>> allFiles;
	
	/**
	 * 
	 */
	protected ExperimentFolder(){
		super();
	}

	/**
	 * Default constructor.
	 * @throws Exception
	 */
	protected ExperimentFolder(
			LocalDirectory fileDirectory, 
			String softwareContext, 
			DirectoryStructureDescriptor descriptorFile) throws Exception
	{
		super(fileDirectory);
		initialize(softwareContext, descriptorFile, null, null);
	}
	
	/**
	 * Default constructor.
	 * @throws Exception
	 */
	protected ExperimentFolder(
			LocalDirectory fileDirectory, 
			String softwareContext, 
			DirectoryStructureDescriptor descriptorFile, 
			String[] structureFormats,
			String[] topologyFormats) throws Exception
	{
		super(fileDirectory);
		initialize(softwareContext, descriptorFile, structureFormats, topologyFormats);
	}
	
	
	private void initialize(
			String softwareContext, 
			DirectoryStructureDescriptor descriptorFile, 
			String[] structureFormats,
			String[] topologyFormats) throws Exception
	{
		this.softwareContext = softwareContext;
		this.parserRuleSet = descriptorFile;
		this.allFiles = fileDirectory.getFilesByFormatRecursive();
		
		if (structureFormats!=null)
			this.setMainStructureFile(structureFormats);
		if (topologyFormats!=null)
			this.setMainTopologyFile(topologyFormats);
	}
	
	/**
	 * Initialize molecular system
	 * @return Molecular system
	 */
	protected MolecularSystem initializeMolecularSystem(){
		MolecularSystem system = null;
		if (mainTopologyFile != null){
			List<MolecularSystem> systems = mainTopologyFile.getMolecularSystems();
			if (systems != null && systems.size()>0){
				system = systems.get(0);
			}
		}
		return system;
	}
	
	/**
	 * Initialize processes based on content of files specified by given format
	 * @param parameterFileFormats List of file formats that specify only parameters/methods
	 * @return List of experiment processes
	 * @throws Exception
	 */
	protected List<ExperimentProcess> initializeProcesses(String[] parameterFileFormats) throws Exception
	{
		if (parameterFileFormats==null || parameterFileFormats.length==0){
			return null;
		}
		String preferredParamFormat = parameterFileFormats[0];
		
		List<ExperimentTask> tasks = new ArrayList<ExperimentTask>();
		List<LocalFile> outFiles = allFiles.get(preferredParamFormat);
		if (outFiles!=null && outFiles.size()>=0)
		{
			//merge REMD replica together when applicable
			TaskGroupingPolicy groupingPolicy = IBIOMESConfiguration.getInstance().getDefaultTaskGroupingPolicy();
			//if no grouping specified for REMD tasks
			if (groupingPolicy==null){
				for (LocalFile outFile : outFiles){
					AbstractParameterFile paramFile = (AbstractParameterFile)outFile;
					List<ExperimentTask> fileTasks = paramFile.getTasks();
					if (fileTasks!=null)
						tasks.addAll(fileTasks);
				}
			}
			else {
				//group REMD replicas together
				HashMap<String, List<LocalFile>> fileGroups = groupingPolicy.apply(outFiles);
				Set<String> groupIds = fileGroups.keySet();
				for (String groupId : groupIds){
					List<LocalFile> fileGroup = fileGroups.get(groupId);
					if (fileGroup!=null && fileGroup.size()>0){
						//files in group NO_ASSIGNED_GROUP don't need to be processed
						if (groupId.equals(TaskGroupingPolicy.NO_ASSIGNED_GROUP)){
							for (LocalFile outFile : fileGroup){
								AbstractParameterFile paramFile = (AbstractParameterFile)outFile;
								List<ExperimentTask> fileTasks = paramFile.getTasks();
								if (fileTasks!=null)
									tasks.addAll(fileTasks);
							}
						}
						else //merge tasks within the same group into a single REMD task
						{
							if (fileGroup.size()>0){
								//retrieve all REMD tasks
								List<REMDTask> remdTasks = new ArrayList<REMDTask>();
								for (LocalFile outFile : fileGroup){
									AbstractParameterFile paramFile = (AbstractParameterFile)outFile;
									List<ExperimentTask> fileTasks = paramFile.getTasks();
									for (ExperimentTask fileTask : fileTasks){
										if (fileTask.getMethodType().equals(ParameterSet.METHOD_REMD))
											remdTasks.add((REMDTask)fileTask);
									}
								}
								//merge
								if (remdTasks.size()>0){
									REMDTask remdTask = REMDTask.merge(remdTasks);
									tasks.add(remdTask);
								}
							}
						}
					}
				}
			}
		}
		//if files in in preferred format not available then check other formats 
		int f=1;
		while (tasks.size()==0 && f<parameterFileFormats.length) {
			outFiles = allFiles.get(parameterFileFormats[f]);
			if (outFiles!=null && outFiles.size()>=0){
				for (LocalFile outFile : outFiles){
					AbstractParameterFile file = (AbstractParameterFile)outFile;
					List<ExperimentTask> fileTasks = file.getTasks();
					if (fileTasks!=null)
						tasks.addAll(fileTasks);
				}
			}
			f++;
		}		
		
		ExperimentProcess process = new ExperimentProcess(null, description, tasks);
		List<ExperimentProcess> processes = new ArrayList<ExperimentProcess>();
		processes.add(process);
		
		return processes;
	}
	
	/**
	 * Initialize process groups (systems and processes) based on content of files specified by given format
	 * @param paramAndTopoFileFormats List of file formats that specify both parameters/methods and topology
	 * @return List of experiment process groups
	 */
	protected void initializeProcessGroups(String[] paramAndTopoFileFormats)
	{
		if (paramAndTopoFileFormats==null || paramAndTopoFileFormats.length==0){
			this.processGroups = null;
		}
		else
		{
			String preferredFormat = paramAndTopoFileFormats[0];
		
			//List<ExperimentProcess> processes = new ArrayList<ExperimentProcess>();
			List<LocalFile> outputFiles = allFiles.get(preferredFormat);
	
			this.processGroups = new ArrayList<ExperimentProcessGroup>();
			if (outputFiles!=null && outputFiles.size()>=0){
				for (LocalFile outputFile : outputFiles){
					AbstractProcessGroupSetFile file = (AbstractProcessGroupSetFile)outputFile;
					this.processGroups.addAll(file.getProcessGroups());
					/*
					MolecularSystem system = null;
					List<MolecularSystem> systems = file.getMolecularSystems();
					if (systems != null && systems.size()>0){
						system = systems.get(0);
					}
					processes = new ArrayList<ExperimentProcess>();
					processes.add(process);
					
					ExperimentProcessGroup processGroup = new ExperimentProcessGroup(null, description, system, processes);
					this.processGroups.add(processGroup);*/
				}
			}
			else {
				int f=1;
				while (this.processGroups.size()==0 && f<paramAndTopoFileFormats.length) {
					outputFiles = allFiles.get(paramAndTopoFileFormats[f]);
					if (outputFiles!=null && outputFiles.size()>=0){
						for (LocalFile outputFile : outputFiles){
							AbstractProcessGroupSetFile file = (AbstractProcessGroupSetFile)outputFile;
							/*MolecularSystem system = null;
							List<MolecularSystem> systems = file.getMolecularSystems();
							if (systems != null && systems.size()>0){
								system = systems.get(0);
							}
							ExperimentProcess process = new ExperimentProcess(null, description, file.getTasks());
							processes = new ArrayList<ExperimentProcess>();
							processes.add(process);
							
							ExperimentProcessGroup processGroup = new ExperimentProcessGroup(null, description, system, processes);
							this.processGroups.add(processGroup);*/
							this.processGroups.addAll(file.getProcessGroups());
						}
					}
					f++;
				}
			}
		}
	}

	/**
	 * Get parser rule set file
	 * @return Parser rule set file
	 */
	@XmlTransient
	public DirectoryStructureDescriptor getParserRuleSet() {
		return parserRuleSet;
	}
	
	/**
	 * Get summary of computational tasks
	 * @return Summary of computational tasks
	 */
	@XmlTransient
	public SummaryExperimentTasks getExperimentTasksSummary() {
		List<ExperimentTask> tasks = this.getTasks();
		if (tasks==null || tasks.size()==0)
			return null;
		return new SummaryExperimentTasks(tasks);
	}
	
	/**
	 * Get summary of targeted molecular systems
	 * @return Summary of targeted molecular systems
	 */
	@XmlTransient
	public SummaryMolecularSystems getMolecularSystemsSummary() {
		List<MolecularSystem> systems = this.getMolecularSystems();
		if (systems==null || systems.size()==0)
			return null;
		return new SummaryMolecularSystems(systems);
	}
	
	/**
	 * Get main structure file
	 * @return main structure file
	 */
	@XmlTransient
	public LocalFile getMainStructureFile() {
		return mainStructureFile;
	}

	/**
	 * Get main topology file
	 * @return main topology file
	 */
	@XmlTransient
	public TopologyFile getMainTopologyFile() {
		return mainTopologyFile;
	}

	@Override
	public MetadataAVUList getMetadata() throws Exception
	{
		MetadataAVUList metadata = super.getMetadata();
		
		String relPathPrefix = fileDirectory.getRelativePathFromTop();
		if (relPathPrefix!=null && relPathPrefix.length()>0)
			relPathPrefix += "/";

		if (mainTopologyFile!=null){
			metadata.add(new MetadataAVU(
					ExperimentMetadata.TOPOLOGY_FILE_PATH, 
					mainTopologyFile.getRelativePathFromProjectRoot()));
		}
		if (mainStructureFile != null){
			metadata.add(new MetadataAVU(
					ExperimentMetadata.MAIN_3D_STRUCTURE_FILE, 
					mainStructureFile.getRelativePathFromProjectRoot()));
		}

		//add parameter/method metadata
		List<ExperimentTask> tasks = this.getTasks();
		for (ExperimentTask task : tasks){
			metadata.addAll(task.getMetadata());
		}
		//remove metadata that needs to be aggregated / summed
		metadata.removeAVUWithAttribute(MethodMetadata.SIMULATED_TIME);
		metadata.removeAVUWithAttribute(MethodMetadata.TIME_STEP_COUNT);
		metadata.removeAVUWithAttribute(MethodMetadata.COMPUTATIONAL_METHOD_NAME);
		metadata.removeAVUWithAttribute(PlatformMetadata.EXECUTION_TIME);
		metadata.removeAVUWithAttribute(PlatformMetadata.TASK_START_TIMESTAMP);
		metadata.removeAVUWithAttribute(PlatformMetadata.TASK_END_TIMESTAMP);
		metadata.removeAVUWithAttribute(PlatformMetadata.SOFTWARE_VERSION);
		metadata.removeAVUWithAttribute(PlatformMetadata.NUMBER_CPUS);
		metadata.removeAVUWithAttribute(PlatformMetadata.NUMBER_GPUS);
		metadata.removeAVUWithAttribute(TopologyMetadata.TOTAL_MOLECULE_CHARGE);
		
		return metadata;
	}
	
	/**
	 * Retrieve analysis files recursively
	 */
	@XmlTransient
	public HashMap<String, List<LocalFile>> getAnalysisFiles() throws Exception {
		
		HashMap<String, List<LocalFile>> analysisFiles = new HashMap<String, List<LocalFile>>();
		Collection<ArrayList<LocalFile>> fileCollection = allFiles.values();
		for (ArrayList<LocalFile> files : fileCollection){
			List<LocalFile> analysisFilesInFormat = new ArrayList<LocalFile>();
			for (LocalFile file : files){
				if (file.getAssignedClasses()!=null && file.getAssignedClasses().contains(FileMetadata.FILE_CLASS_ANALYSIS)){
					analysisFilesInFormat.add(file);
				}
			}
			if (analysisFilesInFormat.size()>0)
				analysisFiles.put(analysisFilesInFormat.get(0).getFormat(), analysisFilesInFormat);
		}
		return analysisFiles;
	}
	
	/**
	 * Find main topology file in the collection using a list of allowed file formats. 
	 * @param allowedFormats List of file formats ordered by priority
	 * @throws Exception 
	 */
	private void setMainTopologyFile(String[] allowedFormats) throws Exception
	{
		mainTopologyFile = TopologyFileCollection.findMainTopologyFile(allowedFormats, this.allFiles);
	}
	
	/**
	 * Find main strucutre file in the collection using a list of allowed file formats. 
	 * @param allowedFormats List of file formats ordered by priority
	 * @throws Exception 
	 */
	private void setMainStructureFile(String[] allowedFormats) throws Exception
	{
		//retrieve list of files that can be chosen as main structure file for this collection
		List<List<LocalFile>> fileGroupList = new ArrayList<List<LocalFile>>();
		for (String format : allowedFormats){
			List<LocalFile> fileGroup = allFiles.get(format);
			if (fileGroup!=null && fileGroup.size()>0){
				fileGroupList.add(fileGroup);
			}
		}
		
		if (fileGroupList.size()>0)
		{
			//check if the main topology file has be set already
			int f=0;
			while (mainStructureFile == null && f<fileGroupList.size()){
				List<LocalFile> fileGroup = fileGroupList.get(f);
				for (LocalFile file : fileGroup){
					if (file.getAssignedClasses()!=null && file.getAssignedClasses().contains(FileMetadata.FILE_CLASS_STRUCTURE_MAIN))
					{
						mainStructureFile = file;
						break;
					}
				}
				f++;
			}
			
			//if not, choose file that meets the criteria
			if (mainStructureFile == null){
				mainStructureFile = fileGroupList.get(0).get(0);
			}
		}
	}
}
