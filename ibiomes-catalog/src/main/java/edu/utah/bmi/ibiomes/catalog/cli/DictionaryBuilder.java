/*
 * iBIOMES - Integrated Biomolecular Simulations
 * Copyright (C) 2014  Julien Thibault, University of Utah
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.utah.bmi.ibiomes.catalog.cli;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

import edu.utah.bmi.ibiomes.catalog.MetadataLookup;

/**
 * Builds metadata lookup dictionary (Lucene index) from MySQL database
 * @author Julien Thibault, University of Utah
 *
 */
public class DictionaryBuilder {
	
	private String _dbServer;
	private String _dbSchema;
	private String _dbUser;
	private String _dbPassword; 
	private int _dbPort; 
	
	public static void main(String args[]) throws Exception {

		if (args.length<4){
			System.out.println("Missing argument.\nUsage: DictionaryBuilder <db-host> <db-schema> <db-username> <db-password> [db-port]\n");
		}
		else {
			//prepare connection to MySQL
			DictionaryBuilder dico = null;
			if (args.length>4){
				dico = new DictionaryBuilder(args[0], args[1], args[2], args[3], Integer.parseInt(args[4]));
			}
			else {
				dico = new DictionaryBuilder(args[0], args[1], args[2], args[3]);
			}

			//create UMLS index from MySql
			String indexFile = "data/metadata/metadata-attr";
			dico.buildIndexFromMysql(indexFile);
		}
		
		
	}
	
	/**
	 * Set DB connection parameters
	 * @param dbServer DB host (IP / hostname)
	 * @param dbSchema DB schema
	 * @param dbUser DB username
	 * @param dbPassword DB password
	 * @param dbPort DB port
	 */
	public DictionaryBuilder(String dbServer, String dbSchema, String dbUser, String dbPassword, int dbPort){
		_dbServer = dbServer;
		_dbSchema = dbSchema;
		_dbUser = dbUser;
		_dbPassword = dbPassword;
		_dbPort = dbPort;
	}
	
	/**
	 * Set DB connection parameters
	 * @param dbServer DB host (IP / hostname)
	 * @param dbSchema DB schema
	 * @param dbUser DB username
	 * @param dbPassword DB password
	 */
	public DictionaryBuilder(String dbServer, String dbSchema, String dbUser, String dbPassword){
		_dbServer = dbServer;
		_dbSchema = dbSchema;
		_dbUser = dbUser;
		_dbPassword = dbPassword;
		_dbPort = 3306;
	}
	
	/**
	 * 
	 * @param semTypes
	 * @param terminologies
	 * @param normalize
	 * @param indexFile
	 * @throws Exception
	 */
	private void buildIndexFromMysql(String indexFile) throws Exception
	{
		Connection con = null;
	    int docCount = 0;
		
	    try{
			Statement stmt;
			//Register the JDBC driver for MySQL.
			Class.forName("com.mysql.jdbc.Driver");

			//Define URL of database
			System.out.println("---------------------------------------------------------------------");
			String url ="jdbc:mysql://"+ _dbServer +":"+_dbPort+"/" + _dbSchema;
			System.out.println("Connecting to database: " + url);
			
			//Get a connection to the database
			con = DriverManager.getConnection( url, _dbUser, _dbPassword);
			System.out.println("Connected!");
			System.out.println("---------------------------------------------------------------------");

			Directory dir = FSDirectory.open(new File(indexFile));
			Analyzer analyzer = new KeywordAnalyzer();
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_41, analyzer);
			IndexWriter writer = new IndexWriter(dir, config);
			writer.deleteAll();
			
			String query = "select * FROM METADATA_ATTRIBUTE";
			  
			//Get a Statement object
			stmt = con.createStatement();

			System.out.println("Executing SQL query...");
			ResultSet rs = stmt.executeQuery(query);

		    //read each SQL record
		    while(rs.next())
		    {
				String attributeCode = rs.getString("CODE");
				attributeCode = attributeCode.toUpperCase();
				String attributeTerm = rs.getString("TERM");
				String attributeDef = rs.getString("DEFINITION");
				String valueType = rs.getString("TYPE");
	
				docCount++;
				Document document = new Document();

	            //indexed fields
	            document.add(new TextField(MetadataLookup.LOOKUP_FIELD_CODE,attributeCode, Field.Store.YES));
	            
	            if (attributeTerm != null){
	              document.add(new TextField(MetadataLookup.LOOKUP_FIELD_TERM,attributeTerm, Field.Store.YES));
	            }
	            if (attributeDef != null){
	              document.add(new TextField(MetadataLookup.LOOKUP_FIELD_DEFINITION,attributeDef, Field.Store.YES));
	            }
	            if (valueType != null){
	              document.add(new TextField(MetadataLookup.LOOKUP_FIELD_TYPE,valueType, Field.Store.YES));
	            }
	            
	            System.out.println("["+docCount+"] " + attributeCode + " (" + attributeTerm + ")");
	            writer.addDocument(document);

			}
		    rs.close();
		    writer.close();
		    
		    System.out.println("\n" + docCount + " added to index '" + indexFile + "'\n");
		    System.out.println("---------------------------------------------------------------------");
			
			//close SQL connection
			System.out.println("Closing SQL connection...");
		    con.close();
		    System.out.println("Done!");
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
			  if (con != null)
				  con.close();
			  System.out.println("\n" + docCount + " added to index '" + indexFile + "'\n");
			  throw new Exception(e);
		  }
	}
}








