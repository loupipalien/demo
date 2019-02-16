package com.ltchen.demo.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * Created by ltchen on 2018/12/15.
 */
public class CounterDemo {
    private static final MetricRegistry metrics = new MetricRegistry();
    // A counter is just a gauge for an AtomicLong instance, and more efficient
    private final Counter queueSize = metrics.counter(MetricRegistry.name(CounterDemo.class, "queue", "size"));
    static {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    private Queue<Integer> queue = new PriorityQueue();

    private void addOne() {
        queueSize.inc();
        this.queue.offer(1);
    }

    private void removeOne() {
        queueSize.dec();
        this.queue.poll();
    }

    public static void main(String[] args) throws InterruptedException {
        CounterDemo demo = new CounterDemo();
        for (int i = 0; i < 100; i++) {
            if (i % 3 != 0) {
                demo.addOne();
            } else {
                demo.removeOne();
            }
            TimeUnit.MILLISECONDS.sleep(300);
        }
    }
}
