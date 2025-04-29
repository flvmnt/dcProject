package benchmark.logging;

public class ConsoleLogger implements ILogger {
    @Override
    public void write(long value) {
        System.out.println(value);
    }

    @Override
    public void write(String text) {
        System.out.println(text);
    }

    @Override
    public void write(Object... values) {
        StringBuilder sb = new StringBuilder();
        for (Object v : values) {
            sb.append(v).append(" ");
        }
        System.out.println(sb.toString().trim());
    }

    @Override
    public void close() {
        // nothing to do
    }
}
