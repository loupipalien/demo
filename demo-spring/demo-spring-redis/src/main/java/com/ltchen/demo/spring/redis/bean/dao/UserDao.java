package com.ltchen.demo.spring.redis.bean.dao;

import java.util.List;

import com.ltchen.demo.spring.redis.bean.User;

public interface UserDao {

	List<User> selectAll();
	
	User select(String id);
}
