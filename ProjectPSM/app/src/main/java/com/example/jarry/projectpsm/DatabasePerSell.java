package com.example.jarry.projectpsm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jarry on 14/8/2015.
 */
public class DatabasePerSell extends SQLiteOpenHelper{

    public static final String DATABASE_NAME="PerSell.db";
    private static final String TAG ="DatabasePerSell";

    //1. User Table
    public static final String User_tb="User";
    public static final String User_ID="UserID";
    public static final String User_Name="UserName";
    public static final String User_Phone="UserPhone";
    public static final String User_Email="UserEmail";
    public static final String User_Detail="OtherUserDetail";
    public static final String User_Photo="UserPhoto";
    public static final String User_Facebook="UserFacebook";

    //2. Item Table
    public static final String Item_tb="Item";
    public static final String Item_ID="ItemID";
    public static final String Item_Category="ItemCategoryCode";
    public static final String Item_Name="ItemName";
    public static final String Item_Picture="ItemPicture";
    public static final String Item_Detail="ItemDetail";
    public static final String Item_Status="ItemStatusCode";
    public static final String Item_Price="ItemPrice";
    public static final String Item_Date="ItemDate";
    public static final String Item_Owner="UserID";

    //3. User's Address table
    public static final String UserAddress_tb="UserAddress";
    public static final String UserAddress_Poskod="PoskodNo";
    public static final String UserAddress_City="CityName";
    public static final String UserAddress_State="StateCode";
    public static final String UserAddress_Address1="Address1";
    public static final String UserAddress_Address2="Address2";
    public static final String UserAddress_Owner="UserID";

    //4. State Malaysia table
    public static final String State_tb="State";
    public static final String State_ID="StateCode";
    public static final String State_Des="StateDes";

    //5. UserLogin table
    public static final String UserLogin_tb="UserLogin";
    public static final String UserLogin_Username="Username";
    public static final String UserLogin_Password="Password";
    public static final String UserLogin_Owner="UserID";

    //6. Item Status table
    public static final String ItemStatus_tb="ItemStatus";
    public static final String ItemStatus_ID="ItemStatusCode";
    public static final String ItemStatus_Des="ItemStatusDes";

    //7. Item Category table
    public static final String ItemCategory_tb="ItemCategory";
    public static final String ItemCategory_ID="ItemCategoryCode";
    public static final String ItemCategory_Des="ItemCategoryDes";

    //8. Feedback table
    public static final String Feedback_tb="Feedback";
    public static final String Feedback_ID="FeedbackID";
    public static final String Feedback_Message="FeedbackMessage";
    public static final String Feedback_Date="FeedbackDate";
    public static final String Feedback_item="ItemID";
    public static final String Feedback_user="UserID";

    //9. User Bank Account
    public static final String BankAccount_tb="BankAccount";
    public static final String BankAccount_UserAccount="UserBankAccount";
    public static final String BankAccount_OwnerName="OwnerName";
    public static final String BankAccount_Owner="UserID";
    public static final String BankAccount_Bank="BankCode";
    public static final String BankAccount_ID="BankAccID";

    //10. Bank Table
    public static final String Bank_tb="Bank";
    public static final String Bank_ID="BankCode";
    public static final String Bank_Des="BankDes";

    //11. Contact Table
    public static final String Contact_tb="Contact";
    public static final String Contact_ID="ContactID";
    public static final String Contact_Sender="SenderID";
    public static final String Contact_Receiver="ReceiverID";

    //12. Contact Detail table
    public static final String ContactDetail_tb="ContactDetail";
    public static final String ContactDetail_Date="ContactDate";
    public static final String ContactDetail_Message="ContactMessage";
    public static final String ContactDetail_Status="ContactStatusCode";
    public static final String ContactDetail_ID="ContactID";

    //13. Contact Status table
    public static final String ContactStatus_tb="ContactStatus";
    public static final String ContactStatus_ID="ContactStatusCode";
    public static final String ContactStatus_Des="ContactStatusDes";

    //14. Order table
    public static final String Order_tb="OrderItem";
    public static final String Order_ID="OrderID";
    public static final String Order_item="ItemID";
    public static final String Order_Status="OrderStatusCode";
    public static final String Order_Date="OrderDate";
    public static final String Order_Buyer="UserID";

    //15. Order Status table
    public static final String OrderStatus_tb="OrderStatus";
    public static final String OrderStatus_ID="OrderStatusCode";
    public static final String OrderStatus_Des="OrderStatusDes";

    //16. Invoice Table
    public static final String Invoice_tb="Invoice";
    public static final String Invoice_ID="InvoiceID";
    public static final String Invoice_Date="InvoiceDate";
    public static final String Invoice_Bank="InvoiceBankingPic";
    public static final String Invoice_OrderID="OrderID";

    //Create SQL User Table
    public static final String SQL_User="create table User (" +
            "UserID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "UserName TEXT, " +
            "UserPhone TEXT, " +
            "UserEmail TEXT," +
            "OtherUserDetail TEXT, " +
            "UserPhoto BLOB," +
            "UserFacebook TEXT " +
            ") ";

    //Create SQL Item table
    public static final String SQL_Item="create table Item (" +
            "ItemID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ItemCategoryCode INTEGER, " +
            "ItemName TEXT, " +
            "ItemPicture BLOB," +
            "ItemDetail TEXT, " +
            "ItemStatusCode INTEGER," +
            "ItemPrice DECIMAL(10, 2), " +
            "ItemDate DATETIME DEFAULT CURRENT_TIMESTAMP, " +
            "UserID INTEGER " +
            ") ";

    //Create SQL USER ADDRESS TABLE
    public static final String SQL_UserAddress="create table UserAddress (" +
            "PoskodNo INTEGER, " +
            "CityName TEXT, " +
            "StateCode INTEGER," +
            "Address1 TEXT, " +
            "Address2 TEXT," +
            "UserID INTEGER " +
            ") ";

    //Create SQL STATE MALAYSIA TABLE
    public static final String SQL_State="create table State (" +
            "StateCode INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "StateDes TEXT " +
            ") ";

    //Create SQL USER LOGIN TABLE
    public static final String SQL_UserLogin="create table UserLogin (" +
            "Username TEXT, " +
            "Password TEXT, " +
            "UserID INTEGER " +
            ") ";

    //Create SQL ITEM STATUS TABLE
    public static final String SQL_ItemStatus="create table ItemStatus (" +
            "ItemStatusCode INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ItemStatusDes TEXT " +
            ") ";

    //Create SQL ITEM CATEGORY TABLE
    public static final String SQL_ItemCategory="create table ItemCategory (" +
            "ItemCategoryCode INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ItemCategoryDes TEXT " +
            ") ";

    //Create SQL FEEDBACK TABLE
    public static final String SQL_Feedback="create table Feedback (" +
            "FeedbackID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "FeedbackMessage TEXT, " +
            "FeedbackDate DATETIME DEFAULT CURRENT_TIMESTAMP, " +
            "ItemID INTEGER," +
            "UserID INTEGER " +
            ") ";

    //Create SQL USER BANK ACCOUNT
    public static final String SQL_BankAccount="create table BankAccount (" +
            "UserBankAccount TEXT, " +
            "OwnerName TEXT, " +
            "UserID INTEGER," +
            "BankCode INTEGER, " +
            "BankAccID INTEGER PRIMARY KEY AUTOINCREMENT"+
            ") ";

    //Create SQL BANK TABLE
    public static final String SQL_Bank="create table Bank (" +
            "BankCode INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "BankDes TEXT " +
            ") ";

    //Create SQL CONTACT TABLE
    public static final String SQL_Contact="create table Contact (" +
            "ContactID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "SenderID INTEGER, " +
            "ReceiverID INTEGER " +
            ") ";

    //Create SQL CONTACT DETAIL TABLE
    public static final String SQL_ContactDetail="create table ContactDetail (" +
            "ContactDate DATETIME DEFAULT CURRENT_TIMESTAMP, " +
            "ContactMessage TEXT, " +
            "ContactStatusCode INTEGER, " +
            "ContactID INTEGER " +
            ") ";

    //Create SQL CONTACT STATUS TABLE
    public static final String SQL_ContactStatus="create table ContactStatus (" +
            "ContactStatusCode INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ContactStatusDes TEXT " +
            ") ";

    //Create SQL Order table
    public static final String SQL_OrderItem="create table OrderItem (" +
            "OrderID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ItemID INTEGER, " +
            "OrderStatusCode INTEGER, " +
            "OrderDate DATETIME DEFAULT CURRENT_TIMESTAMP, " +
            "UserID INTEGER " +
            ") ";

    //Create SQL ORDER STATUS TABLE
    public static final String SQL_OrderStatus="create table OrderStatus (" +
            "OrderStatusCode INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "OrderStatusDes TEXT " +
            ") ";

    //Create SQL INVOICE TABLE
    public static final String SQL_Invoice="create table Invoice (" +
            "InvoiceID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "InvoiceDate DATETIME DEFAULT CURRENT_TIMESTAMP, " +
            "InvoiceBankingPic BLOB, " +
            "OrderID INTEGER " +
            ") ";


    public DatabasePerSell(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Execute SQL table
        db.execSQL(SQL_Bank);
        db.execSQL(SQL_BankAccount);
        db.execSQL(SQL_Contact);
        db.execSQL(SQL_ContactDetail);
        db.execSQL(SQL_ContactStatus);
        db.execSQL(SQL_Feedback);
        db.execSQL(SQL_Invoice);
        db.execSQL(SQL_Item);
        db.execSQL(SQL_ItemCategory);
        db.execSQL(SQL_ItemStatus);
        db.execSQL(SQL_OrderItem);
        db.execSQL(SQL_OrderStatus);
        db.execSQL(SQL_State);
        db.execSQL(SQL_User);
        db.execSQL(SQL_UserAddress);
        db.execSQL(SQL_UserLogin);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading the database from version " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+User_tb);
        db.execSQL("DROP TABLE IF EXISTS "+Item_tb);
        db.execSQL("DROP TABLE IF EXISTS "+UserAddress_tb);
        db.execSQL("DROP TABLE IF EXISTS "+State_tb);
        db.execSQL("DROP TABLE IF EXISTS "+UserLogin_tb);
        db.execSQL("DROP TABLE IF EXISTS "+ItemStatus_tb);
        db.execSQL("DROP TABLE IF EXISTS "+ItemCategory_tb);
        db.execSQL("DROP TABLE IF EXISTS "+Feedback_tb);
        db.execSQL("DROP TABLE IF EXISTS "+BankAccount_tb);
        db.execSQL("DROP TABLE IF EXISTS "+Bank_tb);
        db.execSQL("DROP TABLE IF EXISTS "+Contact_tb);
        db.execSQL("DROP TABLE IF EXISTS "+ContactDetail_tb);
        db.execSQL("DROP TABLE IF EXISTS "+ContactStatus_tb);
        db.execSQL("DROP TABLE IF EXISTS "+Order_tb);
        db.execSQL("DROP TABLE IF EXISTS "+OrderStatus_tb);
        db.execSQL("DROP TABLE IF EXISTS "+Invoice_tb);
        onCreate(db);

    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public boolean insertStateData(String State){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(State_Des,State);
        long result=db.insert(State_tb,null,contentValues);

        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getStatesData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + State_tb, null);
        return res;
    }

    public String getStatesData(int ID){
        SQLiteDatabase db=this.getWritableDatabase();
        String result="";
        Cursor res=db.rawQuery("select * from " + State_tb + " where " +
                State_ID + " = " + ID, null);

        while(res.moveToNext())
        {   result=res.getString(1).toString();
        return result; }

        return result;
    }

    public boolean insertBankData(String Bank){//
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Bank_Des,Bank);//
        long result=db.insert(Bank_tb, null, contentValues);//

        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getBankData(){//
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+Bank_tb,null);//
        return res;
    }

    public String getBankData(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        String result="";
        Cursor res=db.rawQuery("select * from " + Bank_tb + " where " +
                Bank_ID + " = " + id, null);//

        while(res.moveToNext())
        { result=res.getString(1).toString();}
        return result;
    }


    public boolean insertItemCategoryData(String Category){//
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ItemCategory_Des,Category);//
        long result=db.insert(ItemCategory_tb,null,contentValues);//

        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getItemCategoryData(){//
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + ItemCategory_tb, null);//
        return res;
    }

    public String getItemCategoryData(int ID){//
        SQLiteDatabase db=this.getWritableDatabase();
        String result="";
        Cursor res=db.rawQuery("select * from " + ItemCategory_tb + " where " +
                ItemCategory_ID + " = " + ID
                , null);//

        while(res.moveToNext()){
            result=res.getString(1).toString();
            return result;
        }

        return result;
    }

    public boolean insertItemStatusData(String ItemStatus){//
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ItemStatus_Des,ItemStatus);//
        long result=db.insert(ItemStatus_tb,null,contentValues);//

        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getItemStatusData(){//
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+ItemStatus_tb,null);//
        return res;
    }

    public boolean insertContactStatusData(String Contact){//
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ContactStatus_Des,Contact);//
        long result=db.insert(ContactStatus_tb,null,contentValues);//

        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getContactStatusData(){//
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+ContactStatus_tb,null);//
        return res;
    }

    public boolean insertOrderStatusData(String OrderStatus){//
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(OrderStatus_Des,OrderStatus);//
        long result=db.insert(OrderStatus_tb, null, contentValues);//

        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getOrderStatusData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + OrderStatus_tb, null);
        return res;
    }

    ///////////////

    public boolean insertUserLoginData(String username,String password,int id){//
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(UserLogin_Username,username);//
        contentValues.put(UserLogin_Password,password);//
        contentValues.put(UserLogin_Owner,id);//
        long result=db.insert(UserLogin_tb, null, contentValues);//

        if(result==-1)
            return false;
        else
            return true;
    }

    public boolean updateUserLoginData(String password,int id){//
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(UserLogin_Password,password);//
        long result=db.update(UserLogin_tb, contentValues, UserLogin_Owner + " = ?", new String[]{String.valueOf(id)});//

        if(result==-1)
            return false;
        else
            return true;
    }

    public boolean getUserLoginData(String username){
        SQLiteDatabase db=this.getWritableDatabase();
        boolean result=true;

       Cursor res=db.rawQuery("select * from " + UserLogin_tb, null);

        while(res.moveToNext()){
            if(res.getString(0).equals(username))
                return false;
        }
        return result;

    }

    public int getUserLoginData(String username,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        int result=-1;

        /*Cursor res=db.rawQuery("select * from " + UserLogin_tb, null);

        while(res.moveToNext()){
            if(res.getString(0).equals(username) && res.getString(1).equals(password))
                return res.getInt(2);
        }*/
        Cursor res=db.rawQuery("select * from " + UserLogin_tb+ " where "+
                UserLogin_Username+" = ?  and "+
                UserLogin_Password+" = ?", new String[]{username,password});

        while(res.moveToNext()){
                return res.getInt(2);
        }

        return result;

    }

    public String getUserLoginPassword(int ID){
        SQLiteDatabase db=this.getWritableDatabase();
        String result="";

        Cursor res=db.rawQuery("select * from " + UserLogin_tb + " where " +
                UserLogin_Owner + " = " + ID
                , null);

        while(res.moveToNext()){
                return res.getString(1).toString();
        }
        return result;
    }

    public boolean insertUserData(String username,String phone,String email,String userdetail,String facebook,byte[] photo){//
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(User_Name,username);//
        contentValues.put(User_Phone,phone);//
        contentValues.put(User_Email,email);//
        contentValues.put(User_Detail,userdetail);//
        contentValues.put(User_Facebook,facebook);//
        contentValues.put(User_Photo,photo);//
        long result=db.insert(User_tb, null, contentValues);//

        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getUserData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + User_tb, null);
        return res;
    }

    public boolean updateUserData(int id,String username,String phone,String email,String userdetail,String facebook,byte[] photo){//
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(User_Name,username);//
        contentValues.put(User_Phone,phone);//
        contentValues.put(User_Email,email);//
        contentValues.put(User_Detail,userdetail);//
        contentValues.put(User_Facebook,facebook);//
        contentValues.put(User_Photo, photo);//
        long result=db.update(User_tb, contentValues, User_ID + " = ?", new String[]{String.valueOf(id)});//

        if(result==-1)
            return false;
        else
            return true;
    }

    public int getUserID(String name,String phone,String email){
        SQLiteDatabase db=this.getWritableDatabase();
        int result=-1;

        Cursor res=db.rawQuery("select * from " + User_tb, null);

        while(res.moveToNext()){
            if(name.equals(res.getString(1)) && phone.equals(res.getString(2)) && email.equals(res.getString(3)))
            {result=res.getInt(0);}
        }

        return result;
    }

    public Cursor getUserDetail(int ID){
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor res=db.rawQuery("select * from " + User_tb + " where " +
                User_ID + " = " + ID
                , null);

        return res;
    }

    public boolean insertBankAccData(String bankName,String bankAcc,int UserID,int BankID){//


        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(BankAccount_UserAccount,bankAcc);//
        contentValues.put(BankAccount_OwnerName,bankName);
        contentValues.put(BankAccount_Owner,UserID);
        contentValues.put(BankAccount_Bank,BankID);
        long result=db.insert(BankAccount_tb, null, contentValues);//

        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getBankAccData(int ID){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + BankAccount_tb + " where " +
                BankAccount_Owner + " = " + ID, null);
        return res;
    }

    public boolean deleteAllBankAccData(int id){
        SQLiteDatabase db=this.getWritableDatabase();

        int result=db.delete(BankAccount_tb, BankAccount_Owner + " = ?", new String[]{String.valueOf(id)});

        if(result==-1)
            return false;
        else
            return true;
    }

    public boolean deleteBankAccData(int id){
        SQLiteDatabase db=this.getWritableDatabase();

        int result=db.delete(BankAccount_tb, BankAccount_ID + " = ?", new String[]{String.valueOf(id)});

        if(result==-1)
            return false;
        else
            return true;
    }

    public boolean insertUserAddData(String poskod,String address,String city,int StateID,int UserID){//
        SQLiteDatabase db=this.getWritableDatabase();
        int poskodNo=Integer.parseInt(poskod);

        ContentValues contentValues=new ContentValues();
        contentValues.put(UserAddress_Poskod,poskodNo);//
        contentValues.put(UserAddress_Address1,address);
        contentValues.put(UserAddress_City,city);
        contentValues.put(UserAddress_State,StateID);
        contentValues.put(UserAddress_Owner,UserID);

        long result=db.insert(UserAddress_tb, null, contentValues);//

        if(result==-1)
            return false;
        else
            return true;
    }

    public boolean updateUserAddData(int id,String poskod,String address,String city,int StateID){//
        SQLiteDatabase db=this.getWritableDatabase();
        int poskodNo=Integer.parseInt(poskod);

        ContentValues contentValues=new ContentValues();
        contentValues.put(UserAddress_Poskod,poskodNo);//
        contentValues.put(UserAddress_Address1,address);
        contentValues.put(UserAddress_City,city);
        contentValues.put(UserAddress_State,StateID);

        long result=db.update(UserAddress_tb, contentValues, UserAddress_Owner + " = ?", new String[]{String.valueOf(id)});//

        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getUserAddData(int ID){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + UserAddress_tb + " where " +
                UserAddress_Owner + " = " + ID, null);
        return res;
    }

    public boolean insertItemData(int CategoryID,String itemname,byte[] photo,String itemdetail,double price,int OwnerID){//
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(Item_Category,CategoryID);//
        contentValues.put(Item_Picture,photo);
        contentValues.put(Item_Name,itemname);
        contentValues.put(Item_Detail,itemdetail);
        contentValues.put(Item_Status,2);
        contentValues.put(Item_Price,price);
        contentValues.put(Item_Date,getDateTime());
        contentValues.put(Item_Owner,OwnerID);

        long result=db.insert(Item_tb, null, contentValues);//

        if(result==-1)
            return false;
        else
            return true;
    }
    public boolean updateItemData(int id,int status){//
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(Item_Status,status);

        long result=db.update(Item_tb, contentValues, Item_ID + " = ?", new String[]{String.valueOf(id)});//

        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getItemData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + Item_tb + " where " +
                Item_Status + " = " + 2 +
                " order by " + Item_Date + " desc ", null);
        return res;
    }

    public Cursor getItemData(int ID){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + Item_tb
                +" where " + Item_ID+ " = "+ID
                , null);
        return res;
    }

    public Cursor getItemOwnerData(int ID,int status){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + Item_tb
                +" where " + Item_Owner+ " = "+ID+" and "+
                Item_Status+" = "+status+
                " order by " + Item_Date + " desc ", null);
        return res;
    }
//////////////////////Search
    public Cursor getItemNameData(String data)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + Item_tb + " where " +
                Item_Status + " = " + 2 +" and "+
                        Item_Name+" LIKE '%"+data+"%' "+
                " order by " + Item_Date + " desc " , null);

        return res;
    }

    public Cursor getItemData(String data)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + Item_tb + " where " +
                Item_Status + " = " + 2+" and ("+
                Item_Name+" like '%"+data+"%' or "+
                Item_Detail+" like '%"+data+"%' )"+
                " order by " + Item_Date + " desc " , null);
        return res;
    }

    public Cursor getItemDetailCategoryData(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + Item_tb + " where " +
                Item_Status + " = " + 2+" and "+
                Item_Category+" = "+id+
                " order by " + Item_Date + " desc ", null);
        return res;
    }

    public Cursor getItemNameCategoryData(String data,int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + Item_tb + " where " +
                Item_Status + " = " + 2+" and "+
                Item_Name+" like '%"+data+"%' and "+
                Item_Category+" = "+id+
                " order by " + Item_Date + " desc ", null);
        return res;
    }

    public Cursor getItemCategoryData(String data,int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + Item_tb + " where " +
                Item_Status + " = " + 2+" and ("+
                Item_Name+" like '%"+data+"%' or "+
                Item_Detail+" like '%"+data+"%') and "+
                Item_Category+" = "+id+
                " order by " + Item_Date + " desc ", null);
        return res;
    }

    public Cursor getItemStateData(int stateID)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor res=db.rawQuery("select * from " + Item_tb +","+UserAddress_tb+ " where " +
                Item_tb+"."+Item_Owner+" = "+UserAddress_tb+"."+UserAddress_State+" and "+
                Item_tb+"."+Item_Status + " = " + 2+" and "+
                UserAddress_tb+"."+UserAddress_State+" = "+stateID+
                " order by " + Item_Date + " desc ", null);
        return res;
    }

    public Cursor getItemNameStateData(String data,int stateID)
    {
        SQLiteDatabase db=this.getWritableDatabase();


        Cursor res=db.rawQuery("select * from " + Item_tb +","+UserAddress_tb+ " where " +
                Item_tb+"."+Item_Owner+" = "+UserAddress_tb+"."+UserAddress_Owner+" and "+
                Item_tb+"."+Item_Status + " = " + 2+" and "+
                Item_tb+"."+Item_Name+" like '%"+data+"%' and "+
                UserAddress_tb+"."+UserAddress_State+" = "+stateID+
                " order by " + Item_Date + " desc ", null);

        return res;
    }

    public Cursor getItemStateData(String data,int stateID)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor res=db.rawQuery("select * from " + Item_tb +","+UserAddress_tb+ " where " +
                Item_tb+"."+Item_Owner+" = "+UserAddress_tb+"."+UserAddress_Owner+" and "+
                Item_tb+"."+Item_Status + " = " + 2+" and ("+
                Item_tb+"."+Item_Name+" like '%"+data+"%' or "+
                Item_tb+"."+Item_Detail+" like '%"+data+"%') and "+
                UserAddress_tb+"."+UserAddress_State+" = "+stateID+
                " order by " + Item_Date + " desc ", null);
        return res;
    }

    public Cursor getItemNameCategoryStateData(String data,int id,int stateID)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor res=db.rawQuery("select * from " + Item_tb +","+UserAddress_tb+ " where " +
                Item_tb+"."+Item_Owner+" = "+UserAddress_tb+"."+UserAddress_Owner+" and "+
                Item_tb+"."+Item_Status + " = " + 2+" and "+
                Item_tb+"."+Item_Name+" like '%"+data+"%' and "+
                Item_tb+"."+Item_Category+" = "+id+" and "+
                UserAddress_tb+"."+UserAddress_State+" = "+stateID+
                " order by " + Item_Date + " desc ", null);
        return res;
    }

    public Cursor getItemCategoryStateData(String data,int id,int stateID)
    {
        SQLiteDatabase db=this.getWritableDatabase();


        Cursor res=db.rawQuery("select * from " + Item_tb +","+UserAddress_tb+ " where " +
                Item_tb+"."+Item_Owner+" = "+UserAddress_tb+"."+UserAddress_Owner+" and "+
                Item_tb+"."+Item_Status + " = " + 2+" and ("+
                        Item_tb+"."+Item_Name+" like '%"+data+"%' or "+
                        Item_tb+"."+Item_Detail+" like '%"+data+"%') and "+
                        Item_tb+"."+Item_Category+" = "+id+" and "+
                        UserAddress_tb+"."+UserAddress_State+" = "+stateID+
                " order by " + Item_Date + " desc ", null);
        return res;
    }

    public boolean insertFeedData(String message,int itemID,int userID){//
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(Feedback_Message,message);//
        contentValues.put(Feedback_Date,getDateTime());
        contentValues.put(Feedback_item,itemID);
        contentValues.put(Feedback_user,userID);

        long result=db.insert(Feedback_tb, null, contentValues);//

        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getFeedbackData(int itemID)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor res=db.rawQuery("select * from " + Feedback_tb + " where " +
                Feedback_item + " = " + itemID, null);

        return res;
    }

    public Cursor getFeedbackLimitData(int itemID)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor res=db.rawQuery("select * from " + Feedback_tb + " where " +
                Feedback_item+" = "+itemID+
                " order by "+Feedback_Date+" desc limit 5", null);


        return res;
    }
}
