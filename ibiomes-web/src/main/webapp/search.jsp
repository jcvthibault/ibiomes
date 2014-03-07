<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/D/tdxhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ page buffer="12kb" %>

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
<script type="text/javascript" src="js/metadataLoadForSearch.js"></script>
<script type="text/javascript" src="js/metadata.js"></script>
<script type="text/javascript" src="js/search.js"></script>
<script type="text/javascript" src="js/cart.js"></script>
<script type="text/javascript" src="js/dialogs.js"></script>
<script type="text/javascript" src="js/resources.js"></script>

<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-1.10.2.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript">
$(document).ready(function() {

	var resturl = webServiceUrl + "/rest/metadata/";
	//loadAutocompleteAVU("software", resturl + "SOFTWARE_NAME/values");
	loadAutocompleteAVU("basisset", resturl + "QM_BASIS_SET/values");
	loadAutocompleteAVU("leveloftheory", resturl + "QM_LEVEL_OF_THEORY/values");
	loadAutocompleteAVU("qmMethod", resturl + "QM_METHOD_NAME/values");
	loadAutocompleteAVU("forcefield", resturl + "FORCE_FIELD/values");
	
	$("#tabs").tabs();
	
	loadPreviousSearchResults();

	for (var i=0;i<3;i++){
		addSearchCriteria("searchCriteria", "newCriteriaId");
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
			<li><a href="search.do" id="current">Search</a></li>
			<li><a href="documentation.jsp">Documentation</a></li>
			<li><a href="about.jsp">About</a></li>
			<li><a href="cart.do"><h:cart/></a></li>
		</ul>
		<!-- login/logout link -->
		<h:login/>
	
	</div></div>
	<!-- content-wrap starts here -->
	<div id="content-wrap"><div id="content">
		
		<div id="main" style="width:100%">

		<br/>
		<div class="post">
			<h1>Search simulations</h1>
			<p>The <i>experiment search</i> allows you to search simulations using basic criteria (e.g. method, residue chain). 
			The <i>'MD search'</i> and <i>'QM search'</i> provide more criteria specific to Molecular Dynamics and Quantum Mechanics.
			To manually build your own query go to the <i>Advanced search</i> tab. If you are looking for a particular file (e.g. PDB) 
			go to the <i>file search</i> option.</p>
			<p>Note that you can use '%' for wildcards (e.g. %ATTTCGGAA%).</p>
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
		<br/>
		
		<!-- the tabs -->
		<div id="tabs">
		
			<ul>
				<li><a href="#collections">Experiments</a></li>
				<li><a href="#md_collections">MD</a></li>
				<li><a href="#qm_collections">QM</a></li>
				<li><a href="#sets">Sets</a></li>
				<li><a href="#files">Files</a></li>
				<li><a href="#custom">Advanced</a></li>
				<li><a href="#results">Results</a></li>
			</ul>
			
			<!-- ===================================== BASIC SEARCH (ALL EXPERIMENTS) =================================== -->
			<div id="collections">
				<form id="basicSearchForm" class="blank">
		        	<table class="center">
		        		<tr><td>
		        				<img class="icon" src="images/icons/folder_full_large.png" title="Search all experiments"/>
		        				<span style="font-size: 1.2em;"><strong>Search all experiments</strong></span>
		        			</td>
		        		</tr>
		        		<tr>
			          		<td style="width:100%;"><br/>
								<hr/>
				          		<b>General</b><br/>
					          	<table>
					          		<tr>
										<td style="width:200px;text-align:right">Software</td>
										<td><img src="images/icons/info_small.png" title="Name of the software package used to perform the simulations/calculations" /></td>
										<td>
								            <select name="software" style="width:250px">
								              	<c:forEach items="${softwareList}" var="sw">
													<option value="<c:out value="${sw.term}" />"><c:out value="${sw.term}" /></option>
												</c:forEach>
												<option selected="selected" value=""></option>
								            </select>
							        	</td>
							        </tr>
							        <tr>
							        	<td style="width:200px;text-align:right">Experiment name</td>
										<td><img src="images/icons/info_small.png" title="Name of the experiment (root directory). Use '%' for wildcards." /></td>
							        	<td><input name="name" value="" type="text" style="width:250px" /></td>
							        </tr>
							        <tr>
							        	<td style="width:200px;text-align:right">Description</td>
										<td><img src="images/icons/info_small.png" title="Description of the experiment. Use '%' for wildcards." /></td>
							        	<td><input name="description" value="" type="text" style="width:250px" /></td>
							        </tr>
							        <tr>
							        	<td style="width:200px;text-align:right">Owner</td>
										<td><img src="images/icons/info_small.png" title="Owner's username" /></td>
							        	<td><input name="owner" value="" type="text" style="width:250px"/></td>
							        </tr>
		        					<tr><td style="width:200px;text-align:right">Registration date</td>
										<td><img src="images/icons/info_small.png" title="Date the experiment was registered into iBIOMES." /></td>
			        					<td style="border:0px;">
								            <select name="registration" style="width:250px">
								              <option value="none">anytime</option>
								              <option value="1week">last week</option>
								              <option value="1month">last month</option>
								              <option value="6months">last 6 months</option>
								              <option value="1year">last year</option>
								              <option value="over1year">over a year ago</option>
								            </select>
								        </td>
							   		</tr>
						        </table>
				          	</td>
				        </tr>
		          		<tr>
			          		<td style="width:100%;">
								<hr/>
				          		<b>Molecular system</b><br/>
					          	<table style="width:100%;">
					          		<tr>
										<td style="width:200px;text-align:right">Name/description</td>
										<td><img src="images/icons/info_small.png" title="Name/description of the system (e.g. 'B-DNA', 'tamoxifen')" /></td>
										<td><input name="systemname" value="" type="text" style="width:250px" /></td>
									</tr>
									<tr>
										<td style="width:200px;text-align:right">Molecule type</td>
										<td><img src="images/icons/info_small.png" title="Type of molecule composing the system." /></td>
										<td>
								            <select name="molecule" style="width:250px">
								              	<c:forEach items="${moleculeList}" var="molecule">
													<option value="<c:out value="${molecule.term}" />"><c:out value="${molecule.term}" /></option>
												</c:forEach>
												<option selected="selected" value=""></option>
								            </select>
							        	</td>
							        </tr>
						            <tr>
						            	<td style="width:200px;text-align:right">Residue chain</td>
										<td><img src="images/icons/info_small.png" title="Amino-acid or nucleotide sequence (software-specific or standard symbols) Use '%' for wildcards (e.g. '%ATTCG%', 'RG5 RG RC RU %')." /></td>
						            	<td><input name="residues" value="" type="text" style="width:250px" /></td>
						            </tr>
						            <tr>
						            	<td style="width:200px;text-align:right">Number of atoms: Min</td>
										<td><img src="images/icons/info_small.png" title="Minimum number of atoms in the system." /></td>
						            	<td><input name="nAtomsMin" value="" type="text" style="width:50px" /></td>
						            </tr>
						            <tr>
						            	<td style="width:200px;text-align:right">Max</td>
										<td><img src="images/icons/info_small.png" title="Maximum number of atoms in the system." /></td>
						            	<td><input name="nAtomsMax" value="" type="text" style="width:50px" /></td>
						            </tr>
				          		</table>
				          	</td>
				        </tr>
				        <tr>
			          		<td style="width:100%;">
								<hr/>
					            <b>Method and parameters</b><br/>
					            <table style="width:100%;">
							         <tr><td style="width:200px;text-align:right">Modelling type</td>
										<td><img src="images/icons/info_small.png" title="Modelling type" /></td>
							         	<td>
							              <select name="model" style="width:250px">
							              <c:forEach items="${methodList}" var="method">
												<option value="<c:out value="${method.term}" />"><c:out value="${method.term}" /></option>
											</c:forEach>
											<option selected="selected" value=""></option>
							            </select>
						            </td></tr>
					            </table>
								<hr/>
				            </td>
				      	</tr>
		        		
			          <tr><td style="height:5px;"></td></tr>
			          <tr>
						<td style="text-align:right;width:100%;">
							<input class="button" type="button" value=" Search " onclick="executeExperimentSearch(20, 'all', 'basicSearchForm', 0)"/>
						</td>
			          </tr>
		          </table>
				</form>
				</div>
				
				<!-- ===================================== MD EXPERIMENT SEARCH =================================== -->
			    <div id="md_collections">
					<form id="mdSearchForm" class="blank">
		        	<table class="center">
		        		<tr><td>
		        				<img class="icon" src="images/md_logo.png" title="Search molecular dynamics simulations"/>
		        				<span style="font-size: 1.2em;"><strong>Search molecular dynamics simulations</strong></span>
		        			</td>
		        		</tr>
		        		<tr>
			          		<td style="width:100%;"><br/>
								<hr/>
				          		<b>General</b><br/>
					          	<table style="width:100%;">
									<tr><td style="width:200px;text-align:right">Experiment name</td>
										<td><img src="images/icons/info_small.png" title="Name of the experiment (root directory). Use '%' for wildcards." /></td>
										<td><input name="name" value="" type="text" style="width:250px" /></td>
									</tr>
							        <tr>
							        	<td style="width:200px;text-align:right">Description</td>
										<td><img src="images/icons/info_small.png" title="Description of the experiment. Use '%' for wildcards." /></td>
							        	<td><input name="description" value="" type="text" style="width:250px" /></td>
							        </tr>
									<tr>
										<td style="width:200px;text-align:right">Software</td>
										<td><img src="images/icons/info_small.png" title="Name of the software package used to perform the simulations" /></td>
										<td>
								            <select name="software" style="width:250px">
								              	<c:forEach items="${softwareList}" var="sw">
													<option value="<c:out value="${sw.term}" />"><c:out value="${sw.term}" /></option>
												</c:forEach>
												<option selected="selected" value=""></option>
								            </select>
								            <input name="model" type="hidden" value="Molecular dynamics"/>
							        	</td>
							        </tr>
						        </table>
				          	</td>
				         </tr>
		          		<tr>
			          		<td style="width:100%;">
								<hr/>
				          		<b>Molecular system</b><br/>
					          	<table style="width:100%;">
									<tr>
										<td style="width:200px;text-align:right">Name/description</td>
										<td><img src="images/icons/info_small.png" title="Name/description of the system (e.g. 'B-DNA', 'tamoxifen')" /></td>
										<td><input name="systemname" value="" type="text" style="width:250px" /></td>
									</tr>
									<tr>
										<td style="width:200px;text-align:right">Type</td>
										<td><img src="images/icons/info_small.png" title="Type of molecule composing the system." /></td>
										<td>
							            <select name="molecule" style="width:250px">
							              	<c:forEach items="${moleculeList}" var="molecule">
												<option value="<c:out value="${molecule.term}" />"><c:out value="${molecule.term}" /></option>
											</c:forEach>
											<option selected="selected" value=""></option>
							            </select>
							        	</td>
							        </tr>
						            <tr>
						            	<td style="width:200px;text-align:right">Residue chain</td>
										<td><img src="images/icons/info_small.png" title="Amino-acid or nucleotide sequence (software-specific or standard symbols) Use '%' for wildcards (e.g. '%ATTCG%', 'RG5 RG RC RU %')." /></td>
						            	<td><input name="residues" value="" type="text" style="width:250px" /></td>
						            </tr>
						            <tr>
						            	<td style="width:200px;text-align:right">Number of atoms: Min</td>
										<td><img src="images/icons/info_small.png" title="Minimum number of atoms in the system." /></td>
						            	<td><input name="nAtomsMin" value="" type="text" style="width:50px" /></td>
						            </tr>
						            <tr>
						            	<td style="width:200px;text-align:right">Max</td>
										<td><img src="images/icons/info_small.png" title="Maximum number of atoms in the system." /></td>
						            	<td><input name="nAtomsMax" value="" type="text" style="width:50px" /></td>
						            </tr>
						        </table>
				          	</td>
				        </tr>
				        <tr>
			          		<td style="width:100%;">
								<hr/>
					            <b>Method and parameters</b><br/>
					            <table style="width:100%;">
							        <tr>
							        	<td style="width:200px;text-align:right">Force field</td>
										<td><img src="images/icons/info_small.png" title="Force field name" /></td>
							        	<td><input id="forcefield" name="forcefield" value="" type="text" style="width:250px"/></td>
							        </tr>
							        <tr>
							        	<td style="width:200px;text-align:right">Solvent</td>
										<td><img src="images/icons/info_small.png" title="Representation of the solvent in the system" /></td>
							        	<td>
							        		<select name="solvent" style="width:250px">
								              	<c:forEach items="${solventList}" var="solv">
													<option value="<c:out value="${solv.term}" />"><c:out value="${solv.term}" /></option>
												</c:forEach>
												<option selected="selected" value=""></option>
								            </select>
							        	</td>
							        </tr>
							        <tr>
							        	<td style="width:200px;text-align:right">Electrostatics</td>
										<td><img src="images/icons/info_small.png" title="Modelling method for electrostatics interactions" /></td>
							        	<td>
							        		<select name="electrostatics" style="width:250px">
								              	<c:forEach items="${electrostaticsList}" var="elec">
													<option value="<c:out value="${elec.term}" />"><c:out value="${elec.term}" /></option>
												</c:forEach>
												<option selected="selected" value=""></option>
								            </select>
							        	</td>
							        </tr>
							        <tr>
							        	<td style="width:200px;text-align:right">Boundary conditions</td>
										<td><img src="images/icons/info_small.png" title="Type of boundary conditions" /></td>
							        	<td>
							        		<select name="boundcond" style="width:250px">
								              	<c:forEach items="${boundaryConditionList}" var="bc">
													<option value="<c:out value="${bc.term}" />"><c:out value="${bc.term}" /></option>
												</c:forEach>
												<option selected="selected" value=""></option>
								            </select>
							        	</td>
							        </tr>
							        <tr>
							        	<td style="width:200px;text-align:right">Constraints</td>
										<td><img src="images/icons/info_small.png" title="Constraint algorithm" /></td>
							        	<td>
							        		<select name="constraints" style="width:250px">
								              	<c:forEach items="${constraintList}" var="constraint">
													<option value="<c:out value="${constraint.term}" />"><c:out value="${constraint.term}" /></option>
												</c:forEach>
												<option selected="selected" value=""></option>
								            </select>
							        	</td>
							        </tr>
					            </table>
								<hr/>
				            </td>
				      </tr>
			          <tr><td style="height:5px;"></td></tr>
			          <tr>
						<td style="text-align:right;width:100%;">
							<input class="button" type="button" value=" Search " onclick="executeExperimentSearch(20,'MD','mdSearchForm',0)"/>
						</td>
			          </tr>
		          </table>
				</form>
				</div>
				
				<!-- ===================================== QM EXPERIMENT SEARCH  =================================== -->
				<div id="qm_collections">
				<form id="qmSearchForm" class="blank">
		        	<table class="center">
		        		<tr><td>
		        				<img class="icon" src="images/qm_icon_small.png" title="Search quantum calculations"/>
		        				<span style="font-size: 1.2em;"><strong>Search quantum calculations</strong></span>
		        			</td>
		        		</tr>
		        		<tr>
			          		<td style="width:100%;"><br/>
								<hr/>
				          		<b>General</b><br/>
					          	<table style="width:100%;">
									<tr>
										<td style="width:200px;text-align:right">Experiment name</td>
										<td><img src="images/icons/info_small.png" title="Name of the experiment (root directory). Use '%' for wildcards." /></td>
										<td><input name="name" value="" type="text" style="width:250px" /></td>
									</tr>
							        <tr>
							        	<td style="width:200px;text-align:right">Description</td>
										<td><img src="images/icons/info_small.png" title="Description of the experiment. Use '%' for wildcards." /></td>
							        	<td><input name="description" value="" type="text" style="width:250px" /></td>
							        </tr>
									<tr>
										<td style="width:200px;text-align:right">Software</td>
										<td><img src="images/icons/info_small.png" title="Name of the software package used to perform the calculations" /></td>
										<td>
								            <select name="software" style="width:250px">
								              	<c:forEach items="${softwareList}" var="sw">
													<option value="<c:out value="${sw.term}" />"><c:out value="${sw.term}" /></option>
												</c:forEach>
												<option selected="selected" value=""></option>
								            </select>
								            <input name="model" type="hidden" value="Quantum mechanics"/>
							        	</td>
							        </tr>
						        </table>
				          	</td>
				         </tr>
		          		<tr>
			          		<td style="width:100%;">
								<hr/>
				          		<b>Compound</b><br/>
					          	<table style="width:100%;">
									<tr>
										<td style="width:200px;text-align:right">Name/description</td>
										<td><img src="images/icons/info_small.png" title="Name/description of the compound (e.g. 'tamoxifen')" /></td>
										<td><input name="systemname" value="" type="text" style="width:250px" /></td>
									</tr>
						            <tr>
						            	<td style="width:200px;text-align:right">Atomic composition</td>
										<td><img src="images/icons/info_small.png" title="Atomic composition of the compound (e.g. 'N[2]', 'C[21]H[28]O[5]', 'CH[5]N')." /></td>
						            	<td><input name="composition" value="" type="text" style="width:250px" /></td>
						            </tr>
						        </table>
				          	</td>
				        </tr>
				        <tr>
		            		<td style="width:100%;">
								<hr/>
					            <b>Method and parameters</b><br/>
					            <table style="width:100%;">
							        <tr>
							        	<td style="width:200px;text-align:right">Basis set</td>
										<td><img src="images/icons/info_small.png" title="Name of the basis set library. Use '%' for wildcards (e.g. '6-31g%')." /></td>
							        	<td><input id="basisset" name="basisset" value="" type="text" style="width:250px"/></td>
							        </tr>
							        <tr>
							        	<td style="width:200px;text-align:right">Level of theory</td>
										<td><img src="images/icons/info_small.png" title="Level of theory (e.g. HF, Moeller-Plesset, DFT)" /></td>
							        	<td><input id="leveloftheory" name="leveloftheory" value="" type="text" style="width:250px"/></td>
							        </tr>
							        <tr>
							        	<td style="width:200px;text-align:right">Method name</td>
										<td><img src="images/icons/info_small.png" title="Specific method name (e.g. MP2, B3LYP, CCSD)" /></td>
							        	<td><input id="qmMethod" name="qmMethod" value="" type="text" style="width:250px"/></td>
							        </tr>
							        <tr>
							        	<td style="width:200px;text-align:right">Total charge</td>
										<td><img src="images/icons/info_small.png" title="Total charge" /></td>
							        	<td><input name="charge" value="" type="text" style="width:250px"/></td>
							        </tr>
							        <tr>
							        	<td style="width:200px;text-align:right">Spin multiplicity</td>
										<td><img src="images/icons/info_small.png" title="Spin multiplicity" /></td>
							        	<td><input name="multiplicity" value="" type="text" style="width:250px"/></td>
							        </tr>
					            </table>
				            </td>
			            </tr>
						<tr>
							<td style="width:100%;">
								<hr/>
								<b>Calculations</b><br/>
								<table style="width:100%;">
							        <tr>
										<td><input name="calculations" type="checkbox" value="spe" />&nbsp;SPE calculation</td>
										<td><input name="calculations" type="checkbox" value="optimization" />&nbsp;Geometry optimization</td>
										<td><input name="calculations" type="checkbox" value="freq" />&nbsp;Frequency calculation</td>
									</tr>
									<tr>
										<td><input name="calculations" type="checkbox" value="scan" />&nbsp;Scan</td>
										<td><input name="calculations" type="checkbox" value="nmr" />&nbsp;NMR calculation</td>
										<td><input name="calculations" type="checkbox" value="stability" />&nbsp;Stability calculation</td>
									</tr>
								</table>
								<hr/>
			          		</td>
			          </tr>
			          <tr><td style="height:5px;"></td></tr>
			          <tr>
						<td style="text-align:right;width:100%;" colspan="2">
							<input class="button" type="button" value=" Search " onclick="executeExperimentSearch(20,'QM','qmSearchForm',0)"/>
						</td>
			          </tr>
		          </table>
				</form>
			</div>
			<!-- ===================================== SET SEARCH  =================================== -->
			<div id="sets">
				<form id="setSearchForm" class="blank">
		        	<table class="center">
		        		<tr><td>
		        				<img class="icon" src="images/icons/folder_full_large.png" title="Search all experiment sets"/>
		        				<span style="font-size: 1.2em;"><strong>Search experiment sets</strong></span>
		        			</td>
		        		</tr>
		        		<tr>
			          		<td style="width:100%;"><br/>
								<hr/>
				          		<b>General</b><br/>
					          	<table style="width:100%;">
					          		<tr>
										<td style="width:200px;text-align:right">Software</td>
										<td><img src="images/icons/info_small.png" title="Name of the software package used to perform the simulations/calculations" /></td>
										<td>
								            <select name="software" style="width:250px">
								              	<c:forEach items="${softwareList}" var="sw">
													<option value="<c:out value="${sw.term}" />"><c:out value="${sw.term}" /></option>
												</c:forEach>
												<option selected="selected" value=""></option>
								            </select>
							        	</td>
							        </tr>
							        <tr>
							        	<td style="width:200px;text-align:right">Set name</td>
										<td><img src="images/icons/info_small.png" title="Name of the experiment set. Use '%' for wildcards." /></td>
							        	<td><input name="name" value="" type="text" style="width:250px" /></td>
							        </tr>
							        <tr>
							        	<td style="width:200px;text-align:right">Description</td>
										<td><img src="images/icons/info_small.png" title="Description of the experiment set. Use '%' for wildcards." /></td>
							        	<td><input name="description" value="" type="text" style="width:250px" /></td>
							        </tr>
							        <tr>
							        	<td style="width:200px;text-align:right">Owner</td>
										<td><img src="images/icons/info_small.png" title="Owner's username" /></td>
							        	<td><input name="owner" value="" type="text" style="width:250px"/></td>
							        </tr>
		        					<tr><td style="width:200px;text-align:right">Creation date</td>
										<td><img src="images/icons/info_small.png" title="Date the experiment set was created" /></td>
			        					<td style="border:0px;">
								            <select name="registration" style="width:250px">
								              <option value="none">anytime</option>
								              <option value="1week">last week</option>
								              <option value="1month">last month</option>
								              <option value="6months">last 6 months</option>
								              <option value="1year">last year</option>
								              <option value="over1year">over a year ago</option>
								            </select>
								        </td>
							   		</tr>
						        </table>
				          	</td>
				        </tr>
		          		<tr>
			          		<td style="width:100%;">
								<hr/>
				          		<b>Molecular system</b><br/>
					          	<table style="width:100%;">
					          		<tr>
										<td style="width:200px;text-align:right">Name/description</td>
										<td><img src="images/icons/info_small.png" title="Name/description of the system (e.g. 'B-DNA', 'tamoxifen')" /></td>
										<td><input name="systemname" value="" type="text" style="width:250px" /></td>
									</tr>
									<tr>
										<td style="width:200px;text-align:right">Molecule type</td>
										<td><img src="images/icons/info_small.png" title="Type of molecule composing the system." /></td>
										<td>
								            <select name="molecule" style="width:250px">
								              	<c:forEach items="${moleculeList}" var="molecule">
													<option value="<c:out value="${molecule.term}" />"><c:out value="${molecule.term}" /></option>
												</c:forEach>
												<option selected="selected" value=""></option>
								            </select>
							        	</td>
							        </tr>
						            <tr>
						            	<td style="width:200px;text-align:right">Residue chain</td>
										<td><img src="images/icons/info_small.png" title="Amino-acid or nucleotide sequence (software-specific or standard symbols) Use '%' for wildcards (e.g. '%ATTCG%', 'RG5 RG RC RU %')." /></td>
						            	<td><input name="residues" value="" type="text" style="width:250px" /></td>
						            </tr>
				          		</table>
				          	</td>
				        </tr>
				        <tr>
			          		<td style="width:100%;">
								<hr/>
					            <b>Method and parameters</b><br/>
					            <table>
							         <tr><td style="width:200px;text-align:right">Modelling type</td>
										<td><img src="images/icons/info_small.png" title="Modelling type" /></td>
							         	<td>
							              <select name="model" style="width:250px">
							              <c:forEach items="${methodList}" var="method">
												<option value="<c:out value="${method.term}" />"><c:out value="${method.term}" /></option>
											</c:forEach>
											<option selected="selected" value=""></option>
							            </select>
						            </td></tr>
					            </table>
								<hr/>
				            </td>
				      	</tr>
		        		
			          <tr><td style="height:5px;"></td></tr>
			          <tr>
						<td style="text-align:right;width:100%;">
							<input class="button" type="button" value=" Search " onclick="executeSetSearch(20, 'setSearchForm', 0)"/>
						</td>
			          </tr>
		          </table>
				</form>
				</div>
				
				<!-- ===================================== SET SEARCH =================================== -->
				
				<!-- ===================================== SEARCH FILES =================================== -->
				<div id="files" >
				<form id="fileSearchForm" class="blank">
		        	<table class="center">
		        		<tr><td>
		        				<img class="icon" src="images/icons/full_page_large.png" title="Search all files"/>
		        				<span style="font-size: 1.2em;"><strong>Search files</strong></span>
		        			</td>
		        		</tr>
		          		<tr>
		          			<td style="width:100%;"><br/>
		          				<hr/>
					            <b>General</b><br/>
								<table style="width:100%;">
									<tr>
										<td style="width:200px;text-align:right">File name</td>
										<td><img src="images/icons/info_small.png" title="Name of the file. Use '%' for wildcards (e.g. 'myfile%.pdb')." /></td>
										<td><input name="name" value="" type="text" style="width:250px"/></td>
									</tr>
							        <tr>
							        	<td style="width:200px;text-align:right">Description</td>
										<td><img src="images/icons/info_small.png" title="Description of the file Use '%' for wildcards." /></td>
							        	<td><input name="description" value="" type="text" style="width:250px" /></td>
							        </tr>
									<tr><td style="width:200px;text-align:right">Owner</td>
										<td><img src="images/icons/info_small.png" title="File owner's username" /></td>
										<td><input name="owner" value="" type="text" style="width:250px"/></td>
									</tr>
						            <tr><td style="width:200px;text-align:right">Registration date</td>
										<td><img src="images/icons/info_small.png" title="When the file was registered into iBIOMES" /></td>
										<td style="border:0px;">
								            <select name="registration" style="width:250px">
								              <option value="none">anytime</option>
								              <option value="1week">last week</option>
								              <option value="1month">last month</option>
								              <option value="6months">last 6 months</option>
								              <option value="1year">last year</option>
								              <option value="over1year">over a year ago</option>
								            </select>
							        	</td>
							       	</tr>
							        <tr><td style="width:200px;text-align:right">File size Min</td>
										<td><img src="images/icons/info_small.png" title="Minimum size of the file in KB (kilobytes)" /></td>
						            	<td><input name="sizeMin" value="" type="text" style="width:120px"/> KB</td>
						            </tr>
						            <tr>
						            	<td style="width:200px;text-align:right">Max</td>
										<td><img src="images/icons/info_small.png" title="Maximum size of the file in KB (kilobytes)" /></td>
						            	<td><input name="sizeMax" value="" type="text" style="width:120px"/> KB</td>
						            </tr>
					            </table>
				            </td>
				         </tr>
				         <tr>
		            		<td style="width:100%;">
		          				<hr/>
					            <b>Content</b><br/>
					            <table style="width:100%;">
						            <tr>
						            	<td style="width:200px;text-align:right">Software</td>
										<td><img src="images/icons/info_small.png" title="Name of the software package used to generate the file" /></td>
										<td>
								            <select name="software" style="width:250px">
											 	<c:forEach items="${softwareList}" var="software">
													<option value="<c:out value="${software.term}" />"><c:out value="${software.term}" /></option>
												</c:forEach>
												<option selected='selected' value=""></option>
								            </select>
								        </td>
								    </tr>
						            <tr>
						            	<td style="width:200px;text-align:right">File format</td>
										<td><img src="images/icons/info_small.png" title="File format" /></td><td>
							              <select name="format" style="width:250px">
							             	<c:forEach items="${formatList}" var="format">
												<option value="<c:out value="${format.term}" />"><c:out value="${format.term}" /></option>
											</c:forEach>
											<option selected='selected' value=""></option>
							            </select>
						            </td></tr>
					            </table>
		          				<hr/>
				            </td>
			          </tr>
			          <tr style="height:5px"><td></td></tr>
			          <tr>
						<td style="text-align:right;width:100%;">
							<input class="button" type="button" value=" Search " onclick="executeFileSearch(20, 'fileSearchForm', 0)"/>
						</td>
			          </tr>
		          </table>
				</form>
				</div>
				
				<!-- ===================================== CUSTOM SEARCH =================================== -->
				
				<div id="custom">
					<form id="customSearchForm" class="blank">
						<table class="center">
			        		<tr><td>
			        				<img class="icon" src="images/icons/process_large.png" title="Build your own search"/>
			        				<span style="font-size: 1.2em;"><strong>Advanced experiment search</strong></span>
			        			</td>
			        		</tr>
							<tr><td><br/><hr/><b>General</b></td></tr>
							<tr style="width:100%;">
								<td>
									<table>
										<tr>
											<td style="width:150px;text-align:right">Experiment name</td>
											<td><img src="images/icons/info_small.png" title="Name of the experiment (root directory). Use '%' for wildcards." /></td>
											<td><input name="name" value="" type="text" style="width:200px" /></td>
										</tr>
								        <tr>
								        	<td style="width:150px;text-align:right">Description</td>
											<td><img src="images/icons/info_small.png" title="Description of the experiment. Use '%' for wildcards." /></td>
								        	<td><input name="description" value="" type="text" style="width:200px" /></td>
								        </tr>
										<tr><td style="width:150px;text-align:right">Created</td>
										<td><img src="images/icons/info_small.png" title="When the file was registered into iBIOMES" /></td>
											<td>
												<select name="registration" style="width:200px">
									              <option value="none">anytime</option>
									              <option value="1week">last week</option>
									              <option value="1month">last month</option>
									              <option value="6months">last 6 months</option>
									              <option value="1year">last year</option>
								              	  <option value="over1year">over a year ago</option>
									            </select>
						            		</td>
						            	</tr>
										<tr>
											<td style="width:150px;text-align:right">Owner</td>
											<td><img src="images/icons/info_small.png" title="Experiment owner's username" /></td>
											<td><input name="owner" value="" type="text" style="width:200px" /></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr><td style="width:100%;"><hr/><b>Metadata</b></td></tr>
							<tr>
					        	<td style="width:100%;">
					        		<table id="searchCriteria" style="margin-left:0px"></table>
					        	</td>
					        </tr> 
					        <tr><td>
								<a style="cursor:pointer" onclick="addSearchCriteria('searchCriteria','newCriteriaId')">
									<img class="icon" src="images/icons/add_small.png"/> Add new standard search criteria
								</a>
								<input id="newCriteriaId" type="hidden" value="0"/></td>
							</tr>
							<tr><td>
								<a style="cursor:pointer" onclick="addFreeSearchCriteria('searchCriteria','newCriteriaId')">
									<img class="icon" src="images/icons/add_small.png"/> Add new free search criteria
								</a></td>
							</tr>
					        <tr style="height:10px"></tr>
					        <tr>
					        	<td style="text-align:right;width:100%;">
					        		<input class="button" type="button" value=" Search " onclick="executeExperimentSearch(20, 'custom', 'customSearchForm', 0)"/></td></tr>
					        <tr style="height:10px"><td></td>
					        </tr>
			          	</table>
					</form>
				</div>
				<!-- ===================================== SEARCH RESULTS =================================== -->
				<div id="results">
					<br/>
					<div id="search-pages-links"></div>
					<div id="search-results"></div>
					<div id="search-loading" style="text-align:center;display:none;"><img src="animations/loading50.gif"></img></div>
				</div>
			</div>
			
			
		<div id="error-message" title="Error"></div>
		<div id="info-message" title="Info"></div>
		<div id="confirm-message" title="Confirm"></div>
		
	</div>
	
	<!-- content-wrap ends here -->		
	</div></div>
	
<!-- wrap ends here -->

<br/>
</div>

<!-- footer -->
<h:footer/>

</body>
</html>
