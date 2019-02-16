package com.ltchen.demo.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * Created by ltchen on 2018/12/15.
 */
public class GaugeDemo {
    private static final MetricRegistry metrics = new MetricRegistry();
    static {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    private Queue<Integer> queue = new PriorityQueue();
    public GaugeDemo() {
        // Most of java.util and java.util.concurrent have implementations of #size() which are O(n), which means your gauge will be slow (potentially while holding a lock).
        metrics.register(MetricRegistry.name(GaugeDemo.class, "queue", "size"), (Gauge)() -> this.queue.size());
    }

    private void addOne() {
        this.queue.offer(1);
    }

    public static void main(String[] args) throws InterruptedException {
        GaugeDemo demo = new GaugeDemo();
        for (int i = 0; i < 100; i++) {
            demo.addOne();
            TimeUnit.MILLISECONDS.sleep(300);
        }
    }
}
