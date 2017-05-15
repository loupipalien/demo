package com.ltchen.demo.ldap.dao.impl;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Component;

import com.ltchen.demo.common.bean.User;
import com.ltchen.demo.ldap.dao.UserDao;

@Component("userDaoImpl")
public class UserDaoImpl implements UserDao{

	private LdapTemplate ldapTemplate;
	
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
	    this.ldapTemplate = ldapTemplate;
	}
	
	@Override
	public User find(String rdn) {
        return (User)this.ldapTemplate.lookup(rdn, new AttributesMapper<User>(){
            public User mapFromAttributes(Attributes attributes)throws NamingException {
                User user = new User();
                if(attributes!=null){
                    Attribute idAttr = attributes.get("uid");
                    if(idAttr!=null){
                        user.setId(Integer.valueOf((String) idAttr.get()));
                    }
                    Attribute usernameAttr = attributes.get("cn");
                    if(usernameAttr!=null){
                        user.setUsername((String)usernameAttr.get());
                    }
                }
                return user;
            }
        });
	}
}
