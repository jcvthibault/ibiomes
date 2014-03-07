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

package edu.utah.bmi.ibiomes.parse.chem.charmm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractParameterFile;

/**
 * CHARMM input file (.inp).
 * 
 * @author Julien Thibault, University of Utah
 *
 */
public class CHARMMInputFile extends AbstractParameterFile
{
	private static final long serialVersionUID = 244857191937798175L;

	private HashMap<String, List<String>> parameters;
	
	/**
	 * Default constructor.
	 * @param pathname Topology file path.
	 * @throws Exception
	 */
	public CHARMMInputFile(String pathname) throws Exception
	{
		super(pathname, LocalFile.FORMAT_CHARMM_INP);
		parameters = new HashMap<String, List<String>>();
		parseFile();
	}
	
	/**
	 * Parse CHARMM parameter file
	 * @param filePath
	 * @throws Exception
	 */
	private void parseFile() throws Exception 
	{
		ArrayList<String> lines = new ArrayList<String>();
	    IBIOMESFileReader br = null;
	    String line = null;
	    boolean append = false;
	    boolean appendCurrent = false;
	    int nLines = 0;
	    
	    try{
	    	br = new IBIOMESFileReader(this);
	        while (( line = br.readLine()) != null)
	        {
	        	line = line.trim();
	        	if (!line.startsWith("!") && !line.startsWith("*") && line.length()>0)
	        	{
	        		int indexComment = line.indexOf('!');
	        		if (indexComment!=-1)
	        			line = line.substring(0,indexComment).trim();
	        		
	        		appendCurrent = append;
	        		
	        		if (line.endsWith(" -")){
	        			line = line.substring(0, line.lastIndexOf(" -")).trim();
	        			append = true;
	        		}
	        		else append = false;
	        		
	        		if (!appendCurrent){
	        			lines.add(line);
	        			nLines++;
	        		}
	        		else{
	        			String concatLine = lines.get(nLines-1) + " " + line;
	        			lines.remove(nLines-1);
	        			lines.add(concatLine);
	        		}
	        	}
	        }
	        br.close();
	        for (String entry : lines)
	        {
	        	entry = entry.toLowerCase();
	        	int index = entry.indexOf(' ');
	        	if (index>0){
	        		//use first word as keyword
	        		String key = entry.substring(0, index);
	        		//put rest of values in array
	        		String[] values = entry.substring(index+1).split("\\s+");
	        		List<String> valueList = Arrays.asList(values);
	        		parameters.put(key, valueList);
	        	}
	        }
	        
	        ExperimentTask task = CHARMMKeywordMapper.mapKeysToTask(parameters);
	        this.tasks = new ArrayList<ExperimentTask>();
	        this.tasks.add(task);
	    }
		catch (Exception e){
			this.format = LocalFile.FORMAT_UNKNOWN;
	    	if (IBIOMESConfiguration.getInstance().isOutputToConsole())
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as a CHARMM input file.");
			if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
				e.printStackTrace();
			if (br!=null){
				try{
					br.close();
				} catch(Exception e2){
					e.printStackTrace();
				}
			}
			throw e;
		}
	}	
}
