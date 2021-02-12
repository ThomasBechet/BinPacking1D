import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start");

        List<Item> items = new ArrayList<>();
        items.add(new Item(8));
        items.add(new Item(7));
        items.add(new Item(3));
        items.add(new Item(2));

        DataSet dataSet = new DataSet(items, 10);


        List<Solution> solutions = new ArrayList<>();

        solutions.add(SolutionBuilder.buildDefault(dataSet));

        solutions.add(SolutionBuilder.buildNeighbours(solutions.get(0)));
    }
}
