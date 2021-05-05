package com.tp2.service.email;

import lombok.Data;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

@Data
public class Mail {

    private String mailTo;
    private String subject;
    private String body;
    private JavaMailSender javaMailSender;
    private MimeMessage mimeMessage;
    private MimeMessageHelper mimeMessageHelper;

    public Mail(JavaMailSender javaMailSender, String mailTo, String subject, String body) {
        this.javaMailSender = javaMailSender;
        this.mailTo = mailTo;
        this.subject = subject;
        this.body = body;
        this.mimeMessage = this.javaMailSender.createMimeMessage();
    }

    public void send() throws Exception {
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
        buildEmail(message);
        javaMailSender.send(mimeMessage);
    }

    protected void buildEmail(MimeMessageHelper messageHelper) throws Exception {
        messageHelper.setTo(mailTo);
        messageHelper.setSubject(subject);
        messageHelper.setText(body);
    }
}
