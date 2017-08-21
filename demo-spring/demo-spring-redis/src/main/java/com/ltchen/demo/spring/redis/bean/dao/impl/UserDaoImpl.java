package com.ltchen.demo.spring.redis.bean.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.ltchen.demo.spring.redis.bean.User;
import com.ltchen.demo.spring.redis.bean.dao.UserDao;

@Repository
public class UserDaoImpl implements UserDao{

	@Autowired  
	private RedisTemplate<String,User> redisTemplate; 
	
	@Override
	public List<User> selectAll() {
		return null;
	}

	@Override
	public User select(String id) {
		return null;
	}

}
