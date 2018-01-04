package com.qixun.model;

/**
 * Created by saosinwork on 2018/1/4.
 */
public enum PAYTYPE {

    WFT_BOTH_C_SCAN_B(88,"9,12,16","威富通主扫业务",true,true),
    WFT_B_SCAN_C_ALIPAY(76,"威富通大商户支付宝B扫C",true,false),
    WFT_B_SCAN_C_WX(77,"威富通大商户微信B扫C",false,true);

    private int code;
    private String description;
    private String orderValue;

    public String getOrderValue() {
        return orderValue;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAlipay() {
        return isAlipay;
    }

    public boolean isWeChat() {
        return isWeChat;
    }

    private boolean isAlipay;
    private boolean isWeChat;

    private PAYTYPE(int code,String desc,boolean isAlipay,boolean isWeChat){
        this.code = code;
        this.orderValue = String.valueOf(code);
        this.description = desc;
        this.isAlipay = isAlipay;
        this.isWeChat = isWeChat;
    }

    private PAYTYPE(int code,String orderValue,String desc,boolean isAlipay,boolean isWeChat){
        this.code = code;
        this.orderValue=orderValue;
        this.description = desc;
        this.isAlipay = isAlipay;
        this.isWeChat = isWeChat;
    }

    public static PAYTYPE ValueOf(int value){

        for (PAYTYPE paytype: PAYTYPE.values()) {
            if(value == paytype.getCode()) {
                return paytype;
            }
        }

        return null;
    }
}
