/**
 1.两数之和（简单）
 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那 两个 整数，并返回它们的数组下标。
 你可以假设每种输入只会对应一个答案，并且你不能使用两次相同的元素。
 你可以按任意顺序返回答案。

 示例 1：
 输入：nums = [2,7,11,15], target = 9
 输出：[0,1]
 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。

 示例 2：
 输入：nums = [3,2,4], target = 6
 输出：[1,2]

 提示：
 2 <= nums.length <= 10⁴
 -10⁹ <= nums[i] <= 10⁹
 -10⁹ <= target <= 10⁹
 只会存在一个有效答案

 Related Topics 数组 哈希表
 */

import java.util.*;

public class twoSum_hash_1 {

    public static int[] twoSum(int[] nums, int target) {

        Map<Integer, Integer> map = new HashMap();

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            } else {
                map.put(nums[i], i);
            }
        }
        return new int[0];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] split = s.split(" ");
        int[] nums = new int[split.length];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = Integer.parseInt(split[i]);
        }
        int target = sc.nextInt();
        System.out.println(Arrays.toString(twoSum(nums, target)));
    }
}

/**
 * 思路：
 * map（key 存储元素值，value 为索引）。
 * 遍历数组的过程中，判断 map 中是否有（target - 当前元素），有则返回当前元素下标和 map 中（target - 当前元素）的 value；
 * 如果没有就把当前元素插入到 map。
 *
 * 时间复杂度 O(n), 空间复杂度 O(n)
 */