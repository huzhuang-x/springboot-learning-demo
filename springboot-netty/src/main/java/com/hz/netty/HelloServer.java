package com.hz.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class HelloServer {
    public static void main(String[] args) {
        // 1.启动器，负责组装netty组件，启动服务器
        new ServerBootstrap()
                // 2.BossEventLoop,WorkerEventLoop(selector,thread),group组
                .group(new NioEventLoopGroup())
                // 3.选择服务器ServerSocketChannel的实现
                .channel(NioServerSocketChannel.class)
                // 4.boss负责处理连接，worker负责处理读写
                .childHandler(new ChannelInitializer<NioSocketChannel>(){
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBufAllocator byteBuf = ctx.alloc();
                                super.channelRead(ctx, msg);
                                Channel channel = ctx.channel();
//                                System.out.println(msg);
                            }
                        });
                    }
                })
                .bind(9091);
    }
}
