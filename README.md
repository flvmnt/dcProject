
# 🧪 Simple Java Benchmark Framework

A modular benchmarking tool built in Java to test **CPU** and **HDD performance**.  
Supports measuring execution time, throughput (MB/s), and IOPS across multiple test types.

---

## ✅ Features

- **CPU Benchmarks**:
  - Bubble sort
  - Digits of Pi calculation
  - Fixed-point arithmetic
  - Fixed-point vs Floating-point comparison
  - Recursion with loop unrolling (prime finder)

- **Disk Benchmarks**:
  - Sequential Write Speed (MB/s)
  - Random Access (Reads/Writes via IOPS and MB/s)

- **Logging**:
  - Console output
  - File-based logging

- **Precision**:
  - Nanosecond timing via a custom `Timer` utility

---

## ⚙️ Compilation

From the project root, run:

```bash
javac -d bin \
  src/benchmark/timing/*.java \
  src/benchmark/logging/*.java \
  src/benchmark/bench/*.java \
  src/benchmark/cpu/*.java \
  src/bench/hdd/*.java \
  src/benchmark/Main.java \
  src/testbench/*.java
```

---

## 🧭 Running Benchmarks

### List all available benchmarks:

```bash
java -cp bin benchmark.Main --list
```

### General usage format:

```bash
java -cp bin benchmark.Main \
  --benchmark <benchmark_name> \
  --logger <console|file> \
  [--logfile <log.txt>] \
  [--size <n>] \
  [--path <output_file>]
```

---

## 🚀 Benchmark Commands

### 🧠 CPU Benchmarks

| Task                        | Command                                                                 |
|-----------------------------|-------------------------------------------------------------------------|
| Bubble Sort                 | `java -cp bin benchmark.Main --benchmark cpu_sort --logger console --size 10000` |
| Fixed-point Arithmetic      | `java -cp bin benchmark.Main --benchmark cpu_fixed --logger console --size 100000` |
| Fixed vs Floating-point     | `java -cp bin benchmark.Main --benchmark cpu_fixed_vs_float --logger console --size 100000` |
| Digits of Pi                | `java -cp bin benchmark.Main --benchmark cpu_pi --logger console --size 1000` |
| Recursion (Prime Generator) | `java -cp bin benchmark.Main --benchmark cpu_recursion --logger console --size 5000` |

---

### 💾 Disk Benchmarks

| Task                          | Command                                                                 |
|-------------------------------|-------------------------------------------------------------------------|
| File Write (1M lines)         | `java -cp bin benchmark.Main --benchmark file_write --logger console --path output.txt` |
| HDD Write Speed (Sequential)  | `java -cp bin benchmark.Main --benchmark hdd_write --logger console` |
| HDD Random Access (All modes) | `java -cp bin benchmark.Main --benchmark hdd_random_access --logger console` |

---

## 📁 Output

Depending on the logger used:

- `console`: results are printed to the terminal
- `file`: results are saved to a log file using `--logfile <name>`
