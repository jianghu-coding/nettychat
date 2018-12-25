package com.im.nettychat.util;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by hejianglong on 2017/7/28.
 */
public class Util {

    private static final int HASH_ITERATIONS = 1000;
    public static final String EMPTY_STRING = "";
    private static final int HASH_KEY_LENGTH = 192;
    private static final int DEFAULT_SALT_SIZE = 32;

    public static boolean isEmpty(String source) {
        return (source == null || "".equals(source));
    }

    private static String generateSalt() {
        Random r = new SecureRandom();
        byte[] saltBinary = new byte[DEFAULT_SALT_SIZE];
        r.nextBytes(saltBinary);
        return Base64.getEncoder().encodeToString(saltBinary);
    }

    private static String hashPasswordAddingSalt(String password, byte[] salt) {
        if (isEmpty(password)) {
            return EMPTY_STRING;
        }
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            SecretKey key = f.generateSecret(new PBEKeySpec(
                    password.toCharArray(), salt, HASH_ITERATIONS, HASH_KEY_LENGTH)
            );
            return Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (Exception e) {
            return EMPTY_STRING;
        }
    }

    public static String hashPasswordAddingSalt(String password) {
        byte[] salt = generateSalt().getBytes();
        return Base64.getEncoder().encodeToString(salt) + '$' + hashPasswordAddingSalt(password, salt);
    }

    public static boolean isValidPassword(String password, String hashedPassword) {
        String[] saltAndPass = hashedPassword.split("\\$");
        if (saltAndPass.length != 2) {
            throw new IllegalStateException(
                    "The stored password have the form 'salt$hash'");
        }
        String hashOfInput = hashPasswordAddingSalt(password, Base64.getDecoder().decode(saltAndPass[0]));
        return hashOfInput.equals(saltAndPass[1]);
    }

    public static Util shaPasswordEncoder() {
        return new Util();
    }

    public static boolean stringIsEquals(String strA, String strB) {
        if(strA == null) {
            strA = "";
        }
        if(strB == null) {
            strB = "";
        }
        return strA.equals(strB);
    }

    public static boolean stringIsNotEquals(String strA, String strB) {
        return !stringIsEquals(strA, strB);
    }

    public static boolean integerIsEquals(Integer inA, Integer inB) {
        if(inA == null) {
            inA = 0;
        }
        if(inB == null) {
            inB = 0;
        }
        return inA.equals(inB);
    }

    public static boolean integerIsNotEquals(Integer inA, Integer inB) {
        return !integerIsEquals(inA, inB);
    }

    public static boolean doubleIsEquals(Double dbA, Double dbB) {
        if(dbA == null) {
            dbA = 0d;
        }
        if(dbB == null) {
            dbB = 0d;
        }
        return dbA.equals(dbB);
    }

    public static boolean doubleIsNotEquals(Double dbA, Double dbB) {
        return !doubleIsEquals(dbA, dbB);
    }

    public static boolean shortIsEquals(Short inA, Short inB) {
        if(inA == null) {
            inA = 0;
        }
        if(inB == null) {
            inB = 0;
        }
        return inA == inB;
    }

    public static boolean shortIsNotEquals(Short inA, Short inB) {
        return !shortIsEquals(inA, inB);
    }

    public static boolean isNullOrZero(Long value) {
        return (value == null || value == 0);
    }

    public  static Long formartDate(String date){
        try{
            return Long.valueOf(date.replaceAll("[-\\s:]",""));
        }catch (Exception e){
            return 0l;
        }
    }

    /**
     * 判断今天，昨天，前天
     * @param startDate
     * @return
     */
    public static int getDays(Date startDate){
        int offSet = Calendar.getInstance().getTimeZone().getRawOffset();
        long today = (System.currentTimeMillis()+offSet)/86400000;
        long start = (startDate.getTime()+offSet)/86400000;
        return Long.valueOf(start-today).intValue();
    }

    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static Map<String, Object> objectToMap(Object obj, String... ignores) throws IllegalAccessException {
        if(obj == null){
            return null;
        }
        List<String> ignoreList = new ArrayList<>();
        for(String ignore : ignores) {
            ignoreList.add(ignore);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if(ignoreList.contains(field)) {
                continue;
            }
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }

        return map;
    }
}
