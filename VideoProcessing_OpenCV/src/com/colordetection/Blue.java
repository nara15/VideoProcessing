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

public class Blue 
{
	public static void showResult(Mat img) {
	    Imgproc.resize(img, img, new Size(640, 480));
	    MatOfByte matOfByte = new MatOfByte();
	    Highgui.imencode(".jpg", img, matOfByte);
	    byte[] byteArray = matOfByte.toArray();
	    BufferedImage bufImage = null;
	    try {
	        InputStream in = new ByteArrayInputStream(byteArray);
	        bufImage = ImageIO.read(in);
	        JFrame frame = new JFrame();
	        frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
	        frame.pack();
	        frame.setVisible(true);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public double getHistAverage(Mat pHsvImage, Mat pHueValues)
	{
		double average = 0.0;
		Mat hist_hue = new Mat();
		
		// 0 to 180: which is the range for Hue values
		MatOfInt histSize = new MatOfInt(180);
		List<Mat> hue = new ArrayList<>();
		hue.add(pHueValues);
		
		// Compute the histogram for the hue on the image
		Imgproc.calcHist(hue, new MatOfInt(0), new Mat(), hist_hue, histSize, new MatOfFloat(0,179));
		
		
		// Getting the average hue value from the image
		// It gets the hue of each pixel in the image, add them and
		// divide for the image size (height and width)
		for (int h = 0; h < 180; h ++)
		{
			average += (hist_hue.get(h, 0)[0] * h);
		}
		
		return average = average / pHsvImage.size().height / pHsvImage.size().width;
	}

	public static void main(String args[])
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		//Mat image = Highgui.imread("images/field5.png");
		
		Mat image = Highgui.imread("images/field2.jpg");
		
		Mat hsv = new Mat();
		hsv.create(image.size(), CvType.CV_8U);
		Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV);
		

		
		
		
		//Color verde
		Scalar lower_blue = new Scalar(40,0,0);
		Scalar upper_blue = new Scalar(80,255,255);
		
		Mat mask = new Mat();
		mask.create(image.size(), CvType.CV_8U);
		Core.inRange(hsv, lower_blue, upper_blue, mask);
		
		Mat res = new Mat();
		Core.bitwise_and(image, image,res, mask);
		
		
		
		
	
		
	/*	
		Mat hola = new Mat();
		hola.create(new Size(mask.width()+2,mask.height()+2), CvType.CV_8U);
		Imgproc.floodFill(mask, hola, new Point(0,0), new Scalar(0));
	*/	
		
		Imgproc.dilate(mask, mask, new Mat(), new Point(-1, -1), 1);
		Imgproc.erode(mask, mask, new Mat(), new Point(-1, -1), 2);
		
		
		Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_OPEN, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(40,40)));
		Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_CLOSE, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(40,40)));
		
		
		showResult(mask);
		showResult(res);
		
		

		
				
		
		
		
		image.release();
		hsv.release();
		mask.release();
	//	hola.release();
		
		
		
	}

}
