package com.colordetection;


import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Blue 
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
		
		//Bounds to detect green color
		Scalar lower_green = new Scalar(20,100,100);
		Scalar upper_green = new Scalar(85,255,255);
		//Detect mask with green colors
		Mat mask = new Mat();
		Core.inRange(planes.get(0), lower_green, upper_green, mask);
				
		//File holes from mask
		Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_OPEN, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(40,40)));
		Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_CLOSE, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(40,40)));
		
		Highgui.imwrite("images/field_detected.jpg", mask);

		image.release();
		hsv.release();
		mask.release();

			
	}

}
