package com.ltchen.demo.mongodb.service;

import java.util.List;

import com.ltchen.demo.mongodb.bean.User;

public interface UserService {

	List<User> getAll();
	
	User get(String id);
	
	void add(User user);
	
	void update(User user);
	
	void remove(String id);
}
