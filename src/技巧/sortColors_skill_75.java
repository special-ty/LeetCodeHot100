/**
 * 75.颜色分类（中等）
 * 给定一个包含红色、白色和蓝色、共 n 个元素的数组 nums，原地 对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序表示。
 * 我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
 * 必须在不使用库内置的 sort 函数的情况下解决这个问题。
 * <p>
 * 示例：
 * 输入：nums = [2,0,2,1,1,0]
 * 输出：[0,0,1,1,2,2]
 * <p>
 * 提示：
 * n == nums.length
 * 1 <= n <= 300
 * nums[i] 为 0、1 或 2
 * <p>
 * Related Topics 数组 双指针 排序
 */

import java.util.*;

public class sortColors_skill_75 {

    // 计数排序：先数一遍各有多少个，再按顺序填回去（两趟扫描）
    public static void sortColors1(int[] nums) {
        int[] count = new int[3];
        for (int num : nums) {
            count[num]++;
        }
        int idx = 0;
        for (int c = 0; c < 3; c++) {
            for (int k = 0; k < count[c]; k++) {
                nums[idx++] = c;
            }
        }
    }

    // 荷兰国旗三指针：left 左边全是 0，right 右边全是 2，i 是当前检查位置（一趟扫描）
    public static void sortColors(int[] nums) {
        int left = 0, i = 0, right = nums.length - 1;
        // 数组被分成四段：[0,left) 全是 0，[left,i) 全是 1，[i,right] 待检查，(right,n-1] 全是 2
        while (i <= right) {
            if (nums[i] == 0) {
                // 和 left 交换：left 及其左边只可能是 0 或 1，换过来的一定不是 2，可以安全前进
                swap(nums, i, left);
                left++;
                i++;
            } else if (nums[i] == 2) {
                // 和 right 交换：right 位置的元素还没检查过，换过来可能是 0/1/2，所以 i 不能前进
                swap(nums, i, right);
                right--;
            } else {
                i++;
            }
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
        sortColors(nums);
        System.out.println(Arrays.toString(nums));
    }
}

/**
 * 思路：荷兰国旗（三指针）
 * <p>
 * 版本一（sortColors，三指针，一趟扫描，O(n) 时间 O(1) 空间）：
 * 维护三个指针，把数组分成四段：
 * [0, left)      全是 0
 * [left, i)      全是 1
 * [i, right]     还没检查
 * (right, n-1]   全是 2
 * 遍历中只看 nums[i]：
 * - 等于 0：和 left 交换，left++、i++（left 及其左边只可能是 0 或 1，换过来的一定不是 2）；
 * - 等于 1：i++；
 * - 等于 2：和 right 交换，right--，但 i 不动——right 位置的元素还没检查过，换过来可能是 0/1/2。
 * <p>
 * 版本二（sortColors1，计数排序，两趟扫描，O(n) 时间 O(1) 空间）：
 * 先数一遍 0、1、2 各有多少个，再按顺序填回数组。更简单，但要扫两遍。
 * <p>
 * 以 nums = [2,0,2,1,1,0] 为例（版本一）：
 * i=0 遇到 2，和 right 交换 -> [0,0,2,1,1,2]，right=4，i 不动
 * i=0 遇到 0，和 left 交换 -> left=1，i=1
 * i=1 遇到 0，和 left 交换 -> left=2，i=2
 * i=2 遇到 2，和 right 交换 -> [0,0,1,1,2,2]，right=3，i 不动
 * i=2、i=3 都是 1，i 一路走到 4 > right，结束
 * 结果 [0,0,1,1,2,2]。
 * <p>
 * 时间复杂度 O(n)：版本一每个元素最多被交换两次；版本二扫两遍
 * 空间复杂度 O(1)：只用几个指针/计数变量
 */
