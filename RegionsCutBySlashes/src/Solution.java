// Union Find:
// Imagine each square can be divided into 4 pieces, North(0), West(1), East(2), South(3)
// Create a array of length 4 * N * N to represent the total pieces in that graph
// For each grid, check:
// 1. If it's not a \, we can union 0, 1 and 2, 3
// 2. If it's not a /, we can union 0, 2 and 1, 3
// Then, we need to check if we can union current grid with its top, bottom, left and right
// Union with top: curNorth and curNorth - (4 * N) + 3
// Union with bottom: curSouth and curNorth + (4 * N)
// Union with left: curWest and (curNorth - 4) + 2
// Union with right: curEast and (curNorth + 4) + 1
// Finally, check the roots array, if the current node's root is itself (It is the root), count++. Return count at the end
// Time: O(n^2), Space: O(n^2)
class Solution {
    public static void main(String[] args) {
        System.out.println(new Solution().regionsBySlashes(new String []{" /","/ "}));
    }
    int [] roots;
    public int regionsBySlashes(String[] grid) {
        int N = grid.length;
        roots = new int[4 * N * N];
        for(int i = 0; i < roots.length; i++) {
            roots[i] = i;
        }

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                int curNorth = 4 * (i * N + j);
                // Backslash needs to be escaped
                if(grid[i].charAt(j) != '\\') {
                    union(curNorth, curNorth + 1);
                    union(curNorth + 2, curNorth + 3);
                }

                if(grid[i].charAt(j) != '/') {
                    union(curNorth, curNorth + 2);
                    union(curNorth + 1, curNorth + 3);
                }

                // Union with top
                if(i > 0) {
                    union(curNorth, curNorth - (4 * N) + 3);
                }

                // Union with bottom
                if(i + 1 < N) {
                    union(curNorth + 3, curNorth + (4 * N));
                }

                // Union with left
                if(j > 0) {
                    union(curNorth + 1, (curNorth - 4) + 2);
                }

                // Union with right
                if(j + 1 < N) {
                    union(curNorth + 2, (curNorth + 4) + 1);
                }
            }
        }

        int count = 0;
        for(int i = 0; i < 4 * N * N; i++) {
            if(find(i) == i) {
                count++;
            }
        }
        return count;
    }

    private int find(int n) {
        if(roots[n] == n) {
            return n;
        }

        roots[n] = find(roots[n]);
        return roots[n];
    }

    // Use find() to get the root of x and y!
    // Equals to int r1 = find(x); int r2 = find(y); roots[r2] = r1;
    private void union(int x, int y) {
        roots[find(x)] = find(y);
    }
}
