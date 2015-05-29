package net.basicsocket;

/**
 * ServerSocket使用示例，主要适用于地吞吐量服务器
 * 要使用Java 编写服务器，ServerSocket类指定了所需的所有方法。
 * 其中包括创建新ServerSocket对象的构造方法，
 * 监听端口的方法
 * 配置各种服务器Socket选项的方法
 * 以及平常的杂项方法
 * Java 服务器程序的基本声明周期是：
 * ① 使用ServerSocket() 构造方法在某端口上创建一个新的ServerSocket
 * ② ServerSocket使用其accept() 方法监听此端口上的入站连接（阻塞等待）
 * ③ 读取/写出数据
 * ④ 客户端和服务器进行交互，直到关闭连接
 * ⑤ 服务器或客户端关闭连接
 * ⑥ 等待下一次连接
 */
public class ServerSocket {
    public static void main(String[] args) {
        /*
        如果客户端与服务器端交互时间很长，那么较好的方式是每个请求到来，服务器端开辟一个线程来处理该请求。主线程继续阻塞等待下一个请求
        操作系统把指向某个端口的入站连接请求，存储在先进先出的队列中，队列的默认长度一般为50。
        未处理连接的队列达到其容量时，主机会拒绝与此端口的额外连接，直到队列有位置。
        许多客户端在首次连接被拒绝后会再次尝试多次进行连接。
        入站连接和队列会由操作系统管理，程序不需要关心这一点，如果可以调节队列长度，那么也无法将长度调整到大于操作系统支持的最大长度。
         */

        /*
        ServerSocket 构造方法
        ServerSocket() throws IOException
        ServerSocket(int port) throws IOException
        ServerSocket(int port, int backlog) throws IOException
        ServerSocket(int port, int backlog,InetAddress bindAddr) throws IOException
        参数port指定服务器要绑定的端口（监听端口）
        参数backlog指定客户连接请求队列的长度
        参数bindAddr指定服务器要绑定的IP地址
         */



    }

}
