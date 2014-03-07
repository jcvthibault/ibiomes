<xsl:stylesheet version="2.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
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
		<h2 id="slogan"><xsl:text>Local indexing of biomolecular simulation data</xsl:text></h2>
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
			<td><a class="link" href="runs.html"><xsl:text>Execution info</xsl:text></a><xsl:text> </xsl:text></td>
			<td class="separator"><xsl:text> </xsl:text></td>
			<td><img class="icon" src="../../images/icons/next_small.png" alt="Workflow" /></td>
			<td><strong><xsl:text>Protocol</xsl:text></strong></td>
		</tr>
		</table>
	</div>
	<p></p>
	<div class="post">
		<div id="fileTree" style="background-color:white;">
		<ul>
			<li class="jstree-open"><a><xsl:text>Experiment</xsl:text></a>
			<ul>
			
			
<!-- ====================== PROCESS GOUPS =========================== -->

<xsl:for-each select="processGroups/processGroup">
	<li class="jstree-open">
		<a><xsl:text>Process group</xsl:text></a>
		<ul>
		
<!-- ====================== MOLECULAR SYSTEM =========================== -->
<xsl:if test="molecularSystem">
	<li class="jstree-open">
		<a><xsl:text>Molecular system</xsl:text>
			<xsl:if test="molecularSystem/description != ''">
				<xsl:text> [ </xsl:text><i><xsl:value-of select="molecularSystem/description"></xsl:value-of></i><xsl:text> ]</xsl:text>
			</xsl:if>
		</a>
		<ul>
			<li><a><xsl:text>Number of solute molecules: </xsl:text><xsl:value-of select="molecularSystem/soluteMoleculeCount"></xsl:value-of></a></li>
			<li><a><xsl:text>Number of atoms: </xsl:text><xsl:value-of select="molecularSystem/atomCount"></xsl:value-of></a></li>
			<li><a><xsl:text>Number of solvent molecules: </xsl:text><xsl:value-of select="molecularSystem/solventMoleculeCount"></xsl:value-of></a></li>
			<li><a><xsl:text>Ions: </xsl:text><xsl:value-of select="molecularSystem/ionCount"></xsl:value-of>
				<xsl:if test="molecularSystem/ionOccurrences/moleculeOccurrence">
				<xsl:text> (</xsl:text>
					<xsl:for-each select="molecularSystem/ionOccurrences/moleculeOccurrence">
						<xsl:value-of select="molecule/@name"/>
				        <xsl:if test="not(position() = last())"><xsl:text> / </xsl:text></xsl:if>
					</xsl:for-each>
				<xsl:text>)</xsl:text>
				</xsl:if>
			</a></li>
						
<!-- ====================== MOLECULES =========================== -->
<li class="jstree-open"><a><xsl:text>Molecules</xsl:text></a>
	<ul>
		<xsl:for-each select="molecularSystem/soluteMolecules/molecule">
		<li>
			<a><xsl:text>Molecule</xsl:text>
				<xsl:choose>
					<xsl:when test="@type != ''">
						<xsl:text> [ </xsl:text><i><xsl:value-of select="@type"></xsl:value-of></i><xsl:text> ]</xsl:text>
					</xsl:when>
					<xsl:when test="@name != ''">
						<xsl:value-of select="@name"></xsl:value-of>
					</xsl:when>
				</xsl:choose>
			</a>
			<ul>
				<li><a><xsl:text>Number of atoms: </xsl:text><xsl:value-of select="@atomCount"></xsl:value-of></a></li>
				<xsl:if test="@residueCount != ''">
					<li><a><xsl:text>Number of residues: </xsl:text><xsl:value-of select="@residueCount"></xsl:value-of></a></li>
				</xsl:if>
				<xsl:if test="stoichiometry">
					<li><a><xsl:text>Stoichiometry: </xsl:text>
						<xsl:variable name="comp1">
						  <xsl:call-template name="string-replace-all">
						    <xsl:with-param name="text" select="stoichiometry" />
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
					</a></li>
				</xsl:if>
				<xsl:if test="residueChain != ''">
					<li><a><xsl:text>Residue chain: </xsl:text><xsl:value-of select="residueChain"></xsl:value-of></a></li>
				</xsl:if>
				<xsl:if test="residueChainNorm != ''">
					<li><a><xsl:text>Normalized residue chain: </xsl:text><xsl:value-of select="residueChainNorm"></xsl:value-of></a></li>
				</xsl:if>
				<xsl:if test="charge">
					<li><a><xsl:text>Formal charge: </xsl:text><xsl:value-of select="charge"></xsl:value-of></a></li>
				</xsl:if>
			</ul>
		</li>
		</xsl:for-each>
	</ul>
</li>
<!-- end molecules -->

		</ul>
	</li>
</xsl:if>
			
<!-- ====================== PROCESSES =========================== -->

<li class="jstree-open"><a><xsl:text>Processes</xsl:text></a>
<ul>
	<xsl:for-each select="processes/process">
	<li class="jstree-open">
		<a>
		<xsl:choose>
			<xsl:when test="@name != ''">
				<xsl:value-of select="@name"></xsl:value-of>
			</xsl:when>
			<xsl:otherwise><xsl:text>Process</xsl:text></xsl:otherwise>
		</xsl:choose>
		<xsl:if test="description != ''">
			 <xsl:text> [ </xsl:text><i><xsl:value-of select="description"></xsl:value-of></i><xsl:text> ]</xsl:text>
		</xsl:if>
		</a>
		<ul>
		
<!-- ====================== TASKS =========================== -->

<xsl:if test="tasks/task">
	<li class="jstree-open">
		<a>Tasks</a>
		<ul>
		
<xsl:for-each select="tasks/task">
<li>
	<a><xsl:value-of select="@type"></xsl:value-of><xsl:text> task</xsl:text>
	<xsl:if test="description != ''">
		<xsl:text> [ </xsl:text><i><xsl:value-of select="description"></xsl:value-of></i><xsl:text> ]</xsl:text>
	</xsl:if>
	</a>
	<ul>
		<xsl:if test="software">
			<li><a><xsl:text>Software: </xsl:text>
			<xsl:value-of select="software/@name"></xsl:value-of>
			<xsl:if test="software/@version">
				<xsl:text> </xsl:text>
				<xsl:value-of select="software/@version"></xsl:value-of>
			</xsl:if>
			<xsl:if test="software/@executableName">
				<xsl:text> (</xsl:text><xsl:value-of select="software/@executableName"></xsl:value-of><xsl:text> )</xsl:text>
			</xsl:if>
			</a></li>
		</xsl:if>
		<xsl:if test="boundaryConditions">
			<li><a><xsl:text>Boundary conditions: </xsl:text>
				<xsl:value-of select="boundaryConditions"></xsl:value-of>
			</a></li>
		</xsl:if>
		<xsl:if test="inputFiles/file">
			<li><a><xsl:text>Files (input)</xsl:text></a>
				<ul>
				<xsl:for-each select="inputFiles/file">
					<xsl:sort select="."></xsl:sort>
					<li><a><xsl:value-of select="."></xsl:value-of></a></li>
				</xsl:for-each>
				</ul>
			</li>
		</xsl:if>
		<xsl:if test="outputFiles/file">
			<li><a><xsl:text>Files (output)</xsl:text></a>
				<ul>
				<xsl:for-each select="outputFiles/file">
					<xsl:sort select="."></xsl:sort>
					<li><a><xsl:value-of select="."></xsl:value-of></a></li>
				</xsl:for-each>
				</ul>
			</li>
		</xsl:if>
		<xsl:if test="simulatedConditionSet">
			<li><a><xsl:text>Conditions</xsl:text></a>
				<ul>
					<xsl:if test="simulatedConditionSet/initialTemperature">
						<li><a><xsl:text>Temperature (initial): </xsl:text>
							<xsl:value-of select="simulatedConditionSet/initialTemperature/@value"></xsl:value-of>
							<xsl:text> </xsl:text>
							<xsl:value-of select="simulatedConditionSet/initialTemperature/@unit"></xsl:value-of>
						</a></li>
					</xsl:if>
					<xsl:if test="simulatedConditionSet/referenceTemperature">
						<li><a><xsl:text>Temperature (reference): </xsl:text>
							<xsl:value-of select="simulatedConditionSet/referenceTemperature/@value"></xsl:value-of>
							<xsl:text> </xsl:text>
							<xsl:value-of select="simulatedConditionSet/referenceTemperature/@unit"></xsl:value-of>
						</a></li>
					</xsl:if>
					<xsl:if test="simulatedConditionSet/initialPressure">
						<li><a><xsl:text>Pressure (initial): </xsl:text>
							<xsl:value-of select="simulatedConditionSet/initialPressure/@value"></xsl:value-of>
							<xsl:text> </xsl:text>
							<xsl:value-of select="simulatedConditionSet/initialPressure/@unit"></xsl:value-of>
						</a></li>
					</xsl:if>
					<xsl:if test="simulatedConditionSet/referencePressure">
						<li><a><xsl:text>Pressure (reference): </xsl:text>
							<xsl:value-of select="simulatedConditionSet/referencePressure/@value"></xsl:value-of>
							<xsl:text> </xsl:text>
							<xsl:value-of select="simulatedConditionSet/referencePressure/@unit"></xsl:value-of>
						</a></li>
					</xsl:if>
				</ul>
			</li>
		</xsl:if>
		<xsl:if test="taskExecution">
			<li><a><xsl:text>Execution</xsl:text></a>
				<ul>
					<xsl:if test="taskExecution/@terminationStatus">
						<li><a><xsl:text>Termination status: </xsl:text>
						<xsl:choose>
							<xsl:when test="taskExecution/@terminationStatus = 'error'">
								<strong><span style="color:red">error</span></strong>
							</xsl:when>
							<xsl:when test="taskExecution/@terminationStatus = 'normal'">
								<xsl:text>normal</xsl:text>
							</xsl:when>
							<xsl:otherwise><xsl:text> ? </xsl:text></xsl:otherwise>
						</xsl:choose>
						</a></li>
					</xsl:if>
					<xsl:if test="taskExecution/executionTime">
						<li><a><xsl:text>Execution time: </xsl:text><xsl:value-of select="taskExecution/executionTime/@value"></xsl:value-of><xsl:text> </xsl:text>
								<xsl:value-of select="taskExecution/executionTime/@unit"></xsl:value-of></a></li>
					</xsl:if>
					<xsl:if test="taskExecution/startTimestamp &gt; 0">
						<li><a><xsl:text>Execution start: </xsl:text>
							<xsl:call-template name="epoch-to-string">
						    	<xsl:with-param name="epoch-time" select="taskExecution/startTimestamp"></xsl:with-param>
							</xsl:call-template>
						</a></li>
					</xsl:if>
					<xsl:if test="taskExecution/endTimestamp &gt; 0">
						<li><a><xsl:text>Execution end: </xsl:text>
							<xsl:call-template name="epoch-to-string">
						    	<xsl:with-param name="epoch-time" select="taskExecution/endTimestamp"></xsl:with-param>
							</xsl:call-template>
						</a></li>
					</xsl:if> 
					<xsl:if test="taskExecution/numberOfCPUs &gt; 0">
						<li><a><xsl:text>CPUs: </xsl:text><xsl:value-of select="taskExecution/numberOfCPUs"></xsl:value-of></a></li>
					</xsl:if> 
					<xsl:if test="taskExecution/numberOfGPUs &gt; 0">
						<li><a><xsl:text>GPUs: </xsl:text><xsl:value-of select="taskExecution/numberOfGPUs"></xsl:value-of>
							<xsl:if test="computingEnvironment/gpuArchitecture">
								<xsl:text> (</xsl:text><xsl:value-of select="computingEnvironment/gpuArchitecture"></xsl:value-of><xsl:text>)</xsl:text>
							</xsl:if>
						</a></li>
					</xsl:if>
				</ul>
			</li>
		</xsl:if>
<!-- ====================== PARAMETER SETS =========================== -->
<xsl:for-each select="parameterSet">
	<li>
		<a><xsl:text>Parameter set</xsl:text>
			<xsl:if test="@type">
				<xsl:text> for </xsl:text><xsl:value-of select="@type"></xsl:value-of>
			</xsl:if>
		</a>
		<ul>
			<xsl:if test="@name">
				<li><a><xsl:text>Method: </xsl:text><xsl:value-of select="@name"></xsl:value-of></a></li>
			</xsl:if>
			<xsl:if test="samplingMethod">
				<li><a><xsl:text>Enhanced sampling: </xsl:text><xsl:value-of select="samplingMethod/name"></xsl:value-of></a></li>
			</xsl:if>
			<xsl:if test="numberOfReplica">
				<li><a><xsl:text>Number of replica: </xsl:text><xsl:value-of select="numberOfReplica"></xsl:value-of></a></li>
			</xsl:if>
			<xsl:if test="solventType">
				<li><a><xsl:text>Solvent type: </xsl:text><xsl:value-of select="solventType"></xsl:value-of>
						<xsl:if test="implicitSolventModel">
							<xsl:text> (</xsl:text><xsl:value-of select="implicitSolventModel"></xsl:value-of><xsl:text>)</xsl:text>
						</xsl:if>
					</a>
				</li>
			</xsl:if>
			<xsl:if test="numberOfSteps">
				<li><a><xsl:text>Number of steps: </xsl:text><xsl:value-of select="numberOfSteps"></xsl:value-of></a></li>
			</xsl:if>
			<xsl:if test="timeStepLength">
				<li><a><xsl:text>Time step length: </xsl:text><xsl:value-of select="timeStepLength/@value"></xsl:value-of><xsl:text> </xsl:text>[<xsl:value-of select="timeStepLength/@unit"></xsl:value-of>]</a></li>
			</xsl:if>
			
			<!-- Molecular dynamics -->
			
			<xsl:if test="ensemble">
				<li><a><xsl:text>Ensemble: </xsl:text><xsl:value-of select="ensemble"></xsl:value-of></a></li>
			</xsl:if>
			<xsl:if test="constraints/constraint">
				<xsl:for-each select="constraints/constraint">
					<li><a><xsl:text>Constraint: </xsl:text><xsl:value-of select="./@algorithm"></xsl:value-of>
							<xsl:if test="./@target">
								<xsl:text> (</xsl:text><xsl:value-of select="./@target"></xsl:value-of><xsl:text>)</xsl:text>
							</xsl:if>
					</a></li>
				</xsl:for-each>
			</xsl:if>
			<xsl:if test="restraints/restraint">
				<li><a><xsl:text>Restraints: yes</xsl:text></a></li>
			</xsl:if>
			<xsl:if test="barostat">
				<li><a><xsl:text>Barostat</xsl:text></a>
				<ul>
					<xsl:if test="barostat/@algorithm">
						<li><a><xsl:text>Algorithm: </xsl:text><xsl:value-of select="barostat/@algorithm"></xsl:value-of></a></li>
					</xsl:if>
					<xsl:if test="barostat/@type">
						<li><a><xsl:text>Type: </xsl:text><xsl:value-of select="barostat/@type"></xsl:value-of></a></li>
					</xsl:if>
					<xsl:if test="barostat/timeConstant">
						<li><a><xsl:text>Time constant: </xsl:text><xsl:value-of select="barostat/timeConstant"></xsl:value-of></a></li>
					</xsl:if>
				</ul></li>
			</xsl:if>
			<xsl:if test="thermostat">
				<li><a><xsl:text>Thermostat</xsl:text></a>
				<ul>
					<xsl:if test="thermostat/@name">
						<li><a><xsl:text>Algorithm: </xsl:text><xsl:value-of select="thermostat/@name"></xsl:value-of></a></li>
					</xsl:if>
					<xsl:if test="thermostat/timeConstant">
						<li><a><xsl:text>Time constant: </xsl:text><xsl:value-of select="thermostat/timeConstant"></xsl:value-of></a></li>
					</xsl:if>
					<xsl:if test="thermostat/collisionFrequency">
						<li><a><xsl:text>Collision frequency: </xsl:text><xsl:value-of select="thermostat/collisionFrequency/@value"></xsl:value-of><xsl:text> [</xsl:text><xsl:value-of select="thermostat/collisionFrequency/@unit"></xsl:value-of><xsl:text>]</xsl:text></a></li>
					</xsl:if>
					<xsl:if test="thermostat/randomSeed">
						<li><a><xsl:text>Random seed: </xsl:text><xsl:value-of select="thermostat/randomSeed"></xsl:value-of></a></li>
					</xsl:if>
				</ul></li>
			</xsl:if>
			
			<xsl:if test="electrostaticsModel">
			<li>
				<a>Electrostatics</a>
				<ul>
					<xsl:if test="electrostaticsModel/name">
						<li><a><xsl:text>Model: </xsl:text><xsl:value-of select="electrostaticsModel/name"></xsl:value-of></a></li>
					</xsl:if>
					<xsl:if test="electrostaticsModel/interpolationOrder &gt; 0">
						<li><a><xsl:text>Interpolation order: </xsl:text><xsl:value-of select="electrostaticsModel/interpolationOrder"></xsl:value-of></a></li>
					</xsl:if>
					<xsl:if test="electrostaticsModel/ewaldCoefficient &gt; 0.0">
						<li><a><xsl:text>Ewald coefficient: </xsl:text><xsl:value-of select="electrostaticsModel/ewaldCoefficient"></xsl:value-of></a></li>
					</xsl:if>
					<xsl:if test="electrostaticsModel/cutoff/@value &gt; 0.0">
						<li><a><xsl:text>Cutoff: </xsl:text><xsl:value-of select="electrostaticsModel/cutoff/@value"></xsl:value-of>
							<xsl:text> </xsl:text><xsl:value-of select="electrostaticsModel/cutoff/@unit"></xsl:value-of>
						</a></li>
					</xsl:if>
				</ul>
			</li>
			</xsl:if>
			<xsl:if test="cutoffForVanDerWaals">
				<li><a><xsl:text>Van der Waals interaction cutoff: </xsl:text>
						<xsl:value-of select="cutoffForVanDerWaals/@value"></xsl:value-of>
						<xsl:text> [</xsl:text><xsl:value-of select="cutoffForVanDerWaals/@unit"></xsl:value-of><xsl:text>]</xsl:text>
				</a></li>
			</xsl:if>
			
			<!-- Quantum chemistry -->
			
			<xsl:if test="basisSets/basisSet">
				<li><a><xsl:text>Basis sets</xsl:text></a>
					<ul>
					<xsl:for-each select="basisSets/basisSet">
						<li><a><xsl:value-of select="."></xsl:value-of></a></li>
					</xsl:for-each>
					</ul>
				</li>
			</xsl:if>
			<xsl:if test="spinMultiplicity">
				<li><a><xsl:text>Spin multiplicity: </xsl:text><xsl:value-of select="spinMultiplicity"></xsl:value-of></a></li>
			</xsl:if>
			<xsl:if test="totalCharge">
				<li><a><xsl:text>Total charge: </xsl:text><xsl:value-of select="totalCharge"></xsl:value-of></a></li>
			</xsl:if>
			<xsl:if test="calculations/calculation">
				<li><a><xsl:text>Calculations</xsl:text></a>
					<ul>
					<xsl:for-each select="calculations/calculation">
						<li><a><xsl:value-of select="."></xsl:value-of></a></li>
					</xsl:for-each>
					</ul>
				</li>
			</xsl:if>
			
			<!-- QM/MM -->
	
			<xsl:if test="qmSpaceDefinition">
				<li><a><xsl:text>QM region: </xsl:text><xsl:value-of select="qmSpaceDefinition"></xsl:value-of></a></li>
			</xsl:if>
			<xsl:if test="boundaryTreatment">
				<li><a><xsl:text>QM/MM boundary treatment: </xsl:text><xsl:value-of select="boundaryTreatment"></xsl:value-of></a></li>
			</xsl:if>
			<xsl:if test="qmElectrostatics">
				<li><a><xsl:text>QM electrostatics</xsl:text></a>
					<ul>
						<li><a><xsl:value-of select="qmElectrostatics/name"></xsl:value-of></a></li>
						<xsl:if test="qmElectrostatics/cutoff/@value">
							<li><a><xsl:text>Cutoff: </xsl:text>
								<xsl:value-of select="qmElectrostatics/cutoff/@value"></xsl:value-of>
								<xsl:text> [</xsl:text><xsl:value-of select="qmElectrostatics/cutoff/@unit"></xsl:value-of><xsl:text>]</xsl:text>
							</a></li>
						</xsl:if>
					</ul>
				</li>
			</xsl:if>

		</ul>
	</li>
</xsl:for-each>
<!-- end parameter sets -->

				</ul>
			</li>
		</xsl:for-each>
		</ul>
	</li>
</xsl:if>
<!-- end tasks -->

		</ul>
	</li>
	</xsl:for-each>
</ul>
</li>
<!-- end processes -->

		</ul>
	</li>
</xsl:for-each>
<!-- end process groups -->

			</ul>
			</li>
		</ul>
	<!-- file tree end here -->
	</div>
<br/>
<!-- post ends here -->
</div>

</div>		
<!-- content-wrap ends here -->		
</div></div>

<br/>
<!-- wrap ends here -->
</div>

<!-- footer -->
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
	$("#fileTree").jstree({ 
		"themes" : {
			"theme" : "default",
			"dots" : true,
			"icons" : false
		},
		"plugins" : [ "themes", "html_data", "ui" ]
	});
});
]]>
</xsl:comment>
</script>
</body>
</html>
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

<!-- ============================================ -->

<xsl:template name="epoch-to-string">
  <xsl:param name="epoch-time" />
  <xsl:value-of select='xs:dateTime("1970-01-01T00:00:00") + ($epoch-time*1000) * xs:dayTimeDuration("PT0.001S")'/>
</xsl:template>
 
<!-- ============================================ -->

</xsl:stylesheet>
