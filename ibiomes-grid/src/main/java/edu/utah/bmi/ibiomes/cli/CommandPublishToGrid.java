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

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;

import edu.utah.bmi.Utils;
import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.parse.DirectoryParsingProgressBar;
import edu.utah.bmi.ibiomes.parse.IBIOMESListener;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFactory;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFolder;
import edu.utah.bmi.ibiomes.pub.IBIOMESPublisher;
import edu.utah.bmi.ibiomes.security.IRODSConnector;

/**
 * CLI to publish experiment to iBIOMES. The experiment data is assumed to be in a different resource than the target iBIOMES resource.
 * @author Julien Thibault, University of Utah
 *
 */
public class CommandPublishToGrid extends AbstractCLICommandParse{

	private final static String markerIrodsPath = "-o";
	private final static String markerIrodsResc = "-r";
	private final static String markerIrodsUser = "-u";
	private final static String markerIrodsPwd = "-p";

	private String irodsPath = null;
	private String username = null;
	private String password = null;
	private String server = null;

	public CommandPublishToGrid()
	{
		super("ibiomes-grid-publish", "Publish computational experiment to iBIOMES repository");
		
		this.arguments.get(markerLocalInput)
			.setDefinition("Path to the input directory containing the files to publish");
		
		this.arguments.put(markerIrodsPath, new CLICommandArgument(
				markerIrodsPath, 
				"ibiomes-path", 
				"Path to the iBIOMES collection where the experiment will be uploaded", 
				true, 
				false));
		this.arguments.put(markerIrodsResc, new CLICommandArgument(
				markerIrodsResc, 
				"resource", 
				"Default resource to use when connecting to iBIOMES repository.", 
				true, 
				true));
		this.arguments.put(markerIrodsUser, new CLICommandArgument(
				markerIrodsUser, 
				"username", 
				"Username used to connect to iBIOMES.", 
				true, 
				true));
		this.arguments.put(markerIrodsPwd, new CLICommandArgument(
				markerIrodsPwd, 
				"password", 
				"Password used to connect to iBIOMES.", 
				true, 
				true));
	}
	

	/**
	 * Publish experiment to iBIOMES repository
	 * @param args Arguments
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		CommandPublishToGrid cmd = new CommandPublishToGrid();
		cmd.setArguments(args);
		LogManager.getRootLogger().setLevel(Level.OFF);//turn off iRODS logging for CLI
		cmd.execute();
	}
	
	/**
	 * Set arguments
	 * @param args Arguments
	 * @throws Exception 
	 */
	public void setArguments(String[] args) throws Exception
	{
		//check general arguments for parsing
		List<String> unusedArgList = this.setArgumentsForParsing(args);
		
		//check for arguments specific to this command
		for (int i = 0; i < unusedArgList.size(); i++) 
		{
			if (markerIrodsPath.equals(unusedArgList.get(i))) {
				if (unusedArgList.size()>i)
					irodsPath = unusedArgList.get(i+1);
				else throwErrorMissingArgument(markerIrodsPath);
				i++;
			}
			else if (markerIrodsUser.equals(unusedArgList.get(i))) {
				if (unusedArgList.size()>i)
					username = unusedArgList.get(i+1);
				else throwErrorMissingArgument(markerIrodsUser);
				i++;
			} 
			else if (markerIrodsPwd.equals(unusedArgList.get(i))) {
				if (unusedArgList.size()>i)
					password = unusedArgList.get(i+1);
				else throwErrorMissingArgument(markerIrodsPwd);
				i++;
			}
			else if (markerIrodsResc.equals(unusedArgList.get(i))) {
				if (unusedArgList.size()>i)
					server = unusedArgList.get(i+1);
				else throwErrorMissingArgument(markerIrodsResc);
				i++;
			}
			else {
				System.out.println("ERROR: unknown option: " + unusedArgList.get(i) + "\n");
				this.printSynopsis();
				System.exit(1);
			}
		}
	}

	/**
	 * Publish experiment
	 */
	public void execute() throws Exception
	{	
		System.out.println("-----------------------------------");
		System.out.println("  iBIOMES Grid - publish experiment");
		System.out.println("-----------------------------------");
		try{
			if (!inputFile.isDirectory()){
				System.out.println("'" + localPath + "' is not a directory!");
	    		System.exit(1);
			}
			inputFile = inputFile.getCanonicalFile();
			localPath = inputFile.getCanonicalPath();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
			Console objConsole = System.console();
			if (objConsole == null) {
				System.err.println("Console Object is not available.");
				System.exit(1);
			}
	
			//read XML descriptor file if exists
			DirectoryStructureDescriptor desc = null; 
			if (xmlDescPath != null && xmlDescPath.length()!=0){
				if (outputToConsole)
					System.out.println("Loading metadata generation rules...");
				desc = new DirectoryStructureDescriptor(xmlDescPath);
			}
			//create listener for progress bar
			List<IBIOMESListener> parserListeners = null;
			IBIOMESListener publishListener = null;
			int nFiles = 0;
			if (IBIOMESConfiguration.getInstance().isOutputToConsole()){
				nFiles = Utils.countNumberOfFilesInDirectory(localPath);
				parserListeners = new ArrayList<IBIOMESListener>();
				parserListeners.add((IBIOMESListener)new DirectoryParsingProgressBar(nFiles, "Parsing file"));
				publishListener = new DirectoryParsingProgressBar(nFiles, "Registering file");
			}
			ExperimentFactory expFactory = new ExperimentFactory(inputFile.getCanonicalPath());
			ExperimentFolder experimentFolder = expFactory.parseDirectoryForExperimentWorkflowAndMetadata(software, desc, parserListeners);
			
	
			//get iBIOMES connection
			IRODSConnector cnx =  CLIUtils.getConnection(username, password, server);
			if (cnx != null)
			{
				//if the iRODS path was not specified, upload to iRODS user home directory
				if (irodsPath == null){
					irodsPath = "/" + cnx.getAccount().getZone() + "/" + "home/"+ cnx.getAccount().getUserName() + "/" + inputFile.getName();
				}
				else //make sure path to iRODS is canonical
				{
					if (!irodsPath.startsWith("/"))
						irodsPath = "/" + cnx.getAccount().getZone() + "/" + "home/"+ cnx.getAccount().getUserName() + "/" + irodsPath;
				}
	
				System.out.println("Your experiment will be published in '" + irodsPath + "'.");
				System.out.println("The default resource is set to '" + cnx.getAccount().getDefaultStorageResource() + "'.");
				System.out.println("Continue? [y/n]");
	
				String input = in.readLine();
				if ( input==null || input.length()==0 ||input.equals("y") || input.equals("yes"))
				{	
					if (outputToConsole)
						System.out.println("Registering files into iBIOMES...");
					IBIOMESPublisher publisher = new IBIOMESPublisher(cnx.getAccount(), cnx.getFileSystem().getIRODSAccessObjectFactory());
					IRODSFileFactory cAO = cnx.getFileSystem().getIRODSFileFactory(cnx.getAccount());
					IRODSFile icoll = cAO.instanceIRODSFile(irodsPath);
	
					boolean overwrite = false;
					if (icoll.exists())
					{
						System.out.println("The collection '"+icoll.getAbsolutePath()+"' already exists. Overwrite? [y]");
						input = in.readLine();
						overwrite = ( input==null || input.length()==0 ||input.equals("y") || input.equals("yes") ) ;
						if (outputToConsole)
							System.out.println("Overwriting: " + overwrite);
					}
					publisher.publishExperiment(experimentFolder.getFileDirectory(), irodsPath, overwrite, publishListener);
	
					if (outputToConsole)
						System.out.println("Done! You can now access your simulation files through iBIOMES at " + irodsPath);
				}
	
				//close connections
				cnx.getFileSystem().closeAndEatExceptions();
	
				System.exit(0);
			}
			else System.exit(1);
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
