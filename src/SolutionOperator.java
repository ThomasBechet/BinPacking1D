import java.util.Random;

public interface SolutionOperator {
    void apply(Solution solution, Random rng);
    boolean equals(SolutionOperator operator);
}
