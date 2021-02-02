package com.zl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/2/1 14:48
 */
@ConfigurationProperties(prefix = "jwt.config")
@Data
public class JwtConfig {

    private String key = "zhuangl";

    /**
     * jwt 过期时间，默认值：600000 {@code 10 分钟}.
     */
    private Long ttl = 600000L;

    /**
     * 开启 记住我 之后 jwt 过期时间，默认值 604800000 {@code 7 天}
     */
    private Long remember = 604800000L;
}
