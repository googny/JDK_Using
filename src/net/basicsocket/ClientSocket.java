package net.basicsocket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 数据在网络中是以有限大小的包形式传输的，这些包称为数据报。
 * 每个数据报包含一个首部和一个有效载荷。
 * 首部包含目的地址和端口、源地址和端口以及用于保证可靠传输的各种其他管理信息。
 * 有效载荷包含数据本身。 由于数据报长度有限， 必须将数据分割成多个包，在目的地再重组。
 * 也有可能需要重传；或者乱序到达目的地，需要重排序。
 * 这些事情需要大量复杂的代码。
 * Socket 已经帮我们做了一层抽象，程序员可将网络连接视为一种可以读取字节的流。
 * Socket 掩盖了底层细节，如纠错、包大小、包重传、网络地址等
 */
public class ClientSocket {

    public static void main(String[] args) {
        /*
        Socket是两台主机之间的一个连接，可以进行七项基本操作：
        ① 连接远程机器/关闭连接
        ② 发送/接受数据
        ③ 绑定端口
        ④ 监听入站数据
        ⑤ 在所绑定端口上接收来自远程主机的连接
         */

        /*
        使用客户端Socket的过程：
        ① 构造一个Socket对象
        ② 与服务器端建立连接
        ③ 发送/接收数据（取决于具体的协议）
        ④ 关闭连接（Http 无状态）
         */
        //创建一个Socket
        // public Socket(String host, int port) throws UnknownHostException,IOException
        // public Socket(InetAddress host,int port) throws IOException
        try {
            Socket toBaidu = new Socket("www.baidu.com", 80);
        } catch (UnknownHostException ex) {
            System.out.println("主机名错误");
        } catch (IOException e) {
            System.out.println("IO错误：主机不可达，拨号故障，路由错误");
        }

        /*
         public Socket(String host, int port, InetAddress interface,int localPort) throws UnknownHostException,IOException
         public Socket(InetAddress host, int port, InetAddress interface,int localPort) throws IOException
         host 远程主机
         port 远程主机端口
         Interface 本地网络接口
         localPort 本地端口　（０不关心使用哪个端口）
          */

        /*
        通过Socket对象可以获取一些相关信息
        ① 获取本地/远端主机InetAddress对象
        ② 获取本地/远端端口
        ③ 获取输入/输出流
         */

        /*
        使用完Socket之后，切记关闭Socket
        ① close() 方法 释放资源，清除工作
        ② isClosed() 检查是否关闭（即使Socket从没打开过，也返回false）
        ③ isConnected() 检查socket是否曾经连接过远程主机（连接成功过，即使socket已经关闭也返回true）
        ④ isBound() socket是否绑定于本地系统的向外端口

        半关闭
        ① shutdownInput() 关闭输入流，继续读取输入流，得到-1
        ② shutdownOutput() 关闭输出流，继续写入输出流，则抛出IO异常
        ③ isInputShutdown() 检测
        ④ isOutputShutdown() 检测
        即使半关闭了连接，甚至两次半关闭，仍需要在结束后关闭该socket
         */

        /*
        Socket 选项
        TCP_NODELAY
        设置方法：
        public void setTcpNoDelay(boolean on) throws SocketException
        public boolean getTcpNoDelay() throws SocketException
        设置TCP_NODELAY为true，可确保包被尽可能快的发送，而无论包的大小。
        （正常情况下，小的包在发送前会组合成大点的包。在发送另一个包之前，本地主机要等待远程系统对前一个包的回应
        TCP_NODELAY 打破这种缓冲模式，这样所有包已就绪就能发送）
        ==========================================
        SO_BINDADDR

        SO_TIMEOUT
        设置方法：
        public void setSoTimeout(int milliseconds) throws SocketException 0 被认为无限制
        public getSoTimeout() throws SocketException
        设置最长的阻塞时间，超时则抛出InterruptedIOException异常，应当捕获这个异常，
        但此时socket仍然是连接的。虽然此次read()失败了，但可再次尝试读取该socket。
        ==========================================
        SO_LINGER
        设置方法
        public void setSoLinger(boolean on,int seconds) throws SocketException
        public int getSoLinger() throws SocketException -1 表示该选项被禁用，尽可能多的时间发送剩余数据
        该选项规定，当socket关闭时如何处理尚未发送的数据报。
        默认情况下，close()方法将立即返回，但系统仍会尝试发送剩余的数据。
        如果延时设置为0，那么socket关闭时，所有未发送的数据将被丢弃。
        如果延时设置为正，那么close()方法将会阻塞指定的秒数，等待数据发送和接收回应。
        ==========================================
        SO_SNDBUF
        设置方法
        public void setSendBufferSize(int size) throws SocketException,IllegalArgumentException
        public int getSendBufferSize() throws SocketException
        和SO_RCVBUF类似
        ==========================================
        SO_RCVBUF
        设置方法
        public void setReceiveBufferSize(int size) throws SocketException,IllegalArgumentException
        public int getReceiveBufferSize() throws SocketException
        TCP栈使用缓冲区提升网络性能，
        （传输大的连续的数据块）较大的缓冲区会提升快速连接的网络性能
        （交互式的）较慢的拨号连接在较小的缓冲区下表现更佳
        ==========================================
        SO_KEEPLIVE
        设置方法
        public void setKeepAlive(boolean on) throws SocketException
        public boolean getKeepAlive() throws SocketException
        默认为false，如果启用，客户端会偶尔通过一个空闲连接发送一个数据包，以确保服务器未崩溃。
        如果服务器没能响应此包，客户端会持续12分钟，知道收到响应为止，如果在12分钟内未收到响应，客户端关闭socket。
        没有SO_KEEPALIVE，不活动的客户端可能永久存在下去，而不会注意到服务器已经崩溃。
        ===========================================
        OOBINLINE
        设置方法
        public void sendUrgentData(int data) throws IOException // 发送最低低字节
        默认情况下，Java 忽略从Socket收到的紧急数据
        如果希望接收正常数据中的紧急数据
        public void setOOBInline(boolean on) throws SocketException
        public boolean getOOBInline() throws SocketException
        TCP可以发送一个字节的"紧急"数据，这种数据将被立即发送，此外，当接收方收到紧急数据时会得到通知，可以选择在处理
        其他所有已经收到的数据之前先处理紧急数据。
         */


    }
}
