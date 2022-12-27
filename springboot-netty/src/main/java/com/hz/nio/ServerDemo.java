package com.hz.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ServerDemo {
    public static void main(String[] args) throws IOException {
        // 使用nio理解阻塞模式，单线程
        ByteBuffer buffer = ByteBuffer.allocate(16);

        // 1.创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 2.绑定监听端口
        ssc.bind(new InetSocketAddress(9091));
        ssc.configureBlocking(false);   // 设置模式为非阻塞

        // 3.连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            // 4.建立与客户端连接，SocketChannel用来与客户端通信
            log.debug("connecting...");
            SocketChannel sc = ssc.accept();    // 非阻塞，线程继续运行，没有客户端连接时返回sc为null
            if (sc != null) {
                channels.add(sc);
                log.debug("connecting...{}",sc);
            }
            for (SocketChannel channel : channels) {
                channel.configureBlocking(false);   // 非阻塞
                // 5.接收客户端发送的数据
                log.debug("before read...{}", channel);
                int read = channel.read(buffer);    // 非阻塞，线程继续运行，客户端没有发送数据时返回0
                if (read > 0) {
                    buffer.flip();
                }
            }
        }
    }
}
