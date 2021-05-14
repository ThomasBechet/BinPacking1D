import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SolutionSwapOperator implements SolutionOperator {
    private Bin firstBin;
    private Bin secondBin;
    private Item firstItem;
    private Item secondItem;

    @Override
    public void apply(Solution solution, Random rng) {
        // Initialize values
        this.firstBin = null;
        this.secondBin = null;
        this.firstItem = null;
        this.secondItem = null;

        boolean found = false;

        // Create first bin list
        List<Bin> firstBins = new ArrayList<>(solution.getBins());

        while (firstBins.size() > 0 && !found) {
            // Pick first bin
            this.firstBin = firstBins.remove(rng.nextInt(firstBins.size()));

            // Create second bin list
            List<Bin> secondBins = new ArrayList<>(solution.getBins());
            secondBins.remove(this.firstBin);

            while (secondBins.size() > 0 && !found) {
                // Pick second bin
                this.secondBin = secondBins.remove(rng.nextInt(secondBins.size()));

                // Create first item list
                List<Item> firstItems = new ArrayList<>(this.firstBin.getItems());

                while (firstItems.size() > 0 && !found) {
                    // Pick first item
                    this.firstItem = firstItems.remove(rng.nextInt(firstItems.size()));

                    // Create second item list
                    List<Item> secondItems = new ArrayList<>(this.secondBin.getItems());

                    while (secondItems.size() > 0 && !found) {
                        // Pick second item
                        this.secondItem = secondItems.remove(rng.nextInt(secondItems.size()));

                        // Check solution
                        if (this.secondItem.getValue() > (this.firstBin.getRemainingLength() + this.firstItem.getValue()) ||
                            this.firstItem.getValue() > (this.secondBin.getRemainingLength() + this.secondItem.getValue())) {
                            continue;
                        }

                        // Found
                        found = true;
                    }
                }
            }


        }

        // Check operator failure
        if (!found) {
            throw new UnsupportedOperationException();
        }

        // Move item
        this.firstBin.remove(this.firstItem);
        if (this.firstBin.size() == 0) {
            solution.remove(this.firstBin);
        }
        this.secondBin.remove(this.secondItem);
        if (this.secondBin.size() == 0) {
            solution.remove(this.secondBin);
        }
        this.firstBin.add(this.secondItem);
        this.secondBin.add(this.firstItem);
    }

    @Override
    public boolean equals(SolutionOperator operator) {
        if (operator instanceof SolutionSwapOperator) {
            SolutionSwapOperator op = (SolutionSwapOperator)operator;

            boolean sameBins0 = (this.firstBin == op.firstBin) && (this.secondBin == op.secondBin);
            boolean sameBins1 = (this.firstBin == op.secondBin) && (this.secondBin == op.firstBin);
            boolean sameItem0 = (this.firstItem == op.firstItem) && (this.secondItem == op.secondItem);
            boolean sameItem1 = (this.firstItem == op.secondItem) && (this.secondItem == op.firstItem);

            return (sameBins0 || sameBins1) && (sameItem0 || sameItem1);
        }
        return false;
    }
}