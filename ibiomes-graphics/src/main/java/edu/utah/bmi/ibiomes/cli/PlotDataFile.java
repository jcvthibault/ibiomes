/*
 * iBIOMES - Integrated Biomolecular Simulations
 * Copyright (C) 2014  Julien Thibault, University of Utah
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.utah.bmi.ibiomes.cli;

import java.io.File;

import org.jfree.chart.JFreeChart;

import edu.utah.bmi.ibiomes.graphics.plot.ColumnDataFile;
import edu.utah.bmi.ibiomes.graphics.plot.PlotGenerator;

/**
 * CLI for chart creation
 * @author Julien Thibault, University of Utah
 *
 */
public class PlotDataFile {
	
	private final static String usage = "ibiomes-plot <data-file> <output-file> [dot|line|heatmap] <title>";
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		System.out.println("[iBIOMES] Generating plot...");

		String inputPath = null;
		String outputPath = null;
		String chartType = null;
		
		String title = null; 
		String xTitle = null;
		String yTitle = null; 
		String zTitle = null;
		
		int width = 800;
		int height = 800;
		String format = "png";
		
		//TODO more options to specify axis and image format
		
		if (args.length>=2){
			inputPath = args[0];
			outputPath = args[1];
			if (args.length>=3){
				chartType = args[2].toLowerCase();
				if (args.length>=4)
				title = args[3];
			}
			else chartType = PlotGenerator.PLOT_TYPE_LINE; 
			
			PlotDataFile.plot(inputPath, outputPath, chartType, width, height, format, title, xTitle, yTitle, zTitle);
			
			System.out.println("[iBIOMES] Done...");
		}
		else {
			System.out.println(usage);
			System.exit(1);
		}
	}
	
	/**
	 * Plot data file and save to image file
	 * @param inputPath
	 * @param outputPath
	 * @param chartType
	 * @param width
	 * @param height
	 * @param format
	 * @param title
	 * @param xtitle
	 * @param ytitle
	 * @param ztitle
	 * @throws Exception
	 */
	public static void plot(
			String inputPath, 
			String outputPath, 
			String chartType, 
			int width, 
			int height,
			String format,
			String title,
			String xtitle,
			String ytitle,
			String ztitle) throws Exception{
		
		PlotGenerator plotBuilder = new PlotGenerator();
		ColumnDataFile csv = new ColumnDataFile(new File(inputPath));
		JFreeChart chart = plotBuilder.createPlot(csv, chartType, title, xtitle, ytitle, ztitle);
		plotBuilder.createImage(chart, width, height, outputPath, format);
	}
}
