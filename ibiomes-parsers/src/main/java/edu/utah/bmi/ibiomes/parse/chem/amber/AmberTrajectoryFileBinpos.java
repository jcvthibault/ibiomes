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

package edu.utah.bmi.ibiomes.parse.chem.amber;

import java.io.IOException;

import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractTrajectoryFile;

/**
 * AMBER BINPOS trajectory file
 * @author Julien Thibault
 *
 */
public class AmberTrajectoryFileBinpos extends AbstractTrajectoryFile
{
	private static final long serialVersionUID = 7539188574632577402L;
	
	/**
	 * Load an AMBER trajectory file
	 * @param path
	 * @throws IOException 
	 */
	public AmberTrajectoryFileBinpos(String path) throws IOException
	{
		super(path, LocalFile.FORMAT_AMBER_TRAJ_BINPOS);
		
	}

	public static boolean checkFormat(String path) {
		
		/*unsigned char buffer[4];
		  buffer[0] = ' ';
		  buffer[1] = ' ';
		  buffer[2] = ' ';
		  buffer[3] = ' ';
		  if ( OpenFile() ) return false;
		  IO->Read(buffer, 4);
		  CloseFile();
		  // Check for the magic header of the Scripps binary format.
		  if (buffer[0] == 'f' &&
		      buffer[1] == 'x' &&
		      buffer[2] == 'y' &&
		      buffer[3] == 'z')
		    return true;*/
		  return false;

	}
}
