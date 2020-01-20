package com.example.restro.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.restro.MainActivity;
import com.example.restro.R;
import com.example.restro.my_db.DatabaseClient;
import com.example.restro.my_db.my_entity.Add_on_Table;
import com.example.restro.my_db.my_entity.MyMenuTable;
import com.example.restro.my_db.my_entity.ProductTable;
import java.util.ArrayList;
import java.util.List;

public class AddOnActivity extends AppCompatActivity {
    private EditText add_on_name;
    private Spinner spinner_product_Code,add_on_product_spinner_name;
   private Button btn_add_on_save;
   private String add_name,product_Id,product_name,spinner_code,product_spiiner_code;
   private String selectValue,categoryName,getProductName;


   private int size,categoryId,productId;
    private List<ProductTable> myOrderList=new ArrayList<>();
    private ArrayList<String> product_ID=new ArrayList<>();
    private ArrayList<String> product_Name=new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_on_activity );
        getSupportActionBar().setTitle("Add_On Screen");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getMenu();
        getProductMenu();
        setUpUi();
        spinner_product_Code.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectValue=spinner_product_Code.getSelectedItem().toString();
                getProductMenu();
                getCategoryId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );

        add_on_product_spinner_name.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                add_name=add_on_name.getText().toString().trim();
                getProductId();
//                product_spiiner_code=add_on_product_spinner_name.getSelectedItem().toString();
//                getProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );
        add_on_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                add_name=add_on_name.getText().toString().trim();
                getCategory();
            }
        });

//        add_on_name.addTextChangedListener( new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                add_name=add_on_name.getText().toString().trim();
//                spinner_code=spinner_product_Code.getSelectedItem().toString();
//                product_spiiner_code=add_on_product_spinner_name.getSelectedItem().toString();
//                getProducts();
//            }
//        } );
        btn_add_on_save.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getProductId();
                getCategoryId();
                saveCategory();
//                if (size>0){
//                    getCategoryId();
//                    getProductId();
//                    Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
//                }
//                if (size==0){
//                    getCategoryId();
//                    getProductId();
//                   saveCategory();
//                }
            }
        } );



    }

    private void setUpUi() {
        btn_add_on_save=findViewById( R.id.btn_add_on_save);
        add_on_name=findViewById( R.id.add_on_name );
        spinner_product_Code=findViewById( R.id.spinner_product_Codde );
        add_on_product_spinner_name=findViewById( R.id.add_on_product_spinner_name );
    }


    @Override
    public boolean onSupportNavigateUp() {
        Intent intent=new Intent( AddOnActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent( AddOnActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }



//    private void getProducts() {
//        add_name=add_on_name.getText().toString().trim();
//        spinner_code=spinner_product_Code.getSelectedItem().toString();
//        class GetTasks extends AsyncTask<Void, Void, List<ProductTable>> {
//
//            @Override
//            protected List<ProductTable> doInBackground(Void... voids) {
//                List<ProductTable> stores = DatabaseClient
//                        .getInstance(getApplicationContext())
//                        .getAppDatabase()
//                        .productDao()
//                        .getDuplicateProduct(add_name,spinner_code);
//
//                return stores;
//            }
//
//            @Override
//            protected void onPostExecute(List<ProductTable> mystore) {
//                super.onPostExecute(mystore);
//                size=mystore.size();
//            }
//
//        }
//        GetTasks gt=new GetTasks();
//        gt.execute();
//    }
    private void getMenu(){
        class GetTasks extends AsyncTask<Void, Void, ArrayList<String>> {

            @Override
            protected ArrayList<String> doInBackground(Void... voids) {
                List<MyMenuTable> stores = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .myMenuDao()
                        .getAll();
                product_ID.add( "Please Select" );
                for (int i=0;i<stores.size();i++){
                    product_ID.add(stores.get(i).getCategory_name());
                }
                return product_ID;
            }

            @Override
            protected void onPostExecute(ArrayList<String> mystore) {
                super.onPostExecute(mystore);
                ArrayAdapter<String> adp1 = new ArrayAdapter<String>(AddOnActivity.this,
                        android.R.layout.simple_list_item_1, mystore);
                adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_product_Code.setAdapter(adp1);
//                selectValue=spinner_product_Code.getSelectedItem().toString();
//                getProductMenu();
            }

        }
        GetTasks gt=new GetTasks();
        gt.execute();
    }
    private void getProductMenu(){
        class GetTasks extends AsyncTask<Void, Void, ArrayList<String>> {

            @Override
            protected ArrayList<String> doInBackground(Void... voids) {
                List<ProductTable> stores = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .productDao()
                        .getAllByProductCode(selectValue);
                product_Name.clear();
                product_Name.add( "Default" );
                for (int i=0;i<stores.size();i++){
                    product_Name.add(stores.get(i).getProduct_name());
                }

                return product_Name;
            }

            @Override
            protected void onPostExecute(ArrayList<String> mystore) {
                super.onPostExecute(mystore);
                ArrayAdapter<String> adp1 = new ArrayAdapter<String>(AddOnActivity.this,
                        android.R.layout.simple_list_item_1, mystore);
             adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                add_on_product_spinner_name.setAdapter(adp1);

            }

        }
        GetTasks gt=new GetTasks();
        gt.execute();
    }
//    private void getCategoryId(){
//        class GetTasks extends AsyncTask<Void, Void, List<MyMenuTable>> {
//
//            @Override
//            protected List<MyMenuTable> doInBackground(Void... voids) {
//                List<MyMenuTable> stores = DatabaseClient
//                        .getInstance(getApplicationContext())
//                        .getAppDatabase()
//                        .myMenuDao()
//                        .getAllByCode(selectValue);
//
//
//                return stores;
//            }
//
//            @Override
//            protected void onPostExecute(List<MyMenuTable> mystore) {
//                super.onPostExecute(mystore);
//                categoryName=mystore.get( 0 ).getCategory_name();
//
//            }
//
//        }
//        GetTasks gt=new GetTasks();
//        gt.execute();
//    }

    private void getCategory() {
        add_name=add_on_name.getText().toString().trim();
        class GetTasks extends AsyncTask<Void, Void, List<Add_on_Table> >{

            @Override
            protected List<Add_on_Table> doInBackground(Void... voids) {
                List<Add_on_Table> stores = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .add_on_tableDao()
                        .getAllByCode(add_name);

                return stores;
            }

            @Override
            protected void onPostExecute(List<Add_on_Table> mystore) {
                super.onPostExecute(mystore);
                size=mystore.size();
            }

        }
        GetTasks gt=new GetTasks();
        gt.execute();
    }
    private void saveCategory(){
        add_name=add_on_name.getText().toString().trim();



        if (add_name.isEmpty()) {
            add_on_name.setError("Product name required");
            add_on_name.requestFocus();
            return;
        }
         if (categoryId==0){
            Toast.makeText( getApplicationContext(),"Please Try Again",Toast.LENGTH_LONG ).show();
        }
        if (productId==0){
            Toast.makeText( getApplicationContext(),"Please Try Again",Toast.LENGTH_LONG ).show();

        }
        class SaveClass extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                if(add_name!=null && !add_name.isEmpty())
                {
                    //creating a task
                    Add_on_Table tables = new Add_on_Table();
                    tables.setAdd_name(add_name);
                    tables.setCategoryId(categoryId);
                    tables.setProductId(productId);
                    //adding to database
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .add_on_tableDao()
                            .insert(tables);

                }


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                    Toast.makeText(getApplicationContext(),"aaaaaaa",Toast.LENGTH_SHORT).show();

                    getCategory();


            }


        }
        SaveClass sv=new SaveClass();
        sv.execute();

    }
    public void getCategoryId() {
        categoryName=spinner_product_Code.getSelectedItem().toString();
        class GetTasks extends AsyncTask<Void, Void, List<MyMenuTable>> {

            @Override
            protected List<MyMenuTable> doInBackground(Void... voids) {
                List<MyMenuTable> stores = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .myMenuDao()
                        .getAllByCode(categoryName);

                return stores;
            }

            @Override
            protected void onPostExecute(List<MyMenuTable> mystore) {
                super.onPostExecute(mystore);
                for (int i=0;i<mystore.size();i++){
                    categoryId=mystore.get( i ).getId();

                }
            }

        }
        GetTasks gt=new GetTasks();
        gt.execute();
    }
    public void getProductId() {
        getProductName=add_on_product_spinner_name.getSelectedItem().toString();
        class GetTasks extends AsyncTask<Void, Void, List<ProductTable>> {

            @Override
            protected List<ProductTable> doInBackground(Void... voids) {
                List<ProductTable> stores = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .productDao()
                        .getAllByCode(categoryName);

                return stores;
            }

            @Override
            protected void onPostExecute(List<ProductTable> mystore) {
                super.onPostExecute(mystore);
                for (int i=0;i<mystore.size();i++){
                    productId=mystore.get( i ).getId();

                }
            }

        }
        GetTasks gt=new GetTasks();
        gt.execute();
    }



    }



