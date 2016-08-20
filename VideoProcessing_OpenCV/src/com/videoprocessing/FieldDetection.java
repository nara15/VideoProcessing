package com.videoprocessing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class FieldDetection
{
	public static void main(String args[])
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		VideoCapture video = new VideoCapture();
		
		FieldDetector field = new FieldDetector();
		
		Mat frame = new Mat();	
		Mat result = new Mat();
		int i = 0;
		
		video.open("C:/Users/jonaranjo/OneDrive/TEC/ACS/PROYECTOS/IMAGE PROCESSING/Videos/soccer_video.avi");
		
		
		while (true)
		{
			if (video.read(frame))
			{
				result = field.doBackgroundRemoval(frame);
				Highgui.imwrite("C:/Users/jonaranjo/OneDrive/TEC/ACS/PROYECTOS/IMAGE PROCESSING/Frames/"+(i++)+".jpg", result);
			}
			else
			{
				break;
			}
		}
	}
}
