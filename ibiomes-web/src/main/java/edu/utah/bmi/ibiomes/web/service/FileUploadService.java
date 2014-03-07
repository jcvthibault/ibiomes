package edu.utah.bmi.ibiomes.web.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.DataObjectAO;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.Stream2StreamAO;
import org.irods.jargon.core.pub.domain.AvuData;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.utah.bmi.ibiomes.metadata.FileMetadata;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.LocalFileFactory;
import edu.utah.bmi.ibiomes.pub.IBIOMESFile;
import edu.utah.bmi.ibiomes.web.IBIOMESResponse;

@Controller
@RequestMapping(value="/services/upload")
public class FileUploadService
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
	
    private long MAX_FILE_SIZE_FOR_TRANSFER = 1000000;
    
    @RequestMapping(value="/file", method=RequestMethod.POST)
	public @ResponseBody IBIOMESResponse upload(
			@RequestParam("file") MultipartFile f,
			@RequestParam("uri") String uri,
			@RequestParam(value="overwrite", required=false, defaultValue="false") boolean overwrite) throws JargonException 
	{
    	try {
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
					
			String name = f.getOriginalFilename();
			if (f.getSize() > MAX_FILE_SIZE_FOR_TRANSFER) {
				return new IBIOMESResponse(false, "File size cannot exceed " + (MAX_FILE_SIZE_FOR_TRANSFER/1000) + " KB", null);
			} 
			else if (f.getSize() == 0) {
				return new IBIOMESResponse(false, "File is empty", null);
			}
			if (f == null || f.isEmpty()) {
				return new IBIOMESResponse(false, "No file to upload", null);
			}
			if (uri == null || uri.isEmpty()) {
				return new IBIOMESResponse(false, "No iRODS target collection given for upload", null);
			}
	
			InputStream fis = null;
			IBIOMESFile uploadedFile = null;
			
			IRODSFileFactory fileFactory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
			String fileURI = uri + "/" + f.getOriginalFilename();
			IRODSFile iFileNew = fileFactory.instanceIRODSFile(fileURI);
			
			//if the file does not exist in iRODS or overwrite is allowed
			if (!iFileNew.exists() || overwrite)
			{
				
				fis = new BufferedInputStream(f.getInputStream());
				IRODSFileFactory irodsFileFactory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
				IRODSFile targetFile = irodsFileFactory.instanceIRODSFile(uri, name);
				targetFile.setResource(irodsAccount.getDefaultStorageResource());
				Stream2StreamAO stream2Stream = irodsAccessObjectFactory.getStream2StreamAO(irodsAccount);
				stream2Stream.transferStreamToFileUsingIOStreams(fis, (File)targetFile, f.getSize(), 0);
				
				//add format if necessary
				try{
					LocalFileFactory localFileFactory = LocalFileFactory.instance();
					String format = localFileFactory.getFileFormatFromExtension(iFileNew.getName());
					if (format!=null && !format.equals(LocalFile.FORMAT_UNKNOWN)){
						DataObjectAO dao = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
						dao.addAVUMetadata(fileURI, AvuData.instance(FileMetadata.FILE_FORMAT, format, ""));
					}
				} catch (Exception e){
					e.printStackTrace();
				}
				
				//set permissions to inherit from parent directory
				/*try{
					DataObjectAO dao = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
					dao.;
				} catch (Exception e){
					e.printStackTrace();
				}*/
				
				uploadedFile = new IBIOMESFile(iFileNew, null);
				return new IBIOMESResponse(true, "File successfully uploaded", uploadedFile);
			}
			else //file already exists 
			{
				return new IBIOMESResponse(false, "File "+uri+"/"+f.getOriginalFilename()+" already exists!", null);
			}
    	} catch (Exception e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, e.getMessage(), null);
		} finally {
			// stream2Stream will close input and output streams
		}
	}
}

