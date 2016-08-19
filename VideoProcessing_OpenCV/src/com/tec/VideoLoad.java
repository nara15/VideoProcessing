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
		//VideoCapture camera = new VideoCapture("C:/Users/Jose Mario/Videos/segTemporal1.avi");
		VideoCapture camera = new VideoCapture();
		System.out.println(camera.open("C:/Users/jonaranjo/OneDrive/TEC/ACS/PROYECTOS/IMAGE PROCESSING/Videos/soccer_video.avi"));
		int i = 0;
/*		
		while(true)
		{
			if (camera.read(frame))
			{
				Highgui.imwrite("C:/Users/Jose Mario/OneDrive/TEC/ASEGURAMIENTO DE LA CALIDAD DE SOFTWARE/PROYECTOS/IMAGE PROCESSING/Frames/"+(i++)+".jpg", frame);
			}
			else
			{
				break;
			}
		}
*/		
	}
}
