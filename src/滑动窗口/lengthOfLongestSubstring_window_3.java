/**
 * 3.无重复字符的最长子串（中等）
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长 子串 的长度。
 * <p>
 * 示例:
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。注意 "bca" 和 "cab" 也是正确答案。
 * <p>
 * 提示：
 * 0 <= s.length <= 10⁵
 * s 由英文字母、数字、符号和空格组成
 * <p>
 * Related Topics 哈希表 字符串 滑动窗口
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class lengthOfLongestSubstring_window_3 {
    public static int lengthOfLongestSubstring(String s) {
        // key：字符，value：该字符最近一次出现的下标
        Map<Character, Integer> map = new HashMap<>();
        int left = 0, maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            // 该字符在当前窗口内出现过，left 直接跳到重复字符的下一个位置
            if (map.containsKey(c) && map.get(c) >= left) {
                left = map.get(c) + 1;
            }
            // 更新字符的最新下标
            map.put(c, right);
            // 更新最大长度
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        System.out.println(lengthOfLongestSubstring(s));
    }
}

/**
 * 思路：滑动窗口 + 哈希表
 * 维护一个窗口 [left, right]，保证窗口内没有重复字符。right 不断右移扩大窗口；
 * 一旦遇到窗口内已存在的字符，就把 left 跳到这个重复字符上次出现位置的下一个位置，完成收缩。
 * map 记录每个字符最近一次出现的下标，用来 O(1) 判断重复、并确定 left 该跳到的位置。
 * <p>
 * 关键判断是 map.get(c) >= left：只有重复字符出现在当前窗口内（下标不小于 left）才需要收缩；
 * 如果它在 left 左边，说明早就被移出窗口了，不影响当前窗口。
 * <p>
 * 以 "abcabcbb" 为例：right 走到下标 2 时窗口是 "abc"，maxLen = 3；
 * 走到下标 3 又遇到 'a'，'a' 上次出现在下标 0，left 跳到 1，窗口变成 "bca"，长度仍是 3；
 * 后续同理，窗口一直在长度为 3 的范围内滑动，最终答案 3。
 * <p>
 * 时间复杂度：O(n)，right 遍历一次，left 只会向右跳，每个字符最多进出窗口一次
 * 空间复杂度：O(min(m, n))，m 为字符集大小，map 最多存一个窗口内的字符
 */
