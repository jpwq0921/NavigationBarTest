package com.sp.navigationbartest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Blob;

public class RestaurantHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "restaurantlist.db";
    private static final int SCHEMA_VERSION = 1;

    public RestaurantHelper(Context context)
    {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //will bbe called once when the database is not created
        db.execSQL("CREATE TABLE restaurants_table (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " userName TEXT, userAge TEXT, userLocation TEXT, userGender TEXT ,  userPlatform TEXT, userGenre TEXT, lat REAL, lon REAL, userImage BLOB );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //will not be called until SCHEMA_VERSION increases
        //Here we can upgrade the database e.g. add more tables
    }

    /*Read all records from restaurants_table*/
    public Cursor getAll()
    {
        return(getReadableDatabase().rawQuery(
                "SELECT _id, userName, userAge, userLocation," +
                        "    userGender,userPlatform, userGenre, lat, lon, userImage FROM restaurants_table ORDER BY userName", null));
    }

    /*Read all records from restaurants_table*/
    public Cursor getById(String id){
        String[] args ={id};

        return (getReadableDatabase().rawQuery(
                "SELECT _id, userName, userAge, userLocation, " +
                        "   userGender,userPlatform,  userGenre, lat, lon, userImage FROM restaurants_table WHERE _ID = ?", args));
    }

    /* Write a record into restaurants_table*/
    public void insert(String userName, String userAge,
                       String userLocation ,String userGender, String userPlatform, String userGenre, double lat, double lon, byte[] userImage)
    {
        ContentValues cv = new ContentValues();

        cv.put("userName", userName);
        cv.put("userAge", userAge);
        cv.put("userLocation", userLocation);
        cv.put("userGender", userGender);
        //cv.put("userComp", userComp);
        cv.put("userPlatform", userPlatform);
        cv.put("userGenre", userGenre);
        cv.put("userImage", userImage);
        cv.put("lat", lat);
        cv.put("lon", lon);


        getWritableDatabase().insert("restaurants_table", "userName", cv);
    }

    public void update(String id, String userName, String userAge, String userLocation,String userGender, String userPlatform, String userGenre, double lat, double lon, byte[] userImage){
        ContentValues cv = new ContentValues();
        String[] args = {id};
        cv.put("userName", userName);
        cv.put("userAge", userAge);
        cv.put("userLocation", userLocation);
        cv.put("userGender", userGender);
        //cv.put("userComp", userComp);
        cv.put("userPlatform", userPlatform);
        cv.put("userGenre", userGenre);
        cv.put("userImage", userImage);
        cv.put("lat", lat);
        cv.put("lon", lon);


        getWritableDatabase().update("restaurants_table", cv, " _ID = ?", args);
    }

    public String getID(Cursor c){
        return (c.getString(0));
    }
    public String getuserName(Cursor c){
        return (c.getString(1));
    }
    public String getuserAge(Cursor c){
        return (c.getString(2));
    }
    public String getuserLocation(Cursor c){
        return (c.getString(3));
    }
    public String getuserGender(Cursor c){
        return (c.getString(4));
    }
    //public String getuserComp(Cursor c){return (c.getString(5));}
    public String getuserPlatform(Cursor c){return (c.getString(5));}
    public String getuserGenre(Cursor c){return (c.getString(6));}
    //public byte[] getuserImage(Cursor c){return (c.getBlob(8));}
    public double getLatitude(Cursor c){return (c.getDouble(7));}
    public double getLongitude (Cursor c){return (c.getDouble(8));}
    public byte[] getuserImage(Cursor c){return (c.getBlob(9));}
}
