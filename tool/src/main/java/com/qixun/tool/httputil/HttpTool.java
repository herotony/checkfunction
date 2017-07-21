package com.qixun.tool.httputil;

import java.security.KeyStore;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

/**
 * Created by saosinwork on 2017/7/20.
 */
public class HttpTool {


        public static CloseableHttpClient createHttpClientWithCert(KeyStore keyStore, String keyStorePassword, KeyStore trustStoreFile,
                                                                   int connMaxTotal,
                                                                   int connDefaultMaxPerRoute,
                                                                   int validateInactivityMillSeconds,
                                                                   int connEvictIdleConnectionsTimeoutMillSeconds,
                                                                   String proxyHost,
                                                                   int proxyPort,
                                                                   String proxyUsername,
                                                                   String proxyPassword) {
            SSLContext sslcontext = null;
            try {
                sslcontext = SSLContexts.custom()
                        .loadKeyMaterial(keyStore, keyStorePassword.toCharArray())
                        .loadTrustMaterial(trustStoreFile, new TrustSelfSignedStrategy()).build();
            } catch (Exception e) {
                throw new RuntimeException("load key store fail", e);
            }

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            //      allHostsValid);

            // Create a registry of custom connection socket factories for supported
            // protocol schemes.
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", sslsf)
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .build();


            // Create a connection manager with custom configuration.
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

            // Create socket configuration
            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();// 小数据网络包
            // Configure the connection manager to use socket configuration either
            // by default or for a specific host.
            connManager.setDefaultSocketConfig(socketConfig);
            // Validate connections after 1 sec of inactivity
            connManager.setValidateAfterInactivity(validateInactivityMillSeconds);


            // Configure total max or per route limits for persistent connections
            // that can be kept in the pool or leased by the connection manager.
            connManager.setMaxTotal(connMaxTotal);
            connManager.setDefaultMaxPerRoute(connDefaultMaxPerRoute);

            // Use custom cookie store if necessary.
            CookieStore cookieStore = new BasicCookieStore();
            // Use custom credentials provider if necessary.
            //CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            // Create global request configuration
            RequestConfig defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT)
                    .setExpectContinueEnabled(true)
                    .build();

            HttpHost proxy = null;
            if (StringUtils.isNotEmpty(proxyHost)) {
                proxy = new HttpHost(proxyHost, proxyPort, "http");
            }
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            if (StringUtils.isNotEmpty(proxyUsername) && StringUtils.isNotEmpty(proxyPassword)) {
                credsProvider.setCredentials(
                        new AuthScope(proxyHost, proxyPort),
                        new UsernamePasswordCredentials(proxyUsername, proxyPassword));
            }

            // Create an HttpClient with the given custom dependencies and
            // configuration.
            CloseableHttpClient httpclient;
            if (proxy == null) {
                httpclient = HttpClients.custom().setConnectionManager(connManager)
                        .setDefaultCookieStore(cookieStore)
                        .setDefaultRequestConfig(defaultRequestConfig).evictExpiredConnections()
                        .evictIdleConnections(connEvictIdleConnectionsTimeoutMillSeconds, TimeUnit.MILLISECONDS)
                        .setSSLSocketFactory(sslsf).build();
            } else {
                httpclient = HttpClients.custom().setConnectionManager(connManager)
                        .setProxy(proxy)
                        .setDefaultCredentialsProvider(credsProvider)
                        .setDefaultCookieStore(cookieStore)
                        .setDefaultRequestConfig(defaultRequestConfig).evictExpiredConnections()
                        .evictIdleConnections(connEvictIdleConnectionsTimeoutMillSeconds, TimeUnit.MILLISECONDS)
                        .setSSLSocketFactory(sslsf).build();
            }
            return httpclient;
        }
}
