<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/D/tdxhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<h:meta/>

<script type="text/javascript">
	var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
</script>

<script type="text/javascript" src="js/resources.js"></script>

<link rel="stylesheet" href="style/css/ibiomes.css" type="text/css" />

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
			<li><a href="index.do">Home</a></li>
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
	<div id="content-wrap"><div id="content">
		
	<div id="main" style="width:100%">
	<div class="post">
		<h1><c:out value="${errorTitle}"/></h1>
		<p style="text-align:justify"><c:out value="${errorMsg}" escapeXml="false"/></p>
		<br/>
		<p>What are you looking for?</p>
		<ul>
			<li><a class="link" href="index.do">home</a></li>
			<li><a class="link" href="search.do">search</a></li>
			<li><a class="link" href="documentation.jsp">documentation</a></li>
			<li><a class="link" href="about.jsp">about</a></li>
		</ul>
	</div>
	
	<c:if test="${not empty errorTrace}">
		<br/>
		<div class="post">
			<h3>Details</h3>
			<p style="text-align:justify">
				<c:out value="${errorTrace}" escapeXml="false"/>
			</p>
		</div>
	</c:if>
	<br/>
	
	<br/>
	<br/>
	
	</div>
    
	<!-- content-wrap ends here -->		
	</div></div>
	
<!-- wrap ends here -->
</div>

<!-- footer -->
<h:footer/>

</body>
</html>
