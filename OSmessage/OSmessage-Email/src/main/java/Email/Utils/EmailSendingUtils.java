package Email.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@Component
public class EmailSendingUtils {
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    /**
     * 发送纯文本Email
     *
     * @param  subject  邮件主题
     * @param  to       接受方
     * @param  content  邮件内容
     */
    public void sendMail(String subject, String[] to, String content){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject(subject);
        msg.setFrom(from);
        msg.setTo(to);
        msg.setSentDate(new Date());
        msg.setText(content);
        javaMailSender.send(msg);
    }

    /**
     * 发送带附件的Email
     *
     * @param  subject  邮件主题
     * @param  to       接受方
     * @param  content  邮件内容
     * @param  file     附件
     */
    public void sendMail(String subject, String[] to, String content, File file){
        MimeMessage msg = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(msg,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            helper.addAttachment(file.getName(), file);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(msg);
    }

    /**
     * 发送带多个附件的Email
     *
     * @param  subject  邮件主题
     * @param  to       接受方
     * @param  content  邮件内容
     * @param  files    多个附件（文件数组）
     */
    public void sendMail(String subject, String[] to, String content, File[] files){
        MimeMessage msg = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(msg,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            if (files != null && files.length > 0) { // 添加附件（多个）
                for (File file : files) {
                    helper.addAttachment(file.getName(), file);
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(msg);
    }

    /**
     * 发送Thymeleaf模版的Email
     *
     */
    public void sendThymeleafMail(String subject, String[] to, String templateName, Context context){
        MimeMessage msg = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setSubject(subject);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSentDate(new Date());
//            context = new Context();
//            context.setVariable("param1", "现在在测试群发功能");
//            context.setVariable("param2", "传入String[]测试");
//            context.setVariable("param3", "收到不要回复");
            String process = templateEngine.process(templateName, context);
            helper.setText(process, true);
        }catch (MessagingException e){
            e.printStackTrace();
        }
        javaMailSender.send(msg);
    }

}
