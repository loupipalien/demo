package com.ltchen.demo.dubbox.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProviderApp {

	public static void main(String[] args){  
		  
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext( new String[] { "application.xml" });
		context.start();  
        try {
        	System.out.println("demo-dubbox-provider is starting...");
	        while(true){
					Thread.sleep(5000L);
	        }
        } catch (InterruptedException e) {
        	System.err.println(e);
        } finally {
        	context.close();
        	System.out.println("demo-dubbox-provider is ending...");
		}
    } 
}
