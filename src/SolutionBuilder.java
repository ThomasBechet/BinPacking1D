import java.util.*;

public class SolutionBuilder {

    /**
     * Generate a solution with the 'FirstFit' method.
     * @param dataSet Entry dataset.
     * @return the generated solution.
     */
    static Solution firstFit(DataSet dataSet) {
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

    /**
     * Generate a solution with the 'FirstFit' method.
     * @param dataSet Entry dataset.
     * @return the generated solution.
     */
    static Solution firstFitSorted(DataSet dataSet) {
        // Create new sorted
        DataSet sortedDataSet = new DataSet(dataSet);
        Collections.sort(sortedDataSet.getItems(), (a, b) -> {return b.getValue() - a.getValue();});

        return SolutionBuilder.firstFit(sortedDataSet);
    }

    /**
     * Generate a solution with the 'One Item per Bin' method.
     * @param dataSet Entry dataset.
     * @return the generated solution.
     */
    static Solution oneItemPerBin(DataSet dataSet) {
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
    static Solution findBestSolutionRecuitSimule(Solution solution, Random rng, RecuitSimuleParameters parameters) {
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
    static Solution findBestSolutionTabuSearch(Solution solution, Random rng, TabuSearchParameters parameters) {
        int iterationCount = parameters.iterationCount;
        int queueLength    = parameters.queueLength;
        int neighbourCount = parameters.neighbourCount;

        Solution x    = solution;       // current solution
        Solution xmax = x;              // best solution
        int fmax      = xmax.fitness(); // best fitness
        Deque<SolutionOperator> tabuQueue = new LinkedList<>();
        for (int i = 0; i < iterationCount; i++) {
            List<Solution> neighbours = x.generateNeighbours(neighbourCount, rng);
            Collections.sort(neighbours, (a, b) -> {return b.fitness() - a.fitness();});

            // find best neighbour with its operator not in the tabu queue
            for (Solution neighbour : neighbours) {
                SolutionOperator nop = neighbour.getLastOperator();
                if (!tabuQueue.stream().anyMatch((o) -> o.equals(nop))) {

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