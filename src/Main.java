import java.io.PrintWriter;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {

        /* LP SOLVE DEMO */

//        DataSet dataSet = new DataSet("binpack1d_simple.txt");
//        Solution lpSolution = SolutionBuilder.findBestSolutionLpSolve(dataSet);
//        for (Bin bin : lpSolution.getBins()) {
//            for (Item i : bin.getItems()) {
//                System.out.print(i.getValue() + " ");
//            }
//            System.out.println();
//        }

        /* First fit DEMO */

//        DataSet dataSet = new DataSet("binpack1d_00.txt");
//        Random rng = new Random(12345);
//        System.out.println("Lower bound: " + dataSet.getLowerBound() + " Upper bound: " + dataSet.getUpperBound());
//        System.out.println("First fit random => " + SolutionBuilder.firstFitRandom(dataSet, rng));
//        System.out.println("First fit sorted => " + SolutionBuilder.firstFitSorted(dataSet));

//        recorder.println("fitness: " + firstFitSortedFitness + " " + firstFitFitness);
//        recorder.println(300 * 5 + " " + 1500);

        /* Recuit Simulé DEMO */

//        DataSet dataSet = new DataSet("binpack1d_05.txt");
//        Random rng = new Random(12345);
//        Solution initialSolution = SolutionBuilder.oneItemPerBin(dataSet);
//        SolutionBuilder.RecuitSimuleParameters RSParameters = new SolutionBuilder.RecuitSimuleParameters();
//        RSParameters.initialTemperature        = 10000.0f;
//        RSParameters.temperatureDecreaseFactor = 0.99f;
//        RSParameters.temperatureChangeCount    = 300;
//        RSParameters.iterationPerTemperature   = 5;
//        RSParameters.neighbourCount            = 100;
//        RSParameters.recorder                  = null; // peut être mis à null pour ignorer l'enregistrement
//        System.out.println("Recuit simulé => " + SolutionBuilder.findBestSolutionRecuitSimule(initialSolution, rng, RSParameters));

        /* Recherche Tabou DEMO */

//        DataSet dataSet = new DataSet("binpack1d_05.txt");
//        Random rng = new Random(12345);
//        Solution initialSolution = SolutionBuilder.oneItemPerBin(dataSet);
//        SolutionBuilder.TabuSearchParameters TSParameters = new SolutionBuilder.TabuSearchParameters();
//        TSParameters.iterationCount = 1500;
//        TSParameters.queueLength    = 5;
//        TSParameters.neighbourCount = 100;
//        TSParameters.recorder = null; // peut être mis à null pour ignorer l'enregistrement
//        System.out.println("Tabu search => " + SolutionBuilder.findBestSolutionTabuSearch(initialSolution, rng, TSParameters));

        /* recorder (pour les scripts python) */

//        (new PrintWriter("record.txt")).close();
//        PrintWriter recorder = new PrintWriter("record.txt");
        // Ajouter algo avec recorder activé dans les paramètres
//        recorder.flush();
    }
}