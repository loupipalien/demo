package com.ltchen.demo.ldap.service;

import java.util.List;

import com.ltchen.demo.common.bean.Group;

public interface GroupService {

	/**
	 * 添加用户组
	 * @param rdn 相对区别名 
	 * @param group 用户组实例
	 */
	void add(String rdn, Group group);
	
	/**
	 * 根据rdn删除用户组
	 * @param rdn 相对区别名
	 */
	void delete(String rdn);
	/**
	 * 更新用户组
	 * @param rdn 相对区别名
	 * @param group 用户组实例
	 */
	void update(String rdn, Group group);
	
	/**
	 * 根据rdn查找用户组
	 * @param rdn 相对区别名
	 * @return
	 */
	Group find(String rdn);
	
	/**
	 * 重命名rdn
	 * @param oldRdn 旧相对区别名
	 * @param newRdn 新相对区别名
	 */
	void rename(String oldRdn, String newRdn);
	
	/**
	 * 根据rdn和过滤条件搜索用户组
	 * @param rdn 相对区别名
	 * @param filter 过滤条件
	 * @return
	 */
	List<Group> search(String rdn, String filter);
	
	/**
	 * 将指定用户添加到指定用户组
	 * @param userRdn 用户实例
	 * @param groupRdn 用户组实例
	 */
	void addUserToGroup(String userRdn, String groupRdn);
	
	/**
	 * 将指定用户从指定用户组中删除
	 * @param userRdn 用户实例
	 * @param groupRdn 用户组实例
	 */
	void removeUserFromGroup(String userRdn, String groupRdn);
}
