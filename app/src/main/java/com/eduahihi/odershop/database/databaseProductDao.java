package com.eduahihi.odershop.database;

import static com.eduahihi.odershop.database.constant.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eduahihi.odershop.model.product;

import java.util.ArrayList;
import java.util.List;

public class databaseProductDao {
    private databaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public databaseProductDao(Context context) {
        databaseHelper = new databaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void insertProduct(product product,onClickSaveProduct onClickSaveProduct){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_PRODUCT,product.getName());
        contentValues.put(COLUMN_PRICE_PRODUCT,product.getPrice());
        contentValues.put(COLUMN_IMAGE_PRODUCT,product.getImage());
        contentValues.put(COLUMN_QUANTITY_PRODUCT,product.getQuantity());
        contentValues.put(COLUMN_DESCRIPTION_PRODUCT,product.getDescription());
        contentValues.put(COLUMN_ID_CATEGORY_PRODUCT,product.getId_category());
        long result = sqLiteDatabase.insert(TABLE_PRODUCT,null,contentValues);
        if (result > 0){
            onClickSaveProduct.success();
        }else {
            onClickSaveProduct.fail();
        }
    }

    public void getAllProduct(int idCategory,onClickGetAll onClickGetAll){
        List<product> products = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_PRODUCT + " WHERE " + COLUMN_ID_CATEGORY_PRODUCT + " = " + idCategory;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                product product = new product();
                product.setId(cursor.getString(0));
                product.setName(cursor.getString(1));
                product.setPrice(cursor.getString(2));
                product.setImage(cursor.getBlob(3));
                product.setQuantity(cursor.getString(4));
                product.setDescription(cursor.getString(5));
                product.setId_category(cursor.getString(6));
                products.add(product);
            }while (cursor.moveToNext());
            onClickGetAll.success(products);
        }else {
            onClickGetAll.fail();
        }
    }

    //get product by id
    public product getProductById(int id){
        product product = new product();
        String query = "SELECT * FROM " + TABLE_PRODUCT + " WHERE " + COLUMN_ID_PRODUCT + " = " + id;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if (cursor.moveToFirst()){
            product.setId(cursor.getString(0));
            product.setName(cursor.getString(1));
            product.setPrice(cursor.getString(2));
            product.setImage(cursor.getBlob(3));
            product.setQuantity(cursor.getString(4));
            product.setDescription(cursor.getString(5));
            product.setId_category(cursor.getString(6));
        }
        return product;
    }

    public void deleteProduct(String id,onClickSaveProduct onClickSaveProduct){
        long result = sqLiteDatabase.delete(TABLE_PRODUCT,COLUMN_ID_PRODUCT + " = ?",new String[]{id});
        if (result > 0){
            onClickSaveProduct.success();
        }else {
            onClickSaveProduct.fail();
        }
    }

    public void updateProduct(product product,onClickSaveProduct onClickSaveProduct){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_PRODUCT,product.getName());
        contentValues.put(COLUMN_PRICE_PRODUCT,product.getPrice());
        contentValues.put(COLUMN_IMAGE_PRODUCT,product.getImage());
        contentValues.put(COLUMN_QUANTITY_PRODUCT,product.getQuantity());
        contentValues.put(COLUMN_DESCRIPTION_PRODUCT,product.getDescription());
        contentValues.put(COLUMN_ID_CATEGORY_PRODUCT,product.getId_category());
        long result = sqLiteDatabase.update(TABLE_PRODUCT,contentValues,COLUMN_ID_PRODUCT + " = ?",new String[]{product.getId()});
        if (result > 0){
            onClickSaveProduct.success();
        }else {
            onClickSaveProduct.fail();
        }
    }

    public interface onClickSaveProduct{
        void success();
        void fail();
    }

    public interface onClickGetAll{
        void success(List<product> product);
        void fail();
    }
}
