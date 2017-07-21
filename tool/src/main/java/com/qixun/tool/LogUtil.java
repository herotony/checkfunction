package com.qixun.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by saosinwork on 2017/7/21.
 */
public class LogUtil {

    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    public static void INFO(String message){

        logger.info(message);
    }

    public static void main(String[] args){

        INFO("hello world");
    }
}
