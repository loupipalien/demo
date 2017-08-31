package com.ltchen.demo.mongodb.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.ltchen.demo.mongodb.bean.User;
import com.ltchen.demo.mongodb.dao.UserDao;

@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao{

	@Autowired private MongoTemplate mongoTemplate; 
	
	@Override
	public List<User> selectAll() {
		//return mongoTemplate.find(new Query(), User.class);
		return mongoTemplate.findAll(User.class);
	}

	@Override
	public User select(String id) {
		return mongoTemplate.findById(id, User.class);
	}

	@Override
	public void insert(User user) {
		mongoTemplate.insert(user);
	}

	@Override
	public void update(User user) {
		//mongoTemplate.updateFirst(new Query(Criteria.where("id").is(user.getId())), new Update(), User.class);
		mongoTemplate.findAndModify(new Query(Criteria.where("id").is(user.getId())), 
				new Update().set("username", user.getUsername())
							.set("password", user.getPassword())
							.set("telephone", user.getTelephone())
							.set("email", user.getEmail())
							.set("description", user.getDescription()), User.class);
	}

	@Override
	public void delete(String id) {
		//mongoTemplate.remove(id);
		mongoTemplate.remove(new Query(Criteria.where("id").is(id)), User.class);
	}

}
