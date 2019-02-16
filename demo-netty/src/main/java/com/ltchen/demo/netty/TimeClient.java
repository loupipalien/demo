package com.ltchen.demo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Created by ltchen.
 */
public class TimeClient {

    public static void main(String[] args) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // ch.pipeline().addLast(new TimeClientHandler());
                    // ch.pipeline().addLast(new TimeClientHandlerWithCumulativeBuffer());
                    // ch.pipeline().addLast(new TimeDecoder(), new TimeClientHandler());
                    ch.pipeline().addLast(new ReplayingDecoder<Void>() {
                        @Override
                        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
                            out.add(in.readBytes(4));
                        }
                    }, new TimeClientHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect("localhost", 8080).sync(); // (5)

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}

/**
 * (1)
 * Bootstrap 和 ServerBootstrap 相似的, 除了它是针对于非服务器信道的, 例如客户端的或者无连接的信道
 * (2)
 * 如果只指定一个 EventLoopGroup, 它将会同时作为 boss 组合 worker 组; 但 boss worker 并不会用在客户端
 * (3)
 * NioSocketChannel 用于创建客户端的 Channel, 而不是 NioServerSocketChannel
 * (4)
 * 注意这里并没有像 ServerBootstrap 那样使用 childOption(), 因为客户端的 SocketChannel 没有父亲
 * (5)
 * 应该调用 connect() 方法而不是 bind() 方法
 */
