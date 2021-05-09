package com.tp2.utils;

public class EnvironmentVariables {

    //public static final String HOST_URL = "http://ec2-35-182-16-246.ca-central-1.compute.amazonaws.com:4200";
    public static final String HOST_URL = "http://192.168.0.42:4200";

    // QR code
    public static final String QR_URL = HOST_URL + "/permis/verify/";
    public static final int QR_WIDTH = 300;
    public static final int QR_HEIGHT = 300;
    public static final String QR_FORMAT = "PNG";
    public static final String QR_MIME_TYPE = "image/png";


    // Emails
    public static final String EMAIL_SUBJECT = "QR code covid";
    public static final String EMAIL_BODY = "Voici votre code qr dans le format choisit";
    public static final String EMAIL_PDF_MIME_TYPE = "application/pdf";


}
