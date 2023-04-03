package com.eduahihi.odershop.database;

import static com.eduahihi.odershop.database.constant.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class databaseHelper extends SQLiteOpenHelper {
    public databaseHelper(@Nullable Context context) {
        super(context, "odershop", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_TABLE_CART);
        db.execSQL(CREATE_TABLE_ORDER);
        db.execSQL(CREATE_TABLE_CATEGORY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USER);
        db.execSQL(DROP_TABLE_PRODUCT);
        db.execSQL(DROP_TABLE_CART);
        db.execSQL(DROP_TABLE_ORDER);
        db.execSQL(DROP_TABLE_CATEGORY);
        onCreate(db);
    }
}
