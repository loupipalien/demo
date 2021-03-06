package com.ltchen.demo.spring.mvc.bean;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Spittle {

	private final Long id;
	private final String message;
	private final Date time;
	private Double latitude;
	
	private Double longitude;
	public Long getId() {
		return id;
	}
	public String getMessage() {
		return message;
	}
	public Date getTime() {
		return time;
	}
	public Double getLatitude() {
		return latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	
	public Spittle(String message,Date time){
		this(message,time,null,null);
	}
	
	public Spittle(String messgae,Date time,Double latitude,Double longitude){
		this.id = null;
		this.message = messgae;
		this.time = time;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	@Override
	public boolean equals(Object that) {
		return EqualsBuilder.reflectionEquals(this, that, "id","time");
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "id","time");
	}
}
