package net.basicsocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by mti1301 on 2015/5/26.
 */
public class SingleThreadServerWithProtocol {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9090);
        boolean shutdown = false;

        while(!shutdown){
            Socket socket = server.accept();
            BufferedInputStream reader = new BufferedInputStream(socket.getInputStream());
            BufferedOutputStream writer = new BufferedOutputStream(socket.getOutputStream());

            int len = reader.read();
            byte [] rs = new byte[len];
            reader.read(rs); // read the last bytes

            String in = new String(rs);
            System.out.println("接收到客户端信息: "+in);
            if("shutdown".equals(in)){
                shutdown = true;
                System.out.println("即将关闭服务器...");
            }else {
                String echo = "echo: "+in;
                writer.write(echo.length()); // send the length firstly
                writer.write(echo.getBytes());
                writer.flush();
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
