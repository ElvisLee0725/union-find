import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    // Create Adjacency List for items, Ex. <Item Name, List of adjacent items>
    // For each item in the adjacency map, call dfs to find all its union.
    // Update the largest union or if size is tie, compare the first item of sorted result
    public static void main(String[] args) {

    }

    public List<String> LargestItemAssociation(List<PairString> itemAssociation) {
        HashMap<String, List<String>> map = new HashMap();
        for(PairString p : itemAssociation) {
            map.computeIfAbsent(p.first, x -> new ArrayList()).add(p.second);
            map.computeIfAbset(p.second, x -> new ArrayList()).add(p.first);
        }

        int max = 0;
        List<String> res = new ArrayList();
        HashSet<String> hs = new HashSet();
        for(String cur : map.keySet()) {
            List<String> tmp = new ArrayList();
            if(!hs.contains(cur)) {
                tmp = dfs(cur, map, hs);
            }

            if(tmp.size() > max) {
                res = new ArrayList(tmp);
                max = res.size();
            }
            else if(tmp.size() == max) {
                Collections.sort(res);
                Collections.sort(tmp);

                if(res.get(0).compareTo(tmp.get(0)) > 0) {
                    res = new ArrayList(tmp);
                }
            }
        }
        return res;
    }
}
