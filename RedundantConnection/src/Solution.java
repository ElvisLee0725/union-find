// In this problem, a tree is an undirected graph that is connected and has no cycles.
// You are given a graph that started as a tree with n nodes labeled from 1 to n, with one additional edge added. The added edge has two different vertices chosen from 1 to n, and was not an edge that already existed. The graph is represented as an array edges of length n where edges[i] = [ai, bi] indicates that there is an edge between nodes ai and bi in the graph.
// Return an edge that can be removed so that the resulting graph is a tree of n nodes. If there are multiple answers, return the answer that occurs last in the input.

// Union Find
// Create an array and initialize with its index value
// Iterate edges, call find function to find root for both vertices
// If they are different, merge roots[root2] to root1
// Else, that means they are already connected so it's a redundant edge. Add both vertices to result
// Return the result after the iteration
// Time: O(n), Space: O(n)
class Solution {
    public static void main(String[] args) {
        int [][] e = {{1,4},{3,4},{1,3},{1,2},{4,5}};
        int [] res = new Solution().findRedundantConnection(e);
        System.out.println(res[0] + ", " + res[1]);
    }
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        int [] roots = new int[n+1];
        for(int i = 0; i < roots.length; i++) {
            roots[i] = i;
        }

        int [] res = new int[2];
        for(int [] edge : edges) {
            int v1 = edge[0];
            int v2 = edge[1];
            int root1 = find(roots, v1);
            int root2 = find(roots, v2);

            if(root1 != root2) {
                roots[root2] = root1;
            }
            else {
                res[0] = v1;
                res[1] = v2;
            }
        }
        return res;
    }

    private int find(int [] roots, int x) {
        if(roots[x] == x) {
            return x;
        }
        roots[x] = find(roots, roots[x]);
        return roots[x];
    }
}
