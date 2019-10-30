package mail.utils;

import mail.entity.MailInfo;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.List;

/**
 * @author LENOVO
 * @description 发送邮件Util
 * @date 2019-10-29 16:12
 */
public class MailUtil {

    private static final String MAIL_SERVER_HOST = "smtp.qq.com";
    private static final int MAIL_SERVER_PORT = 465;
    private static final String SEND_USER = "439294170@qq.com";
    private static final String SEND_PASSWORD = "yvcxzjhoasoqcaif";

    /**
     * 发送 邮件方法 (Html格式，支持附件)
     *
     * @return void
     */
    public static void sendEmail(MailInfo mailInfo) {

        try {
            HtmlEmail email = new HtmlEmail();
            // 配置信息
            email.setCharset("UTF-8");
            email.setHostName(MAIL_SERVER_HOST);
            email.setSmtpPort(MAIL_SERVER_PORT);
            //验证信息(发送的邮箱地址与密码) 注:这里的密码是授权码
            email.setAuthenticator(new DefaultAuthenticator(SEND_USER, SEND_PASSWORD));
            email.setSSLOnConnect(true); // 是否启用SSL
            email.setFrom(SEND_USER); //发送邮件的地址(和验证信息的地址一样)

            email.setSubject(mailInfo.getSubject());
            email.setHtmlMsg(mailInfo.getContent());


            // 添加附件
            List<EmailAttachment> attachments = mailInfo.getAttachments();
            if (null != attachments && attachments.size() > 0) {
                for (int i = 0; i < attachments.size(); i++) {
                    email.attach(attachments.get(i));
                }
            }

            // 收件人
            List<String> toAddress = mailInfo.getToAddress();
            if (null != toAddress && toAddress.size() > 0) {
                for (int i = 0; i < toAddress.size(); i++) {
                    email.addTo(toAddress.get(i));
                }
            }
            // 抄送人
            List<String> ccAddress = mailInfo.getCcAddress();
            if (null != ccAddress && ccAddress.size() > 0) {
                for (int i = 0; i < ccAddress.size(); i++) {
                    email.addCc(ccAddress.get(i));
                }
            }
            //邮件模板 密送人
            List<String> bccAddress = mailInfo.getBccAddress();
            if (null != bccAddress && bccAddress.size() > 0) {
                for (int i = 0; i < bccAddress.size(); i++) {
                    email.addBcc(bccAddress.get(i));
                }
            }
            email.send();
            System.out.println("邮件发送成功！");
        } catch (EmailException e) {
            e.printStackTrace();
        }

    }
}
