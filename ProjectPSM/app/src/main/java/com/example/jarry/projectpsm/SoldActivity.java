package com.example.jarry.projectpsm;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SoldActivity extends ActionBarActivity {

    private int ID=-1;
    private Intent intent;
    DatabasePerSell myDb;
    int status=0;

    private GridViewAdapter gridAdapter;
    Context CTX=SoldActivity.this;
    private ListView listView;
    private CheckBox cb_booked,cb_available,cb_sold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sold);

        ID=getIntent().getExtras().getInt("ID");

        if(ID==-1){
            intent=new Intent(SoldActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        status=getIntent().getExtras().getInt("status");
        myDb=new DatabasePerSell(SoldActivity.this);
        cb_sold=(CheckBox)findViewById(R.id.cb_sold);
        cb_available=(CheckBox)findViewById(R.id.cb_available);
        cb_booked=(CheckBox)findViewById(R.id.cb_booked);
        listView=(ListView)findViewById(R.id.listView);

        if(status==0 || status==2){
            status=2;
            cb_available.setChecked(true);
        }else if(status==1)
            cb_sold.setChecked(true);
        else if(status==3)
            cb_booked.setChecked(true);

        cb_sold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_booked.setChecked(false);
                cb_available.setChecked(false);
                status=1;
                getData(status);
            }
        });

        cb_available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_booked.setChecked(false);
                cb_sold.setChecked(false);
                status=2;
                getData(status);
            }
        });

        cb_booked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_sold.setChecked(false);
                cb_available.setChecked(false);
                status=3;
                getData(status);
            }
        });

        gridAdapter = new GridViewAdapter(CTX, R.layout.item_list_display_layout, getImageData());
        listView.setAdapter(gridAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);

                //Create intent
                Intent intent = new Intent(SoldActivity.this, SoldItemActivity.class);
                intent.putExtra("titleID", item.getID());
                intent.putExtra("ID",ID);

                //Start details activity
                startActivity(intent);
            }
        });
    }

    private void getData(int status) {
        intent=new Intent(SoldActivity.this,SoldActivity.class);
        intent.putExtra("ID", ID);
        intent.putExtra("status",status);
        startActivity(intent);
    }

    public ArrayList<ImageItem> getImageData() {
        Cursor res=myDb.getItemOwnerData(ID,status);

        final ArrayList<ImageItem> imageItems = new ArrayList<>();

        if(res.getCount() == 0) {
            showMessageToast("Count2: "+res.getCount());
        }

        while(res.moveToNext()){

            byte bitmapdata[]=res.getBlob(3);
            int itemID=res.getInt(0);
            String itemName=res.getString(2).toString();
            int OwenerID=res.getInt(8);
            String date=res.getString(7);
            double price=res.getDouble(6);

                    String message=itemName+"\nPrice : RM"+price+" \n "+date;

                    Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                    imageItems.add(new ImageItem(bitmap, message,itemID));

        }
        return imageItems;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sold, menu);

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
            intent=new Intent(SoldActivity.this,HomeActivity.class);
            intent.putExtra("ID", ID);
            startActivity(intent);}

        return super.onOptionsItemSelected(item);
    }

    private void showMessageToast(String message) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
    }
}
