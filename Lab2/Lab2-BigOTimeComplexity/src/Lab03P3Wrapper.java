public class Lab03P3Wrapper {
    public static boolean isValidSudoku(char[][] board) {
        // Verify rows
        for (int i = 0; i < 9; i++) {
            boolean[] checkForRows = new boolean[9];
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    if (checkForRows[num])
                        return false;
                    checkForRows[num] = true;
                }
            }
        }

        // Check columns
        for (int i = 0; i < 9; i++) {
            boolean[] checkForColumns = new boolean[9];
            for (int j = 0; j < 9; j++) {
                if (board[j][i] != '.') {
                    int num = board[j][i] - '1';
                    if (checkForColumns[num])
                        return false;
                    checkForColumns[num] = true;
                }
            }
        }

        // Check each 3 x 3 sub-box
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                boolean[] checkEach3x3 = new boolean[9];
                for (int k = 0; k < 9; k++) {
                    int row = i + k / 3;
                    int col = j + k % 3;
                    if (board[row][col] != '.') {
                        int num = board[row][col] - '1';
                        if (checkEach3x3[num])
                            return false;
                        checkEach3x3[num] = true;
                    }
                }
            }
        }

        return true;
    }

}