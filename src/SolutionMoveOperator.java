import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SolutionMoveOperator implements SolutionOperator {
    private Bin sourceBin;
    private Bin targetBin;
    private Item movedItem;

    @Override
    public void apply(Solution solution, Random rng) {
        // Initialize values
        this.sourceBin = null;
        this.targetBin = null;
        this.movedItem = null;

        // Generate possible target bins
        List<Bin> targetBins = solution.getBins().stream()
                .filter(b -> b.getRemainingLength() > 0).collect(Collectors.toList());

        while(targetBins.size() > 0 && (this.movedItem == null)) {
            // Get a target bin with available space
            this.targetBin = targetBins.remove(rng.nextInt(targetBins.size()));
            int remainingLength = this.targetBin.getRemainingLength();

            // Generate possible source bins
            List<Bin> sourceBins = new ArrayList<>(solution.getBins());
            sourceBins.remove(this.targetBin);

            while (sourceBins.size() > 0 && (this.movedItem == null)) {
                // Get a source bin
                this.sourceBin = sourceBins.remove(rng.nextInt(sourceBins.size()));

                // Find a valid item
                List<Item> items = new ArrayList<>(this.sourceBin.getItems());
                while (items.size() > 0) {
                    Item item = items.remove(rng.nextInt(items.size()));
                    if (item.getValue() <= remainingLength) {
                        this.movedItem = item;
                        break;
                    }
                }
            }
        }

        // Check operator failure
        if (this.movedItem == null) {
            // Create a new empty bin
            Bin bin = new Bin(solution.getBins().get(0).getCapacity());

            // Pick random bin
            Bin sourceBin = solution.getBins().get(rng.nextInt(solution.getBins().size()));

            // Pick random item
            Item item = sourceBin.getItems().get(rng.nextInt(sourceBin.getItems().size()));

            // Move the item
            bin.add(item);
            sourceBin.remove(item);

            // Check source bin empty
            if (sourceBin.size() == 0) {
                solution.remove(sourceBin);
            }
            solution.add(bin);
            return;
        }

        // Move item
        this.sourceBin.remove(this.movedItem);
        if (this.sourceBin.size() == 0) {
            solution.remove(this.sourceBin);
        }
        this.targetBin.add(this.movedItem);
    }

    @Override
    public boolean equals(SolutionOperator operator) {
        if (operator instanceof SolutionMoveOperator) {
            SolutionMoveOperator op = (SolutionMoveOperator)operator;
            return this.sourceBin == op.sourceBin &&
                    this.targetBin == op.targetBin &&
                    this.movedItem == op.movedItem;
        }
        return false;
    }
}