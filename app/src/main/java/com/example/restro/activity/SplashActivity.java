package com.example.restro.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.restro.MainActivity;
import com.example.restro.R;


public class SplashActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView splash_txt;
    private SharedPreferences pref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        setUp();
        pref = getSharedPreferences("user_details",MODE_PRIVATE);


        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    if(pref.contains("username") && pref.contains("password")){
                        Intent intent=new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        getSupportActionBar().hide();
        Animation splashani= AnimationUtils.loadAnimation(this, R.anim.myanimation);
        imageView.setAnimation(splashani);
        splash_txt.setAnimation(splashani);



    }


    //for link with xml
    private void setUp(){
        splash_txt=findViewById(R.id.splash_text);
        imageView=findViewById(R.id.splash_img);
    }
}
