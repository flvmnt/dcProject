package bench.hdd;

import benchmark.bench.IBenchmark;

public class HDDWriteSpeed implements IBenchmark {

    private String mode = "fs";
    private boolean clean = true;

    @Override
    public void initialize(Object... params) {
        // Optionally accept mode and clean from params
        if (params.length >= 1) this.mode = (String) params[0];
        if (params.length >= 2) this.clean = (Boolean) params[1];
    }

    @Override
    public void warmup() {
        // Could simulate disk warmup if desired
    }

    @Override
    public void run() {
        FileWriter writer = new FileWriter();

        String prefix = "/Users/flv/Desktop/";
        String suffix = ".dat";
        int minIndex = 0;
        int maxIndex = 8;

        if (mode.equals("fs")) {
            long fileSize = 512L * 1024 * 1024; // 512MB
            writer.streamWriteFixedSize(prefix, suffix, minIndex, maxIndex, fileSize, clean);
        } else if (mode.equals("fb")) {
            int bufferSize = 2 * 1024; // 2KB
            writer.streamWriteFixedBuffer(prefix, suffix, minIndex, maxIndex, bufferSize, clean);
        } else {
            throw new IllegalArgumentException("Unknown mode: " + mode);
        }
    }

    @Override
    public void clean() {
        // Not needed
    }

    @Override
    public void cancel() {
        // Not needed
    }
}
