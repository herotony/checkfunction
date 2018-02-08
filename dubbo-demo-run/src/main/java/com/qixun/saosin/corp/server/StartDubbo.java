package com.qixun.saosin.corp.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by saosinwork on 2018/2/8.
 */
public class StartDubbo {

    private static ApplicationContext appCtx = null;

    public static void main(String[] args)throws Exception{

        appCtx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        MainServer mainServer = (MainServer)appCtx.getBean("mainServerInstance");
        mainServer.running();


    }
}
