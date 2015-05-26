package net.basicsocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 该类实现单线程的服务器，每次接受一个请求，处理一个请求，然后关闭socket，处理
 * 下一个请求。
 *
 * @author tonghaoqi
 * @version 1.0.0
 */
public class SingleThreadServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9090);
        boolean shutdown = false;

        while(!shutdown){
            Socket socket = server.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            PrintStream writer = new PrintStream(socket.getOutputStream());

            String in = reader.readLine();
            System.out.println("接收到客户端信息: "+in);
            if("shutdown".equals(in)){
                shutdown = true;
                System.out.println("即将关闭服务器...");
            }else {
                writer.write("echo: "+in+'\n');
                writer.flush();
//                writer.println("echo: "+in);
            }


            if(socket != null){
                socket.close();
            }
        }


        if (server != null) {
            server.close();
        }
    }
}
