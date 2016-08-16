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
		camera.open("C:/Users/Jose Mario/Videos/hola.3gp");
		int i = 0;
		
		while(true)
		{
			System.out.println(camera.isOpened());
			if (camera.read(frame))
			{
				Highgui.imwrite("C:/Users/Jose Mario/OneDrive/TEC/ASEGURAMIENTO DE LA CALIDAD DE SOFTWARE/PROYECTOS/IMAGE PROCESSING/Frames/"+(i++)+".jpg", frame);
			}
			else
			{
				break;
			}
		}
		
	}
}
