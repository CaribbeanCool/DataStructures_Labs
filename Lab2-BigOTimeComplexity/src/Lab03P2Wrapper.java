import java.util.Arrays;

public class Lab03P2Wrapper {

    public static int[] twoSum(int[] array, int targetSum) {
        int[] result = new int[2];
        Arrays.sort(array);
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            int sum = array[left] + array[right];
            if (sum == targetSum) {
                result[0] = array[left];
                result[1] = array[right];
                return result;
            } else if (sum > targetSum) {
                right--;
            } else {
                left++;
            }
        }
        return new int[0];
    }
}