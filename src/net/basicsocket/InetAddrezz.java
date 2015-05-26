package net.basicsocket;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;

/**
 * 该类主要示例InetAddress类的使用，以及过程中需要注意的方法
 */
public class InetAddrezz {

    public static void main(String[] args) throws IOException {

        /*
            以下三个方法都会在必要时连接本地DNS服务器，填充InetAddress对象中的信息
            ① 在禁止连接DNS服务器时，方法会抛出安全异常
            ② 连接一个尚未连接的主机可能拨号到供应商。
            这些方法不只是使用参数来填充内部字段，实际上要进行网络连接来获取所需的信息
         */
        InetAddress address = InetAddress.getByName("www.baidu.com");
        InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");
        InetAddress localhost = InetAddress.getLocalHost();

        /*
            getAddress() 和getHostName() 主要使用以上静态方法提供的信息
            它们不进行网络连接，即使进行网络连接也不会抛出异常。
            当getByName()参数是ip地址字符串时，会进行实际的DNS查询
         */
        System.out.println(address);
        System.out.println(Arrays.toString(address.getAddress()));
        System.out.println(address.getHostName());

        System.out.println("========================");
        for (InetAddress addrezz : addresses) {
            System.out.println(addrezz);
        }

        System.out.println("========================");
        System.out.println(localhost);
        System.out.println("========================");


        /*
            以下两个方法不用本地DNS服务器检查地址
            第一个方法用IP地址但不用主机名，创建一个InetAddress对象。
            第二个方法用IP地址和主机名共同创建一个InetAddress对象

            不保证存在这样的主机，也不保证主机名能正确的映射到该IP地址
            只做address参数传递的字节数组大小检查
         */
        InetAddress addressByIP = InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
        InetAddress addressByIPAndHostName = InetAddress.getByAddress("localhost", new byte[]{127, 0, 0, 1});
        System.out.println("========通过IP或IP和主机名获取InetAddress对象=========");
        // addressByIP.getHostName();
        System.out.println(addressByIP);
        System.out.println(addressByIPAndHostName);
        System.out.println("===================================================");


        // InetAddress 获取方法
        /*
        InetAddress 包含三个获取方法
        返回字符串形式的主机名 String getHostName()
        返回字符串形式的IP地址 String getHostAddress()
        返回字节数组形式的IP地址 byte[] getAddress()
         */
        String hostName = address.getHostName();
        String ipStr = address.getHostAddress();
        byte[] ipBytes = address.getAddress();// 返回的字节是无符号的，值大于127的会变成负数

        System.out.println(hostName);
        System.out.println(ipStr);
        for (byte b : ipBytes) {
            System.out.print(b < 0 ? b + 256 : b);
            System.out.print(' ');
        }
        System.out.println();

        /*
          地址类型
          有些IP地址和地址模式有特殊的意义。
          以下方法可以测试InetAddress 对象是否符合某一项标准
        */
        /*
         ①public boolean isAnyLocalAddress()
         这个方法在地址是通配地址时返回true，否则返回false。
         通配地址可匹配本地系统中的所有地址。
         对于有几个网络接口的系统而言，很重要。
          */
        InetAddress anyLocalAddress = InetAddress.getByName("0.0.0.0");
        System.out.println(anyLocalAddress.getHostName());
        System.out.println(anyLocalAddress.isAnyLocalAddress());

        /*
        ② public boolean isLoopbackAddress()
        这个方法在地址是回路地址时返回true，否则返回false
        回路地址直接在IP层连接同一台计算机，不适用任何物理硬件。
        连接回路地址，与从同一个系统中连接系统的正常IP有所不同
         */
        InetAddress loopbackAddress = InetAddress.getByName("127.0.0.1");
        System.out.println(loopbackAddress.getHostName());
        System.out.println(loopbackAddress.isLoopbackAddress());

        /*
        ③ public boolean isMulticastAddress()
        这个方法在地址是组播地址时返回true，否则返回false。
        组播内容广播给所有预定的计算机，而不是某台计算机
        IPV4 中组播地址是 224.0.0.0 ~ 239.255.255.255范围内
         */
        InetAddress multiCastAddress = InetAddress.getByName("224.0.0.1");
        System.out.println(multiCastAddress.getHostName());
        System.out.println(multiCastAddress.isMulticastAddress());

        //  测试可达性
        /*
        InetAddress 有两个方法来测试当前主机能够到达某个节点，即是否能进行一个网络连接。
        连接可能由于很多原因阻塞，防火墙，代理服务器，行为失常的路由器，断开的线缆等
        ① boolean isReachable(int timeout)  IOException
        ② boolean isReachable(NetworkInterface interface,int ttl, int timeout)  IOException
        这些方法尝试连接远程主机网站的echo端口，查看是否可达。
        如果在timeout内响应，则返回true，否则false。如果网络出错抛出IOException异常
        第二个方法还可以指定进行连接的本地接口和生存时间
        在局域网中使用效果好。。
         */
        InetAddress baidu = InetAddress.getByName("www.baidu.com");
        System.out.println(baidu.getHostName());
        System.out.println(baidu.getHostAddress());
        System.out.println(baidu.isReachable(3000));

        InetAddress loopback = InetAddress.getByName("localhost");
        System.out.println(loopback.getHostName());
        System.out.println(loopback.getHostAddress());
        System.out.println(loopback.isReachable(3000));







    }
}