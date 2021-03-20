import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        try {
            DataSet dataSet = new DataSet("binpack1d_02.txt");

            // Solution solution = SolutionBuilder.firstFitSorted(dataSet);
            // Solution solution = SolutionBuilder.oneItemPerBin(dataSet);
            Solution solution = SolutionBuilder.firstFit(dataSet);

            System.out.println("Bin capacity: " + dataSet.getBinCapacity());
            System.out.println("Item count: " + dataSet.getItems().size());
            System.out.println("Lower Bound: " + dataSet.getLowerBound());
            System.out.println("Upper Bound: " + dataSet.getUpperBound());
            System.out.println("Fitness: " + solution.fitness());
            /*int binIndex = 1;
            for (Bin bin : solution.getBins()) {
                System.out.print("(" + binIndex + ") ");
                for (Item item : bin.getItems()) {
                    System.out.print(item.getValue() + " ");
                }
                System.out.println();
                binIndex++;
            }*/

            Random rng = new Random(12345);

            int iteration = 100;
            int neighbourCount = 100;
            Solution bestSolution = solution;
            for (int i = 0; i < iteration; i++) {
                List<Solution> neighbours = bestSolution.generateNeighbours(neighbourCount, rng);
                for (Solution neighbour : neighbours) {
                    if (neighbour.fitness() < bestSolution.fitness()) {
                        bestSolution = neighbour;
                    }
                }
            }
            System.out.println("New Fitness: " + bestSolution.fitness());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
