package com.ltchen.demo.ldap.service;

import java.util.List;

import com.ltchen.demo.common.bean.User;

public interface UserService {

	/**
	 * 获取所有用户
	 * @return
	 */
	List<User> getAll();
	
	/**
	 * 用户组是否存在
	 * @param userRdn 用户组相对区别名 
	 * @return
	 */
	public boolean isExist(String userRdn);
	
	/**
	 * 添加用户
	 * @param userRdn 用户相对区别名
	 * @param user 用户实例
	 */
	void add(String userRdn, User user);
	
	/**
	 * 根据userRdn删除用户
	 * @param userRdn 用户相对区别名
	 */
	void remove(String userRdn);
	
	/**
	 * 更新用户
	 * @param userRdn 用户相对区别名
	 * @param user 用户实例
	 */
	void update(String userRdn, User user);
	
	/**
	 * 根据userRdn查找用户
	 * @param userRdn 用户相对区别名
	 * @return
	 */
	User find(String userRdn);
	
	/**
	 * 重命名userRdn
	 * @param oldUserRdn 旧用户相对区别名
	 * @param newUserRdn 新用户相对区别名
	 */
	void rename(String oldUserRdn, String newUserRdn);
	
	/**
	 * 根据userRdn和过滤条件搜索用户
	 * @param userRdn 用户相对区别名
	 * @param filter 过滤条件
	 * @return
	 */
	List<User> search(String userRdn, String filter);
}
