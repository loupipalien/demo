package com.ltchen.demo.util;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App {

    private static List<WeightRoundRobin.Server> wrrServers = new ArrayList<>();
    private static WeightRoundRobin wrr;
    private static List<SmoothWeightRoundRobin.Server> swrrServers = new ArrayList<>();
    private static SmoothWeightRoundRobin swrr;
    static {
        wrrServers.add(new WeightRoundRobin.Server("192.168.0.1", 1));
        wrrServers.add(new WeightRoundRobin.Server("192.168.0.2", 2));
        wrrServers.add(new WeightRoundRobin.Server("192.168.0.3", 4));
        wrrServers.add(new WeightRoundRobin.Server("192.168.0.4", 8));
        wrr = new WeightRoundRobin(wrrServers);
        swrrServers.add(new SmoothWeightRoundRobin.Server("192.168.0.1", 1));
        swrrServers.add(new SmoothWeightRoundRobin.Server("192.168.0.2", 2));
        swrrServers.add(new SmoothWeightRoundRobin.Server("192.168.0.3", 4));
        swrrServers.add(new SmoothWeightRoundRobin.Server("192.168.0.4", 8));
        swrr = new SmoothWeightRoundRobin(swrrServers);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void weightRoundRobin() {
        wrr.next();
    }

//    @Benchmark
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.NANOSECONDS)
//    public void smoothWeightRoundRobin() {
//        swrr.next();
//    }

    public static void main( String[] args ) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(App.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
