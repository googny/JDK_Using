package net.urlanduri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 分解URL
 * URL 由以下五部分组成
 * ① 模式 （协议）
 * ② 授权机构
 * ③ 路径
 * ④ 片段标识符（段或ref）
 * ⑤ 查询字符串
 */
public class ResolveURL {
    public static void main(String[] args) {
        /*
        http://www.ibiblio.org/javafaq/books/jnp/index.html?isbn=15697865546#toc
        模式是 http
        授权机构是 www.ibiblio.org
        路径是 /javafaq/books/jnp/index.html
        片段标识符是 toc
        查询字符串是 isbn=15697865546
         */
        try {
            URL url = new URL("http://www.ibiblio.org/javafaq/books/jnp/index.html?isbn=15697865546#toc");
            System.out.println("协议："+url.getProtocol());
            System.out.println("授权机构："+url.getAuthority());
            System.out.println("路径："+url.getPath());
            System.out.println("片段："+url.getRef());
            System.out.println("查询字符串："+url.getQuery());
            System.out.println("===============");
            System.out.println("文件："+url.getFile()); // 主机名后第一个/到开始片段标识符的#号之前
            System.out.println("主机名？："+url.getHost());
            System.out.println("端口："+url.getPort()); // 没有显示指定端口返回 -1
            System.out.println("默认端口："+url.getDefaultPort()); // http 默认为80
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
