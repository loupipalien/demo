package com.ltchen.demo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by ltchen.
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * Time Protocol: https://tools.ietf.org/html/rfc868
     * @param ctx
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
        final ByteBuf time = ctx.alloc().buffer(4); // (2)
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
        f.addListener(future -> ctx.close()); // (4)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

/**
 * (1)
 * 当一个连接建立并且准备好产生流量, channelActive() 方法会被调用; 这个方法里使用一个 32 位的整形来表示当前时间
 * (2)
 * 为了发送信息, 需要分配一个新的缓冲区来保存信息; 我们将写一个 32 位的整形; 因此需要一个容量至少为 4 字节的 ByteBuf;
 * 通过 ChannelHandlerContext.alloc().buffer() 获取当前的 ByteBufAllocator 并分配一个新的缓冲区
 * (3)
 * 通常, 我们会写出构造的信息
 * 但是等等, flip 去哪了?  NIO 中发送信息前我们不是通常会调用 java.nio.ByteBuffer.flip() 方法吗? ByteBuf 没有这样的方法,
 * 因为它有两个指针; 一个针对读操作, 一个针对写操作; 当写入到 ByteBuf 中时写索引会增加而读索引不变; 读索引和写索引各自的表示消息的
 * 开始和结束
 * 相反的, NIO 缓冲区除了调用 flip 方法, 没有提供一个简洁的方式计算出消息内容的开始和结束; 当你忘记翻转缓冲区, 你会陷入发送空或者不正确的数据的麻烦;
 * 这样的错误不会在 Netty 中发生, 因为对于不同的操作有不同的指针; 当你习惯它时你会发现它会是你的生活如此容易, 生活不会失控! (哈哈, 实力嘲讽)
 * 另一个重要的点是 ChannelHandlerContext.write() (和 writeAndFlush()) 方法会返回一个 ChannelFuture, 一个 ChannelFuture 代表着一个
 * 还没有完成的 I/O 操作; 这意味着任何请求的操作可能都没有完成, 因为在 Netty 中所有的操作都是异步的; 例如, 以下代码可能在消息尚未发送前就关闭了连接
 * Channel ch = ...;
 * ch.writeAndFlush(message);
 * ch.close();
 * 因此, 需要在 ChannelFuture 完成后调用 close() 方法, ChannelFuture 会被 write() 方法返回; 当写操作完成时它会通知它的监听器：需要注意的是
 * close() 也可能没有立刻关闭连接, 并且也返回了一个 ChannelFuture
 * (4)
 * 当一个请求完成后如何获得通知? 只要简单的添加一个 ChannelFutureListener 给返回的 ChannelFuture 即可; 这里创建了一个新的匿名的 ChannelFutureListener
 * , 当操作完成后它将会关闭信道
 * 可选的, 也可以使用预定义监听器简化代码: f.addListener(ChannelFutureListener.CLOSE);
 */