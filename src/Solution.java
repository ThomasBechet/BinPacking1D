import java.util.ArrayList;
import java.util.List;

public class Solution {
    private List<Bin> bins = new ArrayList<>();

    public Solution(List<Bin> bins) {
        this.bins = bins;
    }

    public List<Bin> getBins() {
        return bins;
    }
}
