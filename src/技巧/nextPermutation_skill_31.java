/**
 * 31.下一个排列（中等）
 * 整数数组的 下一个排列 是指其整数的下一个字典序更大的排列。
 * 如果不存在下一个更大的排列，则将数组重排为字典序最小的排列（即升序排列）。
 * 必须 原地 修改，只允许使用额外常数空间。
 * <p>
 * 示例 1：
 * 输入：nums = [1,2,3]
 * 输出：[1,3,2]
 * <p>
 * 示例 2：
 * 输入：nums = [3,2,1]
 * 输出：[1,2,3]
 * <p>
 * 示例 3：
 * 输入：nums = [1,1,5]
 * 输出：[1,5,1]
 * <p>
 * 提示：
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 100
 * <p>
 * Related Topics 数组 双指针
 */

import java.util.*;

public class nextPermutation_skill_31 {

    // 标准解法：从右往左找第一处"上升"，变大一点，再把右边整理成最小
    public static void nextPermutation(int[] nums) {
        int n = nums.length;
        // 第一步：从右往左找第一个满足 nums[i] < nums[i+1] 的下标 i
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if (i >= 0) {
            // 第二步：在 i 右边从右往左找第一个大于 nums[i] 的数，交换
            int j = n - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }
        // 第三步：反转 i 右边的部分（单调不增 -> 单调不减，即最小）
        reverse(nums, i + 1, n - 1);
    }

    private static void reverse(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    private static void swap(int[] nums, int a, int b) {
        int tmp = nums[a];
        nums[a] = nums[b];
        nums[b] = tmp;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] split = s.split(" ");
        int[] nums = new int[split.length];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = Integer.parseInt(split[i]);
        }
        nextPermutation(nums);
        System.out.println(Arrays.toString(nums));
    }
}

/**
 * 思路：
 * <p>
 * 排列的字典序，是从左往右逐位比较，第一个不同的位置谁大谁就大（例如 123 < 213，因为第 1 位 1 < 2）。
 * 位置越靠左、对大小的影响越大，所以"下一个排列"要尽可能在靠右的位置做最小的改动：
 * 从右往左找到第一处还能变大的地方，把它变大一点，再把右边整理成最小。
 * <p>
 * 版本一（nextPermutation，O(n) 时间 O(1) 空间）：
 * 1. 从右往左找第一个满足 nums[i] < nums[i+1] 的下标 i（第一处"上升"）。
 *    它右边是单调不增的，说明右边已经排到最大，无法只靠重排右边变得更大，必须动 i 或更左的位置；
 *    而"下一个排列"要的是尽可能小的增量，所以选最靠右的可变位置 i。
 * 2. 如果找不到（整个数组单调不增），说明当前就是最大的排列，直接反转整个数组变成最小。
 * 3. 在 i 右边从右往左找第一个大于 nums[i] 的数 nums[j]。
 *    因为 i 右边单调不增（越靠右越小），大于 nums[i] 的数在后缀里连续地排在最左端，
 *    所以从右往左第一个遇到的大于 nums[i] 的，就是其中最小的——"刚好比它大一点"的那个。
 * 4. 交换 nums[i] 和 nums[j]——i 这一位变大了一点。
 * 5. 反转 i 右边的部分：交换后 i 右边仍然单调不增，反转后变成单调不减，即最小。
 * <p>
 * 以 nums = [1,3,2] 为例：
 * 从右往左第一处上升是 i=0（nums[0]=1 < nums[1]=3）；
 * i 右边从右往左第一个大于 1 的是 nums[2]=2，交换得 [2,3,1]；
 * 反转 i 右边的 [3,1] 得 [1,3]，最终 [2,1,3]。
 * <p>
 * 时间复杂度 O(n)：找 i、找 j、反转各扫一遍
 * 空间复杂度 O(1)：只用几个下标变量
 */
