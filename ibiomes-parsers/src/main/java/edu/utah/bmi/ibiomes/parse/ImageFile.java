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
package edu.utah.bmi.ibiomes.parse;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.utah.bmi.Utils;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

/**
 * Generic image file
 * @author Julien Thibault, University of Utah
 *
 */
public class ImageFile extends AbstractLocalFileImpl
{
	private static final long serialVersionUID = -8025149118474553688L;
	
	public static final String HEX_HEADER_JPEG 	= "FFD8FFE0";
	public static final String HEX_HEADER_PNG 	= "89504E47";
	public static final String HEX_HEADER_GIF	= "47494638";
	public static final String HEX_HEADER_TIF_BE= "4D4D002A";
	public static final String HEX_HEADER_TIF_LE= "49492A00";
	public static final String HEX_HEADER_BMP 	= "424D";
	
	public static final String IMAGE_TRANSPARENCY 	= "IMAGE_TRANSPARENCY";
	public static final String IMAGE_WIDTH 			= "IMAGE_WIDTH";
	public static final String IMAGE_HEIGHT 		= "IMAGE_HEIGHT";
	public static final String IMAGE_TYPE 			= "IMAGE_TYPE";	
	
	private BufferedImage img;
		
	/**
	 * Reference new image file
	 * @param localPath Path to local file
	 * @throws IOException
	 */
	public ImageFile(String localPath) throws IOException{
		super(localPath, FORMAT_UNKNOWN, LocalFile.TYPE_IMAGE);
		if (this.exists()){
			String fileFormat = ImageFile.findImageFormat(localPath);
			this.setFormat(fileFormat);
			getImageInfo();
		}
		else throw new IOException("Local image \""+ localPath +"\" does not exist!");
	}

	private boolean getImageInfo()
	{
		//get image info
		try{
			this.img = ImageIO.read(this);
			return true;
		}
		catch(IOException e){
			e.printStackTrace();
			System.out.println("Warning: cannot load the image (" + e.getMessage() + ")");
			return false;
		}
	}
	
	@Override
	public MetadataAVUList getMetadata() throws Exception {
		MetadataAVUList metadata = super.getMetadata();
		metadata.add(new MetadataAVU(ImageFile.IMAGE_TRANSPARENCY, String.valueOf(img.getTransparency())));
		metadata.add(new MetadataAVU(ImageFile.IMAGE_HEIGHT, String.valueOf(img.getHeight())));
		metadata.add(new MetadataAVU(ImageFile.IMAGE_WIDTH, String.valueOf(img.getWidth())));
		metadata.add(new MetadataAVU(ImageFile.IMAGE_TYPE, String.valueOf(img.getType())));
		return metadata;
	}
	
	/**
	 * Check the format of the picture by looking at header
	 * @param filePath File path
	 * @return Image format
	 * @throws IOException
	 */
	public static String findImageFormat(String filePath)
	{
		try{
			File file = new File(filePath);
			byte[] bytes = Utils.readFirstBytes(4, file);
			String hex = Utils.getHex(bytes);
			
			if (hex.equals(HEX_HEADER_JPEG)){
				return LocalFile.FORMAT_JPEG;
			}
			else if (hex.equals(HEX_HEADER_PNG)){
				return LocalFile.FORMAT_PNG;
			}
			else if (hex.equals(HEX_HEADER_TIF_BE)||hex.equals(HEX_HEADER_TIF_LE)){
				return LocalFile.FORMAT_TIF;
			}
			else if (hex.equals(HEX_HEADER_GIF)){
				return LocalFile.FORMAT_GIF;
			}
			else if (hex.startsWith(HEX_HEADER_BMP)){
				return LocalFile.FORMAT_BMP;
			}
			else return LocalFile.FORMAT_UNKNOWN;
		}
		catch(Exception e){
			e.printStackTrace();
			return LocalFile.FORMAT_UNKNOWN;
		}
	}
}
