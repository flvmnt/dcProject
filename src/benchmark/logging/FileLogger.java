package benchmark.logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements ILogger {
    private final BufferedWriter writer;

    public FileLogger(String filepath) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(filepath));
    }

    @Override
    public void write(long value) {
        write(Long.toString(value));
    }

    @Override
    public void write(String text) {
        try {
            writer.write(text);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to log file", e);
        }
    }

    @Override
    public void write(Object... values) {
        StringBuilder sb = new StringBuilder();
        for (Object v : values) {
            sb.append(v).append(" ");
        }
        write(sb.toString().trim());
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            // ignore or report
            e.printStackTrace();
        }
    }
}
