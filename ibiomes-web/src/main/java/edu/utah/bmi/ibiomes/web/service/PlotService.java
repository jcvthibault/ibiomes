package edu.utah.bmi.ibiomes.web.service;


import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.DataNotFoundException;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.exception.OverwriteException;
import org.irods.jargon.core.pub.DataObjectAO;
import org.irods.jargon.core.pub.DataTransferOperations;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.domain.DataObject;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.utah.bmi.ibiomes.graphics.plot.ColumnDataFile;
import edu.utah.bmi.ibiomes.graphics.plot.PlotGenerator;
import edu.utah.bmi.ibiomes.web.IBIOMESResponse;

@Controller
@RequestMapping(value="/services/plot")
public class PlotService
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


	@RequestMapping(value="", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse generatePlot(
			@RequestParam("uri") String uri, 
			@RequestParam("type") String chartType,
			@RequestParam(value="title",required=false,defaultValue="") String title,
			@RequestParam(value="xTitle",required=false,defaultValue="") String xTitle,
			@RequestParam(value="yTitle",required=false,defaultValue="") String yTitle,
			@RequestParam(value="zTitle",required=false,defaultValue="") String zTitle,
			@RequestParam(value="width",required=false,defaultValue="600") int width,
			@RequestParam(value="height",required=false,defaultValue="500") int height,
			@RequestParam(value="series",required=false) String[] seriesLabels) 
	{
		try{
			//check authentication
			HttpSession session = request.getSession();
			IRODSAccount irodsAccount = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
			if (irodsAccount != null)
			{
				IRODSFileFactory fileFactory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
				IRODSFile dataFile = fileFactory.instanceIRODSFile(uri);
				DataObjectAO dao = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
				DataObject data = dao.findByAbsolutePath(uri);
				
				String tempDirForUser = (String)session.getAttribute("USER_DIR");
				String relativePath = tempDirForUser + "/" + data.getId();
				String path = session.getServletContext().getRealPath("/") + "/" + relativePath;
				File localFile = new File(path);
				
				//check if the file has already been copied
				if (!localFile.exists())
			    {
					DataTransferOperations dataTransfer = irodsAccessObjectFactory.getDataTransferOperations(irodsAccount);
					//if user has read access to the file
					dataTransfer.getOperation(dataFile, localFile, null, null);
			    }
				if (!localFile.exists())
					return new IBIOMESResponse(false, "File already exists. Overwrite error", null);
				else {
					//generate plot
					ColumnDataFile csv;
					PlotGenerator plotBuilder = new PlotGenerator();
					
					csv = new ColumnDataFile(localFile);
					JFreeChart chart = plotBuilder.createPlot(csv, chartType, title, xTitle, yTitle, zTitle, seriesLabels, false, false);
					String relImagePath = relativePath + "_" + chartType;
					plotBuilder.createImage(chart, width, height, session.getServletContext().getRealPath("/") + "/" + relImagePath, "png");
					//delete data file
					localFile.delete();
					//return path to image
					return new IBIOMESResponse(true, null, relImagePath + ".png");
				}
			}
			else {
				return new IBIOMESResponse(false, "Authentication required!", null);
			}
		} catch (DataNotFoundException e1) {
			e1.printStackTrace();
			return new IBIOMESResponse(false, "File Not found. Check that the file still exists and that you have read access.", null);
		} catch (OverwriteException e2) {
			e2.printStackTrace();
			return new IBIOMESResponse(false, "File already exists. Overwrite error", null);
		} catch (JargonException e3) {
			e3.printStackTrace();
			return new IBIOMESResponse(false, "Server exception: " + e3.getMessage(), null);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Error: " + e.getLocalizedMessage(), null);
		}
	}
	
	
}

