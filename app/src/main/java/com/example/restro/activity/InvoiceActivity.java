package com.example.restro.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.restro.R;
import com.example.restro.my_adapter.ShowOrderListAdapter;
import com.example.restro.my_adapter.TabsAdapter;
import com.example.restro.my_adapter.TakeAwayAdapter;
import com.example.restro.my_db.DatabaseClient;
import com.example.restro.my_db.my_entity.OrderTable;
import com.example.restro.my_db.my_entity.Take_order_Table;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class InvoiceActivity extends AppCompatActivity implements View.OnClickListener {
      private TabLayout Tablayout;
      private ViewPager Viewpager;
//    private RecyclerView dine_in_Recyclerview;
//    private TextView Dine_In,Take_Away;
    private TakeAwayAdapter takeAwayAdapter;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_invoice );
        Tablayout=findViewById( R.id.Tablayout );
        Viewpager=findViewById( R.id.Viewpager );
        Tablayout.addTab(Tablayout.newTab().setText("Dine In"));
        Tablayout.addTab(Tablayout.newTab().setText("Take Away"));
        final InvoiceTabAdapter adapter = new InvoiceTabAdapter(this,getSupportFragmentManager(), Tablayout.getTabCount());
        Viewpager.setAdapter(adapter);
        Tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        for(int i=0; i < Tablayout.getTabCount(); i++) {
            View tab = ((ViewGroup) Tablayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 100, 0);
            tab.requestLayout();
        }



        Viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener( Tablayout ));

        Tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        dine_in_Recyclerview = findViewById( R.id.dine_in_recyclerview );
//        Dine_In = findViewById( R.id.Dine_In );
//        Take_Away = findViewById( R.id.Take_Away );
//        Dine_In.setOnClickListener( this );
//        Take_Away.setOnClickListener( this );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );


    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent=new Intent( InvoiceActivity.this,HomeActivity.class );
        startActivity( intent );
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(InvoiceActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }




    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<OrderTable>> {

            @Override
            protected List<OrderTable> doInBackground(Void... voids) {
                List<OrderTable> student = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .orderTableDao()
                        .getAll();

                return student;
            }

            @Override
            protected void onPostExecute(List<OrderTable> student) {
                super.onPostExecute(student);
                ShowOrderListAdapter showOrderListAdapter1=new ShowOrderListAdapter(InvoiceActivity.this,student);
//                dine_in_Recyclerview.setAdapter(showOrderListAdapter1);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    private void getTakeAway() {
        class GetTasks extends AsyncTask<Void, Void, List<Take_order_Table>> {

            @Override
            protected List<Take_order_Table> doInBackground(Void... voids) {
                List<Take_order_Table> student = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .take_order_tableDao()
                        .getAll();

                return student;
            }

            @Override
            protected void onPostExecute(List<Take_order_Table> student) {
                super.onPostExecute(student);
                 takeAwayAdapter=new TakeAwayAdapter(InvoiceActivity.this,student);
//                dine_in_Recyclerview.setAdapter(takeAwayAdapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.Dine_In:
//                LinearLayoutManager manager = new LinearLayoutManager( InvoiceActivity.this, LinearLayoutManager.VERTICAL, false );
//                dine_in_Recyclerview.setLayoutManager( manager );
//                getTasks();
//                break;
//            case R.id.Take_Away:
//                LinearLayoutManager manager1 = new LinearLayoutManager( InvoiceActivity.this, LinearLayoutManager.VERTICAL, false );
//                dine_in_Recyclerview.setLayoutManager( manager1 );
//                getTakeAway();
//                break;
        }
    }
}
