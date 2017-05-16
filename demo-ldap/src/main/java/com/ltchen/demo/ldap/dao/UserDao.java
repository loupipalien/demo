package com.ltchen.demo.ldap.dao;

import java.util.List;

import com.ltchen.demo.common.bean.User;

public interface UserDao {

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
	 * @param oldRdn
	 * @param newRdn
	 */
	void rename(String oldRdn, String newRdn);
	
	/**
	 * 根据过滤条件搜索rdn
	 * @param rdn 相对区别名
	 * @param filter 过滤条件
	 * @return
	 */
	List<User> search(String rdn, String filter);
}
