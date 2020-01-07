package dev.minhazav.camera2benchmark.benchmark;

import androidx.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import dev.minhazav.camera2benchmark.benchmark.ObservableBenchmark.BenchmarkResult;

public final class BenchmarkResultImpl implements BenchmarkResult {

    private final boolean hasStarted;
    private final int progress;
    private final Map<String, BenchmarkMetric> metricMap;

    public static BenchmarkResult empty() {
        return new BenchmarkResultImpl();
    }

    public static BenchmarkResult create(int progress, Map<String, BenchmarkMetric> metricMap) {
        return new BenchmarkResultImpl(progress, metricMap);
    }

    public static BenchmarkResult update(
            BenchmarkResult benchmarkResult, BenchmarkMetric benchmarkMetric) {
        Map<String, BenchmarkMetric> metricMap = benchmarkResult.getMetricsMap();
        metricMap.put(benchmarkMetric.getTitle(), benchmarkMetric);
        return new BenchmarkResultImpl(benchmarkResult.getProgress(), metricMap);
    }

    private BenchmarkResultImpl(int progress, Map<String, BenchmarkMetric> metricMap) {
        this.hasStarted = true;
        this.progress = progress;
        this.metricMap = metricMap;
    }

    private BenchmarkResultImpl() {
        this.hasStarted = false;
        this.progress = 0;
        this.metricMap = new HashMap<>();
    }

    @Override
    public boolean hasStarted() {
        return hasStarted;
    }

    @Override
    public int getProgress() {
        return progress;
    }

    @Nullable
    @Override
    public BenchmarkMetric getMetric(String title) {
        return metricMap.get(title);
    }

    @Override
    public Collection<BenchmarkMetric> getMetrics() {
        return metricMap.values();
    }

    @Override
    public Map<String, BenchmarkMetric> getMetricsMap() {
        return metricMap;
    }
}
