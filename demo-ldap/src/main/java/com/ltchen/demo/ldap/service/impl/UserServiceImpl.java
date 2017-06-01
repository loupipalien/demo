package com.ltchen.demo.ldap.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltchen.demo.common.bean.Group;
import com.ltchen.demo.common.bean.User;
import com.ltchen.demo.ldap.dao.GroupDao;
import com.ltchen.demo.ldap.dao.UserDao;
import com.ltchen.demo.ldap.service.UserService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private GroupDao groupDao;
	
	@Override
	public void add(String userRdn, User user) {
		userDao.add(userRdn, user);

	}

	@Override
	public void delete(String userRdn) {
		//TODO 删除用户时,同时也删除用户组中的此用户
		userDao.delete(userRdn);

	}

	@Override
	public void update(String userRdn, User user) {
		userDao.update(userRdn, user);
	}

	@Override
	public User find(String userRdn) {
		User user = userDao.find(userRdn);
		user.setGroups(this.getGroups(userRdn));
		return user;
	}

	@Override
	public void rename(String oldUserRdn, String newUserRdn) {
		//TODO 重命名用户,同时也重命名用户组中的此用户
		userDao.rename(oldUserRdn, newUserRdn);
	}

	@Override
	public List<User> search(String userRdn, String filter) {
		return userDao.search(userRdn, filter);
	}

	private List<Group> getGroups(String userRdn){
		List<Group> groups = new ArrayList<Group>();
		User user = this.find(userRdn);
		//获取所有用户组
		List<Group> allGroup = groupDao.search("ou=group", "(objectClass=*)");
		for (Group group : allGroup) {
			//添加所属的用户组
			if(group.getUsers().contains(user)){
				groups.add(group);
			}
		}
		return groups;
	}
}
