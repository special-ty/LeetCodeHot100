/**
 * 239.滑动窗口最大值（困难）
 * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 * 返回 滑动窗口中的最大值 。
 * <p>
 * 示例：
 * 输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
 * 输出：[3,3,5,5,6,7]
 * 解释：
 * 滑动窗口的位置                最大值
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 *  1 [3  -1  -3] 5  3  6  7       3
 *  1  3 [-1  -3  5] 3  6  7       5
 *  1  3  -1 [-3  5  3] 6  7       5
 *  1  3  -1  -3 [5  3  6] 7       6
 *  1  3  -1  -3  5 [3  6  7]      7
 * <p>
 * 提示：
 * 1 <= nums.length <= 10⁵
 * -10⁴ <= nums[i] <= 10⁴
 * 1 <= k <= nums.length
 * <p>
 * Related Topics 队列 数组 滑动窗口 单调队列 堆（优先队列）
 */

import java.util.*;

public class maxSlidingWindow_sub_239 {

    // 单调队列：队列里存下标，对应的值从队头到队尾单调递减
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            // 队尾所有值不大于当前值的下标，之后都不可能成为最大值，直接移除
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            deque.offerLast(i);
            // 队头下标已经滑出窗口左边界，移除
            if (deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }
            // 窗口形成后，队头对应的值就是当前窗口的最大值
            if (i >= k - 1) {
                ans[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] split = s.split(" ");
        int[] nums = new int[split.length];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = Integer.parseInt(split[i]);
        }
        int k = sc.nextInt();
        System.out.println(Arrays.toString(maxSlidingWindow(nums, k)));
    }
}

/**
 * 思路：单调队列
 * 暴力解法是每个窗口内遍历一遍找最大值，O(nk)，k 很大时会退化到 O(n²)。
 * <p>
 * 关键观察：窗口向右滑动时，如果新进来的元素比前面某些元素大，
 * 那前面那些较小的元素在当前及之后的窗口里都不可能再成为最大值（新元素更大、又更晚滑出窗口），可以直接丢弃。
 * <p>
 * 用一个双端队列存下标，保证对应的值从队头到队尾单调递减：
 * 1.入队前，从队尾移除所有值不大于当前值的下标（它们不可能再成为最大值）；
 * 2.队头下标如果已经滑出窗口左边界，移除（队头下标是最小的）；
 * 3.窗口形成后（i >= k - 1），队头对应的值就是当前窗口的最大值。
 * <p>
 * 注意点：
 * 1.队列里存下标而不是值：既要比较大小，又要判断是否滑出窗口；
 * 2.队尾移除用 <=（相等也移除），保留更靠后的下标，避免同一个值在队列里堆多层；
 * 3.每个下标最多入队一次、出队一次，所以整体是 O(n)，不是 O(n²)。
 * <p>
 * 以 nums = [1,3,-1,-3,5,3,6,7], k = 3 为例：
 * i = 2 时队列是 [1,2]（对应值 3、-1），队头 3 就是窗口最大值；
 * i = 4 时 5 进来，会把队尾的 -3、-1、3 全部移除，队列只剩 [4]，队头 5 成为新的最大值。
 * 最终答案 [3,3,5,5,6,7]。
 * <p>
 * 时间复杂度 O(n)，每个下标最多入队、出队各一次
 * 空间复杂度 O(k)，队列最多同时存 k 个下标（不计答案数组）
 */
