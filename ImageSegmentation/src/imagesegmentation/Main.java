
package imagesegmentation;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

/**
 *
 * @author José Mario Naranjo Leiva
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        Mat image = Highgui.imread("G:\\git\\VideoProcessing\\ImageSegmentation\\src\\images\\result.jpg");
        
        ISegmentor s = new SimpleImageSegmentor();
        
       
        utils.ShowImage.showResult(s.doBackgroundRemoval(image));
    }
    
}
