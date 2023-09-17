package com.vison.javacv.tools.image;

import org.bytedeco.javacv.FrameGrabber;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

class ImageUtilsTest {

    @Test
    void display() throws InterruptedException {
        String imgPath = this.getClass().getResource("/img.png").getPath();
        ImageUtils.display(imgPath, "Image");
    }

    @Test
    void displayAnimation() throws InterruptedException, FrameGrabber.Exception {
        String imgGifPath = this.getClass().getResource("/img.gif").getPath();
        ImageUtils.displayAnimation(imgGifPath, "Animation Image");
    }

    @Test
    public void testGIFLoader() throws IOException, InterruptedException {
        String gifImgPath = this.getClass().getResource("/img.gif").getPath();
        List<GIFImageFrame> gifImageFrames = GIFImageUtils.parse(new File(gifImgPath));
        System.out.println(gifImageFrames.toString());

//        ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
//        ImageInputStream stream = ImageIO.createImageInputStream(new File(gifImgPath));
//        reader.setInput(stream);
//
//        int count = reader.getNumImages(true);
//        System.out.printf("GIF frame count: %s%n", count);
//        for (int i = 0; i < count; i++) {
//            IIOMetadataNode root = (IIOMetadataNode) reader.getImageMetadata(i).getAsTree("javax_imageio_gif_image_1.0");
//            IIOMetadataNode gce = (IIOMetadataNode) root.getElementsByTagName("GraphicControlExtension").item(0);
//            for (int j = 0; j < gce.getAttributes().getLength(); j++) {
//                System.out.printf("GIF Frame[%s] metadata: %s%n", i, gce.getAttributes().item(j).getNodeName() + ":" + gce.getAttributes().item(j).getNodeValue());
//            }
//        }

//        BufferedImage bufferedImage = ImageIO.read(new File(gifImgPath));
//        CanvasFrame canvas = new CanvasFrame("Test", 1);
//        canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        canvas.showImage(bufferedImage);
//        canvas.waitKey();
    }
}