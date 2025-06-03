package testbench;

import benchmark.cpu.CPUFixedVsFloatingPoint;
import benchmark.cpu.CPUFixedVsFloatingPoint.NumberRepresentation;
import benchmark.logging.ConsoleLogger;
import benchmark.logging.ILogger;
import benchmark.timing.Timer;

public class TestCPUFixedVsFloatingPoint {
    public static void main(String[] args) {
        ILogger logger = new ConsoleLogger();
        Timer timer = new Timer();
        CPUFixedVsFloatingPoint bench = new CPUFixedVsFloatingPoint();

        bench.warmup();

        for (NumberRepresentation type : NumberRepresentation.values()) {
            bench.clean();
            timer.start();
            bench.run(type);
            long time = timer.stop();
            logger.write("Type: " + type, "Time (ms):", String.format("%.3f", time / 1_000_000.0));
        }

        logger.close();
    }
}
