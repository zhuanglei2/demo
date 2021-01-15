package com.zl.demo.chat.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zl.demo.chat.ChatService;
import com.zl.demo.common.util.QRCodeUtil;
import com.zl.demo.http.HttpClientUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/11/6 15:39
 */
@Service
@Slf4j
public class ChatServiceImpl implements ChatService {


    /**
     * 获取微信token
     * https请求方式: GET https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
     * @param appId
     * @param appSecret
     * @return
     */
    @Override
    public String getToken(String appId, String appSecret) {
        Map<String,Object> map = new HashMap<>();
        map.put("grant_type","client_credential");
        map.put("appid",appId);
        map.put("secret",appSecret);
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        final String response = HttpClientUtils.get(url, map, "UTF-8");
        final JSONObject jsonObject = JSONObject.parseObject(response);
        return (String) jsonObject.get("access_token");
    }

    @Override
    public void create(String token) {
//        https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+token;

        ChatInfo  chatInfo = new ChatInfo();
        ActionInfo actionInfo = new ActionInfo();
        Scene scene = new Scene();
        scene.setScene_id(123);
        actionInfo.setScene(scene);
        chatInfo.setExpire_seconds(2592000);
        chatInfo.setAction_name("QR_SCENE");
        chatInfo.setAction_info(actionInfo);
        System.out.println(JSON.toJSONString(chatInfo));
        try {
            log.info(HttpClientUtils.post(url, JSON.toJSONString(chatInfo)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getImg(String ticket) {
        FileOutputStream out = null;
        URL url = null;
        try {
            String qrUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
            url = new URL(qrUrl );
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\Shanzhen\\Desktop\\1.jpg"));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void createQrImg(String url) {
        try {
            QRCodeUtil.encode(url,"C:\\Users\\Shanzhen\\Desktop\\2.png","C:\\Users\\Shanzhen\\Desktop",true,"result");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Data
    public static class ChatInfo{
        private Integer expire_seconds;
        private String action_name;
        private ActionInfo action_info;
    }

    @Data
    public static class ActionInfo{
        private Scene scene;
    }

    @Data
    public static class Scene{
        private Integer scene_id;
    }
}
