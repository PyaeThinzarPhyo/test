package com.example.restro.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.restro.MainActivity;
import com.example.restro.R;
import com.example.restro.my_db.DatabaseClient;
import com.example.restro.my_db.my_entity.MyMenuTable;

import java.util.List;

public class AddNewCategoryActivity extends AppCompatActivity {
    private EditText edtCategoryName;
    private String categoryname;
    private Button btnAddCategory;
    private int mysize;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_category_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add New Category");
        setUp();
        edtCategoryName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                categoryname=edtCategoryName.getText().toString().trim();
                getCategory();
            }
        });

        btnAddCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                categoryname=edtCategoryName.getText().toString().trim();
                getCategory();
                if (mysize>0) {
                    Toast.makeText(getApplicationContext(),"Category Already exit",Toast.LENGTH_SHORT).show();
                }
                else {
                    saveCategory();
                }

            }
        });
    }
    private void setUp(){
        edtCategoryName=findViewById(R.id.edt_category_name);
        btnAddCategory=findViewById(R.id.btn_add_category);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent=new Intent(AddNewCategoryActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    private void saveCategory(){
        categoryname=edtCategoryName.getText().toString().trim();

        if (categoryname.isEmpty()) {
            edtCategoryName.setError("Product name required");
            edtCategoryName.requestFocus();
            return;
        }


        class SaveClass extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                MyMenuTable tables = new MyMenuTable();
                tables.setCategory_name(categoryname);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .myMenuDao()
                        .insert(tables);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(),"saved",Toast.LENGTH_SHORT).show();
                getCategory();

            }
        }
        SaveClass st = new SaveClass();
        st.execute();
    }
    private void getCategory() {
        categoryname=edtCategoryName.getText().toString().trim();
        class GetTasks extends AsyncTask<Void, Void, List<MyMenuTable>> {

            @Override
            protected List<MyMenuTable> doInBackground(Void... voids) {
                List<MyMenuTable> stores = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .myMenuDao()
                        .getAllByCode(categoryname);

                return stores;
            }

            @Override
            protected void onPostExecute(List<MyMenuTable> mystore) {
                super.onPostExecute(mystore);
                mysize=mystore.size();
            }

        }
        GetTasks gt=new GetTasks();
        gt.execute();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(AddNewCategoryActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
