package com.hz;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.NettyRuntime;

public class Demo {
    public static void main(String[] args) {
        System.out.println(NettyRuntime.availableProcessors());
        ChannelInboundHandlerAdapter h1 = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                Channel channel = ctx.channel();
                super.channelRead(ctx, msg);
            }
        };
    }
}
