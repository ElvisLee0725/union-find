// In a social group, there are N people, with unique integer ids from 0 to N-1.
//
//We have a list of logs, where each logs[i] = [timestamp, id_A, id_B] contains a non-negative integer timestamp, and the ids of two different people.
//
//Each log represents the time in which two different people became friends.  Friendship is symmetric: if A is friends with B, then B is friends with A.
//
//Let's say that person A is acquainted with person B if A is friends with B, or A is a friend of someone acquainted with B.
//
//Return the earliest time for which every person became acquainted with every other person. Return -1 if there is no such earliest time.


// Sort the logs matrix by logs[0] (Timestamp)
// Union: Use an array of length N to represent people. roots[i] means person i's root id. By default, roots[i] equals i. Ex. [0, 1, 2]
// Iterate the sorted logs matrix. At each log, if the root of friend1 != root of friend2, that means they are not acquainted. Need to union them and reduce the counter by 1
// If the counter reaches 1, that means every one is united, return the current timestamp
// Else, we do nothing
// Finally, return -1 since we can't make everyone become friends
// Time: O(nlogn) while n is the rows of logs, Space: O(N)

import java.util.Arrays;
import java.util.Comparator;

class Solution {
    public static void main(String[] args) {
        int [][] logs = {{20190101,0,1},{20190104,3,4},{20190107,2,3},{20190211,1,5},{20190224,2,4},{20190301,0,3},{20190312,1,2},{20190322,4,5}};
        System.out.print(new Solution().earliestAcq(logs,6));
    }

    public int earliestAcq(int[][] logs, int N) {
        int [] roots = new int[N];
        for(int i = 0; i < roots.length; i++) {
            roots[i] = i;
        }

        Arrays.sort(logs, new Comparator<int []>(){
            public int compare(int [] a, int [] b) {
                return a[0] < b[0] ? -1 : 1;
            }
        });

        int counter = N;
        for(int [] cur : logs) {
            int root1 = findRoot(cur[1], roots);
            int root2 = findRoot(cur[2], roots);
            if(root1 != root2) {
                roots[root2] = root1;
                counter--;
                if(counter == 1) {
                    return cur[0];
                }
            }
        }
        return -1;
    }

    private int findRoot(int cur, int [] roots) {
        if(cur == roots[cur]) {
            return cur;
        }

        roots[cur] = findRoot(roots[cur], roots);
        return roots[cur];
    }
}