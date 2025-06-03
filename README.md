# Simple Java Benchmark Framework

A modular benchmarking tool built in Java to test CPU and storage performance.  
Supports measuring execution time, throughput, and IOPS for multiple scenarios (CPU, HDD sequential and random access).

---

## ✅ Features

- **CPU Benchmarks**: Bubble sort, Pi digit calculation, fixed-point math
- **Disk Benchmarks**:
  - **Sequential Write**: Measure MB/s with configurable file sizes and buffer sizes
  - **Random Access**: Simulate database-style random reads/writes, measure IOPS and MB/s
- **Logging**: Console or file output
- **Accurate Timing**: Nanosecond precision via custom `Timer`

---

## ⚙️ Compilation

From project root:

```bash
javac -d bin \
  src/benchmark/timing/*.java \
  src/benchmark/logging/*.java \
  src/benchmark/bench/*.java \
  src/benchmark/cpu/*.java \
  src/bench/hdd/*.java \
  src/benchmark/Main.java \
  src/testbench/*.java

java -cp bin benchmark.Main --list

java -cp bin benchmark.Main
Available benchmarks:
  cpu_sort           - Bubble sort on a random array
  file_write         - Write 1,000,000 lines to a file
  hdd_write          - Sequential file write speed test
  hdd_random_access  - Random access read/write benchmark
  cpu_pi             - Compute digits of Pi
  cpu_fixed          - Fixed-point arithmetic performance
  cpu_fixed_vs_float - Compare fixed-point vs floating-point
  cpu_recursion      - Recursive prime finder with loop unrolling
Usage:
  java -cp bin benchmark.Main --list
  java -cp bin benchmark.Main --benchmark <benchmark_name> --logger <console|file>
       [--logfile <file>] [--size <n>] [--path <file>]

CPU: Bubble Sort
java -cp bin benchmark.Main --benchmark cpu_sort --logger console --size 10000

CPU: Fixed-point Arithmetic
java -cp bin benchmark.Main --benchmark cpu_fixed --logger console --size 100000

CPU: Fixed-point vs Floating-point
java -cp bin benchmark.Main --benchmark cpu_fixed_vs_float --logger console --size 100000

CPU: Digits of Pi
java -cp bin benchmark.Main --benchmark cpu_pi --logger console --size 1000

CPU: Recursion & Loop Unrolling
java -cp bin benchmark.Main --benchmark cpu_recursion --logger console --size 5000

File Write (1 million lines)
java -cp bin benchmark.Main --benchmark file_write --logger console --path output.txt

HDD Write Speed (Sequential)
java -cp bin benchmark.Main --benchmark hdd_write --logger console

HDD Random Access Benchmark
java -cp bin benchmark.Main --benchmark hdd_random_access --logger console