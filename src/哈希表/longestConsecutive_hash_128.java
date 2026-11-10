/**
 * 128.最长连续序列（中等）
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 * <p>
 * 示例 1：
 * 输入：nums = [100,4,200,1,3,2]
 * 输出：4
 * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
 * <p>
 * 示例 2：
 * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
 * 输出：9
 * <p>
 * 提示：
 * 0 <= nums.length <= 10⁵
 * -10⁹ <= nums[i] <= 10⁹
 * <p>
 * Related Topics 并查集 数组 哈希表
 */

import java.util.*;

public class longestConsecutive_hash_128 {
    public static int longestConsecutive(int[] nums) {
        // 使用 set 的原因是：查找某个数是否存在的时间复杂度是 O(1)，同时可以去重
        HashSet<Integer> set = new HashSet<>();
        int ans = 0;

        for (int num : nums) {
            set.add(num);
        }

        for (int num : set) {
            // 判断 num 是否是一个连续序列的起点
            // 如果 num-1 不存在，说明 num 是序列的最小值
            if (!set.contains(num - 1)) {
                // 当前连续序列的长度，至少为 1
                int len = 1;
                // 不断检查 num+1、num+2、num+3...
                // 如果存在，就继续延长连续序列
                while (set.contains(++num)) {
                    len++;
                }
                // 更新最大连续长度
                ans = Math.max(ans, len);
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        String[] strs = s.split(" ");
        int[] nums = new int[strs.length];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = Integer.parseInt(strs[i]);
        }
        System.out.println(longestConsecutive(nums));
    }
}

/**
 * 思路：
 * 用哈希集合去重 + 只从 一段连续序列的起点 开始往后遍历，避免重复计算，保证整体 O (n)。
 * <p>
 * 1. 先把所有数字放进 `HashSet`，快速判断某个数是否存在，同时去重；
 * 2. 遍历集合里每个数 `x`：
 * - 如果 `x - 1` 不在集合里 → 说明 `x` 是一段连续序列的起点；
 * - 从起点 `x` 开始，不断看 `x+1、x+2...` 是否存在，统计当前段长度；
 * - 更新全局最大长度；
 * 3. 如果 `x-1` 在集合里，说明这个数不是起点，直接跳过，不往里走。
 * <p>
 * > 关键：每个数字只会被访问一次，所以是 O (n)，不是 O (n²)
 * > 如果不做这个判断，直接每个数都往后搜，最坏会退化成 O (n²)
 * <p>
 * 时间：O(n)，每个元素入集合一次、while 循环里最多访问一次
 * 空间：O(n)，哈希集合存全部数字
 */