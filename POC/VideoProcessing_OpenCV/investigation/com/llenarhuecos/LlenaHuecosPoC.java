package com.llenarhuecos;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class LlenaHuecosPoC 
{
	
	public static void main(String[] args)
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		//Read the image
		Mat image = Highgui.imread("images/nickle.jpg");
		ArrayList<Mat> canales = new ArrayList<>();
		Core.split(image, canales);
	
		//Threshold the input image to obtain binary image
		Mat im_th = new Mat();
		Imgproc.threshold(canales.get(0), im_th, 200, 255, Imgproc.THRESH_BINARY_INV);
		
		// Floodfill from pixel (0,0
		Mat im_floodfill = im_th.clone();
		Imgproc.floodFill(im_floodfill, new Mat(), new Point(0,0), new Scalar(255));
		
		// Invert floodfilled image
		Mat im_floodfill_inv = new Mat();
		Core.bitwise_not(im_floodfill, im_floodfill_inv);
		
		// Combine the two images to get the foreground.
		Mat out = new Mat();
		Core.bitwise_or(im_th, im_floodfill_inv, out);
		
		com.utils.ShowImage.showResult(out);
	}

}
