/**
 * 11.盛最多水的容器（中等）
 * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * 返回容器可以储存的最大水量。
 * 说明：你不能倾斜容器。
 * <p>
 * 示例：
 * 输入：[1,8,6,2,5,4,8,3,7]
 * 输出：49
 * 解释：选索引 1、7 时，容器能够容纳水的最大值为 49。
 * <p>
 * 提示：
 * n == height.length
 * 2 <= n <= 10⁵
 * 0 <= height[i] <= 10⁴
 * <p>
 * Related Topics 贪心 数组 双指针
 */

import java.util.Scanner;

public class maxArea_11 {
    public static int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int max = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                max = Math.max(max, (right - left) * height[left]);
                left++;
            } else {
                max = Math.max(max, (right - left) * height[right]);
                right--;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] split = s.split(" ");
        int[] height = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            height[i] = Integer.parseInt(split[i]);
        }
        System.out.println(maxArea(height));
    }
}

/**
 * 思路：对撞双指针
 * 面积由宽度 × 两条线中较短的那条决定：面积 = min(h[left], h[right]) × (right - left)。
 * 从两端往中间收拢，每次移动较短的那根指针。
 * 原因：宽度只会越来越小，只有把较短的线换掉，才可能得到更大的面积；移动较长的那根，短板不变、宽度变小，面积只会更小，永远不可能更优。
 * <p>
 * 时间复杂度：O(n)，left、right 一共最多移动 n 次
 * 空间复杂度：O(1)，原地计算，只有常数个变量
 */