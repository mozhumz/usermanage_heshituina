//package com.mozhumz.usermanage;
//
//import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
//import com.mozhumz.usermanage.config.RabbitConfig;
//import com.mozhumz.usermanage.config.RabbitProducer;
//import com.mozhumz.usermanage.config.kafka.KafkaProducer;
//import com.mozhumz.usermanage.mapper.IUserMapper;
//import com.mozhumz.usermanage.model.dto.SendEmailDto;
//import com.mozhumz.usermanage.model.entity.User;
//import com.mozhumz.usermanage.utils.EmailUtil;
//import com.mozhumz.usermanage.utils.MD5Util;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import javax.jms.Queue;
//import javax.jms.Topic;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ApplicationTest {
//
//    @Autowired
//    private RabbitProducer producer;
//    @Resource
//    IUserMapper userMapper;
//    @Resource
//    KafkaProducer kafkaProducer;
//
////    @Autowired
////    private JmsTemplate jmsTemplate;
////
////    @Autowired
////    private Topic topic;
////
////    @Autowired
////    private Queue queue;
//
//
//    //Direct
//    @Test
//    public void sendDirectMsg() {
//        producer.sendDirectMsg(RabbitConfig.addUserQueue, new Date());
//    }
//
//    //Topic
//    @Test
//    public void sendtopicMsg() {
//        producer.sendExchangeMsg(RabbitConfig.topicExchange,"org.cord.as.X", new Date());
//    }
//
//    //Fanout 与路由key无关 会发送到绑定该交换机的所有队列
//    @Test
//    public void sendFanoutMsg() {
//        producer.sendExchangeMsg(RabbitConfig.fanoutExchange, null, new Date());
//    }
//
//    //Headers
//    @Test
//    public void sendHeadersMsg() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("First","A");
//        map.put("Fourth","D");
//        producer.sendHeadersMsg(RabbitConfig.headersExchange, new Date(), map);
//    }
//
//    @Test
//    public void addUser(){
//        User user=new User();
//        user.setCreateDate(new Date());
//        user.setUpdateDate(new Date());
//        user.setPassword(MD5Util.getDefaultPwd());
//        user.setUsername("test2");
//        user.setRoleIdStr(",1,");
//
//        userMapper.addOne(user);
//    }
//
//    @Test
//    public void sendEmail(){
//        SendEmailDto sendEmailDto=new SendEmailDto();
//        sendEmailDto.setTitle("测试"+1314);
//        sendEmailDto.setContent(1314+"");
//        sendEmailDto.setSendEmail("289215182@qq.com");
//        sendEmailDto.setSendPwd("vtkkubmpydiqcagi");
//        sendEmailDto.setReceiveEmail("554501252@qq.com");
//        sendEmailDto.setKey("key");
//        EmailUtil.send(sendEmailDto);
//    }
//
//    @Test
//    public void updateUser(){
//        User user=userMapper.selectById(1);
//        user.setPhone("15002813293");
//        user.updateById();
//    }
//
//    @Test
//    public void updateUser2(){
//        User user=userMapper.selectById(1);
//        UpdateWrapper<User> updateWrapper=new UpdateWrapper();
//        updateWrapper.set("phone",null);
//        updateWrapper.eq("id",user.getId());
//        userMapper.update(null,updateWrapper);
//
//    }
//
////    @Test
////    public void sendActiveMq(){
////        jmsTemplate.convertAndSend(this.topic,"发送的topic数据!");
////        jmsTemplate.convertAndSend(this.queue,"发送的queue数据!");
////        System.out.println("ok");
////    }
//
//    @Test
//    public void sendKafkaMsg(){
//        kafkaProducer.sendMessage("kafka msg test");
//        System.out.println("ok");
//    }
//}
