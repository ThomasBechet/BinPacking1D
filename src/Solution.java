import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Solution {
    private final List<Bin> bins;
    private SolutionOperator lastOperator;

    public Solution() {
        this.bins = new ArrayList<>();
    }

    public Solution(Solution solution) {
        this.bins = new ArrayList<>(solution.bins.size());
        for (Bin bin : solution.bins) {
            this.bins.add(new Bin(bin));
        }
        this.lastOperator = solution.lastOperator;
    }

    @Override
    public String toString() {
        return "fitness: " + fitness() + " bin count: " + this.bins.size();
    }

    public SolutionOperator getLastOperator() {
        return this.lastOperator;
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
                SolutionOperator operator;
                if (rng.nextInt() % 2 == 0) {
                    operator = new SolutionMoveOperator();
                } else {
                    operator = new SolutionSwapOperator();
                }

                operator.apply(solution, rng);
                solution.lastOperator = operator;

                neighbours.add(solution);
                validSolutions++;
            } catch (UnsupportedOperationException e) {

            }
        }

//        System.out.println(validSolutions + "/" + count + " operators");

        return neighbours;
    }
}