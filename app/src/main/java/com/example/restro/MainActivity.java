package com.example.restro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restro.activity.AddNewCategoryActivity;
import com.example.restro.activity.AddOnActivity;
import com.example.restro.activity.AddNewProductActivity;
import com.example.restro.activity.AdminActivity;
import com.example.restro.activity.LoginActivity;
import com.example.restro.my_adapter.TabsAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity  {
private ViewPager viewPager;
private TabLayout tabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        prf = getSharedPreferences("user_details",MODE_PRIVATE);
//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        mylogintext = (TextView) headerView.findViewById(R.id.login_email);
//        profileImg=headerView.findViewById(R.id.my_profile_image);
//        mylogintext.setText(prf.getString("username",null));
//        userName=prf.getString("username",null);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.pager);
        tabLayout.addTab(tabLayout.newTab().setText("First Floor"));
        tabLayout.addTab(tabLayout.newTab().setText("Second Floor"));
        tabLayout.addTab(tabLayout.newTab().setText("Outside"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        for(int i=0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 100, 0);
            tab.requestLayout();
        }

        final TabsAdapter adapter = new TabsAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent=new Intent(MainActivity.this, AdminActivity.class);
        startActivity(intent);
        finish();
        return true;
    }


}

