package com.example.jarry.projectpsm;

import android.app.ActionBar;
import android.content.Context;
import android.database.Cursor;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jarry on 18/8/2015.
 */
public class BankAccountAdapter extends ArrayAdapter<BankAccount> {

    private ArrayList<BankAccount> bankAcc_list=new ArrayList<BankAccount>();
    Context CTX;
    TextView tvName,tvAcc,tvBank;
    DatabasePerSell myDb;
    BankAccount temp;

    public BankAccountAdapter(Context context, int resource) {
        super(context, resource);
        CTX=context;
    }

    public void add(BankAccount object){
        bankAcc_list.add(object);
        super.add(object);
    }

    public void removeObject(BankAccount object){
        bankAcc_list.remove(object);
        super.remove(object);
    }

    public int getCount(){
        return bankAcc_list.size();
    }

    public BankAccount getItem(int position){
        return bankAcc_list.get(position);
    }

    public int getBankID(int position){
        temp=getItem(position);
        return temp.getBankID();
    }
    public String getBankAcc(int position){
        temp=getItem(position);
        return temp.getAccountNo();
    }

    public String getBankName(int position){
        temp=getItem(position);
        return temp.getAccountName();
    }

    public String getBBankName(int position){
        int a=getBankID(position);
        String bbName=null;

        Cursor res=myDb.getBankData();//

        while(res.moveToNext()){
            if (a==res.getInt(0))
            {
                bbName=res.getString(1);
            }
        }
        return bbName;
    }

    public View getView(int position,View convertView,ViewGroup parent){

        if(convertView==null)
        {
            LayoutInflater inflator=(LayoutInflater)CTX.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflator.inflate(R.layout.bank_acc_list_layout,parent,false);
        }
        tvAcc=(TextView)convertView.findViewById(R.id.tvAcc);
        tvBank=(TextView)convertView.findViewById(R.id.tvBank);
        tvName=(TextView)convertView.findViewById(R.id.tvName);

        myDb=new DatabasePerSell(CTX);

        String bank,account,name;
        int bankID;

        BankAccount provider=getItem(position);
        bankID=provider.getBankID();
        account=provider.getAccountNo();
        name=provider.getAccountName();
        tvAcc.setText(account);
        tvName.setText(name);

        bank=myDb.getBankData(bankID);
        tvBank.setText(bank);

        return convertView;
    }
}
