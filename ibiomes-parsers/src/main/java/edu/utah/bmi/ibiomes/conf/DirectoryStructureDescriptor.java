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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.utah.bmi.ibiomes.conf.DirectoryStructureRule.RuleType;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.xml.XPathReader;

/**
 * XML descriptor file for directory structure. 
 * @author Julien Thibault
 *
 */
public class DirectoryStructureDescriptor extends File {

	private static final long serialVersionUID = 2621268641630581692L;
	
	private ArrayList<DirectoryStructureRule> rules = null;

	/**
	 * Parse descriptor file
	 * @param pathname Path to the file
	 */
	public DirectoryStructureDescriptor(String pathname) {
		super(pathname);
		
		rules = new ArrayList<DirectoryStructureRule>();
		this.parseRules();
	}
	
	/**
	 * Parse rules from XML descriptor
	 */
	private void parseRules(){
		
		XPathReader xreader = new XPathReader(this.getAbsolutePath());
		NodeList nodesRule = (NodeList) xreader.read("/rules/rule", XPathConstants.NODESET);
		
		for (int r=0; r<nodesRule.getLength(); r++) 
		{
			//parse file name matching config
			Element nodeRule = (Element)nodesRule.item(r);

			String ruleType = nodeRule.getAttribute("type");
			if (ruleType == null || ruleType.length()==0 || ruleType.equals("file"))
			{
				//parse rules for file
				parseRulesForFile(null, nodeRule, "/rules/rule["+(r+1)+"]");
			}
			else if (ruleType != null && ruleType.equals("experiment"))
			{
				//parse rules for experiment
				parseRulesForExperiment(nodeRule, "/rules/rule["+(r+1)+"]");
			}
			else{
				//parse rules for directory (recursive)
				parseRulesForDirectory(nodeRule, "/rules/rule["+(r+1)+"]");
			}
			
			
		}
	}
	
	/**
	 * Parse directory rules from XML descriptor
	 */
	private void parseRulesForDirectory(Element nodeRuleDir, String xpath){
		
		String regex = nodeRuleDir.getAttribute("match");
		String description = nodeRuleDir.getAttribute("description");
		String softwareContext = nodeRuleDir.getAttribute("softwareContext");
		//get extended attributes
		XPathReader xreader = new XPathReader(this.getAbsolutePath());
		NodeList nodesRule = (NodeList) xreader.read(xpath + "/rules/rule", XPathConstants.NODESET);
		
		for (int r=0; r<nodesRule.getLength(); r++) {
			Element ruleElt = (Element)nodesRule.item(r);
			parseRulesForFile(regex, ruleElt, xpath + "/rules/rule["+(r+1)+"]");
		}
		MetadataAVUList extendedAttributes = parseMetadataNode(xpath);
		
		//create rule object
		DirectoryStructureRule rule = new DirectoryStructureRule(regex,RuleType.DIRECTORY);
		rule.setExtendedAttributes(extendedAttributes);
		if (description!=null && description.length()>0){
			rule.setDescription(description);
		}
		if (softwareContext!=null && softwareContext.length()>0){
			rule.setSoftwareContext(softwareContext);
		}
		rules.add(rule);
	}
	
	/**
	 * Parse experiment rules from XML descriptor
	 */
	private void parseRulesForExperiment(Element nodeRuleDir, String xpath)
	{
		MetadataAVUList metadata = parseMetadataNode(xpath);
		//create rule object
		DirectoryStructureRule rule = new DirectoryStructureRule("", RuleType.EXPERIMENT);
		rule.setExtendedAttributes(metadata);
		rules.add(rule);
	}
	/**
	 * Parse file rules from XML descriptor
	 */ 
	private void parseRulesForFile(String prefixRegex, Element nodeRule, String xpath){
		
		String fileClassesStr = nodeRule.getAttribute("class");
		String[] fileClassArray = null;
		fileClassArray = fileClassesStr.split("\\,");
		ArrayList<String> fileClasses = new ArrayList<String>();
		for (int c=0; c<fileClassArray.length;c++){
			String fileClass = fileClassArray[c].trim().toUpperCase();
			if (fileClass.length()>0)
				fileClasses.add(fileClass);
		}
		
		String fileFormat = nodeRule.getAttribute("format");
		String fileDescription = nodeRule.getAttribute("description");
		String fileSoftware = nodeRule.getAttribute("softwareContext");
		
		String regex = nodeRule.getAttribute("match");
		if (prefixRegex!=null && prefixRegex.length()>0){
			regex = prefixRegex + "/" + regex;
		}
		else regex = "(.*/)?(" + regex + ")";
		
		MetadataAVUList metadata = parseMetadataNode(xpath);
		
		//create rule object
		DirectoryStructureRule rule = new DirectoryStructureRule(regex, RuleType.FILE);
		rule.setFileClasses(fileClasses);
		if (fileFormat!=null && fileFormat.length()>0){
			rule.setFormat(fileFormat);
		}
		if (fileDescription!=null && fileDescription.length()>0){
			rule.setDescription(fileDescription);
		}
		if (fileSoftware!=null && fileSoftware.length()>0){
			rule.setSoftwareContext(fileSoftware);
		}
		rule.setExtendedAttributes(metadata);
		
		rules.add(rule);
	}
	
	/**
	 * Parse metadata XML node
	 * @param xpath XPath to target node
	 * @return List of metadata
	 */
	private MetadataAVUList parseMetadataNode(String xpath)
	{
		MetadataAVUList metadata = new MetadataAVUList();
		
		XPathReader xreader = new XPathReader(this.getAbsolutePath());
		NodeList avuNodes = (NodeList) xreader.read(xpath + "/metadata/avu", XPathConstants.NODESET);
		
		for (int a=0; a<avuNodes.getLength(); a++)
		{
			Element avuNode = (Element) avuNodes.item(a);
			String avuAttribute = avuNode.getAttribute("attribute").toUpperCase();
			String avuUnit = avuNode.getAttribute("unit");
			String avuValue = avuNode.getTextContent();
			
			MetadataAVU avu = new MetadataAVU(avuAttribute, avuValue, avuUnit);
			metadata.add(avu);
		}
		return metadata;
	}
	
	/**
	 * Add new rule to the descriptor
	 * @param rule Rule
	 */
	public void addRule(DirectoryStructureRule rule){
		rules.add(rule);
	}

	/**
	 * Get list of rules contained in this descriptor
	 * @return List of rules
	 */
	public List<DirectoryStructureRule> getRules(){
		return rules;
	}
	
	/**
	 * Find list of rules that apply for the file specified by the given path
	 * @param path File path
	 * @return List of rules
	 */
	public DirectoryStructureRuleSet getRuleSetForFile(String path)
	{
		DirectoryStructureRuleSet rules = new DirectoryStructureRuleSet();
		for (DirectoryStructureRule rule : this.rules)
		{
			if (rule.isValidForFile(path)) {
				rules.add(rule);
			}
		}
		return rules;
	}
		
	/**
	 * Find file format for the file specified by the given path
	 * @param path File path
	 * @return File format
	 */
	public String getFormatForFile(String path)
	{
		for (DirectoryStructureRule rule : this.rules)
		{
			if (rule.isValidForFile(path)) {
				String format = rule.getFormat();
				if (format != null && format.length()>0)
					return format;
			}
		}
		return null;
	}
	
	/**
	 * Find file description for the file specified by the given path
	 * @param path File path
	 * @return File description
	 */
	public String getDescriptionForFile(String path)
	{
		for (DirectoryStructureRule rule : this.rules)
		{
			if (rule.isValidForFile(path)) {
				String desc = rule.getDescription();
				if (desc != null && desc.length()>0)
					return desc;
			}
		}
		return null;
	}

	/**
	 * Find metadata that apply for the file specified by the given path
	 * @param path File path
	 * @return List of metadata
	 */
	public MetadataAVUList getExtendedAttributesForFile(String path)
	{
		MetadataAVUList metadata = new MetadataAVUList();
		for (DirectoryStructureRule rule : this.rules)
		{
			if (rule.isValidForFile(path)) {
				metadata.addAll(rule.getExtendedAttributes());
			}
		}
		return metadata;
	}
	
	/**
	 * Find metadata that apply to the experiment level
	 * @return List of metadata
	 */
	public MetadataAVUList getExtendedAttributesForExperiment()
	{
		MetadataAVUList metadata = new MetadataAVUList();
		for (DirectoryStructureRule rule : this.rules)
		{
			if (rule.getRuleType()==RuleType.EXPERIMENT) {
				metadata.addAll(rule.getExtendedAttributes());
			}
		}
		return metadata;
	}
	
	/**
	 * Find metadata that apply for the directory specified by the given path
	 * @param path Directory path
	 * @return List of metadata
	 */
	public MetadataAVUList getExtendedAttributesForDirectory(String path){
		MetadataAVUList metadata = new MetadataAVUList();
		for (DirectoryStructureRule rule : this.rules)
		{
			if (rule.isValidForDirectory(path)) {
				metadata.addAll(rule.getExtendedAttributes());
			}
		}
		return metadata;
	}
	
	/**
	 * Save rules to file as XML
	 */
	public void save(){
		//TODO
	}
}
