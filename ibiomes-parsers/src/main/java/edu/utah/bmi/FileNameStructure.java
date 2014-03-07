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

package edu.utah.bmi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Julien Thibault, University of Utah
 *
 */
public class FileNameStructure implements Comparable<FileNameStructure>{

	private String fileName;
	private String root;
	private int index;
	
	public FileNameStructure(String str)
	{
		this.fileName = str;
		
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(str);
		int start = -1;
		String match = "";
		boolean found = false;
		while (m.find()){
			match=str.substring(m.start(),m.end());
			found = true;
			start = m.start();
		}
		if (found)
		{
			this.index = Integer.parseInt(match);
			this.root = str.substring(0, start);
		}
		else {
			this.index = -1;
			this.root = str;
		}
		
		//System.out.println(this.fileName + ": " + this.root + " [" + this.index + "]");
	}

	public String getFileName() {
		return fileName;
	}

	public String getRoot() {
		return root;
	}

	public int getIndex() {
		return index;
	}

	public int compareTo(FileNameStructure fs) 
	{	
		//if the root string is the same, check indices
		if (this.root.equals(fs.getRoot()))
		{
			if (fs.getIndex()>-1 && this.index<0)
				return -1;
			else if (fs.getIndex()<0 && this.index>-1)
				return 1;
			else
				return this.index - fs.getIndex();
		}
		else //compare root strings
		{
			return this.root.compareTo(fs.getRoot());
		}
	}
}
