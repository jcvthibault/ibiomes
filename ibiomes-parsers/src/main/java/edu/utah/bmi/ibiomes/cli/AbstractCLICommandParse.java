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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.conf.TaskGroupingPolicy;

/**
 * Abstract CLI to parse file/experiments and generate metadata
 * @author Julien Thibault, University of Utah
 *
 */
public abstract class AbstractCLICommandParse extends CLICommand implements CommandInterface {

	protected final static String markerLocalInput = "-i";
	protected final static String markerSoftware = "-s";
	protected final static String markerDepthIndependentGroups = "-depth";
	protected final static String markerXmlDesc = "-x";
	protected final static String markerExternalURL = "-url";
	protected final static String markerREMDGrouping = "-remd";
	protected final static String markerSilent = "--silent";
	
	protected String localPath = null;
	protected File inputFile = null;
	protected String software = null;
	protected String outputFormat = null;
	protected String xmlDescPath = null;
	protected String externalUrl = null;
	protected int depth = 0;
	protected String remdGrouping = null;
	protected boolean outputToConsole = true;

	/**
	 * New parse command
	 */
	public AbstractCLICommandParse(String name, String description) {
		super(name, description);
		this.arguments.put(markerLocalInput, new CLICommandArgument(
				markerLocalInput, 
				"input",
				"Path to the input file or to the directory containing the files to parse", 
				true, 
				false));
		this.arguments.put(markerSoftware, new CLICommandArgument(
				markerSoftware, 
				"software", 
				"Name of the main software package used to run the simulations/calculations (e.g. Amber, Gaussian, NWchem)", 
				true, 
				true));
		this.arguments.put(markerXmlDesc, new CLICommandArgument(
				markerXmlDesc, 
				"xml-descriptor", 
				"Path to the XML descriptor defining the parsing rules", 
				true, 
				true));
		this.arguments.put(markerExternalURL, new CLICommandArgument(
				markerExternalURL, 
				"external-url", 
				"URL that points to root dir of eperiment (use only if directory is already available online)", 
				true, 
				true));
		this.arguments.put(markerDepthIndependentGroups, new CLICommandArgument(
				markerDepthIndependentGroups, 
				"depth-indep-groups", 
				"Depth in the file tree that is used to represent independent groups of files that should be parsed independently (e.g. different simulations).", 
				true, 
				true));
		this.arguments.put(markerREMDGrouping, new CLICommandArgument(
				markerREMDGrouping, "file-name-pattern", 
				"Output file name pattern used to create group of REMD replicas. "
				+ "Syntax: replace by '$' the sequence of numeric characters representing "
				+ "the group ID and by '#' the sequence of numeric characters that identify "
				+ "tasks within their group. "
				+ "For example the file pattern \"rep.$.#.out\" would match rep.001.002.out "
				+ "where 001 is the group ID and 002 the replica ID.", 
				true, 
				true));
		this.arguments.put(markerSilent, new CLICommandArgument(
				markerSilent, "", 
				"Limit console output to a minimum", 
				false, 
				true));
	}
	
	/**
	 * Parse arguments
	 * @param args List of arguments
	 * @throws Exception
	 */
	protected List<String> setArgumentsForParsing(String[] args) throws Exception
	{
		//check if help menu is requested
		checkHelp(args);
		
		//check for other arguments
		ArrayList<String> unusedArgs = new ArrayList<String>(); 
		for (int i = 0; i < args.length; i++) 
		{
			if (markerVersion.equals(args[i])) {
				System.out.println("Version: " + IBIOMESConfiguration.getVersion());
				System.exit(0);
			}
			else if (markerSoftware.equals(args[i])) {
				if (args.length>i+1)
		    		software = args[i+1];
		    	else this.throwErrorMissingArgument(markerSoftware);
		    	i++;
			}
		    else if (markerLocalInput.equals(args[i])) {
		    	if (args.length>i+1)
		    	  localPath = args[i+1];
		    	else this.throwErrorMissingArgument(markerLocalInput);
		        i++;
			}
		    else if (markerXmlDesc.equals(args[i])) {
		    	if (args.length>i+1)
		    		xmlDescPath = args[i+1];
		    	else this.throwErrorMissingArgument(markerXmlDesc);
		    	i++;
			}
		    else if (markerExternalURL.equals(args[i])) {
		    	if (args.length>i+1)
		    		externalUrl = args[i+1];
		    	else this.throwErrorMissingArgument(markerExternalURL);
		    	i++;
			} 
		    else if (markerSilent.equals(args[i])) {
		    	outputToConsole = false;
			}
		    else if (markerREMDGrouping.equals(args[i])) {
		    	remdGrouping = args[i+1];
			    i++;
			}
		    else if (markerDepthIndependentGroups.equals(args[i])) {
		    	if (args.length>i+1){
		    		try{
		    			depth = Integer.parseInt(args[i+1]);
			    	} catch(NumberFormatException e){
			    		System.out.println("\nERROR: integer expected for " + markerDepthIndependentGroups + " option\n");
			    		this.printSynopsis();
			    		System.exit(1);
			    	}
		    	}
			    else this.throwErrorMissingArgument(markerDepthIndependentGroups);
			    i++;
			}
		    else unusedArgs.add(args[i]);
		}
		//check arguments are valid
		this.checkArguments();
		
		return unusedArgs;
	}
	
	/**
	 * Check arguments are provided and valid
	 * @throws Exception
	 */
	protected void checkArguments() throws Exception
	{
		//check all required arguments are provided and valid
		if (localPath != null)
		{
			//check if input file/directory exists
			inputFile = new File(localPath);
			if (!inputFile.exists()){
				System.out.println("Directory or file '" + inputFile + "' does not exist.");
		    	System.exit(1);
			}
			localPath = inputFile.getCanonicalPath();
			
		}
		else {
			System.out.println("\nERROR: missing or non-valid arguments ("+markerLocalInput+").\n");
			this.printSynopsis();
	    	System.exit(1);
		}
		
		//initialize configuration for parsing
		IBIOMESConfiguration config = IBIOMESConfiguration.getInstance();
		if (remdGrouping!=null)
			config.setDefaultTaskGroupingPolicy(new TaskGroupingPolicy(remdGrouping));
		config.setOutputToConsole(outputToConsole);
		/*if (outputToConsole){
			config.printProperties();
			System.out.println("");
		}*/
	}
}
