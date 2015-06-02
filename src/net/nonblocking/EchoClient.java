package net.nonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * 回显字符串的客户端
 */
public class EchoClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        SocketChannel client = SocketChannel.open();
        InetSocketAddress address = new InetSocketAddress("localhost",8080);
        client.connect(address);
        System.out.println("连接服务器成功...");

        ByteBuffer buffer = ByteBuffer.allocate(5);
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        while (true){
            String in = scanner.nextLine();
//            ++i;
//            String in = i+"";
            if(in.equals("bye")){
                break;
            }
            buffer.put(in.getBytes());
            buffer.flip();
            client.write(buffer);
            buffer.clear();
            client.read(buffer);
            buffer.flip();
            System.out.println(new String(buffer.array(),0,buffer.limit()));
            buffer.clear();
        }
        client.close();
    }
}
