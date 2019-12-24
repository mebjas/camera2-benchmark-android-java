package dev.minhazav.camera2benchmark.viewpager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import dev.minhazav.camera2benchmark.MainFragment;
import dev.minhazav.camera2benchmark.R;

import static dev.minhazav.camera2benchmark.viewpager.MainViewPagerAdapter.DemoObjectFragment.ARG_OBJECT;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    private static MainViewPagerAdapter instance;
    private static String LOG_TAG = MainViewPagerAdapter.class.getCanonicalName();

    private Map<String, Fragment> fragmentMap = new HashMap<>();
    private LinkedHashMap<String, Callable<Fragment>> fragmentGeneratorMap = new LinkedHashMap<>();

    public static MainViewPagerAdapter getInstance(FragmentManager fragmentManager) {
        if (instance == null) {
            instance = new MainViewPagerAdapter(fragmentManager);
        }

        return instance;
    }

    private MainViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

        fragmentGeneratorMap.put("Main", MainFragment::create);
        fragmentGeneratorMap.put("Result", getDemoFragmentFactory(2));
        fragmentGeneratorMap.put("API Probe", getDemoFragmentFactory(3));
    }

    private Callable<Fragment> getDemoFragmentFactory(int arg) {
        return () -> {
            DemoObjectFragment demoObjectFragment = new DemoObjectFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_OBJECT, arg);
            demoObjectFragment.setArguments(args);
            return demoObjectFragment;
        };
    }

    @Override
    public Fragment getItem(int position) {
        assert position > 0;
        assert position < fragmentGeneratorMap.size();

        String title = fragmentGeneratorMap.keySet().toArray()[position].toString();
        if (!fragmentMap.containsKey(title)) {
            try {
                fragmentMap.put(title, fragmentGeneratorMap.get(title).call());
            } catch (Exception ex) {
                Log.e(LOG_TAG, "Fragment factory failed.");
                return null;
            }
        }

        return fragmentMap.get(title);
    }

    @Override
    public int getCount() {
        return fragmentGeneratorMap.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        assert position > 0;
        assert position < fragmentGeneratorMap.size();

        return fragmentGeneratorMap.keySet().toArray()[position].toString();
    }

    public static class DemoObjectFragment extends Fragment {
        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(
                LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.demo_fragment, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            Bundle args = getArguments();
            ((TextView) view.findViewById(R.id.demo_item))
                    .setText(Integer.toString(args.getInt(ARG_OBJECT)));
        }
    }
}
