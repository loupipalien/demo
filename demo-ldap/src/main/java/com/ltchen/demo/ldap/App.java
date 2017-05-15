package com.ltchen.demo.ldap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ltchen.demo.common.bean.User;
import com.ltchen.demo.ldap.dao.UserDao;
import com.ltchen.demo.ldap.dao.impl.UserDaoImpl;

public class App {
	
    public static void find(UserDao userDao){
        User user = userDao.find("uid=1, ou=people");
        System.out.println(user.getId() + " | " + user.getUsername());
    }
    
    public static void main( String[] args ){
    	ApplicationContext ac=new ClassPathXmlApplicationContext("ldap/spring-ldap.xml");
        UserDao userDao = (UserDaoImpl)ac.getBean("userDao");
        System.out.println(userDao);
        find(userDao);
//        System.out.println( "Hello World!" );
    }
}
