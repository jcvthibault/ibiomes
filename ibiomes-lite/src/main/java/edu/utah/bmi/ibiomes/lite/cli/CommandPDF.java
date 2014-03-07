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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import edu.utah.bmi.ibiomes.cli.AbstractCLICommandParse;
import edu.utah.bmi.ibiomes.cli.CLICommandArgument;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.lite.IBIOMESLiteManager;

/**
 * CLI to publish experiments
 * @author Julien Thibault, University of Utah
 *
 */
public class CommandPDF extends AbstractCLICommandParse {

	private final static String markerOutputPath = "-o";
	private static final String markerForce = "-f";

	private String outputPath = null;
	private boolean isForceDescUpdate = false;
	
	/**
	 * New iBIOMES lite PDF command
	 */
	public CommandPDF()
	{
		super("ibiomes-lite-pdf", "Create a PDF document summarizing the computational experiment");
		this.arguments.get(markerLocalInput).
			setDefinition("Path to the directory containing the files to parse");
		this.arguments.put(markerOutputPath, new CLICommandArgument(
				markerOutputPath, 
				"output-pdf-file", 
				"Path to the output file.", 
				true, 
				false));
		this.arguments.put(markerForce, new CLICommandArgument(
				markerForce, "", 
				"Force parsing if this directory has been parsed before.", 
				false, 
				true));
	}
	
	/**
	 * Execute PDF converter command
	 * @param args Arguments
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		CommandPDF parseCommand = new CommandPDF();
		parseCommand.setArguments(args);
		parseCommand.execute();	
	}
	
	/**
	 * Set arguments
	 */
	public void setArguments(String[] args) throws Exception {

		//check general arguments for parsing
		List<String> unusedArgList = this.setArgumentsForParsing(args);
		
		//check arguments specific to XML converter
		for (int i = 0; i < unusedArgList.size(); i++)
		{
			if (markerOutputPath.equals(unusedArgList.get(i))) {
				if (unusedArgList.size()>i+1)
					outputPath = unusedArgList.get(i+1);
				else this.throwErrorMissingArgument(markerOutputPath);
				i++;
			}
			else if (markerForce.equals(unusedArgList.get(i))) {
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
	 * Parse file/experiment and generate PDF file.
	 */
	public void execute() throws Exception
	{
		try{
			//check that output path is provided
			if (outputPath==null){
				System.out.println("ERROR: missing or non-valid arguments ("+markerOutputPath+").\n");
				this.printSynopsis();
				System.exit(1);
			}		
			//check input is a valid directory
			if (!inputFile.isDirectory()){
				System.out.println("ERROR: the "+markerLocalInput+" argument must point to a valid directory.\n");
				this.printSynopsis();
				System.exit(1);
			}
			//check output file is not a directory
			if (Files.isDirectory(Paths.get(outputPath))){
				System.out.println("ERROR: the "+markerOutputPath+" argument must point to the new PDF document to create, not a directory.\n");
				this.printSynopsis();
				System.exit(1);
			}

			//instantiate iBIOMES Lite manager
			IBIOMESLiteManager lite = IBIOMESLiteManager.getInstance();
			
			lite.generatePdf(outputPath, localPath, software, xmlDescPath, isForceDescUpdate, depth);
			System.out.println("PDF document successfully generated ("+outputPath+").");
			
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
