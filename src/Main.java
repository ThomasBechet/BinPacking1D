import java.io.PrintWriter;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {

        /* LP SOLVE DEMO */

        DataSet dataSet = new DataSet("binpack1d_simple.txt");
        Solution lpSolution = SolutionBuilder.findBestSolutionLpSolve(dataSet);
        for (Bin bin : lpSolution.getBins()) {
            for (Item i : bin.getItems()) {
                System.out.print(i.getValue() + " ");
            }
            System.out.println();
        }

//        (new PrintWriter("record.txt")).close();
//        PrintWriter recorder = new PrintWriter("record.txt");

//        DataSet dataSet = new DataSet("binpack1d_05.txt");
//        Random rng = new Random(12345);
//        System.out.println(dataSet.getLowerBound() + " " + dataSet.getUpperBound());
//        Solution initialSolution = SolutionBuilder.oneItemPerBin(dataSet);
//        int firstFitSortedFitness = SolutionBuilder.firstFitSorted(dataSet).fitness();
//        int firstFitFitness = SolutionBuilder.firstFitRandom(dataSet, rng).fitness();
//        System.out.println(firstFitSortedFitness);
//        System.out.println(firstFitFitness);
//        recorder.println("fitness: " + firstFitSortedFitness + " " + firstFitFitness);
//        recorder.println(300 * 5 + " " + 1500);

        /* Recuit Simulé DEMO */

//        SolutionBuilder.RecuitSimuleParameters RSParameters = new SolutionBuilder.RecuitSimuleParameters();
//        RSParameters.initialTemperature        = 10000.0f;
//        RSParameters.temperatureDecreaseFactor = 0.99f;
//        RSParameters.temperatureChangeCount    = 300;
//        RSParameters.iterationPerTemperature   = 5;
//        RSParameters.neighbourCount            = 100;
//        RSParameters.recorder                  = recorder; // peut être mis à null pour ignorer l'enregistrement
//        System.out.println("Recuit simulé: " + SolutionBuilder.findBestSolutionRecuitSimule(initialSolution, rng, RSParameters).fitness());

        /* Recherche Tabou DEMO */

//        SolutionBuilder.TabuSearchParameters TSParameters = new SolutionBuilder.TabuSearchParameters();
//        TSParameters.iterationCount = 1500;
//        TSParameters.queueLength    = 5;
//        TSParameters.neighbourCount = 100;
//        TSParameters.recorder = recorder; // peut être mis à null pour ignorer l'enregistrement
//        System.out.println("Tabu search: " + SolutionBuilder.findBestSolutionTabuSearch(initialSolution, rng, TSParameters).fitness());

//        recorder.flush();
    }
}