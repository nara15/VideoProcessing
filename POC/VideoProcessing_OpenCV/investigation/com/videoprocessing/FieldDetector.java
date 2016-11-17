package com.videoprocessing;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class FieldDetector
{
	private Mat _hsvImage;
	
	public FieldDetector()
	{
		_hsvImage = new Mat();
	}
	
	public Mat doBackgroundRemoval(Mat pFrame)
	{
		List<Mat> hsvChannels = new ArrayList<>();
		Mat thresholdImage = new Mat();
		
		// Apply threshold to the image with the average hue value
		_hsvImage.create(pFrame.size(), CvType.CV_8U);
		
		// 1. Convert image from RGB to HSV
		Imgproc.cvtColor(pFrame, _hsvImage, Imgproc.COLOR_BGR2HSV);
		Core.split(_hsvImage, hsvChannels);
		
		// get the average hue value of the image
		double threshValue = this.getHistAverage(_hsvImage, hsvChannels.get(0));
		
		Imgproc.threshold(hsvChannels.get(0), thresholdImage, threshValue, 179.0, Imgproc.THRESH_BINARY_INV);
		
		Imgproc.blur(thresholdImage, thresholdImage, new Size(5, 5));
		
		// (2) dilate to fill gaps, erode to smooth edges
		Imgproc.dilate(thresholdImage, thresholdImage, new Mat(), new Point(-1, -1), 1);
		Imgproc.erode(thresholdImage, thresholdImage, new Mat(), new Point(-1, -1), 3);
		
		Imgproc.threshold(thresholdImage, thresholdImage, threshValue, 180, Imgproc.THRESH_BINARY);
		
		// create the new image
		Mat foreground = new Mat(pFrame.size(), CvType.CV_8UC3, new Scalar(255, 255, 255));
		pFrame.copyTo(foreground, thresholdImage);
		
		return foreground;
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
	
}
