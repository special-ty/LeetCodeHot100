/**
 * 287.寻找重复数（中等）
 * 给定一个包含 n + 1 个整数的数组 nums，其数字都在 [1, n] 范围内（包括 1 和 n），可知至少存在一个重复的整数。
 * 假设 nums 只有一个重复的整数 ，返回这个重复的数。
 * 你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。
 * <p>
 * 提示：
 * 1 <= n <= 10⁵
 * nums.length == n + 1
 * 1 <= nums[i] <= n
 * nums 中 只有一个整数出现两次或多次，其余整数均只出现一次
 * <p>
 * Related Topics 位运算 数组 双指针 二分查找
 */

import java.util.*;

public class findDuplicate_skill_287 {

    // 哈希集合：记录出现过的数，第一个放进去失败的就是重复的（O(n) 空间）
    public static int findDuplicate1(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                return num;
            }
        }
        return -1; // 题目保证有重复，不会走到这里
    }

    // Floyd 判圈：把数组看成链表，快慢指针找环入口（O(1) 空间）
    public static int findDuplicate(int[] nums) {
        // 阶段一：快慢指针在环内相遇
        int slow = nums[0], fast = nums[nums[0]];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }
        // 阶段二：一个指针回到起点，两个指针同速前进，相遇点就是环入口（即重复的数）
        int p = 0;
        while (p != slow) {
            p = nums[p];
            slow = nums[slow];
        }
        return p;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] split = s.split(" ");
        int[] nums = new int[split.length];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = Integer.parseInt(split[i]);
        }
        System.out.println(findDuplicate(nums));
    }
}

/**
 * 思路：Floyd 判圈（快慢指针）
 * <p>
 * 关键转化：把数组看成一个链表——下标 i 的"下一个节点"是 nums[i]。
 * 因为值域是 [1, n]、长度是 n+1，所以 nums[i] 一定是合法下标；
 * 而必然有一个数出现两次（鸽巢原理），也就必然有两个下标指向同一个节点，即链表有环。
 * 重复的那个数，就是环的入口。
 * <p>
 * 分两步（和"判断链表有环 + 找环入口"完全一样）：
 * 1. 快慢指针：slow 每次走一步、fast 每次走两步，一定会在环内相遇；
 * 2. 把一个指针放回起点（下标 0），两个指针都每次走一步，再次相遇的地方就是环入口，即重复的数。
 * <p>
 * 为什么第二步相遇在环入口（推导）：
 * 把链表画成 head → (a 步) → 入口 → (b 步) → 相遇点 → (c 步) → 回到入口，环长 L = b + c。
 * 第一阶段：设相遇时 slow 走了 S 步、fast 走了 F 步，则 F = 2S；
 * 而两者走的都是"先 a + b 到相遇点，再在环里绕若干圈"，即
 *   S = a + b + p·L ，F = a + b + q·L
 * 代入 F = 2S 得 a + b = (q - 2p)·L —— a + b 是环长 L 的整数倍。
 * 第二阶段：
 * - 指针 p 从起点走 a 步，正好到达入口；
 * - slow 从相遇点走 a 步，相遇点在入口之后 b 步处，所以它相对入口的位置是
 *   (a + b) mod L = k·L mod L = 0，也就是正好回到入口。
 * 两个指针同速、同时出发、同时到达入口，所以相遇点必是环入口；
 * 也不可能更早相遇——前 a 步里 p 还在环外的"尾巴"上，slow 一直在环内，不会碰面。
 * <p>
 * 补充：起点一定在环外——因为 nums[i] ∈ [1, n]，没有任何下标指向 0，
 * 所以下标 0 不可能在环上，"起点到入口的距离 a" 一定存在。
 * <p>
 * 版本二（findDuplicate1，哈希集合，O(n) 空间）：
 * 遍历数组，用 set 记录出现过的数，第一个放进去失败（已存在）的就是重复的。
 * <p>
 * 为什么不能排序或下标计数：题目要求不能修改原数组，且只能用 O(1) 额外空间。
 * <p>
 * 以 nums = [1,3,4,2,2] 为例（n=4，值域 [1,4]）：
 * 看成链表：0→1→3→2→4→2→…（下标 4 又指回 2，成环）
 * 第一阶段：slow 走 1→3→2→4，fast 走 3→4→4，在 4 相遇；
 * 第二阶段：p 从 0 出发、slow 从 4 出发，同速前进，在 2 相遇 → 答案 2。
 * <p>
 * 时间复杂度 O(n)：两个阶段都是 O(n)
 * 空间复杂度 O(1)：只用几个变量（版本二是 O(n)）
 */
