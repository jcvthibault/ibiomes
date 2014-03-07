<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="no" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" />

	<xsl:template match="/fileMetadataSets">
	
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
		
		
		<h1>Files</h1>
	
	<!-- ===================== Raw files (topology and trajectory files) ====================== -->
		<div>		
			<h1>Raw files (topology and trajectory files)</h1>
			  
	          <table width="700px">
				<tr>
	   				<th class="first">Content</th>
	   				<th>File</th>
	   				<th style="text-align:center">Type</th>
	   				<th style="text-align:right">Size</th>
	    		</tr>

	    		<xsl:for-each select="file">
	    		<xsl:if test="(metadata/attribute[@id='FILE_TYPE']='TOPOLOGY' or metadata/attribute[@id='FILE_TYPE']='TRAJECTORY') and metadata/attribute[@id='FILE_FORMAT']!='PDB'">
    				<tr>
	   				<xsl:choose>
			            <xsl:when test="(position() mod 2) = 0">
			            	<xsl:attribute name="class">row-a</xsl:attribute>
			            </xsl:when>
			            <xsl:otherwise>
			            	<xsl:attribute name="class">row-b</xsl:attribute>
			            </xsl:otherwise>
			        </xsl:choose>
    				
    				<!-- for download  -->
    				<td><a href=""><img class="icon" src="http://juliens-grid-node.chpc.utah.edu:8080/ibiomes/images/icons/download.png" title="download..."/></a></td>
    				
    				<!-- file info -->
    				<td><xsl:value-of select="metadata/attribute[@id='file name']"/></td>
    				<td style="text-align:center">
    					<xsl:value-of select="metadata/attribute[@id='FILE_TYPE']"/> 
    					(<xsl:value-of select="metadata/attribute[@id='FILE_FORMAT']"/>)
    				</td>
    				<td style="text-align:right"><xsl:value-of select="metadata/attribute[@id='file size']"/> KB</td>
    				</tr>
    			</xsl:if>
    			</xsl:for-each>
    		</table>
		</div>
		
		
		<!-- ===================== Structures (snapshots, averages, and clusters) ====================== -->
		<div>		
			<h1>Structures (snapshots, averages, and clusters)</h1>
			  <table width="700px">
				<tr>
	   				<th class="first">Content</th>
	   				<th>File</th>
	   				<th style="text-align:center">Type</th>
	   				<th style="text-align:right">Size</th>
	    		</tr>

	    		<xsl:for-each select="files/file">
	    		<xsl:if test="metadata/attribute[@id='FILE_FORMAT']='PDB'">
    				<tr>
	   				<xsl:choose>
			            <xsl:when test="(position() mod 2) = 0">
			            	<xsl:attribute name="class">row-a</xsl:attribute>
			            </xsl:when>
			            <xsl:otherwise>
			            	<xsl:attribute name="class">row-b</xsl:attribute>
			            </xsl:otherwise>
			        </xsl:choose>
    				
    				<!-- for download  -->
    				<td><a href=""><img class="icon" src="http://juliens-grid-node.chpc.utah.edu:8080/ibiomes/images/icons/download.png" title="download..."/></a></td>
    				
    				<!-- file info -->
    				<td><xsl:value-of select="metadata/attribute[@id='file name']"/></td>
    				<td style="text-align:center">
    					<xsl:value-of select="metadata/attribute[@id='FILE_TYPE']"/> 
    					(<xsl:value-of select="metadata/attribute[@id='FILE_FORMAT']"/>)
    				</td>
    				<td style="text-align:right"><xsl:value-of select="metadata/attribute[@id='file size']"/> KB</td>
    				</tr>
    			</xsl:if>
    			</xsl:for-each>
    		</table>
		</div>
		
		
		<!-- ===================== Analysis data files ====================== -->
		<div>		
			<h1>Analysis data files</h1>
	          <table width="700px">
				<tr>
	   				<th class="first">Content</th>
	   				<th>File</th>
	   				<th style="text-align:center">Type</th>
	   				<th style="text-align:right">Size</th>
	    		</tr>

	    		<xsl:for-each select="files/file">
	    		<xsl:if test="metadata/attribute[@id='FILE_TYPE']='ANALYSIS'">
    				<tr>
	   				<xsl:choose>
			            <xsl:when test="(position() mod 2) = 0">
			            	<xsl:attribute name="class">row-a</xsl:attribute>
			            </xsl:when>
			            <xsl:otherwise>
			            	<xsl:attribute name="class">row-b</xsl:attribute>
			            </xsl:otherwise>
			        </xsl:choose>
    				
    				<!-- for download  -->
    				<td><a href=""><img class="icon" src="http://juliens-grid-node.chpc.utah.edu:8080/ibiomes/images/icons/download.png" title="download..."/></a></td>
    				
    				<!-- file info -->
    				<td><xsl:value-of select="metadata/attribute[@id='file name']"/></td>
    				<td style="text-align:center">
    					<xsl:value-of select="metadata/attribute[@id='FILE_TYPE']"/> 
    					(<xsl:value-of select="metadata/attribute[@id='FILE_FORMAT']"/>)
    				</td>
    				<td style="text-align:right"><xsl:value-of select="metadata/attribute[@id='file size']"/> KB</td>
    				</tr>
    			</xsl:if>
    			</xsl:for-each>
    		</table>
		</div>
		
		<!-- ===================== Other files ====================== -->
		<div>		
			<h1>Other files</h1>
			  
	          <table width="700px">
				<tr>
	   				<th class="first">Content</th>
	   				<th>File</th>
	   				<th style="text-align:center">Type</th>
	   				<th style="text-align:right">Size</th>
	    		</tr>

	    		<xsl:for-each select="files/file">
	    		<xsl:if test="metadata/attribute[@id='FILE_TYPE'] = 'OTHER'">
    				<tr>
	   				<xsl:choose>
			            <xsl:when test="(position() mod 2) = 0">
			            	<xsl:attribute name="class">row-a</xsl:attribute>
			            </xsl:when>
			            <xsl:otherwise>
			            	<xsl:attribute name="class">row-b</xsl:attribute>
			            </xsl:otherwise>
			        </xsl:choose>
    				
    				<!-- for download  -->
    				<td><a href=""><img class="icon" src="http://juliens-grid-node.chpc.utah.edu:8080/ibiomes/images/icons/download.png" title="download..."/></a></td>
    				
    				<!-- file info -->
    				<td><xsl:value-of select="metadata/attribute[@id='file name']"/></td>
    				<td style="text-align:center">
    					<xsl:value-of select="metadata/attribute[@id='FILE_TYPE']"/> 
    					(<xsl:value-of select="metadata/attribute[@id='FILE_FORMAT']"/>)
    				</td>
    				<td style="text-align:right"><xsl:value-of select="metadata/attribute[@id='file size']"/> KB</td>

    				</tr>
    			</xsl:if>
    			</xsl:for-each>
    		</table>
		</div>
		
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
