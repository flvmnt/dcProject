package bench.hdd;

import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class RandomAccessRW {
    private final Random random = new Random();
    private File tempFile;

    public File initializeTempFile(long sizeBytes) throws IOException {
        tempFile = File.createTempFile("tempfile", ".dat");
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.setLength(sizeBytes); // allocate 1GB
        }
        return tempFile;
    }

    public long randomReadFixedSize(File file, int bufferSize, int toRead) throws IOException {
        byte[] buffer = new byte[bufferSize];
        RandomAccessFile raf = new RandomAccessFile(file, "r");

        long fileSize = file.length();
        long startTime = System.nanoTime();
        for (int i = 0; i < toRead; i++) {
            long pos = Math.abs(random.nextLong()) % (fileSize - bufferSize);
            raf.seek(pos);
            raf.readFully(buffer);
        }
        long endTime = System.nanoTime();
        raf.close();
        return endTime - startTime;
    }

    public int randomReadFixedTime(File file, int bufferSize, long durationMillis) throws IOException {
        byte[] buffer = new byte[bufferSize];
        RandomAccessFile raf = new RandomAccessFile(file, "r");

        long fileSize = file.length();
        long start = System.currentTimeMillis();
        int jumps = 0;
        while (System.currentTimeMillis() - start < durationMillis) {
            long pos = Math.abs(random.nextLong()) % (fileSize - bufferSize);
            raf.seek(pos);
            raf.readFully(buffer);
            jumps++;
        }
        raf.close();
        return jumps;
    }

    public long randomWriteFixedSize(File file, int bufferSize, int toWrite) throws IOException {
        byte[] buffer = new byte[bufferSize];
        random.nextBytes(buffer);
        RandomAccessFile raf = new RandomAccessFile(file, "rw");

        long fileSize = file.length();
        long startTime = System.nanoTime();
        for (int i = 0; i < toWrite; i++) {
            long pos = Math.abs(random.nextLong()) % (fileSize - bufferSize);
            raf.seek(pos);
            raf.write(buffer);
        }
        long endTime = System.nanoTime();
        raf.close();
        return endTime - startTime;
    }

    public int randomWriteFixedTime(File file, int bufferSize, long durationMillis) throws IOException {
        byte[] buffer = new byte[bufferSize];
        random.nextBytes(buffer);
        RandomAccessFile raf = new RandomAccessFile(file, "rw");

        long fileSize = file.length();
        long start = System.currentTimeMillis();
        int jumps = 0;
        while (System.currentTimeMillis() - start < durationMillis) {
            long pos = Math.abs(random.nextLong()) % (fileSize - bufferSize);
            raf.seek(pos);
            raf.write(buffer);
            jumps++;
        }
        raf.close();
        return jumps;
    }
}
