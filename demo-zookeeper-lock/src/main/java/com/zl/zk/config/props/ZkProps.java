package com.zl.zk.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/9/11 11:43
 */
@Data
@ConfigurationProperties(prefix = "zk")
public class ZkProps {

    /**
     * 连接地址
     */
    private String url;

    /**
     * 超时时间(毫秒) ,默认1000
     */
    private int timeout = 1000;

    /**
     * 重试次数
     */
    private int retry = 3;
}
