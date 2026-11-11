/**
 * 56.合并区间（中等）
 * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [start_i, end_i] 。
 * 请你合并所有重叠的区间，并返回一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间。
 * <p>
 * 示例：
 * 输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
 * 输出：[[1,6],[8,10],[15,18]]
 * 解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6]。
 * <p>
 * 提示：
 * 1 <= intervals.length <= 10⁴
 * intervals[i].length == 2
 * 0 <= start_i <= end_i <= 10⁴
 * <p>
 * Related Topics 数组 排序
 */

import java.util.*;

public class merge_array_56 {

    // 排序 + 贪心：按左端点排序后从左到右扫一遍，只和结果中最后一个区间比较
    public static int[][] merge(int[][] intervals) {

        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> res = new ArrayList<>();
        for (int[] interval : intervals) {
            // 因为排过序，interval[0] 不小于结果列表最后一个区间的左端点，
            // 所以是否重叠只需比较 interval[0] 与结果列表最后一个区间的右端点
            if (res.isEmpty() || res.get(res.size() - 1)[1] < interval[0]) {
                // 不重叠：结果列表最后一个区间就此"封口"，interval 作为新的"最后区间"加入
                res.add(interval);
            } else {
                // 重叠：把 interval 并进结果列表最后一个区间，右端点取较大者
                res.get(res.size() - 1)[1] = Math.max(res.get(res.size() - 1)[1], interval[1]);
            }
        }

        return res.toArray(new int[0][]);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] intervals = new int[n][2];
        for (int i = 0; i < n; i++) {
            intervals[i][0] = sc.nextInt();
            intervals[i][1] = sc.nextInt();
        }
        System.out.println(Arrays.deepToString(merge(intervals)));
    }
}

/**
 * 思路：
 * 最优（merge）：先按左端点排序，再从左到右扫一遍：
 * 1.结果列表为空，或当前区间与结果列表最后一个区间不重叠（结果列表最后一个区间的右端点 < 当前区间左端点），直接加入；
 * 2.否则重叠，合并：把最后一个区间的右端点扩大到 max(它的右端点, 当前右端点)。
 * <p>
 * 为什么排序后只需要和"最后一个"比较：排序后左端点递增，当前区间的左端点一定不小于结果里所有区间的左端点；
 * 所以当前区间只要和已有区间有重叠，就必然和紧邻的最后一个重叠（它的右端点是最大的）。
 * <p>
 * 以 intervals = [[1,3],[2,6],[8,10],[15,18]] 为例：
 * 排序后顺序不变；[1,3] 直接加入；[2,6] 与 [1,3] 重叠，合并成 [1,6]；
 * [8,10] 与 [1,6] 不重叠，加入；[15,18] 与 [8,10] 不重叠，加入。
 * 结果 [[1,6],[8,10],[15,18]]。
 * <p>
 * 时间复杂度 O(n log n)，主要来自排序；扫描过程 O(n)
 * 空间复杂度 O(n)，存放结果
 */
