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

package edu.utah.bmi.ibiomes.parse.chem.amber;

import java.util.ArrayList;
import java.util.HashMap;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractParameterFile;

/**
 * AMBER MD input file
 * @author Julien Thibault
 *
 */
public class AmberMdInputFile extends AbstractParameterFile {

	private static final long serialVersionUID = -5577978145409702898L;
	private HashMap<String, String> parameters;

	public AmberMdInputFile(String localPath) throws Exception {
		super(localPath, FORMAT_AMBER_MDIN);
		parameters = new HashMap<String, String>();
		try {
			parseFile();
		} catch (Exception e) {
			this.format = LocalFile.FORMAT_UNKNOWN;
			if (IBIOMESConfiguration.getInstance().isOutputToConsole())
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as an AMBER MD input file.");
			if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
				e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Parse AMBER input file
	 * @throws Exception
	 */
	private void parseFile() throws Exception 
	{
		IBIOMESFileReader inputReader = new IBIOMESFileReader(this);
		
		try{
		    String line = null;
		    
		    String header = inputReader.readLine();
		    if (header != null && header.trim().length()>0)
		    	this.description = header.trim();
		    
	        while (( line = inputReader.readLine()) != null)
	        {
	        	line = line.trim();
	        	if (!line.startsWith("!") && line.indexOf('=')>0)
	        	{
	        		//remove comment at end of line
	        		int commentIdx = line.indexOf("!");
	        		if (commentIdx>0)
	        			line = line.substring(0, commentIdx).trim();
	        		
	        		//parse values
	    			String[] pairs = line.split("\\,");
	    			for (int p=0;p<pairs.length;p++)
	    			{
	    				String pair = pairs[p];
	    				String[] splitPair = pair.split("\\=");
	    				if (splitPair.length==2)
	    				{
				    		String attribute = splitPair[0].trim().toLowerCase();
				    		String value = splitPair[1].trim();
			        		//remove quotes for string if any
			        		if (value.startsWith("'") && value.endsWith("'"))
			        			value = value.substring(1, value.length()-1);        		
				    		parameters.put(attribute, value);
	    				}
	    			}
	    		}
	        }
	        inputReader.close();
	
	        this.tasks = AmberKeywordMapper.mapToTasks(parameters, null);
	        if (description!=null && description.length()>0){
	        	for (ExperimentTask task : tasks){
	        		task.setDescription(description);
	        	}
	        };
			for (ExperimentTask task : tasks){
				task.setSoftware(new Software(Software.AMBER));
				ArrayList<String> relatedFiles = new ArrayList<String>();
				relatedFiles.add(this.getCanonicalPath());
				task.setInputFiles(relatedFiles);
			}
	
		}
		catch (Exception e){
			try {
				if (inputReader!=null)
					inputReader.close();
			} catch (Exception e1) {
			}
			throw e;
		}
	}
}
