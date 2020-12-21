package fzu.hhj.help2.Util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

@Component
public class MailUtil {
    @Value("${mail.transport}")
    private String transport;
    @Value("${mail.host}")
    private String host;
//    @Value("${mail.port}")
//    private String prot;
    @Value("${mail.userName}")
    private String userName;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.enableSSL}")
    private String enableSSL;


    public void sendMail(String email, String subject, String content)
            throws AddressException, MessagingException {
        // 1.创建一个程序与邮件服务器会话对象 Session

        Properties props = new Properties();
        //设置发送的协议
        //普通SMTP
        props.setProperty("mail.transport.protocol", transport);
        //设置发送邮件的服务器
        //设置QQ邮件服务器 网易163邮箱为; smtp.163.com
        props.setProperty("mail.host", host);

        // 指定验证为true
        props.setProperty("mail.smtp.auth", "true");
        //发送端口
        props.setProperty("mail.smtp.port", "465");
        //启用SSL
        props.setProperty("mail.smtp.ssl.enable", enableSSL);
        //启用调试
        props.setProperty("mail.debug", "true");

        // 创建验证器
        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                //设置发送人的帐号和密码
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(props, auth);

        // 2.创建一个Message，它相当于是邮件内容
        Message message = new MimeMessage(session);

        //TODO: debug
        System.out.println(userName + "test");
        //设置发送者
        message.setFrom(new InternetAddress(userName));

        //设置发送方式与接收者
        message.setRecipient(RecipientType.TO, new InternetAddress(email));
        message.addRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse(userName));

        //设置邮箱标题
        message.setSubject(subject);
        //设置邮件内容
        message.setContent(content, "text/html;charset=utf-8");
        message.setSentDate(new Date());

        // 3.创建 Transport用于将邮件发送
        Transport.send(message);
    }
}

