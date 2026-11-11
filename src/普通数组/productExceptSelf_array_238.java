/**
 * 238.除自身以外数组的乘积（中等）
 * 给你一个整数数组 nums，返回数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积。
 * 题目数据保证数组 nums 之中任意元素的全部前缀元素和后缀的乘积都在 32 位 整数范围内。
 * <p>
 * 请不要使用除法，且在 O(n) 时间复杂度内完成此题。
 * <p>
 * 示例：
 * 输入：nums = [1,2,3,4]
 * 输出：[24,12,8,6]
 * <p>
 * 提示：
 * 2 <= nums.length <= 10⁵
 * -30 <= nums[i] <= 30
 * <p>
 * Related Topics 数组 前缀和
 */

import java.util.*;

public class productExceptSelf_array_238 {

    // 双数组：prefix[i] 是 nums[0..i-1] 的乘积，suffix[i] 是 nums[i+1..n-1] 的乘积
    public static int[] productExceptSelf1(int[] nums) {
        int n = nums.length;
        int[] prefix = new int[n];
        int[] suffix = new int[n];
        // 前缀积：prefix[i] = nums[0] * ... * nums[i-1]
        prefix[0] = 1;
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] * nums[i - 1];
        }
        // 后缀积：suffix[i] = nums[i+1] * ... * nums[n-1]
        suffix[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            suffix[i] = suffix[i + 1] * nums[i + 1];
        }
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = prefix[i] * suffix[i];
        }
        return ans;
    }

    // 空间优化：ans 先存前缀积，再从右往左用一个变量累积后缀积乘进去
    public static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        // 第一步：ans[i] 先存 i 左边所有元素的乘积
        ans[0] = 1;
        for (int i = 1; i < n; i++) {
            ans[i] = ans[i - 1] * nums[i - 1];
        }
        // 第二步：从右往左，right 累积 i 右边所有元素的乘积，直接乘进 ans[i]
        int right = 1;
        for (int i = n - 1; i >= 0; i--) {
            ans[i] *= right;
            right *= nums[i];
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
        System.out.println(Arrays.toString(productExceptSelf(nums)));
    }
}

/**
 * 思路：前缀积 × 后缀积
 * <p>
 * 题目的定义：answer[i] = nums 中除 nums[i] 以外所有元素的乘积
 * = (nums[0] × ... × nums[i-1]) × (nums[i+1] × ... × nums[n-1])
 * = i 左边所有元素的乘积 × i 右边所有元素的乘积
 * <p>
 * 关键：这两个乘积都可以递推得到，不必对每个 i 都重新算一遍：
 * - 前缀积 prefix[i] = prefix[i-1] × nums[i-1]（从左往右扫）
 * - 后缀积 suffix[i] = suffix[i+1] × nums[i+1]（从右往左扫）
 * <p>
 * 版本一（productExceptSelf1，O(n) 空间）：
 * 用两个数组分别存 prefix 和 suffix，再对应位置相乘得到答案，最直观。
 * <p>
 * 版本二（productExceptSelf，O(1) 额外空间）：
 * 对版本一的空间优化——输出数组 ans 不被视为额外空间，可以拿它当 prefix 用：
 * 1. 第一遍从左往右：ans[i] = ans[i-1] × nums[i-1]，此时 ans[i] 存的是前缀积；
 * 2. 第二遍从右往左：用一个变量 right 累积后缀积，ans[i] *= right，然后 right *= nums[i]。
 * 两个辅助数组就省成了一个变量。
 * <p>
 * 注意：题目要求不能用除法，而且数组里可能有 0，"总乘积除以自身"在遇到 0 时会失效（不能除以 0），
 * 前缀积的做法天然规避了这两个问题。
 * <p>
 * 时间复杂度 O(n)：两遍（版本一三遍）扫描，每个元素访问常数次
 * 空间复杂度 O(1)：输出数组不算额外空间，只用到 right 一个变量（版本一是 O(n)）
 */
