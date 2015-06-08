package io.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 遍历目录下指定文件类型的文件名
 * mti1301
 * 2015/6/8.
 */
public class DirListWithFilter {
    public static void main(String[] args) {
        File path = null;
        if (args.length > 0) {
            path = new File(args[0]);
        } else {
            Scanner scanner = new Scanner(System.in);
            path = new File(scanner.nextLine());
        }
        String[] list = path.list(new DirFilter("java"));
        Arrays.sort(list,String.CASE_INSENSITIVE_ORDER);
        for (String dirItem:list){
            System.out.println(dirItem);
        }
    }

    static class DirFilter implements FilenameFilter {
        private Pattern pattern;
        private String stuffix;

        public DirFilter(String regex) {
            pattern = Pattern.compile(regex);
            stuffix = regex;
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(stuffix);
//            System.out.println("in FileNameFilter " + dir.getName()+"   "+name);
//            return patterns.matcher(name).matches();
        }
    }
}
