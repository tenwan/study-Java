---
title: DFS相关算法题
excerpt: 对DFS相关算法题的总结
categories:
- java算法
tags:
- 习题
---

### 1.1、[矩阵中的路径](https://www.nowcoder.com/practice/c61c6999eecb4b8f88a98f66b273a3cc?tpId=13&tqId=11218&tPage=4&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

>设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从矩阵中的任意一格开始，每一步可以在矩阵中向左、右、上、下移动一格。如果一条路径经过了矩阵的某一格，那么该路径不能再次进入该格子。
>
>```
>输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
>输出：true
>```
>
>```
>输入：board = [["a","b"],["c","d"]], word = "abcd"
>输出：false
>```

```
private final static int[][] next = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
private int rows;
private int cols;

public boolean hasPath(char[] array, int rows, int cols, char[] str) {
    if (rows == 0 || cols == 0) return false;
    this.rows = rows;
    this.cols = cols;
    boolean[][] marked = new boolean[rows][cols];
    char[][] matrix = buildMatrix(array);
    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++)
            if (backtracking(matrix, str, marked, 0, i, j))
                return true;

    return false;
}

private boolean backtracking(char[][] matrix, char[] str,
                             boolean[][] marked, int pathLen, int r, int c) {
     // 如果长度满足，则为true：true的条件                           
    if (pathLen == str.length) return true;
    // 如果任意满足，则false：false的条件
    if (r < 0 || r >= rows || c < 0 || c >= cols
            || matrix[r][c] != str[pathLen] || marked[r][c]) {

        return false;
    }
    // 我这个元素只能拿一次，递归的时候，你不能拿了
    marked[r][c] = true;
    for (int[] n : next)
        if (backtracking(matrix, str, marked, pathLen + 1, r + n[0], c + n[1]))
            return true;
    // 递归结束，该元素为false，意味着，可以拿了，回溯嘛，就像线程切换一样
    marked[r][c] = false;
    return false;
}

private char[][] buildMatrix(char[] array) {
    char[][] matrix = new char[rows][cols];
    for (int r = 0, idx = 0; r < rows; r++)
        for (int c = 0; c < cols; c++)
            matrix[r][c] = array[idx++];
    return matrix;
}
```

### 1.2. 单词搜索(420)

>给定一个二维网格和一个单词，找出该单词是否存在于网格中。
>
>board =
>[
>  ['A','B','C','E'],
>  ['S','F','C','S'],
>  ['A','D','E','E']
>]
>
>给定 word = "ABCCED", 返回 true
>给定 word = "SEE", 返回 true
>给定 word = "ABCB", 返回 false

```
class Solution {
    private final static int[][] direction = {{1,0},{-1,0},{0,1},{0,-1}};
    private int m;
    private int n;
    public boolean exist(char[][] board, String word) {
        if (word == null || word.length() == 0) return true;
        if (board == null || board.length == 0 || board[0].length == 0) return false;
        m = board.length;
        n = board[0].length;
        boolean[][] hasVisited = new boolean[m][n];
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (backtracking(0, r, c, hasVisited, board, word)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean backtracking(int curLen, int r, int c, boolean[][] visited, final char[][] board, final String word) {
        // 符合条件
        if (curLen == word.length()) return true;
        // 不符合条件
        if (r < 0 || r >= m || c < 0 || c >= n || board[r][c] != word.charAt(curLen) || visited[r][c]) return false;
        // 表面元素已用过
        visited[r][c] = true;
        for (int[] d : direction) {
            if (backtracking(curLen + 1, r + d[0], c + d[1], visited, board, word)) return true;
        }
        // 可以重新使用
        visited[r][c] = false;
        return false;
    }
}
```

### 2.1、[字符串的排列](https://www.nowcoder.com/practice/fe6b651b66ae47d7acce78ffdd9a96c7?tpId=13&tqId=11180&tPage=2&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

>输入一个字符串，打印出该字符串中字符的所有排列。
>
>```
>输入：s = "abc"
>输出：["abc","acb","bac","bca","cab","cba"]
>```

```
private ArrayList<String> ret = new ArrayList<>();

public ArrayList<String> Permutation(String str) {
    if (str.length() == 0)
        return ret;
    char[] chars = str.toCharArray();
    // 排序，过滤重复
    Arrays.sort(chars);
    backtracking(chars, new boolean[chars.length], new StringBuilder());
    return ret;
}

private void backtracking(char[] chars, boolean[] hasUsed, StringBuilder s) {
    // 满足条件
    if (s.length() == chars.length) {
        ret.add(s.toString());
        return;
    }
    // 遍历
    for (int i = 0; i < chars.length; i++) {
        // 我已经拿过了，不能在拿了。
        if (hasUsed[i])
            continue;
        // 避免重复，实际上优化！ 注意后面那个条件，上一个元素没用过
        if (i != 0 && chars[i] == chars[i - 1] && !hasUsed[i - 1]) /* 保证不重复 */
            continue;
        // 标记只能取一次
        hasUsed[i] = true;
        s.append(chars[i]);
        backtracking(chars, hasUsed, s);
        s.deleteCharAt(s.length() - 1);
        hasUsed[i] = false;
    }
}
```

### 2.2. 全排列(985)

>给定一个 **没有重复** 数字的序列，返回其所有可能的全排列。
>
>```
>输入: [1,2,3]
>输出:
>[
>  [1,2,3],
>  [1,3,2],
>  [2,1,3],
>  [2,3,1],
>  [3,1,2],
>  [3,2,1]
>]
>```

```
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> permutes = new ArrayList<>();
        List<Integer> permuteList = new ArrayList<>();
        boolean[] hasVisited = new boolean[nums.length];
        backtracking(permuteList, permutes, hasVisited, nums);
        return permutes;
    }
    private void backtracking(List<Integer> permuteList, List<List<Integer>> permutes, boolean[] visited, final int[] nums) {
        // 满足条件
        if (permuteList.size() == nums.length) {
            permutes.add(new ArrayList<>(permuteList)); // 重新构造一个List
            return;
        }
        // 遍历
        for (int i = 0; i < visited.length; i++) {
            // 已经拿过了，不能再拿了
            if (visited[i]) 
                continue;
            // 标记
            visited[i] = true;
            permuteList.add(nums[i]);
            backtracking(permuteList, permutes, visited, nums);
            // 回溯
            permuteList.remove(permuteList.size() - 1);
            visited[i] = false;
        }
        
    }
}
```

### 2.3. 全排列 II(429)

>给定一个可包含重复数字的序列 `nums` ，**按任意顺序** 返回所有不重复的全排列。
>
>```
>输入：nums = [1,1,2]
>输出：
>[[1,1,2],
> [1,2,1],
> [2,1,1]]
>```
>
>```
>输入：nums = [1,2,3]
>输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
>```

```
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> permutes = new ArrayList<>();
        List<Integer> permuteList = new ArrayList<>();
        Arrays.sort(nums); // 排序，为了避免重复
        boolean[] hasVisited = new boolean[nums.length];
        backtracking(permuteList, permutes, hasVisited, nums);
        return permutes;
    }
    private void backtracking(List<Integer> permuteList, List<List<Integer>> permutes, boolean[] visited, final int[] nums) {
        // 满足条件
        if (permuteList.size() == nums.length) {
            permutes.add(new ArrayList<>(permuteList));
            return;
        }
        // 遍历
        for (int i = 0; i < visited.length; i++) {
            // 避免重复
            if (i != 0 && nums[i] == nums[i -1] && !visited[i - 1]) {
                continue; // 防止重复
            }
            // 表明已经拿了，退出
            if (visited[i]) 
                continue;
            // 标记，只能拿一次
            visited[i] = true;
            permuteList.add(nums[i]);
            backtracking(permuteList, permutes, visited, nums);
            // 回溯
            permuteList.remove(permuteList.size() - 1);
            visited[i] = false;
        }
    }
}
```

### 2.4. 组合总和(582)

>给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
>
>candidates 中的数字可以无限制重复被选取。
>
>```
>输入：candidates = [2,3,6,7], target = 7,
>所求解集为：
>[
>  [7],
>  [2,2,3]
>]
>```
>
>```
>输入：candidates = [2,3,5], target = 8,
>所求解集为：
>[
>  [2,2,2,2],
>  [2,3,3],
>  [3,5]
>]
>```

```
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> combinations = new ArrayList<>();
        backtracking(new ArrayList<>(), combinations, 0, target, candidates);
        return combinations;
    }

    private void backtracking(List<Integer> tempCombination, List<List<Integer>> combinations,
                            int start, int target, final int[] candidates) {
        // target为0，则满足
        if (target == 0) {
            combinations.add(new ArrayList<>(tempCombination));
            return;
        }
        // 遍历从start开始
        for (int i = start; i < candidates.length; i++) {
            // 注意这个骚条件，满足才行
            if (candidates[i] <= target) {
                tempCombination.add(candidates[i]);
                backtracking(tempCombination, combinations, i, target - candidates[i], candidates);
                // 回溯
                tempCombination.remove(tempCombination.size() - 1);
            }
        }
    }
}
```

### 2.5. 组合总和 II(401)

>给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
>
>candidates 中的每个数字在每个组合中只能使用一次。
>
>```
>输入: candidates = [10,1,2,7,6,1,5], target = 8,
>所求解集为:
>[
>  [1, 7],
>  [1, 2, 5],
>  [2, 6],
>  [1, 1, 6]
>]
>```
>
>```
>输入: candidates = [2,5,2,1,2], target = 5,
>所求解集为:
>[
>  [1,2,2],
>  [5]
>]
>```

```
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> combinations = new ArrayList<>();
        Arrays.sort(candidates); // 为了避免重复
        backtracking(new ArrayList<>(), combinations, new boolean[candidates.length], 0, target, candidates);
        return combinations;
    }

    private void backtracking(List<Integer> tempCombination, List<List<Integer>> combinations,
                            boolean[] hasVisited, int start, int target, final int[] candidates) {

        if (target == 0) {
            combinations.add(new ArrayList<>(tempCombination));
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if(hasVisited[i])
                continue;
            // 一样的道理
            if (i != 0 && candidates[i] == candidates[i - 1] && !hasVisited[i - 1]) {
                continue;
            }
            if (candidates[i] <= target) {
                tempCombination.add(candidates[i]);
                // 只能拿一次
                hasVisited[i] = true;
                backtracking(tempCombination, combinations, hasVisited, i, target - candidates[i], candidates);
                hasVisited[i] = false;
                tempCombination.remove(tempCombination.size() - 1);
            }
        }
    }

}
```

### 3.1. 子集(633)

>给你一个整数数组 `nums` ，数组中的元素 **互不相同** 。返回该数组所有可能的子集（幂集）。
>
>解集 **不能** 包含重复的子集。你可以按 **任意顺序** 返回解集。
>
> ```
>输入：nums = [1,2,3]
>输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
> ```
>
>```
>输入：nums = [0]
>输出：[[],[0]]
>```

```
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> subsets = new ArrayList<>();
        List<Integer> tempSubset = new ArrayList<>();
        for (int size = 0; size <= nums.length; size++) {
            backtracking(0, tempSubset, subsets, size, nums); // 不同的子集大小
        }
        return subsets;
    }

    private void backtracking(int start, List<Integer> tempSubset, List<List<Integer>> subsets,
                            final int size, final int[] nums) {

        if (tempSubset.size() == size) {
            subsets.add(new ArrayList<>(tempSubset));
            return;
        }
        for (int i = start; i < nums.length; i++) {
            tempSubset.add(nums[i]);
            backtracking(i + 1, tempSubset, subsets, size, nums);
            tempSubset.remove(tempSubset.size() - 1);
        }
    }

}
```

### 3.2. 子集 II(304)

>给定一个可能包含重复元素的整数数组 ***nums***，返回该数组所有可能的子集（幂集）。
>
>```
>输入: [1,2,2]
>输出:
>[
>  [2],
>  [1],
>  [1,2,2],
>  [2,2],
>  [1,2],
>  []
>]
>```

```
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums); // 注意
        List<List<Integer>> subsets = new ArrayList<>();
        List<Integer> tempSubset = new ArrayList<>();
        boolean[] hasVisited = new boolean[nums.length];
        for (int size = 0; size <= nums.length; size++) {
            backtracking(0, tempSubset, subsets, hasVisited, size, nums); // 不同的子集大小
        }
        return subsets;
    }

    private void backtracking(int start, List<Integer> tempSubset, List<List<Integer>> subsets, boolean[] hasVisited,
                            final int size, final int[] nums) {

        if (tempSubset.size() == size) {
            subsets.add(new ArrayList<>(tempSubset));
            return;
        }
        for (int i = start; i < nums.length; i++) {
            // 注意
            if (i != 0 && nums[i] == nums[i - 1] && !hasVisited[i - 1]) {
                continue;
            }
            tempSubset.add(nums[i]);
            hasVisited[i] = true;
            backtracking(i + 1, tempSubset, subsets, hasVisited, size, nums);
            hasVisited[i] = false;
            tempSubset.remove(tempSubset.size() - 1);
        }
    }

}
```

### 4.1. 岛屿数量(853)

>给你一个由 `'1'`（陆地）和 `'0'`（水）组成的的二维网格，请你计算网格中岛屿的数量。
>
>```
>输入：grid = [
>  ["1","1","1","1","0"],
>  ["1","1","0","1","0"],
>  ["1","1","0","0","0"],
>  ["0","0","0","0","0"]
>]
>输出：1
>```
>
>```
>输入：grid = [
>  ["1","1","0","0","0"],
>  ["1","1","0","0","0"],
>  ["0","0","1","0","0"],
>  ["0","0","0","1","1"]
>]
>输出：3
>```

```
class Solution {
    // 像这种二维， 定义四个全局方向
    private int m, n;
    private int[][] direaction = {{0,1},{0,-1},{1,0},{-1,0}};
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        m = grid.length;
        n = grid[0].length;
        int islandsNum = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 不等于0，才能dfs
                if (grid[i][j] != '0') {
                    dfs(grid, i, j);
                    // 成功一次，加一次
                    islandsNum++;
                }
            }
        }
        return islandsNum;
    }

    private void dfs(char[][] grid, int i, int j) {
        // 失败条件
        if (i < 0 || i >= m || j < 0 || j >=n || grid[i][j] == '0') {
            return;
        }
        // 标记，已走过
        grid[i][j] = '0';
        for (int[] d : direaction) {
            dfs(grid, i + d[0], j + d[1]);
        }
    }
}
```

### 4.2. 岛屿的最大面积(648)

>给定一个包含了一些 `0` 和 `1` 的非空二维数组 `grid` 。
>
>找到给定的二维数组中最大的岛屿面积。(如果没有岛屿，则返回面积为 `0` 。)
>
>```
>[[0,0,1,0,0,0,0,1,0,0,0,0,0],
> [0,0,0,0,0,0,0,1,1,1,0,0,0],
> [0,1,1,0,1,0,0,0,0,0,0,0,0],
> [0,1,0,0,1,1,0,0,1,0,1,0,0],
> [0,1,0,0,1,1,0,0,1,1,1,0,0],
> [0,0,0,0,0,0,0,0,0,0,1,0,0],
> [0,0,0,0,0,0,0,1,1,1,0,0,0],
> [0,0,0,0,0,0,0,1,1,0,0,0,0]]
>```

```
class Solution {
    private int m, n;
    private int[][] direaction = {{0,1},{0,-1},{1,0},{-1,0}};
    public int maxAreaOfIsland(int[][] grid) {
        if(grid == null || grid.length == 0) return 0;
        m = grid.length;
        n = grid[0].length;
        int maxArea = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 这里可以加个条件，不等于0进来
                // 每次取最大面积
                maxArea = Math.max(maxArea, dfs(grid, i, j));
            }
        }
        return maxArea;
    }   
    private int dfs(int[][] grid, int r, int c) {
        // 失败条件
        if (r < 0 || r >= m || c < 0 || c >= n || grid[r][c] == 0) {
            return 0;
        }
        // 标记走过
        grid[r][c] = 0;
        // 开始dfs
        int area = 1;
        for (int[] d : direaction) {
            area += dfs(grid, r + d[0], c + d[1]);
        }
        return area;
    }
}
```

### 5. 电话号码的字母组合(1085)

>给定一个仅包含数字 `2-9` 的字符串，返回所有它能表示的字母组合。答案可以按 **任意顺序** 返回。
>
>```
>输入：digits = "23"
>输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
>```
>
>```
>输入：digits = "2"
>输出：["a","b","c"]
>```
>
>在电话键盘上的对应顺序为：
>
>2-abc，3-def，4-ghi，5-jkl，6-mno，7-pqrs，8-tuv，9-wxyz。

```
class Solution {
    private static final String[] KEYS = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    public List<String> letterCombinations(String digits) {
        List<String> combinnations = new ArrayList<>();
        if (digits == null || digits.length() == 0) return combinnations;
        doCombination(new StringBuilder(), combinnations, digits);
        return combinnations;
    }
    
    private void doCombination(StringBuilder prefix, List<String> combinnations, final String digits) {
        if (prefix.length() == digits.length()) {
            combinnations.add(prefix.toString());
            return;
        }
        int curDigits = digits.charAt(prefix.length()) - '0';
        String letters = KEYS[curDigits];
        for (char c : letters.toCharArray()) {
            prefix.append(c);
            doCombination(prefix, combinnations, digits);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
}
```

### 6. 被围绕的区域(328)

>给你一个 m x n 的矩阵 board ，由若干字符 'X' 和 'O' ，找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。
>
>```
>输入：board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
>输出：[["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
>解释：被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。 任何不在边界上，或不与边界上的 'O' 相连的 'O' 最终都会被填充为 'X'。如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。
>```

```
class Solution {
    private int[][] direction = {{0,1},{0,-1},{1,0},{-1,0}};
    private int m, n;
    public void solve(char[][] board) {
        if (board == null || board.length == 0) return;
        m = board.length;
        n = board[0].length;
        // 边缘两列
        for (int i = 0; i < m; i++) {
            dfs(board, i, 0);
            dfs(board, i, n - 1);
        }
        // 上下两行
        for (int i = 0; i < n; i++) {
            dfs(board, 0, i);
            dfs(board, m - 1, i);
        }

        // 再走全部走一遍
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 遇见T标记O
                if (board[i][j] == 'T') {
                    board[i][j] = 'O';
                    // 遇见O标记X
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }

    private void dfs(char[][] board, int r, int c) {
        if(r < 0 || r >= m || c < 0 || c >= n || board[r][c] != 'O') {
            return;
        }
        board[r][c] = 'T';
        for (int[] d : direction) {
            dfs(board, r + d[0], c + d[1]);
        }
    }
}
```

### 7. 求 [1,n] 这 n 个数字的排列组合有多少个

条件：相邻的两个数字的绝对值不能等于1. 例如： 4 [2, 4, 1, 3] [3, 1, 4, 2]

```
private static List<List<Integer>> ret = new ArrayList<>();
private static int n = 0;
public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    n = sc.nextInt();
    boolean[] marked = new boolean[n + 1];
    dfs(0, marked, new ArrayList<>());
    for (List<Integer> list : ret) {
        System.out.println(list.toString());
    }
}

private static void dfs(int x, boolean[] marked, ArrayList<Integer> list) {
    if (list.size() == n) {
        ret.add(new ArrayList<>(list));
        return;
    }
    // 开始遍历
    for (int i = 1; i <= n; i++) {
        // 关键是这个条件
        if (!marked[i] && (list.isEmpty() || Math.abs(list.get(list.size() - 1) - i) != 1)){
            list.add(i);
            marked[i] = true;
            dfs(x+1, marked, list);
            list.remove(list.size() - 1);
            marked[i] = false;
        }
    }
}
```

### 51. N皇后

>n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
>
>给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
>
>皇后攻击范围为横线，竖线，斜线。
>
>```
>输入：n = 4
>输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
>解释：如上图所示，4 皇后问题存在两个不同的解法。
>```
>
>```
>输入：n = 1
>输出：[["Q"]]
>```

```
class Solution {
    boolean[] col = null;
    boolean[] left = null;
    boolean[] right = null;
    List<List<String>> res = new ArrayList<>();
    public List<List<String>> solveNQueens(int n) {
        col = new boolean[n];
        left = new boolean[2 * n - 1];
        right = new boolean[2 * n - 1];
        char[][] board = new char[n][n];
        dfs(board, 0, n);
        return res;
    }

    public void dfs(char[][] board, int r, int n) {
        if (r >= n) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < n; i++)
                list.add(new String(board[i]));
            res.add(list);
            return;
        }
        Arrays.fill(board[r], '.');
        for (int i = 0; i < n; i++) {
            if (!col[i] && !left[r + i] && !right[r - i + n - 1]) {
                board[r][i] = 'Q';
                col[i] = true;
                left[r + i] = true;
                right[r - i + n - 1] = true;
                dfs(board, r + 1, n);
                board[r][i] = '.';
                col[i] = false;
                left[r + i] = false;
                right[r - i + n - 1] = false;
            }
        }
    }
}
```

### 329. 矩阵中的最长递增路径

>给定一个 `m x n` 整数矩阵 `matrix` ，找出其中 **最长递增路径** 的长度。
>
>```
>输入：matrix = [[9,9,4],[6,6,8],[2,1,1]]
>输出：4 
>解释：最长递增路径为 [1, 2, 6, 9]。
>```
>
>```
>输入：matrix = [[3,4,5],[3,2,6],[2,2,1]]
>输出：4 
>解释：最长递增路径是 [3, 4, 5, 6]。注意不允许在对角线方向上移动。
>```

```
class Solution {
    int[][] next = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    int rows = 0, cols = 0;
    boolean[][] marked = null;
    int[][] res = null;
    public int longestIncreasingPath(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return 0;
        int max = 0;
        this.rows = matrix.length;
        this.cols = matrix[0].length;
        this.marked = new boolean[rows][cols];
        this.res = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                max = Math.max(max, dfs(matrix, i, j));
            }
        }
        return max;
    }

    public int dfs(int[][] matrix, int x, int y) {
        if(res[x][y] != 0) {
            return res[x][y];
        }
        marked[x][y] = true;
        int len = 0;
        for (int[] n : next) {
            int nx = x + n[0];
            int ny = y + n[1];
            if (nx >= 0 && nx < rows && ny >= 0 && ny < cols && matrix[x][y] < matrix[nx][ny] && !marked[nx][ny])
                len = Math.max(len, dfs(matrix, nx, ny));
        }
        marked[x][y] = false;
        res[x][y] = len + 1;
        return res[x][y];
    }
}
```

### 93. 复原IP地址

>给定一个只包含数字的字符串，用以表示一个 IP 地址，返回所有可能从 s 获得的 有效 IP 地址 。你可以按任何顺序返回答案。
>
>有效 IP 地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），整数之间用 '.' 分隔。
>
>```
>输入：s = "25525511135"
>输出：["255.255.11.135","255.255.111.35"]
>```
>
>```
>输入：s = "010010"
>输出：["0.10.0.10","0.100.1.0"]
>```
>
>```
>输入：s = "101023"
>输出：["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
>```

```
class Solution {
    List<String> addresses = new ArrayList<>();
    public List<String> restoreIpAddresses(String s) {
        
        StringBuilder sb = new StringBuilder();
        dfs(0, sb, s);
        return addresses;
    }
    private void dfs(int k, StringBuilder sb, String s) {
        if (k == 4 || s.length() == 0) {
            if (k == 4 && s.length() == 0) {
                addresses.add(sb.toString());
            }
            return;
        }
        for (int i = 0; i < s.length() && i <= 2; i++) {
            if (i != 0 && s.charAt(0) == '0') break;
            String part = s.substring(0, i + 1);
            if (Integer.valueOf(part) <= 255) {
                if (sb.length() != 0) {
                    part = "." + part;
                }
                sb.append(part);
                dfs(k + 1, sb, s.substring(i + 1));
                sb.delete(sb.length() - part.length(),sb.length());
            }
        }
    }
}
```