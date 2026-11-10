/**
 * 49.字母异位词分组（中等）
 * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
 * <p>
 * 示例 1:
 * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
 * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * <p>
 * 解释：
 * 在 strs 中没有字符串可以通过重新排列来形成 "bat"。
 * 字符串 "nat" 和 "tan" 是字母异位词，因为它们可以重新排列以形成彼此。
 * 字符串 "ate" ，"eat" 和 "tea" 是字母异位词，因为它们可以重新排列以形成彼此。
 * <p>
 * 示例 2:
 * 输入: strs = [""]
 * 输出: [[""]]
 * <p>
 * 提示：
 * 1 <= strs.length <= 10⁴
 * 0 <= strs[i].length <= 100
 * strs[i] 仅包含小写字母
 * <p>
 * Related Topics 数组 哈希表 字符串 排序
 */

import java.util.*;

public class groupAnagrams_hash_49 {

    public static List<List<String>> groupAnagrams(String[] strs) {
        // 创建一个哈希表，用于存储排序后的字符串及其对应的字母异位词列表
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            // 将当前字符串转换为字符数组
            char[] array = str.toCharArray();
            // 对字符数组进行排序
            Arrays.sort(array);
            // 将排序后的字符数组转换为字符串，作为哈希表的键
            String key = new String(array);
            // 如果哈希表中不包含该键，则创建一个新的列表并将其与该键关联
            map.computeIfAbsent(key, k -> new ArrayList<>());
            map.get(key).add(str);
        }

        return new ArrayList<>(map.values());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] strs = s.split(" ");
        System.out.println(groupAnagrams(strs));
        /*还有一种输出的写法
        List<List<String>> ans = groupAnagrams(strs);
        for (List<String> list : ans) {
            for (String str : list) {
                System.out.print(str + " ");
            }
            System.out.println();
        }
        */
    }
}

/**
 * 思路：
 * 把字符串内部字符排序之后，得到的字符串一定相同。
 * 利用这一点，使用 map，key 为经过排序后的字符串，value 为当前异位词列表。
 * `computeIfAbsent`：key 不存在就新建 List，存在直接取出 List，然后把原字符串 add 进去。
 * <p>
 * n：字符串数组长度；k：单个字符串的最大长度
 * 时间复杂度：O(nklogk)
 * 每个字符串内部排序耗时 O(klogk)，一共 n 个字符串；哈希表的存取是近似 O(1)。
 * 空间复杂度：O(nk)
 * 需要存储所有字符串，map 里存全部字符串，key 也是排序后的字符串，总共占用 nk。
 */
