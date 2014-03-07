<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ page buffer="16kb" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<h:meta/>

<link rel="stylesheet" href="style/css/ibiomes.css" type="text/css" />
<link type="text/css" rel="Stylesheet" href="style/smoothness/jquery-ui-1.10.4.custom.css" /> 

<script type="text/javascript">
	var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
	var webServiceUrl = '<c:out value="${sessionScope.webServiceURL}"/>';
</script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/metadata.js"></script>
<script type="text/javascript" src="js/collections.js"></script>
<script type="text/javascript" src="js/dialogs.js"></script>
<script type="text/javascript" src="js/acl.js"></script>
<script type="text/javascript" src="js/resources.js"></script>

<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript">
	$(function(){

		getCollectionSubdirectories("<c:out value="${collection.absolutePath}" />");
		
		$("#tabs").tabs();
	});
</script>
<script type="text/javascript">

$(document).ready(function() {
	
	$('#collection-avu-loading').hide(); //initially hide the loading icon
	
	var uri = '<c:out value="${collection.absolutePath}" />';
	var resturl = webServiceUrl + "/rest/metadata/";
	getCollectionAVUs(uri);
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

    <br/>
		
	<div id="main" style="width:100%">

		<div class="post" style="border-color:#72A545; border-width:2px">
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
		        		<img class="icon" src="images/icons/folder_full_large.png" title="File collection (edit mode)"/>
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
	<h:collectionActions collectionUri="${collection.absolutePath}" canWrite="true" isExperiment="false" isEdit="true" />
	    
	<!-- the tabs -->
	<div id="tabs">
	
		<ul>
			<li><a href="#editinfo">Edit metadata</a></li>
			<li><a href="#permissioninfo">Permissions</a></li>
		</ul>
		
		<!-- ===================================== EDIT METADATA (SIMULATION PARAMETERS AND STRUCTURE) =================================== -->
	    <div id="editinfo">
	        <br/>
	        <h1>Metadata</h1>
	        <br/>
	        <div style="border:1px solid #DADADA;">
	        	<p><strong>Description</strong></p>
		        <form id="updateMetadataGeneralForm" class="blank">
		        	<table>
		        		<tr>
		        		<td><input type="text" style="width:400px;" name="DESCRIPTION"><c:out value="${simulation.description}" /></input></td>
		        		<td><img src="images/icons/accept.png" title="save modifications" onclick="updateCollectionAVUs('<c:out value="${collection.absolutePath}" />','updateMetadataGeneralForm')"></img></td>
		        		</tr>
		        	</table>
	        	</form>
        	</div>
        	<br/>
	        <div style="border:1px solid #DADADA;">
	        	<p><strong>Advanced</strong></p>
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
			    				style="border-width:0px" onclick="addCollectionAVU('<c:out value="${collection.absolutePath}" />', 'attrlist', 'vallist', 'avu_unit_std')"/>
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
			    				style="border-width:0px" onclick="addCollectionAVU('<c:out value="${collection.absolutePath}" />', 'avu_attribute', 'avu_value', 'avu_unit')"/>
			    		</td>
					</tr>
				</table>
	        </div>
	        <br/>
	        
	        <!-- -------------------- LIST OF CURRENT METADATA --------------------  -->

	        <h1>Current metadata</h1>
	        <br/>
	        	<div id="collection-avu-loading" style="text-align:center;"><img src="animations/loading50.gif"/></div>
				<div id="collection-avu-table"></div>
			<br/>
		
		</div>
		<!-- ===================================== ADVANCED EDIT METADATA =================================== -->
		
	   <!-- ===================================== PERMISSIONS =================================== -->
		
	    <div id="permissioninfo">
	    	<br/>
	    	
	    	<h1>Permissions</h1>
	        <table id="updateInheritForm">
	        	<tr><td colspan="2"><h3>General rules</h3></td></tr>
	        	<tr><td colspan="2">
	        		<form>
	        		<table>
						<tr><td style="border:0px;">Set default to inherit access permissions from parent collection</td></tr>
						<tr>
						<c:if test="${inheritPermissions}">
							<c:set var="inheritCheck" value="checked=\"checked\""/>
						</c:if>
						<c:if test="${not inheritPermissions}">
							<c:set var="noInheritCheck" value="checked=\"checked\""/>
						</c:if>
							<td style="border:0px;">
								<input name="permissionACL" type="radio" value="INHERIT" <c:out value="${inheritCheck}" />/> Yes&nbsp;&nbsp;&nbsp;
								<input name="permissionACL" type="radio" value="NO_INHERIT" <c:out value="${noInheritCheck}" />/> No&nbsp;&nbsp;&nbsp;
								<input class="button" type="button" value=" Update " onclick="updateInheritFlag('<c:out value="${collection.absolutePath}" />','updateInheritForm')"/>
						</td></tr>
	        		</table>
	        		</form>
		        </td>
		        </tr>
		        <tr style=""><td style="border:0px;"/></tr>
		        <tr><td><h3>Access control by user/group</h3></td></tr>
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
		        			<tr>
		        				<td style="border:0px;"><strong>Recursive</strong></td>
			        			<td style="border:0px;"><input type="checkbox" name="recursiveACL" value="yes"/></td>
		        			</tr>
		        			<tr style="height:10px"></tr>
		        			<tr><td style="border:0px;text-align:center" colspan="2">
		        				<input class="button" type="button" value=" Add permission " onclick="updateAcl('<c:out value="${collection.absolutePath}" />','updatePermissionsForm')"/>
		        			</td></tr>
		        		</table>
		        	</form>
		        </td>
		        <td>
		        	<form id="removePermissionsForm" class="blank">
		        		<div id="acl-table"></div>
		        	</form>
		        	<p><input class="button" type="button" value=" Remove "
			        			onclick="removePermissions('<c:out value="${collection.absolutePath}" />', 'removePermissionsForm')" />
			        </p>
		        </td>
		       </tr>
	        </table>
	    </div>
	    <!-- ===================================== PERMISSIONS =================================== -->

	   </div>
	 
	</div>
	
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
