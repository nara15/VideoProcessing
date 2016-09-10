package com.utils;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgproc.Imgproc;

public class Average
{
	public static double getHistAverage(Mat pHsvImage, Mat pHueValues)
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
