package edu.utah.bmi.ibiomes.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.query.AVUQueryElement;
import org.irods.jargon.core.query.AVUQueryElement.AVUQueryPart;
import org.irods.jargon.core.query.AVUQueryOperatorEnum;
import org.irods.jargon.core.query.JargonQueryException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.utah.bmi.ibiomes.web.domain.QMMethod;
import edu.utah.bmi.ibiomes.web.domain.Simulation;
import edu.utah.bmi.ibiomes.search.IBIOMESExperimentSearch;
import edu.utah.bmi.ibiomes.search.IBIOMESExperimentSetSearch;
import edu.utah.bmi.ibiomes.search.IBIOMESFileSearch;
import edu.utah.bmi.ibiomes.search.IBIOMESSearch;
import edu.utah.bmi.ibiomes.search.IBIOMESSearchStatus;
import edu.utah.bmi.ibiomes.pub.IBIOMESCollection;
import edu.utah.bmi.ibiomes.pub.IBIOMESCollectionAO;
import edu.utah.bmi.ibiomes.pub.IBIOMESExperimentAO;
import edu.utah.bmi.ibiomes.pub.IBIOMESFile;
import edu.utah.bmi.ibiomes.pub.IBIOMESFileAO;
import edu.utah.bmi.ibiomes.pub.IBIOMESFileList;
import edu.utah.bmi.ibiomes.pub.set.IBIOMESExperimentSet;
import edu.utah.bmi.ibiomes.pub.set.IBIOMESExperimentSetAO;
import edu.utah.bmi.ibiomes.web.IBIOMESResponse;
import edu.utah.bmi.ibiomes.metadata.FileMetadata;
import edu.utah.bmi.ibiomes.metadata.GeneralMetadata;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.metadata.PlatformMetadata;
import edu.utah.bmi.ibiomes.metadata.TopologyMetadata;

@Controller
@RequestMapping(value="/services/search")
public class SearchService
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
    
	private DataSource getDataSource(){
    	WebApplicationContext wContext = WebApplicationContextUtils.getWebApplicationContext(this.context);
    	return (DataSource)wContext.getBean("dataSource");
    }
	
    private static final String SESSION_SEARCH_OBJECT = "SEARCH_OBJECT";
    private static final String SESSION_SEARCH_TYPE = "SEARCH_TYPE";
    private static final String SEARCH_TYPE_FILE = "file";
    private static final String SEARCH_TYPE_SET = "set";
    private static final String SEARCH_TYPE_EXPERIMENT = "all";
    private static final String SEARCH_TYPE_EXPERIMENT_MD = "md";
    private static final String SEARCH_TYPE_EXPERIMENT_QM = "qm";
    private static final String SEARCH_TYPE_EXPERIMENT_CUSTOM = "custom";
    private static final String SEARCH_TYPE_EXPERIMENT_KEYWORD = "keyword";

	private static final long day = 3600*24;
	private static final long week = day*7;
	private static final long month = day*31;
	private static final long year = month*12;
	
	private String name = null;
	private String owner = null;
	
	private long registrationDateMin = 0;
	private long registrationDateMax = 0;
	
	private void setRegistrationTimeRange() 
	{
		registrationDateMin = 0; registrationDateMax = 0;
		
		String dateRange = request.getParameter("registration");
		if (dateRange!=null && !dateRange.equals("none"))
		{
			long timestamp = (new Date()).getTime() / 1000;
			if (dateRange.equals("1week")){
				registrationDateMin = timestamp - week;
				registrationDateMax = 0;
			}
			else if (dateRange.equals("1month")){
				registrationDateMin = timestamp - month;
				registrationDateMax = 0;
			}
			else if (dateRange.equals("6month")){
				registrationDateMin = timestamp - 6*month;
				registrationDateMax = 0;
			}
			else if (dateRange.equals("1year")){
				registrationDateMin = timestamp - year;
				registrationDateMax = 0;
			}
			else if (dateRange.equals("over1year")){
				registrationDateMin = 0;
				registrationDateMax = timestamp - year;
			}
		}
	}
	/**
	 * Retrieve results of the current search (experiments)
	 * @throws Exception 
	 */
	@RequestMapping(value="/clear", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse clearSearch() 
	{
		IBIOMESSearch searchObject = (IBIOMESSearch)request.getSession().getAttribute(SESSION_SEARCH_OBJECT);
		if (searchObject!=null){
			try {
				searchObject.closeSearch();
				request.getSession().removeAttribute(SESSION_SEARCH_OBJECT);
				request.getSession().removeAttribute(SESSION_SEARCH_TYPE);
				return new IBIOMESResponse(true, "Search reset", null);
			} catch (Exception e) {
				return new IBIOMESResponse(false, "Error: " + e.getLocalizedMessage(), null);
			} 
		}
		else return new IBIOMESResponse(true, null, null);
	}
	
	/**
	 * Retrieve results of the current search (experiments)
	 * @throws Exception 
	 */
	@RequestMapping(value="/experiments/current", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getCurrentExperimentsSearchResults() 
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		IBIOMESSearch searchObject = (IBIOMESSearch)request.getSession().getAttribute(SESSION_SEARCH_OBJECT);
		try
		{
			if (searchObject != null){
				IBIOMESCollectionAO ibiomesCollAO = new IBIOMESCollectionAO(irodsAccessObjectFactory, irodsAccount);
				List<String> uris = searchObject.getResults();
				if (uris != null && uris.size()>0){
					List<Simulation> experiments = new ArrayList<Simulation>();
					List<IBIOMESCollection> collList = ibiomesCollAO.getCollectionsFromPath(uris);
					for (IBIOMESCollection coll : collList){
						Simulation simulation = new Simulation(coll);
						experiments.add(simulation);
					}
					return new IBIOMESResponse(true, null, experiments);
				}
				else return new IBIOMESResponse(true, "No experiment found", null);
			}
			else return new IBIOMESResponse(true, "No current search", null);
		}
		catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getLocalizedMessage(), null);
		}
	}
	
	/**
	 * Retrieve next results based on current search (experiments)
	 */
	@RequestMapping(value="/experiments/next", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getNextExperimentsSearchResults()
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		IBIOMESSearch searchObject = (IBIOMESSearch)request.getSession().getAttribute(SESSION_SEARCH_OBJECT);
		try{
			if (searchObject != null){
				IBIOMESCollectionAO ibiomesCollAO = new IBIOMESCollectionAO(irodsAccessObjectFactory, irodsAccount);
				List<String> uris = searchObject.executeForNextPage();
				if (uris != null && uris.size()>0){
					List<Simulation> experiments = new ArrayList<Simulation>();
					List<IBIOMESCollection> collList = ibiomesCollAO.getCollectionsFromPath(uris);
					for (IBIOMESCollection coll : collList){
						Simulation simulation = new Simulation(coll);
						experiments.add(simulation);
					}
					return new IBIOMESResponse(true, null, experiments);
				}
				return new IBIOMESResponse(true, null, null);
			}
			else return new IBIOMESResponse(true, null, null);
		}
		catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getLocalizedMessage(), null);
		}
	}
	
	/**
	 * Retrieve next results based on current search (experiments)
	 * @throws Exception 
	 */
	@RequestMapping(value="/experiments/previous", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getPreviousExperimentsSearchResults() 
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		IBIOMESSearch searchObject = (IBIOMESSearch)request.getSession().getAttribute(SESSION_SEARCH_OBJECT);
		try{
			if (searchObject != null){

				IBIOMESCollectionAO ibiomesCollAO = new IBIOMESCollectionAO(irodsAccessObjectFactory, irodsAccount);
				List<String> uris = searchObject.executeForPreviousPage();
				if (uris != null && uris.size()>0){
					List<Simulation> experiments = new ArrayList<Simulation>();
					List<IBIOMESCollection> collList = ibiomesCollAO.getCollectionsFromPath(uris);
					for (IBIOMESCollection coll : collList){
						Simulation simulation = new Simulation(coll);
						experiments.add(simulation);
					}
					return new IBIOMESResponse(true, null, experiments);
				}
				return new IBIOMESResponse(true, null, null);
			}
			else return new IBIOMESResponse(true, null, null);
		}
		catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getLocalizedMessage(), null);
		}
	}
	
	/**
	 * Retrieve results of the current search (files)
	 */
	@RequestMapping(value="/files/current", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getCurrentFileSearchResults() 
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IBIOMESSearch searchObject = (IBIOMESSearch)request.getSession().getAttribute(SESSION_SEARCH_OBJECT);
			if (searchObject != null){
				IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
				List<String> uris = searchObject.getResults();
				if (uris != null && uris.size()>0){
					IBIOMESFileList fileList = ibiomesFileAO.getFilesFromPath(uris);
					return new IBIOMESResponse(true, null, fileList);
				}
				else return new IBIOMESResponse(true, null, null);
			}
			else return new IBIOMESResponse(true, null, null);
		}
		catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getLocalizedMessage(), null);
		}
	}
	
	/**
	 * Retrieve next results of the current search (files)
	 */
	@RequestMapping(value="/files/next", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getNextFileSearchResults() 
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IBIOMESSearch searchObject = (IBIOMESSearch)request.getSession().getAttribute(SESSION_SEARCH_OBJECT);
			if (searchObject != null){
				IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
				List<String> uris = searchObject.executeForNextPage();
				if (uris != null && uris.size()>0){
					IBIOMESFileList fileList = ibiomesFileAO.getFilesFromPath(uris);
					return new IBIOMESResponse(true, null, fileList);
				}
				else return new IBIOMESResponse(true, null, null);
			}
			else return new IBIOMESResponse(true, null, null);
		}
		catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getLocalizedMessage(), null);
		}
	}
	
	/**
	 * Retrieve previous results of the current search (files)
	 */
	@RequestMapping(value="/files/previous", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getPreviousFileSearchResults() 
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IBIOMESSearch searchObject = (IBIOMESSearch)request.getSession().getAttribute(SESSION_SEARCH_OBJECT);
			if (searchObject != null){
				IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);	
				List<String> uris = searchObject.executeForPreviousPage();
				if (uris != null && uris.size()>0){
					IBIOMESFileList fileList = ibiomesFileAO.getFilesFromPath(uris);
					return new IBIOMESResponse(true, null, fileList);
				}
				else return new IBIOMESResponse(true, null, null);
			}
			else return new IBIOMESResponse(true, null, null);
		}
		catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getLocalizedMessage(), null);
		}
	}
	
	/**
	 * Retrieve results of the current search (sets) 
	 */
	@RequestMapping(value="/sets/current", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getCurrentSetSearchResults() 
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IBIOMESSearch searchObject = (IBIOMESSearch)request.getSession().getAttribute(SESSION_SEARCH_OBJECT);
			if (searchObject != null){
				IBIOMESExperimentSetAO factory = new IBIOMESExperimentSetAO(irodsAccessObjectFactory, irodsAccount, this.getDataSource());
				List<String> ids = searchObject.getResults();
				if (ids != null && ids.size()>0){
					List<IBIOMESExperimentSet> experimentSets = new ArrayList<IBIOMESExperimentSet>();
					for (String id : ids){
						IBIOMESExperimentSet set = factory.getExperimentSet(Long.parseLong(id));
						experimentSets.add(set);
					}
					return new IBIOMESResponse(true, null, experimentSets);
				}
				else return new IBIOMESResponse(true, null, null);
			}
			else return new IBIOMESResponse(true, null, null);
		}
		catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getLocalizedMessage(), null);
		}
	}
	
	/**
	 * Retrieve next results based on current search (sets) 
	 */
	@RequestMapping(value="/sets/next", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getNextSetSearchResults() 
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IBIOMESSearch searchObject = (IBIOMESSearch)request.getSession().getAttribute(SESSION_SEARCH_OBJECT);
			if (searchObject != null){
				IBIOMESExperimentSetAO factory = new IBIOMESExperimentSetAO(irodsAccessObjectFactory, irodsAccount, this.getDataSource());
				List<String> ids = searchObject.executeForNextPage();
				if (ids != null && ids.size()>0){
					List<IBIOMESExperimentSet> experimentSets = new ArrayList<IBIOMESExperimentSet>();
					for (String id : ids){
						IBIOMESExperimentSet set = factory.getExperimentSet(Long.parseLong(id));
						experimentSets.add(set);
					}
					return new IBIOMESResponse(true, null, experimentSets);
				}
				else return new IBIOMESResponse(true, null, null);
			}
			else return new IBIOMESResponse(true, null, null);
		}
		catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getLocalizedMessage(), null);
		}
	}
	
	/**
	 * Retrieve next results based on current search (sets)
	 */
	@RequestMapping(value="/sets/previous", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getPreviousSetSearchResults() 
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			IBIOMESSearch searchObject = (IBIOMESSearch)request.getSession().getAttribute(SESSION_SEARCH_OBJECT);
			if (searchObject != null){
				IBIOMESExperimentSetAO factory = new IBIOMESExperimentSetAO(irodsAccessObjectFactory, irodsAccount, this.getDataSource());
				List<String> ids = searchObject.executeForPreviousPage();
				if (ids != null && ids.size()>0){
					List<IBIOMESExperimentSet> experimentSets = new ArrayList<IBIOMESExperimentSet>();
					for (String id : ids){
						IBIOMESExperimentSet set = factory.getExperimentSet(Long.parseLong(id));
						experimentSets.add(set);
					}
					return new IBIOMESResponse(true, null, experimentSets);
				}
				else return new IBIOMESResponse(true, null, null);
			}
			else return new IBIOMESResponse(true, null, null);
		}
		catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getLocalizedMessage(), null);
		}
	}
	
	/**
	 * Search experiments. Available search types: 'md', 'qm', 'all', 'custom', and 'keyword'
	 * @throws Exception 
	 */
	@RequestMapping(value="/experiments/{searchType}", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse searchExperiments(
			@PathVariable("searchType") String searchType,
			@RequestParam(value="numberOfRows",required=false, defaultValue="20") int maxRows,
			@RequestParam(value="continueIndex",required=false, defaultValue="0") int continueIndex
		) 
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		searchType = searchType.toLowerCase();
		IBIOMESSearch searchObject = (IBIOMESSearch)request.getSession().getAttribute(SESSION_SEARCH_OBJECT);
		request.getSession().setAttribute(SESSION_SEARCH_TYPE, searchType);
		
		if (irodsAccount != null)
		{
			try{
				//if this is a new search
				if (continueIndex == 0)
				{
					if (searchObject != null)
						searchObject.closeSearch();
					searchObject = null;
					request.getSession().removeAttribute(SESSION_SEARCH_OBJECT);
				}
				else //continue with current search results (paging)
				{
					List<Simulation> results = executeExperimentSearch(searchObject, continueIndex, irodsAccount);
					return new IBIOMESResponse(true, null, results);
				}
				
				//file/collection name
				name = request.getParameter("name");
				owner = request.getParameter("owner");
				
				//registration date range
				this.setRegistrationTimeRange();
				
				ArrayList<AVUQueryElement> conditionList = new ArrayList<AVUQueryElement>();
				
				// ================================================================================================
				// COLLECTION search
				// ================================================================================================
				
				//search by keyword
				if (searchType.equals(SEARCH_TYPE_EXPERIMENT_KEYWORD)){
					//compare keyword to all metadata values
					String keyword = request.getParameter("keyword");
					if (keyword!=null && keyword.trim().length()>0){
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.LIKE,  "%"));
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  "%" + keyword + "%"));
					}
				}
				else {
					//description
					String description = request.getParameter("description");
					if (description!=null && description.length()>0 && !description.equals("UNKNOWN")){
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, GeneralMetadata.EXPERIMENT_DESCRIPTION));
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  description));
					}
					
					//software
					String software = request.getParameter("software");
					if (software!=null && software.length()>0 && !software.equals("UNKNOWN")){
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, PlatformMetadata.SOFTWARE_NAME));
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  software));
					}
					
					//molecular system name/description
					String systemName = request.getParameter("systemname");
					if (systemName!=null && systemName.length()>0 && !systemName.equals("UNKNOWN")){
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, TopologyMetadata.MOLECULAR_SYSTEM_DESCRIPTION));
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  systemName));
					}
				}
				
				// ================================================================================================
				// MD EXPERIMENT search
				// ================================================================================================
				
				if (searchType.equals(SEARCH_TYPE_EXPERIMENT_MD))
				{
					conditionList.addAll(this.getSearchParamsForMD());
				}
				
				// ================================================================================================
				// QM EXPERIMENT search
				// ================================================================================================
				
				else if (searchType.equals(SEARCH_TYPE_EXPERIMENT_QM))
				{
					conditionList.addAll(this.getSearchParamsForQM());
				}
				
				// ================================================================================================
				// ANY EXPERIMENT search
				// ================================================================================================
				
				else if (searchType.equals(SEARCH_TYPE_EXPERIMENT))
				{
					//method
					String method = request.getParameter("model");
					if (method!=null && method.trim().length()>0){
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, MethodMetadata.COMPUTATIONAL_METHOD_NAME));
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  method));
					}
					
					//residue chain (normalized or not)
					String residueChain = request.getParameter("residues");
					if (residueChain!=null && residueChain.trim().length()>0){
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.LIKE, TopologyMetadata.RESIDUE_CHAIN + "%"));
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, residueChain));
					}
					
					//molecule type
					String moleculeType = request.getParameter("molecule");
					if (moleculeType!=null && moleculeType.length()>0 && !moleculeType.equals("UNKNOWN")){
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, TopologyMetadata.MOLECULE_TYPE));
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, moleculeType));
					}
					
	
					//number of atoms
					String nAtomsMin = request.getParameter("nAtomsMin");
					String nAtomsMax = request.getParameter("nAtomsMax");
					try{
						if (nAtomsMin!=null && nAtomsMin.length()>0)
						{
							int min = Integer.parseInt(nAtomsMin);
							conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, TopologyMetadata.COUNT_ATOMS));
							conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.NUM_GREATER_OR_EQUAL, nAtomsMin));
						}
						if (nAtomsMax!=null && nAtomsMax.length()>0)
						{
							int max = Integer.parseInt(nAtomsMax);
							conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, TopologyMetadata.COUNT_ATOMS));
							conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.NUM_LESS_OR_EQUAL, nAtomsMax));
						}
					}
					catch (NumberFormatException exc){
						//TODO
					}
				}
				// ================================================================================================
				// CUSTOM search
				// ================================================================================================
				else if (searchType.equals(SEARCH_TYPE_EXPERIMENT_CUSTOM))
				{
					ArrayList<String> attrs = new ArrayList<String>();
					ArrayList<String> ops = new ArrayList<String>();
					ArrayList<String> vals = new ArrayList<String>();
					
					Enumeration<String> paramNames = request.getParameterNames();
					while (paramNames.hasMoreElements())
					{
						String param = paramNames.nextElement();
						if (param.matches("attr\\d+")){
							int i = Integer.parseInt(param.substring(4));
							String attr = request.getParameter(param);
							if (attr.length()>0){
								attrs.add(attr);
								ops.add(request.getParameter("op"+i));
								vals.add(request.getParameter("val"+i));
							}
						}
					}
					for (int i=0;i<attrs.size();i++){
						AVUQueryOperatorEnum op = AVUQueryOperatorEnum.LIKE;
						if (ops.get(i).equals("LIKE")){
							op = AVUQueryOperatorEnum.LIKE;
						}
						else if (ops.get(i).equals("EQUAL")){
							op = AVUQueryOperatorEnum.EQUAL;
						}
						else if (ops.get(i).equals("NUM_GREATER_OR_EQUAL")){
							op = AVUQueryOperatorEnum.NUM_GREATER_OR_EQUAL;
						}
						else if (ops.get(i).equals("NUM_LESS_OR_EQUAL")){
							op = AVUQueryOperatorEnum.NUM_LESS_OR_EQUAL;
						}
						else if (ops.get(i).equals("NUM_LESS_THAN")){
							op = AVUQueryOperatorEnum.NUM_LESS_THAN;
						}
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, attrs.get(i)));
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, op,  vals.get(i)));
					}
				}
				//execute query
				searchObject = this.prepareCollectionSearch(irodsAccount, conditionList, name, owner, registrationDateMin, registrationDateMax, maxRows, searchType);
				List<Simulation> results = executeExperimentSearch(searchObject, continueIndex, irodsAccount);
				
				return new IBIOMESResponse(true, null, results);
				
			} catch (Exception e){
				e.printStackTrace();
				return new IBIOMESResponse(false, "Error: " + e.getLocalizedMessage(), e);
			}
		}
		else return new IBIOMESResponse(false, "User is not authenticated!", null);
	}

	/**
	 * Search files
	 * @throws Exception 
	 */
	@RequestMapping(value="/files", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse searchFiles(
			@RequestParam(value="numberOfRows",required=false, defaultValue="20") int maxRows,
			@RequestParam(value="continueIndex",required=false, defaultValue="0") int continueIndex
		) 
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		
		IBIOMESSearch searchObject = (IBIOMESSearch)request.getSession().getAttribute(SESSION_SEARCH_OBJECT);
		request.getSession().setAttribute(SESSION_SEARCH_TYPE, SEARCH_TYPE_FILE);
		
		if (irodsAccount != null)
		{
			try
			{
			//if this is a new search
				if (continueIndex == 0)
				{
					if (searchObject != null)
						searchObject.closeSearch();
					searchObject = null;
					request.getSession().removeAttribute(SESSION_SEARCH_OBJECT);
				}
				else //continue with current search results (paging)
				{
					List<IBIOMESFile> results = executeFileSearch(searchObject, continueIndex, irodsAccount);
					return new IBIOMESResponse(true, null, results);
				}
				
				//file/collection name
				name = request.getParameter("name");
				owner = request.getParameter("owner");
				
				String parentDir = null;
				long sizeMin = 0, sizeMax = 0;
				
				//registration date range
				this.setRegistrationTimeRange();
				
				ArrayList<AVUQueryElement> conditionList = new ArrayList<AVUQueryElement>();
				
				// ================================================================================================
				// FILE search
				// ================================================================================================
				
				//check if parent directory was specified
				String parentDirVal = request.getParameter("directory");
				if (parentDirVal!=null && parentDirVal.trim().length()>0)
					parentDir = parentDirVal;
				
				//compare keyword to all metadata value
				String keyword = request.getParameter("keyword");
				if (keyword!=null && keyword.trim().length()>0){
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.LIKE,  "%"));
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  "%" + keyword + "%"));
				}
				
				//format
				String format = request.getParameter("format");
				if (format!=null && format.length()>0 && !format.equals("UNKNOWN")){
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, FileMetadata.FILE_FORMAT));
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  format));
				}
				
				//size
				String sizeMinStr = request.getParameter("sizeMin");
				String sizeMaxStr = request.getParameter("sizeMax");
				try{
					if (sizeMinStr!=null && sizeMinStr.length()>0) 
						sizeMin = Long.parseLong(sizeMinStr)*1000;
				} catch (NumberFormatException ne){
					//return createError("'" + sizeMinStr + "' is not a valid file size. Integer value is required.");
				}
				try{
					if (sizeMaxStr!=null && sizeMaxStr.length()>0) 
						sizeMax = Long.parseLong(sizeMaxStr)*1000;
				} catch (NumberFormatException ne){
					//return createError("'" + sizeMaxStr + "' is not a valid file size. Integer value is required.");
				}
	
				//software
				String software = request.getParameter("software");
				if (software!=null && software.length()>0 && !software.equals("UNKNOWN")){
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, PlatformMetadata.SOFTWARE_NAME));
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  software));
				}
				
				//description
				String description = request.getParameter("description");
				if (description!=null && description.length()>0 && !description.equals("UNKNOWN")){
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, FileMetadata.FILE_DESCRIPTION));
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  description));
				}
				//execute query
				searchObject = this.prepareFileSearch(irodsAccount, conditionList, parentDir, name, owner, registrationDateMin, registrationDateMax, sizeMin, sizeMax, maxRows);
				List<IBIOMESFile> results = executeFileSearch(searchObject, continueIndex, irodsAccount);
				
				return new IBIOMESResponse(true, null, results);
				
			} catch (Exception e){
				e.printStackTrace();
				return new IBIOMESResponse(false, "Error: " + e.getLocalizedMessage(), e);
			}
			
		}
		else return new IBIOMESResponse(false, "User is not authenticated!", null);
	}
	
	/**
	 * Search files
	 * @throws Exception 
	 */
	@RequestMapping(value="/sets", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse searchSets(
			@RequestParam(value="numberOfRows",required=false, defaultValue="20") int maxRows,
			@RequestParam(value="continueIndex",required=false, defaultValue="0") int continueIndex
		) 
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		IBIOMESSearch searchObject = (IBIOMESSearch)request.getSession().getAttribute(SESSION_SEARCH_OBJECT);
		request.getSession().setAttribute(SESSION_SEARCH_TYPE, SEARCH_TYPE_SET);
		
		if (irodsAccount != null)
		{
			try{
				//if this is a new search
				if (continueIndex == 0)
				{
					if (searchObject != null)
						searchObject.closeSearch();
					searchObject = null;
					request.getSession().removeAttribute(SESSION_SEARCH_OBJECT);
				}
				else //continue with current search results (paging)
				{
					List<IBIOMESExperimentSet> results = executeSetSearch((IBIOMESExperimentSetSearch)searchObject, continueIndex, irodsAccount);
					return new IBIOMESResponse(true, null, results);
				}
				
				//file/collection name
				name = request.getParameter("name");
				owner = request.getParameter("owner");
				
				//registration date range
				this.setRegistrationTimeRange();
				
				ArrayList<AVUQueryElement> conditionList = new ArrayList<AVUQueryElement>();
				
				// ================================================================================================
				// EXPERIMENT SET search
				// ================================================================================================
				
				//description
				String description = request.getParameter("description");
				if (description!=null && description.length()>0 && !description.equals("UNKNOWN")){
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, GeneralMetadata.EXPERIMENT_DESCRIPTION));
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  description));
				}
				
				//software
				String software = request.getParameter("software");
				if (software!=null && software.length()>0 && !software.equals("UNKNOWN")){
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, PlatformMetadata.SOFTWARE_NAME));
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  software));
				}
				
				//molecular system name/description
				String systemName = request.getParameter("systemname");
				if (systemName!=null && systemName.length()>0 && !systemName.equals("UNKNOWN")){
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, TopologyMetadata.MOLECULAR_SYSTEM_DESCRIPTION));
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  systemName));
				}
				
				//method
				String method = request.getParameter("model");
				if (method!=null && method.trim().length()>0){
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, MethodMetadata.COMPUTATIONAL_METHOD_NAME));
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  method));
				}
				
				//residue chain (normalized or not)
				String residueChain = request.getParameter("residues");
				if (residueChain!=null && residueChain.trim().length()>0){
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.LIKE, TopologyMetadata.RESIDUE_CHAIN + "%"));
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, residueChain));
				}
				
				//molecule type
				String moleculeType = request.getParameter("molecule");
				if (moleculeType!=null && moleculeType.length()>0 && !moleculeType.equals("UNKNOWN")){
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, TopologyMetadata.MOLECULE_TYPE));
					conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, moleculeType));
				}
				
	
				//number of atoms
				String nAtomsMin = request.getParameter("nAtomsMin");
				String nAtomsMax = request.getParameter("nAtomsMax");
				try{
					if (nAtomsMin!=null && nAtomsMin.length()>0)
					{
						int min = Integer.parseInt(nAtomsMin);
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, TopologyMetadata.COUNT_ATOMS));
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.NUM_GREATER_OR_EQUAL, nAtomsMin));
					}
					if (nAtomsMax!=null && nAtomsMax.length()>0)
					{
						int max = Integer.parseInt(nAtomsMax);
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, TopologyMetadata.COUNT_ATOMS));
						conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.NUM_LESS_OR_EQUAL, nAtomsMax));
					}
				}
				catch (NumberFormatException exc){
					//TODO
				}
				
				//execute query
				searchObject = this.prepareSetSearch(irodsAccount, conditionList, name, owner, registrationDateMin, registrationDateMax, maxRows);
				List<IBIOMESExperimentSet> results = executeSetSearch((IBIOMESExperimentSetSearch)searchObject, continueIndex, irodsAccount);
			
				return new IBIOMESResponse(true, null, results);
				
			} catch (Exception e){
				e.printStackTrace();
				return new IBIOMESResponse(false, "Error: " + e.getLocalizedMessage(), e);
			}
		}
		else return new IBIOMESResponse(false, "User is not authenticated!", null);
	}
	
	 /**
	  * Search collections/experiments
	  * @param conditionList
	  * @param fs
	  * @param account
	  * @return
	 * @throws Exception
	 */
	private List<Simulation> executeExperimentSearch(IBIOMESSearch searchObject, int continueIndex, IRODSAccount irodsAccount) throws Exception
	{
		IBIOMESExperimentAO factory = new IBIOMESExperimentAO(irodsAccessObjectFactory, irodsAccount);
		
		List<String> uris = searchObject.executeWithStart(continueIndex);
		request.getSession().setAttribute(SESSION_SEARCH_OBJECT, searchObject);
		
		if (uris != null && uris.size()>0)
		{
			List<Simulation> experiments = new ArrayList<Simulation>();
			List<IBIOMESCollection> collList = factory.getExperimentsFromPath(uris);
			for (IBIOMESCollection coll : collList){
				Simulation simulation = new Simulation(coll);
				experiments.add(simulation);
			}
			return experiments;
		}
		return null;
	}
	
	/**
	  * Search experiment sets
	  * @param conditionList
	  * @param fs
	  * @param account
	  * @return
	 * @throws Exception
	 */
	private List<IBIOMESExperimentSet> executeSetSearch(IBIOMESExperimentSetSearch searchObject, int continueIndex, IRODSAccount irodsAccount) throws Exception
	{
		IBIOMESExperimentSetAO factory = new IBIOMESExperimentSetAO(irodsAccessObjectFactory, irodsAccount, this.getDataSource());
		List<String> ids = searchObject.executeWithStart(continueIndex);
		request.getSession().setAttribute(SESSION_SEARCH_OBJECT, searchObject);
		
		if (ids != null && ids.size()>0)
		{
			List<IBIOMESExperimentSet> experimentSets = new ArrayList<IBIOMESExperimentSet>();
			for (String id : ids){
				IBIOMESExperimentSet set = factory.getExperimentSet(Long.parseLong(id));
				experimentSets.add(set);
			}
			return experimentSets;
		}
		return null;
	}
	
	/**
	 * Search files
	 * @param continueIndex
	 * @param irodsAccount
	 * @return
	 * @throws Exception
	 */
	private List<IBIOMESFile> executeFileSearch(IBIOMESSearch searchObject, int continueIndex, IRODSAccount irodsAccount) throws Exception
	{
		IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
		
		List<String> uris = searchObject.executeWithStart(continueIndex);
		request.getSession().setAttribute(SESSION_SEARCH_OBJECT, searchObject);
		
		if (uris != null && uris.size()>0)
		{
			IBIOMESFileList fileList = ibiomesFileAO.getFilesFromPath(uris);
			return fileList;
		}
		return null;
	}
	 
	private IBIOMESExperimentSearch prepareCollectionSearch(IRODSAccount irodsAccount, ArrayList<AVUQueryElement> conditionList, String collectionName, String owner, long creationDateMin, long creationDateMax, int maxRows, String searchType) throws Exception
	{
		IBIOMESExperimentSearch experimentSearch = new IBIOMESExperimentSearch(true, true, irodsAccessObjectFactory, irodsAccount);
		experimentSearch.setNumberOfRowsRequested(maxRows);
		experimentSearch.setAvuConditions(conditionList);
		experimentSearch.setCollectionName(collectionName);
		experimentSearch.setOwnerUsername(owner);
		experimentSearch.setCreationDateMin(creationDateMin);
		experimentSearch.setCreationDateMax(creationDateMax);
		
		return experimentSearch;
	}
	
	private IBIOMESExperimentSetSearch prepareSetSearch(IRODSAccount irodsAccount, ArrayList<AVUQueryElement> conditionList, String setName, String owner, long creationDateMin, long creationDateMax, int maxRows) throws Exception
	{
		IBIOMESExperimentSetSearch experimentSetSearch = new IBIOMESExperimentSetSearch(irodsAccount, this.getDataSource());
		experimentSetSearch.setNumberOfRowsRequested(maxRows);
		experimentSetSearch.setAvuConditions(conditionList);
		experimentSetSearch.setName(setName);
		experimentSetSearch.setOwnerUsername(owner);
		experimentSetSearch.setCreationDateMin(creationDateMin);
		experimentSetSearch.setCreationDateMax(creationDateMax);
		
		return experimentSetSearch;
	}
	
	private IBIOMESFileSearch prepareFileSearch(IRODSAccount irodsAccount, ArrayList<AVUQueryElement> conditionList, String parentDir, String fileName, String owner, long creationDateMin, long creationDateMax, long sizeMin, long sizeMax, int maxRows) throws Exception
	{
		IBIOMESFileSearch fileSearch = new IBIOMESFileSearch(true, true, irodsAccessObjectFactory, irodsAccount);
		fileSearch.setNumberOfRowsRequested(maxRows);
		fileSearch.setAvuConditions(conditionList);
		fileSearch.setDirectory(parentDir);
		fileSearch.setFilename(fileName);
		fileSearch.setOwnerUsername(owner);
		fileSearch.setCreationDateMin(creationDateMin);
		fileSearch.setCreationDateMax(creationDateMax);
		fileSearch.setSizeMin(sizeMin);
		fileSearch.setSizeMax(sizeMax);
		
		return fileSearch;
	}
	
	/**
	 * Get status of current search
	 * @throws Exception 
	 */
	@RequestMapping(value="/status", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getSearchStatus() 
	{
		try{
			IBIOMESSearch searchObject = (IBIOMESSearch)request.getSession().getAttribute(SESSION_SEARCH_OBJECT);
			if (searchObject == null)
				return new IBIOMESResponse(true, null, null);
			IBIOMESSearchStatus status = searchObject.getStatus();
			status.setSearchType((String)request.getSession().getAttribute(SESSION_SEARCH_TYPE));
			return new IBIOMESResponse(true, null, status);
		}
		catch(Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Error: " + e.getLocalizedMessage(), null);
		}
	}
	
	/**
	 * Retrieve parameters for MD simulation search
	 * @return
	 * @throws JargonQueryException 
	 */
	private ArrayList<AVUQueryElement> getSearchParamsForMD() throws JargonQueryException
	{
		ArrayList<AVUQueryElement> conditionList = new ArrayList<AVUQueryElement>();
		
		//MD simulations only (include classical MD, Langevin MD, REMD, and quantum MD(?))
		conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, MethodMetadata.TIME_STEP_LENGTH));
		conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.NUM_GREATER_OR_EQUAL, "0"));
		
		//residue chain (normalized or not)
		String residueChain = request.getParameter("residues");
		if (residueChain!=null && residueChain.trim().length()>0){
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.LIKE, TopologyMetadata.RESIDUE_CHAIN + "%"));
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, residueChain));
		}
		
		//molecule type
		String moleculeType = request.getParameter("molecule");
		if (moleculeType!=null && moleculeType.length()>0 && !moleculeType.equals("UNKNOWN")){
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, TopologyMetadata.MOLECULE_TYPE));
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, moleculeType));
		}
		
		//force-field
		String ff = request.getParameter("forcefield");
		if (ff!=null && ff.trim().length()>0 && !ff.equals("UNKNOWN")){
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, MethodMetadata.FORCE_FIELD));
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  ff));
		}
		
		//solvent
		String solvent = request.getParameter("solvent");
		if (solvent!=null && solvent.trim().length()>0 && !solvent.equals("UNKNOWN")){
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, MethodMetadata.SOLVENT_TYPE));
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  solvent));
		}
		
		//constraints
		String constraints = request.getParameter("constraints");
		if (constraints!=null && constraints.trim().length()>0 && !constraints.equals("UNKNOWN")){
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, MethodMetadata.CONSTRAINT_ALGORITHM));
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  constraints));
		}
		
		//boundary conditions
		String bc = request.getParameter("boundcond");
		if (bc!=null && bc.trim().length()>0 && !bc.equals("UNKNOWN")){
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, MethodMetadata.BOUNDARY_CONDITIONS));
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  bc));
		}
		
		//electrostatics
		String electrostatics = request.getParameter("electrostatics");
		if (electrostatics!=null && electrostatics.trim().length()>0 && !electrostatics.equals("UNKNOWN")){
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, MethodMetadata.ELECTROSTATICS_MODELING));
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  electrostatics));
		}
		
		//number of atoms
		String nAtomsMin = request.getParameter("nAtomsMin");
		String nAtomsMax = request.getParameter("nAtomsMax");
		try{
			if (nAtomsMin!=null && nAtomsMin.trim().length()>0)
			{
				int min = Integer.parseInt(nAtomsMin);
				conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, TopologyMetadata.COUNT_ATOMS));
				conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.NUM_GREATER_OR_EQUAL, nAtomsMin));
			}
			if (nAtomsMax!=null && nAtomsMax.trim().length()>0)
			{
				int max = Integer.parseInt(nAtomsMax);
				conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, TopologyMetadata.COUNT_ATOMS));
				conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.NUM_LESS_OR_EQUAL, nAtomsMax));
			}
		}
		catch (NumberFormatException exc){
			//TODO
		}
		
		return conditionList;
	}
	
	
	/**
	 * Retrieve parameters for QM calculation search
	 * @return
	 * @throws JargonQueryException 
	 */
	private ArrayList<AVUQueryElement> getSearchParamsForQM() throws JargonQueryException
	{
		ArrayList<AVUQueryElement> conditionList = new ArrayList<AVUQueryElement>();
		
		//QM calculation only ('Quantum mechanics' and 'Quantum MD')
		conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, MethodMetadata.COMPUTATIONAL_METHOD_NAME));
		conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  "Quantum%"));
		
		//atomic composition
		String composition = request.getParameter("composition");
		if (composition!=null && composition.trim().length()>0){
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.LIKE, TopologyMetadata.MOLECULE_ATOMIC_COMPOSITION + "%"));
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, composition));
		}
							
		//basis set
		String basisset = request.getParameter("basisset");
		if (basisset!=null && basisset.length()>0 && !basisset.equals("UNKNOWN")){
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, MethodMetadata.QM_BASIS_SET));
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  basisset));
		}
		
		//level of theory
		String leveloftheory = request.getParameter("leveloftheory");
		if (leveloftheory!=null && leveloftheory.length()>0 && !leveloftheory.equals("UNKNOWN")){
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, MethodMetadata.QM_LEVEL_OF_THEORY));
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  leveloftheory));
		}

		//qm method name
		String qmMethod = request.getParameter("qmMethod");
		if (qmMethod!=null && qmMethod.length()>0 && !qmMethod.equals("UNKNOWN")){
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, MethodMetadata.QM_METHOD_NAME));
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  qmMethod));
		}
		//calculations
		String[] calculations = request.getParameterValues("calculations");
		if (calculations!=null && calculations.length>0){
			for (int c=0;c<calculations.length;c++)
			{
				conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, MethodMetadata.CALCULATION));
				String calcType = calculations[c];
				if (calculations[c].equals("spe"))
					calcType = QMMethod.CALCULATION_ENERGY_SP;
				else if (calculations[c].equals("scan"))
					calcType = QMMethod.CALCULATION_SCAN;
				else if (calculations[c].equals("optimization"))
					calcType = QMMethod.QM_GEOMETRY_OPTIMIZATION;
				else if (calculations[c].equals("freq"))
					calcType = QMMethod.CALCULATION_FREQUENCY;
				conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  calcType));
			}
		}
		
		//spin multiplicity
		String multiplicity = request.getParameter("multiplicity");
		if (multiplicity!=null && multiplicity.length()>0 && !multiplicity.equals("UNKNOWN")){
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, MethodMetadata.QM_SPIN_MULTIPLICITY));
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  multiplicity));
		}
		
		//charge
		String charge = request.getParameter("charge");
		if (charge!=null && charge.length()>0 && !charge.equals("UNKNOWN")){
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, TopologyMetadata.TOTAL_MOLECULE_CHARGE));
			conditionList.add(AVUQueryElement.instanceForValueQuery( AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  charge));
		}
		
		return conditionList;
	}
}

