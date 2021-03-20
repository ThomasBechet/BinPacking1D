import java.util.Collections;

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
}
