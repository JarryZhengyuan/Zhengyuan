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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ItemActivity extends ActionBarActivity {

    private Intent intent;
    private int ID=-1;
    private int titleID=-1;

    DatabasePerSell myDb;
    private ImageView pic_item,pic_owner;
    private TextView tv_item;
    String message;
    int owner=-1;

    private Button btn_Call,btn_Comment,btn_Purchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        ID=getIntent().getExtras().getInt("ID");
        titleID=getIntent().getExtras().getInt("titleID");

        if(ID==-1){
            intent=new Intent(ItemActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        pic_item=(ImageView)findViewById(R.id.pic_item);
        pic_owner=(ImageView)findViewById(R.id.pic_owner);
        tv_item=(TextView)findViewById(R.id.tv_item);
        myDb=new DatabasePerSell(ItemActivity.this);

        btn_Call=(Button)findViewById(R.id.btn_Call);
        btn_Comment=(Button)findViewById(R.id.btn_Comment);
        btn_Purchase=(Button)findViewById(R.id.btn_Purchase);

        btn_Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(ItemActivity.this,ChatActivity.class);
                intent.putExtra("ID",ID);
                intent.putExtra("titleID",titleID);
                startActivity(intent);
            }
        });
        btn_Comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(ItemActivity.this,CommentActivity.class);
                intent.putExtra("ID",ID);
                intent.putExtra("titleID",titleID);
                startActivity(intent);
            }
        });
        btn_Purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Cursor resItem=myDb.getItemData(titleID);
        if(resItem.getCount()==0)
        {showMessageToast("Nothing Found");}

        while(resItem.moveToNext()){
            byte bitmapItem[]=resItem.getBlob(3);
            final String itemName=resItem.getString(2).toString();
            String category=myDb.getItemCategoryData(resItem.getInt(1));
            String desc=resItem.getString(4).toString();
            double price=resItem.getDouble(6);
            owner=resItem.getInt(8);

            message=" Item        : "+itemName+
                    "\nCategory    : "+category+
                    "\nPrice       : RM"+price+
                    "\nDescription : "+desc;

            tv_item.setText(message);

            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapItem, 0, bitmapItem.length);
            pic_item.setImageBitmap(bitmap);

            Cursor resOwner=myDb.getUserDetail(owner);
            if(resOwner.getCount()==0)
            {showMessageToast("Nothing found");}

            while(resOwner.moveToNext()){
                byte bitmapOwner[]=resOwner.getBlob(5);
                Bitmap bitmapUser = BitmapFactory.decodeByteArray(bitmapOwner, 0, bitmapOwner.length);
                pic_owner.setImageBitmap(bitmapUser);
            }

            pic_owner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent=new Intent(ItemActivity.this,OwnerActivity.class);
                    intent.putExtra("Owner",owner);
                    intent.putExtra("ID",ID);
                    intent.putExtra("titleID",titleID);
                    startActivity(intent);
                }
            });
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);

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
            intent=new Intent(ItemActivity.this,HomeActivity.class);
            intent.putExtra("ID",ID);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showMessageToast(String message) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
    }
}
