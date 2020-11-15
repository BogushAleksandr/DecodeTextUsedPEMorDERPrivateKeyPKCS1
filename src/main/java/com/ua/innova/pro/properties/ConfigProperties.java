package com.ua.innova.pro.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * @author Bogush Aleksandr
 * @version 1.0
 * @since 15-11-2020
 */

public class ConfigProperties {

    String result = "";
    InputStream inputStream;

    public String getPropUserString() throws IOException {

        try {
            Properties prop = new Properties();
            String propFileName = "rsakeyanddata.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            String userString = prop.getProperty("textUser");
            result = userString;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return result;
    }

    public String getPropKeyPem() throws IOException {

        try {
            Properties prop = new Properties();
            String propFileName = "rsakeyanddata.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            String keyPem = prop.getProperty("privateKeyPem");
            result = keyPem;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return result;
    }
}
