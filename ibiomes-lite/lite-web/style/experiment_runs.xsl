<xsl:stylesheet version="2.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:fn="http://www.w3.org/2005/xpath-functions"
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
<xsl:template match="/experiment">

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
		<div id="top-header" class="post">
			<table class="layout-tight">
				<tr>
					<td style="width:500px">
						<h1>
							<xsl:text>[Experiment] </xsl:text>
							<span style="text-transform:uppercase;"><xsl:value-of select="@name"></xsl:value-of></span>
						</h1>
					</td>
					<td><img class="icon" src="../../images/icons/full_page_small.png" alt="summary" /></td>
					<td><a class="link" href="index.html"><xsl:text>Summary</xsl:text></a></td>
					<td class="separator"><xsl:text> </xsl:text></td>
					<td><img class="icon" src="../../images/icons/folder_full_small.png" alt="Files"></img></td>
					<td><a class="link" href="files.html"><xsl:attribute name="title"><xsl:value-of select="@rootDirectoryPath"/></xsl:attribute><xsl:text>Browse files</xsl:text></a></td>
					<td class="separator"><xsl:text> </xsl:text></td>
					<td><img class="icon" src="../../images/icons/process_small.png" alt="runs" /></td>
					<td><strong><xsl:text>Execution info</xsl:text></strong></td>
					<td class="separator"><xsl:text> </xsl:text></td>
					<td><img class="icon" src="../../images/icons/next_small.png" alt="Workflow" /></td>
					<td><a class="link" href="details.html"><xsl:text>Protocol</xsl:text></a></td>
				</tr>
			</table>
		</div>
<!-- ====================== EXPERIMENT PROCESS GROUP SUMMARIES =========================== -->
<p></p>
<table class="layout">
  	<tr>
		<td style="vertical-align:top;">
			<table class="layout-tight">
				<tr>
					<td class="label-right"><xsl:text>Execution time</xsl:text></td>
					<td class="separator"><xsl:text> </xsl:text></td>
					<td>
						<xsl:choose>
							<xsl:when test="tasksSummary/executionTime/@value">
								<xsl:variable name="execTimeValue"><xsl:value-of select="tasksSummary/executionTime/@value"></xsl:value-of></xsl:variable> 
								<xsl:variable name="execTimeUnit"><xsl:value-of select="tasksSummary/executionTime/@unit"></xsl:value-of></xsl:variable> 
								<xsl:choose>
									<xsl:when test="$execTimeUnit eq 'min'">
										<xsl:value-of select="format-time(xs:time('00:00:00Z')+$execTimeValue*xs:dayTimeDuration('PT1M'), '[H01] hours [m01] minutes')"></xsl:value-of> 
								 	</xsl:when>
								 	<xsl:otherwise>
								 		<xsl:value-of select="$execTimeValue"></xsl:value-of><xsl:text> </xsl:text>
								 		<xsl:value-of select="$execTimeUnit"></xsl:value-of>
								 	</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise><xsl:text> ? </xsl:text></xsl:otherwise>
						</xsl:choose>
					</td>
					<td class="separator"><xsl:text> </xsl:text></td>
					<td class="label-right">
						<xsl:if test="tasksSummary/simulatedTime/@value">
							<xsl:text>Simulated time</xsl:text>
						</xsl:if>
					</td>
					<td class="separator"><xsl:text> </xsl:text></td>
					<td>
						<xsl:if test="tasksSummary/simulatedTime/@value">
							<xsl:call-template name="convert-time-units">
								<xsl:with-param name="value"><xsl:value-of select="tasksSummary/simulatedTime/@value"></xsl:value-of></xsl:with-param>
								<xsl:with-param name="unit"><xsl:value-of select="tasksSummary/simulatedTime/@unit"></xsl:value-of></xsl:with-param>
							</xsl:call-template>
						</xsl:if>
					</td>
				</tr>
				<tr>
					<td class="label-right"><xsl:text>Number of tasks</xsl:text></td>
					<td class="separator"><xsl:text> </xsl:text></td>
					<td>
						<xsl:if test="tasksSummary/@numberOfTasks">
							<xsl:value-of select="tasksSummary/@numberOfTasks"></xsl:value-of>
						</xsl:if>
					</td>
					<td class="separator"><xsl:text> </xsl:text></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<p></p>
<xsl:for-each select="processGroups/processGroup">
<h1>
	<xsl:choose>
		<xsl:when test="tasksSummary/methods/method">
		<xsl:for-each select="tasksSummary/methods/method">
			<xsl:value-of select="."/>
	        <xsl:if test="not(position() = last())"><xsl:text> / </xsl:text></xsl:if>
		</xsl:for-each>
	</xsl:when>
	<xsl:otherwise><xsl:text>Process group</xsl:text></xsl:otherwise>
	</xsl:choose>
	<xsl:if test="tasksSummary/softwarePackages/softwarePackage">
	<xsl:text>(</xsl:text><xsl:for-each select="tasksSummary/softwarePackages/softwarePackage">
			<xsl:value-of select="."/>
	        <xsl:if test="not(position() = last())"><xsl:text>, </xsl:text></xsl:if>
		</xsl:for-each><xsl:text>)</xsl:text>
	</xsl:if>
</h1>

<table class="width:100%;">
	<thead>
		<tr>
			<th>Info</th>
			<th>Method</th>
			<th>Program</th>
			<th style="text-align:right">CPU</th>
			<th style="text-align:right">GPU</th>
			<th style="text-align:right">Execution time</th>
			<th style="text-align:center">Termination</th>
			<th style="text-align:right">Replica</th>
			<th style="text-align:right">Simulated time</th>
		</tr>
	</thead>
	<tbody>
<!-- ====================== EXPERIMENT PROCESS DETAILS =========================== -->
<xsl:if test="processes/process">
	<xsl:for-each select="processes/process">
		<tr>
			<td colspan="5">
				<p><i><strong><xsl:value-of select="description"></xsl:value-of></strong></i></p>
			</td>
			<td style="text-align:right">
				<xsl:if test="tasksSummary/executionTime/@value">
					<xsl:variable name="execTimeValue"><xsl:value-of select="tasksSummary/executionTime/@value"></xsl:value-of></xsl:variable> 
					<xsl:variable name="execTimeUnit"><xsl:value-of select="tasksSummary/executionTime/@unit"></xsl:value-of></xsl:variable> 
					<i><strong>
					<xsl:choose>
						<xsl:when test="$execTimeUnit eq 'min'">
							<xsl:value-of select="format-time(xs:time('00:00:00Z')+$execTimeValue*xs:dayTimeDuration('PT1M'), '[H01] hours [m01] min')"></xsl:value-of> 
					 	</xsl:when>
					 	<xsl:otherwise>
					 		<xsl:value-of select="$execTimeValue"></xsl:value-of><xsl:text> </xsl:text>
					 		<xsl:value-of select="$execTimeUnit"></xsl:value-of>
					 	</xsl:otherwise>
					</xsl:choose>
					</strong></i>
				</xsl:if>
			</td>
			<td></td>
			<td></td>
			<td style="text-align:right">
				<xsl:choose>
					<xsl:when test="tasksSummary/simulatedTime/@value">
						<i><strong>
						<xsl:call-template name="convert-time-units">
							<xsl:with-param name="value"><xsl:value-of select="tasksSummary/simulatedTime/@value"></xsl:value-of></xsl:with-param>
							<xsl:with-param name="unit"><xsl:value-of select="tasksSummary/simulatedTime/@unit"></xsl:value-of></xsl:with-param>
						</xsl:call-template>
						</strong></i>
					</xsl:when>
					<xsl:otherwise><xsl:text> - </xsl:text></xsl:otherwise>
				</xsl:choose>
			</td>
		</tr>

<!-- ====================== EXPERIMENT TASKS DETAILS =========================== -->
<xsl:if test="tasks/task">
<xsl:for-each select="tasks/task">
	<tr>
		<xsl:variable name="row-class">
			<xsl:choose>
				<xsl:when test="position() mod 2 = 0">row-a</xsl:when>
				<xsl:otherwise>row-b</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:attribute name="class">
			<xsl:value-of select="$row-class"></xsl:value-of>
		</xsl:attribute>
		<td>
			<!-- task description (or process description if not available) -->
			<img class="icon" src="../../images/icons/info_small.png" alt="desc">
			<xsl:choose>
				<xsl:when test="description != ''">
					<xsl:attribute name="title"><xsl:value-of select="description"></xsl:value-of></xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:if test="../../description != ''">
						<xsl:attribute name="title"><xsl:value-of select="../../description"></xsl:value-of></xsl:attribute>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>
			</img>
			<!-- list input and output files associated with this task -->
			<img class="icon" src="../../images/icons/full_page_small.png" alt="files">
			<xsl:attribute name="title">
				<xsl:for-each select="outputFiles/file | inputFiles/file">
					<xsl:sort select="."></xsl:sort>
					<xsl:value-of select="."></xsl:value-of>
					<xsl:if test="position() != last()"><xsl:text> </xsl:text></xsl:if>
				</xsl:for-each>
			</xsl:attribute>
			</img>
		</td>
		<td>
			<xsl:if test="@type"><xsl:value-of select="@type"></xsl:value-of></xsl:if></td>
		<td>
			<xsl:if test="software">
				<span>
					<xsl:if test="software/@executableName">
						<xsl:attribute name="title">
							<xsl:value-of select="software/@executableName"></xsl:value-of>
						</xsl:attribute>
						<xsl:attribute name="class">
							<xsl:text>text-tooltip</xsl:text>
						</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="software/@name"></xsl:value-of>
					<xsl:if test="software/@version">
						<xsl:text> </xsl:text><xsl:value-of select="software/@version"></xsl:value-of>
					</xsl:if>
				</span>
			</xsl:if>
		</td>
		<td style="text-align:right">
			<xsl:if test="taskExecution/numberOfCPUs &gt; 0">
				<span>
					<xsl:if test="computingEnvironment/cpuArchitecture">
						<xsl:attribute name="title">
							<xsl:value-of select="computingEnvironment/cpuArchitecture"></xsl:value-of>
						</xsl:attribute>
						<xsl:attribute name="class">
							<xsl:text>text-tooltip</xsl:text>
						</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="taskExecution/numberOfCPUs"></xsl:value-of>
				</span>
			</xsl:if>
		</td>
		<td style="text-align:right">
			<xsl:if test="taskExecution/numberOfGPUs &gt; 0">
				<span>
					<xsl:if test="computingEnvironment/gpuArchitecture">
						<xsl:attribute name="title">
							<xsl:value-of select="computingEnvironment/gpuArchitecture"></xsl:value-of>
						</xsl:attribute>
						<xsl:attribute name="class">
							<xsl:text>text-tooltip</xsl:text>
						</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="taskExecution/numberOfGPUs"></xsl:value-of>
				</span>
			</xsl:if>
		</td>
		<td style="text-align:right">
			<xsl:choose>
				<xsl:when test="taskExecution/executionTime">
					<xsl:value-of select="taskExecution/executionTime/@value"></xsl:value-of><xsl:text> </xsl:text>
					<xsl:value-of select="taskExecution/executionTime/@unit"></xsl:value-of>
				</xsl:when>
				<xsl:otherwise><xsl:text> ? </xsl:text></xsl:otherwise>
			</xsl:choose>
		</td>
		<td style="text-align:center">
			<xsl:choose>
				<xsl:when test="taskExecution/@terminationStatus = 'error'">
					<strong><span style="color:red">error</span></strong>
				</xsl:when>
				<xsl:when test="taskExecution/@terminationStatus = 'normal'">
					<xsl:text>normal</xsl:text>
				</xsl:when>
				<xsl:otherwise><xsl:text> ? </xsl:text></xsl:otherwise>
			</xsl:choose>
		</td>
<!-- ====================== PARAMETER SET DETAILS =========================== -->

	<td style="text-align:right">
	<xsl:if test="parameterSet/numberOfReplica">
		<xsl:value-of select="parameterSet/numberOfReplica"></xsl:value-of>
	</xsl:if>
	</td>
	<td style="text-align:right">
	<xsl:choose>
		<xsl:when test="parameterSet/simulatedTime/@value">
			<xsl:call-template name="convert-time-units">
				<xsl:with-param name="value"><xsl:value-of select="parameterSet/simulatedTime/@value"></xsl:value-of></xsl:with-param>
				<xsl:with-param name="unit"><xsl:value-of select="parameterSet/simulatedTime/@unit"></xsl:value-of></xsl:with-param>
			</xsl:call-template>
		</xsl:when>
		<xsl:otherwise><xsl:text> - </xsl:text></xsl:otherwise>
	</xsl:choose>
	</td>
	
<!-- end parameter sets -->
		
	</tr>
	</xsl:for-each>
</xsl:if>
<!-- end tasks -->
	</xsl:for-each>
</xsl:if>
<!-- end processes -->
</tbody>
</table>

</xsl:for-each>
<!-- end process groups -->

</div>
</div>

</div>
<!-- content-wrap ends here -->		
</div>

<br></br>
<!-- ====================== FOOTER =========================== -->

<div id="footer">
<div id="footer-content">
	<div style="text-align:center">
		<p><xsl:text>&#xA9; copyright 2014 </xsl:text><strong><a class="link" href="http://www.utah.edu">University of Utah</a></strong></p>
		<p><xsl:text>Design by: </xsl:text><a class="link" href="http://www.styleshout.com"><strong><xsl:text>styleshout</xsl:text></strong></a></p>
</div></div></div>

</body>
</html>
</xsl:template>

<!-- ================== CONVERT TIME LENGTH TO APROPRIATE UNIT ========================== -->
<xsl:template name="convert-time-units">
	<xsl:param name="unit"/>
	<xsl:param name="value"/>
	<xsl:variable name="units" as="xs:string*" select="('fs','ps','ns','us')"/>
	<xsl:choose>
		<xsl:when test="(number($value) &gt;= 1000) and (fn:index-of($units, $unit))">
			<xsl:value-of select="$value div 1000"></xsl:value-of>
			<xsl:choose>
				<xsl:when test="$unit = 'fs'"><xsl:text> ps</xsl:text></xsl:when>
				<xsl:when test="$unit = 'ps'"><xsl:text> ns</xsl:text></xsl:when>
				<xsl:when test="$unit = 'ns'"><xsl:text> us</xsl:text></xsl:when>
				<xsl:when test="$unit = 'us'"><xsl:text> ms</xsl:text></xsl:when>
			</xsl:choose>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$value"></xsl:value-of><xsl:text> </xsl:text>
			<xsl:value-of select="$unit"></xsl:value-of>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>


<!-- ===================== REPLACE FUNCTION ======================= -->

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

<!-- ================== CUT LONG LINES WITH BREAKING SPACES ========================== -->
<xsl:template name="cut-long-lines">
	<xsl:param name="count"/>
	<xsl:param name="myString"/>
	<xsl:choose>
		<xsl:when test="$count &lt; string-length($myString) ">
			<xsl:value-of select="substring($myString, ($count - 50), 50)"/>
			<br></br>
			<xsl:call-template name="cut-long-lines">
				<xsl:with-param name="myString" select="$myString"/>
				<xsl:with-param name="count" select="$count + 50"/>
			</xsl:call-template>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="substring($myString, ($count - 50), 50)"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>


</xsl:stylesheet>
