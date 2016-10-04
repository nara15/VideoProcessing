package com.colordetection;


import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class ReconocimientoJugadores 
{
	
	public static void main(String args[])
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat image = Highgui.imread("images/result.jpg");
		Mat cancha = Highgui.imread("images/field_detected.jpg");
		
		Mat hsv = new Mat();
		hsv.create(image.size(), CvType.CV_8U);
		Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV);
		
		ArrayList<Mat> planes = new ArrayList<Mat>();
		Core.split(hsv, planes);
		
		//Image normalization
		Mat normalHSV = new Mat();		
		Core.normalize(planes.get(0), normalHSV, 255, 0, Core.NORM_MINMAX);
		
		//Local variance from image
		Mat mu = new Mat();
		Imgproc.blur(normalHSV, mu, new Size(5,5));
		Core.absdiff(mu, normalHSV, mu);
		Core.pow(mu, 2.0, mu);
		
		
		Core.bitwise_not(mu, mu);
		 Mat jugares= new Mat(mu.rows(),mu.cols(),mu.type()),hier = new Mat(mu.rows(),mu.cols(),mu.type());
		 //mu.copyTo(hier);
		 //Imgproc.cvtColor(hier, hier, Imgproc.COLOR_BGR2GRAY);
		 //Vector<Vec4i> hierarchy;
		 List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		 Imgproc.findContours(mu, contours, new Mat(),Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
		 for(int i =0;i<contours.size(); i++){
			 //System.out.println(contours.get(i).size().height);
			 if((contours.get(i).size().height<310))
				 Imgproc.drawContours(mu, contours, i, new Scalar( 255, 255, 255 ),  9 );
			 
		 }
		 Core.bitwise_not(mu, mu);
		 Core.bitwise_not(cancha, cancha);
		// for (int i = 0; i < mu.rows(); i++) {
		  //      for (int j = 0; j < mu.cols(); j++) {
		  //          hier.put(i, j, mu.get(i, j));
		  //      }
		  //  }
		    for (int i = 0; i < cancha.rows(); i++) {
		        for (int j = 0; j < cancha.cols(); j++) {
		        	//System.out.println(cancha.get(i, j)[0]);
		            if(cancha.get(i, j)[0]==0.0){
		            	hier.put(i, j, mu.get(i, j));
		            	
		            }
		            else{
		            	
		            	hier.put(i, j, cancha.get(i, j));
		            }
		        	
		        }
		    }
		 
		  //Imgproc.threshold(hier, hier, 0, 255, Imgproc.THRESH_OTSU);
		//----  Imgproc.erode(hier, hier, new Mat());
		    
		    //---------adelgazar lineas
		  Imgproc.dilate(hier, hier, new Mat(), new Point(0, 0), 3);
		  
		  
		  for (int i = 0; i < hier.rows(); i++) {
		        for (int j = 0; j < hier.cols(); j++) {
		        	//System.out.println(cancha.get(i, j)[0]);
		            if(hier.get(i, j)[0]==0.0){
		            	jugares.put(i, j, image.get(i, j));
		            	
		            }
		            else{
		            	
		            	jugares.put(i, j, hier.get(i, j));
		            }
		        	
		        }
		    }
		  
		  //Imgproc.cvtColor(jugares, jugares, Imgproc.COLOR_HSV2RGB);
		  
		  ///-----------
		 //Core.fillPoly(mu, contours, new Scalar(255, 0, 0));
		 //Imgproc.drawContours(mu, contours, -1, new Scalar(255, 0, 0));
		// Imgproc.drawContours(hier, contours, -1, new Scalar(255, 0, 0), 2);
		 //Imgproc.cvtColor(hier, hier, Imgproc.COLOR_GRAY2BGR);
		 
		  
		//*****Fill holes
		 
		 
		/*Mat fill= new Mat(),contrno= new Mat(), hier = new Mat();
		Core.bitwise_not(mu, fill);	
		//Imgproc.findContours(fill, null, contrno, 0, 0);
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		//Imgproc.findContours(fill,contours , contrno,Imgproc.RETR_LIST/*CV_RETR_EXTERNAL ,Imgproc.CHAIN_APPROX_SIMPLE/*CV_CHAIN_APPROX_SIMPLE );
		//Imgproc.drawContours(hier, contours, -1, new Scalar(0,0,255));
		/*System.out.println(contours.size());
		
		for(int i=0; i< contours.size();i++){
			
			//Imgproc.drawContours(hier,contours.get(i),0,255,-1);
	       // System.out.println(Imgproc.contourArea(contours.get(i)));
	        if (Imgproc.contourArea(contours.get(i)) > 50 ){
	            Rect rect = Imgproc.boundingRect(contours.get(i));
	            //System.out.println(rect.height);
	            if (rect.height > 28){
	            //System.out.println(rect.x +","+rect.y+","+rect.height+","+rect.width);
	            //Core.rectangle(hier, new Point(rect.x,rect.height), new Point(rect.y,rect.width),new Scalar(0,0,255));
	            	Core.cir(hier, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar(0,0,255));
	            }
	        }
	    }
		
		*/
		
		
		//*********
		
		
		
		
		//ShowImage.showResult(hier);
		Highgui.imwrite("images/rellena.jpg", mu);
		Highgui.imwrite("images/and.jpg", hier);
		Highgui.imwrite("images/jugadores.jpg", jugares);
		hsv.release();
		image.release();
		normalHSV.release();
		mu.release();
		hier.release();
		
	}

}

