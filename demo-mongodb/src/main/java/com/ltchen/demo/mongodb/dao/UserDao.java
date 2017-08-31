package com.ltchen.demo.mongodb.dao;

import java.util.List;

import com.ltchen.demo.mongodb.bean.User;

public interface UserDao {

	List<User> selectAll();
	
	User select(String id);
	
	void insert(User user);
	
	void update(User user);
	
	void delete(String id);
}
