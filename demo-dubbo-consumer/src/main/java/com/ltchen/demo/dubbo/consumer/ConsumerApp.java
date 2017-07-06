package com.ltchen.demo.dubbo.consumer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ltchen.demo.dubbo.api.service.DemoService;
import com.ltchen.demo.dubbo.api.service.WebHdfsService;
import com.ltchen.demo.dubbo.exception.HdfsRestApiException;

public class ConsumerApp {

	private static Logger LOG = LoggerFactory.getLogger(ConsumerApp.class);
	
	public static void main(String[] args) throws HdfsRestApiException, IOException {  
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "demo-dubbo-consumer.xml" });  
        context.start(); 
        
//        DemoService demoService = (DemoService) context.getBean("demoService");  
//        LOG.info(demoService.sayHello("World"));
        
        WebHdfsService webHdfsService = (WebHdfsService) context.getBean("webHdfsService");  
        webHdfsService.uploadFile("/tmp", new File("D:\\Logs\\LVS配置.txt"));
//        InputStream is = new FileInputStream(new File("D:\\Logs\\LVS配置.txt"));
//        byte[] bytes = new byte[is.available()];
//        is.read(bytes);
//        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
//        webHdfsService.uploadFile("/tmp",bais);
        context.close();  
    }
}
