package com.mozhumz.usermanage.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description rabbit消息生产者
 */
@Component
@Slf4j
public class RabbitProducer {

    @Autowired
    private AmqpTemplate template;

    @Autowired
    private AmqpAdmin admin;

    /**
     * 路由模式(Direct)
     * @param routingKey 路由关键字
     * @param msg 消息体
     */
    public void sendDirectMsg(String routingKey, Object msg) {
        template.convertAndSend(routingKey, msg);
    }

    /**
     * 通配符模式(Topic)&&发布订阅模式(Fanout)
     * @param routingKey 路由关键字
     * @param msg 消息体
     * @param exchange 交换机
     */
    public void sendExchangeMsg(String exchange, String routingKey, Object msg) {
        template.convertAndSend(exchange, routingKey, msg);
    }

    /**
     * @param map 消息headers属性
     * @param exchange 交换机
     * @param msg 消息体
     */
    public void sendHeadersMsg(String exchange, Object msg, Map<String, Object> map) {
        template.convertAndSend(exchange, null, msg, message -> {
            message.getMessageProperties().getHeaders().putAll(map);
            return message;
        });
    }

}
