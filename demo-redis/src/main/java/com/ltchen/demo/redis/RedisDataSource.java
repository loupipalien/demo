package com.ltchen.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Repository("redisDataSource")
public class RedisDataSource {

	@Autowired private ShardedJedisPool shardedJedisPool;
	
    public ShardedJedis getResource() {
        try {
        	return shardedJedisPool.getResource();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void returnResource(ShardedJedis shardedJedis) {
    	shardedJedis.close();
    	
    }

}
