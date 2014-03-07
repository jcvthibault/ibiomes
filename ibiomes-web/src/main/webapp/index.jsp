<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript" src="js/collections.js"></script>
<script type="text/javascript" src="js/dialogs.js"></script>
<script type="text/javascript" src="js/search.js"></script>
<script type="text/javascript" src="js/resources.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	
	getLatestUploads("user-uploads");
	
	$("#results").dialog({
		resizable: true,
		height: 700,
        width: 780,
		autoOpen: false,
		modal: true,
		buttons: {
			Close: function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
	$("#kewordSearchButton").click(function() {
		$("#results").dialog('open');
		executeExperimentSearch(20, 'keyword', 'searchform', 0);
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
		<h2 id="slogan">Integrated Biomolecular Simulations</h2>
		
		<!-- Menu Tabs -->
		<ul>
			<li><a href="index.do" id="current">Home</a></li>
			<li><a href="search.do">Search</a></li>
			<li><a href="documentation.jsp">Documentation</a></li>
			<li><a href="about.jsp">About</a></li>
			<li><a href="cart.do"><h:cart/></a></li>
		</ul>
		<!-- login/logout link -->
		<h:login/>
	
	</div></div>
	
	<div class="headerphoto"></div>
	<br/>
			
	<!-- content-wrap starts here -->
	<div id="content-wrap">
	<div id="content">
		
		<div id="sidebar">		

			<div class="sidebox">	
				<h1>Search</h1>	
				<form id="searchform" class="searchform">
					<p>
					<input name="keyword" class="field" type="text"/>&nbsp;&nbsp;
  					<a id="kewordSearchButton" style="cursor:pointer" title="search">
  						<img class="icon" src="images/icons/search_small.png"/>
  					</a>
			        <input type="hidden" name="type" value="keyword"/>
					</p>
				</form>
			</div>
			
			<div class="sidebox">
				<h1 class="clear">Quick links</h1>
				<ul class="sidemenu">
					<c:if test="${not empty sessionScope.homeDirectory}">
						<li><a href="collection.do?uri=<c:out value="${sessionScope.homeDirectory}" />">
							<img class="icon" src="images/icons/user_small.png" title="home directory" style="vertical-align:middle"/>&nbsp;&nbsp;Home directory
						</a></li>
						<li><a href="experimentsetmanager.do">
							<img class="icon" src="images/icons/folder_full_small.png" title="experiment sets" style="vertical-align:middle"/>&nbsp;&nbsp;Experiment sets
						</a></li>
					</c:if>
					<li><a href="search.do">
						<img class="icon" src="images/icons/search_small.png" title="search" style="vertical-align:middle"/>&nbsp;&nbsp;Search simulations
					</a></li>
					<li><a href="documentation.jsp">
						<img class="icon" src="images/icons/help_small.png" title="What is iBIOMES? How do I install/use it?" style="vertical-align:middle"/>&nbsp;&nbsp;Documentation
					</a></li>
					<li><a href="about.jsp">
						<img class="icon" src="images/icons/info_small.png" title="about us" style="vertical-align:middle"/>&nbsp;&nbsp;About us
					</a></li>
				</ul>
			</div>
			
			<div class="sidebox">
				<h1 class="clear">Contributors</h1>
				<ul class="sidemenu">
					<li><a href="http://www.bmi.utah.edu">University of Utah</a></li>
					<li><a href="http://www.chpc.utah.edu/~cheatham/">Cheatham Lab</a></li>
					<li><a href="http://www.chpc.utah.edu">CHPC</a></li>
				</ul>	
			</div>
			
					
		</div>	
	
		<div id="main">	
			
			<div>
	       		<c:if test="${ not empty error }">
			   		<h:messageError message="${error}" />
				</c:if>
				<c:if test="${ not empty message }">
			   		<h:messageSuccess message="${message}" />
				</c:if>
		    </div>
		    
			<h1>The iBIOMES project</h1>
			<p>iBIOMES is a distributed infrastructure that allows researchers to share, search and analyze biomololecular simulation data.
			The iBIOMES project aims to:</p> 
			<ul>
				<li>Improve the workflow at the researcher's lab through a searchable distributed file repository that can be setup locally.</li>
				<li>Allow collaboration at the community level through grid services for data publication, retrieval, and visualization.</li>
			</ul>
				
			<h1>Latest uploads</h1>
			<div id="latestUploads">
				<div id="user-uploads"></div>
			</div>
			
	    	<br />
	    	
			<!-- SEARCH RESULTS  -->
			<div id="results" title="Keyword search results">
				<br/>
				<div id="search-pages-links"></div>
				<br/>
				<div id="search-results"></div>
				<div id="search-loading" style="text-align:center;display:none;"><img src="animations/loading50.gif"></img></div>
				<br/>
				<p>Need more <a class="link" href="search.do">search options</a>?</p>
			</div>
				
			<!-- for jquery dialogs -->
	
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
