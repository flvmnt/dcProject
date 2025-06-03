package benchmark;

import benchmark.timing.Timer;
import benchmark.logging.ConsoleLogger;
import benchmark.logging.FileLogger;
import benchmark.logging.ILogger;
import benchmark.bench.*;
import benchmark.cpu.*;
import bench.hdd.*;

public class Main {
    private static void usage() {
        System.out.println("Usage:");
        System.out.println("  java -cp bin benchmark.Main --list");
        System.out.println("  java -cp bin benchmark.Main --benchmark <benchmark_name> --logger <console|file>");
        System.out.println("       [--logfile <file>] [--size <n>] [--path <file>]");
    }

    private static void listBenchmarks() {
        System.out.println("Available benchmarks:");
        System.out.println("  cpu_sort           - Bubble sort on a random array");
        System.out.println("  file_write         - Write 1,000,000 lines to a file");
        System.out.println("  hdd_write          - Sequential file write speed test");
        System.out.println("  hdd_random_access  - Random access read/write benchmark");
        System.out.println("  cpu_pi             - Compute digits of Pi");
        System.out.println("  cpu_fixed          - Fixed-point arithmetic performance");
        System.out.println("  cpu_fixed_vs_float - Compare fixed-point vs floating-point");
        System.out.println("  cpu_recursion      - Recursive prime finder with loop unrolling");
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            listBenchmarks();
            usage();
            return;
        }

        String benchmark = null, loggerType = null, logfile = null, path = "benchmark_output.txt";
        int size = 10_000;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--list":
                    listBenchmarks(); return;
                case "--benchmark":
                    benchmark = args[++i]; break;
                case "--logger":
                    loggerType = args[++i]; break;
                case "--logfile":
                    logfile = args[++i]; break;
                case "--size":
                    size = Integer.parseInt(args[++i]); break;
                case "--path":
                    path = args[++i]; break;
                default:
                    System.err.println("Unknown option: " + args[i]);
                    usage();
                    return;
            }
        }

        if (benchmark == null || loggerType == null) {
            System.err.println("Error: --benchmark and --logger are required.");
            usage();
            return;
        }

        IBenchmark bench;
        switch (benchmark) {
            case "cpu_sort":
                bench = new CpuSortBenchmark();
                bench.initialize(size);
                break;
            case "file_write":
                bench = new FileWriteBenchmark();
                bench.initialize(path);
                break;
            case "hdd_write":
                bench = new HDDWriteSpeed();
                bench.initialize();
                break;
            case "hdd_random_access":
                bench = new HDDRandomAccess();
                bench.initialize();
                break;
            case "cpu_pi":
                bench = new CPUDigitsOfPi();
                bench.initialize(size);
                break;
            case "cpu_fixed":
                bench = new CPUFixedPoint();
                bench.initialize(size);
                break;
            case "cpu_fixed_vs_float":
                bench = new CPUFixedVsFloatingPoint();
                bench.initialize(size);
                break;
            case "cpu_recursion":
                bench = new CPURecursionLoopUnrolling();
                bench.initialize(size);
                break;
            default:
                System.err.println("Unknown benchmark: " + benchmark);
                return;
        }

        ILogger logger;
        try {
            switch (loggerType) {
                case "console":
                    logger = new ConsoleLogger(); break;
                case "file":
                    if (logfile == null) {
                        System.err.println("Error: --logfile is required for file logger.");
                        return;
                    }
                    logger = new FileLogger(logfile); break;
                default:
                    System.err.println("Unknown logger: " + loggerType);
                    return;
            }
        } catch (Exception e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
            return;
        }

        Timer timer = new Timer();
        timer.start();
        bench.run();
        long elapsedNs = timer.stop();
        double elapsedMs = elapsedNs / 1_000_000.0;

        logger.write("Benchmark:", benchmark);
        logger.write("Elapsed (ns):", elapsedNs);
        logger.write("Elapsed (ms):", String.format("%.3f", elapsedMs));
        logger.close();
        bench.clean();
    }
}
