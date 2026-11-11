/**
 * 41.缺失的第一个正数（困难）
 * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
 * 请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
 * <p>
 * 示例：
 * 输入：nums = [1,2,0]
 * 输出：3
 * 解释：范围 [1,2] 中的数字都在数组中。
 * <p>
 * 提示：
 * 1 <= nums.length <= 10⁵
 * -2³¹ <= nums[i] <= 2³¹ - 1
 * <p>
 * Related Topics 数组 哈希表
 */

import java.util.*;

public class firstMissingPositive_array_41 {

    // 哈希集合：把所有数放进 set，从 1 开始逐个检查（O(n) 空间）
    public static int firstMissingPositive1(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int ans = 1;
        while (set.contains(ans)) {
            ans++;
        }
        return ans;
    }

    // 原地哈希：把数值 x 放到下标 x-1 处，再扫描找第一个位置不对的下标（O(1) 额外空间）
    public static int firstMissingPositive(int[] nums) {
        int n = nums.length;
        // 第一遍：把每个在 [1, n] 范围内的数，交换到它该在的下标（值 x -> 下标 x-1）
        for (int i = 0; i < n; i++) {
            // 循环条件：nums[i] 在 [1,n] 内，且它还没在正确的位置上
            // 后面的 nums[nums[i]-1] != nums[i] 用来防止重复元素导致死循环
            while (nums[i] >= 1 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
        }
        // 第二遍：第一个满足 nums[i] != i+1 的下标 i，答案就是 i+1
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        // 1~n 都齐了，答案是 n+1
        return n + 1;
    }

    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] split = s.split(" ");
        int[] nums = new int[split.length];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = Integer.parseInt(split[i]);
        }
        System.out.println(firstMissingPositive(nums));
    }
}

/**
 * 思路：原地哈希（O(n) 时间，O(1) 额外空间）
 * <p>
 * 关键观察：长度为 n 的数组，答案一定落在 [1, n+1] 里。
 * 因为如果 1~n 全都出现了，答案就是 n+1；否则答案就是 1~n 里缺失的那个。
 * 所以数组里的负数、0、以及大于 n 的数都是"无用"的，可以把它们当作空位。
 * <p>
 * 做法：把每个数放到它"该在"的位置——数值 x 放到下标 x-1（原地哈希，类似桶排序）：
 * 1.第一遍扫描：如果 nums[i] 在 [1, n] 内，且 nums[nums[i]-1] != nums[i]，就把它交换到下标 nums[i]-1 处；
 *   重复这个过程，直到 nums[i] 不在 [1,n] 内、或者它已经在正确位置。
 * 2.第二遍扫描：找第一个下标 i 满足 nums[i] != i+1，答案就是 i+1；如果都满足，答案是 n+1。
 * <p>
 * 为什么交换条件里要有 nums[nums[i]-1] != nums[i]：防止重复元素导致死循环。
 * 比如 [1,1]，如果不判断，下标 0 的 1 和下标 1 的 1 会一直交换下去。
 * <p>
 * 以 nums = [3,4,-1,1] 为例：
 * i=0：3 换到下标 2  -> [-1,4,3,1]，-1 无用，停
 * i=1：4 换到下标 3  -> [-1,1,3,4]；1 换到下标 0 -> [1,-1,3,4]，-1 无用，停
 * i=2：3 已经在下标 2，跳过
 * i=3：4 已经在下标 3，跳过
 * 第二遍：下标 1 处是 -1，期望 2 -> 答案 2。
 * <p>
 * 另一个版本（firstMissingPositive1）用哈希集合：把所有数放进 set，然后从 1 开始逐个检查是否在 set 里，
 * 第一个不在的就是答案。直观，但需要 O(n) 额外空间。
 * <p>
 * 时间复杂度 O(n)：虽然有两层循环，但每次交换都至少把一个数放到它的最终位置，
 * 而已经到位的数不会再动，所以交换总次数不超过 n，总体是 O(n)
 * 空间复杂度 O(1)：只用常数个临时变量（firstMissingPositive1 是 O(n)）
 */
