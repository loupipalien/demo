package com.ltchen.demo.ldap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltchen.demo.common.bean.User;
import com.ltchen.demo.ldap.dao.UserDao;
import com.ltchen.demo.ldap.service.UserService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public void add(String rdn, User user) {
		userDao.add(rdn, user);

	}

	@Override
	public void delete(String rdn) {
		//TODO 删除用户时,同时也删除用户组中的此用户
		userDao.delete(rdn);

	}

	@Override
	public void update(String rdn, User user) {
		userDao.update(rdn, user);
	}

	@Override
	public User find(String rdn) {
		return userDao.find(rdn);
	}

	@Override
	public void rename(String oldRdn, String newRdn) {
		//TODO 重命名用户,同时也重命名用户组中的此用户
		userDao.rename(oldRdn, newRdn);
	}

	@Override
	public List<User> search(String rdn, String filter) {
		return userDao.search(rdn, filter);
	}

}
