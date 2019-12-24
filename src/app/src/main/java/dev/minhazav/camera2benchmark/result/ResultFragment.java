package dev.minhazav.camera2benchmark.result;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.minhazav.camera2benchmark.R;

public class ResultFragment extends Fragment {

   private ResultFragment() {
        // Required empty public constructor
    }

    public static ResultFragment create() {
        return new ResultFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }
}
