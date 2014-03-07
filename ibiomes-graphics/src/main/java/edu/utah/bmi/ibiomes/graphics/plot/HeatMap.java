package edu.utah.bmi.ibiomes.graphics.plot;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.LookupPaintScale;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultHeatMapDataset;
import org.jfree.data.general.HeatMapUtilities;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.VerticalAlignment;

/**
 * Class used to create heat maps from 3 column-data files (XYZ)
 * @author Julien Thibault
 *
 */
public class HeatMap
{
	private final Color[] colorMap = {
			new Color(204,0,0), 
			new Color(255,51,0), 
			new Color(255,102,0), 
			new Color(255,153,0), 
			new Color(255,204,0), 
			new Color(255,255,51), 
			new Color(204,255,102), 
			new Color(204,255,0), 
			new Color(153,255,0), 
			new Color(0,255,0)
		};
	private ColumnDataFile csvParser;
	private final LegendItemCollection legendItems = new LegendItemCollection();
	private LookupPaintScale colorScale;

	/**
	 * Initialize new heatmap
	 * @param csv CSV file
	 */
	public HeatMap(ColumnDataFile csv){
		csvParser = csv;
	}
	
	/**
	 * Generate heatmap
	 * @param title Main title
	 * @param xTitle X-axis title
	 * @param yTitle Y-axis title
	 * @param zTitle Z-axis title
	 * @return Plot object
	 * @throws Exception 
	 */
	public JFreeChart createHeatMap(String title, String xTitle, String yTitle, String zTitle) throws Exception
	{		
		JFreeChart chart = null;
		
		DefaultHeatMapDataset dataset = null;
		
		double maxZ, minZ;
		double minY, maxY;
		
		boolean isMultiSeries =  false;
		
		
		//System.out.println(arrays.size() + " points found in dataset.");
		
		if (csvParser.getNumberOfColumns() == 3) {
			dataset = loadDatasetFromXYZ(csvParser.getDataset());

			maxZ = csvParser.getMaxValues()[2];
			minZ = csvParser.getMinValues()[2];
			minY = 0.0;
			maxY = 1.0;
		}
		else {
			isMultiSeries = true;
			
			dataset = loadDatasetFromXZZ(csvParser.getDataset());

			maxZ = csvParser.getMax();
			minZ = csvParser.getMin();
			
			minY = csvParser.getDataset().get(0)[0];
			maxY = csvParser.getDataset().get(csvParser.getDataset().size()-1)[0];
		}
		String[] headers = csvParser.getHeaders();
		
		//System.out.println("Z value range = [" + minZ + ", " + maxZ + "]");
		//System.out.println("Y value range = [" + minY + ", " + maxY + "]");
		
		//color scale
		initializeColorScale(minZ, maxZ);
        
		//create heatmap image
        BufferedImage heatmapImg = HeatMapUtilities.createHeatMapImage(dataset, colorScale);
        
        //build empty bar plots to set category labels
        DefaultCategoryDataset series = new DefaultCategoryDataset();
        if (headers != null && isMultiSeries){
        	for (int i=0; i<headers.length; i++) {
            	series.addValue(0.0, "0", String.valueOf(headers[i]));
        	}
        }
        else if (isMultiSeries){
        	for (int i=0; i<csvParser.getNumberOfColumns()-1; i++) {
            	series.addValue(0.0, "0", String.valueOf(i+1));
        	}
        }
        
        chart = ChartFactory.createBarChart( 
        			title, 
        			xTitle, 
        			yTitle, 
        			series, 
        			PlotOrientation.HORIZONTAL, 
        			true, 
        			true, 
        			false);
        
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundImageAlpha(1.0f);
        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(false);
        plot.setBackgroundImage(heatmapImg);
        plot.setOutlineVisible(false);
        
        CategoryAxis domain = plot.getDomainAxis();
        domain.setLowerMargin(0.0);
        domain.setUpperMargin(0.0);
        domain.setTickMarksVisible(false);
        
        /*NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setRange(arrays.get(0)[0], arrays.get(arrays.size()-1)[0]);
        domain.setRange(0.0, w);
        //domain.setTickUnit(new NumberTickUnit(0.1));
        //domain.setVerticalTickLabels(true);*/
        
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        if (isMultiSeries)
        	range.setRange(minY, maxY);
        else {
        	range.setTickMarksVisible(false);
        	range.setTickLabelsVisible(false);
        }
        plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        
        
        //set legend
        plot.setFixedLegendItems(this.legendItems);
        
        LegendTitle legend = chart.getLegend();
        legend.setHorizontalAlignment(HorizontalAlignment.LEFT);
        legend.setVerticalAlignment(VerticalAlignment.TOP);
        legend.setPosition(RectangleEdge.RIGHT);
        legend.setBorder(0.0, 0.0, 0.0, 0.0);

        /*TextTitle legendText = new TextTitle(zTitle);
        BlockContainer container = new BlockContainer();
        container.add(legendText);
        container.add(chart.getLegend());
        legend.setWrapper(container);*/
        
        return chart;
	}
	
	/**
	 * Create heat map from 3-column datasets (XYZ)
	 * @param arrays Data in XYZ format
	 * @return Parsed dataset
	 */
	private DefaultHeatMapDataset loadDatasetFromXYZ(ArrayList<double[]> arrays)
	{
		DefaultHeatMapDataset dataset = null;

		double maxX = csvParser.getMaxValues()[0];
		double maxY = csvParser.getMaxValues()[1];
		
		double minX = csvParser.getMinValues()[0];
		double minY = csvParser.getMinValues()[1];
		
		int nX = (int)maxX + 1;
        int nY = (int)maxY + 1;
		
		dataset = new DefaultHeatMapDataset(nX, nY, minX, maxX, minY, maxY);
		for (double[] values : arrays){
			dataset.setZValue((int)values[0], (int)values[1], values[2]);
		}
		
		return dataset;
	}
	
	/**
	 * Create heat map from 3+ columns
	 * @param data Data in XZZ format
	 * @return Parsed dataset
	 */
	private DefaultHeatMapDataset loadDatasetFromXZZ(ArrayList<double[]> data)
	{
		DefaultHeatMapDataset dataset = null;

		int nSeries = data.get(0).length-1;
		int nValues = data.size();
		dataset = new DefaultHeatMapDataset( nValues, nSeries, 0, nValues-1, 0, nSeries-1);
		
		for (int s=0; s < nSeries;s++){
			for (int v=0; v < nValues; v++){
				dataset.setZValue(v, s, data.get(v)[s+1]);
			}
		}
		
		return dataset;
	}
	
	/**
	 * Create color scale
	 * @param minZ Minimum value in Z dimension
	 * @param maxZ Maximum value in Z dimension
	 */
	private void initializeColorScale(double minZ, double maxZ)
	{
		colorScale = new LookupPaintScale(minZ, maxZ, Color.BLACK);
		
		double range = (maxZ - minZ) / colorMap.length;
		double[] ranges = new double[colorMap.length];
		
		ranges[0] = minZ;
		ranges[colorMap.length-1] = maxZ;
		for (int i=1;i<colorMap.length-1;i++){
			ranges[i] = minZ + range*((double)i);
		}
		
		for (int s=0;s<ranges.length;s++){
			colorScale.add(ranges[s], colorMap[s]);
		}
		
		//create legend
        for (int s=0;s<ranges.length;s++){
        	String rangeValue = String.valueOf( ((double)Math.round(ranges[s]*100.0)/100.0));
        	Shape shape = new Rectangle(10, 10);
        	LegendItem item = new LegendItem(rangeValue, null, null, null, shape, colorMap[s] );
        	this.legendItems.add(item);
        };
	}
}
