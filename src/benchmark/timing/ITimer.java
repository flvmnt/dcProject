package benchmark.timing;

public interface ITimer {
    /** Reset any previous time and start timing from now. */
    void start();

    /** Stop timing and return cumulative elapsed nanoseconds. */    
    long stop();
    
    // Pause timing (accumulate since last start) and return the last segment’s nanoseconds.
    long pause();

    // Resume timing after a pause.
    void resume();
}
