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
package edu.utah.bmi.ibiomes.parse;

import java.io.IOException;

import org.apache.commons.compress.compressors.CompressorException;

import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

/**
 * Local CSV file containing simulation analysis results (time series, average pressure, energy RMSd, etc).
 * @author Julien Thibault
 *
 */
public class CSVFile extends AbstractLocalFileImpl
{
	private static final long serialVersionUID = 4716756938454598338L;
	
	private String delimiter = "\\,";
	private int nColumns = 0;
	boolean hasHeader = false;
	String[] units;
	String[] labels;
	
	/**
	 * Series of labels characterizing the data columns (e.g. "RMSd, time" or "Temperature, time step")
	 */
	public final static String DATA_LABELS = "DATA_LABELS";
	/**
	 * Series of units characterizing data values (e.g. "Angstrom, nanosecond" or "K, picosecond")
	 */
	public final static String DATA_UNITS = "DATA_UNITS";
	
	/**
	 * Preferred graphical representation type when plotting the data (e.g. line, dot, heatmap, histogram) 
	 */
	public final static String PREFERRED_PLOT_TYPE = "PREFERRED_PLOT_TYPE";
	
	/**
	 * Series names (e.g. 'dataset 1', 'dataset 2')
	 */
	public static final String SERIES_LABELS = "SERIES_LABELS";

	/**
	 * Flag to use logarithmic scale for X axis
	 */
	public static final String SCALE_LOG_X = "SCALE_LOG_X";

	/**
	 * Flag to use logarithmic scale for Y axis
	 */
	public static final String SCALE_LOG_Y = "SCALE_LOG_Y";
	
	/**
	 * Reference new CSV file
	 * @param localPath Local path to the file
	 * @throws IOException
	 */
	public CSVFile(String localPath) throws IOException {
		super(localPath, FORMAT_CSV, TYPE_SPREADSHEET);
		this.init(localPath);
	}
	
	/**
	 * Initialize CSV file
	 * @param localPath Path to the file
	 */
	private void init(String localPath){
		
		try{
			String[] labels = this.readCsvHeader();
			if (labels != null)
			{
				this.labels = labels;
				this.hasHeader = true;
			}
			this.findDataFormat(this.hasHeader);
			
			if (this.description == null || this.description.length()==0){
				//by default use the name of the file as a description of the file
				String name = this.getName();
				if (name.indexOf('.')>0)
					this.setDescription(name.substring(0, name.lastIndexOf('.')).replaceAll("_", " "));
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public MetadataAVUList getMetadata() throws Exception
	{
		MetadataAVUList metadata = super.getMetadata();
		if (this.labels != null && this.labels.length>0)
		{
			String labelStr = "";
			for (int l=0;l<this.labels.length; l++){
				labelStr += "," + this.labels[l];
			}
			metadata.updatePair(DATA_LABELS, labelStr.substring(1));
		}
		if (this.units != null && this.units.length>0)
		{
			String labelStr = "";
			for (int l=0;l<this.units.length; l++){
				labelStr += "," + this.units[l];
			}
			metadata.updatePair(DATA_UNITS, labelStr.substring(1));
		}
		return metadata;
	}
	
	/**
	 * Specify the labels for the data sets contained in the file (e.g. time, energy).
	 * @param labels Data labels (column names in a CSV file)
	 */
	public void setDataLabels(String[] labels){
		this.labels = labels;
	}
	
	/**
	 * Get the labels for the data sets contained in the file (e.g. time, energy).
	 * @return labels Data labels (column names in a CSV file)
	 */
	public String[] getDataLabels(){
		return this.labels;
	}
	
	/**
	 * Specify the units for the data sets contained in the file (e.g. second, angstrom).
	 * @param units Data units 
	 */
	public void setDataUnits(String[] units){
		this.units = units;
	}
	
	/**
	 * Get the units for the data sets contained in the file (e.g. second, angstrom).
	 * @return units Data units 
	 */
	public String[] getDataUnits(){
		return this.units;
	}
	
	/**
	 * Specify the data delimiter for the columns (e.g. space, comma, tabs).
	 * @param delimiter column delimiter
	 */
	public void setDataDelimiter(String delimiter)
	{
		this.delimiter = delimiter;
	}
	
	/**
	 * Get the data delimiter for the columns (e.g. space, comma, tabs).
	 * @return column delimiter
	 */
	public String getDataDelimiter(){
		return this.delimiter;
	}
	
	/**
	 * Specify the number of columns.
	 * @param n column count
	 */
	public void setNumberColumns(int n){
		this.nColumns = n;
	}
	
	/**
	 * Get the number of columns.
	 * @return column count
	 */
	public int getNumberColumns(){
		return this.nColumns;
	}
	
	/**
	 * Try to read the header of the file (if its a CSV file)
	 * @return List of column labels
	 * @throws CompressorException 
	 * @throws IOException
	 */
	private String[] readCsvHeader() throws CompressorException
	{
		try{
			String[] headers = null;
			IBIOMESFileReader br = new IBIOMESFileReader(this);
		    String line = null;
		    
		    //read first line and check if this is a CSV header
		    if ((line = br.readLine()) != null)
		    {
		    	//if it looks like a CSV header (not only numbers)
		    	if (!line.matches("[\\s\\,\\.\\d\\-]+"))
		    	{
		    		//if not comma-separated, assume tab/space-delimited
		    		if (line.indexOf(',') < 1)
		    		{
		    			//replace spaces by commas
			    		line = line.replaceAll("(\\s)+", " ").trim().replaceAll(" ", ",");
			    		//System.out.println("Found CSV header: " + line);
			    		
		    		}
		    		hasHeader = true;
		    		headers = line.split("\\,");
		    	}
		    }
		    br.close();
		    return headers;
		}
		catch(IOException e){
			return null;
		}
	}
	
	/**
	 * Try to find column delimiter and 
	 * @param hasHeader Whether the CSV file has a header
	 * @throws CompressorException 
	 * @throws IOException
	 */
	public void findDataFormat(boolean hasHeader) throws CompressorException
	{
		try{
			IBIOMESFileReader br = new IBIOMESFileReader(this);
		    String line = null;
	    	int nColsComma = 0;
	    	int nColsSpace = 0;
		    
		    //skip header if any
		    if (hasHeader)
		    	br.readLine();
		    
		    //read second line
		    if ((line = br.readLine()) != null)
		    {
		    	line = line.trim();
		    	
		    	String[] commaValues = line.split("\\,");
		    	if (commaValues!= null)
		    		nColsComma = commaValues.length;
		    	
		    	String[] spaceValues = line.split("\\s+");
		    	if (spaceValues!= null)
		    		nColsSpace = spaceValues.length;
		    	
		    	if (nColsComma>nColsSpace){
		    		this.setDataDelimiter("\\,");
			    	this.setNumberColumns(nColsComma);
		    	}
		    	else {
		    		this.setDataDelimiter("\\s+");
			    	this.setNumberColumns(nColsSpace);
		    	}
		    }
		    br.close();
		}
		catch(IOException e){
			
		}
	}
}
