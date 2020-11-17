package com.example.keepsafe_v2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "EntryList";
    private static final String COL_0 = "ID";
    private static final String COL_1 = "Title";
    private static final String COL_2 = "User";
    private static final String COL_3 = "Password";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" + COL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_1 + " TEXT," + COL_2 + " TEXT," + COL_3 + " TEXT" + ");";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //adds new node to db
    public boolean addData(Node n) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_1, n.title);
        cv.put(COL_2, n.user);
        cv.put(COL_3, n.pw);

        Log.d(TAG, "addData: Adding " + n + " to " + TABLE_NAME
                + "title/user/pw: " + n.title + n.user + n.pw);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //gets id based on title
    public Cursor getItemID(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL_0 + " FROM " + TABLE_NAME +
                " WHERE " + COL_1 + " = '" + title + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //gets user based on title
    public Cursor getItemUser(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL_2 + " FROM " + TABLE_NAME +
                " WHERE " + COL_1 + " = '" + title + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //gets pw based on title
    public Cursor getItemPw(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL_3 + " FROM " + TABLE_NAME +
                " WHERE " + COL_1 + " = '" + title + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //updates title field in database
    public void updateTitle(String newTitle, int id, String oldTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL_1 +
                " = '" + newTitle + "' WHERE " + COL_0 + " = '" + id + "'" +
                " AND " + COL_1 + " = '" + oldTitle + "'";

        Log.d(TAG, "updatedTitle: query: " + query);
        Log.d(TAG, "updatedTitle: NewTitle: " + newTitle);
        db.execSQL(query);
    }

    //updates user field in database
    public void updateUser(String newUser, int id, String oldUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL_2 +
                " = '" + newUser + "' WHERE " + COL_0 + " = '" + id + "'" +
                " AND " + COL_2 + " = '" + oldUser + "'";

        Log.d(TAG, "updatedUser: query: " + query);
        Log.d(TAG, "updatedUser: NewUser: " + newUser);
        db.execSQL(query);
    }

    //updates pw field in database
    public void updatePw(String newPw, int id, String oldPw) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL_3 +
                " = '" + newPw + "' WHERE " + COL_0 + " = '" + id + "'" +
                " AND " + COL_3 + " = '" + oldPw + "'";

        Log.d(TAG, "updatedPw: query: " + query);
        Log.d(TAG, "updatedPw: NewUser: " + newPw);
        db.execSQL(query);
    }

    //deletes title in database
    public void deleteTitle(int id, String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_0 +
                " = '" + id + "'" +
                " AND " + COL_1 + " = '" + title + "'";

        Log.d(TAG, "deleteTitle: query: " + query);
        Log.d(TAG, "deleteTitle: Deleting: " + title);
        db.execSQL(query);
    }

    //deletes user in database
    public void deleteUser(int id, String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_0 +
                " = '" + id + "'" +
                " AND " + COL_2 + " = '" + user + "'";

        Log.d(TAG, "deleteUser: query: " + query);
        Log.d(TAG, "deleteUser: Deleting: " + user);
        db.execSQL(query);
    }

    //deletes pw in database
    public void deletePw(int id, String pw) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_0 +
                " = '" + id + "'" +
                " AND " + COL_3 + " = '" + pw + "'";

        Log.d(TAG, "deletePw: query: " + query);
        Log.d(TAG, "deletePw: Deleting: " + pw);
        db.execSQL(query);
    }
}
