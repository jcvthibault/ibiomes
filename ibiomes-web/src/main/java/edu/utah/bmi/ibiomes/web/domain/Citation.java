package edu.utah.bmi.ibiomes.web.domain;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Citation (literature)
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement
public class Citation 
{
	private String title;
	private List<String> authors;
	private String source;
	private Date publicationDate;
	
	public Citation(){
	}

	/**
	 * Get title
	 * @return Title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set title
	 * @param title Title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get list of authors
	 * @return List of authors
	 */
	public List<String> getAuthors() {
		return authors;
	}

	/**
	 * Set the list of authors
	 * @param authors List of authors
	 */
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	/**
	 * Get source
	 * @return Source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Set source
	 * @param source Source
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Get publication date
	 * @return Publication date
	 */
	public Date getPublicationDate() {
		return publicationDate;
	}

	/**
	 * Set publication date
	 * @param publicationDate Publication date
	 */
	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}
}
