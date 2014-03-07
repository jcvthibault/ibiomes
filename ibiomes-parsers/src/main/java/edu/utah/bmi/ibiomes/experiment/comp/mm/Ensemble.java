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

package edu.utah.bmi.ibiomes.experiment.comp.mm;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Ensemble
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement
public class Ensemble {

	/**
	 * Microcanonical ensemble (NVE)
	 */
	public static final String NVE = "NVE";
	/**
	 * Isothermal–isobaric (NPT) ensemble
	 */
	public static final String NPT = "NPT";
	/**
	 * Canonical ensemble (NVT)
	 */
	public static final String NVT = "NVT";
	/**
	 * Generalized ensemble
	 */
	public static final String GENERALIZED_ENSEMBLE = "Generalized ensemble";
	
	private String type;
	private Thermostat themostat;
	private Barostat barostat;
	
	public Ensemble(){
	}
	
	/**
	 * Get ensemble type
	 * @return Ensemble type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Set ensemble type
	 * @param type Ensemble type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Get thermostat
	 * @return Thermostat
	 */
	public Thermostat getThemostat() {
		return themostat;
	}
	
	/**
	 * Set thermostat
	 * @param themostat Thermostat
	 */
	public void setThemostat(Thermostat themostat) {
		this.themostat = themostat;
	}
	
	/**
	 * Get barostat
	 * @return Barostat
	 */
	public Barostat getBarostat() {
		return barostat;
	}
	
	/**
	 * Set barostat
	 * @param barostat Barostat
	 */
	public void setBarostat(Barostat barostat) {
		this.barostat = barostat;
	}
}
