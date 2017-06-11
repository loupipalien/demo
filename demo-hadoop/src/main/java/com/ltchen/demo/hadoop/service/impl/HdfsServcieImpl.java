package com.ltchen.demo.hadoop.service.impl;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.ltchen.demo.hadoop.service.HdfsService;

public class HdfsServcieImpl implements HdfsService {

	@Override
	public FileSystem getFileSystem(String defaultFS) {
		FileSystem fs = null;
		Configuration conf = new Configuration();
		try {
			fs = FileSystem.get(URI.create(defaultFS), conf, "hdfs");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return fs;
	}

	@Override
	public void closeFileSystem(FileSystem fs) {
		if(fs != null){
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void writeFile(FileSystem fs, boolean overwrite, String content, String hdfsDst) {
		FSDataOutputStream out = null;
		try {
			out = fs.create(new Path(hdfsDst),overwrite);
			out.write(content.getBytes());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//关闭资源
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			this.closeFileSystem(fs);
		}
	}
	
	@Override
	public void writeFile(FileSystem fs, String content, String hdfsDst) {
		this.writeFile(fs, false, content, hdfsDst);
	}

	@Override
	public String readFile(FileSystem fs, String filePath, int bufferSize) {
		FSDataInputStream in = null;
		byte[] buffer = null;
		Path path = new Path(filePath);
		try {
			if(fs.exists(path)){
				in = fs.open(path,bufferSize);
				buffer = new byte[bufferSize];
				in.readFully(0, buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			this.closeFileSystem(fs);
		}
		return buffer.toString();
	}

	@Override
	public String readFile(FileSystem fs, String filePath) {
		FSDataInputStream in = null;
		byte[] buffer = null;
		Path path = new Path(filePath);
		try {
			if(fs.exists(path)){
				FileStatus status = fs.getFileStatus(path);
				in = fs.open(path);
				buffer = new byte[Integer.parseInt(String.valueOf(status.getLen()))];
				in.readFully(0, buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			this.closeFileSystem(fs);
		}
		return buffer.toString();
	}
	
	@Override
	public void uploadFile(FileSystem fs, boolean delLocalSrc, String localSrc, String hdfsDst) {
		try {
			fs.copyFromLocalFile(delLocalSrc, new Path(localSrc), new Path(hdfsDst));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//关闭资源
			this.closeFileSystem(fs);
		}
	}

	@Override
	public void uploadFile(FileSystem fs, String localSrc, String hdfsDst) {
		this.uploadFile(fs, true, localSrc, hdfsDst);
	}

	
}
