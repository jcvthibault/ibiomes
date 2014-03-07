package edu.utah.bmi.ibiomes.web;

public class IBIOMESUrl {
	
	private String name;
	private String description;
	private String format;
	private String irodsDataURI;
	
	public IBIOMESUrl(String name, String description, String format, String irodsDataURI){
		this.name = name;
		this.format = format;
		this.description = description;
		this.irodsDataURI = irodsDataURI;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getIrodsDataURI() {
		return irodsDataURI;
	}

	public void setIrodsDataURI(String irodsDataURI) {
		this.irodsDataURI = irodsDataURI;
	}
}
