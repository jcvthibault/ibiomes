<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<meta name="Description" content="iBIOMES Integrated Biomolecular simulations" />
<meta name="Keywords" content="iBIOMES Biomolecular simulations quantum chemistry molecular dynamics" />
<meta name="Distribution" content="Global" />
<meta name="Author" content="Julien Thibault, University of Utah" />
<meta name="Robots" content="index,follow" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

<link type="text/css" rel="stylesheet" href="style/css/ibiomes.css" />
<link type="text/css" rel="Stylesheet" href="style/smoothness/jquery-ui-1.8.20.custom.css" /> 

<title>iBIOMES</title>

</head>

<body>
<!-- wrap starts here -->
<div id="wrap">

	<div id="header"><div id="header-content">	
		<h1 id="logo">i<span class="gray">BIOMES</span><span style="font-size:0.6em"><i>-WS</i></span></h1>
		<h2 id="slogan">Metadata catalog for biomolecular simualtions</h2>
	</div></div>
	<br/>
			
	<!-- content-wrap starts here -->
	<div id="content-wrap">
	<div id="content">
		<div id="main" style="width:100%">
			<div class="post">
				<h1>REST services</h1><br/>
				<h3>Definitions</h3>
				<table>
					<tr><th class="first">Service</th><th>URI</th></tr>
					<tr class="row-a">
						<td class="first">List all attributes</td>
						<td><span class="code">rest/metadata</span></td>
					</tr>
					<tr class="row-b">
						<td class="first">Get attribute</td>
						<td><span class="code">rest/metadata/[attribute code]</span></td>
					</tr>
					<tr class="row-a">
						<td class="first">Get possible values for attribute</td>
						<td><span class="code">rest/metadata/[attribute code]/values</span></td>
					</tr>
				</table>
				<br/>
				<h3>Examples</h3>
				<table>
					<tr>
						<td class="first">List all attributes</td>
						<td><a class="link code" href="rest/metadata" target="_blank">
							<c:out value="${pageContext.request.contextPath}"/>/rest/metadata</a></td>
					</tr>
					<tr>
						<td class="first">Get the definition of FORCE_FIELD attribute</td>
						<td><a class="link code" href="rest/metadata/FORCE_FIELD" target="_blank">
							<c:out value="${pageContext.request.contextPath}"/>/rest/metadata/FORCE_FIELD</a></td>
					</tr>
					<tr>
						<td class="first">Get catalog of force fields</td>
						<td><a class="link code" href="rest/metadata/FORCE_FIELD/values" target="_blank">
							<c:out value="${pageContext.request.contextPath}"/>/rest/metadata/FORCE_FIELD/values</a></td>
					</tr>
				</table>
			</div>
			<br/>
			<div class="post">
				<h1>Resources</h1>
				<ul>
					<li><a class="link" href="http://ibiomes.chpc.utah.edu/ibiomes-web/" target="_blank">iBIOMES web portal</a></li>
					<li><a class="link" href="http://ibiomes.chpc.utah.edu/ibiomes-web/documentation.jsp" target="_blank">iBIOMES documentation</a></li>
					<li><a class="link" href="http://home.chpc.utah.edu/~cheatham/" target="_blank">Cheatham Lab</a></li>
				</ul>
			</div>
		</div>
	<!-- content-wrap ends here -->		
	</div>
	</div>

	<!-- footer -->
	<div id="footer">
	<div id="footer-content">
		<div style="text-align:center">
			&copy; copyright 2013 <strong><a class="link" href="http://www.utah.edu">University of Utah</a></strong><br /> 
			Design by: <a class="link" href="http://www.styleshout.com"><strong>styleshout</strong></a>
	</div></div></div>

<!-- wrap ends here -->
</div>

</body>
</html>
