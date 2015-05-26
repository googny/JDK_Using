package net.inetaddress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 模仿nslookup命令（linux 命令），完成主机名到IP地址以及IP地址到主机名的转换。
 * 主要支持两种交互手段
 * ① 命令式
 * ② 交互式
 * 代码来源  Java 网络编程 （第三版） 第六章 一些有用的程序
 */
public class HostLookup {
    public static void main(String[] args){
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                System.out.println(lookup(args[i]));
            }
        }else{
            BufferedReader reader  = new BufferedReader(new InputStreamReader(System.in));
            String host;
            System.out.println("请输入主机名或IP地址,退出输入\"exit\": ");
            try {
                while(!"exit".equals(host = reader.readLine())){
                    System.out.println(lookup(host));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String lookup(String host){
        InetAddress address;

        try {
            address = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            return "找不到host "+host;
        }

        if (isHostName(host)){
            return address.getHostAddress();
        }else{
            return address.getHostName();
        }
    }

    private static boolean isHostName(String host){
        // 是IPV6 地址不？
        if(host.indexOf(':') != -1){
            return false;
        }

        int lastIndex = host.lastIndexOf('.');
        String slice = host.substring(lastIndex+1);
        boolean isHostName = false;
        try {
            Integer.parseInt(slice);
        } catch (NumberFormatException ex){
            isHostName = true;
        }
        return isHostName;
    }
}
