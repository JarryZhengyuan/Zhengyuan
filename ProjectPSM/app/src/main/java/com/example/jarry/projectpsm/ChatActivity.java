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
import android.widget.TextView;
import android.widget.Toast;


public class ChatActivity extends ActionBarActivity {

    private Intent intent;
    private int ID=-1;
    private int titleID=-1;
    private boolean limit=false;
    DatabasePerSell myDb;

    EditText editText;
    Button button;
    ListView listView;
    boolean position=false;
    ChatAdapter adapter;
    Context ctx=this;
    ImageView sender,receiver;
    TextView tv_sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ID=getIntent().getExtras().getInt("ID");
        titleID=getIntent().getExtras().getInt("titleID");
        limit=getIntent().getExtras().getBoolean("limit");

        if(ID==-1){
            intent=new Intent(ChatActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        myDb=new DatabasePerSell(ChatActivity.this);

        listView=(ListView)findViewById(R.id.listView);
        button=(Button)findViewById(R.id.button);
        editText=(EditText)findViewById(R.id.editText);
        sender=(ImageView)findViewById(R.id.img_sender);
        receiver=(ImageView)findViewById(R.id.img_receiver);
        tv_sender=(TextView)findViewById(R.id.tv_sender);

        Cursor resReceiver=myDb.getUserDetail(ID);
        while(resReceiver.moveToNext()){
              byte  bitmapdata[]=resReceiver.getBlob(5);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);}

        adapter=new ChatAdapter(ctx,R.layout.message_layout);
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.add(new DataProvider(editText.getText().toString(),position,null,null));
                position=!position;

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
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
            intent=new Intent(ChatActivity.this,ItemActivity.class);
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
