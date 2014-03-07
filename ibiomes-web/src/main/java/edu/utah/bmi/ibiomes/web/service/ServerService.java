package edu.utah.bmi.ibiomes.web.service;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.ResourceAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.utah.bmi.ibiomes.pub.ServerMetadata;
import edu.utah.bmi.ibiomes.pub.ServerMetadataList;

@Controller
@RequestMapping(value = "/servers")
public class ServerService
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
    
	/**
	 * Retrieve all iBIOMES resources (servers)
	 * @return Server list
	 * @throws IOException 
	 * @throws JargonException 
	 * @throws Exception 
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ServerMetadataList getAllResources(
			@RequestHeader(value="authorization",required=false) String cnxHeader) throws JargonException, IOException
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		ResourceAO resAO = irodsAccessObjectFactory.getResourceAO(irodsAccount);
		ServerMetadataList servers = new ServerMetadataList(resAO.findAll());
		
		return servers;
	}
	
	/**
	 * Retrieve iBIOMES resource (server)
	 * @return Server info
	 * @throws Exception 
	 */
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	@ResponseBody
	public ServerMetadata getResource(
			@RequestHeader(value="authorization",required=false) String cnxHeader, 
			@PathVariable("name") String name) throws Exception 
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		ResourceAO resAO = irodsAccessObjectFactory.getResourceAO(irodsAccount);
		ServerMetadata server = new ServerMetadata(resAO.findByName(name));
		
		return server;
	}
}