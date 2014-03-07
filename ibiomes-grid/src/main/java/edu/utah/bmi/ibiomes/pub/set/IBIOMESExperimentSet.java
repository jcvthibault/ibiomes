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

package edu.utah.bmi.ibiomes.pub.set;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

/**
 * Experiment set
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="set")
public class IBIOMESExperimentSet implements Serializable 
{
	private static final long serialVersionUID = 5930249197965157198L;
	private long id;
	private String owner;
	private long registrationTimestamp;
	private Date registrationDate;
	private String name;
	private String description;
	private boolean isPublic;
	private MetadataAVUList metadata;
	
	/**
	 * Experiment set
	 * @param id ID
	 * @param owner Owner
	 * @param registrationTimestamp
	 * @param name Set name
	 * @param description Set description
	 * @param isPublic Public flag
	 */
	public IBIOMESExperimentSet(
			long id, 
			String owner, 
			long registrationTimestamp, 
			String name, 
			String description,
			boolean isPublic) 
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.registrationTimestamp = registrationTimestamp;
		this.registrationDate = new Date(this.registrationTimestamp*1000);
		this.owner = owner;
		this.isPublic = isPublic;
	}
	
	public long getId() {
		return id;
	}
	public String getOwner() {
		return owner;
	}
	public long getRegistrationTimestamp() {
		return registrationTimestamp;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public boolean getIsPublic() {
		return this.isPublic;
	}
	public MetadataAVUList getMetadata() {
		return metadata;
	}
}
