package edu.utah.bmi.ibiomes.web.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.DataNotFoundException;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.CollectionAO;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.IRODSRegistrationOfFilesAO;
import org.irods.jargon.core.pub.domain.AvuData;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.irods.jargon.core.query.AVUQueryElement;
import org.irods.jargon.core.query.AVUQueryElement.AVUQueryPart;
import org.irods.jargon.core.query.AVUQueryOperatorEnum;
import org.irods.jargon.core.query.JargonQueryException;
import org.irods.jargon.core.query.MetaDataAndDomainData;
import org.irods.jargon.core.query.RodsGenQueryEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.utah.bmi.ibiomes.web.domain.Method;
import edu.utah.bmi.ibiomes.web.domain.Simulation;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.pub.IBIOMESCollection;
import edu.utah.bmi.ibiomes.pub.IBIOMESCollectionAO;
import edu.utah.bmi.ibiomes.pub.IBIOMESCollectionList;
import edu.utah.bmi.ibiomes.pub.IBIOMESExperimentAO;
import edu.utah.bmi.ibiomes.pub.IBIOMESFile;
import edu.utah.bmi.ibiomes.pub.IBIOMESFileAO;
import edu.utah.bmi.ibiomes.pub.IBIOMESFileList;
import edu.utah.bmi.ibiomes.search.IBIOMESExperimentSearch;
import edu.utah.bmi.ibiomes.search.IBIOMESFileSearch;
import edu.utah.bmi.ibiomes.pub.set.IBIOMESExperimentSetAO;
import edu.utah.bmi.ibiomes.pub.set.IBIOMESExperimentSetList;
import edu.utah.bmi.ibiomes.web.IBIOMESResponse;
import edu.utah.bmi.ibiomes.web.IBIOMESUrl;
import edu.utah.bmi.ibiomes.catalog.MetadataLookup;
import edu.utah.bmi.ibiomes.metadata.FileMetadata;
import edu.utah.bmi.ibiomes.metadata.IBIOMESFileGroup;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataRichAVUList;

@Controller
public class CollectionService
{	
	@Autowired(required = true)  
    private HttpServletRequest request;   
    public HttpServletRequest getRequest() {  
        return request;  
    }
    
    @Autowired(required = true)  
    private IRODSAccessObjectFactory irodsAccessObjectFactory;
	public IRODSAccessObjectFactory getIrodsAccessObjectFactory() {
		return irodsAccessObjectFactory;
	}
	@Autowired(required = true)
    private ServletContext context;
	
	private DataSource getDataSource(){
    	WebApplicationContext wContext = WebApplicationContextUtils.getWebApplicationContext(this.context);
    	return (DataSource)wContext.getBean("dataSource");
	}

    private MetadataLookup metadataDictionary;
    private MetadataLookup getMetadataDictionary(){
    	if (metadataDictionary == null)
    		metadataDictionary = new MetadataLookup(this.context.getRealPath("config/metadata-attr"));
    	return metadataDictionary;
    }
    
	private final static int N_RECORDS_SEARCH = 100;
    private final static String SESSION_FILE_LIST = "SESSION_FILE_LIST";
	
    /**
     * 
     * @param uri
     * @param id
     * @param factory
     * @return
     * @throws JargonQueryException 
     * @throws JargonException 
     * @throws FileNotFoundException 
     */
    private IBIOMESCollection getCollectionFromIdOrPath(String uri, int id, IBIOMESCollectionAO factory) throws JargonException, JargonQueryException, FileNotFoundException{
    	if (uri != null && uri.length()>0)
    		return factory.getCollectionFromPath(uri);
    	else 
    		return factory.getCollectionByID(id);
    }
    
	/**
	 * Retrieve iBIOMES collection
	 * @param uri Collection URI
	 * @return iBIOMES collection
	 * @throws Exception
	 */
	@RequestMapping(value="/services/collection", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getCollectionDetails(
			@RequestParam(value="uri",required=false, defaultValue="") String uri,
			@RequestParam(value="id",required=false, defaultValue="-1") int id) 
	{
		try {
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IBIOMESCollectionAO factory = new IBIOMESCollectionAO(irodsAccessObjectFactory, irodsAccount);
			IBIOMESCollection coll = this.getCollectionFromIdOrPath(uri, id, factory);
			Simulation sim = new Simulation(coll);
			return new IBIOMESResponse(true, null, sim);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}

	/**
	 * Retrieve iBIOMES collection
	 * @param uri Collection URI
	 * @return iBIOMES collection
	 * @throws Exception
	 */
	@RequestMapping(value="/services/collection/xml", method = RequestMethod.GET, produces="application/xml")
	@ResponseBody
	public Simulation getCollectionAsXml(
			@RequestParam(value="uri", required=false, defaultValue="") String uri,
			@RequestParam(value="id", required=false, defaultValue="-1") int id) 
	{
		try {
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IBIOMESCollectionAO factory = new IBIOMESCollectionAO(irodsAccessObjectFactory, irodsAccount);
			IBIOMESCollection coll = this.getCollectionFromIdOrPath(uri, id, factory);
			Simulation sim = new Simulation(coll);
			return sim;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Retrieve files contained in the given collection
	 * @param uri File URI
	 * @return File list
	 * @throws JargonQueryException 
	 * @throws JargonException 
	 * @throws IOException 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/services/collection/files", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getCollectionFiles(
			@RequestParam("uri") String uri,
			@RequestParam(value="recursive", required=false, defaultValue="true") String recursiveStr,
			@RequestParam(value="excludeHidden", required=false, defaultValue="true") boolean excludeHidden
		) throws JargonException, JargonQueryException, IOException
	{
		try{
			boolean recursive = recursiveStr.toLowerCase().matches("(t(rue)?)|(y(es)?)");
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
			List<IBIOMESFile> files = ibiomesFileAO.getFilesUnderCollection(uri, excludeHidden, recursive);
			return new IBIOMESResponse(true, null, files);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Retrieve files contained in the given collection, and sort them by format
	 * @param uri File URI
	 * @return File list
	 * @throws JargonQueryException 
	 * @throws JargonException 
	 * @throws IOException 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/services/collection/files/formatted", method = RequestMethod.GET)
	@ResponseBody
	public  IBIOMESResponse getCollectionFilesByFormat(
			@RequestParam("uri") String uri,
			@RequestParam(value="recursive", required=false, defaultValue="true") String recursiveStr,
			@RequestParam(value="excludeHidden", required=false, defaultValue="true") boolean excludeHidden
		) throws JargonException, JargonQueryException, IOException
	{
		try{
			boolean recursive = recursiveStr.toLowerCase().matches("(t(rue)?)|(y(es)?)");
			
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
			List<IBIOMESFile> files = ibiomesFileAO.getFilesUnderCollection(uri, excludeHidden, recursive);
			
			HashMap<String, List<IBIOMESFile>> fileListByFormat = new HashMap<String, List<IBIOMESFile>>();
			for (IBIOMESFile iFile : files){
				if (!fileListByFormat.containsKey(iFile.getFormat())){
					fileListByFormat.put(iFile.getFormat(), new ArrayList<IBIOMESFile>());
				}
				fileListByFormat.get(iFile.getFormat()).add(iFile);
			}
			return new IBIOMESResponse(true, null, fileListByFormat);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Retrieve files contained in the given collection
	 * @param uri File URI
	 * @return File list
	 * @throws JargonQueryException 
	 * @throws JargonException 
	 * @throws IOException 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/services/collection/files/paged", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getCollectionFilesWithPaging(
			@RequestParam("uri") String uri,
			@RequestParam(value="numberOfRows",required=false, defaultValue="20") int maxRows,
			@RequestParam(value="continueIndex",required=false, defaultValue="0") int continueIndex,
			@RequestParam(value="excludeHidden", required=false, defaultValue="true") boolean excludeHidden
		) throws JargonException, JargonQueryException, IOException
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
			
			IBIOMESFileSearch fileSearch = (IBIOMESFileSearch)request.getSession().getAttribute(SESSION_FILE_LIST);
			if (fileSearch == null || continueIndex==0){
				if (fileSearch!=null)
					fileSearch.closeSearch();
				fileSearch = new IBIOMESFileSearch(true, true, irodsAccessObjectFactory, irodsAccount);
				fileSearch.setNumberOfRowsRequested(maxRows);
				if (excludeHidden){
					//TODO check if files without the FILE_IS_HIDDEN attribute are returned as they should
					List<AVUQueryElement> conditionList = new ArrayList<AVUQueryElement>();
					conditionList.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, FileMetadata.FILE_IS_HIDDEN));
					conditionList.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.NOT_EQUAL, "true"));
					fileSearch.setAvuConditions(conditionList);
				}
				fileSearch.setDirectory(uri);
			}
			List<String> uris = fileSearch.executeWithStart(continueIndex);
			
			IBIOMESFileList fileList = null;
			if (uris != null && uris.size()>0){
				fileList = ibiomesFileAO.getFilesFromPath(uris);
			}
			request.getSession().setAttribute(SESSION_FILE_LIST, fileSearch);
			return new IBIOMESResponse(true, null, fileList);
			
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Retrieve files contained in the given collection, and sort them by format
	 * @param uri File URI
	 * @return File list
	 * @throws JargonQueryException 
	 * @throws JargonException 
	 * @throws IOException 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/services/collection/files/formatted/paged", method = RequestMethod.GET)
	@ResponseBody
	public  IBIOMESResponse getCollectionFilesByFormatWithPaging(
			@RequestParam("uri") String uri,
			@RequestParam(value="numberOfRows",required=false, defaultValue="20") int maxRows,
			@RequestParam(value="continueIndex",required=false, defaultValue="0") int continueIndex,
			@RequestParam(value="excludeHidden", required=false, defaultValue="true") boolean excludeHidden
		) throws JargonException, JargonQueryException, IOException
	{
		try{			
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
			
			IBIOMESFileSearch fileSearch = (IBIOMESFileSearch)request.getSession().getAttribute(SESSION_FILE_LIST);
			if (fileSearch == null || continueIndex==0){
				if (fileSearch!=null)
					fileSearch.closeSearch();
				fileSearch = new IBIOMESFileSearch(true, true, irodsAccessObjectFactory, irodsAccount);
				fileSearch.setNumberOfRowsRequested(maxRows);
				if (excludeHidden){
					//TODO check if files without the FILE_IS_HIDDEN attribute are returned as they should
					//TODO NOT_EQUAL DOESNT WORK!!
					List<AVUQueryElement> conditionList = new ArrayList<AVUQueryElement>();
					conditionList.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, FileMetadata.FILE_IS_HIDDEN));
					conditionList.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.NOT_EQUAL, "true"));
					fileSearch.setAvuConditions(conditionList);
				}
				fileSearch.setDirectory(uri);
			}
			List<String> uris = fileSearch.executeWithStart(continueIndex);
			request.getSession().setAttribute(SESSION_FILE_LIST, fileSearch);
			
			HashMap<String, List<IBIOMESFile>> fileListByFormat = new HashMap<String, List<IBIOMESFile>>();
			IBIOMESFileList fileList = null;
			if (uris != null && uris.size()>0){
				fileList = ibiomesFileAO.getFilesFromPath(uris);
				for (IBIOMESFile iFile : fileList){
					if (!fileListByFormat.containsKey(iFile.getFormat())){
						fileListByFormat.put(iFile.getFormat(), new ArrayList<IBIOMESFile>());
					}
					fileListByFormat.get(iFile.getFormat()).add(iFile);
				}
			}						
			return new IBIOMESResponse(true, null, fileListByFormat);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Retrieve files contained in the given collection
	 * @param uri File URI
	 * @return File list
	 * @throws Exception 
	 */
	@RequestMapping(value = "/services/collection/files/byformat", method = RequestMethod.GET)
	@ResponseBody
	public  IBIOMESResponse getCollectionFilesInFormat(
			@RequestParam("uri") String uri,
			@RequestParam("formats") String formatsStr,
			@RequestParam(value="recursive", required=false, defaultValue="true") String recursiveStr,
			@RequestParam(value="excludeHidden", required=false, defaultValue="true") boolean excludeHidden
		)
	{	
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try{
			String[] formats = formatsStr.split("\\,");
			boolean recursive = recursiveStr.toLowerCase().matches("(t(rue)?)|(y(es)?)");
			IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
			
			List<String> fileNames = new ArrayList<String>();
			
			//for each format
			for (int f=0; f<formats.length; f++)
			{
				String format = formats[f];
				if (format.toLowerCase().trim().matches("(other)|(unknown)|()"))
					format = LocalFile.FORMAT_UNKNOWN;
				//create AVU query to find files in this collection that have a certain format
		      	List<AVUQueryElement> avuQuery = new ArrayList<AVUQueryElement>();
				avuQuery.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.LIKE, FileMetadata.FILE_FORMAT));
				avuQuery.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, formats[f]));
				
				//find files at first level
				IBIOMESFileSearch search = new IBIOMESFileSearch( irodsAccessObjectFactory, irodsAccount);
		      	search.setAvuConditions(avuQuery);
		      	search.setNumberOfRowsRequested(N_RECORDS_SEARCH);
		      	search.setDirectory(uri);
		      	fileNames.addAll(search.executeAndClose());
		      	//find files recursively
		      	if (recursive){
		      		search = new IBIOMESFileSearch( irodsAccessObjectFactory, irodsAccount);
			      	search.setAvuConditions(avuQuery);
			      	search.setNumberOfRowsRequested(N_RECORDS_SEARCH);
			      	search.setDirectory(uri + "/%");
			      	fileNames.addAll(search.executeAndClose());
		      	}	      	
			}
			List<IBIOMESFile> files = ibiomesFileAO.getFilesFromPath(fileNames, excludeHidden);
			
			return new IBIOMESResponse(true, null, files);
			
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Retrieve iBIOMES collections in home directory
	 * @return Collection list
	 * @throws IOException 
	 * @throws JargonException 
	 * @throws JargonQueryException 
	 */
	@RequestMapping(value = "/services/collections/home", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getCollectionsInHomeDir() throws JargonException, JargonQueryException, IOException
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IBIOMESCollectionAO factory = new IBIOMESCollectionAO(irodsAccessObjectFactory, irodsAccount);
			List<IBIOMESCollection> collections = factory.getCollectionsUnderParent(irodsAccount.getZone() + "/home/" + irodsAccount.getUserName(), false);
	
			List<Simulation> simulations = new ArrayList<Simulation>();
			for (IBIOMESCollection coll : collections){
				simulations.add(new Simulation(coll));
			}
			return new IBIOMESResponse(true, null, simulations);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Retrieve iBIOMES collections under given collection
	 * @return Collection list
	 * @throws IOException 
	 * @throws JargonException 
	 * @throws JargonQueryException 
	 */
	@RequestMapping(value = "/services/collections/under", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getSubCollections(
			@RequestParam("uri") String uri,
			@RequestParam(value="recursive", required=false, defaultValue="false") boolean recursive
		) throws JargonException, JargonQueryException, IOException
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IBIOMESCollectionAO ibiomesCollAO = new IBIOMESCollectionAO(irodsAccessObjectFactory, irodsAccount);
			IBIOMESCollectionList collections = ibiomesCollAO.getCollectionsUnderParent(uri, recursive);
			return new IBIOMESResponse(true, null, collections);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Retrieve AVUs for a given collection (filter on the attribute possible)
	 * @return AVU list
	 */
	@RequestMapping(value = "/services/collection/avus", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getCollectionAVUs(
					@RequestParam("uri") String uri,
					@RequestParam(value="attribute", required=false) String attribute) 
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			CollectionAO coFactory = irodsAccessObjectFactory.getCollectionAO(irodsAccount);
			List<MetaDataAndDomainData> metadata;
			//if filtering on the attribute
			if (attribute != null){
				List<AVUQueryElement> avus = new ArrayList<AVUQueryElement>();
				avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, attribute));
				avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, "%"));
				metadata = coFactory.findMetadataValuesByMetadataQueryForCollection(avus, uri);
			}
			else {
				metadata = coFactory.findMetadataValuesForCollection(uri);
			}
			
			MetadataAVUList metadataList = new MetadataAVUList();
			if (metadata!=null && metadata.size()>0)
			{
				for (MetaDataAndDomainData m : metadata){
					metadataList.add(new MetadataAVU(m.getAvuAttribute(), m.getAvuValue(), m.getAvuUnit()));
				}
			}
			MetadataRichAVUList richMetadata = getMetadataDictionary().lookupMetadataAVU(metadataList);
			return new IBIOMESResponse(true, null, richMetadata);
			
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Add AVU to given collection
	 * @return new AVU
	 */
	@RequestMapping(value = "/services/collection/avus/add", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse addCollectionAVU(
					@RequestParam("uri") String uri,
					@RequestParam("attribute") String attribute,
					@RequestParam("value") String value,
					@RequestParam(value="unit", required=false, defaultValue="") String unit) 
	{
		try{
			if (unit==null)
				unit = "";
			AvuData avu = AvuData.instance(attribute, value, unit);
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			CollectionAO ao = irodsAccessObjectFactory.getCollectionAO(irodsAccount);
			ao.addAVUMetadata(uri, avu);
			return new IBIOMESResponse(true, "AVU successfully added to collection '"+uri+"'", new MetadataAVU(attribute, value));
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "AVU could not be added to collection '"+uri+"'. Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Update AVU for given collection
	 * @return new AVU
	 */
	@RequestMapping(value = "/services/collection/avus/update", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse updateCollectionAVU(
					@RequestParam("uri") String uri,
					@RequestParam(value="attribute", required=false) String attribute,
					@RequestParam(value="value", required=false) String value,
					@RequestParam(value="unit", required=false, defaultValue="") String unit,
					@RequestParam(value="string", required=false) String paramStr) 
	{		
		List<MetadataAVU> avus = new ArrayList<MetadataAVU>();
		//if arguments are Attribute-Value-Unit
		if (attribute != null && attribute.length()>0){
			try{
				avus.add(new MetadataAVU(attribute, value, unit));
			}
			catch (Exception e) {
				return new IBIOMESResponse(false, "An error occurred when reading the new AVU value. Exception: "+e.getMessage(), null);
			}
		}
		else if (paramStr != null && paramStr.length()>0)
		{
			try{
				//parse AVUs from string
				String[] params = paramStr.split("\\|\\|");
				for (int p=0;p<params.length;p++)
				{
					String[] avu = params[p].split("\\=");
					if (!avu[0].equals("x") && !avu[0].equals("y"))
					{
						value = "";
						if (avu.length>1)
							value = avu[1];
						avus.add(new MetadataAVU(avu[0], value, ""));
					}
				}
			}
			catch (Exception e) {
				return new IBIOMESResponse(false, "An error occurred when reading the new AVU values. Exception: "+e.getMessage(), null);
			}
		}
		else
			return new IBIOMESResponse(false, "No AVU was specified", null);

		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		
		try{
			CollectionAO ao = irodsAccessObjectFactory.getCollectionAO(irodsAccount);
			IBIOMESCollectionAO factory = new IBIOMESCollectionAO(irodsAccessObjectFactory, irodsAccount);
			IBIOMESCollection iColl = factory.getCollectionFromPath(uri);
			
			for (MetadataAVU avu : avus)
			{	
				String newValue = avu.getValue();
				String newAttribute = avu.getAttribute();
				
				if ((newValue.toLowerCase().equals("unknown") || newValue.length()==0) )
				{	
					//if current value needs to be deleted
					if (iColl.getMetadata().containsAttribute(newAttribute))
						ao.deleteAVUMetadata(uri, AvuData.instance(newAttribute, iColl.getMetadata().getValue(newAttribute), ""));
				}
				else if (iColl.getMetadata().containsAttribute(avu.getAttribute()))	{
					//modify existing AVU
					ao.modifyAvuValueBasedOnGivenAttributeAndUnit(uri, AvuData.instance(avu.getAttribute(), avu.getValue(), avu.getUnit()));
				}
				else {
					//add new AVU to hide file
					ao.addAVUMetadata(uri, AvuData.instance(avu.getAttribute(), avu.getValue(), avu.getUnit()));
				}
			}
		}
		catch (DataNotFoundException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Data not found at '"+uri+"'. Exception: "+e.getMessage(), null);
		} catch (JargonException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Error: "+e.getMessage(), null);
		} catch (JargonQueryException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Error: "+e.getMessage(), null);
		}
		return new IBIOMESResponse(true, "AVUs successfully added to collection '"+uri+"'", null);
	}
	
	/**
	 * Delete AVU for given collection
	 * @return AVU
	 */
	@RequestMapping(value = "/services/collection/avus/delete", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse deleteCollectionAVU(
					@RequestParam("uri") String uri,
					@RequestParam("attribute") String attribute,
					@RequestParam("value") String value,
					@RequestParam(value="unit", required=false, defaultValue="") String unit) 
	{
		try{
			if (unit==null)
				unit = "";
			
			String[] values = value.split("\\|\\|");
			if (values!=null)
			{
				IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
				CollectionAO ao = irodsAccessObjectFactory.getCollectionAO(irodsAccount);
				
				for (int v=0; v< values.length; v++)
				{
					try{
						AvuData avu = AvuData.instance(attribute, values[v], unit);
						ao.deleteAVUMetadata(uri, avu);
					} catch (JargonException exc) {
						return new IBIOMESResponse(false, "AVU could not be deleted for '"+uri+"'. Exception: " + exc.getMessage(), null);
					}
				}
				return new IBIOMESResponse(true, "AVUs successfully deleted '"+uri+"'", null);
			}
			return new IBIOMESResponse(false, "No AVU was deleted for '"+uri+"'", null);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "AVUs could not be deleted. Exception: " + e.getMessage(), null);
		}
	}
	/**
	 * Retrieve list of latest uploads
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/services/collections/latest", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getUserUploads(
			@RequestParam(value="nRows", required=false, defaultValue="12") int nRows)
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		if (irodsAccount == null){
			return new IBIOMESResponse(false, "User not authenticated!", null);
		}
		
		try{
			List<Simulation> simulations = new ArrayList<Simulation>();
			IBIOMESCollectionAO iFactory = new IBIOMESCollectionAO(irodsAccessObjectFactory, irodsAccount);
			
			IBIOMESExperimentSearch collectionSearch = new IBIOMESExperimentSearch( irodsAccessObjectFactory, irodsAccount);
			collectionSearch.setNumberOfRowsRequested(nRows);
			collectionSearch.setOrderBy(RodsGenQueryEnum.COL_COLL_CREATE_TIME);
			collectionSearch.setAscendant(false);
			
			List<String> collectionPaths = collectionSearch.executeAndClose();
			
			if (collectionPaths != null && collectionPaths.size()>0)
			{
				//get metadata
				IBIOMESCollectionList collections = iFactory.getCollectionsFromPath(collectionPaths);
				
				for (IBIOMESCollection c : collections)
				{
					//populate pojo with simulation info
					Simulation sim = new Simulation(c);
					//get abbreviation for method instead of full string
					String method = sim.getMethod().getName();
					if (method == null){
						method = "?";
					}
					else if (method.equals(Method.METHOD_MD) 
							|| method.equals(Method.METHOD_LANGEVIN_DYNAMICS)){
						method = "MD";
					}
					else if (method.equals(Method.METHOD_REMD)){
						method = "REMD";
					}
					else if (method.equals(Method.METHOD_QM)){
						method = "QM";
					}
					else if (method.equals(Method.METHOD_QUANTUM_MD)){
						method = "QMD";
					}
					else if (method.equals(Method.METHOD_QMMM)){
						method = "QM/MM";
					}
					sim.getMethod().setName(method);
					simulations.add(sim);
				}
			}
			return new IBIOMESResponse(true, null, simulations);
			
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception for user '"+irodsAccount.getUserName()+"': " + e.getMessage(), null);
		}
	}
	
	/**
	 * Update the method/parameter-related metadata based on a given file
	 * @param uri Collection URI
	 * @param fileUri File URI
	 * @return List of new metadata
	 */
	@RequestMapping(value = "/services/collection/updatemethod", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse updateCollectionMethodMetadata(
					@RequestParam("uri") String uri,
					@RequestParam("fileUri") String fileUri) 
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try{
			IBIOMESExperimentAO experimentAO = new IBIOMESExperimentAO(irodsAccessObjectFactory, irodsAccount);
			MetadataAVUList newMetadata = experimentAO.updateMethodMetadataFromFile(uri, fileUri);
			return new IBIOMESResponse(true, "Method metadata successfully updated", newMetadata);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Update the topology/system-related metadata of the collection (experiment) based on a given file
	 * @param uri Collection URI
	 * @param fileUri File URI
	 * @return List of new metadata
	 */
	@RequestMapping(value = "/services/collection/updatetopology", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse updateCollectionTopologyMetadata(
					@RequestParam("uri") String uri,
					@RequestParam("fileUri") String fileUri)
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try{
			IBIOMESExperimentAO experimentAO = new IBIOMESExperimentAO(irodsAccessObjectFactory, irodsAccount);
			MetadataAVUList newMetadata = experimentAO.updateTopologyMetadataFromFile(uri, fileUri);
			return new IBIOMESResponse(true, "Topology metadata sucessfully updated", newMetadata);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Delete collection(s)
	 * @return True if success
	 */
	@RequestMapping(value = "/services/collection/delete", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse deleteCollection(@RequestParam("uris") String urisString) 
	{
		try{
			String[] uris = urisString.split("\\,");
			boolean success = true;
			//remove files (deletion)
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IRODSFileFactory factory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
			for (int f=0;f<uris.length;f++)
			{
				IRODSFile file = factory.instanceIRODSFile(uris[f]);
				//try to remove it
				boolean ok = file.deleteWithForceOption();
				if (!ok)
					success = false;
			}
			return new IBIOMESResponse(success, null, null);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Unregister collection(s) and keep physical files
	 * @return True if success
	 */
	@RequestMapping(value = "/services/collection/unregister", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse unregisterCollections(@RequestParam("uris") String urisString) 
	{
		try{
			String[] uris = urisString.split("\\,");
			boolean success = true;
			//remove files (deletion)
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IRODSRegistrationOfFilesAO ao = irodsAccessObjectFactory.getIRODSRegistrationOfFilesAO(irodsAccount);
			for (int f=0;f<uris.length;f++)
			{
				boolean ok = ao.unregisterCollection(uris[f], true);
				if (!ok)
					success = false;
			}
			return new IBIOMESResponse(success, null, null);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Rename collection
	 * @return True if success
	 */
	@RequestMapping(value = "/services/collection/rename", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse renameCollection(
			@RequestParam("uri") String uri,
			@RequestParam("name") String newName) 
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IRODSFileFactory factory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
			IRODSFile file = factory.instanceIRODSFile(uri);
			IRODSFile target = factory.instanceIRODSFile(file.getParent() + "/" + newName);
			boolean success = file.renameTo(target);
			return new IBIOMESResponse(true, null, success);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Create collection
	 * @return True if success
	 */
	@RequestMapping(value = "/services/collection/create", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse createCollection(
			@RequestParam("uri") String uri) 
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IRODSFileFactory factory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
			IRODSFile file = factory.instanceIRODSFile(uri);
			boolean success = file.mkdir();
			return new IBIOMESResponse(true, null, success);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Retrieve list of experiment sets that contain this experiment
	 * @return AVU list
	 */
	@RequestMapping(value = "/services/collection/sets", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse getReferencingExperimentSets(@RequestParam("uri") String uri) 
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try {
			IBIOMESExperimentSetAO coFactory = new IBIOMESExperimentSetAO(irodsAccessObjectFactory, irodsAccount, this.getDataSource());
			IBIOMESExperimentSetList  sets = coFactory.getReferencingExperimentSets(uri);
			return new IBIOMESResponse(true, null, sets);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve list of experiment sets (number format exception).", null);
		} catch (JargonException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve list of experiment sets (Jargon exception: "+e.getMessage()+").", null);
		} catch (JargonQueryException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve list of experiment sets (Jargon exception: "+e.getMessage()+").", null);
		} catch (SQLException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve list of experiment sets (SQL exception: "+e.getMessage()+").", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve list of experiment sets (Exception: "+e.getMessage()+").", null);
		}		
	}
	
	/**
	 * Retrieve list of files set as analysis data for a particular experiment.
	 * @param uri Experiment URI
	 * @return List of thumbnail details
	 */
	@RequestMapping(value="services/experiment/analysis/links", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getAnalysisDataLinks(@RequestParam("uri") String uri)
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
			IBIOMESExperimentAO eao = new IBIOMESExperimentAO(irodsAccessObjectFactory, irodsAccount);
			
			List<IBIOMESUrl> analysisLinks = new ArrayList<IBIOMESUrl>();
			List<String> analysisFileNames = eao.getAnalysisFilesInExperiment(uri);
	      	
			List<IBIOMESFile> analysisFiles = ibiomesFileAO.getFilesFromPath(analysisFileNames);
						
			//classify files by type and format
			if (analysisFiles != null && analysisFiles.size()>0)
			{	
				for (IBIOMESFile file : analysisFiles)
				{
					//get file metadata
					MetadataAVUList fileMeta = file.getMetadata();
					
					String fileFormat = fileMeta.getValue(FileMetadata.FILE_FORMAT);
					String fileDescription = fileMeta.getValue(FileMetadata.FILE_DESCRIPTION);
					
					if (fileDescription.length()==0)
						fileDescription = file.getName();
						
					if (fileFormat == null || fileFormat.length()==0 || fileFormat.equals(LocalFile.FORMAT_UNKNOWN))
						fileFormat = "Other";
					
					if ( !IBIOMESFileGroup.isImageFile(fileFormat)){
						analysisLinks.add(new IBIOMESUrl(file.getName(), fileDescription, file.getFormat(), file.getAbsolutePath()));
					}
				}
			}
			return new IBIOMESResponse(true, null, analysisLinks);
		}
		catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Retrieve list of files set as analysis data for a particular experiment.
	 * @param uri Experiment URI
	 * @return List of thumbnail details
	 */
	@RequestMapping(value="services/experiment/files/analysis", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getAnalysisFiles(@RequestParam("uri") String uri)
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
			IBIOMESExperimentAO eao = new IBIOMESExperimentAO(irodsAccessObjectFactory, irodsAccount);
				      	
			List<String> filePaths = eao.getAnalysisFilesInExperiment(uri);
			List<IBIOMESFile> analysisFiles = ibiomesFileAO.getFilesFromPath(filePaths);
			
			return new IBIOMESResponse(true, null, analysisFiles);
		}
		catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Retrieve list of hidden files for a particular experiment.
	 * @param uri Experiment URI
	 * @return List of hidden files
	 */
	@RequestMapping(value="services/experiment/files/hidden", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getHiddenFiles(@RequestParam("uri") String uri)
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
			
	      	//create AVU query to find files in this collection that are hidden
	      	List<AVUQueryElement> avuQuery = new ArrayList<AVUQueryElement>();
			avuQuery.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.LIKE, FileMetadata.FILE_IS_HIDDEN));
			avuQuery.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, "true"));
			
			//find files at first level
			IBIOMESFileSearch search = new IBIOMESFileSearch(true, false, irodsAccessObjectFactory, irodsAccount);
	      	search.setAvuConditions(avuQuery);
	      	search.setNumberOfRowsRequested(N_RECORDS_SEARCH);
	      	search.setDirectory(uri);
	      	List<String> hiddenFileNames = search.executeAndClose();
	      	
	      	//find files recursively
	      	search = new IBIOMESFileSearch(true, false, irodsAccessObjectFactory, irodsAccount);
	      	search.setAvuConditions(avuQuery);
	      	search.setNumberOfRowsRequested(N_RECORDS_SEARCH);
	      	search.setDirectory(uri + "/%");
	      	hiddenFileNames.addAll(search.executeAndClose());
	      	
			List<IBIOMESFile> hiddenFiles = ibiomesFileAO.getFilesFromPath(hiddenFileNames, false);
			return new IBIOMESResponse(true, null, hiddenFiles);
		}
		catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
}

