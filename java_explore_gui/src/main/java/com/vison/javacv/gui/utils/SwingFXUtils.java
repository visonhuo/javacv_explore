package com.vison.javacv.gui.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;

import java.awt.image.BufferedImage;


public class SwingFXUtils {

    /**
     * 将 BufferedImage 转换成 JavaFx 中的 Image 对象
     */
    public static Image toFxImage(BufferedImage image) {
        if (image == null) {
            return null;
        }

        WritableImage wr = new WritableImage(image.getWidth(), image.getHeight());
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                pw.setArgb(x, y, image.getRGB(x, y));
            }
        }
        return new ImageView(wr).getImage();
    }

    /**
     * 将 OpenCV Mat 对象转换为 JavaFx 中的 Image 对象
     */
    public static Image toFxImage(Mat image) {
        try (OpenCVFrameConverter.ToMat matConverter = new OpenCVFrameConverter.ToMat();
             Java2DFrameConverter java2DConverter = new Java2DFrameConverter();
        ) {
            return toFxImage(java2DConverter.convert(matConverter.convert(image)));
        }
    }
}
