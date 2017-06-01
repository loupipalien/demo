package com.ltchen.demo.ldap.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Component;

import com.ltchen.demo.common.bean.User;
import com.ltchen.demo.common.util.StringUtil;
import com.ltchen.demo.ldap.dao.UserDao;

@Component("userDaoImpl")
public class UserDaoImpl implements UserDao{

	@Autowired
	private LdapTemplate ldapTemplate;
	
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
	    this.ldapTemplate = ldapTemplate;
	}

	@Override
	public void add(String userRdn, User user) {
		Attributes attrs = new BasicAttributes();
		attrs.put("objectClass", "inetOrgPerson");
		attrs.put("uid", String.valueOf(user.getId()));
		attrs.put("cn", user.getAlias());
		attrs.put("sn", user.getUsername());
		attrs.put("userPassword", user.getPassword());
		attrs.put("telephoneNumber", user.getTelephone());
		attrs.put("mail", user.getEmail());
		attrs.put("description", user.getDescription());
		this.ldapTemplate.bind(userRdn, null, attrs);
	}
	
	@Override
	public void delete(String userRdn) {
		this.ldapTemplate.unbind(userRdn);
	}

	@Override
	public void update(String userRdn, User user) {
		List<ModificationItem> modificationItems = new ArrayList<ModificationItem>();
		if(StringUtil.isNotBlank(user.getAlias())){
			Attribute aliasAttr = new BasicAttribute("cn", user.getAlias());
			ModificationItem modificationItem = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, aliasAttr);
			modificationItems.add(modificationItem);
		}
		if(StringUtil.isNotBlank(user.getUsername())){
			Attribute usernameAttr = new BasicAttribute("sn", user.getUsername());
			ModificationItem modificationItem = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, usernameAttr);
			modificationItems.add(modificationItem);
		}
		if(StringUtil.isNotBlank(user.getPassword())){
			Attribute passwordAttr = new BasicAttribute("userPassword", user.getPassword());
			ModificationItem modificationItem = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, passwordAttr);
			modificationItems.add(modificationItem);
		}
		if(StringUtil.isNotBlank(user.getTelephone())){
			Attribute telephoneAttr = new BasicAttribute("telephoneNumber", user.getTelephone());
			ModificationItem modificationItem = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, telephoneAttr);
			modificationItems.add(modificationItem);
		}
		if(StringUtil.isNotBlank(user.getEmail())){
			Attribute emailAttr = new BasicAttribute("mail", user.getEmail());
			ModificationItem modificationItem = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, emailAttr);
			modificationItems.add(modificationItem);
		}
		if(StringUtil.isNotBlank(user.getDescription())){
			Attribute descriptionAttr = new BasicAttribute("description", user.getDescription());
			ModificationItem modificationItem = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, descriptionAttr);
			modificationItems.add(modificationItem);
		}
		this.ldapTemplate.modifyAttributes(userRdn, modificationItems.toArray(new ModificationItem[modificationItems.size()]));
	}

	@Override
	public User find(String userRdn) {
        return this.ldapTemplate.lookup(userRdn, new AttributesMapper<User>(){
        	@Override
        	public User mapFromAttributes(Attributes attributes)throws NamingException {
            	return convert(attributes);
            }
        });
	}
	
	@Override
	public void rename(String oldUserRdn, String newUserRdn) {
		this.ldapTemplate.rename(oldUserRdn, newUserRdn);
	}

	@Override
	public List<User> search(String userRdn, String filter) {
		return this.ldapTemplate.search(userRdn, filter, new AttributesMapper<User>(){
			@Override
			 public User mapFromAttributes(Attributes attributes)throws NamingException {
				return convert(attributes);
             }
		});
	}

	@Override
	public String getBaseDn() {
		LdapContextSource lcs= (LdapContextSource)ldapTemplate.getContextSource();
		return lcs.getBaseLdapPathAsString();
	}
	
	/**
	 * 将Attributes对象转换为User对象
	 * @param <T>
	 * @param attributes Attributes对象
	 * @return 
	 * @throws NamingException
	 */
	private <T> User convert(Attributes attributes)throws NamingException{
		User user = new User();
        if(attributes != null){
            Attribute idAttr = attributes.get("uid");
            if(idAttr != null){
                user.setId(Integer.valueOf((String) idAttr.get()));
            }
            Attribute aliasAttr = attributes.get("cn");
            if(aliasAttr != null){
            	user.setAlias((String)aliasAttr.get());
            }
            Attribute usernameAttr = attributes.get("sn");
            if(usernameAttr != null){
                user.setUsername((String)usernameAttr.get());
            }
            Attribute passwordAttr = attributes.get("userPassword");
            if(passwordAttr != null){
            	//将密码替换
            	user.setPassword("******");
//            	try {
//					user.setPassword(new String(toByteArray(passwordAttr.get()),"UTF-8"));
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
            }
            Attribute telephoneAttr = attributes.get("telephoneNumber");
            if(telephoneAttr != null){
            	user.setTelephone((String)telephoneAttr.get());
            }
            Attribute emailAttr = attributes.get("mail");
            if(emailAttr!=null){
            	user.setEmail((String)emailAttr.get());
            }
            Attribute descriptionAttr = attributes.get("description");
            if(descriptionAttr!=null){
            	user.setDescription((String)descriptionAttr.get());
            }
        }
        return user;
	}
	
}
