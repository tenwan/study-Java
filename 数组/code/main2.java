import java.util.Scanner;

/**
 * @author liujinbo
 * @create 2021-03-15 15:28
 */
public class main2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] inputarray = sc.nextLine().split(" ");
        int[] num = new int[inputarray.length];
        for (int i = 0; i < num.length; i++) {
            num[i] = Integer.parseInt(inputarray[i]);

        }
        System.out.println(findnum(num));
    }
    public static int findnum(int[] array){
        if(array == null) return -1;
        int len = array.length;
        int result =0;
        for (int i = 0; i < len; i++) {
            result ^=array[i];
        }
        for (int i = 1; i < len; i++) {
            result ^=i;
        }
        return result;
    }
}
