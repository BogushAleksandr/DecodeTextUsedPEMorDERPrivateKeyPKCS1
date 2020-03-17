package com.ua.innova.pro;


import com.ua.innova.pro.rsa.RsaKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author Sasha
 */
public class Main {
    public static void main(String[] args) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        RsaKey rk = new RsaKey();
        System.out.println(rk.returnText);
    }
}
