/*
 * Original code: http://quixote.ch.cam.ac.uk/
 *  
 */

(function( $ ){

    $.fn.jmol = function( args ) {

        var settings = {
            'size'        : '350',
            'text'        : 'Load structure',
            'script'      : ''
        };

        this.extend( settings, args );
        this.append(     "<div class='jmolAppletContainer'>"
                        +   "<p style='font-size:1.2em;cursor:pointer;color:#AAAAAA;text-align:center;'>"
                        +		"<strong> &gt; " + settings.text + "</strong>"
                        +   "</p>"
                        +"</div>"
                );
    	
        $( "div.jmolAppletContainer", this ).click( function(e)
        {
        	if (!$('div.jmol-applet').length){
	            Info.script = settings.script;
	    		Jmol.getApplet("myJmol", Info);
	            var jmolInstanceHtml = Jmol.getAppletHtml(myJmol);
	            $(this).html("<div class='jmol-applet'>" + jmolInstanceHtml  + "</div>");
        	}
        });

        return this;

    };

})( jQuery );
