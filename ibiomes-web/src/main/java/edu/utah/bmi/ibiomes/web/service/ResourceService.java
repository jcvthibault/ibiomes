package edu.utah.bmi.ibiomes.web.service;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.DataNotFoundException;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.ResourceAO;
import org.irods.jargon.core.pub.domain.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/services/resources")
public class ResourceService
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
	    
	/**
	 * Retrieve list of resources (individual and groups) in the current iRODS zone
	 * @return list of resources in the current iRODS zone
	 * @throws JargonException
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public  List<String> getListOfResources() throws JargonException
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		ResourceAO rescAO = irodsAccessObjectFactory.getResourceAO(irodsAccount);
		return rescAO.listResourceAndResourceGroupNames();
	}
	
	/**
	 * Select default resource
	 */
	@RequestMapping(value = "/select/{resource}", method = RequestMethod.GET)
	@ResponseBody
	public String selectDefaultResource(
			@PathVariable("resource") String name
		) 
		throws JargonException
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		irodsAccount.setDefaultStorageResource(name);
		//update iRODS account in session
		request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", irodsAccount);
		
		return name;			
	}
}
