/**
 * 15.三数之和（中等）
 * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，
 * 同时还满足 nums[i] + nums[j] + nums[k] == 0 。请你返回所有和为 0 且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 示例：
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 * 解释：
 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
 * 不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
 * 注意，输出的顺序和三元组的顺序并不重要。
 * <p>
 * 提示：
 * 3 <= nums.length <= 3000
 * -10⁵ <= nums[i] <= 10⁵
 * <p>
 * Related Topics 数组 双指针 排序
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class threeSum_15 {
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) break; // 最小的数都大于0，不可能凑出0

            if (i > 0 && nums[i] == nums[i - 1]) continue; // 跳过重复的 i

            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] strs = sc.nextLine().split(" ");
        int[] nums = new int[strs.length];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = Integer.parseInt(strs[i]);
        }
        sc.close();
        System.out.println(threeSum(nums));
    }
}

/**
 * 思路：排序 + 双指针
 * 核心是把三数之和降成两数之和：先排序，固定第一个数 nums[i]，问题就变成在 i 右侧找两个数使它们等于 -nums[i]——排好序的数组上用对撞双指针即可。
 * 去重是关键：排序后相同的数会相邻，所以 i 跳过与 i-1 相同的值；找到一组解后，left、right 各自跳过相同的值。
 * <p>
 * 时间复杂度：O(n²)。排序 O(n log n)，外层固定 i 是 O(n)，内层双指针每轮是 O(n)，总体 O(n²)，排序被吸收；
 * 空间复杂度：O(1) 额外空间（不计输出结果；排序若用 Arrays.sort 是原地双轴快排）。
 */