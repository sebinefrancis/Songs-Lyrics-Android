package com.example.sebinefrancis.addsonglyrics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
/**
 * Created by Sebine Francis on 17/09/2017.
 */
public class PropertyReader {

        String result = "";
        //FileInputStream inputStream;
        InputStream inputStream;

        public String getPropValues(String filePath,String key) throws IOException {

            try {
                Properties prop = new Properties();
                File file = new File(filePath);
//                File file = new File(filePath.toString());
//                FileInputStream fileInputStream = new FileInputStream(file);
                inputStream = new FileInputStream(file);
//                inputStream = this.getResourceAsStream(filePath);
                if (inputStream != null) {
                    prop.load(inputStream);
                } else {
                    throw new FileNotFoundException("property file '" + filePath + "' not found in the classpath");
                }
                result = prop.getProperty(key);
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            } finally {
                inputStream.close();
            }
            return result;
        }
    }
