package com.vison.javacv.tools.image;

import lombok.Data;

import java.awt.image.BufferedImage;

/**
 * GIF图片每帧信息
 */
@Data
public class GIFImageFrame {

    // 帧图像信息
    private BufferedImage image;

    // 相比上一帧的延迟毫秒数
    private long delayTimeMS;
}
