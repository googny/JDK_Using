package net.urlanduri;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

/**
 * 代理的目的： 安全（限制），性能（缓存）
 * 基于URL类的Java程序可以通过多数常见代理服务器和协议进行工作
 * 而不是在原始socket之上运行自己的HTTP或者其他客户端
 */
public class Proxyz {
    public static void main(String[] args) {
        /*
         对于基本操作，所有要做的就是设置一些系统属性，
         指出本地代理服务器的地址。
         如果是纯粹的HTTP代理，
         使用http.proxyHost 设置代理服务器的域名或IP地址
         使用http.proxyPort 设置代理服务器的端口(默认 80)
          */
        System.setProperty("http.proxyHost","192.168.2.170");
        System.setProperty("http.proxyPort","8080");
        System.setProperty("http.nonProxyHosts","192.168.2.170|www.baidu.com|*.baidu.com");

        /*
        Java中允许为不同的远程主机选择不同的代理服务器。
        代理本身用Java.net.Proxy类的实例来表示
        有三种代理：Http，Socks和直接连接 ，Proxy.Type枚举
         */
        SocketAddress address = new InetSocketAddress("googny.com",80);
        Proxy proxy = new Proxy(Proxy.Type.HTTP,address);
    }
}