# Simple Benchmark Framework

A tiny Java tool to time code and log results.

## Quick Start

# 1. Compile everything (no output if successful)
javac -d bin \
  src/benchmark/timing/*.java \
  src/benchmark/logging/*.java \
  src/benchmark/bench/*.java \
  src/benchmark/Main.java \
  src/testbench/TestDemoBench.java

# 2. List available benchmarks
java -cp bin benchmark.Main --list

Available benchmarks:
  cpu_sort   - Bubble sort on a random array
  file_write - Write 1,000,000 lines to a file

# 3. Run CPU‐sort with console logger
java -cp bin benchmark.Main --benchmark cpu_sort --logger console --size 5000

Benchmark: cpu_sort
Elapsed (ns): 123456789
Elapsed (ms): 123.457

# 4. Run CPU‐sort with file logger
java -cp bin benchmark.Main --benchmark cpu_sort --logger file --logfile cpu_log.txt --size 5000

# → no console output, but cpu_log.txt contains:
# Benchmark: cpu_sort
# Elapsed (ns): 123456789
# Elapsed (ms): 123.457

# 5. Run file‐write with console logger
java -cp bin benchmark.Main --benchmark file_write --logger console --path demo_output.txt

Benchmark: file_write
Elapsed (ns): 987654321
Elapsed (ms): 987.654

# 6. Run file‐write with file logger
java -cp bin benchmark.Main --benchmark file_write --logger file --logfile file_log.txt --path demo_output.txt

# → no console output, but file_log.txt contains:
# Benchmark: file_write
# Elapsed (ns): 987654321
# Elapsed (ms): 987.654

# 7. Run the demo/testbench
java -cp bin testbench.TestDemoBench

Expected (ms): 100
Measured (ns): 105416458
Offset: 5.42%

Segment 0 = 105.048166 ms
Segment 1 = 105.04525 ms
Segment 2 = 102.262583 ms
Segment 3 = 105.0355 ms
Segment 4 = 101.854875 ms
Segment 5 = 104.297 ms
Total (ms): 623.546082
Total in seconds: 0.624 SECOND
Total in millis: 623.546 MILLI
Total in micros: 623546.082 MICRO
