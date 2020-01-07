package dev.minhazav.camera2benchmark.benchmark;

import dev.minhazav.camera2benchmark.benchmark.ObservableBenchmark.BenchmarkResult.BenchmarkMetric;

public final class BenchmarkMetricImpl implements BenchmarkMetric {

    private final String title;
    private final float value;
    private final String metric;

    public static BenchmarkMetric create(String title, float value, String metric) {
        return new BenchmarkMetricImpl(title, value, metric);
    }

    private BenchmarkMetricImpl(String title, float value, String metric) {
        this.title = title;
        this.value = value;
        this.metric = metric;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public float getValue() {
        return value;
    }

    @Override
    public String getMetric() {
        return metric;
    }
}
