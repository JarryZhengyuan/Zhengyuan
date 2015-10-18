package com.example.jarry.projectpsm;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;


public class EditProfileActivity extends ActionBarActivity {

    private int ID=-1;
    private Intent intent;
    DatabasePerSell myDb;

    private EditText etName,etPhone,etEmail,etAddress,etPoskod,etCity,etFacebook,etUserDetail;
    private String name,phone,email,address,poskod,city,facebook,userdetail;
    private int state;
    private Button btn_submit;

    private byte img[];
    private ImageView pic;
    private TextView tv;
    int REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ID=getIntent().getExtras().getInt("ID");

        if(ID==-1){
            intent=new Intent(EditProfileActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        myDb=new DatabasePerSell(EditProfileActivity.this);
        etName=(EditText)findViewById(R.id.etName);
        etPhone=(EditText)findViewById(R.id.etPhone);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etAddress=(EditText)findViewById(R.id.etAddress);
        etPoskod=(EditText)findViewById(R.id.etPoskod);
        etCity=(EditText)findViewById(R.id.etCity);
        etFacebook=(EditText)findViewById(R.id.etFacebook);
        etUserDetail=(EditText)findViewById(R.id.etDetail);
        btn_submit=(Button)findViewById(R.id.btn_submit);
        pic=(ImageView)findViewById(R.id.img_photo);
        tv=(TextView)findViewById(R.id.textView7);

        dropdownState();
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(i, REQUEST_CODE);
                }
                tv.setText("");
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=etName.getText().toString();
                phone=etPhone.getText().toString();
                email=etEmail.getText().toString();
                address=etAddress.getText().toString();
                poskod=etPoskod.getText().toString();
                city=etCity.getText().toString();
                facebook=etFacebook.getText().toString();
                userdetail=etUserDetail.getText().toString();

                if(name.length()<1 || phone.length()<1 || email.length()<1 || address.length()<1 || poskod.length()<1 || city.length()<1||img==null || facebook.length()<1 || userdetail.length()<1){
                    showMessageToast("Please fill all the requests");
                }else{

                    boolean isUpdatedUser=myDb.updateUserData(ID,name,phone,email,userdetail,facebook,img);
                    if(isUpdatedUser==false){
                        showMessageToast("User Fail Updated");
                        return;
                    }

                    boolean isUpdatedUserAdd=myDb.updateUserAddData(ID,poskod,address,city,state);
                    if(isUpdatedUserAdd==false){
                        showMessageToast("Address Fail Updated");
                        return;
                    }

                    intent=new Intent(EditProfileActivity.this,ProfileActivity.class);
                    intent.putExtra("ID",ID);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
    }

    private void dropdownState() {
        final Spinner dropdown = (Spinner)findViewById(R.id.sp_State);

        Cursor res=myDb.getStatesData();
        String[] items = new String[14];
        int a=0;

        while(res.moveToNext()){
            items[a]=res.getString(1);
            a++;
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int a = position + 1;
                state = a;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
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
            intent=new Intent(EditProfileActivity.this,ProfileActivity.class);
            intent.putExtra("ID",ID);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK)
        {
            Bundle bundle=new Bundle();
            bundle=data.getExtras();
            Bitmap BMP;
            BMP=(Bitmap)bundle.get("data");
            pic.setImageBitmap(BMP);

            Bitmap photo=(Bitmap)data.getExtras().get("data");
            pic.setImageBitmap(photo);
            ByteArrayOutputStream os=new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, os);
            img=os.toByteArray();
        }
    }

    private void showMessageToast(String message) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
    }
}
