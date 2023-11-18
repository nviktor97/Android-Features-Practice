package com.example.navdraw.ViewPagerPack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.navdraw.R;
import com.google.android.material.tabs.TabLayout;

public class ViewpagerFragment extends Fragment {

    public static final String TAG = ViewpagerFragment.class.getName();

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.viewpager_fragment, container, false);

        viewPager = v.findViewById(R.id.viewpager);

        // setting up the adapter
        viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());

        // add the fragments
        viewPagerAdapter.add(new Page1(), "Page 1");
        viewPagerAdapter.add(new Page2(), "Page 2");
        viewPagerAdapter.add(new Page3(), "Page 3");

        // Set the adapter
        viewPager.setAdapter(viewPagerAdapter);

        // The Page (fragment) titles will be displayed in the
        // tabLayout hence we need to  set the page viewer
        // we use the setupWithViewPager().
        tabLayout = v.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }
}
