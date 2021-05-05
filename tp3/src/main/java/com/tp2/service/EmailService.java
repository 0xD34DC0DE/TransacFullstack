package com.tp2.service;

import com.tp2.model.Permis;
import com.tp2.service.email.PDFMail;
import com.tp2.service.email.QRMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class EmailService {

    private final String subject = "QR code covid";
    private final String body = "Voici votre code qr dans le format choisit";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    QRService qrService;

    @Autowired
    PDFService pdfService;

    public void SendQRImageMail(@NotNull Permis permis, @NotNull String email) throws Exception {

        if (permis == null) {
            throw new Exception("Permis is null");
        }

        if (email == null) {
            throw new Exception("Email is null");
        }

        if (permis.getPermisHash() == null) {
            throw new Exception("Permis is does not have a hash yet");
        }

        new QRMail(mailSender, email, subject, body, qrService, permis.getHash()).send();
    }

    public void SendPDFQRImageMail(@NotNull Permis permis, @NotNull String email) throws Exception {

        if (permis == null) {
            throw new Exception("Permis is null");
        }

        if (email == null) {
            throw new Exception("Email is null");
        }

        if (permis.getPermisHash() == null) {
            throw new Exception("Permis is does not have a hash yet");
        }

        new PDFMail(mailSender, email, subject, body, qrService, pdfService, permis.getHash()).send();
    }


}
