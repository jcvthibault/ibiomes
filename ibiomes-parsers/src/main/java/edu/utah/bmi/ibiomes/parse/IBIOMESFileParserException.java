package edu.utah.bmi.ibiomes.parse;

/**
 * Exception thrown when file parsing error occurs. 
 * @author Julien Thibault, University of Utah
 *
 */
public class IBIOMESFileParserException extends Exception {

	public IBIOMESFileParserException(Exception e) {
		super(e);
	}
	
	public IBIOMESFileParserException(String m) {
		super(m);
	}

	private static final long serialVersionUID = 525941105629278233L;

}
