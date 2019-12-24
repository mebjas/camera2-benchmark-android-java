package dev.minhazav.camera2benchmark.apiprobe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import dev.minhazav.camera2benchmark.R;

public class ApiProbeFragment extends Fragment {

    private ApiProbeFragment() {
        // Required empty public constructor
    }

    public static ApiProbeFragment create() {
        return new ApiProbeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_api_probe, container, false);
    }
}
