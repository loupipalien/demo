package com.ltchen.demo.hadoop.service;

import org.apache.hadoop.fs.FileSystem;

public interface HdfsService {
	
	/**
	 * 获取FileSystem
	 * @param defaultFS core.xml中fs.defaultFS配置(单namenode)
	 * @return
	 */
	FileSystem getFileSystem(String defaultFS);
	
	/**
	 * 关闭FileSystem
	 * @param fs FileSystem实例
	 */
	void closeFileSystem(FileSystem fs);
	
	/**
	 * 写入文件
	 * @param fs FileSystem实例
	 * @param overwrite 是否覆盖
	 * @param content 写入内容
	 * @param hdfsDst 目的文件
	 */
	public void writeFile(FileSystem fs, boolean overwrite, String content,String hdfsDst);
	
	/**
	 * 写入文件(当目的文件存在时覆盖)
	 * @param fs FileSystem实例
	 * @param content 写入内容
	 * @param hdfsDst 目的文件
	 */
	public void writeFile(FileSystem fs, String content,String hdfsDst);
	
	/**
	 * 读取文件
	 * @param fs FileSystem实例
	 * @param filePath 文件路径
	 * @param bufferSize 读取大小(文件可能会比较大)
	 * @return
	 */
	public String readFile(FileSystem fs,String filePath,int bufferSize);
	
	/**
	 * 读取文件(读取全部)
	 * @param fs FileSystem实例
	 * @param filePath 文件路径
	 * @return
	 */
	public String readFile(FileSystem fs,String filePath);
	
	/**
	 * 上传文件
	 * @param fs FileSystem实例
	 * @param delLocalSrc 是否删除本地源文件
	 * @param localSrc 本地源文件
	 * @param hdfsDst HDFS目的文件
	 */
	public void uploadFile(FileSystem fs, boolean delLocalSrc ,String localSrc,String hdfsDst);
	
	/**
	 * 上传文件(当目的文件存在时覆盖)
	 * @param fs FileSystem实例
	 * @param localSrc 本地源文件
	 * @param hdfsDst HDFS目的文件
	 */
	public void uploadFile(FileSystem fs, String localSrc,String hdfsDst);
}
