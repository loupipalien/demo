package com.ltchen.demo.hadoop.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltchen.demo.hadoop.exception.HdfsRestApiException;
import com.ltchen.demo.hadoop.service.WebHdfsService;
import com.ltchen.demo.util.common.PropertyUtil;
import com.ltchen.demo.util.http.HttpClientUtil;

public class WebHdfsServiceImpl implements WebHdfsService {

private static Logger LOG = LoggerFactory.getLogger(WebHdfsServiceImpl.class);
	
	private static Properties prop = null;
	private static String apiUrl = null;
	private static String superUser = null; 
	private static String resourceOwner = null;
	private static String resourceGroup = null;
	private static String defaultFS = null;
	private static String nameservices = null;
	private static String namenodes = null;
	private static String rpc1 = null;
	private static String rpc2 = null;
	private static String provider = null;
	private static Map<String,String> headerMap = null;
	static{
		prop = PropertyUtil.getProperty("config.properties", WebHdfsServiceImpl.class);
		apiUrl = prop.getProperty("webhdfs.rest.api.url");
		superUser =prop.getProperty("hdfs.super.user");
		resourceOwner = prop.getProperty("hdfs.resource.owner");
		resourceGroup =prop.getProperty("hdfs.resource.group");
		defaultFS = prop.getProperty("hdfs.defaultFS");
		nameservices = prop.getProperty("hdfs.nameservices");
		namenodes = prop.getProperty("hdfs.ha.namenodes.ns1");
		rpc1 = prop.getProperty("hdfs.namenode.rpc-address.ns1.nn1");
		rpc2 = prop.getProperty("hdfs.namenode.rpc-address.ns1.nn2");
		provider =  prop.getProperty("hdfs.client.failover.proxy.provider.ns1");
		headerMap = new HashMap<String,String>();
		headerMap.put("Accept", "application/jsson");
	}
	
	@Override
	public boolean uploadFile(String destDirPath, File srcFile) throws HdfsRestApiException {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("op", "create");
		paramMap.put("overwrite", "true");
		paramMap.put("blocksize", "134217728");
		paramMap.put("buffersize", "8388608");
		paramMap.put("user.name", superUser);
		String locationUrl = this.getLocation(destDirPath, srcFile.getName(), paramMap);
		LOG.info(String.format("uploading [%s] to hdfs://[%s].",srcFile.getName(),destDirPath));
		return createFile(locationUrl, srcFile);
	}

	@Override
	public boolean uploadFile(String destDirPath, String srcFileName, InputStream srcIs) throws HdfsRestApiException {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("op", "create");
		paramMap.put("overwrite", "true");
		paramMap.put("blocksize", "134217728");
		paramMap.put("buffersize", "8388608");
		paramMap.put("user.name", superUser);
		String locationUrl = this.getLocation(destDirPath, srcFileName, paramMap);
		LOG.info(String.format("uploading [%s] to hdfs://[%s].",srcFileName,destDirPath));
		return createFile(locationUrl, srcIs);
		
	}

	/**
	 * 获取location
	 * @param dirPath 文件目录
	 * @param fileName 文件名
	 * @throws HdfsRestApiException 
	 */
	private String getLocation(String dirPath,String fileName,Map<String,String> paramMap) throws HdfsRestApiException{
		String url = apiUrl + String.format("%s/%s", dirPath,fileName)  ;
		url = HttpClientUtil.spliceUrl(url, paramMap);
		HttpResponse httpResponse = HttpClientUtil.doPut(url, "UTF-8", headerMap, "");
		String result = HttpClientUtil.parseHttpEntity(httpResponse.getEntity(), "UTF-8");
		if(httpResponse.getStatusLine().getStatusCode() == 307){
			return httpResponse.getFirstHeader("Location").getValue();
		}
		else{
			HdfsRestApiException e = new HdfsRestApiException("response code is:" + httpResponse.getStatusLine().getStatusCode() + 
					System.getProperty("line.separator") + result);
			LOG.error("HdfsRestApiException :", e);
			throw e;
		}
	}
	
	/**
	 * 创建文件
	 * @param locationUrl url
	 * @param file 文件
	 * @return 是否创建成功
	 * @throws HdfsRestApiException
	 */
	private boolean createFile(String locationUrl,File file) throws HdfsRestApiException{
		HttpResponse httpResponse = null;
		httpResponse = HttpClientUtil.filePut(locationUrl, headerMap, file);
		String result = HttpClientUtil.parseHttpEntity(httpResponse.getEntity(), "UTF-8");
		if(httpResponse.getStatusLine().getStatusCode() == 201){
			return true;
		}
		else{
			HdfsRestApiException e = new HdfsRestApiException("response code is:" + httpResponse.getStatusLine().getStatusCode() + 
					System.getProperty("line.separator") + result);
			LOG.error("HdfsRestApiException :", e);
			throw e;
		}
	}
	
	/**
	 * 创建文件
	 * @param locationUrl 上传请求url
	 * @param is 文件
	 * @return 是否创建成功
	 * @throws HdfsRestApiException
	 */
	private boolean createFile(String locationUrl,InputStream is) throws HdfsRestApiException{
		HttpResponse httpResponse = null;
		httpResponse = HttpClientUtil.filePut(locationUrl, headerMap, is);
		String result = HttpClientUtil.parseHttpEntity(httpResponse.getEntity(), "UTF-8");
		if(httpResponse.getStatusLine().getStatusCode() == 201){
			return true;
		}
		else{
			HdfsRestApiException e = new HdfsRestApiException("response code is:" + httpResponse.getStatusLine().getStatusCode() + 
					System.getProperty("line.separator") + result);
			LOG.error("HdfsRestApiException :", e);
			throw e;
		}
	}
	
}
