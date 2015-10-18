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


public class SoldItemActivity extends ActionBarActivity {

    private int ID=-1;
    private Intent intent;
    private int titleID;
    DatabasePerSell myDb;
    private Button button;

    private ImageView pic_item;
    private TextView tv_item;
    String message;
    int status=3;
    int owner=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sold_item);

        ID=getIntent().getExtras().getInt("ID");

        if(ID==-1){
            intent=new Intent(SoldItemActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        titleID=getIntent().getExtras().getInt("titleID");
        button=(Button)findViewById(R.id.button);

        pic_item=(ImageView)findViewById(R.id.pic_item);
        tv_item=(TextView)findViewById(R.id.tv_item);
        myDb=new DatabasePerSell(SoldItemActivity.this);

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
            int old_status= resItem.getInt(5);

            if(old_status==2)
            { button.setText("Cancel Sell");
            status=3;}
            else if(old_status==3)
            { button.setText("Sell");
            status=2;}

            message=" Item        : "+itemName+
                    "\nCategory    : "+category+
                    "\nPrice       : RM"+price+
                    "\nDescription : "+desc;

            tv_item.setText(message);

            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapItem, 0, bitmapItem.length);
            pic_item.setImageBitmap(bitmap);}

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated=myDb.updateItemData(titleID,status);

                if(isUpdated!=true){
                    showMessageToast("Fail to change");
                }

                intent=new Intent(SoldItemActivity.this,SoldActivity.class);
                intent.putExtra("ID", ID);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sold_item, menu);

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
            intent=new Intent(SoldItemActivity.this,SoldActivity.class);
            intent.putExtra("ID", ID);
            startActivity(intent);}

        return super.onOptionsItemSelected(item);
    }

    private void showMessageToast(String message) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
    }
}
