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

package edu.utah.bmi.ibiomes.parse.chem.namd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractParameterFile;

/**
 * NAMD configuration file
 * 
 * @author Julien Thibault, University of Utah
 *
 */
public class NAMDConfigurationFile extends AbstractParameterFile
{
	private static final long serialVersionUID = 244857191937798175L;
	private HashMap<String, String> parameters;
	private HashMap<String, String> variables;
	
	/**
	 * Default constructor.
	 * @param pathname Log file path.
	 * @throws Exception
	 */
	public NAMDConfigurationFile(String pathname) throws Exception
	{
		super(pathname, LocalFile.FORMAT_NAMD_CONFIGURATION);
		parameters = new HashMap<String, String>();
		variables = new HashMap<String, String>();
		parseFile();
	}
	
	/**
	 * Parse NAMD parameter file
	 * @param filePath
	 * @throws Exception
	 */
	private void parseFile() throws Exception 
	{
		IBIOMESFileReader br = null;
		try{
			br =  new IBIOMESFileReader(this);
		    String line = null;
		    
	        while (( line = br.readLine()) != null)
	        {
	        	line = line.trim();
	        	if (line.length()>0 && !line.startsWith("#"))
	        	{
	        		if (!line.matches("((if)|(\\{)|(\\})).*"))
	        		{
		    			String[] values = line.split("\\s+");
		    			String attribute = values[0].trim().toLowerCase();
		    			
		    			//if its a variable
		    			if (attribute.equals("set")){
		    				variables.put(values[1].trim().toLowerCase(), values[2].trim().toLowerCase());
		    			}
		    			else //parameter 
		    			{
			    			String value = values[1].trim().toLowerCase();
			    			
			    			if (values.length>2){
			    				boolean endline = false;
			    				int i = 2;
			    				while (!endline && i<values.length){
			    					if (values[i].startsWith(";") || values[i].startsWith("#"))
			    						endline = true;
			    					else
			    						value += "," + values[i].toLowerCase();
			    					i++;
			    				}
			    			}
			    			//check if the actual value was defined in a variable
			    			if (value.startsWith("$")){
			    				value = variables.get(variables.get(value));
			    			}
			    			parameters.put(attribute, value);
		    			}
	        		}
	        	}
	        }
	        br.close();
	        
	        ExperimentTask task = NAMDInputKeywordMapper.mapToTask(parameters);
	        task.setInputFiles(this.getAbsolutePath());
	        this.tasks = new ArrayList<ExperimentTask>();
	        this.tasks.add(task);
		}
		catch (Exception e){
			this.format = LocalFile.FORMAT_UNKNOWN;
			if (IBIOMESConfiguration.getInstance().isOutputToConsole())
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as a NAMD configuration file.");
			if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
				e.printStackTrace();
			if (br!=null)
				try {
					br.close();
				} catch (IOException e1) {
				}
			throw e;
		}
	}
		
}
