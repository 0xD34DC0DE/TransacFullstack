package com.tp2.service.email;

import com.tp2.service.PDFService;
import com.tp2.service.QRService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class PDFMail extends Mail {

    private static final String attachmentFilename = "QRCode.pdf";

    private final QRService qrService;
    private final PDFService pdfService;
    private final String QRData;

    public PDFMail(JavaMailSender javaMailSender, String mailTo, String subject, String body, QRService qrService, PDFService pdfService, String QRData) {
        super(javaMailSender, mailTo, subject, body);
        this.qrService = qrService;
        this.pdfService = pdfService;
        this.QRData = QRData;
    }

    @Override
    protected void buildEmail(MimeMessageHelper messageHelper) throws Exception {
        super.buildEmail(messageHelper);
        messageHelper.addAttachment(attachmentFilename, pdfService.PDFQRDataSource(qrService, QRData));
    }
}
