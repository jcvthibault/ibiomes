package edu.utah.bmi.ibiomes.web;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Utils {

	/**
	 * Build navigation links from collection path
	 * @param uri Current collection URI
	 * @return
	 */
	public static String getNavigationLink(String uri)
	{	
		//return collection path as HTML navigation links
		String htmlNav = "";
		String tmpHtml = "";
		String[] uriDirs = uri.split("/");
		if (uriDirs == null)
			htmlNav = uri;
		else {
			for (int d=1; d<uriDirs.length-1; d++){
				tmpHtml += "/" + uriDirs[d];
				htmlNav += "<a class='link' href='collection.do?uri=" + tmpHtml + "'>" + uriDirs[d] + "</a> &gt; ";
			}
			htmlNav += uriDirs[uriDirs.length-1];
		}
		return htmlNav;
	}
	
	/**
	 * Build navigation links from collection path
	 * @param uri Current collection URI
	 * @return
	 */
	public static String getNavigationLinkDiabled(String uri)
	{	
		//return collection path as HTML navigation links
		String htmlNav = "";
		String[] uriDirs = uri.split("/");
		if (uriDirs == null)
			htmlNav = uri;
		else {
			for (int d=1; d<uriDirs.length-1; d++){
				htmlNav +=  uriDirs[d] + " &gt; ";
			}
			htmlNav += uriDirs[uriDirs.length-1];
		}
		return htmlNav;
	}
}
