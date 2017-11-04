package com.ltchen.demo.dubbo.consumer;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.NDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @file : App.java
 * @date : 2017年10月22日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc :
 */
public class App {
	
	private static Logger LOG = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args ) {
    	LOG.info("Hello World!");
        System.out.println( "Hello World!" );
        LOG.info("Make sure %x is in your layout pattern!");
        // 从客户端获得IP地址的例子
        String[] ips = {"192.168.0.10","192.168.0.27"};
        // 模拟一个运行方法
        for (int i = 0; i<ips.length ; i++)  
        {
         // 将IP放进 NDC中
         NDC.push(ips[i]);
         LOG.info("A NEW client connected, who's ip should appear in this log message.");
         NDC.pop();
        }
        NDC.remove();
        LOG.info("Finished.");
    }
}
