package com.colordetection;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import com.utils.ShowImage;

public class Green 
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
		
		//Image normalization
		Mat normalHSV = new Mat();		
		Core.normalize(planes.get(0), normalHSV, 255, 0, Core.NORM_MINMAX);
		
		//Local variance from image
		Mat mu = new Mat();
		Imgproc.blur(normalHSV, mu, new Size(7,7));
		Core.absdiff(mu, normalHSV, mu);
		Core.pow(mu, 2.0, mu);
		
		Mat finalMa = new Mat();
		Imgproc.threshold(mu, finalMa, 255, 0, Imgproc.THRESH_TOZERO_INV);
	
		
		ShowImage.showResult(mu);
		
		
		hsv.release();
		image.release();
		normalHSV.release();
		finalMa.release();
		
		
	}

}

