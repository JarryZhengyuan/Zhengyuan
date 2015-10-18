package com.example.jarry.projectpsm;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.TextView;
import android.widget.Toast;


public class UserRegister2Activity extends ActionBarActivity {


    private EditText etFacebook,etUserDetail;
    private String name,phone,email,address,poskod,city,facebook,userdetail,stateDes;
    private byte img[];
    private int state;
    private Button btn_Next,btn_Back;
    private Intent intent;
    private ListView listView_Bank;
    Context ctx=this;
    BankAccountAdapter adapter;
    ArrayAdapter<String> adapterBank;
    private ImageView btn_add;

    private String a=null,b=null;
    private String bankName,accBank;
    private int bankID;

    private  String[] bName=new String[4],bAcc=new String[4],bbName=new String[4];
    private int bID[]={-1,-1,-1,-1};


    DatabasePerSell myDb;
    TextView etAccName,etAccNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register2);

        myDb=new DatabasePerSell(UserRegister2Activity.this);

        //get data from previous
        name=getIntent().getExtras().getString("name");
        phone=getIntent().getExtras().getString("phone");
        email=getIntent().getExtras().getString("email");
        address=getIntent().getExtras().getString("address");
        poskod=getIntent().getExtras().getString("poskod");
        city=getIntent().getExtras().getString("city");
        state=getIntent().getExtras().getInt("state");
        stateDes=getIntent().getExtras().getString("stateDes");
        img=getIntent().getExtras().getByteArray("img");


        Toast.makeText(getApplication(),"Name: "+name+
                "\nPhone: "+phone+
                "\nEmail: "+email+
                "\nAddress: "+address+
                "\nPoskod: "+poskod+
                "\nCity: "+city+
                "\nState: "+stateDes,Toast.LENGTH_LONG).show();

        //declare
        etFacebook=(EditText)findViewById(R.id.etFacebook);
        etUserDetail=(EditText)findViewById(R.id.etUserDetail);
        listView_Bank=(ListView)findViewById(R.id.listView_Bank);
        btn_add=(ImageView)findViewById(R.id.btn_add);
        btn_Back=(Button)findViewById(R.id.btn_Back);
        btn_Next=(Button)findViewById(R.id.btn_Next);
        etAccName=(TextView)findViewById(R.id.etAccName);
        etAccNo=(TextView)findViewById(R.id.etAccNo);

        bankID=dropdownBank();

       adapter=new BankAccountAdapter(ctx,R.layout.bank_acc_list_layout);
       listView_Bank.setAdapter(adapter);
        listView_Bank.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView_Bank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.removeObject(adapter.getItem(position));
            }
        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankName = etAccName.getText().toString();
                accBank = etAccNo.getText().toString();

                  if(bankName.length()<1 || accBank.length()<1){
                     return;
                  }else{
                    adapter.add(new BankAccount(bankName, accBank, bankID));
                  }
            }
        });

        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                facebook=etFacebook.getText().toString();
                userdetail=etUserDetail.getText().toString();
               int BankCount=adapter.getCount();

                for(int count=0;count<BankCount;count++)
                {
                    bName[count]=adapter.getBankName(count);
                    bAcc[count]=adapter.getBankAcc(count);
                    bID[count]=adapter.getBankID(count);
                    bbName[count]=adapter.getBBankName(count);
                }

               intent = new Intent(UserRegister2Activity.this, UserRegister3Activity.class);
                intent.putExtra("name",name);
                intent.putExtra("phone",phone);
                intent.putExtra("email",email);
                intent.putExtra("address",address);
                intent.putExtra("poskod",poskod);
                intent.putExtra("city",city);
                intent.putExtra("state",state);
                intent.putExtra("stateDes",stateDes);
                intent.putExtra("facebook",facebook);
                intent.putExtra("userdetail",userdetail);
                intent.putExtra("bName",bName);
                intent.putExtra("bAcc",bAcc);
                intent.putExtra("bID",bID);
                intent.putExtra("bbName",bbName);
               intent.putExtra("BankCount",BankCount);
                intent.putExtra("img",img);
                startActivity(intent);
            }
        });

        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(UserRegister2Activity.this,UserRegister1Activity.class);
                startActivity(intent);
            }
        });
    }

    private int dropdownBank() {
        Spinner dropdownBankDes = (Spinner)findViewById(R.id.spinner_bank);

        Cursor res=myDb.getBankData();
        String[] items = new String[4];
        int b=0;

        while(res.moveToNext()){
            items[b]=res.getString(1);
            b++;
        }

       // ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(UserRegister2Activity.this,R.layout.support_simple_spinner_dropdown_item,items);

        adapterBank= new ArrayAdapter<>(UserRegister2Activity.this, android.R.layout.simple_spinner_item, items);
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
        getMenuInflater().inflate(R.menu.menu_user_register2, menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
