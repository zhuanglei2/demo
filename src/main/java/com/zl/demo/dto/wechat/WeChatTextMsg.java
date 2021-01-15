package com.zl.demo.dto.wechat;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/11/10 9:13
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class WeChatTextMsg extends WeChatBaseMsg {

    @XmlElement(name="Content")
    private String content;
}
