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
import edu.utah.bmi.ibiomes.conf.DirectoryStructureRuleSet;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.DirectoryParsingProgressBar;
import edu.utah.bmi.ibiomes.parse.IBIOMESListener;
import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.LocalFileFactory;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFactory;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFolder;
import edu.utah.bmi.ibiomes.pub.IBIOMESPublisher;
import edu.utah.bmi.ibiomes.security.IRODSConnector;

/**
 * Command-line interface to add directory/files to existing iBIOMES experiment.
 * @author Julien Thibault, University of Utah
 *
 */
public class CommandAddDataToGrid extends AbstractCLICommandParse {

	private final static String markerIrodsPath = "-o";
	private final static String markerIrodsResc = "-r";
	private final static String markerIrodsUser = "-u";
	private final static String markerIrodsPwd = "-p";
	private final static String markerFormat = "-f";
	
	private String irodsPath = null;
	private String username = null;
	private String password = null;
	private String server = null;
	private String format = null;
	
	public CommandAddDataToGrid() {
		
		super("ibiomes-grid-add", "Add data to computational experiment in iBIOMES repository");

		this.arguments.get(markerLocalInput)
			.setDefinition("Path to the input file/directory to add to the iBIOMES experiment");
		
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
		this.arguments.put(markerFormat, new CLICommandArgument(
				markerFormat, 
				"file-format", 
				"Format of the file to publish.", 
				true, 
				true));
	}
	
	/**
	 * Add data to experiment in iBIOMES repository
	 * @param args Arguments
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		CommandAddDataToGrid cmd = new CommandAddDataToGrid();
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
				irodsPath = unusedArgList.get(i+1);
				i++;
			}
			else if (markerIrodsUser.equals(unusedArgList.get(i))) {
				username = unusedArgList.get(i+1);
				i++;
			} 
			else if (markerIrodsPwd.equals(unusedArgList.get(i))) {
				password = unusedArgList.get(i+1);
				i++;
			}
			else if (markerIrodsResc.equals(unusedArgList.get(i))) {
				server = unusedArgList.get(i+1);
				i++;
			}
		    else if (markerFormat.equals(unusedArgList.get(i))) {
		    	if (unusedArgList.size()>i+1)
		    		format = unusedArgList.get(i+1);
		    	else this.throwErrorMissingArgument(markerFormat);
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
	 * CLI for directory publication
	 * @throws Exception
	 */
	public void execute() throws Exception
	{	
		try{
			System.out.println("-----------------------------------");
			System.out.println("  iBIOMES Grid - Add data to experiment");
			System.out.println("-----------------------------------");
			
			String fileTypeStr = null;
			LocalDirectory localDir = null;
			LocalFile localFile = null;
			List<IBIOMESListener> parserListeners = null;
			IBIOMESListener publishListener = null;
			
			//check if iRODS path is specified
			if (irodsPath==null){
				System.out.println("ERROR: missing or non-valid arguments ("+markerIrodsPath+").\n");
				this.printSynopsis();
				System.exit(1);
			}
			
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

			inputFile = inputFile.getCanonicalFile();
			localPath = inputFile.getCanonicalPath();
			
			//if input is a file
			if (inputFile.isFile()) {
				if (outputToConsole)
					System.out.println("Parsing local file...");
				LocalFileFactory fileFactory = LocalFileFactory.instance();
				MetadataAVUList metadata = null;
				if (desc != null){
					DirectoryStructureRuleSet rules = desc.getRuleSetForFile(localPath);
					metadata = rules.getExtendedAttributes();
					if (format==null)
						format = rules.getFileFormat();
				}
				if (format!=null)
					localFile = fileFactory.getFileInstanceFromFormat(localPath, format);
				else 
					localFile = fileFactory.getFile(localPath, software);
				localFile.setExtendedAttributes(metadata);
				fileTypeStr = "file";
				if (outputToConsole)
					System.out.println(localFile.getMetadata().toString());
			}
			else //its a directory
			{
				//create listener for progress bar
				int nFiles = 0;
				if (IBIOMESConfiguration.getInstance().isOutputToConsole()){
					nFiles = Utils.countNumberOfFilesInDirectory(localPath);
					parserListeners = new ArrayList<IBIOMESListener>();
					parserListeners.add((IBIOMESListener)new DirectoryParsingProgressBar(nFiles, "Parsing file"));
					publishListener = new DirectoryParsingProgressBar(nFiles, "Publishing file");
				}
				//parse
				ExperimentFactory expFactory = new ExperimentFactory(localPath);
				ExperimentFolder experimentFolder = expFactory.parseDirectoryForExperimentWorkflowAndMetadata( software, desc, parserListeners );
				fileTypeStr = "directory";
				//dump parsed metadata to console
				localDir = experimentFolder.getFileDirectory();
				if (outputToConsole){
					System.out.println(localDir.getMetadata().toString());
					//localDir.dumpToText();
				}
			}
			
			//get iBIOMES connection
			IRODSConnector cnx =  CLIUtils.getConnection(username, password, server);
			if (cnx != null)
			{
				//make sure path to iRODS is canonical
				if (!irodsPath.startsWith("/"))
					irodsPath = "/" + cnx.getAccount().getZone() + "/" + "home/"+ cnx.getAccount().getUserName() + "/" + irodsPath;
					
				System.out.println("Your "+fileTypeStr+" will be published in '" + irodsPath + "'.");
				System.out.println("The default resource is set to '" + cnx.getAccount().getDefaultStorageResource() + "'.");
				System.out.println("Continue? [y/n]");
				
				String input = in.readLine();
				if ( input==null || input.length()==0 ||input.equals("y") || input.equals("yes"))
				{	
					if (outputToConsole)
						System.out.println("Publishing files to iBIOMES...");
					IRODSFileFactory irodsFactory = cnx.getFileSystem().getIRODSFileFactory(cnx.getAccount());
					IBIOMESPublisher publisher = new IBIOMESPublisher(cnx.getAccount(), cnx.getFileSystem().getIRODSAccessObjectFactory());
					IRODSFile iFile = irodsFactory.instanceIRODSFile(irodsPath);
					
					boolean overwrite = false;
					boolean fileExists = iFile.exists();
					
					if (fileExists)
					{
						if (iFile.isDirectory() && inputFile.isFile()){
							//check that file doesn't exist in iRODS
							irodsPath += "/" + inputFile.getName();
							iFile = irodsFactory.instanceIRODSFile(irodsPath);
							fileExists = iFile.exists();
						}
					}
					
					if (fileExists)
					{		
						System.out.println("The " + fileTypeStr + "'" +iFile.getAbsolutePath()+"' already exists. Overwrite? [y]");
						input = in.readLine();
						overwrite = ( input==null || input.length()==0 ||input.equals("y") || input.equals("yes") ) ;
						if (outputToConsole)
							System.out.println("Overwriting: " + overwrite);
					}
					
					if (inputFile.isDirectory()){
						//copy folder and content
						publisher.publishDirectory(localDir, irodsPath, overwrite, publishListener);
					}
					else {
						//copy single file
						publisher.publishFile(localFile, irodsPath, overwrite);
					}
					
					if (outputToConsole)
						System.out.println("Done! You can now access your "+fileTypeStr+" through iBIOMES at " + irodsPath);
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
