package dev.minhazav.camera2benchmark.benchmark;

import java.util.Collection;
import java.util.Map;

public interface ObservableBenchmark {

    void addObserver(BenchmarkResultObserver benchmarkResultObserver);

    void removeObserver(BenchmarkResultObserver benchmarkResultObserver);

    void notify(BenchmarkResult benchmarkResult);

    interface BenchmarkResultObserver {

        void onBenchmarkProgress(BenchmarkResult benchmarkResult);
    }

    interface BenchmarkResult {

        boolean hasStarted();

        int getProgress();

        Collection<BenchmarkMetric> getMetrics();

        Map<String, BenchmarkMetric> getMetricsMap();

        BenchmarkMetric getMetric(String title);

        interface BenchmarkMetric {

            String getTitle();
            float getValue();
            String getMetric();
        }
    }
}
