package edu.utah.bmi.ibiomes.graphics.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.VerticalAlignment;

/**
 * Class used to create histogram plots from 2 column-data files (XY)
 * @author Julien Thibault
 *
 */
public class HistogramPlot 
{
	private final Color[] lineColorSet = {
				Color.RED, 
				Color.BLUE, 
				Color.BLACK, 
				Color.GREEN, 
				Color.ORANGE, 
				Color.CYAN, 
				Color.DARK_GRAY, 
				Color.GRAY, 
				Color.LIGHT_GRAY, 
				Color.MAGENTA, 
				Color.PINK, 
				Color.YELLOW
				};
	private ColumnDataFile csvParser;
	
	/**
	 * Initialize new histogram plot
	 * @param csv CSV file
	 */
	public HistogramPlot(ColumnDataFile csv){
		csvParser = csv;
	}

	/**
	 * Generate new histogram
	 * @param title Main title
	 * @param xTitle X-axis title
	 * @param yTitle Y-axis title
	 * @param seriesLabels Labels for series
	 * @param logY Uses logarithmic scale for Y-axis
	 * @return Plot object
	 */
	public JFreeChart createHistogramPlot(String title, String xTitle, String yTitle, String[] seriesLabels, boolean logY) 
	{
		JFreeChart chart = null;
		List<XYSeries> seriesList = null;
		
		if (csvParser.getNumberOfColumns() == 2){
			seriesList = loadDatasetFrom2Columns();
		}
		else {
			seriesList = loadDatasetFromNColumns();
		}
		
		//create dataset
		XYSeriesCollection dataset = new XYSeriesCollection();
		for(int s=0; s<seriesList.size(); s++){
			XYSeries series = seriesList.get(s);
			if (seriesLabels!=null && seriesLabels.length>s && seriesLabels[s]!=null)
				series.setKey(seriesLabels[s]);
			dataset.addSeries(series);
		}
		
		//create chart
		chart = ChartFactory.createXYBarChart(title, xTitle, false, yTitle, dataset, PlotOrientation.VERTICAL, true, true, false);
		
		//set series colors
		((XYBarRenderer) chart.getXYPlot().getRenderer()).setBarPainter(new StandardXYBarPainter());
		((XYBarRenderer) chart.getXYPlot().getRenderer()).setDrawBarOutline(true);
		((XYBarRenderer) chart.getXYPlot().getRenderer()).setBasePaint(new Color(255,255,255,0));
		((XYBarRenderer) chart.getXYPlot().getRenderer()).setBaseOutlineStroke(new BasicStroke(2.0f));
		((XYBarRenderer) chart.getXYPlot().getRenderer()).setBaseFillPaint(new Color(255,255,255,0));
		boolean hasNegativeValueInY = false;
		
		for(int s=0; s<seriesList.size(); s++){
			Color color = Color.BLACK;
			if (s < lineColorSet.length){
				color = lineColorSet[s];
			}
			((XYBarRenderer) chart.getXYPlot().getRenderer()).setSeriesFillPaint(s, new Color(255,255,255,0));
			((XYBarRenderer) chart.getXYPlot().getRenderer()).setSeriesPaint(s, new Color(255,255,255,0));
			((XYBarRenderer) chart.getXYPlot().getRenderer()).setSeriesOutlinePaint(s, color);
			
			if (seriesList.get(s).getMinY() < 0.0)
				hasNegativeValueInY = true;
		}

		//set logarithmic scale if applicable 
		if (!hasNegativeValueInY && logY){
			chart.getXYPlot().setRangeAxis(new LogarithmicAxis(yTitle));
		}
		
		//set other properties
		chart.getXYPlot().setDomainGridlinePaint(Color.white);
		chart.getXYPlot().setRangeGridlinePaint(Color.white);
		chart.getXYPlot().setBackgroundPaint(Color.white);
		chart.getXYPlot().setOutlineVisible(false);

        chart.getLegend().setHorizontalAlignment(HorizontalAlignment.LEFT);
        chart.getLegend().setVerticalAlignment(VerticalAlignment.TOP);
        chart.getLegend().setPosition(RectangleEdge.RIGHT);
        chart.getLegend().setBorder(0.0, 0.0, 0.0, 0.0);
		
		return chart;
	}
	
	/**
	 * Load dataset from 2 columns (X Y) where Y can contain multiple series
	 * @return Dataset
	 */
	private List<XYSeries> loadDatasetFrom2Columns()
	{
		List<XYSeries> seriesList = new ArrayList<XYSeries>();
		String[] headers = csvParser.getHeaders();
		String header = null;
		double prevValue = 0.0;
		
		header = "Series #" + String.valueOf(seriesList.size()+1);
		if (headers!= null && headers.length > 1)
			header = headers[1];
		XYSeries series = new XYSeries(header);
		
		boolean isFirst = true;
		int h=1;
		for (double[] xy : csvParser.getDataset())
		{
			if (isFirst){
				isFirst = false;
				prevValue = xy[0];
			}
			//if new series starts here
			if (xy[0] < prevValue){
				seriesList.add(series);
				
				header = "Series #" + String.valueOf(seriesList.size()+1);
				if (headers!= null && headers.length > h)
					header = headers[h];
				series = new XYSeries(header);
			}
			if (xy.length>1){
				series.add(xy[0], xy[1]);
				prevValue = xy[0];
			}
			//else skip (missing values)
			h++;
		}
		seriesList.add(series);
		
		return seriesList;
	}
	
	/**
	 * Load dataset from at least 3 columns (X Y1 Y2 Y3 ... Yn)
	 * @return Dataset
	 */
	private List<XYSeries> loadDatasetFromNColumns()
	{
		List<XYSeries> seriesList = new ArrayList<XYSeries>();
		String[] headers = csvParser.getHeaders();
		int nSeries = csvParser.getDataset().get(0).length;
		
		//for each series
		for (int s=1; s<nSeries; s++)
		{
			String header = "Series #" + String.valueOf(s);
			if (headers != null && headers.length > s)
				header = headers[s];
			XYSeries series = new XYSeries(header);
			
			//go through each point
			for (int p=0;p<csvParser.getDataset().size();p++)
			{
				series.add(csvParser.getDataset().get(p)[0], csvParser.getDataset().get(p)[s]);
			}
			
			seriesList.add(series);
		}
		return seriesList;
	}
}