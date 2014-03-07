<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/D/tdxhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ page buffer="12kb" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<h:meta/>

<link type="text/css" rel="stylesheet" href="style/css/ibiomes.css" />
<link type="text/css" rel="Stylesheet" href="style/smoothness/jquery-ui-1.10.4.custom.css" /> 

<script type="text/javascript">
	var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
	var webServiceUrl = '<c:out value="${sessionScope.webServiceURL}"/>';
</script>

<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/files.js"></script>
<script type="text/javascript" src="js/collections.js"></script>
<script type="text/javascript" src="js/cart.js"></script>
<script type="text/javascript" src="js/actions.js"></script>
<script type="text/javascript" src="js/dialogs.js"></script>
<script type="text/javascript" src="js/resources.js"></script>
<script type="text/javascript" src="js/experimentSet.js"></script>
<script type="text/javascript" src="js/metadata.js"></script>
<script type="text/javascript">
		
	$(document).ready(function() 
	{
		var id = <c:out value="${set.id}" />;
		var canWrite = <c:out value="${isOwner}" />;
		var resturl = webServiceUrl + "/rest/metadata/";
		
		$("#tabs").tabs();
		
		if (canWrite){
		
			$("#copyMethodMetadataButton").click(function() {
				var uri = $("#methodExperimentList").val();
				copyMethodExperimentAvuToSet(id, uri, "metadataContainer");
			});
			
			$("#copyTopologyMetadataButton").click(function() {
				var uri = $("#topologyExperimentList").val();
				copyTopologyExperimentAvuToSet(id, uri, "metadataContainer");
			});
			
			$("#clearSetMetadataButton").click(function() {
				clearExperimentSetAvus(id, "metadataContainer");
			});
			
			$("#updateSetDialogButton").click(function() {
				$("#updateSetDialog").dialog("open");
			});
			
			$( "#updateSetDialog").dialog({
				height: 250,
			    width: 330,
			    autoOpen: false,
				modal: true,
			    buttons: {
			        "Update": function() {
			        	var name = $("#setNameU").val();
			        	var desc = $("#setDescU").val();
			        	var publicFlag = ($("#setPublicU").is(":checked"));
			        	if (name.length>0){
			            	updateExperimentSet(id, name, desc, publicFlag);
			                $( this ).dialog( "close" );
			        	}
			        },
			        Cancel: function() {
			            $( this ).dialog( "close" );
			        }
			    }
			});
			
			$("#addAnalysisFileButton").click(function() {
				var uris = $("#availableAnalysisFiles").val() || [];
				addAnalysisFileReferenceToSet(id, uris);
			});
			
			$("#removeAnalysisFileButton").click(function() {
				var uris = $("#selectedAnalysisFiles").val();
				removeAnalysisFileReferenceFromSet(id, uris) || [];
			});

			loadSelectedAnalysisFileForSet(id, 'selectedAnalysisFiles');
			loadAvailableAnalysisFileForSet(id, 'availableAnalysisFiles');
			loadMetadataCatalogForAdd("avu_attribute_std_div","avu_value_std_div","avu_unit_std", "avu_std_add",resturl);
		}
		
		loadExperimentSetMetadata(id, "metadataContainer", canWrite);
		
		loadThumbnailsForAnalysisInSet(id, 'analysisImages');
		loadLinksForAnalysisInSet(id, 'analysisLinks');
		
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
			<h1>[ experiment set ] <span style="text-transform:uppercase;"><c:out value="${set.name}" /></span></h1>
	        <table class="layout-tight">
	        	<tr style="vertical-align:top">
	        		<td style="text-align:right">
		        		<img class="icon" src="images/icons/folder_full_large.png" title="Experiment set"/>
	        		</td>
	        		<td style="width:20px"></td>
	        		<td style="vertical-align:top;">
	        			<table class="layout-tight">
	        				<tr>
	        					<td>
	        						<table>
	        							<tr>
							        		<td><strong>Owner</strong></td>
							        		<td style="width:10px">&nbsp;</td>
							        		<td><c:out value="${set.owner}" /></td>
							        		<td style="width:40px">&nbsp;</td>
									        <td><strong>Created on</strong></td>
									        <td style="width:10px">&nbsp;</td>
									        <td><fmt:formatDate type="date" value="${set.registrationDate}"/></td>
							        	</tr>
							        	<tr>
							        		<td><strong>Public</strong></td>
							        		<td style="width:10px">&nbsp;</td>
							        		<td><c:out value="${set.isPublic}" /></td>
							        		<td></td>
									        <td></td>
									        <td></td>
									        <td></td>
							        	</tr>
	        						</table>
	        					</td>
	        				</tr>
	        				<tr style="height:5px"/>
	        				<tr>
	        					<td style="txt-align:justify"><i><c:out value="${set.description}" /></i></td>
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
	
	<br/>
	
	<!-- the tabs -->
	<div id="tabs">
		<ul>
			<li><a href="#description">Details</a></li>
			<c:if test="${ isOwner }">
				<li><a href="#edit">Edit</a></li>
			</c:if>
		</ul>
	
		<!-- ===================================== ASSOCIATED EXPERIMENTS =================================== -->
		<div id="description">
		<br/>
			<h1>Description</h1>
			<c:choose>
				<c:when test="${not empty set.description}">
					<p><c:out value="${set.description}" /></p>
				</c:when>
				<c:otherwise><p>No description available.</p></c:otherwise>
			</c:choose>
			<br/>
			<!--  METADATA  -->
			
			<div id="metadataContainer">
			</div>
			<br/>
			
			<h1>Experiments</h1>
			<c:choose>
	        	<c:when test="${ not empty experimentList }">
				<table>
					<tr><th>Experiment</th><th>Description</th></tr>
					<c:forEach items="${experimentList}" var="experiment" varStatus="r">
						<c:choose>
				        	<c:when test="${r.count % 2 == 0}">
				            	<c:set var="rowStyle" scope="page" value="row-a"/>
				        	</c:when>
				          	<c:otherwise>
				            	<c:set var="rowStyle" scope="page" value="row-b"/>
				          	</c:otherwise>
				        </c:choose>
				        <tr class="<c:out value="${rowStyle}"/>">
							<td>
								<a class="link" href="collection.do?uri=<c:out value="${experiment.absolutePath}"/>">
									<c:out value="${experiment.name}"/>
								</a>
							</td>
							<td><i><c:out value="${experiment.description}"/></i></td>
						</tr>
					</c:forEach>
				</table>
				</c:when>
				<c:otherwise>
					<p>No associated experiment found.</p>
				</c:otherwise>
			</c:choose>
			<br/>
			
			<!--  ANALYSIS DATA  -->
			
	        <h1>Analysis</h1>
	        <h3>Images</h3>
     		<div id="analysisImages"></div>
     		<h3>Links</h3>
     		<div id="analysisLinks"></div>
     		
		</div>
		
		<c:if test="${ isOwner }">
			<!-- ===================================== EDIT =================================== -->
			<div id="edit">
				<h1>General</h1>
				<ul>
					<li><a id="updateSetDialogButton" class="pointer link">Update details</a></li>
					<li><a href="experimentsetmanager.do" class="link">Manage experiment sets</a></li>
				</ul>
				<!--  update experiment set -->
		    	<div id="updateSetDialog" title="Update experiment set">
					<table class="layout" style="text-align:left">
						<tr><td><strong>Name</strong></td><td><input id="setNameU" value="<c:out value="${set.name}" />" type="text" /></td></tr>
						<tr><td colspan="2"><strong>Description</strong></td></tr>
						<tr><td colspan="2"><textarea id="setDescU" cols="30" rows="3" style="width:240px;height:40px"><c:out value="${set.description}" /></textarea></td></tr>
						<tr><td><strong>Public?</strong></td><td>&nbsp;
							<c:choose>
								<c:when test="${set.isPublic}">
									<input id="setPublicU" value="true" type="checkbox" checked="checked"/>
								</c:when>
								<c:otherwise>
									<input id="setPublicU" value="true" type="checkbox" />
								</c:otherwise>
							</c:choose> 
						</td></tr>
					</table>
				</div>
				<br/>
				
				<!-- ANALYSIS DATA -->

				<h1>Presentation of analysis data</h1>
				<table class="layout">
					<tr>
						<td>
							<p>Current data presented in the public page</p>
							<select id="selectedAnalysisFiles" multiple="multiple" style="width:330px" size="7"></select><br/><br/>
							<input type="button" class="button" id="removeAnalysisFileButton" value=" Remove "/>
						</td>
						<td style="width:20px"/>
						<td>
							<p>Available analysis data</p>
							<select id="availableAnalysisFiles" multiple="multiple" style="width:330px" size="7"></select><br/><br/>
							<input type="button" class="button" id="addAnalysisFileButton" value=" Add "/>
						</td>
					</tr>
				</table>
				<br/>
				
				<!-- METADATA -->
				<h1>Metadata</h1>
				<ul><li><a id="clearSetMetadataButton" class="pointer link">Clear all metadata</a></li></ul>
				<c:if test="${ not empty experimentList }">
					<br/>
					<p><strong>Select an experiment to copy metadata to this set</strong></p>
					<div style="border:1px solid #DADADA;">
						<table class="layout">
							<tr><td>Copy topology metadata</td></tr>
							<tr>
								<td>
									<select id="topologyExperimentList">
									<c:forEach items="${experimentList}" var="experiment" varStatus="r">
										<option><c:out value="${experiment.absolutePath}"/></option>
									</c:forEach>
									</select>
								</td>
								<td style="width:5px"></td>
								<td><input type="button" class="button" id="copyTopologyMetadataButton" value=" copy "/></td>
							</tr>
							<tr style="height:15px"/>
							<tr><td>Copy method/parameter metadata</td></tr>
							<tr>
								<td>
									<select id="methodExperimentList">
									<c:forEach items="${experimentList}" var="experiment" varStatus="r">
										<option><c:out value="${experiment.absolutePath}"/></option>
									</c:forEach>
									</select>
								</td>
								<td></td>
								<td><input type="button" class="button" id="copyMethodMetadataButton" value=" copy "/></td>
							</tr>
						</table>
					</div>
				</c:if>
				<br/>
				<p><strong>Manually add metadata</strong></p>
				<div style="border:1px solid #DADADA;">
			    <table>
					<tr>
						<td>Standard attribute</td>
						<td>Value</td>
						<td>Unit</td>
						<td style="width:20px"></td>
					</tr>
					<tr>
						<td><div id="avu_attribute_std_div"/></td>
						<td><div id="avu_value_std_div"/></td>
						<td><input style="width:80px;" id="avu_unit_std" type="text" value=""/></td>
						<td>
							<input id="avu_std_add" type="image" 
			    				src="images/icons/add_small.png" title="add user-defined AVU" 
			    				style="border-width:0px" onclick="addExperimentSetAVU(<c:out value="${set.id}" />, 'attrlist', 'vallist', 'avu_unit_std', 'metadataContainer')"/>
			    		</td>
					</tr>
					<tr style="height:10px"/>
					<tr>
						<td>User-defined attribute</td>
						<td>Value</td>
						<td>Unit</td>
						<td style="width:20px"></td>
					</tr>
					<tr>
						<td><input style="width:200px;" id="avu_attribute" type="text" value=""/></td>
						<td><input style="width:200px;" id="avu_value" type="text" value=""/></td>
						<td><input style="width:80px;" id="avu_unit" type="text" value=""/></td>
						<td>
			    			<input type="image" 
			    				src="images/icons/add_small.png" title="add user-defined AVU" 
			    				style="border-width:0px" onclick="addExperimentSetAVU(<c:out value="${set.id}" />, 'avu_attribute', 'avu_value', 'avu_unit', 'metadataContainer')"/>
			    		</td>
					</tr>
				</table>
	        </div>
			</div>
		</c:if>
	</div>
	
	<div id="error-message" title="Error"></div>
	<div id="info-message" title="Info"></div>
	<div id="confirm-message" title="Confirm"></div>
		
	</div>
				
	<!-- content-wrap ends here -->		
	</div></div>
	
<!-- wrap ends here -->
</div>

<!-- footer -->
<h:footer/>

</body>
</html>
