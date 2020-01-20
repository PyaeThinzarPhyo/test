package com.example.restro.my_adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.restro.my_fragment.FirstFloorFragment;
import com.example.restro.my_fragment.OusiteFragment;
import com.example.restro.my_fragment.SecondFloorFragment;

public class TabsAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;

    public TabsAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FirstFloorFragment homeFragment = new FirstFloorFragment();
                return homeFragment;
            case 1:
                SecondFloorFragment sportFragment = new SecondFloorFragment();
                return sportFragment;
            case 2:
                OusiteFragment movieFragment = new OusiteFragment();
                return movieFragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
