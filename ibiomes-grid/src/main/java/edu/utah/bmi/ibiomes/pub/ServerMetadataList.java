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

package edu.utah.bmi.ibiomes.pub;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.irods.jargon.core.pub.domain.Resource;

/**
 * Used to retrieve information about the resources available for a given iRODS zone.
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="servers")
public class ServerMetadataList extends ArrayList<ServerMetadata> implements Serializable {

	private static final long serialVersionUID = -7012634361969598272L;

	public ServerMetadataList(){
	}
	
	@XmlElement(name="server")
	public List<ServerMetadata> getServers(){
		return this;
	}
	
	/**
	 * Return the list of resources for this file system
	 * @param irodsResources List of resources
	 * @throws IOException 
	 */
	public ServerMetadataList(List<Resource> irodsResources) throws IOException
	{
		if (irodsResources != null)
		{
			for (Resource m : irodsResources){
				this.add(new ServerMetadata(m));
			}
		}
	}
}
