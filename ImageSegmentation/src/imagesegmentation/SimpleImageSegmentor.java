
package imagesegmentation;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author José Mario Naranjo Leiva
 */
public class SimpleImageSegmentor implements ISegmentor {

    public SimpleImageSegmentor() {}

    
    @Override
    public Mat doBackgroundRemoval(Mat pFrame) 
    {
        // initializes the image with HSV color 
        Mat hsvImage = new Mat();
        List<Mat> hsvPlanes = new ArrayList<>();
        Mat thresholdImg = new Mat();
        
        //Convert input image to HSV color
        hsvImage.create(pFrame.size(), CvType.CV_8U);
        Imgproc.cvtColor(pFrame, hsvImage, Imgproc.COLOR_BGR2HSV);
        Core.split(hsvImage, hsvPlanes);
        
        int thresh_type = Imgproc.THRESH_BINARY_INV;
        
        //get the average hue value of image
        double threshValue = this.getHistAverage(hsvImage, hsvPlanes.get(0));
        
        Imgproc.threshold(hsvPlanes.get(0), thresholdImg, threshValue, 179.0, thresh_type);
       
        Imgproc.blur(thresholdImg, thresholdImg, new Size(5,5));
        
        //Dilate to fill gaps, erode to smooth edges
        Imgproc.dilate(thresholdImg, thresholdImg, new Mat(), new Point(-1,-1),1);
        Imgproc.erode(thresholdImg, thresholdImg, new Mat(), new Point(-1,-1),3);
        
        Imgproc.threshold(thresholdImg, thresholdImg, threshValue, 179.0, Imgproc.THRESH_BINARY);
         
        //create the new image
        Mat foreground = new Mat(pFrame.size(), CvType.CV_8UC3, new Scalar(255,255,255));
        pFrame.copyTo(foreground, thresholdImg);
        
        return foreground;
    }
    
    
    /**
	 * Get the average hue value of the image starting from its Hue channel
	 * histogram
	 * 
	 * @param hsvImg
	 *            the current frame in HSV
	 * @param hueValues
	 *            the Hue component of the current frame
	 * @return the average Hue value
	 */
    private double getHistAverage(Mat hsvImg, Mat hueValues)
    {
            // init
            double average = 0.0;
            Mat hist_hue = new Mat();
            // 0-180: range of Hue values
            MatOfInt histSize = new MatOfInt(180);
            List<Mat> hue = new ArrayList<>();
            hue.add(hueValues);

            // compute the histogram
            Imgproc.calcHist(hue, new MatOfInt(0), new Mat(), hist_hue, histSize, new MatOfFloat(0, 179));

            // get the average Hue value of the image
            // (sum(bin(h)*h))/(image-height*image-width)
            // -----------------
            // equivalent to get the hue of each pixel in the image, add them, and
            // divide for the image size (height and width)
            for (int h = 0; h < 180; h++)
            {
                    // for each bin, get its value and multiply it for the corresponding
                    // hue
                    average += (hist_hue.get(h, 0)[0] * h);
            }

            // return the average hue of the image
            return average = average / hsvImg.size().height / hsvImg.size().width;
    }
    
}
