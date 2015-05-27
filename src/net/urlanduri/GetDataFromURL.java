package net.urlanduri;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

/**
 * 从URL指向的文档中取得数据
 * URL类有几个URL获取数据的方法
 * public InputStream openStream() throws IOException
 * public URLConnection openConnection() throws IOException
 * public URLConnection openConnection(Proxy proxy) throws IOException
 * public Object getContent() throws IOException
 * public Object getContent(Class[] classes) throws IOException
 * 这些方法的区别在于它们以不同类的实例返回位于URL的数据
 */
public class GetDataFromURL {
    public static void main(String[] args) throws IOException {
        URL baidu = new URL("http://www.baidu.com");

        /*
        InputStream 流中获取的数据是URL所指向文件的原始内容
        不包含任何HTTP首部或任何与协议有关的信息
        读此InputStream和任何其他InputStream相同
         */
        InputStream inStream = baidu.openStream();

        /*
        上面的方法只能读取最原始的内容，如果读取的是文本信息，涉及到编码问题
        不能使用URL类读取首部，但可以通过openConnection() 方法返回的URLConnection对象读取
        通过URLConnection 对象的一些get操作，可以获取协议的元数据。
        Http协议就是Http首部
         */
        URLConnection connection = baidu.openConnection();
        InputStream input = connection.getInputStream();

        /*
        下面的方法，可指定传递连接所通过的代理服务器
        如果协议处理器不支持代理，次参数将被忽略，直接连接
         */
        // URLConnection proxyConn = baidu.openConnection(proxy);

        /*
        getContent() 获取由URL指向的数据，尝试将其转换为某种对象。
        如果URL指向某种文本对象，ASCII或者HTML文件，通常会转化为InputStream
        如果URL指向图片，通常会转化为java.awt.ImageProducer实现类
        该方法通过查看获取数据的MIME首部中的Content-type字段进行操作。
        如果没有该首部或者不认识Content-type 字段，则返回InputStream实例
         */
        Object content = baidu.getContent();
        // HttpInputStream(HttpURLConnection 内部类)
        System.out.println(content.getClass().getName());

        /*
        内容处理器为一个对象提供不同角度的解释
        下面的方法尝试以数组中的顺序返回URL的内容
        首选将HTML返回为String
        第二个选择是Reader
        第三个选择是InputStream
         */
        Object obj = baidu.getContent(new Class[]{String.class, Reader.class, InputStream.class});

        if (obj instanceof String) {
            System.out.println("是String 类型");
            System.out.println(obj);
        } else if (obj instanceof Reader) {
            System.out.println("是Reader 类型");
            int count;
            Reader reader = (Reader) obj;
            while ((count = reader.read()) != -1) {
                System.out.print((char) count);
            }
            System.out.println();
        } else if (obj instanceof InputStream) {
            System.out.println("是InputStream 类型");
            int count;
            InputStream in = (InputStream) obj;
            while ((count = in.read()) != -1) {
                System.out.write((byte) count);
            }
            System.out.println();
        } else {
            System.out.println("不认识的格式");
        }
    }
}
