package com.videoprocessing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import com.utils.ShowImage;

public class FieldDetection
{
	public static void saveFrames(String pPath)
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			
		FieldDetector field = new FieldDetector();
		
		Mat frame = Highgui.imread("images/result.jpg");
		
		ShowImage.showResult(field.doBackgroundRemoval(frame));

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
