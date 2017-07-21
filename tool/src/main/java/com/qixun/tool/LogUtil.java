package com.qixun.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by saosinwork on 2017/7/21.
 */
public class LogUtil {

    //仅仅需要在pom文件中引入logback-classic的jar包会自动引入slf4j-api和logback-core的jar包，然后配置logback.xml即可
    //然后就可以像本例这样非常开心的开始记录日志了，性能完全不需要担心就是了。
    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    public static void INFO(String message){

        logger.info(message);
    }

    public static void main(String[] args){

        INFO("hello world");
    }
}
