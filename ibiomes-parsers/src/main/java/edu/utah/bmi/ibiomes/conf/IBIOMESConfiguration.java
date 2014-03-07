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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import edu.utah.bmi.Utils;

/**
 * Singleton defining the general configuration of the parsers
 * @author Julien Thibault, University of Utah
 *
 */
public class IBIOMESConfiguration {

	private final Logger logger = Logger.getLogger(IBIOMESConfiguration.class);

	public static final String PATH_FOLDER_SEPARATOR  = (Utils.isWindows() ? "\\" : "/");
	public static final String IBIOMES_VERSION = "11-12-2013";
	public static final String IBIOMES_HOME_ENV_VAR = "IBIOMES_HOME";
	
	private final static String IBIOMES_CONFIG_FILE = "config"+PATH_FOLDER_SEPARATOR+"ibiomes-parser.properties";
	
	private final static String PROPERTY_DEFAULT_SOFTWARE_CONTEXT 		= "DEFAULT_SOFTWARE_CONTEXT";
	private final static String PROPERTY_DEFAULT_PARSER_RULE_XML_FILE 	= "DEFAULT_PARSER_RULE_XML_FILE";
	private final static String PROPERTY_OUTPUT_TO_CONSOLE 				= "OUTPUT_TO_CONSOLE";
	private final static String PROPERTY_OUTPUT_ERROR_STACK_TO_CONSOLE  = "OUTPUT_ERROR_STACK_TO_CONSOLE";
	private final static String PROPERTY_TASK_GROUP_ITEM_PATTERN		= "TASK_GROUP_ITEM_PATTERN";
	private final static String PROPERTY_TIMINGS_ON						= "TIMINGS_ON";
	
	private DirectoryStructureDescriptor defaultParserRuleFile = null;
	private String defaultParserRuleFilePath = null;
	private String defaultSoftwareContext = null;
	private TaskGroupingPolicy defaultTaskGroupingPolicy = null;
	private String ibiomesHomeDirectory;
	private boolean outputToConsole = true;
	private boolean outputErrorStackToConsole = false;
	private static IBIOMESConfiguration ibiomesConfiguration;
	private File propertiesFile = null;
	private String propertiesFilePath = null;
	private boolean timingsOn = false;

	/**
	 * 
	 * @throws IOException
	 * @throws XPathExpressionException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	private IBIOMESConfiguration(String propertiesFilePath) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException
	{		
		//check that $IBIOMES_HOME is set
		ibiomesHomeDirectory = System.getenv(IBIOMES_HOME_ENV_VAR);
		if (ibiomesHomeDirectory == null || ibiomesHomeDirectory.length()==0) {
			throw new FileNotFoundException("Environment variable '"+IBIOMES_HOME_ENV_VAR+"' was not set");
		}
		//load properties
		Properties props = new Properties();
		if (propertiesFilePath==null){
			this.propertiesFilePath = ibiomesHomeDirectory + PATH_FOLDER_SEPARATOR + IBIOMES_CONFIG_FILE;
		}
		else
			this.propertiesFilePath = propertiesFilePath;
		if (Files.exists(Paths.get(this.propertiesFilePath))){
			propertiesFile = new File(this.propertiesFilePath);
			this.propertiesFilePath = propertiesFile.getCanonicalPath();
    		props.load(new FileInputStream(propertiesFile));
    		String defaultSoftwareContextProp = props.getProperty(PROPERTY_DEFAULT_SOFTWARE_CONTEXT);
    		String defaultParserRuleFilePathProp = props.getProperty(PROPERTY_DEFAULT_PARSER_RULE_XML_FILE);
    		String outputToConsoleProp = props.getProperty(PROPERTY_OUTPUT_TO_CONSOLE);
    		String outputErrorStackProp = props.getProperty(PROPERTY_OUTPUT_ERROR_STACK_TO_CONSOLE);
    		String taskGroupingPatternProp = props.getProperty(PROPERTY_TASK_GROUP_ITEM_PATTERN);
    		String propertiesTimings = props.getProperty(PROPERTY_TIMINGS_ON);

    		//set output to console flag
    		if (outputErrorStackProp!=null 
    				&& outputErrorStackProp.trim().toUpperCase().matches("(YES)|(TRUE)")){
    			this.outputErrorStackToConsole = true;
    		}
    		
    		//set output to console flag
    		if (outputToConsoleProp!=null 
    				&& outputToConsoleProp.trim().toUpperCase().matches("(NO)|(FALSE)")){
    			this.outputToConsole = false;
    		}
    		
    		//set timings flag
    		if (propertiesTimings!=null 
    				&& propertiesTimings.trim().toUpperCase().matches("(YES)|(TRUE)")){
    			this.setHasCollectTimingsOn(true);
    		}
    		
    		//load default software context
    		if (defaultSoftwareContextProp!=null
    				&& defaultSoftwareContextProp.trim().length()!=0
    				&& !defaultSoftwareContextProp.trim().toUpperCase().matches("(NULL)|(NONE)")){
    			this.defaultSoftwareContext = defaultSoftwareContextProp;
    		}

    		//load default task grouping policy if defined
    		if (taskGroupingPatternProp!=null
    				&& taskGroupingPatternProp.trim().length()!=0
    				&& !taskGroupingPatternProp.trim().toUpperCase().matches("(NULL)|(NONE)")){
				this.defaultTaskGroupingPolicy = new TaskGroupingPolicy(taskGroupingPatternProp);
    		}
    		
    		//load default XML descriptor file
    		if (defaultParserRuleFilePathProp!=null && defaultParserRuleFilePathProp.trim().length()!=0)
    		{
    			this.defaultParserRuleFilePath = defaultParserRuleFilePathProp.trim();
    			try{
    				DirectoryStructureDescriptor xmlDesc = new DirectoryStructureDescriptor(defaultParserRuleFilePathProp);
    				defaultParserRuleFile = xmlDesc;
    			}
    			catch(Exception e){
    				defaultParserRuleFile = null;
    				logger.warn("Parser rule XML file not found at "+defaultParserRuleFilePathProp);
    			}
    		}
		}
		else {
			logger.warn("iBIOMES configuration file not found at "+propertiesFilePath);
		}
	}
	
	/**
	 * Get current configuration or load settings from default file located in $IBIOMES_HOME/config/.
	 * @throws Exception 
	 */
	public static IBIOMESConfiguration getInstance() throws Exception{
		if (ibiomesConfiguration == null)
			ibiomesConfiguration = new IBIOMESConfiguration(null);
		return ibiomesConfiguration;
	}
	
	/**
	 * Get current configuration or load settings from default file located in $IBIOMES_HOME/config/.
	 * @throws Exception 
	 */
	public static IBIOMESConfiguration getInstance(boolean forceReload) throws Exception{
		if ( (ibiomesConfiguration == null) ||forceReload){
			ibiomesConfiguration = new IBIOMESConfiguration(null);
		}
		return ibiomesConfiguration;
	}
	
	/**
	 * Load iBIOMES configuration settings from the given file
	 * @param propertiesFilePath Path to property file
	 * @throws Exception 
	 */
	public static IBIOMESConfiguration getInstance(String propertiesFilePath, boolean forceReload) throws Exception{
		if (Files.exists(Paths.get(propertiesFilePath))){
			if ( (ibiomesConfiguration == null) ||forceReload){
				ibiomesConfiguration = new IBIOMESConfiguration(propertiesFilePath);
			}
			return ibiomesConfiguration;
		}
		else{
			throw new IOException("Config file "+ propertiesFilePath +" cannot be read!");
		}
	}
	/**
	 * Get flag for output of progress messages and warning to console
	 * @return Flag for output of progress messages and warning to console
	 */
	public boolean isOutputToConsole() {
		return outputToConsole;
	}

	/**
	 * Set flag for output of progress messages and warning to console
	 * @param outputToConsole Flag for output of progress messages and warning to console
	 */
	public void setOutputToConsole(boolean outputToConsole) {
		this.outputToConsole = outputToConsole;
	}

	/**
	 * Get flag for output of error stack
	 * @return Flag for output of error stack
	 */
	public boolean isOutputErrorStackToConsole() {
		return this.outputErrorStackToConsole;
	}
	
	/**
	 * Set flag for output of error stack
	 * @param outputErrorStackToConsole Flag for output of error stack
	 */
	public void setOutputErrorStackToConsole(boolean outputErrorStackToConsole){
		this.outputErrorStackToConsole = outputErrorStackToConsole;
	}
	
	/**
	 * Get path to default parser rule file
	 * @return Path to default parser rule file
	 */
	public String getDefaultParserRuleFilePath() {
		return defaultParserRuleFilePath;
	}

	/**
	 * Set path to default parser rule file
	 * @param defaultParserRuleFilePath Path to default parser rule file
	 */
	public void setDefaultParserRuleFilePath(String defaultParserRuleFilePath) {
		this.defaultParserRuleFilePath = defaultParserRuleFilePath;
		if (this.defaultParserRuleFilePath!=null){
			this.defaultParserRuleFile = new DirectoryStructureDescriptor(this.defaultParserRuleFilePath);
		}
		else this.defaultParserRuleFilePath = null;
	}
	
	/**
	 * Get default parser rule file (XML descriptor)
	 * @return Parser rule file (XML descriptor)
	 */
	public DirectoryStructureDescriptor getDefaultParserRuleFile() {
		return defaultParserRuleFile;
	}

	/**
	 * Set default parser rule file (XML descriptor)
	 * @param defaultParserRuleFile Parser rule file (XML descriptor)
	 */
	public void setDefaultParserRuleFile(
			DirectoryStructureDescriptor defaultParserRuleFile) {
		if (this.defaultParserRuleFile!=null){
			this.defaultParserRuleFilePath = defaultParserRuleFile.getAbsolutePath();
		}
		else this.defaultParserRuleFilePath = null;
		this.defaultParserRuleFile = defaultParserRuleFile;
	}

	/**
	 * Get default software context
	 * @return Default software context
	 */
	public String getDefaultSoftwareContext() {
		return defaultSoftwareContext;
	}

	/**
	 * Set default software context
	 * @param defaultSoftwareContext Default software context
	 */
	public void setDefaultSoftwareContext(String defaultSoftwareContext) {
		this.defaultSoftwareContext = defaultSoftwareContext;
	}

	/**
	 * Get default policy for task grouping (e.g parallel REMD runs for different replicas)
	 * @return Default policy for task grouping
	 */
	public TaskGroupingPolicy getDefaultTaskGroupingPolicy() {
		return defaultTaskGroupingPolicy;
	}

	/**
	 * Set default policy for task grouping (e.g parallel REMD runs for different replicas)
	 * @param defaultTaskGroupingPolicy Default policy for task grouping
	 */
	public void setDefaultTaskGroupingPolicy(TaskGroupingPolicy defaultTaskGroupingPolicy) {
		this.defaultTaskGroupingPolicy = defaultTaskGroupingPolicy;
	}

	/**
	 * Check whether timings are being collected or not
	 * @return True if on
	 */
	public boolean hasCollectTimingsOn() {
		return timingsOn;
	}

	/**
	 * Set whether timings are being collected or not
	 * @param timingsOn True if on
	 */
	public void setHasCollectTimingsOn(boolean timingsOn) {
		this.timingsOn = timingsOn;
	}
	
	/**
	 * Print current iBIOMES configuration
	 */
	public void printProperties(){
		System.out.println("| Home directory = " + this.ibiomesHomeDirectory);
		System.out.println("| Configuration file = " + this.propertiesFilePath);
		if (propertiesFile==null){
			System.out.println("|  >> WARNING: Configuration file cannot be read");
		}
		else{
			System.out.println("| Console output = " + String.valueOf(outputToConsole));
			//load default software context if necessary
			String softwareContext = this.getDefaultSoftwareContext();
			if (softwareContext==null)
				softwareContext = "not specified";
			System.out.println("| Default software context = " + softwareContext);
			
			//load default XML descriptor file if necessary
			String descriptorFilePath = this.getDefaultParserRuleFilePath();
			if (descriptorFilePath==null)
				descriptorFilePath = "not specified";
			System.out.println("| Default parser rule file = " + descriptorFilePath);
			
			//load default task grouping policy if necessary
			TaskGroupingPolicy taskGrouping = this.getDefaultTaskGroupingPolicy();
			if (taskGrouping!=null){
				System.out.println("| Default task grouping policy = [" + taskGrouping.getRegexForFiltering() + "]"
						+ "["+ taskGrouping.getFilePattern() + "]");
			}
		}
	}
	
	/**
	 * get version of iBIOMES parsers
	 * @return version
	 */
	public static String getVersion(){
		return IBIOMES_VERSION + " [" + Utils.getOperatingSystem() + "]";
	}

}
