package benchmark.bench;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriteBenchmark implements IBenchmark {
    private String filepath;
    private volatile boolean cancelled = false;

    @Override
    public void initialize(Object... params) {
        filepath = (String) params[0];
    }

    @Override
    public void run() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
            for (int i = 0; i < 1_000_000 && !cancelled; i++) {
                bw.write("Line " + i);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("File write failed", e);
        }
    }

    @Override
    public void clean() {
        // nothing extra
    }

    @Override
    public void cancel() {
        cancelled = true;
    }
}
