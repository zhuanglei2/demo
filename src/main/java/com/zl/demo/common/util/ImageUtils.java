package com.zl.demo.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/9/2 9:14
 */
@Slf4j
public class ImageUtils {

    public static void cutByCenter(String srcpath,String subpath) throws IOException {//裁剪方法
//        FileInputStream is = null;
//        ImageInputStream iis = null;
//        try {
//            is = new FileInputStream(srcpath); //读取原始图片
//            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg"); //ImageReader声称能够解码指定格式
//            ImageReader reader = it.next();
//            iis = ImageIO.createImageInputStream(is); //获取图片流
//            reader.setInput(iis, true); //将iis标记为true（只向前搜索）意味着包含在输入源中的图像将只按顺序读取
//            ImageReadParam param = reader.getDefaultReadParam(); //指定如何在输入时从 Java Image I/O框架的上下文中的流转换一幅图像或一组图像
//            BufferedImage src = javax.imageio.ImageIO.read(is);
//            Rectangle rect = new Rectangle(src.getWidth()/2, src.getHeight()/2, src.getWidth(), src.getHeight()); //定义空间中的一个区域
//            param.setSourceRegion(rect); //提供一个 BufferedImage，将其用作解码像素数据的目标。
//            BufferedImage bi = reader.read(0, param); //读取索引imageIndex指定的对象
//            ImageIO.write(bi, getFileSuffix(srcpath), new File(subpath)); //保存新图片
//        } finally {
//            if (is != null)
//                is.close();
//            if (iis != null)
//                iis.close();
//        }
    }



    /**
     * 矩形裁剪，设定起始位置，裁剪宽度，裁剪长度
     * 裁剪范围需小于等于图像范围
     * @param image   文件流
     * @param xLength       图片x轴长度
     * @param yLength       图片y轴高度
     * @return
     */
    public static BufferedImage imageCutByRectangle(BufferedImage image, int xLength,
                                                    int yLength) {
        //裁剪的x起始位置
        int xCoordinate = 0;
        //裁剪的y起始位置
        int yCoordinate = 0;
        int width = image.getWidth();
        int height = image.getHeight();
        if(width>xLength){
            xCoordinate = (width-xLength)/2;
        }else {
            xLength = width;
        }
        if(height>yLength){
            yCoordinate = (height-yLength)/2;
        }else {
            yLength = height;
        }
        if(xCoordinate==0&&yCoordinate==0){
            log.info("图片小于尺寸x:{},y:{}不需要裁剪",width,height);
            return image;
        }
        log.info("起始点位置x:{},y:{}",xCoordinate,yCoordinate);
        BufferedImage resultImage = new BufferedImage(xLength, yLength, image.getType());
        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {
                int rgb = image.getRGB(x + xCoordinate, y + yCoordinate);
                resultImage.setRGB(x, y, rgb);
            }
        }
        return resultImage;
    }


    private static String getFileSuffix(final String path) throws IOException {
        String result = "";
        String hex="";
        if (path != null) {
            File image=new File(path);
            InputStream is = new FileInputStream(image);
            byte[] bt = new byte[2];
            is.read(bt);
            log.info(bt+"\n"+bytesToHexString(bt));
            hex=bytesToHexString(bt);
            is.close();
            if(hex.equals("ffd8")){
                result="jpg";
            }else if(hex.equals("4749")){
                result="gif";
            }else if(hex.equals("8950")){
                result="png";
            }
        }

        return result;
    }
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 实现图像的等比缩放
     * @param source
     * @param targetW
     * @param targetH
     * @return
     */
    private static BufferedImage resize(BufferedImage source, int targetW,
                                        int targetH) {
        // targetW，targetH分别表示目标长和宽
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        // 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
        // 则将下面的if else语句注释即可
        if (sx < sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) { // handmade
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW,
                    targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else
            target = new BufferedImage(targetW, targetH, type);
        Graphics2D g = target.createGraphics();
        // smoother than exlax:
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    /**
     * 实现图像的等比缩放和缩放后的截取
     * @param inFilePath 要截取文件的路径
     * @param outFilePath 截取后输出的路径
     * @param width 要截取宽度
     * @param hight 要截取的高度
     * @param proportion
     * @throws Exception
     */

    public static void saveImageAsJpg(String inFilePath, String outFilePath,
                                      int width, int hight, boolean proportion)throws Exception {
        File file = new File(inFilePath);
        InputStream in = new FileInputStream(file);
        File saveFile = new File(outFilePath);

        BufferedImage srcImage = ImageIO.read(in);
        if (width > 0 || hight > 0) {
            // 原图的大小
            int sw = srcImage.getWidth();
            int sh = srcImage.getHeight();
            // 如果原图像的大小小于要缩放的图像大小，直接将要缩放的图像复制过去
            if (sw > width && sh > hight) {
                srcImage = resize(srcImage, width, hight);
            } else {
                String fileName = saveFile.getName();
                String formatName = fileName.substring(fileName
                        .lastIndexOf('.') + 1);
                ImageIO.write(srcImage, formatName, saveFile);
                return;
            }
        }
        // 缩放后的图像的宽和高
        int w = srcImage.getWidth();
        int h = srcImage.getHeight();
        // 如果缩放后的图像和要求的图像宽度一样，就对缩放的图像的高度进行截取
        if (w == width) {
            // 计算X轴坐标
            int x = 0;
            int y = h / 2 - hight / 2;
            saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);
        }
        // 否则如果是缩放后的图像的高度和要求的图像高度一样，就对缩放后的图像的宽度进行截取
        else if (h == hight) {
            // 计算X轴坐标
            int x = w / 2 - width / 2;
            int y = 0;
            saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);
        }
        in.close();
    }
    /**
     * 实现缩放后的截图
     * @param image 缩放后的图像
     * @param subImageBounds 要截取的子图的范围
     * @param subImageFile 要保存的文件
     * @throws IOException
     */
    private static void saveSubImage(BufferedImage image,
                                     Rectangle subImageBounds, File subImageFile) throws IOException {
        if (subImageBounds.x < 0 || subImageBounds.y < 0
                || subImageBounds.width - subImageBounds.x > image.getWidth()
                || subImageBounds.height - subImageBounds.y > image.getHeight()) {
            System.out.println("Bad   subimage   bounds");
            return;
        }
        BufferedImage subImage = image.getSubimage(subImageBounds.x,subImageBounds.y, subImageBounds.width, subImageBounds.height);
        String fileName = subImageFile.getName();
        String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
        ImageIO.write(subImage, formatName, subImageFile);
    }

}
