package testbench;

import benchmark.cpu.CPURecursionLoopUnrolling;

public class TestCPURecursionLoopUnrolling {
    public static void main(String[] args) {
        CPURecursionLoopUnrolling benchmark = new CPURecursionLoopUnrolling();
        benchmark.run(); // runs plain recursion

        System.out.println("\n--- Loop Unrolling Test ---");
        try {
            benchmark = new CPURecursionLoopUnrolling();
            benchmark.recursiveUnrolled(2, 5); // unrollLevel = 5
        } catch (StackOverflowError e) {
            System.out.println("[!] Loop unrolling crashed as expected.");
        }
    }
}
