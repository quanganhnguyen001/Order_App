package com.eduahihi.odershop.database;

import static com.eduahihi.odershop.database.constant.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eduahihi.odershop.model.user;

public class databaseUserDao {
    private databaseHelper databaseHelper;
    private SQLiteDatabase database;

    public databaseUserDao(Context context) {
        databaseHelper = new databaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    public void insertUser(user user,onClickSignUp onClickSignUp){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_PHONE, user.getPhone());
        values.put(COLUMN_PASSWORD, user.getPassword());
        long result = database.insert(TABLE_USER, null, values);
        if (result > 0){
            onClickSignUp.onSuccess();
        }else {
            onClickSignUp.onFail();
        }
    }

    public void getUserByPhone(String phone,onClickLogin onClickLogin){
        user user = new user();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_PHONE + " = '" + phone + "'";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setPhone(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            onClickLogin.onSuccess(user);
        }else {
            onClickLogin.onFail();
        }
    }

    //get user by id
    public user getUserById(int id){
        user user = new user();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_ID + " = " + id;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setPhone(cursor.getString(2));
            user.setPassword(cursor.getString(3));
        }
        return user;
    }

    //update
    public void updateUser(user user,onClickUpdate onClickUpdate){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_PHONE, user.getPhone());
        values.put(COLUMN_PASSWORD, user.getPassword());
        long result = database.update(TABLE_USER, values, COLUMN_ID + " = " + user.getId(), null);
        if (result > 0){
            onClickUpdate.onSuccess();
        }else {
            onClickUpdate.onFail();
        }
    }
    public interface onClickSignUp{
        void onSuccess();
        void onFail();
    }

    public interface onClickLogin{
        void onSuccess(user user);
        void onFail();
    }

    public interface onClickUpdate{
        void onSuccess();
        void onFail();
    }
}
