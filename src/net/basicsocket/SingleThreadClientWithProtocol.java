package net.basicsocket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by mti1301 on 2015/5/26.
 */
public class SingleThreadClientWithProtocol {
    public static void main(String[] args) throws IOException {
        boolean shutdown = false;
        while (!shutdown){
            Socket client = new Socket("localhost",9090);
            BufferedInputStream reader = new BufferedInputStream(client.getInputStream());
            BufferedOutputStream writer = new BufferedOutputStream(client.getOutputStream());

            Scanner scanner = new Scanner(System.in);
            System.out.print("输入消息: ");
            String in = scanner.nextLine();

            writer.write(in.length());
            writer.write(in.getBytes());
            writer.flush();
            System.out.println("发送到服务器消息: "+in);

            if("shutdown".equals(in)){
                shutdown = true;
            }else{
                int len = reader.read();
                byte [] rs = new byte[len];
                reader.read(rs); // read the last bytes

                String receive = new String(rs);
                System.out.println("接收到服务器消息: "+receive);
            }

            if (client != null){
                client.close();
            }
        }
    }
}
