
package imagesegmentation;

import org.opencv.core.Mat;

/**
 *
 * @author José Mario Naranjo Leiva
 */
public interface ISegmentor {
    
    public Mat doBackgroundRemoval(Mat pFrame);
}
