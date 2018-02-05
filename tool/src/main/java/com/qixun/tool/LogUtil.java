package com.qixun.tool;

import com.qixun.model.PAYTYPE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by saosinwork on 2017/7/21.
 */
public class LogUtil {

    //仅仅需要在pom文件中引入logback-classic的jar包会自动引入slf4j-api和logback-core的jar包，然后配置logback.xml即可
    //然后就可以像本例这样非常开心的开始记录日志了，性能完全不需要担心就是了。
    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    private static ConcurrentHashMap<String,Boolean> testHashMap = new ConcurrentHashMap<String, Boolean>();

    public static void INFO(String message){

        logger.info(message);
    }

    public static void main(String[] args){


        List<String> listData = new ArrayList<String>();

        for(int i=0;i<10;i++){

            String orderId = "W18"+ UUID.randomUUID().toString().replace("-","");
            listData.add(orderId);
        }

        Collections.sort(listData);

        int j= listData.size();

        for(int i=listData.size()-1;i>=0;i--){
            System.out.println(listData.get(i));
        }


        INFO("hello world");
        Long currentTime = Long.parseLong("1514649600295");
        Date date = new Date(currentTime);
        SimpleDateFormat sdf  = new SimpleDateFormat("yyMMddHH");//"YYMMddHH"在跨年时出错，会181231
        SimpleDateFormat sdfII = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataf = sdf.format(date);
        String dataF = sdfII.format(date).replace("-","").replace(":","").replace(" ","");

        System.out.println(dataf);
        System.out.println(dataF.substring(0,10));
        System.out.println(date);

        PAYTYPE paytype = PAYTYPE.WFT_BOTH_C_SCAN_B;

        PAYTYPE paytype_test = PAYTYPE.ValueOf(paytype.getCode());
        System.out.println(paytype_test.getDescription());
        System.out.println(paytype_test.getOrderValue());


        if(true){

            for(int i=0;i<3000000;i++){

                String orderId = "W180109"+i;
                testHashMap.put(orderId,true);
            }

            System.out.println("==========");
            System.out.println(testHashMap.size());
        }
    }
}
