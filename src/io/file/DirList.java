package io.file;

import java.io.File;
import java.util.Scanner;

/**
 * 遍历目录结构
 * 递归遍历目录里面的所有文件和文件夹
 * mti1301
 * 2015/6/8.
 */
public class DirList {
    public static void main(String[] args) {
        String filePath = null;
        if (args.length > 0) {
            filePath = args[0];
        } else {
            Scanner scanner = new Scanner(System.in);
            filePath = scanner.nextLine();
            scanner.close();
        }
        File file = new File(filePath);
        listDirectory(file);
    }

    public static void listDirectory(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File subfile : files) {
                System.out.println(subfile.getParent() + "-->" + subfile.getName());
            }
            for (File subfile : files) {
                if (subfile.isDirectory()) {
                    listDirectory(subfile);
                }
            }
        } else {
            System.out.println(file.getName());
        }
    }
}
