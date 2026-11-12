/**
 * 169.多数元素（简单）
 * 给定一个大小为 n 的数组 nums，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 * <p>
 * 示例：
 * 输入：nums = [3,2,3]
 * 输出：3
 * <p>
 * 提示：
 * n == nums.length
 * 1 <= n <= 5 * 10⁴
 * -10⁹ <= nums[i] <= 10⁹
 * <p>
 * Related Topics 数组 哈希表 分治 计数 排序
 */

import java.util.*;

public class majorityElement_skill_169 {

    // 哈希表计数：统计每个元素出现次数，返回超过 n/2 的那个（O(n) 空间）
    public static int majorityElement1(int[] nums) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int num : nums) {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            if (entry.getValue() > nums.length / 2) {
                return entry.getKey();
            }
        }
        return -1; // 题目保证多数元素存在，不会走到这里
    }

    // Boyer-Moore 投票：相同的加票，不同的减票，票数归零就换候选人（O(1) 空间）
    public static int majorityElement(int[] nums) {
        int candidate = nums[0];
        int count = 0;
        for (int num : nums) {
            if (count == 0) {
                candidate = num; // 票数归零，换当前元素当候选人
            }
            count += (num == candidate) ? 1 : -1;
        }
        return candidate;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] split = s.split(" ");
        int[] nums = new int[split.length];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = Integer.parseInt(split[i]);
        }
        System.out.println(majorityElement(nums));
    }
}

/**
 * 思路：Boyer-Moore 投票法
 * <p>
 * 核心思想：多数元素出现次数 > n/2，比其他所有元素加起来还多。
 * 所以可以把它想象成"投票"：遇到相同的就加一票，遇到不同的就减一票，票数归零就换一个候选人。
 * 由于多数元素票数占绝对优势，最后活下来的候选人一定是它。
 * <p>
 * 版本一（majorityElement，投票法，O(n) 时间 O(1) 空间）：
 * 维护 candidate 和 count：
 * - count == 0 时，把当前元素选为新的候选人；
 * - 之后遇到相同的 count++，遇到不同的 count--。
 * 遍历完，candidate 就是多数元素。
 * <p>
 * 版本二（majorityElement1，哈希表计数，O(n) 时间 O(n) 空间）：
 * 统计每个元素出现的次数，返回出现次数超过 n/2 的那个。
 * <p>
 * 以 nums = [2,2,1,1,1,2,2] 为例（版本一）：
 * 元素   2  2  1  1  1  2  2
 * count  1  2  1  0  1  0  1
 * 最后 count=1，candidate=2，就是多数元素。
 * <p>
 * 注意：题目保证多数元素一定存在，所以不用再验证。
 * 如果没有这个保证，需要再遍历一遍确认 candidate 的出现次数确实 > n/2。
 * <p>
 * 时间复杂度 O(n)：遍历一次数组
 * 空间复杂度 O(1)：只用两个变量（版本二是 O(n)）
 */
