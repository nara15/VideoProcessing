package com.colordetection;



import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
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
		Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV);
		
		
		ArrayList<Mat> planes = new ArrayList<Mat>();
		Core.split(hsv, planes);
		
		
		Mat normalHSV = new Mat();		
		Core.normalize(planes.get(1), normalHSV, 255, 0, Core.NORM_MINMAX);
		

		
		Mat mu = new Mat();
		Imgproc.blur(normalHSV, mu, new Size(5,5));
		
		Core.absdiff(mu, normalHSV, mu);
		
		Core.pow(mu, 2.0, mu);
		
		Mat finalMa = new Mat();
		Imgproc.threshold(mu, finalMa, 255, 0, Imgproc.THRESH_TOZERO_INV);
		
		
		ShowImage.showResult(finalMa);
		
		
		hsv.release();
		image.release();
		normalHSV.release();
		finalMa.release();
		
		
	}

}

