package com.ltchen.demo.util;

import javax.jws.soap.SOAPBinding;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by ltchen on 2019/1/11.
 */
public class ConsistentHash<T> {
    /**
     * 每个真实节点对应的虚拟节点数
     */
    private final int replicates;

    /**
     * 存储虚拟节点的 hash 值到真实节点的映射
     */
    private final SortedMap<Integer, T> circle = new TreeMap<>();

    public ConsistentHash(int replicates, Collection<T> nodes) {
        this.replicates = replicates;
        nodes.forEach(this::add);
    }

    /**
     * 对于每个真实节点 node 都有 replicates 个虚拟节点对应,
     * 不同的虚拟节点 i 有不同的 hash 值, 但都对应同一个真实节点 node,
     * 虚拟节点 i 一般是均匀分布在环上的, 数据按顺时针存储到最邻近的虚拟节点上
     */
    public void add(T node) {
        for (int i = 0; i < replicates; i++) {
            String key = node.toString() + i;
            circle.put(hash(md5(key), 0), node);
        }
    }

    public void remove(T node) {
        for (int i = 0; i < replicates; i++) {
            String key = node.toString() + i;
            circle.remove(hash(md5(key), 0));
        }
    }

    private int hash(byte[] digest, int number) {
        return (((digest[3 + number * 4] & 0xFF) << 24)
                | ((digest[2 + number * 4] & 0xFF) << 16)
                | ((digest[1 + number * 4] & 0xFF) << 8)
                | (digest[0 + number * 4] & 0xFF))
                & 0x0FFFFFFF;
    }

    private byte[] md5(String value) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(value.getBytes("UTF-8"));
            return md5.digest();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * 根据给定的 key 取 hash 值, 获取一个顺时针最邻近的虚拟节点,
     * 再根据虚拟节点获取真实节点, 在从真实节点中获取数据
     */
    public T get(Object key) {
        if (circle.isEmpty()) {
            return null;
        }

        int hash = hash(md5(key.toString()), 0);
        // for debug
        System.out.println("hash : " + hash);
        // 当 hash 不等于某个虚拟节点, 则认为 hash 在两个虚拟节点之间, 顺时针获取最邻近的虚拟节点
        if (!circle.containsKey(hash)) {
            SortedMap<Integer, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    public void print() {
        // virtual nodes
        circle.entrySet().forEach(entry -> System.out.println(entry.getKey() + " = " + entry.getValue()));
        // distribution
        Iterator<Integer> iterator = circle.keySet().iterator();
        if (iterator.hasNext()) {
            int previous = iterator.next();
            while (iterator.hasNext()) {
                int current = iterator.next();
                System.out.println(current - previous);
            }
        }
    }

    public static void main(String[] args) {
        List<String> nodes = Arrays.asList(new String[]{"A", "B", "C"});
        ConsistentHash<String> consistentHash = new ConsistentHash<>(2, nodes);
//        consistentHash.print();
        System.out.println("node -> apple : " + consistentHash.get("apple"));
        System.out.println("node -> pear : " + consistentHash.get("pear"));
        System.out.println("node -> banana : " + consistentHash.get("banana"));
        System.out.println("node -> peach : " + consistentHash.get("peach"));
        System.out.println("node -> watermelon : " + consistentHash.get("watermelon"));
        System.out.println("=========================================================");
        consistentHash.remove("B");
        System.out.println("node -> apple : " + consistentHash.get("apple"));
        System.out.println("node -> pear : " + consistentHash.get("pear"));
        System.out.println("node -> banana : " + consistentHash.get("banana"));
        System.out.println("node -> peach : " + consistentHash.get("peach"));
        System.out.println("node -> watermelon : " + consistentHash.get("watermelon"));
        System.out.println("=========================================================");
        consistentHash.add("B");
        consistentHash.add("D");
        System.out.println("node -> apple : " + consistentHash.get("apple"));
        System.out.println("node -> pear : " + consistentHash.get("pear"));
        System.out.println("node -> banana : " + consistentHash.get("banana"));
        System.out.println("node -> peach : " + consistentHash.get("peach"));
        System.out.println("node -> watermelon : " + consistentHash.get("watermelon"));
        System.out.println("=========================================================");

    }
}
