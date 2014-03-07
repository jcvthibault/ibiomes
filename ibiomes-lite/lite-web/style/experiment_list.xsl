<xsl:stylesheet version="2.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns="http://www.w3.org/1999/xhtml">
<xsl:output method="html" indent="no" encoding="ISO-8859-1"
	doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
    doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" />
   
<!-- ============ NORMALIZE HTML CODE AND TEXT ================ -->
<xsl:strip-space elements="*"/>

<xsl:template match="node()|@*">
	<xsl:copy>
		<xsl:apply-templates select="node()|@*"/>
	</xsl:copy>
</xsl:template>

<xsl:template match="text()">
    <xsl:value-of select="normalize-space()" />
</xsl:template>

<!-- ============ TRANSFORMATION ================ -->
<xsl:template match="/ibiomes">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="Distribution" content="Global" />
<meta name="Author" content="Julien Thibault" />
<meta name="Robots" content="index,follow" />
<link type="text/css" rel="stylesheet" href="style/ibiomes-lite.css" />
<link type="text/css" rel="stylesheet" href="style/smoothness/jquery-ui-1.10.4.custom.css" />
<script type="text/javascript" src="js/jquery/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery/js/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="js/tablesorter/jquery.tablesorter.js"></script>
<title>iBIOMES Lite</title>
</head>
<body>
<!-- wrap starts here -->
<div id="wrap">
	<div id="header"><div id="header-content">
		<h1 id="logo"><xsl:text>i</xsl:text><span class="gray"><xsl:text>BIOMES</xsl:text></span><span style="font-size:0.6em"><i><xsl:text> Lite</xsl:text></i></span></h1>
		<h2 id="slogan">Local indexing of biomolecular simulation data</h2>
		<!-- Menu Tabs -->
		<ul>
			<li><a href="index.html" id="current">My experiments</a></li>
			<li><a href="about.html">About</a></li>
		</ul>
	</div></div>
	<!-- content-wrap starts here -->
	<div id="content-wrap"><div id="content">
	<div id="main" style="width:100%"><p></p>
		<table id="experimentList" class="experiments-list" style="margin:0; padding:0;width:100%">
			<thead>
				<tr>
					<th style="text-align:center">Method</th>
					<th>Name</th>
					<th>Software</th>
					<th style="text-align:center">Molecules</th>
					<th>Publisher</th>
					<th style="width:100px">Date</th>
					<th>Experiment path</th>
				</tr>
			</thead>
			<tbody>
			<xsl:for-each select="directory">
			    <tr class="experiments-list-item pointer">
					<xsl:attribute name="onclick">window.location='experiments/<xsl:value-of select="@id"/>/index.html';</xsl:attribute>
			        	<td style="text-align:center">
			        		<xsl:variable name="methodName" select="AVUs/AVU[@id='METHOD']"></xsl:variable>
			        		<xsl:choose>
			    				<xsl:when test="$methodName = 'QM/MM'">
			            			<img class="icon" height="20px" src="images/qmmm_logo.png" title="QM/MM" alt="QM/MM"/>
			    				</xsl:when>
				        		<xsl:when test="$methodName = 'Quantum mechanics'">
									<img class="icon" height="20px" src="images/qm_icon_small.png" title="Quantum calculations" alt="QM"/>
			        			</xsl:when>
			        			<xsl:when test="$methodName = 'Quantum MD'">
									<img class="icon" height="20px" src="images/qm_icon_small.png" title="Quantum MD" alt="QMD"/>
			        			</xsl:when>
					        	<xsl:when test="$methodName = 'Molecular dynamics'">
			            			<img class="icon" height="20px" src="images/md_logo.png" title="Molecular dynamics simulation" alt="MD"/>
			    				</xsl:when>
			    				<xsl:when test="$methodName = 'Replica-exchange MD'">
			            			<img class="icon" height="20px" src="images/remd_logo.png" title="Replica-exchange" alt="REMD"/>
			    				</xsl:when>
		    				</xsl:choose>
		        		</td>
		        		<td>
		        			<span style="text-transform:uppercase;">
	      						<strong><xsl:value-of select="@name"/></strong>
	      					</span>
	      				</td>
	      				<td>
	    					<xsl:if test="AVUs/AVU[@id='SOFTWARE_NAME']">
	    						<xsl:for-each select="AVUs/AVU[@id='SOFTWARE_NAME']">
	     							<xsl:value-of select="."/>
	     							 <xsl:if test="not(position() = last())">, </xsl:if>
	     						</xsl:for-each>
	     					</xsl:if>
	     				</td>
	      				<td style="text-align:center"><i>
		     					<xsl:if test="AVUs/AVU[@id='MOLECULE_TYPE']">
		      						<xsl:for-each select="AVUs/AVU[@id='MOLECULE_TYPE']">
										<xsl:choose>
											<xsl:when test="text() = 'Compound'">
												<xsl:variable name="comp1">
												  <xsl:call-template name="string-replace-all">
												    <xsl:with-param name="text" select="../AVU[@id='MOLECULE_ATOMIC_COMPOSITION2'][1]" />
												    <xsl:with-param name="replace" select="'['" />
												    <xsl:with-param name="by" select="'&lt;sub&gt;'" />
												  </xsl:call-template>
												</xsl:variable>
												<xsl:variable name="comp2">
												  <xsl:call-template name="string-replace-all">
												    <xsl:with-param name="text" select="$comp1" />
												    <xsl:with-param name="replace" select="']'" />
												    <xsl:with-param name="by" select="'&lt;/sub&gt;'" />
												  </xsl:call-template>
												</xsl:variable>
												<xsl:value-of select="$comp2" disable-output-escaping="yes" />
											</xsl:when>
											<xsl:otherwise><xsl:value-of select="."/></xsl:otherwise>
										</xsl:choose>
								        <xsl:if test="not(position() = last())"> / </xsl:if>
							    	</xsl:for-each>
							    </xsl:if></i>
		    				</td>
	      				<td style="text-align:center"><xsl:value-of select="@publisher"/></td>
	      				<td><xsl:value-of select="@publicationDate"/></td>
	      				<td>
	      					<xsl:call-template name="shorten-paths">
								<xsl:with-param name="count">50</xsl:with-param>
								<xsl:with-param name="myString" select="@absolutePath"/>
							</xsl:call-template>
	      				</td>
			        	</tr>
				</xsl:for-each>
			</tbody>
		</table>
	</div>
	<!-- content-wrap ends here -->		
	</div></div>

<br/>
<!-- wrap ends here -->
</div>

<!-- ====================== FOOTER =========================== -->

<div id="footer">
<div id="footer-content">
	<div style="text-align:center">
		<p><xsl:text>&#xA9; copyright 2014 </xsl:text><strong><a class="link" href="http://www.utah.edu">University of Utah</a></strong></p>
		<p><xsl:text>Design by: </xsl:text><a class="link" href="http://www.styleshout.com"><strong><xsl:text>styleshout</xsl:text></strong></a></p>
</div></div></div>

<!-- ====================== JAVASCRIPTS ======================= -->

<script type="text/javascript" defer="defer">
<xsl:comment>
<![CDATA[
$(document).ready(function() {
    $("#experimentList").tablesorter({
	    textExtraction:function(s){
	        if($(s).find('img').length == 0) return $(s).text();
	        return $(s).find('img').attr('alt');
	    },
    	cssAsc: "headerSortUp",
    	cssDesc: "headerSortDown",
    	cssHeader: "headerNormal"
    });
});
]]>
</xsl:comment>
</script>

</body>
</html>

</xsl:template>

<!-- ================== CUT LONG LINES WITH BREAKING SPACES ========================== -->
<xsl:template name="shorten-paths">
	<xsl:param name="count"/>
	<xsl:param name="myString"/> 
	<xsl:choose>
		<xsl:when test="$count &lt; string-length($myString) ">
			<span class="pointer">
				<xsl:attribute name="title"><xsl:value-of select="$myString"/></xsl:attribute>
				<xsl:value-of select="substring($myString, 0, $count)"/>...
			</span>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$myString"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- ============================================ -->

<xsl:template name="string-replace-all">
  <xsl:param name="text" />
  <xsl:param name="replace" />
  <xsl:param name="by" />
  <xsl:choose>
    <xsl:when test="contains($text, $replace)">
      <xsl:value-of select="substring-before($text,$replace)" disable-output-escaping="yes" />
      <xsl:value-of select="$by" disable-output-escaping="yes" />
      <xsl:call-template name="string-replace-all">
        <xsl:with-param name="text"
        select="substring-after($text,$replace)" />
        <xsl:with-param name="replace" select="$replace" />
        <xsl:with-param name="by" select="$by" />
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$text" disable-output-escaping="yes"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>
