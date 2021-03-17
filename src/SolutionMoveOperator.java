import java.util.Random;

public class SolutionMoveOperator implements SolutionOperator {
    private int binSourceIndex;
    private int binTargetIndex;
    private Item item;

    @Override
    public void apply(Solution solution, Random rng) {
        int maxAvailableLength = 0;
        for (Bin bin : solution.getBins()) {
            int availableLength = bin.getRemainingLength();
            maxAvailableLength = availableLength > maxAvailableLength ? availableLength : maxAvailableLength;
        }



        this.binSourceIndex = rng.nextInt(solution.getBins().size());
    }

    @Override
    public boolean equals(SolutionOperator operator) {
        if (operator instanceof SolutionMoveOperator) {
            return true;
        }
        return false;
    }
}
