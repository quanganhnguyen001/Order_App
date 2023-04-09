package com.eduahihi.odershop.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eduahihi.odershop.model.category;

import java.util.ArrayList;
import java.util.List;

public class databaseCategoryDao {
    private databaseHelper databaseHelper;
    private SQLiteDatabase database;

    public databaseCategoryDao(Context context) {
        databaseHelper = new databaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    //insert category
    public void insertCategory(category category) {
        String sql = "INSERT INTO category VALUES (null, ?)";
        database.execSQL(sql, new String[]{category.getName()});
    }

    //update category
    public void updateCategory(category category) {
        String sql = "UPDATE category SET name = ? WHERE id = ?";
        database.execSQL(sql, new String[]{category.getName(), String.valueOf(category.getId())});
    }

    //delete category
    public void deleteCategory(int id) {
        String sql = "DELETE FROM category WHERE id = ?";
        database.execSQL(sql, new String[]{String.valueOf(id)});
    }
    //check category exist
    public boolean checkCategoryExist(String name) {
        String sql = "SELECT * FROM category WHERE name = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{name});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        return false;
    }
    //get Allcategory
    public List<category> getAllCategory() {
        List<category> list = new ArrayList<>();
        String sql = "SELECT * FROM category";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                list.add(new category(id, name));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }
}
