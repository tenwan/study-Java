package zifu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author liujinbo
 * @create 2021-03-19 19:05
 * 阿里笔试，抽奖券：
 * 输入数组代表每人手中奖票，修改卷可加一减一，当奖票为平方数时中奖，求至少需要多少张修改券，可让至少一半人中奖。
 */
public class test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] an = new int[n];
        int[] res = new int[n];
        int result =0;
        for(int i =0;i<an.length;i++){
            an[i] = sc.nextInt();
        }
        for(int i=0;i<n;i++){
           res[i]=jiufang(an[i]);
        }
        Arrays.sort(res);
        for(int i=0;i<res.length/2;i++){
            result+=res[i];
        }
        System.out.println(result);
    }
    public static int jiufang(int n){
        int square = (int)Math.sqrt(n);
        int min = 0;
        min =(n-square*square)<((square+1)*(square+1)-n)?(n-square*square):((square+1)*(square+1)-n);
        return min;
    }
}
