package com.tp2.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.tp2.utils.EnvironmentVariables;
import org.springframework.stereotype.Service;

import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class QRService {

    public ByteArrayDataSource QRCodeDataSource(String data) throws IOException, WriterException {
        ByteArrayOutputStream byteArrayOutputStream = getMatrixByteArrayOutputStream(data);
        byteArrayOutputStream.close();

        return new ByteArrayDataSource(byteArrayOutputStream.toByteArray(), EnvironmentVariables.QR_MIME_TYPE);
    }

    //data:image/png;base64,

    public String base64QRImage(String data) throws IOException, WriterException {
        ByteArrayOutputStream byteArrayOutputStream = getMatrixByteArrayOutputStream(data);
        byteArrayOutputStream.close();

        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(byteArrayOutputStream.toByteArray());
    }

    private ByteArrayOutputStream getMatrixByteArrayOutputStream(String data) throws WriterException, IOException {
        QRCodeWriter qr = new QRCodeWriter();
        BitMatrix bitMatrix = qr.encode(data, BarcodeFormat.QR_CODE, EnvironmentVariables.QR_WIDTH, EnvironmentVariables.QR_HEIGHT);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, EnvironmentVariables.QR_FORMAT, byteArrayOutputStream);
        return byteArrayOutputStream;
    }
}
