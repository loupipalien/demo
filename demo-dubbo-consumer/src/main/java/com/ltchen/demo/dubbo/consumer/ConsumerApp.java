package com.ltchen.demo.dubbo.consumer;

import com.ltchen.demo.dubbo.api.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerApp {

	private static Logger LOG = LoggerFactory.getLogger(ConsumerApp.class);
	
	public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "demo-dubbo-consumer.xml" });  
        context.start(); 
        
        DemoService demoService = (DemoService) context.getBean("demoService");
        LOG.info(demoService.sayHello("World"));

        context.close();  
    }
}
