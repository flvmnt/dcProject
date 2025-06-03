package benchmark.cpu;

import benchmark.bench.IBenchmark;

public class CPUFixedPoint implements IBenchmark {
    private int[] num = { 0, 1, 2, 3 };
    private int[] a, b, c;
    private int size;

    @Override
    public void initialize(Object... params) {
        size = ((Number) params[0]).intValue();
        a = new int[size];
        b = new int[size];
        c = new int[size];
        for (int i = 0; i < size; i++) {
            a[i] = i;
            b[i] = size - i - 1;
        }
    }

    @Override
    public void run() {
        testIntegerArithmetic();
        testBranching();
        testArrayAccess();
    }

    public void run(Object... options) {
        run();
    }

    @Override
    public void clean() {
        a = b = c = null;
    }

    @Override
    public void cancel() {
    }

    @Override
    public void warmup() {
        initialize(1000);
        run();
    }

    private void testIntegerArithmetic() {
        int j = 1, k = 2, l = 3;
        for (int i = 0; i < size; i++) {
            j = num[1] * (k - j) * (l - k);
            k = num[3] * k - (l - j) * k;
            l = (l - k) * (num[2] + j);
        }
    }

    private void testBranching() {
        int j = 0;
        for (int i = 0; i < size; i++) {
            if (j == 1) j = num[2]; else j = num[3];
            if (j > 2) j = num[0]; else j = num[1];
            if (j < 1) j = num[1]; else j = num[0];
        }
    }

    private void testArrayAccess() {
        for (int i = 0; i < size; i++) {
            int index = b[i] % size;
            c[i] = a[index];
        }
    }

    public double getMOPS(long timeNs) {
        final int OPS_PER_ITER = 27; // calculate exact number in your code
        return (OPS_PER_ITER * size) / (timeNs / 1e6);
    }
}
