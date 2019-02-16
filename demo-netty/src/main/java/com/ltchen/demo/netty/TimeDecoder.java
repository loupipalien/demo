package com.ltchen.demo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by ltchen.
 * Desc: 继承 ByteToMessageDecoder 处理分段数据
 */
public class TimeDecoder extends ByteToMessageDecoder { // (1)
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
        if (in.readableBytes() < 4) {
            return; // (3)
        }

        out.add(in.readBytes(4)); // (4)
    }
}

/**
 * (1)
 * ByteToMessageDecoder 是 ChannelInboundHandler 的一个实现, 它可以使分段问题的处理简单化;
 * (2)
 * 当新数据被接收, ByteToMessageDecoder 会使用一个内部维护的累积缓冲区调用 decode() 方法
 * (3)
 * 当累积缓冲区中没有足够的数据时 decode() 可以决定什么都不添加到 out 中; 当有更多的数据接收时,
 * ByteToMessageDecoder 将再一次会调用 decode() 方法
 * (4)
 * 如果 decode() 添加了一个 object 到 out 中, 这意味着装饰器成功装饰可一条消息, ByteToMessageDecoder
 * 将会丢弃累积缓冲区中已读的部分; 值得注意的是你不需要装饰多条消息; ByteToMessageDecoder 会持续调用
 * decode() 方法直到它什么都不添加到 out 中
 */