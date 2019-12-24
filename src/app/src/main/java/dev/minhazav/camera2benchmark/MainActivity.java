package dev.minhazav.camera2benchmark;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dev.minhazav.camera2benchmark.viewpager.MainViewPagerFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainViewPagerFragment.create())
                    .commit();
        }
    }
}
