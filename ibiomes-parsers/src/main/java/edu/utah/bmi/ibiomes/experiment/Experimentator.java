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

package edu.utah.bmi.ibiomes.experiment;

/**
 * Collects information about the person who ran the simulation
 * @author Julien Thibault
 *
 */
public class Experimentator {

	private int id;
	private String firstname;
	private String lastname;
	private String middleName;
	private String affiliation;
	private String email;
	
	/**
	 * 
	 * @param iId
	 * @param iFirstname
	 * @param iLastname
	 * @param iMidname
	 * @param iAffiliation
	 * @param iEmail
	 */
	public Experimentator(int iId, String iFirstname, String iLastname, String iMidname, String iAffiliation, String iEmail){
		id = iId;
		firstname = iFirstname;
		lastname = iLastname;
		middleName = iMidname;
		affiliation = iAffiliation;
		email = iEmail;
	}
	
	public String toString()
	{	
		return (id + ":" + firstname + " " + lastname + " (" + affiliation + ", "+email+")");
	}
	
	public int getID() {
		return id;
	}
	public String getFirstname() {
		return firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public String getMiddleName() {
		return middleName;
	}
	public String getAffiliation() {
		return affiliation;
	}
	public String getEmail() {
		return email;
	}
}
