package com.example.jarry.projectpsm;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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


public class UserRegister1Activity extends ActionBarActivity {

    DatabasePerSell myDb;
    private EditText etName,etPhone,etEmail,etAddress,etPoskod,etCity;
    private String name,phone,email,address,poskod,city,stateDes;
    private String bankName,accBank;
    private int state;
    private Button btn_Next;
    private Intent intent;

    private ImageView img_UserPhoto;
    private TextView textView;
    int REQUEST_CODE=1;
    byte img[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register1);

        //declare edit text
        etName=(EditText)findViewById(R.id.etName);
        etPhone=(EditText)findViewById(R.id.etPhone);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etAddress=(EditText)findViewById(R.id.etAddress);
        etPoskod=(EditText)findViewById(R.id.etPoskod);
        etCity=(EditText)findViewById(R.id.etCity);
        btn_Next=(Button)findViewById(R.id.btn_Next);
        img_UserPhoto=(ImageView)findViewById(R.id.img_ItemPhoto);
        textView=(TextView)findViewById(R.id.textView);

        myDb=new DatabasePerSell(UserRegister1Activity.this);
        dropdownState();
        passDataToUserRegister2();

        img_UserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(i.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(i,REQUEST_CODE);
                }
                textView.setText("");
            }
        });

    }

    private void passDataToUserRegister2() {
        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=etName.getText().toString();
                phone=etPhone.getText().toString();
                email=etEmail.getText().toString();
                address=etAddress.getText().toString();
                poskod=etPoskod.getText().toString();
                city=etCity.getText().toString();

                if(name.length()<1 || phone.length()<1 || email.length()<1 || address.length()<1 || poskod.length()<1 || city.length()<1||img==null){
                    Toast.makeText(getApplication(),"Please fill all the requests",Toast.LENGTH_LONG).show();
                }else{
                   intent=new Intent(UserRegister1Activity.this,UserRegister2Activity.class);
                    intent.putExtra("name",name);
                    intent.putExtra("phone",phone);
                    intent.putExtra("email",email);
                    intent.putExtra("address",address);
                    intent.putExtra("poskod",poskod);
                    intent.putExtra("city",city);
                    intent.putExtra("state",state);
                    intent.putExtra("stateDes",stateDes);
                    intent.putExtra("img",img);
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
                int a=position+1;
                state=a;
                stateDes=(String)dropdown.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_register1, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK)
        {
            Bundle bundle=new Bundle();
            bundle=data.getExtras();
            Bitmap BMP;
            BMP=(Bitmap)bundle.get("data");
            img_UserPhoto.setImageBitmap(BMP);

            Bitmap photo=(Bitmap)data.getExtras().get("data");
            img_UserPhoto.setImageBitmap(photo);
            ByteArrayOutputStream os=new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG,100,os);
            img=os.toByteArray();
        }
    }
}
