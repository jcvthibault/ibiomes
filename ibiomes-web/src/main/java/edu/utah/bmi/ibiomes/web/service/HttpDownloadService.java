package edu.utah.bmi.ibiomes.web.service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.Stream2StreamAO;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.irods.jargon.core.pub.io.IRODSFileInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.utah.bmi.ibiomes.pub.IBIOMESCollection;
import edu.utah.bmi.ibiomes.pub.IBIOMESCollectionAO;

@Controller
public class HttpDownloadService
{
	private static final int BUFSIZE = 4096;
	
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
	
	/**
	 * Send iRODS file to client for download
	 * @param uri File uri
	 * @throws Exception 
	 */
	@RequestMapping(value = "/services/download", method = RequestMethod.GET)
	public void downloadFile(
			@RequestParam(value="uri",required=false) String uri,
			@RequestParam(value="id",required=false, defaultValue="-1") int id,
			HttpServletResponse response) throws Exception
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IRODSFileFactory irodsFileFactory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
			//if needs to find data object by ID
			if (uri == null || uri.length()==0){
				IBIOMESCollectionAO ibiomesCollAO = new IBIOMESCollectionAO(irodsAccessObjectFactory, irodsAccount);
	    		IBIOMESCollection coll = ibiomesCollAO.getCollectionByID(id);
	    		uri = coll.getAbsolutePath();
			}
	        IRODSFileInputStream irodsFileInputStream = irodsFileFactory.instanceIRODSFileInputStream(uri);
			IRODSFile irodsFile = irodsFileFactory.instanceIRODSFile(uri);
			
			long length =  irodsFile.length();
			
			response.setContentType("application/octet-stream");
			response.setContentLength((int) length);
			response.setHeader("Content-disposition", "attachment;filename=\""+irodsFile.getName()+"\"");
			
			//byte[] byteBuffer = new byte[BUFSIZE];
			ServletOutputStream outStream = response.getOutputStream();
	        
			 // Performing a binary stream copy
	        // reads the file's bytes and writes them to the response stream
	        /*while ((irodsFileInputStream != null) && ((length = irodsFileInputStream.read(byteBuffer)) != -1))
	        {
	            outStream.write(byteBuffer, 0, (int) length);
	        }*/
	        
	        Stream2StreamAO s2s = irodsAccessObjectFactory.getStream2StreamAO(irodsAccount);
			s2s.streamToStreamCopy(irodsFileInputStream, outStream);
			
	        response.flushBuffer();
		}
		catch (Exception e){
			e.printStackTrace();
			throw e;
		}
	}
}
