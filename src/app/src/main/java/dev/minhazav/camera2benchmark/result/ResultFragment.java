package dev.minhazav.camera2benchmark.result;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import dev.minhazav.camera2benchmark.R;
import dev.minhazav.camera2benchmark.benchmark.BenchmarkResultImpl;
import dev.minhazav.camera2benchmark.benchmark.ObservableBenchmark;
import dev.minhazav.camera2benchmark.benchmark.ObservableBenchmark.BenchmarkResult;
import dev.minhazav.camera2benchmark.benchmark.ObservableBenchmark.BenchmarkResultObserver;
import dev.minhazav.camera2benchmark.benchmark.ObservableBenchmarkImpl;

public final class ResultFragment extends Fragment {

    private final ObservableBenchmark observableBenchmark = ObservableBenchmarkImpl.getInstance();

    private ImageView resultVectorImageView;
    private TextView subTitleTextView;

    private ResultFragment() {}

    public static ResultFragment create() {
        return new ResultFragment();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resultVectorImageView = view.findViewById(R.id.result_vector);
        subTitleTextView = view.findViewById(R.id.result_subtitle);

        updateView(BenchmarkResultImpl.empty());
        observableBenchmark.addObserver(new BenchmarkResultObserverImpl());
    }

    private void updateView(BenchmarkResult benchmarkResult) {
        if (benchmarkResult.hasStarted()) {
            if (benchmarkResult.getProgress() < 100) {
                resultVectorImageView.setVisibility(View.VISIBLE);
                resultVectorImageView.setImageDrawable(
                        getContext().getDrawable(R.drawable.ic_progress));
                subTitleTextView.setText(R.string.result_fragment_subtitle_in_progress);
            } else {
                resultVectorImageView.setVisibility(View.GONE);
                subTitleTextView.setText(R.string.result_fragment_subtitle_finished);
            }
        } else {
            subTitleTextView.setText(R.string.result_fragment_subtitle_na);
            resultVectorImageView.setImageDrawable(
                    getContext().getDrawable(R.drawable.ic_goal_bold));
            resultVectorImageView.setVisibility(View.VISIBLE);
        }
    }

    private final class BenchmarkResultObserverImpl implements BenchmarkResultObserver {
        @Override
        public void onBenchmarkProgress(BenchmarkResult benchmarkResult) {
            // TODO(mebjas): check for fragment lifecycle?
            updateView(benchmarkResult);
        }
    }
}
