package com.qixun.saosin.corp.trademodel;

import java.io.Serializable;

/**
 * Created by saosinwork on 2018/2/5.
 */
public class TradeDO implements Serializable {

    private String tradeNo;
    private String thirdTradeNo;
    private String tradeMoney;
    private String tradePayTime;
    private int payType;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getThirdTradeNo() {
        return thirdTradeNo;
    }

    public void setThirdTradeNo(String thirdTradeNo) {
        this.thirdTradeNo = thirdTradeNo;
    }

    public String getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(String tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public String getTradePayTime() {
        return tradePayTime;
    }

    public void setTradePayTime(String tradePayTime) {
        this.tradePayTime = tradePayTime;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

}
