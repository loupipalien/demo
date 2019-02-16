package com.ltchen.demo.dubbo.provider;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProviderApp {

	private static Logger LOG = LoggerFactory.getLogger(ProviderApp.class);
	
	public static void main(String[] args) throws IOException {  
		  
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext( new String[] { "applicationContext.xml" });
		context.start();  
        try {
        	LOG.info("demo-dubbo-provider is starting...");
	        while(true){
					Thread.sleep(5000L);
	        }
        } catch (InterruptedException e) {
        	LOG.error("InterruptedException: ",e);
        } finally {
        	context.close();
        	LOG.info("demo-dubbo-provider is ending...");
		}
    } 
}
