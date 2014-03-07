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
 * CLI to publish experiments in IBIOMES Lite
 * @author Julien Thibault, University of Utah
 *
 */
public class CommandPublish extends AbstractCLICommandParse {

	private final static String markerForce = "-f";

	private boolean isForceDescUpdate = false;
	
	public CommandPublish(){
		super("ibiomes-lite-publish", "Publish computational experiment to iBIOMES Lite web directory");
		
		this.getArguments().get(markerLocalInput)
			.setDefinition("Path to the experiment directory to parse and publish");
		
		this.arguments.put(markerForce, new CLICommandArgument(
				markerForce, "", 
				"Force parsing if this directory has been parsed before.", 
				false, 
				true));
	}
	
	/**
	 * Publish experiment into iBIOMES Lite
	 * @param args Arguments
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		CommandPublish cmd = new CommandPublish();
		cmd.setArguments(args);
		cmd.execute();
	}
	
	/**
	 * Set arguments
	 */
	public void setArguments(String[] args) throws Exception {

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
	 * Publish
	 * @throws Exception 
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

			//add new experiment
			IBIOMESLiteManager lite = IBIOMESLiteManager.getInstance();
			lite.publishExperiment(localPath, software, xmlDescPath, isForceDescUpdate, depth );
			System.out.println("Experiment "+localPath+" successfully published to iBIOMES Lite");
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
