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
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;


public class HomeActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private Intent intent;
    private int ID=-1;

    private GridView gridView;
    private GridViewAdapter gridAdapter;
    DatabasePerSell myDb;
    Context CTX=HomeActivity.this;

    boolean isChecked=false;
    private int state=0;
    private int category=0;
    private String search="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ID=getIntent().getExtras().getInt("ID");

        if(ID==-1){
            intent=new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        isChecked=getIntent().getExtras().getBoolean("isChecked");
        category=getIntent().getExtras().getInt("category");
        state=getIntent().getExtras().getInt("state");
        search=getIntent().getExtras().getString("search");

        showMessageToast("isChecked " + isChecked+
        "\nCategory "+category+
        "\nState "+state+
        "\nSearch "+search);//////////////////////////////////////////////////////////////////////////////////////////////////////


        /////////////////////////////////////////////////////////////////////

        myDb=new DatabasePerSell(HomeActivity.this);
        gridView=(GridView)findViewById(R.id.gridView);

        gridAdapter = new GridViewAdapter(CTX, R.layout.item_display_layout, getImageData());
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(this);

    }


    public Cursor getItem() {
        Cursor resi=myDb.getItemData();
        Cursor res= myDb.getItemData();
        int a=0;

        if(isChecked==true){
            if(category>0){
                if(state>0){a=9;}
                else{a=4;}
            }else{
                if(state>0){
                    if(search!=null){a=7;}
                    else{a=6;};}
                        else{a=1;}
        }
        }else{
            if(category>0){
                if(state>0){a=10;}
                else{a=5;}
            }else{
                if(state>0){
                    if(search!=null){a=8;}
                    else{a=6;};
                }
                else{
                    if(search!=null){a=2;}
                    else{a=0;}}}
        }


        switch (a){
            case 1:{resi=myDb.getItemNameData(search);//1
                return resi;}
            case 2: {resi=myDb.getItemData(search);//2
                return resi;}
            case 3:{  resi=myDb.getItemDetailCategoryData(category);//3
                    return resi;}
            case 4:{  resi=myDb.getItemNameCategoryData(search,category);//4
                return resi;}
            case 5:{   resi=myDb.getItemCategoryData(search,category);//5
                return resi;}
            case 6:{   resi=myDb.getItemStateData(state);//6
                return resi;}
            case 7:{   resi=myDb.getItemNameStateData(search,state);//7
                return resi;}
            case 8:{  resi=myDb.getItemStateData(search,state);//8
                return resi;}
            case 9: {  resi=myDb.getItemNameCategoryStateData(search,category,state);//9
                return resi;}
            case 10:{  resi=myDb.getItemCategoryStateData(search,category,state);//10
                return resi;}
            case 0:{resi=myDb.getItemData();
                return resi;}
            default:
                break;
        }
        return res;
    }

    private ArrayList<ImageItem> getImageData(){

        Cursor res=getItem();

        final ArrayList<ImageItem> imageItems = new ArrayList<>();

        if(res.getCount() == 0) {
            showMessageToast("Count2: "+res.getCount());
        }

        while(res.moveToNext()){

            byte bitmapdata[]=res.getBlob(3);
            int itemID=res.getInt(0);
            String itemName=res.getString(2).toString();
            int OwenerID=res.getInt(8);

            Cursor resCity=myDb.getUserAddData(OwenerID);
            if(resCity.getCount()==0)
            {showMessageToast("Error");}
            else{
            while(resCity.moveToNext()){

                String city=resCity.getString(1).toString();
                int stateID=resCity.getInt(2);
                String state=myDb.getStatesData(stateID);

                String message=itemName+"\n"+city+" - "+state;

                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);

                imageItems.add(new ImageItem(bitmap, message,itemID));
            }}

        }
        return imageItems;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
            ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
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
        if (id == R.id.menu_Home) {
            intent=new Intent(HomeActivity.this,HomeActivity.class);
            intent.putExtra("ID",ID);
            startActivity(intent);
        }
        if (id == R.id.menu_Profile) {
            intent=new Intent(HomeActivity.this,ProfileActivity.class);
            intent.putExtra("ID",ID);
            startActivity(intent);
        }
        if (id == R.id.menu_Search) {
            intent=new Intent(HomeActivity.this,SearchActivity.class);
            intent.putExtra("ID",ID);
            startActivity(intent);
        }
        if (id == R.id.menu_Sales) {
            intent=new Intent(HomeActivity.this,SalesActivity.class);
            intent.putExtra("ID",ID);
            startActivity(intent);
        }
        if (id == R.id.menu_SaledItem) {
            intent=new Intent(HomeActivity.this,SoldActivity.class);
            intent.putExtra("ID",ID);
            startActivity(intent);
        }
        if (id == R.id.menu_Order) {
            intent=new Intent(HomeActivity.this,HomeActivity.class);
            intent.putExtra("ID",ID);
            startActivity(intent);
        }
        if (id == R.id.menu_About) {
            intent=new Intent(HomeActivity.this,HomeActivity.class);
            intent.putExtra("ID",ID);
            startActivity(intent);
        }
        if (id == R.id.menu_logout) {
            intent=new Intent(HomeActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showMessageToast(String message) {
        Toast.makeText(getApplication(),message,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageItem item = (ImageItem) parent.getItemAtPosition(position);

        //Create intent
        Intent intent = new Intent(HomeActivity.this, ItemActivity.class);
        intent.putExtra("titleID", item.getID());
        intent.putExtra("ID",ID);

        //Start details activity
        startActivity(intent);
    }

}
