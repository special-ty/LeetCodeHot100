/**
 * 189.轮转数组（中等）
 * 给定一个整数数组 nums 和一个整数 k，将数组中的元素向右轮转 k 个位置。
 * <p>
 * 示例：
 * 输入：nums = [1,2,3,4,5,6,7], k = 3
 * 输出：[5,6,7,1,2,3,4]
 * 解释：
 * 向右轮转 1 步：[7,1,2,3,4,5,6]
 * 向右轮转 2 步：[6,7,1,2,3,4,5]
 * 向右轮转 3 步：[5,6,7,1,2,3,4]
 * <p>
 * 提示：
 * 1 <= nums.length <= 10⁵
 * -2³¹ <= nums[i] <= 2³¹ - 1
 * 0 <= k <= 10⁵
 * <p>
 * Related Topics 数组 数学 双指针
 */

import java.util.*;

public class rotate_array_189 {

    // 辅助数组：原下标 i 的元素旋转后落在 (i + k) % n（O(n) 空间）
    public static void rotate1(int[] nums, int k) {
        int n = nums.length;
        int[] copy = nums.clone();
        for (int i = 0; i < n; i++) {
            nums[(i + k) % n] = copy[i];
        }
    }

    // 三次反转：O(1) 额外空间
    public static void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n; // k 可能大于 n，轮转 n 次等于没转
        // 1. 反转整个数组
        reverse(nums, 0, n - 1);
        // 2. 反转前 k 个
        reverse(nums, 0, k - 1);
        // 3. 反转剩下的 n - k 个
        reverse(nums, k, n - 1);
    }

    // 反转 nums[left..right]
    private static void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int tmp = nums[left];
            nums[left] = nums[right];
            nums[right] = tmp;
            left++;
            right--;
        }
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
        rotate(nums, k);
        System.out.println(Arrays.toString(nums));
    }
}

/**
 * 思路：三次反转（O(1) 额外空间）
 * <p>
 * 目标：把数组向右轮转 k 位，也就是把最后 k 个元素整体挪到最前面。
 * 把数组从 n-k 处切成两段：A = [0, n-k-1]，B = [n-k, n-1]，目标是把 [A, B] 变成 [B, A]。
 * <p>
 * 关键操作：反转。对一段序列反转两次等于不变；而 [A, B] 整体反转后得到 [B逆, A逆]——
 * 两段的位置交换了，但各自内部的顺序也反了，再分别反转每一段即可恢复：
 * 1.反转整个数组          [A, B]    -> [B逆, A逆]
 * 2.反转前 k 个（即 B）   [B逆, A逆] -> [B, A逆]
 * 3.反转后 n-k 个（即 A） [B, A逆]   -> [B, A]
 * <p>
 * 以 nums = [1,2,3,4,5,6,7], k = 3 为例，原数组可看成 A = [1,2,3,4]、B = [5,6,7]：
 * 1.反转全部    -> [7,6,5,4,3,2,1]
 * 2.反转前 3 个 -> [5,6,7,4,3,2,1]
 * 3.反转后 4 个 -> [5,6,7,1,2,3,4]  即答案
 * <p>
 * 注意 k 要先对 n 取模：k 可能大于 n，而轮转 n 次等于没转，取模后 k 落在 [0, n-1]。
 * <p>
 * 另一个版本（rotate1）用辅助数组：原下标 i 的元素旋转后落在 (i + k) % n，
 * 按这个映射拷贝一遍即可，直观但需要 O(n) 额外空间。
 * <p>
 * 时间复杂度 O(n)：三次反转各遍历一段，总体每个元素被访问常数次
 * 空间复杂度 O(1)：只用到几个临时变量（rotate1 是 O(n)）
 */
