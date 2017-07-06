package com.ltchen.demo.util.common;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertyUtil {

	/**
	 * propPath：默认加载resource文件夹下的文件名,多层目录写相对路径
	 * @param propPath
	 * @param clazz
	 * @return
	 */
	public static Properties getProperty(String propPath, Class<?> clazz){
		Properties prop = new Properties();
		InputStream in;
		try {
			in = new BufferedInputStream (clazz.getClassLoader().getResource(propPath).openStream());
			prop.load(new InputStreamReader(in, "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
}
