<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/D/tdxhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<h:meta/>

<script type="text/javascript">
	var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
</script>

<link rel="stylesheet" href="style/css/ibiomes.css" type="text/css" />

<script type="text/javascript" src="js/resources.js"></script>

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
			<li><a id="current" href="documentation.jsp">Documentation</a></li>
			<li><a href="about.jsp">About</a></li>
			<li><a href="cart.do"><h:cart/></a></li>
		</ul>
		<!-- login/logout link -->
		<h:login/>
	
	</div></div>
	<br/>
	
	<!-- content-wrap starts here -->
	<div id="content-wrap"><div id="content">
		
	<div id="main" style="width:100%">
	<div class="post">
	
	<h1>Documentation</h1>
	<ul>
		<li><a class="link" href="#overview">Project overview</a></li>
		<li><a class="link" href="#installation">Installation</a></li>
		<li><a class="link" href="#cli">Command-line interface</a></li>
		<li><a class="link" href="#xmlRules">Metadata generation rules</a></li>
	</ul>
	<p>Note: the source code and compiled libraries will soon be made available through 
	<a href="https://github.com/jcvthibault/ibiomes" class="link">GitHub</a>.</p>
	</div>
	
	<br/>		
	<h3>Overview</h3>
	<p style="text-align:justify">iBIOMES is a project lead by the <a class="link" href="http://www.chpc.utah.edu/~cheatham/">Cheatham lab</a> at the University of Utah. It aims to:</p> 
	<ul>
	<li>Improve the workflow at the researcher's lab through a searchable and distributed file repository that can be setup locally</li>
	<li>Allow collaboration at the community level through grid services for data publication, retrieval, and visualization.</li>
	</ul>
	<p>Check out the <a class="link" href="gallery.jsp">gallery</a> for screenshots of the current web interface.</p>
	<br/>

	<h3>Related links</h3>
	<ul>
	<li><a class="link" href="https://www.irods.org" target="_blank">iRODS home page</a></li>
	<li><a class="link" href="index.do">iBIOMES login</a></li>
	<li><a class="link" href="apidocs/index.html" target="_blank">Java API</a></li>
	</ul>
	
	<!-- ====================================  PROJECT OVERVIEW  ========================================= -->
	
	<br/>
	<br/>
	<a name="overview"></a>
	<div class="post">
	<h1>The iBIOMES project</h1>
	<br/>
	<h3>Creating a virtual data warehouse</h3>
	<p style="text-align:justify"><img src="images/grid.png" class="float-left"/>
	The iBIOMES system (Integrated BIOMolEcular Simulations) is a data handling system that facilitates the management of 
	file servers at a researcher's site working in the field of biomolecular simulation. The data handling system, based on 
	the <a class="link" href="https://www.irods.org">iRODS framework</a> (developed at DICE), creates a virtual data warehouse at the 
	researcher's site, where data can be distributed among multiple servers and searched through metadata query. Metadata 
	include system information (e.g. file location, file name) and user-defined metadata as well (e.g. simulation description,
	title, force field used). iBIOMES offers a unix-like command-line interface, which can be used to publish simulation files 
	into the system. A web client and a REST interface will be available as well to facilitate queries of iBIOMES data for the 
	end-user and external systems.</p>
		
	<br/>
	<h3>Grid integration for collaboration across institutions</h3>
	<p style="text-align:justify">The aim of the proposed infrastructure is to allow collaborative work between institutions for the 
	analysis of biomolecular simulation-derived data. The user should not need any knowledge on the physical 
	location of the data to search and retrieve the data of interest.</p>
	<p style="text-align:justify">The data grid service should support at least the following services:</p>
	<ul>
		<li>publication (users can publish  their data to make it available to the community)</li>
		<li>retrieval service (users can download published data from other researchers' sites)</li>
		<li>search (users can search data using criteria such as molecule name, residues, software used, owner, etc.)</li>
		<li>data transfer (data movements between researchers' sites should be enabled through fast and reliable transfer protocols such as Fast Data Transfer or GridFTP).</li>
	</ul>
	</div>
	
	<!-- ====================================  INSTALLATION  ========================================= -->
	
	<br/>
	<br/>
	<br/>
	<a name="installation"></a>
	<div class="post">
	<h1>iBIOMES system installation</h1>
	<h3>Steps</h3>
	<ol>
		<li>Install iRODS server and client tools</li>
		<li>Populate iBIOMES metadata catalog</li>
		<li>Install iBIOMES library and client tools</li>
		<li>Optional: deploy the iBIOMES web portal</li>
	</ol>
	<h3>System requirements</h3>
	<ul>
		<li>iRODS: gcc (or cc) compiler and the standard include files and libraries</li>
		<li>iBIOMES: Java 1.7 +</li>
		<li>iBIOMES web portal (optional): Apache Tomcat server</li>
	</ul>
	<br/>
	<h3>1. Install iRODS server and client tools</h3>
	<ol>
		<li>Download the <a class="link" href="https://www.irods.org/download.html">latest iRODS release</a> and run <span class="code">./irodssetup</span>. This script will install the iRODS server, the iCAT database, and the client tools (i-commands).</li>
		<li>If necessary open a range of ports to allow parallel transfers, and make sure these ports are specified in the <a class="link" href="https://www.irods.org/index.php/Specifying_Ports">iRODS server config file</a>.</li>
		<li>Set the <span class="code">$IRODS_HOME</span> environment variable to point to the root of the extracted directory and add <span class="code">$IRODS_HOME/clients/icommands/bin</span> to your path</li>
	</ol>
	<code>
	export IRODS_HOME=/IRODS/INSTALL/DIR<br/>
	export PATH=$PATH:$IRODS_HOME/clients/icommands/bin
	</code>
	<p>Notes:</p>
	<ul>
		<li>It is recommended to just use the Postgresql download that they offer during the install as the database management system for iCAT.</li>
		<li>GSI authentication is not necessary to run iBIOMES.</li>
	</ul>
	<p style="text-align:justify;">Once the installation is complete launch the irods server:</p>
	<code>
	irodsctl -start
	</code>
	<p>If you need to stop the server type:</p>
	<code>
	irodsctl -stop
	</code>
	<p>To test out your iRODS server, install the i-commands (without the iRODS server or iCAT) on a different machine (e.g. home computer, XSEDE node) and try to browse the server data using the 'ils' and 'icd' commands 
	(see <a class="link" href="https://www.irods.org/index.php/icommands" target="_blank">i-commands</a> manual for more information).
	It is recommended to go through the <a class="link" href="https://www.irods.org/index.php/Tutorial">iRODS tutorial</a> if you have not used iRODS or the i-commands before.
	</p>
	<p style="text-align:justify;">See the <a class="link" href="https://www.irods.org/index.php/Installation" target="_blank">iRODS installation documentation</a> for more details.</p>

    <br/>
	<h3>2. Populate metadata catalog</h3>
	<p style="text-align:justify;">Nothing to do here yet (use catalog deployed on CHPC server)</p>
	
    <br/>
	<h3>3. Install iBIOMES library and client tools</h3>
	<ol>
		<li>Download and untar <a class="link" href="">ibiomes.tar.gz</a></li>
		<li>Set the <span class="code">$IBIOMES_HOME</span> environment variable to point to the root of the extracted directory and add <span class="code">$IBIOMES_HOME/scripts</span> to your $PATH</li>
	</ol>
	<code>
	export IBIOMES_HOME=/IBIOMES/INSTALL/DIR<br/>
	export PATH=$PATH:$IBIOMES_HOME/scripts
	</code>
	<p>You can also install the iBIOMES tools on a remote resource (e.g. home computer, XSEDE node) if you need to copy and register data from there.
	See the <a class="link" href="documentation.jps">documentation page</a> for usage of the iBIOMES command-line interface.</p>
    
    <br/>
    <h3>4. Deploy iBIOMES web portal</h3>
	<p style="text-align:justify;">
	If you do not want to deploy your own web portal, you can use the one here: 
	<a class="link" href="http://ibiomes.chpc.utah.edu/" target="_blank">http://ibiomes.chpc.utah.edu/</a>.
	To deploy your own iBIOMES portal instance:
	</p>
	<ol>
		<li>Install a web server (e.g. <a class="link" href="http://tomcat.apache.org/" target="_blank">Apache Tomcat server</a>)</li>
		<li>Download and deploy WAR file <a class="link">ibiomes-web.war</a></li>
	</ol>
	<br/>
	</div>
	
	<br/>
	<br/>
	<br/>
	
	<!-- ====================================  CLI  ========================================= -->
	
	<a name="cli"></a>
	<div class="post">
	<h1>Command-line interface</h1>
	<ul>
		<li>Copy and register a directory into iBIOMES</li>
		<li>Register a directory into iBIOMES (in-place registration)</li>
		<li>Parse a directory to extract metadata (no registration)</li>
	</ul>
	<br/>
	<h3>Copy and register a directory into iBIOMES:</h3>
	<p>Use the <i>push</i> command to physically copy a local directory and its content to a remote iBIOMES server.</p>
	<code>ibiomes push -i local-dir [-o irods-vpath] [-s software] [-x xml-descriptor] [-r default-resc]</code>
	
	<br/><h3>Register a directory into iBIOMES (in-place registration):</h3>
	<p>Use the <i>register</i> command to register a local directory and its content into iBIOMES. 
	This assumes that the input directory is hosted by a machine that is already integrated into an iBIOMES zone.</p>
	<code>ibiomes register -i local-dir [-o irods-vpath] [-s software] [-x xml-descriptor]</code>
	
	<br/><h3>Parse a directory to extract metadata (no registration):</h3>
	<p>Use the <i>parse</i> command to generate a list of metadata based on the input directory and its content.
	You can use this command to test the metadata extraction process before actually registering your experiments 
	into iBIOMES using <i>push</i> or <i>register</i></p>
	<p>This command is also useful to create XML descriptors or static HTML pages for your experiments. This is done
	 by specifying the output format (-f) and the output file path (-o)</p>
	<code>ibiomes parse -i local-dir [-s software] [-x xml-descriptor] [-f format] [-o output-file] [--verbose]</code>
	
	<br/><h3>Arguments:</h3>
	<code>
	[-i local-dir] Path to the local directory to parse/register<br/>
	[-o irods-vpath] Virtual path to the iRODS collection to be created<br/>
	[-s software] Name of the software package used to run the simulation/calculations (e.g. amber, nwchem)<br/>
	[-x xml-descriptor] Path to the XML descriptor that specifies metadata generation rules<br/>
	[-r default-resc] Name of the default iRODS resource to use for storage<br/>
	[-f format] Format of the ouput. Possible values: 'html', 'xml' (default is 'text')<br/>
	[-o output-file] Path to the output file when parsing a directory.<br/>
	[--verbose] Display file metadata. By default only the experiment-level metadata will be displayed. 
	</code>
	
	<br/>
	</div>
	<!-- ================================  METADATA GENERATION RULES  ===================================== -->
	
	<a name="xmlRules"></a>
	<div class="post">
	<h1>Metadata generation rules</h1>
	<p>This section describes the structure of the XML file that you can use to customize the way metadata is 
	generated when parsing a file for publication in iBIOMES.</p>
	<h3>SCHEMA</h3>
	<h3>Overview</h3>
	<p>The XML defines a set of rules through the following schema:</p>

<pre><code>&lt;rules&gt;
   &lt;rule type="[experiment|directory|file]" match="PATTERN"&gt;
       &lt;metadata&gt;
          &lt;avu attribute="ATTRIBUTE_CODE"&gt;VALUE&lt;/avu&gt;
       &lt;/metadata&gt;
  &lt;/rule&gt;
&lt;/rules&gt;
</code></pre>
	<p>Each rule can apply to the experiment level, a (sub)directory, or a file. For directory and file rules, 
	the targets are matched using file path matching pattern.</p>
<pre><code>Special characters:
* matches any sequence of characters
$ matches any single character
| OR operator
</code></pre>

	<p>For example *.(prmtop|topo|top) would match any file with the extension .prmtop, .topo, or .top.</p>
	<h3>File rules</h3>
	<p>File rules follow the general schema, but allow the specification of a file format and a class.</p>
<pre><code>&lt;rules&gt;
   &lt;rule type="file" match="PATTERN" format="FILE_FORMAT" class="[analysis|structure|topology|parameters]"&gt;
       &lt;metadata&gt;
          &lt;avu attribute="ATTRIBUTE_CODE"&gt;VALUE&lt;/avu&gt;
       &lt;/metadata&gt;
  &lt;/rule&gt;
&lt;/rules&gt;
</code></pre>
	<ul>
	<li>
	<strong>analysis:</strong> Flag the file as analysis data. In the web portal these files will be presented as such in the experiment summary page.</li>
	<li>
	<strong>structure:</strong> Use this for a Jmol supported file that should be displayed in the experiment summary page in the web portal</li>
	<li>
	<strong>topology:</strong> Use this flag if the file should be used as the source of topology/structure-related metadata for the experiment.</li>
	<li>
	<strong>parameters:</strong> Use this flag if the file should be used as the source of method/parameter-related metadata for the experiment.</li>
	</ul>
	<br/>
	<h3>EXAMPLES</h3>
	<h3>Experiment-level rules</h3>
<pre><code>&lt;rules&gt;
   &lt;rule type="experiment"&gt;
       &lt;metadata&gt;
          &lt;avu attribute="force_field"&gt;SPC/E&lt;/avu&gt;
       &lt;/metadata&gt;
  &lt;/rule&gt;
&lt;/rules&gt;
</code></pre>

	<h3>File-level rules</h3>
	<p>The first 2 rules are used to define the format of files based on their extension. 
	The third rule assigns metadata to a CSV file that follows a certain naming convention.</p>

<pre><code>&lt;rules&gt;
  &lt;rule type="file" match="*.(prmtop|topo|top|parm|parm7)" format="AMBER parmtop"&gt;
    &lt;metadata&gt;&lt;/metadata&gt;
  &lt;/rule&gt;
  &lt;rule type="file" match="*.com" format="GAUSSIAN com"&gt;
    &lt;metadata&gt;
        &lt;avu attribute="software_name"&gt;Gaussian&lt;/avu&gt;
    &lt;/metadata&gt;
  &lt;/rule&gt;
  &lt;rule type="file" match="summary.ETOT(.csv)?" format="CSV" class="analysis"&gt;
    &lt;metadata&gt;
      &lt;avu attribute="file_description"&gt;Evolution of total energy over time&lt;/avu&gt;
      &lt;avu attribute="data_labels"&gt;Time,Etot&lt;/avu&gt;
      &lt;avu attribute="data_units"&gt;ps,KCal/mol&lt;/avu&gt;
    &lt;/metadata&gt;
  &lt;/rule&gt;
&lt;/rules&gt;
</code></pre>
	<p>Define all images as analysis data to be presented in the experiment summary:</p>
<pre><code>&lt;rule type="file" match="*.(png|jpg|jpeg|gif|tif)" class="analysis"&gt;
  &lt;metadata&gt;&lt;/metadata&gt;
&lt;/rule&gt;
</code></pre>
	</div>
	
    <br/>
    </div>
    
	<!-- content-wrap ends here -->		
	</div></div>
	
<!-- wrap ends here -->
</div>

<!-- footer -->
<h:footer/>

</body>
</html>
