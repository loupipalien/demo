package com.ltchen.demo.curator.caches;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * Created by ltchen on 2018/12/14.
 */
public class PathCache {

    public static void main(String[] args) {
        String connectString = "192.168.0.1:2181";
        String zNodePath =  "/hiveserver2";
        try (CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, new ExponentialBackoffRetry(1000, 3))) {
            client.start();
            // cacheDate 设置为 true 可以获取变化的数据
            PathChildrenCache cache = new PathChildrenCache(client, zNodePath , true);
            cache.getListenable().addListener((curatorFramework, event) -> {
                String data = new String(event.getData().getData(), Charset.forName("UTF-8"));
                System.out.println("Type: " + event.getType() + ", Data: " + data);
            });
            // 启动方式有 NORMAL, BUILD_INITIAL_CACHE, POST_INITIALIZED_EVENT
            cache.start();
            // 此处睡眠以触发监听事件
            TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
