package com.example.jarry.projectpsm;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class ProfileActivity extends ActionBarActivity {

    private int ID=-1;
    private Intent intent;
    DatabasePerSell myDb;
    String message;
    String temp;

    private byte img[];
    private ImageView pic;
    private TextView tvDetail;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ID=getIntent().getExtras().getInt("ID");

        if(ID==-1){
            intent=new Intent(ProfileActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        myDb=new DatabasePerSell(ProfileActivity.this);
        tvDetail=(TextView)findViewById(R.id.tvDetail);
        pic=(ImageView)findViewById(R.id.pic);

        Cursor resUser=myDb.getUserDetail(ID);

        if(resUser.getCount()==0){
            showMessageToast("Error");
            return;
        }

        while(resUser.moveToNext())
        {
            message="Name          : "+resUser.getString(1)+
                     "\nPhone Number  : "+resUser.getString(2)+
                     "\nEmail address : "+resUser.getString(3)+
                     "\nFacebook Name : "+resUser.getString(6);

            temp="\n\nAbout\n-----\n"+resUser.getString(4).toString();

            img=resUser.getBlob(5);
        }

        Cursor resAddress=myDb.getUserAddData(ID);
        if(resAddress.getCount()==0)
        {showMessageToast("Error");
            return;}

        while(resAddress.moveToNext()){
            String state=myDb.getStatesData(resAddress.getInt(2));
            message+="\nAddress    : "+resAddress.getString(3)+", "+resAddress.getInt(0)+" "+resAddress.getString(1)+" - "+state;
        }

        bitmap= BitmapFactory.decodeByteArray(img, 0, img.length);
        pic.setImageBitmap(bitmap);
        tvDetail.setText(message + temp);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);

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
            intent=new Intent(ProfileActivity.this,HomeActivity.class);
            intent.putExtra("ID",ID);
            startActivity(intent);
        }
        if(id==R.id.profile_editUser){
            intent=new Intent(ProfileActivity.this,EditProfileActivity.class);
            intent.putExtra("ID",ID);
            startActivity(intent);
        }
        if(id==R.id.profile_changePassword){
            intent=new Intent(ProfileActivity.this,PasswordChangeActivity.class);
            intent.putExtra("ID",ID);
            startActivity(intent);
        }
        if(id==R.id.profile_editAcc) {
            intent=new Intent(ProfileActivity.this,ViewAccActivity.class);
            intent.putExtra("ID",ID);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showMessageToast(String message) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
    }
}
