package com.example.jarry.projectpsm;

import android.graphics.Bitmap;

/**
 * Created by Jarry on 23/8/2015.
 */
public class ImageItem {
    private Bitmap image;
    private String title;
    private int ID;
    // private int image;

    public ImageItem( String title) {
        //public ImageItem(int image,String title){
        super();
        this.title = title;
    }

    public ImageItem(Bitmap image, String title,int ID) {
        //public ImageItem(int image,String title){
        super();
        this.image = image;
        this.title = title;
        this.ID=ID;
    }

    public Bitmap getImage() {
        // public int getImage(){
        return image;
    }

    public void setImage(Bitmap image) {
        // public void setImage(int image){
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setID(int ID){this.ID=ID;}

    public int getID(){return ID;}
}
