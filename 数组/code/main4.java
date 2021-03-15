import java.util.Scanner;

/**
 * @author liujinbo
 * @create 2021-03-15 16:10
 */
public class main4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] inputarray = sc.nextLine().split(" ");
        int[] num = new int[inputarray.length];
        for (int i = 0; i < num.length; i++) {
            num[i] = Integer.parseInt(inputarray[i]);

        }
        System.out.println(find(num));
    }
    public static  int find(int[] arr){
        int result = 0;
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            result^=arr[i];
        }
        for(int i = 1;i<len+2;i++){
            result^=i;
        }
        return result;
    }
}
