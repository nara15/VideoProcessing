package com.videoprocessing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class FieldDetection
{
	public static void saveFrames(String pPath)
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		VideoCapture video = new VideoCapture();
		
		FieldDetector field = new FieldDetector();
		
		Mat frame = new Mat();	
		Mat result = new Mat();
		int i = 0;
		
		video.open("src/soccer_video.avi");
		
		while (true)
		{
			if (video.read(frame))
			{
				result = field.doBackgroundRemoval(frame);
				
				Highgui.imwrite( pPath + (i++) + ".jpg", result);
			}
			else
			{
				break;
			}
		}
	}
	
	public static void main(String args[])
	{
		String homeDir = System.getProperty("user.home");
		
		String saveDir = homeDir + "/Documents/Frames/";
		
		Path path = Paths.get(saveDir);
		
		
            try 
            {
            	if ( ! Files.exists(path))
        		{
            		Files.createDirectories(path);
        		}
            	
            	saveFrames(path.toString());	
            } 
            catch (IOException e)
            {
                //fail to create directory
                e.printStackTrace();
            }
	}
}
