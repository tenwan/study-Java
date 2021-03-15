package test;

import java.util.Scanner;

/**
 * @author liujinbo
 * @create 2021-03-15 15:41
 */
public class main3 {
    private int max;
    private int min;
    public int getMax(){return max;}
    public int getMin(){return min;}
    //分治法寻找数组中最大元素和最小元素
    public void fenzhi(int[] array){
        if(array==null) return ;
        int len =array.length;
        max = array[0];
        //开始分组，较小的数放在左半部分，大的放右半部分
        for (int i = 0; i < len-1; i+=2) {
            if(array[i]>array[i+1]){
                int temp = array[i];
                array[i] =array[i+1];
                array[i+1] = temp;
            }
        }
        //在左半部分寻找最小值
        min = array[0];
        for (int i = 2; i < len; i+=2) {
            if(array[i]<min) min = array[i];
        }
        //在右半部分找最大值
        max = array[1];
        for (int i = 3; i < len; i++) {
            if(array[i]>max) max = array[i];
        }
        //数组为奇数，最后一个元素单独考虑
        if(len%2==1){
            if(max<array[len-1]) max = array[len-1];
            if(min>array[len-1]) min = array[len-1];
        }
    }

    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        String[] inputarray = sc.nextLine().split(" ");
        int[] num = new int[inputarray.length];
        for (int i = 0; i < num.length; i++) {
            num[i] = Integer.parseInt(inputarray[i]);

        }
        main3 m = new main3();
        m.fenzhi(num);
        System.out.println(m.getMax());
        System.out.println(m.getMin());

    }

}
