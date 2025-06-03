package testbench;

import bench.hdd.HDDRandomAccess;

public class TestHDDRandomAccess {
    public static void main(String[] args) {
        HDDRandomAccess bench = new HDDRandomAccess();
        bench.initialize();

        System.out.println("Random READ - fixed size:");
        bench.run("r", "fs", 4096); // 4KB buffer

        System.out.println("\nRandom READ - fixed time:");
        bench.run("r", "ft", 4096);

        System.out.println("\nRandom WRITE - fixed size:");
        bench.run("w", "fs", 4096);

        System.out.println("\nRandom WRITE - fixed time:");
        bench.run("w", "ft", 4096);
    }
}
