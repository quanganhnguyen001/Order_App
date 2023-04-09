package com.eduahihi.odershop.database;

import static com.eduahihi.odershop.database.constant.*;
import static com.eduahihi.odershop.database.constant.TABLE_ORDER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eduahihi.odershop.model.order;

import java.util.ArrayList;
import java.util.List;

public class databaseOrderDao {
    private databaseHelper databaseHelper;
    private SQLiteDatabase database;

    public databaseOrderDao(Context context) {
        databaseHelper = new databaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    public void insertOrder(order order, onClickAddOrder onClickAddOrder) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_USER_ORDER, order.getId_user());
        values.put(COLUMN_ID_CART_ORDER, order.getId_cart());
        values.put(COLUMN_DATE_ORDER, order.getDate());
        values.put(COLUMN_STATUS_ORDER, false);
        long result = database.insert(TABLE_ORDER, null, values);
        if (result > 0) {
            onClickAddOrder.onSuccess();
        } else {
            onClickAddOrder.onFail();
        }
    }
    public void updateStatusOrder(int id_order, boolean status){
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS_ORDER, status);
        database.update(TABLE_ORDER, values, COLUMN_ID_ORDER + " = " + id_order, null);
    }
    public void getOrderByIdUser(int id_user, onClickGetAll onClickGetAll){
        List<order> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_ORDER + " WHERE " + COLUMN_ID_USER_ORDER + " = " + id_user;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                order order = new order();
                order.setId(cursor.getInt(0));
                order.setId_user(cursor.getInt(1));
                order.setId_cart(cursor.getInt(2));
                order.setDate(Long.parseLong(cursor.getString(3)));
                order.setStatus(cursor.getInt(4) == 1);
                list.add(order);
            }while (cursor.moveToNext());
            onClickGetAll.onSuccess(list);
        }else {
            onClickGetAll.onFail();
        }
    }
    public interface onClickAddOrder {
        void onSuccess();

        void onFail();
    }

    public interface onClickGetAll {
        void onSuccess(List<order> list);

        void onFail();
    }
}
