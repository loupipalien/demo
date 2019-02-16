package com.ltchen.demo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * Created by ltchen.
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg; // (1)
        try {
            long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));
            ctx.close();
        } finally {
            m.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

/**
 * (1)
 * 在 TCP/IP 中, Netty 读取从对等点发送的数据到 ByteBuf 中
 *
 * 在基于流的传输, 例如 TCP/IP, 接收数据到套接字的接收缓冲区; 不幸的是, 流传输的缓冲区不是一个包队列而是字节队列;
 * 这意味着, 即使作为两个独立的包发送两条消息, 操作系统将不会认为它们是两条消息而仅仅是一堆字节; 因此, 这里并不保证读到的
 * 正是对等点写入的; 例如, 假设操作系统的 TCP/IP 栈接收到了三个包
 * 这三个包如它们发送的顺序被接收, 因为流协议的基本属性, 在程序中有很高的可能按照以下的分段读取它们: 三个包被分割到四个缓冲
 * 区中; 因此无论是服务端还是客户端, 接收端应该整理接收的数据为一个或多个有意义的片段, 以至于被应用逻辑所理解; 以上的示例中
 * 接收的数据应该按照以下分片: 四个缓冲区被整理到三个中
 */