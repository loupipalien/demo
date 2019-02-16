package com.ltchen.demo.jdk.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by ltchen.
 * Refer: https://juejin.im/post/5ae33c026fb9a07a9c03f45b
 * Desc: 客户端和服务端均无阻塞, 服务端无需使用多个线程处理, 可提高服务端性能, 但客户端和服务端收发消息需指定消息协议, 便于解析避免混淆
 */
public class NIOClientWithNIOServer {

    private static final Logger logger = LoggerFactory.getLogger(NIOClientWithNIOServer.class);
    public static void main(String[] args) {
        // 单线程服务端
        new Thread(new ServerThread()).start();
        // 多客户端
        new Thread(new ClientThread()).start();
    }

    public static class ClientThread implements Runnable {

        @Override
        public void run() {
            try(SocketChannel sc = SocketChannel.open()) {
                sc.configureBlocking(false);
                if (!sc.connect(new InetSocketAddress("localhost", 10444))) {
                    while (!sc.finishConnect()) {
                        System.out.print(".");
                        TimeUnit.SECONDS.sleep(1);
                    }
                }
                System.out.println("Connected to server...");
                // 通信
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                for (int i = 0; i < 10; i++) {
                    // 发送消息
                    String message = "Hello, I'm " + i + "-th message from client.";
                    logger.info("Client said: " + message);
                    buffer.put(message.getBytes());  // 写入 buffer
                    buffer.flip();
                    while (buffer.hasRemaining()) { // 需循环调用写入 channel, 因为 write 方法无法保证写入多少字节
                        sc.write(buffer);
                    }
                    buffer.compact();
                    // 接受消息
                    while (sc.read(buffer) > 0) {
                        buffer.flip();
                        StringBuilder builder = new StringBuilder();
                        while (buffer.hasRemaining()) {
                            builder.append((char) buffer.get());
                        }
                        logger.info(builder.toString());
                        buffer.clear();
                    }
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ServerThread implements Runnable {

        @Override
        public void run() {
            try(Selector selector = Selector.open();
                ServerSocketChannel ssc = ServerSocketChannel.open()) {
                ssc.configureBlocking(false); // 先设置非阻塞模式
                /**
                 * 获取 ServerSocketChannel 的 ServerSocket 并绑定端口
                 */
                ssc.socket().bind(new InetSocketAddress(10444));
                // 将 ServerSocketChannel 注册到 Selector, 关注 Accept 的操作
                ssc.register(selector, SelectionKey.OP_ACCEPT);
                while (true) {
                    if (selector.select(1000) == 0) {
                        continue;
                    }
                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        if (key.isConnectable()) {
                            logger.info("Connected == true");
                        }
                        if (key.isAcceptable()) {
                            handleAccept(key);
                        }
                        if (key.isReadable()) {
                            handleRead(key);
                        }
                        if (key.isWritable()) {
                            handleWrite(key);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void handleAccept(SelectionKey key) throws IOException {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            // 获取客户端的 SocketChannel
            SocketChannel sc = ssc.accept();
            if (sc != null) {
                // 在注册到 Selector 前设置为非阻塞模式
                sc.configureBlocking(false);
                // 注册 SocketChannel 到 Selector, 关注 Read 的操作, attachment 参数设定为与此客户端信道通信的 buffer
                sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(1024));
            }
        }

        public void handleRead(SelectionKey key) throws IOException {
            SocketChannel sc = (SocketChannel) key.channel();
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            // 有消息输出
            while (sc.read(buffer) > 0) {
                String message = new String(buffer.array());
                logger.info(message);
                // 与客户端约定的消息发送完成标识
                if (!message.contains("Hello")) {
                    buffer.clear();
                } else {
                    // 接受完客户端的消息, 将 key 的状态设置为 Write, 以响应客户端
                    key.interestOps(SelectionKey.OP_WRITE);
                    break;
                }
            }
        }

        public void handleWrite(SelectionKey key) throws IOException {
            SocketChannel sc = (SocketChannel) key.channel();
            ByteBuffer buffer = ((ByteBuffer) key.attachment()).put(" Welcome!".getBytes());
            logger.info("Server said: " + new String(buffer.array()));
            // 响应客户端
            buffer.flip();
            while (buffer.hasRemaining()) {
                sc.write(buffer);
            }
            buffer.clear();
            // 响应后将 key 的 ops 设置为读状态, 以便接受下一次客户端的消息
            key.interestOps(SelectionKey.OP_READ);
        }
    }
}
