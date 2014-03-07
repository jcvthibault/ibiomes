<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<h:meta/>

<script type="text/javascript">
	var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
</script>

<link type="text/css" rel="stylesheet" href="style/css/ibiomes.css" />
<link type="text/css" rel="Stylesheet" href="style/smoothness/jquery-ui-1.10.4.custom.css" /> 

<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/resources.js"></script>

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
		
		<div id="sidebar" >		

			<div class="sidebox">	
				<h1>Quick search</h1>	
				<form action="searchresults.do" class="searchform" method="post">
					<p>
					<input name="keyword" class="field" type="text"/>&nbsp;&nbsp;
  					<input style="border:0" type="image" src="images/icons/search_small.png" title="search"/>
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
						<img class="icon" src="images/icons/help_small.png" title="doucmentation" style="vertical-align:middle"/>&nbsp;&nbsp;Documentation
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
				<h1><img class="icon" src="images/icons/users.png"/>&nbsp;USER GROUP [<c:out value="${group.userGroupName}" />]</h1>
				<c:choose>
				<c:when test="${not empty groupUserList}">
					<table class="layout-tight">
					<c:forEach items="${groupUserList}" var="u">
						<tr><td>
								<a class="link" href="user.do?name=<c:out value="${u.name}" />">
								<c:choose>
									<c:when test="${u.userType eq 'RODS_USER'}"><img class="icon" src="images/icons/user_small.png" title="user"/></c:when>
									<c:when test="${u.userType eq 'RODS_ADMIN'}"><img class="icon" src="images/icons/user_small.png" title="administrator"/></c:when>
								</c:choose>
								</a>
							</td>
							<td><strong><c:out value="${u.name}" /></strong></td>
							<td style="width:10px"></td>
							<td><c:out value="${u.info}" /></td></tr>
					</c:forEach>
			    	</table>
			    </c:when>
			    <c:otherwise>No member found for this group.</c:otherwise>
			    </c:choose>
			</div>
			
			<div>
	       		<c:if test="${ not empty error }">
			   		<h:messageError message="${error}" />
				</c:if>
				<c:if test="${ not empty message }">
			   		<h:messageSuccess message="${message}" />
				</c:if>
		    </div>
	    	
			<br/>			
			<h1>Latest uploads by members of <i><c:out value="${group.userGroupName}" /></i></h1>
			
			<c:choose>
			<c:when test="${not empty simulationList}">
			<table>
			<tr>
   				<th class="first" width="30px">Info</th>
   				<th>Name</th>
   				<th>Method</th>
   				<th>Molecule</th>
   				<th>Created</th>
    		</tr>
			<c:forEach items="${simulationList}" var="sim" varStatus="rowCounter">
				<c:choose>
		        	<c:when test="${rowCounter.count % 2 == 0}">
		            	<c:set var="rowStyle" scope="page" value="row-a"/>
		        	</c:when>
		          	<c:otherwise>
		            	<c:set var="rowStyle" scope="page" value="row-b"/>
		          	</c:otherwise>
		        </c:choose>
				<tr class="<c:out value="${rowStyle}"/>">
    				<td class="first">
    					<a class="link" href="<c:out value="collection.do?uri=${sim.fileCollection.absolutePath}" />">
    						<img class="icon" src="images/icons/info_small.png" title="View details" />
    					</a>
    				</td>
    				<td><c:out value="${sim.fileCollection.name}"/></td>
    				<td><c:out value="${sim.method.name}"/></td>
    				<td>
    				<c:set var="moleculeList" value="" scope="request" />
    				<c:forEach items="${sim.molecularSystem.moleculeTypes}" var="molecule" varStatus="status" >
    					<c:set var="moleculeList" value="${moleculeList}${status.first ? '' : ' / '}${molecule}" scope="request" />
    				</c:forEach>
    				<c:out value="${moleculeList}"/>
    				</td>
    				<td><c:out value="${sim.registrationDate}"/></td>
    			</tr>
			</c:forEach>
	    	</table>
	    	</c:when>
	    	<c:otherwise><p>No experiment found.</p></c:otherwise>
	    	</c:choose>
	    	<br />
									
	</div>								
		
	<!-- content-wrap ends here -->		
	</div></div>

<!-- wrap ends here -->
</div>

<!-- footer -->
<h:footer/>

</body>
</html>
