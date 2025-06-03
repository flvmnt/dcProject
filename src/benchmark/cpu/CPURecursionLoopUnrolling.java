// File: src/benchmark/cpu/CPURecursionLoopUnrolling.java
package benchmark.cpu;

import benchmark.bench.IBenchmark;

public class CPURecursionLoopUnrolling implements IBenchmark {
    private long lastPrime = 0;
    private int callCount = 0;
    private long startTime, endTime;

    @Override
    public void initialize(Object... params) {
        // No init needed
    }

    public void run() {
        try {
            startTime = System.nanoTime();
            recursive(2);
        } catch (StackOverflowError e) {
            endTime = System.nanoTime();
            System.out.printf("[!] Stack overflow at recursion depth: %d\n", callCount);
            System.out.printf("[!] Last prime: %d\n", lastPrime);
            System.out.printf("[!] Runtime: %.3f seconds\n", (endTime - startTime) / 1e9);
            System.out.printf("[!] Score: %.2f\n", getScore());
        }
    }

    public long recursive(long n) {
        callCount++;
        if (isPrime(n)) lastPrime = n;
        return recursive(n + 1);
    }

    public long recursiveUnrolled(long n, int unrollLevel) {
        callCount++;
        for (int i = 0; i < unrollLevel; i++) {
            if (isPrime(n)) lastPrime = n;
            n++;
        }
        return recursiveUnrolled(n, unrollLevel);
    }

    private boolean isPrime(long n) {
        if (n < 2) return false;
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private double getScore() {
        double time = (endTime - startTime) / 1e9; // seconds
        return lastPrime / (time * callCount / 1e3 + 1); // simplistic score
    }

    @Override public void clean() {}
    @Override public void cancel() {}
    @Override public void warmup() {}
}
