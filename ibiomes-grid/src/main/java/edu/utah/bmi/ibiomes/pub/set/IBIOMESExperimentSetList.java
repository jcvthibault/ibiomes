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
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * List of experiment sets
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="sets")
public class IBIOMESExperimentSetList extends ArrayList<IBIOMESExperimentSet> implements Serializable  
{
	private static final long serialVersionUID = 128320931750352570L;

	public IBIOMESExperimentSetList(){
	}
	
	@XmlElement(name="set")
	public List<IBIOMESExperimentSet> getExperimentSets(){
		return this;
	}
}
