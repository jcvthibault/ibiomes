package edu.utah.bmi.ibiomes.web.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.DataObjectAO;
import org.irods.jargon.core.pub.DataTransferOperations;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.domain.DataObject;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.query.JargonQueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.utah.bmi.ibiomes.graphics.ImageUtils;
import edu.utah.bmi.ibiomes.metadata.FileMetadata;
import edu.utah.bmi.ibiomes.metadata.IBIOMESFileGroup;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.pub.IBIOMESExperimentAO;
import edu.utah.bmi.ibiomes.pub.IBIOMESFile;
import edu.utah.bmi.ibiomes.pub.IBIOMESFileAO;
import edu.utah.bmi.ibiomes.pub.set.IBIOMESExperimentSetAO;
import edu.utah.bmi.ibiomes.web.IBIOMESResponse;
import edu.utah.bmi.ibiomes.web.ImageThumbnailInfo;
import edu.utah.bmi.ibiomes.web.Utils;

@Controller
@RequestMapping(value="/services/thumbnail")
public class ThumbnailService
{

	@Autowired(required = true)  
    private HttpServletRequest request;   
    public HttpServletRequest getRequest() {  
        return request;  
    }
    @Autowired(required = true)
    private ServletContext context;
    
    @Autowired(required = true)  
    private IRODSAccessObjectFactory irodsAccessObjectFactory;
	public IRODSAccessObjectFactory getIrodsAccessObjectFactory() {
		return irodsAccessObjectFactory;
	}

    /**
     * 
     * @return
     */
    private DataSource getDataSource(){
    	WebApplicationContext wContext = WebApplicationContextUtils.getWebApplicationContext(this.context);
    	return (DataSource)wContext.getBean("dataSource");
    }
    
	/**
	 * Create thumbnails for images stored in iRODS
	 * @param urisStr List of file URIs
	 * @param maxWidth Max width of the thumbnails
	 * @return List of thumbnail details
	 */
	@RequestMapping(value="/files", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getThumbnailsForFiles(
			@RequestParam("uris") String urisStr,
			@RequestParam(value="max",required=false,defaultValue="100") int max)
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
			DataObjectAO dAO = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
			
			String[] uris = urisStr.split("\\,");
			
			List<ImageThumbnailInfo> thumbnails = new ArrayList<ImageThumbnailInfo>();
			for (String uri : uris)
			{
				//store image info
		        ImageThumbnailInfo thumbnail = createThumbnail(uri, max, dAO, ibiomesFileAO, irodsAccount);
		        if (thumbnail!=null)
		        	thumbnails.add(thumbnail);
			}
			return new IBIOMESResponse(true, null, thumbnails);
		}
		catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Retrieve list of thumbnails for the images set as analysis data for a particular experiment.
	 * @param uri Experiment URI
	 * @param Max width of the thumbnails
	 * @return List of thumbnail details
	 */
	@RequestMapping(value="/experiment/analysis", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getThumbnailsForExperimentAnalysisData(
			@RequestParam("uri") String uri,
			@RequestParam(value="max",required=false,defaultValue="100") int max)
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
			IBIOMESExperimentAO eao = new IBIOMESExperimentAO(irodsAccessObjectFactory, irodsAccount);
			DataObjectAO dAO = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
			//find analysis data
			List<String> analysisFileNames = eao.getAnalysisFilesInExperiment(uri);

			List<ImageThumbnailInfo> analysisImages = new ArrayList<ImageThumbnailInfo>();
			if (analysisFileNames==null || analysisFileNames.size()==0)
				return new IBIOMESResponse(true, null, analysisImages);
			
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
					
					if ( IBIOMESFileGroup.isImageFile(fileFormat))
					{
						//copy image file locally (thumbnail)
						ImageThumbnailInfo thumbnail = createThumbnail(file.getAbsolutePath(), max, dAO, ibiomesFileAO, irodsAccount);
						if (thumbnail != null){
							analysisImages.add(thumbnail);
						}
					}
				}
			}
			return new IBIOMESResponse(true, null, analysisImages);
		}
		catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}

	/**
	 * Retrieve analysis images
	 * @param id Experiment set ID
	 * @return File list
	 */
	@RequestMapping(value = "/experimentset/analysis", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse getAnalysisFileReferences(@RequestParam("id") long id,
			@RequestParam(value="max",required=false,defaultValue="100") int max)
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try {
			IBIOMESExperimentSetAO ao = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());
			IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
			DataObjectAO dAO = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
			
			List<ImageThumbnailInfo> thumbnails = new ArrayList<ImageThumbnailInfo>();
			List<Integer> ids = ao.getAnalysisFileReferences(id);
			for (Integer fileId : ids){
				IBIOMESFile file = ibiomesFileAO.getFileByID(fileId);
				if (IBIOMESFileGroup.isImageFile(file.getFormat())){
					//copy image file locally (thumbnail)
					ImageThumbnailInfo thumbnail = createThumbnail(file.getAbsolutePath(), max, dAO, ibiomesFileAO, irodsAccount);
					if (thumbnail != null){
						thumbnails.add(thumbnail);
					}
				}
			}
			return new IBIOMESResponse(true, null, thumbnails);
		}
		catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Create image thumbnail
	 * @param uri IRODS data URI
	 * @param maxWidth Max width of the thumbnail
	 * @param dAO
	 * @param ibiomesFactory
	 * @param irodsAccount IRODS user account
	 * @return
	 * @throws JargonException
	 * @throws JargonQueryException
	 * @throws IOException
	 */
	 private ImageThumbnailInfo createThumbnail(
			 String uri, 
			 float max, 
			 DataObjectAO dAO, 
			 IBIOMESFileAO ibiomesFactory,
			 IRODSAccount irodsAccount) 
					 throws JargonException, JargonQueryException, IOException
	 {			
		IRODSFile iFile = dAO.instanceIRODSFileForPath(uri);
		
		//if user has read access to the file
		if (iFile.canRead())
		{
			DataObject data = dAO.findByAbsolutePath(uri);
			
			IBIOMESFile ibiomesFile = ibiomesFactory.getFileByPath(uri);
			String fileFormat = ibiomesFile.getFormat();
			String description = ibiomesFile.getDescription();
			
			String relativePath = ((String)request.getSession().getAttribute("USER_DIR")) + "/thumbnail_" + data.getId() + "." + fileFormat.toLowerCase();
			String localFilePath = context.getRealPath("/") + "/" + relativePath;
			 
			File localFile = new File(localFilePath);
			if (!localFile.exists())
			{	
				DataTransferOperations dataTransfer = irodsAccessObjectFactory.getDataTransferOperations(irodsAccount);
				dataTransfer.getOperation(iFile, localFile, null, null);
				
				//load image
				ImageUtils.rescaleImage(localFilePath, localFilePath, max, fileFormat);
			}
			//store image info
	        ImageThumbnailInfo thumbnail = new ImageThumbnailInfo(
	        		iFile.getName(), 
	        		description, 
	        		fileFormat, 
	        		relativePath, 
	        		uri
	        	);
	        return thumbnail;
		}
		else return null;
	 }

}

