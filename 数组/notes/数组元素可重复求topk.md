# 题目描述

含有重复元素的数组，求top k 的值。使用优先队列的万能解法。

# 个人解法

```
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @author liujinbo
 * @create 2021-03-25 10:58
 * 求数组top k的值
 */
public class topk {
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        String str = sc.nextLine();
        String[] s = str.split(" ");
        int[] num = new int[s.length];
        for (int i = 0; i < num.length; i++) {
            num[i] = Integer.parseInt(s[i]);
        }
        System.out.println(findk(num,3)); //求第三大的元素
    }
    public static int findk(int[] num,int k){
        PriorityQueue<Integer> q = new PriorityQueue<Integer>(k);
        for(int i:num){
            q.offer(i);    //向优先队列插入元素
            if(q.size()>k){
                q.poll();  //获取并删除队首元素
            }
        }
        return q.peek();  //获取但不删除队首元素
    }
}

```

， 