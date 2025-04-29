package benchmark.bench;

public class DemoBenchmark implements IBenchmark {
    private long sleepMillis;
    private volatile boolean cancelled;

    @Override
    public void initialize(Object... params) {
        sleepMillis = (Long) params[0];
        cancelled = false;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(sleepMillis);
        } catch (InterruptedException ignored) {}
    }

    @Override
    public void clean() {
        // nothing to clean
    }

    @Override
    public void cancel() {
        cancelled = true;
    }
}
