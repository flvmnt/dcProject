package benchmark;

import benchmark.timing.Timer;
import benchmark.logging.ConsoleLogger;
import benchmark.logging.FileLogger;
import benchmark.logging.ILogger;
import benchmark.bench.CpuSortBenchmark;
import benchmark.bench.FileWriteBenchmark;
import benchmark.bench.IBenchmark;

public class Main {
    private static void usage() {
        System.out.println("Usage:");
        System.out.println("  java -jar benchmark.jar --list");
        System.out.println("  java -jar benchmark.jar --benchmark [cpu_sort|file_write] --logger [console|file]");
        System.out.println("       [--logfile <file>] [--size <n>] [--path <file>]");
    }

    private static void listBenchmarks() {
        System.out.println("Available benchmarks:");
        System.out.println("  cpu_sort   - Bubble sort on a random array");
        System.out.println("  file_write - Write 1,000,000 lines to a file");
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
            default:
                System.err.println("Unknown benchmark: " + benchmark);
                return;
        }

        ILogger logger;
        try {
            switch (loggerType) {
                case "console":
                    logger = new ConsoleLogger();
                    break;
                case "file":
                    if (logfile == null) {
                        System.err.println("Error: --logfile is required for file logger.");
                        return;
                    }
                    logger = new FileLogger(logfile);
                    break;
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
