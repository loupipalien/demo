package com.ltchen.demo.netty;

/**
 * Created by ltchen.
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {  // (2)
        // Discard the received data silently.
        ((ByteBuf) msg).release(); // (3)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {  // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}

/**
 * (1)
 * DiscardServerHandler 继承 ChannelInboundHandlerAdapter, 这是一个 ChannelInboundHandler 的实现;
 * ChannelInboundHandler 提供了各种可以复写的时间处理方法; 这里仅需要继承 ChannelInboundHandlerAdapter 就足够,
 * 而不是自己实现这个处理器接口
 * (2)
 * 这里复写了 channelRead() 事件处理方法; 当接收到来自客户端的新数据, 这个方法在接收信息时调用; 这个示例中, 接收的信息类型是 ByteBuf
 * (3)
 * 为了实现 DISCARD 协议, 处理器忽略了接收到的信息; ByteBuf 是一个引用计数 (ReferenceCounted) 对象, 需要明确通过 release() 方法释放;
 * 需要注意的是, 处理器需要对传递给处理器的任何引用计数对象的释放负责; 通常 channelRead() 处理器方法类似以下方式实现
 * @Override
 * public void channelRead(ChannelHandlerContext ctx, Object msg) {
 *     try {
 *         // Do something with msg
 *     } finally {
 *         ReferenceCountUtil.release(msg);
 *     }
 * }
 * (4)
 * 当有 I/O 错误或者处理器的实现导致的处理时间中抛出的异常由于 exceptionCaught() 事件处理器方法被 Netty 抛出时,
 * exceptionCaught() 事件处理器方法会带着 Throwable 被调用;尽管依赖于想如何处理异常状态, 这个方法的实现可以使不同的,
 * 但在大多数情况下, 被捕获的异常应该被记录并且与之相关的信道应该在此关闭; 例如, 可能想在关闭连接前发送携带错误码的响应信息
 */
