package com.zl.demo.dto.wechat;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/11/9 21:00
 */
@Data
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class WeChatBaseMsg {
    /**
     * 开发者微信号
     */
    @XmlElement(name="ToUserName")
    protected String toUserName;
    /**
     * 发送方帐号（一个OpenID）
     */
    @XmlElement(name="FromUserName")
    protected String fromUserName;
    /**
     * 消息创建时间 （整型）
     */
    @XmlElement(name="CreateTime")
    protected String createTime;
    /**
     * 消息类型
     */
    @XmlElement(name="MsgType")
    protected String msgType;
    /**
     * 消息id，64位整型
     */
    protected String msgId;
}
