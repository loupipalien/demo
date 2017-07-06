package com.ltchen.demo.hadoop.bean;

import java.io.Serializable;
import java.util.Date;

public class HdfsResource implements Serializable{

	private static final long serialVersionUID = 8242201340464880524L;
	
	 // 定义资源类型枚举
    public enum Type {
    	FILE("FILE"),DIRECTORY("DIRECTORY");
    	// 定义私有变量
        private String type;
        // 构造函数，枚举类型只能为私有
        private Type(String type) {
             this.type = type;
        }
        @Override
        public String toString() {
             return String.valueOf(this.type);
        }
        public static Type fromValue(String value) {
    		for (Type type : Type.values()) {
    			if (value.equalsIgnoreCase(type.toString())) {
    				return type;
    			}
    		}
    		throw new IllegalArgumentException("Cannot create evalue from value: " + value + " !");
    	}

    }

	private Type type; 
	//资源名称
	private String name;
	//资源大小
	private String size;
	//资源所属用户
	private String owner;
	//资源所属用户组
	private String group;
	//资源修改时间
	private Date modificationTime;
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public Date getModificationTime() {
		return modificationTime;
	}
	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}
	
	@Override
	public String toString() {
		return "HdfsResource [type=" + type + ", name=" + name + ", size=" + size + ", owner=" + owner + ", group="
				+ group + ", modificationTime=" + modificationTime + "]";
	}
	
}
