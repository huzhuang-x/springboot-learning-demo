package com.hz.selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

@Slf4j
public class SelectorServer {

    public static void main(String[] args) throws IOException {
        // 1.创建Selector,管理多个channel
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(9091));
        ssc.configureBlocking(false);

        // 2.建立Selector和Channel的联系（注册）
        // SelectionKey：事件发生后，通过它可以知道事件和哪个channel的事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key:{}",sscKey);
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().stream().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                log.debug("key:{}",sscKey);
                ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                SocketChannel sc = channel.accept();
                log.debug("{}",sc);
            }
        }
    }
}
