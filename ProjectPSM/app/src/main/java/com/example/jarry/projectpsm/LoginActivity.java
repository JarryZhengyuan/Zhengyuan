package com.example.jarry.projectpsm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static android.widget.Toast.LENGTH_SHORT;


public class LoginActivity extends ActionBarActivity {

    private EditText etUsername,etPassword;
    private String username,password;
    private int ID=-1;

    private Button btn_signIn,btn_Insert;
    private TextView txt_signUp;
    public Intent intent;
    DatabasePerSell myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //declare database
        myDb=new DatabasePerSell(this);

        //declare
        etUsername=(EditText)findViewById(R.id.etUsername);
        etPassword=(EditText)findViewById(R.id.etPassword);
        btn_signIn=(Button)findViewById(R.id.btn_signIn);
        btn_Insert=(Button)findViewById(R.id.btn_Insert);
        txt_signUp=(TextView)findViewById(R.id.txt_signUp);


        //button sign in click
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username=etUsername.getText().toString();
                password=etPassword.getText().toString();

                if(username.length()<1 && password.length()<1)
                {   return; }

                ID=myDb.getUserLoginData(username,password);

                intent=new Intent(LoginActivity.this,HomeActivity.class);
                intent.putExtra("ID",ID);
                startActivity(intent);
            }
        });

        btn_Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(LoginActivity.this,InsertActivity.class);
               startActivity(intent);
            }
        });

        //Text Sign up click
        txt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(LoginActivity.this,UserRegister1Activity.class);
                startActivity(intent);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showMessageToast(String message) {
        Toast.makeText(getApplication(),message,Toast.LENGTH_SHORT).show();
    }
}
