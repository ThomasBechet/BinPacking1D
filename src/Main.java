import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            DataSet dataSet = null;
            dataSet = new DataSet("binpack1d_00.txt");
            Solution solution = Solution.firstFitDecreasing(dataSet);

            System.out.println("Bin capacity: " + dataSet.getBinCapacity());
            System.out.println("Item count: " + dataSet.getItems().size());
            System.out.println("Lower Bound: " + dataSet.getLowerBound());
            System.out.println("Upper Bound: " + dataSet.getUpperBound());
            System.out.println("Fitness: " + solution.fitness());
            int binIndex = 1;
            for (Bin bin : solution.getBins()) {
                System.out.print("(" + binIndex + ") ");
                for (Item item : bin.getItems()) {
                    System.out.print(item.getValue() + " ");
                }
                System.out.println();
                binIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
