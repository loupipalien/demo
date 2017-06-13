package com.ltchen.demo.hadoop.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.fs.permission.FsPermission;

import com.ltchen.demo.hadoop.service.HdfsService;

public class HdfsServcieImpl implements HdfsService {

	//hadoop ha
	public FileSystem getFileSystem(){
		FileSystem fs = null;
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://ns1");
		conf.set("dfs.nameservices", "ns1");
		conf.set("dfs.ha.namenodes.ns1", "nn1,nn2");
		conf.set("dfs.namenode.rpc-address.ns1.nn1", "ltchen01:8020");
		conf.set("dfs.namenode.rpc-address.ns1.nn2", "ltchen02:8020");
		conf.set("dfs.client.failover.proxy.provider.ns1", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
		try {
			fs = FileSystem.get(new URI("hdfs://ns1"), conf, "hdfs");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return fs;
	}
	
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
	public void makeDir(FileSystem fs, String dirPath) {
		try {
			fs.mkdirs(new Path(dirPath));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void setPermission(FileSystem fs, String path, short permission) {
		if(permission < 00000 || permission > 01777){
			throw new IllegalArgumentException("the permission must be greater than 00000 and less than 01777.");
		}
		try {
			fs.setPermission(new Path(path), new FsPermission(permission));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void setOwner(FileSystem fs, String path, String username, String groupname) {
		try {
			fs.setOwner(new Path(path), username, groupname);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public List<String> getFiles(FileSystem fs, String dirPath, boolean recursive) {
		if(!this.isDirectory(fs, dirPath)){
			throw new IllegalArgumentException(String.format("the %s is not a directory.",dirPath));
		}
		List<String> files = new ArrayList<String>();
		try {
			RemoteIterator<LocatedFileStatus> iterator = fs.listFiles(new Path(dirPath), recursive);
			while(iterator.hasNext()){
				LocatedFileStatus status = iterator.next();
				files.add(status.getPath().toString());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return files;
	}


	@Override
	public List<String> getFiles(FileSystem fs, String dirPath) {
		return this.getFiles(fs, dirPath, false);
	}
	

	@Override
	public boolean isDirectory(FileSystem fs, String path) {
		try {
			return fs.isDirectory(new Path(path));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public boolean isFile(FileSystem fs, String path) {
		try {
			return fs.isFile(new Path(path));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void delete(FileSystem fs, String path) {
		try {
			fs.delete(new Path(path), true);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
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
		}
		return buffer.toString();
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
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void writeFile(FileSystem fs, String content, String hdfsDst) {
		this.writeFile(fs, true, content, hdfsDst);
	}
	
	@Override
	public void uploadFile(FileSystem fs, boolean delLocalSrc, String localSrc, String hdfsDst) {
		try {
			fs.copyFromLocalFile(delLocalSrc, new Path(localSrc), new Path(hdfsDst));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void uploadFile(FileSystem fs, String localSrc, String hdfsDst) {
		this.uploadFile(fs, true, localSrc, hdfsDst);
	}

	@Override
	public void downloadFile(FileSystem fs, boolean delHdfsSrc, String hdfsSrc, String localDst) {
		try {
			fs.copyToLocalFile(delHdfsSrc, new Path(hdfsSrc), new Path(localDst));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void downloadFile(FileSystem fs, String hdfsSrc, String localDst) {
		this.downloadFile(fs, false, hdfsSrc, localDst);
	}
	
}
