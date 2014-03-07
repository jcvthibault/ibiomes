
var searchExperimentsURL = contextPath + '/rest/services/search/experiments';
var currentExperimentSearchResultsURL = contextPath + '/rest/services/search/experiments/current';
var nextExperimentSearchResultsURL = contextPath + '/rest/services/search/experiments/next';
var previousExperimentSearchResultsURL = contextPath + '/rest/services/search/experiments/previous';

var searchFilesURL = contextPath + '/rest/services/search/files';
var currentFileSearchResultsURL = contextPath + '/rest/services/search/files/current';
var nextFileSearchResultsURL = contextPath + '/rest/services/search/files/next';
var previousFileSearchResultsURL = contextPath + '/rest/services/search/files/previous';

var searchSetsURL = contextPath + '/rest/services/search/sets';
var currentSetSearchResultsURL = contextPath + '/rest/services/search/sets/current';
var nextSetSearchResultsURL = contextPath + '/rest/services/search/sets/next';
var previousSetSearchResultsURL = contextPath + '/rest/services/search/sets/previous';

var clearSearchesURL = contextPath + '/rest/services/search/clear';

var searchStatusURL = contextPath + '/rest/services/search/status';

var downloadFileURL = contextPath + '/rest/services/download';

var metadataCatalogURL = contextPath + '/rest/metadata';
var metadataValuesURL = contextPath + '/rest/metadata/$/values';

var MAX_FILE_SIZE = 10000000; //10 MB

//holder for search requests
var searchRequest;
//holder for search page link requests
var searchPageLinkRequest;

/**
 * 
 */
function initSearchResultPage()
{
	$("#search-results").empty();
	$("#search-pages-links").empty();
	$("#search-loading").show();
}

/**
 * 
 */
function getNextExperimentSearchResults(searchType)
{
	var isQmSearch = (searchType == "QM" || searchType == "qm");
	
	initSearchResultPage();
	$("#tabs").tabs( "enable", 6);
	$("#tabs").tabs( "option", "active", 6 );
	
	if (searchRequest != null){
		searchRequest.abort();
	}
	searchRequest = $.ajax({
	    url: nextExperimentSearchResultsURL,
	    dataType: 'json',
	    success: function(obj){
	    	if (obj.success){
		    	var data = obj.data;
		    	var html = "";
		    	if (data != null && data.length>0)
		    	{
		    		html = buildExperimentSearchResultsHtml(data, isQmSearch, searchType);
			
			    	$("#search-loading").hide();
			    	$("#search-results").html(html);
		    	}
			    else {
			    	$("#search-loading").hide();
			    	$("#search-results").html("<p>The search did not return any result.</p>");
			    }
	    	}
	    	else {
	    		$("#search-loading").hide();
	    		showErrorDialog(obj.message);
	    	}
	    },
	    error: function(jqXHR, textStatus, errorThrown) { 
	    	if (!isPageBeingRefreshed && textStatus != 'abort'){	
		    	$("#search-loading").hide();
		    	showErrorDialog("Error occured while searching");
	    	}
	    }
	});
}

/**
 * 
 */
function getPreviousExperimentSearchResults(searchType)
{
	var isQmSearch = (searchType == "QM" || searchType == "qm");
	
	initSearchResultPage();
	$("#tabs").tabs( "enable", 6 );
	$("#tabs").tabs( "option", "active", 6 );
	
	if (searchRequest != null){
		searchRequest.abort();
	}
	searchRequest = $.ajax({
	    url: previousExperimentSearchResultsURL,
	    dataType: 'json',
	    success: function(obj){
	    	if (obj.success){
		    	var data = obj.data;
		    	var html = "";
		    	if (data != null && data.length>0)
		    	{
		    		html = buildExperimentSearchResultsHtml(data, isQmSearch, searchType);
			
			    	$("#search-loading").hide();
			    	$("#search-results").html(html);
		    	}
			    else {
			    	$("#search-loading").hide();
			    	$("#search-results").html("<p>The search did not return any result.</p>");
			    }
		    }
	    	else {
	    		$("#search-loading").hide();
	    		showErrorDialog(obj.message);
	    	}
	    },
	    error: function(jqXHR, textStatus, errorThrown) { 
	    	if (!isPageBeingRefreshed && textStatus != 'abort'){		
		    	$("#search-loading").hide();
		    	showErrorDialog("Error occured while searching");
	    	}
	    }
	});
}

/**
 * 
 * @param status
 * @param searchType
 */
function getCurrentExperimentSearchResults(searchType)
{
	var isQmSearch = (searchType == "QM" || searchType == "qm");
	initSearchResultPage();
	
	if (searchRequest != null){
		searchRequest.abort();
	}
	searchRequest = $.ajax({
	    url: currentExperimentSearchResultsURL,
	    dataType: 'json',
	    success: function(obj){
	    	if (obj.success){
		    	var data = obj.data;
		    	var html = "";
		    	if (data != null && data.length>0)
		    	{
		    		html = buildExperimentSearchResultsHtml(data, isQmSearch, searchType);
			
			    	$("#search-loading").hide();
			    	$("#search-results").html(html);
		    	}
			    else {
			    	$("#search-loading").hide();
			    	$("#search-results").html("<p>The search did not return any result.</p>");
			    }
	    	}
	    	else {
	    		$("#search-loading").hide();
	    		showErrorDialog(obj.message);
	    	}
	      },
	      error: function(jqXHR, textStatus, errorThrown) { 
	    	if (!isPageBeingRefreshed && textStatus != 'abort'){
	    		$("#search-loading").hide();
	    		showErrorDialog("Error occured while searching");
	    	}
	      }
	});
}

/**
 * Search experiments
 * @param numberOfRowsRequested
 * @param searchType
 * @param formId
 * @param continueIndex
 */
function executeExperimentSearch(numberOfRowsRequested, searchType, formId, continueIndex)
{
	var mySearchURL = searchExperimentsURL + "/" + searchType;
	if (continueIndex == 0){
		var formString = $("#"+formId).serialize();
		mySearchURL += "?" + formString;
	}
	var isQmSearch = (searchType == "QM" || searchType == "qm");

	initSearchResultPage();
	$("#tabs").tabs( "enable", 6);
	$("#tabs").tabs( "option", "active", 6 );
	
	if (searchRequest != null){
		searchRequest.abort();
	}
	searchRequest = $.ajax({
	    url: mySearchURL,
	    dataType: 'json',
	    data: {
	    	"numberOfRows" : numberOfRowsRequested,
	    	"continueIndex" : continueIndex
	    },
	    success: function(obj){
	    	if (obj.success){
		    	var data = obj.data;
		    	var html = "";
		    	if (data != null && data.length>0)
		    	{
		    		html = buildExperimentSearchResultsHtml(data, isQmSearch, searchType);

			    	$("#search-loading").hide();
			    	$("#search-results").html(html);
		    	}
			    else {
			    	$("#search-loading").hide();
			    	$("#search-results").html("<p>The search did not return any result.</p>");
			    }
	    	}
	    	else {
	    		$("#search-loading").hide();
	    		showErrorDialog(obj.message);
	    	}
	    },
	    error: function(jqXHR, textStatus, errorThrown) { 
	    	if (!isPageBeingRefreshed && textStatus != 'abort'){
		    	$("#search-loading").hide();
		    	showErrorDialog("Error occured while searching");
	    	}
	    }
	});
}

/**
 * Build html to present experiment search results
 * @param obj Search result object
 * @returns {String}
 */
function buildExperimentSearchResultsHtml(obj, isQmSearch, searchType)
{
	var i = 0;
	var html = "";
	// create links for pages
	createPagingLinksExperiments(obj.length, searchType);
	
	// result table
	
	html += "<form id='searchResultsForm' class='blank'>";
	html += "<table><tr>";
	html += "	<th class='first'></th>";
	html += "	<th>Experiment</th>";
	html += "	<th style='text-align:center'>Software</th>";
	html += "	<th style='text-align:center'>Method</th>";
	if (isQmSearch){
		html += "	<th>Composition</th>";
	}
	else {
    	html += "	<th>N atoms</th>";
		html += "	<th>N solvent</th>";
		html += "	<th style='text-align:center'>Molecule</th>";
	}
	html += "	<th style='text-align:center'>Owner</th>";
	html += "	<th style='text-align:center'>Published</th>";
	html += "</tr>";
	
	while( i < obj.length )
	{
		var experiment = obj[i];
    	
		var rowClass = "row-a";
		if (i % 2 == 1)
			rowClass = "row-b";
		
		var title = experiment.title;
		if (experiment.fileCollection!=null)
		{
			var s=0;
			var methodStr = "?";
			if (experiment.method != null && experiment.method.name != null)
				methodStr = abbreviateMethod(experiment.method.name);
			var softwareListStr = "";
			var softwareList = experiment.softwarePackages;
    		if (softwareList!=null && softwareList.length>0){
    			for (s=0; s<softwareList.length; s++){
    				softwareListStr += softwareList[s] + ", ";
    			}
    			softwareListStr = softwareListStr.substring(0,softwareListStr.length-2);
    		}
			
			html += "<tr class='"+ rowClass + "'>";
			html += "	<td class='first'><input name='uri' type='checkbox' value='" + experiment.fileCollection.absolutePath + "' /></td>";
			html += "	<td style='text-align:left'><a class='link' href='collection.do?uri="+ experiment.fileCollection.absolutePath +"'>" + title + "</a></td>";
			html += "	<td style='text-align:center'>"+ softwareListStr + "</td>";
			html += "	<td style='text-align:center'>"+ methodStr + "</td>";
			
			if (isQmSearch)
			{
				var compo = "?";
				if (experiment.molecularSystem!=null && experiment.molecularSystem.molecularComposition!=null)
				{
					compo = experiment.molecularSystem.molecularComposition;
					compo = compo.replace(/\[/g ,"<sub>");
					compo = compo.replace(/\]/g ,"</sub>");
				}
				html += "	<td style='text-align:center'>"+ compo + "</td>";
			}
			else 
			{
	    		var moleculeListStr = "";
	    		var m = 0;
	    		var atomCount = "?";
	    		var solventCount = "?";
	    		if (experiment.molecularSystem!=null)
	    		{
	    			atomCount = experiment.molecularSystem.atomCount;
	    			solventCount= experiment.molecularSystem.solventCount;
	    			
		    		var moleculeList = experiment.molecularSystem.moleculeTypes;
		    		if (moleculeList != null && moleculeList.length>0){
		    			for (m=0; m<moleculeList.length; m++){
		    				moleculeListStr += moleculeList[m] + " / ";
		    			}
		    			moleculeListStr = moleculeListStr.substring(0,moleculeListStr.length-3);
		    		}
	    		}	    		
				html += "	<td style='text-align:right'>"+ atomCount + "</td>";
				html += "	<td style='text-align:right'>"+ solventCount + "</td>";
				html += "	<td style='text-align:center'>"+ moleculeListStr + "</td>";
			}
			html += "	<td style='text-align:center'><a class='link' href='user.do?name="+ experiment.fileCollection.owner + "'>"+ experiment.fileCollection.owner +"</a></td>";
		    html += "	<td style='text-align:center'>" + experiment.registrationDate + "</td>";
		    html += "</tr>";
		}
		else {
			alert('Experiment '+title+' is not associated to any collection!');
		}
		i++;
	}
	
	// possible actions on results (e.g. add to cart)
	html += "</table>";
	html += "<table>";
	html += "<tr>";
	html += "  	<td>";
	html += "  		<select name='action'>";
	html += "  			<option value='addtocart'>Add to shopping cart</option>";
	html += "  		</select>&nbsp;";
	html += "  		<input type='button' class='button' value=' Go ' onclick=\"addItemsToCart('searchResultsForm')\" />";
	html += "  	</td>";
	html += "</tr>";
	html += "</table>";
	
	html += "</form>";
	
	return html;
}

/**
 * Search files
 * @param numberOfRowsRequested
 * @param searchType
 * @param formId
 * @param continueIndex
 */
function executeFileSearch(numberOfRowsRequested, formId, continueIndex)
{
	var mySearchURL = searchFilesURL;
	if (continueIndex == 0){
		var formString = $("#"+formId).serialize();
		mySearchURL += "?" + formString;
	}

	initSearchResultPage();
	
	$("#tabs").tabs( "enable", 6);
	$("#tabs").tabs( "option", "active", 6 );
	
	if (searchRequest != null){
		searchRequest.abort();
	}
	searchRequest = $.ajax({
	    url: mySearchURL,
	    dataType: 'json',
	    data: {
	    	"numberOfRows" : numberOfRowsRequested,
	    	"continueIndex" : continueIndex
	    },
	    success: function(obj){
	    	if (obj.success){
		    	var data = obj.data;
		    	var html = "";
		    	if (data != null && data.length>0)
		    	{
		    		html = buildFileSearchResultsHtml(data);
			    	$("#search-results").html(html);
		    	}
			    else {
			    	$("#search-results").html("<p>The search did not return any result.</p>");
			    }
	    	}
	    	else showErrorDialog(obj.message);
	    	$("#search-loading").hide();
	      },
	     error: function(jqXHR, textStatus, errorThrown) { 
		    if (!isPageBeingRefreshed && textStatus != 'abort'){	
		    	$("#search-loading").hide();
		    	showErrorDialog("Error occured while searching");
		    }
	    }
	});
}

function getCurrentFileSearchResults()
{
	initSearchResultPage();
	
	if (searchRequest != null){
		searchRequest.abort();
	}
	searchRequest = $.ajax({
	    url: currentFileSearchResultsURL,
	    dataType: 'json',
	    success: function(obj){
	    	if (obj.success){
		    	var data = obj.data;
		    	var html = "";
		    	if (data != null && data.length>0)
		    	{
		    		html = buildFileSearchResultsHtml(data, "search-results");
			    	$("#search-results").html(html);
		    	}
			    else {
			    	$("#search-results").html("<p>The search did not return any result.</p>");
			    }
	    	}
	    	else showErrorDialog(obj.message);
		    $("#search-loading").hide();
	      },
	    error: function(jqXHR, textStatus, errorThrown) { 
	    	if (!isPageBeingRefreshed && textStatus != 'abort'){		
		    	$("#search-loading").hide();
		    	showErrorDialog("Error occured while searching");
	    	}
	    }
	});
}

function getNextFileSearchResults()
{
	initSearchResultPage();
	
	if (searchRequest != null){
		searchRequest.abort();
	}
	searchRequest = $.ajax({
	    url: nextFileSearchResultsURL,
	    dataType: 'json',
	    success: function(obj){
	    	if (obj.success){
		    	var data = obj.data;
		    	var html = "";
		    	if (data != null && data.length>0)
		    	{
		    		html = buildFileSearchResultsHtml(data, "search-results");
			    	$("#search-results").html(html);
		    	}
			    else {
			    	$("#search-results").html("<p>The search did not return any result.</p>");
			    }
	    	}
	    	else showErrorDialog(obj.message);
	    	$("#search-loading").hide();
	      },
	      error: function(jqXHR, textStatus, errorThrown) { 
		    if (!isPageBeingRefreshed && textStatus != 'abort'){
		    	$("#search-loading").hide();
		    	showErrorDialog("Error occured while searching");
		    }
	    }
	});
}

function getPreviousFileSearchResults()
{
	initSearchResultPage();
	
	if (searchRequest != null){
		searchRequest.abort();
	}
	searchRequest = $.ajax({
	    url: previousFileSearchResultsURL,
	    dataType: 'json',
	    success: function(obj){
	    	if (obj.success){
		    	var data = obj.data;
		    	var html = "";
		    	
		    	if (data != null && data.length>0)
		    	{
		    		html = buildFileSearchResultsHtml(data);
			    	$("#search-results").html(html);
		    	}
			    else {
			    	$("#search-results").html("<p>The search did not return any result.</p>");
			    }
		    	$("#search-loading").hide();
	    	}
	    	else showErrorDialog(obj.message);
	      },
	      error: function(jqXHR, textStatus, errorThrown) { 
		    if (!isPageBeingRefreshed && textStatus != 'abort'){
		    	$("#search-loading").hide();
		    	showErrorDialog("Error occured while searching");
		    }
	    }
	});
}

/**
 * Build html to present file search results
 * @param obj Search result object
 * @returns {String}
 */
function buildFileSearchResultsHtml(obj)
{		
	var i = 0;
	var html = "";
	
	// create links for pages
	createPagingLinksFiles(obj.length);
	
	// result table
	html += "<form id='searchResultsForm' class='blank'>";
	html += "<table><tr>";
	html += "	<th class='first'></th>";
	html += "	<th>Name</th>";
	html += "	<th>Format</th>";
		html += "	<th>Size</th>";
	html += "	<th>Uploaded</th>";
	html += "	<th>Details</th>";
		html += "</tr>";
	
	while( i < obj.length )
	{
		var fileInfo = obj[i];
		var fileSize = parseInt(fileInfo.size);
		var fileSizeStr = getFriendlyFileSize(fileSize);
			
		var fileFormat = fileInfo.format;
		
		var rowClass = "row-a";
		if (i % 2 == 1)
			rowClass = "row-b";

		html += "<tr class='"+ rowClass + "'>";
		html += "	<td class='first'>";
		html += "		<input name='uri' type='checkbox' value='" + fileInfo.absolutePath + "' />";
		html += "	</td>";
		html += "	<td>" + fileInfo.name + "</td>";
		html += "	<td>"+ fileInfo.format + "</td>";
		html += "	<td style='text-align:right'>"+ fileSizeStr + "</td>";
		//html += "	<td><a class='link' href='user.do?name="+ fileInfo.owner + "'>"+ fileInfo.owner +"</a></td>";
		html += "	<td style='text-align:right'>"+ fileInfo.registrationDate + "</td>";
		html += "	<td style='text-align:right'>";
		
		if (fileSize < MAX_FILE_SIZE)
		{
			if (isJmolFormat(fileFormat)){
				html += "<a href='jmol.do?uri=" + fileInfo.absolutePath + "' target='_blank'>";
				html += "<img class='icon' src='images/icons/mol.png' title='display 3D structure...'/></a>";
			}
			else if (fileFormat == "CSV" ){
				html += "<a href='file.do?uri=" + fileInfo.absolutePath + "'>";
				html += "<img class='icon' src='images/icons/chart.png' title='plot graph...'/></a>";
			}
		}
		html += "<a href='"+downloadFileURL+"?uri=" + fileInfo.absolutePath + "' target='_blank'>";
		html += "<img class='icon' src='images/icons/download.png' title='download file...'/></a>";
		
		html += "<a href='collection.do?uri=" + fileInfo.parent + "'>";
		html += "<img class='icon' src='images/icons/folder_full.png' title='go to experiment ("+fileInfo.parent+")...'></a>";
		
		html += "	</td>";
	    html += "</tr>";
		i++;
	}
	
	// possible actions on results (e.g. add to cart)
	html += "</table>";
	html += "<table>";
	html += "<tr>";
	html += "  	<td>";
	html += "  		<select name='action'>";
	html += "  			<option value='addtocart'>Add to shopping cart</option>";
	html += "  		</select>&nbsp;";
	html += "  		<input type='button' class='button' value=' Go ' onclick=\"addItemsToCart('searchResultsForm')\" />";
	html += "  	</td>";
	html += "</tr>";
	html += "</table>";
	
	html += "</form>";
	
	return html; 
}

/**
 * Search sets
 * @param numberOfRowsRequested
 * @param searchType
 * @param formId
 * @param continueIndex
 */
function executeSetSearch(numberOfRowsRequested, formId, continueIndex)
{
	var mySearchURL = searchSetsURL;
	if (continueIndex == 0){
		var formString = $("#"+formId).serialize();
		mySearchURL += "?" + formString;
	}
	initSearchResultPage();
	
	$("#tabs").tabs( "enable", 6);
	$("#tabs").tabs( "option", "active", 6 );
	
	if (searchRequest != null){
		searchRequest.abort();
	}
	searchRequest = $.ajax({
	    url: mySearchURL,
	    dataType: 'json',
	    data: {
	    	"numberOfRows" : numberOfRowsRequested,
	    	"continueIndex" : continueIndex
	    },
	    success: function(obj){
	    	if (obj.success){
		    	var data = obj.data;
		    	var html = "";
		    	if (data != null && data.length>0)
		    	{
		    		html = buildSetSearchResultsHtml(data);
			    	$("#search-results").html(html);
		    	}
			    else {
			    	$("#search-results").html("<p>The search did not return any result.</p>");
			    }
	    	}
	    	else showErrorDialog(obj.message);
	    	$("#search-loading").hide();
	      },
	     error: function(jqXHR, textStatus, errorThrown) { 
		    if (!isPageBeingRefreshed && textStatus != 'abort'){
		    	$("#search-loading").hide();
		    	showErrorDialog("Error occured while searching");
		    }
	    }
	});
}

function getCurrentSetSearchResults()
{
	initSearchResultPage();
	
	if (searchRequest != null){
		searchRequest.abort();
	}
	searchRequest = $.ajax({
	    url: currentSetSearchResultsURL,
	    dataType: 'json',
	    success: function(obj){
	    	if (obj.success){
		    	var data = obj.data;
		    	var html = "";
		    	if (data != null && data.length>0)
		    	{
		    		html = buildSetSearchResultsHtml(data, "search-results");
			    	$("#search-results").html(html);
		    	}
			    else {
			    	$("#search-results").html("<p>The search did not return any result.</p>");
			    }
	    	}
	    	else showErrorDialog(obj.message);
		    $("#search-loading").hide();
	      },
	    error: function(jqXHR, textStatus, errorThrown) { 
	    	if (!isPageBeingRefreshed && textStatus != 'abort'){		
		    	$("#search-loading").hide();
		    	showErrorDialog("Error occured while searching");
	    	}
	    }
	});
}

function getNextSetSearchResults()
{
	initSearchResultPage();
	
	if (searchRequest != null){
		searchRequest.abort();
	}
	searchRequest = $.ajax({
	    url: nextSetSearchResultsURL,
	    dataType: 'json',
	    success: function(obj){
	    	if (obj.success){
		    	var data = obj.data;
		    	var html = "";
		    	if (data != null && data.length>0)
		    	{
		    		html = buildSetSearchResultsHtml(data, "search-results");
			    	$("#search-results").html(html);
		    	}
			    else {
			    	$("#search-results").html("<p>The search did not return any result.</p>");
			    }
	    	}
	    	else showErrorDialog(obj.message);
	    	$("#search-loading").hide();
	      },
	      error: function(jqXHR, textStatus, errorThrown) { 
		    if (!isPageBeingRefreshed && textStatus != 'abort'){
		    	$("#search-loading").hide();
		    	showErrorDialog("Error occured while searching");
		    }
	    }
	});
}

function getPreviousSetSearchResults()
{
	initSearchResultPage();
	
	if (searchRequest != null){
		searchRequest.abort();
	}
	searchRequest = $.ajax({
	    url: previousSetSearchResultsURL,
	    dataType: 'json',
	    success: function(obj){
	    	if (obj.success){
		    	var data = obj.data;
		    	var html = "";
		    	
		    	if (data != null && data.length>0)
		    	{
		    		html = buildSetSearchResultsHtml(data);
			    	$("#search-results").html(html);
		    	}
			    else {
			    	$("#search-results").html("<p>The search did not return any result.</p>");
			    }
		    	$("#search-loading").hide();
	    	}
	    	else showErrorDialog(obj.message);
	      },
	      error: function(jqXHR, textStatus, errorThrown) { 
		    if (!isPageBeingRefreshed && textStatus != 'abort'){
		    	$("#search-loading").hide();
		    	showErrorDialog("Error occured while searching");
		    }
	    }
	});
}

/**
 * Build html to present experiment set search results
 * @param obj Search result object
 * @returns {String}
 */
function buildSetSearchResultsHtml(obj)
{		
	var i = 0;
	var html = "";
	
	// create links for pages
	createPagingLinksSets(obj.length);
	
	html += "<form id='searchResultsForm' class='blank'>";
	html += "<table><tr>";
	html += "	<th class='first'>Set</th>";
	html += "	<th>Description</th>";
	html += "	<th>Created</th>";
	html += "	<th>Owner</th>";
	html += "</tr>";
	
	while( i < obj.length )
	{
		var set = obj[i];
		
		var rowClass = "row-a";
		if (i % 2 == 1)
			rowClass = "row-b";

		html += "<tr class='"+ rowClass + "'>";
		html += "	<td class='first'>";
		html += "		<a href='experimentset.do?id=" + set.id + "' class='link'>"+ set.name + "<a/>";
		html += "	</td>";
		html += "	<td><i>"+ set.description + "</i></td>";
		html += "	<td style='text-align:center'>"+ getFriendlyDateShort(set.registrationDate) + "</td>";
		html += "	<td style='text-align:center'><a class='link' href='user.do?name="+ set.owner + "'>"+ set.owner +"</a></td>";
		
	    html += "</tr>";
		i++;
	}
	html += "</table>";
	html += "</form>";
	
	return html; 
}

/**
 * create links to change result pages
 */
function createPagingLinksExperiments(numberOfRows, searchType)
{
	if (searchPageLinkRequest != null){
		searchPageLinkRequest.abort();
	}
	searchPageLinkRequest = $.ajax({
	    url: searchStatusURL,
	    dataType: 'json',
	    success: function(obj)
	    {
	    	if (obj.success){
		    	var status = obj.data;
		    	var count = status.totalNumberOfResults;
		    	var continueIndex = status.continueIndex;
		    	var hasMore = status.hasMoreResults;
		    				
				var html = "<p>Displaying results ";
				html += 	"<strong><span style='color:#65944A'>"+(continueIndex + 1)+" - "+ (continueIndex +numberOfRows) +"</span>&nbsp;/&nbsp;"+count+"</strong>&nbsp;&nbsp;&nbsp;";
				if (continueIndex!=0){
					html += "<input type='button' class='button' onclick=\"getPreviousExperimentSearchResults('"+searchType+"')\" value='&lt; previous page' />";
				}
				else html += "<input type='button' class='button-disabled' value=' &lt; previous page ' disabled='disabled' />";
				
				if (hasMore){
					html += "&nbsp;&nbsp;<input type='button' class='button' onclick=\"getNextExperimentSearchResults('"+searchType+"')\" value='next page &gt;' /></p>";
				}
				else html += "&nbsp;&nbsp;<input type='button' class='button-disabled' value=' next page &gt; ' disabled='disabled' /></p>";
	
				html += "<p><a class='link pointer' title='clear current search results' onclick='clearSearches()'>Clear</a></p>";
				$("#search-pages-links").html(html); 
	    	} 
	    	else showErrorDialog(obj.message);
	    },
	    error: function(jqXHR, textStatus, errorThrown) { 
	    	if (!isPageBeingRefreshed && textStatus != 'abort')	
		    	showErrorDialog("Error occured while retrieving the number of pages");
	    }
	});
}

/**
 * create links to change result pages
 */
function createPagingLinksFiles(numberOfRows)
{
	if (searchPageLinkRequest != null){
		searchPageLinkRequest.abort();
	}
	searchPageLinkRequest = $.ajax({
	    url: searchStatusURL,
	    dataType: 'json',
	    success: function(obj)
	    {
	    	if (obj.success){
		    	var status = obj.data;
		    	var count = status.totalNumberOfResults;
		    	var continueIndex = status.continueIndex;
		    	var hasMore = status.hasMoreResults;
		    	
				var html = "<p>Displaying results ";
				html += 	"<strong><span style='color:#65944A'>"+(continueIndex + 1)+" - "+ (continueIndex +numberOfRows) +"</span>&nbsp;/&nbsp;"+count+"</strong>&nbsp;&nbsp;&nbsp;";
				if (continueIndex!=0){
					html += "<input type='button' class='button' onclick=\"getPreviousFileSearchResults()\" value='&lt; previous page' />";
				}
				else html += "<input type='button' class='button-disabled' value=' &lt; previous page ' disabled='disabled' />";
				
				if (hasMore){
					html += "&nbsp;&nbsp;<input type='button' class='button' onclick=\"getNextFileSearchResults()\" value='next page &gt;' /></p>";
				}
				else html += "&nbsp;&nbsp;<input type='button' class='button-disabled' value=' next page &gt; ' disabled='disabled' /></p>";
				
				html += "<p><a class='link pointer' title='clear current search results' onclick='clearSearches()'>Clear</a></p>";
				
				$("#search-pages-links").html(html); 
	    	}
	    	else showErrorDialog(obj.message);
	    },
	    error: function(jqXHR, textStatus, errorThrown) { 
	    	if (!isPageBeingRefreshed && textStatus != 'abort')
	    		showErrorDialog("Error occured while retrieving the number of pages");
	    }
	});
}

/**
 * create links to change result pages
 */
function createPagingLinksSets(numberOfRows)
{
	if (searchPageLinkRequest != null){
		searchPageLinkRequest.abort();
	}
	searchPageLinkRequest = $.ajax({
	    url: searchStatusURL,
	    dataType: 'json',
	    success: function(obj)
	    {
	    	if (obj.success){
		    	var status = obj.data;
		    	var count = status.totalNumberOfResults;
		    	var continueIndex = status.continueIndex;
		    	var hasMore = status.hasMoreResults;
		    	
				var html = "<p>Displaying results ";
				html += 	"<strong><span style='color:#65944A'>"+(continueIndex + 1)+" - "+ (continueIndex +numberOfRows) +"</span>&nbsp;/&nbsp;"+count+"</strong>&nbsp;&nbsp;&nbsp;";
				if (continueIndex!=0){
					html += "<input type='button' class='button' onclick=\"getPreviousFileSearchResults()\" value='&lt; previous page' />";
				}
				else html += "<input type='button' class='button-disabled' value=' &lt; previous page ' disabled='disabled' />";
				
				if (hasMore){
					html += "&nbsp;&nbsp;<input type='button' class='button' onclick=\"getNextFileSearchResults()\" value='next page &gt;' /></p>";
				}
				else html += "&nbsp;&nbsp;<input type='button' class='button-disabled' value=' next page &gt; ' disabled='disabled' /></p>";
				
				html += "<p><a class='link pointer' title='clear current search results' onclick='clearSearches()'>Clear</a></p>";
				
				$("#search-pages-links").html(html); 
	    	}
	    	else showErrorDialog(obj.message);
	    },
	    error: function(jqXHR, textStatus, errorThrown) { 
	    	if (!isPageBeingRefreshed && textStatus != 'abort')
	    		showErrorDialog("Error occured while retrieving the number of pages");
	    }
	});
}

/**
 * Load current search results (from session information)
 */
function loadPreviousSearchResults(){
	
	$.ajax({
	    url: searchStatusURL,
	    dataType: 'json',
	    success: function(obj)
	    {
	    	if (obj.success){
		    	var searchStatus = obj.data;
		    	if (searchStatus != null)
		    	{
			    	$("#tabs").tabs( "enable", 6);
			    	$("#tabs").tabs( "option", "active", 6);
			    	
			    	if (searchStatus.searchType != null && searchStatus.searchType == 'file'){
			    		getCurrentFileSearchResults();
			    	}
			    	else if (searchStatus.searchType != null && searchStatus.searchType == 'set'){
			    		getCurrentSetSearchResults();
			    	}
			    	else {
			    		getCurrentExperimentSearchResults(searchStatus.searchType);
			    	}
		    	}
		    	else {
			    	$("#tabs").tabs( "option", "active", 0);
		    		$("#tabs").tabs( "disable", 6);
		    	}
	    	}
		    else showErrorDialog(obj.message);
	    },
	    error: function(jqXHR, textStatus, errorThrown) { 
	    	if (!isPageBeingRefreshed && textStatus != 'abort')
	    		showErrorDialog("Error occured while retrieving the last search results");
	    }
	});
}

/**
 * Add search criteria to custom search form
 * @param tableID
 * @param currentIdInputId
 */
function addSearchCriteria(tableID, currentIdInputId) {
	
	var newId = $("#"+currentIdInputId).val();
	var attrId = "attr-list"+newId;
	var valId = "val-list"+newId;
	var rowId = "row"+newId;
	
	var rowHtml = "<tr id='"+rowId+"'>";
	rowHtml += "<td><a style='cursor:pointer' onclick='removeSearchCriteria(\""+rowId+"\")' title='Remove criteria'>";
	rowHtml += "		<img class='icon' src='images/icons/delete_small.png'/>";
	rowHtml += "	</a></td>";
	rowHtml += "<td><div id='"+attrId+"'></div></td>";
	rowHtml += "<td>";
	rowHtml += "	<select name='op"+newId+"' style='width:60px'>";
	rowHtml += "	  <option value='EQUAL'>=</option>";
	rowHtml += "	  <option value='NUM_GREATER_OR_EQUAL'>&gt;=</option>";
	rowHtml += "	  <option value='NUM_LESS_OR_EQUAL'>&lt;=</option>";
	rowHtml += "	  <option value='NUM_LESS_THAN'>&lt;</option>";
	rowHtml += "	  <option value='LIKE'>like</option>";
	rowHtml += "    </select>";
	rowHtml += "</td>";
	rowHtml += "<td>";
	rowHtml += "    <div id='"+valId+"'></div>";
	rowHtml += "</td>";
	rowHtml += "</tr>";

	var rowCount = $("#"+tableID+" tr").length;
	if (rowCount>0)
		$("#"+tableID+" tr:last").after(rowHtml);
	else $("#"+tableID).append(rowHtml);
	
	populateMetadataAttributes(newId);

	$("#"+currentIdInputId).val((parseInt(newId) + 1));
}
/**
 * Add new search criteria with free input (no metadata list loaded)
 * @param tableID
 * @param currentIdInputId
 */
function addFreeSearchCriteria(tableID, currentIdInputId) {
	
	var newId = $("#"+currentIdInputId).val();
	var attrId = "attr-list"+newId;
	var valId = "val-list"+newId;
	var rowId = "row"+newId;
	
	var rowHtml = "<tr id='"+rowId+"'>";
	rowHtml += "<td><a style='cursor:pointer' onclick='removeSearchCriteria(\""+rowId+"\")' title='Remove criteria'>";
	rowHtml += "		<img class='icon' src='images/icons/delete_small.png'/>";
	rowHtml += "	</a></td>";
	rowHtml += "<td><div id='"+attrId+"'><input type='text' id='attr"+newId+"' name='attr"+newId+"' style='width:200px'></div></td>";
	rowHtml += "<td>";
	rowHtml += "	<select name='op"+newId+"' style='width:60px'>";
	rowHtml += "	  <option value='EQUAL'>=</option>";
	rowHtml += "	  <option value='NUM_GREATER_OR_EQUAL'>&gt;=</option>";
	rowHtml += "	  <option value='NUM_LESS_OR_EQUAL'>&lt;=</option>";
	rowHtml += "	  <option value='NUM_LESS_THAN'>&lt;</option>";
	rowHtml += "	  <option value='LIKE'>like</option>";
	rowHtml += "    </select>";
	rowHtml += "</td>";
	rowHtml += "<td>";
	rowHtml += "    <div id='"+valId+"'><input type='text' id='val"+newId+"' name='val"+newId+"' style='width:200px'></div>";
	rowHtml += "</td>";
	rowHtml += "</tr>";

	$("#"+tableID+" tr:last").after(rowHtml);
	$("#"+currentIdInputId).val((parseInt(newId) + 1));
}

/**
 * Remove selected search criteria row
 * @param rowId
 */
function removeSearchCriteria(rowId) {
	$("#"+rowId).remove();
}

/**
 * Load catalog of metadata into dropdownlist
 * @param attributeDiv
 * @param valueDiv
 */
function populateMetadataAttributes(id){

	var attrDivId = "attr-list"+id;
	var attrValId = "attr"+id;
		
	$.ajax({
	    url: metadataCatalogURL,
	    dataType: 'json',
	    //data: data,
	    success: function(data){
	    	var html = "<select id='"+attrValId+"' name='"+attrValId+"' onchange=\"createInputForSelectedAttribute('"+id+"');\" style=\"width:200px\">";
	    	html += "<option value=''></option>";
	    	var i = 0;
	    	while( i < data.length )
	    	{
	    		var attr = data[i];
	    		html += "<option value='"+attr.code+"' title='"+attr.definition+"'>"+ attr.term + "</option>";
	    		i++;
	    	}
	    	html += "</select>";
	    	
	    	$("#"+attrDivId).html(html);
	      },
	      error: function(jqXHR, textStatus, errorThrown) { 
	    	if (!isPageBeingRefreshed && textStatus != 'abort')
	    		showErrorDialog("Error: could not load the metadata catalog");
	    }
	  });
}

/**
 * Get metadata values allowed for a given attribute (select)
 */
function createInputForSelectedAttribute(id) 
{
	var valDivId = "val-list"+id;
	var attrValId = "attr"+id;
	var valValId = "val"+id;
	
	var code = $("#"+attrValId).val();

	if (code.length>0)
	{
		var mdValuesUrl =  metadataValuesURL.replace("$",code);
		
	    $.ajax({
	    url: mdValuesUrl,
	    dataType: 'json',
	    success: function(data){
	    	var html = "";
	    	if (data != null && data.length>0){
		    	html = "<select style='width:200px' id='"+valValId+"' name='"+valValId+"' >";
		    	var i = 0;
		    	while( i < data.length )
		    	{
		    		var val = data[i];
		    		if (i==0)
		    			html += "<option value='"+val.term+"' title='"+val.definition+"' selected='selected'>"+ val.term + "</option>";
		    		else
		    			html += "<option value='"+val.term+"' title='"+val.definition+"'>"+ val.term + "</option>";
		    		i++;
		    	}
		    	html += "</select>";
	    	}
	    	else{
	    		html = "<input type='text' id='"+valValId+"' name='"+valValId+"' style='width:200px'>";
	    	}
	    	$("#"+valDivId).html(html);
	      },
	      error: function(jqXHR, textStatus, errorThrown) { 
		    if (!isPageBeingRefreshed && textStatus != 'abort')
		    	showError("Error: could not load the value dictionary for '"+code+"'");
	    }
	  });
	}
}

/**
 * Clear previous search results
 */
function clearSearches()
{
	if (searchRequest != null){
		searchRequest.abort();
	}
	$.ajax({
	    url: clearSearchesURL,
	    dataType: 'json',
	    success: function(obj){
	    	if (obj.success){
		    	$("#search-loading").hide();
		    	$("#search-results").empty();
	
		    	$("#tabs").tabs( "option", "active", 0 );
		    	$("#tabs").tabs( "disable", 6 );
	    	}
	    	else showErrorDialog(obj.message);
	    },
	    error: function(jqXHR, textStatus, errorThrown) { 
	    	if (!isPageBeingRefreshed && textStatus != 'abort'){	
		    	$("#search-loading").hide();
		    	showErrorDialog("Error occured while searching");
	    	}
	    }
	});
}
