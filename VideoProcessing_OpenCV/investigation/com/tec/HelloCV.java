package com.tec;

import org.opencv.core.Core;

public class HelloCV 
{
	public static void main(String[] args)
	{
		System.out.println("Hola OpenCV");
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
	}
}
