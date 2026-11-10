/**
 * 42.接雨水（难）
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 * <p>
 * 示例：
 * 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * 输出：6
 * <p>
 * 提示：
 * n == height.length
 * 1 <= n <= 2 * 10⁴
 * 0 <= height[i] <= 10⁵
 * <p>
 * Related Topics 栈 数组 双指针 动态规划 单调栈
 */

import java.util.Scanner;

public class trapRain_42 {
    public static int trap(int[] height) {
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        int ans = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left];        // 左边出现更高的柱子，更新 leftMax
                } else {
                    ans += leftMax - height[left]; // 否则当前柱子可以接水
                }
                left++;
            } else {
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    ans += rightMax - height[right];
                }
                right--;
            }
        }
        return ans;
    }

    public static int trap1(int[] height) {
        int n = height.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] strs = sc.nextLine().split(" ");
        int[] height = new int[strs.length];
        for (int i = 0; i < height.length; i++) {
            height[i] = Integer.parseInt(strs[i]);
        }
        sc.close();
        System.out.println(trap(height));
    }
}

/**
 * 思路：双指针
 * 核心公式：每个位置 i 能接的水 = min(它左边的最高柱, 它右边的最高柱) - height[i]。
 * <p>
 * 暴力做法是对每个位置分别扫一遍左右找最高，O(n²)。双指针把左右最高值在移动中维护起来：
 * <p>
 * left、right 从两端向中间走，leftMax、rightMax 分别记录两侧目前遇到的最高柱；
 * 每次比较 height[left] 和 height[right]，处理较矮的那一侧：因为较矮一侧的水位由它自己这侧的最高值决定——对面至少有一根不低于当前高度的柱子挡着，短板在它自己这边。
 * <p>
 * 为什么处理较矮的一侧是对的：当 height[left] < height[right] 时，right 及其右侧一定存在一根高度 ≥ height[right] > height[left] 的柱子。
 * 而 leftMax 是左侧最高，它要么是在 left 处取的，要么来自更早处理的位置 p，
 * 而 p 被处理时右指针在更右边，满足 height[p] < 当时右指针处的高度 ≤ 当前右侧真实最高值。
 * 所以 leftMax 一定小于右侧真实最高值，left 处的水位就是 leftMax，可以安全地立刻算出。
 * <p>
 * 时间复杂度：O(n)，left、right 一共最多移动 n 次，每个位置只处理一次；
 * 空间复杂度：O(1)，只用了 4 个变量。
 */