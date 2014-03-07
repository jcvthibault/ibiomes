package edu.utah.bmi.ibiomes.web.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author of an experiment
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement
public class Author
{
	private String username;
	private String fullName;
	private String affiliation;
	private String email;
	
	public Author(){
	}
	
	/**
	 * Get author's username
	 * @return author's username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set author's username
	 * @param username Author's username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Get author's full name
	 * @return Author's full name
	 */
	public String getFullName() {
		return fullName;
	}
	
	/**
	 * Set author's full name
	 * @param fullName Author's full name
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Get author's affiliation
	 * @return author's affiliation
	 */
	public String getAffiliation() {
		return affiliation;
	}
	
	/**
	 * Set author's affiliation
	 * @param affiliation Author's affiliation
	 */
	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	/**
	 * Get author's email
	 * @return author's email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Set author's email
	 * @param email author's email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
