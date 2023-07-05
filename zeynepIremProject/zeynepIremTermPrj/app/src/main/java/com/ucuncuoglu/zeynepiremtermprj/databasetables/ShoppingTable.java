package com.ucuncuoglu.zeynepiremtermprj.databasetables;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.ucuncuoglu.zeynepiremtermprj.Activity;
import com.ucuncuoglu.zeynepiremtermprj.databasehelpers.ShoppingDBHelper;

import java.util.ArrayList;

public class ShoppingTable {
    public static String TABLE_NAME="shopping";
    public static String FIELD_ID = "id";
    public static String FIELD_TOPIC = "topic";
    public static String FIELD_DUEDATE = "dueDate";

    public static String CREATE_TABLE_SQL="CREATE TABLE "+TABLE_NAME+" ("+FIELD_ID+" number, "+FIELD_TOPIC +" text, "+FIELD_DUEDATE+" text);";
    public static String DROP_TABLE_SQL = "DROP TABLE if exists "+TABLE_NAME;

    public static ArrayList<Activity> getAllShops(ShoppingDBHelper dbHelper){
        Activity anItem;
        ArrayList<Activity> data = new ArrayList<>();
        Cursor cursor = dbHelper.getAllRecords(TABLE_NAME, null);
        Log.d("DATABASE OPERATIONS", cursor.getCount()+",  "+cursor.getColumnCount());
        while(cursor.moveToNext()){
            int id=cursor.getInt(0);
            String topic= cursor.getString(1);
            String dueDate= cursor.getString(2);
            anItem = new Activity(id, topic, dueDate);
            data.add(anItem);
        }
        Log.d("DATABASE OPERATIONS",data.toString());
        return data;
    }

    public static ArrayList<Activity> findContact(ShoppingDBHelper dbHelper, String key) {
        Activity anItem;
        ArrayList<Activity> data = new ArrayList<>();
        String where = FIELD_TOPIC +" like '%"+key+"%'";

        Cursor cursor = dbHelper.getSomeRecords(TABLE_NAME, null, where);
        Log.d("DATABASE OPERATIONS",  where+", "+cursor.getCount()+",  "+cursor.getColumnCount());
        while(cursor.moveToNext()){
            int id=cursor.getInt(0);
            String topic= cursor.getString(1);
            String dueDate= cursor.getString(2);
            anItem = new Activity(id, topic, dueDate);
            data.add(anItem);
        }
        Log.d("DATABASE OPERATIONS",data.toString());
        return data;
    }

    public static boolean insert(ShoppingDBHelper dbHelper, int id, String topic, String dueDate) {
        ContentValues contentValues = new ContentValues( );
        contentValues.put(FIELD_ID, id);
        contentValues.put(FIELD_TOPIC, topic);
        contentValues.put(FIELD_DUEDATE, dueDate);

        boolean res = dbHelper.insert(TABLE_NAME,contentValues);
        return res;
    }

    public static boolean update(ShoppingDBHelper dbHelper, String id, String topic, String dueDate) {
        //ContentValues  allows to define key value pairs.
        //The key represents the table column identifier and the value represents the content for the table record in this column.
        //ContentVales can be used for insert and update operations over table

        ContentValues contentValues = new ContentValues( );
        contentValues.put(FIELD_TOPIC, topic);
        contentValues.put(FIELD_DUEDATE, dueDate);

        String where = FIELD_ID +" = "+id;
        boolean res = dbHelper.update(TABLE_NAME,contentValues,where );
        return res;
    }

    public static boolean delete(ShoppingDBHelper dbHelper, String id){
        Log.d("DATABASE OPERATIONS", "DELETE DONE");
        String where = FIELD_ID + " = "+id;
        boolean res =  dbHelper.delete(TABLE_NAME, where);
        return  res;
    }
}
