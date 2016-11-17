package com.llenarhuecos;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


public class LlenarHuecos_Cancha
{
	private static String _path = "C:/Users/jonaranjo/Videos/frames/";
	private static String _file = "1.jpg";
	
	public static void main(String[] args)
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat image = Highgui.imread(_path + _file);
		
		Mat image1 = image.clone();
		// Convert to HSV colorspace image
		Imgproc.cvtColor(image1, image1, Imgproc.COLOR_BGR2HSV_FULL);
		ArrayList<Mat> canales = new ArrayList<>();
		Core.split(image1, canales);
		
		// Rangos para detectar el color verde
		// (20,100,100) hasta (120,255,255)
		Scalar verde_inferior = new Scalar(20,130,130);
		Scalar verde_superior = new Scalar(120,255,255);
		
		// Detectar la máscara con los rangos de verdor
		Mat mascaraCancha = new Mat();
		Core.inRange(canales.get(0), verde_inferior, verde_superior, mascaraCancha);
		

		
		
		Imgproc.erode(mascaraCancha, mascaraCancha, new Mat(), new Point(-1, -1), 20);
		Imgproc.dilate(mascaraCancha, mascaraCancha, new Mat(), new Point(-1, -1), 40);
		
		
	
		
		
        
		
	
		com.utils.ShowImage.showResult(image);
		com.utils.ShowImage.showResult(mascaraCancha);
		
		
		
		
		
		
		
		
	}

}
