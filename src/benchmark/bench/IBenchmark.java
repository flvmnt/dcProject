package benchmark.bench;

public interface IBenchmark {
    void initialize(Object... params);
    void warmup();
    void run();
    void clean();
    void cancel();
}
