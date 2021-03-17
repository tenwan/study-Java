---
title: BFS相关算法题
excerpt: 对BFS相关算法题的总结
categories:
- java算法
tags:
- 习题
---

### 完全平方数

>给定正整数 *n*，找到若干个完全平方数（比如 `1, 4, 9, 16, ...`）使得它们的和等于 *n*。让其组成和的完全平方数的个数最少。
>
>```
>输入：n = 12
>输出：3 
>解释：12 = 4 + 4 + 4
>输入：n = 13
>输出：2
>解释：13 = 4 + 9
>```

```
public int numSquares(int n) {
    List<Integer> squares = generateSquares(n);
    Queue<Integer> queue = new LinkedList<>();
    boolean[] marked = new boolean[n + 1]; // 其实感觉是为了剪枝,也可以set标记
    queue.add(n);
    marked[n] = true; // 
    int level = 0; // 
    while (!queue.isEmpty()) {
        int size = queue.size();
        level++;
        while (size-- > 0) {
            int cur = queue.poll();
            for (int s : squares) {
                int next = cur - s;
                if (next < 0) {
                    break;
                }
                if (next == 0) {
                    return level;
                }
                if (marked[next]) {
                    continue; // 剪
                }
                marked[next] = true;
                queue.add(next);
            }
        }
    }
    return n;
}

/**
 * 生成小于 n 的平方数序列
 * @return 1,4,9,...
 */
private List<Integer> generateSquares(int n) {
    List<Integer> squares = new ArrayList<>();
    int square = 1;
    int diff = 3;
    while (square <= n) {
        squares.add(square);
        square += diff;
        diff += 2;
    }
    return squares;
}
```

### 2. 二叉树的最小深度

>给定一个二叉树，找出其最小深度。
>
>最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
>
>```
>输入：root = [3,9,20,null,null,15,7]
>输出：2
>输入：root = [2,null,3,null,4,null,5,null,6]
>输出：5
>```

```
class Solution {
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        // 树不需要标记哦
        queue.add(root);
        int depth = 1; // 根根
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode node = queue.poll();
                if (node.left == null && node.right == null)
                    return depth;
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
            depth++;
        }
        return depth;
    }
}
```

### 3. 打开转盘锁

>你有一个带有四个圆形拨轮的转盘锁。每个拨轮都有10个数字： '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 。每个拨轮可以自由旋转：例如把 '9' 变为  '0'，'0' 变为 '9' 。每次旋转都只能旋转一个拨轮的一位数字。
>
>锁的初始数字为 '0000' ，一个代表四个拨轮的数字的字符串。
>
>列表 deadends 包含了一组死亡数字，一旦拨轮的数字和列表里的任何一个元素相同，这个锁将会被永久锁定，无法再被旋转。
>
>字符串 target 代表可以解锁的数字，你需要给出最小的旋转次数，如果无论如何不能解锁，返回 -1。
>
>```
>输入：deadends = ["0201","0101","0102","1212","2002"], target = "0202"
>输出：6
>解释：
>可能的移动序列为 "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202"。
>注意 "0000" -> "0001" -> "0002" -> "0102" -> "0202" 这样的序列是不能解锁的，
>因为当拨动到 "0102" 时这个锁就会被锁定。
>```
>
>```
>输入: deadends = ["8888"], target = "0009"
>输出：1
>解释：
>把最后一位反向旋转一次即可 "0000" -> "0009"。
>```
>
>```
>输入: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], target = "8888"
>输出：-1
>解释：
>无法旋转到目标数字且不被锁定。
>```

```
class Solution {
    public int openLock(String[] deadends, String target) {
        // 这里将dead和marked放在一起
        Set<String> dead = new HashSet<>();
        for (String s : deadends)
            dead.add(s);
        // queue
        Queue<String> queue = new LinkedList<>();
        Set<String> marked = new HashSet<>();
        queue.add("0000");
        marked.add("0000");
        int cnt = 0;
        // luoji
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                String cur = queue.poll();
                if (dead.contains(cur))
                    continue;
                if (cur.equals(target))
                    return cnt;
                for (int i = 0; i < 4; i++) {
                    String up = plusOne(cur, i);
                    if (!marked.contains(up)) {
                        queue.add(up);
                        marked.add(up);
                    }
                    String down = minusOne(cur, i);
                    if (!marked.contains(down)) {
                        queue.add(down);
                        marked.add(down);
                    }
                }
            }
            cnt++;
        }
        return -1;
    }

    public String plusOne(String s, int j) {
        char[] ch = s.toCharArray();
        if (ch[j] == '9')
            ch[j] = '0';
        else
            ch[j] += 1;
        return new String(ch);
    } 

    public String minusOne(String s, int j) {
        char[] ch = s.toCharArray();
        if (ch[j] == '0')
            ch[j] = '9';
        else
            ch[j] -= 1;
        return new String(ch);
    }
}
```

### 4. 地图分析

>你现在手里有一份大小为 N x N 的 网格 grid，上面的每个 单元格 都用 0 和 1 标记好了。其中 0 代表海洋，1 代表陆地，请你找出一个海洋单元格，这个海洋单元格到离它最近的陆地单元格的距离是最大的。
>
>我们这里说的距离是「曼哈顿距离」（ Manhattan Distance）：(x0, y0) 和 (x1, y1) 这两个单元格之间的距离是 |x0 - x1| + |y0 - y1| 。
>
>如果网格上只有陆地或者海洋，请返回 -1。
>
>```
>输入：[[1,0,1],[0,0,0],[1,0,1]]
>输出：2
>解释： 
>海洋单元格 (1, 1) 和所有陆地单元格之间的距离都达到最大，最大距离为 2。
>即最中间那个方格。
>```
>
>```
>输入：[[1,0,0],[0,0,0],[0,0,0]]
>输出：4
>解释： 
>海洋单元格 (2, 2) 和所有陆地单元格之间的距离都达到最大，最大距离为 4。
>即最右下角的方格。
>```

```
class Solution {
    public int maxDistance(int[][] grid) {
        int[][] dir = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        Queue<Pair<Integer, Integer>> q = new LinkedList<>();
        int m = grid.length, n = grid[0].length;
        // 先把所有的陆地都入队
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1)
                    q.add(new Pair<>(i, j));
            }
        }

        // 判断是否都是陆地 或者没有陆地
        if (q.size() == m * n || q.isEmpty())
            return -1;
        // 从各个陆地开始，一圈一圈的遍历海洋，最后遍历到的海洋就是离陆地最远的海洋。

        int step = 0;
        Pair<Integer, Integer> p = null;
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                p = q.poll();
                int x = p.getKey(), y = p.getValue();
                // 取出队列的元素，将其四周的海洋入队。
                for (int[] d : dir) {
                    int newX = x + d[0];
                    int newY = y + d[1];
                    if (newX < 0 || newX >= m || newY < 0 || newY >= n || grid[newX][newY] != 0) {
                        continue;
                    }
                    grid[newX][newY] = 1; // 标记
                    q.add(new Pair<>(newX, newY));
                }
            }
            if (q.size() > 0)
                step++;
        }
        return step;
    }
}
public int maxDistance(int[][] grid) {
    int[][] dir = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    Queue<Pair<Integer, Integer>> q = new LinkedList<>();
    int m = grid.length, n = grid[0].length;
    // 先把所有的陆地都入队
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == 1)
                q.add(new Pair<>(i, j));
        }
    }

    // 判断是否都是陆地 或者没有陆地
    if (q.size() == m + n || q.isEmpty())
        return -1;
    // 从各个陆地开始，一圈一圈的遍历海洋，最后遍历到的海洋就是离陆地最远的海洋。

    Pair<Integer, Integer> p = null;
    while (!q.isEmpty()) {
        p = q.poll();
        int x = p.getKey(), y = p.getValue();
        // 取出队列的元素，将其四周的海洋入队。
        for (int[] d : dir) {
            int newX = x + d[0];
            int newY = y + d[1];
            if (newX < 0 || newX >= m || newY < 0 || newY >= n || grid[newX][newY] != 0) {
                continue;
            }
            grid[newX][newY] = grid[x][y] + 1; // 省略了标记， 要不然要加标记并且加个变量
            q.add(new Pair<>(newX, newY));
        }
    }
    return grid[p.getKey()][p.getValue()] - 1;
}
```

### 5. 腐烂的橘子

>在给定的网格中，每个单元格可以有以下三个值之一：
>
>值 0 代表空单元格；
>值 1 代表新鲜橘子；
>值 2 代表腐烂的橘子。
>每分钟，任何与腐烂的橘子（在 4 个正方向上）相邻的新鲜橘子都会腐烂。
>
>返回直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1。
>
>```
>输入：[[2,1,1],[1,1,0],[0,1,1]]
>输出：4
>
>输入：[[2,1,1],[0,1,1],[1,0,1]]
>输出：-1
>解释：左下角的橘子（第 2 行， 第 0 列）永远不会腐烂，因为腐烂只会发生在 4 个正向上
>```

```
class Solution {
    public int orangesRotting(int[][] grid) {
        // 俺就不判断了，直接上
        int[][] dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Queue<Pair<Integer, Integer>> q = new LinkedList<>();
        int m = grid.length, n = grid[0].length;
        int cnt = 0; // 表示新鲜的橘子
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1)
                    cnt++; // 新鲜橘子计数
                else if (grid[i][j] == 2)
                    q.add(new Pair<>(i, j)); // 腐烂橘子的坐标
            }
        }
        if (cnt == 0 || q.size() == m * n)
            return 0;
        int step = 0; // 轮数
        while (cnt > 0 && !q.isEmpty()){
            int size = q.size();
            while (size-- > 0) {
                Pair<Integer, Integer> p = q.poll();
                int x = p.getKey(), y = p.getValue();
                for (int[] d : dir) {
                    int newX = x + d[0];
                    int newY = y + d[1];
                    if (newX < 0 || newX >= m || newY < 0 || newY >= n) {
                        continue;
                    }
                    if (grid[newX][newY] == 1) {
                        grid[newX][newY] = 2;
                        q.add(new Pair<>(newX, newY));
                        cnt--;
                    }
                }
            }
            step++;
        }
        return cnt > 0 ? -1 : step;
    }
}
```

### 6. 被围绕的区域

>给你一个 `m x n` 的矩阵 `board` ，由若干字符 `'X'` 和 `'O'` ，找到所有被 `'X'` 围绕的区域，并将这些区域里所有的 `'O'` 用 `'X'` 填充。
>
>```
>输入：board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
>输出：[["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
>解释：被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。 任何不在边界上，或不与边界上的 'O' 相连的 'O' 最终都会被填充为 'X'。如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。
>```
>
>```
>输入：board = [["X"]]
>输出：[["X"]]
>```

```
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public void solve(char[][] board) {
        if (board == null || board.length == 0)
            return;
        int[][] dir = {{0,1},{0,-1},{1,0},{-1,0}};
        int m = board.length, n = board[0].length;
        Queue<Pair<Integer, Integer>> q = new LinkedList<>();
        // 找到边缘的O
        // 边缘两列
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') {
                q.add(new Pair<>(i, 0));
                board[i][0] = 'T';
            }
            if (board[i][n - 1] == 'O') {
                q.add(new Pair<>(i, n - 1));
                board[i][n - 1] = 'T';
            }
        }
        // 上下两列
        for (int i = 0; i < n; i++) {
            if (board[0][i] == 'O') {
                q.add(new Pair<>(0, i));
                board[0][i] = 'T';
            }
            if (board[m - 1][i] == 'O') {
                q.add(new Pair<>(m - 1, i));
                board[m - 1][i] = 'T';
            }
        }

        // bfs 搜索
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                Pair<Integer, Integer> p = q.poll();
                int x = p.getKey(), y = p.getValue();
                for (int[] d : dir) {
                    int nx = x + d[0];
                    int ny = y + d[1];
                    if (nx < 0 || nx >= m || ny < 0 || ny >= n)
                        continue;
                    if (board[nx][ny] == 'O'){
                        q.add(new Pair<>(nx, ny));
                        board[nx][ny] = 'T';
                    }
                }
            }
        }
        // 标记
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
}
```

### 7. 零钱兑换

>给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
>
>```
>输入：coins = [1, 2, 5], amount = 11
>输出：3 
>解释：11 = 5 + 5 + 1
>```
>
>```
>输入：coins = [2], amount = 3
>输出：-1
>```
>
>```
>输入：coins = [1], amount = 0
>输出：0
>```
>
>```
>输入：coins = [1], amount = 2
>输出：2
>```

```
import java.util.*;

public class Solution {
    public int coinChange(int[] coins, int amount) {
        if (amount == 0)
            return 0;
         // bfs
        Queue<Integer> q = new LinkedList<>();
        Set<Integer> marked = new HashSet<>();
        q.add(amount);
        marked.add(amount);
        Arrays.sort(coins);
        int cnt = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            cnt++;
            while (size-- > 0) {
                int cur = q.poll();
                for (int coin : coins) {
                    int next = cur - coin;
                    if (next < 0)
                        break;
                    if (next == 0)
                        return cnt;
                    if (marked.contains(next))
                        continue;
                    q.add(next);
                    marked.add(next);
                }
            }
        }
        return -1;
    }
}
```

### 8. 岛屿数量

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
import java.util.LinkedList;
import java.util.Queue;

public class Solution {

    int[][] dir = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
    int m, n;
    public int numIslands(char[][] grid) {
        this.m = grid.length;
        if (m == 0)
            return 0;
        this.n = grid[0].length;
        boolean[][] marked = new boolean[m][n];
        int cnt = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!marked[i][j] && grid[i][j] == '1') {
                    cnt++;
                    // bfs
                    bfs(grid, marked, i, j);
                }
            }
        }
        return cnt;
    }

    private void bfs(char[][] grid, boolean[][] marked, int i, int j) {
        Queue<Pair<Integer, Integer>> q = new LinkedList<>();
        q.add(new Pair<>(i, j));
        marked[i][j] = true;
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                Pair<Integer, Integer> p = q.poll();
                int x = p.getKey(), y = p.getValue();
                for (int[] d : dir) {
                    int nx = x + d[0];
                    int ny = y + d[1];
                    if (nx < 0 || nx >= m || ny < 0 || ny >= n || marked[nx][ny])
                        continue;
                    if (grid[nx][ny] == '1') {
                        q.add(new Pair<>(nx, ny));
                        marked[nx][ny] = true;
                    }
                }
            }
        }
    }
}
```

### 9. 单词接龙

>字典 wordList 中从单词 beginWord 和 endWord 的 转换序列 是一个按下述规格形成的序列：
>
>序列中第一个单词是 beginWord 。
>序列中最后一个单词是 endWord 。
>每次转换只能改变一个字母。
>转换过程中的中间单词必须是字典 wordList 中的单词。
>给你两个单词 beginWord 和 endWord 和一个字典 wordList ，找到从 beginWord 到 endWord 的 最短转换序列 中的 单词数目 。
>
>```
>输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
>输出：5
>解释：一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog", 返回它的长度 5。
>```
>
>```
>输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
>输出：0
>解释：endWord "cog" 不在字典中，所以无法进行转换。
>```

```
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solution {

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord))
            return 0;
        boolean[] marked = new boolean[wordList.size()]; // 可以set
        //检验是否存在beginWord，如果存在，就置为访问过了,没必要访问
        int idx = wordList.indexOf(beginWord);
        if (idx != -1)
            marked[idx] = true;
        Queue<String> q = new LinkedList<>();
        q.add(beginWord);
        int cnt = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            cnt++;
            while (size-- > 0) {
                String start = q.poll();
                for (int i = 0; i < wordList.size(); i++) {
                    // 访问过了
                    if (marked[i])
                        continue;
                    String s = wordList.get(i);
                    //不满足和当前只差一个字符不同，跳过，访问下一个
                    if (!isConnect(start, s))
                        continue;
                    //和endWord匹配上了，进行返回，因为是bfs，所以找到了直接返回就是最短的
                    if (s.equals(endWord))
                        return cnt+1;
                    q.add(s);
                    marked[i] = true;
                }
            }
        }
        return 0;
    }

    private boolean isConnect(String s1, String s2) {
        int diffCnt = 0;
        for (int i = 0; i < s1.length() && diffCnt <= 1; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                diffCnt++;
            }
        }
        return diffCnt == 1;
    }
}
```