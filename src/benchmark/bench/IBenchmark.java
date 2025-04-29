package benchmark.bench;

public interface IBenchmark {
    /** Prepare the benchmark (e.g. generate data or open files). */
    void initialize(Object... params);
    /** The actual code whose performance is measured. */
    void run();
    /** Cleanup after run (e.g. free memory). */
    void clean();
    /** Signal cancellation (should cause run() to return as soon as possible). */
    void cancel();
}
