// You are given an empty 2D binary grid grid of size m x n. The grid represents a map where 0's represent water and 1's represent land. Initially, all the cells of grid are water cells (i.e., all the cells are 0's).
//
// We may perform an add land operation which turns the water at position into a land. You are given an array positions where positions[i] = [ri, ci] is the position (ri, ci) at which we should operate the ith operation.
//
// Return an array of integers answer where answer[i] is the number of islands after turning the cell (ri, ci) into a land.
//
// An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

// Union Find
// Use a roots array to represent each node's root, initialize to -1
// Iterate the positions, if the root[curIndex] equals -1, it is a new operation, do:
// Assign roots[curIndex] to curIndex and increase count by 1
// Try all four directions from curIndex. If any of those directions has root not -1 and creates a merge, count decrease by 1
// Add count to the result arraylist
// Time: O(L), Space: O(mn)

import java.util.ArrayList;
import java.util.List;

class Solution {
    int [] roots;
    int count;

    public static void main(String[] args) {
        int [][] pos = {{0,0},{0,1},{1,2},{2,1},{1,0},{0,0},{2,2},{1,2},{1,1},{0,1}};
        List<Integer> res = new Solution().numIslands2(3, 3, pos);
        System.out.println(res);
    }

    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        roots = new int[m*n];
        for(int i = 0; i < roots.length; i++) {
            roots[i] = -1;
        }
        count = 0;
        List<Integer> res = new ArrayList();
        int [] deltaX = {-1, 1, 0, 0};
        int [] deltaY = {0, 0, -1, 1};

        for(int [] pos : positions) {
            int i = pos[0];
            int j = pos[1];
            int curIndex = i * n + j;
            if(roots[curIndex] == -1) {
                roots[curIndex] = curIndex;
                count++;

                for(int k = 0; k < 4; k++) {
                    int newX = i + deltaX[k];
                    int newY = j + deltaY[k];
                    int neiIndex = newX * n + newY;
                    if(newX >= 0 && newX < m && newY >= 0 && newY < n && roots[neiIndex] != -1) {
                        boolean merged = union(curIndex, neiIndex);
                        if(merged) {
                            count--;
                        }
                    }
                }
            }
            res.add(count);
        }
        return res;
    }

    // Return true when there is a merge happens
    private boolean union(int x, int y) {
        int root1 = find(x);
        int root2 = find(y);
        if(root1 != root2) {
            // Merge current index with its neighbor
            roots[root1] = root2;
            return true;
        }
        return false;
    }

    private int find(int a) {
        if(a == roots[a]) {
            return a;
        }
        roots[a] = find(roots[a]);
        return roots[a];
    }
}
