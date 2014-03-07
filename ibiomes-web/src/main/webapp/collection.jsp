<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<h:meta/>
<link type="text/css" rel="stylesheet" href="style/css/ibiomes.css" />
<link type="text/css" rel="Stylesheet" href="style/smoothness/jquery-ui-1.10.4.custom.css" /> 

<script type="text/javascript">
	var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
</script>
<script type="text/javascript" src="js/common.js"></script>

<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-1.10.2.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript" src="js/jquery.form.js"></script>

<!-- JQuery TreeView -->
<script type="text/javascript" src="js/jstree/_lib/jquery.cookie.js"></script>
<script type="text/javascript" src="js/jstree/_lib/jquery.hotkeys.js"></script>
<script type="text/javascript" src="js/jstree/jquery.jstree.js"></script>
<script type="text/javascript" src="js/treeView.js"></script>

<script type="text/javascript" src="jsmol/JSmol.min.nojq.js"></script>
<script type="text/javascript">
	var Info = {
	  addSelectionOptions: false,
	  color: "#FFFFFF",
	  debug: false,
	  height: 300,
	  width: 300,
	  j2sPath: "jsmol/j2s", // HTML5 only
	  use: "HTML5",  // "HTML5" or "Java"
	  script: "",
      disableJ2SLoadMonitor: true,
	  disableInitialConsole: true,
	  deferApplet: false,
	  deferUncover: true
	};
	Jmol.setDocument(0);
</script>
<script type="text/javascript" src="js/jmol-jquery.js"></script>
<script type="text/javascript" src="js/files.js"></script>
<script type="text/javascript" src="js/collections.js"></script>
<script type="text/javascript" src="js/experimentSet.js"></script>
<script type="text/javascript" src="js/resources.js"></script>
<script type="text/javascript" src="js/cart.js"></script>
<script type="text/javascript" src="js/actions.js"></script>
<script type="text/javascript" src="js/charts.js"></script>
<script type="text/javascript" src="js/dialogs.js"></script>
<script type="text/javascript">

	$(document).ready(function() {
		
		var uri = "<c:out value="${collection.absolutePath}" />";
		var canWrite = <c:out value="${canWrite}" />;
		
		$("#tabs").tabs();
		
		$("#dirWriteOptions").hide();
		
		loadThumbnailsForAnalysis(uri, 'analysisImages');
		loadLinksForAnalysis(uri, 'analysisLinks');
		loadTreeView(uri,canWrite, true);
		
		loadReferencingExperimentSets(uri, "experimentSetList");
		
		$( "#uploadDialog" ).dialog({
			height: 200,
            width: 300,
            autoOpen: false,
			modal: true
		});
		
		$( "#treeActionButton" ).click(function() {
			var action =  $("#treeAction").val();
			
			if (action == "createDir"){
				displayCreateDirDialog($("#treeView").jstree("get_selected"));
			} 
			else if (action == "renameDir"){
				displayRenameDirDialog($("#treeView").jstree("get_selected"));
			} 
			else if (action == 'deleteDir'){
				displayDeleteDirDialog($("#treeView").jstree("get_selected"));
			} 
			else if (action == 'unregisterDir'){
				displayUnregisterDirDialog($("#treeView").jstree("get_selected"));
			} 
			else if (action == 'uploadFile')
			{
				var selectedUri = $("#treeView").jstree("get_selected").data("absolutePath");
				if (selectedUri == null){
					selectedUri = uri;
				}
				$( "#uploadFileUri" ).val(selectedUri);
	        	$( "#uploadForm" ).show();
				$( '#uploadResults').html('');
	            $( "#uploadDialog" ).dialog("open");
			}
		});

		$( "#uploadForm" ).ajaxForm({
	        beforeSubmit: function() {
	        	$( "#uploadForm" ).hide();
	            $('#uploadResults').html('<p>Uploading...</p>');
	            $('#uploadResults').append('<div><img src="animations/loading26.gif"/></div>');
	        },
	        success: function(response) {
	        	$( "#uploadForm" ).hide();
	            var $out = $('#uploadResults');
	            if (response.success){
		            $out.html('<p><strong>File succesfully uploaded</strong></p>');
		            $out.append('<p><i>'+ response.data.name +'</i> ('+response.data.size+' B)</p>');
		            var selNode = $("#treeView").jstree("get_selected");
		            loadCollectionFilesAnyFormat(selNode.data("absolutePath"), "fileList", false, true, canWrite);
		            setTimeout( function (){$( "#uploadDialog" ).dialog("close");}, 2000);
	            }
	            else {
	            	$out.html('<strong>Error</strong>');
			    	$out.append('<p>'+ response.message +'</p>');
	            }
	        },
	        error: function() { 
	        	$( "#uploadForm" ).hide();
	        	$( "#uploadResults" ).html('Error: upload service request failed');
	        }
	    });
		
		$("#plotWindow").dialog({
			height: 700,
	        width: 800,
	        autoOpen: false,
			modal: true,
	        buttons: {
	        	Close: function() {
	        		$("#plotApplet").empty();
	        		$( this ).dialog( "close" );
	            }
	        }
		});
		
		$("#chartType").change(function() {
			showPlotForSelectedFile($("#selectedFile").val());
		});
		
		//$("#jmol").jmol({
	    //    script: 'load "<c:out value="${jmolFileLocalPath}"/>";  wireframe 40;'
		//});
	});
	
	function showPlotForSelectedFile(path){
		$("#plotWindow").dialog('open');
		$("selectedFile").val(path);
	    requestTmpPwdAndLoadChartApplet(
	    		'plotApplet', 
	    		'<c:out value="${sessionScope.SPRING_SECURITY_CONTEXT.zone}"/>',
				'<c:out value="${sessionScope.SPRING_SECURITY_CONTEXT.host}"/>', 
				'<c:out value="${sessionScope.SPRING_SECURITY_CONTEXT.port}"/>', 
				'<c:out value="${sessionScope.SPRING_SECURITY_CONTEXT.userName}"/>',
				'',				
				path, 
				$('#chartType').val(), 
				'', '', '');
	}
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
			<li><a href="cart.do"><h:cart/></a></li>
		</ul>
		<!-- login/logout link -->
		<h:login/>
	
	</div></div>
				
	<!-- content-wrap starts here -->
	<div id="content-wrap"><div id="content">
    <br/>
		
	<div id="main" style="width:100%">
		<div class="post">
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
		        	</c:if></h1>
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
							        		<td><strong>Software packages</strong></td>				        		
							        		<td style="width:20px">&nbsp;</td>
							        		<td><c:if test="${not empty simulation.softwarePackages}">
										        <c:out value="${swList}" />
								        	</c:if></td>
							        		<td style="width:50px">&nbsp;</td>
							        		<td><strong>Owner</strong></td>
							        		<td style="width:20px">&nbsp;</td>
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
		<h:collectionActions collectionUri="${collection.absolutePath}" canWrite="${canWrite}" isExperiment="true" isEdit="false" />
		
	<!-- the tabs -->
	<div id="tabs">
	
		<ul>
			<li><a href="#generalinfo">Summary</a></li>
			<li><a href="#filesinfo">Files</a></li>
			<li><a href="#referencesTab">References</a></li>
		</ul>
	
		<!-- ===================================== COLLECTION METADATA (SIMULATION PARAMETERS AND STRUCTURE) =================================== -->
		<div id="generalinfo">
			<br/>
			<h1>Molecular system</h1>
			<h2>
				<c:set var="moleculeList" value="" scope="request" />
   				<c:forEach items="${simulation.molecularSystem.moleculeTypes}" var="molecule" varStatus="status" >
   					<c:set var="moleculeList" value="${moleculeList}${status.first ? '' : ' / '}${molecule}" scope="request" />
   				</c:forEach>
   				<c:out value="${moleculeList}"/>
			</h2>
  			<c:if test="${ not empty simulation.molecularSystem.molecularComposition}" >
  				<h3>
	         		<c:set var="compo" value="${fn:replace(simulation.molecularSystem.molecularComposition, '[','<sub>' )}" />
					<c:out value="${fn:replace(compo, ']','</sub>' )}" escapeXml="false" />
				</h3>
			</c:if>
			<c:if test="${ fn:length(jmolFileLocalPath) > 0}">
            	<p>
            		<a class="link" href="jmol.do?uri=<c:out value="${jmolFileUri}"/>" target="_blank" title="Display 3D structure in new window...">
            			<img class="icon" src="images/icons/mol.png" />
            			<i><c:out value="${jmolFileDescription}" /></i>
            		</a>
            	</p>
			</c:if>
			<table class="layout">
				<tr>
					<td><strong>Boundary conditions</strong></td>
					<td style="width:20px"/>
					<td><c:if test="${ not empty simulation.method.boundaryConditions}" >
							<c:out value="${simulation.method.boundaryConditions}" />
						</c:if>
					</td>
					<td style="width:120px"/>
					<td><strong>Solvent molecules</strong></td>
					<td style="width:20px"/>
					<td>
						<c:out value="${simulation.molecularSystem.solventCount}" />
						<c:if test="${ not empty simulation.molecularSystem.solventTypes}" >
							<c:forEach items="${simulation.molecularSystem.solventTypes}" var="solvt" varStatus="status">
				        			<c:set var="solvtList" value="${solvtList}${status.first ? '' : ', '}${solvt}" scope="request" />
			        		</c:forEach>
			        		( <c:out value="${solvtList}" /> )
						</c:if>
					</td>
				</tr>
				<tr>
					<td><strong>Number of atoms</strong></td>
					<td style="width:20px"></td>
					<td><c:out value="${simulation.molecularSystem.atomCount}" /></td>
					<td style="width:120px"/>
					<td><strong>Ions</strong></td>
					<td/>
					<td>
						<c:out value="${simulation.molecularSystem.ionCount}" />
						<c:if test="${ not empty simulation.molecularSystem.ionTypes}" >
							<c:forEach items="${simulation.molecularSystem.ionTypes}" var="ion" varStatus="status">
				        			<c:set var="ionList" value="${ionList}${status.first ? '' : ', '}${ion}" scope="request" />
			        		</c:forEach>
			        		( <c:out value="${ionList}" /> )
						</c:if>
					</td>
				</tr>
				<c:if test="${(simulation.molecularSystem.molecularWeight > 0.0)}" >
	         		<tr>
	         			<td><strong>Molecular weight</strong></td>
	         			<td/>
	         			<td><c:out value="${simulation.molecularSystem.molecularWeight}" /> g/mol</td>
	         			<td/><td/><td/><td/>
	         		</tr>
	         	</c:if>
			</table>
			
			<c:if test="${ not empty simulation.molecularSystem.residueChains}" >
				<p><strong>Software-specific residue chains: </strong></p>
				<ul>
				<c:forEach items="${simulation.molecularSystem.residueChains}" var="resChain">
					<c:choose>
						<c:when test="${fn:length(resChain) gt 80 }">
							<li><c:out value="${fn:substring(resChain,0,80)}" />
								<a class="link" href="#" title="<c:out value="${resChain}" />">[...]</a>
							</li>
						</c:when>
						<c:otherwise><li><c:out value="${resChain}" /></li></c:otherwise>
					</c:choose>
				</c:forEach>
				</ul>
			</c:if>
			<c:if test="${ not empty simulation.molecularSystem.normalizedChains}" >
				<p><strong>Normalized chains:</strong></p>
				<ul>
				<c:forEach items="${simulation.molecularSystem.normalizedChains}" var="resChain">
					<c:choose>
						<c:when test="${fn:length(resChain) gt 75 }">
							<li><c:out value="${fn:substring(resChain,0,75)}" />
								<a class="link" href="#" title="<c:out value="${resChain}" />">[...]</a>
							</li>
						</c:when>
						<c:otherwise><li><c:out value="${resChain}" /></li></c:otherwise>
					</c:choose>
				</c:forEach>
				</ul>
			</c:if>
			<c:if test="${ not empty simulation.molecularSystem.nonStandardResidues}" >
				<p><strong>Non-standard residues:</strong></p>
				<ul>
				<c:forEach items="${simulation.molecularSystem.nonStandardResidues}" var="resChain">
					<li><c:out value="${resChain}" /></li>
				</c:forEach>
				</ul>
			</c:if>
			
			<!-- ========================== METHODS / PARAMETERS ======================= -->
					
			<h1>Methods and parameters</h1>
			
	        <!-- ============================== REMD INFO ============================== -->
	       
       		<c:if test="${(simulation.method.name eq 'Replica-exchange MD')}">
        		<h2>Replica-exchange MD</h2>
				<table class="layout">
				<c:if test="${ not empty simulation.method.samplingMethods}" >
		            <tr>
		        		<td><strong>Sampling method</strong></td>
		        		<td style="width:20px"></td>
						<td><c:forEach items="${simulation.method.samplingMethods}" var="meth" varStatus="status">
			        			<c:set var="methList" value="${methList}${status.first ? '' : ', '}${meth}" scope="request" />
			        		</c:forEach>
			        		<c:out value="${methList}" />
	        			</td>
	        		</tr>
        		</c:if>
        		<c:if test="${ not empty simulation.method.numberOfReplica}" >
		            <tr>
		        		<td><strong>Number of replicas</strong></td>
		        		<td style="width:20px"></td>
						<td><c:out value="${simulation.method.numberOfReplica}" /></td>
	        		</tr>
        		</c:if>
				</table>
			</c:if>
			
			<!-- ============================== QM/MM INFO ============================== -->
	       
       		<c:if test="${(simulation.method.name eq 'QM/MM')}">
        		<h2>QM/MM</h2>
				<table class="layout">
				<c:if test="${ not empty simulation.method.qmmmBoundaryTreatment}" >
		            <tr>
		        		<td><strong>Boundary treatment</strong></td>
		        		<td style="width:20px"></td>
						<td><c:out value="${simulation.method.qmmmBoundaryTreatment}" /></td>
	        		</tr>
        		</c:if>
				</table>
			</c:if>
			
			<!-- ============================== QUANTUM MD INFO ============================== -->
	       
       		<c:if test="${(simulation.method.name eq 'Quantum MD')}">
        		<h2>Quantum MD</h2>
				<table class="layout">
				<c:if test="${ not empty simulation.method.quantumMDMethods}" >
		            <tr>
		        		<td><strong>Method</strong></td>
		        		<td style="width:20px"></td>
						<td><c:forEach items="${simulation.method.quantumMDMethods}" var="qmdMethod" varStatus="status">
			        			<c:set var="qmdMethodList" value="${qmdMethodList}${status.first ? '' : ', '}${qmdMethod}" scope="request" />
			        		</c:forEach>
			        		<c:out value="${qmdMethodList}" />
	        			</td>
	        		</tr>
        		</c:if>
				</table>
			</c:if>
			
	       <!-- ============================== MOLECULAR DYNAMICS INFO ============================== -->
	       
       		<c:if test="${(simulation.method.name eq 'Molecular dynamics') || (simulation.method.name eq 'Stochastic dynamics') || (simulation.method.name eq 'QM/MM') || (simulation.method.name eq 'Quantum MD') || (simulation.method.name eq 'Replica-exchange MD')}">
        		<h2>Molecular Dynamics</h2>
				<table class="layout">
				<c:if test="${ not empty simulation.method.minimizations}" >
		            <tr>
		        		<td><strong>Minimizations</strong></td>
		        		<td style="width:20px"></td>
						<td><c:forEach items="${simulation.method.minimizations}" var="min" varStatus="status">
			        			<c:set var="minList" value="${minList}${status.first ? '' : ', '}${min}" scope="request" />
			        		</c:forEach>
			        		<c:out value="${minList}" />
	        			</td>
	        		</tr>
        		</c:if>
				<c:if test="${ not empty simulation.method.forceFields}" >
		            <tr>
		        		<td><strong>Force-fields</strong></td>
		        		<td style="width:20px"></td>
						<td><c:forEach items="${simulation.method.forceFields}" var="ff" varStatus="status">
			        			<c:set var="ffList" value="${ffList}${status.first ? '' : ', '}${ff}" scope="request" />
			        		</c:forEach>
			        		<c:out value="${ffList}" />
	        			</td>
	        		</tr>
        		</c:if>
        		<c:if test="${ not empty simulation.method.integrators}" >
					<tr><td><strong>Integrator</strong></td>
						<td style="width:20px"/>
						<td>
							<c:forEach items="${simulation.method.integrators}" var="integrator" varStatus="status" >
				        		<c:set var="integratorList" value="${integratorList}${status.first ? '' : ' / '}${integrator}" scope="request" />
		    				</c:forEach>
		    				<c:out value="${integratorList}"/></td>
					</tr>
				</c:if>
        		<c:if test="${ not empty simulation.method.samplingMethods}" >
					<tr><td><strong>Sampling methods</strong></td>
						<td style="width:20px"/>
						<td>
							<c:forEach items="${simulation.method.samplingMethods}" var="sampling" varStatus="status" >
				        		<c:set var="samplingList" value="${samplingList}${status.first ? '' : ', '}${sampling}" scope="request" />
		    				</c:forEach>
		    				<c:out value="${samplingList}"/></td>
					</tr>
				</c:if>
        		<c:if test="${ not empty simulation.method.solventTypes}" >
					<tr>
						<td><strong>Solvent model</strong></td>
						<td style="width:20px"/>
						<td>
							<c:forEach items="${simulation.method.solventTypes}" var="solvent" varStatus="status" >
				        		<c:set var="solventTypes" value="${solventTypes}${status.first ? '' : ', '}${solvent}" scope="request" />
		    				</c:forEach>
		    				<c:out value="${solventTypes}" />
			        		<c:if test="${ not empty simulation.method.implicitSolventModels}" >
				        		<c:forEach items="${simulation.method.implicitSolventModels}" var="implicitSolvent" varStatus="status" >
					        		<c:set var="implicitSolventModels" value="${implicitSolventModels}${status.first ? '' : ', '}${implicitSolvent}" scope="request" />
			    				</c:forEach>
								( <c:out value="${implicitSolventModels}" /> )
							</c:if>
						</td>
					</tr>
				</c:if>
				<c:if test="${ not empty simulation.method.constraints}" >
					<tr><td>
						<strong>Constraints</strong></td><td style="width:20px"/><td>
						<c:set var="constraintList" value="" scope="request" />
	    				<c:forEach items="${simulation.method.constraints}" var="constraint" varStatus="status" >
	    					<c:set var="constraintList" value="${constraintList}${status.first ? '' : ' / '}${constraint}" scope="request" />
	    				</c:forEach>
	    				<c:out value="${constraintList}"/></td></tr>
				</c:if>
				<c:if test="${ not empty simulation.method.restraints}" >
					<tr><td>
						<strong>Restraints</strong></td><td style="width:20px"/><td>
						<c:set var="restraintList" value="" scope="request" />
	    				<c:forEach items="${simulation.method.restraints}" var="restraint" varStatus="status" >
	    					<c:set var="restraintList" value="${restraintList}${status.first ? '' : ', '}${restraint}" scope="request" />
	    				</c:forEach>
	    				<c:out value="${restraintList}"/></td></tr>
				</c:if>
				<c:if test="${ not empty simulation.method.electrostatics}" >
					<tr>
						<td><strong>Electrostatics interactions</strong></td>
						<td style="width:20px"/>
						<td>
	    				<c:forEach items="${simulation.method.electrostatics}" var="elec" varStatus="status" >
	    					<c:set var="elecList" value="${elecList}${status.first ? '' : ' / '}${elec}" scope="request" />
	    				</c:forEach>
	    				<c:out value="${elecList}"/></td>
					</tr>
				</c:if>
				<c:if test="${ not empty simulation.method.unitShapes}" >
					<tr><td><strong>Unit shape</strong></td><td style="width:20px"/>
						<td>
							<c:forEach items="${simulation.method.unitShapes}" var="unitshape" varStatus="status" >
				        		<c:set var="unitshapeList" value="${unitshapeList}${status.first ? '' : ' / '}${unitshape}" scope="request" />
		    				</c:forEach>
		    				<c:out value="${unitshapeList}"/></td>
					</tr>
				</c:if>
				<c:if test="${ not empty simulation.method.thermostats}" >
					<tr><td><strong>Thermostat</strong></td>
						<td style="width:20px"/>
						<td>
							<c:forEach items="${simulation.method.thermostats}" var="thermostat" varStatus="status" >
				        		<c:set var="thermostats" value="${thermostats}${status.first ? '' : ' / '}${thermostat}" scope="request" />
		    				</c:forEach>
		    				<c:out value="${thermostats}"/></td>
					</tr>
				</c:if>
				<c:if test="${ not empty simulation.method.barostats}" >
					<tr><td><strong>Barostat</strong></td>
						<td style="width:20px"/>
						<td>
							<c:forEach items="${simulation.method.barostats}" var="barostat" varStatus="status" >
				        		<c:set var="barostats" value="${barostats}${status.first ? '' : ' / '}${barostat}" scope="request" />
		    				</c:forEach>
		    				<c:out value="${barostats}"/></td></tr>
				</c:if>
				<c:if test="${ (not empty simulation.method.simulatedTime) && (simulation.method.simulatedTime > 0.0)}" >
					<tr>
						<td><strong>Physical time</strong></td>
						<td style="width:20px"/>
						<td><c:out value="${simulation.method.simulatedTime}" /> ns</td></tr>
				</c:if>
				<c:if test="${ (not empty simulation.method.timeStepLength) && (simulation.method.timeStepLength > 0.0)}" >
					<tr><td><strong>Time step</strong></td><td style="width:20px"/><td><c:out value="${simulation.method.timeStepLength}" /> ps</td></tr>
				</c:if>
				</table>
			</c:if>
	        	
	       <!-- ============================== QUANTUM MECHANICS INFO ============================== -->
	       
	       	<c:if test="${(simulation.method.name eq 'Quantum mechanics') || (simulation.method.name eq 'QM/MM') || (simulation.method.name eq 'Quantum MD')}">
	       		<h2>Quantum calculations</h2>
				<table class="layout">
				<c:if test="${ not empty simulation.method.levelsOfTheory}" >
		            <tr>
		        		<td><strong>Level(s) of theory</strong></td>
		        		<td style="width:20px"></td>
		        		<td>
				        	<c:forEach items="${simulation.method.levelsOfTheory}" var="lot" varStatus="status" >
				        		<c:set var="levelList" value="${levelList}${status.first ? '' : ' / '}${lot}" scope="request" />
		    				</c:forEach>
		    				<c:out value="${levelList}"/>
				        </td>
		        	</tr>
		        </c:if>
	        	<c:if test="${ not empty simulation.method.methodNames}" >
		            <tr>
		        		<td><strong>Specific method name</strong></td>
		        		<td style="width:20px"></td>
		        		<td>
				        	<c:forEach items="${simulation.method.methodNames}" var="qmMethod" varStatus="status" >
				        		<c:set var="qmMethodlList" value="${qmMethodlList}${status.first ? '' : ' / '}${qmMethod}" scope="request" />
		    				</c:forEach>
		    				<c:out value="${qmMethodlList}"/>
				        </td>
		        	</tr>
		        </c:if>
	        	<c:if test="${ not empty simulation.method.exchangeCorrelationFns}" >
					<tr><td><strong>Exchange-correlation functional</strong></td>
						<td/>
						<td>
				        	<c:forEach items="${simulation.method.exchangeCorrelationFns}" var="funct" varStatus="status" >
				        		<c:set var="functionalList" value="${functionalList}${status.first ? '' : ', '}${funct}" scope="request" />
		    				</c:forEach>
		    				<c:out value="${functionalList}"/>
						</td></tr>
				</c:if>
				<c:if test="${ not empty simulation.method.basisSets}" >
					<tr>
		        		<td><strong>Basis set(s)</strong>
							<ul><c:forEach items="${simulation.method.basisSets}" var="bs">
								<li><c:out value="${bs}" /></li>
				        	</c:forEach>
				        	</ul>
				        </td>
					</tr>
		        </c:if>
				<c:if test="${ not empty simulation.method.calculationTypes}" >
					<tr>
		        		<td><strong>Calculations</strong>
							<ul><c:forEach items="${simulation.method.calculationTypes}" var="calculation">
								<li><c:out value="${calculation}" /></li>
				        	</c:forEach>
				        	</ul>
				        </td>
					</tr>
		        </c:if>
		        </table>
			</c:if>
			<!-- ========================== SOFTWARE AND HARDWARE INFO ======================= -->
			<c:if test="${ not empty simulation.executionInfo.programs}" >		
				<h1>Execution information</h1>
				<table class="layout">
	            <tr>
	        		<td><strong>Programs</strong></td>
	        		<td style="width:20px"></td>
	        		<td colspan="5">
			        	<c:forEach items="${simulation.executionInfo.programs}" var="app" varStatus="status" >
			        		<c:set var="appList" value="${appList}${status.first ? '' : ', '}${app}" scope="request" />
	    				</c:forEach>
	   					<c:out value="${appList}"/>
			        </td>
	        	</tr>
				<c:if test="${ not empty simulation.executionInfo.operatingSystems}" >
		            <tr>
		        		<td><strong>Operating system</strong></td>
		        		<td style="width:20px"></td>
		        		<td colspan="5">
				        	<c:forEach items="${simulation.executionInfo.operatingSystems}" var="os" varStatus="status" >
				        		<c:set var="osList" value="${osList}${status.first ? '' : ', '}${os}" scope="request" />
		    				</c:forEach>
		    				<c:out value="${osList}"/>
				        </td>
		        	</tr>
		        </c:if>
		        
	            <tr>
	        		<td><strong>CPUs</strong></td>
	        		<td style="width:20px"></td>
	        		<td>
	        			<c:if test="${ not empty simulation.executionInfo.numberOfCPUs}" >
	        				<c:out value="${simulation.executionInfo.numberOfCPUs}"/>
	        			</c:if>
	        			<c:if test="${ not empty simulation.executionInfo.cpuArchitectures}" >
			            	<c:forEach items="${simulation.executionInfo.cpuArchitectures}" var="cpuArch" varStatus="status" >
				        		<c:set var="cpuArchList" value="${cpuArchList}${status.first ? '' : ', '}${cpuArch}" scope="request" />
		    				</c:forEach>
		    				( <c:out value="${cpuArchList}"/> )
		    			</c:if>
			        </td>
					<td style="width:120px"> </td>
			        <td><strong>First job started</strong></td>
					<td style="width:20px"></td>
					<td>
						<c:if test="${ not empty simulation.executionInfo.dateStartTask}" >
							<c:out value="${simulation.executionInfo.dateStartTask}"/>
						</c:if>
					</td>
	        	</tr>
		        
	            <tr>
	        		<td><strong>GPUs</strong></td>
	        		<td style="width:20px"></td>
	        		<td>
	        			<c:if test="${ not empty simulation.executionInfo.numberOfGPUs}" >
	        				<c:out value="${simulation.executionInfo.numberOfGPUs}"/>
	        			</c:if>
	        			<c:if test="${ not empty simulation.executionInfo.gpuArchitectures}" >
				        	<c:forEach items="${simulation.executionInfo.gpuArchitectures}" var="gpuArch" varStatus="status" >
				        		<c:set var="gpuArchList" value="${gpuArchList}${status.first ? '' : ', '}${gpuArch}" scope="request" />
		    				</c:forEach>
		    				( <c:out value="${gpuArchList}"/> )
		    			</c:if>
			        </td>
			        <td style="width:120px"> </td>
					<td><strong>Last job ended</strong></td>
					<td style="width:20px"></td>
					<td>
						<c:if test="${ not empty simulation.executionInfo.dateEndTask}" >
							<c:out value="${simulation.executionInfo.dateEndTask}"/>
						</c:if>
					</td>
	        	</tr>			
				</table>
			</c:if>
			
			<!-- ================================ ANALYSIS DATA ======================================= -->
	        <h1>Analysis</h1>
       		<h3>Images</h3>
      		<div id="analysisImages"></div>
      		<h3>Links</h3>
      		<div id="analysisLinks"></div>
      		
		</div>
		
		<!-- ===================================== FILE COLLECTION =================================== -->
		<div id="filesinfo">
		<br/>
			<div class="treeview" style="width:300px; height:500px;">
				<div id="treeView" style="background-color:white;"></div>
			</div>
			<div id="fileListContainer" style="margin-left:20px;width:625px;float:left">
				<input id="selectedFile" type="hidden"/>
				<hr/><p><strong>Current directory</strong></p>
				<div id="fileListHeader"></div>
				<div id="dirWriteOptions">
					<p>
						<select id="treeAction" style="width:250px">
							<option value="createDir">Create new subfolder</option>
							<option value="renameDir">Rename directory</option>
							<option value="deleteDir">Delete directory</option>
							<option value="unregisterDir">Unregister directory</option>
							<option value="uploadFile">Upload file</option>
						</select>
						<input id="treeActionButton" type="button" class="button" value=" Go " />
					</p>
					<!--  upload file -->
			    	<div id="uploadDialog" title="File upload">
						<form id="uploadForm" action="rest/services/upload/file" method="post" enctype="multipart/form-data" class="blank">
							<p><br/><input id="uploadFileUri" type="hidden" name="uri" value="<c:out value="${ collection.absolutePath }"/>"/>
							   <input name="file" type="file"/></p>
							<!-- <p>File format <select id="uploadFileFormat" name="format"></select></p> -->
							<p>Overwrite existing files&nbsp;&nbsp;<input name="overwrite" type="checkbox" value="true"/></p>
							<p><br/><input class="button" name="save" type="submit" value=" Upload " /></p>
						</form>
						<div id="uploadResults"></div>
					</div>
				</div>
				<br/>
				<hr/>
				<p><strong>File listing</strong></p>
				<br/>
				<div id="fileList"></div>
				<div id="fileListLoading" style="text-align:center;display:none;"><img src="animations/loading50.gif"/></div>
			</div>
			<br style="clear: left;"/>
			
		</div>
		
		<!-- ====================== VISUALIZATION TOOLS =========================== -->
			<div id="plotWindow" title="Plotting tool">
				<span><strong>Chart type</strong></span>
				<select id="chartType" name="chartType">
					<option value="" selected="selected">Unknown</option>
					<option value="line">Line plot</option>
					<option value="heatmap">Heatmap</option>
				</select>
				<br/><br/>
				<div style="text-align:center;width:700px;height:550px;" id="plotApplet"></div>
				<div id="chartAppletLoading" style="text-align:center;display:none;"><img src="animations/loading50.gif"/></div>
			</div>
		
		<!-- ================================ PUBLICATIONS / CITATIONS / REFERENCE STRUCTURES ================================= -->
		<div id="referencesTab">
			<!-- EXPERIMENT SETS -->
			<div id="experimentSets">
				<h1>Referencing experiment sets</h1>
				<div id="experimentSetList"></div>
			</div>
			<h1>Citations</h1>
			<c:choose>
				<c:when test="${ not empty simulation.citations }"> 
					<ul>
		         	<c:forEach items="${simulation.citations}" var="citation">
		        		<c:choose>
		        			<c:when test="${ citation.database == 'pubmed' || citation.database == 'PubMed'}" >
			        			<li><strong>PubMed citation: </strong><a class="link" href="http://www.ncbi.nlm.nih.gov/pubmed?term=<c:out value="${citation.referenceId}"/>" target="_blank"><c:out value="${citation.referenceId}" /></a></li>
		        			</c:when>
		        			<c:otherwise>
		        				<li><strong><c:out value="${citation.database}" /> citation: </strong> <c:out value="${citation.referenceId}" /></li>
		        			</c:otherwise>
		        		</c:choose>
			        </c:forEach>
			        </ul>
				</c:when>
				<c:otherwise><p>No citation available for this experiment.</p></c:otherwise>
			</c:choose>
			<!-- REFERENCE STRUCTURES (e.g. PDB)  -->
			<h1>Reference structure</h1>
			<c:choose>
				<c:when test="${ not empty simulation.structureReferences }">  
		         	<ul>
		         	<c:forEach items="${simulation.structureReferences}" var="reference">
		        		<c:choose>
		        			<c:when test="${ reference.database == 'pdb' || reference.database == 'PDB'}">
			        			<li><strong>Protein Data Bank entry: </strong><a class="link" href="http://www.rcsb.org/pdb/explore/explore.do?pdbId=<c:out value="${reference.referenceId}" />" target="_blank"><c:out value="${reference.referenceId}" /></a></li>
		        			</c:when>
		        			<c:when test="${ reference.database == 'mmdb' || reference.database == 'MMDB'}">
			        			<li><strong>MMDB entry: </strong><a class="link" href="http://www.ncbi.nlm.nih.gov/Structure/mmdb/mmdbsrv.cgi?uid=<c:out value="${reference.referenceId}" />" target="_blank"><c:out value="${reference.referenceId}" /></a></li>
		        			</c:when>
		        			<c:when test="${ reference.database == 'pubchem' || reference.database == 'PubChem'}">
			        			<li><strong>PubChem compound: </strong><a class="link" href="http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?cid=<c:out value="${reference.referenceId}" />" target="_blank"><c:out value="${reference.referenceId}" /></a></li>
		        			</c:when>
		        			<c:otherwise>
		        				<li><strong><c:out value="${reference.database}" /> reference: </strong> <c:out value="${reference.referenceId}" /></li>
		        			</c:otherwise>
		        		</c:choose>
			        </c:forEach>
					</ul>
				</c:when>
				<c:otherwise><p>No reference structure available for this experiment.</p></c:otherwise>
			</c:choose>
			<!-- EXTERNAL LINKS -->
			<h1>External links</h1>
			<c:choose>
				<c:when test="${ not empty simulation.presentation.links }"> 
					<ul>
		         	<c:forEach items="${simulation.presentation.links}" var="link">
		        		<li>
		        			<a href="<c:out value="${link.url}"/>" title="<c:out value="${link.description}"/>" target="_blank">
		        				<c:out value="${link.description}"/>
		        			</a></li>
			        </c:forEach>
	        		</ul>
				</c:when>
				<c:otherwise><p>No external resource available for this experiment.</p></c:otherwise>
			</c:choose>
			<br/>
		</div>
	</div>
	
		<div id="error-message" title="Error"></div>
		<div id="info-message" title="Info"></div>
		<div id="confirm-message" title="Confirm"></div>
		
	</div>
				
	<!-- content-wrap ends here -->		
	</div></div>

<br/>
<!-- wrap ends here -->
</div>

<!-- footer -->
<h:footer/>

</body>
</html>
