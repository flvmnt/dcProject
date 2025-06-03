package testbench;

import bench.hdd.HDDWriteSpeed;

public class TestHDDWriteSpeed {
    public static void main(String[] args) {
        HDDWriteSpeed bench = new HDDWriteSpeed();

        System.out.println("Running fixed file size test (fs)...");
        bench.initialize("fs", true);
        bench.run();

        System.out.println("\nRunning fixed buffer size test (fb)...");
        bench.initialize("fb", true);
        bench.run();
    }
}
