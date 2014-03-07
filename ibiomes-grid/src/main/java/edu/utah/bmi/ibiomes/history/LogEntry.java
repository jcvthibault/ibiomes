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
package edu.utah.bmi.ibiomes.history;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Log entry (note with time stamp).
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="log")
public class LogEntry 
{
	public static final String LOG_ENTRY_DATE_FORMAT = "MM/dd/yy HH:mm:ss";
	
	private Date date;
	private long timestamp;
	private String entry;
	
	public LogEntry(){	
	}
	
	/**
	 * Create new log entry
	 * @param date
	 * @param entry
	 */
	public LogEntry(Date date, String entry){
		this.date = date;
		this.timestamp = date.getTime();
		this.entry = entry;
	}
	
	/**
	 * Load log entry from string representation. Try to parse date.
	 * @param log Log string
	 * @throws ParseException If the format of the date is not correct
	 */
	public LogEntry(String log) throws ParseException
	{
		SimpleDateFormat parserSDF = new SimpleDateFormat(LOG_ENTRY_DATE_FORMAT);
		
		int pos = log.indexOf('#');
		if (pos>-1)
		{
			String date = log.substring(0, pos).trim();
			String entry = log.substring(pos + 1).trim();
			this.date = parserSDF.parse(date);
			this.entry = entry;
		}
		else {
			this.date = null;
			this.entry = log;
		}
	}

	@Override
	public String toString(){
		SimpleDateFormat parserSDF = new SimpleDateFormat(LOG_ENTRY_DATE_FORMAT);
		String date = parserSDF.format(this.date);
		return (date + "# " + this.entry + "");
	}
	
	/**
	 * Get the date of the log
	 * @return Date of the log
	 */
	public Date getDate() {
		return this.date;
	}
	/**
	 * Get log entry
	 * @return Log entry
	 */
	public String getEntry() {
		return this.entry;
	}
	
	/**
	 * Get log timestamp (epoch)
	 * @return Log timestamp (epoch)
	 */
	public long getTimestamp(){
		return this.timestamp;
	}
}
