package com.eduahihi.odershop.database;

import static com.eduahihi.odershop.database.constant.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eduahihi.odershop.model.cart;

import java.util.ArrayList;
import java.util.List;

public class databaseCartDao {
    private databaseHelper databaseHelper;
    private SQLiteDatabase database;
    private databaseProductDao databaseProductDao;
    public databaseCartDao(Context context) {
        databaseHelper = new databaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        databaseProductDao = new databaseProductDao(context);
    }

    public void insertCart(cart card, onClickAddCard onClickAddCard){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_PRODUCT_CART, card.getId_product());
        values.put(COLUMN_ID_USER_CART, card.getId_user());
        values.put(COLUMN_QUANTITY_CART, card.getQuantity());
        values.put(COLUMN_DATE_CART, card.getDate());
        values.put(COLUMN_STATUS_CART, false);
        values.put(COLUMN_ISCART, false);
        long result = database.insert(TABLE_CART, null, values);
        if (result > 0){
            onClickAddCard.onSuccess();
        }else {
            onClickAddCard.onFail();
        }
    }

    //get cart by idUser and status = false
    public void getCartByIdUser(int id_user,onClickGetAll onClickGetAll){
        List<cart> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_CART + " WHERE " + COLUMN_ID_USER_CART + " = " + id_user;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                cart card = new cart();
                card.setId(cursor.getInt(0));
                card.setId_product(cursor.getInt(1));
                card.setId_user(cursor.getInt(2));
                card.setQuantity(cursor.getInt(3));
                card.setDate(Long.parseLong(cursor.getString(4)));
                card.setStatus(cursor.getInt(5));
                card.setIsCart(cursor.getInt(6));
                list.add(card);
            }while (cursor.moveToNext());
            onClickGetAll.onSuccess(list);
        }else {
            onClickGetAll.onFail();
        }
    }
    //get cart by idUser and have date === date
    public void getCartByIdUserAndDate(int id_user,onClickGetAll onClickGetAll){
        List<cart> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_CART + " WHERE " + COLUMN_ID_USER_CART + " = " + id_user + " AND " + COLUMN_DATE_CART + " = " + System.currentTimeMillis();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                cart card = new cart();
                card.setId(cursor.getInt(0));
                card.setId_product(cursor.getInt(1));
                card.setId_user(cursor.getInt(2));
                card.setQuantity(cursor.getInt(3));
                card.setDate(Long.parseLong(cursor.getString(4)));
                card.setStatus(cursor.getInt(5));
                card.setIsCart(cursor.getInt(6));
                list.add(card);
            }while (cursor.moveToNext());
            onClickGetAll.onSuccess(list);
        }else {
            onClickGetAll.onFail();
        }
    }

    //update

    public void getCartsByIdUser(int id_user,onClickGetAll onClickGetAll){
        List<cart> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_CART + " WHERE " + COLUMN_ID_USER_CART + " = " + id_user;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                cart card = new cart();
                card.setId(cursor.getInt(0));
                card.setId_product(cursor.getInt(1));
                card.setId_user(cursor.getInt(2));
                card.setQuantity(cursor.getInt(3));
                card.setDate(Long.parseLong(cursor.getString(4)));
                card.setStatus(cursor.getInt(5));
                card.setIsCart(cursor.getInt(6));
                list.add(card);
            }while (cursor.moveToNext());
            onClickGetAll.onSuccess(list);
        }else {
            onClickGetAll.onFail();
        }
    }

    //get cart by idUser and idProduct
    public void getCartByIdUserAndIdProduct(int id_user, int id_product, onClickGetOne onClickGetOne){
        cart card = new cart();
        String query = "SELECT * FROM " + TABLE_CART + " WHERE " + COLUMN_ID_USER_CART + " = " + id_user + " AND " + COLUMN_ID_PRODUCT_CART + " = " + id_product;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                card.setId(cursor.getInt(0));
                card.setId_product(cursor.getInt(1));
                card.setId_user(cursor.getInt(2));
                card.setQuantity(cursor.getInt(3));
                card.setDate(Long.parseLong(cursor.getString(4)));
                card.setStatus(cursor.getInt(5));
                card.setIsCart(cursor.getInt(6));
            }while (cursor.moveToNext());
            onClickGetOne.onSuccess(card);
        }else {
            onClickGetOne.onFail();
        }
    }

    //getAll cart
    public void getAllCart(onClickGetAll onClickGetAll){
        List<cart> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_CART;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                cart card = new cart();
                card.setId(cursor.getInt(0));
                card.setId_product(cursor.getInt(1));
                card.setId_user(cursor.getInt(2));
                card.setQuantity(cursor.getInt(3));
                card.setDate(Long.parseLong(cursor.getString(4)));
                card.setStatus(cursor.getInt(5));
                card.setIsCart(cursor.getInt(6));
                list.add(card);
            }while (cursor.moveToNext());
            onClickGetAll.onSuccess(list);
        }else {
            onClickGetAll.onFail();
        }
    }

    //get all idUser by status = 1
    public void getAllIdUser(onClickGetAllIdUser onClickGetAllIdUser){
        List<Integer> list = new ArrayList<>();
        String query = "SELECT DISTINCT " + COLUMN_ID_USER_CART + " FROM " + TABLE_CART + " WHERE " + COLUMN_STATUS_CART + " = 0 " + " OR " + COLUMN_STATUS_CART + " = 2";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                list.add(cursor.getInt(0));
            }while (cursor.moveToNext());
            onClickGetAllIdUser.onSuccess(list);
        }else {
            onClickGetAllIdUser.onFail();
        }
    }
    //update status cart by idUser
    public void updateStatusCart(int id_user,int status ,onClickAddCard onClickAddCard){
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS_CART, status);
        long result = database.update(TABLE_CART, values, COLUMN_ID_USER_CART + " = " + id_user, null);
        if (result > 0){
            onClickAddCard.onSuccess();
        }else {
            onClickAddCard.onFail();
        }
    }

    public void updateCart(cart card, onClickAddCard onClickAddCard){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_PRODUCT_CART, card.getId_product());
        values.put(COLUMN_ID_USER_CART, card.getId_user());
        values.put(COLUMN_QUANTITY_CART, card.getQuantity());
        values.put(COLUMN_DATE_CART, card.getDate());
        values.put(COLUMN_STATUS_CART, card.isStatus());
        values.put(COLUMN_ISCART, card.getIsCart());
        long result = database.update(TABLE_CART, values, COLUMN_ID_CART + " = " + card.getId() + " AND " + COLUMN_ID_USER_CART + " = " + card.getId_user(), null);
        if (result > 0){
            onClickAddCard.onSuccess();
        }else {
            onClickAddCard.onFail();
        }
    }
    //update IScart by id
    public void updateCartById(int id,int isCart ,onClickDeleteCart onClickDeleteCart){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ISCART, isCart);
        long result = database.update(TABLE_CART, values, COLUMN_ID_CART + " = " + id, null);
        if (result > 0){
            onClickDeleteCart.onSuccess();
        }else {
            onClickDeleteCart.onFail();
        }
    }

    public void deleteCart(int id, onClickDeleteCart onClickDeleteCart){
        long result = database.delete(TABLE_CART, COLUMN_ID_CART + " = " + id, null);
        if (result > 0){
            onClickDeleteCart.onSuccess();
        }else {
            onClickDeleteCart.onFail();
        }
    }

    public void deleteCartByIdUser(int id_user, onClickDeleteCart onClickDeleteCart){
        long result = database.delete(TABLE_CART, COLUMN_ID_USER_CART + " = " + id_user, null);
        if (result > 0){
            onClickDeleteCart.onSuccess();
        }else {
            onClickDeleteCart.onFail();
        }
    }
    //get quantity by idUser and idProduct
    public int getQuantityByIdUserAndIdProduct(int id_user, int id_product){
        int quantity = 0;
        String query = "SELECT " + COLUMN_QUANTITY_CART + " FROM " + TABLE_CART + " WHERE " + COLUMN_ID_USER_CART + " = " + id_user + " AND " + COLUMN_ID_PRODUCT_CART + " = " + id_product;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                quantity = cursor.getInt(3);
            }while (cursor.moveToNext());
        }
        return quantity;
    }
    //delete cart by idUser and status = 0
    public void deleteCartByIdUserAndStatus(int id_user, onClickDeleteCart onClickDeleteCart){
        long result = database.delete(TABLE_CART, COLUMN_ID_USER_CART + " = " + id_user + " AND " + COLUMN_STATUS_CART + " = 0", null);
        if (result > 0){
            onClickDeleteCart.onSuccess();
        }else {
            onClickDeleteCart.onFail();
        }
    }


    public interface onClickAddCard{
        void onSuccess();
        void onFail();
    }

    public interface onClickDeleteCart{
        void onSuccess();
        void onFail();
    }

    public interface onClickGetAll{
        void onSuccess(List<cart> list);
        void onFail();
    }

    public interface onClickGetOne{
        void onSuccess(cart card);
        void onFail();
    }

    public interface onClickGetAllIdUser{
        void onSuccess(List<Integer> list);
        void onFail();
    }
}
