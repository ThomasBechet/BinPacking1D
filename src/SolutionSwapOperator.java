import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SolutionSwapOperator implements SolutionOperator {
    private int firstBinIndex;
    private int secondBinIndex;
    private int firstItemIndex;
    private int secondItemIndex;

    @Override
    public void apply(Solution solution, Random rng) {
        // Initialize values
        Bin firstBin = null;
        Bin secondBin = null;
        Item firstItem = null;
        Item secondItem = null;

        boolean found = false;

        // Create first bin list
        List<Bin> firstBins = new ArrayList<>(solution.getBins());

        while (firstBins.size() > 0 && !found) {
            // Pick first bin
            this.firstBinIndex = rng.nextInt(firstBins.size());
            firstBin = firstBins.remove(firstBinIndex);

            // Create second bin list
            List<Bin> secondBins = new ArrayList<>(solution.getBins());
            secondBins.remove(firstBin);

            while (secondBins.size() > 0 && !found) {
                // Pick second bin
                this.secondBinIndex = rng.nextInt(secondBins.size());
                secondBin = secondBins.remove(this.secondBinIndex);

                // Create first item list
                List<Item> firstItems = new ArrayList<>(firstBin.getItems());

                while (firstItems.size() > 0 && !found) {
                    // Pick first item
                    this.firstItemIndex = rng.nextInt(firstItems.size());
                    firstItem = firstItems.remove(this.firstItemIndex);

                    // Create second item list
                    List<Item> secondItems = new ArrayList<>(secondBin.getItems());

                    while (secondItems.size() > 0 && !found) {
                        // Pick second item
                        this.secondItemIndex = rng.nextInt(secondItems.size());
                        secondItem = secondItems.remove(this.secondItemIndex);

                        // Check solution
                        if (secondItem.getValue() > (firstBin.getRemainingLength() + firstItem.getValue()) ||
                            firstItem.getValue() > (secondBin.getRemainingLength() + secondItem.getValue())) {
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
        firstBin.remove(firstItem);
        if (firstBin.size() == 0) {
            solution.remove(firstBin);
        }
        secondBin.remove(secondItem);
        if (secondBin.size() == 0) {
            solution.remove(secondBin);
        }
        firstBin.add(secondItem);
        secondBin.add(firstItem);
    }

    @Override
    public boolean equals(SolutionOperator operator) {
        if (operator instanceof SolutionSwapOperator) {
            SolutionSwapOperator op = (SolutionSwapOperator)operator;

            boolean sameBins0 = (this.firstBinIndex == op.firstBinIndex) && (this.secondBinIndex == op.secondBinIndex);
            boolean sameBins1 = (this.firstBinIndex == op.secondBinIndex) && (this.secondBinIndex == op.firstBinIndex);
            boolean sameItem0 = (this.firstItemIndex == op.firstItemIndex) && (this.secondItemIndex == op.secondItemIndex);
            boolean sameItem1 = (this.firstItemIndex == op.secondItemIndex) && (this.secondItemIndex == op.firstItemIndex);

            return (sameBins0 || sameBins1) && (sameItem0 || sameItem1);
        }
        return false;
    }
}