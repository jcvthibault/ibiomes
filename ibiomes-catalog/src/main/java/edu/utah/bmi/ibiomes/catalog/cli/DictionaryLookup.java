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

package edu.utah.bmi.ibiomes.catalog.cli;

import java.io.File;

import edu.utah.bmi.ibiomes.catalog.MetadataLookup;
import edu.utah.bmi.ibiomes.metadata.MetadataAttribute;

/**
 * Command-line for dictionary lookups
 * @author Julien Thibault - University of Utah, BMI
 *
 */
public class DictionaryLookup {

	private final static String usage = "lookup-index -i <index-path> -t <term>";
	
	public static void main(String[] args) throws Exception
    { 
    	String indexFilePath = null;
    	//String lookupFieldName = MetadataLookup.LOOKUP_FIELD_TERM;
    	String term = null;
    	
    	if (args.length==0){
    		System.out.println("Missing arguments!\nUsage:\n" + usage);
			System.exit(1);
    	}    	
    	for (int i = 0; i < args.length; i++) 
		{
		      if (args[i].equals("-i")) {
		    	  indexFilePath = args[i+1];
		        i++;
		      }
		      else if (args[i].equals("-t")) {
		    	  term = args[i+1];
		    	  i++;
		    	  while (i+1<args.length)
		    	  {
		    		  i++;
		    		  if (args[i].startsWith("-")){
		    			  i--;
		    			  break;
		    		  }
		    		  else {
		    			  term += " " + args[i];
		    		  }
		    	  }
		      }
		      /*else if (args[i].equals("-f")) {
		    	  lookupFieldName = args[i+1];
		    	  i++;
		      }*/
		      else System.out.println("\nWARNING: unknown option '" + args[i] + "'\n");
		}
    	
		if (indexFilePath == null || term == null){
    		System.out.println("Missing argument!\nUsage:\n" + usage);
			System.exit(1);
    	}
    	File indexFile = new File(indexFilePath);

    	//System.out.println("Lookup field: "+lookupFieldName);
    	System.out.println("        Term: "+term);
    	System.out.println("  Dictionary: "+indexFile.getCanonicalPath());
    	
    	MetadataLookup lookup = new MetadataLookup(indexFile);
    	MetadataAttribute result = lookup.lookupMetadataAttribute(term);

    	if (result != null && result.getTerm()!=null && result.getTerm().length()>0){
    		System.out.println("\nMatch found:");
    		System.out.println("\tCODE: "+result.getCode());
    		System.out.println("\tTERM: "+result.getTerm());
    		System.out.println("\tDEFINITION: "+result.getDefinition());
    		System.out.println("\tTYPE: "+result.getType());
    	}
    	else System.out.println("\nNo match found for '"+term+"' in this dictionary");
		System.out.println("--------------------------------");
    }
}

