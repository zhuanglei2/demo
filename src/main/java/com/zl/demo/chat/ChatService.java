package com.zl.demo.chat;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/11/6 15:38
 */
public interface ChatService {

    /**
     *
     * @param appId
     * @param appSecret
     * @return
     */
    String getToken(String appId,String appSecret);

    /**
     * 生成二维码
     * @param token
     */
    void create(String token);

    /**
     * 通过ticket换取二维码
     * @param ticket
     */
    void getImg(String ticket);

    /**
     * 自定义生成二维码
     * @param url
     */
    void createQrImg(String url);
}
