package com.infuq;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        int i = 0;

        for (;;) {
            ctx.writeAndFlush("这个是客户端发送的第" + ++i + "个消息");
        }
		
		/* 解决OOM
        ctx.channel().config().setWriteBufferHighWaterMark(20 * 1024 * 1024);

        int i = 0;

        for (;;) {
            if (ctx.channel().isWritable()) {
                ctx.writeAndFlush("这个是客户端发送的第" + ++i + "个消息");
            } else {
                System.out.println("达到高水位,暂时不可写");
            }

        }
		
		*/
		
		
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        ctx.fireExceptionCaught(cause);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println("接收到服务端数据:" + msg);

    }
}

