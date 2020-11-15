package com.ua.innova.pro.rsa;

import com.ua.innova.pro.properties.ConfigProperties;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;

/**
 * @author Bogush Aleksandr
 * @version 1.0
 * @since 15-11-2020
 */

public class RsaKey {
    public static String userText;

    static {
        try {
            userText = new ConfigProperties().getPropUserString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String user_Key;

    static {
        try {
            user_Key = new ConfigProperties().getPropKeyPem();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] decodeText(String s) throws IOException {
        byte[] res = java.util.Base64.getDecoder().decode(s);
        return res;
    }

    private static byte[] getKeyBytes(String s) throws IOException {
        File f = new File(s);
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int) f.length()];
        dis.readFully(keyBytes);
        dis.close();
        return keyBytes;
    }

    public PrivateKey readPrivateKeyPKCS1PEM(String s) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        String privKeyPEM = new String(getKeyBytes(s));
        String privKeyPEMnew = privKeyPEM.replaceAll("\\n", "").replace("-----BEGIN RSA PRIVATE KEY-----", "").replace("-----END RSA PRIVATE KEY-----", "");
        byte[] bytes = java.util.Base64.getDecoder().decode(privKeyPEMnew);

        DerInputStream derReader = new DerInputStream(bytes);
        DerValue[] seq = derReader.getSequence(0);
        BigInteger modulus = seq[1].getBigInteger();
        BigInteger publicExp = seq[2].getBigInteger();
        BigInteger privateExp = seq[3].getBigInteger();
        BigInteger prime1 = seq[4].getBigInteger();
        BigInteger prime2 = seq[5].getBigInteger();
        BigInteger exp1 = seq[6].getBigInteger();
        BigInteger exp2 = seq[7].getBigInteger();
        BigInteger crtCoef = seq[8].getBigInteger();

        RSAPrivateCrtKeySpec keySpec = new RSAPrivateCrtKeySpec(modulus, publicExp, privateExp, prime1, prime2, exp1, exp2, crtCoef);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;

    }

    public String decryptData(byte[] encryptedData, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException, InvalidKeySpecException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        cipher.doFinal(encryptedData);
        return new String(cipher.doFinal(encryptedData));
    }

    public String returnText;

    public RsaKey() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        this.returnText = this.decryptData(this.decodeText(userText), this.readPrivateKeyPKCS1PEM(user_Key));
    }

}