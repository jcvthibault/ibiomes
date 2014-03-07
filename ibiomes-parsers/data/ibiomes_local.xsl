<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" 
	doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
    doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" />
    
<xsl:template match="/experiment">
	
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="Distribution" content="Global" />
<meta name="Author" content="Julien Thibault" />
<meta name="Robots" content="index,follow" />
<link type="text/css" rel="stylesheet" href="../../style/ibiomes-lite.css" />
<link type="text/css" rel="stylesheet" href="../../style/smoothness/jquery-ui-1.8.20.custom.css" />
<script type="text/javascript" src="../../js/jquery/js/jquery-1.7.2.min.js">&#160;</script>
<script type="text/javascript" src="../../js/jquery/js/jquery-ui-1.8.20.custom.min.js">&#160;</script>
<script type="text/javascript" src="../../js/jstree/_lib/jquery.cookie.js">&#160;</script>
<script type="text/javascript" src="../../js/jstree/_lib/jquery.hotkeys.js">&#160;</script>
<script type="text/javascript" src="../../js/jstree/jquery.jstree.js">&#160;</script>
<title>iBIOMES Lite</title>

</head>
<body>
	<h1>Experiment</h1>
	<xsl:if test="description">
		<i><xsl:value-of select="description"></xsl:value-of></i>
	</xsl:if>
	
	<xsl:for-each select="processes/process">
		<h2>Process</h2>
		
		<h3>Molecular system</h3>
		<xsl:if test="molecularSystem/description">
			<p><i><xsl:value-of select="molecularSystem/description"></xsl:value-of></i></p>
		</xsl:if>
		<table>
			<tr>
				<td>Number of molecules</td>
				<td><xsl:value-of select="molecularSystem/moleculeCount"></xsl:value-of></td>
			</tr>
			<tr>
				<td>Number of atoms</td>
				<td><xsl:value-of select="molecularSystem/atomCount"></xsl:value-of></td>
			</tr>
			<tr>
				<td>Number of ions</td>
				<td><xsl:value-of select="molecularSystem/ionCount"></xsl:value-of></td>
			</tr>
			<tr>
				<td>Number of waters</td>
				<td><xsl:value-of select="molecularSystem/waterCount"></xsl:value-of></td>
			</tr>
		</table>
		
		<strong>Molecules</strong>
		<table>
		<xsl:for-each select="molecularSystem/molecules/molecule">
		<tr><td>
			<strong>Molecule</strong> (<xsl:value-of select="@type"></xsl:value-of>)
			<table>
				<xsl:if test="@name">
					<tr>
						<td>Name</td>
						<td><xsl:value-of select="@name"></xsl:value-of></td>
					</tr>
				</xsl:if>
				<tr>
					<td>Number of atoms</td>
					<td><xsl:value-of select="@atomCount"></xsl:value-of></td>
				</tr>
				<xsl:if test="@residueCount">
					<tr>
						<td>Number of residues</td>
						<td><xsl:value-of select="@residueCount"></xsl:value-of></td>
					</tr>
				</xsl:if>
			</table>
			<xsl:if test="residueChain">
				<p>Residue chain: <xsl:value-of select="residueChain"></xsl:value-of></p>
			</xsl:if>
			<xsl:if test="residueChainNorm">
				<p>Normalized residue chain: <xsl:value-of select="residueChainNorm"></xsl:value-of></p>
			</xsl:if>
		</td></tr>
		</xsl:for-each>
		</table>
		
		<h3>Tasks</h3>
		<table>
		<xsl:for-each select="tasks/task">
			<tr><td>
				<h4><xsl:value-of select="@type"></xsl:value-of> task:
				<xsl:if test="description">
					<i><xsl:value-of select="description"></xsl:value-of></i>
				</xsl:if>
				</h4>
				<xsl:if test="software">
					<p><strong>Software:</strong>
					<xsl:value-of select="software/@name"></xsl:value-of>
					<xsl:value-of select="software/version"></xsl:value-of></p>
				</xsl:if>
				
				<xsl:if test="relatedFiles/file">
					<p><strong>Files:</strong></p>
					<ul>
					<xsl:for-each select="relatedFiles/file">
						<li><xsl:value-of select="."></xsl:value-of></li>
					</xsl:for-each>
					</ul>
				</xsl:if>
				
				<xsl:for-each select="method">
					<p><strong>Parameter set</strong></p>
					<table>
						<tr>
							<td>Boundary conditions</td>
							<td><xsl:value-of select="boundaryConditions"></xsl:value-of></td>
						</tr>
						<xsl:if test="solventType">
							<tr>
								<td>Solvent type</td>
								<td><xsl:value-of select="solventType"></xsl:value-of>
									<xsl:if test="implicitSolventModel">
										(<xsl:value-of select="implicitSolventModel"></xsl:value-of>)
									</xsl:if>
								</td>
							</tr>
						</xsl:if>
						<xsl:if test="numberOfSteps">
							<tr>
								<td>Number of steps</td>
								<td><xsl:value-of select="numberOfSteps"></xsl:value-of></td>
							</tr>
						</xsl:if>
						<xsl:if test="timeStepLength">
							<tr>
								<td>Time step length</td>
								<td><xsl:value-of select="timeStepLength"></xsl:value-of> ps</td>
							</tr>
						</xsl:if>
						
						<!-- Molecular dynamics -->
						<xsl:if test="electrostatics">
							<tr>
								<td>Electrostatics</td>
								<td><xsl:value-of select="electrostatics"></xsl:value-of></td>
							</tr>
						</xsl:if>
						
						
						<!-- Quantum chemistry -->
						<xsl:if test="basisSets/basisSet">
							<p><strong>Basis sets:</strong></p>
							<ul>
							<xsl:for-each select="basisSets/basisSet">
								<li><xsl:value-of select="."></xsl:value-of></li>
							</xsl:for-each>
							</ul>
						</xsl:if>
						<xsl:if test="spinMultiplicity">
							<tr>
								<td>Spin multiplicity</td>
								<td><xsl:value-of select="spinMultiplicity"></xsl:value-of></td>
							</tr>
						</xsl:if>
						<xsl:if test="totalCharge">
							<tr>
								<td>Total charge</td>
								<td><xsl:value-of select="totalCharge"></xsl:value-of></td>
							</tr>
						</xsl:if>
						<xsl:if test="calculations/calculation">
							<p><strong>Calculations:</strong></p>
							<ul>
							<xsl:for-each select="calculations/calculation">
								<li><xsl:value-of select="."></xsl:value-of></li>
							</xsl:for-each>
							</ul>
						</xsl:if>
					</table>
				</xsl:for-each>
			</td></tr>
		</xsl:for-each>
		</table>
	</xsl:for-each>

</body>
</html>
</xsl:template>

</xsl:stylesheet>
