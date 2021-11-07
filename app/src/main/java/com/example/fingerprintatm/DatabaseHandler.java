package com.example.fingerprintatm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int  DATABASE_VERSION =1;
    private static final String DATABASE_NAME = "usersManager";
    private static final String TABLE_USERS = "users";
    private static final String USER_ID = "user_id";
    private static final String NAME = "name";
    private static final String PIN = "pin";
    private static final String BALANCE = "balance";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_USERS + "(" + USER_ID + " TEXT PRIMARY KEY," + PIN +" TEXT," + NAME + " TEXT," + BALANCE + " INTEGER" + ")";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
       onCreate(db);
    }

    void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getUserId());
        values.put(PIN, user.getPin());
        values.put(NAME, user.getName());
        values.put(BALANCE, user.getBalance());
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public boolean checkuserId(String userId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where user_id = ?", new String[] {userId});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkuserIdpin(String userId, String pin){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where user_id = ? and pin = ?", new String[] {userId,pin});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    User getUser(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{
                        USER_ID, PIN, NAME, BALANCE},
                USER_ID + "=?", new String[]{userId}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();}
        User user = new User(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3)
        );

        return user;
    }

    public boolean updateBalance(String userId, int balance){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(BALANCE, balance);
        db.update(TABLE_USERS, values, "user_id =?", new String[] {userId});
        return true;
    }

    public boolean updatePin(String userId, String pin){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(PIN, pin);
        db.update(TABLE_USERS, values, "user_id =?", new String[] {userId});
        return true;
    }
}
