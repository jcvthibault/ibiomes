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

package edu.utah.bmi.ibiomes.lite.cli;

import edu.utah.bmi.ibiomes.cli.CLICommand;
import edu.utah.bmi.ibiomes.cli.CLICommandArgument;
import edu.utah.bmi.ibiomes.cli.CommandInterface;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.lite.IBIOMESLiteManager;
import edu.utah.bmi.ibiomes.lite.search.IBIOMESLiteSearchRecord;
import edu.utah.bmi.ibiomes.lite.search.IBIOMESLiteSearchRecordSet;

/**
 * CLI to search experiments
 * @author Julien Thibault, University of Utah
 *
 */
public class CommandSearch extends CLICommand implements CommandInterface{
	
	private String keywords = null;
	
	public CommandSearch(){
		super("ibiomes-lite-search", "Search experiments published in iBIOMES Lite");
		this.setFinalArgument(new CLICommandArgument(
				null, 
				"keywords", 
				"List of keywords separated by '+' character. Wildcards can be specified using '%'. Example: %dynamics+rna+amber.", 
				false, false)
		);
	}

	/**
	 * Execute search command
	 * @param args Arguments
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		CommandSearch cmd = new CommandSearch();
		cmd.setArguments(args);
		cmd.execute();	
	}
	
	/**
	 * Set arguments
	 */
	public void setArguments(String[] args) throws Exception
	{
		try{			
			keywords = "";
			if (args.length>0)
			{
				if (markerHelp.equals(args[0])) {
					this.printUsage();
					System.exit(1);
				}
				else if (markerVersion.equals(args[0])) {
					System.out.println("Version: " + IBIOMESConfiguration.getVersion());
					System.exit(0);
				}
				else {
					for (int i = 0; i < args.length; i++){
						keywords += " " + args[i];
					}
				}
			}
			else {
				System.out.println("Enter at least one keyword!");
				this.printUsage();
				System.exit(1);
			}
			
		}
		catch (Exception e){
			System.out.println("ERROR: the program did not terminate correctly:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Execute search
	 */
	public void execute() throws Exception
	{
		try{
			IBIOMESLiteManager lite = IBIOMESLiteManager.getInstance();
			String[] keywordsArray = keywords.trim().split("\\+");
			IBIOMESLiteSearchRecordSet matches = lite.search(keywordsArray);
			
			System.out.println("Keywords:");
			for (String keyword : keywordsArray){
				System.out.println("\t"+keyword.trim());
			}
			
			if (matches != null && matches.size()>0){
				System.out.println(matches.size() + " experiment(s) found:");
				for (IBIOMESLiteSearchRecord match : matches){
					System.out.println("\t["+match.getExperimentId() + "] " + match.getLocalDirPath());
				}
			}
			else{
				System.out.println("No experiment found for your search criteria.");
			}
		}
		catch (Exception e){
			System.out.println("ERROR: the program did not terminate correctly:");
			System.out.println(e.getMessage());
			if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole()){
				e.printStackTrace();
			}
		}
	}
}
