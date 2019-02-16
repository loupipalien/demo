package com.ltchen.demo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * Created by ltchen.
 * Desc: 使用 internal buffer 处理分段数据
 */
public class TimeClientHandlerWithCumulativeBuffer extends ChannelInboundHandlerAdapter {
    private ByteBuf buf;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        buf = ctx.alloc().buffer(4); // (1)
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        buf.release(); // (1)
        buf = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg;
        buf.writeBytes(m); // (2)
        m.release();

        if (buf.readableBytes() >= 4) { // (3)
            long currentTimeMillis = (buf.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));
            ctx.close();
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
 * ChannelHandler 有两个生命周期监听器方法: handlerAdded() 和 handlerRemoved(), 可以执行任意的
 * (de)initialization 任务, 只要不被阻塞住太久的时间
 * (2)
 * 首先, 接收的数据应该被积累到 buf 中
 * (3)
 * 然后, 处理器检测 buf 是否有足够的数据, 此示例中的 4 个字节, 然后继续实际的业务逻辑
 * 另一方面, 当有更多的数据到达时 Netty 会再一次调用 channelRead() 方法, 最终所有的 4 字节都会被积累
 */