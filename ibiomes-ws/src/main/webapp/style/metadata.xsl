<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="no" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" />

	<xsl:template match="/">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="Distribution" content="Global" />
<meta name="Author" content="Julien Thibault" />
<meta name="Robots" content="index,follow" />

<link rel="stylesheet" href="style/css/ibiomes.css" type="text/css" />

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
	<div id="content-wrap"><div id="content"><br/>
	<div id="main" style="width:100%"><br/>
		<div class="post">
		<h1>iBIOMES metadata definitions</h1>
		<table>
			<tr>
   				<th class="first">Code</th>
   				<th>Term</th>
   				<th>Definition</th>
    		</tr>
    		<xsl:for-each select="//metadataAttribute">
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
		&#169; copyright 2013 <strong><a href="http://www.utah.edu">University of Utah</a></strong><br /> 
		Design by: <a href="index.jsp"><strong>styleshout</strong></a> &#160;&#160;
		Valid <a href="http://jigsaw.w3.org/css-validator/check/referer"><strong>CSS</strong></a> | 
		      <a href="http://validator.w3.org/check/referer"><strong>XHTML</strong></a>
</div></div></div>

</body>
</html>

	</xsl:template>	
</xsl:stylesheet>
