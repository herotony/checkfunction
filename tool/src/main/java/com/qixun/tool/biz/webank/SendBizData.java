package com.qixun.tool.biz.webank;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.security.KeyStore;

import com.qixun.tool.httputil.HttpTool;

/**
 * Created by saosinwork on 2017/7/21.
 */
public class SendBizData {

    public static String SendBizDataToWebank(String bizUrl,String bizData){

        try{

            String keyStorePass = "App1234.";//具体密码值以项目经理提供的为准，客户端证书密码默认为App1234.

            String clientJks = null;
            String serverJks = null;

            String enableProxy = "0";
            String proxyHost = null;
            Integer proxyPort = null;
            String proxyUsername = null;
            String proxyPassword = null;
            String url = bizUrl;

            InputStream keyStoreInput = null;
            InputStream trustStoreInput = null;
            KeyStore keyStore = null;
            KeyStore trustStore = null;
            //导入证书
            try {
                keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStoreInput = new FileInputStream(new File(clientJks));
                keyStore.load(keyStoreInput, keyStorePass.toCharArray());

                trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStoreInput = new FileInputStream(new File(serverJks));
                trustStore.load(trustStoreInput, null);
            }catch (Throwable t){
                throw new RuntimeException("input KeyStore  fail", t);
            }

            if(enableProxy.equals("0")){
                proxyHost="";
            }

            //创建加载证书且设置代理的HttpClient，如不需要代理，请将proxyHost、proxyPort、proxyUsername、proxyPassword全部置为空
            //注：代理只在测试环境使用，生产环境不使用
            HttpClient client = HttpTool.createHttpClientWithCert(keyStore, keyStorePass, trustStore, 200, 5, 1000, 3000, proxyHost, proxyPort, proxyUsername, proxyPassword);

            // 设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000)
                    .setConnectTimeout(3000).build();

            //HttpGet httpGet = new HttpGet(url);//此URL为测试URL，具体业务URL请求请参考接口文
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            httpPost.addHeader("Content-Type","application/json;charset=utf-8");
            StringEntity postData = new StringEntity(bizData);
            httpPost.setEntity(postData);

            //执行
            try {

                HttpResponse response =client.execute(httpPost);

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                System.out.println(EntityUtils.toString(response.getEntity(),Charset.defaultCharset()));

                InputStream resStream = response.getEntity().getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(resStream, "utf-8"));
                StringBuffer resBuffer = new StringBuffer();
                String res = "";
                while ((res = br.readLine()) != null) {
                    resBuffer.append(res);
                }
                String resultReturn = resBuffer.toString();
                System.out.println("result:"+ resultReturn);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally {

                if (keyStoreInput != null) {
                    try {
                        keyStoreInput.close();
                    } catch (IOException e) {
                        throw new RuntimeException("key store close fail", e);
                    }
                }

                if (trustStoreInput != null) {
                    try {
                        trustStoreInput.close();
                    } catch (IOException e) {
                        throw new RuntimeException("trust store close fail", e);
                    }
                }
            }


        }catch(Exception sendBizDataErr){


        }

        return null;
    }

}
