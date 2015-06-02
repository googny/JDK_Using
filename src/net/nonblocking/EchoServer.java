package net.nonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 非阻塞Echo 服务器
 */
public class EchoServer {
    public static int DEFAULT_PORT = 8080;

    public static void main(String[] args) {
        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception ex) {
            port = DEFAULT_PORT;
        }
        System.out.println("监听端口 " + port + "的连接...");

        ServerSocketChannel serverChannel;
        Selector selector;
        try {
            serverChannel = ServerSocketChannel.open();
            java.net.ServerSocket serverSocket = serverChannel.socket();
            InetSocketAddress address = new InetSocketAddress(port);
            serverSocket.bind(address);
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {

            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Set readyKeys = selector.selectedKeys();
            Iterator iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();
                try {
                    if (key.isAcceptable()) {
                        serverChannel = (ServerSocketChannel) key.channel();
                        SocketChannel client = serverChannel.accept();
                        System.out.println("接收到客户端连接 " + client);
                        client.configureBlocking(false);
                        ByteBuffer input = ByteBuffer.allocate(5);
                        client.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE,input);
                    }
                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer input = (ByteBuffer) key.attachment();
                        input.clear();
                        client.read(input);
                        System.out.println(input.position()+" "+input.limit());
                    }
                    if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        output.flip();
                        System.out.println("write "+output.position()+" "+output.limit());
                        client.write(output);
                        output.compact();
                    }
                } catch (IOException e) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }


    }
}
