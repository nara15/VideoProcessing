package model;

import org.opencv.core.Core;

public class TestOpenCV
{
	public static void run()
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		System.out.println("Hola, se acaba de inicar OpenCV");
	}
}
