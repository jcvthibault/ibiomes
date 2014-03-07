package edu.utah.bmi.ibiomes.web.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.DataNotFoundException;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.DataObjectAO;
import org.irods.jargon.core.pub.DataTransferOperations;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.LocalFileFactory;
import edu.utah.bmi.ibiomes.pub.IBIOMESFile;
import edu.utah.bmi.ibiomes.pub.IBIOMESFileAO;
import edu.utah.bmi.ibiomes.catalog.MetadataLookup;
import edu.utah.bmi.ibiomes.metadata.FileMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataRichAVUList;
import edu.utah.bmi.ibiomes.web.IBIOMESResponse;

@Controller
@RequestMapping(value="/services/file")
public class FileService
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
	
    @Autowired
    private ServletContext context;
    
    private MetadataLookup metadataDictionary;
    private MetadataLookup getMetadataDictionary(){
    	if (metadataDictionary == null)
    		metadataDictionary = new MetadataLookup(this.context.getRealPath("config/metadata-attr"));
    	return metadataDictionary;
    }
    
    private long MAX_FILE_SIZE_FOR_TRANSFER = 1000000;
    
	/**
	 * Retrieve iBIOMES file description
	 * @param uri File URI
	 * @param id File ID
	 * @return Metadata list
	 * @throws JargonQueryException 
	 * @throws JargonException
	 * @throws IOException 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public  IBIOMESFile getFile(
			@RequestParam(value="uri",required=false, defaultValue="") String uri,
			@RequestParam(value="id",required=false, defaultValue="-1") int id)
					throws JargonException, JargonQueryException, IOException
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
		IBIOMESFile file = null;
		if (uri != null && uri.length()>0)
			file = ibiomesFileAO.getFileByPath(uri);
		else 
			file = ibiomesFileAO.getFileByID(id);
		return file;
	}
	
	/**
	 * Retrieve AVUs for a given file
	 * @return AVU list
	 */
	@RequestMapping(value = "/avus", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getFileAVUs(
					@RequestParam("uri") String uri,
					@RequestParam(value="attribute", required=false) String attribute) throws Exception 
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			
			DataObjectAO doFactory = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
			List<MetaDataAndDomainData> metadata;
			//if filtering on the attribute
			if (attribute != null){
				List<AVUQueryElement> avus = new ArrayList<AVUQueryElement>();
				avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, attribute));
				avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, "%"));
				metadata = doFactory.findMetadataValuesForDataObjectUsingAVUQuery(avus, uri);
			}
			else {
				metadata = doFactory.findMetadataValuesForDataObject(uri);
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
		} 
		catch (Exception e){
			return new IBIOMESResponse(false, "AVUs for '"+uri+"' could not be retrieved. Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Add AVU to given file
	 * @return new AVU
	 */
	@RequestMapping(value = "/avus/add", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse addFileAVU(
					@RequestParam("uri") String uri,
					@RequestParam("attribute") String attribute,
					@RequestParam("value") String value,
					@RequestParam(value="unit", required=false, defaultValue="") String unit) throws Exception 
	{
		
		AvuData avu = AvuData.instance(attribute, value, unit);

		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try{
			DataObjectAO ao = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
			ao.addAVUMetadata(uri, avu);
		} catch (Exception e){
			return new IBIOMESResponse(false, "AVU could not be added to file '"+uri+"'. Exception: " + e.getMessage(), new MetadataAVU(attribute, value));
		}
		return new IBIOMESResponse(true, "AVU successfully added to file '"+uri+"'", new MetadataAVU(attribute, value));
	}
	
	/**
	 * Update AVU for given file
	 * @return new AVU
	 */
	@RequestMapping(value = "/avus/update", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse updateFileAVU(
			@RequestParam("uri") String uri,
			@RequestParam(value="attribute", required=false) String attribute,
			@RequestParam(value="value", required=false) String value,
			@RequestParam(value="unit", required=false, defaultValue="") String unit,
			@RequestParam(value="string", required=false) String paramStr) throws Exception 
	{
		List<MetadataAVU> avus = new ArrayList<MetadataAVU>();
		try {
			//if arguments are Attribute-Value-Unit
			if (attribute != null && attribute.length()>0){
				avus.add(new MetadataAVU(attribute, value, unit));
			}
			else if (paramStr != null && paramStr.length()>0)
			{
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
			else return new IBIOMESResponse(false, "No AVU was specified", null);
		}
		catch (Exception e) {
			return new IBIOMESResponse(false, "An error occurred when reading the new AVU values. Exception: "+e.getMessage(), null);
		}

		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try{
			DataObjectAO ao = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
			IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
			IBIOMESFile iFile = ibiomesFileAO.getFileByPath(uri);
			
			for (MetadataAVU avu : avus)
			{	
				String newValue = avu.getValue();
				String newAttribute = avu.getAttribute();
				
				if ( (newValue == null) || (newValue.length()==0) || (newValue.toLowerCase().equals("unknown")) )
				{
					//if current value needs to be deleted
					if (iFile.getMetadata().containsAttribute(newAttribute))
						ao.deleteAVUMetadata(uri, AvuData.instance(newAttribute, iFile.getMetadata().getValue(newAttribute), ""));
				}
				else if (iFile.getMetadata().containsAttribute(avu.getAttribute())){
					//modify existing AVU
					ao.modifyAvuValueBasedOnGivenAttributeAndUnit(uri, AvuData.instance(avu.getAttribute(), avu.getValue(), avu.getUnit()));
				}
				else {
					//add new AVU
					ao.addAVUMetadata(uri, AvuData.instance(avu.getAttribute(), avu.getValue(), avu.getUnit()));
				}
			}
		}
		catch (DataNotFoundException e) {
			return new IBIOMESResponse(false, "Data not found at '"+uri+"'. Exception: "+e.getMessage(), null);
		}
		catch (JargonException e) {
			return new IBIOMESResponse(false, "Error: "+e.getMessage(), null);
		}
		return new IBIOMESResponse(true, "AVUs successfully added to file '"+uri+"'", null);
	}
	
	/**
	 * Update AVU for given file
	 * @return new AVU
	 */
	@RequestMapping(value = "/hide", method = RequestMethod.GET)
	@ResponseBody
	public boolean hideFile(
			@RequestParam("uris") String uris) throws Exception 
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		DataObjectAO ao = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
		IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
		
		AvuData avu = AvuData.instance(FileMetadata.FILE_IS_HIDDEN, "true", "");
		
		//for each file
		String[] fileUris = uris.split("\\,");
		if (fileUris!=null)
		{	
			for (int f=0; f<fileUris.length; f++)
			{
				String uri = fileUris[f];
				IBIOMESFile iFile = ibiomesFileAO.getFileByPath(uri);

				if (iFile.getMetadata().containsAttribute(FileMetadata.FILE_IS_HIDDEN)){
					//modify existing AVU
					ao.modifyAvuValueBasedOnGivenAttributeAndUnit(uri, avu);
				}
				else {
					//add new AVU to hide file
					ao.addAVUMetadata(uri, avu);
				}
			}
		}
		return true;
	}
	
	/**
	 * Delete AVU for given file
	 * @return AVU
	 */
	@RequestMapping(value = "/avus/delete", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse deleteFileAVU(
					@RequestParam("uri") String uri,
					@RequestParam("attribute") String attribute,
					@RequestParam("value") String value,
					@RequestParam(value="unit", required=false, defaultValue="") String unit) throws Exception 
	{
		AvuData avu = AvuData.instance(attribute, value, unit);

		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try{
			DataObjectAO ao = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
			ao.deleteAVUMetadata(uri, avu);
		} catch (JargonException exc) {
			return new IBIOMESResponse(false, "AVU could not be deleted for '"+uri+"'. Exception: " + exc.getMessage(), null);
		}
		return new IBIOMESResponse(true, "AVUs successfully deleted for '"+uri+"'", null);
	}
	
	/**
	 * Set visibility for a given file
	 * @return AVU
	 */
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	@ResponseBody
	public MetadataAVU setFileVisibility(
					@RequestParam("uris") String uris,
					@RequestParam(value="visible", required=true, defaultValue="true") String visibleStr) throws Exception 
	{
		
		List<AVUQueryElement> avuQuery = new ArrayList<AVUQueryElement>();
		avuQuery.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.LIKE, FileMetadata.FILE_IS_HIDDEN));
		
		AvuData avuHide = AvuData.instance(FileMetadata.FILE_IS_HIDDEN, "true", "");
		String[] fileUris = uris.split("\\,");
		if (fileUris!=null){
		
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			DataObjectAO ao = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
			
			for (int f=0; f< fileUris.length; f++)
			{
				String uri = fileUris[f]; 
				List<MetaDataAndDomainData> meta = ao.findMetadataValuesForDataObjectUsingAVUQuery(avuQuery, uri);
				//delete hide flag if any
				for (MetaDataAndDomainData m : meta){	
					ao.deleteAVUMetadata(uri, AvuData.instance(m.getAvuAttribute(), m.getAvuValue(), m.getAvuUnit()));
				}
				
				if (!visibleStr.toLowerCase().matches("(t(rue)?)|(y(es)?)"))
				{
					//update or add AVU to hide file
					ao.addAVUMetadata(uri, avuHide);
				}
			}

			return new MetadataAVU(avuHide.getAttribute(), avuHide.getValue());
		}
		return null;
	}
	
	/**
	 * Set analysis data flag for a given file
	 * @return AVU
	 */
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	@ResponseBody
	public MetadataAVU setFileDataFlag(
					@RequestParam("uris") String uris,
					@RequestParam(value="isdata", required=true, defaultValue="true") String isDataStr) throws Exception 
	{
		boolean isAddOperation = isDataStr.toLowerCase().matches("(t(rue)?)|(y(es)?)");
		
		List<AVUQueryElement> avuQuery = new ArrayList<AVUQueryElement>();
		avuQuery.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.LIKE, FileMetadata.FILE_CLASS));
		avuQuery.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, FileMetadata.FILE_CLASS_ANALYSIS));
		
		AvuData avuIsData = AvuData.instance(FileMetadata.FILE_CLASS, FileMetadata.FILE_CLASS_ANALYSIS, "");
		String[] fileUris = uris.split("\\,");
		
		if (fileUris!=null)
		{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			DataObjectAO ao = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
			
			for (int f=0; f< fileUris.length; f++)
			{
				String uri = fileUris[f]; 
				List<MetaDataAndDomainData> meta = ao.findMetadataValuesForDataObjectUsingAVUQuery(avuQuery, uri);
				//delete data flag if any
				for (MetaDataAndDomainData m : meta){
					ao.deleteAVUMetadata(uri, AvuData.instance(m.getAvuAttribute(), m.getAvuValue(), m.getAvuUnit()));
				}
				
				if (isAddOperation){
					//update or add AVU to hide file
					ao.addAVUMetadata(uri, avuIsData);
				}
				//else the flag was deleted
			}
	
			
			
			return new MetadataAVU(avuIsData.getAttribute(), avuIsData.getValue());
		}
		return null;
	}
	
	/**
	 * Update file format
	 * @return New metadata
	 */
	@RequestMapping(value = "/updateformat", method = RequestMethod.GET)
	@ResponseBody
	public MetadataAVUList updateFileFormat(
					@RequestParam("uri") String uri,
					@RequestParam("format") String format) throws Exception 
	{

		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");

		IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
		IRODSFileFactory factory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
		IRODSFile file = factory.instanceIRODSFile(uri);
		
		if (file.isFile())
		{
			//retrieve metadata for the current file
			IBIOMESFile ibiomesFile = ibiomesFileAO.getFileByPath(uri);
			MetadataAVUList existingMetadata = ibiomesFile.getMetadata();
			
			DataObjectAO dataAO = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
			MetadataAVUList updatedMetadata = null;
			
			//copy locally and parse file for metadata only if size < max (transfer time...)
			if (file.length() > MAX_FILE_SIZE_FOR_TRANSFER)
			{	
				updatedMetadata = new MetadataAVUList();
				updatedMetadata.add(new MetadataAVU(FileMetadata.FILE_FORMAT, format));
			}
			else {
			
				LocalFileFactory fileFactory = LocalFileFactory.instance();
				
				//get local copy of file before parsing
				String relativePath = request.getSession().getAttribute("USER_DIR") + "/" + ibiomesFile.getAbsolutePath().replaceAll("/", "_");
				String localFilePath = context.getInitParameter("APP_ROOT_PATH") + "/" + relativePath;
				File localFile = new File(localFilePath);
				//check if the file has already been copied
				if (!localFile.exists())
			    {
					//if user has read access to the file
					if (file.canRead()){
						DataTransferOperations dataTransfer = irodsAccessObjectFactory.getDataTransferOperations(irodsAccount);
						dataTransfer.getOperation(file, localFile, null, null);
					}
				}
				
				if (!localFile.exists()){
					return null;
				}
				
				LocalFile localFileParse = fileFactory.getFileInstanceFromFormat(localFile.getAbsolutePath(), format);
				updatedMetadata = localFileParse.getMetadata();
				
				List<String> keys = updatedMetadata.getAttributes();
				for (String attribute : keys)
				{
					List<String> mdList = updatedMetadata.getValues(attribute);
					String value = mdList.get(0);
					
					boolean hasValue = false;
					boolean exists = false;
					
					if (value!=null)
						hasValue = (value.length()>0);
					
					//get old value (if any)
					String oldValue = "";
					if (existingMetadata.containsAttribute(attribute)){
						exists = true;
						oldValue = existingMetadata.getValue(attribute);
					}
					
					//if the old metadata need to be removed
					if (exists){
						dataAO.deleteAVUMetadata(uri, AvuData.instance(attribute, oldValue, ""));
					}
					//if need to add new value
					if (hasValue){
						dataAO.addAVUMetadata(uri, AvuData.instance(attribute, value, ""));
					}
				}
			}
			

			return updatedMetadata;
		}
		return null;
	}
	
	/**
	 * Delete file(s)
	 * @return True if success
	 * @throws JargonException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse deleteFile(@RequestParam("uris") String urisString) throws JargonException, IOException 
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			String[] uris = urisString.split("\\,");
			boolean success = true;
		
			//remove files (deletion)
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
			return new IBIOMESResponse(false, "Cannot delete these files! (Do you have the right permissions?)", null);
		}
	}
	
	/**
	 * Unregister file(s) and keep physical files
	 * @return True if success
	 */
	@RequestMapping(value = "/unregister", method = RequestMethod.GET)
	@ResponseBody 
	public IBIOMESResponse unregisterFiles(@RequestParam("uris") String urisString) 
	{
		try{
			String[] uris = urisString.split("\\,");
			boolean success = true;
			//remove files (deletion)
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IRODSRegistrationOfFilesAO ao = irodsAccessObjectFactory.getIRODSRegistrationOfFilesAO(irodsAccount);
			for (int f=0;f<uris.length;f++)
			{
				boolean ok = ao.unregisterDataObject(uris[f]);
				if (!ok)
					success = false;
			}
			return new IBIOMESResponse(success, null, null);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Cannot unregister these files! (are they in the iRODS vault?)", null);
		}
	}
	
}

