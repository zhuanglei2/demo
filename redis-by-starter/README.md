1.apollo配置需要一致
例如
biz-gi的redis配置的application域为orgalpha.redis
biz-search的redis配置的application域为application.yml
因此在配置上首先要做到统一,否则无法使用统一的二分包

2.apollo配置,properties中的配置，注意以下条件
@EnableApolloConfig("apollo的域")
@ConfigurationProperties(prefix="xxx")
@Value("${全路径}")

3.在resources下需增加META-INF文件夹,添加spring.factories文件
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
configuration的全路径 例如  com.wayyue.springboot.starter.configuration.RedisConfiguration
