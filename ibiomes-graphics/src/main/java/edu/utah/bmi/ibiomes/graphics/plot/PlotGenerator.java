package edu.utah.bmi.ibiomes.graphics.plot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfree.chart.JFreeChart;

/**
 * Plot generator
 * @author Julien Thibault - University of Utah, BMI
 *
 */
public class PlotGenerator {

	public static final String PLOT_TYPE_HEATMAP = "heatmap";
	public static final String PLOT_TYPE_SCATTER = "dot";
	public static final String PLOT_TYPE_LINE = "line";
	public static final String PLOT_TYPE_HISTOGAM = "histogram";
	
	/**
	 * Create plot of given type using CSV data
	 * @param csv CSV file
	 * @param selectedChartType Plot type
	 * @param title Main title
	 * @param xTitle X-axis title
	 * @param yTitle Y-axis title
	 * @param zTitle Z-axis title
	 * @param seriesLabels Labels for series
	 * @param logX Uses logarithmic scale for X-axis
	 * @param logY Uses logarithmic scale for Y-axis
	 * @return Plot object
	 * @throws Exception
	 */
	public JFreeChart createPlot(
			ColumnDataFile csv, 
			String selectedChartType,
			String title, 
			String xTitle, String yTitle, String zTitle,
			String[] seriesLabels,
			boolean logX, boolean logY) throws Exception
	{
		JFreeChart chart = null;
		
		if ( xTitle == null || xTitle.trim().length()==0)
			xTitle = "X";
		if ( yTitle == null || yTitle.trim().length()==0)
			yTitle = "Y";
		if ( zTitle == null || zTitle.trim().length()==0)
			zTitle = "Z";
		
		///if the type of chart has been specified
		if (selectedChartType != null && selectedChartType.trim().length()>0)
		{
			if (selectedChartType.toLowerCase().equals(PLOT_TYPE_HEATMAP)){
				HeatMap plot = new HeatMap(csv);
				chart = plot.createHeatMap(title, xTitle, yTitle, zTitle);
			}
			else if (selectedChartType.toLowerCase().equals(PLOT_TYPE_SCATTER)){
				DotPlot plot = new DotPlot(csv);
				chart = plot.createDotPlot(title, xTitle, yTitle, seriesLabels, logX, logY);
			}
			else if (selectedChartType.toLowerCase().equals(PLOT_TYPE_HISTOGAM)){
				HistogramPlot plot = new HistogramPlot(csv);
				chart = plot.createHistogramPlot(title, xTitle, yTitle, seriesLabels, logY);
			}
			else {
				LinePlot plot = new LinePlot(csv);
				chart = plot.createLinePlot(title, xTitle, yTitle, seriesLabels, logX, logY);
			}
		}
		else //guess based on number of columns
		{
			if (csv.getNumberOfColumns()==3)
			{
				HeatMap plot = new HeatMap(csv);
				try {
					chart = plot.createHeatMap(title, xTitle, yTitle, zTitle);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				LinePlot plot = new LinePlot(csv);
				chart = plot.createLinePlot(title, xTitle, yTitle, seriesLabels, logX, logY);
			}
		}
		return chart;
	}
	/**
	 * Create plot of given type using CSV data
	 * @param csv CSV file
	 * @param selectedChartType Plot type
	 * @param title Main title
	 * @param xTitle X-axis title
	 * @param yTitle Y-axis title
	 * @param zTitle Z-axis title
	 * @return Plot object
	 * @throws Exception
	 */
	public JFreeChart createPlot(
			ColumnDataFile csv, 
			String selectedChartType,
			String title, 
			String xTitle, String yTitle, String zTitle) throws Exception
	{
		return createPlot(csv, selectedChartType, title, xTitle, yTitle, zTitle, null, false, false);
	}
	
	/**
	 * Create image for given chart object
	 * @param chart Chart
	 * @param width Image width
	 * @param height Image height
	 * @param outputPath Image file path (without extension)
	 * @param format Image format
	 * @throws IOException 
	 */
	public void createImage(JFreeChart chart, int width, int height, String outputPath, String format) throws IOException{
		format = format.toLowerCase();
		BufferedImage image = chart.createBufferedImage(width, height);
		ImageIO.write(image, format, new File(outputPath+"."+format));
	}
}
