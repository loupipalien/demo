package com.ltchen.demo.spring.mvc.bean;

import java.util.List;

public interface SpittleRepository {

	List<Spittle> findSpittles(long max,int count);

	Spittle findOne(long spittleId);
}
