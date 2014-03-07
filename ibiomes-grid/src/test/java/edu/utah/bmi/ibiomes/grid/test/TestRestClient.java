package edu.utah.bmi.ibiomes.grid.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.irods.jargon.core.exception.JargonException;

import edu.utah.bmi.ibiomes.security.IRODSConnector;

public class TestRestClient {

	/**
	 * @param args
	 * @throws JargonException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, JargonException {
		
		// TODO Auto-generated method stub
		String restUrl = "http://juliens-grid-node.chpc.utah.edu:8080/ibiomes-ws/rest/collection?uri=/ibiomesZone/home/jthibault/1BIV";
		try { 
	        URL url = new URL(restUrl); 
	        HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
    		connection.setRequestMethod("GET");
    		connection.setRequestProperty("Accept", "application/xml");
    		BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
    		connection.disconnect();
	    } catch(Exception e) { 
	        throw new RuntimeException(e); 
	    }
	}

}
