package edu.utah.bmi.ibiomes.web;

public class ImageThumbnailInfo {

	private String name;
	private String description;
	private String format;
	private String thumbnailUrl;
	private String irodsDataURI;
	
	public ImageThumbnailInfo(
			String name,
			String description,
			String format,
			String thumbnailUrl,
			String irodsDataURI){
		this.name = name;
		this.description = description;
		this.format = format;
		this.thumbnailUrl = thumbnailUrl;
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
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public String getIrodsDataURI() {
		return irodsDataURI;
	}
	public void setIrodsDataURI(String irodsDataURI) {
		this.irodsDataURI = irodsDataURI;
	}
}
