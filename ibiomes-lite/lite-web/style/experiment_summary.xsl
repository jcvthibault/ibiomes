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
<script type="text/javascript" src="../../jsmol/JSmol.min.nojq.js"></script>
<script type="text/javascript">
<xsl:comment>
<![CDATA[var Info = {
	  addSelectionOptions: false,
	  color: "#FFFFFF",
	  debug: false,
	  height: 450,
	  width: 450,
	  j2sPath: "../../jsmol/j2s", // HTML5 only
	  use: "HTML5",  // "HTML5" or "Java"
	  script: "",
      disableJ2SLoadMonitor: true,
	  disableInitialConsole: true,
	  deferApplet: false,
	  deferUncover: true
	};
	Jmol.setDocument(0);]]>
</xsl:comment>
</script>
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
	<div id="main" style="width:100%">
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
					<td><strong><xsl:text>Summary</xsl:text></strong></td>
					<td class="separator"><xsl:text> </xsl:text></td>
					<td><img class="icon" src="../../images/icons/folder_full_small.png" alt="Files"></img></td>
					<td><a class="link" href="files.html"><xsl:attribute name="title"><xsl:value-of select="@rootDirectoryPath"/></xsl:attribute><xsl:text>Browse files</xsl:text></a></td>
					<td class="separator"><xsl:text> </xsl:text></td>
					<td><img class="icon" src="../../images/icons/process_small.png" alt="runs" /></td>
					<td><a class="link" href="runs.html"><xsl:text>Execution info</xsl:text></a><xsl:text> </xsl:text></td>
					<td class="separator"><xsl:text> </xsl:text></td>
					<td><img class="icon" src="../../images/icons/next_small.png" alt="Workflow" /></td>
					<td><a class="link" href="details.html"><xsl:text>Protocol</xsl:text></a></td>
				</tr>
			</table>
			<!-- ====================== EXPERIMENT SUMMARY =========================== -->
			<p></p>
			<table class="layout-tight">
				<xsl:if test="description">
	     			<tr style="height:5px"></tr>
	     			<tr><td style="txt-align:justify"><i><xsl:value-of select="description"/><xsl:text> </xsl:text></i></td></tr>
	     		</xsl:if>
     			<tr>
     				<td>
     					<table>
					       	<tr>
						        <td><strong><xsl:text>Methods</xsl:text></strong></td>
						        <td class="separator"><xsl:text> </xsl:text></td>
								<td>
									<xsl:choose>
										<xsl:when test="tasksSummary/methods/method">
											<xsl:for-each select="tasksSummary/methods/method">
												<xsl:value-of select="."/>
										        <xsl:if test="not(position() = last())"><xsl:text> / </xsl:text></xsl:if>
											</xsl:for-each>
										</xsl:when>
										<xsl:otherwise><i><xsl:text>Unknown</xsl:text></i></xsl:otherwise>
									</xsl:choose>
								</td>
						        <td style="width:40px"></td>
							    <td><strong><xsl:text>Software packages</xsl:text></strong></td>				        		
					        	<td class="separator"><xsl:text> </xsl:text></td>
					        	<td>
					        		<xsl:choose>
										<xsl:when test="tasksSummary/softwarePackageUsesSummary/softwarePackageSummary">
											<xsl:for-each select="tasksSummary/softwarePackageUsesSummary/softwarePackageSummary">
												<xsl:value-of select="@name"/>
										        <xsl:if test="not(position() = last())"><xsl:text>, </xsl:text></xsl:if>
										        <xsl:if test="softwareVersions/softwareVersion">
											        <xsl:text> (</xsl:text><xsl:for-each select="softwareVersions/softwareVersion">
														<xsl:value-of select="."/>
												        <xsl:if test="not(position() = last())"><xsl:text>, </xsl:text></xsl:if>
													</xsl:for-each><xsl:text>)</xsl:text>
												</xsl:if>
											</xsl:for-each>
										</xsl:when>
										<xsl:otherwise><i><xsl:text>Unknown</xsl:text></i></xsl:otherwise>
									</xsl:choose>
								</td>
							</tr>
							<tr>
								<td><strong><xsl:text>Molecules</xsl:text></strong></td>
							    <td></td>
							    <td>
									<xsl:choose>
										<xsl:when test="molecularSystemsSummary/soluteMoleculeTypes/soluteMoleculeType">
											<xsl:for-each select="molecularSystemsSummary/soluteMoleculeTypes/soluteMoleculeType">
												<xsl:value-of select="."/>
											    <xsl:if test="not(position() = last())">, </xsl:if>
											</xsl:for-each>
										</xsl:when>
										<xsl:otherwise><i><xsl:text>Unknown</xsl:text></i></xsl:otherwise>
									</xsl:choose>
					        	</td>
						        <td style="width:40px"></td>
							    <td><strong><xsl:text>Published by</xsl:text></strong></td>				        		
					        	<td class="separator"><xsl:text> </xsl:text></td>
					        	<td>
					        		<xsl:choose>
										<xsl:when test="@publisher">
											<xsl:value-of select="@publisher"/>
										</xsl:when>
										<xsl:otherwise><i><xsl:text>Unknown</xsl:text></i></xsl:otherwise>
									</xsl:choose>
								</td>
			        		</tr>
    					</table>
    				</td>
    			</tr>
    		</table>
		</div>
		<p></p>

		<xsl:if test="/experiment/jmol">
		<table class="layout">
		<tr>
			<td><a class="pointer">
          			<xsl:attribute name="onclick"><xsl:text>showJmolForSelectedFile('data/</xsl:text><xsl:value-of select="/experiment/jmol/@path"></xsl:value-of><xsl:text>');</xsl:text></xsl:attribute>
          			<img class="icon" title="Display structure in Jmol..." src="../../images/icons/mol_small.png" alt="Jmol" />
          		</a>
          		<xsl:text>Sample structure: </xsl:text>
			<a target="_blank" title="Download file..." class="link">
				<xsl:attribute name="href"><xsl:text>data/</xsl:text><xsl:value-of select="/experiment/jmol/@path"></xsl:value-of></xsl:attribute>
				<xsl:choose>
					<xsl:when test="/experiment/jmol/@description">
						<xsl:value-of select="/experiment/jmol/@description"></xsl:value-of>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="/experiment/jmol/@path"></xsl:value-of>
					</xsl:otherwise>
				</xsl:choose> 
			</a></td>
		</tr>
		</table>
		</xsl:if>
		
		<!-- ====================== ANALYSIS DATA =========================== -->
        <xsl:if test="/experiment/analysis">
			<div>
				<h1>Analysis data</h1>
				<xsl:call-template name="analysisData">
					<xsl:with-param name="node" select="/experiment/analysis"/>
				</xsl:call-template>
			</div>
		</xsl:if>
		
<!-- ====================== EXPERIMENT PROCESS GROUP SUMMARIES =========================== -->
<xsl:for-each select="processGroups/processGroup">
<p></p>
<div>
	<h1><xsl:choose>
		<xsl:when test="tasksSummary/methods/method">
		<xsl:for-each select="tasksSummary/methods/method">
			<xsl:value-of select="."/>
	        <xsl:if test="not(position() = last())"><xsl:text> / </xsl:text></xsl:if>
		</xsl:for-each>
	</xsl:when>
	<xsl:otherwise>Process group</xsl:otherwise>
	</xsl:choose>
	<xsl:if test="tasksSummary/softwarePackages/softwarePackage">
	<xsl:text>(</xsl:text><xsl:for-each select="tasksSummary/softwarePackages/softwarePackage">
			<xsl:value-of select="."/>
	        <xsl:if test="not(position() = last())"><xsl:text>, </xsl:text></xsl:if>
		</xsl:for-each><xsl:text>)</xsl:text>
	</xsl:if>
</h1>
<table class="layout">
 	<tr>
	<td style="vertical-align:top;">
		<xsl:choose>
			<xsl:when test="molecularSystem">
				<table class="layout-tight">
					<xsl:if test="molecularSystem/description != ''">
						<tr>
							<td class="label-right"><xsl:text>System</xsl:text></td>
							<td class="separator"><xsl:text> </xsl:text></td>
							<td><xsl:value-of select="molecularSystem/description"></xsl:value-of></td>
						</tr>
					</xsl:if>
					<tr>
						<td class="label-right"><xsl:text>Molecules</xsl:text></td>
						<td class="separator"><xsl:text> </xsl:text></td>
						<td><xsl:if test="molecularSystem/soluteMolecules/molecule">
								<xsl:for-each select="molecularSystem/soluteMolecules/molecule">
									<xsl:choose>
										<xsl:when test="@type != 'Compound'">
											<xsl:value-of select="@type"/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:choose>
												<xsl:when test="stoichiometry">
													<xsl:call-template name="chemical-formula">
														<xsl:with-param name="text">
															<xsl:value-of select="stoichiometry"></xsl:value-of>
														</xsl:with-param>
													</xsl:call-template>
												</xsl:when>
												<xsl:otherwise><xsl:text>Compound</xsl:text></xsl:otherwise>
											</xsl:choose>
										</xsl:otherwise>
										</xsl:choose>
								    <xsl:if test="not(position() = last())"><xsl:text> / </xsl:text></xsl:if>
								</xsl:for-each>
							</xsl:if>
						</td>
					</tr>
					<xsl:if test="molecularSystem/solventMoleculeOccurrences/moleculeOccurrence">
						<tr>
							<td class="label-right"><xsl:text>Solvent</xsl:text></td>
							<td class="separator"><xsl:text> </xsl:text></td>
							<td>
								<xsl:for-each select="molecularSystem/solventMoleculeOccurrences/moleculeOccurrence">
									<xsl:value-of select="molecule/@name"></xsl:value-of><xsl:text> (</xsl:text><xsl:value-of select="count"></xsl:value-of><xsl:text>)</xsl:text>
									<xsl:if test="not(position() = last())"><xsl:text>, </xsl:text></xsl:if>
								</xsl:for-each>
							</td>
						</tr>
					</xsl:if>
					<xsl:if test="molecularSystem/ionOccurrences/moleculeOccurrence">
						<tr>
							<td class="label-right"><xsl:text>Ions</xsl:text></td>
							<td class="separator"><xsl:text> </xsl:text></td>
							<td>											
								<xsl:for-each select="molecularSystem/ionOccurrences/moleculeOccurrence">
									<xsl:value-of select="molecule/@name"></xsl:value-of><xsl:text> (</xsl:text><xsl:value-of select="count"></xsl:value-of><xsl:text>)</xsl:text>
									<xsl:if test="not(position() = last())"><xsl:text>, </xsl:text></xsl:if>
								</xsl:for-each>
							</td>
						</tr>
					</xsl:if>
					<xsl:if test="molecularSystem/atomCount">
						<tr>
							<td class="label-right"><xsl:text>Atom count</xsl:text></td>
							<td class="separator"><xsl:text> </xsl:text></td>
							<td><xsl:value-of select="molecularSystem/atomCount"></xsl:value-of></td>
						</tr>
					</xsl:if>
				</table>
			</xsl:when>
			<xsl:otherwise><p><xsl:text>No information about the molecular</xsl:text><br></br><xsl:text>system could be found.</xsl:text></p></xsl:otherwise>
		</xsl:choose>
	</td>
	<td style="width:30px"></td>
	<td style="vertical-align:top;">
		<xsl:choose>
			<xsl:when test="tasksSummary/methods/method">
				<table class="layout-tight">
					<xsl:if test="tasksSummary/@numberOfTasks">
						<tr>
							<td class="label-right"><xsl:text>Number of tasks</xsl:text></td>
							<td class="separator"><xsl:text> </xsl:text></td>
							<td><xsl:value-of select="tasksSummary/@numberOfTasks"></xsl:value-of><xsl:text> </xsl:text>
								<xsl:if test="tasksSummary/executionTime">
									<xsl:text>(</xsl:text><xsl:value-of select="tasksSummary/executionTime/@value"></xsl:value-of><xsl:text> </xsl:text>
									 <xsl:value-of select="tasksSummary/executionTime/@unit"></xsl:value-of><xsl:text>)</xsl:text>
								</xsl:if>
							</td>
						</tr>
					</xsl:if>
					<xsl:if test="tasksSummary/minimizationMethods/minimizationMethod">
						<tr>
							<td class="label-right"><xsl:text>Minimizations</xsl:text></td>
							<td class="separator"><xsl:text> </xsl:text></td>
							<td>
								<xsl:for-each select="tasksSummary/minimizationMethods/minimizationMethod">
									<xsl:value-of select="."></xsl:value-of>
									<xsl:if test="not(position() = last())"> / </xsl:if>
								</xsl:for-each>
							</td>
						</tr>
					</xsl:if>
					<xsl:if test="tasksSummary/qmMethods/qmMethod">
						<tr>
							<td class="label-right"><xsl:text>QM methods</xsl:text></td>
							<td class="separator"><xsl:text> </xsl:text></td>
							<td>
								<xsl:for-each select="tasksSummary/qmMethods/qmMethod">
									<xsl:value-of select="."></xsl:value-of>
									<xsl:if test="not(position() = last())"> / </xsl:if>
								</xsl:for-each>
							</td>
						</tr>
					</xsl:if>
					<xsl:if test="tasksSummary/basisSets/basisSet">
						<tr>
							<td class="label-right"><xsl:text>Basis sets</xsl:text></td>
							<td class="separator"><xsl:text> </xsl:text></td>
							<td>
								<xsl:for-each select="tasksSummary/basisSets/basisSet">
									<xsl:value-of select="."></xsl:value-of>
									<xsl:if test="not(position() = last())">, </xsl:if>
								</xsl:for-each>
							</td>
						</tr>
					</xsl:if>
					<xsl:if test="tasksSummary/calculations/calculation">
						<tr>
							<td class="label-right"><xsl:text>Calculations</xsl:text></td>
							<td class="separator"><xsl:text> </xsl:text></td>
							<td>
								<xsl:for-each select="tasksSummary/calculations/calculation">
									<xsl:value-of select="."></xsl:value-of>
									<xsl:if test="not(position() = last())">, </xsl:if>
								</xsl:for-each>
							</td>
						</tr>
					</xsl:if>
					<xsl:if test="tasksSummary/forceFields/forceField">
						<tr>
							<td class="label-right"><xsl:text>Force fields</xsl:text></td>
							<td class="separator"><xsl:text> </xsl:text></td>
							<td>
								<xsl:for-each select="tasksSummary/forceFields/forceField">
									<xsl:value-of select="."></xsl:value-of>
									<xsl:if test="not(position() = last())">, </xsl:if>
								</xsl:for-each>
							</td>
						</tr>
					</xsl:if>
					<xsl:if test="tasksSummary/simulatedTime">
						<tr>
							<td class="label-right"><xsl:text>Simulated time</xsl:text></td>
							<td class="separator"><xsl:text> </xsl:text></td>
							<td>
								<xsl:value-of select="tasksSummary/simulatedTime/@value"></xsl:value-of><xsl:text> </xsl:text>
								<xsl:value-of select="tasksSummary/simulatedTime/@unit"></xsl:value-of>
							</td>
						</tr>
					</xsl:if>
					<xsl:if test="tasksSummary/barostats/barostat">
						<tr>
							<td class="label-right"><xsl:text>Barostats</xsl:text></td>
							<td class="separator"><xsl:text> </xsl:text></td>
							<td>
								<xsl:for-each select="tasksSummary/barostats/barostat">
									<xsl:value-of select="."></xsl:value-of>
									<xsl:if test="not(position() = last())"><xsl:text>, </xsl:text></xsl:if>
								</xsl:for-each>
							</td>
						</tr>
					</xsl:if>
					<xsl:if test="tasksSummary/thermostats/thermostat">
						<tr>
							<td class="label-right"><xsl:text>Thermostats</xsl:text></td>
							<td class="separator"><xsl:text> </xsl:text></td>
							<td>
								<xsl:for-each select="tasksSummary/thermostats/thermostat">
									<xsl:value-of select="."></xsl:value-of>
									<xsl:if test="not(position() = last())"><xsl:text>, </xsl:text></xsl:if>
								</xsl:for-each>
							</td>
						</tr>
					</xsl:if>
					<xsl:if test="tasksSummary/electrostaticsModels/electrostaticsModel">
						<tr>
							<td class="label-right"><xsl:text>Electrostatics</xsl:text></td>
							<td class="separator"><xsl:text> </xsl:text></td>
							<td>
								<xsl:for-each select="tasksSummary/electrostaticsModels/electrostaticsModel">
									<xsl:value-of select="."></xsl:value-of>
									<xsl:if test="not(position() = last())"><xsl:text>, </xsl:text></xsl:if>
								</xsl:for-each>
							</td>
						</tr>
					</xsl:if>
				</table>
			</xsl:when>
			<xsl:otherwise><p><xsl:text>No information about the computational</xsl:text><br></br><xsl:text>tasks could be found.</xsl:text></p></xsl:otherwise>
		</xsl:choose>
	</td>
</tr>
</table>
			
<!-- ====================== MOLECULAR SYSTEM DETAILS =========================== -->

<xsl:if test="molecularSystem">
	<div>
		<xsl:attribute name="id">details_molecular_system<xsl:value-of select="position()" /></xsl:attribute>
		<h3><a href="#"><xsl:text>Molecular system</xsl:text></a></h3>
		<div>
			<xsl:if test="molecularSystem/solventMoleculeOccurrences/moleculeOccurrence">
				<span style="text-transform:uppercase;font-weight:bold"><xsl:text>Solvent</xsl:text></span>
				<hr/>
				<table class="layout-tight">
					<xsl:for-each select="molecularSystem/solventMoleculeOccurrences/moleculeOccurrence">
					<tr><td>
						<strong><xsl:value-of select="molecule/@name"></xsl:value-of></strong><xsl:text> (</xsl:text><xsl:value-of select="count"></xsl:value-of><xsl:text>)</xsl:text>
					</td></tr>
					</xsl:for-each>
					<xsl:for-each select="molecularSystem/ionOccurrences/moleculeOccurrence">
						<tr><td>
							<strong><xsl:value-of select="molecule/@name"></xsl:value-of></strong><xsl:text> (</xsl:text><xsl:value-of select="count"></xsl:value-of><xsl:text>)</xsl:text>
						</td></tr>
					</xsl:for-each>
				</table>
				<p></p>
			</xsl:if>
			<xsl:if test="molecularSystem/soluteMolecules/molecule">
				<span style="text-transform:uppercase;font-weight:bold"><xsl:text>Molecules</xsl:text></span>
				<hr/><p></p>
				<xsl:for-each select="molecularSystem/soluteMolecules/molecule">
					<h4><xsl:text>Molecule </xsl:text>
					<xsl:choose>
						<xsl:when test="@type != ''">
							<xsl:text>[ </xsl:text><i><xsl:value-of select="@type"></xsl:value-of></i><xsl:text> ]</xsl:text>
						</xsl:when>
						<xsl:when test="@name != ''">
							<xsl:value-of select="@name"></xsl:value-of>
						</xsl:when>
					</xsl:choose>
					</h4>
					<table class="layout-tight">
						<xsl:if test="stoichiometry">
							<tr><td class="label-right"><xsl:text>Stoichiometry</xsl:text></td>
								<td class="separator"><xsl:text> </xsl:text></td>
								<td>
									<xsl:call-template name="chemical-formula">
									    <xsl:with-param name="text" select="stoichiometry" />
									</xsl:call-template>
								</td>
							</tr>
						</xsl:if>
						<tr>
							<td class="label-right"><xsl:text>Number of atoms</xsl:text></td>
							<td class="separator"><xsl:text> </xsl:text></td>
							<td><xsl:value-of select="@atomCount"></xsl:value-of></td>
						</tr>
						<xsl:if test="@residueCount != ''">
							<tr><td class="label-right"><xsl:text>Number of residues</xsl:text></td>
								<td class="separator"><xsl:text> </xsl:text></td>
								<td><xsl:value-of select="@residueCount"></xsl:value-of></td>
							</tr>
						</xsl:if>
						<xsl:if test="residueChain != ''">
							<tr><td class="label-right"><xsl:text>Residue chain</xsl:text></td>
								<td class="separator"><xsl:text> </xsl:text></td>
								<td><xsl:value-of select="residueChain"></xsl:value-of></td>
							</tr>
						</xsl:if>
						<xsl:if test="residueChainNorm != ''">
							<tr><td class="label-right"><xsl:text>Normalized residue chain</xsl:text></td>
								<td class="separator"><xsl:text> </xsl:text></td>
								<td>
									<xsl:call-template name="cut-long-lines">
										<xsl:with-param name="count">61</xsl:with-param>
										<xsl:with-param name="myString" select="residueChainNorm"/>
									</xsl:call-template>
								</td>
							</tr>
						</xsl:if>
						<xsl:if test="charge">
							<tr><td class="label-right"><xsl:text>Formal charge</xsl:text></td>
								<td class="separator"><xsl:text> </xsl:text></td>
								<td><xsl:value-of select="charge"></xsl:value-of></td>
							</tr>
						</xsl:if>
					</table>
				</xsl:for-each>
			</xsl:if>
		</div>
	</div>
</xsl:if>

<!-- ====================== EXPERIMENT PROCESS DETAILS =========================== -->
<xsl:if test="processes/process">
	<div>
		<xsl:attribute name="id">details_tasks<xsl:value-of select="position()" /></xsl:attribute>
		<h3><a href="#"><xsl:text>Computational tasks</xsl:text></a></h3>
		<div>
		<xsl:for-each select="processes/process">
			<span style="text-transform:uppercase;font-weight:bold">
				<xsl:choose>
					<xsl:when test="@name != ''">
						<xsl:value-of select="@name"></xsl:value-of>
					</xsl:when>
					<xsl:otherwise><xsl:text>Process</xsl:text></xsl:otherwise>
				</xsl:choose>
				<xsl:if test="description != ''">
					 <xsl:text> [ </xsl:text><i><xsl:value-of select="description"></xsl:value-of></i><xsl:text> ]</xsl:text>
				</xsl:if>
			</span>
			<p></p>
			<hr/>
			<p></p>

<!-- ====================== EXPERIMENT TASKS DETAILS =========================== -->
<xsl:if test="tasks/task">
<xsl:for-each select="tasks/task">
	<table class="layout" style="width:100%; background: #FAFAFA; border: 1px solid #f2f2f2; border-left: 3px solid #72A545;">
		<tr><td style="width:100%; padding-left:15px;">
			<span style="color:#72A545"><strong>
				<xsl:value-of select="@type"></xsl:value-of><xsl:text> task</xsl:text>
				<xsl:if test="description != ''">
					<xsl:text> [ </xsl:text><i><xsl:value-of select="description"></xsl:value-of></i><xsl:text> ]</xsl:text>
				</xsl:if>
				</strong>
			</span><p></p>
			<table class="width:100%; layout-tight">
				<xsl:if test="software">
					<tr>
						<td class="label-left"><xsl:text>Software</xsl:text></td>
						<td class="separator"><xsl:text> </xsl:text></td>
						<td> 
							<xsl:value-of select="software/@name"></xsl:value-of><xsl:text> </xsl:text>
							<xsl:if test="software/@version">
								<xsl:value-of select="software/@version"></xsl:value-of><xsl:text> </xsl:text>
							</xsl:if>
							<xsl:if test="software/@executableName">
								<xsl:text> (</xsl:text>
								<xsl:value-of select="software/@executableName"></xsl:value-of><xsl:text>)</xsl:text>
							</xsl:if>
						</td>
					</tr>
				</xsl:if>
				<xsl:if test="taskExecution/@terminationStatus">
					<tr>
						<td class="label-left"><xsl:text>Normal termination</xsl:text></td>
						<td style="width:15px"></td>
						<td>
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
					</tr>
				</xsl:if>
				<xsl:if test="boundaryConditions">	
					<tr><td class="label-left"><xsl:text>Boundary conditions</xsl:text></td>
						<td class="separator"><xsl:text> </xsl:text></td>
						<td><xsl:value-of select="boundaryConditions"></xsl:value-of></td>
					</tr>
				</xsl:if>
<!-- ====================== PARAMETER SET DETAILS =========================== -->
<xsl:for-each select="parameterSet">
	<tr>
		<td class="label-left">
			<xsl:if test="@type"><xsl:value-of select="@type"></xsl:value-of></xsl:if>
		</td>
		<td class="separator"><xsl:text> </xsl:text></td>
		<td></td>
	</tr>
	<xsl:if test="@name">
		<tr><td class="label-right-minor"><xsl:text>Method</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td><xsl:value-of select="@name"></xsl:value-of></td></tr>
	</xsl:if>
	<xsl:if test="samplingMethod">
		<tr>
			<td class="label-right-minor"><xsl:text>Enhanced sampling</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td><xsl:value-of select="samplingMethod/name"></xsl:value-of></td>
		</tr>
	</xsl:if>
	<xsl:if test="numberOfReplica">
		<tr>
			<td class="label-right-minor"><xsl:text>Number of replicas</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td><xsl:value-of select="numberOfReplica"></xsl:value-of></td>
		</tr>
	</xsl:if>
	<xsl:if test="solventType">
		<tr>
			<td class="label-right-minor"><xsl:text>Solvent type</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td><xsl:value-of select="solventType"></xsl:value-of>
			<xsl:if test="implicitSolventModel">
				<xsl:text>(</xsl:text><xsl:value-of select="implicitSolventModel"></xsl:value-of><xsl:text>)</xsl:text>
			</xsl:if></td>
		</tr>
	</xsl:if>
	<xsl:if test="simulatedTime">
		<tr>
			<td class="label-right-minor"><xsl:text>Simulated time</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td>
				<xsl:value-of select="simulatedTime/@value"></xsl:value-of><xsl:text> </xsl:text>
				<xsl:value-of select="simulatedTime/@unit"></xsl:value-of>
			</td>
		</tr>
	</xsl:if>
	<xsl:if test="numberOfSteps">
		<tr>
			<td class="label-right-minor"><xsl:text>Number of steps</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td><xsl:value-of select="numberOfSteps"></xsl:value-of></td>
		</tr>
	</xsl:if>
	<xsl:if test="timeStepLength">
		<tr>
			<td class="label-right-minor"><xsl:text>Time step length</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td><xsl:value-of select="timeStepLength/@value"></xsl:value-of>
			<xsl:text> </xsl:text><xsl:value-of select="timeStepLength/@unit"></xsl:value-of></td>
		</tr>
	</xsl:if>
	
	<!-- Molecular dynamics -->
	
	<xsl:if test="forceFields/forceField">
		<tr>
			<td class="label-right-minor"><xsl:text>Force fields</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td>
				<xsl:for-each select="forceFields/forceField">
					<xsl:value-of select="."></xsl:value-of>
					<xsl:if test="not(position() = last())">, </xsl:if>
				</xsl:for-each>
			</td>
		</tr>
	</xsl:if>
	<xsl:if test="ensemble">
		<tr>
			<td class="label-right-minor"><xsl:text>Ensemble</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td><xsl:value-of select="ensemble"></xsl:value-of></td>
		</tr>
	</xsl:if>
	<xsl:if test="barostat">
		<tr><td class="label-right-minor"><xsl:text>Barostat</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td><xsl:value-of select="barostat/@algorithm"></xsl:value-of>
				<xsl:if test="barostat/@type">
					<xsl:text>(</xsl:text><xsl:value-of select="barostat/@type"></xsl:value-of><xsl:text>)</xsl:text>
				</xsl:if>
			</td></tr>
	</xsl:if>
	<xsl:if test="thermostat">
		<tr><td class="label-right-minor"><xsl:text>Thermostat</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td><xsl:value-of select="thermostat/@name"></xsl:value-of></td></tr>
	</xsl:if>
	<xsl:if test="thermostat/collisionFrequency">
		<tr><td class="label-right-minor"><xsl:text>Langevin collision frequency</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td><xsl:value-of select="thermostat/collisionFrequency/@value"></xsl:value-of><xsl:text> </xsl:text><xsl:value-of select="thermostat/collisionFrequency/@unit"></xsl:value-of></td></tr>
	</xsl:if>
	<xsl:if test="restraints/restraint">
		<tr><td class="label-right-minor"><xsl:text>Restraints</xsl:text></td>
			<td class="separator"></td>
			<td><xsl:text>yes</xsl:text></td>
		</tr>
	</xsl:if>
	<xsl:if test="constraints/constraint">
		<xsl:for-each select="constraints/constraint">
			<tr><td class="label-right-minor"><xsl:text>Constraints</xsl:text></td>
				<td class="separator"><xsl:text> </xsl:text></td>
				<td>
					<xsl:value-of select="./@algorithm"></xsl:value-of>
					<xsl:if test="./@target">
						<xsl:text> (</xsl:text><xsl:value-of select="./@target"></xsl:value-of><xsl:text>)</xsl:text>
					</xsl:if>
				</td>
			</tr>
		</xsl:for-each>
	</xsl:if>
	<xsl:if test="electrostaticsModel">
		<tr><td class="label-right-minor"><xsl:text>Electrostatics model</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td><xsl:value-of select="electrostaticsModel/name"></xsl:value-of>
			<xsl:if test="electrostaticsModel/cutoff/@value">
				<xsl:text> (</xsl:text>
				<xsl:value-of select="electrostaticsModel/cutoff/@value"></xsl:value-of>
				<xsl:text> </xsl:text><xsl:value-of select="electrostaticsModel/cutoff/@unit"></xsl:value-of>
				<xsl:text> cutoff)</xsl:text>
			</xsl:if>
			</td></tr>
	</xsl:if>
	<xsl:if test="cutoffForVanDerWaals">
		<tr><td class="label-right-minor"><xsl:text>Van der Waals interaction cutoff</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td><xsl:value-of select="cutoffForVanDerWaals/@value"></xsl:value-of>
			<xsl:text> </xsl:text><xsl:value-of select="cutoffForVanDerWaals/@unit"></xsl:value-of></td></tr>
	</xsl:if>
	
	<!-- Quantum chemistry -->
	
	<xsl:if test="basisSets/basisSet">
		<tr><td class="label-right-minor"><xsl:text>Basis sets</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td>
				<xsl:for-each select="basisSets/basisSet">
					<xsl:value-of select="."></xsl:value-of>
					<xsl:if test="not(position() = last())">, </xsl:if>
				</xsl:for-each>
			</td>
		</tr>
	</xsl:if>
	<xsl:if test="totalCharge">
		<tr><td class="label-right-minor"><xsl:text>Total charge</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td><xsl:value-of select="totalCharge"></xsl:value-of></td></tr>
	</xsl:if>
	<xsl:if test="calculations/calculation">
		<tr><td class="label-right-minor"><xsl:text>Calculations</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td><td>
			<ul>
			<xsl:for-each select="calculations/calculation">
				<li><xsl:value-of select="."></xsl:value-of></li>
			</xsl:for-each>
			</ul>
		</td>
		</tr>
	</xsl:if>
	
	
	<!-- QM/MM -->
	
	<xsl:if test="qmSpaceDefinition">
		<tr><td class="label-right-minor"><xsl:text>QM region</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td><xsl:value-of select="qmSpaceDefinition"></xsl:value-of></td></tr>
	</xsl:if>
	<xsl:if test="boundaryTreatment">
		<tr><td class="label-right-minor"><xsl:text>QM/MM boundary treatment</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td><xsl:value-of select="boundaryTreatment"></xsl:value-of></td></tr>
	</xsl:if>
	<xsl:if test="qmElectrostatics">
		<tr><td class="label-right-minor"><xsl:text>QM electrostatics</xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td><xsl:value-of select="qmElectrostatics"></xsl:value-of></td></tr>
	</xsl:if>
		
</xsl:for-each>
<!-- end parameter sets -->

					</table>
				</td>
			</tr>
		</table>
		<p></p>
	</xsl:for-each>
</xsl:if>
<!-- end tasks -->

		<xsl:if test="not(position() = last())"><p></p></xsl:if>
	</xsl:for-each>
	</div>
	</div>
</xsl:if>
<!-- end process groups -->
	
</div>
</xsl:for-each>
<!-- end process groups -->

</div>
<p></p>
<!-- post ends here -->
</div>
</div>
<!-- content-wrap ends here -->		
</div>
<p></p>
<!-- ====================== FOOTER =========================== -->

<div id="footer">
<div id="footer-content">
	<div style="text-align:center">
		<p><xsl:text>&#xA9; copyright 2014 </xsl:text><strong><a class="link" href="http://www.utah.edu">University of Utah</a></strong></p>
		<p><xsl:text>Design by: </xsl:text><a class="link" href="http://www.styleshout.com"><strong><xsl:text>styleshout</xsl:text></strong></a></p>
</div></div></div>
<!-- ====================== VISUALIZATION WINDOWS =========================== -->
<div id="plotWindow" title="Plotting tool">
	<div style="text-align:center;" id="plotContainer"></div>
</div>
<div id="jmolWindow" title="Jmol - 3D rendering">
	<table style="margin-left:auto; margin-right:auto;" class="layout-tight">
	<tr>
		<td><div id="jmolAppletDiv"></div></td>
		<td style="width:150px"><div id="jmolButtonDiv"></div></td>
	</tr>
	</table>
</div>
<!-- ====================== SCRIPTS =========================== -->
<script type="text/javascript" defer="defer">
<xsl:comment>
<![CDATA[$(function () {
	$("[id^=details]").accordion({
		collapsible: true,
		heightStyle: "content",
		animate: true,
		active: false
	});
	$("#plotWindow").dialog({
		height: 600,
        width: 700,
        autoOpen: false,
		modal: true,
        buttons: {
        	Close: function() {
        		$("#plotContainer").empty();
        		$( this ).dialog( "close" );
            }
        }
	});
	$("#jmolWindow").dialog({
		height: 600,
        width: 700,
        autoOpen: false,
		modal: true,
        buttons: {
        	Close: function() {
        		$("#jmolAppletDiv").empty();
        		$( this ).dialog( "close" );
            }
        }
	});
});
function showPlotForSelectedFile(path){
	$("#plotWindow").dialog('open');
	path = path.replace(/ /g, '');
    $("#selectedFile").val(path);
    $("#plotContainer").html('<img src="'+path+'" />');
}
function showImageForSelectedFile(path){
	path = path.substring($("#rootDirPath").val().length + 1);
	path = "data/" + path.replace(/\//g, '_');
    window.open(path, 'Image');
}
function showJmolForSelectedFile(path){
	$("#jmolWindow").dialog('open');
	path = path.replace(/ /g, '');
    $("#selectedFile").val(path);
    
	Info.script = "load '"+path+"';select all; wireframe 50; set ambient 5; set specpower 40;";
	Jmol.getApplet("myJmol", Info);
    Jmol.setButtonCss(null,"style='width:120px'");
	$("#jmolButtonDiv").html(
		Jmol.jmolButton(myJmol, "select all; wireframe off; cpk off; cartoons;", "Cartoon") + "<br />" +
		Jmol.jmolButton(myJmol, "select all; cartoons off; wireframe off;cpk on;", "CPK") + "<br />" +				
		Jmol.jmolButton(myJmol, "select all; cartoons off; cpk off; wireframe 30; trace off; spacefill 75;", "Ball and stick") + "<br />" +
		Jmol.jmolButton(myJmol, "select all; cartoons off; cpk off; wireframe 40; trace off;", "Wireframe") + "<br />" +
		Jmol.jmolButton(myJmol, "select all; cartoons off; cpk off; wireframe off; trace 100;", "Trace") + "<br />"
	);
	$("#jmolAppletDiv").html(Jmol.getAppletHtml(myJmol));
}]]>
</xsl:comment>
</script>
</body>
</html>
</xsl:template>

<!-- ===================== TEMPLATE FOR ANALYSIS DATA ======================= -->
<xsl:template name="analysisData">
  <xsl:param name="node" />
  	<xsl:if test="$node/images/image or $node/structures/structure or $node/spreadsheets/spreadsheet or $node/unknowns/unknown">
 		<xsl:if test="$node/images/image">
		<p>
			<xsl:for-each select="$node/images/image">
				<a target="_blank">
					<xsl:attribute name="href"><xsl:text>data/</xsl:text><xsl:value-of select="@path"></xsl:value-of></xsl:attribute>
					<img height="100px" alt="image">
						<xsl:attribute name="src">
							<xsl:text>data/</xsl:text><xsl:value-of select="@path"></xsl:value-of>
						</xsl:attribute>
						<xsl:choose>
							<xsl:when test="@description">
								<xsl:attribute name="title"><xsl:value-of select="@description"></xsl:value-of></xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<xsl:attribute name="title"><xsl:value-of select="@name"></xsl:value-of></xsl:attribute>
							</xsl:otherwise>
						</xsl:choose>
					</img>
				</a>
			</xsl:for-each>
		</p>
	</xsl:if>
	<xsl:if test="$node/structures/structure">
		<table class="layout" style="width:100%">
		<xsl:for-each select="$node/structures/structure">
			<xsl:if test="position() mod 3 = 1">
				<xsl:text disable-output-escaping="yes">&lt;tr&gt;</xsl:text>
			</xsl:if>
			<td style="width:33%">
			<xsl:call-template name="structure-row">
				<xsl:with-param name="node" select="." />
			</xsl:call-template>
			</td>
			<xsl:if test="(position() = last()) and (position() mod 3 = 1)">
				<td><xsl:text> </xsl:text></td><td><xsl:text> </xsl:text></td>
			</xsl:if>
			<xsl:if test="(position() = last()) and (position() mod 3 = 2)">
				<td><xsl:text> </xsl:text></td>
			</xsl:if>
			<xsl:if test="(position() mod 3 = 0) or (position() = last())">
				<xsl:text disable-output-escaping="yes">&lt;/tr&gt;</xsl:text>
			</xsl:if>
		</xsl:for-each>
		</table>
	</xsl:if>
	<xsl:if test="$node/spreadsheets/spreadsheet">
		<table class="layout" style="width:100%">
		<xsl:for-each select="$node/spreadsheets/spreadsheet">
			<xsl:if test="position() mod 3 = 1">
				<xsl:text disable-output-escaping="yes">&lt;tr&gt;</xsl:text>
			</xsl:if>
			<td style="width:33%">
			<xsl:call-template name="plot-row">
				<xsl:with-param name="node" select="." />
			</xsl:call-template>
			</td>
			<xsl:if test="(position() = last()) and (position() mod 3 = 1)">
				<td></td><td></td>
			</xsl:if>
			<xsl:if test="(position() = last()) and (position() mod 3 = 2)">
				<td></td>
			</xsl:if>
			<xsl:if test="(position() mod 3 = 0) or (position() = last())">
				<xsl:text disable-output-escaping="yes">&lt;/tr&gt;</xsl:text>
			</xsl:if>
		</xsl:for-each>
		</table>
	</xsl:if>
	<xsl:if test="$node/unknowns/unknown">
	<ul>
		<xsl:for-each select="$node/unknowns/unknown">
			<li>
				<a target="_blank" title="Download file..." class="link">
					<xsl:attribute name="href"><xsl:text>data/</xsl:text><xsl:value-of select="@path"></xsl:value-of></xsl:attribute>
					<xsl:choose>
						<xsl:when test="@description">
							<xsl:value-of select="@description"></xsl:value-of>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="@path"></xsl:value-of>
						</xsl:otherwise>
					</xsl:choose>
				</a>
			</li>
		</xsl:for-each>
	</ul>
	</xsl:if>
</xsl:if>
</xsl:template>

<!-- ===================== create Jmol row ======================= -->

<xsl:template name="structure-row">
	<xsl:param name="node" />
	<a class="pointer">
       <xsl:attribute name="onclick"><xsl:text>showJmolForSelectedFile('data/</xsl:text><xsl:value-of select="$node/@path"></xsl:value-of><xsl:text>');</xsl:text></xsl:attribute>
       <img class="icon" title="Display structure in Jmol..." src="../../images/icons/mol_small.png" alt="Jmol" />
    </a>
	<a target="_blank" title="Download file..." class="link">
		<xsl:attribute name="href"><xsl:text>data/</xsl:text><xsl:value-of select="$node/@path"></xsl:value-of></xsl:attribute>
		<img class="icon" src="../../images/icons/download_small.png" alt="download"/>
		<xsl:choose>
			<xsl:when test="$node/@description">
				<xsl:value-of select="$node/@description"></xsl:value-of>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$node/@path"></xsl:value-of>
			</xsl:otherwise>
		</xsl:choose>
	</a>
</xsl:template>

<!-- ===================== create spreadsheet/plot row ======================= -->
<xsl:template name="plot-row">
	<xsl:param name="node" />
	<xsl:if test="$node/@plotPath">
	<a class="pointer">
        <xsl:attribute name="onclick"><xsl:text>showPlotForSelectedFile('data/</xsl:text><xsl:value-of select="$node/@plotPath"></xsl:value-of><xsl:text>');</xsl:text></xsl:attribute>
        <img class="icon" title="Plot data..." src="../../images/icons/chart_small.png" alt="plot" />
    </a>
    </xsl:if>
	<a target="_blank" title="Download file..." class="link">
		<xsl:attribute name="href">data/<xsl:value-of select="$node/@path"></xsl:value-of></xsl:attribute>
		<img class="icon" src="../../images/icons/download_small.png" alt="download" />
		<xsl:choose>
			<xsl:when test="@description">
				<xsl:value-of select="$node/@description"></xsl:value-of>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$node/@path"></xsl:value-of>
			</xsl:otherwise>
		</xsl:choose>
	</a>
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

<!-- ================== CUT LONG LINES WITH BREAKING SPACES ========================== -->
<xsl:template name="cut-long-lines">
	<xsl:param name="count"/>
	<xsl:param name="myString"/>
	<xsl:choose>
		<xsl:when test="$count &lt; string-length($myString) ">
			<xsl:value-of select="substring($myString, ($count - 60), 60)"/><xsl:text> </xsl:text>
			<xsl:call-template name="cut-long-lines">
				<xsl:with-param name="myString" select="$myString"/>
				<xsl:with-param name="count" select="$count + 60"/>
			</xsl:call-template>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="substring($myString, ($count - 60), 60)"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- ================== DISPLAY CHEMICAL FORMULA========================== -->

<xsl:template name="chemical-formula">
  	<xsl:param name="text" />
	<xsl:variable name="comp1">
	  <xsl:call-template name="string-replace-all">
	    <xsl:with-param name="text" select="$text" />
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
</xsl:template>			

</xsl:stylesheet>
