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
<xsl:template match="/">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="Distribution" content="Global" />
<meta name="Author" content="Julien Thibault" />
<meta name="Robots" content="index,follow" />
<link type="text/css" rel="stylesheet" href="../../style/ibiomes-lite.css" />
<link type="text/css" rel="stylesheet" href="../../style/smoothness/jquery-ui-1.10.4.custom.css" />
<script type="text/javascript" src="../../js/jquery/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="../../js/jquery/js/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="../../js/jstree/_lib/jquery.cookie.js"></script>
<script type="text/javascript" src="../../js/jstree/_lib/jquery.hotkeys.js"></script>
<script type="text/javascript" src="../../js/jstree/jquery.jstree.js"></script>
<title>iBIOMES Lite</title>
</head>
<body>
<!-- wrap starts here -->
<div id="wrap">
	<div id="header"><div id="header-content">
		<h1 id="logo"><a href="../../index.html"><xsl:text>i</xsl:text><span class="gray"><xsl:text>BIOMES</xsl:text></span><span style="font-size:0.6em"><i><xsl:text> Lite</xsl:text></i></span></a></h1>
		<h2 id="slogan">Local indexing of biomolecular simulation data</h2>
		<!-- Menu Tabs -->
		<ul>
			<li><a href="../../index.html">My experiments</a></li>
			<li><a href="../../about.html">About</a></li>
		</ul>
	</div></div>
	<!-- content-wrap starts here -->
	<div id="content-wrap"><div id="content">
	<div id="main" style="width:100%"><p></p>
		<input id="rootDirPath" type="hidden">
			<xsl:attribute name="value"><xsl:value-of select="/ibiomes/directory/@absolutePath"/></xsl:attribute>
		</input>
		<input id="selectedFile" type="hidden"></input>
		<div id="top-header" class="post">
			<table class="layout-tight">
			<tr>
				<td style="width:500px">
					<h1>
						<xsl:text>[Experiment] </xsl:text>
						<span style="text-transform:uppercase;"><xsl:value-of select="/ibiomes/directory/@name"></xsl:value-of></span>
					</h1>
				</td>
				<td><img class="icon" src="../../images/icons/full_page_small.png" alt="summary" /></td>
				<td><a class="link" href="index.html"><xsl:text>Summary</xsl:text></a></td>
				<td class="separator"><xsl:text> </xsl:text></td>
				<td><img class="icon" src="../../images/icons/folder_full_small.png" alt="Files"></img></td>
				<td><strong><xsl:text>Browse files</xsl:text></strong></td>
				<td class="separator"><xsl:text> </xsl:text></td>
				<td><img class="icon" src="../../images/icons/process_small.png" alt="runs" /></td>
				<td><a class="link" href="runs.html"><xsl:text>Execution info</xsl:text></a><xsl:text> </xsl:text></td>
				<td class="separator"><xsl:text> </xsl:text></td>
				<td><img class="icon" src="../../images/icons/next_small.png" alt="Workflow" /></td>
				<td><a class="link" href="details.html"><xsl:text>Protocol</xsl:text></a></td>
			</tr>
			</table>
		</div>
		<p></p>
		<!-- =============== FILE TREEVIEW ============= -->
		<div class="treeview" style="width:250px;height:500px;">
			<div id="fileTree" style="background-color:white;">
				<ul>
					<xsl:call-template name="subdirTemplate">
	    				<xsl:with-param name="node" select="/ibiomes/directory/subdirectories"/>
	    			</xsl:call-template>
			  	</ul>
			</div>
		</div>
		<div style="margin-left:20px;width:625px;float:left">
			<xsl:call-template name="fileListTemplate">
				<xsl:with-param name="fileListNodes" select="//files"/>
			</xsl:call-template>
		</div>
		<br style="clear: left;"/>
		<br/>
	</div>
				
	<!-- content-wrap ends here -->		
	</div></div>

<br/>
<!-- wrap ends here -->
</div>
<div id="footer">
<div id="footer-content">
	<div style="text-align:center">
		<p><xsl:text>&#xA9; copyright 2014 </xsl:text><strong><a class="link" href="http://www.utah.edu">University of Utah</a></strong></p>
		<p><xsl:text>Design by: </xsl:text><a class="link" href="http://www.styleshout.com"><strong><xsl:text>styleshout</xsl:text></strong></a></p>
</div></div></div>

<script type="text/javascript" defer="defer">
<xsl:comment>
<![CDATA[		
$(function () {
	$("[id^=accordion]").accordion({
		collapsible: true,
		heightStyle: "content",
		animate: true,
		active: false
	});
	$("[id^=files_]").hide();
	
	$("#fileTree").jstree({ 
		"themes" : {
			"theme" : "default",
			"dots" : true,
			"icons" : true
		},
		"plugins" : [ "themes", "html_data", "ui", "crrm" ]
	})
	.bind("select_node.jstree", function (event, data) {
		var selectedId = data.rslt.obj.attr("id");
		selectDirectory(selectedId);
    })
    .bind("loaded.jstree", function (event, data) {
       data.inst.select_node('ul > li:first');
       data.inst.open_node('ul > li:first');
    });
});

function selectDirectory(id){
	$("[id^=files_]").hide();
	$("#files_"+id).show();
}
]]>
</xsl:comment>
</script>
</body>
</html>

</xsl:template>

<!-- =================== TEMPLATE FOR FILE BROWSER ========================= -->

<xsl:template name="fileListTemplate">
	<xsl:param name="fileListNodes"/>
	<xsl:for-each select="$fileListNodes">
		<div style="width:700px;">
			<xsl:attribute name="id">
				<xsl:text>files_</xsl:text>
				<xsl:call-template name="transform-path-to-id">
					<xsl:with-param name="text"><xsl:value-of select="../@name"/></xsl:with-param>
				</xsl:call-template>
			</xsl:attribute>
			<h3><xsl:text>Files in </xsl:text><i><span style="color:#65944A"><xsl:value-of select="../@name"/><xsl:text>/</xsl:text></span></i></h3>
			<xsl:choose><xsl:when test="../@externalURL and ../@externalURL!=''">
				<p>Browse directory at <a class="link" target="_blank">
				    <xsl:attribute name="href"><xsl:value-of select="../@externalURL"/></xsl:attribute>
				    <xsl:value-of select="../@externalURL"/></a></p>
			</xsl:when></xsl:choose>
			<p></p>
			<div style="width:700px;">
				<xsl:attribute name="id">
					<xsl:text>accordion_</xsl:text>
					<xsl:call-template name="transform-path-to-id">
						<xsl:with-param name="text"><xsl:value-of select="../@name"/></xsl:with-param>
					</xsl:call-template>
				</xsl:attribute>
				<xsl:choose>
				<xsl:when test="fileGroup">
					<xsl:for-each select="fileGroup">
				    	<xsl:sort select="@format"/>
						<xsl:variable name="format"><xsl:value-of select="@format"/></xsl:variable>
						<h3><a href="#"><xsl:value-of select="$format"/><xsl:text> files</xsl:text></a></h3>
						<div>
							<table>
				    		<xsl:for-each select="file">
				    			<xsl:sort select="@name"/>
				    			<tr>
				    				<td class="first">
				    				    <a class="link">
				    				    	<xsl:attribute name="href">
				    					    <xsl:choose>
				    					    <xsl:when test="@externalURL and @externalURL!=''"><xsl:value-of select="@externalURL"/></xsl:when>
				    					    <xsl:otherwise>#</xsl:otherwise>
				    					    </xsl:choose>
				    						</xsl:attribute>
					    				<img alt="file">
					    					<xsl:attribute name="title">
					    						<xsl:choose>
					    						<xsl:when test="@externalURL and @externalURL!=''"><xsl:value-of select="@externalURL"/></xsl:when>
					    					    <xsl:otherwise>File is not available for download</xsl:otherwise>
					    					    </xsl:choose>
					    					</xsl:attribute>
					    					<xsl:choose>
							    				<xsl:when test="($format = 'PDB') or ($format = 'GROMACS gro') or ($format = 'GAUSSIAN log') or ($format = 'NWChem output') or ($format = 'XYZ') or ($format = 'CML') or ($format = 'SDF')">
							    					<xsl:attribute name="src"><xsl:text>../../images/icons/mol_small.png</xsl:text></xsl:attribute>
							    				</xsl:when>
							    				<xsl:when test="($format = 'JPEG') or ($format = 'PNG') or ($format = 'GIF') or ($format = 'TIF')">
							    					<xsl:attribute name="src"><xsl:text>../../images/icons/image_small.png</xsl:text></xsl:attribute>
							    				</xsl:when>
							    				<xsl:when test="($format = 'CSV')">
							    					<xsl:attribute name="src"><xsl:text>../../images/icons/chart_small.png</xsl:text></xsl:attribute>
												</xsl:when>
							    				<xsl:otherwise>
							    					<xsl:attribute name="src"><xsl:text>../../images/icons/full_page_small.png</xsl:text></xsl:attribute>
							    				</xsl:otherwise>
							    			</xsl:choose>
					    				</img></a>
					    			</td>
				    				<td><xsl:value-of select="@name"/></td>
				    				<td style="text-align:right"><xsl:value-of select="format-number( round(@size div 1000), '#')"/><xsl:text> KB</xsl:text></td>
				    				<td><xsl:value-of select="@modificationDate"/></td>
				    			</tr>
				    		</xsl:for-each>
				    		</table>
				    	</div>
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise><p><xsl:text>No file available in this directory.</xsl:text></p></xsl:otherwise>
				</xsl:choose>
			</div>
		</div>
	</xsl:for-each>
</xsl:template>

<!-- ============================================ -->

<xsl:template name="subdirTemplate">
  <xsl:param name="node"/>
  	<li>
  		<xsl:attribute name="id">
  			<xsl:call-template name="transform-path-to-id">
				<xsl:with-param name="text" select="$node/../@name"/>
			</xsl:call-template>
  		</xsl:attribute>
  		<a href="#"><xsl:value-of select="$node/../@name"/></a>
   		<xsl:if test="$node[directory]">
   			<ul>
    			<xsl:for-each select="$node/directory">
    				<xsl:sort select="@absolutePath"></xsl:sort>
	    			<xsl:call-template name="subdirTemplate">
	    				<xsl:with-param name="node" select="subdirectories"/>
	    			</xsl:call-template>
    			</xsl:for-each>
    		</ul>
    	</xsl:if>
   	</li>
</xsl:template>

<!-- ============================================ -->

<xsl:template name="transform-path-to-id">
 <xsl:param name="text" />
  <xsl:variable name="newText">
	  <xsl:call-template name="string-replace-all">
	  	<xsl:with-param name="text" select="$text" />
	  	<xsl:with-param name="replace" select="'.'" />
	  	<xsl:with-param name="by" select="'_dot_'" />
	  </xsl:call-template>
  </xsl:variable>
  <xsl:variable name="newText2">
	  <xsl:call-template name="string-replace-all">
	  	<xsl:with-param name="text" select="$newText" />
	  	<xsl:with-param name="replace" select="'/'" />
	  	<xsl:with-param name="by" select="'_slash_'" />
	  </xsl:call-template>
  </xsl:variable>
  <xsl:value-of select="$newText2" disable-output-escaping="yes" />
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
