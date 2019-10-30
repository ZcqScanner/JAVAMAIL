package mail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 *  发送QQ邮件
 *  方式①：发送邮件的简单代码
 *  方式②：发送邮件中添加附件
 *  方式③：发送HTML的邮件格式
 */
public class SendMail {

    private static final String MAIL_SMTP_HOST = "smtp.qq.com";
    private static final String MAIL_SMTP_PORT = "465";
    private static final String MAIL_SMTP_AUTH = "true";
    private static final String MAIL_DEBUG = "true";
    private static final String MAIL_TRANSPORT = "smtp";
    private static final String MAIL_SMTP_SSL_ENABLE = "true";
    private static final String SEND_USER = "439294170@qq.com";
    private static final String SEND_PASSWORD = "yvcxzjhoasoqcaif";

    private static final String RECEIVE_USER = "zhuchuanqi@chinasofti.com";

    public void sendMailTest() {
        //做链接前的准备工作  也就是参数初始化
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host",MAIL_SMTP_HOST);//发送邮箱服务器
        properties.setProperty("mail.smtp.port",MAIL_SMTP_PORT);//发送端口
        properties.setProperty("mail.smtp.auth",MAIL_SMTP_AUTH);//是否开启权限控制
        properties.setProperty("mail.debug",MAIL_DEBUG);//true 打印信息到控制台
        properties.setProperty("mail.transport",MAIL_TRANSPORT);//发送的协议是简单的邮件传输协议
        properties.setProperty("mail.smtp.ssl.enable",MAIL_SMTP_SSL_ENABLE);
        //建立两点之间的链接)
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SEND_USER,SEND_PASSWORD);
            }
        });
        Transport transport = null;
        //创建邮件对象
        Message message = new MimeMessage(session);
        //设置发件人
        try {
            message.setFrom(new InternetAddress(SEND_USER));

            //设置收件人
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(RECEIVE_USER));//收件人
            //设置主题
            message.setSubject("验证码");
            //设置邮件正文  第二个参数是邮件发送的类型
            message.setContent("2569658","text/html;charset=UTF-8");
            //发送一封邮件
            transport = session.getTransport(MAIL_TRANSPORT);
            transport.connect(SEND_USER,SEND_PASSWORD);
            Transport.send(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        } finally {
            if(transport!=null){
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }

        }

    }


    private static void testEmailAttachment() throws Exception {
        EmailAttachment attachment; //创建一个附件对象
        attachment = new EmailAttachment();
        attachment.setPath("E:\\WorkSpace\\JAVAMAIL\\src\\resources\\log4j2.xml");  //放一张项目中的图片(指向真实的附件)
        attachment.setDisposition(EmailAttachment.ATTACHMENT); //完成附件设置
        attachment.setDescription("这张图片是一个..."); //设置附件的描述
        attachment.setName("log4j2.xml"); //设置附件的名称
        //创建email对象(MultiPartEmail可以操作附件)
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName(MAIL_SMTP_HOST);
        email.setSmtpPort(Integer.parseInt(MAIL_SMTP_PORT));
        //验证信息(发送的邮箱地址与密码) 注:这里的密码是授权码
        email.setAuthenticator(new DefaultAuthenticator(SEND_USER,SEND_PASSWORD));
        email.setSSLOnConnect(true); // 是否启用SSL
        email.setFrom(SEND_USER); //发送邮件的地址(和验证信息的地址一样)
        email.addTo(SEND_USER);  //发送给哪一个邮件
        email.setSubject("这是一张图片"); //邮件标题
        email.setMsg("我发了一张图片给你看哦！");  //邮件内容
        email.attach(attachment); //把附件加到邮件中
        email.send(); //发送邮件
    }

    public static void main(String[] args) throws Exception {
        SendMail sendMail = new SendMail();
//        sendMail.sendMailTest();
        sendMail.testEmailAttachment();
    }
}