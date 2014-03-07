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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * CLI command definition
 * @author Julien Thibault, University of Utah
 *
 */
public abstract class CLICommand {

	protected final static String markerHelp = "--help";
	protected final static String markerVersion = "--version";
	
	protected String name;
	protected String description;
	protected HashMap<String, CLICommandArgument> arguments;
	protected CLICommandArgument finalArgument;

	/**
	 * New command
	 * @param name Name of the command
	 * @param description Description
	 */
	public CLICommand(String name, String description){
		this.name = name;
		this.description = description;
		this.arguments = new HashMap<String, CLICommandArgument>();
	}
	
	/**
	 * Get command name
	 * @return Command name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set command name
	 * @param name Command name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get command description
	 * @return Command description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set command description
	 * @param description Command description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get arguments
	 * @return List of arguments
	 */
	public HashMap<String, CLICommandArgument> getArguments() {
		return arguments;
	}

	/**
	 * Set arguments
	 * @param arguments  List of arguments
	 */
	public void setArguments(HashMap<String, CLICommandArgument> arguments) {
		this.arguments = arguments;
	}

	/**
	 * Get final (mandatory) argument if any
	 * @return Final (mandatory) argument
	 */
	public CLICommandArgument getFinalArgument() {
		return finalArgument;
	}

	/**
	 * Set final mandatory argument
	 * @param finalArgument Final mandatory argument
	 */
	public void setFinalArgument(CLICommandArgument finalArgument) {
		this.finalArgument = finalArgument;
	}
	/**
	 * Print command usage
	 */
	public void printUsage(){
		System.out.println("\nSUMMARY");
		System.out.println(this.name + " - " + this.description);
		System.out.println("\nSYNOPSIS");
		printSynopsis();
		System.out.println("\nARGUMENTS");
		printArgumentsDescription();
		System.out.println("");
	}

	/**
	 * Print usage summary
	 */
	public void printSynopsis(){
		String synopsis = name;
		List<CLICommandArgument> optionalArguments = this.getOptionalArguments();
		List<CLICommandArgument> requiredArguments = this.getRequiredArguments();
		if (requiredArguments.size()>0){
			for (CLICommandArgument arg : requiredArguments){
				synopsis += " " + arg.getUsageArgument();
			}
		}
		if (optionalArguments.size()>0){
			for (CLICommandArgument arg : optionalArguments){
				synopsis += " " + arg.getUsageArgument();
			}
		}
		if (finalArgument!=null)
			synopsis += " " + finalArgument.getShortValueDescription();
		System.out.println(synopsis);
		System.out.println("\n["+ markerHelp+"] Show help menu");
		System.out.println("["+ markerVersion+"] Show program version");
	}
	
	/**
	 * Print description of the arguments
	 */
	public void printArgumentsDescription()
	{
		List<CLICommandArgument> requiredArguments = this.getRequiredArguments();
		List<CLICommandArgument> optionalArguments = this.getOptionalArguments();

		if (finalArgument!=null){
			System.out.println(finalArgument.getShortValueDescription());
			System.out.println("  " + finalArgument.getDefinition());
		}
		
		if (requiredArguments.size()>0){
			System.out.println("Required arguments:");
			for (CLICommandArgument arg : requiredArguments){
				System.out.println("  " + arg.getUsageDescription());
			}
		}
		if (optionalArguments.size()>0){
			System.out.println("Optional arguments:");
			for (CLICommandArgument arg : optionalArguments){
				System.out.println("  " + arg.getUsageDescription());
			}
		}
	}

	/**
	 * Get list of required arguments
	 * @return List of required arguments
	 */
	private List<CLICommandArgument> getArgumentsWithFilter(boolean isOptional) {
		ArrayList<CLICommandArgument> args = new ArrayList<CLICommandArgument>();
		Set<String> keys = this.arguments.keySet();
		//sort list
		List<String> keyList = new ArrayList<String>(keys);
		Collections.sort(keyList);
		//filter
		for (String key : keyList){
			if (isOptional == this.arguments.get(key).isOptional())
				args.add(this.arguments.get(key));
		}
		return args;
	}
	
	/**
	 * Get list of required arguments
	 * @return List of required arguments
	 */
	public List<CLICommandArgument> getRequiredArguments() {
		return getArgumentsWithFilter(false);
	}
	
	/**
	 * Check if help menu is requested
	 * @param args Command arguments
	 */
	public void checkHelp(String[] args){
		for (int i = 0; i < args.length; i++) 
		{
			if (markerHelp.equals(args[i])) {
				this.printUsage();
				System.exit(0);
			}
		}
	}
	
	/**
	 * Get list of optional requirements
	 * @return List of optional requirements
	 */
	public List<CLICommandArgument> getOptionalArguments() {
		return getArgumentsWithFilter(true);
	}
	
	public void throwErrorMissingArgument(String option){
		System.out.println("ERROR: option " + option + " requires an argument");
		System.out.println(arguments.get(option).getUsageDescription());
		System.exit(1);
	}

}
