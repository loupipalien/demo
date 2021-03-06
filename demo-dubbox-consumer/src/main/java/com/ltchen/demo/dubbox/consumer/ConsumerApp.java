package com.ltchen.demo.dubbox.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ltchen.demo.dubbox.api.service.GroupService;
import com.ltchen.demo.dubbox.api.service.UserService;

public class ConsumerApp {

	public static void main(String[] args){  
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "demo-dubbox-consumer.xml" });  
        context.start(); 
        
        UserService userService = (UserService) context.getBean("userService");  
        System.out.println(userService.getAll());
        
        GroupService groupService = (GroupService) context.getBean("groupService");  
        System.out.println(groupService.getAll());
        
        context.close();  
    }
}
