package com.example.restro.activity;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.restro.my_fragment.Dine_In_Fragement;
import com.example.restro.my_fragment.Take_Away_Fragement;

public class InvoiceTabAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;

    public InvoiceTabAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Dine_In_Fragement dine_in_fragement = new Dine_In_Fragement();
                return dine_in_fragement;
            case 1:
                Take_Away_Fragement take_away_fragement = new Take_Away_Fragement();
                return take_away_fragement;

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
