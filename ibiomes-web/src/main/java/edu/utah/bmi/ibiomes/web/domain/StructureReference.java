package edu.utah.bmi.ibiomes.web.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Structure reference (e.g. in PDB).
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement
public class StructureReference 
{
	private String database;
	private String referenceId;
	
	public StructureReference(){
	}
	
	/**
	 * Constructor
	 * @param db Database
	 * @param id Database-specific reference ID
	 */
	public StructureReference(String db, String id){
		this.database = db;
		this.referenceId = id;
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
		this.database = database;
	}

	/**
	 * Get atabase-specific reference ID
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
		this.referenceId = referenceId;
	}
}
