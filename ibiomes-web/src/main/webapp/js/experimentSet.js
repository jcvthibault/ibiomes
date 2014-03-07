
var getListOfExperimentSetsURL = contextPath + '/rest/services/experimentset/list';
var getExperimentSetURL = contextPath + '/rest/services/experimentset';
var getExperimentsInSetURL = contextPath + '/rest/services/experimentset/experiments';
var addExperimentToSetURL = contextPath + '/rest/services/experimentset/experiments/add';
var removeExperimentsFromSetURL = contextPath + '/rest/services/experimentset/experiments/remove';
var createExperimentSetURL = contextPath + '/rest/services/experimentset/create';
var updateExperimentSetURL = contextPath + '/rest/services/experimentset/update';
var deleteExperimentSetURL = contextPath + '/rest/services/experimentset/delete';

var getExperimentSetMetadataURL = contextPath + '/rest/services/experimentset/avus';
var addExperimentSetMetadataURL = contextPath + '/rest/services/experimentset/avus/add';
var removeExperimentSetMetadataURL = contextPath + '/rest/services/experimentset/avus/remove';

var getExperimentSetAnalysisFilesAvailableURL = contextPath + '/rest/services/experimentset/analysis/available';
var getExperimentSetAnalysisFilesSelectedURL = contextPath + '/rest/services/experimentset/analysis';
var addExperimentSetAnalysisFileURL = contextPath + '/rest/services/experimentset/analysis/add';
var removeExperimentSetAnalysisFileURL = contextPath + '/rest/services/experimentset/analysis/remove';

var getExperimentSetImagesURL = contextPath + '/rest/services/thumbnail/experimentset/analysis';
var getExperimentSetAnalysisLinksURL = contextPath + '/rest/services/experimentset/analysis/get/links';

var copyMethodAvuToSetURL = contextPath + '/rest/services/experimentset/avus/method/copy';
var copyTopologyAvuToSetURL = contextPath + '/rest/services/experimentset/avus/topology/copy';
var clearExperimentSetAvusURL = contextPath + '/rest/services/experimentset/avus/clear';

/**
 * Get list of experiment sets
 */
function getListOfExperimentSets(divId) {
	
	showLoadingBarLarge(divId);
	
	$.ajax({
	    url: getListOfExperimentSetsURL,
	    dataType: 'json',
	    data: {	
	    },
	    success: function(obj)
	    {
	    	if (obj.success){
		    	var html = "";
		    	var data = obj.data;
		    	var i = 0;
		    	if (data != null && data.length > 0)
		    	{
		    		html = "<table>";
		        	html += "	<tr>";
		        	html += "		<th class='first'></th>";
		        	html += "		<th>Set</th>";
		        	html += "		<th>Description</th>";
		        	html += "		<th>Public</th>";
		        	html += "		<th>Owner</th>";
		        	html += "	</tr>";
		        	
			    	while( i < data.length ){
			    		var set = data[i];
			    		var rowClass = "row-a";
			    		if (i % 2 == 1)
			    			rowClass = "row-b";
			    		var publicStr = "No";
			    		if (set.isPublic)
			    			publicStr = "Yes";
			    		html += "<tr class='"+ rowClass + "'>";
			    		html += "	<td class='first'>";
			    		html += "		<input type='checkbox' name='expSetSelect' value='"+set.id+"' />";
			    		html += "	</td>";
			    		html += "	<td>";
			    		html += "		<a class='link' href='experimentset.do?id="+ set.id+ "' title='Go to experiment set...'>" + set.name + "</a>";
			    		html += "	</td>";
			    		html += "	<td><i>"+ set.description +"</i></td>";
			    		html += "	<td>" + publicStr + "</td>";
			    		html += "	<td><a class='link' href='user.do?name="+ set.owner + "'>"+ set.owner +"</a></td>";
			    		html += "</tr>";    		
	
			    		i++;
			    	}
			    	html += "</table>";
		    	}
		    	else html = "<p>No experiment set found.</p>";
		    	
		    	hideLoadingBar(divId);
		    	$("#"+divId).html(html);
	    	}
	    	else {
		    	hideLoadingBar(divId);
		    	showErrorDialog(obj.message);
	    	}
	    },
    	error: function(jqXHR, textStatus, errorThrown) { 
	    	hideLoadingBar(divId);
		    if (!isPageBeingRefreshed && textStatus != 'abort'){
		    	showErrorDialog("Server error: the list of experiment sets could not be retrieved");
		    }
	    }
    });
}

/**
 * Get experiment set by ID
 */
function getExperimentSet(id) {
	
	$("#noSelectedSet").empty();
	$("#selectedSet").show();
	
    $.ajax({
	    url: getExperimentSetURL,
	    dataType: 'json',
	    data : { "id" : id },
	    success: function(obj){
	    	if (obj.success){
	    		$("#selectedSetId").val(id);
	    		var set = obj.data;
		    	var html = "";
		    	$("#setName").html(set.name);
		    	if (set.description != null && set.description.length>0)
		    		$("#setDesc").html(set.description);
		    	var isPublic = "no";
		    	if (set.isPublic)
		    		isPublic = "yes";
		    	$("#setPublicFlag").html(isPublic);
		    			    	
		    	getExperimentsInSet(id);
	    	}
	    	else showErrorDialog("<p>"+obj.message+"</p>");
	      },
	    error: function() { 
	    	showErrorDialog("<p>The list of experiment sets could not be retrieved</p>");
	    }
    });
}

/**
 * Get experiment set metadata
 */
function loadExperimentSetMetadata(id, divId, canWrite) {

	showLoadingBarSmall(divId);
	
    $.ajax({
	    url: getExperimentSetMetadataURL,
	    dataType: 'json',
	    data : { "id" : id },
	    success: function(obj){
	    	if (obj.success){
	    		var avus = obj.data;
	    		if (avus!=null && avus.length>0){
	    			var html = "";
	    			var htmlStd = "<table class='layout'>";
	    			var htmlNonStd = "<table class='layout'>";
	    			var hasStd = false;
	    			var hasNonStd = false;
	    			for (var a=0; a<avus.length; a++){
	    				var avu = avus[a];
	    				var avuUnit = avu.unit;
	    				if (avuUnit==null)
	    					avuUnit = "";
	    				if (avu.attribute.standard){
	    					htmlStd += "<tr>";
	    					htmlStd += "<td><img src='images/icons/info_small.png' title='"+avu.attribute.definition+"'/></td>";
	    					htmlStd += "<td><strong>"+avu.attribute.term+"</strong></td>";
	    					htmlStd += "<td style='width:10px'/>";
	    					htmlStd += "<td>"+avu.value.term+"</td>";
	    					htmlStd += "<td style='width:10px'/>";
	    					htmlStd += "<td>"+avuUnit+"</td>";
	    					if (canWrite){
		    					htmlStd += "<td style='width:10px'/>";
	    						htmlStd += "<td>";
	    						htmlStd += "<input type='image' src='images/icons/delete_small.png' title='remove' onclick=\"deleteExperimentSetAVU("+id+",'"+avu.attribute.code+"','"+avu.value.code+"','"+avuUnit+"','"+divId+"')\" />";
	    						htmlStd += "</td>";
	    					}
	    					htmlStd += "</tr>";
	    					hasStd = true;
	    				}
	    				else {
	    					htmlNonStd += "<tr>";
	    					htmlNonStd += "<td><strong>"+avu.attribute.code+"</strong></td>";
	    					htmlNonStd += "<td style='width:10px'/>";
	    					htmlNonStd += "<td>"+avu.value.term+"</td>";
	    					htmlNonStd += "<td style='width:10px'/>";
	    					htmlNonStd += "<td>"+avuUnit+"</td>";
	    					if (canWrite){
		    					htmlNonStd += "<td style='width:10px'/>";
	    						htmlNonStd += "<td>";
	    						htmlNonStd += "<input type='image' src='images/icons/delete_small.png' title='remove' onclick=\"deleteExperimentSetAVU("+id+",'"+avu.attribute.code+"','"+avu.value.code+"','"+avuUnit+"','"+divId+"')\" />";
	    						htmlNonStd += "</td>";
	    					}
	    					htmlNonStd += "</tr>";
	    					hasNonStd = true;
	    				}
	    			}
	    			htmlStd += "</table>";
	    			htmlNonStd += "</table>";
	    			
	    			if (hasStd){
	    				html += "<h3>Standard metadata</h3>" + htmlStd;
	    			}
	    			if (hasNonStd){
	    				html += "<h3>Non-standard metadata</h3>" + htmlNonStd;
	    			}
	    			hideLoadingBar(divId);
	    			$("#"+divId).html(html);
	    		}
	    		else {
	    			hideLoadingBar(divId);
	    			$("#"+divId).html("<p>No metadata is currently associated with this experiment set</p>");
	    		}
	    	}
	    	else {
    			hideLoadingBar(divId);
    			showErrorDialog("<p>"+obj.message+"</p>");
	    	}
	      },
	    error: function() { 
			hideLoadingBar(divId);
	    	showErrorDialog("<p>The list of metadata could not be retrieved</p>");
	    }
    });
}


/**
 * Get list of experiments in set
 */
function getExperimentsInSet(id) {
    $.ajax({
    url: getExperimentsInSetURL,
    dataType: 'json',
    data : { "id" : id },
    success: function(obj){
    	if (obj.success)
    	{
    		var experiments = obj.data;
    		if (experiments!=null && experiments.length>0){
		    	var html = "<table style='margin-left:10px'><tr><th>Referenced experiments</th></tr>";
		    	var i = 0;
		    	while( i < experiments.length )
		    	{
		    		var experiment = experiments[i];
		    		var rowClass = "row-a";
		    		if (i % 2 == 1)
		    			rowClass = "row-b";
		    		html += "<tr class='"+ rowClass + "'>";
		    		html += "  <td class='first' style='width:280px'>";
		    		html += "     <img class='icon pointer' src='images/icons/delete_small.png' title='Remove experiment from set' onclick=\"removeExperimentFromSet("+id+",'"+experiment.absolutePath+"')\" />";
		    		html += "     <a class='link' title='"+experiment.description +"' href='collection.do?uri="+experiment.absolutePath+"'><img class='icon' src='images/icons/folder_full_small.png'/> "+ experiment.name + "</a>";
		    		html += "  </td>";
		    		html += "</tr>";
		    		i++;
		    	}
		    	html += "</table>";
		    	$("#selectedSetExperiments").html(html);
    		}
    		else $("#selectedSetExperiments").html("<p>No experiment avaialable in this set.</p>");
    	}
    	else $("#selectedSetExperiments").html("<p>"+obj.message+"</p>");
      },
    error: function() { 
    	$("#selectedSetExperiments").html("<p>The list of experiments could not be retrieved</p>");
    }
  });
}


/**
 * Remove experiment from set
 */
function removeExperimentFromSet(id, path){
	$.ajax({
	    url: removeExperimentsFromSetURL,
	    dataType: 'json',
	    data : { 
	    	"id" : id,
	    	"uris" : path
	    	},
	    success: function(obj){
	    	if (obj.success)
	    	{
	    		getExperimentsInSet(id);
	    	}
	    	else showErrorDialog(obj.message);
	      },
	    error: function() { 
	    	showErrorDialog("The experiment(s) could not be removed");
	    }
	});
}

/**
 * Add experiment to set
 */
function addExperimentToSet(id, path){
	$.ajax({
	    url: addExperimentToSetURL,
	    dataType: 'json',
	    data : { 
	    	"id" : id,
	    	"uri" : path
	    	},
	    success: function(obj){
	    	if (!obj.success)
	    		showErrorDialog(obj.message);
	    	else showInfoDialog(obj.message);
	      },
	    error: function() { 
	    	showErrorDialog("The experiment(s) could not be removed");
	    }
	});
}

function resetExperimentSetDetails(){
	$("#selectedSet").hide();
	$("#selectedSetExperiments").empty();
	$("#noSelectedSet").html("<p>Select a set from the list</p>");
}

/**
 * Create new experiment set
 * @param name
 * @param desc
 * @param publicFlag
 */
function createExperimentSet(name, desc, publicFlag){
	$.ajax({
	    url: createExperimentSetURL,
	    dataType: 'json',
	    data : { 
	    	"name" : name,
	    	"description" : desc,
	    	"isPublic" : publicFlag
	    	},
	    success: function(obj){
	    	if (!obj.success)
	    		showErrorDialog(obj.message);
	      },
	    error: function() { 
	    	showErrorDialog("The experiment set could not be created");
	    }
	});
}

/**
 * Update experiment
 * @param id
 * @param name
 * @param desc
 * @param publicFlag
 */
function updateExperimentSet(id, name, desc, publicFlag){
	$.ajax({
	    url: updateExperimentSetURL,
	    dataType: 'json',
	    data : { 
	    	"id" : id,
	    	"name" : name,
	    	"description" : desc,
	    	"isPublic" : publicFlag
	    	},
	    success: function(obj){
	    	if (!obj.success)
	    		showErrorDialog(obj.message);
	      },
	    error: function() { 
	    	showErrorDialog("The experiment set could not be updated");
	    }
	});
}

/**
 * Copy topology metadata from experiment to set
 */
function copyTopologyExperimentAvuToSet(id, uri, divId){
	$.ajax({
	    url: copyTopologyAvuToSetURL,
	    dataType: 'json',
	    data : { 
	    	"id" : id,
	    	"uri" : uri
	    	},
	    success: function(obj){
	    	if (obj.success)
	    	{
	    		loadExperimentSetMetadata(id, divId, true);
	    		showInfoDialog(obj.message);
	    	}
	    	else showErrorDialog(obj.message);
	      },
	    error: function() { 
	    	showErrorDialog("The experiment set could not be updated");
	    }
	});
}

/**
 * Copy method metadata from experiment to set
 */
function copyMethodExperimentAvuToSet(id, uri, divId){
	$.ajax({
	    url: copyMethodAvuToSetURL,
	    dataType: 'json',
	    data : { 
	    	"id" : id,
	    	"uri" : uri
	    	},
	    success: function(obj){
	    	if (obj.success)
	    	{
	    		loadExperimentSetMetadata(id, divId, true);
	    		showInfoDialog(obj.message);
	    	}
	    	else showErrorDialog(obj.message);
	      },
	    error: function() { 
	    	showErrorDialog("The experiment set could not be updated");
	    }
	});
}

/**
 * Add AVU to experiment set
 * @param setId
 * @param attribute
 * @param value
 * @param unit
 * @param divId
 */
function addExperimentSetAVU(setId, attributeId, valueId, unitId, divId){
	var attr = $("#" + attributeId).val();
	var value = $("#" + valueId).val();
	var unit = $("#" + unitId).val();
	$.ajax({
	    url: addExperimentSetMetadataURL,
	    dataType: 'json',
	    data : { 
	    	"id" : setId,
	    	"attribute" : attr,
	    	"value" : value,
	    	"unit" : unit
	    },
	    success: function(obj){
	    	if (obj.success){
	    		loadExperimentSetMetadata(setId, divId, true);
	    		$("#" + attributeId).val("");
	    		$("#" + valueId).val("");
	    		$("#" + unitId).val("");
	    		showInfoDialog("The experiment set metadata were successfully updated");
	    	}
	    	else showErrorDialog(obj.message);
	      },
	    error: function() { 
	    	showErrorDialog("The experiment set metadata could not be updated");
	    }
	});
	
}
/**
 * Delete experiment set AVU
 * @param setId
 * @param attribute
 * @param value
 * @param unit
 * @param divId
 */
function deleteExperimentSetAVU(setId, attribute, value, unit, divId){
	
	$.ajax({
	    url: removeExperimentSetMetadataURL,
	    dataType: 'json',
	    data : { 
	    	"id" : setId,
	    	"attribute" : attribute,
	    	"value" : value,
	    	"unit" : unit
	    },
	    success: function(obj){
	    	if (obj.success){
	    		loadExperimentSetMetadata(setId, divId, true);
	    	}
	    	else showErrorDialog(obj.message);
	      },
	    error: function() { 
	    	showErrorDialog("The experiment set AVU could not be removed");
	    }
	});
}

/**
 * Clear experiment set metadata
 */
function clearExperimentSetAvus(id, divId){
	$.ajax({
	    url: clearExperimentSetAvusURL,
	    dataType: 'json',
	    data : { 
	    	"id" : id
	    	},
	    success: function(obj){
	    	if (obj.success)
	    	{
	    		loadExperimentSetMetadata(id, divId, true);
	    		showInfoDialog(obj.message);
	    	}
	    	else showErrorDialog(obj.message);
	      },
	    error: function() { 
	    	showErrorDialog("The experiment set could not be updated");
	    }
	});
}
/**
 * Delete experiment sets
 * @param ids 
 */
function deleteExperimentSets(ids){
	$.ajax({
	    url: deleteExperimentSetURL,
	    dataType: 'json',
	    data : { 
	    	"ids" : ids
	    },
	    success: function(obj){
	    	if (obj.success){
	    		getListOfExperimentSets();
	    	}
	    	else showErrorDialog(obj.message);
	      },
	    error: function() { 
	    	showErrorDialog("The experiment set(s) could not be deleted");
	    }
	});
}


/**
 * 
 * @param uri
 * @param divId
 */
function loadSelectedAnalysisFileForSet(id, divId){
	
	$.ajax({
	    url: getExperimentSetAnalysisFilesSelectedURL,
	    dataType: 'json',
	    data: {	"id" : id },
	    success: function(obj)
	    {
			if (obj.success){
		    	var html = "";
		    	var data = obj.data;
		    	var i = 0;
		    	if (data!=null && data.length > 0)
		    	{
					while( i < data.length ){
						html += '<option value="'+data[i]+'" title="'+data[i]+'">'+data[i]+'</option>';
						i++;
				    }
		    	}
		    	$("#"+divId).html(html);
		    }
	    	else {
	    		showErrorDialog(obj.message);
	    	}
	    },
	    error: function(jqXHR, textStatus, errorThrown) { 
	    	hideLoadingBar(divId);
		    if (!isPageBeingRefreshed && textStatus != 'abort'){
		    	showErrorDialog("Server error: the list of analysis files could not be retrieved");
		    }
	    }
    });
}

/**
 * Load list of analysis files available for add
 * @param uri
 * @param divId
 */
function loadAvailableAnalysisFileForSet(id, divId){
	
	$.ajax({
	    url: getExperimentSetAnalysisFilesAvailableURL,
	    dataType: 'json',
	    data: {	"id" : id },
	    success: function(obj)
	    {
			if (obj.success){
		    	var html = "";
		    	var data = obj.data;
		    	var i = 0;
		    	if (data!=null && data.length > 0)
		    	{
					while( i < data.length ){
						html += '<option value="'+data[i]+'" title="'+data[i]+'">'+data[i]+'</option>';
						i++;
				    }
		    	}
		    	$("#"+divId).html(html);
		    }
	    	else {
	    		showErrorDialog(obj.message);
	    	}
	    },
	    error: function(jqXHR, textStatus, errorThrown) { 
	    	hideLoadingBar(divId);
		    if (!isPageBeingRefreshed && textStatus != 'abort'){
		    	showErrorDialog("Server error: the list of available analysis files could not be retrieved");
		    }
	    }
   });
}

/**
 * Load list of analysis files referenced by this set
 * @param uri
 * @param divId
 */
function loadThumbnailsForAnalysisInSet(id, divId){
	
	showLoadingBarSmall(divId);
	
	$.ajax({
	    url: getExperimentSetImagesURL,
	    dataType: 'json',
	    data: {	"id" : id },
	    success: function(obj)
	    {
			if (obj.success){
		    	var html = "";
		    	var data = obj.data;
		    	var i = 0;
		    	if (data!=null && data.length > 0)
		    	{
					while( i < data.length )
				    {
						var image = data[i];
						html += '<a class="link" href="rest/services/download?uri='+image.irodsDataURI+'" target="_blank">';
						html += '    <img src="'+image.thumbnailUrl+'" title="'+image.description+'" style="height:100px;border:1px solid #DADADA"/>';
						html += '</a>';
						i++;
				    }
		    	}
		    	else html = "<p>No image available</p>";

		    	hideLoadingBar(divId);
		    	$("#"+divId).html(html);
		    }
	    	else {
	    		hideLoadingBar(divId);
	    		showErrorDialog(obj.message);
	    	}
	    },
	    error: function(jqXHR, textStatus, errorThrown) { 
	    	hideLoadingBar(divId);
		    if (!isPageBeingRefreshed && textStatus != 'abort'){
		    	$("#"+divId).html("<p>No image available</p>");
		    	showErrorDialog("Server error: the list of images could not be retrieved");
		    }
	    }
    });
}

/**
 * Load list of analysis files for this set
 * @param setId Experiment set ID
 * @param divId
 */
function loadLinksForAnalysisInSet(setId, divId){
	
	showLoadingBarSmall(divId);

	$.ajax({
	    url: getExperimentSetAnalysisLinksURL,
	    dataType: 'json',
	    data: {	"id" : setId },
	    success: function(obj)
	    {
			if (obj.success){
		    	var html = "";
		    	var data = obj.data;
		    	var i = 0;
		    	if (data!=null && data.length > 0)
		    	{
		    		html += '<table class="layout-tight">';
		    		while( i < data.length ){
						var link = data[i];
						html += "<tr><td>";
						if (isJmolFormat(link.format)){
							html += "<img class='icon' src='images/icons/mol.png' title='Display 3D structure'/>";
						}
						else if (link.format == "CSV" ){
							html += "<img class='icon' src='images/icons/chart.png' title='Plot graph...'/>";
						}
						else if (isImageFormat(link.format)) {
							html += "<img class='icon' src='images/icons/image.png' title='View image...'/>";
						}
						else if (link.format == "PDF"){
							html += "<img class='icon' src='images/icons/pdf_small.png' title='Download PDF document...'/>";
						}
						else {
							html += "<img class='icon' src='images/icons/full_page.png' title='View file details...'/>";
						}
						html += '</td><td><a class="link" href="file.do?uri='+link.irodsDataURI+'" title="'+link.name+'">';
						html += link.description;
						html += '</a></td></tr>';
						i++;
				    }
		    		html += '</table>';
		    	}
		    	else html = "<p>No link available</p>";

		    	hideLoadingBar(divId);
		    	$("#"+divId).html(html);
		    }
	    	else {
	    		hideLoadingBar(divId);
	    		showErrorDialog(obj.message);
	    	}
	    },
	    error: function(jqXHR, textStatus, errorThrown) { 
	    	hideLoadingBar(divId);
		    if (!isPageBeingRefreshed && textStatus != 'abort'){
		    	$("#"+divId).html("<p>No link available</p>");
		    	showErrorDialog("Server error: the list of images could not be retrieved");
		    }
	    }
    });
}

/**
 * Add analysis file reference to experiment set
 * @param setId Experiment set ID
 * @param uri File URI
 */
function addAnalysisFileReferenceToSet(setId, fileUris){
	$.ajax({
	    url: addExperimentSetAnalysisFileURL,
	    dataType: 'json',
	    data : {
	    	"id" : setId,
	    	"files" : fileUris.join(",")
	    },
	    success: function(obj){
	    	if (obj.success)
	    	{
				loadSelectedAnalysisFileForSet(setId, 'selectedAnalysisFiles');
				loadAvailableAnalysisFileForSet(setId, 'availableAnalysisFiles');
				
	    		loadThumbnailsForAnalysisInSet(setId, 'analysisImages');
	    		loadLinksForAnalysisInSet(setId, 'analysisLinks');
	    		
	    		showInfoDialog("The reference was successfully added.");
	    	}
	    	else showErrorDialog(obj.message);
	      },
	      error: function(jqXHR, textStatus, errorThrown) {
	    	  if (!isPageBeingRefreshed && textStatus != 'abort'){
	    		  showErrorDialog("Service unavailable: " + addExperimentSetAnalysisFileURL + " (" + errorThrown +")");
	    	  }
	      }
	});
	
}
/**
 * Remove analysis file reference from experiment set
 * @param setId Experiment set ID
 * @param uri File URI
 */
function removeAnalysisFileReferenceFromSet(setId, fileUris){
	$.ajax({
	    url: removeExperimentSetAnalysisFileURL,
	    dataType: 'json',
	    data : {
	    	"id" : setId,
	    	"files" : fileUris.join(",")
	    },
	    success: function(obj){
	    	if (obj.success)
	    	{
				loadSelectedAnalysisFileForSet(setId, 'selectedAnalysisFiles');
				loadAvailableAnalysisFileForSet(setId, 'availableAnalysisFiles');
				
	    		loadThumbnailsForAnalysisInSet(setId, 'analysisImages');
	    		loadLinksForAnalysisInSet(setId, 'analysisLinks');
	    		
	    		showInfoDialog("The reference was successfully removed.");
	    	}
	    	else showErrorDialog(obj.message);
	    },
	    error: function(jqXHR, textStatus, errorThrown) {
	    	if (!isPageBeingRefreshed && textStatus != 'abort'){
	    		showErrorDialog("Service unavailable: " + removeExperimentSetAnalysisFileURL + " (" + errorThrown +")");
	    	}
	    }
	});
}

