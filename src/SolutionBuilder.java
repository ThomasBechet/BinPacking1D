import lpsolve.LpSolve;
import lpsolve.LpSolveException;

import java.util.*;

public class SolutionBuilder {

    /**
     * Generate a solution with the 'FirstFit' method.
     * @param dataSet Entry dataset.
     * @return the generated solution.
     */
    public static Solution firstFit(DataSet dataSet) {
        Solution solution = new Solution();

        // Add items to the solution
        for (Item item : dataSet.getItems()) {
            assert!(item.getValue() <= dataSet.getBinCapacity());
            boolean binFound = false;
            for (Bin bin : solution.getBins()) {
                if (item.getValue() <= bin.getRemainingLength()) {
                    bin.add(item);
                    binFound = true;
                    break; // Next item
                }
            }

            if (!binFound) {
                Bin newBin = new Bin(dataSet.getBinCapacity());
                newBin.add(item);
                solution.add(newBin);
            }
        }

        return solution;
    }

    public static Solution findBestSolutionLpSolve(DataSet dataSet) {
        int nbItem = dataSet.getItems().size();
        int nbBin = nbItem;
        int totalVariable = nbItem * nbBin + nbBin;
        try {
            // create a problem with 4 variables and 0 constraints
            LpSolve solver = LpSolve.makeLp(nbItem + nbBin, totalVariable);

            // add constraints

            // bins constraints
            for (int j = 0; j < nbBin; j++) {
                String constraint = "";
                for(int i = 0; i < nbItem * nbBin; i++) {
                    if (i % nbItem == j) {
                        constraint += dataSet.getItems().get(i / nbItem).getValue() + " ";
                    } else {
                        constraint += "0 ";
                    }
                }
                for (int i = 0; i < nbBin; i++) {
                    if (i == j) {
                        constraint += "-" + dataSet.getBinCapacity() + " ";
                    } else {
                        constraint += "0 ";
                    }
                }
                System.out.println(constraint);
                solver.strAddConstraint(constraint, LpSolve.LE, 0);
            }

            System.out.println();

            // items constraints
            for (int i = 0; i < nbItem; i++) {
                String constraint = "";

                for(int j = 0; j < nbItem * nbBin; j++) {
                    if (j / nbItem == i) {
                        constraint += "1 ";
                    } else {
                        constraint += "0 ";
                    }
                }

                for(int j = 0; j < nbBin; j++) {
                    constraint += "0 ";
                }

                System.out.println(constraint);
                solver.strAddConstraint(constraint, LpSolve.EQ, 1);
            }

            System.out.println();

            // set objective function
            solver.setMinim();
            String constraint = "";
            for(int i = 0; i < nbItem * nbBin; i++) {
                constraint += "0 ";
            }
            for(int i = nbItem * nbBin; i < totalVariable; i++) {
                constraint += "1 ";
            }
            System.out.println(constraint);
            solver.strSetObjFn(constraint);

            // set all variables types to binary
            for(int i = 0; i < nbItem * nbBin + nbBin; i++) {
                solver.setBinary(1 + i,true);
            }

            // solve the problem
            solver.solve();

            // print solution
            System.out.println("Value of objective function: " + (int)solver.getObjective());
            double[] var = solver.getPtrVariables();
            for (int i = 0; i < var.length; i++) {
                System.out.println("Value of var[" + i + "] = " + var[i]);
            }

            // delete the problem and free memory
            solver.deleteLp();
        }
        catch (LpSolveException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Generate a solution with the 'FirstFit' method.
     * @param dataSet Entry dataset.
     * @return the generated solution.
     */
    public static Solution firstFitSorted(DataSet dataSet) {
        // Create new sorted
        DataSet sortedDataSet = new DataSet(dataSet);
        sortedDataSet.getItems().sort((a, b) -> b.getValue() - a.getValue());

        return SolutionBuilder.firstFit(sortedDataSet);
    }

    /**
     * Generate a solution with the 'One Item per Bin' method.
     * @param dataSet Entry dataset.
     * @return the generated solution.
     */
    public static Solution oneItemPerBin(DataSet dataSet) {
        Solution solution = new Solution();

        for (Item item : dataSet.getItems()) {
            Bin bin = new Bin(dataSet.getBinCapacity());
            bin.add(item);
            solution.add(bin);
        }

        return solution;
    }

    public static class RecuitSimuleParameters {
        public float initialTemperature;
        public float temperatureDecreaseFactor;
        public int temperatureChangeCount;
        public int iterationPerTemperature;
        public int neighbourCount;
    }

    /**
     * Find a better solution using the 'Recuit Simulé' algorithm.
     * @param solution Initial solution
     * @param rng Pseudo random generator
     * @param parameters algorithm parameters
     * @return the generated solution.
     */
    public static Solution findBestSolutionRecuitSimule(Solution solution, Random rng, RecuitSimuleParameters parameters) {
        float tk = parameters.initialTemperature;        // initial temperature
        float u  = parameters.temperatureDecreaseFactor; // temperature decrease u < 1
        int n1   = parameters.temperatureChangeCount;    // n1 changes of temperature
        int n2   = parameters.iterationPerTemperature;   // n2 moves at temperature tk
        int nc   = parameters.neighbourCount;            // number of generated neighbours

        Solution x    = solution;       // current solution
        Solution xmax = x;              // best solution
        int fmax      = xmax.fitness(); // best fitness

        for (int k = 0; k < n1; k++) {
            for (int l = 1; l < n2; l++) {
                List<Solution> neighbours = x.generateNeighbours(nc, rng); // Generate neighbours
                Solution y = neighbours.get(rng.nextInt(neighbours.size())); // Pick one of the neighbours
                int fx = x.fitness();
                int fy = y.fitness();
                float df = fx - fy; // Compute the delta fitness
                if (df <= 0) { // Better fitness than current solution
                    x = y; // Choose this solution as next solution
                    if (fy > fmax) { // Save the solution if it is the best one
                        xmax = y;
                        fmax = fy;
                    }
                } else { // Not better fitness, use the metropolis rule
                    float p = rng.nextFloat(); // Random float between 0.0 and 1.0
                    if (p <= Math.exp(-df / tk)) {
                        x = y;
                    }
                }
            }
            tk *= u; // Decrease temperature
            System.out.println(fmax + " " + tk);
        }
        return xmax;
    }

    public static class TabuSearchParameters {
        public int iterationCount;
        public int queueLength;
        public int neighbourCount;
    }

    /**
     * Find a better solution using the 'Tabu Search' algorithm.
     * @param solution Initial solution
     * @param rng Pseudo random generator
     * @param parameters algorithm parameters
     * @return the generated solution.
     */
    public static Solution findBestSolutionTabuSearch(Solution solution, Random rng, TabuSearchParameters parameters) {
        int iterationCount = parameters.iterationCount;
        int queueLength    = parameters.queueLength;
        int neighbourCount = parameters.neighbourCount;

        Solution x    = solution;       // current solution
        Solution xmax = x;              // best solution
        int fmax      = xmax.fitness(); // best fitness
        Deque<SolutionOperator> tabuQueue = new LinkedList<>();
        for (int i = 0; i < iterationCount; i++) {
            List<Solution> neighbours = x.generateNeighbours(neighbourCount, rng);
            neighbours.sort((a, b) -> b.fitness() - a.fitness());

            // find best neighbour with its operator not in the tabu queue
            for (Solution neighbour : neighbours) {
                SolutionOperator nop = neighbour.getLastOperator();
                if (tabuQueue.stream().noneMatch((o) -> o.equals(nop))) {

                    // add operator to the tabu queue
                    tabuQueue.addFirst(nop);

                    // update the tabu queue
                    if (tabuQueue.size() > queueLength) {
                        tabuQueue.removeLast();
                    }

                    // save the neighbour if it is the best
                    x = neighbour;
                    int fx = x.fitness();
                    if (fx > fmax) {
                        xmax = x;
                        fmax = fx;
                    }

                    break;
                }
            }
        }

        return xmax;
    }
}