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

import java.util.Comparator;

/**
 * Comparator for log entries (based on time stamp to sort from newest to oldest)
 * @author Julien Thibault
 *
 */
public class LogEntryComparator implements Comparator<LogEntry> 
{

	public int compare(LogEntry log1, LogEntry log2){
		
		if (log1.getDate()==null && log2.getDate()==null){
			return 0;
		}
		else if (log1.getDate()!=null && log2.getDate()==null){
			return -1;
		}
		else if (log2.getDate() != null && log1.getDate()==null) {
			return 1;
		}
		else if (log1.getDate().after(log2.getDate())){
			return -1;
		}
		else if (log1.getDate().before(log2.getDate())){ 
			return 1;
		}
		else return 0;
	}
}
