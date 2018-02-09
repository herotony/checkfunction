package com.qixun.saosin.corp.client;

import com.qixun.saosin.corp.tradedubboservice.TradeService;
import com.qixun.saosin.corp.trademodel.TradeDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by saosinwork on 2018/2/6.
 */
public class MainClient {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public  void run() throws  Exception{

        AtomicInteger count = new AtomicInteger(0);
        int total = 0;
        while (true){

            int loopSize = 1000;

            for(int i=0;i<loopSize;i++){

                threadPoolTaskExecutor.execute(new BizRunner(tradeService,count,total));
                total++;
            }

            Thread.sleep(1000);
        }
    }

}
