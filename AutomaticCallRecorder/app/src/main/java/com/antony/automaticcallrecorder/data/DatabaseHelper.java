package com.antony.automaticcallrecorder.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.antony.automaticcallrecorder.Utilities.Static_keys;
import com.antony.automaticcallrecorder.data.Pojo.Blockednumber;
import com.antony.automaticcallrecorder.data.Pojo.Lockednumber;
import com.antony.automaticcallrecorder.data.Pojo.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antony on 6/28/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase sqLiteDatabase;


    public DatabaseHelper(Context context) {
        super(context, "Calltrap", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String table_recording = "CREATE TABLE " + Static_keys.Table_Recording.tablename + " (" + Static_keys.Table_Recording.id + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Static_keys.Table_Recording.name + " TEXT ," + Static_keys.Table_Recording.number + " TEXT ," + Static_keys.Table_Recording.favourite + " INTEGER , " + Static_keys.Table_Recording.callstatus + " INTEGER , " + Static_keys.Table_Recording.Date + " TEXT ," + Static_keys.Table_Recording.time + " TEXT , " + Static_keys.Table_Recording.filename + " TEXT " + ")";

        db.execSQL(table_recording);


        String table_blocked = "CREATE TABLE " + Static_keys.Table_Blockednumber.tablename + " (" + Static_keys.Table_Blockednumber.id + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Static_keys.Table_Blockednumber.name + " TEXT ," + Static_keys.Table_Blockednumber.number + " TEXT "  + ")";

        db.execSQL(table_blocked);

        String table_locked = "CREATE TABLE " + Static_keys.Table_Block_password.tablename + " (" + Static_keys.Table_Block_password.id + " INTEGER PRIMARY KEY AUTOINCREMENT , " +Static_keys.Table_Block_password.password + " TEXT ,"+ Static_keys.Table_Block_password.name + " TEXT ," + Static_keys.Table_Block_password.number + " TEXT "  + ")";

        db.execSQL(table_locked);


    }



    public void delete_lockednumber(Lockednumber lockednumber)
    {
        sqLiteDatabase=this.getWritableDatabase();

        sqLiteDatabase.delete(Static_keys.Table_Blockednumber.tablename,Static_keys.Table_Block_password.id+"=?",new String[]{String.valueOf(lockednumber.getId())});





    }


    public void addLockednumber(Lockednumber lockednumber) {

        sqLiteDatabase = this.getWritableDatabase();

        try {

            sqLiteDatabase.beginTransaction();


            ContentValues contentValues = new ContentValues();
            contentValues.put(Static_keys.Table_Block_password.name, lockednumber.getName());
            contentValues.put(Static_keys.Table_Block_password.number, lockednumber.getNumber());
            contentValues.put(Static_keys.Table_Block_password.password, lockednumber.getPassword());



            sqLiteDatabase.insert(Static_keys.Table_Block_password.tablename, null, contentValues);

            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();
        }


    }


    public List<Lockednumber> getAll_Lockednumber() {
        sqLiteDatabase = this.getWritableDatabase();

        String query = "SELECT * FROM " + Static_keys.Table_Block_password.tablename;

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        List<Lockednumber> records = new ArrayList<>();

        while (cursor.moveToNext()) {
            Lockednumber blockednumber=new Lockednumber();
            blockednumber.setId(cursor.getInt(cursor.getColumnIndex(Static_keys.Table_Block_password.id)));
            blockednumber.setName(cursor.getString(cursor.getColumnIndex(Static_keys.Table_Block_password.name)));
            blockednumber.setNumber(cursor.getString(cursor.getColumnIndex(Static_keys.Table_Block_password.number)));

            blockednumber.setPassword(cursor.getString(cursor.getColumnIndex(Static_keys.Table_Block_password.password)));


            records.add(blockednumber);
        }

        cursor.close();



        return records;


    }







    public void addRecording(Record record) {

        sqLiteDatabase = this.getWritableDatabase();

        try {

            sqLiteDatabase.beginTransaction();


            ContentValues contentValues = new ContentValues();
            contentValues.put(Static_keys.Table_Recording.favourite, record.getFavourite());
            contentValues.put(Static_keys.Table_Recording.filename, record.getFilepath());
            contentValues.put(Static_keys.Table_Recording.name, record.getName());
            contentValues.put(Static_keys.Table_Recording.number, record.getPhonenumber());
            contentValues.put(Static_keys.Table_Recording.callstatus, record.getCallstatus());
            contentValues.put(Static_keys.Table_Recording.Date, record.getDate());
            contentValues.put(Static_keys.Table_Recording.time, record.getTime());

            sqLiteDatabase.insert(Static_keys.Table_Recording.tablename, null, contentValues);

            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();
        }


    }


    public void addBlockednumber(Blockednumber blockednumber) {

        sqLiteDatabase = this.getWritableDatabase();

        try {

            sqLiteDatabase.beginTransaction();


            ContentValues contentValues = new ContentValues();
            contentValues.put(Static_keys.Table_Blockednumber.name, blockednumber.getName());
            contentValues.put(Static_keys.Table_Recording.number, blockednumber.getPhno());



            sqLiteDatabase.insert(Static_keys.Table_Blockednumber.tablename, null, contentValues);

            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();
        }


    }


    public List<Blockednumber> getAllBlockednumber() {
        sqLiteDatabase = this.getWritableDatabase();

        String query = "SELECT * FROM " + Static_keys.Table_Blockednumber.tablename;

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        List<Blockednumber> records = new ArrayList<>();

            while (cursor.moveToNext()) {
              Blockednumber blockednumber=new Blockednumber();
                blockednumber.setId(cursor.getInt(cursor.getColumnIndex(Static_keys.Table_Blockednumber.id)));
                blockednumber.setName(cursor.getString(cursor.getColumnIndex(Static_keys.Table_Blockednumber.name)));
                blockednumber.setPhno(cursor.getString(cursor.getColumnIndex(Static_keys.Table_Blockednumber.number)));


                      records.add(blockednumber);
            }

            cursor.close();



        return records;


    }


    public void delete_Blockednumber(Blockednumber blockednumber)
    {
        sqLiteDatabase=this.getWritableDatabase();

        sqLiteDatabase.delete(Static_keys.Table_Blockednumber.tablename,Static_keys.Table_Blockednumber.id+"=?",new String[]{String.valueOf(blockednumber.getId())});





    }


    public void update_Recording(Record record) {

        sqLiteDatabase = this.getWritableDatabase();

        try {

            sqLiteDatabase.beginTransaction();


            ContentValues contentValues = new ContentValues();
            contentValues.put(Static_keys.Table_Recording.favourite, record.getFavourite());
            contentValues.put(Static_keys.Table_Recording.filename, record.getFilepath());
            contentValues.put(Static_keys.Table_Recording.name, record.getName());
            contentValues.put(Static_keys.Table_Recording.number, record.getPhonenumber());
            contentValues.put(Static_keys.Table_Recording.callstatus, record.getCallstatus());
            contentValues.put(Static_keys.Table_Recording.Date, record.getDate());
            contentValues.put(Static_keys.Table_Recording.time, record.getTime());

            sqLiteDatabase.update(Static_keys.Table_Recording.tablename, contentValues, Static_keys.Table_Recording.id + " =?", new String[]{String.valueOf(record.getId())});

            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {

            Log.e("Database", e.toString());

        } finally {
            sqLiteDatabase.endTransaction();
//            sqLiteDatabase.close();
        }


    }


    public List<Record> getAllRecord() {
        sqLiteDatabase = this.getWritableDatabase();

        String query = "SELECT * FROM " + Static_keys.Table_Recording.tablename;

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        List<Record> records = new ArrayList<>();

            while (cursor.moveToNext()) {
                Record record = new Record();
                record.setCallstatus(cursor.getInt(cursor.getColumnIndex(Static_keys.Table_Recording.callstatus)));
                record.setFavourite(cursor.getInt(cursor.getColumnIndex(Static_keys.Table_Recording.favourite)));
                record.setFilepath(cursor.getString(cursor.getColumnIndex(Static_keys.Table_Recording.filename)));
                record.setName(cursor.getString(cursor.getColumnIndex(Static_keys.Table_Recording.name)));
                record.setPhonenumber(cursor.getString(cursor.getColumnIndex(Static_keys.Table_Recording.number)));
                record.setId(cursor.getInt(cursor.getColumnIndex(Static_keys.Table_Recording.id)));
                record.setDate(cursor.getString(cursor.getColumnIndex(Static_keys.Table_Recording.Date)));
                record.setTime(cursor.getString(cursor.getColumnIndex(Static_keys.Table_Recording.time)));

                records.add(record);
            }

            cursor.close();



        return records;


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
