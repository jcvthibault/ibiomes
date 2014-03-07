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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.irods.jargon.core.pub.domain.UserGroup;


@XmlRootElement(name="usergroups")
public class UserGroupListMetadata extends ArrayList<UserGroupMetadata>  implements Serializable
{
	private static final long serialVersionUID = 1028786416042361697L;

	public UserGroupListMetadata(){
	}
	
	@XmlElement(name="usergroup")
	public List<UserGroupMetadata> getUserGroups(){
		return this;
	}
	
	/**
	 * Create list of users of the file system
	 */
	public UserGroupListMetadata(List<UserGroup> groups) 
	{
		if (groups != null){
			for (UserGroup u : groups){
				this.add(new UserGroupMetadata(u));
			}
			Collections.sort(this);
		}
	}
	
}
