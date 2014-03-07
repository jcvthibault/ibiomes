/* iBIOMES - Integrated Biomolecular Simulations
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

package edu.utah.bmi.ibiomes.local.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.conf.TaskGroupingPolicy;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.parse.IBIOMESFileParserException;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.LocalFileFactory;

/**
 * Test to task grouping by folder or file name pattern
 * @author Julien Thibault, University of Utah
 *
 */
public class TestTaskGrouping{

	private final Logger logger = Logger.getLogger(TestTaskGrouping.class);

	String remdDirsByFileName = TestCommon.TEST_DATA_DIR + "/amber/remd/remd-group-test/byname";
	String remdDirsByFolder = TestCommon.TEST_DATA_DIR + "/amber/remd/remd-group-test/byfolder";

	
	@Test
	public void testGroupByFolder() throws Exception {
		
		TaskGroupingPolicy grpPolicy = new TaskGroupingPolicy("rep.#.out");
		ArrayList<LocalFile> localFiles = getFileListing(remdDirsByFolder);
		
		//group files in directory
		HashMap<String, List<LocalFile>> fileGroups = grpPolicy.apply(localFiles);
		Set<String> groupIds = fileGroups.keySet();

		/*for (String groupId : groupIds){
			System.out.println("["+ groupId + "]");
			List<LocalFile> groupLocalFiles = fileGroups.get(groupId);
			//for each file
			for (LocalFile file : groupLocalFiles){
				System.out.println("\t" + file.getAbsolutePath());
			}
		}*/
		Assert.assertEquals(4, groupIds.size());
	}
	
	@Test
	public void testGetDefaultPolicy() throws Exception 
	{
		TaskGroupingPolicy grpPolicy = IBIOMESConfiguration
				.getInstance(TestCommon.TEST_DATA_DIR + "/config/ibiomes-parser2.properties", true)
				.getDefaultTaskGroupingPolicy();

		Assert.assertEquals("rep.$.#.out", grpPolicy.getFilePattern());
		Assert.assertEquals("rep\\.(?<group>\\d+)\\.(?<task>\\d+)\\.out", grpPolicy.getRegexForFiltering());
	}
	
	@Test
	public void testGroupByFileNamePattern() throws Exception 
	{
		String filePattern = "mdout.$.#";
		TaskGroupingPolicy grpPolicy = new TaskGroupingPolicy(filePattern);
		ArrayList<LocalFile> localFiles = getFileListing(remdDirsByFileName);
		
		//group files in directory
		HashMap<String, List<LocalFile>> fileGroups = grpPolicy.apply(localFiles);
		Set<String> groupIds = fileGroups.keySet();
		/*//for each group
		for (String groupId : groupIds){
			System.out.println("["+ groupId + "]");
			List<LocalFile> groupLocalFiles = fileGroups.get(groupId);
			//for each file
			for (LocalFile file : groupLocalFiles){
				System.out.println("\t" + file.getAbsolutePath());
			}
		}*/
		Assert.assertEquals(5, groupIds.size());
	}
	
	private ArrayList<LocalFile> getFileListing(String dirPath) throws IBIOMESFileParserException, IOException{
		File dir = new File(dirPath);
		String[] filesInDir = listAllFiles(dir);
		ArrayList<LocalFile> localFiles = new ArrayList<LocalFile>();
		for (int f=0;f<filesInDir.length;f++)
		{
			File file = new File(dirPath + "/" + filesInDir[f]);
			LocalFileFactory fileFactory = LocalFileFactory.instance();
			LocalFile parsedFile = fileFactory.getFile(file.getAbsolutePath(), Software.AMBER);
			localFiles.add(parsedFile);
		}
		return localFiles;
	}
	
	/**
	 * List recursively all the files and the sub-collections in this collection
	 * @return List of files
	 */
	private String[] listAllFiles(File dir)
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
				files.addAll(listAllFiles(rootDir, path));
			}
		}
		return files;
	}
}
