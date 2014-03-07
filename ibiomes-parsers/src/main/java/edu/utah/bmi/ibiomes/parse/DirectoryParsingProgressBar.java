package edu.utah.bmi.ibiomes.parse;

/**
 * Progress bar for directory parsing
 * @author Julien Thibault, University of Utah
 *
 */
public class DirectoryParsingProgressBar implements IBIOMESListener {

	private int nFiles;
	private int f;
	private String message;
	
	/**
	 * New progress bar
	 * @param nFiles Number of files
	 * @param message Phrase to use before counter
	 */
	public DirectoryParsingProgressBar(int nFiles, String message){
		this.nFiles = nFiles;
		this.f = 0;
		this.message = message;
	}
	
	public void update() {
		this.f++;
		System.out.print(message + " " +f+"/"+nFiles + "...");
		if (f>=nFiles){
			System.out.print("\n");
		}
		else System.out.print("\r");
	}

}
