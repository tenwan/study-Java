package zifu;

import java.util.Scanner;

/**
 * @author liujinbo
 * @create 2021-03-19 15:05
 * 将一个字符串转换成一个整数，要求不能使用字符串转换整数的库函数。 数值为0或者字符串不是一个合法的数值则返回0
 */
public class toInt {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        System.out.println(StrToInt(s));
    }
    public static int StrToInt(String str) {
        if (str == null || str.length() == 0)
            return 0;
        // 注意第一个字符是否是-
        boolean isNegative = str.charAt(0) == '-';
        int ret = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // 跳过第一个字符
            if (i == 0 && (c == '+' || c == '-'))  /* 符号判定 */
                continue;
            // 防止非法输入
            if (c < '0' || c > '9')                /* 非法输入 */
                return 0;
            // 正常操作，注意“0”
            ret = ret * 10 + (c - '0');
        }
        return isNegative ? -ret : ret;
    }
}
