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

    static Solution firstFitDecreasing(DataSet dataSet) {
        List<Bin> bins = new ArrayList<>();

        // Create sorted item list
        List<Item> sorted = new ArrayList<>(dataSet.getItems());
        Collections.sort(sorted, (a, b) -> {return b.getValue() - a.getValue();});

        // Add items to the solution
        for (Item item : sorted) {
            assert!(item.getValue() <= dataSet.getBinCapacity());
            boolean binFound = false;
            for (Bin bin : bins) {
                if (item.getValue() <= bin.getRemainingLength()) {
                    bin.add(item);
                    binFound = true;
                    break; // Next item
                }
            }

            if (!binFound) {
                Bin newBin = new Bin(dataSet.getBinCapacity());
                newBin.add(item);
                bins.add(newBin);
            }
        }

        return new Solution(bins);
    }
}
