package net.basicsocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 */
public class ClientTester {
    public static void main(String[] args) {
        int port = 9090;
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("服务器监听端口为：" + server.getLocalPort());
            while (true) {
                Socket socket = server.accept();
                Thread inputThread = new Thread(new InputThread(socket.getInputStream()));
                Thread outputThread = new Thread(new OutputThread(socket.getOutputStream()));
                inputThread.start();
                outputThread.start();

                inputThread.join();
                outputThread.join();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                }
            }
        }
    }
}

class InputThread implements Runnable {

    private InputStream input;

    public InputThread(InputStream input) {
        this.input = input;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int recv = input.read();
                if (recv == -1) break;
                System.out.write(recv);
                System.out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class OutputThread implements Runnable {
    private BufferedWriter output;

    public OutputThread(OutputStream output) {
        this.output = new BufferedWriter(new OutputStreamWriter(output));
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String read = scanner.next();
            if (".".equals(read)) break;
            try {
                output.write(read + "\r\n");
                output.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        scanner.close();
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}