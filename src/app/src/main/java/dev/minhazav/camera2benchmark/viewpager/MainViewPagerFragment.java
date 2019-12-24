package dev.minhazav.camera2benchmark.topview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.minhazav.camera2benchmark.R;

public class MainViewPagerFragment extends Fragment {

    public MainViewPagerFragment() {
        // Required empty public constructor
    }

    public static MainViewPagerFragment newInstance() {
        return new MainViewPagerFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_view_pager, container, false);
    }
}
