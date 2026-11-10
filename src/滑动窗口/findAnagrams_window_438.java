/**
 * 438.找到字符串中所有字母异位词（中等）
 * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
 * <p>
 * 异位词 指由相同字母重新排列形成的字符串（包括相同的字符串）。
 * <p>
 * 示例 1:
 * 输入: s = "cbaebabacd", p = "abc"
 * 输出: [0,6]
 * 解释:
 * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
 * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
 * <p>
 * 示例 2:
 * 输入: s = "abab", p = "ab"
 * 输出: [0,1,2]
 * 解释:
 * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
 * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
 * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
 * <p>
 * 提示:
 * 1 <= s.length, p.length <= 3 * 10⁴
 * s 和 p 仅包含小写字母
 * <p>
 * Related Topics 哈希表 字符串 滑动窗口
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class findAnagrams_window_438 {
    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        // 仅包含小写字母，用长度 26 的数组当计数器，比 HashMap 更快
        int[] pCount = new int[26]; // p 中每个字符的出现次数
        int[] sCount = new int[26]; // 当前窗口内每个字符的出现次数
        for (char c : p.toCharArray()) {
            pCount[c - 'a']++;
        }

        // 窗口大小固定为 p.length()，right 每步右移一格
        for (int right = 0; right < s.length(); right++) {
            // 扩大窗口：右边进一个字符
            sCount[s.charAt(right) - 'a']++;
            // 收缩窗口：窗口超过 p.length() 时，左边出一个字符
            if (right >= p.length()) {
                sCount[s.charAt(right - p.length()) - 'a']--;
            }
            // 窗口大小恰好等于 p.length()，比较两个计数器是否完全一致
            if (right >= p.length() - 1 && Arrays.equals(sCount, pCount)) {
                res.add(right - p.length() + 1); // 窗口左端点就是起始索引
            }
        }
        return res;
    }

    // 优化版：不每次比较整个数组，维护"计数不一致的字符种数" diff，把比较降为 O(1)
    public static List<Integer> findAnagrams1(String s, String p) {
        List<Integer> res = new ArrayList<>();
        // cnt[i] = p 中字符 i 的出现次数 - 窗口中字符 i 的出现次数，全部相等时 cnt 全为 0
        int[] cnt = new int[26];
        for (char c : p.toCharArray()) {
            cnt[c - 'a']++;
        }
        int diff = 0; // 有多少个字符的计数还不一致
        for (int x : cnt) {
            if (x != 0) diff++;
        }

        for (int right = 0; right < s.length(); right++) {
            // 右边进一个字符
            int in = s.charAt(right) - 'a';
            if (cnt[in] == 0) diff++; // 原本一致，进来后变得不一致
            cnt[in]--;
            if (cnt[in] == 0) diff--; // 又恢复一致

            // 左边出一个字符
            if (right >= p.length()) {
                int out = s.charAt(right - p.length()) - 'a';
                if (cnt[out] == 0) diff++;
                cnt[out]++;
                if (cnt[out] == 0) diff--;
            }

            // diff == 0 说明窗口与 p 的字符计数完全一致，即异位词
            if (right >= p.length() - 1 && diff == 0) {
                res.add(right - p.length() + 1);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        String p = sc.next();
        System.out.println(findAnagrams(s, p));
    }
}

/**
 * 思路：定长滑动窗口 + 字符计数
 * 异位词的本质是「字符计数完全相同」，所以目标不是找某个子串，而是在 s 上维护一个长度恰好为 p.length() 的窗口，
 * 判断窗口内字符计数是否和 p 的计数一致；一致就说明当前窗口是 p 的异位词，记录左端点。
 * <p>
 * 窗口是定长的：right 每右移一格，左边就必须出去一个字符，窗口大小始终不超过 p.length()。
 * 用长度 26 的数组计数而不是 HashMap，是因为题目限定小写字母，数组下标 c - 'a' 直接定位，更新和比较都更快。
 * <p>
 * 判断两个计数是否一致有两种做法：
 * - 直接 Arrays.equals 比较两个数组：每次 O(26)，常数很小，写法直观；
 * - 维护 diff（计数不一致的字符种数）：进出一个字符时 diff 最多变化 1，比较降为 O(1)。
 * <p>
 * 以 s = "cbaebabacd", p = "abc" 为例：窗口从 "cba" 开始，"cba"、"bae"、"aeb"、"eba"、"bab"、"aba"、"bac"……
 * 其中 "cba"（下标 0）和 "bac"（下标 6）的计数与 "abc" 完全一致，所以答案是 [0, 6]。
 * <p>
 * 时间复杂度：O(n)，right 遍历一次 s，每个字符进出窗口各一次，比较是 O(26) 或 O(1) 的常数
 * 空间复杂度：O(1)，只用两个长度 26 的计数数组（外加 diff 一个变量）
 */
