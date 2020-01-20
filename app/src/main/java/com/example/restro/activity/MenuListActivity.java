package com.example.restro.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restro.R;
import com.example.restro.myObj.AddOnSaveObj;
import com.example.restro.myObj.AddonSpinnerObj;
import com.example.restro.my_adapter.MenuGridAdapter;
import com.example.restro.my_adapter.MyListAdapter;
import com.example.restro.my_adapter.MyOrderListAdapter;
import com.example.restro.my_db.DatabaseClient;
import com.example.restro.my_db.my_entity.Add_on_Table;
import com.example.restro.my_db.my_entity.Alert_Dialog_Table;
import com.example.restro.my_db.my_entity.MyMenuTable;
import com.example.restro.my_db.my_entity.OrderTable;
import com.example.restro.my_db.my_entity.ProductTable;
import com.example.restro.my_interface.GridViewItemClick;
import com.example.restro.my_interface.MyGetData;
import com.example.restro.my_interface.MyitemClick;
import com.example.restro.my_interface.OrderRemove;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MenuListActivity extends AppCompatActivity implements MyitemClick, OrderRemove, MyGetData {
    private GridView gridView;
    private SharedPreferences spf;
    private int total = 0;
    private Button btnHold;
    private String totalcost, getProductNameFromAdapter;
    private int inpoint = 0;
    private int productIdFromClickAdapter;
    private String clickDataSaveFromSpinner;
    private TextView tvTotal, tvDate, tvTime, tvInPoint, tvTablename;
    private String tableName, floor, productName, date, time;
    private RecyclerView foodListRecycler, orderRecycler;
    private FloatingActionButton btnAddOrderNew;
    private List<Alert_Dialog_Table> alertDialogTableList;
    private ArrayList<String> myorderList = new ArrayList<>();
    private ArrayList<String> myprizeList = new ArrayList<>();
    private ArrayList<String> foodList = new ArrayList<>();
    private ArrayList<String> priceList = new ArrayList<>();
    private ArrayList<String> tableList = new ArrayList<>();
    private ArrayList<String> addOnListForSpinner = new ArrayList<>();
    private List<AddonSpinnerObj> addonSpinnerObjs = new ArrayList<>();
    private List<AddOnSaveObj> addOnSaveObjSpinner = new ArrayList<>();
    private AddOnSaveObj addOnSaveObj;

    private MenuGridAdapter galleryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.menu_list_screen );
        this.getSupportActionBar().setDisplayOptions( ActionBar.DISPLAY_SHOW_CUSTOM );
        getSupportActionBar().setDisplayShowCustomEnabled( true );
        getSupportActionBar().setCustomView( R.layout.custom_menu_bar );
        View view = getSupportActionBar().getCustomView();
        btnAddOrderNew = view.findViewById( R.id.btn_add_order );
        btnAddOrderNew.setEnabled( false );
        getProducts();
//        inpoint++;
        SharedPreferences.Editor editor = getPreferences( MODE_PRIVATE ).edit();
        // getting already saved count -  0 in case of first time
        int startCount = getPreferences( MODE_PRIVATE ).getInt( "count_key", inpoint );
        // update count
        startCount++;
        //restoring updated value
        getPreferences( MODE_PRIVATE ).edit().putInt( "count_key", startCount ).commit();

        inpoint = startCount;
        setUp();
        getMenu();

//        spf=getSharedPreferences("Invoice_no",MODE_PRIVATE);
//        if (spf.contains("invoice_no")){
//            inpoint= Integer.parseInt(spf.getString("invoice_no",null));
//            inpoint=inpoint+1;
//        }

        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( this, LinearLayoutManager.VERTICAL, false );
        foodListRecycler.setLayoutManager( linearLayoutManager );
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager( this, LinearLayoutManager.VERTICAL, false );
        orderRecycler.setLayoutManager( linearLayoutManager1 );
        tableName = getIntent().getStringExtra( "tablename" );
        floor = getIntent().getStringExtra( "floor" );
        tableList = getIntent().getStringArrayListExtra( "tableList" );
        tvTablename.setText( "Table :" + tableName );
        tvDate.setText( "Date :" + new SimpleDateFormat( "dd/MM/yy" ).format( new Date() ) );
        date = new SimpleDateFormat( "dd/MM/yy" ).format( new Date() );
        tvTime.setText( "Time :" + new SimpleDateFormat( "HH:mm:ss", Locale.getDefault() ).format( new Date() ) );
        time = new SimpleDateFormat( "HH:mm:ss", Locale.getDefault() ).format( new Date() );
        tvInPoint.setText( "Invoice no :" + "D" + String.valueOf( inpoint ) );
        btnAddOrderNew.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int invoiceno=inpoint;

                saveCategory();
//                SharedPreferences.Editor editor = spf.edit();
//                editor.putString("invoice_no", String.valueOf(invoiceno));
//                editor.commit();
                Intent intent = new Intent( MenuListActivity.this, Dine_InListActivity.class );
                startActivity( intent );
                finish();
            }
        } );
        btnHold.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();
            }
        } );

    }


    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( "Choose Table:" );
        final View customLayout = getLayoutInflater().inflate( R.layout.alertdialog_layout, null );
        builder.setView( customLayout );
        Spinner tableListSpinner = customLayout.findViewById( R.id.table_list_spinner );
        Button btnAlertDone = customLayout.findViewById( R.id.btn_dialog_done );
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>( this,
                android.R.layout.simple_list_item_1, tableList );
        adp1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        tableListSpinner.setAdapter( adp1 );
        btnAlertDone.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MenuListActivity.this, MyHoldOnOrderActivity.class );
                startActivity( intent );
                finish();
            }
        } );
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setUp() {
        gridView = findViewById( R.id.menu_grid );
        btnHold = findViewById( R.id.btn_hold );
        foodListRecycler = findViewById( R.id.foodRecycler );
        orderRecycler = findViewById( R.id.orderRecycler );
        tvTotal = findViewById( R.id.total_prize );
        tvTablename = findViewById( R.id.tv_order_tablename );
        tvDate = findViewById( R.id.tv_order_date );
        tvTime = findViewById( R.id.tv_order_time );
        tvInPoint = findViewById( R.id.inpoint_no );

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( MenuListActivity.this, HomeActivity.class );
        startActivity( intent );
        finish();
        return true;
    }

    private void getMenu() {
        class GetTasks extends AsyncTask<Void, Void, List<MyMenuTable>> implements GridViewItemClick {

            @Override
            protected List<MyMenuTable> doInBackground(Void... voids) {
                List<MyMenuTable> stores = DatabaseClient
                        .getInstance( getApplicationContext() )
                        .getAppDatabase()
                        .myMenuDao()
                        .getAll();

                return stores;
            }

            @Override
            protected void onPostExecute(List<MyMenuTable> mystore) {
                super.onPostExecute( mystore );
                galleryAdapter = new MenuGridAdapter( getApplicationContext(), mystore, this );
                gridView.setAdapter( galleryAdapter );
            }

            @Override
            public void gridViewItemClick(String categoryName) {
                productName = categoryName;
                getProducts();
            }
        }
        GetTasks gt = new GetTasks();
        gt.execute();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent( MenuListActivity.this, HomeActivity.class );
        startActivity( intent );
        finish();
    }


    private void getProducts() {

        class GetTasks extends AsyncTask<Void, Void, List<ProductTable>> {

            @Override
            protected List<ProductTable> doInBackground(Void... voids) {
                List<ProductTable> stores = DatabaseClient
                        .getInstance( getApplicationContext() )
                        .getAppDatabase()
                        .productDao()
                        .getAllByProductCode( productName );

                return stores;
            }

            @Override
            protected void onPostExecute(List<ProductTable> mystore) {
                super.onPostExecute( mystore );
                MyListAdapter adapter = new MyListAdapter( MenuListActivity.this, mystore, MenuListActivity.this );
                foodListRecycler.setAdapter( adapter );
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    @Override
    public void itemClick(String mylist, String prize) {
        productName = mylist;
        myorderList.add( mylist );
        myprizeList.add( prize );
        btnAddOrderNew.setEnabled( true );
        btnHold.setVisibility( View.VISIBLE );
        if (!prize.equals( "" )) {
            int myprize = Integer.parseInt( prize );
            total = total + myprize;

        }
        tvTotal.setVisibility( View.VISIBLE );
        tvTotal.setText( "Total:" + String.valueOf( total ) + "ks" );

        if (myorderList != null) {
            MyOrderListAdapter adapter1 = new MyOrderListAdapter( MenuListActivity.this, myorderList, myprizeList, total, this, this );

            orderRecycler.setAdapter( adapter1 );
            adapter1.notifyDataSetChanged();
            //getProductsPrize();
        }
    }

    @Override
    public void iteRemove(int totalPrize) {
        total = totalPrize;
        tvTotal.setText( "Total:" + String.valueOf( total ) );
    }

    private void saveCategory() {
        tableName = getIntent().getStringExtra( "tablename" );
        floor = getIntent().getStringExtra( "floor" );
        date = new SimpleDateFormat( "dd/MM/yy" ).format( new Date() );
        foodList = myorderList;
        priceList = myprizeList;
        totalcost = String.valueOf( total );
        class SaveClass extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task

                OrderTable tables = new OrderTable();
                tables.setTable_name( tableName );
                tables.setDate( date );
                tables.setFloor( floor );
                tables.setOrder_list( foodList );
                tables.setPrice_list( priceList );
                tables.setTime( time );
                tables.setInvoice( String.valueOf( inpoint ) );
                tables.setTotal( totalcost );
                tables.setStatus( "waiting" );


                //adding to database
                DatabaseClient.getInstance( getApplicationContext() ).getAppDatabase()
                        .orderTableDao()
                        .insert( tables );

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute( aVoid );
                Toast.makeText( getApplicationContext(), "save", Toast.LENGTH_SHORT ).show();

            }
        }
        SaveClass st = new SaveClass();
        st.execute();
    }

    @Override
    public void listClickInAdapter(String productId) {
        getProductNameFromAdapter = productId;
        clickDataSaveFromSpinner=getProductNameFromAdapter;

        addOnSaveObj=new AddOnSaveObj();
        addOnSaveObj.setItemSelectedId( productId );

        if (getProductNameFromAdapter != null) {
            getData();
        }
    }

    public void getData() {

        class GetTasks extends AsyncTask<Void, Void, List<ProductTable>> {

            @Override
            protected List<ProductTable> doInBackground(Void... voids) {
                List<ProductTable> stores = DatabaseClient
                        .getInstance( getApplicationContext() )
                        .getAppDatabase()
                        .productDao()
                        .getByProductName( productName );

                return stores;
            }

            @Override
            protected void onPostExecute(List<ProductTable> mystore) {
                super.onPostExecute( mystore );
                for (int i = 0; i < mystore.size(); i++) {
                    productIdFromClickAdapter = mystore.get( i ).getId();
                }
                getSpinnerList();

            }

        }
        GetTasks gt = new GetTasks();
        gt.execute();
    }

    public void getSpinnerList() {
        class GetTasks extends AsyncTask<Void, Void, List<AddonSpinnerObj>> {

            @Override
            protected List<AddonSpinnerObj> doInBackground(Void... voids) {
                List<Add_on_Table> stores = DatabaseClient
                        .getInstance( getApplicationContext() )
                        .getAppDatabase()
                        .add_on_tableDao()
                        .getAllByProductId( productIdFromClickAdapter );
                for (int i = 0; i < stores.size(); i++) {
                    AddonSpinnerObj addonSpinnerObj = new AddonSpinnerObj();
                    addonSpinnerObj.setAddOnName( stores.get( i ).getAdd_name() );
                    addonSpinnerObjs.add( addonSpinnerObj );
                }
//              for (int j=0;j<stores.size();j++){
//
//                  AddOnSaveObj addOnSaveObj=new AddOnSaveObj();
//                  addOnSaveObj.setAddOnSaveSpinner( stores.get( j ).getAdd_name() );
//                  addOnSaveObjSpinner.add( addOnSaveObj );
//
//              }

                return addonSpinnerObjs;

            }

            @Override
            protected void onPostExecute(List<AddonSpinnerObj> mystore) {
                super.onPostExecute( mystore );
//                for (int i=0;i<addOnSaveObjSpinner.size();i++){
//                    clickDataSaveFromSpinner=addOnSaveObjSpinner.get( i ).getItemSelectedId();
//
//
//                }
                addOnListForSpinner.clear();
                addOnListForSpinner.add( "Please Select" );
                for (int i = 0; i < mystore.size(); i++) {
                    addOnListForSpinner.add( mystore.get( i ).getAddOnName() );
                }


                if (addOnListForSpinner != null) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder( MenuListActivity.this );
                    builder1.setTitle( "Add_On" );
                    final View customLayout = LayoutInflater.from( MenuListActivity.this ).inflate( R.layout.add_on_custon_dialog_activity, null );
                    builder1.setView( customLayout );
                    final AlertDialog dialog = builder1.create();
                    dialog.getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED );
                    final Spinner tableListSpinner = customLayout.findViewById( R.id.add_on_list_spinner );
                    Button btnAlertDone = customLayout.findViewById( R.id.btn_dialog_save );
                    ArrayAdapter<String> adp1 = new ArrayAdapter<String>( MenuListActivity.this,
                            android.R.layout.simple_list_item_1, addOnListForSpinner );
                    adp1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                    tableListSpinner.setAdapter( adp1 );
//                    tableListSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                            clickDataSaveFromSpinner=tableListSpinner.getSelectedItem().toString();
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                        }
//                    } );
                    if (clickDataSaveFromSpinner!=null){
                        for (int i=0;i<tableListSpinner.getCount();i++){
                            if (tableListSpinner.getItemAtPosition( i ).toString().equals( clickDataSaveFromSpinner )){
                                tableListSpinner.setSelection( i );
                            }
                        }
                    }
                    btnAlertDone.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickDataSaveFromSpinner=tableListSpinner.getSelectedItem().toString();
//                            AddOnSaveObj getAddonSave=new AddOnSaveObj();
//                            getAddonSave.setAddOnSaveSpinner( clickDataSaveFromSpinner );
                            addOnSaveObj.setItemSelectedId( getProductNameFromAdapter );
                            addOnSaveObjSpinner.add( addOnSaveObj );
                           dialog.dismiss();
                        }
                    } );

                    dialog.show();

                }

            }

        }
        GetTasks gt = new GetTasks();
        gt.execute();


    }


}

