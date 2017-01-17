package com.waemy.utils;

import java.security.MessageDigest;

/**
 * Created with IntelliJ IDEA.
 * User: PC
 * Date: 13-7-31
 * Time: 下午3:57
 * To change this template use File | Settings | File Templates.
 */
public class Md5Util {

    //加密
    public static String MD5(String inStr){
        MessageDigest md5=null;
        try{
            md5=  MessageDigest.getInstance("MD5");
        }catch (Exception e){
            e.printStackTrace();
        }
        String stringCode=String.valueOf(inStr);
        char[] charArray=stringCode.toCharArray();
        byte[] byteArray=new byte[charArray.length];
        for(int i=0;i<charArray.length;i++)
            byteArray[i]=(byte) charArray[i];
            byte[] md5Bytes=md5.digest(byteArray);
            StringBuffer hexValue=new StringBuffer();
            for(int j=0;j<md5Bytes.length;j++){
                int val=((int)md5Bytes[j]) & 0xff;
                if (val<16)
                    hexValue.append("0");
                    hexValue.append(Integer.toHexString(val));
        }
        return   hexValue.toString();
    }
    
    public static void main(String[] args) {
        System.out.println(MD5("000000"));
    }
}