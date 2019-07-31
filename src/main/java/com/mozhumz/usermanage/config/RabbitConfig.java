package com.mozhumz.usermanage.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @description rabbitmq配置类
 */
@Configuration
public class RabbitConfig {

    public static final String topicExchange = "topic-exchange";
    public static final String fanoutExchange = "fanout-exchange-saveUser";
    public static final String headersExchange = "headers-exchange";

    public static final String addUserQueue = "addUserQueue";
    public static final String queueTest = "test";



    //声明队列
//    @Bean
//    public Queue queueTest() {
//
//        /**
//         * durable： 是否持久化, 队列的声明默认是存放到内存中的，如果rabbitmq重启会丢失，
//         * 如果想重启之后还存在就要使队列持久化，保存到Erlang自带的Mnesia数据库中，当rabbitmq重启之后会读取该数据库
//         * exclusive：是否排外的，有两个作用，一：当连接关闭时connection.close()该队列是否会自动删除；
//         * 二：该队列是否是私有的private，如果不是排外的，可以使用两个消费者都访问同一个队列，没有任何问题，
//         * 如果是排外的，会对当前队列加锁，其他通道channel是不能访问的，如果强制访问会报异常
//         * autoDelete：是否自动删除，当最后一个消费者断开连接之后队列是否自动被删除，
//         * 可以通过RabbitMQ Management，查看某个队列的消费者数量，当consumers = 0时队列就会自动删除
//         */
////        return new Queue(queueName, false, true, true);
//        return new Queue(queueTest);
//    }

//    @Bean
//    public Queue queue() {
//        return new Queue(addUserQueue);
//    }

//    @Bean
//    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(){
//        SimpleRabbitListenerContainerFactory factory=new SimpleRabbitListenerContainerFactory();
//        factory.setConcurrentConsumers(50);
////        factory.setConnectionFactory(connectionFactory());
//        factory.setPrefetchCount(1);
//        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
//
//        return factory;
//    }

//    @Bean
//    public ConnectionFactory connectionFactory(){
//        CachingConnectionFactory factory=new CachingConnectionFactory(host,port);
//        factory.setUsername(username);
//        factory.setPassword(password);
//
//        return factory;
//    }
    //声明Topic交换机
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(topicExchange);
    }

    //将队列与Topic交换机进行绑定，并指定路由键
//    @Bean
//    Binding topicBinding(TopicExchange topicExchange) {
//        return BindingBuilder.bind(queue()).to(topicExchange).with("org.cord.#");
//    }
//    @Bean
//    Binding topicBindingTest(TopicExchange topicExchange) {
//        return BindingBuilder.bind(queueTest()).to(topicExchange).with("org.cord.*.X");
//    }

    //声明fanout交换机
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanoutExchange);
    }

    //将队列与fanout交换机进行绑定
//    @Bean
//    Binding fanoutBinding(FanoutExchange fanoutExchange) {
//        return BindingBuilder.bind(queue()).to(fanoutExchange);
//    }

//    @Bean
//    Binding fanoutBindingTest(FanoutExchange fanoutExchange) {
//        return BindingBuilder.bind(queueTest()).to(fanoutExchange);
//    }

    //声明Headers交换机
    @Bean
    HeadersExchange headersExchange() {
        return new HeadersExchange(headersExchange);
    }

    //将队列与headers交换机进行绑定
//    @Bean
//    Binding headersBinding(HeadersExchange headersExchange) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("First","A");
//        map.put("Fourth","D");
//        //whereAny表示部分匹配，whereAll表示全部匹配
////        return BindingBuilder.bind(queue).to(headersExchange).whereAll(map).match();
//        return BindingBuilder.bind(queue()).to(headersExchange).whereAny(map).match();
//    }

//    @Bean
//    Binding headersBindingTest( HeadersExchange headersExchange) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("First","A");
//        map.put("Fourth","D");
//        //whereAny表示部分匹配，whereAll表示全部匹配
////        return BindingBuilder.bind(queue).to(headersExchange).whereAll(map).match();
//        return BindingBuilder.bind(queueTest()).to(headersExchange).whereAll(map).match();
//    }
}
