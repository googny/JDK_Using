package net.nonblocking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 *
 */
public class NoBlockingEchoServer {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private int port = 8000;
    private Charset UTF8 = Charset.forName("UTF-8");

    public NoBlockingEchoServer() throws IOException {
        selector = Selector.open();
        InetAddress address = InetAddress.getLocalHost();
        InetSocketAddress socketAddress = new InetSocketAddress(address, port);
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(socketAddress);
        serverSocketChannel.configureBlocking(false);
        System.out.println("非阻塞Echo 服务器启动...");
    }

    public void service() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, buffer);
        while (selector.select() > 0) {
            Set selectionKeys = selector.selectedKeys();
            Iterator iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                if (key.isAcceptable()) {

                } else if (key.isReadable()) {

                } else if (key.isWritable()) {

                }
            }
        }
    }

    public void dealAccept(SelectionKey key) throws IOException {
        serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        socketChannel.register(selector, SelectionKey.OP_READ
                | SelectionKey.OP_WRITE, buffer);

    }

    public void dealRead(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();

        ByteBuffer readBuff = ByteBuffer.allocate(32);
        socketChannel.read(readBuff);
        readBuff.flip();
        buffer.limit(buffer.capacity());
        buffer.put(readBuff);
    }

    public void dealWrite(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.flip();
        System.out.println(buffer.limit());
        String rcvStr = new String(buffer.array());
        String sndStr = "echo: " + rcvStr;
        ByteBuffer outputBuffer = ByteBuffer.wrap(sndStr.getBytes());

        while (outputBuffer.hasRemaining()){
            socketChannel.write(outputBuffer);
        }





    }


}
