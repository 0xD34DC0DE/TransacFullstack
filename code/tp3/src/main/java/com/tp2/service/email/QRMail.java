package com.tp2.service.email;

import com.google.zxing.WriterException;
import com.tp2.service.QRService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import java.io.IOException;

public class QRMail extends Mail {

    private static final String attachmentFilename = "QRCode.png";

    private final QRService qrService;
    private final String QRData;

    public QRMail(JavaMailSender javaMailSender, String mailTo, String subject, String body, QRService qrService, String QRData) {
        super(javaMailSender, mailTo, subject, body);
        this.qrService = qrService;
        this.QRData = QRData;
    }

    @Override
    protected void buildEmail(MimeMessageHelper messageHelper) throws IOException, WriterException, MessagingException {
        super.buildEmail(messageHelper);
        messageHelper.addAttachment(attachmentFilename, qrService.QRCodeDataSource(QRData));
    }
}
