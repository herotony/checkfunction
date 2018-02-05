package com.qixun.saosin.corp.tradedubboservice;

import com.qixun.saosin.corp.trademodel.TradeDO;

/**
 * Created by saosinwork on 2018/2/5.
 */
public interface TradeService {

    Boolean submitTrade(TradeDO tradeDO);

    Boolean refundTrade(String tradeNo);

    TradeDO queryTrade(String tradeNo);
}
