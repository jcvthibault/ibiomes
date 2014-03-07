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

/**
 * Clean iBIOMES Lite web content
 * @author Julien Thibault, University of Utah
 *
 */
public class CommandClean extends CLICommand implements CommandInterface{

	private final static String markerLocalDir = "-i";
	
	private boolean cleanEverything = false;
	private String experimentPath = null;
	
	/**
	 * New iBIOMES lite clean command
	 */
	public CommandClean() {
		super("ibiomes-lite clean", 
				"Remove content (XML and HTML) from iBIOMES Lite website. " +
				"XML descriptors at the experiment directory level are conserved, and can be published again. " +
				"If the -i option is not specified then all experiments are removed");
		this.arguments.put(markerLocalDir, new CLICommandArgument(
				markerLocalDir, 
				"experiment-path", 
				"Local path to the experiment to remove from the list of published experiments.", 
				true, 
				true));
	}
	
	/**
	 * Execute cleaning command
	 * @param args Arguments
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		CommandClean cmd = new CommandClean();
		cmd.setArguments(args);
		cmd.execute();	
	}
	
	/**
	 * Set arguments
	 */
	public void setArguments(String[] args) throws Exception
	{		
		if (args.length==0){
			cleanEverything = true;
		}
		else {
			cleanEverything = false;
			
			//check if help is requested
			this.checkHelp(args);
			
			//check other arguments
			for (int i = 0; i < args.length; i++) 
			{
				if (markerVersion.equals(args[i])) {
					System.out.println("Version: " + IBIOMESConfiguration.getVersion());
					System.exit(0);
				}
				else if (markerLocalDir.equals(args[i])) {
					if (args.length>i)
						experimentPath = args[i+1];
					else this.throwErrorMissingArgument(markerLocalDir);
					i++;
				}
				else {
					System.out.println("\nERROR: unknown option: " + args[i]);
					this.printSynopsis();
					System.exit(1);
				}
			}
		}
	}
	
	/**
	 * Clean web directory
	 */
	public void execute() throws Exception
	{
		try{
			IBIOMESLiteManager lite = IBIOMESLiteManager.getInstance();
			
			if (cleanEverything){
				//clean everything
				int nExperiments = lite.cleanData();
				System.out.println(nExperiments + " experiments were removed from web directory ("+lite.getWebDirLocation()+")");
			}
			else{
				//remove only the specified experiment
				if (experimentPath==null){
					System.out.println("\nERROR: missing or non-valid arguments.");
					this.printArgumentsDescription();
					System.exit(1);
				}
				
				int id = lite.removeExperiment(experimentPath);
				if (id>-1)
					System.out.println("Experiment successfully removed from iBIOMES Lite!");
				else
					System.out.println("\nERROR: the experiment '"+experimentPath+"' could not be found in iBIOMES Lite");
			}
		}
		catch (Exception e){
			System.out.println("\nERROR: the program did not terminate correctly:");
			System.out.println(e.getMessage());
			if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole()){
				e.printStackTrace();
			}
		}
	}
}
