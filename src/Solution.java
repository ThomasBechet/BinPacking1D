import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {
    private List<Bin> bins;

    public Solution(List<Bin> bins) {
        this.bins = bins;
    }

    public List<Bin> getBins() {
        return bins;
    }

    public int getBinCapacity() {
        return this.bins.get(0).getCapacity();
    }

    public int fitness() {
        return this.bins.size();
    }
}
