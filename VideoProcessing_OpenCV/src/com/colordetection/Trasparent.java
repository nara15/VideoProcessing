package com.colordetection;

import java.util.ArrayList;


import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import com.utils.Average;
import com.utils.ShowImage;

public class Trasparent 
{
	
	public static void main(String args[])
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat image = Highgui.imread("images/result.jpg");
		
		Mat hsv = new Mat();
		hsv.create(image.size(), CvType.CV_8U);
		Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV);
		
		
		ArrayList<Mat> planes = new ArrayList<Mat>();
		Core.split(hsv, planes);
		
		
		double threshValue = Average.getHistAverage(hsv, planes.get(0));
		Mat thresholdImage = new Mat();
		Imgproc.threshold(planes.get(0), thresholdImage, threshValue, 179.0, Imgproc.THRESH_BINARY_INV);
		Imgproc.blur(thresholdImage, thresholdImage, new Size(5, 5));
		Imgproc.dilate(thresholdImage, thresholdImage, new Mat(), new Point(-1, -1), 1);
		Imgproc.erode(thresholdImage, thresholdImage, new Mat(), new Point(-1, -1), 3);
		
		Imgproc.threshold(thresholdImage, thresholdImage, threshValue, 180, Imgproc.THRESH_BINARY);
		ShowImage.showResult(thresholdImage);
		thresholdImage.release();
		
	
		image.release();
		hsv.release();
		
		
	}

}
