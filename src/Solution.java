import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Solution {
    private List<Bin> bins;

    public Solution() {
        this.bins = new ArrayList<>();
    }

    public Solution(Solution solution) {
        this.bins = new ArrayList<>(solution.bins.size());
        for (Bin bin : solution.bins) {
            this.bins.add(new Bin(bin));
        }
    }

    public List<Bin> getBins() {
        return bins;
    }

    public int fitness() {
        int sum = 0;
        for (Bin bin : this.bins) {
            int binSum = 0;
            for (Item item : bin.getItems()) {
                binSum += item.getValue();
            }
            sum += (binSum * binSum);
        }
        return sum;
    }

    public void add(Bin bin) {
        this.bins.add(bin);
    }

    public void remove(Bin bin) {
        this.bins.remove(bin);
    }

    public List<Solution> generateNeighbours(int count, Random rng) {
        List<Solution> neighbours = new ArrayList<>(count);

        int validSolutions = 0;

        for (int i = 0; i < count; i++) {
            // Generate solution and random operator
            Solution solution = new Solution(this);

            try {
                if (rng.nextInt() % 2 == 0) {
                    (new SolutionMoveOperator()).apply(solution, rng);
                } else {
                    (new SolutionSwapOperator()).apply(solution, rng);
                }

                //(new SolutionMoveOperator()).apply(solution, rng);

                neighbours.add(solution);
                validSolutions++;
            } catch (UnsupportedOperationException e) {

            }
        }

        System.out.println(validSolutions + "/" + count + " operators");

        return neighbours;
    }
}