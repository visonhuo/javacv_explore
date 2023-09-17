package com.vison.javacv.tools.image;

import org.bytedeco.javacv.*;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.Mat;

import javax.swing.*;

/**
 * 图片相关工具类
 */
public class ImageUtils {

    /**
     * 创建窗口展示图片
     */
    public static void display(String imgPath, String caption) throws InterruptedException {
        try (Mat image = opencv_imgcodecs.imread(imgPath);
             OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat()
        ) {
            CanvasFrame canvas = new CanvasFrame(caption, 1);
            canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            canvas.showImage(converter.convert(image));
            canvas.waitKey();   // 等待键盘输入信号关闭窗口
        }
    }

    /**
     * 创建窗口展示动图(如 gif 图片, 目前 ffmpeg 尚不支持 webp 动图解码)
     */
    public static void displayAnimation(String animImgPath, String caption) throws FrameGrabber.Exception, InterruptedException {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(animImgPath)) {
            grabber.start();

            CanvasFrame canvas = new CanvasFrame(caption, 1);
            canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            // 基于帧率计算每一帧之间的停顿时长
            canvas.setLatency((long) (1000 / grabber.getFrameRate()));

            Frame frame;
            while ((frame = grabber.grabImage()) != null) {
                canvas.showImage(frame);
                canvas.waitLatency();
            }
            canvas.waitKey();
        }
    }
}
