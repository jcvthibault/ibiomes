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

/**
 * Default file implementation
 * @author Julien Thibault
 *
 */
public class DefaultLocalFileImpl extends AbstractLocalFileImpl 
{
	private static final long serialVersionUID = -5828778793586101477L;
	
	/**
	 * Constructor
	 * @param localPath Local path to the file
	 * @throws IOException
	 */
	public DefaultLocalFileImpl(String localPath) throws IOException{
		super(localPath, FORMAT_UNKNOWN, TYPE_UNKNOWN);
	}
	
	/**
	 * Constructor
	 * @param fileFormat File format
	 * @param localPath Path to local file
	 * @throws IOException
	 */
	public DefaultLocalFileImpl(String localPath, String fileFormat) throws IOException {
		super(localPath, fileFormat);
	}
	
	/**
	 * Constructor
	 * @param fileFormat File format
	 * @param localPath Path to local file
	 * @throws IOException
	 */
	public DefaultLocalFileImpl(String localPath, String fileFormat, String fileType) throws IOException {
		super(localPath, fileFormat, fileType);
	}
	
	/**
	 * Check if the file is in the expected format
	 */
	public boolean hasValidFormat(int lineNumber, String line){
        return true;
    }
}
