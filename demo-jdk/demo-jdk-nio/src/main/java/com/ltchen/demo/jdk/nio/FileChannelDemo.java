package com.ltchen.demo.jdk.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by ltchen.
 */
public class FileChannelDemo {

    public static void main(String[] args) {
        try(RandomAccessFile file = new RandomAccessFile(FileChannelDemo.class.getResource("/nio-data.txt").getPath(), "r");
            FileChannel channel = file.getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(64); // 分配 buffer 大小
            while (channel.read(buffer) != -1) {
                buffer.flip();  // 翻转读写模式
                while (buffer.hasRemaining()) {
                    System.out.print((char) buffer.get());
                }
                buffer.clear(); // "清空" 整个缓冲区, compact() 方法可以只 "清空" 已读取的数据
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
