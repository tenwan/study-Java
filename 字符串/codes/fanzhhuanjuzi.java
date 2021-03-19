package zifu;

import java.util.Scanner;

/**
 * @author liujinbo
 * @create 2021-03-19 12:02
 * 反转一个英文句子，正确解法为先反转每个单词，再全部反转
 */
public class fanzhhuanjuzi {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        System.out.println(ReverseSentence(s));
    }
    public static String ReverseSentence(String str) {
        int n = str.length();
        char[] chars = str.toCharArray();
        int i = 0, j = 0;
        // 双指针，滑窗，，注意边界。
        while (j <= n) {
            // 关键是这个判断边界
            if (j == n || chars[j] == ' ') {
                // 反转
                reverse(chars, i, j - 1);
                // 下个单词的索引开头
                i = j + 1;
            }
            // 继续走
            j++;
        }
        // 全反转
        reverse(chars, 0, n - 1);
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
