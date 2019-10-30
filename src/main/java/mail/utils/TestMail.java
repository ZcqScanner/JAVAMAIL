package mail.utils;

import mail.entity.MailInfo;
import org.apache.commons.mail.EmailAttachment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LENOVO
 * @description 测试类
 * @date 2019-10-29 16:41
 */
public class TestMail {
    /**
     * @return void
     */
    public static void main(String[] args) {
        MailInfo mailInfo = new MailInfo();
        List<String> toList = new ArrayList<String>();
        toList.add("zcq111006@163.com");

        List<String> ccList = new ArrayList<String>();
        ccList.add("439294170@qq.com");

        List<String> bccList = new ArrayList<String>();
        bccList.add("zhuchuanqi@chinasofti.com");

        //添加附件
        EmailAttachment att = new EmailAttachment();
        att.setPath("E:\\WorkSpace\\JAVAMAIL\\src\\src\\resources\\log4j2.xml");
        att.setName("测试.txt");
        List<EmailAttachment> atts = new ArrayList<EmailAttachment>();
        atts.add(att);
        mailInfo.setAttachments(atts);

        mailInfo.setToAddress(toList);//收件人
        mailInfo.setCcAddress(ccList);//抄送人
        mailInfo.setBccAddress(bccList);//密送人

        mailInfo.setSubject("测试邮件发送");
        mailInfo.setContent("内容：<h1>test,测试</h1>");

        MailUtil.sendEmail(mailInfo);

    }
}
