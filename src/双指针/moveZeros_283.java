/**
 * 283.移动零（简单）
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
 * <p>
 * 示例:
 * 输入: nums = [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 * <p>
 * 提示:
 * 1 <= nums.length <= 10⁴
 * -2³¹ <= nums[i] <= 2³¹ - 1
 * <p>
 * Related Topics 数组 双指针
 */

import java.util.Arrays;
import java.util.Scanner;

public class moveZeros_283 {
    public static void moveZeroes(int[] nums) {
        int slow = 0;
        // slow 指向下一个应该放非0元素的位置
        for (int fast = 0; fast < nums.length; fast++) {
            // 如果当前元素不是0
            if (nums[fast] != 0) {
                nums[slow] = nums[fast];
                // 把非 0 元素放到前面
                slow++;                  // slow 前进
            }
        }
        // 把剩余位置补0
        for (int i = slow; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String[] strs = str.split(" ");
        int[] nums = new int[strs.length];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = Integer.parseInt(strs[i]);
        }
        moveZeroes(nums);
        System.out.println(Arrays.toString(nums));
    }
}

/**
 * 思路：快慢指针
 * 维护一个 slow 指针，表示「下一个非零元素应该放的位置」。fast 从头到尾扫描：
 * fast 遇到非零元素 → 把它放到 slow 位置，slow 前进；
 * fast 遇到 0 → 跳过，什么都不做。
 * 扫完后，slow 及其之后的位置全部填 0 即可。
 * <p>
 * 时间复杂度：O(n)，最多两次线性扫描
 * 空间复杂度：O(1)，原地修改
 */