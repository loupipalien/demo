package com.ltchen.demo.ldap.dao;

import com.ltchen.demo.common.bean.User;

public interface UserDao {

	/**
	 * 根据相对区别名查找User
	 * @param rdn 相对区别名
	 * @return
	 */
	User find(String rdn);
}
