/**
 * 560.和为 K 的子数组（中等）
 * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数 。
 * 子数组是数组中元素的连续非空序列。
 * <p>
 * 示例 1：
 * 输入：nums = [1,1,1], k = 2
 * 输出：2
 * <p>
 * 示例 2：
 * 输入：nums = [1,2,3], k = 3
 * 输出：2
 * <p>
 * 提示：
 * 1 <= nums.length <= 2 * 10⁴
 * -1000 <= nums[i] <= 1000
 * -10⁷ <= k <= 10⁷
 * <p>
 * Related Topics 数组 哈希表 前缀和
 */

import java.util.*;

public class subarraySum_sub_560 {

    // 暴力：枚举所有子数组，累加判断
    public static int subarraySum1(int[] nums, int k) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum == k) {
                    ans++;
                }
            }
        }
        return ans;
    }

    // 前缀和 + 哈希表
    public static int subarraySum(int[] nums, int k) {
        // map：前缀和 -> 该前缀和出现的次数
        Map<Integer, Integer> map = new HashMap<>();
        // 先放入 (0, 1)：前缀和 0 对应"下标 0 之前的空前缀"，作为配对用的虚拟起点，
        // 否则当前前缀和恰好等于 k 时（子数组从下标 0 开始）将无从配对
        map.put(0, 1);
        int ans = 0;
        int preSum = 0;
        for (int num : nums) {
            preSum += num;
            // 若之前出现过前缀和 preSum - k，则这两点之间的子数组和恰好是 k
            if (map.containsKey(preSum - k)) {
                ans += map.get(preSum - k);
            }
            // 先查询、后放入：只与之前的前缀和配对，顺序反了 k = 0 时会多算一个空子数组
            map.put(preSum, map.getOrDefault(preSum, 0) + 1);
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
        System.out.println(subarraySum(nums, k));
    }
}

/**
 * 思路：前缀和 + 哈希表
 * 子数组的和不好直接统计，用前缀和可以把「一段子数组的和」变成「两个前缀和的差」：
 * 记 preSum[t] 为前 t 个元素之和（preSum[0] = 0，即下标 0 之前的空前缀），
 * 则 nums[i..j] 的和 = preSum[j + 1] - preSum[i]，所以「和为 k 的子数组」等价于「preSum[j + 1] - preSum[i] == k」。
 * <p>
 * 遍历数组时维护当前前缀和 preSum，同时问一句：之前出现过多少次 preSum - k？
 * 每出现一次，就对应一个以当前位置结尾、和为 k 的子数组。用哈希表存「前缀和 -> 出现次数」，查询 O(1)。
 * <p>
 * 关键细节：
 * 1.map 预先放入 (0, 1)：前缀和 0 对应下标 0 之前的空前缀（配对用的虚拟起点），当前前缀和恰好等于 k 时配对的就是它，缺少这条记录会漏掉所有从下标 0 开始的子数组；
 * 2.先查后放：先统计 preSum - k 的出现次数，再把当前 preSum 放进 map，保证只与之前的前缀和配对，否则 k = 0 时会多算一个空子数组；
 * 3.数组含负数，前缀和不单调，不能用滑动窗口，哈希表是通用解法。
 * <p>
 * 以 nums = [1,1,1], k = 2 为例：
 * preSum 依次为 1、2、3；preSum = 2 时找到 preSum - k = 0 一次（子数组 [0,1]），
 * preSum = 3 时找到 1 一次（子数组 [1,2]），共 2 个。
 * <p>
 * 时间复杂度 O(n)，只遍历一次数组，哈希表操作 O(1)
 * 空间复杂度 O(n)，哈希表最多存 n + 1 个不同的前缀和
 */
