package com.ltchen.demo.redis.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.ltchen.demo.redis.bean.User;
import com.ltchen.demo.redis.dao.UserDao;

@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao{

	@Autowired private RedisTemplate<String,User> redisTemplate; 
	
	@Override
	public List<User> selectAll() {
		return null;
	}

	@Override
	public User select(String id) {
		ValueOperations<String, User> valueOps = redisTemplate.opsForValue();
		User user = valueOps.get(id);
		return user;
	}

	@Override
	public void insert(User user) {
		ValueOperations<String, User> valueOps = redisTemplate.opsForValue();
		valueOps.set(user.getId(), user);
	}

	@Override
	public void update(User user) {
		//insert(user);
		ValueOperations<String, User> valueOps = redisTemplate.opsForValue();
		valueOps.getAndSet(user.getId(), user);
	}

	@Override
	public void delete(String id) {
		ValueOperations<String, User> valueOps = redisTemplate.opsForValue();  
        RedisOperations<String,User>  redisOps = valueOps.getOperations();  
        redisOps.delete(id); 
	}

}
