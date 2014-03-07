<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ page buffer="16kb" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<h:meta/>

<link type="text/css" rel="stylesheet" href="style/css/ibiomes.css" />
<link type="text/css" rel="Stylesheet" href="style/smoothness/jquery-ui-1.10.4.custom.css" /> 

<script type="text/javascript">
	var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
	var webServiceUrl = '<c:out value="${sessionScope.webServiceURL}"/>';
</script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/metadata.js"></script>
<script type="text/javascript" src="js/collections.js"></script>
<script type="text/javascript" src="js/files.js"></script>
<script type="text/javascript" src="js/acl.js"></script>
<script type="text/javascript" src="js/dialogs.js"></script>
<script type="text/javascript" src="js/resources.js"></script>
<script type="text/javascript" src="js/actions.js"></script>
<script type="text/javascript" src="js/cart.js"></script>

<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript">
		
	$(document).ready(function() {
		
		var resturl = webServiceUrl + "/rest/metadata/";
		var uri = '<c:out value="${collection.absolutePath}" />';

		$('#collection-avu-loading').hide(); //initially hide the loading icon

		getCollectionAVUs(uri);
		getAcl(uri);
		
		loadMetadataCatalogForAdd("avu_attribute_std_div","avu_value_std_div","avu_unit_std","avu_std_add", resturl);
		
		loadHiddenFiles(uri, "hiddenFileList");
		loadAnalysisFiles(uri, "currentAnalysisFileList");
		
		getCollectionSubdirectories(uri);

		$("#tabs").tabs();
		
		$("#metadataTabs").accordion({
			collapsible: true,
			heightStyle: "content",
	  		animate: true,
	  		active: false
		});
		
		function loadMetadataValuesForAutocomplete(inputId, url){
			$("#"+inputId).autocomplete({
				source: function( request, response ) {
					$.ajax({
						url: url,
						dataType: "json",
						data: {
							term: request.term
						},
						success: function( data ) {
							response( $.map( data, function( item ) {
								return {
									label: item.term,
									value: item.term
								};
							}));
						}
					});
				},
			});
		}
		
		var hasMD = <c:out value="${hasMDMetadata}" />;
		var hasQM = <c:out value="${hasQMMetadata}" />;
		
		loadMetadataValuesForAutocomplete("newSW", resturl + "SOFTWARE_NAME/values");
		loadMetadataValuesForAutocomplete("newBC", resturl + "BOUNDARY_CONDITIONS/values");
		loadMetadataValuesForAutocomplete("newSolvent", resturl + "SOLVENT/values");

		getCollectionAvuRowsForAttribute(uri, 'RESIDUE_CHAIN', 'residueChainSpecList');
		getCollectionAvuRowsForAttribute(uri, 'RESIDUE_CHAIN_NORM', 'residueChainNormList');
		getCollectionAvuRowsForAttribute(uri, 'TOTAL_MOLECULE_CHARGE', 'chargeList');
		getCollectionAvuRowsForAttribute(uri, 'SOLVENT', 'solventList');
		getCollectionAvuRowsForAttribute(uri, 'SOFTWARE_NAME', 'swList');
		getCollectionAvuRowsForAttribute(uri, 'SOFTWARE_NAME_W_VERSION', 'swVersionList');
		getCollectionAvuRowsForAttribute(uri, 'SOFTWARE_EXEC_NAME', 'execList');
		
		if (hasMD)
		{
			getCollectionAvuRowsForAttribute(uri, 'MD_MINIMIZATION', 'minimizationList');
			getCollectionAvuRowsForAttribute(uri, 'ENHANCED_SAMPLING_METHOD_NAME', 'samplingMethodList');
			getCollectionAvuRowsForAttribute(uri, 'ELECTROSTATICS', 'electrostaticsList');
			getCollectionAvuRowsForAttribute(uri, 'UNIT_SHAPE', 'unitShapeList');
			getCollectionAvuRowsForAttribute(uri, 'TERMOSTAT_ALGORITHM', 'thermostatList');
			getCollectionAvuRowsForAttribute(uri, 'BAROSTAT_ALGORITHM', 'barostatList');
			getCollectionAvuRowsForAttribute(uri, 'ENSEMBLE', 'ensembleList');
			getCollectionAvuRowsForAttribute(uri, 'FORCE_FIELD', 'ffList');
			getCollectionAvuRowsForAttribute(uri, 'CONSTRAINT_ALGORITHM', 'constraintList');
			getCollectionAvuRowsForAttribute(uri, 'CONSTRAINT_TARGET', 'constraintTargetList');
			getCollectionAvuRowsForAttribute(uri, 'RESTRAINT', 'restraintTypeList');
			getCollectionAvuRowsForAttribute(uri, 'RESTRAINT_TARGET', 'restraintTargetList');
			getCollectionAvuRowsForAttribute(uri, 'BOUNDARY_CONDITIONS', 'boundaryConditionList');

			loadMetadataValuesForAutocomplete("newMin", resturl + "MD_MINIMIZATION/values");
			loadMetadataValuesForAutocomplete("newSamplingMethod", resturl + "ENHANCED_SAMPLING_METHOD_NAME/values");
			loadMetadataValuesForAutocomplete("newElecModel", resturl + "ELECTROSTATICS/values");
			loadMetadataValuesForAutocomplete("newUnitshape", resturl + "UNIT_SHAPE/values");
			loadMetadataValuesForAutocomplete("newFF", resturl + "FORCE_FIELD/values");
			loadMetadataValuesForAutocomplete("newEnsemble", resturl + "ENSEMBLE/values");
			loadMetadataValuesForAutocomplete("newBarostat", resturl + "BAROSTAT_ALGORITHM/values");
			loadMetadataValuesForAutocomplete("newThermostat", resturl + "THERMOSTAT_ALGORITHM/values");
			loadMetadataValuesForAutocomplete("newConstraint", resturl + "CONSTRAINT_ALGORITHM/values");
			loadMetadataValuesForAutocomplete("newRestraintType", resturl + "RESTRAINT/values");
		}
		if (hasQM)
		{
			loadMetadataValuesForAutocomplete("newBS", resturl + "QM_BASIS_SET/values");
			loadMetadataValuesForAutocomplete("newLot", resturl + "QM_LEVEL_OF_THEORY/values");
			loadMetadataValuesForAutocomplete("newQmMethod", resturl + "QM_METHOD_NAME/values");
			loadMetadataValuesForAutocomplete("newCalculation", resturl + "CALCULATION/values");
			loadMetadataValuesForAutocomplete("newFunctional", resturl + "QM_EXCHANGE_CORRELATION/values");
			
			getCollectionAvuRowsForAttribute(uri, 'CALCULATION', 'calculationList');
			getCollectionAvuRowsForAttribute(uri, 'QM_BASIS_SET', 'bsList');
			getCollectionAvuRowsForAttribute(uri, 'QM_LEVEL_OF_THEORY', 'lotList');
			getCollectionAvuRowsForAttribute(uri, 'QM_METHOD_NAME', 'qmMethodList');
			getCollectionAvuRowsForAttribute(uri, 'QM_EXCHANGE_CORRELATION', 'functionalList');
			getCollectionAvuRowsForAttribute(uri, 'QM_SPIN_MULTIPLICITY', 'spinList');
		}
		
	});
</script>

<title>iBIOMES</title>
	
</head>

<body>
<!-- wrap starts here -->
<div id="wrap">

	<div id="header"><div id="header-content">	
		<h1 id="logo"><a href="index.do" title="">i<span class="gray">BIOMES</span><span style="font-size:0.6em"><i>&nbsp;&nbsp;Repository</i></span></a></h1>
		<h2 id="slogan">Integrated Biomolecular Simulations</h2>
		
		<!-- Menu Tabs -->
		<ul>
			<li><a href="index.do">Home</a></li>
			<li><a href="search.do">Search</a></li>
			<li><a href="documentation.jsp">Documentation</a></li>
			<li><a href="about.jsp">About</a></li>
			<li><a href="cart.do">Cart
			<c:if test="${not empty sessionScope.shoppingCartItemCount}">
				(<c:out value="${sessionScope.shoppingCartItemCount}" />)
			</c:if>
			</a></li>
		</ul>
		<!-- login/logout link -->
		<h:login/>
	
	</div></div>

	<!-- content-wrap starts here -->
	<div id="content-wrap"><div id="content">
    <br/>

	<div id="main" style="width:100%">
		<div class="post" style="border-color:#72A545; border-width:2px">
			<h1>[ experiment ] 
				<c:choose>
	        		<c:when test="${empty simulation.title}">
	        			<span style="text-transform:uppercase;"><c:out value="${collection.name}" /></span>
	        		</c:when>
		        	<c:otherwise>
		        		<c:out value="${simulation.title}" />
		        	</c:otherwise>
		        </c:choose>
			 	<c:if test="${not empty simulation.softwarePackages}">
			        <c:forEach items="${simulation.softwarePackages}" var="sw" varStatus="status">
	        			<c:set var="swList" value="${swList}${status.first ? '' : ', '}${sw}" scope="request" />
	        		</c:forEach>
	        		(<c:out value="${swList}" />)
	        	</c:if>
		    </h1>
	        <table class="layout-tight">
	        	<tr style="vertical-align:top">
		        	<td style="text-align:right">
		        		<c:choose>
			        		<c:when test="${simulation.method.name eq 'Quantum mechanics'}">
		        				<img class="icon" src="images/qm_icon_small.png" title="Quantum calculations"/>
		        			</c:when>
		        			<c:when test="${simulation.method.name eq 'Quantum MD'}">
		        				<img class="icon" src="images/qm_icon_small.png" title="Quantum molecular dynamics"/>
		        			</c:when>
			        		<c:when test="${simulation.method.name eq 'Replica-exchange MD'}">
		            			<img class="icon" src="images/remd_logo.png" title="Replica-exchange molecular dynamics"/>
		    				</c:when>
			        		<c:when test="${simulation.method.name eq 'QM/MM'}">
		            			<img class="icon" src="images/qmmm_logo.png" title="Hybrid QM/MM calculations"/>
		    				</c:when>
				        	<c:otherwise>
		            			<img class="icon" src="images/md_logo.png" title="Molecular dynamics simulation"/>
		    				</c:otherwise>
				        </c:choose>
	        		</td>
	        		<td style="width:20px"></td>
	        		<td style="vertical-align:top;">
	        			<table class="layout-tight">
	        				<tr><td><c:out value="${navLink}" escapeXml="false"/></td></tr>
	        				<tr style="height:5px"/>
	        				<tr>
	        					<td style="txt-align:justify"><i><c:out value="${simulation.description}" /></i></td>
	        				</tr>
	        				<tr style="height:5px"/>
	        				<tr>
	        					<td>
	        						<table>
	        							<tr>
							        		<td><strong>Software package</strong></td>				        		
							        		<td style="width:10px">&nbsp;</td>
							        		<td>
												<c:if test="${not empty simulation.softwarePackages}">
											        <c:out value="${swList}" />
									        	</c:if>
											</td>
							        		<td style="width:50px">&nbsp;</td>
							        		<td><strong>Owner</strong></td>
							        		<td style="width:10px">&nbsp;</td>
							        		<td><c:out value="${collection.owner}" /></td>
							        	</tr>
							        	<tr>
									        <td><strong>Method</strong></td>
									        <td></td>
											<td><c:out value="${simulation.method.name}" /></td>
									        <td></td>
									        <td><strong>Uploaded on</strong></td>
									        <td></td>
									        <td><c:out value="${simulation.registrationDate}" /></td>
									    </tr>
							        	<tr>
									        <td><strong>Molecule type</strong></td>
									        <td></td>
									        <td>
												<c:set var="moleculeList" value="" scope="request" />
							    				<c:forEach items="${simulation.molecularSystem.moleculeTypes}" var="molecule" varStatus="status" >
							    					<c:set var="moleculeList" value="${moleculeList}${status.first ? '' : ' / '}${molecule}" scope="request" />
							    				</c:forEach>
							    				<c:out value="${moleculeList}"/>
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
	    
	    <div>
       		<c:if test="${ not empty error }">
				<br/>
		   		<h:messageError message="${error}" />
			</c:if>
			<c:if test="${ not empty message }">
				<br/>
		   		<h:messageSuccess message="${message}" />
			</c:if>
	    </div>
	
		<!-- actions on collection -->
		<h:collectionActions collectionUri="${collection.absolutePath}" canWrite="true" isExperiment="true" isEdit="true" />

	<!-- the tabs -->
	<div id="tabs">
	
		<ul>
			<li><a href="#editinfo">Experiment metadata</a></li>
			<li><a href="#edittemplate">Summary template</a></li>
			<li><a href="#editinfoadv">Advanced edit</a></li>
			<li><a href="#permissioninfo">Permissions</a></li>
		</ul>

		<!-- ===================================== EDIT METADATA (SIMULATION PARAMETERS AND STRUCTURE) =================================== -->
	    <div id="editinfo">
	    	<br/>
	   		<h1>Automatic metadata generation</h1>
	   		<br/>
        	<div style="border:1px solid #DADADA;margin:10px 15px">
				<table style="margin:0; width:100%;">
					<tr style="height:10px"><td class="noborder" colspan="4"></td></tr>
					<tr style="height:0px">
						<td class="noborder"><input type="hidden" name="uri" value="<c:out value="${collection.absolutePath}" />"/></td>
						<td class="noborder"><input type="hidden" name="dispatchto" value="editcollection.do?uri=<c:out value="${collection.absolutePath}" />"/></td>
						<td class="noborder" colspan="2"></td>
					</tr>
	        		<tr>
		            	<td style="border:0px;width:20px;"><img class="icon" src="images/icons/info_small.png" title="Update topology information from file"/></td>
	            		<td style="border:0px;width:200px;">Topology/molecular system</td>
		            	<td class="noborder">
			            	<select id="topologyFileSelect" name="TOPOLOGY_FILE_PATH" style="width:400px">
				            	<option value=""></option>
			              		<c:forEach items="${topologyFileList}" var="topoFile">
							 		<option value="<c:out value="${topoFile}"/>"><c:out value="${topoFile}" /></option>
								</c:forEach>
				            </select>
		            	</td>
		            	<td style="border:0px;text-align:right">
	        				<input style="border:0" type="image" src="images/icons/process.png" title="Update metadata for this experiment" 
	        					onclick="updateTopologyFromFile('<c:out value="${collection.absolutePath}" />', 'topologyFileSelect')" />
	        			</td>
	        		</tr>
        		</table>

				<table style="margin:0; width:100%;">
					<tr style="height:10px"><td class="noborder" colspan="4"></td></tr>
					<tr style="height:0px">
						<td class="noborder"><input type="hidden" name="uri" value="<c:out value="${collection.absolutePath}" />"/></td>
						<td class="noborder"><input type="hidden" name="dispatchto" value="editcollection.do?uri=<c:out value="${collection.absolutePath}" />"/></td>
						<td class="noborder" colspan="2"></td>
					</tr>
	        		<tr>
		            	<td style="border:0px;width:20px;"><img class="icon" src="images/icons/info_small.png" title="Update simulation parameters from file"/></td>
	            		<td style="border:0px;width:200px;">Simulation parameters</td>
		            	<td class="noborder">
			            	<select id="paramFileSelect" name="PARAMETER_FILE_PATH" style="width:400px">
				            	<option value=""></option>
			              		<c:forEach items="${paramFileList}" var="paramFile">
							 		<option value="<c:out value="${paramFile}"/>"><c:out value="${paramFile}" /></option>
								</c:forEach>
				            </select>
		            	</td>
		            	<td style="border:0px;text-align:right">
	        				<input style="border:0" type="image" src="images/icons/process.png" title="Update metadata for this experiment"
	        					onclick="updateMethodFromFile('<c:out value="${collection.absolutePath}" />', 'paramFileSelect')"/>
	        			</td>
	        		</tr>
        		</table>
        	</div>
        	<br/>
        	
	    	<h1>Edit metadata</h1>
	        <br/>
	       
	       <div id="metadataTabs">
		        <h3><a href="#">General information</a></h3>
				<div>
					<form action="updateMetadata.do" method="post">
					<table style="margin:0; width:100%;">
						<tr style="height:10px"><td class="noborder" colspan="4"></td></tr>
						<tr style="height:0px">
							<td class="noborder"><input type="hidden" name="uri" value="<c:out value="${collection.absolutePath}" />"/></td>
							<td class="noborder"><input type="hidden" name="dispatchto" value="editcollection.do?uri=<c:out value="${collection.absolutePath}" />"/></td>
							<td class="noborder" colspan="2"></td>
						</tr>
		        		<tr>
			            	<td class="noborder"><img class="icon" src="images/icons/info_small.png" title="Theoretical methods used to simulate the molecules/minimize energy of the system."/></td>
		            		<td class="noborder"><strong>Method</strong></td>
			            	<td class="noborder">
				            	<select id="methodList" name="METHOD" style="width:400px">
					            	<option value="UNKNOWN"></option>
					        		<c:forEach items="${methodList}" var="method">
								 		<c:choose>
								 			<c:when test="${simulation.method.name eq method.term}">
								 				<option selected='selected' value="<c:out value="${method.term}"/>"><c:out value="${method.term}"/></option>
								 			</c:when>
								 			<c:otherwise>
								 				<option value="<c:out value="${method.term}"/>"><c:out value="${method.term}" /></option>
								 			</c:otherwise>
								 		</c:choose>
									</c:forEach>
					            </select>
			            	</td>
			            	
			            	<td style="border:0px;text-align:right">
			       				<input style="border:0" type="image" src="images/icons/accept.png" title="save modifications"/>
			       			</td>
			       		</tr>
			       		</table>
			       	</form>
			       	
				    <form id="updateMetadataGeneralForm">
					<table style="margin:0; width:100%;">
						<tr style="height:10px"><td class="noborder" colspan="4"></td></tr>
			        	<tr>
			        		<td class="noborder"><img class="icon" src="images/icons/info_small.png" title="Title of the simulation"/></td>
		        			<td class="noborder"><strong>Title</strong></td>
		        			<td class="noborder"><input  style="width:400px;" name="TITLE" type="text" value="<c:out value="${simulation.title}" />"/></td>
		        			<td style="border:0px;width:90px"></td>
		        		</tr>
		        		<tr style="vertical-align:top;">
		        			<td class="noborder"><img class="icon" src="images/icons/info_small.png" title="Textual description of the simulation (molecule description, protocol, comments, etc.)"/></td>
		        			<td class="noborder"><strong>Description</strong></td>
		        			<td class="noborder"><textarea style="width:400px;resize:none;" name="EXPERIMENT_DESCRIPTION" rows="3"><c:out value="${simulation.description}" /></textarea></td>
		        			<td style="border:0px;text-align:right">
		        				<img src="images/icons/accept.png" title="save modifications" 
		        					onclick="updateCollectionAVUs('<c:out value="${collection.absolutePath}" />','updateMetadataGeneralForm')"></img>	
		        			</td>
		        		</tr>
		        		<tr style="height:5px"><td class="noborder" colspan="4"></td></tr>
		        		</table>
		        	</form>
	        	</div>
	        	
	        	<!-- --------------------- COMPUTATIONAL ENVIRONMENT ----------------- -->
	        	
	        	<h3><a href="#">Software and computational environment</a></h3>
				<div>
					<table>
		       			<tr>
				        	<td class="noborder" colspan="4">
				        		<img class="icon" src="images/icons/info_small.png" title="Software packages used to run the simulation."/>
				        		<strong>Software packages</strong>
				        	</td>
				        </tr>
				        <tr>
			       			<td class="noborder"><div id="swList"></div></td>
					        <td class="noborder"></td>
				        	<td class="noborder">
				        		<input id="newSW" style="width:150px;" name="SOFTWARE_NAME" type="text" value=""/>
						    </td>
						    <td>
								<img class="icon" src="images/icons/add_small.png" title="add new software package"
								  	onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'SOFTWARE_NAME', 'newSW', 'swList')"/>				    	
					        </td>
					    </tr>
					    <tr>
				        	<td class="noborder" colspan="4">
				        		<img class="icon" src="images/icons/info_small.png" title="Versionned software packages used to run the simulation."/>
				        		<strong>Software packages</strong>
				        	</td>
				        </tr>
				        <tr>
			       			<td class="noborder"><div id="swVersionList"></div></td>
					        <td class="noborder"></td>
				        	<td class="noborder">
				        		<input id="newSWVersion" style="width:150px;" name="SOFTWARE_NAME_W_VERSION" type="text" value=""/>
						    </td>
						    <td>
								<img class="icon" src="images/icons/add_small.png" title="add new software package"
								  	onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'SOFTWARE_NAME_W_VERSION', 'newSWVersion', 'swVersionList')"/>				    	
					        </td>
					    </tr>
					    <tr>
				        	<td class="noborder" colspan="4">
				        		<img class="icon" src="images/icons/info_small.png" title="Software executables used to run the simulation."/>
				        		<strong>Software executables</strong>
				        	</td>
				        </tr>
				        <tr>
			       			<td class="noborder"><div id="execList"></div></td>
					        <td class="noborder"></td>
				        	<td class="noborder">
				        		<input id="newExec" style="width:150px;" name="SOFTWARE_EXEC_NAME" type="text" value=""/>
						    </td>
						    <td>
								<img class="icon" src="images/icons/add_small.png" title="add new software executable"
								  	onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'SOFTWARE_EXEC_NAME', 'newExec', 'execList')"/>				    	
					        </td>
					    </tr>
					</table>
				</div>
				
	        	<!-- --------------------- TOPOLOGY / MOLECULAR SYSTEM ----------------- -->
	        	
	        	<h3><a href="#">Molecular system / topology</a></h3>
				<div>
					<div>
			       	<table class="noborder" >
			       		<tr>
		        			<td class="noborder" >
		        				<img class="icon" src="images/icons/info_small.png" title="Name/description of the molecular system"/>
					       			<strong>System name/description</strong><br/>
							</td>
					       	<td class="noborder" style="width:20px"></td>
		        			<td class="noborder"></td>
		        		</tr>
		        		<tr>
		        			<td class="noborder" colspan="3">
								<textarea id="systemDescription" rows="2" style="width:500px;resize:none;overflow:auto;" name="MOLECULAR_SYSTEM_DESCRIPTION"><c:out value="${simulation.molecularSystem.description}" /></textarea>
								<br/>
								<input class="button" type="button" value=" Save " title="save modifications" 
									onclick="updateCollectionAVUs('<c:out value="${collection.absolutePath}" />','systemDescription')" />
							</td>
		        		</tr>
				       	<tr><td class="noborder" colspan="3"><hr/></td></tr>
				       	<tr>
				       		<td class="noborder">
					       		<img class="icon" src="images/icons/info_small.png" title="Type of molecule present in the system"/>
					       			<strong>Molecule types</strong>
					       	</td>
					       	<td class="noborder"></td>
					       	<td class="noborder">
					       		<img class="icon" src="images/icons/info_small.png" title="Atomic composition (e.g. H[2]O[1])"/>
					       			<strong>Atomic composition</strong>
		        			</td>
					    </tr>
					    <tr>
					       	<td class="noborder">
				       			<select id="moleculeTypeSelect" name="MOLECULE_TYPE" style="width:200px">
					          		<c:forEach items="${moleculeTypeList}" var="mol">
							 			<option value="<c:out value="${mol.term}"/>"><c:out value="${mol.term}"/></option>
									</c:forEach>
					            </select>
				    			<img class="icon" src="images/icons/add_small.png" title="add molecule type(s)"
				    				 onclick="addCollectionAvuToList('<c:out value="${collection.absolutePath}" />', 'MOLECULE_TYPE', 'moleculeTypeSelect', 'moleculeTypeList')"/>
				    		</td>
					       	<td class="noborder"></td>
					       	<td class="noborder">
					       		<input id="atomicComposition" type="text" style="width:200px;" name="MOLECULE_ATOMIC_COMPOSITION2" value="<c:out value="${simulation.molecularSystem.molecularComposition}" />"/>
		        				<input class="button" type="button" value=" Save " title="save modifications" 
									onclick="updateCollectionAVUs('<c:out value="${collection.absolutePath}" />','atomicComposition')" />
					       	</td>
				    	</tr>
				    	<tr>
						    <td class="noborder">
					       		<select id="moleculeTypeList" name="MOLECULE_TYPE" multiple="multiple" style="width:250px" size="3">
					          		<c:forEach items="${simulation.molecularSystem.moleculeTypes}" var="mol">
							 			<option value="<c:out value="${mol}"/>"><c:out value="${mol}"/></option>
									</c:forEach>
					            </select>
				    			<img class="icon" src="images/icons/delete_small.png" title="remove molecule type(s)"
				    				 onclick="deleteCollectionAvuFromList('<c:out value="${collection.absolutePath}" />', 'MOLECULE_TYPE', 'moleculeTypeList')"/>	
							 </td>
					       	<td class="noborder"></td>
					        <td class="noborder"></td>
				       	</tr>
				    </table>
				    
				    <table>
				       	<tr><td class="noborder"><hr/></td></tr>
				    	<tr>
				        	<td class="noborder">
				        		<img class="icon" src="images/icons/info_small.png" title="Normalized residue chain"/>
				        		<strong>Residue chain (normalized)</strong>
				        	</td>
				        </tr>
					    <tr><td class="noborder"><div id="residueChainNormList"></div></td></tr>
					    <tr><td class="noborder">
						       	<textarea id="newResidueChainNorm" style="width:500px;resize:none;overflow: auto;" name="RESIDUE_CHAIN_NORM" rows="3"></textarea><br/>
						       	<input class="button" type="button" value=" Add " title="add new residue chain"
						    		onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'RESIDUE_CHAIN_NORM', 'newResidueChainNorm', 'residueChainNormList')"/>
					        </td>
					    </tr>
				       	<tr><td class="noborder"><hr/></td></tr>
					    <tr>
				        	<td class="noborder">
				        		<img class="icon" src="images/icons/info_small.png" title="Software-specific residue chain"/>
				        		<strong>Residue chain (software-specific)</strong>
				        	</td>
				        </tr>
					    <tr><td class="noborder"><div id="residueChainSpecList"></div></td></tr>
				        <tr><td class="noborder">
				        		<textarea id="newResidueChainSpec" style="width:500px;resize:none;overflow: auto;" name="RESIDUE_CHAIN" rows="3"></textarea><br/>
						       	<input class="button" type="button" value=" Add " title="add new residue chain"
						    		onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'RESIDUE_CHAIN', 'newResidueChainSpec', 'residueChainSpecList')"/>
					        </td>
			       		</tr>
				       	<tr><td class="noborder"><hr/></td></tr>
		       		</table>
			       
			        <table>
		       			<tr>
				        	<td class="noborder" colspan="4">
				        		<img class="icon" src="images/icons/info_small.png" title="Total molecule charge"/>
				        		<strong>Total molecule charge</strong>
				        	</td>
				        </tr>
				        <tr>
			       			<td class="noborder"><div id="chargeList"></div></td>
					        <td class="noborder"></td>
				        	<td class="noborder">
				        		<input id="newCharge" style="width:150px;" name="TOTAL_MOLECULE_CHARGE" type="text" value=""/>
						    </td>
						    <td>
								<img class="icon" src="images/icons/add_small.png" title="add total molecule charge"
								  	onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'TOTAL_MOLECULE_CHARGE', 'newCharge', 'chargeList')"/>				    	
					        </td>
					    </tr>
					    <tr>
				        	<td class="noborder" colspan="4">
				        		<img class="icon" src="images/icons/info_small.png" title="Representation of the solvent in the simulation"/>
				        		<strong>Solvent model</strong>
				        	</td>
				        </tr>
				        <tr>
				        	<td class="noborder"><div id="solventList"></div></td>
				        	<td class="noborder"></td>
				        	<td class="noborder">
						       	<input id="newSolvent" style="width:150px;" name="SOLVENT" type="text" value=""/>
						    </td>
						    <td>
						       	<img src="images/icons/add_small.png" title="add new solvent model"
						    		onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'SOLVENT', 'newSolvent', 'solventList')"/>
					        </td>
			       		</tr>
					</table> 
			       	</div>
			    </div>
	        	
	        	<!-- --------------------- FOR MOLECULAR DYNAMICS ----------------- -->
	        	
	        	<c:if test="${(simulation.method.name eq 'Molecular dynamics') || (simulation.method.name eq 'Relica-exchange MD') ||(simulation.method.name eq 'Stochastic dynamics') || (simulation.method.name eq 'QM/MM')}">
	        	
		        	<h3><a href="#">Simulated time</a></h3>
					<div>
			        	<form id="updateMetadataMDForm">
							<table style="margin:0;width:100%">
							<tr style="height:5px"><td class="noborder" colspan="4"></td></tr>
							<tr>
			        			<td class="noborder"><img class="icon" src="images/icons/info_small.png" title="Physical time represented by the simulation."/></td>
			        			<td class="noborder"><strong>Simulated time</strong></td>
			        			<td class="noborder"><input style="width:50px" name="TIME_LENGTH" type="text" value="<c:out value="${simulation.method.simulatedTime}" />"/>&nbsp;nano-seconds</td>
			        			<td style="border:0px;width:180px"></td>
			        		</tr>
			        		<tr>
			        			<td class="noborder"><img class="icon" src="images/icons/info_small.png" title="Total number of time steps representing the simulation time."/></td>
			        			<td class="noborder"><strong>Time step length</strong></td>
			        			<td class="noborder"><input style="width:50px" name="TIME_STEP_LENGTH" type="text"value="<c:out value="${simulation.method.timeStepLength}" />"/>&nbsp;pico-seconds</td>
			        			<td style="border:0px;width:180px;text-align:right">
			        				<input class="button" type="button" value=" Save " title="save modifications" 
			        					onclick="updateCollectionAVUs('<c:out value="${collection.absolutePath}" />','updateMetadataMDForm')" />	
			        			</td>
			        		</tr>
			        		<tr style="height:5px"><td class="noborder" colspan="4"></td></tr>
				        </table>
				        </form>
			        </div>
			        
					
					<h3><a href="#">MD settings</a></h3>						
					<div>
				          <table class="noborder">
				          	<tr>
					        	<td class="noborder" colspan="4">
					        		<img class="icon" src="images/icons/info_small.png" title="Minimization method"/>
					        		<strong>Minimization method</strong>
					        	</td>
					        </tr>
					        <tr>
					        	<td class="noborder"><div id="minimizationList"></div></td>
					        	<td class="noborder"></td>
					        	<td class="noborder">
							       	<input id="newMin" style="width:150px;" name="MD_MINIMIZATION" type="text" value=""/>
							    </td>
							    <td>
							       	<img src="images/icons/add_small.png" title="add new sampling method"
							    		onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'MD_MINIMIZATION', 'newMin', 'minimizationList')"/>
						        </td>
				       		</tr>
				       		<tr>
					        	<td class="noborder" colspan="4">
					        		<img class="icon" src="images/icons/info_small.png" title="Enhanced sampling method"/>
					        		<strong>Enhanced sampling method</strong>
					        	</td>
					        </tr>
					        <tr>
					        	<td class="noborder"><div id="samplingMethodList"></div></td>
					        	<td class="noborder"></td>
					        	<td class="noborder">
							       	<input id="newSamplingMethod" style="width:150px;" name="ENHANCED_SAMPLING_METHOD_NAME" type="text" value=""/>
							    </td>
							    <td>
							       	<img src="images/icons/add_small.png" title="add new sampling method"
							    		onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'ENHANCED_SAMPLING_METHOD_NAME', 'newSamplingMethod', 'samplingMethodList')"/>
						        </td>
				       		</tr>
				          	<tr>
					        	<td class="noborder" colspan="4">
					        		<img class="icon" src="images/icons/info_small.png" title="Electrostatics modeling"/>
					        		<strong>Electrostatics modeling</strong>
					        	</td>
					        </tr>
					        <tr>
					        	<td class="noborder"><div id="electrostaticsList"></div></td>
					        	<td class="noborder"></td>
					        	<td class="noborder">
							       	<input id="newElecModel" style="width:150px;" name="ELECTROSTATICS" type="text" value=""/>
							    </td>
							    <td>
							       	<img src="images/icons/add_small.png" title="add new electrostatics model"
							    		onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'ELECTROSTATICS', 'newElecModel', 'electrostaticsList')"/>
						        </td>
				       		</tr>
				          	<tr>
					        	<td class="noborder" colspan="4">
					        		<img class="icon" src="images/icons/info_small.png" title="Boundary conditions"/>
					        		<strong>Boundary conditions</strong>
					        	</td>
					        </tr>
					        <tr>
					        	<td class="noborder"><div id="boundaryConditionList"></div></td>
					        	<td class="noborder"></td>
					        	<td class="noborder">
							       	<input id="newBC" style="width:150px;" name="BOUNDARY_CONDITIONS" type="text" value=""/>
							    </td>
							    <td>
							       	<img src="images/icons/add_small.png" title="add boundary conditions"
							    		onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'BOUNDARY_CONDITIONS', 'newBC', 'boundaryConditionList')"/>
						        </td>
				       		</tr>
				          	<tr>
					        	<td class="noborder" colspan="4">
					        		<img class="icon" src="images/icons/info_small.png" title="Unit shape"/>
					        		<strong>Unit shape</strong>
					        	</td>
					        </tr>
					        <tr>
					        	<td class="noborder"><div id="unitShapeList"></div></td>
					        	<td class="noborder"></td>
					        	<td class="noborder">
							       	<input id="newUnitshape" style="width:150px;" name="UNIT_SHAPE" type="text" value=""/>
							    </td>
							    <td>
							       	<img src="images/icons/add_small.png" title="add new unit shape"
							    		onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'UNIT_SHAPE', 'newUnitshape', 'unitShapeList')"/>
						        </td>
				       		</tr>
				          	<tr>
					        	<td class="noborder" colspan="4">
					        		<img class="icon" src="images/icons/info_small.png" title="Algorithm used for temperature control"/>
					        		<strong>Thermostat</strong>
					        	</td>
					        </tr>
					        <tr>
					        	<td class="noborder"><div id="thermostatList"></div></td>
					        	<td class="noborder"></td>
					        	<td class="noborder">
							       	<input id="newThermostat" style="width:150px;" name="THERMOSTAT_ALGORITHM" type="text" value=""/>
							    </td>
							    <td>
							       	<img src="images/icons/add_small.png" title="add thermostat"
							    		onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'THERMOSTAT_ALGORITHM', 'newThermostat', 'thermostatList')"/>
						        </td>
				       		</tr>
				          	<tr>
					        	<td class="noborder" colspan="4">
					        		<img class="icon" src="images/icons/info_small.png" title="Algorithm used for pressure control"/>
					        		<strong>Barostat</strong>
					        	</td>
					        </tr>
					        <tr>
					        	<td class="noborder"><div id="barostatList"></div></td>
					        	<td class="noborder"></td>
					        	<td class="noborder">
							       	<input id="newBarostat" style="width:150px;" name="BAROSTAT_ALGORITHM" type="text" value=""/>
							    </td>
							    <td>
							       	<img src="images/icons/add_small.png" title="add barostat"
							    		onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'BAROSTAT_ALGORITHM', 'newBarostat', 'barostatList')"/>
						        </td>
				       		</tr>
				          	<tr>
					        	<td class="noborder" colspan="4">
					        		<img class="icon" src="images/icons/info_small.png" title="Type of ensemble"/>
					        		<strong>Ensemble</strong>
					        	</td>
					        </tr>
					        <tr>
					        	<td class="noborder"><div id="ensembleList"></div></td>
					        	<td class="noborder"></td>
					        	<td class="noborder">
							       	<input id="newEnsemble" style="width:150px;" name="ENSEMBLE" type="text" value=""/>
							    </td>
							    <td>
							       	<img src="images/icons/add_small.png" title="add ensemble type"
							    		onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'ENSEMBLE', 'newEnsemble', 'ensembleList')"/>
						        </td>
				       		</tr>
				          	<tr>
					        	<td class="noborder" colspan="4">
					        		<img class="icon" src="images/icons/info_small.png" title="Force field"/>
					        		<strong>Force field</strong>
					        	</td>
					        </tr>
					        <tr>
					        	<td class="noborder"><div id="ffList"></div></td>
					        	<td class="noborder"></td>
					        	<td class="noborder">
							       	<input id="newFF" style="width:150px;" name="FORCE_FIELD" type="text" value=""/>
							    </td>
							    <td>
							       	<img src="images/icons/add_small.png" title="add force field"
							    		onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'FORCE_FIELD', 'newFF', 'ffList')"/>
						        </td>
				       		</tr>
					   </table>
					</div>
				  
					<h3><a href="#">Constraints</a></h3>
					<div>
				       	<table class="noborder">
				          	<tr>
					        	<td class="noborder" colspan="4">
					        		<img class="icon" src="images/icons/info_small.png" title="Constraint algorithms"/>
					        		<strong>Constraint algorithms</strong>
					        	</td>
					        </tr>
					        <tr>
				       			<td class="noborder"><div id="constraintList"></div></td>
						        <td class="noborder"></td>
					        	<td class="noborder">
					        		<input id="newConstraint" style="width:150px;" name="CONSTRAINT_ALGORITHM" type="text" value=""/>
							    </td>
							    <td>
									<img class="icon" src="images/icons/add_small.png" title="add constraint"
									  		 onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'CONSTRAINT_ALGORITHM', 'newConstraint', 'constraintList')"/>				    	
						        </td>
						    </tr>
						    <tr>
					        	<td class="noborder" colspan="4">
					        		<img class="icon" src="images/icons/info_small.png" title="Constraint targets"/>
					        		<strong>Constraint targets</strong>
					        	</td>
					        </tr>
					        <tr>
				       			<td class="noborder"><div id="constraintTargetList"></div></td>
						        <td class="noborder"></td>
					        	<td class="noborder">
					        		<input id="newConstraintTarget" style="width:150px;" name="CONSTRAINT_TARGET" type="text" value=""/>
							    </td>
							    <td>
									<img class="icon" src="images/icons/add_small.png" title="add constraint"
									  		 onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'CONSTRAINT_TARGET', 'newConstraintTarget', 'constraintTargetList')"/>				    	
						        </td>
						    </tr>
					   </table>
				  </div>
				
				<h3><a href="#">Restraints</a></h3>
					<div>
				       	<table class="noborder">
				          	<tr>
					        	<td class="noborder" colspan="4">
					        		<img class="icon" src="images/icons/info_small.png" title="Restraint types"/>
					        		<strong>Restraint types</strong>
					        	</td>
					        </tr>
					        <tr>
				       			<td class="noborder"><div id="restraintTypeList"></div></td>
						        <td class="noborder"></td>
					        	<td class="noborder">
					        		<input id="newRestraintType" style="width:150px;" name="RESTRAINT" type="text" value=""/>
							    </td>
							    <td>
									<img class="icon" src="images/icons/add_small.png" title="add restraint"
									  		 onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'RESTRAINT', 'newRestraintType', 'restraintTypeList')"/>				    	
						        </td>
						    </tr>
						    <tr>
					        	<td class="noborder" colspan="4">
					        		<img class="icon" src="images/icons/info_small.png" title="Restraint targets"/>
					        		<strong>Restraint targets</strong>
					        	</td>
					        </tr>
					        <tr>
				       			<td class="noborder"><div id="restraintTargetList"></div></td>
						        <td class="noborder"></td>
					        	<td class="noborder">
					        		<input id="newRestraintTarget" style="width:150px;" name="RESTRAINT_TARGET" type="text" value=""/>
							    </td>
							    <td>
									<img class="icon" src="images/icons/add_small.png" title="add restraint"
									  		 onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'RESTRAINT_TARGET', 'newRestraintTarget', 'restraintTargetList')"/>				    	
						        </td>
						    </tr>
					   </table>
				  </div>
				
				<!-- ===================================== FOR QUANTUM MECHANICS ===================================== -->
	        
		        </c:if>
				<c:if test="${(simulation.method.name eq 'Quantum mechanics') || (simulation.method.name eq 'QM/MM') || (simulation.method.name eq 'Quantum MD')}">
									
					<h3><a href="#">QM level of theory</a></h3>
					<div>				
					   <table class="noborder">
				          	<tr>
					        	<td class="noborder" colspan="4">
					        		<img class="icon" src="images/icons/info_small.png" title="Level of theory"/>
					        		<strong>Level of theory</strong>
					        	</td>
					        </tr>
					        <tr>
				       			<td class="noborder"><div id="lotList"></div></td>
						        <td class="noborder"></td>
					        	<td class="noborder">
					        		<input id="newLot" style="width:150px;" name="QM_LEVEL_OF_THEORY" type="text" value=""/>
							    </td>
							    <td>
									<img class="icon" src="images/icons/add_small.png" title="add new level of theory"
									  	onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'QM_LEVEL_OF_THEORY', 'newLot', 'lotList')"/>				    	
						        </td>
						    </tr>
						    <tr>
					        	<td class="noborder" colspan="4">
					        		<img class="icon" src="images/icons/info_small.png" title="Specific method name"/>
					        		<strong>QM method</strong>
					        	</td>
					        </tr>
					        <tr>
				       			<td class="noborder"><div id="qmMethodList"></div></td>
						        <td class="noborder"></td>
					        	<td class="noborder">
					        		<input id="newQmMethod" style="width:150px;" name="QM_METHOD_NAME" type="text" value=""/>
							    </td>
							    <td>
									<img class="icon" src="images/icons/add_small.png" title="add new level of theory"
									  	onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'QM_METHOD_NAME', 'newQmMethod', 'qmMethodList')"/>				    	
						        </td>
						    </tr>
					   </table>
				   </div>
	
					<h3><a href="#">QM calculations and parameters</a></h3>
					<div>
					
						<table class="noborder">
						    <tr>
					        	<td class="noborder" colspan="4">
					        		<img class="icon" src="images/icons/info_small.png" title="Basis sets"/>
					        		<strong>Basis sets</strong>
					        	</td>
					        </tr>
					        <tr>
				       			<td class="noborder"><div id="bsList"></div></td>
						        <td class="noborder"></td>
					        	<td class="noborder">
					        		<input id="newBS" style="width:150px;" name="QM_BASIS_SET" type="text" value=""/>
							    </td>
							    <td>
									<img class="icon" src="images/icons/add_small.png" title="add basis set"
									  		 onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'QM_BASIS_SET', 'newBS', 'bsList')"/>				    	
						        </td>
						    </tr>
				          	<tr>
					        	<td class="noborder" colspan="4">
					        		<img class="icon" src="images/icons/info_small.png" title="Types of QM calculation performed"/>
					        		<strong>Calculation types</strong>
					        	</td>
					        </tr>
					        <tr>
				       			<td class="noborder"><div id="qmCalculationList"></div></td>
						        <td class="noborder"></td>
					        	<td class="noborder">
					        		<input id="newQmCalculation" style="width:150px;" name="CALCULATION" type="text" value=""/>
							    </td>
							    <td>
									<img class="icon" src="images/icons/add_small.png" title="add calculation"
									  		 onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'CALCULATION', 'newQmCalculation', 'qmCalculationList')"/>				    	
						        </td>
						    </tr>
						    <tr>
					        	<td class="noborder" colspan="4">
					        		<img class="icon" src="images/icons/info_small.png" title="Exchange-correlation functional"/>
					        		<strong>Exchange-correlation functional</strong>
					        	</td>
					        </tr>
					        <tr>
				       			<td class="noborder"><div id="functionalList"></div></td>
						        <td class="noborder"></td>
					        	<td class="noborder">
					        		<input id="newFunctional" style="width:150px;" name="QM_EXCHANGE_CORRELATION" type="text" value=""/>
							    </td>
							    <td>
									<img class="icon" src="images/icons/add_small.png" title="add exchange-correlation functional"
									  		 onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'QM_EXCHANGE_CORRELATION', 'newFunctional', 'functionalList')"/>				    	
						        </td>
						    </tr>
						    <tr>
					        	<td class="noborder" colspan="4">
					        		<img class="icon" src="images/icons/info_small.png" title="Spin multiplicity (spin multiplicity is given by 2S+1, where S is the total electron spin for the molecule)."/>
					        		<strong>Spin multiplicity</strong>
					        	</td>
					        </tr>
					        <tr>
				       			<td class="noborder"><div id="spinList"></div></td>
						        <td class="noborder"></td>
					        	<td class="noborder">
					        		<input id="newSpinMult" style="width:150px;" name="QM_SPIN_MULTIPLICITY" type="text" value=""/>
							    </td>
							    <td>
									<img class="icon" src="images/icons/add_small.png" title="add spin multiplicity"
									  		 onclick="addCollectionAvuToTable('<c:out value="${collection.absolutePath}" />', 'QM_SPIN_MULTIPLICITY', 'newSpinMult', 'spinList')"/>				    	
						        </td>
						    </tr>
					   </table>

					</div>
			 </c:if>
			
			<!-- ===================================== REFERENCES (e.g. PUBLICATIONS, STRUCTURES, URLs) ===================================== --> 
			 <h3><a href="#">References (publications, structures, websites)</a></h3>
			 <div>
				<div style="border:1px solid #DADADA;">
				<table>
			        <tr>
			        	<td class="noborder">
			        		<strong>Add new publication</strong><br/><br/>
			        		<img class="icon" src="images/icons/info_small.png" title="Publication reference ID"/>
	        				<select id="newPublicationId0" style="width:180px">
							 	<c:forEach items="${literatureDbList}" var="pubDB">
							 		<option value="<c:out value="${pubDB.term}"/>"><c:out value="${pubDB.term}"/></option>
								</c:forEach>
					    	</select>
					    	<input id="newPublicationId1" type="text" value="" style="width:50px"/>
			    			<img class="icon" src="images/icons/add_small.png" title="add reference"
								onclick="addCollectionAvuAggregate('<c:out value="${collection.absolutePath}" />', 'PUBLICATION_REF_ID', 'newPublicationId', 'publicationList', '#', 2)" />
				        </td>
				        <td class="noborder"></td>
			        	<td class="noborder">
			        		<select id="publicationList" multiple="multiple" style="width:250px" size="4">
				    			<c:forEach items="${simulation.citations}" var="pub" varStatus="r">
				    				<option value="<c:out value="${pub.database}#${pub.referenceId}"/>"><c:out value="${pub.database}"/> [<c:out value="${pub.referenceId}"/>]</option>
				    			</c:forEach>
		    				</select>
		    				<img class="icon" src="images/icons/delete_small.png" title="remove" 
								onclick="deleteCollectionAvuFromList('<c:out value="${collection.absolutePath}" />', 'PUBLICATION_REF_ID', 'publicationList')"/>
			        	</td>
		       		</tr>
				</table>
				</div>
				<br/>
				<div style="border:1px solid #DADADA;">
				<table>
			        <tr>
			        	<td class="noborder">
	       					<strong>Add new structure reference</strong><br/><br/>
			        		<img class="icon" src="images/icons/info_small.png" title="Structure reference ID"/>
					       	<select id="newStructureId0" style="width:180px">
							 	<c:forEach items="${structureDbList}" var="structDB">
							 		<option value="<c:out value="${structDB.term}"/>"><c:out value="${structDB.term}" /></option>
								</c:forEach>
					    	</select>
					    	<input id="newStructureId1" type="text" value="" style="width:50px"/>
						    <img class="icon" src="images/icons/add_small.png" title="add reference"
								onclick="addCollectionAvuAggregate('<c:out value="${collection.absolutePath}" />', 'STRUCTURE_REF_ID', 'newStructureId', 'structureRefList', '#', 2)" />
				    	</td>
				    	<td class="noborder"></td>
			        	<td class="noborder">
			        		<select id="structureRefList" multiple="multiple" style="width:250px" size="4">
				    			<c:forEach items="${simulation.structureReferences}" var="struct" varStatus="r">
				    				<option value="<c:out value="${struct.database}#${struct.referenceId}"/>"><c:out value="${struct.database}"/> [<c:out value="${struct.referenceId}"/>]</option>
				    			</c:forEach>
		    				</select>
		    				<img class="icon" src="images/icons/delete_small.png" title="remove" 
								onclick="deleteCollectionAvuFromList('<c:out value="${collection.absolutePath}" />', 'STRUCTURE_REF_ID', 'structureRefList')"/>
			        	</td>
				    </tr>
				</table>
				</div>
				<br/>
				<div style="border:1px solid #DADADA;">
				<table>
			        <tr>
			        	<td class="noborder">
	       					<strong>Add new link to external resource</strong><br/><br/>
			        		<img class="icon" src="images/icons/info_small.png" title="URL to external resource"/>
					       	URL<br/><input id="newUrl0" type="text" style="width:250px"/><br/>
					    	Description<br/><textarea id="newUrl1" style="width:250px;height:50px;resize:none;"></textarea>
					    	<img class="icon" src="images/icons/add_small.png" title="add reference"
								onclick="addCollectionAvuAggregate('<c:out value="${collection.absolutePath}" />', 'EXTERNAL_LINK', 'newUrl', 'linksList', ':', 2)" />
			    		</td>
			    		<td class="noborder"></td>
			        	<td class="noborder">
			        		<select id="linksList" multiple="multiple" style="width:250px" size="4">
				    			<c:forEach items="${simulation.presentation.links}" var="link" varStatus="r">
				    				<option value="<c:out value="${link.url}:${link.description}"/>"><c:out value="${link.url}"/></option>
				    			</c:forEach>
		    				</select>
		    				<img class="icon" src="images/icons/delete_small.png" title="remove" 
								onclick="deleteCollectionAvuFromList('<c:out value="${collection.absolutePath}" />', 'EXTERNAL_LINK', 'linksList')"/>
			        	</td>
		       		</tr>
				   </table>
				   
				 </div>
			</div>
		
		
		</div>
		
		<br/>
		</div>
		
		<!-- ===================================== EDIT METADATA (SIMULATION PARAMETERS AND STRUCTURE) =================================== -->
		
		
		<!-- ===================================== EDIT TEMPLATE (JMOL FILES AND ANLAYSIS DATA) =================================== -->
		
	    <div id="edittemplate">
	    <br/>
	    	<h1>Main 3D structure</h1>
	    	<p>Select the chemical file that represents the 3D structure you want to be displayed on the main page of this collection.</p>
	    	<table>
       			<tr><td class="noborder">
       				<input id="jmolFileAttribute" type="hidden" value="MAIN_3D_STRUCTURE_FILE"></input>
	              	<select id="jmolFileValue" style="width:400px">
	              		<option value="UNKNOWN"></option>
	              		<c:forEach items="${jmolFileList}" var="jmolFile">
					 		<c:choose>
					 			<c:when test="${simulation.presentation.main3DStructure eq jmolFile}">
					 				<option selected='selected' value="<c:out value="${jmolFile}"/>"><c:out value="${jmolFile}"/></option>
					 			</c:when>
					 			<c:otherwise>
					 				<option value="<c:out value="${jmolFile}"/>"><c:out value="${jmolFile}" /></option>
					 			</c:otherwise>
					 		</c:choose>
						</c:forEach>
		            </select>
            	</td>
            	<td style="border:0px;text-align:right;">
            		<input style="border:0" type="image" src="images/icons/accept.png" title="save modifications"
            			onclick="updateCollectionAVU('<c:out value="${collection.absolutePath}" />', 'jmolFileAttribute', 'jmolFileValue', '')" />
            	</td></tr>
	    	</table>
	    	
        	<br/>
        	<h1>Main analysis data</h1>
    		<p>This is the current list of files that are presented on the public page of this experiment as analysis data.</p>
        	<table style="margin:0;">
		        <tr>
       				<td class="noborder">
				       	<select id="currentAnalysisFileList" multiple="multiple" style="width:300px" size="7">
			            </select>
			    	</td>
			    </tr>	
       			<tr><td>Remove file link(s) from main page&nbsp;
		    			<input style="border:0;vertical-align:middle;" type="image" src="images/icons/delete_small.png" title="remove file link(s)" 
		    				onclick="removeAnalysisFlag('<c:out value="${collection.absolutePath}" />','currentAnalysisFileList')"/>
		    		</td>
	    		</tr>
	    	</table>
	    	
	    	<br/>
        	<h1>Hidden files</h1>
    		<p>This is the current list of hidden files that will not be listed in the public page of the experiment.</p>
       		<table style="margin:0;">
       			<tr style="height:2px"></tr>
	    		<tr>
       				<td class="noborder">
				       	<select id="hiddenFileList" name="uri" multiple="multiple" style="width:300px" size="7">
			            </select>
			    	</td>
			    </tr>	
       			<tr><td>Show file(s)&nbsp;
		    			<input style="border:0;text-align:right;vertical-align:middle;" type="image" src="images/icons/add_small.png" 
		    				title="Make selected file(s) visible" 
		    				onclick="removeHiddenFlag('<c:out value="${collection.absolutePath}" />','hiddenFileList')"/>
		    		</td>
	    		</tr>
	    		<tr style="height:2px"></tr>
	    	</table>
	    	<br/><br/>
	    </div>
		
		<!-- ===================================== EDIT TEMPLATE (PDB FILES AND ANLAYSIS DATA) =================================== -->
		
		<!-- ===================================== ADVANCED EDIT METADATA =================================== -->
	    <div id="editinfoadv"> 
	        <br/>
	        <h1>Add metadata</h1>
	        <br/>
	        <div style="border:1px solid #DADADA;">
			    <table>
					<tr>
						<td>Standard attribute</td>
						<td>Value</td>
						<td>Unit</td>
						<td style="width:20px"></td>
					</tr>
					<tr>
						<td><div id="avu_attribute_std_div"/></td>
						<td><div id="avu_value_std_div"/></td>
						<td><input style="width:80px;" id="avu_unit_std" type="text" value=""/></td>
						<td>
							<input id="avu_std_add" type="image" 
			    				src="images/icons/add_small.png" title="add user-defined AVU" 
			    				style="border-width:0px" onclick="addCollectionAVU('<c:out value="${collection.absolutePath}" />', 'attrlist', 'vallist', 'avu_unit_std')"/>
			    		</td>
					</tr>
					<tr style="height:10px"/>
					<tr>
						<td>User-defined attribute</td>
						<td>Value</td>
						<td>Unit</td>
						<td style="width:20px"></td>
					</tr>
					<tr>
						<td><input style="width:200px;" id="avu_attribute" type="text" value=""/></td>
						<td><input style="width:200px;" id="avu_value" type="text" value=""/></td>
						<td><input style="width:80px;" id="avu_unit" type="text" value=""/></td>
						<td>
			    			<input type="image" 
			    				src="images/icons/add_small.png" title="add user-defined AVU" 
			    				style="border-width:0px" onclick="addCollectionAVU('<c:out value="${collection.absolutePath}" />', 'avu_attribute', 'avu_value', 'avu_unit')"/>
			    		</td>
					</tr>
				</table>
	        </div>
	        <br/>
	        
	        <!-- -------------------- LIST OF CURRENT METADATA --------------------  -->

	        <h1>Current metadata</h1>
	        <br/>
	        	<div id="collection-avu-loading" style="text-align:center;"><img src="animations/loading50.gif"/></div>
				<div id="collection-avu-table"></div>
			<br/>
		
		</div>
		<!-- ===================================== ADVANCED EDIT METADATA =================================== -->
		
		
		
		<!-- ===================================== PERMISSIONS =================================== -->
		
	    <div id="permissioninfo">
	    	<br/>
	    	
	    	<h1>Permissions</h1>
	        <table id="updateInheritForm" style="margin-left:0px">
	        	<tr><td colspan="2"><h3>General rules</h3></td></tr>
	        	<tr><td colspan="2">
	        		<form>
	        		<table class="layout">
						<tr><td class="noborder">Set default to inherit access permissions from parent collection</td></tr>
						<tr>
						<c:if test="${inheritPermissions}">
							<c:set var="inheritCheck" value="checked=\"checked\""/>
						</c:if>
						<c:if test="${not inheritPermissions}">
							<c:set var="noInheritCheck" value="checked=\"checked\""/>
						</c:if>
							<td class="noborder">
								<input name="permissionACL" type="radio" value="INHERIT" <c:out value="${inheritCheck}" />/> Yes&nbsp;&nbsp;&nbsp;
								<input name="permissionACL" type="radio" value="NO_INHERIT" <c:out value="${noInheritCheck}" />/> No&nbsp;&nbsp;&nbsp;
								<input class="button" type="button" value=" Update " onclick="updateInheritFlag('<c:out value="${collection.absolutePath}" />','updateInheritForm')"/>
						</td></tr>
	        		</table>
	        		</form>
		        </td>
		        </tr>
		        <tr style=""><td class="noborder"/></tr>
		        <tr><td><h3>Access control by user/group</h3></td></tr>
		        <tr>
		         <td style="vertical-align:top;">
		        	<form id="updatePermissionsForm">
		        		<table class="layout">
		        			<tr>
		        				<td class="noborder"><strong>User / group</strong></td>
		        				<td class="noborder" style="width:10px"></td>
			        			<td class="noborder">
			        				<select style="width:160px" name="userACL">
			        					<c:forEach items="${userList}" var="user">
			        						<option value="<c:out value="${user.name}"/>"><c:out value="${user.name}"/></option>
			        					</c:forEach>
			        				</select>
			        			</td>
			        		</tr>
			        		<tr>
		        				<td class="noborder"><strong>Permissions</strong></td>
		        				<td class="noborder"></td>
			        			<td class="noborder">
			        				<select style="width:160px" name="permissionACL">
			        					<option value="<c:out value="${permissionREAD}"/>">READ</option>
			        					<option value="<c:out value="${permissionWRITE}"/>">WRITE</option>
			        					<option value="<c:out value="${permissionOWN}"/>">OWN</option>
			        				</select>
			        			</td>
		        			</tr>
		        			<tr>
		        				<td class="noborder"><strong>Recursive</strong></td>
		        				<td class="noborder"></td>
			        			<td class="noborder"><input type="checkbox" name="recursiveACL" value="yes" checked="checked"/></td>
		        			</tr>
		        			<tr style="height:10px"></tr>
		        			<tr><td style="border:0px;text-align:center" colspan="3">
		        				<input class="button" type="button" value=" Add permission " onclick="updateAcl('<c:out value="${collection.absolutePath}" />','updatePermissionsForm')"/>
		        			</td></tr>
		        		</table>
		        	</form>
		        </td>
		        <td>
		        	<form id="removePermissionsForm" class="blank">
		        		<div id="acl-table"></div>
		        	</form>
		        	<p><input class="button" type="button" value=" Remove "
			        			onclick="removePermissions('<c:out value="${collection.absolutePath}" />', 'removePermissionsForm')" />
			        </p>
		        </td>
		       </tr>
	        </table>
	    </div>
	    <!-- ===================================== PERMISSIONS =================================== -->

	   </div>
	
	</div>
	
	<!-- for jquery dialogs -->
	
	<div id="error-message" title="Error"></div>
	<div id="info-message" title="Info"></div>
	<div id="confirm-message" title="Confirm"></div>
				
	<!-- content-wrap ends here -->		
	</div></div>
	
<!-- wrap ends here -->
</div>

<!-- footer -->
<h:footer/>

</body>
</html>
