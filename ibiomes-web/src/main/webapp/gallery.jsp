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
			<li><a id="current" href="documentation.jsp">Documentation</a></li>
			<li><a href="about.jsp">About</a></li>
			<li><a href="cart.do"><h:cart/></a></li>
		</ul>
		<!-- login/logout link -->
		<h:login/>
	
	</div></div>
	<br/>
	
	<!-- content-wrap starts here -->
	<div id="content-wrap"><div id="content">
		
	<div id="main" style="width:100%">
	<div class="post">
	
	<h1>Gallery (work in progress)</h1>
	<br/>
	<h3>Main interface</h3>
	<table style="text-align:center">
	<tr><td><a target="_blank" href="images/gallery/ibiomes-home.png" title="home page">
				<img src="images/gallery/ibiomes-home.png" height="160px"/></a>
		</td>
	    <td><a target="_blank" href="images/gallery/ibiomes-experiment.png" title="experiment summary" >
	    		<img src="images/gallery/ibiomes-experiment.png" height="160px"/></a>
	    </td>
	    <td><a target="_blank" href="images/gallery/ibiomes-search-md.png" title="MD data search">
	   			<img src="images/gallery/ibiomes-search-md.png" height="160px"/></a>
	   	</td>
	    <td><a target="_blank" href="images/gallery/ibiomes-search-results.png" title="Search results">
	    		<img src="images/gallery/ibiomes-search-results.png" height="160px"/></a>
	    </td>
	</tr>
	</table>
	<br/>
	
	<h3>Dynamic data visualization</h3>
	<table style="text-align:center">
	    <td><a target="_blank" href="images/gallery/ibiomes-plot-rmsd.png" title="RMSd plot">
	    		<img src="images/gallery/ibiomes-plot-rmsd.png" height="100px"/></a>
	    </td>
	    <td><a target="_blank" href="images/gallery/ibiomes-plot-cumul.png" title="Heatmap for time-dependent correlation analysis">
	    		<img src="images/gallery/ibiomes-plot-cumul.png" height="100px"/></a>
	    </td>
	    <td><a target="_blank" href="images/gallery/ibiomes-plot-heatmap.png" title="2D RMSd matrix">
	    		<img src="images/gallery/ibiomes-plot-heatmap.png" height="100px"/></a>
	    </td>
	    <td><a target="_blank" href="images/gallery/ibiomes-jmol.png" title="3D structure visualization with Jmol">
	    		<img src="images/gallery/ibiomes-jmol.png" height="100px"/></a>
	    </td>
	    <td><a target="_blank" href="images/gallery/ibiomes-jmol2.png" title="3D structure visualization with Jmol">
	    		<img src="images/gallery/ibiomes-jmol2.png" height="100px"/></a>
	    </td>
	</tr>
	</table>


	</div></div>
    
	<!-- content-wrap ends here -->		
	</div></div>
	
<!-- wrap ends here -->
</div>

<!-- footer -->
<h:footer/>

</body>
</html>
