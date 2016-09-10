package com.tec;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class VideoLoad
{
	public static void main(String args[])
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat frame = new Mat();
		VideoCapture camera = new VideoCapture();
		camera.open("src/soccer_video.avi");
		
		int i = 0;
		
		
		while(true)
		{
			if (camera.read(frame))
			{
				Highgui.imwrite("C:/Users/jonaranjo/OneDrive/TEC/ACS/INVESTIGACIONES/IMAGE PROCESSING/Frames/"+(i++)+".jpg", frame);
			}
			else
			{
				break;
			}
		}
		
	}
}
