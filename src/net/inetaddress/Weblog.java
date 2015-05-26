package net.inetaddress;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * 修改日志文件里的IP地址转换为主机名
 * 先读取日志文件里的IP地址，然后利用程序转换为主机名
 */
public class Weblog {
    public static void main(String[] args) {
        Date start = new Date();
        try {
//            FileInputStream fin = new FileInputStream(args[0]);
            FileInputStream fin = new FileInputStream("JDKUsing/Web.log");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
            String entry = null;
            while ((entry=reader.readLine() )!= null) {
                int index = entry.indexOf(' ');
                String ip = entry.substring(0,index);
                String theRest = entry.substring(index,entry.length());
                try {
                    InetAddress address = InetAddress.getByName(ip);
                    System.out.println(address.getHostName()+theRest);
                } catch (UnknownHostException ex){
                    System.out.println(entry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Date end = new Date();
        long elapsedTime = (end.getTime() - start.getTime())/1000;
        System.out.println("执行时间："+elapsedTime+" 秒");
    }
}
