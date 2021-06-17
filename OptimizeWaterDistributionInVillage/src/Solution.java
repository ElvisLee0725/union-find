import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

// There are n houses in a village. We want to supply water for all the houses by building wells and laying pipes.
// For each house i, we can either build a well inside it directly with cost wells[i - 1] (note the -1 due to 0-indexing), or pipe in water from another well to it. The costs to lay pipes between houses are given by the array pipes, where each pipes[j] = [house1j, house2j, costj] represents the cost to connect house1j and house2j together using a pipe. Connections are bidirectional.
// Return the minimum total cost to supply water to all houses.

// Union Find + Min Heap
// Use a min heap to store all cost of building wellls and pipes in ascending order
// P.S. We can modify the wells array into a pipe between a psudo house 0 and current house (0, cur house, cost)
// Modify the union method to return a boolean. The union operations values weight of union
// Pop everything out from minHeap with lowest cost first. When there is a union, add the cost to result.
// Return the final result
// Time: O((M + N)log(M + N) while M is the length of pipes, N is the number of houses. Space: O(M + N) for the heap
class Solution {
    public static void main(String[] args) {
        int [] wells = {1, 2, 2};
        int [][] pipes = {{1,2,1},{2,3,1}};
        System.out.println(new Solution().minCostToSupplyWater(3, wells, pipes));
    }
    int [] roots;
    int [] weight;
    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        roots = new int[n+1];
        for(int i = 0; i < roots.length; i++) {
            roots[i] = i;
        }
        weight = new int[n+1];
        Arrays.fill(weight, 1);

        PriorityQueue<int []> minHeap = new PriorityQueue(new Comparator<int []>(){
            public int compare(int [] a, int [] b) {
                return a[2] - b[2];
            }
        });

        // Modify wells into {0, i, cost}
        for(int i = 0; i < wells.length; i++) {
            minHeap.offer(new int[]{0, i + 1, wells[i]});
        }

        // Add pipes into heap
        for(int [] pipe : pipes) {
            minHeap.offer(pipe);
        }

        int count = 0;
        int cost = 0;
        while(!minHeap.isEmpty()) {
            int [] cur = minHeap.poll();
            if(union(cur[0], cur[1])) {
                cost += cur[2];
                count++;
                // Optional: Since this is a minimum spanning tree. If we do n times union (n + 1 virtual house) then we can stop
                if(count == n) {
                    break;
                }
            }
        }
        return cost;
    }

    private int find(int x) {
        if(roots[x] == x) {
            return x;
        }
        roots[x] = find(roots[x]);
        return roots[x];
    }

    private boolean union(int i, int j) {
        int r1 = find(i);
        int r2 = find(j);

        // Case 1: They are in the same union already
        if(r1 == r2) {
            return false;
        }

        // Case 2: Union r2 to r1 if r1 is bigger
        if(weight[r1] > weight[r2]) {
            roots[r2] = r1;
            weight[r1] += weight[r2];
        }
        // Case 3: Union r1 to r2 if r2 is bigger
        else {
            roots[r1] = r2;
            weight[r2] += weight[r1];
        }
        return true;
    }
}