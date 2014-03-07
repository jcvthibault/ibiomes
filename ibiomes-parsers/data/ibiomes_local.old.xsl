<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" 
	doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
    doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" />
    
<xsl:template match="/ibiomes/directory">
	
	<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="Distribution" content="Global" />
<meta name="Author" content="Julien Thibault" />
<meta name="Robots" content="index,follow" />

<link type="text/css" rel="Stylesheet" href="http://ibiomes.chpc.utah.edu/ibiomes-web/style/smoothness/jquery-ui-1.8.20.custom.css" /> 
<link type="text/css" rel="stylesheet" href="http://ibiomes.chpc.utah.edu/ibiomes-web/style/css/ibiomes.css" />

<script type="text/javascript" src="http://ibiomes.chpc.utah.edu/ibiomes-web/js/jquery/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="http://ibiomes.chpc.utah.edu/ibiomes-web/js/jquery/js/jquery-ui-1.8.20.custom.min.js"></script>


<title>iBIOMES</title>

</head>
<body>
<!-- wrap starts here -->
<div id="wrap">
	<div id="header"><div id="header-content">
		<h1 id="logo">i<span class="gray">BIOMES</span> <span style="font-size:0.6em"><i> Lite</i></span></h1>
		<h2 id="slogan">Local indexing of biomolecular simulation data</h2>
	</div></div>
	<!-- content-wrap starts here -->
	<div id="content-wrap"><div id="content"><br/>
	<div id="main" style="width:100%"><br/>
		<div class="post">
			<h1>[Experiment] <xsl:value-of select="@name"/></h1>
	        <table class="layout-tight">
	        	<tr style="vertical-align:top">
		        	<td style="text-align:right">
		        		<xsl:choose>
			        		<xsl:when test="AVUs/AVU[@id='METHOD'] = 'Quantum mechanics'">
		        				<img class="icon" src="http://ibiomes.chpc.utah.edu/ibiomes-web/images/qm_icon_small.png" title="Quantum calculations"/>
		        			</xsl:when>
				        	<xsl:otherwise>
		            			<img class="icon" src="http://ibiomes.chpc.utah.edu/ibiomes-web/images/md_logo.png" title="Molecular dynamics simulation"/>
		    				</xsl:otherwise>
				        </xsl:choose>
	        		</td>
	        		<td style="width:20px"></td>
	        		<td style="vertical-align:top;">
	        			<table class="layout-tight">
	        				<tr><td><strong>Location: </strong><span style="color:green"><xsl:value-of select="@absolutePath"/></span></td></tr>
	        				<tr style="height:5px"/>
	        				<tr><td style="txt-align:justify"><i><xsl:value-of select="AVUs/AVU[@id='DESCRIPTION']"/></i></td></tr>
	        				<tr style="height:5px"/>
	        				<tr>
	        					<td>
	        						<table>
							        	<tr>
									        <td><strong>Method</strong></td>
									        <td style="width:10px"></td>
											<td><xsl:value-of select="AVUs/AVU[@id='METHOD']"/></td>
									        <td style="width:40px"></td>
									        <td><strong>Software package</strong></td>				        		
							        		<td style="width:10px"></td>
							        		<td><xsl:value-of select="AVUs/AVU[@id='SOFTWARE_NAME']"/></td>
									    </tr>
							        	<tr>
									        <td><strong>Molecule type</strong></td>
									        <td></td>
									        <td>
												<xsl:for-each select="AVUs/AVU[@id='MOLECULE_TYPE']">
											        <xsl:value-of select="."/>
											        <xsl:if test="not(position() = last())"> / </xsl:if>
											    </xsl:for-each>
							        		</td>
									        <td></td>
									        <td></td>
									        <td></td>
									        <td></td>
						        		</tr>
	        						</table>
	        					</td>
	        				</tr>
	        			</table>
	        		</td>
	        	</tr>
	        </table>
	    </div>
		<br/>
		<!-- the tabs -->
	<div id="tabs">
	
		<ul>
			<li><a href="#generalinfo">Structure and methods</a></li>
			<li><a href="#filesinfo">Files</a></li>
		</ul>
	
		<!-- ===================================== COLLECTION METADATA (SIMULATION PARAMETERS AND STRUCTURE) =================================== -->
		<div id="generalinfo">
		<br/>
			<h1>Molecular structure and simulation method</h1>
			<table style="width:740px;">
				<tr>
					<td colspan="3" style="text-align:justify;">
						<h2>Structural information</h2>
						<div style="word-wrap:break-word;width:710px">
						
							<xsl:if test="AVUs/AVU[@id='RESIDUE_CHAIN']" >
								<strong>Residue chain: </strong> <xsl:value-of select="AVUs/AVU[@id='RESIDUE_CHAIN']"/><br/>
								<br/>
							</xsl:if>
							<xsl:if test="AVUs/AVU[@id='RESIDUE_CHAIN_NORM']" >
								<strong>Normalized chains:</strong>
								<ul>
								<xsl:for-each select="AVUs/AVU[@id='RESIDUE_CHAIN_NORM']">
									<li><xsl:value-of select="." /></li>
								</xsl:for-each>
								</ul>
							</xsl:if>
							
							<xsl:if test="AVUs/AVU[@id='NON_STD_RESIDUE']" >
								<br/><strong>Non-standard residues:</strong>
								<ul>
								<xsl:for-each select="AVUs/AVU[@id='NON_STD_RESIDUE']">
									<li><xsl:value-of select="." /></li>
								</xsl:for-each>
								</ul>
							</xsl:if>
							
							<xsl:if test="AVUs/AVU[@id='MOLECULE_ATOMIC_COMPOSITION']" >
								<span style="font-size:1.4em;"><strong><xsl:value-of select="AVUs/AVU[@id='MOLECULE_ATOMIC_COMPOSITION']" /></strong></span>
							</xsl:if>
						
						</div>
					</td>
				</tr>
				<tr style="height:10px;"/>
				<tr>
		         	<td style="vertical-align:top;">
		         		<div class="layout">
						<table>
				            <tr><td><strong>Number of atoms</strong></td><td style="width:10px"></td><td><xsl:value-of select="AVUs/AVU[@id='COUNT_ATOM']" /></td></tr>
							<tr><td><strong>Number of water molecules</strong></td><td/><td><xsl:value-of select="AVUs/AVU[@id='COUNT_WATER']" /></td></tr>
							<tr><td><strong>Number of ions</strong></td><td/><td><xsl:value-of select="AVUs/AVU[@id='COUNT_ION']" />
								<xsl:if test="AVUs/AVU[@id='ION_TYPE']" >[ 
									<xsl:for-each select="AVUs/AVU[@id='ION_TYPE']">
										<xsl:value-of select="." />
										<xsl:if test="not(position() = last())"> / </xsl:if>
									</xsl:for-each> ]
								</xsl:if>
							</td></tr>
							<xsl:if test="AVUs/AVU[@id='MOLECULE_ATOMIC_WEIGHT']" >
				         		<tr><td><strong>Molecular weight</strong></td><td/><td><xsl:value-of select="AVUs/AVU[@id='MOLECULE_ATOMIC_WEIGHT']" /> g/mol</td></tr>
				         	</xsl:if>
				         </table>
				         </div>
			           	
				       <!-- ============================== MOLECULAR DYNAMICS INFO ============================== -->
				       
		        		<xsl:if test="(AVUs/AVU[@id='METHOD'] = 'Molecular dynamics') or (AVUs/AVU[@id='METHOD'] = 'Stochastic dynamics') or (AVUs/AVU[@id='METHOD'] = 'QM/MM')">
			        		
				       	    <br/><h2>Molecular Dynamics</h2>
							<div class="layout">
							<table>
				            <tr>
				        		<td><strong>Force-field(s)</strong></td>
				        		<td style="width:10px"></td>
								<td>
									<xsl:if test="AVUs/AVU[@id='FORCE_FIELD']" >
										<xsl:for-each select="AVUs/AVU[@id='FORCE_FIELD']">
						        			<xsl:value-of select="." />
						        			<xsl:if test="not(position() = last())"> / </xsl:if>
						        		</xsl:for-each>
					        		</xsl:if>
			        			</td>
			        		</tr>
			        		<xsl:if test="AVUs/AVU[@id='MD_SAMPLING_METHOD']" >
								<tr><td><strong>Sampling method</strong></td><td/><td><xsl:value-of select="AVUs/AVU[@id='MD_SAMPLING_METHOD']" /></td></tr>
							</xsl:if>
			        		<xsl:if test="AVUs/AVU[@id='SOLVENT']" >
								<tr><td><strong>Solvent</strong></td><td/><td><xsl:value-of select="AVUs/AVU[@id='SOLVENT']" /></td></tr>
							</xsl:if>
							<xsl:if test="AVUs/AVU[@id='CONSTRAINT']" >
								<tr><td>
									<strong>Constraints</strong></td><td/><td>
				    				<xsl:for-each select="AVUs/AVU[@id='CONSTRAINT']" >
				    					<xsl:value-of select="."/>
				    					<xsl:if test="not(position() = last())"> / </xsl:if>
				    				</xsl:for-each>
				    			</td></tr>
							</xsl:if>
							<xsl:if test="AVUs/AVU[@id='ELECTROSTATICS']" >
								<tr><td><strong>Electrostatics interactions</strong></td><td/><td><xsl:value-of select="AVUs/AVU[@id='ELECTROSTATICS']" /></td></tr>
							</xsl:if>
							<xsl:if test="AVUs/AVU[@id='UNIT_SHAPE']" >
								<tr><td><strong>Unit shape</strong></td><td/><td><xsl:value-of select="AVUs/AVU[@id='UNIT_SHAPE']" /></td></tr>
							</xsl:if>
							<xsl:if test="AVUs/AVU[@id='THERMOSTAT']" >
								<tr><td><strong>Thermostat</strong></td><td/><td><xsl:value-of select="AVUs/AVU[@id='THERMOSTAT']" /></td></tr>
							</xsl:if>
							<xsl:if test="AVUs/AVU[@id='BAROSTAT']" >
								<tr><td><strong>Barostat</strong></td><td/><td><xsl:value-of select="AVUs/AVU[@id='BAROSTAT']" /></td></tr>
							</xsl:if>
							<xsl:if test="AVUs/AVU[@id='BOUNDARY_CONDITIONS']" >
								<tr><td><strong>Boundary conditions</strong></td><td/><td><xsl:value-of select="AVUs/AVU[@id='BOUNDARY_CONDITIONS']" /></td></tr>
							</xsl:if>
							</table>
				      		</div>
						</xsl:if>
				        	
				       <!-- ============================== QUANTUM MECHANICS INFO ============================== -->
				       
				       	<xsl:if test="(AVUs/AVU[@id='METHOD'] = 'Quantum mechanics') or (AVUs/AVU[@id='METHOD'] = 'QM/MM')">
				       
				          <br/><h2>Quantum calculations</h2>
							<div class="layout">
							<table>
				            <tr>
				        		<td><strong>Level(s) of theory</strong></td>
				        		<td style="width:10px"></td>
				        		<td>
				        			<xsl:if test="AVUs/AVU[@id='QM_LEVEL_OF_THEORY']" >
							        	<xsl:for-each select="AVUs/AVU[@id='QM_LEVEL_OF_THEORY']" >
							        		<xsl:value-of select="."/>
					    					<xsl:if test="not(position() = last())"> / </xsl:if>
					    				</xsl:for-each>
				    				</xsl:if>
						        </td>
				        	</tr>
				        	<xsl:if test="AVUs/AVU[@id='QM_EXCHANGE_CORRELATION']" >
								<tr><td><strong>Exchange-correlation functional</strong></td><td/>
								<td><xsl:value-of select="AVUs/AVU[@id='QM_EXCHANGE_CORRELATION']" /></td></tr>
							</xsl:if>
							<tr>
				        		<td><strong>Basis set(s)</strong></td><td/>
								<td>
									<xsl:if test="AVUs/AVU[@id='QM_BASIS_SET']" >
										<xsl:for-each select="AVUs/AVU[@id='QM_BASIS_SET']">
					    					<xsl:value-of select="."/>
					    					<xsl:if test="not(position() = last())"> / </xsl:if>
					    				</xsl:for-each>
					    			</xsl:if>
						        </td>
							</tr>
							<tr><td><strong>Spin multiplicity</strong></td><td/><td><xsl:value-of select="AVUs/AVU[@id='QM_SPIN_MULTIPLICITY']" /></td></tr>
							<tr><td><strong>Molecule charge</strong></td><td/><td><xsl:value-of select="AVUs/AVU[@id='QM_TOTAL_MOLECULE_CHARGE']" /></td></tr>
				        	</table>
				        	</div>
							
							<xsl:if test="AVUs/AVU[@id='CALCULATION']" >
								<br/>
								<div class="layout">
								<table>
								<tr>
					        		<td><strong>Calculations</strong>
										<ul>
										<xsl:if test="AVUs/AVU[@id='CALCULATION']" >
											<xsl:for-each select="AVUs/AVU[@id='CALCULATION']">
												<li><xsl:value-of select="." /></li>
							        		</xsl:for-each>
							        	</xsl:if>
							        	</ul>
							        </td>
								</tr>
					        	</table>
					        	</div>
					        </xsl:if>
					        
						</xsl:if>
			       </td>
			       
			       <!-- ============================== 3D STRUCTURE IN JMOL APPLET ============================== -->
			        	            
		            <td style="vertical-align:top;">
		            	<table class="layout-tight" >
		            		<tr><td style="vertical-align:top; border:1px solid #DADADA;width:350px;">
			            		<h3>3D structure</h3>
			            		<xsl:variable name="jmolFileName" select="AVUs/AVU[@id='MAIN_3D_STRUCTURE_FILE']"/>
			                    <xsl:choose>
					        		<xsl:when test="AVUs/AVU[@id='MAIN_3D_STRUCTURE_FILE']">
					        			<xsl:variable name="jmolFileNode" select="//file[@name=$jmolFileName]"/>
						            	<p>
						            		<img class="icon" src="http://ibiomes.chpc.utah.edu/ibiomes-web/images/icons/mol.png" />
						            		<i><xsl:value-of select="$jmolFileNode/AVUs/AVU[@id='DESCRIPTION']" /></i>
						            	</p>
						            	<div class="jmol">
   											<div id="jmol"></div>
   										</div>
   										<!--  <script language="javascript">
											var loadScript = "load <xsl:value-of select="$jmolFileNode/@absolutePath"/>; select all; wireframe 50; set ambient 5; set specpower 40; spin on;";
											jmolInitialize("./jmol", "JmolAppletSigned0.jar");
											$("#jmol").jmol({
									            'script': loadScript
									        });			            	
        								</script>
        								-->
									</xsl:when>
									<xsl:otherwise>
										<p>No structure to display</p>
									</xsl:otherwise>
					            </xsl:choose>
			            	</td></tr>
						</table>
	            	</td>
	            	<td></td>
	            	
	         	</tr>	         	
	         	</table>
	        <br/>
			
		</div>
		
		<!-- ===================================== FILE COLLECTION =================================== -->
		<div id="filesinfo">
			
			<br/>
			<xsl:for-each select="subdirectories/directory">
				<p style="cursor:pointer">
					<xsl:attribute name="id">subdir_link_<xsl:value-of select="@parent"/>/_<xsl:value-of select="@name"/>')</xsl:attribute>
					<xsl:attribute name="name">subdir_link_root/_<xsl:value-of select="@name"/>')</xsl:attribute>
					<xsl:attribute name="onclick">selectDirectory('<xsl:value-of select="@absolutePath"/>')</xsl:attribute>
					<img class="icon" src="http://ibiomes.chpc.utah.edu/ibiomes-web/images/icons/folder_full_small.png"/>
					<xsl:value-of select="@name"/>
				</p>
				<xsl:call-template name="fileList">
				    <xsl:with-param name="directoryPath" select="@absolutePath"/>
				  </xsl:call-template>
			</xsl:for-each>
			<br/>
			
			<div name="directory_root">
				<xsl:attribute name="id">directory_<xsl:value-of select="@absolutePath"/></xsl:attribute>
				
				<h1>Files in '/'</h1>
				<br/>
				
				<div id="accordion">
	    
		    		<xsl:for-each select="files/fileGroup">
		    			<xsl:variable name="format" select="@format" />
		    			
					    <h3><a href="#"><xsl:value-of select="$format"/> files</a></h3>
						<div>
							<table>
				    		<xsl:for-each select="file">
				    			<tr>
				    				<td class="first">
				    				<a target="_blank">
				    					<xsl:attribute name="href">file://<xsl:value-of select="@absolutePath"/></xsl:attribute>
					    				<img>
					    					<xsl:attribute name="src">
												<xsl:choose>
								    				<xsl:when test="($format = 'PDB') or ($format = 'GROMACS gro') or ($format = 'GAUSSIAN log') or ($format = 'NWChem output') or ($format = 'XYZ') or ($format = 'CML') or ($format = 'SDF')">
								    					<xsl:value-of select="'http://ibiomes.chpc.utah.edu/ibiomes-web/images/icons/mol.png'"/>
								    				</xsl:when>
								    				<xsl:when test="($format = 'JPEG') or ($format = 'PNG') or ($format = 'GIF') or ($format = 'TIF')">
								    					<xsl:value-of select="'http://ibiomes.chpc.utah.edu/ibiomes-web/images/icons/image.png'"/>
								    				</xsl:when>
								    				<xsl:otherwise>
								    					<xsl:value-of select="'http://ibiomes.chpc.utah.edu/ibiomes-web/images/icons/text_page.png'"/>
								    				</xsl:otherwise>
								    			</xsl:choose>
											</xsl:attribute>
					    					<xsl:attribute name="title"><xsl:value-of select="@absolutePath"/></xsl:attribute>
					    				</img>
					    			</a>
					    			</td>
				    				<td><xsl:value-of select="@name"/></td>
				    				<td><xsl:value-of select="format-number( round(@size div 1000), '#')"/> KB</td>
				    				<td><xsl:value-of select="@modificationDate"/></td>
				    			</tr>
				    		</xsl:for-each>
				    		</table>
				    	</div>
					</xsl:for-each>
				</div>
		    	<br/>
			</div>
		</div>
		<!-- ===================================== FILE COLLECTION =================================== -->		
		
	</div>	</div>
				
	<!-- content-wrap ends here -->		
	</div></div>

<br/>
<!-- wrap ends here -->
</div>
<div id="footer">
<div id="footer-content">
	<div style="text-align:center">
		&#169; copyright 2011 <strong><a class="link" href="http://www.utah.edu">University of Utah</a></strong><br /> 
		Design by: <a class="link" href="http://www.styleshout.com"><strong>styleshout</strong></a>
</div></div></div>

<script type="text/javascript" defer="defer">
<xsl:comment>
<![CDATA[		
	$(function(){
		$("#tabs").tabs();
		$("#accordion").accordion({
			collapsible: true,
	  		autoHeight: false,
	  		animated: true,
	  		active: false
		});
		showRootInfoOnly();
		
	});
]]>
</xsl:comment>
</script>
<script type="text/javascript">
<xsl:comment>
<![CDATA[		

	function selectDirectory(directoryPath)
	{
		var target = $("#directory_"+directoryPath);
		if (target!=null && target.attr('name')=='directoy_root')
			showRootInfoOnly();
		else 
		{
			var directoryElts = $('div[id^="directory_"]');
			var i = 0;
			for (i=0;i<directoryElts.length;i++){
				var elt = directoryElts[i];
				if (elt.id != ('directory_'+directoryPath)){
					$(elt).hide();
				}
				else {
					$(elt).show();
				}
			}
		
			var directoryLinks = $('p[id^="subdir_link_"]');
			for (i=0;i<directoryLinks.length;i++){
				var elt = directoryLinks[i];
				var linkNamePrefix = 'subdir_link_' + directoryPath + '/_';
				if (linkNamePrefix != elt.id.substr(0,linkNamePrefix.length) ){
					$(elt).hide();
				}
				else $(elt).show();
			}
		}
	}
	
	function showRootInfoOnly()
	{
		var directoryElts = $('[id^="directory_"]');
		var i = 0;
		for (i=0;i<directoryElts.length;i++){
			var elt = directoryElts[i];
			$(elt).hide();
		}
		$('div[name="directory_root"]').show();
		
		var directoryLinks = $('[id^="subdir_link_"]');
		for (i=0;i<directoryLinks.length;i++){
			var elt = directoryLinks[i];
			$(elt).hide();
		}
		$('p[name^="subdir_link_root/_"]').show();
	}
]]>
</xsl:comment>
</script>
</body>
</html>

</xsl:template>	

<!-- ============================================== -->
<xsl:template name="fileList">

	<xsl:param name="directoryPath" />
	<xsl:variable name="directoryNode" select="//directory[@absolutePath=$directoryPath]"/>
	
	<div>
		<xsl:attribute name="id">directory_<xsl:value-of select="$directoryPath"/></xsl:attribute>
		<h1>Files in '<xsl:value-of select="$directoryNode/@name"/>'</h1>
		<br/>
		<p style="cursor:pointer">
			<xsl:attribute name="id">subdir_link_<xsl:value-of select="$directoryPath"/>/_root')</xsl:attribute>
			<xsl:attribute name="onclick">showRootInfoOnly()</xsl:attribute>
			<img class="icon" src="http://ibiomes.chpc.utah.edu/ibiomes-web/images/icons/folder_full_small.png" title="go to root directory"/> /
		</p>
		<p style="cursor:pointer">
			<xsl:attribute name="id">subdir_link_<xsl:value-of select="$directoryPath"/>/_up')</xsl:attribute>
			<xsl:attribute name="onclick">selectDirectory('<xsl:value-of select="$directoryNode/@parent"/>')</xsl:attribute>
			<img class="icon" src="http://ibiomes.chpc.utah.edu/ibiomes-web/images/icons/folder_full_small.png" title="go to parent directory"/> ..
		</p>
		<xsl:if test="$directoryNode/subdirectories/directory">
			<xsl:for-each select="$directoryNode/subdirectories/directory">
				<p>
					<xsl:attribute name="id">subdir_link_<xsl:value-of select="@parent"/>/_<xsl:value-of select="@name"/>')</xsl:attribute>
					<xsl:attribute name="onclick">selectDirectory('directory_<xsl:value-of select="@absolutePath"/>')</xsl:attribute>
					<img class="icon" src="http://ibiomes.chpc.utah.edu/ibiomes-web/images/icons/folder_full_small.png"/>
					<xsl:value-of select="@name"/>
				</p>
			</xsl:for-each>
			<br/>
		</xsl:if>
		
		<xsl:if test="$directoryNode/files/fileGroup">
	  		<xsl:for-each select="$directoryNode/files/fileGroup">
	  			<xsl:variable name="format" select="@format" />
		  			
			    <h3><xsl:value-of select="$format"/> files</h3>
				<div>
					<table>
		    		<xsl:for-each select="file">
		    			<tr>
		    				<td class="first">
		    				<a target="_blank">
		    					<xsl:attribute name="href">file://<xsl:value-of select="@absolutePath"/></xsl:attribute>
			    				<img>
			    					<xsl:attribute name="src">
										<xsl:choose>
						    				<xsl:when test="($format = 'PDB') or ($format = 'GROMACS gro') or ($format = 'GAUSSIAN log') or ($format = 'NWChem output') or ($format = 'XYZ') or ($format = 'CML') or ($format = 'SDF')">
						    					<xsl:value-of select="'http://ibiomes.chpc.utah.edu/ibiomes-web/images/icons/mol.png'"/>
						    				</xsl:when>
						    				<xsl:when test="($format = 'JPEG') or ($format = 'PNG') or ($format = 'GIF') or ($format = 'TIF')">
						    					<xsl:value-of select="'http://ibiomes.chpc.utah.edu/ibiomes-web/images/icons/image.png'"/>
						    				</xsl:when>
						    				<xsl:otherwise>
						    					<xsl:value-of select="'http://ibiomes.chpc.utah.edu/ibiomes-web/images/icons/text_page.png'"/>
						    				</xsl:otherwise>
						    			</xsl:choose>
									</xsl:attribute>
			    					<xsl:attribute name="title"><xsl:value-of select="@absolutePath"/></xsl:attribute>
			    				</img>
			    			</a>
			    			</td>
		    				<td><xsl:value-of select="@name"/></td>
		    				<td><xsl:value-of select="format-number( round(@size div 1000), '#')"/> KB</td>
		    				<td><xsl:value-of select="@modificationDate"/></td>
		    			</tr>
		    		</xsl:for-each>
		    		</table>
		    	</div>
			</xsl:for-each>
		</xsl:if>
	   	<br/>
	</div>
</xsl:template>

</xsl:stylesheet>
