package com.mozhumz.usermanage.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyj.util.exception.BaseException;
import com.hyj.util.param.CheckParamsUtil;
import com.mozhumz.usermanage.constant.CommonConstant;
import com.mozhumz.usermanage.enums.ErrorCode;
import com.mozhumz.usermanage.mapper.ISysEmailMapper;
import com.mozhumz.usermanage.model.dto.SendEmailDto;
import com.mozhumz.usermanage.model.entity.SysEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Component
@Slf4j
public class EmailUtil {

    private static ISysEmailMapper sysEmailMapper;

    @Resource
    public  void setSysEmailMapper(ISysEmailMapper sysEmailMapper) {
        EmailUtil.sysEmailMapper = sysEmailMapper;
    }

    /**
     * 发送邮件
     * @param sendEmailDto
     */
    public static void sendEmail(SendEmailDto sendEmailDto) {

        //获取可用的系统邮箱列表
        QueryWrapper<SysEmail> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("state",1);
        List<SysEmail> list=sysEmailMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(list)){
            throw new BaseException(ErrorCode.SYS_EMAIL_NOT_SET_ERR.desc);
        }
        for(SysEmail sysEmail:list){
            //设置系统发件箱
            sendEmailDto.setSendEmail(sysEmail.getEmail());
            sendEmailDto.setSendPwd(sysEmail.getPwd());
            CheckParamsUtil.checkObj(sendEmailDto);
            //是否达到发送上限
            boolean flag=send(sendEmailDto);
            if(!flag){
                break;
            }
        }



    }

    public static boolean send(SendEmailDto sendEmailDto) {
        boolean flag=false;
        try {
            log.info("send start:"+sendEmailDto);
            Properties properties = new Properties();
            properties.put("mail.transport.protocol", "smtp");// 连接协议
            properties.put("mail.smtp.host", "smtp.qq.com");// 主机名
            properties.put("mail.smtp.port", 465);// 端口号
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
            properties.put("mail.debug", "false");// 设置是否显示debug信息 true 会在控制台显示相关信息
            // 得到回话对象
            Session session = Session.getInstance(properties);
            // 获取邮件对象
            Message message = new MimeMessage(session);
            // 设置发件人邮箱地址
            message.setFrom(new InternetAddress(sendEmailDto.getSendEmail()));
            // 设置收件人邮箱地址
//            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(sendEmailDto.getReceiveEmail())});//多个收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendEmailDto.getReceiveEmail()));//一个收件人
            // 设置邮件标题
            message.setSubject(sendEmailDto.getTitle());
            // 设置邮件内容
            message.setContent(sendEmailDto.getContent(), "text/html;charset=UTF-8");
            // 得到邮差对象
            Transport transport = session.getTransport();
            // 连接发件人的邮箱账户
            transport.connect(sendEmailDto.getSendEmail(), sendEmailDto.getSendPwd());// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            //发送内容存入redis
            SessionUtil.setRedisKV(sendEmailDto.getKey(),sendEmailDto.getContent(), CommonConstant.customerCodeSeconds);
           log.info("send(SendEmailDto sendEmailDto):ok");
        } catch (AddressException e) {
            log.error("AddressException:"+e);
            throw new BaseException(ErrorCode.SEND_EMAIL_ERR.desc+e.getMessage());
        } catch (MessagingException e) {
            log.error("MessagingException:"+e);
            if(e.getMessage()!=null&&e.getMessage().contains("550 Connection frequency limited")){
                //达到上限 自动换邮箱发送
                flag=true;
            }else {
                throw new BaseException(ErrorCode.SEND_EMAIL_ERR.desc+e.getMessage());
            }

        } catch (Exception e) {
            log.error("Exception:"+e);
            throw new BaseException(ErrorCode.SEND_EMAIL_ERR.desc+e.getMessage());
        }
        return flag;
    }

    /**
     * 获取n位数字验证码
     * @param n
     * @return
     */
    public static String getEmailCode(int n){
        if(n<1){
            n=6;
        }
        return (int)((Math.random()*9+1)*(Math.pow(10,n-1)))+"";
    }

}
