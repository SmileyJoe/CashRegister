package za.co.smileyjoedev.cashregister.object;

import za.co.smileyjoedev.db.DbObject;

/**
 * Created by cody on 2015/04/04.
 */
public class Item extends DbObject {

    private String mTitle;
    private float mPrice;

    public void setTitle(String title){
        mTitle = title;
    }

    public void setPrice(float price){
        mPrice = price;
    }

    public String getTitle(){
        return mTitle;
    }

    public float getPrice(){
        return mPrice;
    }



}
