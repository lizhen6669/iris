package com.li.img;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

public class ImageHelper {
	/*
	 * 根据尺寸图片居中裁剪
	 */
	 public static BufferedImage cutCenterImage(String src,int w,int h) throws IOException{ 
		 Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(getSuffix(src)); 
         ImageReader reader = iterator.next(); 
         InputStream in=new FileInputStream(src);
         ImageInputStream iis = ImageIO.createImageInputStream(in); 
         reader.setInput(iis, true); 
         ImageReadParam param = reader.getDefaultReadParam(); 
         int imageIndex = 0; 
         Rectangle rect = new Rectangle((reader.getWidth(imageIndex)-w)/2, (reader.getHeight(imageIndex)-h)/2, w, h);  
         param.setSourceRegion(rect); 
         BufferedImage bi = reader.read(0,param); 
         return bi;
  
	 }
	/*
	 * 图片裁剪二分之一
	 */
	 public static BufferedImage cutHalfImage(String src) throws IOException{ 
		 Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(getSuffix(src)); 
         ImageReader reader = iterator.next(); 
         InputStream in=new FileInputStream(src);
         ImageInputStream iis = ImageIO.createImageInputStream(in); 
         reader.setInput(iis, true); 
         ImageReadParam param = reader.getDefaultReadParam(); 
         int imageIndex = 0; 
         int width = reader.getWidth(imageIndex)/2; 
         int height = reader.getHeight(imageIndex)/2; 
         Rectangle rect = new Rectangle(width/2, height/2, width, height); 
         param.setSourceRegion(rect); 
         BufferedImage bi = reader.read(0,param);   
         return bi;
	 }
	/*
	 * 图片裁剪通用接口
	 */

    public static BufferedImage cutImage(String src,int x,int y,int w,int h) throws IOException{ 
           Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(getSuffix(src)); 
           ImageReader reader = iterator.next(); 
           InputStream in=new FileInputStream(src);
           ImageInputStream iis = ImageIO.createImageInputStream(in); 
           reader.setInput(iis, true); 
           ImageReadParam param = reader.getDefaultReadParam(); 
           Rectangle rect = new Rectangle(x, y, w,h);  
           param.setSourceRegion(rect); 
           BufferedImage bi = reader.read(0,param);   
           return bi;

    } 
    public static BufferedImage cutImage(String src,int x,int y) throws IOException{ 
    	Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(getSuffix(src)); 
    	ImageReader reader = iterator.next(); 
    	InputStream in=new FileInputStream(src);
    	ImageInputStream iis = ImageIO.createImageInputStream(in); 
    	reader.setInput(iis, true); 
    	ImageReadParam param = reader.getDefaultReadParam(); 
    	
    	ImageIcon ii = new ImageIcon(src);
		Image i = ii.getImage();
		int w = i.getWidth(null);
		int h = i.getHeight(null);
		if(w > h){
			w = h;
		}
    	Rectangle rect = new Rectangle(x, y, w,w);  
    	param.setSourceRegion(rect); 
    	BufferedImage bi = reader.read(0,param);   
    	return bi;           
    	
    } 
    
    /**
     * 缩放图片（较清晰）
     * @param src
     * @param dest
     * @param newWidth
     * @param quality
     * @throws IOException
     */
    public static BufferedImage resizeImage(String src,
			int newWidth, float quality) throws IOException {

		return resizeImage(ImageIO.read(new File(src)), newWidth, quality);
	}
    
    public static BufferedImage resizeImage(BufferedImage srcImg,
			int newWidth, float quality) throws IOException {

		if (quality > 1) {
			throw new IllegalArgumentException(
					"Quality has to be between 0 and 1");
		}

		Image resizedImage = null;

		int iWidth = srcImg.getWidth(null);
		int iHeight = srcImg.getHeight(null);

		if (iWidth > iHeight) {
			resizedImage = srcImg.getScaledInstance(newWidth, (newWidth * iHeight)
					/ iWidth, Image.SCALE_SMOOTH);
		} else {
			resizedImage = srcImg.getScaledInstance((newWidth * iWidth) / iHeight,
					newWidth, Image.SCALE_SMOOTH);
		}

		// This code ensures that all the pixels in the image are loaded.
		Image temp = new ImageIcon(resizedImage).getImage();

		// Create the buffered image.
		BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
				temp.getHeight(null), BufferedImage.TYPE_INT_RGB);

		// Copy image to buffered image.
		Graphics g = bufferedImage.createGraphics();

		// Clear background and paint the image.
		g.setColor(Color.white);
		g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
		g.drawImage(temp, 0, 0, null);
		g.dispose();

		// Soften.
		float softenFactor = 0.05f;
		float[] softenArray = { 0, softenFactor, 0, softenFactor,
				1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
		Kernel kernel = new Kernel(3, 3, softenArray);
		ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		bufferedImage = cOp.filter(bufferedImage, null);

		return bufferedImage;
	}
    
    public static BufferedImage addFrame(BufferedImage img, int frameWidth) {
    	int imgW = img.getWidth(null);
    	int imgH = img.getHeight(null);
    	BufferedImage image = new BufferedImage(imgW + frameWidth*2, imgH + frameWidth*2, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphic = image.createGraphics();
		graphic.setColor(Color.white);
		graphic.fillRect(0, 0, imgW + frameWidth*2, imgH + frameWidth*2);
		graphic.drawImage(img, frameWidth, frameWidth, imgW,imgH, Color.white, null);;
		graphic.dispose();
		return image;
    }
    
    public static void writeToFile(String dest, BufferedImage img) throws IOException {
    	writeToFile(img, getSuffix(dest), new File(dest));
    }
    
    public static void writeToFile(BufferedImage img, String format, File file) throws IOException {
		if (!ImageIO.write(img, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}
	}
    
    public static String getSuffix(String file) {
    	try{
    		return file.substring(file.lastIndexOf(".") + 1);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return "";
    }
}

