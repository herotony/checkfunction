package com.qixun.saosin.corp.server;

import com.qixun.saosin.corp.tradedubboservice.TradeService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.ws.FaultAction;

/**
 * Created by saosinwork on 2018/2/6.
 */
public class MainServer {

    @Autowired
    private TradeService tradeServiceImpl;

    public void running()throws  Exception{

        while(true){

            int value =  System.in.read();
            System.out.println("u input key:"+value);
        }
    }
}
