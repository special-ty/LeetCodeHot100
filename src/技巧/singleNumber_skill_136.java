/**
 * 136.只出现一次的数字（简单）
 * 给你一个 非空 整数数组 nums，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 * 你必须设计并实现线性时间复杂度的算法来解决此问题，且该算法只使用常量额外空间。
 * <p>
 * 示例：
 * 输入：nums = [2,2,1]
 * 输出：1
 * <p>
 * 提示：
 * 1 <= nums.length <= 3 * 10⁴
 * -3 * 10⁴ <= nums[i] <= 3 * 10⁴
 * 除了某个元素只出现一次以外，其余每个元素均出现两次。
 * <p>
 * Related Topics 位运算 数组
 */

import java.util.*;

public class singleNumber_skill_136 {

    // 哈希集合：没见过的数加进来，见过的移除，最后剩下的就是答案（O(n) 空间）
    public static int singleNumber1(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                set.remove(num); // 第二次出现，移除
            }
        }
        return set.iterator().next();
    }

    // 异或：成对的数会抵消成 0，剩下的就是只出现一次的那个（O(1) 空间）
    public static int singleNumber(int[] nums) {
        int ans = 0;
        for (int num : nums) {
            ans ^= num;
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
        System.out.println(singleNumber(nums));
    }
}

/**
 * 思路：异或
 * <p>
 * 异或运算（^）的三条性质：
 * 1.任何数和 0 异或等于它本身：a ^ 0 = a；
 * 2.任何数和它自己异或等于 0：a ^ a = 0；
 * 3.异或满足交换律和结合律。
 * <p>
 * 所以把数组里所有数一起异或：出现两次的数会两两抵消成 0，最后剩下的就是那个只出现一次的数。
 * <p>
 * 版本一（singleNumber，异或，O(n) 时间 O(1) 空间）：
 * 初始 ans = 0，遍历数组逐个异或即可。
 * <p>
 * 版本二（singleNumber1，哈希集合，O(n) 时间 O(n) 空间）：
 * 遍历数组，遇到没见过的数就加进 set，遇到见过的就移除；最后 set 里剩下的就是答案。
 * <p>
 * 时间复杂度 O(n)：遍历一次数组
 * 空间复杂度 O(1)：只用一个变量（版本二是 O(n)）
 */
