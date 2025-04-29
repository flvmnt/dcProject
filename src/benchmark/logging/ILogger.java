package benchmark.logging;

import benchmark.logging.TimeUnit;

public interface ILogger {
    void write(long value);
    void write(String text);
    void write(Object... values);
    void close();

    /** Convert & write a time value in the given unit. */
    default void writeTime(String label, long nanos, TimeUnit unit) {
        double v = unit.fromNanos(nanos);
        write(label, String.format("%.3f", v), unit.name());
    }
}
