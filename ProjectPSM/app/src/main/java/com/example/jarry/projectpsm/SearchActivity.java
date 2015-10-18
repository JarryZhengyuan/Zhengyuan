package com.example.jarry.projectpsm;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class SearchActivity extends ActionBarActivity {

    private int ID=-1;
    private Intent intent;
    DatabasePerSell myDb;

    private CheckBox ch_box;
    private EditText etSearch;
    private Button btn_submit;

    int state;
    int category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ID=getIntent().getExtras().getInt("ID");

        if(ID==-1){
            intent=new Intent(SearchActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        myDb=new DatabasePerSell(SearchActivity.this);

        ch_box=(CheckBox)findViewById(R.id.ch_box);
        etSearch=(EditText)findViewById(R.id.etSearch);
        btn_submit=(Button)findViewById(R.id.btn_submit);

        dropdownState();
        dropdownCategory();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked=false;
                if(ch_box.isChecked())
                { isChecked=true; }

                intent=new Intent(SearchActivity.this,HomeActivity.class);
                intent.putExtra("isChecked",isChecked);
                intent.putExtra("category",category);
                intent.putExtra("state",state);
                intent.putExtra("search",etSearch.getText().toString());
                intent.putExtra("ID",ID);
                startActivity(intent);
            }
        });
    }

    private void dropdownCategory() {
        final Spinner dropdown = (Spinner)findViewById(R.id.sp_category);

        Cursor res=myDb.getItemCategoryData();
        String[] items = new String[7];
        items[0]="All Categories";
        int a=1;

        while(res.moveToNext()){
            items[a]=res.getString(1);
            a++;
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void dropdownState() {
            final Spinner dropdown = (Spinner)findViewById(R.id.sp_state);

            Cursor res=myDb.getStatesData();
            String[] items = new String[15];
            items[0]="Entire Malaysia";
            int a=1;

            while(res.moveToNext()){
                items[a]=res.getString(1);
                a++;
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
            dropdown.setAdapter(adapter);
            dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    state = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

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
            intent=new Intent(SearchActivity.this,HomeActivity.class);
            intent.putExtra("ID",ID);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
