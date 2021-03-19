package zifu;

import java.util.Scanner;

/**
 * @author liujinbo
 * @create 2021-03-19 15:07
 * 给你一个字符串 `s`，找到 `s` 中最长的回文子串
 */
public class longestchuan {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        System.out.println(longestPalindrome(s));
    }
    public static String longestPalindrome(String s) {
        if (s == null || s.length() == 0) return s;
        int start = 0, end = 0; // 记录起始位置
        for (int i = 0; i < s.length(); i++) {
            // 两种情况 以i为中心，以i和i+1为中心
            int len1 = expand(s, i - 1, i + 1); // 中心扩展
            int len2 = expand(s, i, i + 1);
            int len = Math.max(len1, len2); // 取最长的长度
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private static int expand(String s, int l, int r) {
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        // 这里要注意
        return r - l - 1;
    }
}
