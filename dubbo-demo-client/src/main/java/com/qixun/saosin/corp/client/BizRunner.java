package com.qixun.saosin.corp.client;

import com.qixun.saosin.corp.tradedubboservice.TradeService;
import com.qixun.saosin.corp.trademodel.TradeDO;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by saosinwork on 2018/2/6.
 */
public class BizRunner implements Runnable {

    private TradeService tradeService;
    private AtomicInteger runningCount;
    private int index;
    private Logger logger = LoggerFactory.getLogger(BizRunner.class);

    public BizRunner(TradeService tradeService, AtomicInteger runningCount,int index){
        this.tradeService = tradeService;
        this.runningCount = runningCount;
        this.index = index;
    }

    public void run(){

        long startTime = System.currentTimeMillis();
        try{

            runningCount.getAndIncrement();

            TradeDO tradeDO =  tradeService.queryTrade("trade_"+index);

            ObjectMapper objectMapper = new ObjectMapper();

            String jsonStr = objectMapper.writeValueAsString(tradeDO);

            logger.info(Thread.currentThread().getName()+"'s result:"+jsonStr);



        }catch(Exception runErr){

            logger.error("runningCount:"+runningCount+",occur error:"+runErr.getMessage()+"\nstackTrace:"+runErr.getStackTrace());
        }finally {

            runningCount.getAndDecrement();
            logger.info("runningCount:"+runningCount+" consume time:"+(System.currentTimeMillis()-startTime)+"ms");
        }
    }

}
