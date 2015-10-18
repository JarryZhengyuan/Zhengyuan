package com.example.jarry.projectpsm;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
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
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.ListIterator;


public class CommentActivity extends ActionBarActivity {

    private Intent intent;
    private int ID=-1;
    private int titleID=-1;
    private boolean limit=false;

    DatabasePerSell myDb;
    private GridViewAdapter adapter;
    Context CTX=CommentActivity.this;
    private ListView listView;
    private EditText et_comment;
    private Button btn_send;
    private ImageView img_previous;
    Bitmap bitmapOwner;
    Cursor res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        ID=getIntent().getExtras().getInt("ID");
        titleID=getIntent().getExtras().getInt("titleID");
        limit=getIntent().getExtras().getBoolean("limit");

        if(ID==-1){
            intent=new Intent(CommentActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        myDb=new DatabasePerSell(CommentActivity.this);
        btn_send=(Button)findViewById(R.id.btn_send);
        et_comment=(EditText)findViewById(R.id.et_comment);
        img_previous=(ImageView)findViewById(R.id.img_previous);
        listView=(ListView)findViewById(R.id.listView);

        Cursor resOwner=myDb.getUserDetail(ID);

        while(resOwner.moveToNext()){
            byte user_image[]=resOwner.getBlob(5);
            bitmapOwner = BitmapFactory.decodeByteArray(user_image, 0, user_image.length);}

        if(limit==true){
            res=myDb.getFeedbackData(titleID);
            img_previous.setImageBitmap(null);
        }else{
            res=myDb.getFeedbackLimitData(titleID);}

        adapter=new GridViewAdapter(CTX,R.layout.comment_list_layout,getImageData(res,limit));
        listView.setAdapter(adapter);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

            @Override
            public boolean equals(Object o) {
                return super.equals(o);
            }

            @Override
            protected void finalize() throws Throwable {
                super.finalize();
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(adapter.getCount() - 1);
            }

            @Override
            public void onInvalidated() {
                super.onInvalidated();
            }

            @Override
            public String toString() {
                return super.toString();
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.add(new ImageItem(bitmapOwner, et_comment.getText().toString(), -1));
                boolean isInserted = myDb.insertFeedData(et_comment.getText().toString(), titleID, ID);
                if (isInserted == true) {
                    // refresh();
                } else {
                    showMessageToast("Fail to comment");
                }
            }
        });

        img_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(CommentActivity.this,CommentActivity.class);
                intent.putExtra("ID",ID);
                intent.putExtra("titleID",titleID);
                intent.putExtra("limit",true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void refresh() {
        intent=new Intent(CommentActivity.this,CommentActivity.class);
        intent.putExtra("ID",ID);
        intent.putExtra("titleID",titleID);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public ArrayList<ImageItem> getImageData(Cursor res,boolean limit) {

        final ArrayList<ImageItem> imageItems = new ArrayList<>();

        if(res.getCount()==0)
        { showMessageToast("Nothing Found");}
        else if(res.getCount()>5)
        {

        }


        if(limit==true){
            while(res.moveToNext()){
                int feedbackID=res.getInt(0);
                String message=res.getString(1);
                String date=res.getString(2);
                int userID=res.getInt(4);
                byte bitmapdata[]=null;

                Cursor resUser=myDb.getUserDetail(userID);

                while(resUser.moveToNext()){
                    bitmapdata=resUser.getBlob(5);}

                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                imageItems.add(new ImageItem(bitmap, message,feedbackID));
            }
        }else{

            ImageItem item[]=new ImageItem[5];
            int a=0;
            while(res.moveToNext()){
                int feedbackID=res.getInt(0);
                String message=res.getString(1);
                String date=res.getString(2);
                int userID=res.getInt(4);
                byte bitmapdata[]=null;

                Cursor resUser=myDb.getUserDetail(userID);

                while(resUser.moveToNext()){
                    bitmapdata=resUser.getBlob(5);}

                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                item[a]=new ImageItem(bitmap, message,feedbackID);
                a++;
            }

            for(int i=4;i>=0;i--){
                imageItems.add(item[i]);
            }
            }


        return imageItems;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment, menu);

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
            intent=new Intent(CommentActivity.this,ItemActivity.class);
            intent.putExtra("ID",ID);
            intent.putExtra("titleID",titleID);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showMessageToast(String message) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
    }

}
