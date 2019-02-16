package com.ltchen.demo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by ltchen.
 * Handles a server-side channel.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter { // (1)

    /**
     * Echo Protocol: https://tools.ietf.org/html/rfc862
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.write(msg); // (1)
        ctx.flush(); // (2)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

/**
 * (1)
 * ChannelHandlerContext 对象提供了各种操作, 允许触发各种 I/O 事件和操作; 这里, 调用 write() 方法将
 * 接受到的信息逐字写出; 注意这里我们没有像 DISCARD 示例中那样释放接受的信息, 因为当它被写出到电报中 Netty 会为你释放它
 * (2)
 * ctx.write() 并没有使信息写出到电报中, 而是被缓存起来, 直到被 ctx.flush() 将其刷入电报中; 可选的, 也可以简洁的调用
 * ctx.writeAndFlush()
 */
