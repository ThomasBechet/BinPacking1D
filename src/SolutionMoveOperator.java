import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SolutionMoveOperator implements SolutionOperator {
    private int sourceBinIndex;
    private int targetBinIndex;
    private int movedItemIndex;

    @Override
    public void apply(Solution solution, Random rng) {
        // Initialize values
        Bin sourceBin = null;
        Bin targetBin = null;
        Item movedItem = null;

        // Generate possible target bins
        List<Bin> targetBins = solution.getBins().stream()
                .filter(b -> b.getRemainingLength() > 0).collect(Collectors.toList());

        while(targetBins.size() > 0 && (movedItem == null)) {
            // Get a target bin with available space
            this.targetBinIndex = rng.nextInt(targetBins.size());
            targetBin = targetBins.remove(this.targetBinIndex);
            int remainingLength = targetBin.getRemainingLength();

            // Generate possible source bins
            List<Bin> sourceBins = new ArrayList<>(solution.getBins());
            sourceBins.remove(targetBin);

            while (sourceBins.size() > 0 && (movedItem == null)) {
                // Get a source bin
                this.sourceBinIndex = rng.nextInt(sourceBins.size());
                sourceBin = sourceBins.remove(this.sourceBinIndex);

                // Find a valid item
                List<Item> items = new ArrayList<>(sourceBin.getItems());
                while (items.size() > 0) {
                    int index = rng.nextInt(items.size());
                    Item item = items.remove(index);
                    if (item.getValue() <= remainingLength) {
                        movedItem = item;
                        this.movedItemIndex = index;
                        break;
                    }
                }
            }
        }

        // Check operator failure
        if (movedItem == null) {
            // Create a new empty bin
            Bin bin = new Bin(solution.getBins().get(0).getCapacity());
            this.targetBinIndex = solution.getBins().size();

            // Pick random bin
            this.sourceBinIndex = rng.nextInt(solution.getBins().size());
            sourceBin = solution.getBins().get(this.sourceBinIndex);

            // Pick random item
            this.movedItemIndex = rng.nextInt(sourceBin.getItems().size());
            movedItem = sourceBin.getItems().get(this.movedItemIndex);

            // Move the item
            bin.add(movedItem);
            sourceBin.remove(movedItem);

            // Check source bin empty
            if (sourceBin.size() == 0) {
                solution.remove(sourceBin);
            }
            solution.add(bin);
            return;
        }

        // Move item
        sourceBin.remove(movedItem);
        if (sourceBin.size() == 0) {
            solution.remove(sourceBin);
        }
        targetBin.add(movedItem);
    }

    @Override
    public boolean equals(SolutionOperator operator) {
        if (operator instanceof SolutionMoveOperator) {
            SolutionMoveOperator op = (SolutionMoveOperator)operator;
            boolean sameBins0 = (this.sourceBinIndex == op.sourceBinIndex) && (this.targetBinIndex == op.targetBinIndex);
            boolean sameBins1 = (this.sourceBinIndex == op.targetBinIndex) && (this.targetBinIndex == op.sourceBinIndex);
            return (sameBins0 || sameBins1) && this.movedItemIndex == op.movedItemIndex;
        }
        return false;
    }
}