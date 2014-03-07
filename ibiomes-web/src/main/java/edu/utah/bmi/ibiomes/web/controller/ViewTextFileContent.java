package edu.utah.bmi.ibiomes.web.controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.DataTransferOperations;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.irods.jargon.core.pub.io.IRODSFileInputStream;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import java.io.*;

/**
 * Download file
 * @author Julien Thibault
 *
 */
@Deprecated
public class ViewTextFileContent extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;
	private final static long MAX_LENGTH = 1000000; 
	private static final int BUFSIZE = 4096;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return downloadFile(request, response);
	}
	
	private ModelAndView downloadFile(HttpServletRequest request, HttpServletResponse response) throws JargonException
	{
		HttpSession session = request.getSession(true);
		String target;
		
		//check authentication
		IRODSAccount irodsAccount = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
		if (irodsAccount != null)
		{			
			String uri = request.getParameter("uri");
			
			IRODSFileFactory fileFactory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
			IRODSFile iFile = fileFactory.instanceIRODSFile(uri);
			
			String textContent = "";
			
			if (iFile.exists() && iFile.canRead())
			{
				String relativePath = session.getAttribute("USER_DIR") + "/" + iFile.getAbsolutePath().replaceAll("/", "_");
				String localFilePath = getServletContext().getRealPath("/") + "/" + relativePath;
				File localFile = new File(localFilePath);
				
				//check if the file has already been copied
				if (!localFile.exists())
			    {
					if (iFile.length() <= MAX_LENGTH)
					{
						DataTransferOperations dataTransfer = irodsAccessObjectFactory.getDataTransferOperations(irodsAccount);
						dataTransfer.getOperation(iFile, localFile, null, null);
					}
					else //go to shopping cart
					{
						ModelAndView mav = new ModelAndView("/shoppingCart.do?action=add&dispatchto=cart.do&uri"+uri);
						return mav;
					}
			    }
				
				//get file content
				if (localFile.exists()){
					target = "/" + relativePath;
					
					ModelAndView mav = new ModelAndView(target);
					request.setAttribute("content", textContent);
					return mav;
				}
				else {
					ModelAndView mav = new ModelAndView("/exception.jsp");
					mav.addObject("errorTitle", "Cannot read file!");
					mav.addObject("errorMsg", "Sorry, we cannot display the content of this file. Check that this file really exists and that you have the right permissions.");
				  	return mav;			
				}
			}
			else {
				ModelAndView mav = new ModelAndView("/exception.jsp");
				mav.addObject("errorTitle", "Cannot read file!");
				mav.addObject("errorMsg", "Sorry, we cannot display the content of this file. Check that this file really exists and that you have the right permissions.");
			  	return mav;			
			}
		}
		else 
		{
			ModelAndView mav = new ModelAndView("index.do");
			return mav;
		}
	}
	
	public void downloadFileHttp(HttpServletRequest request, HttpServletResponse response) throws JargonException, IOException
	{
		String uri = request.getParameter("uri");
		
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		IRODSFileFactory irodsFileFactory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
        IRODSFileInputStream irodsFileInputStream = irodsFileFactory.instanceIRODSFileInputStream(uri);
		IRODSFile irodsFile = irodsFileFactory.instanceIRODSFile(uri);
		long length =  irodsFile.length();
		
		response.setContentType("application/octet-stream");
		response.setContentLength((int) length);
		response.setHeader("Content-disposition", "attachment;filename=\""+irodsFile.getName()+"\"");
		
		byte[] byteBuffer = new byte[BUFSIZE];
		ServletOutputStream outStream = response.getOutputStream();
        
		 // Performing a binary stream copy
        // reads the file's bytes and writes them to the response stream
        while ((irodsFileInputStream != null) && ((length = irodsFileInputStream.read(byteBuffer)) != -1))
        {
            outStream.write(byteBuffer, 0, (int) length);
        }
        //close input and output streams
        //irodsFileInputStream.close();
        //outStream.close();
        response.flushBuffer();
        
        /*
          Apparently, the Spring Framework assumes you are handling the response if it passes it into a method with a return type of void. This is because there is no good way for it to know if you did or didn’t.
			No big deal, we can use a string to prevent this behavio
         */
        //return "";
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
