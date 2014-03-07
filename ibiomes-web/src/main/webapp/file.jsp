<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ib" uri="http://ibiomes.chpc.utah.edu/tlds" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<h:meta/>

<link type="text/css" rel="stylesheet" href="style/css/ibiomes.css" />
<link type="text/css" rel="Stylesheet" href="style/smoothness/jquery-ui-1.10.4.custom.css" /> 

<script type="text/javascript">
	var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
</script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/charts.js"></script>
<script type="text/javascript" src="js/files.js"></script>
<script type="text/javascript" src="js/resources.js"></script>
<script type="text/javascript" src="js/dialogs.js"></script>

<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="jsmol/JSmol.min.nojq.js"></script>
<script type="text/javascript">

	function getDisplayType(){
		return $("input[name='jmolDisplayType']:checked").val();
	}
	var Info = {
	  addSelectionOptions: false,
	  color: "#FAFAFA",
	  debug: false,
	  width: 640,
	  height: 480,
	  j2sPath: "jsmol/j2s", // HTML5 only
	  use: "HTML5",  // "HTML5" or "Java"
	  script: "",
      disableJ2SLoadMonitor: true,
	  disableInitialConsole: true,
	  deferApplet: false,
	  deferUncover: true
	};
	Jmol.setDocument(0);
</script>
<script type="text/javascript">
$(document).ready(function() {

	$("#tabs").tabs();
		
	<c:if test="${fileFormat eq 'CSV'}">
		
		loadPlotImage('plotContainer', '<c:out value="${file.absolutePath}"/>', '', '<c:out value="${file.description}"/>', '<c:out value="${csvAxis}"/>', '<c:out value="${csvUnits}"/>', 700, 550);
		
		$("#chartType").change(function() {
			var chartType = $("#chartType").val();
			loadPlotImage('plotContainer', '<c:out value="${file.absolutePath}"/>', chartType, '<c:out value="${file.description}"/>', '<c:out value="${csvAxis}"/>', '<c:out value="${csvUnits}"/>', 700, 550);
		});
		
	</c:if>
	
	if ($("#jmolAppletDiv").length){
		Info.script = 'load ' + '<c:out value="${relativePath}"/>' + '; cpk off; wireframe 40;';
		Jmol.getApplet("fileJmol", Info);
		$("#jmolAppletDiv").html(Jmol.getAppletHtml(fileJmol));
	}
	
	$("input[name='jmolDisplayType']").change(function(){ 
		Jmol.script(fileJmol,getDisplayType());
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
			<li><a href="about.do">About</a></li>
		</ul>
		<!-- login/logout link -->
		<h:login/>
	
	</div></div>
	
	<!-- content-wrap starts here -->
	<div id="content-wrap"><div id="content">
		<div id="main" style="width:100%">
			<br/>
			<div class="post">
			<h1>[ file ] <c:out value="${file.name}"/></h1>
			<table class="layout-tight">
	        	<tr style="vertical-align:top">
		        	<td style="text-align:right">
		        		<img class="icon" src="images/icons/full_page_large.png" title="File (read mode)"/>
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
				<!-- IF WRITE PERMISSIONS --> 
				<c:if test="${canWrite}">
					<td><a class="icon" href="editfile.do?uri=<c:out value="${file.absolutePath}" />" title="Edit file properties">
						<img src="images/icons/page_process.png"/>
					</a></td>
				</c:if>
				</tr>
				</table>
			</div>
    
			<!-- the tabs -->
			<div id="tabs">
			
				<ul>
					<li><a href="#view">View data</a></li>
				</ul>
			
				<!-- ===========================================  VISUALIZATION TAB =========================================== -->
				
				<div id="view">
					<c:choose>
						<c:when test="${not canRead}">
							<p>You don't have read access to this file. Please contact the owner if you need access.</p>
						</c:when>
						<c:otherwise>
							<br/>
							<c:choose>
								<c:when test="${fileFormat eq 'CSV'}">
									<!-- ================================================= CSV FILE  ================================================= -->
									<div>
										Chart type
										<select id="chartType">
											<option value="">Unknown</option>
											<option value="line">Line plot</option>
											<option value="dot">Scatter plot</option>
											<option value="histogram">Histogram</option>
											<option value="heatmap">Heatmap</option>
										</select>
										<br/><br/>
										<div style="text-align:center;" id="plotContainer"></div>
										<div id="chartAppletLoading"></div>
									</div>
								</c:when>
								<c:when test="${ib:isImageFileFormat(fileFormat)}">
									<!--  =================================================  IMAGE FILES  ================================================= -->
									<div style="text-align:center">
										<c:if test="${not empty file.description}">
											<h2><c:out value="${file.description}"/></h2>
										</c:if>
										<img style="width:750px;" src="<c:out value="${localFileUrl}"/>"></img>
									</div>
								</c:when>
								<c:when test="${ib:isJmolFileFormat(fileFormat)}">
									<!--  =================================================  3D STRUCTURE FILE   ================================================= -->
									<div>
										<table>
										<tr><td><strong>JMOL Viewer</strong> | <a class="link" href="jmol.do?uri=<c:out value="${file.absolutePath}"/>" target="_blank">Open viewer in new window</a></td></tr>
										<tr><td>
										<table>
											<tr>
												<td><strong>Representation:</strong></td>
												<td style="width:500px;">
													<input type="radio" name="jmolDisplayType" value="select all; wireframe off; cpk off; cartoons;"/> Cartoon<br/>
													<input type="radio" name="jmolDisplayType" value="select all; cartoons off; wireframe off;cpk on;"/> CPK<br/>
													<input type="radio" name="jmolDisplayType" value="select all; cartoons off; cpk off; wireframe 40;" checked="checked"/> Wireframe<br/>
												</td>
											</tr>
										</table>
										<br/>
										</td></tr>
										<tr>
											<td><div id="jmolAppletDiv"></div>
										</td></tr></table> 
									</div>
								</c:when>
								<c:otherwise>
									<c:if test="${not empty file.description}">
										<h2><c:out value="${file.description}"/></h2>
									</c:if>
									<p>Cannot find any appropriate graphical representation for this data file.</p>
									<p>Click <a class="link" href="rest/services/download?uri=<c:out value="${file.absolutePath}"/>" target="_blank">here</a> to download this file.</p>
								</c:otherwise>
							</c:choose>
			
						</c:otherwise>
					</c:choose>
				</div>	
	
	<!-- for jquery dialogs -->
	
	<div id="error-message" title="Error"></div>
	<div id="info-message" title="Info"></div>
	<div id="confirm-message" title="Confirm"></div>
	
		</div></div>
	
	<!-- content-wrap ends here -->		
	</div></div>
	
<!-- wrap ends here -->
</div>

<!-- footer -->
<h:footer/>

</body>
</html>
