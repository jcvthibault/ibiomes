<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<h:meta/>

<link rel="stylesheet" href="style/css/ibiomes.css" type="text/css" />

<script type="text/javascript">
	var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
</script>
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
			<li><a href="documentation.jsp">Documentation</a></li>
			<li><a id="current" href="about.jsp">About</a></li>
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
	<h1>Contacts</h1>
	<p><strong>Feedback</strong></p>
	<ul>
		<li><a href="https://github.com/jcvthibault/ibiomes/issues" class="link" target="_blank">Report a bug</a></li>
		<li><a href="https://github.com/jcvthibault/ibiomes/issues" class="link" target="_blank">Request a feature</a></li>
	</ul>
	<p><b>Julien C. Thibault</b> (lead developer)</p>
	<ul>
	  <li>PhD student, University of Utah, department of <a class="link" href="http://www.bmi.utah.edu/" target="_blank">Biomedical Informatics</a></li>
	  <li>email: <a class="link" href="mailto:julien.thibault@utah.edu">&lt;julien.thibault@utah.edu&gt;</a></li>
	</ul>
	<p><b>Thomas E. Cheatham III</b> (PI)</p>
	<ul>
	  <li>Associate Professor, University of Utah, department of <a class="link" href="http://www.pharmacy.utah.edu/medchem/" target="_blank">Medicinal Chemistry</a></li>
	  <li>Website: <a class="link" href="http://www.chpc.utah.edu/~cheatham/">http://www.chpc.utah.edu/~cheatham/</a></li>
	</ul>
	</div>
	
	<div><br/>
	<h1>Publications</h1>
	<ul>
		<li>Thibault JC, Facelli JC, Cheatham III TE, 
		iBIOMES: Managing and sharing biomolecular simulation data in a distributed environment,
		<i>J. Chem. Inf. Model.</i> <strong>2013</strong>, <a class="link" href="http://www.ncbi.nlm.nih.gov/pubmed/23413948">link</a></li>
	</ul>
	<br/>
	<h1>Contributors</h1>
	<ul>
	<li><a class="link" href="http://www.bmi.utah.edu/" target="_blank">BMI department web site</a></li>
	<li><a class="link" href="http://www.chpc.utah.edu/" target="_blank">Center for High Performance Computing</a></li>
	<li><a class="link" href="http://www.chpc.utah.edu/~cheatham/" target="_blank">Cheatham Lab</a></li>
	</ul>
	<br/>
	<h3>Cheatham Lab</h3>
	<p style="text-align:justify;">The research in our lab involves the development and application of molecular dynamics and 
	free energy simulation methodologies (with <a class="link" href="http://ambermd.org">AMBER</a>, <a class="link" href="http://www.charmm.org">CHARMM</a>,
	 <a class="link" href="http://www.ks.uiuc.edu/Research/namd/">NAMD</a>, and other programs) to biomolecular systems in their native 
	environments. Until recently, this has focused heavily on the reliable representation of a large variety of nucleic acid 
	systems (DNA and RNA) in solution. We also work with various coiled-coils, enzymes and cytochrome P450's with a focus on 
	improving stability or understanding how ligands alter receptor structure upon binding. A keen focus is to both assess and 
	validate the simulation results, and to expose and overcome limitations in the methods and force fields. In addition to 
	continued development of <i>ptraj</i> (see the <a class="link" href="http://www.chpc.utah.edu/~cheatham/software.html">software</a>
	link) for analysis of MD trajectories, we are exploring methods to mine more information from the simulation data and means
	to more broadly disseminate the MD results.</p> 

	<h3>Department of Biomedical Informatics (BMI)</h3>
	<p style="text-align:justify;">The Department of Biomedical Informatics was established in 1964 at the University of Utah. Located 
	in Salt Lake City, the department is internationally recognized as a leader in biomedical informatics research and education.
	<br/>The department has a National Library of Medicine (NLM) training grant to support its educational programs. 
	Master of Science (MS) and Doctor of Philosophy (PhD) degree programs are offered along with short-term traineeships for 
	students and visiting fellows. Research affiliations include the University of Utah Health Sciences Center, Intermountain 
	Health Care facilities, the Veterans Administration Medical Center, HealthInsight (the Peer Review Organization for Utah 
	and Nevada), and the Utah Department of Health. As one of the largest biomedical informatics training programs in the world, 
	the department's faculty and students are a diverse group with a wide range of experience and interests.</p>
	
	<h3>Center for High-Performance Computing (CHPC)</h3>
	<p style="text-align:justify;">The University of Utah's Center for High Performance Computing provides large-scale computer systems, networking, and
	 the expertise to optimize the use of these high-end technologies. CHPC facilitates advance in academic disciplines whose 
	 computational requirements exceed the resources available in individual colleges or departments. Since 1996 these 
	 collaborations have resulted in more than 651 technical publications. CHPC's purview is to support faculty and research 
	 groups whose main focus requires computing and advanced networking as core instrument(s) central to their research.</p>
    
    <!-- main/post ends here -->	
    </div></div>
    
	<!-- content-wrap ends here -->		
	</div></div>
	
<!-- wrap ends here -->
</div>

<!-- footer -->
<h:footer/>

</body>
</html>
