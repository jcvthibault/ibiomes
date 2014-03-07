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

package edu.utah.bmi.ibiomes.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import edu.utah.bmi.ibiomes.parse.LocalFile;

/**
 * Policy for task grouping. For example one can specify if all the parallel 
 * REMD run are in different folders, or if REMD run groups 
 * should be inferred from the associated file names, using a given pattern.
 * <br/>
 * A pattern contains characters that are shared by all targeted tasks (e.g. 
 * 'rep.' prefix , '.out' suffix), characters that are shared only by tasks 
 * in the same group, and characters that are differentiate the tasks within 
 * the same group.
 * <br/>
 * For example, assume 2 REMD runs with 10 replica each. The output could look 
 * like this: rep.000.000.out, rep.000.001.out, ..., rep.000.009.out for the 
 * first group (run) and rep.001.000.out, rep.001.001.out, ..., rep.001.009.out 
 * for the second group. The file pattern can be described as 
 * rep.[group-id].[task-id].out. 
 * <br/>Syntax: replace by '$' the sequence of numeric characters representing 
 * the group ID and by '#' the sequence of numeric characters that identify  
 * tasks within their group. Using the same example the file pattern would be: 
 * 'rep.$.#.out'.
 *    
 * @author Julien Thibault - University of Utah, BMI
 *
 */
public class TaskGroupingPolicy {

	public static final String GROUP_ID_CHAR = "$";
	public static final String TASK_ID_CHAR = "#";
	public static final String NO_ASSIGNED_GROUP = "NO_ASSIGNED_GROUP";

	private final Logger logger = Logger.getLogger(TaskGroupingPolicy.class);
	
	private String filePatternForGrouping;
	private String filterRegex;
	private boolean hasGroupId = false;
	private Pattern pattern = null;
	
	/**
	 * Create new task grouping policy
	 * @param filePatternForGrouping String file name pattern.
	 */
	public TaskGroupingPolicy(String filePatternForGrouping){
		this.setFilePattern(filePatternForGrouping);
	}
	
	/**
	 * Get file pattern to identify the task group a file belongs to
	 * @return File pattern
	 */
	public String getFilePattern() {
		return filePatternForGrouping;
	}

	/**
	 * Set file pattern to identify the task group a file belongs to
	 * @param filePatternForGrouping File pattern
	 */
	public void setFilePattern(String filePatternForGrouping)
	{
		this.filePatternForGrouping = filePatternForGrouping;
		this.filterRegex = this.filePatternForGrouping.
				replaceAll("\\.", "\\\\.").
				replaceAll("\\"+TASK_ID_CHAR, "(?<task>\\\\d+)").
				replaceAll("\\"+GROUP_ID_CHAR, "(?<group>\\\\d+)");
		this.pattern  = Pattern.compile(filterRegex);
		this.hasGroupId = (this.filePatternForGrouping.indexOf(GROUP_ID_CHAR) >-1) ;
	}

	/**
	 * Get regular expression used to filter candidates for grouping
	 * @return Regular expression used to filter candidates for grouping
	 */
	public String getRegexForFiltering() {
		return filterRegex;
	}
	
	/**
	 * Whether task grouping occurs only for files within the same folder, instead of file name pattern matching.
	 * @return Whether task grouping occurs only for files within the same folder or not
	 */
	public boolean isGroupByFolderOnly() {
		return !hasGroupId;
	}
	
	/**
	 * Apply grouping policy to list of files
	 * @param taskFiles List of files associated to tasks.
	 * @return Groups of files/tasks with group identifier.
	 */
	public HashMap<String, List<LocalFile>> apply(List<LocalFile> taskFiles){
		//group by folder in any case first
		HashMap<String, List<LocalFile>> taskGroups = applyGroupByFolder(taskFiles);
		if (!this.hasGroupId)
			return taskGroups;
		else{
			HashMap<String, List<LocalFile>> newTaskGroups = new HashMap<String, List<LocalFile>>();
			Set<String> folderKeys = taskGroups.keySet();
			//if only one folder
			if (folderKeys.size()==1)
				return applyGroupByFilePattern(taskFiles);
			else{
				//for each folder
				for (String folderKey : folderKeys){
					//apply file name pattern matching
					HashMap<String, List<LocalFile>> currFolderTaskGroups = applyGroupByFilePattern(taskGroups.get(folderKey));
					//copy results to final hashmap
					Set<String> groupKeys = currFolderTaskGroups.keySet();
					for (String groupKey : groupKeys){
						if (!groupKey.equals(NO_ASSIGNED_GROUP))
							newTaskGroups.put("[" + folderKey + "]["+ groupKey + "]", currFolderTaskGroups.get(groupKey));
						else
							newTaskGroups.put(NO_ASSIGNED_GROUP, currFolderTaskGroups.get(groupKey));
					}
				}
				return newTaskGroups;
			}
		}
	}

	/**
	 * Group files by file name pattern
	 * @param taskFiles List of files associated to tasks.
	 * @return Groups of files/tasks with group identifier (folder path).
	 */
	private HashMap<String, List<LocalFile>> applyGroupByFilePattern(List<LocalFile> taskFiles) {
		
		HashMap<String, List<LocalFile>> groups = new HashMap<String, List<LocalFile>>();
		for (LocalFile taskFile : taskFiles){
			String groupId = NO_ASSIGNED_GROUP;
			String taskId = null;
			String fileName = taskFile.getName();
			//if the file is a candidate for grouping
			if (fileName.matches(filterRegex))
			{
				//retrieve group ID and task ID
				Matcher m = pattern.matcher(fileName);
				m.find();
				groupId = m.group("group");
				taskId = m.group("task");
			}
			if (!groups.containsKey(groupId)){
				groups.put(groupId, new ArrayList<LocalFile>());
			}
			List<LocalFile> group = groups.get(groupId);
			group.add(taskFile);
		}
		return groups;
	}

	/**
	 * Group files by folder
	 * @param taskFiles List of files associated to tasks.
	 * @return Groups of files/tasks with group identifier (folder path).
	 */
	private HashMap<String, List<LocalFile>> applyGroupByFolder(List<LocalFile> taskFiles){

		HashMap<String, List<LocalFile>> groups = new HashMap<String, List<LocalFile>>();
		for (LocalFile taskFile : taskFiles){
			String groupId = NO_ASSIGNED_GROUP;
			if (taskFile.getName().matches(filterRegex)){
				groupId = taskFile.getParent();
			}
			
			if (!groups.containsKey(groupId)){
				groups.put(groupId, new ArrayList<LocalFile>());
			}
			List<LocalFile> group = groups.get(groupId);
			group.add(taskFile);
		}
		return groups;
	}

}
