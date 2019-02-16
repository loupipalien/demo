package com.ltchen.demo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by ltchen.
 * Discards any incoming data.
 */
public class Server {

    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();  // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();  // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)  // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() {  // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // ch.pipeline().addLast(new DiscardServerHandler());
                            // ch.pipeline().addLast(new PrintServerHandler());
                            // ch.pipeline().addLast(new EchoServerHandler());
                             ch.pipeline().addLast(new TimeServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)  // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);  // (6)

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync();  // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            ((ChannelFuture) workerGroup.shutdownGracefully()).addListener(ChannelFutureListener.CLOSE);
            ((ChannelFuture) bossGroup.shutdownGracefully()).addListener(ChannelFutureListener.CLOSE);
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new Server(port).run();
    }
}

/**
 * (1)
 * NioEventLoopGroup 是一个处理 I/O 操作的多线程事件循环; Netty 对于不同的传输类型提供了各种 EventLoopGroup 实现;
 * 在这个示例中实现了一个服务端的应用, 并且这里用到了两个 NioEventLoopGroup; 第一个被称为 "boss", 用于接受到来的连接;
 * 第二个被称为 "worker", 一旦 boss 接受连接并将其注册到 worker, worker 就处理接受连接的流量; 使用多少线程, 如何映射到创建的
 * 信道, 依赖于 EventLoopGroup 的实现以及通过构造器传递的配置
 * (2)
 * ServerBootstrap 是启动服务器的帮助类; 可以使用信道直接启动一个服务器, 然而需要注意的是这是一个繁琐的过程, 并且在大多数情况下
 * 都不需要这样做
 * (3)
 * 这里指定使用 NioServerSocketChannel 类, 它被用于初始化一个新的信道来接受到来的连接
 * (4)
 * 这里指定的处理器总是被一个新接受的信道所计算; ChannelInitializer 是一个特殊的处理器, 用于帮助一个用户配置一个新的信道; 最有可能的是
 * 你想通过添加一些类似 DiscardServerHandler 的处理器配置信道的 ChannelPipeline 来实现你的网络应用; 当应用变得复杂, 可以添加更多的
 * 处理器到管道中, 并且最终可以抽象为匿名类到顶类中
 * (5)
 * 你也可以为信道的实现指定一些参数; 写一个 TCP/IP 的服务器, 所以可以允许设置如 tcpNoDelay 和 keepAlive 的套接字参数; 请参考 ChannelOption
 * 的文档和特定的 ChannelConfig 实现获取关于支持的 ChannelOption 概览
 * (6)
 * option() 是面向于接受到来连接的 NioServerSocketChannel, childOption() 是面向于通过父亲 ServerChannel 而接受的信道, 在本示例中父亲
 * ServerChannel 是 NioServerSocketChannel
 * (7)
 * 到此已经准备好, 剩下的是绑定端口和启动服务器; 这里我们绑定机器上所有网卡的 8080 端口; 你可以多次调用 bind() 方法 (使用不同的绑定端口)
 */