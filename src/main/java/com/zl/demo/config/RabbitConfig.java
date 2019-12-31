package com.zl.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    public static final String EXCHANGE_A = "szRabbitExchange";


    public static final String QUEUE_A = "sz.queues.openservice.wxinsure.order.notify";

    public static final String ROUTINGKEY_A = "sz.queues.openservice.wxinsure.order.notify.key";

    @Bean
    public ConnectionFactory  connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }


    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(@Qualifier("connectionFactory") ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }


    @Bean(name = "defaultExchange")
    public DirectExchange defaultExchange(){
        return new DirectExchange(EXCHANGE_A);
    }

    @Bean(name = "queueA")
    public Queue queueA(){
        return new Queue(QUEUE_A,true);
    }


    @Bean
    public Binding binding(@Qualifier("queueA") Queue queue, @Qualifier("defaultExchange") DirectExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with(RabbitConfig.ROUTINGKEY_A);
    }
}
