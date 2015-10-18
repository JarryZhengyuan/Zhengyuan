package com.example.jarry.projectpsm;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class PasswordChangeActivity extends ActionBarActivity {

    private int ID=-1;
    private Intent intent;
    DatabasePerSell myDb;

    private EditText etOld,etNew,etConfirm;
    private String oldPass,newPass,confirmPass;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        ID=getIntent().getExtras().getInt("ID");

        if(ID==-1){
            intent=new Intent(PasswordChangeActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        myDb=new DatabasePerSell(PasswordChangeActivity.this);

        etOld=(EditText)findViewById(R.id.etOld);
        etNew=(EditText)findViewById(R.id.etNew);
        etConfirm=(EditText)findViewById(R.id.etConfirm);
        btn_submit=(Button)findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPass=etOld.getText().toString();
                newPass=etNew.getText().toString();
                confirmPass=etConfirm.getText().toString();

                if(!oldPass.equals(myDb.getUserLoginPassword(ID))){
                    showMessageToast("Your Old Password is wrong");
                    return;
                }else if(newPass.length()<8 || confirmPass.length()<8)
                {
                    showMessageToast("Minimum 8 letters or digits");
                    return;
                }else{

                    boolean isUpdatedLogin=myDb.updateUserLoginData(newPass,ID);

                    if(isUpdatedLogin==true){
                    intent=new Intent(PasswordChangeActivity.this,ProfileActivity.class);
                    intent.putExtra("ID",ID);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);}
                    else{
                        showMessageToast("Fail update");
                        return;
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_password_change, menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF7DA7FF")));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==android.R.id.home){
            intent=new Intent(PasswordChangeActivity.this,ProfileActivity.class);
            intent.putExtra("ID",ID);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showMessageToast(String message) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
    }
}
