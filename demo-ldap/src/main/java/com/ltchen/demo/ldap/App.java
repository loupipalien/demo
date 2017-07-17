package com.ltchen.demo.ldap;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ltchen.demo.ldap.bean.Group;
import com.ltchen.demo.ldap.bean.User;
import com.ltchen.demo.ldap.service.GroupService;
import com.ltchen.demo.ldap.service.UserService;

public class App {
	
	private UserService userService;
    public void addUser(String rdn, User user){
    	System.out.println(user);
    	userService.add(rdn, user);
    }
	
    public void deleteUser(String rdn){
    	userService.remove(rdn);
    }
    
    public void updateUser(String rdn, User user){
    	System.out.println(user);
    	userService.update(rdn, user);
    }
    
    public void findUser(String rdn){
        User user = userService.find(rdn);
        System.out.println(user);
    }
    
    public void renameUser(String oldRdn, String newRdn){
    	userService.rename(oldRdn, newRdn);
    }
    
    public void searchUser(String rdn, String filter){
        List<User> users = userService.search(rdn, filter);
        for (User user : users) {
        	System.out.println(user);
		}
    }
    
    private GroupService groupService;
    public void addGroup(String rdn, Group group){
    	System.out.println(group);
    	groupService.add(rdn, group);
    }
	
    public void deleteGroup(String rdn){
    	groupService.remove(rdn);
    }
    
    public void updateGroup(String rdn, Group group){
    	System.out.println(group);
    	groupService.update(rdn, group);
    }
    
    public void findGroup(String rdn){
    	Group group = groupService.find(rdn);
        System.out.println(group);
    }
    
    public void renameGroup(String oldRdn, String newRdn){
    	groupService.rename(oldRdn, newRdn);
    }
    
    public void searchGroup(String rdn, String filter){
        List<Group> groups = groupService.search(rdn, filter);
        for (Group group : groups) {
        	System.out.println(group);
		}
    }
    
    public void addUser(String userRdn, String groupRdn){
    	groupService.addUser(userRdn, groupRdn);
    }
    
    public void removeUser(String userRdn, String groupRdn){
    	groupService.removeUser(userRdn, groupRdn);
    }
    
    public static void main( String[] args ){
    	ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring-ldap.xml");
    	context.start();
    	App app = new App();
    	app.userService = (UserService)context.getBean("userServiceImpl");
//    	app.addUser("uid=3,ou=people", new User(3, "ltchen", "chenlantian", "123456", "18320896212", "loupipalien@gmail.com", "Alele"));
//    	app.deleteUser("uid=3,ou=people");	
//    	app.findUser("uid=3,ou=people");
//    	app.updateUser("uid=2,ou=people", new User("ltchen", "chenlantian", "123456", "18320896212", "loupipalien@gmail.com", "Aha"));
//    	app.renameUser("uid=4,ou=people", "uid=3,ou=people");
//    	app.searchUser("ou=people", "(objectClass=inetOrgPerson)");
    	app.groupService = (GroupService)context.getBean("groupServiceImpl");
//    	app.addGroup("cn=test,ou=group", new Group("test", "Ohaha"));
//    	app.deleteGroup("cn=test,ou=group");
//    	app.findGroup("cn=test,ou=group");
//    	app.updateGroup("cn=ltchen,ou=group", new Group("ltchen", "haha"));
//    	app.renameGroup("cn=ltchen,ou=group", "cn=loupipalien,ou=group");
//    	app.searchGroup("ou=group", "(objectClass=groupOfNames)");
//    	app.addUser("uid=3,ou=people","cn=ltchen,ou=group");
//    	app.removeUser("uid=4,ou=people","cn=ltchen,ou=group");
        context.close();
    }
}
