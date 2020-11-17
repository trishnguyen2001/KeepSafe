package com.example.keepsafe_v2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PINStorage extends SQLiteOpenHelper {
    private static final String TAG = "PINStorage";

    private static final String TABLE_NAME = "pinTable";
    private static final String COL_0 = "ID";
    private static final String COL_1 = "PIN";

    public PINStorage(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_1 + " TEXT DEFAULT '0000');";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //adds new pin to db
    public boolean newPin(String newPin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //insert new PIN into db
        cv.put(COL_1, newPin);

        Log.d(TAG, "addPIN: Adding " + newPin + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //returns pin in db
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //clears db
    public void clearDatabase(String TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM " + TABLE_NAME;
        db.execSQL(clearDBQuery);
    }

    //checks if database is empty or not --> has pin or not
    public boolean isEmpty() {
        Cursor data = this.getData();
        return data.getCount() <= 0;
    }
}
