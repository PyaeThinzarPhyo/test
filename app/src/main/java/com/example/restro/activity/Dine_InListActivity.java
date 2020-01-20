package com.example.restro.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restro.R;
import com.example.restro.my_adapter.ShowOrderListAdapter;
import com.example.restro.my_adapter.TakeAwayAdapter;
import com.example.restro.my_db.DatabaseClient;
import com.example.restro.my_db.my_entity.OrderTable;
import com.example.restro.my_db.my_entity.Take_order_Table;

import java.util.List;

public class Dine_InListActivity extends AppCompatActivity {
    private RecyclerView dine_in_recyclerview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.dine_in_screen);
        dine_in_recyclerview=findViewById( R.id.dine_in_recyclerview );
        getSupportActionBar().setTitle("Dine _In List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        dine_in_recyclerview.setLayoutManager(linearLayoutManager);
        getTask();
    }
    private void getTask() {

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

                ShowOrderListAdapter showOrderListAdapter1=new ShowOrderListAdapter( Dine_InListActivity.this,student);
                dine_in_recyclerview.setAdapter(showOrderListAdapter1);

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent=new Intent( Dine_InListActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent( Dine_InListActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
