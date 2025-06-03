// File: src/benchmark/cpu/CPUDigitsOfPi.java
package benchmark.cpu;

import benchmark.bench.IBenchmark;

import java.math.BigDecimal;
import java.math.MathContext;

public class CPUDigitsOfPi implements IBenchmark {
    private int digits;

    @Override
    public void initialize(Object... params) {
        this.digits = (Integer) params[0];
    }

    @Override
    public void run() {
        computePiArctangent(digits);
    }

    public void run(Object... options) {
        int algo = options.length > 0 ? (Integer) options[0] : 1;
        switch (algo) {
            case 1:
            default:
                computePiArctangent(digits);
        }
    }

    @Override
    public void clean() {
        // Nothing to clean for now
    }

    @Override
    public void cancel() {
        // Not used
    }

    @Override
    public void warmup() {
        computePiArctangent(1000); // small warmup workload
    }

    // Machin-like formula: pi = 16*arctan(1/5) - 4*arctan(1/239)
    private void computePiArctangent(int digits) {
        MathContext mc = new MathContext(digits + 5);
        BigDecimal arctan1_5 = arctan(5, digits, mc);
        BigDecimal arctan1_239 = arctan(239, digits, mc);
        BigDecimal pi = arctan1_5.multiply(BigDecimal.valueOf(16)).subtract(
                arctan1_239.multiply(BigDecimal.valueOf(4)));
    }

    private BigDecimal arctan(int inverseX, int digits, MathContext mc) {
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal x = BigDecimal.ONE.divide(BigDecimal.valueOf(inverseX), mc);
        BigDecimal xPower = x;
        BigDecimal invX2 = x.multiply(x);
        int sign = 1;

        for (int i = 1; i < digits * 2; i += 2) {
            BigDecimal term = xPower.divide(BigDecimal.valueOf(i), mc);
            result = sign > 0 ? result.add(term) : result.subtract(term);
            xPower = xPower.multiply(invX2);
            sign *= -1;
        }
        return result;
    }
}
