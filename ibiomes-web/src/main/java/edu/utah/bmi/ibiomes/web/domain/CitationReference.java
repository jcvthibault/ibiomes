package edu.utah.bmi.ibiomes.web.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Citation reference (from a database)
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement
public class CitationReference 
{
	private String database;
	private String referenceId;
	
	public CitationReference(){
	}
	
	/**
	 * Constructor
	 * @param db Database
	 * @param id Database-specific reference ID 
	 */
	public CitationReference(String db, String id){
		this.setDatabase(db);
		this.setReferenceId(id);
	}

	/**
	 * Get database
	 * @return Database
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * Set database
	 * @param database Database
	 */
	public void setDatabase(String database) {
		if (database!=null)
			this.database = database.trim();
	}

	/**
	 * Get database-specific reference ID 
	 * @return Database-specific reference ID 
	 */
	public String getReferenceId() {
		return referenceId;
	}

	/**
	 * Set database-specific reference ID 
	 * @param referenceId Database-specific reference ID 
	 */
	public void setReferenceId(String referenceId) {
		if (referenceId!=null)
			this.referenceId = referenceId.trim();
	}
}
