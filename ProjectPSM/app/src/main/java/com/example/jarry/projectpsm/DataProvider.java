package com.example.jarry.projectpsm;

/**
 * Created by Jarry on 27/8/2015.
 */
public class DataProvider {
    public boolean position;
    public String message;
    public String date;
    public String status;

    public DataProvider(String message, boolean position,String date,String status) {
        super();
        this.message = message;
        this.position = position;
        this.date=date;
        this.status=status;
        }
    }

