package com.colordetection;



import java.util.ArrayList;


import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import com.utils.ShowImage;

public class Red 
{
	
	public static void main(String args[])
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat image = Highgui.imread("images/result.jpg");
		
		Mat hsv = new Mat();
		hsv.create(image.size(), CvType.CV_8U);
		Imgproc.cvtColor(image, hsv, Imgproc.COLOR_RGB2HSV_FULL);
		
		
		ArrayList<Mat> planes = new ArrayList<Mat>();
		Core.split(hsv, planes);
		
	
		
		Mat normalHSV = new Mat();		
		Core.normalize(hsv, normalHSV, 255, 0, Core.NORM_MINMAX);
		
		
		
		
		Mat mu = new Mat();
		Imgproc.blur(normalHSV, mu, new Size(15,15));
		Core.absdiff(mu, normalHSV, mu);
		Core.pow(mu, 2.0, mu);
	
		
		ShowImage.showResult(mu);
		
		
		Mat finalMa = new Mat();
		//Imgproc.threshold(mu, finalMa, 255, 0, Imgproc.THRESH_TRUNC);
		Imgproc.cvtColor(mu, finalMa, Imgproc.COLOR_RGB2GRAY);
		Imgproc.GaussianBlur(finalMa, finalMa,new Size(5,5), 0);
		
		Imgproc.threshold(finalMa, finalMa, 255, 255, Imgproc.THRESH_OTSU);
		
		
		//Imgproc.morphologyEx(finalMa, finalMa, Imgproc.MORPH_OPEN, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3)));
		//Imgproc.morphologyEx(finalMa, finalMa, Imgproc.MORPH_CLOSE, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3)));
		
		Imgproc.morphologyEx(finalMa, finalMa, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3)));
		
		
		
		
		//Imgproc.morphologyEx(finalMa, finalMa, Imgproc.MORPH_CLOSE, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1,1)));
		Imgproc.morphologyEx(finalMa, finalMa, Imgproc.MORPH_OPEN, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(10,10)));
		Imgproc.morphologyEx(finalMa, finalMa, Imgproc.MORPH_CLOSE, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(10,10)));
				
		
		
		ShowImage.showResult(finalMa);
		
		
		hsv.release();
		image.release();
		normalHSV.release();
		finalMa.release();
		
		
	}

}

