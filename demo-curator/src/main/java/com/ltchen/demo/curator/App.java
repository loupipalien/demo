package com.ltchen.demo.curator;

import java.util.ArrayList;
import java.util.List;

public class App {

	private static String zkConnStr = "192.168.0.126:2181";

	public static void main(String[] args) throws Exception {
		// ZKCliHelper zkCliHelper = new ZKCliHelper();
		// zkCliHelper.setZkConnStr(zkConnStr);
		// zkCliHelper.init();
		// zkCliHelper.createPath("/ltchen", "wawo");
		//// System.out.println(zkCliHelper.getData("/ltchen"));
		// zkCliHelper.destory();

		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		for (String item : list) {
			if ("2".equals(item)) {
				list.remove(item);
			}
		}
		System.out.println(list);
	}

}
