package com.ltchen.demo.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.TimeUnit;

/**
 * Created by ltchen on 2018/12/15.
 */
public class MeterDemo {
    private static final MetricRegistry metrics = new MetricRegistry();
    // A meter measures the rate of events over time (e.g., “requests per second”)
    private final Meter requests = metrics.meter(MetricRegistry.name(MeterDemo.class, "method", "requests"));
    static {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    public void method() {
        requests.mark();
    }

    public static void main(String[] args) throws InterruptedException {
        MeterDemo demo = new MeterDemo();
        for (int i = 0; i < 100; i++) {
            demo.method();
            TimeUnit.MILLISECONDS.sleep(300);
        }
    }
}
