package net.nonblocking;

import java.nio.ByteBuffer;

/**
 * 学习缓冲区compact方法
 */
public class CompactLearn {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(32);
//        byte[] bytes = "HelloWorld".getBytes();
//        System.out.println(bytes.length);
        buffer.put("HelloWorld".getBytes());
        System.out.println(buffer.position() + " " + buffer.limit() + " " + buffer.capacity());
        buffer.compact();
        System.out.println(buffer.position() + " " + buffer.limit() + " " + buffer.capacity());
        buffer.flip();
        System.out.println(buffer.position() + " " + buffer.limit() + " " + buffer.capacity());
    }
}
