package net.urlanduri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * URLHolder 用来演示 URL类的使用方法。
 * URL 类是final类，不依赖于继承来配置各种类型URL的实例，而是使用策略模式。
 * 协议处理器是策略，而URL类形成选择不同策略的上下文。
 */
public class ConstructURL {
    public static void main(String[] args) {
        /*
        创建URL对象
        除了验证能否识别URL模式外，Java不会对要构造的URL进行任何正确性检查。
        程序员负责确保URL合法地得到创建。
         */
        // 通过字符串构造URL
        // ① public URL(String url) throws MalformedURLException

        try {
            // 必须带上协议哟，这是Java唯一保证要验证的部分
            URL baidu = new URL("http://www.baidu.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // 通过各个部分构造URL
        /*
        public URL(String protocol, String hostname, String file) throws MalformedURLException
        public URL(String protocol, String hostname, int port, String file) throws MalformedURLException
         */
        try {
            URL baiduIndex = new URL("http", "www.baidu.com", "/index.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // 构造相对URL
        /*
        public URL(URL base, String relative) throws MalformedURLException
         */
        try {
            // 在首页 index.html 里面有指向pic.html的链接，pic 指向 http://www.baidu.com/pic.html
            URL index = new URL("http://www.baidu.com/index.html");
            URL pic = new URL(index, "pic.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        /*
        指定一个URLStreamHandler
        所有URL 对象都有URLStreamHandler对象为它们工作，下面两个构造函数需要根据某种协议将默认的URLStreamHandler
        的子类变成自己想要的子类。
        当操作某个虚拟机所不支持的URL模式时，以及添加默认的流处理器未提供的功能时，非常有用。
        public URL(URL base, String relative, URLStreamHandler handler) throws MalformedURLException
        public URL(String protocol, String host, int port, String file, URLStreamHandler handler) throws MalformedURLException
         */
    }
}
