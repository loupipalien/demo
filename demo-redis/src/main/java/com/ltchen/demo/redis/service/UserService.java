package com.ltchen.demo.redis.service;

import java.util.List;

import com.ltchen.demo.redis.bean.User;

public interface UserService {

	List<User> getAll();
	
	User get(String id);
	
	void add(User user);
	
	void update(User user);
	
	void remove(String id);
}
