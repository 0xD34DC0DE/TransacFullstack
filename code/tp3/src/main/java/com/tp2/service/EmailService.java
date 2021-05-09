package com.tp2.service;

import com.tp2.model.Permis;
import com.tp2.service.email.PDFMail;
import com.tp2.service.email.QRMail;
import com.tp2.service.exception.HashingErrorException;
import com.tp2.service.exception.NullEmailException;
import com.tp2.service.exception.NullHashException;
import com.tp2.service.exception.NullPermisException;
import com.tp2.utils.EnvironmentVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    QRService qrService;

    @Autowired
    PDFService pdfService;

    public void SendQRImageMail(@NotNull Permis permis, @NotNull String email) throws Exception {

        if (permis == null) {
            throw new NullPermisException();
        }

        if (email == null) {
            throw new NullEmailException();
        }

        if (permis.getPermisHash() == null) {
            throw new NullHashException();
        }

        new QRMail(mailSender,
                email,
                EnvironmentVariables.EMAIL_SUBJECT,
                EnvironmentVariables.EMAIL_BODY,
                qrService,
                EnvironmentVariables.QR_URL + permis.getHash()).send();
    }

    public void SendPDFQRImageMail(@NotNull Permis permis, @NotNull String email) throws Exception {

        if (permis == null) {
            throw new NullPermisException();
        }

        if (email == null) {
            throw new NullEmailException();
        }

        if (permis.getPermisHash() == null) {
            throw new NullHashException();
        }

        new PDFMail(mailSender,
                email,
                EnvironmentVariables.EMAIL_SUBJECT,
                EnvironmentVariables.EMAIL_BODY,
                qrService,
                pdfService,
                EnvironmentVariables.QR_URL + permis.getHash()).send();
    }


}
