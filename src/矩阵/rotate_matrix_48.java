/**
 * 48.旋转图像（中等）
 * 给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
 * 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要使用另一个矩阵来旋转图像。
 * <p>
 * 示例：
 * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * 输出：[[7,4,1],[8,5,2],[9,6,3]]
 * <p>
 * 提示：
 * n == matrix.length == matrix[i].length
 * 1 <= n <= 20
 * -1000 <= matrix[i][j] <= 1000
 * <p>
 * Related Topics 数组 数学 矩阵
 */

import java.util.*;

public class rotate_matrix_48 {

    // 辅助矩阵：原位置 (i, j) 的元素顺时针转 90 度后落在 (j, n-1-i)（O(n²) 空间）
    public static void rotate1(int[][] matrix) {
        int n = matrix.length;
        int[][] res = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[j][n - 1 - i] = matrix[i][j];
            }
        }
        // 拷回原矩阵
        for (int i = 0; i < n; i++) {
            System.arraycopy(res[i], 0, matrix[i], 0, n);
        }
    }

    // 转置 + 左右翻转（O(1) 额外空间）
    public static void rotate(int[][] matrix) {
        int n = matrix.length;
        // 第一步：转置——沿主对角线翻转，交换 matrix[i][j] 和 matrix[j][i]
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
        // 第二步：每一行左右翻转
        for (int i = 0; i < n; i++) {
            int left = 0, right = n - 1;
            while (left < right) {
                int tmp = matrix[i][left];
                matrix[i][left] = matrix[i][right];
                matrix[i][right] = tmp;
                left++;
                right--;
            }
        }
    }

    public static void main(String[] args) {
        // 输入格式：第一行是 n，接下来 n 行，每行 n 个整数
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] matrix = new int[n][n];
        sc.nextLine(); // 吃掉第一行末尾的换行
        for (int i = 0; i < n; i++) {
            String[] row = sc.nextLine().split(" ");
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Integer.parseInt(row[j]);
            }
        }
        rotate(matrix);
        for (int[] r : matrix) {
            System.out.println(Arrays.toString(r));
        }
    }
}

/**
 * 思路：转置 + 左右翻转
 * <p>
 * 版本一（rotate，O(1) 额外空间）：
 * 顺时针旋转 90 度可以拆成两个简单的原地操作：
 * 1.转置：沿主对角线翻转，即交换 matrix[i][j] 和 matrix[j][i]（i < j）；
 * 2.左右翻转：把每一行反转。
 * <p>
 * 为什么这两步等于顺时针转 90 度：
 * 转置把"行变列"（(i, j) 挪到 (j, i)），但得到的是沿主对角线的镜像，还不是旋转；
 * 再左右翻转一次，就把镜像翻正，得到顺时针 90 度。
 * <p>
 * 以 matrix = [[1,2,3],[4,5,6],[7,8,9]] 为例：
 * 转置后    [[1,4,7],[2,5,8],[3,6,9]]
 * 每行反转 [[7,4,1],[8,5,2],[9,6,3]]  即答案
 * <p>
 * 版本二（rotate1，O(n²) 空间）：
 * 直接按定义算出新位置：原位置 (i, j) 的元素顺时针转 90 度后落在 (j, n-1-i)。
 * 开个新矩阵按这个映射填，最后拷回原矩阵。直观，但需要 O(n²) 额外空间。
 * <p>
 * 记忆小技巧：顺 90 = 转置 + 左右翻；逆 90 = 转置 + 上下翻。
 * <p>
 * 时间复杂度 O(n²)：每个元素访问常数次
 * 空间复杂度 O(1)：只用几个临时变量（版本二是 O(n²)）
 */
