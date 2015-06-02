package net.nonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SocketChannel;

/**
 * 测试产生整数的客户端
 */
public class IntGenClient {
    public static void main(String[] args) throws IOException {
//        SocketChannel client = SocketChannel.open();
//        InetSocketAddress remoteHost = new InetSocketAddress("localhost", 8080);
//        client.connect(remoteHost);
//        System.out.println("客户端连接上服务器...");
//
//        ByteBuffer input = ByteBuffer.allocate(4);
//        while (true) {
//            int total = 0;
//            while (total < 4) {
//                int len = client.read(input);
//                if (len == -1) {
//                    break;
//                }
//                total += len;
//            }
//            input.flip();
//            System.out.println(input.getInt());
//            input.clear();
//        }
        ViewIntGenClient.main(args);
    }
}

class ViewIntGenClient {

    public static void main(String[] args) throws IOException {
        SocketChannel client = SocketChannel.open();
        InetSocketAddress remoteHost = new InetSocketAddress("localhost", 8080);
        client.connect(remoteHost);
//        client.configureBlocking(false);
        System.out.println("客户端连接上服务器...");
        ByteBuffer input = ByteBuffer.allocate(4);
        IntBuffer view = input.asIntBuffer();

        for (int expected = 0; ; expected++) {
            client.read(input);
            int actual = view.get();
            input.clear();
            view.rewind();
            if (actual != expected) {
                System.err.println("期望得到 " + expected + "; 得到是 " + actual);
                break;
            }
            System.out.println(actual);
        }
    }
}
