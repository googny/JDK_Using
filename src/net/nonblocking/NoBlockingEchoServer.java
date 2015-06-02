package net.nonblocking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 *
 */
public class NoBlockingEchoServer {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private byte[] rotation;
    private int port = 8000;

    public NoBlockingEchoServer() {
        try {
            selector = Selector.open();
            InetAddress address = InetAddress.getLocalHost();
            InetSocketAddress socketAddress = new InetSocketAddress(address, port);
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(socketAddress);
            serverSocketChannel.configureBlocking(false);
            rotation = new byte[95 * 2];
            for (byte i = ' '; i != '~'; i++) {
                rotation[i - ' '] = i;
                rotation[i + 95 - ' '] = i;
            }
            System.out.println("非阻塞Echo 服务器启动...");
        } catch (IOException ex) {
            return;
        }
    }

    public void service() throws IOException {
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select() > 0) {
            Set selectionKeys = selector.selectedKeys();
            Iterator iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                if (key.isAcceptable()) {
                    dealAccept(key);
                } else if (key.isReadable()) {
                    dealRead(key);
                } else if (key.isWritable()) {
                    dealWrite(key);
                }
            }
        }
    }

    public void dealAccept(SelectionKey key) throws IOException {
        serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ
                | SelectionKey.OP_WRITE);
        ByteBuffer buffer = ByteBuffer.allocate(72);
        key.attach(buffer);
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
        if (!buffer.hasRemaining()) {
            buffer.rewind();
            int first = buffer.get();
            buffer.rewind();
            int position = first - ' ' + 1;
            buffer.put(rotation, position, 72);
            buffer.put((byte) '\r');
            buffer.put((byte) '\n');
            buffer.flip();
        }
        socketChannel.write(buffer);
    }


}
