package com.videoprocessing;

import org.opencv.core.Core;
import org.opencv.highgui.VideoCapture;

public class FieldDetection
{
	public static void main(String args[])
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		VideoCapture video = new VideoCapture();
		
		video.open("C:/Users/jonaranjo/OneDrive/TEC/ACS/PROYECTOS/IMAGE PROCESSING/Videos/soccer_video.avi");
		
		System.out.println(video.isOpened());
		
		FieldDetector field = new FieldDetector();
	}
}
