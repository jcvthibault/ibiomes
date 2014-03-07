package edu.utah.bmi.ibiomes.web.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.query.JargonQueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.utah.bmi.ibiomes.catalog.MetadataLookup;
import edu.utah.bmi.ibiomes.metadata.IBIOMESFileGroup;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataRichAVUList;
import edu.utah.bmi.ibiomes.pub.IBIOMESCollection;
import edu.utah.bmi.ibiomes.pub.IBIOMESCollectionList;
import edu.utah.bmi.ibiomes.pub.IBIOMESExperimentAO;
import edu.utah.bmi.ibiomes.pub.IBIOMESFile;
import edu.utah.bmi.ibiomes.pub.IBIOMESFileAO;
import edu.utah.bmi.ibiomes.pub.set.IBIOMESExperimentSet;
import edu.utah.bmi.ibiomes.pub.set.IBIOMESExperimentSetAO;
import edu.utah.bmi.ibiomes.pub.set.IBIOMESExperimentSetList;
import edu.utah.bmi.ibiomes.web.IBIOMESResponse;
import edu.utah.bmi.ibiomes.web.IBIOMESUrl;

@Controller
@RequestMapping(value="/services/experimentset")
public class ExperimentSetService
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
     * 
     * @return
     */
    private DataSource getDataSource(){
    	WebApplicationContext wContext = WebApplicationContextUtils.getWebApplicationContext(this.context);
    	return (DataSource)wContext.getBean("dataSource");
    }
    
    private MetadataLookup metadataDictionary;
    private MetadataLookup getMetadataDictionary(){
    	if (metadataDictionary == null)
    		metadataDictionary = new MetadataLookup(this.context.getRealPath("config/metadata-attr"));
    	return metadataDictionary;
    }
        
	/**
	 * Retrieve experiment set
	 * @param id Experiment set ID
	 * @return Experiment set
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getExperimentSetByID(@RequestParam("id") long id)
	{
		IBIOMESExperimentSet set = null;
		IBIOMESExperimentSetAO ao = null;
		
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try {
			ao = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());
			set = ao.getExperimentSet(id);
			return new IBIOMESResponse(true, null, set);
		} catch (JargonException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve experiment set (Jargon error: "+e.getMessage()+")", null);
		} catch (SQLException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve experiment set (SQL error: "+e.getMessage()+")", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve experiment set (Error: "+e.getMessage()+")", null);
		}
	}
	
	/**
	 * Retrieve experiment set list for user
	 * @return List of experiment sets
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse listExperimentSets()
	{
		IBIOMESExperimentSetList sets = null;
		IBIOMESExperimentSetAO ao = null;
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try {
			ao = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());
			sets = ao.listExperimentSetsWithOwn();
			return new IBIOMESResponse(true, null, sets);
		} catch (JargonException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve experiment set (Jargon error: "+e.getMessage()+")", null);
		} catch (JargonQueryException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve experiment set (Jargon query error: "+e.getMessage()+")", null);
		} catch (SQLException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve experiment set (SQL error: "+e.getMessage()+")", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve experiment set (Error: "+e.getMessage()+")", null);
		}
	}
	
	/**
	 * Retrieve experiments in set
	 * @param id Experiment set ID
	 * @return List of experiments
	 */
	@RequestMapping(value = "/experiments", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse getExperimentsInSet(@RequestParam("id") long id) 
	{
		IBIOMESCollectionList experiments = null;
		IBIOMESExperimentSetAO setAO = null;
		IBIOMESExperimentAO expAO = null;
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try {
			expAO = new IBIOMESExperimentAO(irodsAccessObjectFactory, irodsAccount);
			setAO = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());
			List<String> experimentPaths = setAO.listExperimentsInSet(id);
			experiments = expAO.getExperimentsFromPath(experimentPaths);
			return new IBIOMESResponse(true, null, experiments);
		} catch (JargonException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve experiment (Jargon error: "+e.getMessage()+")", null);
		} catch (JargonQueryException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve experiment (Jargon query error: "+e.getMessage()+")", null);
		} catch (SQLException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve experiment (SQL error: "+e.getMessage()+")", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve experiment (Error: "+e.getMessage()+")", null);
		}
	}


	/**
	 * Add experiment to set
	 * @param id Experiment set ID
	 * @param uri Experiment path
	 */
	@RequestMapping(value = "/experiments/add", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse addExperimentToSet(
					@RequestParam("id") int id,
					@RequestParam("uri") String uri) 
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");

		try{
			IBIOMESExperimentSetAO ao = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());
			ao.addExperimentToSet(id, uri);
			return new IBIOMESResponse(true, "Experiment ["+uri+"] successfully added to experiment set", null);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Experiment ["+uri+"] could not be added to experiment set. Exception: " + e.getMessage(), null);
		}
	}

	/**
	 * Remove experiments from set
	 * @param id Experiment set ID
	 * @param uris Experiment paths
	 */
	@RequestMapping(value = "/experiments/remove", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse removeExperimentsFromSet(
					@RequestParam("id") int id,
					@RequestParam("uris") String urisStr) 
	{
		String[] uris = urisStr.split("\\,");
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try{
			IBIOMESExperimentSetAO ao = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());
			for (int u=0;u<uris.length;u++){
				ao.removeExperimentFromSet(id, uris[u]);
			}
			return new IBIOMESResponse(true, uris.length + " experiments successfully removed from set", null);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "These "+ uris.length +" experiments could not be removed. Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Create experiment set
	 * @param id Experiment set ID
	 * @return Experiment set
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse createExperimentSet(
			@RequestParam("name") String name,
			@RequestParam("description") String description,
			@RequestParam(value="isPublic", required=false, defaultValue="false") boolean isPublic
		)
	{
		IBIOMESExperimentSet set = null;
		IBIOMESExperimentSetAO ao = null;
		
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try {
			ao = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());
			set = ao.createExperimentSet(name, description, isPublic);
			return new IBIOMESResponse(true, null, set);
		} catch (JargonException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not create experiment set (Jargon error: "+e.getMessage()+")", null);
		} catch (SQLException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not create experiment set (SQL error: "+e.getMessage()+")", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not create experiment set (Error: "+e.getMessage()+")", null);
		}
	}
	
	/**
	 * Create experiment set
	 * @param id Experiment set ID
	 * @return Experiment set
	 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse createExperimentSet(
			@RequestParam("id") long id,
			@RequestParam("name") String name,
			@RequestParam("description") String description,
			@RequestParam(value="isPublic", required=false, defaultValue="false") boolean isPublic
		)
	{
		IBIOMESExperimentSet set = null;
		IBIOMESExperimentSetAO ao = null;
		
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try {
			ao = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());
			set = ao.updateExperimentSet(id, name, description, isPublic);
			return new IBIOMESResponse(true, null, set);
		} catch (JargonException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not create experiment set (Jargon error: "+e.getMessage()+")", null);
		} catch (SQLException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not create experiment set (SQL error: "+e.getMessage()+")", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not create experiment set (Error: "+e.getMessage()+")", null);
		}
	}
	
	/**
	 * Delete experiment set(s)
	 * @param ids Experiment set IDs
	 * @return True if success
	 * @throws Exception 
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse deleteFile(@RequestParam("ids") String idsString)
	{
		String[] ids = idsString.split("\\,");
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");		
		IBIOMESExperimentSetAO ao = null;
		try{
			ao = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());
			for (int f=0;f<ids.length;f++){
				ao.deleteExperimentSet(Long.parseLong(ids[f]));
			}
			return new IBIOMESResponse(true, ids.length + "experiments set were successfully deleted", null);
		} catch (JargonException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Experiment set could not be deleted'. Exception: " + e.getMessage(), null);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Experiment set ID not valid!", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Experiment set could not be deleted'. Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Retrieve AVUs for a given experiment set
	 * @param id Experiment set ID
	 * @return AVU list
	 */
	@RequestMapping(value = "/avus", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse getExperimentSetMetadata(@RequestParam("id") long id)
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		IBIOMESExperimentSetAO ao = null;
		MetadataAVUList metadata = null;
		try {
			ao = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());
			metadata = ao.getMetadata(id);
			MetadataRichAVUList richMetadata = getMetadataDictionary().lookupMetadataAVU(metadata);
			return new IBIOMESResponse(true, null, richMetadata);
		} catch (JargonException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Experiment set metadata could not be retrieved (Jargon exception: " + e.getMessage() + ")", null);
		} catch (SQLException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Experiment set metadata could not be retrieved (SQL exception: " + e.getMessage() + ")", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Experiment set metadata could not be retrieved (Exception: " + e.getMessage() + ")", null);
		}
	}
	
	/**
	 * Add AVU to experiment set
	 * @param id Experiment set ID
	 * @return new AVU
	 */
	@RequestMapping(value = "/avus/add", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse addAvuToExperimentSet(
					@RequestParam("id") long id,
					@RequestParam("attribute") String attribute,
					@RequestParam("value") String value,
					@RequestParam(value="unit", required=false, defaultValue="") String unit) 
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try{
			MetadataAVU avu = new MetadataAVU(attribute, value, unit);
			IBIOMESExperimentSetAO ao = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());
			ao.addMetadata(id, avu);
			return new IBIOMESResponse(true, "AVU ["+avu+"] successfully added to experiment set", avu);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "AVU ["+attribute+"="+value+"] could not be added to experiment set. Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Remove AVU from experiment set
	 * @param id Experiment set ID
	 * @return new AVU
	 */
	@RequestMapping(value = "/avus/remove", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse removeAvuFromExperimentSet(
					@RequestParam("id") long id,
					@RequestParam("attribute") String attribute,
					@RequestParam("value") String value,
					@RequestParam(value="unit", required=false, defaultValue="") String unit) 
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try{
			MetadataAVU avu = new MetadataAVU(attribute, value, unit);
			IBIOMESExperimentSetAO ao = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());
			ao.deleteMetadata(id, avu);
			return new IBIOMESResponse(true, "AVU ["+avu+"] successfully removed from experiment set", avu);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "AVU ["+attribute+"="+value+"] could not be removed from experiment set. Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Copy topology-related AVUs and add to experiment set
	 * @param id Experiment set ID
	 * @return new AVUs
	 */
	@RequestMapping(value = "/avus/topology/copy", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse copyTopologyExperimentAvuToSet(
					@RequestParam("id") long id,
					@RequestParam("uri") String uri) 
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try{
			IBIOMESExperimentAO eAO = new IBIOMESExperimentAO(irodsAccessObjectFactory, irodsAccount);
			IBIOMESExperimentSetAO setAO = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());

			IBIOMESCollection experiment = eAO.getExperimentFromPath(uri);
			MetadataAVUList topoMetadata = experiment.getMetadata().getTopologyMetadata();
			
			setAO.addMetadata(id, topoMetadata);
			return new IBIOMESResponse(true, "Topology AVUs successfully added to experiment set", topoMetadata);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Topology AVUs could not be added to experiment set. Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Copy method-related AVUs and add to experiment set
	 * @param id Experiment set ID
	 * @return new AVUs
	 */
	@RequestMapping(value = "/avus/method/copy", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse copyMethodExperimentAvuToSet(
					@RequestParam("id") long id,
					@RequestParam("uri") String uri) 
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try{
			IBIOMESExperimentAO eAO = new IBIOMESExperimentAO(irodsAccessObjectFactory, irodsAccount);
			IBIOMESExperimentSetAO setAO = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());

			IBIOMESCollection experiment = eAO.getExperimentFromPath(uri);
			MetadataAVUList methodMetadata = experiment.getMetadata().getMethodMetadata();
			
			setAO.addMetadata(id, methodMetadata);
			return new IBIOMESResponse(true, "Method/parameter AVUs successfully added to experiment set", methodMetadata);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Method/parameter AVUs could not be added to experiment set. Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Retrieve analysis files 
	 * @param id Experiment set ID
	 * @return File list
	 */
	@RequestMapping(value = "/analysis", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse getAnalysisFileReferences(@RequestParam("id") long id)
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try {
			IBIOMESExperimentSetAO ao = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());
			IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
			List<String> files = new ArrayList<String>();
			List<Integer> ids = ao.getAnalysisFileReferences(id);
			for (Integer fileId : ids){
				IBIOMESFile file = ibiomesFileAO.getFileByID(fileId);
				files.add(file.getAbsolutePath());
			}
			return new IBIOMESResponse(true, null, files);
		}
		catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Retrieve list of analysis files in referenced experiments minus the ones that are already directly referenced by the set.
	 * @param id Experiment set ID
	 * @return File list
	 */
	@RequestMapping(value = "/analysis/available", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse getAvailableAnalysisFile(@RequestParam("id") long id)
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try {
			IBIOMESExperimentSetAO ao = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());
			IBIOMESExperimentAO expAO = new IBIOMESExperimentAO(irodsAccessObjectFactory, irodsAccount);
			IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
			
			List<String> availableFiles = new ArrayList<String>();
			
			//get list of analysis files in referenced experiments
			List<String> experimentPaths = ao.listExperimentsInSet(id);
			for (String experimentPath : experimentPaths){
				availableFiles.addAll(expAO.getAnalysisFilesInExperiment(experimentPath));
			}
			//remove files that are already referenced by this set
			List<Integer> ids = ao.getAnalysisFileReferences(id);
			for (Integer fileId : ids){
				IBIOMESFile file = ibiomesFileAO.getFileByID(fileId);
				availableFiles.remove(file.getAbsolutePath());
			}
			return new IBIOMESResponse(true, null, availableFiles);
		}
		catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Delete AVU for given experiment set
	 * @param idStr Experiment set ID
	 * @return AVU
	 */
	@RequestMapping(value = "/analysis/remove", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse removeAnalysisFileReferenceFromExperimentSet(
			@RequestParam("id") long id, 
			@RequestParam("files") String fileURIs) 
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try{
			String[] uris = fileURIs.split("\\,");
			IBIOMESExperimentSetAO ao = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());
			for (int f=0; f<uris.length;f++){
				ao.removeAnalysisFileReference(id, uris[f]);
			}
			return new IBIOMESResponse(true, uris.length + " references successfully removed", null);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "These references could not be removed. Exception: " + e.getMessage(), null);
		}
	}
		
	/**
	 * Add AVU to experiment set
	 * @param idStr Experiment set ID
	 * @return new AVU
	 */
	@RequestMapping(value = "/analysis/add", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse addAnalysisFileReferenceToExperimentSet(
					@RequestParam("id") long id,
					@RequestParam("files") String fileURIs) 
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try{
			String[] uris = fileURIs.split("\\,");
			IBIOMESExperimentSetAO ao = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());
			for (int f=0; f<uris.length;f++){
				ao.addAnalysisFileReference(id, uris[f]);
			}
			return new IBIOMESResponse(true, uris.length+" references successfully added to experiment set", null);
		} catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "These references could not be added to experiment set. Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Retrieve analysis file links (other than images)
	 * @param id Experiment set ID
	 * @return File list
	 */
	@RequestMapping(value = "/analysis/get/links", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse getAnalysisFileReferences(@RequestParam("id") long id,
			@RequestParam(value="max",required=false,defaultValue="100") int max)
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try {
			IBIOMESExperimentSetAO ao = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());
			IBIOMESFileAO ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
			
			List<IBIOMESUrl> urls = new ArrayList<IBIOMESUrl>();
			List<Integer> ids = ao.getAnalysisFileReferences(id);
			for (Integer fileId : ids){
				IBIOMESFile file = ibiomesFileAO.getFileByID(fileId);
				if (!IBIOMESFileGroup.isImageFile(file.getFormat())){
					IBIOMESUrl url = new IBIOMESUrl(file.getName(), file.getDescription(), file.getFormat(), file.getAbsolutePath());
					urls.add(url);
				}
			}
			return new IBIOMESResponse(true, null, urls);
		}
		catch (Exception e){
			e.printStackTrace();
			return new IBIOMESResponse(false, "Exception: " + e.getMessage(), null);
		}
	}
	
	/**
	 * Retrieve analysis files referenced by experiments in the set
	 * @param id Experiment set ID
	 * @return List of file paths
	 */
	@RequestMapping(value = "/experiments/analysis", method = RequestMethod.GET)
	public @ResponseBody IBIOMESResponse getAnalysisFilesFromExperiments(@RequestParam("id") long id)
	{
		IBIOMESExperimentSetAO setAO = null;
		IBIOMESExperimentAO expAO = null;
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		try {
			expAO = new IBIOMESExperimentAO(irodsAccessObjectFactory, irodsAccount);
			setAO = new IBIOMESExperimentSetAO( irodsAccessObjectFactory, irodsAccount, this.getDataSource());
			List<String> filePaths = new ArrayList<String>();
			//get list of experiments in this set
			List<String> experimentPaths = setAO.listExperimentsInSet(id);
			for (String experimentPath : experimentPaths){
				//get list of analysis files for this experiment
				filePaths.addAll(expAO.getAnalysisFilesInExperiment(experimentPath));
			}
			return new IBIOMESResponse(true, null, filePaths);
		} catch (JargonException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve experiment (Jargon error: "+e.getMessage()+")", null);
		} catch (JargonQueryException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve experiment (Jargon query error: "+e.getMessage()+")", null);
		} catch (SQLException e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve experiment (SQL error: "+e.getMessage()+")", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new IBIOMESResponse(false, "Could not retrieve experiment (Error: "+e.getMessage()+")", null);
		}
	}
}

