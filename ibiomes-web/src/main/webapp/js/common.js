
var isPageBeingRefreshed = false;

window.onbeforeunload = function() {
    isPageBeingRefreshed = true;
};

/**
 * Check if format is image
 * @param format
 * @returns {Boolean}
 */
function isImageFormat(format){
	return (format == "JPEG" || 
			format == "PNG" || 
			format == "GIF" || 
			format == "TIF");
}

/**
 * Check if format is supoprted by Jmol
 * @param format
 * @returns {Boolean}
 */
function isJmolFormat(format){
	return (format == "PDB" || 
			format == "GAMESS output" || 
			format == "GROMACS gro" || 
			format == "GAUSSIAN log" || 
			format == "NWChem output" || 
			format == "XYZ" || 
			format == "CML" || 
			format == "SDF" || 
			format == "Mol2"
		);
}

/**
 * Abbreviate computational method name
 * @param method Method name
 */
function abbreviateMethod(method){
	var methodUpper = method.toUpperCase();
	if (methodUpper == "MOLECULAR DYNAMICS" || methodUpper == "LANGEVIN DYNAMICS")
		return "MD";
	else if (methodUpper == "QUANTUM MECHANICS")
		return "QM";
	else if (methodUpper == "REPLICA-EXCHANGE MD")
		return "QM";
	else if (methodUpper == "QUANTUM MD")
		return "QMD";
	else if (methodUpper == "QM/MM")
		return "QM/MM";
	else return method;
}

/**
 * Get string representation of file size
 * @param fileSize
 * @returns {String}
 */
function getFriendlyFileSize(fileSize)
{
	var sizeUnit = "B";
	if (fileSize > 1000000000){
		fileSize = Math.round( fileSize / 1000000000 );
		sizeUnit = "GB";
	} else if (fileSize > 1000000){
		fileSize = Math.round( fileSize / 1000000 );
		sizeUnit = "MB";
	} else if (fileSize > 1000){
		fileSize = Math.round( fileSize / 1000 );
		sizeUnit = "KB";
	}
	return (fileSize + " " + sizeUnit);
}

/**
 * Get string representation of epoch timestamp (long date format).
 * @param epoch Epoch timestamp (in ms)
 */
function getFriendlyDateLong(epoch){
	
	var m_names = new Array("January", "February", "March", 
			"April", "May", "June", "July", "August", "September", 
			"October", "November", "December");

	var d = new Date(epoch);
	var curr_date = d.getDate();
	var curr_month = d.getMonth();
	var curr_year = d.getFullYear();
	
	return (m_names[curr_month] + " " +curr_date + ", " + curr_year);
}

/**
 * Get string representation of epoch timestamp (short date format).
 * @param epoch Epoch timestamp (in ms)
 */
function getFriendlyDateShort(epoch){

	var d = new Date(epoch);
	var curr_date = d.getDate();
	var curr_month = d.getMonth();
	var curr_year = d.getFullYear().toString().substring(2);
	
	return ((curr_month+1) + "/" +curr_date + "/" + curr_year);
}

