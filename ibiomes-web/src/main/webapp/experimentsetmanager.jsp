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

<script type="text/javascript" src="js/experimentSet.js"></script>
<script type="text/javascript" src="js/dialogs.js"></script>
<script type="text/javascript" src="js/search.js"></script>
<script type="text/javascript" src="js/resources.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	
	getListOfExperimentSets('experimentSetList');
	
	$("#deleteSetsButton").click(function() {
		var nExpSets = $("input[name='expSetSelect']:checked").length;
		if (nExpSets>0){
			$("#deleteSetsDialogText").html("You are about to delete "+nExpSets+" experiment set(s).<br/>Continue?");
			$("#deleteSetsDialog").dialog('open');
		}
	});
	
	$( "#deleteSetsDialog" ).dialog({
		height: 170,
	    width: 250,
	    autoOpen: false,
		modal: true,
	    buttons: {
	        "Delete": function() {
	        	var idsInput = $("input[name='expSetSelect']:checked");
	        	var idsStr = "";
	        	var i = 0;
	        	for (i=0; i<idsInput.length; i++){
	        		idsStr += "," + idsInput[i].value;
	        	}
	        	idsStr = idsStr.substring(1);
	        	deleteExperimentSets(idsStr);
	        	getListOfExperimentSets('experimentSetList');
	            $( this ).dialog( "close" );
	        },
	        Cancel: function() {
	            $( this ).dialog( "close" );
	        }
	    }
	});

	$("#createSetButton").click(function() {
		$("#setNameN").val('');
    	$("#setDescN").val('');
    	$("#setPublicN").prop('checked', false);
		$("#createSetDialog").dialog('open');
	});
	
	$( "#createSetDialog" ).dialog({
		height: 250,
	    width: 330,
	    autoOpen: false,
		modal: true,
	    buttons: {
	        "Create": function() {
	        	var name = $("#setNameN").val();
	        	var desc = $("#setDescN").val();
	        	var publicFlag = ($("#setPublicN").is(":checked"));
	        	if (name.length>0){
	            	createExperimentSet(name, desc, publicFlag);
	            	getListOfExperimentSets('experimentSetList');
	                $( this ).dialog( "close" );
	        	}
	        },
	        Cancel: function() {
	            $( this ).dialog( "close" );
	        }
	    }
	});
});

</script type="text/javascript">

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
			<div class="post">
				<h1>Experiment sets</h1>
				<p>Define and manage your experiments sets.</p>
				<br/>
				<h3>Current experiment sets</h3>
				<div id="experimentSetList"></div>
				<p>
					<img class="pointer icon" id="createSetButton" title="Create new set..." src="images/icons/add_small.png"></img>
					<img class="pointer icon" id="deleteSetsButton" title="Delete selected set(s)..." src="images/icons/delete_small.png"></img>
				</p>
			</div>
			<!-- for jquery dialogs -->
			
			<!--  create experiment set -->
	    	<div id="createSetDialog" title="Create experiment set">
				<table class="layout" style="text-align:left">
					<tr><td><strong>Name</strong></td><td><input id="setNameN" value="" type="text" /></td></tr>
					<tr><td colspan="2"><strong>Description</strong></td></tr>
					<tr><td colspan="2"><textarea id="setDescN" cols="20" rows="3" style="width:240px;height:40px"></textarea></td></tr>
					<tr><td><strong>Public?</strong></td><td>&nbsp;<input id="setPublicN" value="true" type="checkbox" /></td></tr>
				</table>
			</div>
			<!--  delete experiment sets -->
	    	<div id="deleteSetsDialog" title="Delete">
				<div id="deleteSetsDialogText"></div>
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
