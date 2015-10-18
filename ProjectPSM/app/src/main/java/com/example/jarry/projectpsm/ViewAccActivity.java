package com.example.jarry.projectpsm;

import android.content.Context;
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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


public class ViewAccActivity extends ActionBarActivity {

    private int ID=-1;
    private Intent intent;
    DatabasePerSell myDb;

    private ListView listView_Bank;
    private ImageView btn_add;
    private EditText etAcc,etName;
    private Button btn_deleteAll;
    Context ctx=this;
    BankAccountAdapter adapter;
    ArrayAdapter<String> adapterBank;

    private  String[] bName=new String[4],bAcc=new String[4],bbName=new String[4];
    private int bID[]={-1,-1,-1,-1};
    private int bbID[]={-1,-1,-1,-1};

    private String name,acc;
    private int bankID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_acc);

        ID=getIntent().getExtras().getInt("ID");

        if(ID==-1){
            intent=new Intent(ViewAccActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        myDb=new DatabasePerSell(ViewAccActivity.this);
        listView_Bank=(ListView)findViewById(R.id.listView_bank);
        etAcc=(EditText)findViewById(R.id.etAcc);
        etName=(EditText)findViewById(R.id.etName);
        btn_add=(ImageView)findViewById(R.id.btn_add);
        btn_deleteAll=(Button)findViewById(R.id.btn_deleteAll);

        bankID=dropdownBank();

        adapter=new BankAccountAdapter(ctx,R.layout.bank_acc_list_layout);
        listView_Bank.setAdapter(adapter);
        listView_Bank.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        Cursor res=myDb.getBankAccData(ID);
        if(res.getCount()==0){
            showMessageToast("Nothing found");
        }else
        {
            int m=0;
            while(res.moveToNext())
            {
                bName[m]=res.getString(1);
                bAcc[m]=res.getString(0);
                bID[m]=res.getInt(3);
                bbID[m]=res.getInt(4);
                bbName[m]=myDb.getBankData(bID[m]);

                adapter.add(new BankAccount(bName[m],bAcc[m],bID[m]));
                m++;
            }
        }

        listView_Bank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                showMessageToast("Bank :"+position);
                boolean deleteAcc=myDb.deleteBankAccData(bbID[position]);

                if(deleteAcc==true)
                {showMessageToast(bAcc[position]+" > "+bName[position]+"is deleted");
                   refreseList();}

            }
        });


        btn_deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDeleteAllAcc = myDb.deleteAllBankAccData(ID);

                if (isDeleteAllAcc == true) {
                    showMessageToast("Delete All Successful");
                    refreseList();
                }
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = etName.getText().toString();
                acc = etAcc.getText().toString();

                boolean insertedAcc = myDb.insertBankAccData(name, acc, ID, bankID);

                if (insertedAcc == true) {
                    showMessageToast("Account is inserted");
                }

                refreseList();

                etName.setText("");
                etAcc.setText("");
            }
        });


    }

    private void refreseList() {
        intent = new Intent(ViewAccActivity.this, ViewAccActivity.class);
        intent.putExtra("ID", ID);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private int dropdownBank() {
        Spinner dropdownBankDes = (Spinner)findViewById(R.id.spinner_bank);

        Cursor res=myDb.getBankData();
        String[] items = new String[4];
        int b=0;

        while(res.moveToNext()){
            items[b]=res.getString(1);
            b++;}
        adapterBank= new ArrayAdapter<>(ViewAccActivity.this, android.R.layout.simple_spinner_item, items);
        dropdownBankDes.setAdapter(adapterBank);
        dropdownBankDes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bankID=position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return bankID;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_acc, menu);

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
            intent=new Intent(ViewAccActivity.this,ProfileActivity.class);
            intent.putExtra("ID",ID);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showMessageToast(String message) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
    }
}
