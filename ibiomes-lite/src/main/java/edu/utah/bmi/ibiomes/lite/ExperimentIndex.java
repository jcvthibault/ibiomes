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

package edu.utah.bmi.ibiomes.lite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.compress.compressors.CompressorException;

import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;

/**
 * Index of experiments published in iBIOMES Lite. Each experiment is represented by an entry in a dedicated text file.
 * @author Julien Thibault, University of Utah
 *
 */
public class ExperimentIndex {

	private File file;
	
	private class IndexEntry{
		long timestamp;
		String experimentPath;
		public IndexEntry(String experimentPath){
			this.experimentPath = experimentPath;
			this.timestamp = (new Date()).getTime();
		}
		public IndexEntry(long timestamp, String experimentPath){
			this.experimentPath = experimentPath;
			this.timestamp = timestamp;
		}
		@Override
		public String toString(){
			return (this.timestamp + "\t" + this.experimentPath);
		}
	}
	
	/**
	 * Create reference to index file
	 * @param filePath Path to index file
	 * @throws IOException 
	 */
	public ExperimentIndex(String filePath) throws IOException{
		Path path = Paths.get(filePath);
		if (Files.exists(path)){
			Files.createFile(path);
		}
		this.file = new File(filePath);
	}
	
	private HashMap<String, IndexEntry> loadIndexFromFile() throws IOException, CompressorException{
		HashMap<String, IndexEntry> index = new HashMap<String, IndexEntry>();
		IBIOMESFileReader br = null;
		try {
		  br = new IBIOMESFileReader(this.file);
		  String line = null;
		  while ((line = br.readLine()) != null) {
			  line = line.trim();
			  if (line.length()>0){
				  String[] entryValues = line.split("\\t");
				  long timestamp = Long.parseLong(entryValues[0]);
				  String experiment = entryValues[1];
				  index.put(experiment, new IndexEntry(timestamp, experiment));
			  }
		  }
		  return index;
		}
		finally{
			if (br!=null)
				br.close();
		}
	}
	
	private void storeIndexToFile(HashMap<String, IndexEntry> index) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(this.file, false));
		Collection<IndexEntry> entries = index.values();
		for (IndexEntry entry : entries){
			bw.append(entry.toString() + "\n");
		}
		bw.close();
	}
	
	/**
	 * Add entry (experiment) to index
	 * @param experimentPath Path to experiment
	 * @throws IOException 
	 */
	public void addEntry(String experimentPath) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(this.file, true));
		bw.append(new IndexEntry(experimentPath).toString() + "\n");
		bw.close();
	}
	
	/**
	 * Remove entry (experiment) from index
	 * @param experimentPath Path to experiment
	 * @throws IOException 
	 * @throws CompressorException 
	 */
	public void removeEntry(String experimentPath) throws IOException, CompressorException{
		HashMap<String, IndexEntry> index = loadIndexFromFile();
		index.remove(experimentPath);
		storeIndexToFile(index);
	}
	
	/**
	 * Remove all entries
	 * @throws IOException 
	 */
	public void clearAll() throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(this.file, false));
		bw.write("");
		bw.close();
	}
}
