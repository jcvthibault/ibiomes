package edu.utah.bmi.ibiomes.graphics.test;

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.utah.bmi.ibiomes.graphics.ImageUtils;


public class TestImageThumbnail {

	private final static String imgSrcFilePath = "test/img/autocorr.jpg";
	private final static String imgOutFilePath = "test/img/autocorr_scaled.jpg";
	private final static String fileFormat = "jpg";

	private final Logger logger = Logger.getLogger(TestImageThumbnail.class);
	
	@Test
	public void test() throws Exception
	{
		try{
			float maxWidth = 50.0f;
			logger.info("Rescaling "+imgSrcFilePath+"...");
			ImageUtils.rescaleImage(imgSrcFilePath, imgOutFilePath, maxWidth, fileFormat);
		}
		catch (Exception e){
			e.printStackTrace();
			throw e;
		}
	}
}
