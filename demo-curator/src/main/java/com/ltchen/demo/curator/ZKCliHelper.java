package com.ltchen.demo.curator;

import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

public class ZKCliHelper {

	private String zkConnStr;
	private CuratorFramework client;
	
	public String getZkConnStr() {
		return zkConnStr;
	}

	public void setZkConnStr(String zkConnStr) {
		this.zkConnStr = zkConnStr;
	}

	public CuratorFramework getClient() {
		return client;
	}
	
	public void init(){
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
//		client = CuratorFrameworkFactory.newClient(zkConnStr, retryPolicy);
		client = CuratorFrameworkFactory.builder().connectString(zkConnStr)  
		        .sessionTimeoutMs(30000)  
		        .connectionTimeoutMs(30000)  
		        .retryPolicy(retryPolicy)  
		        .build();  
		client.start();
	}
	
	public void destory(){
		if(client != null){
			client.close();
		}
	}

	public CuratorFramework getClientWithOptions(String zkConnStr, RetryPolicy retryPolicy, int connTimeoutMs, int sessionTimeoutMs) {
		return CuratorFrameworkFactory.builder()
				.connectString(zkConnStr)
				.retryPolicy(retryPolicy)
				.connectionTimeoutMs(connTimeoutMs)
				.sessionTimeoutMs(sessionTimeoutMs)
				.build();
	}
	
	//创建路径(默认父目录不存在时创建)
	public void createPath(String path, String data ,CreateMode createMode,List<ACL> aclList){
		try {
			client.create()
			.creatingParentsIfNeeded()//如果父目录不存在则创建
			.withMode(createMode)
			.withACL(aclList)
			.inBackground()
			.forPath(path, data.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//创建路径,aclList为ZooDefs.Ids.OPEN_ACL_UNSAFE(默认父目录不存在时创建)
	public void createPath(String path, String data ,CreateMode createMode){
		createPath(path, data, createMode, ZooDefs.Ids.OPEN_ACL_UNSAFE);
	}
	
	//创建路径,createMode为CreateMode.EPHEMERAL_SEQUENTIAL,aclList为ZooDefs.Ids.OPEN_ACL_UNSAFE(默认父目录不存在时创建)
	public void createPath(String path, String data){
		createPath(path, data, CreateMode.EPHEMERAL_SEQUENTIAL, ZooDefs.Ids.OPEN_ACL_UNSAFE);
	}
	
	//获取节点值
	public String getData(String path){
		byte[] bytes = null;
		try {
			bytes = client.getData().inBackground().forPath(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(bytes);
	}
	
	//设置路径的值
	public void setData(String path,String data){
		try {
			client.setData().inBackground().forPath(path, data.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//删除路径(默认删除子路径)
	public void deletePath(String path){
		try {
			client.delete()
			.deletingChildrenIfNeeded()//如果目录下存在子路径
			.inBackground()
			.forPath(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//检查路径是否存在
	public boolean isExists(String path){
		boolean flag = false;
		try {
			Stat stat = client.checkExists().inBackground().forPath(path);
			if(stat != null){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
