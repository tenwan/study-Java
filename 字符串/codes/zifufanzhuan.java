package zifu;

import java.util.Scanner;

/**
 * @author liujinbo
 * @create 2021-03-19 11:32
 * 反转字符串
 */
public class zifufanzhuan {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        char[] s1 = s.toCharArray();
        int p1 = 0,p2 =s1.length-1;
        while(p1<p2){
            swap(s1,p1++,p2--);
        }
        System.out.println(s1);
    }
    public static void swap(char[] s,int i,int j ){
        char temp = s[i];
        s[i] = s[j];
        s[j] = temp;
    }
}
