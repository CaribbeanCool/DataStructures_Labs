public class Lab03P1Wrapper {

    public static int[] getRowPascalTriangle(int rowIndex) {
        int[] result = new int[rowIndex + 1];
        result[0] = 1;
        for (int i = 1; i < result.length; i++) {
            // Using the formula:
            result[i] = result[i - 1] * (rowIndex - i + 1) / i;
        }
        return result;
    }
}