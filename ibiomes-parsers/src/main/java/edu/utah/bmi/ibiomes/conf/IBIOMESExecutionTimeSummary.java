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

package edu.utah.bmi.ibiomes.conf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;


/**
 * Singleton used to collect various timings for method executions
 * @author Julien Thibault, University of Utah
 *
 */
public class IBIOMESExecutionTimeSummary {

	private final Logger logger = Logger.getLogger(IBIOMESExecutionTimeSummary.class);
	
	private static IBIOMESExecutionTimeSummary summary;
	private ArrayList<ExecutionTimingRecord> timingRecords = null;

	/**
	 * Private constructor
	 */
	private IBIOMESExecutionTimeSummary(){	
		timingRecords = new ArrayList<ExecutionTimingRecord>();
	}
	
	/**
	 * Get current execution time summary
	 */
	public static IBIOMESExecutionTimeSummary getInstance() throws Exception{
		if (summary == null)
			summary = new IBIOMESExecutionTimeSummary();
		return summary;
	}

	/**
	 * Get records
	 * @return List of records
	 */
	public ArrayList<ExecutionTimingRecord> getTimingRecords() {
		return timingRecords;
	}
	
	/**
	 * Add new record
	 * @param name Name
	 * @param description Description
	 * @param normalTermination Normal termination flag
	 * @param time Time in ms
	 * @return New record
	 */
	public ExecutionTimingRecord addExecutionTimingRecord(String name, String description, boolean normalTermination, long time){
		ExecutionTimingRecord record = new ExecutionTimingRecord(name, description, normalTermination);
		record.setTime(time);
		timingRecords.add(record);
		return record;
	}
	
	/**
	 * Clear records
	 */
	public void clear(){
		this.timingRecords.clear();
	}
	
	/**
	 * Print records
	 */
	public void print(){
		for (ExecutionTimingRecord record : timingRecords){
			System.out.println("["+record.isNormalTermination()+"]["+ record.getTime() +"][" + record.getName() + "] " + record.getDescription() + "");
		}
	}
	
	/**
	 * Print records to log files
	 * @throws IOException 
	 */
	public void printToFile(String logFilePath) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(logFilePath)));
		bw.append("NORMAL_TERMINATION,TIME_MS,NAME,DESCRIPTION\n");
		for (ExecutionTimingRecord record : timingRecords){
			bw.append(record.isNormalTermination()+","+ record.getTime() +"," + record.getName() + "," + record.getDescription() + "\n");
		}
		bw.close();
	}
	
}
