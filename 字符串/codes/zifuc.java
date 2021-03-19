package zifu;

import java.util.Scanner;

/**
 * @author liujinbo
 * @create 2021-03-19 11:18
 * 两字符串相乘，返回字符串形式结果
 */
public class zifuc {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str1 = sc.nextLine();
        String str2 = sc.nextLine();
        //System.out.println(str1+str2);
        System.out.println(MultiplyStrings(str1,str2));
    }
    public static String MultiplyStrings(String num1, String num2){
        int len1 = num1.length();
        int len2 = num2.length();
        if (len1 == 0 || len2 == 0) return "0";
        int[] mul = new int[len1 + len2];
        for (int i = len1 - 1; i >= 0; i--){
            for (int j = len2 - 1; j >= 0; j--){
                int n = (num1.charAt(i) - '0') * (num2.charAt(j) - '0') + mul[i + j + 1];
                mul[i + j + 1] = n % 10;
                mul[i + j] += n / 10;
            }
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < len1 + len2 - 1 && mul[i] == 0) i++;
        while (i < len1 + len2) sb.append(mul[i++]);
        return sb.toString();
    }
}
