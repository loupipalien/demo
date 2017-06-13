package com.ltchen.demo.hadoop.service;

import java.util.List;

import org.apache.hadoop.fs.FileSystem;

public interface HdfsService {
	
	/**
	 * 获取FileSystem
	 * @param defaultFS core.xml中fs.defaultFS配置(单namenode)
	 * @return
	 */
	FileSystem getFileSystem(String defaultFS);
	
	/**
	 * 关闭FileSystem实例资源
	 * @param fs FileSystem实例
	 */
	void closeFileSystem(FileSystem fs);
	
	/**
	 * 创建文件夹
	 * @param fs FileSystem实例
	 * @param dirPath 文件夹路径
	 */
	void makeDir(FileSystem fs, String dirPath);
	
	/**
	 * 设置文件或文件夹权限
	 * @param fs FileSystem实例
	 * @param path 文件或文件夹路径
	 * @param permission 权限(八进制数:在00000 - 01777之间)
	 */
	void setPermission(FileSystem fs, String path, short permission);
	
	/**
	 * 设置文件或文件夹属主
	 * @param fs FileSystem实例
	 * @param path 文件或文件夹路径
	 * @param username 用户名
	 * @param groupname 用户组名
	 */
	void setOwner(FileSystem fs, String path, String username, String groupname);
	
	/**
	 * 获取文件夹下的文件
	 * @param fs FileSystem实例
	 * @param dirPath 文件夹路径
	 * @param recursive 是否递归
	 * @return
	 */
	List<String> getFiles(FileSystem fs, String dirPath, boolean recursive);
	
	/**
	 * 获取文件夹下的文件(默认不递归)
	 * @param fs FileSystem实例
	 * @param dirPath 文件夹路径
	 * @return
	 */
	List<String> getFiles(FileSystem fs, String dirPath);
	
	/**
	 * 删除文件夹或文件(如果是文件夹路径则递归删除)
	 * @param fs FileSystem实例
	 * @param path 文件夹或文件路径
	 */
	void delete(FileSystem fs, String path);
	
	/**
	 * 判断路径是否时文件夹
	 * @param fs FileSystem实例
	 * @param path 路径
	 * @return
	 */
	boolean isDirectory(FileSystem fs, String path);
	
	/**
	 * 判断路径是否时文件
	 * @param fs FileSystem实例
	 * @param path 路径
	 * @return
	 */
	boolean isFile(FileSystem fs, String path);
	
	/**
	 * 读取文件
	 * @param fs FileSystem实例
	 * @param filePath 文件路径
	 * @param bufferSize 读取大小(文件可能会比较大)
	 * @return
	 */
	String readFile(FileSystem fs,String filePath,int bufferSize);
	
	/**
	 * 读取文件(读取全部)
	 * @param fs FileSystem实例
	 * @param filePath 文件路径
	 * @return
	 */
	String readFile(FileSystem fs,String filePath);
	
	/**
	 * 写入文件
	 * @param fs FileSystem实例
	 * @param overwrite 是否覆盖
	 * @param content 写入内容
	 * @param hdfsDst 目的文件
	 */
	void writeFile(FileSystem fs, boolean overwrite, String content,String hdfsDst);
	
	/**
	 * 写入文件(当目的文件存在时覆盖)
	 * @param fs FileSystem实例
	 * @param content 写入内容
	 * @param hdfsDst 目的文件
	 */
	void writeFile(FileSystem fs, String content,String hdfsDst);
	
	/**
	 * 上传文件
	 * @param fs FileSystem实例
	 * @param delLocalSrc 是否删除本地源文件
	 * @param localSrc 本地源文件
	 * @param hdfsDst HDFS目的文件
	 */
	void uploadFile(FileSystem fs, boolean delLocalSrc ,String localSrc,String hdfsDst);
	
	/**
	 * 上传文件(当目的文件存在时覆盖)
	 * @param fs FileSystem实例
	 * @param localSrc 本地源文件
	 * @param hdfsDst HDFS目的文件
	 */
	void uploadFile(FileSystem fs, String localSrc,String hdfsDst);
	
	/**
	 * 下载文件
	 * @param fs FileSystem实例
	 * @param delHdfsSrc 是否删除HDFS源文件
	 * @param hdfsSrc HDFS源文件
	 * @param localDst 本地目的文件
	 */
	void downloadFile(FileSystem fs, boolean delHdfsSrc , String hdfsSrc,String localDst);
	
	/**
	 * 下载文件(默认不删除HDFS源文件)
	 * @param fs FileSystem实例
	 * @param hdfsSrc HDFS源文件
	 * @param localDst 本地目的文件
	 */
	void downloadFile(FileSystem fs, String hdfsSrc,String localDst);
	
}
