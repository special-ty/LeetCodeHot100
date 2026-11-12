/**
 * 54.螺旋矩阵（中等）
 * 给你一个 m 行 n 列的矩阵 matrix，请按照 顺时针螺旋顺序，返回矩阵中的所有元素。
 * <p>
 * 示例：
 * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * 输出：[1,2,3,6,9,8,7,4,5]
 * <p>
 * 提示：
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 10
 * -100 <= matrix[i][j] <= 100
 * <p>
 * Related Topics 数组 矩阵 模拟
 */

import java.util.*;

public class spiralOrder_matrix_54 {

    // 边界收缩：用 top、bottom、left、right 框住还没走的部分（O(1) 额外空间）
    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        int top = 0, bottom = matrix.length - 1, left = 0, right = matrix[0].length - 1;
        while (top <= bottom && left <= right) {
            // 1. 从左到右走顶边，走完顶边下移
            for (int j = left; j <= right; j++) {
                res.add(matrix[top][j]);
            }
            top++;
            // 2. 从上到下走右边，走完右边左移
            for (int i = top; i <= bottom; i++) {
                res.add(matrix[i][right]);
            }
            right--;
            // 3. 从右到左走底边（走完前两条边后可能只剩一行，需要判断）
            if (top <= bottom) {
                for (int j = right; j >= left; j--) {
                    res.add(matrix[bottom][j]);
                }
                bottom--;
            }
            // 4. 从下到上走左边（同理，可能只剩一列）
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    res.add(matrix[i][left]);
                }
                left++;
            }
        }
        return res;
    }

    // 方向模拟：方向数组 + visited，撞到边界或访问过就右转（O(mn) 空间）
    public static List<Integer> spiralOrder1(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        int m = matrix.length, n = matrix[0].length;
        boolean[][] visited = new boolean[m][n];
        // 依次表示：右、下、左、上
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int i = 0, j = 0, d = 0;
        for (int k = 0; k < m * n; k++) {
            res.add(matrix[i][j]);
            visited[i][j] = true;
            int ni = i + dirs[d][0], nj = j + dirs[d][1];
            // 出界或已访问过，就向右转 90 度
            if (ni < 0 || ni >= m || nj < 0 || nj >= n || visited[ni][nj]) {
                d = (d + 1) % 4;
                ni = i + dirs[d][0];
                nj = j + dirs[d][1];
            }
            i = ni;
            j = nj;
        }
        return res;
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
        System.out.println(spiralOrder(matrix));
    }
}

/**
 * 思路：
 * <p>
 * 版本一（spiralOrder，边界收缩，O(1) 额外空间）：
 * 用四个边界 top、bottom、left、right 把"还没走的部分"框起来，每走一条边就把对应边界往里缩一格：
 * 1.从左到右走顶边，走完 top++；
 * 2.从上到下走右边，走完 right--；
 * 3.从右到左走底边，走完 bottom--；
 * 4.从下到上走左边，走完 left++。
 * 重复直到边界交叉。
 * <p>
 * 两个关键判断：第 3、4 步之前要检查 top <= bottom 和 left <= right。
 * 每走完一条边，对应边界就收缩：走完顶边 top++、走完右边 right--。
 * 如果这一轮矩形本来只剩一行（top == bottom），走完顶边就变成 top > bottom——
 * 行已经用完了，第 3 步的"底边"其实就是刚走过的顶边，再走就重复了；
 * 只剩一列（left == right）时同理，第 4 步的"左边"就是刚走过的右边。
 * 例如 [[1,2,3]] 只有一行：走完顶边后 top=1 > bottom=0，第 3 步必须跳过。
 * 大矩阵的最后一轮也会触发：比如 3x3 走到中心那 1 个格子时，同样只剩 1 行 1 列。
 * <p>
 * 版本二（spiralOrder1，方向模拟，O(mn) 空间）：
 * 用方向数组 dirs = {{0,1},{1,0},{0,-1},{-1,0}} 表示"右、下、左、上"，配合 visited 数组：
 * 每次直走，撞到边界或已访问过就向右转 90 度。直观，但需要 O(mn) 的 visited 数组。
 * <p>
 * 以 matrix = [[1,2,3],[4,5,6],[7,8,9]] 为例（版本一）：
 * 第 1 圈：顶边 1,2,3 -> 右边 6,9 -> 底边 8,7 -> 左边 4，边界收缩到中心；
 * 第 2 圈：顶边 5。
 * 结果 [1,2,3,6,9,8,7,4,5]。
 * <p>
 * 时间复杂度 O(mn)：每个元素恰好被访问一次
 * 空间复杂度 O(1)：输出不算额外空间，只用四个边界变量（版本一是 O(mn)）
 */
