/**
 * 73.矩阵置零（中等）
 * 给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0。
 * 请使用 原地 算法。
 * <p>
 * 示例：
 * 输入：matrix = [[1,1,1],[1,0,1],[1,1,1]]
 * 输出：[[1,0,1],[0,0,0],[1,0,1]]
 * <p>
 * 提示：
 * m == matrix.length
 * n == matrix[0].length
 * 1 <= m, n <= 200
 * -2³¹ <= matrix[i][j] <= 2³¹ - 1
 * <p>
 * Related Topics 数组 哈希表 矩阵
 */

import java.util.*;

public class setZeroes_matrix_73 {

    // 标记数组：zeroRow[i] 表示第 i 行要置零，zeroCol[j] 表示第 j 列要置零（O(m+n) 空间）
    public static void setZeroes1(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean[] zeroRow = new boolean[m];
        boolean[] zeroCol = new boolean[n];
        // 第一遍：标记哪些行、哪些列里出现过 0
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    zeroRow[i] = true;
                    zeroCol[j] = true;
                }
            }
        }
        // 第二遍：所在行或列被标记过的位置，置 0
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (zeroRow[i] || zeroCol[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    // O(1) 额外空间：用矩阵的第一行和第一列当标记数组
    public static void setZeroes(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        // 先记录第一行、第一列本身是否含 0（它们待会要被当作标记位，先存下来）
        boolean firstRowZero = false;
        boolean firstColZero = false;
        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) {
                firstRowZero = true;
                break;
            }
        }
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                firstColZero = true;
                break;
            }
        }
        // 用第一列标记"这一行要置零"，用第一行标记"这一列要置零"
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        // 根据第一列、第一行的标记，把对应位置置零（除第一行第一列）
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        // 最后处理第一行和第一列
        if (firstRowZero) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }
        if (firstColZero) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    public static void main(String[] args) {
        // 输入格式：第一行是 m n，接下来 m 行，每行 n 个整数
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        int[][] matrix = new int[m][n];
        sc.nextLine(); // 吃掉第一行末尾的换行
        for (int i = 0; i < m; i++) {
            String[] row = sc.nextLine().split(" ");
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Integer.parseInt(row[j]);
            }
        }
        setZeroes(matrix);
        for (int[] r : matrix) {
            System.out.println(Arrays.toString(r));
        }
    }
}

/**
 * 思路：
 * <p>
 * 版本一（setZeroes1，O(m+n) 空间）：
 * 1.先扫一遍，用两个布尔数组 zeroRow、zeroCol 记下哪些行、哪些列里出现过 0；
 * 2.再扫一遍，凡是所在行或列被标记过的位置，置 0。
 * <p>
 * 版本二（setZeroes，O(1) 额外空间）：
 * 借助矩阵自身当标记数组——第一列的第 i 行标记"第 i 行要置零"，第一行的第 j 列标记"第 j 列要置零"。
 * 但第一行、第一列自己要不要置零，会被这个标记过程覆盖掉，所以必须先用两个变量把它们原本是否含 0 记下来：
 * 1.先记录第一行、第一列本身是否含 0（firstRowZero、firstColZero）；
 * 2.扫描除第一行第一列以外的位置，遇到 0 就把对应的第一列、第一行位置标记为 0；
 * 3.再扫描除第一行第一列以外的位置，根据第一列/第一行的标记置零；
 * 4.最后根据 firstRowZero、firstColZero 处理第一行和第一列。
 * <p>
 * 顺序很关键：必须"先标记、后处理第一行第一列"。
 * 如果先把第一行置零，就会污染标记数组，导致后面判断列的时候出错。
 * <p>
 * 以 matrix = [[1,1,1],[1,0,1],[1,1,1]] 为例（版本二）：
 * 第一行、第一列原本都没有 0；扫描到 (1,1) 是 0，标记 matrix[1][0]=0、matrix[0][1]=0；
 * 再按标记把第 1 行、第 1 列（除首行首列）置零，得到 [[1,0,1],[0,0,0],[1,0,1]]。
 * <p>
 * 时间复杂度 O(mn)：都是常数次扫描
 * 空间复杂度 O(1)：只用两个布尔变量（版本一是 O(m+n)）
 */
