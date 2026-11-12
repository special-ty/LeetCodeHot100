/**
 * 240.搜索二维矩阵 II（中等）
 * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：
 * 每行的元素从左到右升序排列。
 * 每列的元素从上到下升序排列。
 * <p>
 * 示例：
 * 输入：matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 5
 * 输出：true
 * <p>
 * 提示：
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= n, m <= 300
 * -10⁹ <= matrix[i][j] <= 10⁹
 * 每行的所有元素从左到右升序排列
 * 每列的所有元素从上到下升序排列
 * -10⁹ <= target <= 10⁹
 * <p>
 * Related Topics 数组 二分查找 分治 矩阵
 */

import java.util.*;

public class searchMatrix_matrix_240 {

    // 逐行二分：只利用"每行递增"这一条（O(m log n)）
    public static boolean searchMatrix1(int[][] matrix, int target) {
        for (int[] row : matrix) {
            if (binarySearch(row, target)) {
                return true;
            }
        }
        return false;
    }

    private static boolean binarySearch(int[] row, int target) {
        int left = 0, right = row.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (row[mid] == target) {
                return true;
            } else if (row[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }

    // Z 字形搜索：从右上角出发，每次排除一整行或一整列（O(m+n)）
    public static boolean searchMatrix(int[][] matrix, int target) {
        int i = 0, j = matrix[0].length - 1; // 从右上角出发
        while (i < matrix.length && j >= 0) {
            if (matrix[i][j] == target) {
                return true;
            } else if (matrix[i][j] > target) {
                j--; // 当前值太大：这一列往下只会更大，整列排除，往左走
            } else {
                i++; // 当前值太小：这一行往左只会更小，整行排除，往下走
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // 输入格式：第一行是 m n target，接下来 m 行，每行 n 个整数
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        int target = sc.nextInt();
        int[][] matrix = new int[m][n];
        sc.nextLine(); // 吃掉第一行末尾的换行
        for (int i = 0; i < m; i++) {
            String[] row = sc.nextLine().split(" ");
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Integer.parseInt(row[j]);
            }
        }
        System.out.println(searchMatrix(matrix, target));
    }
}

/**
 * 思路：
 * <p>
 * 版本一（searchMatrix，Z 字形搜索，O(m+n)）：
 * 从右上角出发（左下角也行），利用矩阵的两个有序性做排除：
 * - 当前值 == target：找到了；
 * - 当前值 > target：说明这一列往下都更大（列递增），整列排除，往左走一列；
 * - 当前值 < target：说明这一行往左都更小（行递增），整行排除，往下走一行。
 * 每次比较都能排除一整行或一整列，最多走 m + n 步。
 * <p>
 * 为什么不能从左上角出发：左上角是整个矩阵的最小值，往右、往下都变大，无法判断该往哪走。
 * 右上角（或左下角）的特殊性在于：往左变小、往下变大，两个方向一大一小，才能做决策。
 * <p>
 * 版本二（searchMatrix1，逐行二分，O(m log n)）：
 * 只利用"每行递增"这一条，对每一行做一次二分查找。
 * 更通用（不要求列也有序），但没用上列有序的性质，效率也低一些。
 * <p>
 * 以 matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]]，target = 5 为例：
 * 从右上角 15 出发：15 > 5 往左 → 11 > 5 往左 → 7 > 5 往左 → 4 < 5 往下 → 5 == 5，找到。
 * <p>
 * 时间复杂度 O(m+n)：每次排除一行或一列，最多 m+n 步（版本二是 O(m log n)）
 * 空间复杂度 O(1)：只用两个下标变量
 */
