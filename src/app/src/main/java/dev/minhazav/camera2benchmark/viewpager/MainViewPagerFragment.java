package dev.minhazav.camera2benchmark.viewpager;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import dev.minhazav.camera2benchmark.R;

public class MainViewPagerFragment extends Fragment {

    MainViewPagerAdapter mainViewPagerAdapter;
    ViewPager viewPager;

    private MainViewPagerFragment() {
        // Required empty public constructor
    }

    public static MainViewPagerFragment create() {
        return new MainViewPagerFragment();
    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_view_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mainViewPagerAdapter = MainViewPagerAdapter.getInstance(getChildFragmentManager());
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(mainViewPagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
