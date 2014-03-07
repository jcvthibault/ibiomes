package edu.utah.bmi.ibiomes.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.DataNotFoundException;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.exception.OverwriteException;
import org.irods.jargon.core.pub.DataTransferOperations;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import edu.utah.bmi.Utils;

/**
 * Donwload simulation file/collections from the iRODS file system
 * @author Julien Thibault
 *
 */
public class JMol extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(true);
		
		//check authentication
		IRODSAccount irodsAccount = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
		if (irodsAccount != null)
		{
			File localFile = null;
			boolean hasError = false;
			
			String[] uris = request.getParameterValues("uri");
			String script = request.getParameter("script");
			
			if (uris != null)
			{
				//sort files by name to assure frames are in order
				//TODO (sort using numbers)
				Utils.sortFileNames(uris);

				IRODSFileFactory fileFactory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
				DataTransferOperations dataTransfer = irodsAccessObjectFactory.getDataTransferOperations(irodsAccount);

				String listFilesScript = "";
				List<String> listFiles = new ArrayList<String>();
				List<String> listFilesLocal = new ArrayList<String>();
				
				for (int u=0;u<uris.length;u++)
				{
					String uri = uris[u];		
					IRODSFile pdbFile = fileFactory.instanceIRODSFile(uri);
					
					String relativePath = session.getAttribute("USER_DIR") + "/" + pdbFile.getAbsolutePath().replaceAll("/", "_");
					String path = getServletContext().getRealPath("/") + "/" + relativePath;
					localFile = new File(path);
					
					//check if the file has already been copied
					if (!localFile.exists())
				    {
						String errMessage = "";
						//if user has read access to the file
						try{
							dataTransfer.getOperation(pdbFile, localFile, null, null);

						} catch (DataNotFoundException e1) {
							hasError = true;
							errMessage = "File Not found. Check that the file still exists and that you have read access.";
						} catch (OverwriteException e2) {
							hasError = true;
							errMessage = "File already exists. Overwrite error";
						} catch (JargonException e3) {
							hasError = true;
							errMessage = "Server exception: " + e3.getMessage();
						}
						
						if (hasError) {
							ModelAndView mav = new ModelAndView("/exception.jsp");
							mav.addObject("errorTitle", "File access failed");
							mav.addObject("errorMsg", errMessage);
							return mav;
						}
					}
					if (!localFile.exists())
						hasError = true;
					else {
						listFiles.add(pdbFile.getAbsolutePath());
						listFilesLocal.add(relativePath);
						listFilesScript += " '" + relativePath + "'";
					}
				}
	
				if (!hasError)
			    {
					if (script != null && script.equals("comparison")){
						//display all the structures together for comparison
						script = "load MODELS " + listFilesScript + " ; frame all; cpk off; wireframe 40;";
					}
					else //animation (display files one after the other)
						script = "load FILES " + listFilesScript + " ; anim fps 5; cpk off; wireframe 40;";
			    }
			
				
				ModelAndView mav = new ModelAndView("/jmol.jsp");
				mav.addObject("fileList", listFiles);
				mav.addObject("fileListLocal", listFilesLocal);
				mav.addObject("script", script);
				return mav;
			}
			else 
			{
				ModelAndView mav = new ModelAndView("/exception.jsp");
				mav.addObject("errorTitle", "User error!");
				mav.addObject("errorMsg", "No file selected!");
				return mav;
			}			
		}
		else 
		{
			ModelAndView mav = new ModelAndView("index.do");
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