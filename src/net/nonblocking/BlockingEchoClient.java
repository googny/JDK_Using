package net.nonblocking;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * 阻塞回显客户端
 */
public class BlockingEchoClient {
    private SocketChannel socketChannel;


    public BlockingEchoClient(String host, int port) throws IOException {
        socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
        System.out.println("与服务器 " + host + ": " + port + "连接建立成功");
    }

    public static void main(String[] args) throws IOException {
        BlockingEchoClient client = new BlockingEchoClient("localhost", 8000);
        Scanner scanner = new Scanner(System.in);
        String msg = null;
        while (true) {
            msg = scanner.nextLine();
            client.writeMsg(msg);
            if ("bye".equals(msg)) {
                break;
            } else {
                client.readMsg();
            }
        }
    }

    public void talk() throws IOException {
        Socket socket = socketChannel.socket();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String msg = null;
        while (true) {
            msg = input.readLine();
            writer.write(msg + "\r\n");
            if ("bye".equals(msg)) {
                break;
            } else {
                String rcvStr = reader.readLine();
                System.out.println("服务器回显的消息：" + rcvStr);
            }
        }
        if (socketChannel != null) {
            socketChannel.close();
        }
    }

    public void writeMsg(String msg) throws IOException {
        Socket socket = socketChannel.socket();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write(msg + "\r\n");
        writer.flush();
        System.out.println("发送给服务器的消息：" + msg);
    }

    public void readMsg() throws IOException {
        Socket socket = socketChannel.socket();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String msg = null;
        msg = reader.readLine();
        System.out.println("服务器回显的消息：" + msg);
    }
}
