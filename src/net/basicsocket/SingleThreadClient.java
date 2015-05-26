package net.basicsocket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * 该类实现单线程的客户端，该客户端循环向服务器发送信息，
 * 然后从服务器端接受回显消息
 *
 * @author tonghaoqi
 * @version 1.0.0
 */
public class SingleThreadClient {
    public static void main(String[] args) throws IOException {
        boolean shutdown = false;
        while (!shutdown){
            Socket client = new Socket("localhost",9090);
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
//            PrintStream writer = new PrintStream(client.getOutputStream());

            Scanner scanner = new Scanner(System.in);
            System.out.print("输入消息: ");
            String in = scanner.nextLine();

//            writer.println(in);
            writer.write(in+'\n');
            writer.flush();
            System.out.println("发送到服务器消息: "+in);

            if("shutdown".equals(in)){
                shutdown = true;
            }else{
                String receive = reader.readLine();
                System.out.println("接收到服务器消息: "+receive);
            }

            if (client != null){
                client.close();
            }
        }
    }
}
