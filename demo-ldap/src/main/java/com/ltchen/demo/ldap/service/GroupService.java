package com.ltchen.demo.ldap.service;

import java.util.List;

import com.ltchen.demo.ldap.bean.Group;

public interface GroupService {

	/**
	 * 获取所有用户组
	 * @return
	 */
	List<Group> getAll();
	
	/**
	 * 用户组是否存在
	 * @param groupRdn 用户组相对区别名 
	 * @return
	 */
	public boolean isExist(String groupRdn);
	
	/**
	 * 添加用户组
	 * @param groupRdn 用户组相对区别名 
	 * @param group 用户组实例
	 */
	void add(String groupRdn, Group group);
	
	/**
	 * 根据groupRdn删除用户组
	 * @param groupRdn 用户组相对区别名
	 */
	void remove(String groupRdn);
	/**
	 * 更新用户组
	 * @param groupRdn 用户组相对区别名
	 * @param group 用户组实例
	 */
	void update(String groupRdn, Group group);
	
	/**
	 * 根据groupRdn查找用户组
	 * @param groupRdn 用户组相对区别名
	 * @return
	 */
	Group find(String groupRdn);
	
	/**
	 * 重命名groupRdn
	 * @param oldGroupRdn 旧用户组相对区别名
	 * @param newGroupRdn 新用户组相对区别名
	 */
	void rename(String oldGroupRdn, String newGroupRdn);
	
	/**
	 * 根据groupRdn和过滤条件搜索用户组
	 * @param groupRdn 用户组相对区别名
	 * @param filter 过滤条件
	 * @return
	 */
	List<Group> search(String groupRdn, String filter);
	
	/**
	 * 将指定用户添加到指定用户组
	 * @param userRdn 用户实例
	 * @param groupRdn 用户组实例
	 */
	void addUser(String userRdn, String groupRdn);
	
	/**
	 * 将指定用户从指定用户组中删除
	 * @param userRdn 用户实例
	 * @param groupRdn 用户组实例
	 */
	void removeUser(String userRdn, String groupRdn);
	
}
