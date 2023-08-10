package team.star.healthcodesystem.util;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码生成工具类
 *
 * @author Patrick_Star
 * @version 1.1
 */
public class QRCode {
    static final String PATH_PREFIX = "/qrcode/";
    static final String CHARSET = "UTF-8";
    static final int WIDTH = 400;
    static final int HEIGHT = 400;
    private static final MultiFormatWriter MUTI_WRITER = new MultiFormatWriter();

    /**
     * 生成一个保存所需数据的二维码图片，并保存到服务器本地
     *
     * @param content 需要被保存的数据
     * @param path    二维码的名称
     */
    public static void createQR(String content, String path, Color color) {
        try {
            ImageIO.write(genBarcode(content, color), "png", new File(PATH_PREFIX + path));
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到BufferedImage
     *
     * @param content 二维码显示的文本
     */
    public static BufferedImage genBarcode(String content, Color color)
            throws WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<>(2);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        BitMatrix matrix = MUTI_WRITER.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
        int[] pixels = new int[WIDTH * HEIGHT];
        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                int colorInt = color.getRGB();
                pixels[y * WIDTH + x] = matrix.get(x, y) ? colorInt : 0xffffff;
            }
        }
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        image.getRaster().setDataElements(0, 0, WIDTH, HEIGHT, pixels);
        return image;
    }
}
