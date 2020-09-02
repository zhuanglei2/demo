package com.zl.demo.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
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
        }
        if(height>yLength){
            yCoordinate = (height-yLength)/2;
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
}
