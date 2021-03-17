import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start");

        DataSet dataSet = null;
        try {
            dataSet = new DataSet("binpack1d_00.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert(dataSet != null);


        List<Solution> solutions = new ArrayList<>();

        solutions.add(SolutionBuilder.buildDefault(dataSet));

        solutions.add(SolutionBuilder.buildNeighbours(solutions.get(0)));
    }
}
