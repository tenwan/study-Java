package zifu;

import java.util.Scanner;

/**
 * @author liujinbo
 * @create 2021-03-19 10:47
 * 给定两个字符串形式的非负整数 `num1` 和`num2` ，计算它们的和。
 */
public class zifusum {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str1 = sc.nextLine();
        String str2 = sc.nextLine();
        //System.out.println(str1+str2);
        addStrings(str1,str2);
    }
    public static void addStrings(String num1,String num2){
        StringBuilder str = new StringBuilder();
        int carry =0,i = num1.length()-1,j = num2.length()-1;
        while(carry==1||i>=0||j>=0){
            int x =i<0?0:num1.charAt(i--)-'0';
            int y = j<0?0:num2.charAt(j--) -'0';
            str.append((x+y+carry)%10);    //后续再使用字符串反转
            carry = (x+y+carry)/10;
        }
        System.out.println(str.reverse().toString());
    }
}
