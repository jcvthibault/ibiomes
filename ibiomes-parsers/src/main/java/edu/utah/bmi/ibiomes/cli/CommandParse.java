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

package edu.utah.bmi.ibiomes.cli;

import java.util.ArrayList;
import java.util.List;

import edu.utah.bmi.Utils;
import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.conf.DirectoryStructureRuleSet;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.DirectoryParsingProgressBar;
import edu.utah.bmi.ibiomes.parse.IBIOMESListener;
import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.LocalFileFactory;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFactory;

/**
 * CLI to parse file/experiment and generate metadata
 * @author Julien Thibault, University of Utah
 *
 */
public class CommandParse extends AbstractCLICommandParse {

	private final static String markerFormat = "-f";
	private final static String markerOutput = "-o";
	private final static String markerOutputFormat = "-of";
	private final static String markerVerbose = "--verbose";

	private String outputFormat = null;
	private String outputPath = null;
	private String format = null;
	private boolean isVerbose = false;
	
	/**
	 * Execute parse command
	 * @param args Arguments
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		System.out.println("----------------");
		System.out.println("  iBIOMES parser");
		System.out.println("----------------");
		
		CommandParse parseCommand = new CommandParse();
		parseCommand.setArguments(args);
		parseCommand.execute();	
	}
	
	/**
	 * New parse command
	 */
	public CommandParse() {
		super("ibiomes-parse", "Parse set of files to build a representation of the virtual experiment");
		this.arguments.put(markerOutputFormat, new CLICommandArgument(
				markerOutputFormat, 
				"xml|html", 
				"Format of the output (XML or HTML). Default is console output.", 
				true, 
				true));
		this.arguments.put(markerOutput, new CLICommandArgument(
				markerOutput, 
				"output-file", 
				"Path to the output file.", 
				true, 
				true));
		this.arguments.put(markerFormat, new CLICommandArgument(
				markerFormat, 
				"file-format", 
				"Format of the file to parse.", 
				true, 
				true));
		this.arguments.put(markerVerbose, new CLICommandArgument(
				markerVerbose, "", 
				"Output details of experiment files", 
				false, 
				true));
	}
	
	/**
	 * Set command arguments
	 * @param args
	 * @throws Exception
	 */
	public void setArguments(String[] args) throws Exception
	{
		//check general arguments for parsing
		List<String> unusedArgList = this.setArgumentsForParsing(args);
		
		//check for arguments specific to this command
		for (int i = 0; i < unusedArgList.size(); i++) 
		{
			if (markerFormat.equals(unusedArgList.get(i))) {
		    	if (unusedArgList.size()>i)
		    		format = unusedArgList.get(i+1);
		    	else this.throwErrorMissingArgument(markerFormat);
			    i++;
			} 
		    else if (markerOutputFormat.equals(unusedArgList.get(i))) {
		    	if (unusedArgList.size()>i)
		    		outputFormat = unusedArgList.get(i+1);
		    	else this.throwErrorMissingArgument(markerOutputFormat);
			    i++;
			} 
		    else if (markerOutput.equals(unusedArgList.get(i))) {
		    	if (unusedArgList.size()>i)
		    		outputPath = unusedArgList.get(i+1);
		    	else this.throwErrorMissingArgument(markerOutput);
			    i++;
			}
		    else if (markerVerbose.equals(unusedArgList.get(i))) {
		    	isVerbose = true;
			    i++;
			}
		    else System.out.println("\nWARNING: unknown option '" + unusedArgList.get(i) + "'");
		}
	}
	
	/**
	 * Parse file/experiment and generate metadata.
	 * @throws Exception
	 */
	public void execute() throws Exception
	{
		LocalDirectory coll = null;

		if (localPath != null)
		{
			//read descriptor file if exists
			DirectoryStructureDescriptor desc = null; 
			if (xmlDescPath != null && xmlDescPath.length()!=0){
				System.out.println("Loading metadata generation rules...");
				desc = new DirectoryStructureDescriptor(xmlDescPath);
			}
			
			//if input is a file
			if (inputFile.isFile()) 
			{
				LocalFileFactory fileFactory = LocalFileFactory.instance();
				LocalFile localFile = null;
				MetadataAVUList metadata = null;
				if (desc != null){
					DirectoryStructureRuleSet rules = desc.getRuleSetForFile(localPath);
					metadata = rules.getExtendedAttributes();
					format = rules.getFileFormat();
				}
				if (format!=null)
					localFile = fileFactory.getFileInstanceFromFormat(localPath, format);
				else 
					localFile = fileFactory.getFile(localPath, software);
				localFile.setExtendedAttributes(metadata);
				if (IBIOMESConfiguration.getInstance().isOutputToConsole())
					System.out.println(localFile.getMetadata().toString());
			}
			else //experiment/directory
			{
				ExperimentFactory expFactory = new ExperimentFactory(localPath, depth);
				
				//create listener for progress bar
				List<IBIOMESListener> listeners = null;
				if (IBIOMESConfiguration.getInstance().isOutputToConsole()){
					int nFiles = Utils.countNumberOfFilesInDirectory(localPath);
					listeners = new ArrayList<IBIOMESListener>();
					listeners.add((IBIOMESListener)new DirectoryParsingProgressBar(nFiles, "Parsing file"));
				}
				//parse
				coll = expFactory.parseDirectoryForMetadata(software, desc, listeners);
				if (outputFormat == null)
				{
					if (IBIOMESConfiguration.getInstance().isOutputToConsole()){
						if (isVerbose)
							coll.dumpToText();
						else {
							dumpDirInfo(coll);
						}
					}
				}
				else {
					if (outputFormat.toLowerCase().matches("html")){
						coll.storeToHTML(outputPath);
					}
					else if (outputFormat.toLowerCase().matches("xml")){
						coll.storeToXML(outputPath);
					}
					else {
						System.out.println("\nERROR: unknown output format: " + outputFormat + "\n");
						this.printSynopsis();
				    	System.exit(1);
					}
				}
			}
			
		}
		else {
			System.out.println("\nERROR: missing or non-valid arguments.\n");
			this.printSynopsis();
	    	System.exit(1);
		}
	}
	
	private static void dumpDirInfo(LocalDirectory collection) throws Exception
	{
		System.out.println("-----------------------------------------------------------");
		System.out.println("Experiment: " + collection.getAbsolutePath());
		MetadataAVUList dirMD = collection.getMetadata();
		for (MetadataAVU avu : dirMD){
			String avuStr = avu.toString().replaceAll("\\n", " ");
			if (avuStr.length()>100){
				avuStr = avuStr.substring(0, 100) + "...\"";
			}
			System.out.println("\t" + avuStr);
		}
		System.out.println("");
	}
}
