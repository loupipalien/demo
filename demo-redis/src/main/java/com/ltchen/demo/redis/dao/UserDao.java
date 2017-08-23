package com.ltchen.demo.redis.dao;

import java.util.List;

import com.ltchen.demo.redis.bean.User;

public interface UserDao {

	List<User> selectAll();
	
	User select(String id);
	
	void insert(User user);
	
	void update(User user);
	
	void delete(String id);
}
