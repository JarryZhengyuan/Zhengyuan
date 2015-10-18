package com.example.jarry.projectpsm;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;


public class SalesActivity extends ActionBarActivity {

    private Intent intent;
    private int ID=-1;
    DatabasePerSell myDb;
    private int category;

    private EditText etItemName,etPrice,etItemDetail;
    private String itemname,itemdetail;
    private double price=0;
    private Button btn_done;
    private ImageView img_ItemPhoto;
    private TextView textView;
    int REQUEST_CODE=1;
    byte img[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        ID=getIntent().getExtras().getInt("ID");

        if(ID==-1){
            intent=new Intent(SalesActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        myDb=new DatabasePerSell(SalesActivity.this);

        etItemName=(EditText)findViewById(R.id.etItemName);
        etPrice=(EditText)findViewById(R.id.etPrice);
        etItemDetail=(EditText)findViewById(R.id.etItemDetail);
        btn_done=(Button)findViewById(R.id.btn_done);
        img_ItemPhoto=(ImageView)findViewById(R.id.img_ItemPhoto);
        textView=(TextView)findViewById(R.id.textView5);

        img_ItemPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(i.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(i,REQUEST_CODE);
                }
                textView.setText("");
            }
        });


        dropdownCategory();

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemname=etItemName.getText().toString();
                itemdetail=etItemDetail.getText().toString();
                price=Double.parseDouble(etPrice.getText().toString());

                if(itemdetail.length()<1 || itemname.length()<1 || img==null)
                {
                    showMessageToast("Please fill the blanks");
                    return;
                }

                   boolean isInsertedItem=myDb.insertItemData(category,itemname,img,itemdetail,price,ID);

                if(isInsertedItem!=true)
                {  showMessageToast("Item fail Inserted");
                  return;}else{
                    intent=new Intent(SalesActivity.this,HomeActivity.class);
                    intent.putExtra("ID",ID);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    showMessageToast("Item is successfull inserted");
                    startActivity(intent);
                }



            }
        });
    }

    private void dropdownCategory() {
        final Spinner dropdown = (Spinner)findViewById(R.id.spinner_category);

        Cursor res=myDb.getItemCategoryData();
        String[] items = new String[6];
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
                category = a;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sales, menu);
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
            intent=new Intent(SalesActivity.this,HomeActivity.class);
            intent.putExtra("ID",ID);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showMessageToast(String message) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK)
        {
            Bundle bundle=new Bundle();
            bundle=data.getExtras();
            Bitmap BMP;
            BMP=(Bitmap)bundle.get("data");
            img_ItemPhoto.setImageBitmap(BMP);

            Bitmap photo=(Bitmap)data.getExtras().get("data");
            img_ItemPhoto.setImageBitmap(photo);
            ByteArrayOutputStream os=new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG,100,os);
            img=os.toByteArray();
        }
    }
}
