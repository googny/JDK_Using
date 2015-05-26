package net.inetaddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 该类来示例IP地址特性，只列出我们感兴趣的方法
 */
public class IPCharacteristics {
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName(args[0]);
            if(address.isAnyLocalAddress()){
                System.out.println(address+" 是一个通配地址");
            }
            if(address.isLoopbackAddress()){
                System.out.println(address+" 是一个回环地址");
            }
            if (address.isMulticastAddress()){
                System.out.println(address+" 是一个多播地址");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
