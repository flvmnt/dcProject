package testbench;

import benchmark.cpu.CPUFixedPoint;
import benchmark.logging.ConsoleLogger;
import benchmark.logging.ILogger;
import benchmark.timing.Timer;

public class TestCPUFixedPoint {
    public static void main(String[] args) {
        ILogger logger = new ConsoleLogger();
        Timer timer = new Timer();
        CPUFixedPoint bench = new CPUFixedPoint();
        int size = 1_000_000;

        bench.initialize(size);
        bench.warmup();

        timer.start();
        bench.run();
        long time = timer.stop();

        double mops = bench.getMOPS(time);
        logger.write("Fixed point MOPS:", String.format("%.2f", mops / 1_000_000) + " MOPS");
        logger.close();
    }
}
