package com.qixun.saosin.corp.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by saosinwork on 2018/2/8.
 */
public class ClientStart {

    private static ApplicationContext appCtx = null;

    public static void main(String[] args)throws  Exception{

        appCtx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        MainClient mainClient = (MainClient)appCtx.getBean("mainClientInstance");
        mainClient.run();

    }
}
