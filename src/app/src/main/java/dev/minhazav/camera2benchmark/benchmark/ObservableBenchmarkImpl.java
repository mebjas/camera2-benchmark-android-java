package dev.minhazav.camera2benchmark.benchmark;

import java.util.LinkedList;
import java.util.List;

public class ObservableBenchmarkResult implements ObservableBenchmark {

    private static ObservableBenchmarkResult instance;

    private List<BenchmarkResultObserver> observers = new LinkedList<>();

    private ObservableBenchmarkResult() {}

    public static ObservableBenchmarkResult getInstance() {
        // TODO(mebjas): for thread safety add lock
        if (instance == null) {
            instance = new ObservableBenchmarkResult();
        }

        return instance;
    }

    @Override
    public void addObserver(BenchmarkResultObserver benchmarkResultObserver) {
        // TODO(mebjas): synchronisation issue?
        observers.add(benchmarkResultObserver);
    }

    @Override
    public void removeObserver(BenchmarkResultObserver benchmarkResultObserver) {
        // TODO(mebjas): synchronisation issue?
        if (observers.contains(benchmarkResultObserver)) {
            observers.remove(benchmarkResultObserver);
        }
    }

    @Override
    public void notify(int progress, BenchmarkResult benchmarkResult) {
        // TODO(mebjas): synchronisation issue?
        for (BenchmarkResultObserver observer : observers) {
            observer.onBenchmarkProgress(progress, benchmarkResult);
        }
    }
}
