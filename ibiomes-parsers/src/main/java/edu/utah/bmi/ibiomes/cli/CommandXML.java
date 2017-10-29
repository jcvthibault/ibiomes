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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import edu.utah.bmi.Utils;
import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.experiment.Experiment;
import edu.utah.bmi.ibiomes.experiment.XMLConverter;
import edu.utah.bmi.ibiomes.parse.DirectoryParsingProgressBar;
import edu.utah.bmi.ibiomes.parse.IBIOMESListener;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFactory;

/**
 * CLI to parse file/experiment and generate XML representation
 * @author Julien Thibault, University of Utah
 *
 */
public class CommandXML extends AbstractCLICommandParse {

	private final static String markerOutputPath = "-o";
	
	private String outputPath = null;

	/**
	 * New XML conversion command
	 */
	public CommandXML() {
		super("ibiomes xml", "Create an XML summary of the computational experiment");
		this.arguments.get(markerLocalInput).
			setDefinition("Path to the directory containing the files to parse");
		this.arguments.put(markerOutputPath, new CLICommandArgument(
				markerOutputPath, 
				"output-xml-file", 
				"Path to the output XML file.", 
				true, 
				true));
	}

	/**
	 * Execute XML converter command
	 * @param args Arguments
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		CommandXML parseCommand = new CommandXML();
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
			else {
				System.out.println("ERROR: unknown option: " + unusedArgList.get(i) + "\n");
				this.printSynopsis();
				System.exit(1);
			}
		}
	}

	/**
	 * Parse file/experiment and generate XML file.
	 */
	public void execute() throws Exception
			{
		Experiment exp = null;
		
		if (outputPath==null){
			System.out.println("ERROR: missing or non-valid arguments ("+markerOutputPath+").\n");
			this.printSynopsis();
			System.exit(1);
		}
		if (Files.isDirectory(Paths.get(outputPath))){
			System.out.println("ERROR: the "+markerOutputPath+" argument must point to the new PDF document to create, not a directory.\n");
			this.printSynopsis();
			System.exit(1);
		}	

		//read descriptor file if exists
		DirectoryStructureDescriptor desc = null; 
		if (xmlDescPath != null && xmlDescPath.length()!=0){
			System.out.println("Loading metadata generation rules...");
			desc = new DirectoryStructureDescriptor(xmlDescPath);
		}

		//if input is a file exit
		if (inputFile.isFile()) {
			System.out.println(inputFile.getCanonicalPath() + " is not a directory!");
			System.exit(1);
		}
		else //parse experiment directory
		{
			boolean outputToConsole = IBIOMESConfiguration.getInstance().isOutputToConsole();
			if (outputToConsole)
				System.out.println("Parsing directory...");
			ExperimentFactory expFactory = new ExperimentFactory(localPath, depth);
			//create listener for progress bar
			List<IBIOMESListener> listeners = null;
			if (outputToConsole){
				int nFiles = Utils.countNumberOfFilesInDirectory(localPath);
				listeners = new ArrayList<IBIOMESListener>();
				listeners.add((IBIOMESListener)new DirectoryParsingProgressBar(nFiles, "Parsing file"));
			}
			//parse
			exp = expFactory.parseDirectoryForExperimentWorkflow(software, desc, listeners, externalUrl);

			//generate XML
			if (outputToConsole)
				System.out.println("Generating XML document...");
			XMLConverter converter = new XMLConverter();
			Document doc = converter.convertExperiment(exp);
			net.sf.saxon.TransformerFactoryImpl tFactory = new net.sf.saxon.TransformerFactoryImpl();
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			File outputXmlFile = new File(outputPath);
			StreamResult result = new StreamResult(outputXmlFile);
			transformer.transform(source, result);
		}
	}
}
