import java.util.Arrays;
import java.util.Scanner;

/**
 * @author liujinbo
 * @create 2021-03-15 16:29
 */
public class main5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        sc.nextLine();
        String[] inputarray = sc.nextLine().split(" ");
        int[] num = new int[inputarray.length];
        for (int i = 0; i < num.length; i++) {
            num[i] = Integer.parseInt(inputarray[i]);

        }
        Arrays.sort(num);
        System.out.println(num[k-1]);

    }
}
