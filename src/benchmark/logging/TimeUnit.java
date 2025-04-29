package benchmark.logging;

/** Supported units for converting nanoseconds. */
public enum TimeUnit {
    NANO   (1L),
    MICRO  (1_000L),
    MILLI  (1_000_000L),
    SECOND (1_000_000_000L);

    private final long factor;

    TimeUnit(long nanosPerUnit) {
        this.factor = nanosPerUnit;
    }

    /** Convert nanoseconds to this unit (returns a double). */
    public double fromNanos(long nanos) {
        return nanos / (double)factor;
    }
}
