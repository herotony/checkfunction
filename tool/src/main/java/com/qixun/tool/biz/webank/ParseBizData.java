package com.qixun.tool.biz.webank;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by saosinwork on 2017/7/20.
 */
public class ParseBizData {

    //signTicket有效期1小时，全局唯一，定时更新,此前需要从缓存中提取signTicket,如果没有需要
    //从webank去用AccessToken获取。
    public static String Sign(List<String> values, String signTicket) {
        if (values == null) {
            throw new NullPointerException("values is null");
        }
        values.removeAll(Collections.singleton(null));// remove null
        values.add(signTicket);
        java.util.Collections.sort(values);
        StringBuilder sb = new StringBuilder();
        for (String s : values) {
            sb.append(s);
        }
        try {
            MessageDigest md = MessageDigest.getInstance("sha1");
            md.update(sb.toString().getBytes("UTF-8"));
            String sign = bytesToHexString(md.digest());
            return sign;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHexString(byte[] bytes){

        StringBuffer buffer = new StringBuffer();

        for(int i=0;i<bytes.length;i++){

            buffer.append(byteToHexString(bytes[i]));
        }

        return buffer.toString().toUpperCase();
    }

    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5","6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    private static String byteToHexString(byte b) {

        int ret = b;

        if (ret < 0) {
            ret += 256;
        }
        int m = ret / 16;
        int n = ret % 16;
        return hexDigits[m] + hexDigits[n];
    }

    public static void main(String[] args) throws Exception{

        List<String> list = new ArrayList<>();

        list.add("Y13");
        list.add("tony");
        list.add("{\"j\":\"12\",\"a\":1}");
        list.add("batch");
        list.add(null);
        list.add("6163");
        list.add("");

        String signValue = Sign(list,"bk");

    }

}
