<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="no" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" />

	<xsl:template match="/">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="Distribution" content="Global" />
<meta name="Author" content="Julien Thibault" />
<meta name="Robots" content="index,follow" />

<script src="http://cdn.jquerytools.org/1.2.5/full/jquery.tools.min.js"></script>

<link rel="stylesheet" href="http://juliens-grid-node.chpc.utah.edu:8080/ibiomes/style/css/ibiomes.css" type="text/css" />

<title>iBIOMES</title>

</head>
<body>

<!-- wrap starts here -->
<div id="wrap">
	<div id="header"><div id="header-content">	
		<h1 id="logo"><a href="index.jsp" title="">i<span class="gray">BIOMES</span></a></h1>
		<h2 id="slogan">Disseminating simulation data to create knowledge</h2>
		<!-- Menu Tabs -->
		<ul>
			<li><a href="index.jsp" id="current">Home</a></li>
			<li><a href="search.jsp">Search</a></li>
			<li><a href="documentation.jsp">Documentation</a></li>
			<li><a href="about.jsp">About</a></li>
		</ul>
	</div></div>
	<!-- content-wrap starts here -->
	<div id="content-wrap"><div id="content"><br/>
	<div id="main" style="width:100%"><br/>
		<div class="post">
		<h1>iBIOMES metadata definition</h1>
		<table>
			<tr>
   				<th class="first">Code</th>
   				<th>Term</th>
   				<th>Definition</th>
    		</tr>
    		<xsl:for-each select="//metadataValue">
    			<tr>
	   				<xsl:choose>
			            <xsl:when test="(position() mod 2) = 0">
			            	<xsl:attribute name="class">row-a</xsl:attribute>
			            </xsl:when>
			            <xsl:otherwise>
			            	<xsl:attribute name="class">row-b</xsl:attribute>
			            </xsl:otherwise>
			        </xsl:choose>
    					
    				<td class="first"><xsl:value-of select="code"/></td>
    				<td><xsl:value-of select="term"/></td>
    				<td><xsl:value-of select="definition"/></td>
    			</tr>
    		</xsl:for-each>
    	</table>
		</div>
	
	</div>
	
	<!-- content-wrap ends here -->		
	</div></div>

<!-- wrap ends here -->
</div>

<div id="footer">
<div id="footer-content">
	<div style="text-align:center">
		&#169; copyright 2011 <strong><a href="http://www.utah.edu">University of Utah</a></strong><br /> 
		Design by: <a href="index.jsp"><strong>styleshout</strong></a> &#160;&#160;
		Valid <a href="http://jigsaw.w3.org/css-validator/check/referer"><strong>CSS</strong></a> | 
		      <a href="http://validator.w3.org/check/referer"><strong>XHTML</strong></a>
</div></div></div>

</body>
</html>

	</xsl:template>	
</xsl:stylesheet>
