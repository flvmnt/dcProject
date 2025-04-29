package benchmark.logging;

public interface ILogger {
    /** Print a single long. */
    void write(long value);
    /** Print a single String. */
    void write(String text);
    /** Print multiple values separated by spaces. */
    void write(Object... values);
    /** Close any resources (no-op for console). */
    void close();
}
