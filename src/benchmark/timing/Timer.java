package benchmark.timing;

public class Timer implements ITimer {
    private long startTime;
    private long elapsed;
    private boolean running;
    private boolean started;

    public Timer() {
        this.elapsed = 0;
        this.running = false;
        this.started = false;
    }

    @Override
    public void start() {
        // Always reset
        elapsed = 0;
        startTime = System.nanoTime();
        running = true;
        started = true;
    }

    @Override
    public long pause() {
        if (!started || !running) {
            throw new IllegalStateException("Cannot pause: timer is not running");
        }
        long now = System.nanoTime();
        long delta = now - startTime;
        elapsed += delta;
        running = false;
        return delta;
    }

    @Override
    public void resume() {
        if (!started) {
            throw new IllegalStateException("Cannot resume: timer has not been started");
        }
        if (running) {
            throw new IllegalStateException("Cannot resume: timer is already running");
        }
        startTime = System.nanoTime();
        running = true;
    }

    @Override
    public long stop() {
        if (!started) {
            throw new IllegalStateException("Cannot stop: timer has not been started");
        }
        if (running) {
            long now = System.nanoTime();
            elapsed += now - startTime;
            running = false;
        }
        started = false;
        return elapsed;
    }
}
