package com.example.restro.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.restro.MainActivity;
import com.example.restro.R;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mydrawer;
    private ActionBarDrawerToggle mtoggle;
    private SharedPreferences prf;
    private TextView mylogintext;
    private String userName;
    private ImageView profileImg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.admin_activity );
        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=findViewById(R.id.mynav);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        mydrawer=findViewById(R.id.my_drawer);
        mtoggle=new ActionBarDrawerToggle(this,mydrawer,R.string.open,R.string.close);
        mydrawer.addDrawerListener(mtoggle);
        mtoggle.syncState();

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();
        if (id== R.id.item_home){
            Intent intent=new Intent( AdminActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (id==R.id.item_add_product){
            Intent intent=new Intent(AdminActivity.this, AddNewProductActivity.class);
            startActivity(intent);
            finish();
        }
        if (id==R.id.item_add_category){
            Intent intent=new Intent(AdminActivity.this, AddNewCategoryActivity.class);
            startActivity(intent);
            finish();
        }
        if (id==R.id.add_on){
            Intent intent=new Intent(AdminActivity.this, AddOnActivity.class);
            startActivity(intent);
            finish();
        }

        if (id==R.id.item_setting){
            Toast.makeText(getApplicationContext(),"It is Setting",Toast.LENGTH_SHORT).show();
        }
        if (id==R.id.item_logout){
            SharedPreferences.Editor editor = prf.edit();
            editor.clear();

            editor.commit();
            Intent intent=new Intent(AdminActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return false;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
