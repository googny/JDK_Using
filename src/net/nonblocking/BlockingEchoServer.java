package net.nonblocking;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 利用java.nio包中的类创建阻塞EchoServer
 * 主线程阻塞等待客户端连接，线程池中的工作线程负责处理每个客户连接。
 */
public class BlockingEchoServer {
    private static final int POOL_MULTIPLE = 4;
    private int port = 8000;
    private ServerSocketChannel serverSocketChannel;
    private ExecutorService executorService;

    public BlockingEchoServer() throws IOException {
        executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors() * POOL_MULTIPLE);
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.socket().setReuseAddress(true);
        serverSocketChannel.configureBlocking(true);
        System.out.println("阻塞式回显服务器已经开启...");
    }

    public static void main(String[] args) throws IOException {
        BlockingEchoServer server = new BlockingEchoServer();
        server.service();
    }

    public void service() throws IOException {
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            executorService.execute(new Worker(socketChannel));
        }
    }
}

class Worker implements Runnable {

    private final int BUFFER_SIZE = 512;
    private SocketChannel socketChannel;

    public Worker(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try {
            Socket socket = socketChannel.socket();
            System.out.println("接收到客户连接，来自：" + socket.getInetAddress() + ":" + socket.getPort());

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String msg = null;
            while ((msg = reader.readLine()) != null) {
                System.out.println("收到客户端的消息：" + msg);
                if (msg.equals("bye")) {
                    break;
                } else {
                    writer.write("echo: " + msg + "\r\n");
                    writer.flush();
                    System.out.println("回显给客户端的消息：echo：" + msg);
                }
            }
            if (socketChannel != null) {
                socketChannel.close();
            }
        } catch (IOException e) {
            // catch it
        }
    }
}
