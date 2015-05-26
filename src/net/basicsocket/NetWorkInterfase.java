package net.basicsocket;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * 该类主要演示NetworkInterface 类的使用
 */
public class NetWorkInterfase {
    public static void main(String[] args) throws SocketException, UnknownHostException {
        /*
        NetworkInterface 类用以表示一个本地IP地址，可以是一个物理接口，也可以是绑定于
        同一个物理硬件的虚拟接口，以作为机器的其他IP地址。
         */
        NetworkInterface ni = NetworkInterface.getByName("eth0"); // 仅仅是示例
        if(ni == null){
            System.err.println("没有对应的网卡：etho0");
        }

        NetworkInterface local = NetworkInterface.getByInetAddress(InetAddress.getByName("localhost"));
        if(local == null){
            System.err.println("没有对应的网卡：etho1");
        }else {
            System.out.println(local.getDisplayName());
            System.out.println(Arrays.toString(local.getHardwareAddress()));
        }


        /*
        遍历机器上的所有网络接口
         */
        Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()){
            NetworkInterface nic = (NetworkInterface) interfaces.nextElement();
            System.out.println(nic.getIndex());
            System.out.println(nic.getDisplayName());
            System.out.println(nic);
        }

    }
}
