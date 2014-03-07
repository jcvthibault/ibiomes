<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/D/tdxhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ib" uri="http://ibiomes.chpc.utah.edu/tlds" %>
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
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/metadata.js"></script>
<script type="text/javascript" src="js/acl.js"></script>
<script type="text/javascript" src="js/dialogs.js"></script>
<script type="text/javascript" src="js/files.js"></script>
<script type="text/javascript" src="js/resources.js"></script>

<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript">
  function play(script) {
    document.jmol_large.script(script);
  }
</script>
<script type="text/javascript">
	$(function(){
		$("#tabs").tabs();
		$('#acl-loading').hide();
	});
</script>
<script type="text/javascript">
	$(document).ready(function() {
		var uri = '<c:out value="${file.absolutePath}" />';
		var resturl = webServiceUrl + "/rest/metadata/";
		getFileAVUs(uri);
		getAcl(uri);
		loadMetadataCatalogForAdd("avu_attribute_std_div","avu_value_std_div","avu_unit_std","avu_std_add",resturl);
		
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
			<li><a href="cart.do">Cart
			<c:if test="${not empty sessionScope.shoppingCartItemCount}">
				(<c:out value="${sessionScope.shoppingCartItemCount}" />)
			</c:if>
			</a></li>
		</ul>
		<!-- login/logout link -->
		<h:login/>
		
	</div></div>
				
	<!-- content-wrap starts here -->
	<div id="content-wrap"><div id="content">
		<div id="main" style="width:100%">
			<br/>
			<div class="post" style="border-color:#72A545; border-width:2px">
			<h1>[ file ] <c:out value="${file.name}"/></h1>
			<table class="layout-tight">
	        	<tr style="vertical-align:top">
		        	<td style="text-align:right">
		        		<img class="icon" src="images/icons/full_page_large.png" title="File (edit mode)"/>
	        		</td>
	        		<td style="width:20px"></td>
	        		<td style="vertical-align:top;">
	        			<table class="layout-tight">
							<tr><td><c:out value="${navLink}" escapeXml="false"/></td></tr>
							<tr style="height:5px"></tr>
							<c:if test="${not empty file.description}">
								<tr><td>
									<i><c:out value="${file.description}"/></i></td>
								</tr>
								<tr style="height:5px"></tr>
							</c:if>
							<tr>
	        					<td>
	        						<table>
							        	<tr>
									        <td><strong>File format</strong></td>
									        <td style="width:10px"></td>
											<td><c:out value="${fileFormat}" /></td>
									        <td style="width:50px">&nbsp;</td>
									        <td><strong>Size</strong></td>
									        <td style="width:10px"></td>
									        <td><c:out value="${ib:fileSize(file.size)}" /></td>
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
		    
			<div style="width:100%;height:60px;vertical-align:middle;">
				<table class="layout-tight" style="float:right">
				<tr>
				<td><a class="icon" href="collection.do?uri=<c:out value="${experiment.absolutePath}" />" title="Go to experiment summary page">
					<img src="images/icons/folder_full.png"/>
				</a></td>
				<td><a class="icon" href="rest/services/download?uri=<c:out value="${file.absolutePath}"/>" target="_blank" title="Download file">
					<img src="images/icons/download.png"/>
				</a></td>
				<td><a class="link" href="file.do?uri=<c:out value="${file.absolutePath}"/>" title="Summary page">
					Summary view
				</a></td>
				</tr>
				</table>
			</div>
	    
			<!-- the tabs -->
			<div id="tabs">
			
			<ul>
				<li><a href="#edit">Edit properties</a></li>
				<li><a href="#editinfoadv">Advanced edit</a></li>
				<li><a href="#permissions">Permissions</a></li>
			</ul>
										
			<!-- ===========================================  METADATA EDIT TAB =========================================== -->
			<div id="edit">
			 <br/><h1>Metadata</h1>
			 	       
			 <table>
				<tr><td>
					<form action="updateFormat.do" method="get">
						<table>
						<tr style="height:0px">
							<td colspan="3" style="border:0px;">
								<input type="hidden" name="uri" value="<c:out value="${file.absolutePath}"/>"/>
								<input type="hidden" name="dispatchto" value="/editfile.do?uri=<c:out value="${file.absolutePath}"/>"/>
							</td>
						</tr>
						<tr>
							<td style="border:0px;"><strong>Format</strong></td>
							<td style="border:0px;">
								<select name="FILE_FORMAT" style="width:300px">
									<option value="UNKNOWN"></option>
									<c:forEach items="${formatList}" var="format">
								 		<c:choose>
								 			<c:when test="${fileFormat eq format.term}">
								 				<option selected='selected' value="<c:out value="${format.term}"/>"><c:out value="${format.term}"/></option>
								 			</c:when>
								 			<c:otherwise>
								 				<option value="<c:out value="${format.term}"/>"><c:out value="${format.term}" /></option>
								 			</c:otherwise>
								 		</c:choose>
								 	</c:forEach>
					            </select>
							</td>
							<td style="border:0px;"><input class="button" type="submit" value=" Update " style="vertical-align:middle;"/></td>
						</tr>
						</table>
					</form>

					<form id="updateDescriptionForm">
					<table>
						<tr><td style="border:0px;"><strong>Description</strong></td>
							<td style="border:0px;"><textarea style="width:300px;" name="FILE_DESCRIPTION"><c:out value="${fileDescription}"/></textarea></td>
							<td style="border:0px;">
								<img src="images/icons/accept.png" title="save modifications" 
	        						onclick="updateFileAVUs('<c:out value="${file.absolutePath}" />','updateDescriptionForm')"></img>
							</td>
						</tr>
						</table>
					</form>
				</td></tr>
				<!-- ===============================================  TOPOLOGY FILE  =============================================== -->
				
				<c:choose>
					<c:when test="${(fileFormat eq 'AMBER parmtop') || (fileFormat eq 'PDB')}">
				
						<tr style="height:15px"><td/></tr>
						<tr><td>
						<c:if test="${ not empty residueChain}" >
							<strong>Residue chain: </strong> <c:out value="${residueChain}" /><br/>
							<br/>
						</c:if>
						<c:if test="${ not empty normalizedChains}" >
							<strong>Normalized chains:</strong>
							<ul>
							<c:forEach items="${normalizedChains}" var="resChain">
								<li><c:out value="${resChain}" /></li>
							</c:forEach>
							</ul>
						</c:if>
						
						<c:if test="${ not empty nonStandardResidues}" >
							<br/><strong>Non-standard residues:</strong>
							<ul>
							<c:forEach items="${nonStandardResidues}" var="resChain">
								<li><c:out value="${resChain}" /></li>
							</c:forEach>
							</ul>
						</c:if>
						
						<c:if test="${ not empty atomChain}" >
							<br/><strong>Atom chain: </strong> <c:out value="${atomChain}" />
						</c:if>
					</td></tr>
				</c:when>
				<c:when test="${fileFormat eq 'CSV'}">
					<!-- ===============================================  CSV FILE  =============================================== -->
					<tr><td>
						
						<form  id="updateSpecificFileAvuForm">							
							<table>
			        			<tr><td style="border:0px;"><strong>Labels:</strong></td>
			        				<td style="border:0px;"><input style="width:300px;" name="DATA_LABELS" type="text" value="<c:out value="${csvAxis}"/>"/></td>
			        				<td style="border:0px;vertical-align:middle;" rowspan="2">
			        					<img src="images/icons/accept.png" title="save modifications" 
	        								onclick="updateFileAVUs('<c:out value="${file.absolutePath}" />','updateSpecificFileAvuForm')"></img>
			        				</td>
			        			</tr>
								<tr><td style="border:0px;"><strong>Units:</strong></td>
									<td style="border:0px;"><input style="width:300px;" name="DATA_UNITS" type="text" value="<c:out value="${csvUnits}"/>"/></td>
								</tr>
							</table>
							<p>Warning: Labels and Units must be separated by a comma (,)</p>
						</form>
					</td></tr>
					
					</c:when>
				</c:choose>
				</table>
			</div>
			
			
		<!-- ===================================== ADVANCED EDIT METADATA =================================== -->
	    <div id="editinfoadv"> 
	        <h1>Add metadata</h1>
	        <br/>
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
			    				style="border-width:0px" onclick="addFileAVU('<c:out value="${file.absolutePath}" />', 'attrlist', 'vallist', 'avu_unit_std')"/>
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
			    				style="border-width:0px" onclick="addFileAVU('<c:out value="${file.absolutePath}" />', 'avu_attribute', 'avu_value', 'avu_unit')"/>
			    		</td>
					</tr>
				</table>
	        </div>
	        <br/>
	        
	        <!-- ------------------- LIST OF CURRENT METADATA --------------------  -->
	        
	        <h1>Current metadata</h1>
	        <br/>
	        	<div id="file-avu-loading" style="text-align:center;"><img src="animations/loading50.gif"/></div>
				<div id="file-avu-table"></div>
			<br/>
		
		</div>
		<!-- ===================================== ADVANCED EDIT METADATA =================================== -->
		
		<!-- ===========================================  PERMISSIONS TAB =========================================== -->
      	<div id="permissions">
	        <br/>
	        <h1>Permissions</h1>
	        <table>
	        	<tr><td colspan="2"><h3>Access control by user/group</h3></td></tr>
		        <tr>
		         <td style="vertical-align:top;">
		        	<form id="updatePermissionsForm">
		        		<table>
		        			<tr>
		        				<td style="border:0px;"><strong>User / group</strong></td>
			        			<td style="border:0px;">
			        				<select style="width:160px" name="userACL">
			        					<c:forEach items="${userList}" var="user">
			        						<option value="<c:out value="${user.name}"/>"><c:out value="${user.name}"/></option>
			        					</c:forEach>
			        				</select>
			        			</td>
			        		</tr>
			        		<tr>
		        				<td style="border:0px;"><strong>Permissions</strong></td>
			        			<td style="border:0px;">
			        				<select style="width:160px" name="permissionACL">
			        					<option value="<c:out value="${permissionREAD}"/>">READ</option>
			        					<option value="<c:out value="${permissionWRITE}"/>">WRITE</option>
			        					<option value="<c:out value="${permissionOWN}"/>">OWN</option>
			        				</select>
			        			</td>
		        			</tr>
		        			<tr style="height:10px"></tr>
		        			<tr><td style="border:0px;text-align:center" colspan="2">
		        				<input class="button" type="button" value=" Add permission " onclick="updateAcl('<c:out value="${file.absolutePath}" />','updatePermissionsForm')"/>
		        			</td></tr>
		        		</table>
		        	</form>
		        </td>
		        <td>
		        	<form id="removePermissionsForm" class="blank">
	        			<div id="acl-loading" style="text-align:center;"><img src="animations/loading50.gif"/></div>
		        		<div id="acl-table"></div>
		        	</form>
		        	<p><input class="button" type="button" value=" Remove "
			        			onclick="removePermissions('<c:out value="${file.absolutePath}" />', 'removePermissionsForm')" />
			        </p>
		        </td>
		       </tr>
	        </table>

	    </div>
		<!-- ===========================================  PERMISSIONS TAB =========================================== -->
			
		</div></div>
		
		
	<!-- for jquery dialogs -->
	
	<div id="error-message" title="Error"></div>
	<div id="info-message" title="Info"></div>
	<div id="confirm-message" title="Confirm"></div>
	
	<!-- content-wrap ends here -->		
	</div></div>
	
<!-- wrap ends here -->
</div>

<!-- footer -->
<h:footer/>

</body>
</html>
