package testbench;

import benchmark.bench.DemoBenchmark;
import benchmark.logging.ConsoleLogger;
import benchmark.logging.ILogger;
import benchmark.logging.TimeUnit;
import benchmark.timing.Timer;

public class TestDemoBench {
    public static void main(String[] args) {
        ILogger log = new ConsoleLogger();
        Timer t = new Timer();
        DemoBenchmark demo = new DemoBenchmark();

        long sleepMs = 100;      // expected 100 ms
        demo.initialize(sleepMs);

        // 1) measure straight sleep + offset %
        t.start();
        demo.run();
        long measuredNs = t.stop();
        double expectedNs = sleepMs * 1_000_000.0;
        double offset = 100.0 * (measuredNs - expectedNs) / expectedNs;
        log.write("Expected (ms):", sleepMs);
        log.write("Measured (ns):", measuredNs);
        log.write(String.format("Offset: %.2f%%", offset));

        // 2) test pause/resume sequence in a loop (six 100 ms sleeps)
        t.start();
        t.pause();  // immediately pause so resume() is valid

        for (int i = 0; i < 6; i++) {
            t.resume();      // now legal, because paused
            try { Thread.sleep(sleepMs); } catch (InterruptedException ignored) {}
            long delta = t.pause();
            log.write("Segment", i, "=", delta / 1_000_000.0, "ms");
        }
        long total = t.stop();   // sum of all six segments
        log.write("Total (ms):", total / 1_000_000.0);

        // 3) now demonstrate writeTime in different units:
        log.writeTime("Total in seconds:", total, TimeUnit.SECOND);
        log.writeTime("Total in millis:", total, TimeUnit.MILLI);
        log.writeTime("Total in micros:", total, TimeUnit.MICRO);
        log.close();
    }
}
