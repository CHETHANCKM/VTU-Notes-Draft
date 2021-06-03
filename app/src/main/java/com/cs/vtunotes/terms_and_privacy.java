package com.cs.vtunotes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class terms_and_privacy extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_privacy);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);


        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("R!");
        arrayList.add("R@");

        prepareviewpager(viewPager, arrayList);


    }

    private void prepareviewpager(ViewPager viewPager, ArrayList<String> arrayList) {

        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager());

    }

    private class MainAdapter extends FragmentPagerAdapter {

        public MainAdapter(@NonNull FragmentManager fm) {
            super(fm);

            ArrayList<String> arrayList = new ArrayList<>();
            List<Fragment> fragments = new ArrayList<>();
        }

        public MainAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }
        
        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }
}