package edu.utah.bmi.ibiomes.graphics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {
	/**
	 * Rescale image
	 * @param imgSrcFilePath Path to original image file
	 * @param imgOutFilePath Path to rescaled image
	 * @param max Max width of the rescaled image
	 * @param fileFormat Image format ('jpg', 'png', ...)
	 * @throws IOException
	 */
	public static void rescaleImage(String imgSrcFilePath, String imgOutFilePath, float max, String fileFormat) throws IOException{
		
		//load image
		File originalFile = new File(imgSrcFilePath);
	    BufferedImage originalImage = ImageIO.read(originalFile);
	    
	    //rescale
		float scaleFactor = max / ( Math.max( ((float)originalImage.getWidth()), ((float)originalImage.getHeight())) );
        int newW = (int)( ((float)originalImage.getWidth()) * scaleFactor);
        int newH = (int)( ((float)originalImage.getHeight()) * scaleFactor);
        
	    BufferedImage outputImage = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g = outputImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(originalImage, 0, 0, newW, newH, null);
        g.dispose();
        
        //write to disk
        ImageIO.write(outputImage, fileFormat, new File(imgOutFilePath));
	}
}
