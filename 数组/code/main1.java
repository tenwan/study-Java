import java.util.HashMap;
import java.util.Scanner;

/**
 * @author liujinbo
 * @create 2021-03-15 15:02
 */
public class main1 {
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
        HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
        for (int i = 0; i < len; i++) {
            map.put(i,0);
        }
        for (int i = 0; i < len; i++) {
            if(map.get(array[i]-1)==0){
                map.put(array[i]-1,array[i]-1);
            }else{
                return array[i];
            }

        }
        return -1;
    }
}
