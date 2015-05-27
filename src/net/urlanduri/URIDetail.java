package net.urlanduri;

/**
 * URI 是对统一资源定位以及统一资源名的URL抽象。
 * 大多数URI都是URL
 * 两者的主要区别在于
 * ① URI类纯粹是关于资源的标识和URI的解析。它没有提供方法来获取URI所标识的资源。
 * ② 相对于URL类，URI类与相关的规范更一致
 * ③ URI对象可以表示相对URI。URL在存储URI之前会将其绝对化。
 */
public class URIDetail {
    public static void main(String[] args) {
        // URI 对象纯粹是可以解析和操作的字符串，URI类没有网络获取功能
        // 构造URI，它从字符串构建。只要是URI语法上正确，Java 就不需要为了创建用于表示的URI对象而理解其协议
        // public URI(String uri) throws URISyntaxException
        // public URI(String scheme,String schemeSpecificPart,String fragment) throws URISyntaxException
        // public URI(String scheme,String host,String path,String fragment) throws URISyntaxException
        // public URI(String scheme,String authority,String path,String query,String fragment) throws URISyntaxException
        // public URI(String scheme,String userInfo,String host,int port,String path,String query,String fragment) throws URISyntaxException
        // public static URI create(String uri)

    }
}
