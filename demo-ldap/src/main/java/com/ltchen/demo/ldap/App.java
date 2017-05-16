package com.ltchen.demo.ldap;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ltchen.demo.common.bean.User;
import com.ltchen.demo.ldap.dao.UserDao;
import com.ltchen.demo.ldap.dao.impl.UserDaoImpl;

public class App {
	
    public static void add(UserDao userDao, String rdn, User user){
    	System.out.println(user);
        userDao.add(rdn, user);
    }
	
    public static void delete(UserDao userDao, String rdn){
        userDao.delete(rdn);
    }
    
    public static void update(UserDao userDao, String rdn, User user){
    	System.out.println(user);
        userDao.update(rdn, user);
    }
    
    public static void find(UserDao userDao, String rdn){
        User user = userDao.find(rdn);
        System.out.println(user);
    }
    
    public static void rename(UserDao userDao, String oldRdn, String newRdn){
        userDao.rename(oldRdn, newRdn);
    }
    
    public static void search(UserDao userDao, String rdn, String filter){
        List<User> users = userDao.search(rdn, filter);
        for (User user : users) {
        	System.out.println(user);
		}
    }
    
    public static void main( String[] args ){
    	ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("ldap/spring-ldap.xml");
    	context.start();
        UserDao userDao = (UserDaoImpl)context.getBean("userDao");
//        find(userDao,"uid=3,ou=people");
//        delete(userDao,"uid=0,ou=people");	
//        update(userDao, "uid=2,ou=people", new User("ltchen", "chenlantian", "123456", "18320896212", "loupipalien@gmail.com", "Aha"));
//        add(userDao, "uid=3,ou=people", new User(3, "ltchen", "chenlantian", "123456", "18320896212", "loupipalien@gmail.com", "Alele"));
//        rename(userDao, "uid=1,ou=people", "uid=3,ou=people");
//        search(userDao, "ou=people", "(objectClass=*)");
        context.close();
    }
}
