package testbench;

import benchmark.cpu.CPUDigitsOfPi;
import benchmark.logging.ConsoleLogger;
import benchmark.logging.ILogger;
import benchmark.logging.TimeUnit;
import benchmark.timing.Timer;

public class TestCPUDigitsOfPi {
    public static void main(String[] args) {
        CPUDigitsOfPi piBench = new CPUDigitsOfPi();
        ILogger logger = new ConsoleLogger();
        Timer timer = new Timer();

        int digits = 1000; // try 100, 500, 1000, 5000, etc.
        piBench.initialize(digits);
        piBench.warmup(); // trigger class loading, warmup JVM

        for (int i = 0; i < 5; i++) {
            timer.start();
            piBench.run();
            long time = timer.stop();
            logger.write("Run #" + (i + 1), "Time (ms):", String.format("%.3f", time / 1_000_000.0));
        }

        logger.close();
        piBench.clean();
    }
}
