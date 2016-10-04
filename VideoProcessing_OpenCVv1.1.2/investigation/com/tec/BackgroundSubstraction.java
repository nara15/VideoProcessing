package com.tec;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.BackgroundSubtractorMOG2;

public class BackgroundSubstraction
{
	public static void main(String args[])
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		VideoCapture capture = new VideoCapture("C:/Users/jonaranjo/OneDrive/TEC/ACS/PROYECTOS/IMAGE PROCESSING/Videos/soccer_video.avi");
		  Mat camImage = new Mat();
		  BackgroundSubtractorMOG2 backgroundSubtractorMOG=new BackgroundSubtractorMOG2();
		  int i = 0;
		    if (capture.isOpened())
		    {
		        while (true) 
		        {
		            capture.read(camImage);
		            Mat fgMask=new Mat();
		            backgroundSubtractorMOG.apply(camImage, fgMask,0.1);	
		            Mat output=new Mat();
		            camImage.copyTo(output,fgMask);		
		            //displayImageOnScreen(output);
		        	Highgui.imwrite("C:/Users/jonaranjo/OneDrive/TEC/ACS/PROYECTOS/IMAGE PROCESSING/Frames/"+(i++)+".jpg", output);
					
		        }
		    }
	}
	
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


}
