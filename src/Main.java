//import lpsolve.LpSolve;
//import lpsolve.LpSolveException;

import lpsolve.LpSolve;
import lpsolve.LpSolveException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        try {
            DataSet dataSet = new DataSet("binpack1d_yolo.txt");
            System.out.println(dataSet.getLowerBound() + " " + dataSet.getUpperBound());
            Solution firstSolution = SolutionBuilder.findBestSolutionLpSolve(dataSet);


        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            DataSet dataSet = new DataSet("binpack1d_13.txt");
//            Solution firstSolution = SolutionBuilder.oneItemPerBin(dataSet);
//            System.out.println("before " + firstSolution);
//            Random rng = new Random(12345);
//            SolutionBuilder.RecuitSimuleParameters parameters = new SolutionBuilder.RecuitSimuleParameters();
//            parameters.initialTemperature        = 200.0f;
//            parameters.temperatureDecreaseFactor = 0.98f;
//            parameters.temperatureChangeCount    = 50;
//            parameters.iterationPerTemperature   = 15;
//            parameters.neighbourCount            = 300;
//            Solution rsSolution = SolutionBuilder.findBestSolutionRecuitSimule(firstSolution, rng, parameters);
//            System.out.println("after " + rsSolution);
//            System.out.println("first fit " + SolutionBuilder.firstFit(dataSet));
//            System.out.println("first fit sorted " + SolutionBuilder.firstFitSorted(dataSet));
//            System.out.println(dataSet.getLowerBound() + " " + dataSet.getUpperBound());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
//            DataSet dataSet = new DataSet("binpack1d_13.txt");
//            Solution firstSolution = SolutionBuilder.oneItemPerBin(dataSet);
//            System.out.println("before " + firstSolution);
//            Random rng = new Random(12345);
//            SolutionBuilder.TabuSearchParameters parameters = new SolutionBuilder.TabuSearchParameters();
//            parameters.iterationCount = 1000;
//            parameters.queueLength    = 5;
//            parameters.neighbourCount = 100;
//            Solution rsSolution = SolutionBuilder.findBestSolutionTabuSearch(firstSolution, rng, parameters);
//            System.out.println("after " + rsSolution);
//            System.out.println("first fit " + SolutionBuilder.firstFit(dataSet));
//            System.out.println("first fit sorted " + SolutionBuilder.firstFitSorted(dataSet));
//            System.out.println(dataSet.getLowerBound() + " " + dataSet.getUpperBound());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        System.out.println("Optimal solution with recuit + tabou + some magic thinks -> 252");

//        try {
//            DataSet dataSet = new DataSet("binpack1d_02.txt");
//
//            // Solution solution = SolutionBuilder.firstFitSorted(dataSet);
//            // Solution solution = SolutionBuilder.oneItemPerBin(dataSet);
//            Solution solution = SolutionBuilder.firstFit(dataSet);
//
//            System.out.println("Bin capacity: " + dataSet.getBinCapacity());
//            System.out.println("Item count: " + dataSet.getItems().size());
//            System.out.println("Lower Bound: " + dataSet.getLowerBound());
//            System.out.println("Upper Bound: " + dataSet.getUpperBound());
//            System.out.println("Fitness: " + solution.fitness());
//            System.out.println("Bin count: " + solution.getBins().size());
//            /*int binIndex = 1;
//            for (Bin bin : solution.getBins()) {
//                System.out.print("(" + binIndex + ") ");
//                for (Item item : bin.getItems()) {
//                    System.out.print(item.getValue() + " ");
//                }
//                System.out.println();
//                binIndex++;
//            }*/
//
//            Random rng = new Random(12345);
//
//            int iteration = 100;
//            int neighbourCount = 100;
//            Solution bestSolution = solution;
//            for (int i = 0; i < iteration; i++) {
//                List<Solution> neighbours = bestSolution.generateNeighbours(neighbourCount, rng);
//                for (Solution neighbour : neighbours) {
//                    if (neighbour.fitness() < bestSolution.fitness()) {
//                        bestSolution = neighbour;
//                    }
//                }
//            }
//            System.out.println("New Fitness: " + bestSolution.fitness());
//            System.out.println("New Bin count: " + solution.getBins().size());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}