package zifu;

import java.util.Scanner;

/**
 * @author liujinbo
 * @create 2021-03-19 11:51
 * 左旋字符串，可以拆解为三部分反转求解，即n之前反转，n之后反转，全部反转
 */
public class zuoxuan {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        LeftRotateString(s,3);
        System.out.println(LeftRotateString(s,3));
    }
    public static String LeftRotateString(String str, int n) {
        if (n >= str.length())
            return str;
        char[] chars = str.toCharArray();
        reverse(chars, 0, n - 1);
        reverse(chars, n, chars.length - 1);
        reverse(chars, 0, chars.length - 1);
        return new String(chars);
    }
    public static void reverse(char[] chars, int i, int j) {
        while (i < j)
            swap(chars, i++, j--);
    }
    public static void swap(char[] chars, int i, int j) {
        char t = chars[i];
        chars[i] = chars[j];
        chars[j] = t;
    }
}
