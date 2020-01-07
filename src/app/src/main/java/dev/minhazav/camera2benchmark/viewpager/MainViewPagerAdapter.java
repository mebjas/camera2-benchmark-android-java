package dev.minhazav.camera2benchmark.viewpager;

import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import dev.minhazav.camera2benchmark.benchmark.BenchmarkFragment;
import dev.minhazav.camera2benchmark.apiprobe.ApiProbeFragment;
import dev.minhazav.camera2benchmark.result.ResultFragment;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    private static MainViewPagerAdapter instance;
    private static String LOG_TAG = MainViewPagerAdapter.class.getCanonicalName();

    private Map<String, Fragment> fragmentMap = new HashMap<>();
    private LinkedHashMap<String, Callable<Fragment>> fragmentGeneratorMap = new LinkedHashMap<>();

    public static MainViewPagerAdapter getInstance(FragmentManager fragmentManager) {
        // TODO(mebjas): for thread safety add lock
        if (instance == null) {
            instance = new MainViewPagerAdapter(fragmentManager);
        }

        return instance;
    }

    private MainViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

        fragmentGeneratorMap.put("Result", ResultFragment::create);
        fragmentGeneratorMap.put("Main", BenchmarkFragment::create);
        fragmentGeneratorMap.put("API Probe", ApiProbeFragment::create);
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

    @Override
    public CharSequence getPageTitle(int position) {
        assert position > 0;
        assert position < fragmentGeneratorMap.size();

        return fragmentGeneratorMap.keySet().toArray()[position].toString();
    }
}
