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

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

/**
 * Rules set in the directory descriptor file. Rules define metadata for matching files.
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="rule")
public class DirectoryStructureRule 
{	
	private String regex = null;
	private List<String> fileClasses = null;
	private MetadataAVUList extendedAttributes;
	private String format = null;
	public enum RuleType {FILE, DIRECTORY, EXPERIMENT};
	private RuleType ruleType;
	private String description;
	private String softwareContext;
	
	/**
	 * Create new rule
	 * @param regex Regular expression
	 */
	public DirectoryStructureRule(String regex, RuleType ruleType){
		this.setRegex(regex);
		this.ruleType = ruleType;
	}
	
	/**
	 * Create new rule
	 * @param regex Regular expression
	 */
	public DirectoryStructureRule(String regex, RuleType ruleType, MetadataAVUList metadata, List<String> classes){
		this.setRegex(regex);
		this.ruleType = ruleType;
		this.setExtendedAttributes(metadata);
		this.setFileClasses(classes);
	}

	/** 
	 * Get the regular expression that is used to match a set of file names or path
	 * @return Regular expression
	 */
	public String getRegex() {
		return regex;
	}
	/** 
	 * Set the regular expression that is used to match a set of file names or path
	 * @param regex Regular expression
	 */
	public void setRegex(String regex) {
		regex = regex.replaceAll("\\.", "\\\\.");
		regex = regex.replaceAll("\\*", ".*");
		regex = regex.replaceAll("\\$", ".");
		//add wildcard for parent directories
		regex = "(.*/)?" + regex;
		this.regex = regex;
	}
	
	/**
	 * Get file class
	 * @return File class
	 */
	public List<String> getFileClasses() {
		return this.fileClasses;
	}
	/**
	 * Set file classes
	 * @param fileClasses File classes
	 */
	public void setFileClasses(List<String> fileClasses) {
		this.fileClasses = fileClasses;
	}
	
	/**
	 * Get file format assigned by this rule
	 * @return file format
	 */
	public String getFormat() {
		return this.format;
	}
	
	/**
	 * Set file format
	 * @param fileFormat File format
	 */
	public void setFormat(String fileFormat) {
		this.format  = fileFormat;
	}
	
	/**
	 * Get file description
	 * @return File description
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Set file description
	 * @param description File description
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**
	 * Get software context to use to parse the file/directory
	 * @return Software context
	 */
	public String getSoftwareContext() {
		return softwareContext;
	}

	/**
	 * Set software context to use to parse the file/directory
	 * @param softwareContext Software context
	 */
	public void setSoftwareContext(String softwareContext) {
		this.softwareContext = softwareContext;
	}
	
	/**
	 * Get list of metadata defined by this rule
	 * @return List of metadata defined by this rule
	 */
	public MetadataAVUList getExtendedAttributes() {
		return extendedAttributes;
	}
	/**
	 * Set list of metadata defined by this rule
	 * @param metadata List of metadata defined by this rule
	 */
	public void setExtendedAttributes(MetadataAVUList metadata) {
		this.extendedAttributes = metadata;
	}
	/**
	 * Get rule type
	 * @return Rule type
	 */
	public RuleType getRuleType() {
		return this.ruleType;
	}
	/**
	 * Set rule type
	 * @param ruleType Rule type
	 */
	public void setRuleType(RuleType ruleType) {
		this.ruleType = ruleType;
	}
	
	/**
	 * Check if the rule applies to the given file
	 * @param filePath Path to the file (relative to project top directory)
	 * @return True if the rule applies to the given file
	 */
	public boolean isValidForFile(String filePath)
	{
		if (this.ruleType==RuleType.FILE && isValidForPath(filePath)){
			return true;
		}
		else return false;
	}
	
	/**
	 * Check if the rule applies to the given directory
	 * @param dirPath Path to the directory (relative to project top directory)
	 * @return True if the rule applies to the given directory
	 */
	public boolean isValidForDirectory(String dirPath)
	{
		if (this.ruleType==RuleType.DIRECTORY && isValidForPath(dirPath)){
			return true;
		}
		else return false;
	}
	
	/**
	 * Check if the rule applies to the experiment level
	 * @return True if the rule applies to the experiment
	 */
	public boolean isValidForExperiment()
	{
		if (this.ruleType==RuleType.EXPERIMENT){
			return true;
		}
		else return false;
	}
	
	/**
	 * Check if the rule applies to the given file
	 * @param filePath Path to the file (relative to project top directory)
	 * @return True if the rule applies to the given file
	 */
	private boolean isValidForPath(String filePath)
	{
		if (filePath.matches(this.regex)){
			return true;
		}
		else return false;
	}

	@Override
	public String toString()
	{
		String res = ruleType.name() + " rule\n";
		res += "   |- Pattern:          " + this.regex + "\n";
		res += "   |- Description:      " + this.description + "\n";
		res += "   |- Software context: " + this.softwareContext + "\n";
		if (ruleType==RuleType.FILE){
			res += "   |- Format:           " + this.format + "\n";
		}
		if (ruleType==RuleType.FILE && this.fileClasses!=null && this.fileClasses.size()>0){
			res += "   |- Classes\n";
			for (String fileClass : fileClasses){
				res += "        |- " + fileClass + "\n";
			}
		}
		if (this.extendedAttributes != null && this.extendedAttributes.size()>0){
			res += "   |- Extended attributes";
			for (MetadataAVU avu : this.extendedAttributes){
				res += "\n        |- " + avu.getAttribute() + " = " + avu.getValue();
				if (avu.getUnit() != null && avu.getUnit().length()>0){
					res += " (" + avu.getUnit() + ")";
				}
			}
		}
		return res;
	}
}
