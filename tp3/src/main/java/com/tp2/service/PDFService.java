package com.tp2.service;

import com.google.zxing.WriterException;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PDFService {

    private final String mimeType = "application/pdf";

    public ByteArrayDataSource PDFQRDataSource(QRService qrService, String data) throws IOException, WriterException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(pdfWriter);
        Document document = new Document(pdf);

        Image qrCodeImage = new Image(
                ImageDataFactory.create(qrService.QRCodeDataSource(data).getInputStream().readAllBytes())
        );

        Paragraph p = new Paragraph("Bonjour voici votre code QR sous forme PDF\n")
                .add("Code QR:")
                .add(qrCodeImage);

        document.add(p);

        document.close();

        return new ByteArrayDataSource(byteArrayOutputStream.toByteArray(), mimeType);
    }

}
