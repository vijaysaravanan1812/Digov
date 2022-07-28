package com.example.digov;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper
{

    /**
     * creating a constant variables for our database.
     * below variable is for our database name.
     */
    private static final String DB_NAME = "items.db";

    /**
     *  below int is our database version
     */
    private static final int DB_VERSION = 1;

    /**
     *  below variable is for our table name.
     */
    private static final String TABLE_NAME = "item";

    /**
     *  below variable is for our id column.
     */
    private static final String ID_COL = "id";

    /**
     *  below variable is for our course name column
     */
    private static final String ITEM_NAME = "Item_name";
    private static final String PRICE = "Price";




    /**
     *  creating a constructor for our database handler.
     * @param context
     */
    public Database(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     *  below method is for creating a database by running a sqlite query
     * @param db
     */
    @Override
    public void onCreate(@NonNull SQLiteDatabase db)
    {
        /**
         * on below line we are creating an sqlite query and we are
         * setting our column names along with their data types.
         */
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ITEM_NAME + " TEXT ," +
                PRICE + " INTEGER )";


        Log.d("Create Database:", "Successful");

        /**
         *  at last we are calling a exec sql
         * method to execute above sql query
         */
        db.execSQL(query);
    }

    /**
     * this method is use to add new course to our sqlite database.
     * @param item_Name
     * @return
     */
    public boolean addOne(String item_Name  , int price)
    {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(ITEM_NAME, item_Name);
        values.put(PRICE , price);

        // after adding all values we are passing
        // content values to our table.
        long insert =  db.insert(TABLE_NAME, null, values);
        db.close();
        if(insert == - 1)
        {
            return  false;
        }
        else {
            return  true;
        }
        // at last we are closing our
        // database after adding database.

    }



    public List<Item> getEveryone()
    {
        List<Item> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        //creating cursor object to fetch the result of query
        Cursor cursor = db.rawQuery(queryString , null);

        if (cursor.moveToFirst())
        {
            do {
                String Item_Name = cursor.getString(1);
                int price = cursor.getInt(2);
                Item item = new Item(Item_Name , price);
                returnList.add(item);
            } while (cursor.moveToNext());
        }
        else {
            // fail to add
        }

        //close the cursor and database
        cursor.close();
        db.close();

        return returnList;
    }

    public String getCount(){
        String Count;
        SQLiteDatabase db = this.getReadableDatabase();
        String sumQuery = "SELECT COUNT (" + PRICE + ") AS TOTAL FROM " + TABLE_NAME +";";
        Cursor cursor = db.rawQuery(sumQuery, null);

        //check condition
        if(cursor.moveToFirst()){
            //when cursor move to first item
            //get count
            Count= String.valueOf(cursor.getInt(0));
        }
        else {
            Count = "0";
        }
        db.close();
        return Count;
    }

    public String GetSum(){
        String Sum ;
        SQLiteDatabase db = this.getReadableDatabase();

        //Getting querry for sum
        String sumQuery = "SELECT SUM (" + PRICE + ") AS TOTAL FROM " + TABLE_NAME;

        //Intialize cursor
        Cursor cursor = db.rawQuery(sumQuery, null);
        //Check condition
        if(cursor.moveToFirst()){
            //when cursor move to first item
            //get count
            Sum= String.valueOf(cursor.getInt(0));
        }
        else {
            Sum = "0";
        }
        cursor.close();
        db.close();
        return Sum;
    }

    public boolean delete()
    {
        String query = "DELETE FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query , null);

        if (cursor.moveToFirst())
        {
            cursor.close();
            db.close();
            return true;
        }
        else
        {
            cursor.close();
            db.close();
            return false;
        }
    }

    public boolean deleteOne(int val){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COL + " = " + val;
        Cursor cursor = db.rawQuery(Query , null);
        if (cursor.moveToFirst())
        {
            cursor.close();
            db.close();
            return true;
        }
        else
        {
            cursor.close();
            db.close();
            return false;
        }
    }
}


