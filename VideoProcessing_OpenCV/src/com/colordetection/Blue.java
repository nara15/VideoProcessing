package com.colordetection;


import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import com.utils.ShowImage;

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
		
		
			
		
		//Color verde
		//Scalar lower_blue = new Scalar(40,0,0);
		Scalar lower_blue = new Scalar(40,20,20);
		Scalar upper_blue = new Scalar(80,255,255);
		
		Mat mask = new Mat();
		mask.create(image.size(), CvType.CV_8U);
		Core.inRange(planes.get(0), lower_blue, upper_blue, mask);
		
		
		Mat res = new Mat();
		Core.bitwise_and(image, image,res, mask);
		
		
		Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_OPEN, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(40,40)));
		Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_CLOSE, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(40,40)));
		
		
		ShowImage.showResult(mask);
		ShowImage.showResult(res);
		
		
		
		

		
				
		
		
		
		image.release();
		hsv.release();
		mask.release();
	//	hola.release();
		
		
		
	}

}
