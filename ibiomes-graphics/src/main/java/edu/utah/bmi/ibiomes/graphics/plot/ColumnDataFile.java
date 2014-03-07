package edu.utah.bmi.ibiomes.graphics.plot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Parser for CSV files to find number of columns and delimiter (e.g. comma, space)
 * @author Julien Thibault, University of Utah
 *
 */
public class ColumnDataFile 
{
	private boolean hasHeader = false;
	private String[] headers = null;
	private double[] maxValues = null;
	private double[] minValues = null;
	private ArrayList<double[]> dataset = null;
	private double max = 0.0;
	private double min = 0.0;
	private int nColumns = 0;
	private String delimiter = "\\,";
		
	/**
	 * Parse data from CSV file 
	 * @param csvFile CSV file
	 * @throws IOException
	 */
	public ColumnDataFile(File csvFile) throws IOException
	{		
		String headersStr = null;
	    String line = null;
	    BufferedReader br = null;
	    
	    br = new BufferedReader(new FileReader(csvFile));
	    
	    try {
		    //read first line and check if this is a CSV header
		    if ((line = br.readLine()) != null)
		    {	    		
		    	//if it looks like a CSV header (not only numbers)
		    	if (!line.matches("[\\s\\,\\.\\-\\d]+"))
		    	{
		    		//if not comma-separated, assume tab/space-delimited
		    		if (line.indexOf(',') < 1)
		    		{
		    			//replace spaces by commas
			    		line = line.replaceAll("(\\s)+", " ").trim().replaceAll(" ", ",");
			    		//System.out.println("Found CSV header: " + line);
			    		
		    		}
		    		hasHeader = true;
		    		headersStr = line;
		    	}
		    }
		    br.close();
	    } catch (Exception e) {
	    	throw new IOException("Cannot parse CSV header:\n" + e.getLocalizedMessage());
	    }
		
	    try {
			//find column count and delimiter
			br = new BufferedReader(new FileReader(csvFile));
		    line = null;
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
		    	if (commaValues!= null){
		    		nColsComma = commaValues.length;
		    	}
		    	
		    	String[] spaceValues = line.split("\\s+");
		    	if (spaceValues!= null)
		    		nColsSpace = spaceValues.length;
		    	
		    	if (nColsComma>nColsSpace){
		    		delimiter = "\\,";
			    	nColumns = nColsComma;
		    	}
		    	else {
		    		delimiter = "\\s+";
		    		nColumns = nColsSpace;
		    	}
	    		if (headersStr != null){
	    			headers = headersStr.split("\\,");
	    			//check that header and data have same number of columns
	    			if (headers.length != nColumns){
	    				headers = null;
	    			}
	    		}
		    }
		    br.close();
	    } catch (Exception e) {
	    	throw new IOException("Cannot parse CSV data:\n" + e.getLocalizedMessage());
	    }
	    
	    maxValues = new double[nColumns];
		minValues = new double[nColumns];
		
		//read dataset
		try{
			br = new BufferedReader(new FileReader(csvFile));
			
			ArrayList<double[]> valueArrays = new ArrayList<double[]>();
		    line = null;
		    
		    //read first line and check if there is a CSV header
		    if (this.isHasHeader())
		    	line = br.readLine();
		    
		    //for each data point
	        while (( line = br.readLine()) != null)
	        {
	        	line = line.replaceAll("(\\s)+", " ").trim();
	        	if (line.length()>1)
	        	{
		        	String[] valuesStr = line.split(delimiter);
	        		double[] values = new double[valuesStr.length];
		        	for (int v=0;v<valuesStr.length;v++)
		        	{
		        		values[v] = new Double(valuesStr[v]);
		        		//check min and max values for each column
		        		if (v==0){
		        			maxValues[v] = values[v];
		        			minValues[v] = values[v];
			        	}
			        	else {
			        		if (values[v] > maxValues[v])
			        			maxValues[v] = values[v];
				        	if (values[v] < minValues[v])
				        		minValues[v] = values[v];
			        	}
		        	}
	        		valueArrays.add(values);
	        	}
	        }
	        br.close();
	        
	        //find absolute max and min (skip first column)
	        for (int m=1;m < maxValues.length;m++){
	        	if (m==0){
        			max = maxValues[m];
        			min = minValues[m];
	        	}
	        	else {
	        		if (max < maxValues[m])
	        			max = maxValues[m];
		        	if (min > minValues[m])
		        		min = minValues[m];
	        	}
	        }
	        dataset = valueArrays;
		}
		catch(Exception e){
			throw new IOException("Error while reading the values from this file.");
		}
	}
	
	/**
	 * Load numeric values from CSV file
	 * @return Dataset
	 * @throws Exception
	 */
	public ArrayList<double[]> getDataset(){
		return dataset;
	}
	
	/**
	 * Check whether the CSV file has a header or not
	 * @return True if the CSV file has a header
	 */
	public boolean isHasHeader() {
		return hasHeader;
	}

	/**
	 * Set whether the CSV file has a header or not
	 * @param hasHeader True if the CSV file has a header
	 */
	public void setHasHeader(boolean hasHeader) {
		this.hasHeader = hasHeader;
	}

	/**
	 * Get column headers
	 * @return Column headers
	 */
	public String[] getHeaders() {
		return headers;
	}

	/**
	 * Set column headers
	 * @param headers Column headers
	 */
	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	/**
	 * Get number of columns
	 * @return Number of columns
	 */
	public int getNumberOfColumns() {
		return nColumns;
	}

	/**
	 * Set number of columns
	 * @param nColumns Number of columns
	 */
	public void setNumberOfColumns(int nColumns) {
		this.nColumns = nColumns;
	}

	/**
	 * Get column delimiter
	 * @return Column delimiter
	 */
	public String getDelimiter() {
		return delimiter;
	}

	/**
	 * Set column delimiter
	 * @param delimiter Column delimiter
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * Get max values for each column
	 * @return Max values for each column
	 */
	public double[] getMaxValues() {
		return maxValues;
	}

	/**
	 * Set max values for each column
	 * @param maxValues Max values for each column
	 */
	public void setMaxValues(double[] maxValues) {
		this.maxValues = maxValues;
	}

	/**
	 * Get min values for each column
	 * @return Min values for each column
	 */
	public double[] getMinValues() {
		return minValues;
	}
	
	/**
	 * Set min values for each column
	 * @param minValues Min values for each column
	 */
	public void setMinValues(double[] minValues) {
		this.minValues = minValues;
	}

	/**
	 * Get absolute max
	 * @return Absolute max
	 */
	public double getMax() {
		return max;
	}

	/**
	 * Set absolute max 
	 * @param max Absolute max
	 */
	public void setMax(double max) {
		this.max = max;
	}

	/**
	 * Get absolute min
	 * @return Absolute min
	 */
	public double getMin() {
		return min;
	}
	
	/**
	 * Set absolute min 
	 * @param min Absolute min
	 */
	public void setMin(double min) {
		this.min = min;
	}

}
