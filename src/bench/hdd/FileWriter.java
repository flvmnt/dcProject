package bench.hdd;

import java.io.*;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.Random;

public class FileWriter {
    private final DecimalFormat df = new DecimalFormat("#0.00");

    public void streamWriteFixedSize(String prefix, String suffix, int minIndex, int maxIndex,
                                     long fileSize, boolean clean) {
        int bufferSize = 1 * 1024; // start with 1KB
        int index = minIndex;
        double totalSpeed = 0;

        while (index <= maxIndex && bufferSize <= 64 * 1024 * 1024) {
            String fileName = prefix + index + suffix;
            totalSpeed += writeFile(fileName, bufferSize, fileSize, clean);
            index++;
            bufferSize *= 4;
        }

        printBenchmarkScore(prefix, maxIndex - minIndex + 1, totalSpeed);
    }

    public void streamWriteFixedBuffer(String prefix, String suffix, int minIndex, int maxIndex,
                                       int bufferSize, boolean clean) {
        long[] fileSizes = {
            1L * 1024 * 1024,
            10L * 1024 * 1024,
            100L * 1024 * 1024,
            1024L * 1024 * 1024
        };

        double totalSpeed = 0;

        for (int i = 0; i <= maxIndex && i < fileSizes.length; i++) {
            String fileName = prefix + i + suffix;
            totalSpeed += writeFile(fileName, bufferSize, fileSizes[i], clean);
        }

        printBenchmarkScore(prefix, fileSizes.length, totalSpeed);
    }

    private double writeFile(String fileName, int bufferSize, long fileSize, boolean clean) {
        try {
            File file = new File(fileName);
            file.getParentFile().mkdirs();

            byte[] buffer = new byte[bufferSize];
            new Random().nextBytes(buffer);
            long totalBytes = 0;
            long startTime = System.nanoTime();

            try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file), bufferSize)) {
                while (totalBytes < fileSize) {
                    int writeSize = (int) Math.min(bufferSize, fileSize - totalBytes);
                    out.write(buffer, 0, writeSize);
                    totalBytes += writeSize;
                }
            }

            long endTime = System.nanoTime();
            return printStats(fileName, totalBytes, bufferSize, endTime - startTime, clean);

        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
            return 0;
        }
    }

    private double printStats(String fileName, long totalBytes, int bufferSize, long timeNs, boolean clean) {
        double timeSec = timeNs / 1e9;
        double mb = totalBytes / (1024.0 * 1024.0);
        double speed = mb / timeSec;

        System.out.println("Wrote " + fileName + " | Size: " + df.format(mb) + "MB | Buffer: " +
                bufferSize / 1024 + "KB | Speed: " + df.format(speed) + " MB/s");

        if (clean) {
            try {
                Files.deleteIfExists(new File(fileName).toPath());
            } catch (IOException ignored) {}
        }

        return speed;
    }

    private void printBenchmarkScore(String path, int fileCount, double totalSpeed) {
        String drive = path.split(":")[0];
        double avg = totalSpeed / fileCount;
        System.out.println("=== Avg write speed on partition " + drive + ": " + df.format(avg) + " MB/s ===");
    }
}
