package net.urlanduri;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * URL 类不自动执行编码或解码
 * 可以使用无效的ASCII字符和非ASCII字符以及百分号转义字符来构造URL对象
 * 这样的字符不会再getPath()和toExternalForm()等方法输出时自动编码或解码，
 * 由用户负责确保这样的字符在用于构造URL对象的字符串中正确的编码
 * public class URLDecoder extends Object
 * |--
 * public class URLEncoder extends Object
 * |--public static String encode(String s, String encoding) throws UnsupportedEncodingException
 * 该方法把所有非字母数字字符改变为%序列（除了空格[+]、下划线、连字符、点和星号字符）
 */
public class Coder {

    public static void main(String[] args) throws UnsupportedEncodingException {
        encoderTest();
        encoderTest();
    }

    public static void encoderTest() throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode("this string has 空格", "UTF-8"));
        System.out.println(URLEncoder.encode("this*string*has*星星", "UTF-8"));// 没编码
        System.out.println(URLEncoder.encode("this%string%has%百分号", "UTF-8"));
        System.out.println(URLEncoder.encode("this+string+has+加号", "UTF-8"));
        System.out.println(URLEncoder.encode("this/string/has/反斜杠", "UTF-8"));
        System.out.println(URLEncoder.encode("this\"string\"has\"转义字符", "UTF-8"));
        System.out.println(URLEncoder.encode("this:string:has:冒号", "UTF-8"));
        System.out.println(URLEncoder.encode("this~string~has~波浪线", "UTF-8"));
        System.out.println(URLEncoder.encode("this(string)has(小括号)", "UTF-8"));
        System.out.println(URLEncoder.encode("this.string.has.点号", "UTF-8")); // 没编码
        System.out.println(URLEncoder.encode("this=string=has=等号", "UTF-8"));
        System.out.println(URLEncoder.encode("this&string&has&与操作符", "UTF-8"));


    }

    public void decoderTest() throws UnsupportedEncodingException {
        System.out.println(URLDecoder.decode(URLEncoder.encode("this string has 空格", "UTF-8"), "UTF-8"));
        System.out.println(URLDecoder.decode(URLEncoder.encode("this*string*has*星星", "UTF-8"),"UTF-8"));// 没编码
        System.out.println(URLDecoder.decode(URLEncoder.encode("this%string%has%百分号", "UTF-8"),"UTF-8"));
        System.out.println(URLDecoder.decode(URLEncoder.encode("this+string+has+加号", "UTF-8"),"UTF-8"));
        System.out.println(URLDecoder.decode(URLEncoder.encode("this/string/has/反斜杠", "UTF-8"),"UTF-8"));
        System.out.println(URLDecoder.decode(URLEncoder.encode("this\"string\"has\"转义字符", "UTF-8"),"UTF-8"));
        System.out.println(URLDecoder.decode(URLEncoder.encode("this:string:has:冒号", "UTF-8"),"UTF-8"));
        System.out.println(URLDecoder.decode(URLEncoder.encode("this~string~has~波浪线", "UTF-8"),"UTF-8"));
        System.out.println(URLDecoder.decode(URLEncoder.encode("this(string)has(小括号)", "UTF-8"),"UTF-8"));
        System.out.println(URLDecoder.decode(URLEncoder.encode("this.string.has.点号", "UTF-8"),"UTF-8")); // 没编码
        System.out.println(URLDecoder.decode(URLEncoder.encode("this=string=has=等号", "UTF-8"),"UTF-8"));
        System.out.println(URLDecoder.decode(URLEncoder.encode("this&string&has&与操作符", "UTF-8"),"UTF-8"));

    }
}
