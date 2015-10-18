package com.example.jarry.projectpsm;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class InsertActivity extends ActionBarActivity {

    DatabasePerSell myDb;

    private String[] State={"Kuala Lumpur","Sarawak","Penang","Selangor","Malacca","Negeri Sembilan","Pahang","Johor","Terengganu","Perak","Sabah","Perlis","Kedah","Kelantan"};
    private String[] Bank={"MayBank","CIMB Bank","Public Bank","Hong Leong Bank"};
    private String[] Category={"Vehicle","Properties","Electronic","Home or Personal Items","SPORTs","Other"};
    private String[] ItemStatus={"Sold","Available","Booked"};
    private String[] ContactStatus={"unRead","Seen"};
    private String[] OrderStatus={"Cancelled","Booked","Paid"};

    Button btn_State,btn_Bank,btn_Category,btn_ItemStatus,btn_ContactStatus,btn_OrderStatus;
    Button btn_ViewState,btn_ViewBank,btn_ViewCategory,btn_ViewItemStatus,btn_ViewContactStatus,btn_ViewOrderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);


        myDb=new DatabasePerSell(InsertActivity.this);

       //declare button
        btn_State=(Button)findViewById(R.id.btn_State);
        btn_Category=(Button)findViewById(R.id.btn_Category);
        btn_Bank=(Button)findViewById(R.id.btn_Bank);
        btn_ItemStatus=(Button)findViewById(R.id.btn_ItemStatus);
        btn_ContactStatus=(Button)findViewById(R.id.btn_ContactStatus);
        btn_OrderStatus=(Button)findViewById(R.id.btn_OrderStatus);

        btn_ViewState=(Button)findViewById(R.id.btn_ViewState);
        btn_ViewBank=(Button)findViewById(R.id.btn_ViewBank);
        btn_ViewCategory=(Button)findViewById(R.id.btn_ViewCategory);
        btn_ViewItemStatus=(Button)findViewById(R.id.btn_ViewItemStatus);
        btn_ViewContactStatus=(Button)findViewById(R.id.btn_ViewContactStatus);
        btn_ViewOrderStatus=(Button)findViewById(R.id.btn_ViewOrderStatus);

        //Insert State data and view list of State
        btn_State.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View v) {

                Cursor res=myDb.getStatesData();//
                if(res.getCount()==0){
                    for(int a=0;a<State.length;a++) {
                        myDb.insertStateData(State[a]);}//
                }else{
                    return;
               }}});

        btn_ViewState.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View v) {
                Cursor res=myDb.getStatesData();//
                if(res.getCount()==0){
                    showMessage("Error","Nothing found");
                    return;
                }

                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){
                    buffer.append(res.getString(0)+" :" + res.getString(1) + "\n");
                }
                showMessage("State",buffer.toString());}});//

        //Insert Bank data and view list of Bank
        btn_Bank.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View v) {

                Cursor res=myDb.getBankData();//
                if(res.getCount()==0){
                    for(int a=0;a<Bank.length;a++) {//
                        myDb.insertBankData(Bank[a]);}//
                }else{
                    return;
                }}});

        btn_ViewBank.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View v) {
                Cursor res=myDb.getBankData();//
                if(res.getCount()==0){
                    showMessage("Error","Nothing found");
                    return;
                }

                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){
                    buffer.append(res.getString(0)+" :" + res.getString(1) + "\n");
                }
                showMessage("Bank",buffer.toString());}});//

        //Insert Category data and view list of Category
        btn_Category.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View v) {

                Cursor res=myDb.getItemCategoryData();//
                if(res.getCount()==0){
                    for(int a=0;a<Category.length;a++) {//
                        myDb.insertItemCategoryData(Category[a]);}//
                }else{
                    return;
                }}});

        btn_ViewCategory.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View v) {
                Cursor res=myDb.getItemCategoryData();//
                if(res.getCount()==0){
                    showMessage("Error","Nothing found");
                    return;
                }

                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){
                    buffer.append(res.getString(0)+" :" + res.getString(1) + "\n");
                }
                showMessage("Category",buffer.toString());}});//

        //Insert Order status data and view list of order status
        btn_OrderStatus.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View v) {

                Cursor res=myDb.getOrderStatusData();//
                if(res.getCount()==0){
                    for(int a=0;a<OrderStatus.length;a++) {//
                        myDb.insertOrderStatusData(OrderStatus[a]);}//
                }else{
                    return;
                }}});

        btn_ViewOrderStatus.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View v) {
                Cursor res=myDb.getOrderStatusData();//
                if(res.getCount()==0){
                    showMessage("Error","Nothing found");
                    return;
                }

                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){
                    buffer.append(res.getString(0)+" :" + res.getString(1) + "\n");
                }
                showMessage("Order Status",buffer.toString());}});//

        //Insert Item status data and view list of item status
        btn_ItemStatus.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View v) {

                Cursor res=myDb.getItemStatusData();//
                if(res.getCount()==0){
                    for(int a=0;a<ItemStatus.length;a++) {//
                        myDb.insertItemStatusData(ItemStatus[a]);}//
                }else{
                    return;
                }}});

        btn_ViewItemStatus.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View v) {
                Cursor res=myDb.getItemStatusData();//
                if(res.getCount()==0){
                    showMessage("Error","Nothing found");
                    return;
                }

                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){
                    buffer.append(res.getString(0)+" :" + res.getString(1) + "\n");
                }
                showMessage("Item Status",buffer.toString());}});//

        //Insert Contact status data and view list of Contact status
        btn_ContactStatus.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View v) {

                Cursor res=myDb.getContactStatusData();//
                if(res.getCount()==0){
                    for(int a=0;a<ContactStatus.length;a++) {//
                        myDb.insertContactStatusData(ContactStatus[a]);}//
                }else{
                    return;
                }}});

        btn_ViewContactStatus.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View v) {
                Cursor res=myDb.getContactStatusData();//
                if(res.getCount()==0){
                    showMessage("Error","Nothing found");
                    return;
                }

                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){
                    buffer.append(res.getString(0)+" :" + res.getString(1) + "\n");
                }
                showMessage("Contact Status",buffer.toString());}});//

    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_insert, menu);
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
