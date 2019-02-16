package com.ltchen.demo.metrics.jvm;

import com.codahale.metrics.*;
import com.codahale.metrics.jvm.*;

import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by ltchen on 2018/12/18.
 */
public class JvmMetricsDemo {
    private static final MetricRegistry registry = new MetricRegistry();
    static {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(10, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        new JvmMetricsDemo().buildJvmMetrics(registry);
        TimeUnit.SECONDS.sleep(100);
    }

    private void buildJvmMetrics(MetricRegistry registry) {
        registerAll("jvm.buffer", new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()), registry);
        registerAll("jvm.class", new ClassLoadingGaugeSet(), registry);
        register("jvm.uptime", (Gauge) () -> ManagementFactory.getRuntimeMXBean().getUptime(), registry);
        register("jvm.time", (Gauge<Long>) () -> new CpuTimeClock().getTime(), registry);
        register("jvm.fd", new FileDescriptorRatioGauge(), registry);
        registerAll("jvm.gc", new GarbageCollectorMetricSet(), registry);
        registerAll("jvm.attribute", new JvmAttributeGaugeSet(), registry);
        registerAll("jvm.memory", new MemoryUsageGaugeSet(), registry);
        registerAll("jvm.thread", new ThreadStatesGaugeSet(ManagementFactory.getThreadMXBean(), new ThreadDeadlockDetector()), registry);
    }

    /**
     * 注册 metric set
     * @param prefix
     * @param metricSet
     * @param registry
     */
    private static void registerAll(String prefix, MetricSet metricSet, MetricRegistry registry) {
        for (Map.Entry<String, Metric> entry : metricSet.getMetrics().entrySet()) {
            if (entry.getValue() instanceof MetricSet) {
                registerAll(prefix + "." + entry.getKey(), (MetricSet) entry.getValue(), registry);
            } else {
                register(prefix + "." + entry.getKey(), entry.getValue(), registry);
            }
        }
    }

    /**
     * 注册 metric
     * @param name
     * @param metric
     * @param registry
     */
    private static void register(String name, Metric metric, MetricRegistry registry) {
        registry.register(name, metric);
    }
}
