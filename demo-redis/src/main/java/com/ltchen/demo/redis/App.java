package com.ltchen.demo.redis;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ltchen.demo.redis.bean.User;
import com.ltchen.demo.redis.service.UserService;

public class App {
	
	private UserService userService;
	
    public void addUser(User user){
    	System.out.println(user);
    	userService.add(user);
    }
    
    public void getUser(String id){
    	User user = userService.get(id);
    	System.out.println(user);
    }
    
    public void updateUser(User user){
    	System.out.println(user);
    	userService.update(user);
    }
	
    public void removeUser(String id){
    	userService.remove(id);
    }
	
    public static void main( String[] args ){
    	ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring-redis.xml");
    	context.start();
    	App app = new App();
    	app.userService = (UserService)context.getBean("userServiceImpl");
    	app.addUser(new User("1","chenlantian", "123456", "18320896212", "loupipalien@gmail.com", "Alele"));
    	app.updateUser(new User("1","chenlantian", "123456", "18320896212", "loupipalien@gmail.com", "Ohaha"));
    	app.getUser("1");
//    	app.removeUser("1");
//    	app.getUser("1");
    	context.close();
    }
}
