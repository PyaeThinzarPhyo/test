package com.example.restro.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.restro.R;
import com.example.restro.my_adapter.TakeAwayAdapter;
import com.example.restro.my_db.DatabaseClient;
import com.example.restro.my_db.my_entity.OrderTable;
import com.example.restro.my_db.my_entity.Take_order_Table;

import java.util.List;

public class Take_away_ListActivity extends AppCompatActivity {
    private RecyclerView TakeRecy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout. take_new_screen_activity);
        TakeRecy=findViewById(R.id.take_new_recycler);
        getSupportActionBar().setTitle("Take Away List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        TakeRecy.setLayoutManager(linearLayoutManager);
        getTas();


    }

    private void getTas() {

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

                TakeAwayAdapter showOrderListAdapter1=new TakeAwayAdapter( Take_away_ListActivity.this,student);
                TakeRecy.setAdapter(showOrderListAdapter1);

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent=new Intent( Take_away_ListActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent( Take_away_ListActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
