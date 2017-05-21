package com.ltchen.demo.ldap.dao;

import java.util.List;

import com.ltchen.demo.common.bean.Group;

public interface GroupDao {

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
	 * @param userDn 用户绝对区别名
	 * @param groupRdn 用户组相对区别名
	 */
	void addMember(String userDn, String groupRdn);
	
	/**
	 * 将指定用户从指定用户组删除
	 * @param userdDn 用户绝对区别名
	 * @param groupRdn 用户组相对区别名
	 */
	void deleteMember(String userDn, String groupRdn);
}
