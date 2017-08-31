package com.ltchen.demo.mongodb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltchen.demo.mongodb.bean.User;
import com.ltchen.demo.mongodb.dao.UserDao;
import com.ltchen.demo.mongodb.service.UserService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

	@Autowired private UserDao userDao;
	
	@Override
	public List<User> getAll() {
		return userDao.selectAll();
	}

	@Override
	public User get(String id) {
		return userDao.select(id);
	}

	@Override
	public void add(User user) {
		userDao.insert(user);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public void remove(String id) {
		userDao.delete(id);
	}

}
