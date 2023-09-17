package com.vison.javacv.tools.image;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GIFImageUtils {

    /**
     * 解析每一帧的信息
     */
    @SneakyThrows
    public static List<GIFImageFrame> parse(File gifImgFile) {
        ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
        ImageInputStream stream = ImageIO.createImageInputStream(gifImgFile);
        reader.setInput(stream);

        int numImages = reader.getNumImages(true);
        List<GIFImageFrame> gifImageFrames = new ArrayList<>(numImages);
        for (int i = 0; i < numImages; i++) {
            BufferedImage image = reader.getRawImageType(i).createBufferedImage(reader.getWidth(i), reader.getHeight(i));
            IIOMetadataNode root = (IIOMetadataNode) reader.getImageMetadata(i).getAsTree("javax_imageio_gif_image_1.0");
            IIOMetadataNode gce = (IIOMetadataNode) root.getElementsByTagName("GraphicControlExtension").item(0);
            long delayTimeMS = Integer.parseInt(gce.getAttribute("delayTime")) * 10L;

            GIFImageFrame gifImageFrame = new GIFImageFrame();
            gifImageFrame.setImage(image);
            gifImageFrame.setDelayTimeMS(delayTimeMS);
            gifImageFrames.add(gifImageFrame);
        }
        return gifImageFrames;
    }
}
