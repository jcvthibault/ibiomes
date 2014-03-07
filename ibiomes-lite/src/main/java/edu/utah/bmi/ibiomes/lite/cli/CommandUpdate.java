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

import java.util.List;

import edu.utah.bmi.ibiomes.cli.AbstractCLICommandParse;
import edu.utah.bmi.ibiomes.cli.CLICommandArgument;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.lite.IBIOMESLiteManager;

/**
 * CLI to update web content related to published experiments
 * @author Julien Thibault, University of Utah
 *
 */
public class CommandUpdate extends AbstractCLICommandParse {

	private final static String markerForce = "-f";

	private boolean isForceDescUpdate = false;
	
	/**
	 * New command
	 */
	public CommandUpdate(){
		
		super("ibiomes-lite-update", "Rebuild pages in iBIOMES Lite web directory for the experiments that are already published.");
		
		this.arguments.put(markerForce, new CLICommandArgument(
				markerForce, "", 
				"Force parsing if this directory has been parsed before.", 
				false, 
				true));
	}
	
	/**
	 * Execute update command
	 * @param args Arguments
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		CommandUpdate cmd = new CommandUpdate();
		cmd.setArguments(args);
		cmd.execute();	
	}
	
	/**
	 * Set arguments
	 */
	public void setArguments(String[] args) throws Exception
	{
		//check general arguments for parsing
		List<String> unusedArgList = this.setArgumentsForParsing(args);
		
		//check arguments specific to iBIOMES Lite experiment publishing
		for (int i = 0; i < unusedArgList.size(); i++) 
		{
			if (markerForce.equals(unusedArgList.get(i))) {
				isForceDescUpdate = true;
			}
			else {
				System.out.println("ERROR: unknown option: " + unusedArgList.get(i) + "\n");
				this.printSynopsis();
				System.exit(1);
			}
		}
	}
	
	/**
	 * Execute search
	 */
	public void execute() throws Exception
	{
		try{
			//check input is a valid directory
			if (!inputFile.isDirectory()){
				System.out.println("ERROR: the "+markerLocalInput+" argument must point to a valid directory.\n");
				this.printSynopsis();
				System.exit(1);
			}
			//get reference to iBIOMES Lite manager
			IBIOMESLiteManager lite = IBIOMESLiteManager.getInstance();

			//update
			List<String> experiments = null;
			if (isForceDescUpdate){
				experiments = lite.regeneratePublishedDescriptors();
			}
			experiments = lite.updateWebContent();
			int nFiles = experiments.size();
			if (IBIOMESConfiguration.getInstance().isOutputToConsole()){
				System.out.println(nFiles + " experiments re-published at "+lite.getWebDirLocation()+":");
				for (String experiment : experiments){
					System.out.println("\t"+experiment);
				}
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
