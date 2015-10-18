package com.example.jarry.projectpsm;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class UserRegister3Activity extends ActionBarActivity {

    private EditText etPasswordC,etPassword,etUserName;
    String password,Cpassword,username;
    private Button btn_done;
    DatabasePerSell myDb;

    private String name,phone,email,address,poskod,city,facebook,userdetail,stateDes;
    private int state;
    private byte img[];
    private  String[] bName=new String[4],bAcc=new String[4],bbName=new String[4];
    private int bID[]={-1,-1,-1,-1};
    private int BankCount;
    private TextView tvPerDetail;
    private ImageView pic;
    String BankMessage="";
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register3);

        //declare
        btn_done=(Button)findViewById(R.id.btn_done);
        etUserName=(EditText)findViewById(R.id.etUserName);
        etPassword=(EditText)findViewById(R.id.etPassword);
        etPasswordC=(EditText)findViewById(R.id.etPasswordC);
        tvPerDetail=(TextView)findViewById(R.id.tvPerDetail);
        pic=(ImageView)findViewById(R.id.pic);

     myDb=new DatabasePerSell(UserRegister3Activity.this);

       //get data from previous
        name=getIntent().getExtras().getString("name");
        phone=getIntent().getExtras().getString("phone");
        email=getIntent().getExtras().getString("email");
        address=getIntent().getExtras().getString("address");
        poskod=getIntent().getExtras().getString("poskod");
        city=getIntent().getExtras().getString("city");
        state=getIntent().getExtras().getInt("state");
        facebook=getIntent().getExtras().getString("facebook");
        userdetail=getIntent().getExtras().getString("userdetail");
        bName=getIntent().getExtras().getStringArray("bName");
       bAcc=getIntent().getExtras().getStringArray("bAcc");
        bID=getIntent().getExtras().getIntArray("bID");
        BankCount=getIntent().getExtras().getInt("BankCount");
        bbName=getIntent().getExtras().getStringArray("bbName");
        stateDes=getIntent().getExtras().getString("stateDes");
        img=getIntent().getExtras().getByteArray("img");

        bitmap=BitmapFactory.decodeByteArray(img, 0, img.length);
        pic.setImageBitmap(bitmap);


          int b=1;
       for(int a=0;a<BankCount;a++){
            BankMessage+=bbName[a]+">"+bAcc[a]+">"+bName[a]+"\n";
           b++;
        }

        tvPerDetail.setText(
                "User Detail\n"+
                "\nName: "+name+
                "\nPhone: " + phone +
                "\nEmail: " + email +
                "\nAddress: " + address +
                "\nPoskod: " + poskod +
                "\nCity: " + city +
                "\nState: " + stateDes +
                "\nFacebook: " + facebook +
                "\nUserDetail: " + userdetail +
                "\n\nBank:\n" + BankMessage);


        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=etUserName.getText().toString();
                password=etPassword.getText().toString();
                Cpassword=etPasswordC.getText().toString();

               boolean isInsertedUsername=myDb.getUserLoginData(username);
                    if(isInsertedUsername==false){
                        showMessageToast("Please Create other Username");
                        return;
                    }

                if(!password.equals(Cpassword)){
                    showMessageToast("Please key in password again");
                    etPasswordC.setText("");
                    etPassword.setText("");
                    return;
                }
                if(password.length()<8){
                    showMessageToast("Minimum 8 letter(digits or alphabet) for Password");
                    return;
                }
                if(username.length()<8){
                    showMessageToast("Minimum 8 letter(digits or alphabet) for Username");
                    return;
                }

                insertData();
            }

        });

    }

    private void insertData() {

        boolean isInsertedUser=myDb.insertUserData(name,phone,email,userdetail,facebook,img);
        if(isInsertedUser!=true)
        {  showMessageToast("User fail Inserted");}

        int ID=myDb.getUserID(name,phone,email);

        boolean isInsertedUserLogin=myDb.insertUserLoginData(username,password,ID);
        if(isInsertedUserLogin!=true)
        {  showMessageToast("UserLogin fail Inserted");}

        boolean isInsertedBank=false;
        for(int q=0;q<BankCount;q++){
        isInsertedBank=myDb.insertBankAccData(bName[q],bAcc[q],ID,bID[q]);
            if(isInsertedBank!=true)
            {  showMessageToast("Bank Account fail Inserted");}}

        boolean isInsertedAddress=myDb.insertUserAddData(poskod,address,city,state,ID);
        if(isInsertedAddress!=true)
        {  showMessageToast("Address fail Inserted");}

        if(isInsertedUser==true && isInsertedUserLogin==true && isInsertedBank==true && isInsertedAddress==true){
            Intent intent=new Intent(UserRegister3Activity.this,LoginActivity.class);
            showMessageToast("Your Account is created Successfull.\nYou can login with your username and password");
            startActivity(intent);
        }


    }

    private void showMessageToast(String message) {
        Toast.makeText(getApplication(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_register3, menu);
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
}
