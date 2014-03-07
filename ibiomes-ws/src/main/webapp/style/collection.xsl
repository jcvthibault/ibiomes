<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="no" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" />
<xsl:template match="/collection">
	
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
			<h1>Simulation details &gt; <xsl:value-of select="metadata/attribute[@id='DUBLIN_TITLE']"/></h1>
	        <table>        	
		        <tr>
		        	<td><strong>Collection: </strong> <xsl:value-of select="metadata/attribute[@id='directory name']"/></td>
	        		<td><strong>Software package: </strong> <xsl:value-of select="metadata/attribute[@id='SOFTWARE']"/></td>
	        		<td>
	        			<strong>Molecule type: </strong> <xsl:for-each select="metadata/attribute[@id='MOLECULE_TYPE']">
					        <xsl:value-of select="."/>
					        <xsl:if test="not(position() = last())"> / </xsl:if>
					    </xsl:for-each>
					</td>
				</tr>
				<tr>
					<td><strong>Author: </strong> <xsl:value-of select="metadata/attribute[@id='directory owner']"/></td>
	        		<td><strong>Method: </strong> <xsl:value-of select="metadata/attribute[@id='METHOD']"/></td>
	        		<td><strong>Length: </strong> <xsl:value-of select="metadata/attribute[@id='TIME_LENGTH']"/> ms</td>
	        	</tr>
	        	<tr>
	        		<td><strong>Uploaded on: </strong> <xsl:value-of select="metadata/attribute[@id='DUBLIN_DATE']"/></td>
	        		<td><strong>Number of files: </strong> <xsl:value-of select="metadata/attribute[@id='TIME_STEPS']"/></td>
	        		<td><strong>Time steps: </strong> <xsl:value-of select="metadata/attribute[@id='TIME_STEPS']"/></td>
        		</tr>
        		<tr>
	        		<td colspan="3" style="txt-align:justify"><strong>Description: </strong> <xsl:value-of select="metadata/attribute[@id='DUBLIN_DESCRIPTION']"/><br/></td>
        		</tr>
	        </table>
	    </div>
		<br/>
		<!-- the tabs -->
		<ul class="tabs" style="list-style-image: none;">
			<li><a href="#generalinfo">Structure and parameters</a></li>
			<li><a href="#filesinfo">Associated files</a></li>
		</ul>
		<!-- tab "panes" -->
		<div class="panes">
			<div id="generalinfo" style="border:1px solid #DADADA; border-width:0 1px 1px 1px;">
			<br/>
				<h1>Molecule structure and simulation parameters</h1>
				<table style="width:700px">
					<tr>
						<td colspan="2" style="text-align:justify;">
							<strong>Residue chain: </strong> <xsl:value-of select="metadata/attribute[@id='RESIDUE_CHAIN']"/>
							<br/><br/><strong>Non-standard residues:</strong>
							<ul>
							<xsl:for-each select="metadata/attribute[@id='RESIDUE_NON_STD']">
								<li><xsl:value-of select="text()"/></li>
							</xsl:for-each>
							</ul>
						</td>
					</tr>
					<tr style="height:10px;"/>
			         	<td style="vertical-align:top;width:300px">
				           <strong>Number of atoms: </strong><xsl:value-of select="metadata/attribute[@id='COUNT_ATOM']"/>
				        </td>
				        <td style="vertical-align:top;border:1px" rowspan="10">
				            <applet name="jmol_small" code="JmolApplet" archive="../JmolApplet.jar" width="350" height="350">
								<param name="load" value="test.pdb"/>
								<param name="progressbar" value="true"/>
								<param name="bgcolor" value="#FFFFFF"/>
								<param name="script" value="select all; wireframe 50; set ambient 5; set specpower 40;"/>
							</applet>
		            	</td>
				    <tr><td><strong>Number of water molecules: </strong><xsl:value-of select="metadata/attribute[@id='COUNT_H2O']"/></td></tr>
			        <tr><td><strong>Number of ions: </strong><xsl:value-of select="metadata/attribute[@id='COUNT_ION']"/></td></tr>
			        <br/>
					<tr><td><strong>Force-field: </strong> 
						<xsl:for-each select="metadata/FORCE_FIELD">
						        <xsl:value-of select="."/>
						        <xsl:if test="not(position() = last())"> / </xsl:if>
						</xsl:for-each>
						</td>
					</tr>
					<tr><td><strong>Solvent: </strong> <xsl:value-of select="metadata/attribute[@id='SOLVENT']"/></td></tr>
					<tr><td><strong>Hydrogen modeling: </strong> <xsl:value-of select="metadata/attribute[@id='HYDOGEN']"/></td></tr>
					<tr><td><strong>Electrostatics modeling: </strong> <xsl:value-of select="metadata/attribute[@id='ELECTROSTATICS']"/></td></tr>
					<tr><td><strong>Unit shape: </strong> <xsl:value-of select="metadata/attribute[@id='UNIT_SHAPE']"/></td></tr>
					<tr><td><strong>Ensemble: </strong> <xsl:value-of select="metadata/attribute[@id='ENSEMBLE']"/></td></tr>
					<tr><td></td></tr>
	         	</table>
	         	
	         	<h1>Reference structure</h1>
	         	<table>
				<tr><td>
					<xsl:choose>
						<xsl:when test="metadata/attribute[@id='PDB_ID'] != ''">
			        		<strong>PDB entry: </strong>
					       		<a><xsl:attribute name="href">http://www.rcsb.org/pdb/explore/explore.do?structureId=<xsl:value-of select="metadata/attribute[@id='PDB_ID']"/></xsl:attribute>
					       			http://www.rcsb.org/pdb/explore/explore.do?structureId=<xsl:value-of select="metadata/attribute[@id='PDB_ID']"/></a>
	        			</xsl:when>
	        			<xsl:otherwise>
	        				<p>No structure reference for this simulation.<br/>If you are the owner you can point to an existing structure by specifying its PDB ID.</p>
	        			</xsl:otherwise>
	        		</xsl:choose>
	        		</td>
					</tr>
				</table>
			</div>
			
			<div id="filesinfo" style="border:1px solid #DADADA; border-width:0 1px 1px 1px;">
			<br/>
				<div id="filesinfo_raw">	
					<h1>Raw files (topology and trajectory files)</h1>
					  <form action="FileCollectionAction" method="get" class="blank">
			          <table width="700px">
						<tr>
			   				<th class="first" colspan="1">Content</th>
			   				<th>File</th>
			   				<th style="text-align:center">Type</th>
			   				<th style="text-align:right">Size</th>
			    		</tr>
			    		
	    				<xsl:for-each select="files/file">
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
		    		</form>
				</div>
				
				<div id="filesinfo_struct">	
					<h1>Structures (snapshots, averages, and clusters)</h1>
					  <form action="FileCollectionAction" method="get" class="blank">
			          <table width="700px">
						<tr>
			   				<th class="first" colspan="1">Content</th>
			   				<th>File</th>
			   				<th style="text-align:center">Type</th>
			   				<th style="text-align:right">Size</th>
			    		</tr>
			    		<xsl:for-each select="file">
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
		    		</form>
				</div>
				
				<div id="filesinfo_analysis">
					<h1>Analysis data files</h1>
			          <table width="700px">
						<tr>
			   				<th class="first" colspan="1">Content</th>
			   				<th>File</th>
			   				<th style="text-align:center">Type</th>
			   				<th style="text-align:right">Size</th>
			    		</tr>
			    		<xsl:for-each select="file">
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
				
				<div id="filesinfo_other">	
					<h1>Other files</h1>
					  
			          <table width="700px">
						<tr>
			   				<th class="first" colspan="1">Content</th>
			   				<th>File</th>
			   				<th style="text-align:center">Type</th>
			   				<th style="text-align:right">Size</th>
			    		</tr>
			    		<xsl:for-each select="file">
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

<script type="text/javascript" defer="true">
<xsl:comment>
<![CDATA[
	$(function() {
		$(".tabs:first").tabs(".panes:first > div");
	}); 
]]>
</xsl:comment>
</script>

</body>
</html>

	</xsl:template>	
</xsl:stylesheet>