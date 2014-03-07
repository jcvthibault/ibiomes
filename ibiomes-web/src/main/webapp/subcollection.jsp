<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/D/tdxhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<h:meta/>

<link type="text/css" rel="stylesheet" href="style/css/ibiomes.css" />
<link type="text/css" rel="Stylesheet" href="style/smoothness/jquery-ui-1.10.4.custom.css" /> 
<script type="text/javascript">
	var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
</script>

<script type="text/javascript" src="js/common.js"></script>

<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-1.10.2.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript" src="js/jquery.form.js"></script>

<!-- JQuery TreeView -->
<script type="text/javascript" src="js/jstree/_lib/jquery.cookie.js"></script>
<script type="text/javascript" src="js/jstree/_lib/jquery.hotkeys.js"></script>
<script type="text/javascript" src="js/jstree/jquery.jstree.js"></script>
<script type="text/javascript" src="js/treeView.js"></script>

<script type="text/javascript" src="js/files.js"></script>
<script type="text/javascript" src="js/collections.js"></script>
<script type="text/javascript" src="js/resources.js"></script>
<script type="text/javascript" src="js/cart.js"></script>
<script type="text/javascript" src="js/actions.js"></script>
<script type="text/javascript" src="js/dialogs.js"></script>

<script type="text/javascript">

	$(document).ready(function() {

		var uri = "<c:out value="${collection.absolutePath}" />";
		var canWrite = "<c:out value="${canWrite}" />";
		
		$("#tabs").tabs();
		
		$("#dirWriteOptions").hide();
		
		loadTreeView(uri, canWrite, false);
		
		$( "#uploadDialog" ).dialog({
			height: 200,
            width: 300,
            autoOpen: false,
			modal: true
		});
		
		$( "#treeActionButton" ).click(function() {
			var action =  $("#treeAction").val();
			
			if (action == "createDir"){
				displayCreateDirDialog($("#treeView").jstree("get_selected"));
			} 
			else if (action == "renameDir"){
				displayRenameDirDialog($("#treeView").jstree("get_selected"));
			} 
			else if (action == 'deleteDir'){
				displayDeleteDirDialog($("#treeView").jstree("get_selected"));
			} 
			else if (action == 'uploadFile')
			{
				var selectedUri = $("#treeView").jstree("get_selected").data("absolutePath");
				if (selectedUri == null){
					selectedUri = uri;
				}
				$( "#uploadFileUri" ).val(selectedUri);
	        	$( "#uploadForm" ).show();
				$( '#uploadResults').html('');
	            $( "#uploadDialog" ).dialog("open");
			}
		});

		$( "#uploadForm" ).ajaxForm({
	        beforeSubmit: function() {
	        	$( "#uploadForm" ).hide();
	            $('#uploadResults').html('<p>Uploading...</p>');
	            $('#uploadResults').append('<div><img src="animations/loading26.gif"/></div>');
	        },
	        success: function(response) {
	        	$( "#uploadForm" ).hide();
	            var $out = $('#uploadResults');
	            if (response.success){
		            $out.html('<p><strong>File succesfully uploaded</strong></p>');
		            $out.append('<p><i>'+ response.data.name +'</i> ('+response.data.size+' B)</p>');
		            var selNode = $("#treeView").jstree("get_selected");
		            loadCollectionFilesAnyFormat(selNode.data("absolutePath"), "fileList", false, true, canWrite);
		            setTimeout( function (){$( "#uploadDialog" ).dialog("close");}, 2000);
	            }
	            else {
	            	$out.html('<strong>Error</strong>');
			    	$out.append('<p>'+ response.message +'</p>');
	            }
	        },
	        error: function() { 
	        	$( "#uploadForm" ).hide();
	        	$( "#uploadResults" ).html('Error: upload service request failed');
	        }
	    });
		
	});
</script>

<title>iBIOMES</title>
	
</head>

<body>
<!-- wrap starts here -->
<div id="wrap">

	<div id="header"><div id="header-content">	
		<h1 id="logo"><a href="index.do" title="">i<span class="gray">BIOMES</span><span style="font-size:0.6em"><i>&nbsp;&nbsp;Repository</i></span></a></h1>		
		<h2 id="slogan">Disseminating simulation data to create knowledge</h2>		
		
		<!-- Menu Tabs -->
		<ul>
			<li><a href="index.do">Home</a></li>
			<li><a href="search.do">Search</a></li>
			<li><a href="documentation.jsp">Documentation</a></li>
			<li><a href="about.jsp">About</a></li>
			<li><a href="cart.do"><h:cart/></a></li>
		</ul>
		<!-- login/logout link -->
		<h:login/>
	
	</div></div>
	
	<!-- content-wrap starts here -->
	<div id="content-wrap"><div id="content">
    <br/>
		
	<div id="main" style="width:100%">

		<div class="post">
			<c:choose>
				<c:when test="${not empty rootExperiment}">
					<h1>[ experiment ] <span style="text-transform:uppercase;"><c:out value="${rootExperiment.fileCollection.name}" /></span>
					<c:if test="${not empty rootExperiment.softwarePackages}">
				        <c:forEach items="${rootExperiment.softwarePackages}" var="sw" varStatus="status">
		        			<c:set var="swList" value="${swList}${status.first ? '' : ', '}${sw}" scope="request" />
		        		</c:forEach>
		        		(<c:out value="${swList}" />)
		        	</c:if>
		        	</h1>
				</c:when>
				<c:otherwise><h1><c:out value="${collection.absolutePath}" /></h1></c:otherwise>
			</c:choose>
	        <table class="layout-tight">
	        	<tr style="vertical-align:top">
	        		<td style="text-align:right">
		        		<img class="icon" src="images/icons/folder_full_large.png" title="File collection (read mode)"/>
	        		</td>
	        		<td style="width:20px"></td>
	        		<td style="vertical-align:top;">
	        			<table class="layout-tight">
	        				<tr><td><c:out value="${navLink}" escapeXml="false"/></td></tr>
	        				<tr style="height:5px"/>
	        				<tr>
	        					<td style="txt-align:justify"><i><c:out value="${collection.description}" /></i></td>
	        				</tr>
	        				<tr style="height:5px"/>
	        				<tr>
	        					<td>
	        						<table>
	        							<tr>
							        		<td><strong>Owner</strong></td>
							        		<td style="width:10px">&nbsp;</td>
							        		<td><c:out value="${collection.owner}" /></td>
							        		<td style="width:40px">&nbsp;</td>
									        <td><strong>Created on</strong></td>
									        <td style="width:10px">&nbsp;</td>
									        <td><c:out value="${collection.registrationDate}" /></td>
							        	</tr>
	        						</table>
	        					</td>
	        				</tr>
	        			</table>
	        		</td>
	        	</tr>
	        </table>
	    </div>
	    
		<div>
       		<c:if test="${ not empty error }">
				<br/>
		   		<h:messageError message="${error}" />
			</c:if>
			<c:if test="${ not empty message }">
				<br/>
		   		<h:messageSuccess message="${message}" />
			</c:if>
	    </div>
		
		<!-- actions on collection -->
		<h:collectionActions collectionUri="${collection.absolutePath}" canWrite="${canWrite}" isExperiment="false" isEdit="false" />
		
	<!-- the tabs -->
	<div id="tabs">
		<ul>
			<li><a href="#filesinfo">Files</a></li>
		</ul>
	
		<!-- ===================================== FILE COLLECTION =================================== -->
		<div id="filesinfo">
		<br/>
			<div class="treeview" style="width:300px;height:500px;">
				<div id="treeView" style="background-color:white;"></div>
			</div>
			<div id="fileListContainer" style="margin-left:20px;width:625px;float:left">
				<input id="selectedFile" type="hidden"/>
				<hr/><p><strong>Current directory</strong></p>
				<div id="fileListHeader"></div>
				<div id="dirWriteOptions">
					<p>
						<select id="treeAction" style="width:250px">
							<option value="createDir">Create new subfolder</option>
							<option value="renameDir">Rename directory</option>
							<option value="deleteDir">Delete directory</option>
							<option value="uploadFile">Upload file</option>
						</select>
						<input id="treeActionButton" type="button" value=" Go " />
					</p>
					<!--  upload file -->
			    	<div id="uploadDialog" title="File upload">
						<form id="uploadForm" action="rest/services/upload/file" method="post" enctype="multipart/form-data" class="blank">
							<p><br/><input id="uploadFileUri" type="hidden" name="uri" value="<c:out value="${ collection.absolutePath }"/>"/>
							   <input name="file" type="file"/></p>
							<p>Overwrite existing files&nbsp;&nbsp;<input name="overwrite" type="checkbox" value="true"/></p>
							<p><br/><input class="button" name="save" type="submit" value=" Upload " /></p>
						</form>
						<div id="uploadResults"></div>
					</div>
				</div>
				<br/>
				<hr/>
				<p><strong>File listing</strong></p>
				<br/>
				<div id="fileList"></div>
				<div id="fileListLoading" style="text-align:center;display:none;"><img src="animations/loading50.gif"/></div>
			</div>
			<br style="clear: left;"/>
			
		</div>
		<!-- ===================================== FILE COLLECTION =================================== -->
	    
		<div id="error-message" title="Error"></div>
		<div id="info-message" title="Info"></div>
		<div id="confirm-message" title="Confirm"></div>
			
	</div>
	
	</div>
				
	<!-- content-wrap ends here -->		
	</div></div>
	
<!-- wrap ends here -->
</div>

<!-- footer -->
<h:footer/>

</body>
</html>
