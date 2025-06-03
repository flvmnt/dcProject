package benchmark.cpu;

import benchmark.bench.IBenchmark;

public class CPUFixedVsFloatingPoint implements IBenchmark {
    private static final int ITERATIONS = 50_000_000;
    private double resultFloat = 0.0;
    private int resultFixed = 0;

    public enum NumberRepresentation {
        FIXED, FLOATING
    }

    @Override
    public void initialize(Object... params) {
        // not used
    }

    @Override
    public void run() {
        // not used
    }

    public void run(Object... options) {
        NumberRepresentation type = (NumberRepresentation) options[0];
        if (type == NumberRepresentation.FLOATING) {
            for (int i = 1; i <= ITERATIONS; i++) {
                resultFloat += i / 256.0; // Floating point division
            }
        } else if (type == NumberRepresentation.FIXED) {
            for (int i = 1; i <= ITERATIONS; i++) {
                resultFixed += i >> 8; // Bit-shift instead of /256
            }
        }
    }

    @Override
    public void clean() {
        resultFloat = 0.0;
        resultFixed = 0;
    }

    @Override
    public void cancel() {
    }

    @Override
    public void warmup() {
        run(NumberRepresentation.FIXED);
        run(NumberRepresentation.FLOATING);
    }
}
