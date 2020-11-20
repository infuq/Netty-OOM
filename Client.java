package com.infuq;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

public class Client {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        EventLoopGroup businessGroup = new NioEventLoopGroup(8);
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ChannelPipeline channelPipeline = ch.pipeline();
                        channelPipeline.addLast(new StringDecoder());
                        channelPipeline.addLast(new StringEncoder());
                        channelPipeline.addLast(businessGroup, new ClientHandler());
                    }
                });

        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);


        bootstrap.connect(new InetSocketAddress("127.0.0.1", 8080));


    }



}
