package io.file;

import java.io.File;
import java.io.IOException;

/**
 * 该类主要演示File类的各个调用的使用
 * mti1301
 * 2015/6/9.
 */
public class FileCreation {
    public static void main(String[] args) throws IOException {
        String userDirPath = System.getProperty("user.dir");
        File userDir = new File(userDirPath);
        File subFile = new File(userDirPath + File.separator + "hello.txt");
        if (!subFile.exists()) {
            subFile.createNewFile();
        }
        if (subFile.canExecute()) {
            subFile.setExecutable(false);
        }

        if (!subFile.canRead()) {
            subFile.setReadable(true);
        }
        if (subFile.canWrite()) {
            subFile.setWritable(false);
        }

        File tmpFile = File.createTempFile("tttt", "tmp");
        tmpFile.deleteOnExit();
        File tmpFile2 = File.createTempFile("tttt", "tmp", userDir);
        tmpFile2.deleteOnExit();

        System.out.println("parent file is : " + userDir.equals(subFile.getParentFile()));

        File absoluteFile = subFile.getAbsoluteFile();
        System.out.println("absoluteFile: " + absoluteFile);

        String absolutePath = subFile.getAbsolutePath();
        System.out.println("File is absolute: " + subFile.isAbsolute());
        System.out.println("absolutePath: " + absolutePath);

        File canonicalFile = subFile.getCanonicalFile();
        System.out.println("canonicalFile: " + canonicalFile);

        String canonicalPath = subFile.getCanonicalPath();
        System.out.println("canonicalPath: " + canonicalPath);

        System.out.println("user dir total space " + userDir.getTotalSpace());
        System.out.println("user dir used space " + userDir.getUsableSpace());
        System.out.println("user dir free space " + userDir.getFreeSpace());

        System.out.println("Sub file name is " + subFile.getName());
        System.out.println("Parent file name is " + subFile.getParent());

        System.out.println("Sub file path is " + subFile.getPath());

        System.out.println("length of file: " + subFile.length());

        subFile.renameTo(new File(userDirPath + File.separator + "helloAgain.txt"));
        System.out.println("after rename the file name is: " + subFile.getName());
        subFile.deleteOnExit();
    }
}
