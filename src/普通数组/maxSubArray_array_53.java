/**
 * 53.最大子数组和（中等）
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 子数组是数组中的一个连续部分。
 * <p>
 * 示例：
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 * <p>
 * 提示：
 * 1 <= nums.length <= 10⁵
 * -10⁴ <= nums[i] <= 10⁴
 * <p>
 * Related Topics 数组 分治 动态规划
 */

import java.util.*;

public class maxSubArray_array_53 {

    // 暴力：枚举所有子数组，累加求和（超时）
    public static int maxSubArray1(int[] nums) {
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                ans = Math.max(ans, sum);
            }
        }
        return ans;
    }

    // 动态规划（Kadane 算法）
    public static int maxSubArray(int[] nums) {
        // cur：以当前元素结尾的最大子数组和
        // 转移：cur = max(nums[i], cur + nums[i])——前面的和是负贡献就丢弃，从当前元素重新开始
        int cur = 0;
        int ans = Integer.MIN_VALUE;
        for (int num : nums) {
            cur = Math.max(num, cur + num);
            ans = Math.max(ans, cur);
        }
        return ans;
    }

    // 前缀和：子数组和 = 两个前缀和之差，维护之前出现过的最小前缀和
    public static int maxSubArray2(int[] nums) {
        int ans = Integer.MIN_VALUE;
        int preSum = 0;    // 当前前缀和 preSum[j+1]
        // 之前出现过的最小前缀和；初始 0 对应 preSum[0]（下标 0 之前的空前缀），
        // 保证从下标 0 开始的子数组也能被统计到
        int minPreSum = 0;
        for (int num : nums) {
            preSum += num;
            // 以当前元素结尾的子数组，最大和 = 当前前缀和 - 之前的最小前缀和
            ans = Math.max(ans, preSum - minPreSum);
            // 注意：先算答案、再更新 minPreSum。否则会允许 i = j+1，把「空子数组」（和为 0）算进去，全负数时会出错
            minPreSum = Math.min(minPreSum, preSum);
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
        System.out.println(maxSubArray(nums));
    }
}

/**
 * 思路：
 * <p>
 * 暴力（maxSubArray1）：枚举所有子数组，累加求和（会超时），O(n²)。
 * <p>
 * 动态规划（maxSubArray，Kadane 算法）：
 * 设 dp[i] 表示「以 nums[i] 结尾的最大子数组和」，则
 * dp[i] = max(nums[i], dp[i-1] + nums[i])
 * —— 要么把 nums[i] 接在前面最优子数组的后面，要么从 nums[i] 重新开始（前面的和是负贡献，带着只会更小）。
 * 答案就是所有 dp[i] 中的最大值。dp[i] 只依赖 dp[i-1]，用一个变量 cur 滚动更新即可，不需要整个 dp 数组。
 * <p>
 * 前缀和（maxSubArray2）：
 * 记 preSum[t] 为前 t 个元素之和（preSum[0] = 0），则子数组 nums[i..j] 的和 = preSum[j+1] - preSum[i]。
 * 要最大化这个差，对每个 j 只需要让 preSum[i]（i ≤ j）尽可能小——
 * 所以遍历过程中维护「之前出现过的最小前缀和」minPreSum，用 当前前缀和 - minPreSum 更新答案。
 * 两个细节：
 * 1. minPreSum 初始为 0，对应 preSum[0]，保证从下标 0 开始的子数组能被统计到；
 * 2. 必须先算答案、再更新 minPreSum，否则会允许 i = j+1，把「空子数组」（和为 0）算进去，全负数时会出错。
 * <p>
 * 时间复杂度 O(n)，只遍历一次数组
 * 空间复杂度 O(1)，只用常数个变量
 * <p>
 * 补充：本题还可以用分治法（O(n log n)）：最大子数组要么完全在左半、要么完全在右半、要么跨过中点，
 * 递归求解后取三者最大；跨过中点的情况，从中点向左、向右分别求最大后缀/前缀和再相加即可。
 */
