package bench.hdd;

import benchmark.bench.IBenchmark;
import java.io.File;

public class HDDRandomAccess implements IBenchmark {
    private File file;
    private final RandomAccessRW accessRW = new RandomAccessRW();

    @Override
    public void initialize(Object... params) {
        try {
            long fileSize = 1024L * 1024 * 1024; // 1 GB
            file = accessRW.initializeTempFile(fileSize);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create temp file: " + e.getMessage());
        }
    }

    @Override
    public void warmup() {
        // Not needed
    }

    @Override
    public void run() {
        // Default full test suite
        run("r", "fs", 4 * 1024);
        run("r", "ft", 4 * 1024);
        run("w", "fs", 4 * 1024);
        run("w", "ft", 4 * 1024);
    }

    public void run(Object... options) {
        if (options.length < 3)
            throw new IllegalArgumentException("Expected: mode ('r' or 'w'), type ('fs' or 'ft'), bufferSize");

        String rw = (String) options[0];
        String type = (String) options[1];
        int bufferSize = (Integer) options[2];

        int ops = 10_000;
        int timeMs = 5000;

        try {
            switch (rw + "_" + type) {
                case "r_fs" -> {
                    long ns = accessRW.randomReadFixedSize(file, bufferSize, ops);
                    double mb = ops * bufferSize / (1024.0 * 1024.0);
                    double sec = ns / 1e9;
                    System.out.printf("READ-FS: %.2f MB read in %.3f s → %.2f MB/s%n", mb, sec, mb / sec);
                }
                case "r_ft" -> {
                    int jumps = accessRW.randomReadFixedTime(file, bufferSize, timeMs);
                    double mb = jumps * bufferSize / (1024.0 * 1024.0);
                    System.out.printf("READ-FT: %d jumps in %d ms → %.2f MB/s%n", jumps, timeMs, mb / (timeMs / 1000.0));
                }
                case "w_fs" -> {
                    long ns = accessRW.randomWriteFixedSize(file, bufferSize, ops);
                    double mb = ops * bufferSize / (1024.0 * 1024.0);
                    double sec = ns / 1e9;
                    System.out.printf("WRITE-FS: %.2f MB written in %.3f s → %.2f MB/s%n", mb, sec, mb / sec);
                }
                case "w_ft" -> {
                    int jumps = accessRW.randomWriteFixedTime(file, bufferSize, timeMs);
                    double mb = jumps * bufferSize / (1024.0 * 1024.0);
                    System.out.printf("WRITE-FT: %d jumps in %d ms → %.2f MB/s%n", jumps, timeMs, mb / (timeMs / 1000.0));
                }
                default -> throw new IllegalArgumentException("Invalid mode/type combo");
            }
        } catch (Exception e) {
            throw new RuntimeException("Benchmark error: " + e.getMessage());
        }
    }

    @Override public void clean() {}
    @Override public void cancel() {}
}
