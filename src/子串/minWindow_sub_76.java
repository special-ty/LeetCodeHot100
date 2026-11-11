/**
 * 76.最小覆盖子串（困难）
 * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
 * 注意：
 * 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
 * 如果 s 中存在这样的子串，我们保证它是唯一的答案。
 * <p>
 * 示例 1：
 * 输入：s = "ADOBECODEBANC", t = "ABC"
 * 输出："BANC"
 * 解释：最小覆盖子串 "BANC" 包含来自字符串 t 的 'A'、'B' 和 'C'。
 * <p>
 * 示例 2：
 * 输入：s = "a", t = "a"
 * 输出："a"
 * 解释：整个字符串 s 是最小覆盖子串。
 * <p>
 * 示例 3：
 * 输入：s = "a", t = "aa"
 * 输出：""
 * 解释：t 中两个字符 'a' 均应包含在 s 的子串中，因此没有符合条件的子字符串，返回空字符串。
 * <p>
 * 提示：
 * m == s.length
 * n == t.length
 * 1 <= m, n <= 10⁵
 * s 和 t 由英文字母组成
 * <p>
 * Related Topics 哈希表 字符串 滑动窗口
 */

import java.util.*;

public class minWindow_sub_76 {

    // 滑动窗口：need 记录"还差多少个"，required 记录"还没凑够的字符种数"
    public static String minWindow(String s, String t) {
        // need[c]：字符 c 还差多少个才够（负数表示窗口里多出了一些）
        int[] need = new int[128];
        for (char c : t.toCharArray()) {
            need[c]++;
        }
        int required = 0;
        for (int x : need) {
            if (x > 0) {
                required++;
            }
        }
        int left = 0;
        int bestLeft = 0, bestLen = s.length() + 1; // 取 length + 1 表示"还没找到"
        for (int right = 0; right < s.length(); right++) {
            // 右边进窗口
            if (--need[s.charAt(right)] == 0) {
                required--; // 这个字符刚好凑够
            }
            // 窗口已覆盖 t，尝试收缩左边界
            while (required == 0) {
                if (right - left + 1 < bestLen) {
                    bestLen = right - left + 1;
                    bestLeft = left;
                }
                // 左边出窗口
                if (++need[s.charAt(left)] == 1) {
                    required++; // 这个字符又不够了
                }
                left++;
            }
        }
        return bestLen == s.length() + 1 ? "" : s.substring(bestLeft, bestLeft + bestLen);
    }

    // 直观版：窗口是否覆盖 t，直接比较两个计数数组
    public static String minWindow1(String s, String t) {
        int[] need = new int[128];
        for (char c : t.toCharArray()) {
            need[c]++;
        }
        int[] window = new int[128];
        int left = 0;
        int bestLeft = 0, bestLen = s.length() + 1;
        for (int right = 0; right < s.length(); right++) {
            window[s.charAt(right)]++;
            while (covers(window, need)) {
                if (right - left + 1 < bestLen) {
                    bestLen = right - left + 1;
                    bestLeft = left;
                }
                window[s.charAt(left)]--;
                left++;
            }
        }
        return bestLen == s.length() + 1 ? "" : s.substring(bestLeft, bestLeft + bestLen);
    }

    // 窗口内的字符计数是否不少于 need
    private static boolean covers(int[] window, int[] need) {
        for (int i = 0; i < 128; i++) {
            if (window[i] < need[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        String t = sc.next();
        System.out.println(minWindow(s, t));
    }
}

/**
 * 思路：滑动窗口
 * 暴力做法是枚举所有子串、逐个判断是否包含 t，子串有 O(m²) 个、每次判断 O(n)，整体太慢。
 * <p>
 * 用滑动窗口维护区间 [left, right]，让窗口内的字符始终"向着覆盖 t 的方向"移动：
 * 1.right 右移，把字符加进窗口，直到窗口覆盖 t；
 * 2.覆盖后右移 left 收缩窗口，每收缩一步更新最优答案，直到窗口不再覆盖 t；
 * 3.重复上述过程，直到 right 走到头。
 * <p>
 * 关键设计：need[c] 表示"字符 c 还差多少个才够"，初始为 t 中各字符的计数：
 * - 字符进窗口：need[c]--，减到 0 说明这个字符刚好凑够，required--；
 * - 字符出窗口：need[c]++，从 0 变 1 说明这个字符又不够了，required++；
 * - required 为 0 就表示窗口覆盖了 t，判断覆盖从 O(128) 降为 O(1)。
 * <p>
 * 两个版本的区别只在"怎么判断覆盖"：
 * - minWindow：维护 required 计数，判断 O(1)；
 * - minWindow1：每次直接比较两个计数数组，直观，但每次判断 O(128)。
 * <p>
 * 以 s = "ADOBECODEBANC", t = "ABC" 为例：
 * right 走到下标 5 时窗口 "ADOBEC" 首次覆盖 t；之后 right 继续右移，
 * 每覆盖一次就尽量收缩 left，最终在下标 [9,12] 得到最短覆盖子串 "BANC"。
 * <p>
 * 时间复杂度 O(m)，minWindow1 为 O(128m)：left、right 各自最多移动 m 次，每个字符进出窗口一次
 * 空间复杂度 O(1)，只用两个长度 128 的计数数组
 */
