package edu.utah.bmi.ibiomes.web;

public class JSTLibraries {

	public static String friendlyFileSize(long size){
		
		String sizeUnit = "B";
		if (size>1000000000){
			size = Math.round( size / 1000000000 );
    		sizeUnit = "GB";
		} else if (size>1000000){
			size = Math.round( size / 1000000 );
    		sizeUnit = "MB";
		} else if (size>1000){
			size = Math.round( size / 1000 );
    		sizeUnit = "KB";
		}
		return (String.valueOf(size) + " " + sizeUnit);
	}
}
