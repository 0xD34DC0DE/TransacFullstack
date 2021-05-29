package com.tp4.admin.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    static MessageDigest digest = null;

    static {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String Hash(String... values) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        for (String val : values) {
            outputStream.write(val.getBytes(StandardCharsets.UTF_8));
        }

        return bytesToHex(digest.digest(outputStream.toByteArray()));
    }

}
