package com.example.restro.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.restro.MainActivity;
import com.example.restro.R;
import com.example.restro.my_db.DatabaseClient;
import com.example.restro.my_db.my_entity.Users;

import java.util.List;

import static com.example.restro.my_db.DatabaseClient.getInstance;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText edtUsername,edtPassword;
    private String username,password;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.login_screen);
        setUp();
        preferences= getSharedPreferences("user_details",MODE_PRIVATE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=edtUsername.getText().toString();
                password=edtPassword.getText().toString();

                if (username.contentEquals( "admin" )&& password.contentEquals( "12345" )){

                    Intent intent=new Intent( LoginActivity.this, AdminActivity.class );
                    startActivity( intent );
                    finish();
                }
                else if(!username.equals("") && !password.equals("")){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username",username);
                    editor.putString("password",password);
                    editor.commit();
                    Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    saveTask();
                }
                else {
                    Toast.makeText( getApplicationContext(),"Please Try Again",Toast.LENGTH_LONG ).show();
                }
            }
        });
    }
    private void setUp(){
        btnLogin=findViewById(R.id.btn_login);
        edtUsername=findViewById(R.id.edt_username);
        edtPassword=findViewById(R.id.edt_pass);
    }

    private void saveTask() {
        username=edtUsername.getText().toString().trim();
        password=edtPassword.getText().toString().trim();

        class SaveClass extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                Users users = new Users();
                users.setUser_name(username);
                users.setPassword(password);

                //adding to database
                getInstance(getApplicationContext()).getAppDatabase()
                        .usersDao()
                        .insert(users);
                List<Users> myusers=DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().usersDao().getAll();
                Log.d("UserList","UserList"+myusers);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("username",username);
                editor.putString("password",password);
                editor.commit();
            }
        }
        SaveClass st = new SaveClass();
        st.execute();
    }


}
