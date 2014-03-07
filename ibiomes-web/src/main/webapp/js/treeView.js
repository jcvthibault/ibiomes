
var getChildrenDirsURL = contextPath + '/rest/services/collections/under';
var createDirectoryURL = contextPath + '/rest/services/collection/create';
var renameDirectoryURL = contextPath + '/rest/services/collection/rename';
var deleteDirectoryURL = contextPath + '/rest/services/collection/delete';
var unregisterDirectoryURL = contextPath + '/rest/services/collection/unregister';

var getFilesUnderCollectionByFormatURL = contextPath + '/rest/services/collection/files/formatted';

var fileLoadRequest;

/**
 * Load directory tree structure
 * @param uri File URI
 */
function loadTreeView(uri, canWrite, rootIsExperiment) {

	var rootType = "folder";
	if (rootIsExperiment){
		rootType = "experiment";
	}
	var ind = uri.lastIndexOf('/');
	var rootName = uri;
	if (ind>-1)
		rootName = uri.substring(ind+1);
	$("#treeView").jstree({
		"json_data" : {
			"data" : {
				"data" : rootName,
				"metadata" : { "absolutePath" : uri , "name" : rootName, "dirType" : rootType , "canWrite" : canWrite},
				"attr" : { "rel" : rootType, "title" : "List files in " +uri },
				"state" : "closed"
			},
            "ajax" : {
            	"url": function (node){
            		var selectedNodeUri = node.data("absolutePath");
            		return getChildrenDirsURL + "?recursive=false&uri="+selectedNodeUri;
            	},
            	"type" : "get",
            	"success" : function(obj) {
            		var data = [];
            		var dirs = obj.data;
            		if (obj.success){
	                    if (dirs != null){
		                    for( var d=0; d<dirs.length; d++ ){
		                      var dir = dirs[d];
		                      var typeStr = "folder";
		                      var stateStr = "closed";
		                      var nodeAttr = { "rel" : "folder" , "title" : "List files in " + dir.absolutePath };
		                      var avus = dir.metadata;
		                      var found = false;
		                      var j = 0;
		                      while ( j<avus.length && !found )
		                      {
			  		    		if (avus[j].attribute == "FILE_TYPE"){
			  		    			if (avus[j].value == "EXPERIMENT"){
			  		    				typeStr = "experiment";
			  		    				nodeAttr = { "rel" : "experiment" , "title" : "List files in " + dir.absolutePath };
			  		    				stateStr = "closed";
			  		    			}
			  		    			found = true;
			  		    		}
			  		    		j++;
		                      }
		                      
		                      var node = {
		                          "data" : dir.name,
		                          "metadata" :  { 
		                        	  "absolutePath" : dir.absolutePath, 
		                        	  "parent" : dir.parent ,
		                        	  "name" : dir.name,
		                        	  //TO CHANGE "canWrite" : dir.writable,
		                        	  "canWrite" : canWrite,
		                        	  "dirType" : typeStr } ,
		                          "attr" :  nodeAttr ,
		                          "state" : stateStr
		                      };
		                      data.push( node );
		                    }
	                    }
                    }
            		else {
            			showErrorDialog(obj.message);
            		}
                    return data;
            	}
		    }
		},
		"themes" : {
			"theme" : "default",
			"dots" : true,
			"icons" : true
		},
		"types" : {
            "max_depth" : -2, 
            "max_children" : -2, 
            "valid_children" : [ "folder", "experiment" ], 
            "types" : {
				"experiment" : {
					"icon" : { 
						"image" : contextPath + "/images/icons/mol_tiny.png" 
					},
					"valid_children" : [ "folder", "experiment" ]
				},
				"folder" : {
					"valid_children" : [ "folder", "experiment" ]
				}
			}
		},
		"contextmenu" : {
	        items: function (node) {
	        	if (node.data("canWrite")){
	        		var myItems =  {
			            "page" : {
			                "label" : "Go to experiment",
			                "_class" : "align-left",
			                "separator_after" : true,
			                "action" : function (obj) { window.location.href = "collection.do?uri=" + node.data("absolutePath"); }
			            },
			            "delete" : {
			                "label" : "Delete",
			                "_class" : "align-left",
			                "action" : function (obj) { displayDeleteDirDialog(node); }
			            },
						"add" : {
				            "label" : "Add new directory",
			                "_class" : "align-left",
				            "action" : function (obj) { displayCreateDirDialog(node); }
				        },
				        "rename" : {
			                "label" : "Rename",
			                "_class" : "align-left",
			                "action" : function (obj) { displayRenameDirDialog(node); }
			            }
		            };
	        		if (node.data("dirType")=="folder"){
	        			delete myItems.page;
	        		}
	        		return myItems;
	        	} else {
	        		return {};
	        	}
	        }
		},
		"plugins" : [ "themes", "json_data", "ui", "types", "contextmenu", "crrm" ]
	})
	.bind("select_node.jstree", function (event, data){
		var path = data.rslt.obj.data("absolutePath");
		var canWriteToNode = data.rslt.obj.data("canWrite");
		selectDirectoryInTree(path, canWriteToNode, uri);
    })
    .bind("loaded.jstree", function (event, data) {
	   data.inst.select_node('ul > li:first');
       data.inst.open_node('ul > li:first');
    });
}

function displayRenameDirDialog(node){
	var mydialog = $("#renameDirDialog");
	if (mydialog.length)
		mydialog.remove();
	mydialog = $('<div id="renameDirDialog"><p>Rename directory "'+node.data("absolutePath") + '?"<p>New name: <input id="updatedDirName" type="text" value =""/></p></div>');
	mydialog.dialog({
		height: 200,
        width: 300,
        modal: true,
        title: "Rename directory",
        buttons: [
            {text: "Ok", click: function() {
            	var newDirName = $("#updatedDirName").val();
            	if (newDirName.trim()!=""){
            		renameDirectoryInTree(node, newDirName);
            		$(this).dialog("close");
            	}
            	}
            },
            {text: "Cancel", click: function() {$(this).dialog("close");}}
        ]
	});
}

function displayDeleteDirDialog(node){
	var mydialog = $("#deleteDirInTreeDialog");
	if (mydialog.length)
		mydialog.remove();
	mydialog = $('<div id="deleteDirInTreeDialog"><p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>Are you sure you want to delete <i>'+node.data("absolutePath")+'</i>?</p></div>');
	mydialog.dialog({
		height: 200,
        width: 300,
        modal: true,
        title: "Delete directory",
        buttons: [
            {text: "Ok", click: function() {
            	deleteDirectoryInTree(node);
            	$(this).dialog("close");}
            },
            {text: "Cancel", click: function() {$(this).dialog("close");}}
        ]
	});
}
function displayUnregisterDirDialog(node){
	var mydialog = $("#unregisterDirInTreeDialog");
	if (mydialog.length)
		mydialog.remove();
	mydialog = $('<div id="unregisterDirInTreeDialog"><p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>Are you sure you want to unregister <i>'+node.data("absolutePath")+'</i>?</p></div>');
	mydialog.dialog({
		height: 200,
        width: 300,
        modal: true,
        title: "Unregister directory",
        buttons: [
            {text: "Ok", click: function() {
            	unregisterDirectoryInTree(node);
            	$(this).dialog("close");}
            },
            {text: "Cancel", click: function() {$(this).dialog("close");}}
        ]
	});
}

function displayCreateDirDialog(node){
	var mydialog = $("#createDirDialog");
	if (mydialog.length)
		mydialog.remove();
	mydialog = $('<div id="createDirDialog"><p>Add new directory under "'+node.data("absolutePath") + '"</p><p>Name&nbsp;<input id="newDirName" type="text" value =""/></p></div>');
	mydialog.dialog({
		height: 200,
        width: 300,
        modal: true,
        title: "Create directory",
        buttons: [
            {text: "Ok", click: function() {
            	var newDirName = $("#newDirName").val();
            	if (newDirName.length>0){
            		createDirectoryInTree(node, newDirName);
            		$(this).dialog("close");
            	}
            }
            },
            {text: "Cancel", click: function() {$(this).dialog("close");}}
        ]
	});
}

/**
 * List files in selected directory
 * @param path
 * @param canWrite
 */
function selectDirectoryInTree(path, canWrite, rootDir)
{
	var relativePath = rootDir.substring(rootDir.lastIndexOf('/')+1);
	if (path != rootDir){
		relativePath += path.substring(rootDir.length,path.length);
		var start = relativePath.lastIndexOf('/');
		var dirName = relativePath.substring(start+1);
		relativePath = relativePath.substring(0, start+1);
		relativePath = relativePath.replace(/\//g, ' > ') + "<span style='color:#65944A;font-weight:bold'>" + dirName + "</span>";
	}
	else relativePath = "<span style='color:#65944A;font-weight:bold'>" + relativePath + "</span>";
	
	$("#fileListHeader").empty();
	$("#fileListHeader").html("<p>"+relativePath+"</p>");
	
	if (canWrite){
		$("#dirWriteOptions").show();
	}
	else {
		$("#dirWriteOptions").hide();
	}
	loadCollectionFilesAnyFormat(path, "fileList", false, true, canWrite);
}

/**
 * Rename selected directory and refresh tree.
 * @param newDirName
 */
function renameDirectoryInTree(node, newDirName){
	var path = node.data("absolutePath");
	$.ajax({
	    url: renameDirectoryURL,
	    dataType: 'json',
	    data: {
	    	"uri" : path,
	    	"name" : newDirName
	    },
	    success: function(obj){
	    	if (obj.success){
	    		var tree = $.jstree._reference("#treeView");
	    		var newPath = node.data("parent") + "/" + newDirName;
	    		node.data("absolutePath", newPath);
	    		tree.rename_node(node , newDirName);
	    	} 
	    	else showErrorDialog(obj.message);
	      },
	    error: function(jqXHR, textStatus, errorThrown) { 
		    if (!isPageBeingRefreshed && textStatus != 'abort')
		    	showErrorDialog("Error: could not rename the directory");
	    }
	});
}

/**
 * Add new directory into the tree
 * @param dirName Name of the new directory
 */
function createDirectoryInTree(parentNode, newDirName){
	var path = parentNode.data("absolutePath") + "/" + newDirName;
	$.ajax({
	    url: createDirectoryURL,
	    dataType: 'json',
	    data: {
	    	"uri" : path
	    },
	    success: function(obj){
	    	if (obj.success){
	    		var tree = $.jstree._reference("#treeView");
	    		tree.open_node(parentNode);
	    		tree.refresh(parentNode);
	    	} 
	    	else showErrorDialog(obj.message);
	      },
	    error: function(jqXHR, textStatus, errorThrown) { 
		   	if (!isPageBeingRefreshed && textStatus != 'abort')
		   		showErrorDialog("Error: could not create directory '"+path+"'");
	    }
	});
}
/**
 * Delete selected directory in tree
 * @param path
 */
function deleteDirectoryInTree(node){
	var path = node.data("absolutePath");
	$.ajax({
	    url: deleteDirectoryURL,
	    dataType: 'json',
	    data: {
	    	"uris" : path
	    },
	    success: function(obj){
	    	if (obj.success){
	    		var tree = $.jstree._reference("#treeView");
	    		var parentNode = tree._get_parent(node);
	    		tree.open_node(parentNode);
	    		tree.refresh(parentNode);
	    	} 
	    	else showErrorDialog(obj.message);
	      },
	    error: function(jqXHR, textStatus, errorThrown) { 
		    if (!isPageBeingRefreshed && textStatus != 'abort')
		    	showErrorDialog("Error: could not delete directory '"+path+"'");
	    }
	});
}

/**
 * Unload selected directory in tree
 * @param path
 */
function unregisterDirectoryInTree(node){
	var path = node.data("absolutePath");
	$.ajax({
	    url: unregisterDirectoryURL,
	    dataType: 'json',
	    data: {
	    	"uris" : path
	    },
	    success: function(obj){
	    	if (obj.success){
	    		var tree = $.jstree._reference("#treeView");
	    		var parentNode = tree._get_parent(node);
	    		tree.open_node(parentNode);
	    		tree.refresh(parentNode);
	    	} 
	    	else showErrorDialog(obj.message);
	      },
	    error: function(jqXHR, textStatus, errorThrown) { 
		    if (!isPageBeingRefreshed && textStatus != 'abort')
		    	showErrorDialog("Error: could not unregister directory '"+path+"'");
	    }
	});
}

/**
 * Load list of files in collection
 * @param uri Collection URI
 */
function loadCollectionFilesAnyFormat(collectionUri, divId, recursive, excludeHidden, canWrite)
{
	var isAccordion = $("#" + divId).hasClass("ui-accordion");
	if (isAccordion){
		$("#" + divId).accordion('destroy');
	}
	$("#" + divId).empty();
	$("#"+divId+"Loading").show();
	
	if (fileLoadRequest != null){
		fileLoadRequest.abort();
	}
	fileLoadRequest = $.ajax({
	    url: getFilesUnderCollectionByFormatURL,
	    dataType: 'json',
	    data: {
	    	"uri" : collectionUri,
	    	"recursive" : recursive,
	    	"excludeHidden" : excludeHidden
	    },
	    success: function(obj)
	    {	
	    	if (obj.success){
		    	var html = "";
		    	var filesByFormat = obj.data;
		    	if (filesByFormat!=null)
		    	{	
		    		var f = 0;
		    		var nTypes = 0;
		    		var fileFormats = [];
		    		for (var format in filesByFormat)
		    		{
		    			if (format!=null && format.length>0){
		    				fileFormats[nTypes] = format;
		    				nTypes++;
		    			}
		    		}
		    		if (nTypes == 0){
		    			$("#"+divId+"Loading").hide();
			    		$("#" + divId).html("<p>No file available.</p>");
		    		}
		    		else
		    		{
		    			fileFormats.sort();
			    		while (f < fileFormats.length)
			    		{
			    			var fileFormat = fileFormats[f];
			    			var formId = "fileListForm" + f;
			    			var files = filesByFormat[fileFormat];
			    			
				    		html += "<h3 id='" + formId + "TabHeader'><a href='#'>" + fileFormat + " files</a></h3>";
				    		html += "<div id='" + formId + "TabContent' class='content'>";
				    		html += "	<form id='" + formId + "' class='blank'>";
				    		
				    		html += displayFilesInSameFormat(formId, files, fileFormat, collectionUri, canWrite);
					    	
					    	html += "</form>";
					    	html += "</div>";
					    	
					    	f++;
		    			}
	    		
			    		$("#"+divId+"Loading").hide();
				    	
				    	$("#" + divId).html(html);
				    	
				    	//create accordion (jQuery)
				    	$("#" + divId).accordion({
							collapsible: true,
							heightStyle: "content",
					  		animate: true,
					  		active: false
						});
		    		}
		    	}
		    	else {
		    		$("#"+divId+"Loading").hide();
		    		$("#" + divId).html("<p>No file available.</p>");
		    	}
		    } 
	    	else {
	    		$("#"+divId+"Loading").hide();
	    		showErrorDialog(obj.message);
	    	}
	      },
	      error: function(jqXHR, textStatus, errorThrown) { 
		    if (!isPageBeingRefreshed && textStatus != 'abort'){
		    	$("#"+divId+"Loading").hide();
		    	showErrorInline("Error: could not load the list of files", divId);
		    }
	    }
	});
}


