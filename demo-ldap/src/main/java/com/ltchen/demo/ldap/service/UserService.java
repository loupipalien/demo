package com.ltchen.demo.ldap.service;

import java.util.List;

import com.ltchen.demo.common.bean.User;

public interface UserService {

	/**
	 * 添加用户
	 * @param rdn 相对区别名
	 * @param user 用户实例
	 */
	void add(String rdn, User user);
	
	/**
	 * 根据rdn删除用户
	 * @param rdn 相对区别名
	 */
	void delete(String rdn);
	
	/**
	 * 更新用户
	 * @param rdn 相对区别名
	 * @param user 用户实例
	 */
	void update(String rdn, User user);
	
	/**
	 * 根据rdn查找用户
	 * @param rdn 相对区别名
	 * @return
	 */
	User find(String rdn);
	
	/**
	 * 重命名rdn
	 * @param oldRdn 旧相对区别名
	 * @param newRdn 新相对区别名
	 */
	void rename(String oldRdn, String newRdn);
	
	/**
	 * 根据rdn和过滤条件搜索用户
	 * @param rdn 相对区别名
	 * @param filter 过滤条件
	 * @return
	 */
	List<User> search(String rdn, String filter);
}
