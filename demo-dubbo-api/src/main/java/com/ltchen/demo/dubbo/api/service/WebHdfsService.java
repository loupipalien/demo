package com.ltchen.demo.dubbo.api.service;

import java.io.File;
import java.io.InputStream;

import com.ltchen.demo.dubbo.exception.HdfsRestApiException;

public interface WebHdfsService {

	
	/**
	 * 上传文件
	 * @param destDirPath 目的文件夹路径
	 * @param srcFile 源文件
	 * @return
	 * @throws HdfsRestApiException
	 */
	boolean uploadFile(String destDirPath, File srcFile) throws HdfsRestApiException;
	
	/**
	 * 上传文件
	 * @param destDirPath 目的文件夹路径
	 * @param srcFileName 源文件名
	 * @param srcIs 源输入流
	 * @return
	 * @throws HdfsRestApiException
	 */
	boolean uploadFile(String destDirPath, String srcFileName, InputStream srcIs) throws HdfsRestApiException;
	
	boolean uploadFile(String destDirPath, InputStream srcIs) throws HdfsRestApiException;
}
