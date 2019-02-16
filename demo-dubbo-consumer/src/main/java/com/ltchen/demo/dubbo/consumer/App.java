package com.ltchen.demo.dubbo.consumer;

import org.apache.log4j.NDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 * 
 * @file : App.java
 * @date : 2017年10月22日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc :
 */
public class App {
	
	private static Logger logger = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args ) {
    	logger.info("Hello World!");
        System.out.println( "Hello World!" );
        logger.info("Make sure %x is in your layout pattern!");
        // 从客户端获得IP地址的例子
        String[] ips = {"192.168.0.10","192.168.0.27"};
        // 模拟一个运行方法
        for (int i = 0; i<ips.length ; i++)  
        {
         // 将IP放进 NDC中
         NDC.push(ips[i]);
         logger.info("A NEW client connected, who's ip should appear in this log message.");
         NDC.pop();
        }
        NDC.remove();
        logger.info("Finished.");
    }
}