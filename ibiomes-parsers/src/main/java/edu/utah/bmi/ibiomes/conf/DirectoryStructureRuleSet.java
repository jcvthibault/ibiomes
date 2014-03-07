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
import java.util.List;

import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.LocalFile;

/**
 * Set of rules for metadata definition. This class can be used to reference multiple rules that apply to a particular file.
 * @author Julien Thibault, University of Utah
 *
 */
public class DirectoryStructureRuleSet extends ArrayList<DirectoryStructureRule>{

	private static final long serialVersionUID = -8539541829321825262L;

	/**
	 * Get file format defined by this rule set
	 * @return File format
	 */
	public String getFileFormat(){
		for (DirectoryStructureRule rule : this){
			String format = rule.getFormat();
			if (format!=null && 
				format.length()>0 && 
				!format.toLowerCase().equals(LocalFile.FORMAT_UNKNOWN.toLowerCase())){
				return format;
			}
		}
		return null;
	}
	
	/**
	 * Retrieve list of classes defined by the rule set.
	 * @return List of classes
	 */
	public List<String> getFileClasses(){
		List<String> metadata = new ArrayList<String>();
		for (DirectoryStructureRule rule : this){
			for (String fileClass : rule.getFileClasses()){
				metadata.add(fileClass);
			}
		}
		return metadata;
	}
	
	/**
	 * Get description defined by this rule set
	 * @return File description
	 */
	public String getDescription(){
		for (DirectoryStructureRule rule : this){
			String description = rule.getDescription();
			if (description!=null && 
					description.length()>0){
				return description;
			}
		}
		return null;
	}
	
	/**
	 * Get software context to use to parse the file/directory
	 * @return Software context
	 */
	public String getSoftwareContext(){
		for (DirectoryStructureRule rule : this){
			String sw = rule.getSoftwareContext();
			if (sw!=null && 
					sw.length()>0){
				return sw;
			}
		}
		return null;
	}
	
	/**
	 * Get all extended attributes defined in the rule set
	 * @return List of metadata.
	 */
	public MetadataAVUList getExtendedAttributes(){
		MetadataAVUList metadata = new MetadataAVUList();
		for (DirectoryStructureRule rule : this){
			metadata.addAll(rule.getExtendedAttributes());
		}
		return metadata;
	}
	
}
