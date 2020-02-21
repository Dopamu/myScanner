package com.example.myscanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;


public class DbHelper extends SQLiteOpenHelper {
    public static final String TAG = DbHelper.class.getSimpleName();
    public static final String DB_NAME = "myscanner.db";
    public static final int DB_VERSION = 1;


    public static final String USER_TABLE = "users";
    public static final String COLUMN_ID = "student_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_Class = "CLASS";

    public static final String CREATE_TABLE_USERS = "CREATE TABLE users(student_id INTEGER PRIMARY KEY AUTOINCREMENT, email, TEXT, username TEXT,password TEXT,CLASS TEXT);";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public void addUser(String email, String CLASS,String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_Class, CLASS);


        long id = db.insert(USER_TABLE, null, values);
        db.close();

        Log.d(TAG, "user inserted" + id);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean getUser(String username, String password) {
        //HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = String.format("select * from  %s where %s = '%s' and %s = '%s'", USER_TABLE, COLUMN_USERNAME, username, COLUMN_PASSWORD, password);
        try (SQLiteDatabase db = this.getReadableDatabase()) {
            Cursor cursor = db.rawQuery(selectQuery, null);
            // Move to first row
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {

                return true;
            }
            cursor.close();
            db.close();
        }

        return false;

    }

}
