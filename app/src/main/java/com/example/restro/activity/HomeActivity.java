package com.example.restro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.restro.MainActivity;
import com.example.restro.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button DineIn,TakeAway,Invoice;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );
        getSupportActionBar().setTitle("Home Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpUi();
        DineIn.setOnClickListener( this );
        TakeAway.setOnClickListener( this );
        Invoice.setOnClickListener( this );
    }

    private void setUpUi() {
        DineIn=findViewById( R.id.dine_in );
        TakeAway=findViewById( R.id.take_away );
        Invoice=findViewById( R.id.invoice );

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent=new Intent( HomeActivity.this,LoginActivity.class );
        startActivity( intent );
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dine_in:
                Intent intent=new Intent(HomeActivity.this, MainActivity.class);
                startActivity( intent );
                break;
            case R.id.take_away:
                Intent intent1=new Intent(HomeActivity.this, TakeAwayMenuListScreenActivity.class);
                startActivity( intent1);
                break;
            case R.id.invoice:
                 Intent intent2=new Intent( HomeActivity.this,InvoiceActivity.class );
                 startActivity( intent2 );
                break;

        }
    }
}
