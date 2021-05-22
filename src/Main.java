import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {

        (new PrintWriter("record.txt")).close();
        PrintWriter recorder = new PrintWriter("record.txt");

        DataSet dataSet = new DataSet("binpack1d_31.txt");
        Random rng = new Random(12345);
        System.out.println(dataSet.getLowerBound() + " " + dataSet.getUpperBound());
        Solution initialSolution = SolutionBuilder.oneItemPerBin(dataSet);
        int firstFitSortedFitness = SolutionBuilder.firstFitSorted(dataSet).fitness();
        int firstFitFitness = SolutionBuilder.firstFit(dataSet).fitness();
        recorder.println("fitness: " + firstFitSortedFitness + " " + firstFitFitness);
        recorder.println(200 * 15 + " " + 1000);

//        DataSet dataSet = new DataSet("binpack1d_yolo.txt");
//        System.out.println(dataSet.getLowerBound() + " " + dataSet.getUpperBound());
//        Solution firstSolution = SolutionBuilder.findBestSolutionLpSolve(dataSet);

        SolutionBuilder.RecuitSimuleParameters RSParameters = new SolutionBuilder.RecuitSimuleParameters();
        RSParameters.initialTemperature        = 1000.0f;
        RSParameters.temperatureDecreaseFactor = 0.99f;
        RSParameters.temperatureChangeCount    = 200;
        RSParameters.iterationPerTemperature   = 15;
        RSParameters.neighbourCount            = 300;
        RSParameters.recorder                  = recorder;
        SolutionBuilder.findBestSolutionRecuitSimule(initialSolution, rng, RSParameters);

        SolutionBuilder.TabuSearchParameters TSParameters = new SolutionBuilder.TabuSearchParameters();
        TSParameters.iterationCount = 1000;
        TSParameters.queueLength    = 5;
        TSParameters.neighbourCount = 100;
        TSParameters.recorder = recorder;
        SolutionBuilder.findBestSolutionTabuSearch(initialSolution, rng, TSParameters);

//        Solution firstSolution = SolutionBuilder.oneItemPerBin(dataSet);
//        System.out.println("before " + firstSolution);
//        Random rng = new Random(12345);
//        SolutionBuilder.TabuSearchParameters parameters = new SolutionBuilder.TabuSearchParameters();
//        parameters.iterationCount = 1000;
//        parameters.queueLength    = 5;
//        parameters.neighbourCount = 100;
//        parameters.recorder = recorder;
//        Solution rsSolution = SolutionBuilder.findBestSolutionTabuSearch(firstSolution, rng, parameters);
//        System.out.println("after " + rsSolution);
//        System.out.println("first fit " + SolutionBuilder.firstFit(dataSet));
//        System.out.println("first fit sorted " + SolutionBuilder.firstFitSorted(dataSet));
//        System.out.println(dataSet.getLowerBound() + " " + dataSet.getUpperBound());

//        // Solution solution = SolutionBuilder.firstFitSorted(dataSet);
//        // Solution solution = SolutionBuilder.oneItemPerBin(dataSet);
//        Solution solution = SolutionBuilder.firstFit(dataSet);
//        System.out.println("Bin capacity: " + dataSet.getBinCapacity());
//        System.out.println("Item count: " + dataSet.getItems().size());
//        System.out.println("Lower Bound: " + dataSet.getLowerBound());
//        System.out.println("Upper Bound: " + dataSet.getUpperBound());
//        System.out.println("Fitness: " + solution.fitness());
//        System.out.println("Bin count: " + solution.getBins().size());
//            /*int binIndex = 1;
//            for (Bin bin : solution.getBins()) {
//                System.out.print("(" + binIndex + ") ");
//                for (Item item : bin.getItems()) {
//                    System.out.print(item.getValue() + " ");
//                }
//                System.out.println();
//                binIndex++;
//            }*/
//        Random rng = new Random(12345);
//        int iteration = 100;
//        int neighbourCount = 100;
//        Solution bestSolution = solution;
//        for (int i = 0; i < iteration; i++) {
//            List<Solution> neighbours = bestSolution.generateNeighbours(neighbourCount, rng);
//            for (Solution neighbour : neighbours) {
//                if (neighbour.fitness() < bestSolution.fitness()) {
//                    bestSolution = neighbour;
//                }
//            }
//        }
//        System.out.println("New Fitness: " + bestSolution.fitness());
//        System.out.println("New Bin count: " + solution.getBins().size());

        recorder.flush();
    }
}