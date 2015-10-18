package com.example.jarry.projectpsm;

import android.app.ActionBar;
import android.content.Context;
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
 * Created by Jarry on 27/8/2015.
 */
public class ChatAdapter extends ArrayAdapter<DataProvider> {

    private ArrayList<DataProvider> chat_list=new ArrayList<DataProvider>();
    private TextView chat_text,tv_date,tv_status;
    private ImageView imageView;
    Context CTX;

    public ChatAdapter(Context context, int resource) {
        super(context, resource);
        CTX=context;
    }

    public void add(DataProvider object){
        chat_list.add(object);
        super.add(object);
    }

    public int getCount(){
        return chat_list.size();
    }

    public DataProvider getItem(int position){
        return chat_list.get(position);
    }

    public View getView(int position,View convertView,ViewGroup parent){

        if(convertView==null)
        {
            LayoutInflater inflator=(LayoutInflater)CTX.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflator.inflate(R.layout.message_layout,parent,false);
        }
        chat_text=(TextView)convertView.findViewById(R.id.singleM);
       // imageView=(ImageView)convertView.findViewById(R.id.imageView);
        tv_date=(TextView)convertView.findViewById(R.id.tv_date);
        tv_status=(TextView)convertView.findViewById(R.id.tv_status);

        String Message;
        boolean POSITION;
        String date;
        String status;
        DataProvider provider=getItem(position);
        Message=provider.message;
        POSITION=provider.position;
        date=provider.date;
        status=provider.status;
        chat_text.setText(Message);
        tv_date.setText(date);
        tv_status.setText(status);

        //  chat_text.setBackgroundResource(POSITION ? R.mipmap.left : R.mipmap.right);

        LinearLayout.LayoutParams params1=new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT);;
        LinearLayout.LayoutParams params;
        if(chat_text.length()>28){
            params=new LinearLayout.LayoutParams(600,ActionBar.LayoutParams.WRAP_CONTENT);
        }else
        {
            params=new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT);
        }

        if(!POSITION){
            params.gravity= Gravity.RIGHT;
            params1.gravity= Gravity.RIGHT;
        }else{
            params.gravity= Gravity.LEFT;
            params1.gravity= Gravity.LEFT;
        }
        chat_text.setLayoutParams(params);
        tv_status.setLayoutParams(params);
       // imageView.setLayoutParams(params1);

        return convertView;
    }
}