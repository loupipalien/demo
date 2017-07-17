package com.ltchen.demo.dubbox.api.service;

import java.util.List;

import com.ltchen.demo.dubbox.api.bean.Group;

public interface GroupService {

	/**
	 * 获取所有用户组
	 * @return
	 */
	List<Group> getAll();
	
	/**
	 * 获取指定用户组
	 * @param id
	 * @return
	 */
	Group get(String id);
	
	/**
	 * 添加用户组
	 * @param group
	 * @return
	 */
	Group add(Group group);
	
	/**
	 * 更新用户组
	 * @param group
	 * @return
	 */
	Group update(Group group);
	
	/**
	 * 删除用户组
	 * @param id
	 */
	void delete(String id);
}
