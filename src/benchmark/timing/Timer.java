package benchmark.timing;

public class Timer implements ITimer {
    private long startTime;
    private long elapsed;
    private boolean running;

    public Timer() {
        this.elapsed = 0;
        this.running = false;
    }

    @Override
    public void start() {
        elapsed = 0;
        startTime = System.nanoTime();
        running = true;
    }

    @Override
    public long stop() {
        if (running) {
            long now = System.nanoTime();
            elapsed += now - startTime;
            running = false;
        }
        return elapsed;
    }

    @Override
    public long pause() {
        if (running) {
            long now = System.nanoTime();
            long delta = now - startTime;
            elapsed += delta;
            running = false;
            return delta;
        }
        return 0;
    }

    @Override
    public void resume() {
        if (!running) {
            startTime = System.nanoTime();
            running = true;
        }
    }
}
