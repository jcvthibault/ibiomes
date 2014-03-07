package edu.utah.bmi.ibiomes.web.controller.operation;

import javax.servlet.*;
import javax.servlet.http.*;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.pub.DataObjectAO;
import org.irods.jargon.core.pub.DataTransferOperations;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.domain.AvuData;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.LocalFileFactory;
import edu.utah.bmi.ibiomes.pub.IBIOMESExperimentAO;
import edu.utah.bmi.ibiomes.pub.IBIOMESFile;
import edu.utah.bmi.ibiomes.pub.IBIOMESFileAO;
import edu.utah.bmi.ibiomes.metadata.FileMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

import java.io.*;
import java.util.List;

/**
 * Search simulations in iRODS file system
 * @author Julien Thibault
 *
 */
public class UpdateFileFormat extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;

	private static final String TO_REMOVE = "unknown";
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try{
			HttpSession session = request.getSession(true);
			
			//check authentication
			IRODSAccount irodsAccount = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
			if (irodsAccount != null)
			{
				ServletContext context = getServletContext();
				String uri = request.getParameter("uri");
				String target = (String)request.getParameter("dispatchto");
				
				if (target == null || target.length()==0)
					target = "/editfile.do?uri=" + uri;
				
				String format = (String)request.getParameter(FileMetadata.FILE_FORMAT);

				IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);				
				IRODSFileFactory factory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
				IRODSFile file = factory.instanceIRODSFile(uri);
				
				if (file.isFile())
				{
					//retrieve metadata for the current file
					IBIOMESFile ibiomesFile = ibiomesFileAO.getFileByPath(uri);
					MetadataAVUList existingMetadata = ibiomesFile.getMetadata();
					
					DataObjectAO dataAO = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
					
					//TODO parse file for metadata only if size < max (transfer time...)
					
					LocalFileFactory fileFactory = LocalFileFactory.instance();
					
					//get local copy of file before parsing
					String relativePath = session.getAttribute("USER_DIR") + "/" + ibiomesFile.getAbsolutePath().replaceAll("/", "_");
					String localFilePath = context.getRealPath("/") + "/" + relativePath;
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
						ModelAndView maverr = new ModelAndView(target);
						maverr.addObject("error", "File " + localFilePath +" does not exist on the server.");
						return maverr;
					}
					
					LocalFile localFileParse = fileFactory.getFileInstanceFromFormat(localFile.getAbsolutePath(), format);
					MetadataAVUList updatedMetadata = localFileParse.getMetadata();
					
					List<String> keys = updatedMetadata.getAttributes();
					for (String attribute : keys)
					{
						List<String> mdList = updatedMetadata.getValues(attribute);
						String value = mdList.get(0);
						
						boolean hasValue = false;
						boolean exists = false;
						
						if (value!=null)
							hasValue = (value.length()>0) && (value.compareTo(TO_REMOVE)!=0);
						
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
				ModelAndView mav = new ModelAndView(target);
				mav.addObject("message", "File format successfully updated.");
				return mav;
			}
			else 
			{
				ModelAndView mav = new ModelAndView("index.do");
				return mav;
			}
		}
		catch(Exception e){
			ModelAndView mav = new ModelAndView("/error.do");
			mav.addObject("exception", e);
			return mav;
		}
	}
	
	 /**
	  * @return the irodsAccessObjectFactory
	  */
	 public IRODSAccessObjectFactory getIrodsAccessObjectFactory() {
		return irodsAccessObjectFactory;
	 }

	 /**
	 * @param irodsAccessObjectFactory
	 *            the irodsAccessObjectFactory to set
	 */
	 public void setIrodsAccessObjectFactory(
			IRODSAccessObjectFactory irodsAccessObjectFactory) {
		 this.irodsAccessObjectFactory = irodsAccessObjectFactory;
	 }
}
			