package com.example.restro.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restro.R;
import com.example.restro.my_adapter.MenuGridAdapter;
import com.example.restro.my_adapter.MyListAdapter;
import com.example.restro.my_adapter.MyOrderListAdapter;
import com.example.restro.my_db.DatabaseClient;
import com.example.restro.my_db.my_entity.MyMenuTable;
import com.example.restro.my_db.my_entity.OrderTable;
import com.example.restro.my_db.my_entity.ProductTable;
import com.example.restro.my_db.my_entity.Take_order_Table;
import com.example.restro.my_interface.GridViewItemClick;
import com.example.restro.my_interface.MyGetData;
import com.example.restro.my_interface.MyitemClick;
import com.example.restro.my_interface.OrderRemove;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TakeAwayMenuListScreenActivity extends AppCompatActivity implements MyitemClick, OrderRemove, MyGetData {
    private RecyclerView take_food_recycler,take_order_recyclerview;
    private GridView Take_Grid;
    private MenuGridAdapter galleryAdapter1;
//    private SharedPreferences spf;
    private TextView Total,TDate,TTime,tvInpoint;
    private String productname,Date,Time;
    private FloatingActionButton btnAddOrder;
    private int total=0;
    private String totalcost;
    private Button btnHold;
    private int inpoint=0;
    private ArrayList<String> myorderList=new ArrayList<>();
    private ArrayList<String> myprizeList=new ArrayList<>();
    private ArrayList<String> foodList=new ArrayList<>();
    private ArrayList<String> priceList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.take_away_activity);
        setUpUi();
        getMenu();
//        inpoint++;


        // getting shared preferences.
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        // getting already saved count -  0 in case of first time
        int startCount = getPreferences(MODE_PRIVATE).getInt("Invoice",inpoint);
        // update count
        startCount++;
        //restoring updated value
        getPreferences(MODE_PRIVATE).edit().putInt("Invoice",startCount).commit();

        inpoint = startCount;
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_take_bar);
        View view = getSupportActionBar().getCustomView();
        btnAddOrder=view.findViewById(R.id.btn_order);
//        spf=getSharedPreferences("Invoice_no",MODE_PRIVATE);
//        if (spf.contains("invoice_no")){
//           inpoint= Integer.parseInt(spf.getString("invoice_no",null));
//            inpoint=inpoint+1;
//        }
        btnAddOrder.setEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        take_food_recycler.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        take_order_recyclerview.setLayoutManager(linearLayoutManager1);
        TDate.setText("Date :"+new SimpleDateFormat("dd/MM/yy").format(new Date()));
        Date=new SimpleDateFormat("dd/MM/yy").format(new Date());
        TTime.setText("Time :"+new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
        Time=new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        tvInpoint.setText("Invoice no :"+"T"+String.valueOf(inpoint));



        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int invoiceno=inpoint;
                saveClass();
//                SharedPreferences.Editor editor = spf.edit();
//                editor.putString("invoice_no", String.valueOf(invoiceno));
//                editor.commit();
                Intent intent=new Intent( TakeAwayMenuListScreenActivity.this, Take_away_ListActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void setUpUi() {
        take_food_recycler=findViewById( R.id.foodRecycler_take );
        Take_Grid=findViewById( R.id.menu_grid );
        btnHold=findViewById(R.id.btn_hold);
        take_order_recyclerview=findViewById(R.id.take_away_order_recyclerview);
        Total=findViewById(R.id.total_prize);
        TDate=findViewById(R.id.tv_order_date);
        TTime=findViewById(R.id.tv_order_time);
        tvInpoint=findViewById(R.id.inpoint_no);
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent( TakeAwayMenuListScreenActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent=new Intent( TakeAwayMenuListScreenActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
        return true;
    }
    private void getMenu(){
        class GetTasks extends AsyncTask<Void, Void, List<MyMenuTable>> implements GridViewItemClick {

            @Override
            protected List<MyMenuTable> doInBackground(Void... voids) {
                List<MyMenuTable> stores = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .myMenuDao()
                        .getAll();

                return stores;
            }

            @Override
            protected void onPostExecute(List<MyMenuTable> mystore) {
                super.onPostExecute(mystore);
                galleryAdapter1 = new MenuGridAdapter(getApplicationContext(),mystore,this);
                Take_Grid.setAdapter(galleryAdapter1);
            }

            @Override
            public void gridViewItemClick(String categoryName) {
                productname=categoryName;
                getProducts();
            }
        }
        GetTasks gt=new GetTasks();
        gt.execute();
    }

    private void getProducts() {

        class GetTasks extends AsyncTask<Void, Void, List<ProductTable>> {

            @Override
            protected List<ProductTable> doInBackground(Void... voids) {
                List<ProductTable> stores = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .productDao()
                        .getAllByProductCode(productname);

                return stores;
            }

            @Override
            protected void onPostExecute(List<ProductTable> mystore) {
                super.onPostExecute(mystore);
                MyListAdapter adapter = new MyListAdapter( TakeAwayMenuListScreenActivity.this, mystore, (MyitemClick) TakeAwayMenuListScreenActivity.this );
                take_food_recycler.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }
    @Override
    public void itemClick(String mylist, String prize) {
        productname=mylist;
        myorderList.add(mylist);
        myprizeList.add(prize);
        btnAddOrder.setEnabled(true);
        btnHold.setVisibility( View.VISIBLE);
        if (!prize.equals("")){
            int myprize= Integer.parseInt(prize);
            total=total+myprize;

        }
        Total.setVisibility(View.VISIBLE);
        Total.setText("Total:"+String.valueOf(total)+"ks");

        if (myorderList!=null){
            MyOrderListAdapter adapter1 = new MyOrderListAdapter( TakeAwayMenuListScreenActivity.this, myorderList,myprizeList,total,this,this);
            take_order_recyclerview.setAdapter(adapter1);
            //getProductsPrize();
        }
    }
    @Override
    public void iteRemove(int totalPrize) {
        total=totalPrize;
        Total.setText("Total:"+String.valueOf(total));
    }
    private void saveClass(){
        Date=new SimpleDateFormat("dd/MM/yy").format(new Date());
        foodList=myorderList;
        priceList=myprizeList;
        totalcost=String.valueOf(total);
        class SaveClass extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task

//                OrderTable tables = new OrderTable();
//                tables.setDate(Date);
//                tables.setOrder_list(foodList);
//                tables.setPrice_list(priceList);
//                tables.setTime(Time);
//                tables.setTotal(totalcost);
//                tables.setInvoice( String.valueOf( inpoint ) );
//                tables.setStatus("waiting");
                Take_order_Table table=new Take_order_Table();
                table.setDate( Date );
                table.setOrder_list( foodList );
                table.setPrice_list( priceList );
                table.setTime( Time );
                table.setTotal( totalcost );
                table.setTinvoice( String.valueOf( inpoint ) );
                table.setStatus( "waiting" );

                //adding to database
//                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
//                        .orderTableDao()
//                        .insert(tables);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .take_order_tableDao()
                       .insert(table);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(),"save",Toast.LENGTH_SHORT).show();

            }
        }
        SaveClass st = new SaveClass();
        st.execute();
    }

    @Override
    public void listClickInAdapter(String productId) {

    }
}
