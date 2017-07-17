package com.ltchen.demo.dubbox.api.service;

import java.util.List;

import com.ltchen.demo.dubbox.api.bean.User;

public interface UserService {

	/**
	 * 获取所有用户
	 * @return
	 */
	List<User> getAll();
	
	/**
	 * 获取指定用户
	 * @param id
	 * @return
	 */
	User get(String id);
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	User add(User user);
	
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	User update(User user);
	
	/**
	 * 删除用户
	 * @param id
	 */
	void delete(String id);
	
}
