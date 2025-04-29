package benchmark.bench;

import java.util.Random;

public class CpuSortBenchmark implements IBenchmark {
    private double[] data;
    private volatile boolean cancelled = false;

    @Override
    public void initialize(Object... params) {
        int size = (Integer) params[0];
        data = new double[size];
        Random rnd = new Random();
        for (int i = 0; i < size; i++) {
            data[i] = rnd.nextDouble();
        }
    }

    @Override
    public void run() {
        int n = data.length;
        for (int i = 0; i < n && !cancelled; i++) {
            for (int j = 0; j < n - i - 1 && !cancelled; j++) {
                if (data[j] > data[j + 1]) {
                    double tmp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = tmp;
                }
            }
        }
    }

    @Override
    public void clean() {
        data = null;
    }

    @Override
    public void cancel() {
        cancelled = true;
    }
}
